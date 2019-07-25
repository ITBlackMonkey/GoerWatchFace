package com.goertek.commonlib.xml;

import com.google.gson.stream.JsonWriter;

import java.io.Writer;

class XmlWriter extends JsonWriter {
    public XmlWriter(Writer writer, String name) {
        super(writer);
    }
}
