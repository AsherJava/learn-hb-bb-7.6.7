/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource;

import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceRangeValues;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.DimTreeHierarchy;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import java.util.List;

public interface IParameterDataSourceProvider {
    public ParameterResultset getDefaultValue(ParameterDataSourceContext var1) throws ParameterException;

    public ParameterResultset getCandidateValue(ParameterDataSourceContext var1, ParameterHierarchyFilterItem var2) throws ParameterException;

    default public int getCandidateValueCount(ParameterDataSourceContext context) throws ParameterException {
        ParameterResultset rs = this.getCandidateValue(context, null);
        return rs.size();
    }

    public ParameterResultset compute(ParameterDataSourceContext var1, AbstractParameterValue var2) throws ParameterException;

    public ParameterResultset search(ParameterDataSourceContext var1, List<String> var2) throws ParameterException;

    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(AbstractParameterDataSourceModel var1) throws ParameterException;

    default public boolean supportOptimizeSmartSelector() {
        return false;
    }

    default public ParameterDataSourceRangeValues getDataSourceCandidateRange(ParameterDataSourceContext context) throws ParameterException {
        return null;
    }

    default public List<DimTreeHierarchy> getCandidateTreeHierarchies(ParameterDataSourceContext context) throws ParameterException {
        return null;
    }
}

