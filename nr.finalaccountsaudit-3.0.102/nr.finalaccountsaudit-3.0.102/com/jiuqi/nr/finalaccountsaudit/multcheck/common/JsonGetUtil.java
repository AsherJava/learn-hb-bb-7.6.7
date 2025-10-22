/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.common;

import java.math.BigDecimal;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonGetUtil {
    public static boolean getBoolean(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getBoolean(key);
        }
        catch (JSONException e) {
            return false;
        }
    }

    public static String getString(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        }
        catch (JSONException e) {
            return null;
        }
    }

    public static BigDecimal getBigDecimal(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getBigDecimal(key);
        }
        catch (JSONException e) {
            return null;
        }
    }
}

