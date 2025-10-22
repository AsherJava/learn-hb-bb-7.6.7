/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.query.old.DataRowImpl
 *  com.jiuqi.np.dataengine.query.old.ReadonlyTableImpl
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.grid2.GridEnums
 *  com.jiuqi.np.grid2.GridEnums$DataType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.definition.common.DataLinkEditMode
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.graphics.Point
 *  com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize
 *  io.netty.util.internal.StringUtil
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.query.service.impl;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.query.old.DataRowImpl;
import com.jiuqi.np.dataengine.query.old.ReadonlyTableImpl;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.grid2.GridEnums;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.datawarning.dao.IWarningRow;
import com.jiuqi.nr.datawarning.defines.DataWarningDefine;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.query.block.BlockInfo;
import com.jiuqi.nr.query.block.ColumnWidth;
import com.jiuqi.nr.query.block.DimensionPageLoadInfo;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QueryFieldPosition;
import com.jiuqi.nr.query.block.QueryItemSortDefine;
import com.jiuqi.nr.query.block.QueryPreWarn;
import com.jiuqi.nr.query.block.QuerySelectField;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.block.QueryStatisticsItem;
import com.jiuqi.nr.query.block.SuperLinkInfor;
import com.jiuqi.nr.query.common.GridBlockType;
import com.jiuqi.nr.query.common.QueryBlockType;
import com.jiuqi.nr.query.common.QueryConst;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.defines.DimensionItemType;
import com.jiuqi.nr.query.defines.QueryDimItem;
import com.jiuqi.nr.query.querymodal.QueryType;
import com.jiuqi.nr.query.service.GridType;
import com.jiuqi.nr.query.service.IQueryGrid;
import com.jiuqi.nr.query.service.QueryCacheManager;
import com.jiuqi.nr.query.service.QueryGridDefination;
import com.jiuqi.nr.query.service.QueryGridFactory;
import com.jiuqi.nr.query.service.QueryWarningExecutor;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.graphics.Point;
import com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class QueryGrid
implements IQueryGrid {
    ITaskOptionController taskOptionController = (ITaskOptionController)BeanUtil.getBean(ITaskOptionController.class);
    drawGridCache drawCache;
    QueryGridDefination defina;
    QueryWarningExecutor warnExecutor;
    Grid2Data grid;
    private static final Logger logger = LoggerFactory.getLogger(QueryGrid.class);
    QueryGridFactory queryGridCache = new QueryGridFactory();
    private QueryCacheManager queryCache = (QueryCacheManager)BeanUtil.getBean(QueryCacheManager.class);
    String userKey;
    String blockKey;

    public QueryGrid(QueryBlockDefine block, boolean isExport, boolean onlyLoadForm) throws Exception {
        this.defina = this.queryGridCache.getGridCache(block, isExport, onlyLoadForm);
        this.init(onlyLoadForm);
    }

    public QueryGrid(QueryBlockDefine block, boolean isExport) throws Exception {
        this.defina = this.queryGridCache.getGridCache(block, isExport);
        this.init(false);
    }

    private void init(boolean onlyLoadForm) {
        this.drawCache = new drawGridCache();
        this.grid = new Grid2Data();
        if (this.defina == null) {
            return;
        }
        if (this.defina.gridType == null) {
            this.defina.gridType = GridType.DETAIL;
        }
        if (this.defina.gridType.equals((Object)GridType.RAC)) {
            this.drawCache.rowOrColToSubs = new LinkedHashMap<Integer, List<Integer>>();
            this.drawCache.subIndexAndFaDim = new LinkedHashMap<Integer, QueryDimItem>();
        }
        if (!onlyLoadForm) {
            for (int i = 0; i < this.defina.block.getBlockInfo().getQueryDimensions().size(); ++i) {
                if (!this.defina.block.getBlockInfo().getQueryDimensions().get(i).getDimensionType().equals((Object)QueryDimensionType.QDT_FIELD)) continue;
                List<QuerySelectField> fieldList = this.defina.block.getBlockInfo().getQueryDimensions().get(i).getSelectFields();
                for (int j = 0; j < fieldList.size(); ++j) {
                    QuerySelectField field = fieldList.get(j);
                    FieldType filedType = field.getFiledType();
                    if (filedType.equals((Object)FieldType.FIELD_TYPE_DATE_TIME) || filedType.equals((Object)FieldType.FIELD_TYPE_TIME) || this.defina.duplicateFieldValues == null || this.defina.duplicateFieldValues.get(field.getCode()) == null) continue;
                    if (field.getSort() != null) {
                        Map<String, String> data = this.defina.duplicateFieldValues.get(field.getCode());
                        field.getSort().setData(data);
                        continue;
                    }
                    QueryItemSortDefine sortDefine = new QueryItemSortDefine();
                    Map<String, String> data = this.defina.duplicateFieldValues.get(field.getCode());
                    sortDefine.setData(data);
                    field.setSort(sortDefine);
                }
            }
        }
        this.drawCache.nextFiledMergeCount = 0;
        this.drawCache.onlyLoadForm = onlyLoadForm;
        this.drawCache.blockShowTotal = this.defina.block.getBlockInfo().isShowSum();
        this.drawCache.bolckShowDetial = this.defina.block.getBlockInfo().isShowDetail() != false || this.defina.mixShowDetail;
        QueryFieldPosition position = this.defina.block.getBlockInfo().getFieldPosition();
        this.drawCache.fieldIsDown = position != null ? this.defina.block.getBlockInfo().getFieldPosition().equals((Object)QueryFieldPosition.DOWN) : true;
        this.drawCache.colOrRowIndex = new ArrayList<Integer>();
        this.drawCache.indexWithField = new LinkedHashMap<Integer, QuerySelectField>();
        this.drawCache.depthColumn = new LinkedHashMap<Integer, Integer>();
        this.drawCache.mixedGridMes = new LinkedHashMap<Integer, QueryDimItem>();
        this.drawCache.mixedNoFieldGridMes = new LinkedHashMap<Integer, QueryDimItem>();
        this.drawCache.depthRow = new LinkedHashMap<Integer, Integer>();
        this.drawCache.depthRow.put(1, 1);
        this.drawCache.depthColumn.put(1, 1);
        this.drawCache.sumTotalColIndex = new LinkedHashMap<Integer, List<Integer>>();
        this.creatNewGridByType();
    }

    @Override
    public Grid2Data drawFormGrid() {
        try {
            Object tempc;
            if (this.defina == null || this.defina.block.getBlockType().equals((Object)QueryBlockType.QBT_CHART)) {
                return this.grid;
            }
            this.userKey = QueryHelper.getCacheKey(NpContextHolder.getContext().getTenant().toString(), NpContextHolder.getContext().getUserId().toString());
            this.blockKey = this.defina.block.getId();
            DimensionPageLoadInfo pageInfo = new DimensionPageLoadInfo();
            if (this.defina.isPaging && this.defina.block.getQueryType() == QueryType.NEXTPAGE && (tempc = this.queryCache.getCache(this.userKey, this.blockKey, "PAGEINFO")) != null) {
                this.defina.pageInfo = pageInfo = (DimensionPageLoadInfo)tempc;
            }
            if (this.defina.isFieldInCol) {
                this.drawColDim();
            }
            if (this.defina.isSimpleQuery && !this.defina.isSinglePeriod && this.defina.block.isShowNullRow() && this.defina.isFieldInCol) {
                this.drawRowDimIsSimpleQuery();
            } else {
                this.drawRowDim();
            }
            if (this.defina.isSimpleQuery && !this.defina.isSinglePeriod && this.defina.block.isShowNullRow() && !this.defina.isFieldInCol) {
                this.drawColDimIsSimpleQuery();
            } else if (!this.defina.isFieldInCol) {
                this.drawColDim();
            }
            this.grid.setHeaderRowCount(this.drawCache.getValueIndex());
            this.grid.setHeaderColumnCount(this.drawCache.getFieldIndex());
            if (this.defina.gridType.equals((Object)GridType.RAC) && this.defina.cols != null && this.defina.rows != null) {
                boolean isAutoGather = this.defina.helper.autoGatherCheck(QueryHelper.getSelectFieldsInBlock(this.defina.block));
                if (isAutoGather) {
                    this.initMixSubData();
                } else {
                    this.initMixSubDataNotGather();
                }
                this.grid.mergeCells(1, 1, this.drawCache.getFieldIndex() - 1, this.drawCache.getValueIndex() - 1);
                if (this.grid.getHeaderColumnCount() < 2) {
                    this.grid.setHeaderColumnCount(2);
                }
            }
            if (!(this.defina.cols == null || this.defina.isFieldInCol && this.defina.block.getHasUserForm() && this.defina.block.getFormdata() != null || this.defina.isFieldInCol)) {
                this.grid.setHeaderColumnCount(this.drawCache.getFieldIndex() + 1);
            }
            this.handlerCustomFormGrid();
            this.handleColumnWidth();
            if (!this.defina.block.isShowNullRow() && this.defina.dataTable != null && this.defina.dataTable.getTotalCount() < 50) {
                this.defina.block.setEnd(true);
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return this.grid;
    }

    private void handleColumnWidth() {
        try {
            if (this.defina.block.getBlockInfo().isTranspose() || !this.defina.isFieldInCol) {
                this.defina.block.getBlockInfo().setColumnWidth(null);
                return;
            }
            if (!this.drawCache.onlyLoadForm) {
                List<ColumnWidth> columnWidth = this.defina.block.getBlockInfo().getColumnWidth();
                List<QuerySelectField> fields = this.defina.block.getBlockInfo().getQueryDimensions().get(0).getSelectFields();
                ArrayList<String> colField = new ArrayList<String>();
                for (QuerySelectField field : fields) {
                    if (field.isHidden()) continue;
                    colField.add(field.getCode());
                    if (CollectionUtils.isEmpty(field.getStatisticsFields())) continue;
                    for (QueryStatisticsItem statisticsField : field.getStatisticsFields()) {
                        colField.add(statisticsField.getFormulaExpression());
                    }
                }
                int fieldSize = colField.size();
                int headerColumnCount = this.grid.getHeaderColumnCount();
                int columnCount = this.grid.getColumnCount();
                ArrayList<ColumnWidth> columnWidths = new ArrayList<ColumnWidth>();
                boolean anyMatch = this.defina.colDimensions.stream().anyMatch(e -> e.getDimensionType() != QueryDimensionType.QDT_FIELD);
                if (this.defina.gridType.equals((Object)GridType.RAC) || anyMatch) {
                    this.adaptRacColWidth(columnWidth, fieldSize, columnWidths);
                } else {
                    this.handleRowGridWidth(columnWidth, colField, headerColumnCount, columnCount, columnWidths);
                }
                if (columnWidth != null) {
                    for (ColumnWidth width : columnWidth) {
                        Boolean isheader = width.getIsheader();
                        Integer column = width.getColumn();
                        Integer wid = width.getWidth();
                        if (column >= columnCount) continue;
                        String colTag = width.getColTag();
                        if (isheader.booleanValue() && wid != null) {
                            this.grid.setColumnWidth(column.intValue(), wid.intValue());
                            continue;
                        }
                        if (this.defina.block.getHasUserForm() || wid == null) continue;
                        this.grid.setColumnWidth(column.intValue(), wid.intValue());
                    }
                }
            }
        }
        catch (Exception e2) {
            logger.error("\u5217\u5bbd\u5904\u7406\u5f02\u5e38" + e2.getMessage());
        }
    }

    private void handleRowGridWidth(List<ColumnWidth> columnWidth, ArrayList<String> colField, int headerColumnCount, int columnCount, ArrayList<ColumnWidth> columnWidths) {
        if (CollectionUtils.isEmpty(columnWidth) || columnWidth.size() != this.grid.getColumnCount() - 1) {
            this.addHeaderColWidth(columnWidth, headerColumnCount, columnWidths);
            for (int i = headerColumnCount; i < columnCount; ++i) {
                Optional<ColumnWidth> optional;
                ColumnWidth col = new ColumnWidth();
                String tag = null;
                int index = i - headerColumnCount;
                String fieldCode = colField.get(index);
                tag = this.defina.fieldDimNames.get(fieldCode);
                if (tag == null && columnWidths.stream().anyMatch(e -> this.defina.fieldDimNames.get(fieldCode).equals(e.getColTag()))) {
                    tag = fieldCode;
                }
                col.setColumn(i);
                col.setColTag(tag);
                col.setIsheader(false);
                if (columnWidth != null && (optional = columnWidth.stream().filter(e -> e.getColTag().equals(col.getColTag())).findFirst()).isPresent()) {
                    ColumnWidth old = optional.get();
                    col.setWidth(old.getWidth());
                }
                columnWidths.add(col);
            }
            this.defina.block.getBlockInfo().setColumnWidth(columnWidths);
        } else {
            ColumnWidth columnWidth1;
            Optional<ColumnWidth> optional;
            String finalTag;
            List<ColumnWidth> oldColumnWidth = null;
            try {
                oldColumnWidth = QueryHelper.deepCopy(this.defina.block.getBlockInfo().getColumnWidth());
            }
            catch (IOException | ClassNotFoundException e2) {
                logger.error(e2.getMessage(), e2);
            }
            block3: for (ColumnWidth width : columnWidth) {
                String tag;
                int i;
                String colTag = width.getColTag();
                Integer column = width.getColumn();
                if (width.getIsheader().booleanValue()) {
                    i = 1;
                    if (i >= headerColumnCount || i != column || this.defina.rowDimensions == null || i - 1 >= this.defina.rowDimensions.size() || (tag = this.defina.rowDimensions.get(i - 1).getDimensionName()).equals(colTag)) continue;
                    Optional<ColumnWidth> optional2 = oldColumnWidth.stream().filter(e -> e.getColTag().equals(tag) && e.getIsheader() != false).findFirst();
                    if (optional2.isPresent()) {
                        ColumnWidth columnWidth12 = optional2.get();
                        width.setWidth(columnWidth12.getWidth());
                        continue;
                    }
                    width.setWidth(null);
                    continue;
                }
                for (i = headerColumnCount; i < columnCount; ++i) {
                    tag = null;
                    int index = i - headerColumnCount;
                    String fieldCode = colField.get(index);
                    tag = this.defina.fieldDimNames.get(fieldCode);
                    if (tag == null || columnWidth.stream().anyMatch(e -> this.defina.fieldDimNames.containsKey(e.getColTag()))) {
                        tag = fieldCode;
                    }
                    if (column != i) continue;
                    if (tag == null || tag.equals(colTag)) continue block3;
                    finalTag = tag;
                    optional = oldColumnWidth.stream().filter(e -> e.getColTag().equals(finalTag) && e.getIsheader() == false).findFirst();
                    if (!optional.isPresent()) continue block3;
                    columnWidth1 = optional.get();
                    if (!width.getColTag().equals(finalTag)) continue block3;
                    width.setWidth(columnWidth1.getWidth());
                    width.setColTag(tag);
                    continue block3;
                }
            }
            block5: for (int i = headerColumnCount; i < columnCount; ++i) {
                String tag = null;
                int index = i - headerColumnCount;
                String fieldCode = colField.get(index);
                tag = this.defina.fieldDimNames.get(fieldCode);
                if (oldColumnWidth.stream().anyMatch(e -> this.defina.fieldDimNames.get(fieldCode).equals(e.getColTag())) && this.defina.fieldDimNames.containsKey(fieldCode)) {
                    tag = fieldCode;
                }
                for (ColumnWidth width : columnWidth) {
                    String colTag = width.getColTag();
                    Integer column = width.getColumn();
                    finalTag = tag;
                    optional = oldColumnWidth.stream().filter(e -> e.getColTag().equals(finalTag)).findFirst();
                    if (!optional.isPresent()) continue;
                    columnWidth1 = optional.get();
                    if (!width.getColTag().equals(finalTag)) continue;
                    width.setWidth(columnWidth1.getWidth());
                    width.setColumn(i);
                    continue block5;
                }
            }
        }
    }

    private void addHeaderColWidth(List<ColumnWidth> columnWidth, int headerColumnCount, ArrayList<ColumnWidth> columnWidths) {
        for (int i = 1; i < headerColumnCount; ++i) {
            Optional<ColumnWidth> optional;
            ColumnWidth col = new ColumnWidth();
            col.setIsheader(true);
            col.setColumn(i);
            if (this.defina.rowDimensions != null && i - 1 < this.defina.rowDimensions.size()) {
                col.setColTag(this.defina.rowDimensions.get(i - 1).getDimensionName());
            } else {
                col.setColTag("\u5c0f\u8ba1");
            }
            if (columnWidth != null && (optional = columnWidth.stream().filter(e -> e.getColTag().equals(col.getColTag())).findFirst()).isPresent()) {
                ColumnWidth old = optional.get();
                col.setWidth(old.getWidth());
            }
            columnWidths.add(col);
        }
    }

    private void adaptRacColWidth(List<ColumnWidth> columnWidth, int fieldSize, ArrayList<ColumnWidth> columnWidths) {
        int headerColumnCount;
        this.addHeaderColWidth(columnWidth, this.grid.getHeaderColumnCount(), columnWidths);
        int headerRowCount = this.grid.getHeaderRowCount();
        for (int j = headerColumnCount = this.grid.getHeaderColumnCount(); j < this.grid.getColumnCount(); ++j) {
            String tag = "";
            ColumnWidth col = new ColumnWidth();
            block1: for (int i = 1; i < headerRowCount; ++i) {
                GridCellData titleCell = this.grid.getGridCellData(j, i);
                String fieldCode = titleCell.getPersistenceData("fieldCode");
                String dimName = titleCell.getPersistenceData("DimName");
                String dimValue = titleCell.getPersistenceData("DimValue");
                if (!StringUtils.isEmpty((String)fieldCode)) {
                    tag = tag + "#" + fieldCode;
                    continue;
                }
                if (!StringUtils.isEmpty((String)dimValue)) {
                    tag = tag + dimName + "|" + dimValue;
                    continue;
                }
                for (int k = 1; k < fieldSize; ++k) {
                    GridCellData cell = this.grid.getGridCellData(j - k, i);
                    if (cell == null) continue;
                    String dim = cell.getPersistenceData("DimName");
                    String val = cell.getPersistenceData("DimValue");
                    if (StringUtils.isEmpty((String)val)) continue;
                    tag = tag + dim + "|" + val;
                    continue block1;
                }
            }
            col.setColTag(tag);
            col.setColumn(j);
            col.setIsheader(false);
            if (columnWidth != null) {
                String finalTag = tag;
                Optional<ColumnWidth> itemOpt = columnWidth.stream().filter(e -> finalTag.equals(e.getColTag())).findFirst();
                if (itemOpt.isPresent()) {
                    ColumnWidth origion = itemOpt.get();
                    col.setWidth(origion.getWidth());
                }
            }
            columnWidths.add(col);
        }
        this.defina.block.getBlockInfo().setColumnWidth(columnWidths);
    }

    public void drawColDim() throws Exception {
        if (this.defina.cols != null) {
            this.drawCache.setColIndex(this.grid.getColumnCount() - 1);
            if (!this.defina.isFieldInCol || !this.defina.block.getHasUserForm() || this.defina.block.getFormdata() == null) {
                boolean isFirstDim = true;
                boolean needNewcol = true;
                DimensionValueSet curDimValue = new DimensionValueSet();
                if (!this.defina.isFieldInCol) {
                    this.drawCache.setRowIndex(1);
                    this.drawCache.setValueIndex(2);
                    this.drawCache.hasValueIndex = true;
                    this.drawCache.setFieldIndex(this.drawCache.getColIndex());
                    this.drawCache.setColIndex(this.drawCache.getColIndex() + 1);
                    this.grid.insertColumns(this.drawCache.getColIndex(), 1, 1);
                    this.grid.insertRows(1, 1, 1);
                    this.drawCache.depthRow.put(1, 1);
                }
                if (this.defina.isFieldInCol) {
                    this.grid.insertRows(1, 1, 1);
                    this.drawCache.hasValueIndex = true;
                    this.drawCache.setValueIndex(2);
                }
                for (int i = 0; i < this.defina.cols.size(); ++i) {
                    QueryDimItem cacheItem = this.defina.cols.get(i);
                    int childRows = this.getItemTotalSize(cacheItem);
                    if (this.defina.gridType.equals((Object)GridType.RAC) && !this.drawCache.fieldIsDown && cacheItem.getItemType().equals((Object)DimensionItemType.FIELD) && cacheItem.getIsMaster() || childRows <= 0 && !cacheItem.getChildHasDetailRow() && !cacheItem.getItemType().equals((Object)DimensionItemType.FIELD) && (!this.defina.gridType.equals((Object)GridType.RAC) || !cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) && !this.drawCache.onlyLoadForm) continue;
                    if (!isFirstDim) {
                        this.drawCache.insertColIndex(1, "col");
                    }
                    if (this.defina.isFieldInCol && this.defina.rows == null && cacheItem.getItemType().equals((Object)DimensionItemType.FIELD) && !this.drawCache.fieldIsDown) {
                        this.drawCache.curField = this.defina.showedFields.stream().filter(f -> f.getCode().equals(cacheItem.getEditTitle())).findFirst().get();
                    }
                    if (this.defina.gridType.equals((Object)GridType.RAC) && this.defina.isFieldInCol && !this.drawCache.fieldIsDown) {
                        QuerySelectField field = this.defina.showedFields.stream().filter(f -> f.getCode().equals(cacheItem.getEditTitle())).findFirst().get();
                        this.drawCache.indexWithField.put(this.drawCache.getColIndex(), field);
                    }
                    if (this.defina.isFieldInCol && this.defina.gridType.equals((Object)GridType.RAC) && (cacheItem.getChildItemhasField() || cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || cacheItem.getItemType().equals((Object)DimensionItemType.MASTERENTITY))) {
                        this.drawCache.mixedGridMes.put(this.drawCache.getColIndex(), cacheItem);
                        this.drawCache.colOrRowIndex.add(this.drawCache.getColIndex());
                    }
                    this.loopDimItemDrawColFormGrid(null, cacheItem, 1, needNewcol, curDimValue);
                    isFirstDim = false;
                }
            }
            if (this.defina.gridType.equals((Object)GridType.RAC) && this.defina.cols.size() > 0 && this.defina.cols.get(0).getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && this.defina.isFieldInCol && this.drawCache.fieldIsDown) {
                this.grid.mergeCells(1, 1, 1, this.drawCache.getValueIndex() - 1);
            }
            if (!this.drawCache.staticticsFieldColIndexs.isEmpty() && this.defina.gridType != GridType.COL) {
                for (int col = this.drawCache.fieldIndex + 1; col < this.grid.getColumnCount(); ++col) {
                    if (this.drawCache.staticticsFieldColIndexs.contains(col)) continue;
                    this.grid.mergeCells(col, this.drawCache.staticticsRowIndex - 1, col, this.drawCache.staticticsRowIndex.intValue());
                }
            }
            if (!this.drawCache.fieldInSubTotalColIndexs.isEmpty()) {
                GridCellData cell;
                for (Integer col : this.drawCache.fieldInSubTotalColIndexs) {
                    cell = this.grid.getGridCellData(col.intValue(), this.drawCache.fieldInSubTotalRowIndex.intValue());
                    cell.setMerged(false);
                    cell.setMergeInfo(null);
                    this.grid.mergeCells(col.intValue(), this.drawCache.fieldInSubTotalRowIndex.intValue(), col.intValue(), this.drawCache.getValueIndex() - 1);
                }
                int end = this.drawCache.fieldInSubTotalColIndexs.get(0);
                for (int i = 1; i < end; ++i) {
                    cell = this.grid.getGridCellData(i, this.drawCache.fieldInSubTotalRowIndex - 1);
                    cell.setMerged(false);
                    cell.setMergeInfo(null);
                    this.grid.mergeCells(i, this.drawCache.fieldInSubTotalRowIndex - 1, i, this.drawCache.getValueIndex() - 1);
                }
            }
            if (this.drawCache.getFieldIndex() > 0) {
                this.grid.mergeCells(1, 1, this.drawCache.getFieldIndex(), this.drawCache.getValueIndex() - 1);
            }
        } else {
            this.handlerDetialsGrid("col");
        }
    }

    public void drawColDimIsSimpleQuery() throws Exception {
        if (this.defina.cols != null) {
            this.drawCache.setColIndex(this.grid.getColumnCount() - 1);
            if (!this.defina.isFieldInCol || !this.defina.block.getHasUserForm() || this.defina.block.getFormdata() == null) {
                boolean isFirstDim = true;
                boolean needNewcol = true;
                DimensionValueSet curDimValue = new DimensionValueSet();
                if (!this.defina.isFieldInCol) {
                    this.drawCache.setRowIndex(1);
                    this.drawCache.setValueIndex(1);
                    this.drawCache.hasValueIndex = true;
                    this.drawCache.setFieldIndex(this.drawCache.getColIndex());
                    this.drawCache.setColIndex(this.drawCache.getColIndex() + 1);
                    this.grid.insertColumns(this.drawCache.getColIndex(), 1, 1);
                    this.drawCache.depthRow.put(1, 1);
                }
                for (int i = 0; i < this.defina.cols.size(); ++i) {
                    QueryDimItem cacheItem = this.defina.cols.get(i);
                    int childRows = this.getItemTotalSize(cacheItem);
                    if (!this.drawCache.fieldIsDown && cacheItem.getItemType().equals((Object)DimensionItemType.FIELD) && cacheItem.getIsMaster() || childRows <= 0 && !cacheItem.getChildHasDetailRow() && !cacheItem.getItemType().equals((Object)DimensionItemType.FIELD) && !this.drawCache.onlyLoadForm && !this.defina.block.isShowNullRow()) continue;
                    if (!isFirstDim) {
                        this.drawCache.insertColIndex(1, "col");
                    }
                    this.loopSimpleDimItemDrawColFormGrid(null, cacheItem, 1, needNewcol, curDimValue);
                    isFirstDim = false;
                }
            }
        } else {
            this.handlerDetialsGrid("col");
        }
    }

    public void drawRowDimIsSimpleQuery() throws Exception {
        if (this.defina.rows != null) {
            int sumTotalRow = 0;
            boolean needNewRow = false;
            if (this.defina.cols != null && this.defina.isFieldInCol) {
                this.drawCache.setColIndex(1);
                this.drawCache.orderColIndex = 0;
                this.drawCache.setFieldIndex(1);
                if (this.drawCache.blockShowTotal && this.defina.pageInfo.isFirstPage.booleanValue()) {
                    this.drawCache.setRowIndex(this.drawCache.valueIndex - 1);
                    this.drawCache.insertRowIndex(1, "row");
                    sumTotalRow = this.drawCache.getRowIndex(-1);
                    if (!this.drawCache.onlyLoadForm) {
                        List<Object> showedFields = new ArrayList();
                        showedFields = !this.drawCache.staticticsFieldColIndexs.isEmpty() ? this.getHasStatisticShowFields() : this.defina.showedFields;
                        this.initSelectFieldsCell(null, -1, this.defina.totalRow, showedFields, "row", 0);
                    } else {
                        this.initSelectFieldsCell(null, -1, null, this.defina.showedFields, "row", 0);
                    }
                    this.getCell(null, this.drawCache.getRowIndex(-1), this.drawCache.getColIndex(), "\u5408\u8ba1", 3);
                    int masterCount = this.defina.showedFields.stream().filter(p -> p.getIsMaster().equals("true")).collect(Collectors.toList()).size();
                    this.grid.mergeCells(1, sumTotalRow, this.drawCache.getFieldIndex() + masterCount - 1, sumTotalRow);
                }
                this.drawCache.setRowIndex(this.grid.getRowCount() - 1);
            }
            this.drawCache.depthRow = new LinkedHashMap<Integer, Integer>();
            if (this.defina.rows.size() == 0 && this.defina.isFieldInCol || !this.defina.block.isPaging()) {
                this.defina.block.setEnd(true);
            }
            for (int i = 0; i < this.defina.rows.size(); ++i) {
                QueryDimItem cacheItem = this.defina.rows.get(i);
                int childSize = this.getItemTotalSize(cacheItem);
                if (cacheItem.getChildDataSize() <= 0 && cacheItem.getOwnDataSize() <= 0 && !cacheItem.getItemType().equals((Object)DimensionItemType.FIELD) && childSize <= 0 && !this.drawCache.onlyLoadForm && !this.defina.block.isShowNullRow()) continue;
                this.loopSimpleDimItemDrawRowFormGrid(null, cacheItem, 1, needNewRow);
                needNewRow = true;
            }
            if (!this.defina.isFieldInCol && this.defina.gridType.equals((Object)GridType.DETAIL) && this.defina.rows != null) {
                this.handlerDetialsGrid("col");
            }
        } else {
            this.handlerDetialsGrid("row");
        }
    }

    public void drawRowDim() throws Exception {
        if (this.defina.rows != null) {
            int sumTotalRow = 0;
            boolean needNewRow = false;
            if (this.defina.cols != null && this.defina.isFieldInCol) {
                this.drawCache.setColIndex(1);
                this.drawCache.orderColIndex = 0;
                this.drawCache.setFieldIndex(2);
                this.grid.insertColumns(this.drawCache.getColIndex(), 1, 1);
                if (this.defina.gridType != GridType.RAC && this.drawCache.blockShowTotal && this.defina.pageInfo.isFirstPage.booleanValue()) {
                    this.drawCache.setRowIndex(this.drawCache.valueIndex - 1);
                    this.drawCache.insertRowIndex(1, "row");
                    sumTotalRow = this.drawCache.getRowIndex(-1);
                    this.getCell(null, this.drawCache.getRowIndex(-1), this.drawCache.getColIndex(), "\u5408\u8ba1", 3);
                    if (!this.drawCache.onlyLoadForm) {
                        List<Object> showedFields = new ArrayList();
                        showedFields = !this.drawCache.staticticsFieldColIndexs.isEmpty() ? this.getHasStatisticShowFields() : this.defina.showedFields;
                        this.initSelectFieldsCell(null, -1, this.defina.totalRow, showedFields, "row", 0);
                    } else {
                        this.initSelectFieldsCell(null, -1, null, this.defina.showedFields, "row", 0);
                    }
                }
                this.drawCache.setRowIndex(this.grid.getRowCount() - 1);
            }
            if (this.defina.cols == null && !this.defina.isFieldInCol) {
                this.grid.insertColumns(1, 1, 1);
                this.drawCache.setFieldIndex(2);
            }
            this.drawCache.depthRow = new LinkedHashMap<Integer, Integer>();
            if (this.defina.rows.size() == 0 && this.defina.isFieldInCol || !this.defina.block.isPaging()) {
                this.defina.block.setEnd(true);
            }
            for (int i = 0; i < this.defina.rows.size(); ++i) {
                QueryDimItem cacheItem = this.defina.rows.get(i);
                int childSize = this.getItemTotalSize(cacheItem);
                if (cacheItem.getChildDataSize() <= 0 && cacheItem.getOwnDataSize() <= 0 && !cacheItem.getChildHasDetailRow() && !cacheItem.getItemType().equals((Object)DimensionItemType.FIELD) && childSize <= 0 && (!this.defina.gridType.equals((Object)GridType.RAC) || !cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) && !this.drawCache.onlyLoadForm && !this.defina.block.isShowNullRow()) continue;
                if (!this.defina.isFieldInCol && this.defina.cols == null && cacheItem.getItemType().equals((Object)DimensionItemType.FIELD)) {
                    this.drawCache.curField = this.defina.showedFields.stream().filter(f -> f.getCode().equals(cacheItem.getEditTitle())).findFirst().get();
                }
                if (this.defina.gridType.equals((Object)GridType.RAC) && !this.defina.isFieldInCol && !this.drawCache.fieldIsDown && cacheItem.getItemType().equals((Object)DimensionItemType.FIELD) && cacheItem.getIsMaster()) continue;
                this.loopDimItemDrawRowFormGrid(null, cacheItem, 1, needNewRow);
                needNewRow = true;
            }
            if (this.defina.gridType != GridType.RAC && this.drawCache.getFieldIndex() > 0) {
                if (this.drawCache.getValueIndex() > 0 && this.defina.block.getBlockInfo().getCustomFieldName() == null) {
                    this.grid.mergeCells(1, 1, this.drawCache.getFieldIndex() - 1, this.drawCache.getValueIndex() - 1);
                }
                this.grid.mergeCells(1, sumTotalRow, this.drawCache.getFieldIndex() - 1, sumTotalRow);
            }
            if (this.defina.gridType.equals((Object)GridType.RAC) && this.defina.rows.size() > 0 && this.defina.rows.get(0).getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && !this.defina.isFieldInCol && this.drawCache.fieldIsDown) {
                this.grid.mergeCells(1, 1, this.drawCache.getFieldIndex() - 1, 1);
            }
            if (!this.defina.isFieldInCol && this.defina.gridType.equals((Object)GridType.DETAIL) && this.defina.rows != null && !this.defina.block.isShowNullRow()) {
                this.handlerDetialsGrid("col");
            }
        } else {
            this.handlerDetialsGrid("row");
        }
    }

    public void loopDimItemDrawColFormGrid(QueryDimItem parentItem, QueryDimItem cacheItem, int depth, boolean needNewcol, DimensionValueSet curDimValue) throws Exception {
        boolean isNotLastDim;
        Optional<QuerySelectField> fieldOpt;
        boolean isHasSum;
        boolean isLastDimAndSumFirst = false;
        int curDepth = this.getCurDepth(cacheItem, depth);
        int childRows = this.getItemTotalSize(cacheItem);
        List<Object> datas = new ArrayList();
        boolean hasOneMoreRow = cacheItem.totalSize > 1 || childRows > 1;
        boolean bl = isHasSum = cacheItem.getStaticticsRow() != null && hasOneMoreRow && cacheItem.getIsShowSubTotal() || !this.drawCache.bolckShowDetial && cacheItem.getStaticticsRow() != null;
        if (!this.drawCache.bolckShowDetial && isHasSum && parentItem == null && cacheItem.getChildItems().size() > 1) {
            int childsubSize = this.childItemSubSize(cacheItem, false, false);
            boolean bl2 = isHasSum = childsubSize > 1 ? isHasSum : false;
        }
        if (cacheItem.getDetailRows() != null && cacheItem.getDetailRows().size() > 0 && isHasSum && cacheItem.getStaticticsRow() != null && cacheItem.getIsSubTotalInFront() && this.drawCache.bolckShowDetial) {
            isLastDimAndSumFirst = isHasSum;
            datas = cacheItem.getDetailRows();
        } else {
            if (cacheItem.getDetailRows() != null && cacheItem.getDetailRows().size() > 0 && this.drawCache.bolckShowDetial) {
                datas = cacheItem.getDetailRows();
            }
            if (cacheItem.getStaticticsRow() != null && cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || parentItem != null && parentItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && cacheItem.getItemType().equals((Object)DimensionItemType.FIELDTEXT) && parentItem.getStaticticsRow() != null) {
                if ((cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || cacheItem.getItemType().equals((Object)DimensionItemType.FIELDTEXT) && !cacheItem.getChildItems().isEmpty()) && cacheItem.getStaticticsRow() != null) {
                    datas.add(cacheItem.getStaticticsRow());
                }
                if (parentItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && cacheItem.getItemType().equals((Object)DimensionItemType.FIELDTEXT) && !cacheItem.getChildItems().isEmpty()) {
                    cacheItem.setStaticticsRow(parentItem.getStaticticsRow());
                    datas.add(cacheItem.getStaticticsRow());
                }
            }
            if (datas.isEmpty() && (cacheItem.getDetailRows() == null || cacheItem.getDetailRows().size() <= 0 || cacheItem.getStaticticsRow() == null) && parentItem != null && parentItem.getDetailRows() != null && !parentItem.getDetailRows().isEmpty()) {
                datas = parentItem.getDetailRows();
            }
        }
        if (this.drawCache.bolckShowDetial || !cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || parentItem.getChildItems().size() != 1) {
            this.getCell(cacheItem, this.drawCache.getRowIndex(curDepth), this.drawCache.getColIndex(), cacheItem.getShowTitle(), 3);
        }
        if (cacheItem.getItemType().equals((Object)DimensionItemType.FIELD) && this.defina.statisticsFieldMap.containsKey(cacheItem.getEditTitle())) {
            this.drawCache.staticticsFieldColIndexs.add(this.drawCache.getColIndex());
            this.drawCache.staticticsRowIndex = this.drawCache.getRowIndex(curDepth);
        }
        if (this.defina.gridType.equals((Object)GridType.RAC) && !cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && cacheItem.getItemType().equals((Object)DimensionItemType.FIELD) && (fieldOpt = this.defina.showedFields.stream().filter(f -> f.getCode().equals(cacheItem.getEditTitle())).findFirst()).isPresent()) {
            QuerySelectField fieldd = fieldOpt.get();
            this.drawCache.fieldInIndex.put(this.drawCache.getColIndex(), fieldd);
        }
        int startCol = this.drawCache.getColIndex();
        int mergeRow = this.drawCache.getRowIndex(curDepth);
        boolean sumStartRow = false;
        boolean sumStartCol = false;
        if (!isLastDimAndSumFirst && this.defina.gridType != GridType.RAC) {
            this.loadData(cacheItem, cacheItem, datas, isLastDimAndSumFirst, curDepth, "col");
        }
        if (this.defina.gridType.equals((Object)GridType.RAC) && !this.defina.isFieldInCol && (cacheItem.getChildItems() == null || cacheItem.getChildItems().isEmpty())) {
            this.drawCache.mixedNoFieldGridMes.put(this.drawCache.getColIndex(), cacheItem);
        }
        if (this.defina.gridType.equals((Object)GridType.RAC) && !this.defina.isFieldInCol && (!cacheItem.getChildItemhasField() && cacheItem.getChildItems() != null && cacheItem.getChildItems().isEmpty() || cacheItem.getChildItemhasField() || cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW))) {
            this.loadMixData(parentItem, cacheItem, isLastDimAndSumFirst, curDepth, "col");
        }
        if (cacheItem.getChildItems() != null && cacheItem.getChildItems().size() > 0) {
            ++depth;
            DimensionValueSet childDimValue = null;
            for (int j = 0; j < cacheItem.getChildItems().size(); ++j) {
                QueryDimItem childItem = cacheItem.getChildItems().get(j);
                int childSize = this.getItemTotalSize(childItem);
                if (childSize <= 0 && childItem.getOwnDataSize() <= 0 && childItem.getChildDataSize() <= 0 && !childItem.getItemType().equals((Object)DimensionItemType.FIELD) && (!childItem.getItemType().equals((Object)DimensionItemType.FIELDTEXT) || childItem.getChildItems().isEmpty()) && !childItem.getChildHasDetailRow() && (!this.defina.gridType.equals((Object)GridType.RAC) || !childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) && !this.drawCache.onlyLoadForm || (this.defina.gridType != GridType.RAC || this.drawCache.fieldIsDown || !cacheItem.getItemType().equals((Object)DimensionItemType.FIELD)) && (!isHasSum && childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || this.defina.gridType == GridType.RAC && !this.drawCache.fieldIsDown && cacheItem.getItemType().equals((Object)DimensionItemType.MASTERENTITY) && childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW))) continue;
                curDepth = this.getCurDepth(childItem, depth);
                if (this.drawCache.bolckShowDetial || !childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || cacheItem.getChildItems().size() != 1) {
                    this.drawCache.insertRowIndex(curDepth, "col");
                }
                if (!needNewcol) {
                    this.drawCache.insertColIndex(curDepth, "col");
                }
                needNewcol = false;
                if (this.defina.isFieldInCol && this.defina.gridType.equals((Object)GridType.RAC) && (childItem.getChildItemhasField() && this.drawCache.fieldIsDown || !this.drawCache.fieldIsDown && (childItem.getChildItems() == null || childItem.getChildItems().size() == 0))) {
                    this.drawCache.mixedGridMes.put(this.drawCache.getColIndex(), childItem);
                    this.drawCache.colOrRowIndex.add(this.drawCache.getColIndex());
                }
                this.loopDimItemDrawColFormGrid(cacheItem, childItem, curDepth, true, childDimValue);
                if (!this.drawCache.fieldIsDown && this.defina.isFieldInCol && cacheItem.getChildItems().size() == 1 && isHasSum && childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
                    this.drawCache.insertColIndex(curDepth, "col");
                }
                if (this.defina.gridType == GridType.RAC || !childItem.getItemType().equals((Object)DimensionItemType.FIELD) || !this.drawCache.fieldIsDown || !childItem.getChildItems().isEmpty()) continue;
                this.loadData(cacheItem, childItem, datas, isLastDimAndSumFirst, curDepth, "col");
            }
        } else if (!cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || this.defina.isFieldInCol) {
            // empty if block
        }
        if (isLastDimAndSumFirst && this.defina.gridType != GridType.RAC) {
            this.loadData(cacheItem, cacheItem, datas, isLastDimAndSumFirst, curDepth, "col");
        }
        if (startCol != this.drawCache.getColIndex() || mergeRow != mergeRow) {
            this.grid.mergeCells(startCol, mergeRow, this.drawCache.getColIndex(), mergeRow);
        }
        if (this.defina.gridType == GridType.COL && this.drawCache.fieldIsDown && (cacheItem.getChildItems() == null || cacheItem.getChildItems().isEmpty())) {
            this.grid.mergeCells(this.drawCache.getColIndex(), this.drawCache.getRowIndex(curDepth), this.drawCache.getColIndex(), this.drawCache.getValueIndex() - 1);
            if (cacheItem.getItemType().equals((Object)DimensionItemType.FIELD) && parentItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
                this.drawCache.fieldInSubTotalRowIndex = this.drawCache.getRowIndex(curDepth);
                this.drawCache.fieldInSubTotalColIndexs.add(this.drawCache.getColIndex());
            }
        }
        boolean bl3 = isNotLastDim = parentItem == null || parentItem.getChildItems().size() > 1;
        if (this.drawCache.getValueIndex() > 0 && isHasSum && isNotLastDim && mergeRow + 1 < this.drawCache.getValueIndex() - 1) {
            this.grid.mergeCells(startCol, mergeRow + 1, startCol, this.drawCache.getValueIndex() - 1);
        }
        if (cacheItem.getOwnDataSize() == 1 && !isHasSum && cacheItem.getChildItems().size() == 1 && mergeRow < this.drawCache.getValueIndex() - 1) {
            this.grid.mergeCells(startCol, mergeRow, startCol, this.drawCache.getValueIndex() - 1);
        }
    }

    public void loopSimpleDimItemDrawColFormGrid(QueryDimItem parentItem, QueryDimItem cacheItem, int depth, boolean needNewcol, DimensionValueSet curDimValue) throws Exception {
        int curDepth = this.getCurDepth(cacheItem, depth);
        ArrayList<IDataRow> datas = new ArrayList();
        if (cacheItem.getDetailRows() != null && cacheItem.getDetailRows().size() > 0) {
            datas = cacheItem.getDetailRows();
        } else if (this.defina.block.isShowNullRow() && !this.defina.isFieldInCol) {
            int fieldCount = this.defina.block.getQueryDimensions().get(0).getSelectFields().size();
            ArrayList<String> rowDatas = new ArrayList<String>();
            rowDatas.add(cacheItem.getEditTitle());
            for (int x = 0; x < fieldCount - 1; ++x) {
                rowDatas.add(null);
            }
            DataRowImpl nullDetailRow = new DataRowImpl((ReadonlyTableImpl)this.defina.dataTable, cacheItem.getDimValueSet(), rowDatas);
            datas.add((IDataRow)nullDetailRow);
            this.defina.block.getBlockInfo().setTotalCount(this.defina.block.getBlockInfo().getTotalCount() + 1);
        }
        this.loadData(null, null, datas, false, curDepth, "col");
    }

    public void loopDimItemDrawRowFormGrid(QueryDimItem parentItem, QueryDimItem cacheItem, int depth, boolean needNewRow) throws Exception {
        List<IDataRow> detailRows;
        boolean isHasSum;
        boolean sumStartCol = false;
        boolean sumStartRow = false;
        boolean isLastDimAndSumFirst = false;
        boolean isInsertDetial = false;
        List<Object> datas = new ArrayList();
        int curDepth = this.getCurDepth(cacheItem, depth);
        int childRows = this.getItemTotalSize(cacheItem);
        String title = this.getCurTitle(cacheItem);
        boolean hasOneMoreRow = cacheItem.totalSize >= 1 || childRows > 1;
        boolean bl = isHasSum = cacheItem.getStaticticsRow() != null && hasOneMoreRow && cacheItem.getIsShowSubTotal() || cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || !this.drawCache.bolckShowDetial && cacheItem.getStaticticsRow() != null;
        if (!this.drawCache.bolckShowDetial && isHasSum && parentItem == null && cacheItem.getChildItems().size() > 1) {
            int childsubSize = this.childItemSubSize(cacheItem, false, false);
            cacheItem.setChildItemSize(childsubSize);
            boolean bl2 = isHasSum = childsubSize > 1 ? isHasSum : false;
        }
        if (cacheItem.getDetailRows() != null && cacheItem.getDetailRows().size() > 0 && isHasSum && cacheItem.getIsSubTotalInFront() && this.drawCache.bolckShowDetial) {
            isLastDimAndSumFirst = isHasSum;
            datas = cacheItem.getDetailRows();
        } else {
            boolean onlyOneItemShowSub;
            if (cacheItem.getDetailRows() != null && cacheItem.getDetailRows().size() > 0 && this.drawCache.bolckShowDetial) {
                datas = cacheItem.getDetailRows();
                isInsertDetial = true;
            }
            boolean bl3 = onlyOneItemShowSub = cacheItem.totalSize == 1 && !this.defina.block.isShowDetail();
            if ((isHasSum && cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || !cacheItem.getIsNotTreeStruct() && cacheItem.getStaticticsRow() != null && cacheItem.getChildHasDetailRow() || onlyOneItemShowSub) && datas.size() == 0) {
                datas.add(cacheItem.getStaticticsRow());
            }
        }
        if (this.defina.block.isShowNullRow() && datas.isEmpty() && !cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && this.defina.isFieldInCol && this.defina.isSimpleQuery) {
            int fieldCount = this.defina.block.getQueryDimensions().get(0).getSelectFields().size();
            ArrayList<String> rowDatas = new ArrayList<String>();
            rowDatas.add(cacheItem.getEditTitle());
            for (int x = 0; x < fieldCount - 1; ++x) {
                rowDatas.add(null);
            }
            DataRowImpl nullDetailRow = new DataRowImpl((ReadonlyTableImpl)this.defina.dataTable, cacheItem.getDimValueSet(), rowDatas);
            datas.add(nullDetailRow);
            this.defina.block.getBlockInfo().setTotalCount(this.defina.block.getBlockInfo().getTotalCount() + 1);
        }
        if (needNewRow) {
            this.drawCache.insertRowIndex(curDepth, "row");
            needNewRow = false;
        }
        if ((cacheItem.getIsNotTreeStruct() || !cacheItem.getIsNotTreeStruct() && this.defina.block.getBlockInfo().getDimLevelShowType() == 2) && (this.drawCache.bolckShowDetial || !cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || parentItem.getChildItems().size() != 1)) {
            this.drawCache.insertColIndex(curDepth, "row");
        }
        int startRow = this.drawCache.getRowIndex(curDepth);
        int startCol = this.drawCache.getColIndex();
        if (cacheItem.getIsNotTreeStruct() || !cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
            if (this.drawCache.bolckShowDetial || !cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || parentItem.getChildItems().size() != 1) {
                this.getCell(cacheItem, this.drawCache.getRowIndex(curDepth), this.drawCache.getColIndex(), title, 1);
            }
            if ((!cacheItem.getIsSubTotalInFront() || !cacheItem.getIsShowSubTotal() || this.defina.block.isShowNullRow()) && cacheItem.isLastDimension() && this.drawCache.isFirstDrawThisDim && this.defina.gridType == GridType.ROW && !this.defina.isSimpleQuery) {
                this.drawCache.isFirstDrawThisDim = false;
                this.drawCache.insertColIndex(curDepth + 1, "row");
                int insertDepth = curDepth + 1;
                if (this.drawCache.depthColumn.containsKey(insertDepth)) {
                    this.drawCache.setColIndex(this.drawCache.depthColumn.get(insertDepth));
                } else {
                    this.drawCache.colIndex++;
                    if (this.drawCache.colIndex == this.drawCache.orderColIndex) {
                        return;
                    }
                    this.drawCache.fieldIndex++;
                    this.grid.insertColumns(this.drawCache.colIndex, 1, this.drawCache.colIndex - 1);
                    if (this.drawCache.rowOrColToSubs != null) {
                        LinkedHashMap<Integer, List<Integer>> rowOrColToSubsNew = new LinkedHashMap<Integer, List<Integer>>();
                        for (Integer key : this.drawCache.rowOrColToSubs.keySet()) {
                            List<Integer> subsVal = this.drawCache.rowOrColToSubs.get(key);
                            rowOrColToSubsNew.put(key + 1, subsVal);
                        }
                        this.drawCache.rowOrColToSubs = rowOrColToSubsNew;
                    }
                    this.drawCache.depthColumn.put(insertDepth, this.drawCache.colIndex);
                    this.drawCache.fieldIndex = Math.max(this.drawCache.fieldIndex, this.drawCache.colIndex + 1);
                }
            }
        } else if (parentItem != null && this.defina.block.isShowDetail() && !CollectionUtils.isEmpty(detailRows = parentItem.getDetailRows())) {
            datas.addAll(detailRows);
        }
        if (this.defina.gridType.equals((Object)GridType.RAC) && !cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && cacheItem.getItemType().equals((Object)DimensionItemType.FIELD)) {
            QuerySelectField fieldd = this.defina.showedFields.stream().filter(f -> f.getCode().equals(cacheItem.getEditTitle())).findFirst().get();
            this.drawCache.fieldInIndex.put(this.drawCache.getRowIndex(depth), fieldd);
        }
        if (this.defina.gridType.equals((Object)GridType.RAC) && !this.defina.isFieldInCol && !this.drawCache.fieldIsDown && cacheItem.getItemType().equals((Object)DimensionItemType.FIELD)) {
            QuerySelectField field = this.defina.showedFields.stream().filter(f -> f.getCode().equals(cacheItem.getEditTitle())).findFirst().get();
            this.drawCache.indexWithField.put(this.drawCache.getRowIndex(curDepth), field);
        }
        if (this.defina.gridType.equals((Object)GridType.RAC) && !this.defina.isFieldInCol && (cacheItem.getChildItemhasField() || cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || cacheItem.getItemType().equals((Object)DimensionItemType.MASTERENTITY) || (cacheItem.getChildItems() == null || cacheItem.getChildItems().isEmpty()) && (cacheItem.getItemType().equals((Object)DimensionItemType.ENTITY) || cacheItem.getItemType().equals((Object)DimensionItemType.PERIODENTITY)) && this.defina.block.getFieldPosition() == QueryFieldPosition.UP)) {
            this.drawCache.mixedGridMes.put(this.drawCache.getRowIndex(curDepth), cacheItem);
            this.drawCache.colOrRowIndex.add(this.drawCache.getRowIndex(curDepth));
        }
        if (this.defina.gridType.equals((Object)GridType.RAC) && this.defina.isFieldInCol && (cacheItem.getChildItems() == null || cacheItem.getChildItems().isEmpty())) {
            this.drawCache.mixedNoFieldGridMes.put(this.drawCache.getRowIndex(curDepth), cacheItem);
        }
        if (!isLastDimAndSumFirst && this.defina.gridType != GridType.RAC) {
            if (!cacheItem.getIsNotTreeStruct() && cacheItem.getStaticticsRow() == null && cacheItem.getIsShowSubTotal() && childRows > 1) {
                isLastDimAndSumFirst = true;
            }
            this.loadData(cacheItem, cacheItem, datas, isLastDimAndSumFirst, curDepth, "row");
            isLastDimAndSumFirst = false;
        }
        if (isHasSum && !cacheItem.getIsNotTreeStruct() && isLastDimAndSumFirst && cacheItem.getStaticticsRow() == null) {
            ArrayList<IDataRow> sumData = new ArrayList<IDataRow>();
            this.loadData(cacheItem, cacheItem, sumData, false, curDepth, "row");
        }
        if (this.defina.gridType.equals((Object)GridType.RAC) && this.defina.isFieldInCol && (!cacheItem.getChildItemhasField() && cacheItem.getChildItems() != null && cacheItem.getChildItems().isEmpty() || cacheItem.getChildItemhasField() || cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW))) {
            this.loadMixData(parentItem, cacheItem, isLastDimAndSumFirst, curDepth, "row");
        }
        if (cacheItem.getChildItems() != null && cacheItem.getChildItems().size() > 0) {
            ++depth;
            for (int j = 0; j < cacheItem.getChildItems().size(); ++j) {
                QueryDimItem childItem = cacheItem.getChildItems().get(j);
                int childSize = this.getItemTotalSize(childItem);
                if (childSize <= 0 && childItem.getOwnDataSize() <= 0 && childItem.getChildDataSize() <= 0 && !childItem.getItemType().equals((Object)DimensionItemType.FIELD) && !childItem.getChildHasDetailRow() && (!this.defina.gridType.equals((Object)GridType.RAC) || !childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) || (this.defina.gridType != GridType.RAC || this.drawCache.fieldIsDown || !cacheItem.getItemType().equals((Object)DimensionItemType.FIELD)) && childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && !isHasSum || this.defina.gridType.equals((Object)GridType.RAC) && childItem.getItemType().equals((Object)DimensionItemType.FIELD) && childItem.getIsMaster()) continue;
                if (!cacheItem.getIsSubTotalInFront() && isHasSum && isInsertDetial || !cacheItem.getIsNotTreeStruct() && !childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && (this.defina.block.getBlockInfo().getDimLevelShowType() == 1 || this.defina.block.getBlockInfo().getDimLevelShowType() == -1)) {
                    needNewRow = true;
                }
                if (cacheItem.hasWriteTotal() && !this.defina.isTreeLoad) {
                    needNewRow = false;
                }
                curDepth = this.getCurDepth(childItem, depth);
                this.loopDimItemDrawRowFormGrid(cacheItem, childItem, curDepth, needNewRow);
                needNewRow = true;
                if (!this.drawCache.fieldIsDown && !this.defina.isFieldInCol && cacheItem.getChildItems().size() == 1 && isHasSum && childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
                    this.drawCache.insertRowIndex(curDepth, "row");
                }
                if (this.defina.gridType == GridType.RAC || !childItem.getItemType().equals((Object)DimensionItemType.FIELD) || !this.drawCache.fieldIsDown) continue;
                this.loadData(cacheItem, childItem, datas, isLastDimAndSumFirst, curDepth, "row");
            }
        } else if (!cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || !this.defina.isFieldInCol) {
            // empty if block
        }
        if (cacheItem.getIsNotTreeStruct() && isLastDimAndSumFirst && this.defina.gridType != GridType.RAC) {
            this.loadData(cacheItem, cacheItem, datas, isLastDimAndSumFirst, curDepth, "row");
        }
        if (cacheItem.getIsNotTreeStruct() || !cacheItem.getIsNotTreeStruct() && this.defina.block.getBlockInfo().getDimLevelShowType() == 2) {
            boolean mergeNullRowDim;
            boolean isNotLastDim;
            if (startRow != this.drawCache.getRowIndex(curDepth)) {
                this.grid.mergeCells(startCol, startRow, startCol, this.drawCache.getRowIndex(curDepth));
            }
            boolean bl4 = isNotLastDim = parentItem == null || parentItem.getChildItems().size() > 1;
            if (this.drawCache.getFieldIndex() > 0 && isHasSum && isNotLastDim && startCol + 1 < this.drawCache.getFieldIndex() - 1) {
                this.grid.mergeCells(startCol + 1, startRow, this.drawCache.getFieldIndex() - 1, startRow);
            }
            boolean bl5 = mergeNullRowDim = this.defina.gridType == GridType.ROW && !isHasSum && this.drawCache.bolckShowDetial && cacheItem.isLastDimension() && (cacheItem.totalSize == 0 || cacheItem.totalSize == 1) && !cacheItem.getIsShowSubTotal();
            if (!(!mergeNullRowDim && (cacheItem.getOwnDataSize() != 1 && !cacheItem.getChildHasDetailRow() || isHasSum || cacheItem.getChildItems().size() != 1 || startCol >= this.drawCache.getFieldIndex() - 1) || this.defina.isCustomInput && !mergeNullRowDim)) {
                this.grid.mergeCells(startCol, startRow, this.drawCache.getFieldIndex() - 1, startRow);
            }
        }
    }

    public void loopSimpleDimItemDrawRowFormGrid(QueryDimItem parentItem, QueryDimItem cacheItem, int depth, boolean needNewRow) throws Exception {
        ArrayList<IDataRow> datas = new ArrayList();
        int curDepth = this.getCurDepth(cacheItem, depth);
        if (cacheItem.getDetailRows() != null && cacheItem.getDetailRows().size() > 0 && this.drawCache.bolckShowDetial) {
            datas = cacheItem.getDetailRows();
        } else if (this.defina.block.isShowNullRow() && this.defina.isFieldInCol) {
            int fieldCount = this.defina.block.getQueryDimensions().get(0).getSelectFields().size();
            ArrayList<String> rowDatas = new ArrayList<String>();
            rowDatas.add(cacheItem.getEditTitle());
            for (int x = 0; x < fieldCount - 1; ++x) {
                rowDatas.add(null);
            }
            DataRowImpl nullDetailRow = new DataRowImpl((ReadonlyTableImpl)this.defina.dataTable, cacheItem.getDimValueSet(), rowDatas);
            datas.add((IDataRow)nullDetailRow);
            this.defina.block.getBlockInfo().setTotalCount(this.defina.block.getBlockInfo().getTotalCount() + 1);
        }
        if (needNewRow) {
            this.drawCache.insertRowIndex(curDepth, "row");
            needNewRow = false;
        }
        if (!datas.isEmpty()) {
            this.loadData(null, null, datas, false, curDepth, "row");
        }
    }

    private DimensionValueSet parseString(String dimValue) {
        DimensionValueSet dimValueSet = new DimensionValueSet();
        if (dimValue == null) {
            return dimValueSet;
        }
        StringTokenizer st = new StringTokenizer(dimValue, ";,");
        while (st.hasMoreTokens()) {
            String data = st.nextToken().trim();
            int p = data.indexOf(61);
            if (p <= 0) continue;
            String dimension = data.substring(0, p);
            Object value = null;
            value = data.length() >= 32 ? UUID.fromString(data.substring(p + 1).trim()) : DimensionValueSet.deserialize((String)data.substring(p + 1).trim());
            dimValueSet.setValue(dimension, value);
        }
        return dimValueSet;
    }

    private void loadMixData(QueryDimItem cacheItem, QueryDimItem childItem, boolean isLastDimAndSumFirst, int curDepth, String type) throws Exception {
        int row = 0;
        int col = 0;
        int nextStartNum = 0;
        QuerySelectField preField = null;
        ArrayList<Integer> fieldIndex = new ArrayList<Integer>();
        LinkedHashMap<Integer, Double> definaSum = new LinkedHashMap();
        List<Object> showedFields = new ArrayList();
        if (this.drawCache.fieldIsDown) {
            showedFields = this.defina.showedFields;
        }
        if (childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && this.defina.block.getFieldPosition() == QueryFieldPosition.UP && !this.defina.isFieldInCol) {
            col = this.drawCache.getColIndex();
            ArrayList rowList = new ArrayList();
            this.drawCache.rowOrColToSubs.put(col, rowList);
        }
        LinkedHashMap<String, QueryDimensionDefine> dimCache = new LinkedHashMap<String, QueryDimensionDefine>();
        for (int i = 0; i < this.defina.block.getQueryDimensions().size(); ++i) {
            QueryDimensionDefine dimf = this.defina.block.getQueryDimensions().get(i);
            if (!dimf.getDimensionType().equals((Object)QueryDimensionType.QDT_DICTIONARY) && !dimf.getDimensionType().equals((Object)QueryDimensionType.QDT_ENTITY) && !dimf.getDimensionType().equals((Object)QueryDimensionType.QDT_GRIDFIELD)) continue;
            dimCache.put(dimf.getDimensionName(), dimf);
        }
        boolean dimIsInCol = ((QueryDimensionDefine)dimCache.get(childItem.getDimensionName())).getLayoutType().equals((Object)QueryLayoutType.LYT_COL);
        block1: for (int colOrRow : this.drawCache.mixedGridMes.keySet()) {
            String cacheValue;
            DimensionValueSet curValue;
            QueryDimItem item = this.drawCache.mixedGridMes.get(colOrRow);
            if (item.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && !this.defina.mixShowDetail) {
                if (this.defina.isFieldInCol) {
                    col = this.drawCache.getFieldIndex() + colOrRow - 1;
                    row = this.drawCache.getRowIndex(curDepth);
                } else {
                    col = this.drawCache.getColIndex();
                    row = this.drawCache.getValueIndex() + colOrRow - 1;
                }
                if (childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
                    List<Object> rowList = new ArrayList<Integer>();
                    if (this.drawCache.rowOrColToSubs.containsKey(col)) {
                        rowList = this.drawCache.rowOrColToSubs.get(col);
                        rowList.add(row);
                    } else {
                        rowList.add(row);
                        this.drawCache.rowOrColToSubs.put(col, rowList);
                    }
                    this.drawCache.subIndexAndFaDim.put(row, cacheItem);
                }
                curValue = new DimensionValueSet();
                ArrayList<QueryDimItem> itemList = new ArrayList<QueryDimItem>();
                cacheValue = childItem.getDimensionValueSet();
                if (cacheValue != null) {
                    curValue = this.parseString(cacheValue);
                }
                if (this.drawCache.fieldIsDown) {
                    for (int a : this.drawCache.mixedGridMes.keySet()) {
                        if (a == colOrRow) continue;
                        QueryDimItem b = this.drawCache.mixedGridMes.get(a);
                        itemList.add(b);
                    }
                    for (int s = 0; s < this.defina.showedFields.size(); ++s) {
                        if (Boolean.parseBoolean(this.defina.showedFields.get(s).getIsMaster())) continue;
                        fieldIndex.add(this.defina.fieldIndex.get(this.defina.showedFields.get(s).getCode()));
                    }
                } else {
                    for (int a : this.drawCache.mixedGridMes.keySet()) {
                        if (a == colOrRow || a < colOrRow) continue;
                        if (this.drawCache.indexWithField.containsKey(a)) break;
                        QueryDimItem b = this.drawCache.mixedGridMes.get(a);
                        itemList.add(b);
                    }
                    preField = this.drawCache.indexWithField.get(colOrRow);
                    fieldIndex.add(this.defina.fieldIndex.get(preField.getCode()));
                }
                definaSum = this.defina.getSubTotalRowNew(curValue, fieldIndex, item, itemList, dimIsInCol, preField);
                this.initMixData(definaSum, col, row);
                continue;
            }
            cacheValue = childItem.getDimensionValueSet();
            curValue = cacheValue != null ? this.parseString(cacheValue) : new DimensionValueSet();
            DimensionValueSet dimValue = item.getDimensionValueSet() != null ? this.parseString(item.getDimensionValueSet()) : new DimensionValueSet();
            curValue.combine(dimValue);
            IDataRow composeData = null;
            if (this.defina.dataTable != null) {
                if (this.defina.mixShowDetail) {
                    List racDetail;
                    List list = racDetail = childItem.getItemType() == DimensionItemType.SUBTOTALROW ? null : this.defina.dataTable.findDetailRowsByGroupKey(curValue);
                    if (null != racDetail && racDetail.size() > 0) {
                        composeData = (IDataRow)racDetail.get(0);
                    }
                } else {
                    composeData = childItem.getItemType() == DimensionItemType.SUBTOTALROW ? null : this.defina.dataTable.findGroupingRow(curValue);
                }
            }
            for (int i = 0; i < this.drawCache.colOrRowIndex.size(); ++i) {
                int curIndex = this.drawCache.colOrRowIndex.get(i);
                if (colOrRow != curIndex) continue;
                nextStartNum = i + 1 < this.drawCache.colOrRowIndex.size() ? this.drawCache.colOrRowIndex.get(i + 1) : (this.defina.isFieldInCol ? this.grid.getColumnCount() - this.drawCache.getFieldIndex() + 1 : this.grid.getRowCount() - this.drawCache.getValueIndex() + 1);
                for (int j = colOrRow; j < nextStartNum; ++j) {
                    if (!this.drawCache.fieldIsDown) {
                        if (type == "row") {
                            col = this.drawCache.getFieldIndex() + j - 1;
                            row = this.drawCache.getRowIndex(curDepth);
                            this.setRowLeveIndex(childItem, row);
                        } else {
                            col = this.drawCache.getColIndex();
                            row = this.drawCache.getValueIndex() + j - 1;
                            this.setColLeveIndex(childItem, col);
                        }
                        if (this.drawCache.indexWithField.containsKey(j)) {
                            preField = new QuerySelectField();
                            preField = this.drawCache.indexWithField.get(j);
                        }
                        boolean isMaster = Boolean.parseBoolean(preField.getIsMaster());
                        this.initRowData(childItem, preField == null ? new QuerySelectField() : preField, composeData, col, row, isMaster, 0);
                        continue;
                    }
                    int needSubStract = 0;
                    for (int k = 0; k < this.defina.showedFields.size(); ++k) {
                        preField = new QuerySelectField();
                        preField = this.defina.showedFields.get(k);
                        boolean isMaster = Boolean.parseBoolean(preField.getIsMaster());
                        if (isMaster) {
                            ++needSubStract;
                            continue;
                        }
                        if (type == "row") {
                            col = this.drawCache.getFieldIndex() + colOrRow - 1 + k - needSubStract;
                            row = this.drawCache.getRowIndex(curDepth);
                            this.setRowLeveIndex(childItem, row);
                        } else {
                            col = this.drawCache.getColIndex();
                            row = this.drawCache.getValueIndex() + colOrRow - 1 + k - needSubStract;
                            this.setColLeveIndex(childItem, col);
                        }
                        this.initRowData(childItem, preField, composeData, col, row, isMaster, 0);
                    }
                }
                continue block1;
            }
        }
    }

    private double getLevelSubTotal(List<QueryDimItem> cacheItems, QueryDimItem curItem, List<Integer> fieldIndex, QuerySelectField preField) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        double total = 0.0;
        int depth = 0;
        try {
            QueryDimItem cacheItem;
            int i;
            for (i = 0; i < cacheItems.size(); ++i) {
                cacheItem = cacheItems.get(i);
                if (cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) continue;
                int curDepth = cacheItem.getDepth();
                if (curDepth > depth) {
                    depth = curDepth;
                }
                if (cacheItem.getDepth() != curItem.getDepth()) continue;
                map.put(cacheItem.getEditTitle(), i);
            }
            for (i = ((Integer)map.get(curItem.getEditTitle())).intValue(); i < cacheItems.size(); ++i) {
                cacheItem = cacheItems.get(i);
                if (cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) continue;
                if (cacheItem.getDepth() != curItem.getDepth() || cacheItem.getEditTitle().equals(curItem.getEditTitle())) {
                    if (cacheItem.getDetailRows() == null || cacheItem.getDetailRows().isEmpty()) continue;
                    for (IDataRow row : cacheItem.getDetailRows()) {
                        int curFieldIndex = this.defina.fieldIndex.get(preField.getCode());
                        for (Integer index : fieldIndex) {
                            AbstractData value;
                            if (curFieldIndex != index || (value = row.getValue(index.intValue())) == null || value.dataType != 10 && value.dataType != 3 && value.dataType != 5) continue;
                            total += value.getAsFloat();
                        }
                    }
                    continue;
                }
                break;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return total;
    }

    private void setRowLeveIndex(QueryDimItem childItem, int row) {
        List<Object> firstColIndex;
        if (!childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && !(firstColIndex = this.drawCache.rowLeveIndex.get(childItem.getDepth()) != null ? this.drawCache.rowLeveIndex.get(childItem.getDepth()) : new ArrayList()).contains(row)) {
            firstColIndex.add(row);
            this.drawCache.rowLeveIndex.put(childItem.getDepth(), firstColIndex);
        }
    }

    private void setColLeveIndex(QueryDimItem childItem, int col) {
        List<Object> firstColIndex;
        if (!childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) && !(firstColIndex = this.drawCache.colLeveIndex.get(childItem.getDepth()) != null ? this.drawCache.colLeveIndex.get(childItem.getDepth()) : new ArrayList()).contains(col)) {
            firstColIndex.add(col);
            this.drawCache.colLeveIndex.put(childItem.getDepth(), firstColIndex);
        }
    }

    private void initMixData(Map<Integer, Double> definaSum, int col, int row) {
        if (definaSum.size() != 0) {
            for (int i : definaSum.keySet()) {
                GridCellData cell = this.grid.getGridCellData(col, row);
                cell.setHorzAlign(2);
                cell.setBottomBorderStyle(1);
                cell.setRightBorderStyle(1);
                cell.setBackGroundColor(0xFFFFFF);
                cell.setSelectable(true);
                cell.setEditable(false);
                cell.setSilverHead(false);
                double value = definaSum.get(i);
                BigDecimal bd2 = new BigDecimal(value);
                cell.setShowText(bd2.setScale(2, 6).toPlainString());
            }
        } else {
            GridCellData cell = this.grid.getGridCellData(col, row);
            cell.setHorzAlign(2);
            cell.setBottomBorderStyle(1);
            cell.setRightBorderStyle(1);
            cell.setBackGroundColor(0xFFFFFF);
            cell.setSelectable(true);
            cell.setEditable(false);
            cell.setSilverHead(false);
            cell.setShowText("");
        }
    }

    private double getLevelSubTotalRow(QueryDimItem curItem, List<Integer> fieldIndex, QuerySelectField preField, int curRowIndex) {
        if (curItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
            return 0.0;
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int end = 0;
        double total = 0.0;
        int depth = 0;
        int nextStartNum = this.drawCache.mixedGridMes.keySet().size();
        try {
            int i;
            for (i = curRowIndex - this.grid.getHeaderRowCount() + 2; i < nextStartNum; ++i) {
                QueryDimItem dimItem;
                if (this.drawCache.mixedGridMes.containsKey(i)) {
                    // empty if block
                }
                if ((dimItem = this.drawCache.mixedGridMes.get(i)).getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) continue;
                int curDepth = dimItem.getDepth();
                if (curDepth > depth) {
                    depth = curDepth;
                }
                if (dimItem.getDepth() == curItem.getDepth()) {
                    if (curRowIndex != i) {
                        end = i + this.grid.getHeaderRowCount() - 1;
                        break;
                    }
                    map.put(dimItem.getEditTitle(), i + this.grid.getHeaderRowCount() - 1);
                }
                if (i != this.drawCache.mixedGridMes.keySet().size() - 1) continue;
                end = i + this.grid.getHeaderRowCount();
            }
            for (i = ((Integer)map.get(curItem.getEditTitle())).intValue(); i <= end; ++i) {
                QueryDimItem cacheItem = this.drawCache.mixedGridMes.get(i - this.grid.getHeaderRowCount() + 1);
                if (cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) continue;
                if (cacheItem.getDepth() != curItem.getDepth() || cacheItem.getEditTitle().equals(curItem.getEditTitle())) {
                    if (cacheItem.getDetailRows() == null || cacheItem.getDetailRows().isEmpty()) continue;
                    for (IDataRow row : cacheItem.getDetailRows()) {
                        int curFieldIndex = this.defina.fieldIndex.get(preField.getCode());
                        for (Integer index : fieldIndex) {
                            AbstractData value;
                            if (curFieldIndex != index || (value = row.getValue(index.intValue())) == null || value.dataType != 10 && value.dataType != 3 && value.dataType != 5) continue;
                            total += value.getAsFloat();
                        }
                    }
                    continue;
                }
                break;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return total;
    }

    private double getLevelSubTotalRowFieldIsDown(QueryDimItem curItem, List<Integer> fieldIndex, QuerySelectField preField, int curRowIndex, int colIndex) {
        if (curItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
            return 0.0;
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int end = 0;
        double total = 0.0;
        int depth = 0;
        int nextStartNum = this.grid.getRowCount();
        try {
            for (int i = curRowIndex - this.grid.getHeaderRowCount() + 2; i < nextStartNum; ++i) {
                QueryDimItem dimItem;
                if (!this.drawCache.mixedGridMes.containsKey(i) || (dimItem = this.drawCache.mixedGridMes.get(i)) == null || dimItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) continue;
                int curDepth = dimItem.getDepth();
                if (curDepth > depth) {
                    depth = curDepth;
                }
                if (dimItem.getDepth() <= curItem.getDepth()) {
                    if (curRowIndex != i) {
                        end = i + this.grid.getHeaderRowCount() - 1;
                        break;
                    }
                    map.put(dimItem.getEditTitle(), i + this.grid.getHeaderRowCount() - 1);
                }
                if (i != nextStartNum - this.grid.getHeaderRowCount() + 1 - this.defina.showedFields.size()) continue;
                end = i + this.grid.getHeaderRowCount() - 1 + this.defina.showedFields.size();
            }
            QuerySelectItem field = null;
            for (int i = ((Integer)map.get(curItem.getEditTitle())).intValue(); i < end; ++i) {
                GridCellData cell;
                if (this.drawCache.fieldInIndex.containsKey(i - this.grid.getHeaderRowCount() + 1)) {
                    field = this.drawCache.fieldInIndex.get(i - this.grid.getHeaderRowCount() + 1);
                }
                if (!field.getCode().equals(preField.getCode()) || (cell = this.grid.getGridCellData(colIndex, i)).getShowText() == null || cell.getShowText().replace(",", "") == null || "".equals(cell.getShowText().trim())) continue;
                total += Double.parseDouble(cell.getShowText().replace(",", ""));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return total;
    }

    private double getLevelSubTotalRowFieldIsUp(QueryDimItem curItem, List<Integer> fieldIndex, QuerySelectField preField, int curRowIndex, int colIndex) {
        if (curItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
            return 0.0;
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int end = 0;
        double total = 0.0;
        int depth = 0;
        int nextStartNum = this.grid.getRowCount();
        try {
            int i;
            for (i = curRowIndex; i < nextStartNum - this.grid.getHeaderRowCount() + 1; ++i) {
                if (!this.drawCache.mixedGridMes.containsKey(i)) continue;
                QueryDimItem dimItem = this.drawCache.mixedGridMes.get(i);
                if (dimItem == null || dimItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
                    end = i + this.grid.getHeaderRowCount() - 1;
                    break;
                }
                int curDepth = dimItem.getDepth();
                if (curDepth > depth) {
                    depth = curDepth;
                }
                if (dimItem.getDepth() <= curItem.getDepth()) {
                    if (curRowIndex != i) {
                        end = i + this.grid.getHeaderRowCount() - 1;
                        break;
                    }
                    map.put(dimItem.getEditTitle(), i + this.grid.getHeaderRowCount() - 1);
                }
                if (i + 1 != nextStartNum - this.grid.getHeaderRowCount() + 1) continue;
                end = nextStartNum;
            }
            for (i = ((Integer)map.get(curItem.getEditTitle())).intValue(); i < end; ++i) {
                GridCellData cell = this.grid.getGridCellData(colIndex, i);
                if (cell.getShowText() == null || cell.getShowText().replace(",", "") == null || "".equals(cell.getShowText())) continue;
                total += Double.parseDouble(cell.getShowText().replace(",", ""));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return total;
    }

    private double getLevelSubTotalRowFieldIsDownOrUpAndFieldInCol(QueryDimItem curItem, int curRowIndex, int colIndex) {
        if (curItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
            return 0.0;
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int end = 0;
        double total = 0.0;
        int depth = 0;
        int nextStartNum = this.grid.getRowCount();
        try {
            int i;
            for (i = curRowIndex; i < nextStartNum; ++i) {
                QueryDimItem dimItem;
                if (!this.drawCache.mixedNoFieldGridMes.containsKey(i) || (dimItem = this.drawCache.mixedNoFieldGridMes.get(i)) == null || dimItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) continue;
                int curDepth = dimItem.getDepth();
                if (curDepth > depth) {
                    depth = curDepth;
                }
                if (dimItem.getDepth() == curItem.getDepth()) {
                    if (curRowIndex != i) {
                        end = i;
                        break;
                    }
                    map.put(dimItem.getEditTitle(), i);
                }
                if (i != nextStartNum - 1) continue;
                end = i + 1;
            }
            for (i = ((Integer)map.get(curItem.getEditTitle())).intValue(); i < end; ++i) {
                GridCellData cell = this.grid.getGridCellData(colIndex, i);
                if (cell.getShowText() == null || cell.getShowText().replace(",", "") == null || " ".equals(cell.getShowText()) || "".equals(cell.getShowText())) continue;
                total += Double.parseDouble(cell.getShowText().replace(",", ""));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return total;
    }

    private double getLevelSubTotalCol(QueryDimItem curItem, List<Integer> fieldIndex, QuerySelectField preField, int colIndex) {
        if (curItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
            return 0.0;
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int end = 0;
        double total = 0.0;
        int depth = 0;
        int deviation = 2;
        try {
            int i;
            for (i = colIndex - this.grid.getHeaderColumnCount() + deviation; i < this.drawCache.mixedGridMes.keySet().size(); ++i) {
                QueryDimItem dimItem = this.drawCache.mixedGridMes.get(i);
                if (dimItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) continue;
                int curDepth = dimItem.getDepth();
                if (curDepth > depth) {
                    depth = curDepth;
                }
                if (dimItem.getDepth() == curItem.getDepth()) {
                    if (colIndex != i) {
                        end = i + this.grid.getHeaderRowCount() - 1;
                        break;
                    }
                    map.put(dimItem.getEditTitle(), i + this.grid.getHeaderRowCount() - 1);
                }
                if (i != this.drawCache.mixedGridMes.keySet().size() - 1) continue;
                end = i + this.grid.getHeaderRowCount();
            }
            for (i = ((Integer)map.get(curItem.getEditTitle())).intValue(); i <= end; ++i) {
                QueryDimItem cacheItem = this.drawCache.mixedGridMes.get(i - this.grid.getHeaderRowCount() + 1);
                if (cacheItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) continue;
                if (cacheItem.getDepth() != curItem.getDepth() || cacheItem.getEditTitle().equals(curItem.getEditTitle())) {
                    if (cacheItem.getDetailRows() == null || cacheItem.getDetailRows().isEmpty()) continue;
                    for (IDataRow row : cacheItem.getDetailRows()) {
                        int curFieldIndex = this.defina.fieldIndex.get(preField.getCode());
                        for (Integer index : fieldIndex) {
                            AbstractData value;
                            if (curFieldIndex != index || (value = row.getValue(index.intValue())) == null || value.dataType != 10 && value.dataType != 3 && value.dataType != 5) continue;
                            total += value.getAsFloat();
                        }
                    }
                    continue;
                }
                break;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return total;
    }

    private double getLevelSubTotalColFieldIsDownAndFieldInRow(QueryDimItem curItem, List<Integer> fieldIndex, QuerySelectField preField, int colIndex, int rowIndex) {
        if (curItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
            return 0.0;
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int end = 0;
        double total = 0.0;
        int depth = 0;
        int nextStartNum = this.grid.getColumnCount();
        try {
            int i;
            for (i = colIndex; i < nextStartNum; ++i) {
                QueryDimItem dimItem;
                if (!this.drawCache.mixedNoFieldGridMes.containsKey(i) || (dimItem = this.drawCache.mixedNoFieldGridMes.get(i)) == null || dimItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) continue;
                int curDepth = dimItem.getDepth();
                if (curDepth > depth) {
                    depth = curDepth;
                }
                if (dimItem.getDepth() == curItem.getDepth()) {
                    if (colIndex != i) {
                        end = i;
                        break;
                    }
                    map.put(dimItem.getEditTitle(), i);
                }
                if (i != nextStartNum - 1) continue;
                end = i + 1;
            }
            for (i = ((Integer)map.get(curItem.getEditTitle())).intValue(); i < end; ++i) {
                GridCellData cell = this.grid.getGridCellData(i, rowIndex);
                if (cell.getShowText() == null || cell.getShowText().replace(",", "") == null || "".equals(cell.getShowText())) continue;
                total += Double.parseDouble(cell.getShowText().replace(",", ""));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return total;
    }

    private double getLevelSubTotalColFieldIsDown(QueryDimItem curItem, QuerySelectField preField, int colIndex, int rowIndex) {
        if (curItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
            return 0.0;
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int end = 0;
        double total = 0.0;
        int depth = 0;
        int nextStartNum = this.grid.getColumnCount();
        try {
            int i;
            for (i = colIndex - this.grid.getHeaderColumnCount() + 2; i < nextStartNum; ++i) {
                QueryDimItem dimItem;
                if (!this.drawCache.mixedGridMes.containsKey(i) || (dimItem = this.drawCache.mixedGridMes.get(i)) == null || dimItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) continue;
                int curDepth = dimItem.getDepth();
                if (curDepth > depth) {
                    depth = curDepth;
                }
                if (dimItem.getDepth() == curItem.getDepth()) {
                    if (colIndex != i) {
                        end = i + this.grid.getHeaderColumnCount() - 1;
                        break;
                    }
                    map.put(dimItem.getEditTitle(), i + this.grid.getHeaderColumnCount() - 1);
                }
                if (i != nextStartNum - this.grid.getHeaderColumnCount() + 1 - this.defina.showedFields.size()) continue;
                end = i + this.grid.getHeaderColumnCount() - 1 + this.defina.showedFields.size();
            }
            for (i = ((Integer)map.get(curItem.getEditTitle())).intValue(); i < end; ++i) {
                GridCellData cell;
                QuerySelectField field = this.drawCache.fieldInIndex.get(i - this.grid.getHeaderColumnCount() + 1);
                if (!field.getCode().equals(preField.getCode()) || (cell = this.grid.getGridCellData(i, rowIndex)).getShowText() == null || cell.getShowText().replace(",", "") == null || "".equals(cell.getShowText())) continue;
                total += Double.parseDouble(cell.getShowText().replace(",", ""));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return total;
    }

    private double getLevelSubTotalColFieldIsUpAndFieldInCol(QueryDimItem curItem, QuerySelectField preField, int colIndex, int rowIndex) {
        if (curItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
            return 0.0;
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int end = 0;
        double total = 0.0;
        int depth = 0;
        int nextStartNum = this.grid.getColumnCount();
        try {
            int i;
            for (i = colIndex; i < nextStartNum - this.grid.getHeaderColumnCount() + 1; ++i) {
                if (!this.drawCache.mixedGridMes.containsKey(i)) continue;
                QueryDimItem dimItem = this.drawCache.mixedGridMes.get(i);
                if (dimItem == null || dimItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
                    end = i + this.grid.getHeaderColumnCount() - 1;
                    break;
                }
                int curDepth = dimItem.getDepth();
                if (curDepth > depth) {
                    depth = curDepth;
                }
                if (dimItem.getDepth() <= curItem.getDepth()) {
                    if (colIndex != i) {
                        end = i + this.grid.getHeaderColumnCount() - 1;
                        break;
                    }
                    map.put(dimItem.getEditTitle(), i + this.grid.getHeaderColumnCount() - 1);
                }
                if (i + 1 != nextStartNum - this.grid.getHeaderColumnCount() + 1) continue;
                end = i + 1 + this.grid.getHeaderColumnCount() - 1;
            }
            for (i = ((Integer)map.get(curItem.getEditTitle())).intValue(); i < end; ++i) {
                GridCellData cell = this.grid.getGridCellData(i, rowIndex);
                if (cell.getShowText() == null || cell.getShowText().replace(",", "") == null || "".equals(cell.getShowText())) continue;
                total += Double.parseDouble(cell.getShowText().replace(",", ""));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return total;
    }

    private void initMixSubDataNotGather() {
        for (int key : this.drawCache.rowOrColToSubs.keySet()) {
            List<Integer> rowList = this.drawCache.rowOrColToSubs.get(key);
            for (int i = 0; i < rowList.size(); ++i) {
                DecimalFormat df = new DecimalFormat("0.0#");
                int startRow = rowList.get(i);
                int endCol = this.grid.getColumnCount() - 1;
                int endRow = this.grid.getRowCount() - 1;
                QueryDimItem item = this.drawCache.subIndexAndFaDim.get(startRow);
                endRow = i + 1 < rowList.size() && item != null ? rowList.get(i + 1) - 1 : this.grid.getRowCount() - 1;
                if (item != null) continue;
                for (int colStart = key + 1; colStart <= endCol; ++colStart) {
                    Double total = 0.0;
                    GridCellData subCell = this.grid.getGridCellData(colStart, startRow);
                    for (int start = startRow + 1; start <= endRow; ++start) {
                        GridCellData curCell = this.grid.getGridCellData(colStart, start);
                        QuerySelectField field = this.drawCache.fieldInIndex.get(colStart - this.grid.getHeaderColumnCount() + 1);
                        if (!QueryHelper.isNumField(field) || null == curCell.getShowText() || "".equals(curCell.getShowText())) continue;
                        String numberText = curCell.getShowText().replaceAll(",", "");
                        total = total + Double.parseDouble(numberText);
                        total = Double.parseDouble(df.format(total));
                    }
                    BigDecimal bd2 = new BigDecimal(total);
                    subCell.setShowText(bd2.setScale(2, 6).toPlainString());
                    subCell.setHorzAlign(2);
                }
            }
        }
    }

    private void initMixSubData() {
        for (int key : this.drawCache.rowOrColToSubs.keySet()) {
            List<Integer> rowList = this.drawCache.rowOrColToSubs.get(key);
            if (rowList.isEmpty() && this.defina.block.getFieldPosition() == QueryFieldPosition.UP && !this.defina.isFieldInCol) {
                GridCellData curCell;
                int dataStartRow;
                Object curCell2;
                QuerySelectField field = new QuerySelectField();
                for (int fIndex : this.drawCache.fieldInIndex.keySet()) {
                    int curFieldIndex = this.defina.fieldIndex.get(this.drawCache.fieldInIndex.get(fIndex).getCode());
                    if (this.drawCache.fieldIndexList.contains(curFieldIndex)) continue;
                    this.drawCache.fieldIndexList.add(curFieldIndex);
                }
                for (int colStart = key + 1; colStart < this.grid.getColumnCount(); ++colStart) {
                    for (int rowIndex : this.drawCache.mixedGridMes.keySet()) {
                        if (this.drawCache.fieldInIndex.containsKey(rowIndex)) {
                            field = this.drawCache.fieldInIndex.get(rowIndex);
                        }
                        QueryDimItem dimItem = this.drawCache.mixedGridMes.get(rowIndex);
                        curCell2 = this.grid.getGridCellData(colStart, rowIndex + this.grid.getHeaderRowCount() - 1);
                        if (curCell2.getShowText() != null && !"".equals(curCell2.getShowText())) continue;
                        double total = this.getLevelSubTotalRow(dimItem, this.drawCache.fieldIndexList, field, rowIndex);
                        curCell2.setShowText(Double.toString(total));
                        curCell2.setHorzAlign(2);
                    }
                }
                if (!this.drawCache.colLeveIndex.isEmpty() && this.drawCache.colLeveIndex.keySet().size() > 1 && this.drawCache.colLeveIndex.get(0) != null) {
                    for (dataStartRow = this.drawCache.getValueIndex(); dataStartRow < this.grid.getRowCount(); ++dataStartRow) {
                        double total = 0.0;
                        curCell = this.grid.getGridCellData(key, dataStartRow);
                        curCell2 = this.drawCache.colLeveIndex.get(0).iterator();
                        while (curCell2.hasNext()) {
                            int colIndex = (Integer)curCell2.next();
                            GridCellData childCell = this.grid.getGridCellData(colIndex, dataStartRow);
                            if (StringUtils.isEmpty((String)childCell.getShowText())) continue;
                            total += Double.parseDouble(childCell.getShowText().replace(",", ""));
                        }
                        curCell.setShowText(Double.toString(total));
                        curCell.setHorzAlign(2);
                        curCell.setSelectable(true);
                        curCell.setEditable(false);
                    }
                } else {
                    for (dataStartRow = this.drawCache.getValueIndex(); dataStartRow < this.grid.getRowCount(); ++dataStartRow) {
                        double total = 0.0;
                        curCell = this.grid.getGridCellData(key, dataStartRow);
                        for (int j = key + 1; j <= this.grid.getColumnCount() - 1; ++j) {
                            GridCellData childCell = this.grid.getGridCellData(j, dataStartRow);
                            if (StringUtils.isEmpty((String)childCell.getShowText())) continue;
                            total += Double.parseDouble(childCell.getShowText().replace(",", ""));
                        }
                        curCell.setShowText(Double.toString(total));
                        curCell.setHorzAlign(2);
                        curCell.setSelectable(true);
                        curCell.setEditable(false);
                    }
                }
                break;
            }
            for (int i = 0; i < rowList.size(); ++i) {
                int startRow = rowList.get(i);
                GridCellData curCell = this.grid.getGridCellData(key, startRow);
                QueryDimItem item = this.drawCache.subIndexAndFaDim.get(startRow);
                int endRow = 0;
                endRow = i + 1 < rowList.size() && item != null ? rowList.get(i + 1) - 1 : this.grid.getRowCount() - 1;
                double total = 0.0;
                if (item == null) {
                    this.initMixNotSubData(key, startRow, endRow, this.grid.getColumnCount() - 1, rowList);
                }
                if (this.drawCache.rowLeveIndex.keySet().size() > 1 && this.drawCache.rowLeveIndex.get(0) != null) {
                    for (int rowIndex : this.drawCache.rowLeveIndex.get(0)) {
                        GridCellData childCell = this.grid.getGridCellData(key, rowIndex);
                        if (StringUtils.isEmpty((String)childCell.getShowText().trim()) || StringUtils.isEmpty((String)childCell.getShowText().replaceAll(",", ""))) continue;
                        total += Double.parseDouble(childCell.getShowText().replaceAll(",", ""));
                    }
                } else if (!this.drawCache.colLeveIndex.isEmpty()) {
                    int rowSub = this.drawCache.rowOrColToSubs.get(key).get(i);
                    for (int colIndex : this.drawCache.colLeveIndex.get(0)) {
                        GridCellData childCell = this.grid.getGridCellData(colIndex, rowSub);
                        if (StringUtils.isEmpty((String)childCell.getShowText()) || StringUtils.isEmpty((String)childCell.getShowText().replaceAll(",", ""))) continue;
                        total += Double.parseDouble(childCell.getShowText().replaceAll(",", ""));
                    }
                } else {
                    for (int j = startRow + 1; j <= endRow; ++j) {
                        GridCellData childCell;
                        if (rowList.contains(j) || StringUtils.isEmpty((String)(childCell = this.grid.getGridCellData(key, j)).getShowText()) || StringUtils.isEmpty((String)childCell.getShowText().replaceAll(",", ""))) continue;
                        total += Double.parseDouble(childCell.getShowText().replaceAll(",", ""));
                    }
                }
                curCell.setShowText(Double.toString(total));
                curCell.setHorzAlign(2);
                curCell.setSelectable(true);
                curCell.setEditable(false);
            }
        }
    }

    private void initMixNotSubData(int key, int start, int end, int startRowOrCol, List<Integer> list) {
        int colStart;
        QueryDimItem dimItem;
        int rowStart;
        DecimalFormat df = new DecimalFormat("0.0#");
        if (this.defina.isFieldInCol) {
            int colStart2;
            QueryDimItem dimItem2;
            int rowStart2;
            QuerySelectField field = null;
            for (int fIndex : this.drawCache.fieldInIndex.keySet()) {
                int curFieldIndex;
                if (!this.defina.fieldIndex.containsKey(this.drawCache.fieldInIndex.get(fIndex).getCode()) || this.drawCache.fieldIndexList.contains(curFieldIndex = this.defina.fieldIndex.get(this.drawCache.fieldInIndex.get(fIndex).getCode()).intValue())) continue;
                this.drawCache.fieldIndexList.add(curFieldIndex);
            }
            if (this.defina.block.getFieldPosition() == QueryFieldPosition.DOWN) {
                if (this.defina.block.getBlockInfo().isTranspose()) {
                    for (rowStart2 = start; rowStart2 <= end; ++rowStart2) {
                        for (int colIndex : this.drawCache.mixedGridMes.keySet()) {
                            dimItem2 = this.drawCache.mixedGridMes.get(colIndex);
                            if (dimItem2.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) continue;
                            for (int i = 0; i < this.defina.showedFields.size(); ++i) {
                                field = this.drawCache.fieldInIndex.get(colIndex + i);
                                GridCellData curCell = this.grid.getGridCellData(colIndex + i + this.grid.getHeaderColumnCount() - 1, rowStart2);
                                if (curCell.getShowText() != null && !"".equals(curCell.getShowText()) && !"0.0".equals(curCell.getShowText())) continue;
                                double total = this.getLevelSubTotalColFieldIsDown(dimItem2, field, colIndex, rowStart2);
                                total = Double.parseDouble(df.format(total));
                                curCell.setShowText(Double.toString(total));
                                curCell.setHorzAlign(2);
                            }
                        }
                    }
                } else {
                    for (colStart2 = key; colStart2 <= startRowOrCol; ++colStart2) {
                        for (int rowIndex : this.drawCache.mixedNoFieldGridMes.keySet()) {
                            GridCellData curCell;
                            dimItem2 = this.drawCache.mixedNoFieldGridMes.get(rowIndex);
                            if (dimItem2.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || (curCell = this.grid.getGridCellData(colStart2, rowIndex)).getShowText() != null && !"".equals(curCell.getShowText()) && !"0.0".equals(curCell.getShowText()) || !"0.0".equals(curCell.getShowText()) && this.drawCache.fieldInIndex.get(colStart2 - this.drawCache.colIndex).getFiledType().equals((Object)FieldType.FIELD_TYPE_STRING)) continue;
                            double total = this.getLevelSubTotalRowFieldIsDownOrUpAndFieldInCol(dimItem2, rowIndex, colStart2);
                            total = Double.parseDouble(df.format(total));
                            curCell.setShowText(Double.toString(total));
                            curCell.setHorzAlign(2);
                        }
                    }
                }
            } else if (this.defina.block.getBlockInfo().isTranspose()) {
                for (rowStart2 = start; rowStart2 <= end; ++rowStart2) {
                    for (int colIndex : this.drawCache.mixedGridMes.keySet()) {
                        GridCellData curCell;
                        if (this.drawCache.fieldInIndex.containsKey(colIndex)) {
                            field = this.drawCache.fieldInIndex.get(colIndex);
                        }
                        if ((dimItem2 = this.drawCache.mixedGridMes.get(colIndex)).getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || (curCell = this.grid.getGridCellData(colIndex + this.grid.getHeaderColumnCount() - 1, rowStart2)).getShowText() != null && !"".equals(curCell.getShowText()) && !"0.0".equals(curCell.getShowText())) continue;
                        double total = this.getLevelSubTotalColFieldIsUpAndFieldInCol(dimItem2, field, colIndex, rowStart2);
                        total = Double.parseDouble(df.format(total));
                        curCell.setShowText(Double.toString(total));
                        curCell.setHorzAlign(2);
                    }
                }
            } else {
                for (colStart2 = key; colStart2 <= startRowOrCol; ++colStart2) {
                    for (int rowIndex : this.drawCache.mixedNoFieldGridMes.keySet()) {
                        GridCellData curCell;
                        dimItem2 = this.drawCache.mixedNoFieldGridMes.get(rowIndex);
                        if (dimItem2.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || (curCell = this.grid.getGridCellData(colStart2, rowIndex)).getShowText() != null && !"".equals(curCell.getShowText()) && !"0.0".equals(curCell.getShowText())) continue;
                        double total = this.getLevelSubTotalRowFieldIsDownOrUpAndFieldInCol(dimItem2, rowIndex, colStart2);
                        total = Double.parseDouble(df.format(total));
                        curCell.setShowText(Double.toString(total));
                        curCell.setHorzAlign(2);
                    }
                }
            }
            for (int k = key + 1; k <= startRowOrCol; ++k) {
                double total;
                if (this.drawCache.rowOrColToSubs.containsKey(k) || this.drawCache.fieldInIndex.get(k - this.drawCache.colIndex).getFiledType().equals((Object)FieldType.FIELD_TYPE_STRING)) continue;
                GridCellData curRightCell = null;
                curRightCell = this.grid.getGridCellData(k, start);
                if (this.drawCache.rowLeveIndex.keySet().size() > 1 && this.drawCache.rowLeveIndex.get(0) != null) {
                    total = 0.0;
                    for (Integer rowIndex : this.drawCache.rowLeveIndex.get(0)) {
                        GridCellData childCell = this.grid.getGridCellData(k, rowIndex.intValue());
                        if (StringUtils.isEmpty((String)childCell.getShowText()) || StringUtils.isEmpty((String)childCell.getShowText().replaceAll(",", "")) || " ".equals(childCell.getShowText())) continue;
                        total += Double.parseDouble(childCell.getShowText().replaceAll(",", ""));
                    }
                    curRightCell.setShowText(Double.toString(total));
                    curRightCell.setHorzAlign(2);
                    curRightCell.setSelectable(true);
                    curRightCell.setEditable(false);
                    continue;
                }
                total = 0.0;
                for (int j = start + 1; j <= end; ++j) {
                    if (list.contains(j)) continue;
                    GridCellData childCell = null;
                    childCell = this.grid.getGridCellData(k, j);
                    if (this.drawCache.fieldInIndex.get(k - this.drawCache.colIndex).getFiledType().equals((Object)FieldType.FIELD_TYPE_STRING) || StringUtils.isEmpty((String)childCell.getShowText()) || StringUtils.isEmpty((String)childCell.getShowText().replaceAll(",", ""))) continue;
                    total += Double.parseDouble(childCell.getShowText().replaceAll(",", ""));
                }
                total = Double.parseDouble(df.format(total));
                curRightCell.setShowText(Double.toString(total));
                curRightCell.setHorzAlign(2);
                curRightCell.setSelectable(true);
                curRightCell.setEditable(false);
            }
            return;
        }
        QuerySelectField field = new QuerySelectField();
        for (int fIndex : this.drawCache.fieldInIndex.keySet()) {
            int curFieldIndex;
            if (this.defina.fieldIndex == null || this.defina.fieldIndex.isEmpty() || this.drawCache.fieldIndexList.contains(curFieldIndex = this.defina.fieldIndex.get(this.drawCache.fieldInIndex.get(fIndex).getCode()).intValue())) continue;
            this.drawCache.fieldIndexList.add(curFieldIndex);
        }
        if (this.defina.block.getFieldPosition() == QueryFieldPosition.UP) {
            if (this.defina.block.getBlockInfo().isTranspose()) {
                for (rowStart = start; rowStart <= end; ++rowStart) {
                    for (int colIndex : this.drawCache.mixedNoFieldGridMes.keySet()) {
                        GridCellData curCell;
                        dimItem = this.drawCache.mixedNoFieldGridMes.get(colIndex);
                        if (dimItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || (curCell = this.grid.getGridCellData(colIndex, rowStart)).getShowText() != null && !"".equals(curCell.getShowText()) && !"0.0".equals(curCell.getShowText())) continue;
                        double total = this.getLevelSubTotalColFieldIsDownAndFieldInRow(dimItem, this.drawCache.fieldIndexList, field, colIndex, rowStart);
                        total = Double.parseDouble(df.format(total));
                        curCell.setShowText(Double.toString(total));
                        curCell.setHorzAlign(2);
                    }
                }
            } else {
                for (colStart = key; colStart <= startRowOrCol; ++colStart) {
                    for (int rowIndex : this.drawCache.mixedGridMes.keySet()) {
                        GridCellData curCell;
                        if (this.drawCache.fieldInIndex.containsKey(rowIndex)) {
                            field = this.drawCache.fieldInIndex.get(rowIndex);
                        }
                        if ((dimItem = this.drawCache.mixedGridMes.get(rowIndex)).getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || (curCell = this.grid.getGridCellData(colStart, rowIndex + this.grid.getHeaderRowCount() - 1)).getShowText() != null && !"".equals(curCell.getShowText()) && !"0.0".equals(curCell.getShowText())) continue;
                        double total = this.getLevelSubTotalRowFieldIsUp(dimItem, this.drawCache.fieldIndexList, field, rowIndex, colStart);
                        total = Double.parseDouble(df.format(total));
                        curCell.setShowText(Double.toString(total));
                        curCell.setHorzAlign(2);
                    }
                }
            }
        } else if (this.defina.block.getBlockInfo().isTranspose()) {
            for (rowStart = start; rowStart <= end; ++rowStart) {
                for (int colIndex : this.drawCache.mixedNoFieldGridMes.keySet()) {
                    GridCellData curCell;
                    dimItem = this.drawCache.mixedNoFieldGridMes.get(colIndex);
                    if (dimItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW) || (curCell = this.grid.getGridCellData(colIndex, rowStart)).getShowText() != null && !"".equals(curCell.getShowText()) && !"0.0".equals(curCell.getShowText())) continue;
                    double total = this.getLevelSubTotalColFieldIsDownAndFieldInRow(dimItem, this.drawCache.fieldIndexList, field, colIndex, rowStart);
                    total = Double.parseDouble(df.format(total));
                    curCell.setShowText(Double.toString(total));
                    curCell.setHorzAlign(2);
                }
            }
        } else {
            for (colStart = key; colStart <= startRowOrCol; ++colStart) {
                for (int rowIndex : this.drawCache.mixedGridMes.keySet()) {
                    dimItem = this.drawCache.mixedGridMes.get(rowIndex);
                    if (dimItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) continue;
                    for (int i = 0; i < this.defina.showedFields.size(); ++i) {
                        field = this.drawCache.fieldInIndex.get(rowIndex + i);
                        GridCellData curCell = this.grid.getGridCellData(colStart, rowIndex + i + this.grid.getHeaderRowCount() - 1);
                        if (curCell.getShowText() != null && !"".equals(curCell.getShowText()) && !"0.0".equals(curCell.getShowText())) continue;
                        double total = this.getLevelSubTotalRowFieldIsDown(dimItem, this.drawCache.fieldIndexList, field, rowIndex, colStart);
                        total = Double.parseDouble(df.format(total));
                        curCell.setShowText(Double.toString(total));
                        curCell.setHorzAlign(2);
                    }
                }
            }
        }
        for (int k = start + 1; k <= end; ++k) {
            double total = 0.0;
            GridCellData curRightCell = null;
            curRightCell = this.grid.getGridCellData(key, k);
            if (this.drawCache.colLeveIndex.keySet().size() > 1 && this.drawCache.colLeveIndex.get(0) != null) {
                for (Integer colIndex : this.drawCache.colLeveIndex.get(0)) {
                    GridCellData childCell = this.grid.getGridCellData(colIndex.intValue(), k);
                    if (StringUtils.isEmpty((String)childCell.getShowText()) || StringUtils.isEmpty((String)childCell.getShowText().replaceAll(",", ""))) continue;
                    total += Double.parseDouble(childCell.getShowText().replaceAll(",", ""));
                }
            } else {
                for (int j = key + 1; j <= startRowOrCol; ++j) {
                    GridCellData childCell;
                    if (list.contains(j) || StringUtils.isEmpty((String)(childCell = this.grid.getGridCellData(j, k)).getShowText()) || StringUtils.isEmpty((String)childCell.getShowText().replaceAll(",", ""))) continue;
                    total += Double.parseDouble(childCell.getShowText().replaceAll(",", ""));
                }
            }
            total = Double.parseDouble(df.format(total));
            curRightCell.setShowText(Double.toString(total));
            curRightCell.setHorzAlign(2);
            curRightCell.setBottomBorderStyle(1);
            curRightCell.setRightBorderStyle(1);
            curRightCell.setBackGroundColor(0xFFFFFF);
            curRightCell.setSelectable(true);
            curRightCell.setEditable(false);
            curRightCell.setSilverHead(false);
        }
    }

    private List<QuerySelectField> getHasStatisticShowFields() {
        ArrayList<QuerySelectField> showedFields = new ArrayList<QuerySelectField>();
        for (QuerySelectField field : this.defina.showedFields) {
            String dimName;
            showedFields.add(field);
            if (field.getStatisticsFields() != null && !field.getStatisticsFields().isEmpty()) {
                for (QueryStatisticsItem item : field.getStatisticsFields()) {
                    QuerySelectField statisticeField = new QuerySelectField();
                    statisticeField.setTitle(this.defina.getStatisticsFieldName(item.getBuiltIn()));
                    statisticeField.setCode(item.getFormulaExpression());
                    statisticeField.setIsMaster(field.getIsMaster());
                    showedFields.add(statisticeField);
                }
            }
            if (!Boolean.valueOf(field.getIsMaster()).booleanValue() || StringUtils.isEmpty((String)(dimName = this.defina.fieldDimNames.get(field.getCode())))) continue;
            this.drawCache.masterDimNameList.add(dimName);
        }
        return showedFields;
    }

    private void loadData(QueryDimItem cacheItem, QueryDimItem childItem, List<IDataRow> datas, boolean isLastDimAndSumFirst, int curDepth, String type) throws Exception {
        int orderNum = 1;
        List<Object> showedFields = new ArrayList();
        boolean newColOrRow = isLastDimAndSumFirst;
        if (this.defina.isFieldInCol && "row".equals(type) || !this.defina.isFieldInCol && "col".equals(type)) {
            showedFields = this.getHasStatisticShowFields();
        } else {
            newColOrRow = false;
            if (this.drawCache.fieldIsDown) {
                if (childItem != null && childItem.getItemType().equals((Object)DimensionItemType.FIELD)) {
                    QuerySelectField field = null;
                    field = this.defina.statisticsFieldMap.containsKey(childItem.getEditTitle()) ? this.defina.statisticsFieldMap.get(childItem.getEditTitle()) : this.defina.showedFields.stream().filter(f -> f.getCode().equals(childItem.getEditTitle())).findFirst().get();
                    showedFields.add(field);
                    if (field.getStatisticsFields() != null && !field.getStatisticsFields().isEmpty()) {
                        for (QueryStatisticsItem item : field.getStatisticsFields()) {
                            if (!item.getFormulaExpression().equals(childItem.getEditTitle())) continue;
                            QuerySelectField statisticeField = new QuerySelectField();
                            statisticeField.setTitle(this.defina.getStatisticsFieldName(item.getBuiltIn()));
                            statisticeField.setCode(item.getFormulaExpression());
                            statisticeField.setIsMaster(field.getIsMaster());
                            showedFields.add(statisticeField);
                        }
                    }
                }
            } else if (this.drawCache.curField != null) {
                showedFields.add(this.drawCache.curField);
            }
        }
        int colAddCount = 0;
        if (this.defina.lastItemTitle != null && cacheItem.getEditTitle().equals(this.defina.lastItemTitle)) {
            orderNum = 1;
        }
        this.drawCache.orderColIndex = this.drawCache.getColIndex();
        if (this.defina.lastItemNotEnd && cacheItem != null && !cacheItem.isFirst && this.defina.isFieldInCol && "row".equals(type) && datas.size() > 1) {
            colAddCount = 1;
            if (!cacheItem.hasWriteTotal()) {
                this.grid.insertColumns(this.drawCache.getFieldIndex(), 1, this.drawCache.getFieldIndex() - 1);
                this.drawCache.depthColumn.put(this.drawCache.getColIndex() + colAddCount, this.drawCache.getFieldIndex());
                this.drawCache.setFieldIndex(this.drawCache.getFieldIndex() + 1);
                this.drawCache.orderColIndex = this.drawCache.getColIndex() + colAddCount;
            }
            orderNum = cacheItem.itemPageStart + 1;
            this.defina.lastItemNotEnd = false;
            this.defina.lastItemTitle = cacheItem.getEditTitle();
            this.creatIndexCell(cacheItem, childItem, curDepth, orderNum, colAddCount);
            ++orderNum;
        }
        for (int i = 0; i < datas.size(); ++i) {
            if (newColOrRow) {
                if (type == "row") {
                    if (this.defina.isFieldInCol) {
                        this.drawCache.insertRowIndex(curDepth, "row");
                        this.creatIndexCell(cacheItem, childItem, curDepth, orderNum, colAddCount);
                    } else {
                        int colCount = this.grid.getColumnCount();
                        if (colCount - this.drawCache.getFieldIndex() <= i) {
                            this.grid.insertColumns(colCount, 1, this.drawCache.getColIndex() - 1);
                        }
                    }
                } else if (!this.defina.isFieldInCol) {
                    this.drawCache.insertColIndex(curDepth, "col");
                    GridCellData cell = this.grid.getGridCellData(this.drawCache.getColIndex(), this.drawCache.getRowIndex(curDepth));
                    cell.setShowText(String.valueOf(orderNum));
                    cell.setHorzAlign(3);
                } else {
                    int rowCount = this.grid.getRowCount();
                    if (rowCount - this.drawCache.getValueIndex() <= i) {
                        this.grid.insertRows(rowCount, 1, this.drawCache.getRowIndex(curDepth) - 1);
                    }
                }
                ++orderNum;
            } else if (type == "row" && this.defina.isFieldInCol && cacheItem != null) {
                try {
                    if (i == 0 && datas.size() > 1 && cacheItem.isLastDimension() && (!cacheItem.getIsShowSubTotal() && !cacheItem.hasWriteTotal() || !cacheItem.getIsSubTotalInFront() && cacheItem.getItemType() != DimensionItemType.SUBTOTALROW) && cacheItem.totalSize != 0) {
                        if (this.drawCache.orderColIndex != this.drawCache.getFieldIndex() - 1) {
                            this.drawCache.orderColIndex = this.drawCache.getFieldIndex() - 1;
                        }
                        this.creatIndexCell(cacheItem, childItem, curDepth, orderNum, colAddCount);
                        ++orderNum;
                    }
                }
                catch (Exception e) {
                    logger.error("\u8865\u5145\u5e8f\u53f7\u5217\u5931\u8d25: " + e.getMessage());
                }
            }
            IDataRow data = null;
            if (!this.defina.block.isShowDetail() && datas.size() == 1 || this.drawCache.bolckShowDetial || !this.drawCache.bolckShowDetial && childItem.getItemType().equals((Object)DimensionItemType.SUBTOTALROW)) {
                data = datas.get(i);
            }
            this.initSelectFieldsCell(cacheItem, curDepth, data, showedFields, type, i);
            newColOrRow = true;
        }
        if (datas.size() == 0) {
            this.initSelectFieldsCell(cacheItem, curDepth, null, showedFields, type, 0);
        }
    }

    private void creatIndexCell(QueryDimItem cacheItem, QueryDimItem childItem, int curDepth, int orderNum, int colAddCount) {
        GridCellData cell = this.grid.getGridCellData(this.drawCache.orderColIndex, this.drawCache.getRowIndex(curDepth));
        cell.setShowText(String.valueOf(orderNum));
        cell.setHorzAlign(3);
        if (cacheItem != null && !cacheItem.getIsNotTreeStruct()) {
            int depth = cacheItem == childItem ? cacheItem.getDepth() + 1 : childItem.getDepth();
            cell.setPersistenceData("Depth", String.valueOf(depth));
            cell.setPersistenceData("DimName", cacheItem.getDimensionName() != null ? cacheItem.getDimensionName() : "");
            cell.setPersistenceData("nodeDimSet", cacheItem.getDimensionValueSet());
            JSONObject dims = new JSONObject();
            dims.put(cacheItem.getDimensionName(), (Object)cacheItem.getEditTitle());
            cell.setPersistenceData("dimensions", dims.toString());
            cell.setExpandable(false);
            cell.setExpended(false);
            cell.setCheckable(false);
            cell.setCellMode(2);
        }
    }

    private void mergeCellByFormGrid(int col, int row) {
        if (this.defina.block.getHasUserForm() && this.defina.block.getFormdata() != null) {
            if (col >= this.grid.getColumnCount()) {
                return;
            }
            int heardRowLast = this.grid.getHeaderRowCount() - 1;
            GridCellData heardCell = this.grid.getGridCellData(col, heardRowLast);
            Point mergeInfo = heardCell.getMergeInfo();
            Grid2FieldList mergeList = this.grid.merges();
            if (heardCell.isMerged() && mergeInfo != null) {
                for (int i = 0; i < mergeList.count(); ++i) {
                    Grid2CellField info = mergeList.get(i);
                    if (info.left != col || info.top != row - 1 || info.bottom != row - 1) continue;
                    this.grid.mergeCells(info.left, row, info.right, row);
                    this.drawCache.nextFiledMergeCount += info.right - info.left;
                    break;
                }
            }
        }
    }

    private void initSelectFieldsCell(QueryDimItem cacheItem, int depth, IDataRow data, List<QuerySelectField> showedFields, String type, int offsetIndex) throws Exception {
        block11: {
            JSONObject linkDims;
            block12: {
                int row;
                this.drawCache.nextFiledMergeCount = 0;
                for (int e = 0; e < showedFields.size(); ++e) {
                    int col = 0;
                    row = 0;
                    QuerySelectField field = showedFields.get(e);
                    boolean isMaster = Boolean.parseBoolean(field.getIsMaster());
                    if (type == "row") {
                        if (this.defina.isFieldInCol) {
                            col = this.drawCache.getFieldIndex() + e + this.drawCache.nextFiledMergeCount;
                            row = this.drawCache.getRowIndex(depth);
                            this.mergeCellByFormGrid(col, row);
                        } else {
                            col = this.drawCache.getFieldIndex() + offsetIndex;
                            row = this.drawCache.getRowIndex(depth);
                        }
                    } else if (!this.defina.isFieldInCol) {
                        row = this.drawCache.getValueIndex() + e;
                        col = this.drawCache.getColIndex();
                    } else {
                        row = this.drawCache.getValueIndex() + offsetIndex;
                        col = this.drawCache.getColIndex();
                    }
                    this.initRowData(cacheItem, field, data, col, row, isMaster, offsetIndex);
                }
                if (data == null || data.equals(this.defina.totalRow)) break block11;
                DimensionValueSet dims = data.getRowKeys();
                if (dims == null) {
                    return;
                }
                linkDims = new JSONObject();
                for (int j = 0; j < dims.size(); ++j) {
                    String dim = dims.getName(j);
                    if (dim.equalsIgnoreCase("RECORDKEY") || dim.equalsIgnoreCase("VERSIONID")) continue;
                    Object dimValue = ((DataRowImpl)data).getKeyValue(dim);
                    linkDims.put(dim, dimValue);
                }
                if (this.defina.gridType != GridType.ROW && this.defina.gridType != GridType.DETAIL) break block12;
                row = this.drawCache.getRowIndex(depth);
                for (int i = 1; i < this.grid.getColumnCount(); ++i) {
                    GridCellData dimCellData = this.grid.getGridCellData(i, row);
                    this.setCellDimensions(linkDims, dimCellData);
                }
                break block11;
            }
            if (this.defina.gridType != GridType.COL) break block11;
            int col = this.drawCache.getColIndex();
            for (int i = 1; i < this.grid.getRowCount(); ++i) {
                GridCellData dimCellData = this.grid.getGridCellData(col, i);
                this.setCellDimensions(linkDims, dimCellData);
            }
        }
    }

    private void setCellDimensions(JSONObject linkDims, GridCellData dimCellData) {
        String dimStr = dimCellData.getPersistenceData("dimensions");
        if (StringUtils.isEmpty((String)dimStr)) {
            dimCellData.setPersistenceData("dimensions", linkDims.toString());
        } else {
            JSONObject oldDim = new JSONObject(dimStr);
            JSONObject combineJson = this.combineJson(oldDim, linkDims);
            dimCellData.setPersistenceData("dimensions", combineJson.toString());
        }
    }

    private String canUseSort(QuerySelectField field) {
        Boolean isEntityField = false;
        if (field.getDataLink() != null) {
            Integer dataLinkLen = field.getDataLink().length();
            isEntityField = dataLinkLen < 36;
        }
        if (field.getCustom() || Boolean.valueOf(field.getIsMaster()).booleanValue() || isEntityField.booleanValue()) {
            return "false";
        }
        return "true";
    }

    private void setCellWrite(GridCellData cell, String fieldCode, IDataRow data, DataLinkDefine link, QueryDimItem cacheItem) {
        boolean hasNoAuth = this.defina.hasNtWriteAuthFields.contains(fieldCode);
        boolean linkReadOnly = link != null && (link.getEditMode() == DataLinkEditMode.DATA_LINK_READ_ONLY || link.getEditMode() == DataLinkEditMode.DATA_LINK_FIELD_READ_ONLY);
        DimensionValueSet dimValue = new DimensionValueSet();
        if (data == null) {
            if (cacheItem != null && cacheItem.getDimensionValueSet() != null && !StringUtils.isEmpty((String)this.defina.curPeriod)) {
                dimValue.parseString(cacheItem.getDimensionValueSet());
                dimValue.setValue("DATATIME", (Object)this.defina.curPeriod);
            }
        } else {
            dimValue = data.getRowKeys();
        }
        boolean isUploaded = this.defina.checkUploaded(dimValue);
        boolean entityNoWrite = false;
        if (dimValue.size() > 0 && !this.drawCache.onlyLoadForm) {
            entityNoWrite = this.defina.checkEntityAuth(dimValue);
        }
        if (hasNoAuth || linkReadOnly || isUploaded || entityNoWrite) {
            cell.setPersistenceData("rowflag", "0");
            cell.setPersistenceData("canwrite", "false");
        } else {
            String flag = data == null ? "-1" : String.valueOf(data.getGroupingFlag());
            cell.setPersistenceData("rowflag", flag);
        }
    }

    private void initRowData(QueryDimItem cacheItem, QuerySelectField field, IDataRow data, int col, int row, boolean isMaster, int dataIndex) throws Exception {
        List<IWarningRow> warnList;
        DimensionValueSet rowKeys;
        boolean canWrite;
        GridCellData cell = this.grid.getGridCellData(col, row);
        if (cell == null) {
            return;
        }
        String fieldCode = field.getCode();
        cell.setHorzAlign(1);
        cell.setFontStyle(1);
        cell.setForeGroundColor(0x626262);
        cell.setBottomBorderStyle(1);
        cell.setRightBorderStyle(1);
        cell.setBackGroundColor(0xFFFFFF);
        cell.setSelectable(true);
        cell.setEditable(false);
        cell.setSilverHead(false);
        if (this.defina.hasNtAuthFields.contains(fieldCode)) {
            cell.setEditText("/");
            cell.setShowText("/");
            return;
        }
        DataLinkDefine link = this.defina.fieldMapLink.get(fieldCode);
        boolean bl = canWrite = this.defina.isdataPenetration || this.defina.isCustomInput || this.defina.isHistoryData;
        if (!this.defina.isExport && canWrite) {
            this.setCellWrite(cell, fieldCode, data, link, cacheItem);
        }
        FieldDefine item = null;
        if (this.defina.statisticsFieldMap.containsKey(fieldCode)) {
            if (Boolean.parseBoolean(field.getIsMaster())) {
                String tableName = field.getTableName();
                String tableKey = field.getTableKey();
                Optional<QuerySelectField> first = this.defina.statisticsFieldMap.values().stream().filter(e -> !Boolean.parseBoolean(e.getIsMaster())).findFirst();
                if (first.isPresent() && (tableName = first.get().getTableName()) == null && first.get().getCustom() && field.getDataSheet() != null) {
                    tableName = field.getDataSheet();
                }
                TableDefine tableDefine = this.defina.dataDefinitionRuntimeController.queryTableDefine(tableKey);
                item = this.defina.dataDefinitionRuntimeController.queryFieldByCodeInTable(field.getFileExtension(), tableDefine.getKey());
                if ("DW".equals(field.getFileExtension()) && item == null) {
                    item = this.defina.dataDefinitionRuntimeController.queryFieldByCodeInTable("MDCODE", field.getTableKey());
                }
            } else {
                item = this.defina.dataDefinitionRuntimeController.queryFieldDefine(this.defina.statisticsFieldMap.get(fieldCode).getCode());
            }
        } else if (Boolean.parseBoolean(field.getIsMaster())) {
            item = this.defina.dataDefinitionRuntimeController.queryFieldByCodeInTable(field.getFileExtension(), field.getTableKey());
            if ("DW".equals(field.getFileExtension()) && item == null) {
                item = this.defina.dataDefinitionRuntimeController.queryFieldByCodeInTable("MDCODE", field.getTableKey());
            }
        } else {
            item = this.defina.dataDefinitionRuntimeController.queryFieldDefine(fieldCode);
        }
        this.setDataTypeByFieldType(cell, item, field.getCustom());
        if (cacheItem != null && this.defina.isCustomInput) {
            cell.setPersistenceData("itemSize", String.valueOf(cacheItem.totalSize));
            cell.setPersistenceData("index", String.valueOf(dataIndex));
        }
        if (link != null) {
            JSONObject linkInfo = new JSONObject();
            linkInfo.put("key", (Object)link.getKey());
            linkInfo.put("capnames", (Object)link.getCaptionFieldsString());
            linkInfo.put("dropnames", (Object)link.getDropDownFieldsString());
            linkInfo.put("pname", (Object)link.getEnumTitleField());
            cell.setPersistenceData("linkInfo", linkInfo.toString());
        }
        cell.setPersistenceData("fieldCode", fieldCode);
        if (this.defina.showedFieldsIndex.get(fieldCode) != null) {
            cell.setPersistenceData("fieldIndex", this.defina.showedFieldsIndex.get(fieldCode).toString());
        }
        if (data == null) {
            Object rowKeys2;
            cell.setPersistenceData("isEmptyRow", "true");
            String dims = cacheItem != null ? cacheItem.getDimensionValueSet() : "";
            Object object = rowKeys2 = data != null ? data.getRowKeys() : null;
            if (rowKeys2 != null) {
                cell.setPersistenceData("rowkeys", rowKeys2.toString());
            } else {
                cell.setPersistenceData("rowkeys", StringUtils.isEmpty((String)dims) ? "" : cacheItem.getDimensionValueSet());
            }
            cell.setEditText("");
            cell.setShowText("");
            return;
        }
        cell.setPersistenceData("isEmptyRow", "false");
        cell.setPersistenceData("canSort", this.canUseSort(field));
        cell.setPersistenceData("PeriodDimName", this.defina.periodDimName);
        if (data.getGroupingFlag() < 0 && (rowKeys = data.getRowKeys()) != null) {
            if (this.defina.isFloat && !this.defina.floatOrderField.isEmpty()) {
                for (FieldDefine orderField : this.defina.floatOrderField) {
                    AbstractData value = data.getValue(orderField);
                    rowKeys.setValue(orderField.getCode(), (Object)value.getAsFloat());
                }
            }
            cell.setPersistenceData("rowkeys", rowKeys.toString());
        }
        StringData value = null;
        String strValue = "";
        if (StringUtils.isEmpty((String)fieldCode)) {
            value = StringData.NULL;
        } else {
            int index;
            if (this.defina.fieldIndex == null || this.defina.fieldIndex.isEmpty()) {
                return;
            }
            int n = index = this.defina.fieldIndex.containsKey(fieldCode) ? this.defina.fieldIndex.get(fieldCode) : -1;
            if (index < 0) {
                return;
            }
            if (data != null) {
                boolean isTotalMasterFieldValue;
                if (data.getFieldsInfo().getFieldCount() > index) {
                    value = data.getValue(index);
                }
                if (isMaster) {
                    DimensionValueSet rowkeys = data.getRowKeys();
                    if (rowkeys == null) {
                        return;
                    }
                    Object keyValue = rowkeys.getValue(this.defina.fieldDimNames.get(fieldCode));
                    if (keyValue != null) {
                        value = AbstractData.valueOf((String)keyValue.toString());
                    }
                } else {
                    String taskKey = field.getTaskId();
                    if (!StringUtils.isEmpty((String)taskKey) && item != null && !this.defina.block.getIsDataSet()) {
                        FieldType fieldType;
                        String numberZeroShow = this.taskOptionController.getValue(taskKey, "NUMBER_ZERO_SHOW");
                        if (numberZeroShow == null) {
                            numberZeroShow = "";
                        }
                        if (!((fieldType = item.getType()) != FieldType.FIELD_TYPE_INTEGER && fieldType != FieldType.FIELD_TYPE_FLOAT && fieldType != FieldType.FIELD_TYPE_DECIMAL || value == null || value.toString().equals("") || value.getAsString() == "\u2014\u2014" || !(Math.abs(value.getAsFloat()) < 1.0E-10) || "0".equals(numberZeroShow))) {
                            value = AbstractData.valueOf((String)numberZeroShow);
                        }
                    }
                }
                boolean totalCheck = this.defina.block.isShowSum() && this.defina.totalRow != null && this.defina.totalRow.getGroupingFlag() >= 0;
                boolean bl2 = isTotalMasterFieldValue = totalCheck && data.equals(this.defina.totalRow) && Boolean.parseBoolean(field.getIsMaster());
                if (value == null || value.isNull || isTotalMasterFieldValue) {
                    cell.setEditText("");
                    cell.setShowText("");
                    return;
                }
                if (Boolean.parseBoolean(field.getIsMaster()) && value.isNull && !data.equals(this.defina.totalRow)) {
                    String dimName = QueryGridDefination.dataAssist.getDimensionName(item);
                    DimensionValueSet valueSet = data.getRowKeys();
                    Object newValue = valueSet.getValue(dimName);
                    value = AbstractData.valueOf((Object)newValue, (int)DataTypesConvert.fieldTypeToDataType((FieldType)item.getType()));
                }
                strValue = value.getAsString();
            }
        }
        if (data.getGroupingFlag() < 0 && (warnList = this.defina.warnTable.getRowsByFieldCode(fieldCode, data.getRowKeys())).size() > 0) {
            for (int i = 0; i < warnList.size(); ++i) {
                IWarningRow warnRow = warnList.get(i);
                JSONObject result = warnRow.getResultList();
                DataWarningDefine warnDef = this.defina.warnDao.QueryById(warnRow.getWarnId());
                if (warnDef != null && warnDef.getProperty().isReverseIconBtn()) {
                    this.reverseIcon(result);
                }
                cell.setPersistenceData("warnReult", result.toString());
                if (warnDef == null || !warnDef.getProperty().isOnlyIconCheckBox()) continue;
                return;
            }
        }
        try {
            String dimCode = this.defina.fieldDimNames.get(fieldCode);
            if (Boolean.parseBoolean(field.getIsMaster()) || this.defina.enumFieldTable.containsKey(dimCode)) {
                PeriodWrapper pw;
                IEntityTable table = this.defina.enumFieldTable.get(dimCode != null ? dimCode : fieldCode);
                table = table != null ? table : this.defina.enumFieldTable.get(fieldCode);
                cell.setHorzAlign(1);
                if (!"DATATIME".equals(dimCode)) {
                    if (!value.isNull) {
                        if (value.getAsString() == "\u2014\u2014") {
                            cell.setEditText(value.getAsString());
                            cell.setShowText(" ");
                        } else {
                            boolean isEntityOrEnum;
                            FieldDefine fieldDef = this.defina.fieldDefines.get(fieldCode);
                            boolean isEntityField = false;
                            if (dimCode != null && this.defina.dimensionLinks.containsKey(dimCode)) {
                                isEntityField = TableKind.TABLE_KIND_ENTITY.name().equals(this.defina.dimensionLinks.get(dimCode).getTableKind());
                            }
                            boolean bl3 = isEntityOrEnum = !StringUtils.isEmpty((String)fieldDef.getEntityKey()) || isEntityField;
                            if (table != null && isEntityOrEnum) {
                                String[] valueList = strValue.split(";");
                                String title = "";
                                for (int s = 0; s < valueList.length; ++s) {
                                    String curValue;
                                    IEntityRow entity;
                                    String rowCaption = "";
                                    if (s > 0) {
                                        rowCaption = rowCaption + ";";
                                    }
                                    entity = (entity = table.findByEntityKey(curValue = valueList[s])) == null ? table.findByCode(curValue) : entity;
                                    DataLinkDefine linkDefine = this.defina.fieldMapLink.get(fieldCode);
                                    if (linkDefine != null && !StringUtils.isEmpty((String)linkDefine.getCaptionFieldsString())) {
                                        String[] fields = linkDefine.getCaptionFieldsString().split(";");
                                        for (int i = 0; i < fields.length; ++i) {
                                            String fieldKey = fields[i];
                                            if (fieldKey == null) continue;
                                            try {
                                                com.jiuqi.nr.entity.engine.data.AbstractData dataValue = entity.getValue(fieldKey);
                                                if (rowCaption.length() > 0 && i > 0) {
                                                    rowCaption = rowCaption + "|";
                                                }
                                                rowCaption = rowCaption + dataValue.getAsString();
                                                continue;
                                            }
                                            catch (Exception ex) {
                                                logger.error("\u83b7\u53d6\u5b9e\u4f53\u6570\u636e\u5f02\u5e38\uff1a" + ex.getMessage());
                                            }
                                        }
                                    } else if (entity != null) {
                                        rowCaption = entity.getTitle();
                                    }
                                    title = title + rowCaption;
                                }
                                if (!title.equals("")) {
                                    cell.setEditText(title);
                                    cell.setShowText(title);
                                    this.mergeSameFixedFieldCell(field, col, row, cacheItem, cell);
                                } else if (StringUtil.isNullOrEmpty((String)strValue)) {
                                    cell.setEditText("\u672a\u77e5");
                                    cell.setShowText("\u672a\u77e5");
                                } else {
                                    cell.setEditText(strValue);
                                    cell.setShowText(strValue + "\uff08\u65e0\u6b64\u5b9e\u4f53\uff09");
                                }
                            } else {
                                cell.setEditText(strValue);
                                cell.setShowText(strValue);
                                this.mergeSameFixedFieldCell(field, col, row, cacheItem, cell);
                            }
                        }
                    }
                } else if (value.isNull) {
                    if (data.getGroupingFlag() >= 0) {
                        cell.setShowText(null);
                        cell.setEditText(null);
                    } else {
                        Object code = data.getRowKeys().getValue(dimCode);
                        if (code != null) {
                            pw = PeriodUtil.getPeriodWrapper((String)code.toString());
                            strValue = pw.toTitleString();
                            cell.setEditText(strValue);
                            cell.setShowText(strValue);
                        }
                    }
                } else {
                    if (value.getAsString() == "\u2014\u2014") {
                        cell.setShowText(" ");
                    } else {
                        String periodTitle;
                        String valueStr = value.getAsString();
                        pw = PeriodUtil.getPeriodWrapper((String)valueStr);
                        strValue = pw.getType() == 8 ? (periodTitle = this.defina.customPeriodTable.getPeriodTitle(pw)) : pw.toTitleString();
                        cell.setEditText(strValue);
                        cell.setShowText(strValue);
                    }
                    this.mergeSameFixedFieldCell(field, col, row, cacheItem, cell);
                }
            } else if (!value.isNull) {
                String valStr = value.getAsString();
                if ("\u2014\u2014".equals(valStr)) {
                    cell.setShowText(null);
                    cell.setEditText(null);
                    cell.setFitFontSize(false);
                } else {
                    cell.setEditText(valStr);
                    if (value.dataType != 6) {
                        valStr = this.getValue(item, valStr, field);
                    }
                    if ("\u73af\u6bd4".equals(field.getTitle()) || "\u540c\u6bd4".equals(field.getTitle())) {
                        cell.setShowText(valStr + "%");
                    } else {
                        cell.setShowText(valStr);
                    }
                    if (item != null && QueryHelper.isNumField(item) || field.getCustom() && field.getFiledType() == FieldType.FIELD_TYPE_DECIMAL) {
                        cell.setHorzAlign(2);
                        cell.setFitFontSize(true);
                    } else {
                        cell.setFitFontSize(false);
                        cell.setPersistenceData("type", String.valueOf(QueryHelper.convertToHtmlDataType(FieldType.FIELD_TYPE_STRING)));
                    }
                    this.mergeSameFixedFieldCell(field, col, row, cacheItem, cell);
                }
                if (item != null && item.getType() == FieldType.FIELD_TYPE_LOGIC) {
                    cell.setHorzAlign(3);
                }
            } else {
                cell.setShowText("");
                cell.setEditText("");
            }
            List<SuperLinkInfor> linkes = null;
            if (field != null) {
                linkes = field.getLinkes();
            }
            if (!data.equals(this.defina.totalRow)) {
                if (!this.defina.gridType.equals((Object)GridType.DETAIL)) {
                    if (cacheItem != null && !cacheItem.getIsSubTotalItem()) {
                        this.setLinkInfo(cell, linkes);
                    }
                } else {
                    this.setLinkInfo(cell, linkes);
                }
            }
        }
        catch (Exception e2) {
            value = null;
            Log.error((Exception)e2);
            logger.error(e2.getMessage());
        }
    }

    private void mergeSameFixedFieldCell(QuerySelectField field, int col, int row, QueryDimItem cacheItem, GridCellData cell) {
        try {
            GridCellData preCell = null;
            String cellDimCodeStr = null;
            if (this.defina.simpleFieldMap.containsKey(field.getCode())) {
                preCell = this.grid.getGridCellData(col, row - 1);
                cellDimCodeStr = preCell.getPersistenceData("dimensions");
            }
            Boolean isCellWithSameDim = false;
            if (!StringUtils.isEmpty(cellDimCodeStr)) {
                if (cacheItem != null) {
                    isCellWithSameDim = new JSONObject(cellDimCodeStr).getString(cacheItem.getDimensionName()).equals(cacheItem.getEditTitle());
                } else if (!this.drawCache.masterDimNameList.isEmpty()) {
                    isCellWithSameDim = this.isSameDimCell(field, preCell, cell);
                }
            }
            if (isCellWithSameDim.booleanValue() && preCell != null && !StringUtils.isEmpty((String)preCell.getShowText()) && !StringUtils.isEmpty((String)preCell.getEditText()) && cell.getEditText().equals(preCell.getEditText()) && cell.getShowText().equals(preCell.getShowText())) {
                if (preCell.isMerged()) {
                    int y = preCell.getMergeInfo().y;
                    this.grid.getGridCellData(col, y).setMerged(false);
                    this.grid.mergeCells(col, y, col, row);
                } else {
                    this.grid.mergeCells(col, row - 1, col, row);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Boolean isSameDimCell(QuerySelectField selectField, GridCellData preCell, GridCellData cell) {
        block8: {
            try {
                String dimName = null;
                if (Boolean.parseBoolean(selectField.getIsMaster())) {
                    dimName = this.defina.fieldDimNames.get(selectField.getCode());
                } else {
                    FieldDefine field = this.defina.dataDefinitionRuntimeController.queryFieldDefine(selectField.getCode());
                    dimName = QueryHelper.getDimName(field);
                }
                if (!StringUtils.isEmpty((String)dimName) && this.drawCache.masterDimNameList.contains(dimName)) {
                    Integer checkIndex = this.drawCache.masterDimNameList.indexOf(dimName);
                    for (int i = 0; i < this.drawCache.masterDimNameList.size(); ++i) {
                        String masterDimName = this.drawCache.masterDimNameList.get(i);
                        DimensionValueSet preCellDVS = new DimensionValueSet();
                        preCellDVS.parseString(preCell.getPersistenceData("rowkeys"));
                        DimensionValueSet curCellDVS = new DimensionValueSet();
                        curCellDVS.parseString(cell.getPersistenceData("rowkeys"));
                        String perMasterDimCode = preCellDVS.getValue(masterDimName).toString();
                        String curMasterDimCode = curCellDVS.getValue(masterDimName).toString();
                        boolean isSame = perMasterDimCode.equals(curMasterDimCode);
                        if (isSame && i <= checkIndex) {
                            if (i != checkIndex) continue;
                            return true;
                        }
                        return false;
                    }
                    break block8;
                }
                for (String masterDimName : this.drawCache.masterDimNameList) {
                    String curMasterDimCode;
                    DimensionValueSet preCellDVS = new DimensionValueSet();
                    preCellDVS.parseString(preCell.getPersistenceData("rowkeys"));
                    DimensionValueSet curCellDVS = new DimensionValueSet();
                    curCellDVS.parseString(cell.getPersistenceData("rowkeys"));
                    String perMasterDimCode = preCellDVS.getValue(masterDimName).toString();
                    boolean isSame = perMasterDimCode.equals(curMasterDimCode = curCellDVS.getValue(masterDimName).toString());
                    if (isSame) continue;
                    return false;
                }
                return true;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return false;
    }

    private JSONObject buildJsonData(DimensionValueSet rowKeys) {
        if (rowKeys != null) {
            JSONObject rowJson = new JSONObject();
            for (int i = 0; i < rowKeys.size(); ++i) {
                String name = rowKeys.getName(i);
                if (rowJson.has(name)) continue;
                Object value = rowKeys.getValue(i);
                rowJson.put(name, value);
            }
            return rowJson;
        }
        return null;
    }

    private void reverseIcon(JSONObject result) {
        String groupType;
        switch (groupType = result.get("iconGroupIndex").toString()) {
            case "group1": 
            case "group2": 
            case "group3": 
            case "group4": 
            case "group5": 
            case "group6": 
            case "group7": 
            case "group8": 
            case "group9": 
            case "group10": {
                if (result.get("iconIndex").toString().equals("0.0")) {
                    result.put("iconIndex", (Object)"2.0");
                    break;
                }
                if (!result.get("iconIndex").toString().equals("2.0")) break;
                result.put("iconIndex", (Object)"0.0");
                break;
            }
            case "group11": 
            case "group12": 
            case "group13": {
                if (result.get("iconIndex").toString().equals("0.0")) {
                    result.put("iconIndex", (Object)"3.0");
                    break;
                }
                if (result.get("iconIndex").toString().equals("1.0")) {
                    result.put("iconIndex", (Object)"2.0");
                    break;
                }
                if (result.get("iconIndex").toString().equals("2.0")) {
                    result.put("iconIndex", (Object)"1.0");
                    break;
                }
                if (!result.get("iconIndex").toString().equals("3.0")) break;
                result.put("iconIndex", (Object)"0.0");
                break;
            }
            case "group14": 
            case "group15": 
            case "group16": 
            case "group17": 
            case "group18": {
                if (result.get("iconIndex").toString().equals("0.0")) {
                    result.put("iconIndex", (Object)"4.0");
                    break;
                }
                if (result.get("iconIndex").toString().equals("1.0")) {
                    result.put("iconIndex", (Object)"3.0");
                    break;
                }
                if (result.get("iconIndex").toString().equals("3.0")) {
                    result.put("iconIndex", (Object)"1.0");
                    break;
                }
                if (!result.get("iconIndex").toString().equals("4.0")) break;
                result.put("iconIndex", (Object)"0.0");
                break;
            }
        }
    }

    public int childItemSubSize(QueryDimItem cacheItem, boolean type, boolean beforeHasRowSub) {
        int rowSubSize = 0;
        int childRowSubSize = 0;
        if (cacheItem != null) {
            if (!cacheItem.getIsSubTotalItem() && cacheItem.getOwnDataSize() > 0 && cacheItem.getIsShowSubTotal() && type || beforeHasRowSub && cacheItem.getStaticticsRow() != null) {
                rowSubSize = 1;
            }
            if (cacheItem.getChildItems() != null && cacheItem.getChildItems().size() > 0 && cacheItem.getIsNotTreeStruct()) {
                for (int i = 0; i < cacheItem.getChildItems().size(); ++i) {
                    childRowSubSize = this.childItemSubSize(cacheItem.getChildItems().get(i), true, beforeHasRowSub);
                    if (childRowSubSize > 0) {
                        beforeHasRowSub = true;
                    }
                    rowSubSize += childRowSubSize;
                }
            }
        }
        return rowSubSize;
    }

    public int getItemTotalSize(QueryDimItem cacheItem) {
        int rowSize = 0;
        int childSize = 0;
        if (cacheItem != null) {
            if (cacheItem.getDetailRows() != null) {
                rowSize = cacheItem.getDetailRows().size();
            }
            if (cacheItem.getChildItems() != null && cacheItem.getChildItems().size() > 0 && (!this.defina.isTreeLoad || cacheItem.getIsNotTreeStruct())) {
                for (int i = 0; i < cacheItem.getChildItems().size() && (rowSize += (childSize = this.getItemTotalSize(cacheItem.getChildItems().get(i)))) <= 0; ++i) {
                }
            }
        }
        return rowSize;
    }

    public String getCurTitle(QueryDimItem cacheItem) {
        String title = cacheItem.getShowTitle();
        if (cacheItem.getIsTree() && (cacheItem.getIsTree() || cacheItem.getIsNotTreeStruct()) && title != "\u5c0f\u8ba1") {
            int depth = cacheItem.getDepth();
            for (int i = 0; i < depth; ++i) {
                title = this.defina.isExport ? "      " + title : "   " + title;
            }
        }
        return title;
    }

    public int getCurDepth(QueryDimItem cacheItem, int depth) {
        int curDepth = 0;
        curDepth = cacheItem.getIsNotTreeStruct() || !cacheItem.getIsNotTreeStruct() && this.defina.block.getBlockInfo().getDimLevelShowType() == 2 ? depth : cacheItem.getDepth();
        return curDepth;
    }

    private void updateMergeMessage(int row) {
        if (!this.drawCache.sumTotalColIndex.containsKey(row)) {
            ArrayList<Integer> rowList = new ArrayList<Integer>();
            rowList.add(this.drawCache.getColIndex());
            this.drawCache.sumTotalColIndex.put(row, rowList);
        } else {
            List<Integer> indexList = this.drawCache.sumTotalColIndex.get(row);
            indexList.add(this.drawCache.getColIndex());
        }
    }

    private void mergeGridCells() {
        for (int row : this.drawCache.sumTotalColIndex.keySet()) {
            List<Integer> colIndexList = this.drawCache.sumTotalColIndex.get(row);
            for (int i = 0; i < colIndexList.size(); ++i) {
                int startCol = colIndexList.get(i);
                int endCol = 0;
                endCol = i + 1 < colIndexList.size() ? colIndexList.get(i + 1) - 1 : this.drawCache.fieldIndex - 1;
                if (startCol == endCol) continue;
                this.grid.mergeCells(startCol, row, endCol, row);
            }
        }
    }

    public Grid2Data creatNewGridByType() {
        try {
            if (this.defina.block.getHasUserForm() && this.defina.block.getFormdata() != null) {
                ObjectMapper mapper = new ObjectMapper();
                SimpleModule module = new SimpleModule();
                module.addDeserializer(Grid2Data.class, (JsonDeserializer)new Grid2DataDeserialize());
                module.addDeserializer(GridCellData.class, (JsonDeserializer)new GridCellDataDeserialize());
                mapper.registerModule((Module)module);
                this.grid = (Grid2Data)mapper.readValue(this.defina.block.getFormdata(), Grid2Data.class);
                if (this.defina.block.getBlockInfo().getCustomFieldName() != null) {
                    Integer beforeHeaderRowNum = this.grid.getHeaderRowCount();
                    this.grid.insertRows(this.grid.getRowCount(), 1, this.grid.getRowCount() - 1);
                    Integer afterHeaderRowNum = this.grid.getHeaderRowCount();
                    if (afterHeaderRowNum > beforeHeaderRowNum) {
                        this.grid.setHeaderRowCount(beforeHeaderRowNum.intValue());
                    }
                    this.drawCache.nextFiledMergeCount = 0;
                    for (int i = 0; i < this.grid.getColumnCount(); ++i) {
                        this.mergeCellByFormGrid(i, this.grid.getRowCount() - 1);
                    }
                } else {
                    this.grid.insertRows(this.grid.getRowCount(), 1);
                }
                this.drawCache.setValueIndex(this.grid.getRowCount() - 1);
                this.drawCache.setRowIndex(this.grid.getRowCount() - 1);
            } else {
                this.grid.setColumnCount(2);
                this.grid.setRowCount(2);
                this.grid.setRowHidden(0, true);
                this.grid.setColumnHidden(0, true);
                this.drawCache.setRowIndex(1);
                this.drawCache.setColIndex(1);
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return this.grid;
    }

    @Override
    public Grid2Data drawGridData() {
        return null;
    }

    private GridCellData getCell(QueryDimItem cacheItem, int row, int col, String title, int align) {
        GridCellData cell = this.grid.getGridCellData(col, row);
        cell.setFontStyle(1);
        int n = align = "\u5c0f\u8ba1".equals(title) ? 3 : align;
        if (cacheItem != null) {
            if (this.defina.dimensionLinks != null && !cacheItem.getIsSubTotalItem()) {
                for (Map.Entry<String, QueryDimensionDefine> entry : this.defina.dimensionLinks.entrySet()) {
                    if (cacheItem.getDimensionName() == null || !cacheItem.getDimensionName().equals(entry.getKey())) continue;
                    List<SuperLinkInfor> linkes = entry.getValue().getLinkes();
                    cell.setPersistenceData("viewId", entry.getValue().getViewId());
                    this.setLinkInfo(cell, linkes);
                }
            }
            if (cacheItem.getItemType().equals((Object)DimensionItemType.FIELD)) {
                QuerySelectField field = null;
                field = this.defina.statisticsFieldMap.containsKey(cacheItem.getEditTitle()) ? this.defina.statisticsFieldMap.get(cacheItem.getEditTitle()) : this.defina.showedFields.stream().filter(f -> f.getCode().equals(cacheItem.getEditTitle())).findFirst().get();
                cell.setPersistenceData("fieldCode", field.getCode());
                cell.setPersistenceData("type", String.valueOf(QueryHelper.convertToHtmlDataType(field.getFiledType())));
                cell.setPersistenceData("canSort", this.canUseSort(field));
            }
            if (!(cacheItem.getIsNotTreeStruct() || this.defina.block.getBlockInfo().getDimLevelShowType() != 1 && this.defina.block.getBlockInfo().getDimLevelShowType() != -1)) {
                boolean treeloadExpand;
                cell.setPersistenceData("Depth", String.valueOf(cacheItem.getDepth()));
                cell.setPersistenceData("DimName", cacheItem.getDimensionName() != null ? cacheItem.getDimensionName() : "");
                cell.setPersistenceData("nodeDimSet", cacheItem.getDimensionValueSet());
                cell.setPersistenceData("isLoadChild", "false");
                JSONObject dims = new JSONObject();
                dims.put(cacheItem.getDimensionName(), (Object)cacheItem.getEditTitle());
                cell.setPersistenceData("dimensions", dims.toString());
                int childRows = this.getItemTotalSize(cacheItem);
                boolean expand = false;
                boolean bl = treeloadExpand = !this.defina.isTreeLoad && (cacheItem.getChildDataSize() > 0 || cacheItem.getOwnDataSize() > 1) && this.defina.isSimpleQuery;
                if ((cacheItem.getChildHasDetailRow() || cacheItem.getOwnDataSize() > 1 || treeloadExpand) && !this.defina.isSimpleQuery) {
                    expand = true;
                }
                if (!this.defina.block.isShowDetail() && !cacheItem.getIsNotTreeStruct()) {
                    expand = cacheItem.hasChildItem;
                }
                cell.setExpandable(expand);
                if (this.defina.isTreeLoad) {
                    cell.setExpended(false);
                    if (this.defina.pageInfo.strucNode == null && cacheItem.getDepth() == 0) {
                        cell.setExpended(true);
                    }
                } else {
                    cell.setExpended(true);
                }
                if (cell.isExpended()) {
                    cell.setPersistenceData("isLoadChild", "true");
                }
                if (!this.defina.isTreeLoad) {
                    cell.setExpandable(false);
                    cell.setExpended(false);
                }
                cell.setCheckable(false);
                cell.setCellMode(2);
            }
            cell.setDepth(cacheItem.getDepth());
            cell.setPersistenceData("DimName", cacheItem.getDimensionName() != null ? cacheItem.getDimensionName() : "");
            cell.setPersistenceData("DimValue", cacheItem.getEditTitle());
            if ("\u5c0f\u8ba1".equals(title)) {
                cell.setPersistenceData("DimName", cacheItem.getDimensionName() != null ? cacheItem.getDimensionName() : "");
                cell.setPersistenceData("DimValue", "\u5c0f\u8ba1");
            }
        }
        cell.setShowText(title);
        cell.setFitFontSize(false);
        cell.setSilverHead(true);
        cell.setBottomBorderStyle(1);
        cell.setRightBorderStyle(1);
        cell.setSelectable(true);
        cell.setEditable(false);
        cell.setHorzAlign(align);
        cell.setPersistenceData("fontSize", String.valueOf(12));
        cell.setForeGroundColor(QueryConst.htmlColorToInt("#444444"));
        cell.setFontSize(13);
        cell.setBackGroundColor(0xF8F8F8);
        return cell;
    }

    private boolean hasChildItem(QueryDimItem cacheItem) {
        List<QueryDimItem> childItems = cacheItem.getChildItems();
        for (QueryDimItem item : childItems) {
            if (item.getIsSubTotalItem() || item.getChildDataSize() <= 0 && item.getOwnDataSize() <= 0) continue;
            return true;
        }
        return false;
    }

    private void setLinkInfo(GridCellData cell, List<SuperLinkInfor> linkes) {
        if (linkes != null && linkes.size() > 0) {
            cell.setPersistenceData("islink", "true");
            if (linkes.size() > 1) {
                cell.setPersistenceData("hasIcon", "true");
            } else {
                cell.setForeGroundColor(255);
                cell.setFontStyle(8);
            }
        }
    }

    public void handlerCustomFormGrid() {
        if (this.defina.block.getHasUserForm() && this.defina.block.getFormdata() != null && this.defina.block.getBlockInfo().getCustomFieldName() != null) {
            int demoColor;
            int formColor = -1;
            int headerRowCount = this.grid.getHeaderRowCount();
            int headerColumnCount = this.grid.getHeaderColumnCount();
            for (int h = 0; h < headerRowCount; ++h) {
                boolean isBreak = false;
                for (int l = 0; l < headerColumnCount; ++l) {
                    GridCellData cell = this.grid.getGridCellData(l, h);
                    int backGroundColor = cell.getBackGroundColor();
                    if (backGroundColor == -1 || backGroundColor == 0xF8F8F8) continue;
                    formColor = backGroundColor;
                    isBreak = true;
                    break;
                }
                if (isBreak) break;
            }
            for (int i = 1; i < this.drawCache.getValueIndex(); ++i) {
                for (int j = 1; j < this.drawCache.getFieldIndex(); ++j) {
                    GridCellData cell = this.grid.getGridCellData(j, i);
                    cell.setForeGroundColor(0x444444);
                    int color = formColor == -1 ? 0xF8F8F8 : formColor;
                    cell.setBackGroundColor(color);
                }
            }
            int top = 1;
            Grid2FieldList mergeList = this.grid.merges();
            ArrayList<String[]> needMerges = new ArrayList<String[]>();
            for (int i = 0; i < mergeList.count(); ++i) {
                Grid2CellField info = mergeList.get(i);
                if (info.left != this.drawCache.fieldIndex || info.right != this.grid.getColumnCount() - 1 || info.top >= this.drawCache.getValueIndex()) continue;
                String[] str = new String[5];
                top = info.bottom;
                str[0] = "1";
                str[1] = info.top + "";
                str[2] = info.right + "";
                str[3] = info.bottom + "";
                str[4] = this.grid.getGridCellData(info.left, info.top).getShowText();
                needMerges.add(str);
            }
            for (String[] str : needMerges) {
                GridCellData cell = this.grid.getGridCellData(1, Integer.valueOf(str[1]).intValue());
                cell.setShowText(str[4]);
                cell.setMerged(false);
                this.grid.mergeCells(1, Integer.valueOf(str[1]).intValue(), Integer.valueOf(str[2]).intValue(), Integer.valueOf(str[3]).intValue());
            }
            if (this.drawCache.getValueIndex() > 0) {
                String range = this.defina.block.getBlockInfo().getCustomFieldMergeRange();
                if (StringUtils.isEmpty((String)range)) {
                    this.mergeAll(top);
                } else {
                    String[] temps = range.split("-");
                    if (temps.length > 1) {
                        int start = Integer.parseInt(temps[0]);
                        if (start <= top) {
                            start = top + 1;
                        }
                        int end = Integer.parseInt(temps[1]);
                        if (start >= headerRowCount || end >= headerRowCount || start > end) {
                            logger.info("\u81ea\u5b9a\u4e49\u8868\u5934\u5408\u5e76\u5f00\u59cb\u6216\u7ed3\u675f\u884c\u53f7\u8d85\u8fc7\u6700\u5927\u8868\u5934\u884c\u53f7\uff0c\u6267\u884c\u9ed8\u8ba4\u903b\u8f91");
                            this.mergeAll(top);
                        } else {
                            for (int i = top + 1; i < end; ++i) {
                                if (i < start) {
                                    this.grid.getGridCellData(1, i).setRightBorderStyle(0);
                                    if (i >= start - 1) continue;
                                    this.grid.getGridCellData(1, i).setBottomBorderStyle(0);
                                    continue;
                                }
                                this.grid.getGridCellData(1, i).setRightBorderStyle(1);
                                this.grid.getGridCellData(1, i).setBottomBorderStyle(1);
                            }
                            this.grid.getGridCellData(1, start).setShowText(this.defina.block.getBlockInfo().getCustomFieldName());
                            this.grid.mergeCells(1, start, this.drawCache.getFieldIndex() - 1, end);
                        }
                    } else {
                        this.mergeAll(top);
                    }
                }
            }
            int sideColor = (demoColor = this.grid.getGridCellData(0, 0).getBackGroundColor()) == -1 ? 0xEBEBEB : demoColor;
            for (int i = 0; i < this.grid.getRowCount(); ++i) {
                GridCellData cell = this.grid.getGridCellData(0, i);
                cell.setBackGroundColor(sideColor);
            }
        }
    }

    private void mergeAll(int top) {
        for (int i = top + 1; i < this.drawCache.getValueIndex(); ++i) {
            this.grid.getGridCellData(1, i).setRightBorderStyle(1);
        }
        this.grid.mergeCells(1, top + 1, this.drawCache.getFieldIndex() - 1, this.drawCache.getValueIndex() - 1);
    }

    public void handlerDetialsGrid(String type) throws Exception {
        if (this.drawCache.hasWriteDetails) {
            return;
        }
        this.drawCache.hasWriteDetails = true;
        this.defina.pageInfo.isFirstPage = this.defina.pageInfo.isFirstPage != false || !this.defina.isPaging;
        if (this.defina.gridType.equals((Object)GridType.DETAIL) || this.defina.isSimpleQuery) {
            int i;
            Boolean isFirstPage = this.defina.pageInfo.isFirstPage;
            int masterFieldCount = 0;
            if (type.equals("row")) {
                this.drawCache.setFieldIndex(1);
                if (!this.defina.isFieldInCol || !this.defina.block.getHasUserForm() || this.defina.block.getFormdata() == null) {
                    this.drawCache.setRowIndex(this.drawCache.getValueIndex());
                }
            } else {
                this.drawCache.setValueIndex(1);
                this.drawCache.setColIndex(this.drawCache.getFieldIndex());
            }
            if (!this.drawCache.onlyLoadForm) {
                if (this.drawCache.blockShowTotal && this.defina.pageInfo.isFirstPage.booleanValue()) {
                    List<Object> showedFields = new ArrayList();
                    showedFields = !this.drawCache.staticticsFieldColIndexs.isEmpty() ? this.getHasStatisticShowFields() : this.defina.showedFields;
                    this.initSelectFieldsCell(null, -1, this.defina.totalRow, showedFields, type, 0);
                }
                List<Object> detialsRows = new ArrayList();
                int total = this.defina.dataTable == null ? 0 : this.defina.dataTable.getCount();
                detialsRows = this.defina.detailRows;
                if (detialsRows == null || detialsRows.size() == 0) {
                    for (int j = 0; j < total; ++j) {
                        IDataRow dateRow = this.defina.dataTable.getItem(j);
                        if (dateRow.getGroupingFlag() >= 0) continue;
                        QueryGridDefination.RowDataType valType = this.defina.checkRowData(dateRow);
                        if (!this.defina.block.isShowNullRow() && valType == QueryGridDefination.RowDataType.ALLNULL || !this.defina.block.isShowZeroRow() && valType == QueryGridDefination.RowDataType.ALLZERO) continue;
                        detialsRows.add(dateRow);
                    }
                }
                total = detialsRows.size();
                int start = 0;
                int end = total;
                BlockInfo blockInfo = this.defina.block.getBlockInfo();
                this.defina.block.getBlockInfo().setTotalCount(blockInfo.isShowSum() ? total + 1 : total);
                if (this.defina.isPaging) {
                    int n = end = this.defina.maxItemCount <= total ? this.defina.maxItemCount : total;
                    if (!this.defina.pageInfo.isFirstPage.booleanValue()) {
                        start = this.defina.pageInfo.detailStart;
                        end = this.defina.pageInfo.detailEnd <= total ? this.defina.pageInfo.detailEnd : end;
                    }
                }
                logger.debug("page\uff1a" + start + "," + end);
                ArrayList<IDataRow> curPageRows = new ArrayList<IDataRow>();
                for (int j = start; j < end; ++j) {
                    IDataRow dateRow = (IDataRow)detialsRows.get(j);
                    curPageRows.add(dateRow);
                }
                Boolean needNewRow = this.drawCache.blockShowTotal && this.defina.pageInfo.isFirstPage != false;
                this.loadData(null, null, curPageRows, needNewRow, -1, type);
                if (this.defina.isPaging) {
                    this.defina.pageInfo.isFirstPage = false;
                    if (end == total) {
                        this.defina.block.setEnd(true);
                    }
                    this.defina.pageInfo.detailStart = end <= total ? end : total;
                    this.defina.pageInfo.detailEnd = end + this.defina.maxItemCount <= total ? end + this.defina.maxItemCount : total;
                    this.queryCache.setItem(this.userKey, this.blockKey, "PAGEINFO", this.defina.pageInfo);
                }
            } else {
                this.initSelectFieldsCell(null, -1, null, this.defina.showedFields, type, 0);
                for (int colnum = 0; colnum < this.grid.getColumnCount(); ++colnum) {
                    GridCellData cell = this.grid.getGridCellData(colnum, 2);
                    if (cell == null) continue;
                    cell.setBottomBorderStyle(1);
                    cell.setRightBorderStyle(1);
                }
            }
            if (this.defina.rows == null && this.drawCache.blockShowTotal && isFirstPage.booleanValue()) {
                boolean masterFieldIsFirst = false;
                for (i = 0; i < this.defina.showedFields.size(); ++i) {
                    QuerySelectField field = this.defina.showedFields.get(i);
                    if (!Boolean.parseBoolean(field.getIsMaster())) continue;
                    ++masterFieldCount;
                    if (i != 0) continue;
                    masterFieldIsFirst = true;
                }
                if (masterFieldCount > 0 && masterFieldIsFirst) {
                    this.getCell(null, this.drawCache.getValueIndex(), 1, "\u5408\u8ba1", 3);
                }
                this.grid.mergeCells(1, 2, masterFieldCount == 0 ? 1 : masterFieldCount, 2);
            }
            if (this.defina.cols == null && this.drawCache.blockShowTotal && isFirstPage.booleanValue() && "col".equals(type)) {
                boolean masterFieldIsFirst = false;
                for (i = 0; i < this.defina.showedFields.size(); ++i) {
                    QuerySelectField field = this.defina.showedFields.get(i);
                    if (!Boolean.parseBoolean(field.getIsMaster())) continue;
                    ++masterFieldCount;
                    if (i != 0) continue;
                    masterFieldIsFirst = true;
                }
                if (masterFieldCount > 0 && masterFieldIsFirst) {
                    this.getCell(null, 1, 2, "\u5408\u8ba1", 3);
                }
                this.grid.mergeCells(2, 1, 2, masterFieldCount == 0 ? 1 : masterFieldCount);
            }
        }
    }

    private void setDataTypeByFieldType(GridCellData cell, FieldDefine field, Boolean isCustomer) {
        int digit = 2;
        int fieldType = 1;
        if (field == null) {
            cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
            cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Text)));
        } else {
            fieldType = field.getType().getValue();
            digit = field.getFractionDigits();
        }
        if (this.defina.decimalVal != null) {
            if (StringUtils.isNotEmpty((String)field.getMeasureUnit())) {
                String fieldMeasureUnit = field.getMeasureUnit();
                if (!fieldMeasureUnit.contains("NotDimession")) {
                    digit = this.defina.decimalVal;
                }
            } else {
                digit = this.defina.decimalVal;
            }
        }
        String digitStr = "";
        for (int i = 0; i < digit; ++i) {
            if (i == 0) {
                digitStr = digitStr + ".";
            }
            digitStr = digitStr + "0";
        }
        if (fieldType == 2 && !StringUtil.isNullOrEmpty((String)field.getEntityKey())) {
            fieldType = 23;
        }
        cell.setPersistenceData("valueType", String.valueOf(fieldType));
        switch (fieldType) {
            case 1: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Number)));
                cell.setPersistenceData("formatter", "#,##0" + digitStr);
                break;
            }
            case 2: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Text)));
                break;
            }
            case 3: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Number)));
                cell.setPersistenceData("formatter", "#,##0" + digitStr);
                break;
            }
            case 4: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Boolean));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Boolean)));
                cell.setPersistenceData("formatter", "\u662f/\u5426");
                break;
            }
            case 5: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
                cell.setFormatter("yyyy-MM-dd");
                cell.setPersistenceData("formatter", "yyyy-MM-dd");
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime)));
                break;
            }
            case 6: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
                cell.setFormatter("yyyy-M-d h:mm");
                cell.setPersistenceData("formatter", "yyyy-M-d h:mm");
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime)));
                break;
            }
            case 7: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Text)));
                break;
            }
            case 8: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Number)));
                cell.setPersistenceData("formatter", "#,##0" + digitStr);
                break;
            }
            case 19: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
                cell.setFormatter("h:mm");
                cell.setPersistenceData("formatter", "h:mm");
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime)));
                break;
            }
            default: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Text)));
            }
        }
    }

    public String getValue(FieldDefine curent, String valStr, QuerySelectField selectField) {
        int fractionDigits = 2;
        FieldType type = FieldType.FIELD_TYPE_DECIMAL;
        if (curent != null) {
            fractionDigits = curent.getFractionDigits();
            type = curent.getType();
        } else if (selectField.getCustom()) {
            fractionDigits = selectField.getDotNum();
        }
        if (this.defina.decimal != null) {
            if (StringUtils.isNotEmpty((String)curent.getMeasureUnit())) {
                String fieldMeasureUnit = curent.getMeasureUnit();
                if (!fieldMeasureUnit.contains("NotDimession")) {
                    fractionDigits = Integer.parseInt(this.defina.decimal);
                }
            } else {
                fractionDigits = Integer.parseInt(this.defina.decimal);
            }
        }
        if (valStr == "\u2014\u2014" || StringUtil.isNullOrEmpty((String)valStr)) {
            return valStr;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0" + QueryGrid.getDigits(fractionDigits));
        try {
            switch (type) {
                case FIELD_TYPE_INTEGER: {
                    if (valStr != null && valStr.length() > 9) {
                        BigDecimal decimalValue = Convert.toBigDecimal((Object)valStr);
                        return decimalFormat.format(decimalValue);
                    }
                    if (valStr.contains(".")) {
                        return valStr;
                    }
                    Integer iVal = Convert.toInt((String)valStr);
                    return decimalFormat.format(iVal);
                }
                case FIELD_TYPE_DECIMAL: {
                    BigDecimal decimalValue = Convert.toBigDecimal((Object)valStr);
                    BigDecimal deciValue = decimalValue.setScale(fractionDigits, 4);
                    return decimalFormat.format(deciValue);
                }
                case FIELD_TYPE_FLOAT: {
                    double floatValue = Convert.toDouble((String)valStr);
                    Double dbVal = Round.callFunction((Number)floatValue, (int)fractionDigits);
                    return decimalFormat.format(dbVal);
                }
            }
            return valStr;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return valStr;
        }
    }

    private static String getDigits(int size) {
        String digitStr = "";
        for (int i = 0; i < size; ++i) {
            digitStr = digitStr + "0";
        }
        if (!StringUtil.isNullOrEmpty((String)digitStr)) {
            return "." + digitStr;
        }
        return digitStr;
    }

    private JSONObject combineJson(JSONObject Obj1, JSONObject Obj2) throws JSONException {
        Iterator Keys2 = Obj2.keys();
        while (Keys2.hasNext()) {
            String key = (String)Keys2.next();
            String value = Obj2.optString(key);
            Obj1.put(key, (Object)value);
        }
        return Obj1;
    }

    class drawGridCache {
        public boolean isPaging;
        public boolean pageEnd;
        public boolean hasValueIndex;
        public boolean fieldIsDown;
        public boolean onlyLoadForm;
        public boolean blockShowTotal;
        public boolean bolckShowDetial;
        public QuerySelectField curField;
        public int pageSize;
        private int rowIndex;
        private int colIndex;
        private int maxRowIndex;
        private int maxColIndex;
        private int fieldIndex;
        private int valueIndex;
        public int orderColIndex;
        public boolean isFirstDrawThisDim = true;
        public GridBlockType gridType;
        public DimensionValueSet curDimValue;
        public List<Integer> colOrRowIndex;
        public Map<Integer, List<Integer>> rowOrColToSubs;
        public Map<Integer, QueryDimItem> subIndexAndFaDim;
        public Map<Integer, QueryDimItem> mixedGridMes;
        public Map<Integer, QuerySelectField> indexWithField;
        public Map<Integer, Integer> depthRow;
        public Map<Integer, Integer> depthColumn;
        public Map<Integer, List<Integer>> sumTotalColIndex;
        public Map<Integer, List<Integer>> sumTotalRowIndex;
        public Map<Integer, List<Integer>> sumColMergeMes;
        public Map<Integer, List<Integer>> colLeveIndex = new LinkedHashMap<Integer, List<Integer>>();
        public Map<Integer, List<Integer>> rowLeveIndex = new LinkedHashMap<Integer, List<Integer>>();
        public List<Integer> fieldIndexList = new ArrayList<Integer>();
        public Map<Integer, QuerySelectField> fieldInIndex = new LinkedHashMap<Integer, QuerySelectField>();
        public Map<Integer, QueryDimItem> mixedNoFieldGridMes;
        public Set<Integer> staticticsFieldColIndexs = new HashSet<Integer>();
        public Integer staticticsRowIndex;
        public List<Integer> fieldInSubTotalColIndexs = new ArrayList<Integer>();
        public Integer fieldInSubTotalRowIndex;
        public Map<String, QueryPreWarn> warnIdWithPreWarn;
        public List<FieldDefine> warnFieldList;
        public Map<String, Map<String, List<String>>> fieldAndpreIdAndFormula;
        public Map<String, Map<String, String>> fieldAndSortValue;
        public Map<Integer, Map<String, List<String>>> colWithFormula;
        public Map<Integer, Integer> gridRowIndexAndWarnRowIndex;
        public int nextFiledMergeCount;
        public boolean hasWriteDetails = false;
        public List<String> masterDimNameList = new ArrayList<String>();

        public int getRowIndex(int depth) {
            if (this.depthRow.containsKey(depth)) {
                this.maxRowIndex = this.rowIndex;
                this.setRowIndex(this.depthRow.get(depth));
            }
            return this.rowIndex;
        }

        public void setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
        }

        public int getColIndex() {
            return this.colIndex;
        }

        public void setColIndex(int colIndex) {
            this.colIndex = colIndex;
        }

        public int getFieldIndex() {
            return this.fieldIndex;
        }

        public void setFieldIndex(int fieldIndex) {
            this.fieldIndex = fieldIndex;
        }

        public int getValueIndex() {
            return this.valueIndex;
        }

        public void setValueIndex(int valueIndex) {
            this.valueIndex = valueIndex;
        }

        public void insertRowIndex(int depth, String type) {
            if ("col".equals(type)) {
                if (this.depthRow.containsKey(depth)) {
                    this.maxRowIndex = this.rowIndex;
                    this.setRowIndex(this.depthRow.get(depth));
                } else {
                    this.rowIndex = this.maxRowIndex > this.rowIndex ? this.maxRowIndex : this.rowIndex;
                    ++this.rowIndex;
                    if (this.hasValueIndex) {
                        ++this.valueIndex;
                    }
                    QueryGrid.this.grid.insertRows(this.rowIndex, 1, this.rowIndex - 1);
                    if (this.rowOrColToSubs != null) {
                        for (Integer key : this.rowOrColToSubs.keySet()) {
                            List<Integer> subsVal = this.rowOrColToSubs.get(key);
                            ArrayList<Integer> subsRow = new ArrayList<Integer>();
                            for (Integer row : subsVal) {
                                subsRow.add(row + 1);
                            }
                            this.rowOrColToSubs.put(key, subsRow);
                        }
                    }
                    this.depthRow.put(depth, this.rowIndex);
                }
            } else {
                ++this.rowIndex;
                Integer beforeHeaderRowNum = QueryGrid.this.grid.getHeaderRowCount();
                QueryGrid.this.grid.insertRows(this.rowIndex, 1, this.rowIndex - 1);
                Integer afterHeaderRowNum = QueryGrid.this.grid.getHeaderRowCount();
                if (afterHeaderRowNum > beforeHeaderRowNum) {
                    QueryGrid.this.grid.setHeaderRowCount(beforeHeaderRowNum.intValue());
                }
            }
        }

        public void insertValueIndex(int index) {
            if (QueryGrid.this.grid.getRowCount() - 1 < index) {
                QueryGrid.this.grid.insertRows(index, 1, this.valueIndex);
            }
        }

        public void insertColIndex(int depth, String type) {
            if ("col".equals(type)) {
                ++this.colIndex;
                QueryGrid.this.grid.insertColumns(this.colIndex, 1, this.colIndex - 1);
            } else if (this.depthColumn.containsKey(depth)) {
                this.setColIndex(this.depthColumn.get(depth));
            } else {
                ++this.colIndex;
                if (this.colIndex == QueryGrid.this.drawCache.orderColIndex) {
                    return;
                }
                ++this.fieldIndex;
                QueryGrid.this.grid.insertColumns(this.colIndex, 1, this.colIndex - 1);
                if (this.rowOrColToSubs != null) {
                    LinkedHashMap<Integer, List<Integer>> rowOrColToSubsNew = new LinkedHashMap<Integer, List<Integer>>();
                    for (Integer key : this.rowOrColToSubs.keySet()) {
                        List<Integer> subsVal = this.rowOrColToSubs.get(key);
                        rowOrColToSubsNew.put(key + 1, subsVal);
                    }
                    this.rowOrColToSubs = rowOrColToSubsNew;
                }
                this.depthColumn.put(depth, this.colIndex);
                this.fieldIndex = this.fieldIndex < this.colIndex + 1 ? this.colIndex + 1 : this.fieldIndex;
            }
        }
    }
}

