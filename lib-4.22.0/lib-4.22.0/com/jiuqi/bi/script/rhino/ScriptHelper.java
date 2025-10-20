/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.mozilla.javascript.Context
 *  org.mozilla.javascript.IdFunctionObject
 *  org.mozilla.javascript.IdScriptableObject
 *  org.mozilla.javascript.NativeArray
 *  org.mozilla.javascript.NativeJavaObject
 *  org.mozilla.javascript.Scriptable
 *  org.mozilla.javascript.ScriptableObject
 */
package com.jiuqi.bi.script.rhino;

import com.jiuqi.bi.script.IScriptObject;
import com.jiuqi.bi.script.rhino.JSONScriptObject;
import com.jiuqi.bi.script.rhino.RhinoScriptObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class ScriptHelper
extends ScriptableObject {
    private static final long serialVersionUID = 3090419610223575618L;
    private Scriptable scope;

    ScriptHelper(Scriptable scope) {
        this.scope = scope;
        super.setParentScope(scope);
    }

    public Object toJava(Object obj) {
        return ScriptHelper._toJava(obj, this.scope);
    }

    public Object toScript(Object obj) {
        return ScriptHelper._toScript(obj, this.scope);
    }

    public Object fromJsonString(String json) throws Exception {
        if (json == null) {
            return null;
        }
        if ((json = json.trim()).startsWith("[")) {
            JSONArray array = new JSONArray(json);
            return JSONScriptObject.parseArray(array);
        }
        JSONObject jsonObj = new JSONObject(json);
        return new JSONScriptObject(jsonObj);
    }

    public Object fromJsonFile(String filePath, String charset) throws Exception {
        File file = new File(filePath);
        StringBuffer sb = new StringBuffer();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader((InputStream)fis, charset);
             BufferedReader br = new BufferedReader(isr);){
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        }
        return this.fromJsonString(sb.toString());
    }

    public void print(Object obj) {
        System.out.println(obj);
    }

    public String getClassName() {
        return "ScriptUtil";
    }

    static Object _toJava(Object obj, Scriptable scope) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RhinoScriptObject) {
            return ((RhinoScriptObject)((Object)obj)).getScriptObject();
        }
        if (obj instanceof NativeJavaObject) {
            Object o = ((NativeJavaObject)obj).unwrap();
            if (o instanceof RhinoScriptObject) {
                return ((RhinoScriptObject)((Object)o)).getScriptObject();
            }
            return o;
        }
        if (!(obj instanceof IdScriptableObject)) {
            return obj;
        }
        IdScriptableObject result = (IdScriptableObject)obj;
        if (result.getClassName().equals("Date")) {
            IdFunctionObject v = (IdFunctionObject)ScriptableObject.getProperty((Scriptable)result, (String)"getTime");
            Object m = result.execIdCall(v, Context.getCurrentContext(), scope, (Scriptable)result, null);
            long millis = ((Double)m).longValue();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(millis);
            return c;
        }
        if (obj instanceof NativeArray) {
            NativeArray jsArray = (NativeArray)obj;
            Object[] ids = jsArray.getAllIds();
            HashMap<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < ids.length - 1; ++i) {
                String key = ids[i].toString();
                if (key == null || !(key instanceof String)) continue;
                Object value = NativeArray.getProperty((Scriptable)jsArray, (String)key);
                map.put(key, value);
            }
            return map;
        }
        return result;
    }

    static Object _toScript(Object obj, Scriptable scope) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof IScriptObject) {
            return new RhinoScriptObject((IScriptObject)obj);
        }
        if (obj instanceof IdScriptableObject) {
            return obj;
        }
        if (obj instanceof NativeJavaObject) {
            Object result = ((NativeJavaObject)obj).unwrap();
            if (result instanceof IScriptObject) {
                return new RhinoScriptObject((IScriptObject)result);
            }
            if (result instanceof Calendar) {
                Calendar c = (Calendar)result;
                return Context.getCurrentContext().newObject(scope, "Date", new Object[]{new Long(c.getTimeInMillis())});
            }
            if (result instanceof Date) {
                Date d = (Date)result;
                return Context.getCurrentContext().newObject(scope, "Date", new Object[]{new Long(d.getTime())});
            }
        }
        return obj;
    }
}

