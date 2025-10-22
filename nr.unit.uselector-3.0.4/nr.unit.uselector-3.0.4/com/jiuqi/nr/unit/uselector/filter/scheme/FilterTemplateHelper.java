/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplate
 *  com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplateImpl
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.uselector.filter.scheme;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplate;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplateImpl;
import com.jiuqi.nr.unit.uselector.checker.DisplayType;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValuesImpl;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckerHelper;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FilterTemplateHelper {
    public static final String LOGIC_KEYWORD = "#logic_condition";

    private FilterTemplateHelper() {
    }

    public static USFilterTemplate toFilterTemplate(JSONObject json) {
        if (null != json && json.has("template")) {
            JSONArray jsonArray = json.getJSONArray("template");
            if (null != jsonArray) {
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject runtimePara;
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    jsonObject.remove("type");
                    if (!jsonObject.has("checkValues")) continue;
                    JSONObject checkValues = jsonObject.getJSONObject("checkValues");
                    checkValues.put("values", jsonObject.get("values"));
                    if (!checkValues.has("runtimePara") || (runtimePara = checkValues.getJSONObject("runtimePara")) == null) continue;
                    jsonObject.put("runtimePara", runtimePara.toMap());
                }
            }
            return new USFilterTemplateImpl(json.toString());
        }
        return null;
    }

    public static JSONObject toJSONObject(IRowCheckerHelper checkerHelper, IUnitTreeContext context, USFilterTemplate template) {
        JSONObject jsonTemp;
        if (null != template && null != (jsonTemp = template.getTemplate())) {
            JSONArray jsonArray = jsonTemp.getJSONArray("template");
            if (null != jsonArray) {
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (!jsonObject.has("keyword")) continue;
                    String keyword = jsonObject.getString("keyword");
                    if (LOGIC_KEYWORD.equals(keyword)) {
                        jsonObject.put("type", (Object)DisplayType.NONE.toString());
                        jsonObject.put("checkValues", (Object)FilterTemplateHelper.getLogicFilterValues());
                        continue;
                    }
                    IRowChecker checker = checkerHelper.getChecker(keyword);
                    if (null == checker) {
                        jsonArray.remove(i);
                        continue;
                    }
                    jsonObject.put("type", (Object)checker.getDisplayType().toString());
                    jsonObject.put("checkValues", (Object)FilterTemplateHelper.getCheckerFilterValue(checker, context, jsonObject));
                }
            }
            return jsonTemp;
        }
        return null;
    }

    private static IFilterCheckValues getCheckerFilterValue(IRowChecker checker, IUnitTreeContext context, JSONObject jsonObject) {
        if (null != checker && checker.isDisplay(context)) {
            JSONObject runtimePara;
            IFilterCheckValues values = checker.getExecutor(context).getValues();
            if (jsonObject.has("runtimePara") && (runtimePara = jsonObject.getJSONObject("runtimePara")) != null && !runtimePara.isEmpty()) {
                Map map = runtimePara.toMap();
                for (Map.Entry entry : map.entrySet()) {
                    if (entry.getValue() == null) continue;
                    values.getRuntimePara().put((String)entry.getKey(), entry.getValue().toString());
                }
            }
            return values;
        }
        return new IFilterCheckValuesImpl();
    }

    private static IFilterCheckValues getLogicFilterValues() {
        IFilterCheckValuesImpl values = new IFilterCheckValuesImpl();
        HashMap<String, String> logicOr = new HashMap<String, String>();
        logicOr.put("text", "\u6216\u8005");
        logicOr.put("value", "logic_or_condition");
        HashMap<String, String> logicAnd = new HashMap<String, String>();
        logicAnd.put("text", "\u5e76\u4e14");
        logicAnd.put("value", "logic_and_condition");
        values.getValues().add(logicAnd);
        values.getValues().add(logicOr);
        return values;
    }
}

