/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.ParameterCalculator
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDependAnalyzer
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 */
package com.jiuqi.bi.dataset.parameter.extend;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.parameter.extend.DSFieldDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDependAnalyzer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DSFieldDependAnalyzer
implements IParameterDependAnalyzer {
    private Map<String, DSField> nameAndModelMap = new HashMap<String, DSField>();
    private IDataSetManager dataSetManager = DataSetManagerFactory.create();

    public List<ParameterDependMember> findDepends(ParameterCalculator calculator, ParameterModel model) throws ParameterException {
        try {
            return this.find(calculator, model, true);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public List<String> findAffects(ParameterCalculator calculator, ParameterModel model) throws ParameterException {
        try {
            List<ParameterDependMember> list = this.find(calculator, model, false);
            return list.stream().map(d -> d.getParameterName()).collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    private List<ParameterDependMember> find(ParameterCalculator calculator, ParameterModel model, boolean depend) throws Exception {
        ArrayList<ParameterDependMember> list = new ArrayList<ParameterDependMember>();
        DSFieldDataSourceModel datasource = (DSFieldDataSourceModel)model.getDatasource();
        DSModel dsModel = this.dataSetManager.findModel(datasource.getDsName(), datasource.getDsType());
        List<DSHierarchy> dsHierarchies = dsModel.getHiers();
        if (dsHierarchies == null || dsHierarchies.isEmpty()) {
            return list;
        }
        List parameterModels = calculator.getParameterModels();
        for (DSHierarchy dsHierarchy : dsHierarchies) {
            for (ParameterModel parameterModel : parameterModels) {
                AbstractParameterDataSourceModel parameterDataSourceModel = parameterModel.getDatasource();
                if (parameterDataSourceModel == datasource || !(parameterDataSourceModel instanceof DSFieldDataSourceModel)) continue;
                DSFieldDataSourceModel dsFieldDataSourceModel = (DSFieldDataSourceModel)parameterDataSourceModel;
                if (!datasource.getDsName().equals(dsFieldDataSourceModel.getDsName()) || !this.inSameHierarchy(dsHierarchy, datasource, dsFieldDataSourceModel, depend)) continue;
                list.add(new ParameterDependMember(parameterModel.getName(), null));
            }
        }
        return list;
    }

    private boolean inSameHierarchy(DSHierarchy dsHierarchy, DSFieldDataSourceModel dataSourceModel, DSFieldDataSourceModel dsFieldDataSourceModel, boolean depend) throws BIDataSetNotFoundException, BIDataSetException {
        List<String> levels = dsHierarchy.getLevels();
        if (levels == null || levels.size() == 0) {
            return false;
        }
        DSField currDsFieldModel = this.getDsField(dataSourceModel.getDsName(), dataSourceModel.getDsFieldName(), dataSourceModel);
        String currKeyFieldName = currDsFieldModel.getKeyField();
        DSField dsFieldModel = this.getDsField(dsFieldDataSourceModel.getDsName(), dsFieldDataSourceModel.getDsFieldName(), dataSourceModel);
        String dsKeyFieldName = dsFieldModel.getKeyField();
        if (levels.contains(currKeyFieldName) && levels.contains(dsKeyFieldName)) {
            int trad;
            int curr = levels.indexOf(currKeyFieldName);
            if (curr < (trad = levels.indexOf(dsKeyFieldName)) && !depend) {
                return true;
            }
            if (curr > trad && depend) {
                return true;
            }
        }
        return false;
    }

    private DSField getDsField(String dsName, String fieldName, DSFieldDataSourceModel dataSourceModel) throws BIDataSetNotFoundException, BIDataSetException {
        DSField dsField = this.nameAndModelMap.get(dsName + fieldName);
        if (dsField == null) {
            dsField = this.dataSetManager.findField(dsName, dataSourceModel.getDsType(), fieldName);
            this.nameAndModelMap.put(dsName + fieldName, dsField);
        }
        return dsField;
    }
}

