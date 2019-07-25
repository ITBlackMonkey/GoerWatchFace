package com.goertek.commonlib.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;

import com.goertek.commonlib.provider.manager.AssetPackage;
import com.goertek.commonlib.utils.FpsUtils;
import com.goertek.commonlib.view.element.BackgroundElement;
import com.goertek.commonlib.view.element.BaseElement;
import com.goertek.commonlib.view.element.DialElement;
import com.goertek.commonlib.view.element.TimeElement;
import com.goertek.commonlib.view.element.WidgetElement;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public abstract class BaseCanvasWatchFaceService extends GoerCanvasWatchFaceService {
    private static final int MSG_UPDATE_TIME = 0;

    private List<BaseElement> mDrawElements = new ArrayList<>(0);

    public abstract AssetPackage getAssetPackage();

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private static class EngineHandler extends Handler {

        private final WeakReference<BaseCanvasWatchFaceService.Engine> mWeakReference;

        public EngineHandler(BaseCanvasWatchFaceService.Engine reference) {
            mWeakReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseCanvasWatchFaceService.Engine engine = mWeakReference.get();
            if (engine != null) {
                switch (msg.what) {
                    case MSG_UPDATE_TIME:
                        engine.handleUpdateTimeMessage();
                        break;
                }
            }
        }
    }

    public class Engine extends GoerCanvasWatchFaceService.Engine {
        private boolean mRegisteredTimeZoneReceiver = false;

        private Calendar mCalendar;

        private boolean mAmbient;
        private final Handler mUpdateTimeHandler = new EngineHandler(this);

        private final BroadcastReceiver mTimeZoneReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mCalendar.setTimeZone(TimeZone.getDefault());
                invalidate();
            }
        };
        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);

            AssetPackage assetPackage = getAssetPackage();

            BackgroundElement bElement = new BackgroundElement(assetPackage);
            TimeElement tElement = new TimeElement(assetPackage);
            DialElement dElement = new DialElement(assetPackage);
            WidgetElement wElement = new WidgetElement(assetPackage);

            mDrawElements.add(bElement);
            mDrawElements.add(tElement);
            mDrawElements.add(dElement);
            mDrawElements.add(wElement);
        }

        @Override
        public void onTapCommand(int tapType, int x, int y, long eventTime) {
            super.onTapCommand(tapType, x, y, eventTime);
            for (BaseElement drawElement : mDrawElements) {
                drawElement.onClick(x, y);
            }
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
            for (BaseElement drawElement : mDrawElements) {
                drawElement.preDraw(canvas, isInAmbientMode());
                drawElement.draw(canvas);
            }
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);

            mAmbient = inAmbientMode;

            updateTimer();

            for (BaseElement drawElement : mDrawElements) {
                drawElement.onAmbientModeChanged(inAmbientMode);
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

            if (visible) {
                registerReceiver();
                mCalendar.setTimeZone(TimeZone.getDefault());
                invalidate();
            } else {
                unregisterReceiver();
            }
            updateTimer();
        }

        private void registerReceiver() {
            if (mRegisteredTimeZoneReceiver) {
                return;
            }
            mRegisteredTimeZoneReceiver = true;
            IntentFilter filter = new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED);
            BaseCanvasWatchFaceService.this.registerReceiver(mTimeZoneReceiver, filter);
        }

        private void unregisterReceiver() {
            if (!mRegisteredTimeZoneReceiver) {
                return;
            }
            mRegisteredTimeZoneReceiver = false;
            BaseCanvasWatchFaceService.this.unregisterReceiver(mTimeZoneReceiver);
        }

        private void updateTimer() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            if (shouldTimerBeRunning()) {
                mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
            }
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

        @Override
        public void onDestroy() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            super.onDestroy();
        }

        public void handleUpdateTimeMessage() {
            invalidate();
            if (shouldTimerBeRunning()) {
                long timeMs = System.currentTimeMillis();
                long updateTime = FpsUtils.getFpsTimeUpdate();
                long delayMs = updateTime - (timeMs % updateTime);
                mUpdateTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs);
            }
        }

        private boolean shouldTimerBeRunning() {
            return isVisible() && !mAmbient;
        }
    }
}
