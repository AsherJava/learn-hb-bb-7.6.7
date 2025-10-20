/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.bi.parameter.model.ParameterModel
 *  com.jiuqi.bi.parameter.model.ParameterWidgetType
 *  com.jiuqi.bi.parameter.model.SmartSelector
 *  com.jiuqi.bi.parameter.model.SmartSelector$RangeItem
 *  com.jiuqi.bi.parameter.model.SmartSelector$RangeType
 *  com.jiuqi.bi.parameter.model.SmartSelector$SelectType
 *  com.jiuqi.bi.parameter.model.SmartSelector$SelectedValue
 *  com.jiuqi.bi.util.StringHelper
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.html.message;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.ParameterWidgetType;
import com.jiuqi.bi.parameter.model.SmartSelector;
import com.jiuqi.bi.util.StringHelper;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParamStringValuesParser {
    private final IParameterEnv env;

    public ParamStringValuesParser(IParameterEnv env) {
        this.env = env;
    }

    public void parse2Map(String parameterName, List<String> paramValues, Map<String, Object> paramValueMap) {
        ParameterModel parameterModel = null;
        if (this.env.containsParameterWithAlias(parameterName)) {
            parameterModel = this.env.getParameterWithAlias(parameterName);
        } else if (this.env.containsParameter(parameterName)) {
            parameterModel = this.env.getParameterModelByName(parameterName);
        }
        this.parse2Map(parameterModel, paramValues, paramValueMap);
    }

    private void parse2Map(ParameterModel parameterModel, List<String> paramValues, Map<String, Object> paramValueMap) {
        if (parameterModel == null || paramValues == null) {
            return;
        }
        if (parameterModel.getWidgetType() == ParameterWidgetType.SMARTSELECTOR) {
            paramValueMap.put(parameterModel.getName(), ParamStringValuesParser.parse2SmartSelector(paramValues));
        } else if (parameterModel.getDataType() == DataType.INTEGER) {
            ArrayList<Integer> pIntValues = new ArrayList<Integer>(paramValues.size());
            for (String paramValue : paramValues) {
                Integer pIntValue = StringUtils.isEmpty((String)paramValue) ? null : Integer.valueOf(Integer.parseInt(paramValue));
                pIntValues.add(pIntValue);
            }
            paramValueMap.put(parameterModel.getName(), pIntValues);
            if (parameterModel.isRangeParameter() && paramValues.size() > 0) {
                ArrayList minList = new ArrayList();
                minList.add(pIntValues.get(0));
                paramValueMap.put(parameterModel.getName() + ".MIN", minList);
                if (paramValues.size() > 1) {
                    ArrayList maxList = new ArrayList();
                    maxList.add(pIntValues.get(1));
                    paramValueMap.put(parameterModel.getName() + ".MAX", maxList);
                }
            }
        } else if (parameterModel.getDataType() == DataType.DOUBLE) {
            ArrayList<Double> pDoubleValues = new ArrayList<Double>(paramValues.size());
            for (String paramValue : paramValues) {
                Double pDoubleValue = StringUtils.isEmpty((String)paramValue) ? null : Double.valueOf(Double.parseDouble(paramValue));
                pDoubleValues.add(pDoubleValue);
            }
            paramValueMap.put(parameterModel.getName(), pDoubleValues);
            if (parameterModel.isRangeParameter() && paramValues.size() > 0) {
                ArrayList minList = new ArrayList();
                minList.add(pDoubleValues.get(0));
                paramValueMap.put(parameterModel.getName() + ".MIN", minList);
                if (paramValues.size() > 1) {
                    ArrayList maxList = new ArrayList();
                    maxList.add(pDoubleValues.get(1));
                    paramValueMap.put(parameterModel.getName() + ".MAX", maxList);
                }
            }
        } else if (parameterModel.getDataType() == DataType.BOOLEAN) {
            ArrayList<Boolean> pBooleanValues = new ArrayList<Boolean>(paramValues.size());
            for (String paramValue : paramValues) {
                pBooleanValues.add(StringHelper.isBoolean((String)paramValue));
            }
            paramValueMap.put(parameterModel.getName(), pBooleanValues);
        } else {
            paramValueMap.put(parameterModel.getName(), paramValues);
            if (parameterModel.isRangeParameter() && paramValues.size() > 0) {
                ArrayList<String> minList = new ArrayList<String>();
                minList.add(paramValues.get(0));
                paramValueMap.put(parameterModel.getName() + ".MIN", minList);
                ArrayList<String> maxList = new ArrayList<String>();
                maxList.add(paramValues.size() > 1 ? paramValues.get(1) : paramValues.get(0));
                paramValueMap.put(parameterModel.getName() + ".MAX", maxList);
            }
        }
    }

    private static SmartSelector parse2SmartSelector(List<String> newValues) {
        SmartSelector newSelector = new SmartSelector();
        if (newValues != null && newValues.size() > 0) {
            String singleValue = newValues.get(0);
            if (newValues.size() == 1) {
                ParamStringValuesParser.praseSingleSmartValue(newSelector, singleValue);
            } else {
                ParamStringValuesParser.parseMultiSmartValues(newValues, newSelector, singleValue);
            }
        }
        return newSelector;
    }

    private static void praseSingleSmartValue(SmartSelector newSelector, String singleValue) {
        if (singleValue.startsWith("{$") && singleValue.endsWith("$}")) {
            newSelector.setMatchTxt(singleValue.substring(2, singleValue.length() - 2));
            newSelector.setExact(false);
            newSelector.setType(SmartSelector.SelectType.FUZZY);
        } else if (singleValue.startsWith("{") && singleValue.endsWith("}")) {
            newSelector.setMatchTxt(singleValue.substring(1, singleValue.length() - 1));
            newSelector.setExact(true);
            newSelector.setType(SmartSelector.SelectType.FUZZY);
        } else if (singleValue.indexOf(45) != -1) {
            SmartSelector.RangeItem rangeItem = new SmartSelector.RangeItem();
            if (singleValue.startsWith("~")) {
                rangeItem.rtype = SmartSelector.RangeType.EXCLUDERANGE;
                String[] values = singleValue.split("[~|-]");
                if (values.length == 3) {
                    rangeItem.minValue = values[1];
                    rangeItem.maxValue = values[2];
                    newSelector.getRanges().add(rangeItem);
                }
            } else {
                rangeItem.rtype = SmartSelector.RangeType.INCLUDERANGE;
                String[] values = singleValue.split("-");
                if (values.length == 2) {
                    rangeItem.minValue = values[0];
                    rangeItem.maxValue = values[1];
                    newSelector.getRanges().add(rangeItem);
                }
            }
            newSelector.setType(SmartSelector.SelectType.RANGE);
        } else if (singleValue.startsWith("~")) {
            SmartSelector.RangeItem rangeItem = new SmartSelector.RangeItem();
            rangeItem.rtype = SmartSelector.RangeType.EXCLUDERANGE;
            rangeItem.value = singleValue.replaceFirst("~", "");
            newSelector.getRanges().add(rangeItem);
            newSelector.setType(SmartSelector.SelectType.RANGE);
        } else if (singleValue.endsWith("$")) {
            SmartSelector.RangeItem rangeItem = new SmartSelector.RangeItem();
            rangeItem.rtype = SmartSelector.RangeType.STARTWITH;
            rangeItem.value = singleValue.substring(0, singleValue.length() - 1);
            newSelector.getRanges().add(rangeItem);
            newSelector.setType(SmartSelector.SelectType.RANGE);
        } else {
            SmartSelector.SelectedValue sv = new SmartSelector.SelectedValue((Object)singleValue);
            newSelector.getSelectedValues().add(sv);
            newSelector.setType(SmartSelector.SelectType.FIXED);
        }
    }

    private static void parseMultiSmartValues(List<String> newValues, SmartSelector newSelector, String singleValue) {
        if (singleValue.indexOf(45) == -1 && singleValue.indexOf(126) == -1 && singleValue.indexOf(36) == -1) {
            for (String newValue : newValues) {
                SmartSelector.SelectedValue sv = new SmartSelector.SelectedValue((Object)newValue);
                newSelector.getSelectedValues().add(sv);
            }
            newSelector.setType(SmartSelector.SelectType.FIXED);
        } else {
            for (String newValue : newValues) {
                SmartSelector.RangeItem rangeItem;
                if (newValue.indexOf(45) != -1) {
                    String[] values;
                    rangeItem = new SmartSelector.RangeItem();
                    SmartSelector.RangeType rangeType = rangeItem.rtype = newValue.startsWith("~") ? SmartSelector.RangeType.EXCLUDERANGE : SmartSelector.RangeType.INCLUDERANGE;
                    if (rangeItem.rtype == SmartSelector.RangeType.INCLUDERANGE) {
                        values = newValue.split("-");
                        if (values.length != 2) continue;
                        rangeItem.minValue = values[0];
                        rangeItem.maxValue = values[1];
                        newSelector.getRanges().add(rangeItem);
                        continue;
                    }
                    values = newValue.split("[~|-]");
                    if (values.length != 3) continue;
                    rangeItem.minValue = values[1];
                    rangeItem.maxValue = values[2];
                    newSelector.getRanges().add(rangeItem);
                    continue;
                }
                if (newValue.startsWith("~")) {
                    rangeItem = new SmartSelector.RangeItem();
                    rangeItem.rtype = SmartSelector.RangeType.EXCLUDEVALUE;
                    rangeItem.value = newValue.replaceFirst("~", "");
                    newSelector.getRanges().add(rangeItem);
                    continue;
                }
                if (!newValue.endsWith("$")) continue;
                rangeItem = new SmartSelector.RangeItem();
                rangeItem.rtype = SmartSelector.RangeType.STARTWITH;
                rangeItem.value = newValue.substring(0, newValue.length() - 1);
                newSelector.getRanges().add(rangeItem);
            }
            newSelector.setType(SmartSelector.SelectType.RANGE);
        }
    }
}

