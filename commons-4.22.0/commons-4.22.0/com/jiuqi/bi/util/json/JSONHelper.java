/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.util.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {
    private JSONHelper() {
    }

    public static void writeJSONObject(OutputStream outStream, JSONObject json) throws IOException {
        String s = json.toString();
        JSONHelper.writeString(outStream, s);
    }

    public static JSONObject readJSONObject(InputStream inStream) throws IOException, JSONException {
        String s = JSONHelper.readString(inStream);
        if (s == null) {
            return null;
        }
        return new JSONObject(s);
    }

    public static void writeString(OutputStream outStream, String s) throws IOException {
        if (s == null) {
            return;
        }
        byte[] data = s.getBytes("UTF-8");
        outStream.write(data);
    }

    public static String readString(InputStream inStream) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = inStream.read(buffer);
        while (len != -1) {
            byteStream.write(buffer, 0, len);
            len = inStream.read(buffer);
        }
        if (byteStream.size() == 0) {
            return null;
        }
        return byteStream.toString("UTF-8");
    }
}

