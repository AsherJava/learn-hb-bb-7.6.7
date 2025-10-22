/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DSContext
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.dataset.manager.DataSetManagerFactory
 *  com.jiuqi.bi.dataset.manager.IDataSetManager
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.sql.SQLModel
 *  com.jiuqi.bi.dataset.storage.DataSetStorageException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.nr.common.util.TimeDimHelper
 *  com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException
 *  com.jiuqi.nr.query.datascheme.extend.DataQueryParam
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor
 *  com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnvFactory
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 */
package com.jiuqi.nr.bql.extend;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.sql.SQLModel;
import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.nr.bql.datasource.ComponentSet;
import com.jiuqi.nr.common.util.TimeDimHelper;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.DataQueryParam;
import com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor;
import com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterEnvFactory;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlDSDataTableQueryExecuter
implements IDataTableQueryExecutor {
    private SQLModel sqlModel;
    private DataSetStorageProvider dataSetStorageProvider;

    public SqlDSDataTableQueryExecuter(ComponentSet componentSet, String srcKey) throws DataSetStorageException {
        this.dataSetStorageProvider = componentSet.dataSetStorageProvider;
        this.sqlModel = (SQLModel)this.dataSetStorageProvider.findModel(srcKey);
    }

    public MemoryDataSet<QueryField> execute(DataQueryParam param) throws DataTableAdaptException {
        try {
            DSContext context = this.createDSContext((DSModel)this.sqlModel, param.getParamValues());
            IDataSetManager dataSetManager = DataSetManagerFactory.create();
            BIDataSet biDataSet = dataSetManager.open((IDSContext)context, (DSModel)this.sqlModel);
            Metadata metadata = biDataSet.getMetadata();
            Metadata resultMetadata = new Metadata();
            Column bizKeyOrderColumn = null;
            for (int i = 0; i < metadata.getColumnCount(); ++i) {
                Column column = metadata.getColumn(i);
                Column resultColumn = new Column(column.getName(), column.getDataType(), column.getTitle(), null);
                if ("BIZKEYORDER".equals(column.getName())) {
                    bizKeyOrderColumn = resultColumn;
                }
                resultMetadata.addColumn(resultColumn);
            }
            if (bizKeyOrderColumn == null) {
                bizKeyOrderColumn = new Column("BIZKEYORDER", 6, "BIZKEYORDER", null);
                resultMetadata.addColumn(bizKeyOrderColumn);
            }
            MemoryDataSet result = new MemoryDataSet(resultMetadata);
            int rowIndex = 1;
            for (BIDataRow srcRow : biDataSet) {
                DataRow row = result.add();
                for (int i = 0; i < metadata.getColumnCount(); ++i) {
                    row.setValue(i, srcRow.getValue(i));
                }
                row.setValue(bizKeyOrderColumn.getIndex(), (Object)String.valueOf(rowIndex));
                row.commit();
                ++rowIndex;
            }
            return result;
        }
        catch (Exception e) {
            throw new DataTableAdaptException(e.getMessage(), e);
        }
    }

    private DSContext createDSContext(DSModel dsModel, Map<String, Object> paramValues) throws Exception {
        String userGuid = NpContextHolder.getContext().getUserId();
        List parameterModels = dsModel.getParameterModels();
        IParameterEnv parameterEnv = ParameterEnvFactory.createParameterEnv((List)parameterModels, (String)userGuid);
        dsModel.prepareParameterEnv(parameterEnv);
        for (int i = 0; i < parameterModels.size(); ++i) {
            ParameterModel paramModel = (ParameterModel)parameterModels.get(i);
            String paramName = paramModel.getName();
            Object paraValue = paramValues.get(paramName);
            if (paraValue == null) continue;
            ArrayList<Object> values = new ArrayList<Object>();
            if (paraValue instanceof List) {
                values.addAll((List)paraValue);
            } else {
                values.add(paraValue);
            }
            if (paramModel.getDatasource().isTimekey()) {
                ArrayList<String> timeKeys = new ArrayList<String>();
                TimeDimHelper helper = new TimeDimHelper();
                for (Object e : values) {
                    String timeKey = helper.periodToTimeKey((String)e);
                    timeKeys.add(timeKey);
                }
                values.clear();
                values.addAll(timeKeys);
            }
            if (paramModel == null) continue;
            if (paramModel.isRangeParameter()) {
                if (values.size() > 0) {
                    ArrayList minValues = new ArrayList();
                    minValues.add(values.get(0));
                    parameterEnv.setValue(paramName + ".MIN", minValues);
                }
                if (values.size() <= 1) continue;
                ArrayList maxValues = new ArrayList();
                maxValues.add(values.get(1));
                parameterEnv.setValue(paramName + ".MAX", maxValues);
                continue;
            }
            parameterEnv.setValue(paramName, values);
        }
        DSContext context = new DSContext(dsModel, userGuid, parameterEnv);
        return context;
    }

    public List<String> getParamNames() throws DataTableAdaptException {
        List parameterModels = this.sqlModel.getParameterModels();
        ArrayList<String> paramNames = new ArrayList<String>();
        for (ParameterModel p : parameterModels) {
            String paramName = p.getName();
            paramNames.add(paramName);
        }
        return paramNames;
    }
}

