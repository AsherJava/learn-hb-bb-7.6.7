/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DSContext
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.dataset.manager.DataSetManagerFactory
 *  com.jiuqi.bi.dataset.manager.IDataSetManager
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.sql.SQLModel
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnvFactory
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 */
package com.jiuqi.budget.sqldataset;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.sql.SQLModel;
import com.jiuqi.budget.sqldataset.DataExtractResult;
import com.jiuqi.budget.sqldataset.ExtractDataRow;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterEnvFactory;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BudSqlDataSetRequest {
    @Autowired
    private DataSetStorageProvider dataSetStorageProvider;

    public DataExtractResult getResult(String queryName, List<String> argValues) throws Exception {
        DataExtractResult result = null;
        SQLModel sqlModel = (SQLModel)this.dataSetStorageProvider.findModel(queryName);
        DSContext context = this.createDSContext(sqlModel, argValues);
        IDataSetManager dataSetManager = DataSetManagerFactory.create();
        BIDataSet biDataSet = dataSetManager.open((IDSContext)context, (DSModel)sqlModel);
        Metadata metadata = biDataSet.getMetadata();
        result = new DataExtractResult(metadata.getColumnCount());
        for (int i = 0; i < metadata.getColumnCount(); ++i) {
            Column column = metadata.getColumn(i);
            result.setDataType(i, column.getDataType());
            result.setColumnName(i, column.getName());
        }
        for (BIDataRow row : biDataSet) {
            ExtractDataRow resultRow = result.addRow();
            for (int i = 0; i < metadata.getColumnCount(); ++i) {
                resultRow.setFieldValue(i, row.getValue(i));
            }
        }
        return result;
    }

    private DSContext createDSContext(SQLModel dsModel, List<String> argValues) throws Exception {
        String userGuid = NpContextHolder.getContext().getUserId();
        List parameterModels = dsModel.getParameterModels();
        IParameterEnv parameterEnv = ParameterEnvFactory.createParameterEnv((List)parameterModels, (String)userGuid);
        dsModel.prepareParameterEnv(parameterEnv);
        for (int i = 0; i < parameterModels.size(); ++i) {
            ParameterModel paramModel = (ParameterModel)parameterModels.get(i);
            String paraValue = argValues.get(i);
            ArrayList<String> values = new ArrayList<String>();
            if (paramModel.getSelectMode() == ParameterSelectMode.SINGLE) {
                values.add(paraValue);
            } else {
                String[] valueArray;
                for (String value : valueArray = paraValue.split(",|;")) {
                    values.add(value);
                }
            }
            if (paramModel == null) continue;
            if (paramModel.isRangeParameter()) {
                if (values.size() > 0) {
                    ArrayList minValues = new ArrayList();
                    minValues.add(values.get(0));
                    parameterEnv.setValue(paramModel.getName() + ".MIN", minValues);
                }
                if (values.size() <= 1) continue;
                ArrayList maxValues = new ArrayList();
                maxValues.add(values.get(1));
                parameterEnv.setValue(paramModel.getName() + ".MAX", maxValues);
                continue;
            }
            parameterEnv.setValue(paramModel.getName(), values);
        }
        DSContext context = new DSContext((DSModel)dsModel, userGuid, parameterEnv);
        return context;
    }
}

