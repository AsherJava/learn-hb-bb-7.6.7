/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  org.dom4j.Attribute
 *  org.dom4j.Document
 *  org.dom4j.DocumentException
 *  org.dom4j.DocumentHelper
 *  org.dom4j.Element
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.singlequeryimport.utils;

import com.jiuqi.np.definition.common.StringUtils;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

public class XmlToJsonUtil {
    private XmlToJsonUtil() {
    }

    public static JSONObject xml2Json(String xmlStr) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText((String)xmlStr);
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
        JSONObject json = new JSONObject();
        if (null != doc) {
            XmlToJsonUtil.dom4j2Json(doc.getRootElement(), json);
        }
        return json;
    }

    private static void dom4j2Json(Element element, JSONObject json) {
        for (Object o : element.attributes()) {
            Attribute attr = (Attribute)o;
            if (StringUtils.isEmpty((String)attr.getValue())) continue;
            json.put("@" + attr.getName(), (Object)attr.getValue());
        }
        List chdEl = element.elements();
        if (chdEl.isEmpty() && !StringUtils.isEmpty((String)element.getText())) {
            json.put(element.getName(), (Object)element.getText());
        }
        for (Element e : chdEl) {
            if (!e.elements().isEmpty()) {
                JSONObject chdjson = new JSONObject();
                XmlToJsonUtil.dom4j2Json(e, chdjson);
                String name = e.getName();
                boolean has = json.has(e.getName());
                if (has) {
                    Object o = json.get(e.getName());
                    JSONArray jsona = null;
                    if (o instanceof JSONObject) {
                        JSONObject jsono = (JSONObject)o;
                        json.remove(e.getName());
                        jsona = new JSONArray();
                        jsona.put((Object)jsono);
                        jsona.put((Object)chdjson);
                    }
                    if (o instanceof JSONArray) {
                        jsona = (JSONArray)o;
                        jsona.put((Object)chdjson);
                    }
                    json.put(e.getName(), (Object)jsona);
                    continue;
                }
                if ("QueryMergeCell".equals(name) || "QuerySelectItem".equals(name)) {
                    JSONArray jsona = new JSONArray();
                    if (chdjson.isEmpty()) continue;
                    jsona.put((Object)chdjson);
                    json.put(e.getName(), (Object)jsona);
                    continue;
                }
                if (chdjson.isEmpty()) continue;
                json.put(e.getName(), (Object)chdjson);
                continue;
            }
            for (Object o : element.attributes()) {
                Attribute attr = (Attribute)o;
                if (StringUtils.isEmpty((String)attr.getValue())) continue;
                json.put("@" + attr.getName(), (Object)attr.getValue());
            }
            if (e.getText().isEmpty()) continue;
            json.put(e.getName(), (Object)e.getText());
        }
    }

    public static JSONArray addObjection(JSONArray array, JSONArray addArray, Integer index) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < array.length(); ++i) {
            JSONArray jsonArray = array.getJSONArray(i);
            if (i == index) {
                result.put((Object)addArray);
            }
            result.put((Object)jsonArray);
        }
        return result;
    }

    public static JSONArray addObjection(JSONArray array, JSONObject addArray, Integer index) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < array.length(); ++i) {
            JSONObject jsonObject = array.getJSONObject(i);
            if (i == index) {
                result.put((Object)addArray);
            }
            result.put((Object)jsonObject);
        }
        return result;
    }
}

