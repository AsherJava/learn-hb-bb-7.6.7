/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.DataDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableRunInfo
 *  com.jiuqi.nr.calibre2.domain.CalibreDataRegion
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.common.QueryContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.parse.ColumnNode
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.bi.dataset.calibersum.query;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.calibersum.CaliberSumContext;
import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSField;
import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSModel;
import com.jiuqi.bi.dataset.calibersum.result.CaliberSumDSRow;
import com.jiuqi.bi.dataset.calibersum.result.CaliberSumResultSet;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DataDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableRunInfo;
import com.jiuqi.nr.calibre2.domain.CalibreDataRegion;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.common.QueryContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.parse.ColumnNode;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class CaliberSumDataQuerier_deprecated {
    private DimensionValueSet srcMasterKeys;
    private CaliberSumContext context;
    private DataDefinitionsCache dataDefinitionsCache;
    private DataAccessContext dataAccessContext;
    private CaliberSumDSModel dsModel;
    private DataModelService dataModelService;
    private INvwaDataAccessProvider accessProvider;
    private String unitColumnCode = "MDCODE";
    private Map<String, NvwaQueryModel> queryModels = new HashMap<String, NvwaQueryModel>();
    private Map<String, Integer> columnIndexes = new HashMap<String, Integer>();
    private int unitColumnIndex;
    private CaliberSumResultSet result;

    public CaliberSumDataQuerier_deprecated(DimensionValueSet srcMasterKeys, CaliberSumContext context, CaliberSumDSModel dsModel, DataModelService dataModelService, INvwaDataAccessProvider accessProvider) throws ParseException {
        this.srcMasterKeys = srcMasterKeys;
        this.context = context;
        this.dataDefinitionsCache = context.getExecutorContext().getCache().getDataDefinitionsCache();
        this.dataAccessContext = new DataAccessContext(dataModelService);
        this.dsModel = dsModel;
        this.dataModelService = dataModelService;
        this.accessProvider = accessProvider;
    }

    public CaliberSumResultSet runQuery(List<CalibreDataRegion> calibreDataRegions) throws Exception {
        this.buildQuery();
        this.result = this.doQuery(calibreDataRegions);
        this.tryAdjustData();
        return this.result;
    }

    private void buildQuery() {
        for (int dsIndex = 0; dsIndex < this.dsModel.getFields().size(); ++dsIndex) {
            CaliberSumDSField caliberSumDSField = (CaliberSumDSField)((Object)this.dsModel.getFields().get(dsIndex));
            ColumnModelDefine columnModel = caliberSumDSField.getColumnModel();
            if (columnModel == null) continue;
            String tableID = columnModel.getTableID();
            NvwaQueryModel queryModel = this.queryModels.get(tableID);
            if (queryModel == null) {
                queryModel = new NvwaQueryModel();
                this.queryModels.put(tableID, queryModel);
                TableRunInfo tableInfo = this.dataDefinitionsCache.getTableInfoByCode(caliberSumDSField.getTableModelCode());
                for (int i = 0; i < this.srcMasterKeys.size(); ++i) {
                    String dimension = this.srcMasterKeys.getName(i);
                    String dimColumnCode = tableInfo.getDimensionFieldCode(dimension);
                    ColumnModelDefine dimColumnModel = this.dataModelService.getColumnModelDefineByCode(tableID, dimColumnCode);
                    queryModel.getColumnFilters().put(dimColumnModel, this.srcMasterKeys.getValue(i));
                    if (!dimColumnCode.equals(this.unitColumnCode) && !"DW".equals(dimColumnCode)) continue;
                    NvwaQueryColumn column = new NvwaQueryColumn(dimColumnModel);
                    this.unitColumnIndex = queryModel.getColumns().size();
                    queryModel.getColumns().add(column);
                    queryModel.getGroupByColumns().add(this.unitColumnIndex);
                }
            }
            NvwaQueryColumn column = new NvwaQueryColumn(columnModel);
            this.columnIndexes.put(column.getKey(), dsIndex);
            if (column.getAggrType() == AggrType.NONE) {
                column.setAggrType(AggrType.MIN);
            }
            queryModel.getColumns().add(column);
        }
    }

    private CaliberSumResultSet doQuery(List<CalibreDataRegion> calibreDataRegions) throws Exception {
        CaliberSumResultSet resultSet = new CaliberSumResultSet(this.dsModel);
        int rowIndex = 0;
        for (CalibreDataRegion calibreDataRegion : calibreDataRegions) {
            CaliberSumDSRow sumRow = new CaliberSumDSRow(this.dsModel, calibreDataRegion, rowIndex);
            resultSet.addRow(sumRow);
            ++rowIndex;
        }
        for (NvwaQueryModel queryModel : this.queryModels.values()) {
            INvwaDataAccess dataAccess = this.accessProvider.createReadOnlyDataAccess(queryModel);
            MemoryDataSet dataSet = dataAccess.executeQuery(this.dataAccessContext);
            Metadata metadata = dataSet.getMetadata();
            for (DataRow dataRow : dataSet) {
                String unitKey = dataRow.getString(this.unitColumnIndex);
                List<CaliberSumDSRow> destRows = resultSet.getDestRows(unitKey);
                if (destRows == null) continue;
                for (int columnIndex = 0; columnIndex < metadata.getColumnCount(); ++columnIndex) {
                    String columnKey;
                    Integer destColumnIndex;
                    Object value = dataRow.getValue(columnIndex);
                    if (value instanceof BigDecimal) {
                        BigDecimal b = (BigDecimal)value;
                        value = b.doubleValue();
                    }
                    if ((destColumnIndex = this.columnIndexes.get(columnKey = ((NvwaQueryColumn)metadata.getColumn(columnIndex).getInfo()).getKey())) == null) continue;
                    for (CaliberSumDSRow destRow : destRows) {
                        destRow.statisticValue(destColumnIndex, value);
                    }
                }
            }
        }
        return resultSet;
    }

    private void tryAdjustData() {
        if (this.dsModel.getCaliberSumDSDefine().getDataMerge().booleanValue()) {
            try {
                this.buildAdjustQuery();
                this.adjust();
            }
            catch (Exception e) {
                this.context.getLogger().error("\u5408\u5e76\u8c03\u6574\u51fa\u9519", e);
            }
        }
    }

    private void buildAdjustQuery() {
        this.columnIndexes.clear();
        this.queryModels.clear();
        this.unitColumnIndex = -1;
        QueryContext queryContext = new QueryContext(this.dataAccessContext);
        for (int dsIndex = 0; dsIndex < this.dsModel.getFields().size(); ++dsIndex) {
            CaliberSumDSField caliberSumDSField = (CaliberSumDSField)((Object)this.dsModel.getFields().get(dsIndex));
            String adjustExpression = caliberSumDSField.getAdjustExpression();
            if (StringUtils.isEmpty((String)adjustExpression)) continue;
            String tableID = null;
            try {
                IExpression exp = this.dataAccessContext.getParser().parseEval(adjustExpression, (IContext)queryContext);
                for (IASTNode node : exp) {
                    if (!(node instanceof ColumnNode)) continue;
                    ColumnModelDefine columnModel = ((ColumnNode)node).getQueryColumn();
                    tableID = columnModel.getTableID();
                    break;
                }
            }
            catch (ParseException e) {
                this.context.getLogger().error("\u89e3\u6790\u5408\u5e76\u8c03\u6574\u8868\u8fbe\u5f0f\u51fa\u9519", e);
            }
            if (tableID == null) continue;
            NvwaQueryModel queryModel = this.queryModels.get(tableID);
            if (queryModel == null) {
                queryModel = new NvwaQueryModel();
                this.queryModels.put(tableID, queryModel);
                TableRunInfo tableInfo = this.dataDefinitionsCache.getTableInfoByCode(caliberSumDSField.getTableModelCode());
                DimensionValueSet masterKeys = new DimensionValueSet(this.context.getDestMasterKeys());
                masterKeys.clearValue(this.context.getUnitDim());
                for (int i = 0; i < masterKeys.size(); ++i) {
                    String dimension = masterKeys.getName(i);
                    String dimColumnCode = tableInfo.getDimensionFieldCode(dimension);
                    ColumnModelDefine dimColumnModel = this.dataModelService.getColumnModelDefineByCode(tableID, dimColumnCode);
                    queryModel.getColumnFilters().put(dimColumnModel, masterKeys.getValue(i));
                }
                String caliberMapRule = this.dsModel.getCaliberSumDSDefine().getCaliberMapRule();
                int leftBracesIndex = caliberMapRule.indexOf("[");
                if (leftBracesIndex > 0) {
                    caliberMapRule = caliberMapRule.substring(leftBracesIndex + 1, caliberMapRule.length() - 1);
                }
                ColumnModelDefine destUnitColumnModel = this.dataModelService.getColumnModelDefineByCode(tableID, caliberMapRule);
                NvwaQueryColumn column = new NvwaQueryColumn(destUnitColumnModel);
                this.unitColumnIndex = queryModel.getColumns().size();
                queryModel.getColumns().add(column);
                queryModel.getGroupByColumns().add(this.unitColumnIndex);
            }
            NvwaQueryColumn column = new NvwaQueryColumn(adjustExpression);
            this.columnIndexes.put(column.getExpression(), dsIndex);
            if (column.getAggrType() == AggrType.NONE) {
                column.setAggrType(AggrType.MIN);
            }
            queryModel.getColumns().add(column);
        }
    }

    private void adjust() throws Exception {
        for (NvwaQueryModel queryModel : this.queryModels.values()) {
            INvwaDataAccess dataAccess = this.accessProvider.createReadOnlyDataAccess(queryModel);
            MemoryDataSet dataSet = dataAccess.executeQuery(this.dataAccessContext);
            Metadata metadata = dataSet.getMetadata();
            for (DataRow dataRow : dataSet) {
                String unitKey = dataRow.getString(this.unitColumnIndex);
                CaliberSumDSRow unitRow = this.result.findRowByMainDim(unitKey);
                if (unitRow == null) continue;
                this.adjustRowData((Metadata<NvwaQueryColumn>)metadata, dataRow, unitRow);
                ArrayList<CaliberSumDSRow> parents = new ArrayList<CaliberSumDSRow>();
                this.addParents(unitRow, parents);
                for (CaliberSumDSRow parentRow : parents) {
                    this.adjustRowData((Metadata<NvwaQueryColumn>)metadata, dataRow, parentRow);
                }
            }
        }
    }

    private void adjustRowData(Metadata<NvwaQueryColumn> metadata, DataRow dataRow, CaliberSumDSRow unitRow) {
        for (int columnIndex = 0; columnIndex < metadata.getColumnCount(); ++columnIndex) {
            String columnKey;
            Integer destColumnIndex;
            if (columnIndex == this.unitColumnIndex) continue;
            Object value = dataRow.getValue(columnIndex);
            if (value instanceof BigDecimal) {
                BigDecimal b = (BigDecimal)value;
                value = b.doubleValue();
            }
            if ((destColumnIndex = this.columnIndexes.get(columnKey = ((NvwaQueryColumn)metadata.getColumn(columnIndex).getInfo()).getExpression())) == null) continue;
            unitRow.adjustValue(destColumnIndex, value);
        }
    }

    private void addParents(CaliberSumDSRow unitRow, List<CaliberSumDSRow> parents) {
        String parent = unitRow.getCalibreDataRegion().getCalibreData().getParent();
        if (parent != null) {
            CaliberSumDSRow parentRow = this.result.findRowByCode(parent);
            if (parentRow != null && parentRow.getCalibreDataRegion().getEntityKeys().contains(unitRow.getMainDimKey())) {
                parents.add(parentRow);
            }
            this.addParents(parentRow, parents);
        }
    }
}

