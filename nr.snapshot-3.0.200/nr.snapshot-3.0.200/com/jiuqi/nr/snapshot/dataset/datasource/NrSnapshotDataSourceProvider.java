/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.snapshot.dataset.datasource;

import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.snapshot.dataset.datasource.SnapshotQueryHelper;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class NrSnapshotDataSourceProvider
implements IParameterDataSourceProvider {
    private JdbcTemplate jdbcTemplate;
    private IRunTimeViewController runTimeViewController;
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    public NrSnapshotDataSourceProvider(JdbcTemplate jdbcTemplate, IRunTimeViewController runTimeViewController, IRuntimeDataSchemeService runtimeDataSchemeService) {
        this.jdbcTemplate = jdbcTemplate;
        this.runTimeViewController = runTimeViewController;
        this.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    public ParameterResultset getDefaultValue(ParameterDataSourceContext context) throws ParameterException {
        AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
        ParameterCandidateValueMode candidateMode = cfg.getCandidateMode();
        List v = null;
        if (candidateMode == ParameterCandidateValueMode.EXPRESSION) {
            throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u8fc7\u6ee4\u65b9\u5f0f\u4e0d\u652f\u6301\u4f7f\u7528\u516c\u5f0f");
        }
        if (candidateMode == ParameterCandidateValueMode.APPOINT && (null == (v = cfg.getCandidateValue()) || v.isEmpty())) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        String defMode = cfg.getDefaultValueMode();
        if (defMode.equals("expr")) {
            throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u9ed8\u8ba4\u503c\u4e0d\u652f\u6301\u4f7f\u7528\u516c\u5f0f");
        }
        if (defMode.equals("none")) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        if (defMode.equals("appoint")) {
            AbstractParameterValue defaultVal = cfg.getDefaultValue();
            if (defaultVal.isEmpty()) {
                return ParameterResultset.EMPTY_RESULTSET;
            }
            ArrayList<String> keys = (ArrayList<String>)defaultVal.getValue();
            if (keys.isEmpty()) {
                return ParameterResultset.EMPTY_RESULTSET;
            }
            if (null != v) {
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
            SnapshotQueryHelper snapshotQueryHelper = new SnapshotQueryHelper(this.jdbcTemplate, this.runTimeViewController, this.runtimeDataSchemeService);
            return snapshotQueryHelper.getFilterValue(context.getModel(), context.getCalculator(), keys);
        }
        if (defMode.equals("first")) {
            SnapshotQueryHelper snapshotQueryHelper = new SnapshotQueryHelper(this.jdbcTemplate, this.runTimeViewController, this.runtimeDataSchemeService);
            return snapshotQueryHelper.getFirstValue(context.getModel(), context.getCalculator(), v);
        }
        throw new ParameterException("\u672a\u77e5\u7684\u53d6\u503c\u65b9\u5f0f\uff1a" + defMode);
    }

    public ParameterResultset getCandidateValue(ParameterDataSourceContext context, ParameterHierarchyFilterItem hierarchyFilter) throws ParameterException {
        AbstractParameterValueConfig valueCfg = context.getModel().getValueConfig();
        ParameterCandidateValueMode mode = valueCfg.getCandidateMode();
        if (mode == ParameterCandidateValueMode.EXPRESSION) {
            throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u8fc7\u6ee4\u65b9\u5f0f\u4e0d\u652f\u6301\u4f7f\u7528\u516c\u5f0f");
        }
        List v = null;
        if (mode == ParameterCandidateValueMode.APPOINT && ((v = valueCfg.getCandidateValue()) == null || v.isEmpty())) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        SnapshotQueryHelper snapshotQueryHelper = new SnapshotQueryHelper(this.jdbcTemplate, this.runTimeViewController, this.runtimeDataSchemeService);
        return snapshotQueryHelper.getFilterValue(context.getModel(), context.getCalculator(), v);
    }

    public ParameterResultset compute(ParameterDataSourceContext context, AbstractParameterValue value) throws ParameterException {
        List values = (List)value.getValue();
        AbstractParameterValueConfig valueCfg = context.getModel().getValueConfig();
        ParameterCandidateValueMode mode = valueCfg.getCandidateMode();
        if (mode == ParameterCandidateValueMode.APPOINT) {
            List candidateValues = valueCfg.getCandidateValue();
            Iterator itor = values.iterator();
            while (itor.hasNext()) {
                String v = (String)itor.next();
                if (candidateValues.contains(v)) continue;
                itor.remove();
            }
        }
        if ((values = (List)value.getValue()).isEmpty()) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        SnapshotQueryHelper snapshotQueryHelper = new SnapshotQueryHelper(this.jdbcTemplate, this.runTimeViewController, this.runtimeDataSchemeService);
        return snapshotQueryHelper.getFilterValue(context.getModel(), context.getCalculator(), values);
    }

    public ParameterResultset search(ParameterDataSourceContext context, List<String> searchValues) throws ParameterException {
        AbstractParameterValueConfig valueCfg = context.getModel().getValueConfig();
        ParameterCandidateValueMode mode = valueCfg.getCandidateMode();
        if (mode == ParameterCandidateValueMode.EXPRESSION) {
            throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u8fc7\u6ee4\u65b9\u5f0f\u4e0d\u652f\u6301\u4f7f\u7528\u516c\u5f0f");
        }
        List v = null;
        if (mode == ParameterCandidateValueMode.APPOINT && ((v = valueCfg.getCandidateValue()) == null || v.isEmpty())) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        SnapshotQueryHelper snapshotQueryHelper = new SnapshotQueryHelper(this.jdbcTemplate, this.runTimeViewController, this.runtimeDataSchemeService);
        return snapshotQueryHelper.getSearchValue(context.getModel(), context.getCalculator(), v, searchValues);
    }

    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(AbstractParameterDataSourceModel datasourceModel) throws ParameterException {
        return new ArrayList<DataSourceCandidateFieldInfo>();
    }
}

