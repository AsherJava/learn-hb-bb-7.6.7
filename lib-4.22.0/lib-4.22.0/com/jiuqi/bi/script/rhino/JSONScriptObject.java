/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.mozilla.javascript.Scriptable
 *  org.mozilla.javascript.ScriptableObject
 */
package com.jiuqi.bi.script.rhino;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class JSONScriptObject
extends ScriptableObject {
    private static final String CLASSNAME = "JsonScriptObject";
    private JSONObject json;

    public JSONScriptObject(JSONObject json) {
        this.json = json;
    }

    public String getClassName() {
        return CLASSNAME;
    }

    public boolean has(String name, Scriptable start) {
        return this.json.has(name);
    }

    public boolean has(int index, Scriptable start) {
        Iterator keys = this.json.keys();
        int count = 0;
        while (keys.hasNext()) {
            if (index == count) {
                return true;
            }
            keys.next();
            ++count;
        }
        return false;
    }

    public Object get(String name, Scriptable start) {
        Object obj = this.json.opt(name);
        if (obj instanceof JSONObject) {
            return new JSONScriptObject((JSONObject)obj);
        }
        if (obj instanceof JSONArray) {
            JSONArray array = (JSONArray)obj;
            try {
                return JSONScriptObject.parseArray(array);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return obj;
    }

    public Object get(int index, Scriptable start) {
        Iterator keys = this.json.keys();
        int count = 0;
        String key = null;
        while (keys.hasNext()) {
            if (index == count) {
                key = (String)keys.next();
            }
            ++count;
        }
        if (key != null) {
            return this.get(key, start);
        }
        return null;
    }

    public void delete(String name) {
        if (this.json.has(name)) {
            this.json.remove(name);
        }
    }

    public void delete(int index) {
        Iterator keys = this.json.keys();
        int count = 0;
        String key = null;
        while (keys.hasNext()) {
            if (index == count) {
                key = (String)keys.next();
            }
            ++count;
        }
        if (key != null) {
            this.delete(key);
        }
    }

    public static Object[] parseArray(JSONArray array) throws Exception {
        ArrayList<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); ++i) {
            Object value = array.get(i);
            if (value instanceof JSONObject) {
                JSONScriptObject o = new JSONScriptObject((JSONObject)value);
                list.add((Object)o);
                continue;
            }
            if (value instanceof JSONArray) {
                Object[] objs = JSONScriptObject.parseArray(array);
                list.add(objs);
                continue;
            }
            list.add(value);
        }
        return list.toArray();
    }
}

