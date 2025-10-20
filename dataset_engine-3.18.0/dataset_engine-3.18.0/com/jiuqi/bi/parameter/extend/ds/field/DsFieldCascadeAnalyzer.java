/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.extend.ds.field;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.parameter.DataSetManagerUtils;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.extend.ds.field.DSFieldDataSourceModel;
import com.jiuqi.bi.parameter.manager.ICascadeAnalyzer;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DsFieldCascadeAnalyzer
implements ICascadeAnalyzer {
    private DSFieldDataSourceModel dataSourceModel;
    private ParameterEngineEnv env;
    private Map<String, DSField> nameAndModelMap = new HashMap<String, DSField>();

    @Override
    public List<String> analyze(DataSourceModel tempDataSourceModel, ParameterEngineEnv env, boolean cascadeCurrParameter) throws DataSourceException {
        try {
            this.env = env;
            ArrayList<String> cascadeParameterNames = new ArrayList<String>();
            this.dataSourceModel = (DSFieldDataSourceModel)tempDataSourceModel;
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            String dsType = this.dataSourceModel.getDsType();
            DSModel dsModel = dataSetManager.findModel(dsName, dsType);
            List<DSHierarchy> dsHierarchies = dsModel.getHiers();
            if (dsHierarchies != null && dsHierarchies.size() != 0) {
                for (DSHierarchy dsHierarchy : dsHierarchies) {
                    this.analyzeHierarchy(dsHierarchy, cascadeParameterNames, cascadeCurrParameter, dataSetManager);
                }
            }
            return cascadeParameterNames;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    private void analyzeHierarchy(DSHierarchy dsHierarchy, List<String> cascadeParameterNames, boolean cascadeCurrParameter, IDataSetManager dataSetManager) throws BIDataSetNotFoundException, BIDataSetException {
        String dsName = this.dataSourceModel.getDsName();
        List<ParameterModel> parameterModels = this.env.getParameterModels();
        for (ParameterModel parameterModel : parameterModels) {
            DSFieldDataSourceModel dsFieldDataSourceModel;
            DataSourceModel parameterDataSourceModel = parameterModel.getDataSourceModel();
            if (parameterDataSourceModel == null || !(parameterDataSourceModel instanceof DSFieldDataSourceModel) || (dsFieldDataSourceModel = (DSFieldDataSourceModel)parameterDataSourceModel) == this.dataSourceModel || !dsName.equals(dsFieldDataSourceModel.getDsName()) || !this.inSameHierarchy(dsHierarchy, dsFieldDataSourceModel, cascadeCurrParameter, dataSetManager)) continue;
            cascadeParameterNames.add(parameterModel.getName());
        }
    }

    private boolean inSameHierarchy(DSHierarchy dsHierarchy, DSFieldDataSourceModel dsFieldDataSourceModel, boolean cascadeCurrParameter, IDataSetManager dataSetManager) throws BIDataSetNotFoundException, BIDataSetException {
        List<String> levels = dsHierarchy.getLevels();
        if (levels == null || levels.size() == 0) {
            return false;
        }
        DSField currDsFieldModel = this.getDsField(this.dataSourceModel.getDsName(), this.dataSourceModel.getDsFieldName(), dataSetManager);
        String currKeyFieldName = currDsFieldModel.getKeyField();
        DSField dsFieldModel = this.getDsField(dsFieldDataSourceModel.getDsName(), dsFieldDataSourceModel.getDsFieldName(), dataSetManager);
        String dsKeyFieldName = dsFieldModel.getKeyField();
        if (levels.contains(currKeyFieldName) && levels.contains(dsKeyFieldName)) {
            if (levels.indexOf(currKeyFieldName) < levels.indexOf(dsKeyFieldName) && !cascadeCurrParameter) {
                return true;
            }
            if (levels.indexOf(currKeyFieldName) > levels.indexOf(dsKeyFieldName) && cascadeCurrParameter) {
                return true;
            }
        }
        return false;
    }

    private DSField getDsField(String dsName, String fieldName, IDataSetManager dataSetManager) throws BIDataSetNotFoundException, BIDataSetException {
        DSField dsField = this.nameAndModelMap.get(dsName + fieldName);
        if (dsField == null) {
            dsField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), fieldName);
            this.nameAndModelMap.put(dsName + fieldName, dsField);
        }
        return dsField;
    }
}

