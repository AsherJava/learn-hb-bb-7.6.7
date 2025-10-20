/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 */
package com.jiuqi.bi.parameter.manager;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.manager.DataSourceAttrBean;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import java.util.ArrayList;
import java.util.List;

public interface IDataSourceDataProvider {
    public void init(ParameterEngineEnv var1) throws DataSourceException;

    public MemoryDataSet<ParameterColumnInfo> filterDefaultValues(ParameterModel var1, ParameterEngineEnv var2) throws DataSourceException;

    public MemoryDataSet<ParameterColumnInfo> filterChoiceValues(ParameterModel var1, ParameterEngineEnv var2) throws DataSourceException;

    public MemoryDataSet<ParameterColumnInfo> filterValues(Object var1, ParameterModel var2, ParameterEngineEnv var3) throws DataSourceException;

    public DataSourceMetaInfo getDataSourceMetaInfo() throws DataSourceException;

    public MemoryDataSet<ParameterColumnInfo> getAllValues(String var1, ParameterEngineEnv var2) throws DataSourceException;

    public MemoryDataSet<ParameterColumnInfo> searchValues(List<String> var1, boolean var2, ParameterEngineEnv var3) throws DataSourceException;

    public MemoryDataSet<ParameterColumnInfo> fuzzySearch(List<String> var1, ParameterEngineEnv var2) throws DataSourceException;

    public String formatValue(String var1, DataSourceAttrBean var2) throws DataSourceException;

    default public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(DataSourceModel dataSourceModel) throws DataSourceException {
        return new ArrayList<DataSourceCandidateFieldInfo>();
    }

    default public int getCandidateValueCount(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        MemoryDataSet<ParameterColumnInfo> rs = this.filterChoiceValues(parameterModel, env);
        return rs.size();
    }
}

