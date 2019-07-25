package com.goertek.watchface;

import android.content.Context;
import android.util.Xml;
import com.goertek.commonlib.provider.model.Container;
import com.goertek.commonlib.provider.model.Element;
import com.goertek.commonlib.provider.model.Layer;
import com.goertek.commonlib.provider.model.Option;
import com.goertek.commonlib.provider.model.Provider;
import com.goertek.commonlib.provider.model.Providers;
import com.goertek.commonlib.utils.LogUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * xml文件解析
 */

public class FileParseEngine {

    /**
     * pull解析xml文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static List<User> xmlParseFile(Context context, String fileName) {
        List<User> users = null;
        User user = null;// 记录当前的user
        String currentTag;// 记录当前解析到的节点名称
        try {
            //加载文件流
            InputStream is = context.getAssets().open(fileName);
            XmlPullParser parse = Xml.newPullParser();
            parse.setInput(is, "UTF-8");
            int eventType = parse.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        users = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        String name = parse.getName();
                        if ("user".equals(name)) {
                            user = new User();
                            user.setId(Long.parseLong(parse.getAttributeValue(0)));
                        }
                        if (user != null) {
                            if ("name".equals(name)) {
                                //得到当前指向元素的值并赋值给name
                                user.setName(parse.nextText());
                            }
                            if ("password".equals(name)) {
                                //得到当前指向元素的值并赋值给age
                                user.setPassword(parse.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("user".equals(parse.getName())) {
                            users.add(user);
                            user = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = parse.next();
            }
            is.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析xml
     * 目前遇到一个问题:
     * option节点下的layer集合和layer节点下的layer集合区分不开，不知道layer结束的时候该给哪个集合加layer对象
     * @param context
     * @param fileName
     * @return
     */

    public static Providers parseWatchFileWithPull(Context context, String fileName) {
        try {
            Providers providers = null;
            Provider provider = null;
            List<Element> elements = null;
            Element element = null;
            List<Container> containers = null;
            Container container = null;
            List<Option> options = null;
            Option option = null;
            ArrayList<Layer> layers = null;//layer里面的集合
            ArrayList<Layer> optionLayers = null;//option里面的layer集合
            Layer layer = null;
            boolean isOption = false;
            boolean isInnerLayer = false;
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            InputStream is = context.getAssets().open(fileName);
            parser.setInput(is, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT://开始解析文档
                        providers = new Providers();
                        break;
                    case XmlPullParser.START_TAG://开始解析标记
                        LogUtil.e("davin", nodeName);
                        if ("provider".equals(nodeName)) {
                            provider = new Provider();
                            elements = new ArrayList<>();
                            String dpi = parser.getAttributeValue(null, "dpi");
                            provider.setDpi(dpi);
                        } else if ("element".equals(nodeName)) {
                            element = new Element();
                            containers = new ArrayList<>();
                            options = new ArrayList<>();
                            String container_count = parser.getAttributeValue("", "container_count");
                            String is_support_option = parser.getAttributeValue("", "is_support_option");
                            String label = parser.getAttributeValue("", "label");
                            String option_count = parser.getAttributeValue("", "option_count");
                            LogUtil.e("davin", container_count + "," + is_support_option + "," + label + "," + option_count);
                           /* element.setContainer_count(container_count);
                            element.setIsSupportOption(is_support_option);
                            element.setLabel(label);
                            element.setOption_count(option_count);*/
                        } else if ("container".equals(nodeName)) {
                            container = new Container();
                            container.setAvailableOption(parser.getAttributeValue("", "available_option"));
                            container.setIndex(parser.getAttributeValue("", "index"));
                            container.setIsSupportOption(parser.getAttributeValue("", "is_support_option"));
                            container.setRect(parser.getAttributeValue("", "rect"));
                            container.setSelectedOption(parser.getAttributeValue("", "selected_option"));
                        } else if ("option".equals(nodeName)) {
                            option = new Option();
                            option.setIndex(parser.getAttributeValue("", "available_option"));
                            option.setDataType(parser.getAttributeValue("", "data_type"));
                            option.setResPreview(parser.getAttributeValue("", "res_preview"));
                            option.setLayer_count(parser.getAttributeValue("", "layer_count"));
                            layers = new ArrayList<>();
                        } else if ("layer".equals(nodeName)) {
                            LogUtil.e("davin", "layer start");
                            layer = new Layer();
                            layer.setIndex(parser.getAttributeValue("", "index"));
                            layer.setDrawType(parser.getAttributeValue("", "draw_type"));
                            layer.setResActive(parser.getAttributeValue("", "res_active"));
                            layer.setResAlign(parser.getAttributeValue("", "res_align"));
                            layer.setResPositionRelative(parser.getAttributeValue("", "res_position_relative"));
                            layer.setValueType(parser.getAttributeValue("", "value_type"));
                        }
                        break;
                    case XmlPullParser.END_TAG://结束标记
                        /*if ("provider".equals(nodeName)) {
                            providers.setProviders(provider);
                        } else if ("element".equals(nodeName)) {
                            elements.add(element);
                            provider.setElements(elements);
                        } else if ("container".equals(nodeName)) {
                            containers.add(container);
                            element.setContainers(containers);
                        } else if ("option".equals(nodeName)) {
                            option.setLayers(layers);
                            options.add(option);
                            element.setOptions(options);
                        } else if ("layer".equals(nodeName)) {
                            LogUtil.e("davin", "layer end");
                            layers.add(layer);
                        }
                        break;*/
                }
                eventType = parser.next();
            }
            return providers;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
