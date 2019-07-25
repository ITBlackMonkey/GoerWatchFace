package com.goertek.commonlib.provider.model;


import android.text.TextUtils;

import com.goertek.commonlib.utils.WatchFaceUtil;
import com.google.gson.annotations.SerializedName;

import java.nio.file.OpenOption;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Element {
        @SerializedName("option")
        private List<Option> options;

        @SerializedName("container")
        private List<Container> containers;

        @SerializedName("layer")
        private List<Layer> layers;

        @SerializedName("@label")
        private String label;

        @SerializedName("@is_support_option")
        private String isSupportOption;

        @SerializedName("@selected_option")
        private String selectedOption;

        @SerializedName("@res_preview")
        private String resPreview;

        public List<Option> getOptions() {
            return options;
        }

        public List<Container> getContainers() {
            return containers;
        }

        public List<Layer> getLayers() {
            return layers;
        }

        public String getLabel() {
            return label;
        }

        public String getIsSupportOption() {
            return isSupportOption;
        }

        public String getSelectedOption() {
            return selectedOption;
        }

        public void setSelectedOption(String index) {
            selectedOption = index;
        }

        public String getResPreview() {
            return resPreview;
        }

        public Option getOption(String optionIndexStr) {
            //TODO：测试数据

            return new Option();

            /*if (TextUtils.isEmpty(optionIndexStr)) {
                return null;
            }
            if ((options == null) || (options.size() <= 0)) {
                return null;
            }
            for (Option option : options) {
                if (option == null) {
                    continue;
                }
                if (TextUtils.equals(option.getIndex().trim(), optionIndexStr.trim())) {
                    return null;
                }
            }
            return null;*/
        }

        public Container getContainer(String index) {
            if ((containers == null) || (containers.size() <= 0)) {
                return null;
            }
            for (Container container : containers) {
                if (container == null) {
                    continue;
                }
                if (TextUtils.equals(container.getIndex().trim(), index.trim())) {
                    return null;
                }
            }
            return null;
        }

        public Layer getLayer(String index) {
            if ((layers == null) || (layers.size() <= 0)) {
                return null;
            }
            for (Layer layer : layers) {
                if (layer == null) {
                    continue;
                }
                if (TextUtils.equals(layer.getIndex().trim(), index.trim())) {
                    return layer;
                }
            }
            return null;
        }

        public String getPreview() {
            if (WatchFaceUtil.getBoolValue(getIsSupportOption())) {
                Option option = getOption(getSelectedOption());
                if (option == null) {
                    return "";
                }
                return option.getResPreview();
            } else {
                return getResPreview();
            }
        }

        public String getPreview(String index) {
            Container container = getContainer(index);
            if (container == null) {
                return "";
            }
            if (WatchFaceUtil.getBoolValue(container.getIsSupportOption())) {
                Option option = getOption(container.getSelectedOption());
                if (option == null) {
                    return "";
                }
                return option.getResPreview();
            } else {
                return container.getResPreview();
            }
        }

        public String getBorderPreview(String index) {
            Container container = getContainer(index);
            if (container == null) {
                return "";
            }
            if (WatchFaceUtil.getBoolValue(container.getIsSupportOption())) {
                Option option = getOption(container.getSelectedOption());
                if (option == null) {
                    return "";
                }
                return option.getResBorderPreview();
            } else {
                return "";
            }
        }

    @Override
    public String toString() {
        return "Element{" +
                       "options=" + options +
                       ", containers=" + containers +
                       ", layers=" + layers +
                       ", label='" + label + '\'' +
                       ", isSupportOption='" + isSupportOption + '\'' +
                       ", selectedOption='" + selectedOption + '\'' +
                       ", resPreview='" + resPreview + '\'' +
                       '}';
    }
}