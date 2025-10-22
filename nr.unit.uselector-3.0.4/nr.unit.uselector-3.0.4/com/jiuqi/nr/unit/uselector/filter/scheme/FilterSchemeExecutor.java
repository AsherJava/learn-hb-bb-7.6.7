/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.itreebase.collection.FilterStringList
 *  com.jiuqi.nr.itreebase.collection.IFilterStringList
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  com.jiuqi.nr.unit.treecommon.utils.JSONValidator
 *  com.jiuqi.nr.unit.treecommon.utils.JSONValidator$Type
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  com.jiuqi.nr.unit.treestore.uselector.bean.USFilterScheme
 *  com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplate
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.uselector.filter.scheme;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.itreebase.collection.FilterStringList;
import com.jiuqi.nr.itreebase.collection.IFilterStringList;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import com.jiuqi.nr.unit.treecommon.utils.JSONValidator;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterScheme;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplate;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValuesImpl;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckerHelper;
import com.jiuqi.nr.unit.uselector.filter.scheme.AbstractConditionChain;
import com.jiuqi.nr.unit.uselector.filter.scheme.CheckerCondition;
import com.jiuqi.nr.unit.uselector.filter.scheme.FilterSchemeHighLevelExecutor;
import com.jiuqi.nr.unit.uselector.filter.scheme.LogicAndCondition;
import com.jiuqi.nr.unit.uselector.filter.scheme.LogicOrCondition;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class FilterSchemeExecutor {
    private USFilterScheme filterScheme;
    private IRowCheckerHelper checkerHelper;
    private IUSelectorEntityRowProvider entityRowsProvider;

    public FilterSchemeExecutor(USFilterScheme filterScheme, IUSelectorEntityRowProvider entityRowsProvider) {
        this.filterScheme = filterScheme;
        this.entityRowsProvider = entityRowsProvider;
        this.checkerHelper = (IRowCheckerHelper)BeanUtil.getBean(IRowCheckerHelper.class);
    }

    public List<String> execute(IUnitTreeContext context) {
        USFilterTemplate template = this.filterScheme.getTemplate();
        List<AbstractConditionChain> chain = this.getFilterCheckerChain(context, template);
        if (!chain.isEmpty()) {
            int i = 0;
            while (i + 1 < chain.size()) {
                AbstractConditionChain ac = chain.get(i);
                ac.setNextCondition(chain.get(i + 1));
                ++i;
            }
            FilterStringList operateSet = new FilterStringList();
            chain.get(0).handle(null, (IFilterStringList)operateSet);
            if (this.isHighestLevelSelect(template)) {
                return new FilterSchemeHighLevelExecutor(context, this.entityRowsProvider).getHighestLevelSet(operateSet.toList());
            }
            return operateSet.toList();
        }
        return new ArrayList<String>();
    }

    private boolean isHighestLevelSelect(USFilterTemplate template) {
        JSONObject json = template.getTemplate();
        if (json.has("isHighestSelect")) {
            return json.getBoolean("isHighestSelect");
        }
        return false;
    }

    private List<AbstractConditionChain> getFilterCheckerChain(IUnitTreeContext context, USFilterTemplate template) {
        ArrayList<AbstractConditionChain> condiFilters = new ArrayList<AbstractConditionChain>();
        JSONObject jsonObj = template.getTemplate();
        if (null != jsonObj) {
            JSONArray jtemplate = jsonObj.getJSONArray("template");
            for (int i = 0; i < jtemplate.length(); ++i) {
                JSONObject condi = jtemplate.getJSONObject(i);
                String keyword = condi.getString("keyword");
                if ("#logic_condition".equals(keyword)) {
                    condiFilters.add(this.getLogicFilter(context, condi));
                    continue;
                }
                condiFilters.add(this.getConditionFilter(context, condi));
            }
        }
        return condiFilters;
    }

    private AbstractConditionChain getConditionFilter(IUnitTreeContext context, JSONObject condi) {
        CheckerCondition impl = new CheckerCondition(context, this.entityRowsProvider);
        String keyword = condi.getString("keyword");
        IRowChecker checker = this.checkerHelper.getChecker(keyword);
        if (null != checker) {
            IFilterCheckValues values = this.translateValues(context, checker, condi);
            impl.setRowChecker(checker);
            impl.setCheckValues(values);
        }
        return impl;
    }

    private AbstractConditionChain getLogicFilter(IUnitTreeContext context, JSONObject condi) {
        String logicKey = condi.getString("values");
        if ("logic_and_condition".equals(logicKey)) {
            return new LogicAndCondition(context, this.entityRowsProvider);
        }
        if ("logic_or_condition".equals(logicKey)) {
            return new LogicOrCondition(context, this.entityRowsProvider);
        }
        throw new UnitTreeRuntimeException("\u4e0d\u5408\u6cd5\u7684\u7b5b\u9009\u903b\u8f91\uff01");
    }

    private IFilterCheckValues translateValues(IUnitTreeContext context, IRowChecker checker, JSONObject condi) {
        IFilterCheckValuesImpl impl;
        block6: {
            Object object;
            ArrayList<Map<String, String>> values;
            block7: {
                impl = new IFilterCheckValuesImpl();
                values = new ArrayList<Map<String, String>>();
                impl.setRuntimePara(this.getRunTimePara(context, checker, condi));
                impl.setValues(values);
                object = condi.get("values");
                if (null == object) break block6;
                JSONValidator validator = JSONValidator.from((String)object.toString());
                JSONValidator.Type type = validator.getType();
                if (null == type) break block7;
                switch (type) {
                    case ARRAY: {
                        JSONArray jArray = JavaBeanUtils.toJSONArray((String)object.toString());
                        for (int i = 0; i < jArray.length(); ++i) {
                            String v = jArray.getString(i);
                            HashMap<String, String> vMap = new HashMap<String, String>();
                            vMap.put("value", v);
                            values.add(vMap);
                        }
                        break block6;
                    }
                    case OBJECT: {
                        break;
                    }
                    case VALUE: {
                        HashMap<String, String> vMap = new HashMap<String, String>();
                        vMap.put("value", object.toString());
                        values.add(vMap);
                        break;
                    }
                }
                break block6;
            }
            HashMap<String, String> vMap = new HashMap<String, String>();
            vMap.put("value", object.toString());
            values.add(vMap);
        }
        return impl;
    }

    private Map<String, String> getRunTimePara(IUnitTreeContext context, IRowChecker checker, JSONObject condi) {
        JSONObject jsonObject;
        if (condi.has("runtimePara") && !(jsonObject = condi.getJSONObject("runtimePara")).isEmpty()) {
            HashMap<String, String> runtimePara = new HashMap<String, String>();
            Map map = jsonObject.toMap();
            for (Map.Entry entry : map.entrySet()) {
                Object value = entry.getValue();
                runtimePara.put((String)entry.getKey(), value != null ? value.toString() : null);
            }
            return runtimePara;
        }
        return checker.getExecutor(context).getValues().getRuntimePara();
    }
}

