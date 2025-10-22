/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.dataset.storage.DataSetStorageException
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException
 *  com.jiuqi.nr.query.datascheme.extend.DataQueryParam
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.bql.extend;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.nr.bql.datasource.ComponentSet;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.DataQueryParam;
import com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NvwaModelDataTableQueryExecuter
implements IDataTableQueryExecutor {
    private TableModelDefine tableModel;
    private DataModelService dataModelService;
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    private List<String> paramNames;

    public NvwaModelDataTableQueryExecuter(ComponentSet componentSet, String srcKey) throws DataSetStorageException {
        this.nvwaDataAccessProvider = componentSet.nvwaDataAccessProvider;
        this.dataModelService = componentSet.dataModelService;
        this.tableModel = this.dataModelService.getTableModelDefineByCode(srcKey);
    }

    public MemoryDataSet<QueryField> execute(DataQueryParam param) throws DataTableAdaptException {
        try {
            NvwaQueryModel queryModel = new NvwaQueryModel();
            List allColumnModels = this.dataModelService.getColumnModelDefinesByTable(this.tableModel.getID());
            for (ColumnModelDefine columnModelDefine : allColumnModels) {
                queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            }
            for (Map.Entry entry : param.getParamValues().entrySet()) {
                ColumnModelDefine columnModel;
                String paramName = (String)entry.getKey();
                Object paramValue = entry.getValue();
                if (paramValue == null || (columnModel = this.dataModelService.getColumnModelDefineByCode(this.tableModel.getID(), paramName.substring("P_".length()))) == null) continue;
                queryModel.getColumnFilters().put(columnModel, paramValue);
            }
            INvwaDataAccess dataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
            MemoryDataSet queryResult = dataAccess.executeQuery(dataAccessContext);
            Metadata resultMetadata = new Metadata();
            Column bizKeyOrderColumn = null;
            for (int i = 0; i < queryResult.getMetadata().getColumnCount(); ++i) {
                Column queryResultColumn = queryResult.getMetadata().getColumn(i);
                String columnName = ((NvwaQueryColumn)queryResultColumn.getInfo()).getColumnModel().getName();
                Column column = new Column(columnName, queryResultColumn.getDataType(), queryResultColumn.getTitle(), null);
                if ("BIZKEYORDER".equals(columnName)) {
                    bizKeyOrderColumn = column;
                }
                resultMetadata.addColumn(column);
            }
            if (bizKeyOrderColumn == null) {
                bizKeyOrderColumn = new Column("BIZKEYORDER", 6, "BIZKEYORDER", null);
                resultMetadata.addColumn(bizKeyOrderColumn);
            }
            MemoryDataSet result = new MemoryDataSet(resultMetadata);
            int rowIndex = 1;
            for (DataRow queryRow : queryResult) {
                DataRow row = result.add();
                for (int i = 0; i < queryResult.getMetadata().getColumnCount(); ++i) {
                    row.setValue(i, queryRow.getValue(i));
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

    public List<String> getParamNames() throws DataTableAdaptException {
        if (this.paramNames == null) {
            this.paramNames = new ArrayList<String>();
            List allColumns = this.dataModelService.getColumnModelDefinesByTable(this.tableModel.getID());
            List paraColumns = allColumns.stream().filter(o -> o.getColumnType() == ColumnModelType.STRING).collect(Collectors.toList());
            for (ColumnModelDefine paraColumn : paraColumns) {
                String paramName = "P_" + paraColumn.getCode();
                this.paramNames.add(paramName);
            }
        }
        return this.paramNames;
    }
}

