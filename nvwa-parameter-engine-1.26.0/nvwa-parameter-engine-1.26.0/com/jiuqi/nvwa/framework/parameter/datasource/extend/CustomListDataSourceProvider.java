/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource.extend;

import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomListDataSourceProvider
implements IParameterDataSourceProvider {
    @Override
    public ParameterResultset getDefaultValue(ParameterDataSourceContext context) throws ParameterException {
        ParameterModel parameterModel = context.getModel();
        CustomListDataSourceModel model = (CustomListDataSourceModel)parameterModel.getDatasource();
        List<CustomListDataSourceModel.FixedMemberItem> valueItems = model.getItems();
        if (valueItems == null || valueItems.isEmpty()) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        IParameterValueFormat format = ParameterUtils.createValueFormat(model);
        AbstractParameterValueConfig cfg = parameterModel.getValueConfig();
        String mode = cfg.getDefaultValueMode();
        ArrayList<ParameterResultItem> list = new ArrayList<ParameterResultItem>();
        if (mode.equals("appoint")) {
            valueItems = this.getCandidateValues(valueItems, cfg);
            FixedMemberParameterValue defaultVal = (FixedMemberParameterValue)cfg.getDefaultValue();
            List<Object> items = defaultVal.toValueList(format);
            block0: for (Object item : items) {
                for (CustomListDataSourceModel.FixedMemberItem v : valueItems) {
                    if (!v.getCode().equals(format.format(item))) continue;
                    list.add(new ParameterResultItem(item, v.getTitle()));
                    continue block0;
                }
            }
        } else if (mode.equals("first")) {
            valueItems = this.getCandidateValues(valueItems, cfg);
            Object value = format.parse(valueItems.get(0).getCode());
            list.add(new ParameterResultItem(value, valueItems.get(0).getTitle()));
        } else {
            if (mode.equals("none")) {
                return new ParameterResultset();
            }
            if (mode.equals("expr")) {
                throw new ParameterException("\u81ea\u5b9a\u4e49\u5217\u8868\u6765\u6e90\u53c2\u6570\u4e0d\u652f\u6301\u9ed8\u8ba4\u503c\u4e3a\u8868\u8fbe\u5f0f");
            }
        }
        return new ParameterResultset(list);
    }

    @Override
    public ParameterResultset getCandidateValue(ParameterDataSourceContext context, ParameterHierarchyFilterItem hierarchyFilter) throws ParameterException {
        CustomListDataSourceModel model = (CustomListDataSourceModel)context.getModel().getDatasource();
        List<CustomListDataSourceModel.FixedMemberItem> valueItems = model.getItems();
        if (valueItems == null || valueItems.isEmpty()) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        IParameterValueFormat format = ParameterUtils.createValueFormat(model);
        AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
        ParameterCandidateValueMode mode = cfg.getCandidateMode();
        ArrayList<ParameterResultItem> list = new ArrayList<ParameterResultItem>();
        if (mode == ParameterCandidateValueMode.ALL) {
            for (CustomListDataSourceModel.FixedMemberItem v : valueItems) {
                Object data = format.parse(v.getCode());
                list.add(new ParameterResultItem(data, v.getTitle()));
            }
        } else if (mode == ParameterCandidateValueMode.APPOINT) {
            valueItems = this.getCandidateValues(valueItems, cfg);
            for (CustomListDataSourceModel.FixedMemberItem v : valueItems) {
                Object data = format.parse(v.getCode());
                list.add(new ParameterResultItem(data, v.getTitle()));
            }
        } else if (mode == ParameterCandidateValueMode.EXPRESSION) {
            throw new ParameterException("\u81ea\u5b9a\u4e49\u5217\u8868\u6765\u6e90\u53c2\u6570\u4e0d\u652f\u6301\u53ef\u9009\u503c\u4e3a\u8868\u8fbe\u5f0f");
        }
        return new ParameterResultset(list);
    }

    @Override
    public ParameterResultset compute(ParameterDataSourceContext context, AbstractParameterValue value) throws ParameterException {
        CustomListDataSourceModel model = (CustomListDataSourceModel)context.getModel().getDatasource();
        List<CustomListDataSourceModel.FixedMemberItem> valueItems = model.getItems();
        if (valueItems == null || valueItems.isEmpty()) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        if (value.isFormulaValue()) {
            throw new ParameterException("\u81ea\u5b9a\u4e49\u5217\u8868\u6765\u6e90\u53c2\u6570\u4e0d\u652f\u6301\u516c\u5f0f\u8fc7\u6ee4");
        }
        IParameterValueFormat format = ParameterUtils.createValueFormat(model);
        List<String> valuelist = value.getKeysAsString(format);
        ArrayList<ParameterResultItem> list = new ArrayList<ParameterResultItem>();
        for (CustomListDataSourceModel.FixedMemberItem item : valueItems) {
            if (!valuelist.contains(item.getCode())) continue;
            Object data = format.parse(item.getCode());
            list.add(new ParameterResultItem(data, item.getTitle()));
        }
        return new ParameterResultset(list);
    }

    @Override
    public ParameterResultset search(ParameterDataSourceContext context, List<String> searchValues) throws ParameterException {
        CustomListDataSourceModel model = (CustomListDataSourceModel)context.getModel().getDatasource();
        List<CustomListDataSourceModel.FixedMemberItem> valueItems = model.getItems();
        if (valueItems == null || valueItems.isEmpty()) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        IParameterValueFormat format = ParameterUtils.createValueFormat(model);
        ArrayList<ParameterResultItem> list = new ArrayList<ParameterResultItem>();
        for (CustomListDataSourceModel.FixedMemberItem item : valueItems) {
            boolean matched = false;
            for (String sv : searchValues) {
                if (item.getCode().toUpperCase().contains(sv.toUpperCase())) {
                    matched = true;
                }
                if (item.getTitle().toUpperCase().contains(sv.toUpperCase())) {
                    matched = true;
                }
                if (!matched) continue;
                break;
            }
            if (!matched) continue;
            Object data = format.parse(item.getCode());
            list.add(new ParameterResultItem(data, item.getTitle()));
        }
        return new ParameterResultset(list);
    }

    @Override
    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(AbstractParameterDataSourceModel datasourceModel) throws ParameterException {
        ArrayList<DataSourceCandidateFieldInfo> list = new ArrayList<DataSourceCandidateFieldInfo>();
        list.add(new DataSourceCandidateFieldInfo("code", "\u7f16\u7801"));
        list.add(new DataSourceCandidateFieldInfo("title", "\u6807\u9898"));
        return list;
    }

    private List<CustomListDataSourceModel.FixedMemberItem> getCandidateValues(List<CustomListDataSourceModel.FixedMemberItem> valueItems, AbstractParameterValueConfig cfg) throws ParameterException {
        if (cfg.getCandidateMode() == ParameterCandidateValueMode.APPOINT) {
            List<String> values = cfg.getCandidateValue();
            ArrayList<CustomListDataSourceModel.FixedMemberItem> candidateValues = new ArrayList<CustomListDataSourceModel.FixedMemberItem>();
            HashMap<String, CustomListDataSourceModel.FixedMemberItem> map = new HashMap<String, CustomListDataSourceModel.FixedMemberItem>();
            for (CustomListDataSourceModel.FixedMemberItem v : valueItems) {
                map.put(v.getCode(), v);
            }
            for (String value : values) {
                if (!map.containsKey(value)) continue;
                candidateValues.add((CustomListDataSourceModel.FixedMemberItem)map.get(value));
            }
            return candidateValues;
        }
        if (cfg.getCandidateMode() == ParameterCandidateValueMode.EXPRESSION) {
            throw new ParameterException("\u81ea\u5b9a\u4e49\u5217\u8868\u6765\u6e90\u53c2\u6570\u4e0d\u652f\u6301\u53ef\u9009\u503c\u4e3a\u8868\u8fbe\u5f0f");
        }
        return valueItems;
    }
}

