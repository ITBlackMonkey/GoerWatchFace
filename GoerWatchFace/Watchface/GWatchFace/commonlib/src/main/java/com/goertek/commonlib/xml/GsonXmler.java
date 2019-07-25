package com.goertek.commonlib.xml;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Primitives;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * 上层解析类
 * <p>
 *  @author: carey.yu
 *  @version: 1.0.0
 *  @since: 2019/7/12
 */
public class GsonXmler {

    private Gson mCore;
    private XmlReader.Options mOptions;

    public GsonXmler(Gson gson, XmlReader.Options options) {
        this.mCore = gson;
        this.mOptions = options;
    }

    private static void assertFullConsumption(Object obj, JsonReader jsonReader) {
        if (obj != null) {
            try {
                if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                    throw new JsonIOException((String) "JSON document was not fully consumed.");
                }
            } catch (MalformedJsonException e) {
                throw new JsonSyntaxException((Throwable) e);
            } catch (IOException e2) {
                throw new JsonIOException((Throwable) e2);
            }
        }
    }

    public <T> T fromXml(String str, Class<T> cls) throws JsonSyntaxException {
        return Primitives.wrap(cls).cast(fromXml(str, (Type) cls instanceof Type ? cls : null));
    }

    public <T> T fromXml(String str, Type type) throws JsonSyntaxException {
        if (str == null) {
            return null;
        }
        return fromXml((Reader) new StringReader(str), type);
    }

    public <T> T fromXml(Reader reader, Class<T> cls) throws JsonSyntaxException, JsonIOException {
        XmlReader xmlReader = new XmlReader(reader, this.mOptions);
        T fromXml = fromXml(xmlReader, (Type) cls);
        assertFullConsumption(fromXml, xmlReader);
        return Primitives.wrap(cls).cast(fromXml);
    }

    public <T> T fromXml(Reader reader, Type type) throws JsonIOException, JsonSyntaxException {
        XmlReader xmlReader = new XmlReader(reader, this.mOptions);
        T fromXml = fromXml(xmlReader, type);
        assertFullConsumption(fromXml, xmlReader);
        return fromXml;
    }

    private <T> T fromXml(XmlReader xmlReader, Type type) throws JsonIOException, JsonSyntaxException {
        return this.mCore.fromJson((JsonReader) xmlReader, type);
    }

    public void toXml(Writer writer, Object obj) throws JsonIOException, JsonSyntaxException {
        try {
            mCore.toJson(obj, (Type) obj.getClass(), (JsonWriter) new XmlWriter(writer, obj.getClass().getSimpleName()));
        } catch (JsonIOException e2) {
            throw new JsonIOException((Throwable) e2);
        }
    }

    public String toString() {
        return this.mCore.toString();
    }
}
