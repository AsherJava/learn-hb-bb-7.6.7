/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.syntax.DataType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static JSONObject toTypedValue(Object value) throws JSONException {
        if (value == null) {
            return null;
        }
        int dataType = DataType.typeOf((Object)value);
        String str = DataType.formatValue((int)dataType, (Object)value);
        JSONObject obj = new JSONObject();
        obj.put("t", dataType);
        obj.put("v", (Object)str);
        return obj;
    }

    public static Object fromTypedValue(JSONObject obj) throws JSONException {
        if (obj == null) {
            return null;
        }
        int dataType = obj.getInt("t");
        switch (dataType) {
            case 6: {
                return obj.getString("v");
            }
            case 3: {
                return obj.getDouble("v");
            }
            case 1: {
                String str = obj.getString("v");
                return DataType.parseValue((int)1, (String)str);
            }
            case 10: {
                return new BigDecimal(obj.getString("v"));
            }
            case 2: {
                Date date;
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = fmt.parse(obj.getString("v"));
                }
                catch (ParseException e) {
                    throw new JSONException((Throwable)e);
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return cal;
            }
        }
        return obj.getString("v");
    }
}

