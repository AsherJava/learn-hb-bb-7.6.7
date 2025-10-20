/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource.extend;

import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLQueryHelper;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.SmartSelector;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.model.value.SmartSelectorParameterValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SQLDataSourceProvider
implements IParameterDataSourceProvider {
    @Override
    public ParameterResultset getDefaultValue(ParameterDataSourceContext context) throws ParameterException {
        AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
        String defMode = cfg.getDefaultValueMode();
        ParameterCandidateValueMode candidateMode = cfg.getCandidateMode();
        List<String> v = null;
        if (candidateMode == ParameterCandidateValueMode.APPOINT && ((v = cfg.getCandidateValue()) == null || v.isEmpty())) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        if (defMode.equals("expr")) {
            throw new ParameterException("SQL\u6765\u6e90\u7684\u53c2\u6570\u9ed8\u8ba4\u503c\u4e0d\u652f\u6301\u4f7f\u7528\u516c\u5f0f");
        }
        if (defMode.equals("none")) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        SQLDataSourceModel datasourceModel = (SQLDataSourceModel)context.getModel().getDatasource();
        if (defMode.equals("appoint")) {
            AbstractParameterValue defaultVal = cfg.getDefaultValue();
            if (defaultVal.isEmpty()) {
                return ParameterResultset.EMPTY_RESULTSET;
            }
            IParameterValueFormat format = ParameterUtils.createValueFormat(datasourceModel);
            List<String> keys = defaultVal.getKeysAsString(null, format);
            if (keys.isEmpty()) {
                return ParameterResultset.EMPTY_RESULTSET;
            }
            if (v != null) {
                ArrayList<String> ss = new ArrayList<String>();
                for (String key : keys) {
                    if (!v.contains(key)) continue;
                    ss.add(key);
                }
                keys = ss;
                if (keys.isEmpty()) {
                    return ParameterResultset.EMPTY_RESULTSET;
                }
            }
            SQLQueryHelper queryHelper = new SQLQueryHelper(context.getModel());
            ParameterResultset result = queryHelper.filterByAppointValue(context.getCalculator(), keys, null, context.getPageInfo());
            result.sortByKeysOrder(keys);
            return result;
        }
        if (defMode.equals("first")) {
            SQLQueryHelper queryHelper = new SQLQueryHelper(context.getModel());
            return queryHelper.getFirstValue(context.getCalculator(), v);
        }
        throw new ParameterException("\u672a\u77e5\u7684\u53d6\u503c\u65b9\u5f0f\uff1a" + defMode);
    }

    @Override
    public ParameterResultset getCandidateValue(ParameterDataSourceContext context, ParameterHierarchyFilterItem hierarchyFilter) throws ParameterException {
        AbstractParameterValueConfig valueCfg = context.getModel().getValueConfig();
        ParameterCandidateValueMode mode = valueCfg.getCandidateMode();
        if (mode == ParameterCandidateValueMode.EXPRESSION) {
            throw new ParameterException("SQL\u6765\u6e90\u7684\u53c2\u6570\u53ef\u9009\u503c\u4e0d\u652f\u6301\u4f7f\u7528\u516c\u5f0f");
        }
        SQLDataSourceModel datasourceModel = (SQLDataSourceModel)context.getModel().getDatasource();
        SQLQueryHelper queryHelper = new SQLQueryHelper(context.getModel());
        List<String> v = null;
        if (mode == ParameterCandidateValueMode.APPOINT && ((v = valueCfg.getCandidateValue()) == null || v.isEmpty())) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        ParameterResultset result = datasourceModel.hasHierarchy() && hierarchyFilter != null ? queryHelper.getChildrenValue(context.getCalculator(), v, hierarchyFilter) : queryHelper.filterByAppointValue(context.getCalculator(), v, null, context.getPageInfo());
        if (mode == ParameterCandidateValueMode.APPOINT) {
            result.sortByKeysOrder(v);
        }
        return result;
    }

    @Override
    public boolean supportOptimizeSmartSelector() {
        return true;
    }

    @Override
    public ParameterResultset compute(ParameterDataSourceContext context, AbstractParameterValue value) throws ParameterException {
        SQLDataSourceModel datasourceModel = (SQLDataSourceModel)context.getModel().getDatasource();
        AbstractParameterValueConfig valueCfg = context.getModel().getValueConfig();
        SQLQueryHelper queryHelper = new SQLQueryHelper(context.getModel());
        if (value instanceof SmartSelectorParameterValue) {
            String[] columns = queryHelper.getSQLColumnNames(context.getCalculator());
            SmartSelector smartSelector = (SmartSelector)value.getValue();
            String filter = smartSelector.toBQL(columns[0], 6);
            return queryHelper.filterByAppointValue(context.getCalculator(), valueCfg.getCandidateValue(), filter, context.getPageInfo());
        }
        IParameterValueFormat format = ParameterUtils.createValueFormat(datasourceModel);
        List<String> values = value.getKeysAsString(null, format);
        ParameterCandidateValueMode mode = valueCfg.getCandidateMode();
        if (mode == ParameterCandidateValueMode.APPOINT) {
            List<String> candidateValues = valueCfg.getCandidateValue();
            Iterator<String> itor = values.iterator();
            while (itor.hasNext()) {
                String v = itor.next();
                if (candidateValues.contains(v)) continue;
                itor.remove();
            }
        }
        if (values.isEmpty()) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        return queryHelper.filterByAppointValue(context.getCalculator(), values, null, context.getPageInfo());
    }

    @Override
    public ParameterResultset search(ParameterDataSourceContext context, List<String> searchValues) throws ParameterException {
        ParameterModel model = context.getModel();
        List<String> candidateValue = null;
        ParameterCandidateValueMode mode = model.getValueConfig().getCandidateMode();
        if (mode == ParameterCandidateValueMode.APPOINT) {
            candidateValue = model.getValueConfig().getCandidateValue();
        }
        SQLQueryHelper queryHelper = new SQLQueryHelper(context.getModel());
        return queryHelper.search(context.getCalculator(), candidateValue, searchValues, context.getPageInfo(), context.isShowPathInSearchResult());
    }

    @Override
    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(AbstractParameterDataSourceModel datasourceModel) throws ParameterException {
        ParameterModel pm = new ParameterModel();
        pm.setDatasource(datasourceModel);
        pm.setValueConfig(new ParameterValueConfig());
        SQLQueryHelper queryHelper = new SQLQueryHelper(pm);
        return queryHelper.getSQLFields();
    }
}

