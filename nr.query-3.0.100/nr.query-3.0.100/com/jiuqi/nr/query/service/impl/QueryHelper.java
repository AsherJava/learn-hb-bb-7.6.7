/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.GradeLinkItem
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IFieldValueProcessor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.setting.DataRegTotalInfo
 *  com.jiuqi.np.dataengine.setting.GradeTotalItem
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.sql.SummarizingMethod
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.common.summary.SummaryScheme
 *  com.jiuqi.nr.data.engine.dataquery.IDataQueryProvider
 *  com.jiuqi.nr.data.engine.dataquery.IMultiPeriodQuery
 *  com.jiuqi.nr.data.engine.validation.CompareType
 *  com.jiuqi.nr.data.engine.validation.DataValidationExpression
 *  com.jiuqi.nr.data.engine.validation.DataValidationExpressionFactory
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize
 *  io.netty.util.internal.StringUtil
 *  javax.servlet.http.HttpServletRequest
 *  org.joda.time.DateTime
 *  org.json.JSONObject
 *  org.springframework.web.context.request.RequestAttributes
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.nr.query.service.impl;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.GradeLinkItem;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.DataRegTotalInfo;
import com.jiuqi.np.dataengine.setting.GradeTotalItem;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.sql.SummarizingMethod;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.common.summary.SummaryScheme;
import com.jiuqi.nr.data.engine.dataquery.IDataQueryProvider;
import com.jiuqi.nr.data.engine.dataquery.IMultiPeriodQuery;
import com.jiuqi.nr.data.engine.validation.CompareType;
import com.jiuqi.nr.data.engine.validation.DataValidationExpression;
import com.jiuqi.nr.data.engine.validation.DataValidationExpressionFactory;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.query.block.DimensionItemScop;
import com.jiuqi.nr.query.block.FilterSymbols;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QueryEntityData;
import com.jiuqi.nr.query.block.QueryFieldCondition;
import com.jiuqi.nr.query.block.QueryFilterDefine;
import com.jiuqi.nr.query.block.QueryGridExtension;
import com.jiuqi.nr.query.block.QueryItemSortDefine;
import com.jiuqi.nr.query.block.QuerySelectField;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.block.QuerySortType;
import com.jiuqi.nr.query.block.QueryStatisticsItem;
import com.jiuqi.nr.query.block.UploadStateDimension;
import com.jiuqi.nr.query.common.GridBlockType;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.common.QuerySelectionType;
import com.jiuqi.nr.query.common.SummarySchemeUtils;
import com.jiuqi.nr.query.service.QueryEntityUtil;
import com.jiuqi.nr.query.service.impl.DataQueryHelper;
import com.jiuqi.nr.query.service.impl.DimensionInfor;
import com.jiuqi.nr.query.service.impl.MeasureFieldValueProcessor;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize;
import io.netty.util.internal.StringUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.invoke.LambdaMetafactory;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class QueryHelper {
    public static final String QUERYCONST_ENTITYCOLUMNNUMBER = "EntityColumnNumber";
    public static final String ROOTGROUPID = "b8079ac0-dc15-11e8-969b-64006a6432d8";
    public static final String TIKETNAME = "ticket";
    public static final String SERVERIDENTIFY = "&as";
    private static final Logger logger = LoggerFactory.getLogger(QueryHelper.class);
    public static final String DT = "dt";
    private static IRuntimeDataSchemeService iRuntimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
    private static DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
    private static IColumnModelFinder columnModelFinder = (IColumnModelFinder)BeanUtil.getBean(IColumnModelFinder.class);
    public static IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
    private static IDataAccessProvider dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
    private static IDataQueryProvider dataQueryProvider = (IDataQueryProvider)BeanUtil.getBean(IDataQueryProvider.class);
    static IDataAssist dataAssist = dataAccessProvider.newDataAssist(new ExecutorContext(dataDefinitionRuntimeController));
    IRunTimeViewController viewController;
    private static IEntityViewRunTimeController entityViewRunTimeController;
    private ExecutorContext executorContext;
    private static QueryContext qContext;
    private static IPeriodProvider customPeriodTable;
    boolean isAutoGather;
    private static QueryEntityUtil queryEntityUtil;
    private static IEntityMetaService entityMetaService;
    private IPeriodEntityAdapter periodEntityAdapter;
    private static final String COLOR16_REGEX = "^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$";
    private static final Pattern pattern;

    public QueryHelper() {
        dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
        this.viewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.executorContext = new ExecutorContext(dataDefinitionRuntimeController);
        entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        this.periodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
        try {
            qContext = new QueryContext(this.executorContext, null);
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static String getCacheKey(String tenant, String userId) {
        String sessionId = QueryHelper.getHttpSessionId();
        return sessionId + "_" + tenant + "_" + userId;
    }

    public static IEntityTable getEntityTable(String entityId) {
        EntityViewDefine entityViewDefine = entityViewRunTimeController.buildEntityView(entityId);
        return QueryHelper.getEntityTable(entityViewDefine);
    }

    public static EntityViewDefine getEntityView(String entityId) {
        EntityViewDefine entityView = entityViewRunTimeController.buildEntityView(entityId);
        return entityView;
    }

    public static int getDetailCount(IReadonlyTable dataTable) {
        int totalCount = 0;
        int rowCount = dataTable.getTotalCount();
        for (int i = 0; i < rowCount; ++i) {
            IDataRow row = dataTable.getItem(i);
            if (row.getGroupingFlag() != -1) continue;
            ++totalCount;
        }
        return totalCount;
    }

    private static String getHttpSessionId() {
        HttpServletRequest request;
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes instanceof ServletRequestAttributes && (request = ((ServletRequestAttributes)attributes).getRequest()) != null) {
            if (DT.equals(request.getHeader("isDesktop"))) {
                return DT;
            }
            return request.getSession().getId();
        }
        return null;
    }

    public static IEntityTable getEntityTable(EntityViewDefine entityView) {
        return queryEntityUtil.getEntityTable(entityView, null, null, null, true, AuthorityType.Read);
    }

    public static IEntityTable getEntityTable(EntityViewDefine entityView, ReloadTreeInfo reloadTreeInfo) {
        return queryEntityUtil.getEntityTable(entityView, null, null, reloadTreeInfo, true, AuthorityType.Read);
    }

    public static IEntityTable getEntityTable(EntityViewDefine entityView, DimensionValueSet masterKeys) {
        return queryEntityUtil.getEntityTable(entityView, masterKeys, null, null, true, AuthorityType.Read);
    }

    public static IEntityTable getEntityTableOnce(EntityViewDefine entityView, DimensionValueSet masterKeys) {
        return queryEntityUtil.getEntityTableUseCache(entityView, masterKeys, AuthorityType.Read);
    }

    public static IEntityTable getEntityTable(EntityViewDefine entityView, DimensionValueSet masterKeys, ReloadTreeInfo reloadTreeInfo) {
        return queryEntityUtil.getEntityTable(entityView, masterKeys, null, reloadTreeInfo, true, AuthorityType.Read);
    }

    public static IEntityTable getEntityTable(EntityViewDefine entityView, DimensionValueSet masterKeys, String rowFilter) {
        return queryEntityUtil.getEntityTable(entityView, masterKeys, rowFilter, null, true, AuthorityType.Read);
    }

    public static IEntityTable getEntityTable(EntityViewDefine entityView, DimensionValueSet masterKeys, String rowFilter, ReloadTreeInfo reloadTreeInfo) {
        return queryEntityUtil.getEntityTable(entityView, masterKeys, rowFilter, reloadTreeInfo, true, AuthorityType.Read);
    }

    public static String getDimName(EntityViewDefine entityView) {
        IDataAssist dataAssist = dataAccessProvider.newDataAssist(new ExecutorContext(dataDefinitionRuntimeController));
        String dimName = dataAssist.getDimensionName(entityView);
        return dimName;
    }

    public static String getDimName(FieldDefine field) {
        IDataAssist dataAssist = dataAccessProvider.newDataAssist(new ExecutorContext(dataDefinitionRuntimeController));
        String dimName = dataAssist.getDimensionName(field);
        return dimName;
    }

    public int htmlColorToInt(String color) {
        Matcher matcher = pattern.matcher(color);
        if (!matcher.matches()) {
            color = "#0F0F0F";
        }
        return Integer.parseInt(color.substring(1), 16);
    }

    public static int convertToHtmlDataType(FieldType type) {
        switch (type) {
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_INTEGER: 
            case FIELD_TYPE_DECIMAL: {
                return 1;
            }
            case FIELD_TYPE_GENERAL: 
            case FIELD_TYPE_STRING: 
            case FIELD_TYPE_TEXT: {
                return 10;
            }
            case FIELD_TYPE_DATE: 
            case FIELD_TYPE_DATE_TIME: 
            case FIELD_TYPE_TIME_STAMP: 
            case FIELD_TYPE_TIME: {
                return 2;
            }
            case FIELD_TYPE_LOGIC: {
                return 3;
            }
            case FIELD_TYPE_UUID: {
                return 4;
            }
        }
        return 10;
    }

    public static Map<String, String> getPeriodByDim(QueryBlockDefine block, QueryDimensionDefine dimension, QueryDimensionDefine conditonDim, PeriodType type, String startPeriod, String endPeriod, IPeriodProvider custable) {
        Map<String, String> periods;
        block12: {
            customPeriodTable = custable;
            if (conditonDim == null) {
                boolean flag = !StringUtils.isEmpty((String)startPeriod) && !StringUtils.isEmpty((String)endPeriod);
                try {
                    return flag ? QueryHelper.getTimeFromToEnd(startPeriod, endPeriod, type) : QueryHelper.getPeriodsData(type);
                }
                catch (Exception e) {
                    LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u9ed8\u8ba4\u65e5\u671f\u9519\u8bef");
                    logger.error(e.getMessage(), e);
                }
            }
            List<QuerySelectField> selectFieldsInBlock = QueryHelper.getSelectFieldsInBlock(block);
            boolean existSelectByField = selectFieldsInBlock.stream().anyMatch(QuerySelectField::isSelectByField);
            List<QuerySelectItem> selectedItems = conditonDim.getSelectItems();
            periods = new LinkedHashMap<String, String>();
            try {
                if (existSelectByField) {
                    periods = QueryHelper.getPeriodsData(type);
                    break block12;
                }
                if (selectedItems != null && selectedItems.size() > 0) {
                    switch (conditonDim.getSelectType()) {
                        case MULTIITES: 
                        case SINGLE: {
                            for (QuerySelectItem period : selectedItems) {
                                if (PeriodType.CUSTOM == type) {
                                    String title = custable.getPeriodTitle(period.getCode());
                                    LinkedHashMap<String, String> cperiod = new LinkedHashMap<String, String>();
                                    cperiod.put(period.getCode(), title);
                                    periods.putAll(cperiod);
                                    continue;
                                }
                                periods.putAll(QueryHelper.parseToPeriod(period.getCode(), type));
                            }
                            break block12;
                        }
                        default: {
                            String startTime = selectedItems.get(0).getCode();
                            String endTime = selectedItems.get(1).getCode();
                            periods = QueryHelper.getTimeFromToEnd(startTime, endTime, type);
                        }
                    }
                    break block12;
                }
                periods = QueryHelper.getPeriodsData(type);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return periods;
    }

    public static List<QuerySelectField> getSelectFieldsInBlock(QueryBlockDefine block) {
        Optional<QueryDimensionDefine> fieldDim;
        List<QuerySelectField> selectedFields = new ArrayList<QuerySelectField>();
        List<QueryDimensionDefine> dims = block.getQueryDimensions();
        if (dims != null && (fieldDim = dims.stream().filter(idx -> idx.getDimensionType() == QueryDimensionType.QDT_FIELD).findFirst()).isPresent()) {
            QueryDimensionDefine fd = fieldDim.get();
            selectedFields = fd.getSelectFields();
        }
        return selectedFields;
    }

    public static void setSelectFieldsInBlock(QueryBlockDefine block, List<QuerySelectField> selectedFields) {
        Optional<QueryDimensionDefine> fieldDim;
        List<QueryDimensionDefine> dims = block.getQueryDimensions();
        if (dims != null && (fieldDim = dims.stream().filter(idx -> idx.getDimensionType() == QueryDimensionType.QDT_FIELD).findFirst()).isPresent()) {
            QueryDimensionDefine fd = fieldDim.get();
            fd.setSelectFields(selectedFields);
        }
    }

    public static void setDataRowStyle(Grid2Data grid, int row, int colStart, int colCount) {
        for (int i = colStart; i < colCount; ++i) {
            GridCellData headerCell = grid.getGridCellData(i, row);
            headerCell.setFitFontSize(true);
            headerCell.setForeGroundColor(0x626262);
            headerCell.setFontSize(13);
            headerCell.setBackGroundColor(0xFFFFFF);
            headerCell.setSelectable(false);
            headerCell.setBottomBorderStyle(1);
            headerCell.setRightBorderStyle(1);
        }
    }

    public static Grid2Data createDetailFormStyle(QueryBlockDefine block, boolean isDetailed, boolean isLoadData) throws Exception {
        LinkedHashMap columnWidths = new LinkedHashMap();
        List<QuerySelectField> selectedFields = QueryHelper.getSelectFieldsInBlock(block);
        List showedFields = selectedFields.stream().filter(field -> !field.isHidden()).collect(Collectors.toList());
        int fieldSize = showedFields.size();
        boolean hasStaticRow = false;
        int staticColCount = 0;
        boolean rowHeaderCount = true;
        Grid2Data gridData = new Grid2Data();
        int defaultColumn = fieldSize + staticColCount + 1;
        if (defaultColumn <= 1) {
            gridData.setColumnCount(2);
            gridData.setRowCount(1);
            GridCellData headerCell = gridData.getGridCellData(1, 0);
            headerCell.setFitFontSize(false);
            headerCell.setSilverHead(true);
            headerCell.setBottomBorderStyle(1);
            headerCell.setRightBorderStyle(1);
            headerCell.setSelectable(false);
            headerCell.setPersistenceData("fontSize", String.valueOf(12));
            headerCell.setForeGroundColor(0x444444);
            headerCell.setFontSize(13);
            headerCell.setBackGroundColor(0xF8F8F8);
            headerCell.setShowText(" ");
            headerCell.setEditText(" ");
            headerCell.setHorzAlign(3);
            gridData.setColumnWidth(1, 100);
        } else {
            gridData.setColumnCount(defaultColumn);
            if (isDetailed && isLoadData) {
                gridData.setRowCount(hasStaticRow ? 2 : 1);
            } else {
                gridData.setRowCount(hasStaticRow ? 3 : 2);
                for (int j = 1; j < defaultColumn; ++j) {
                    GridCellData headerCell = gridData.getGridCellData(j, hasStaticRow ? 2 : 1);
                    headerCell.setFitFontSize(true);
                    headerCell.setForeGroundColor(0x626262);
                    headerCell.setFontSize(13);
                    headerCell.setBackGroundColor(0xFFFFFF);
                    headerCell.setSelectable(false);
                    headerCell.setBottomBorderStyle(1);
                    headerCell.setRightBorderStyle(1);
                }
            }
            int fieldIndex = 0;
            fieldIndex = 0;
            for (int i = 1; i < defaultColumn; ++i) {
                if (fieldIndex > showedFields.size()) continue;
                int fieldCol = i;
                QuerySelectField field2 = (QuerySelectField)showedFields.get(fieldIndex);
                GridCellData headerCell = QueryHelper.setCell(gridData, 0, i, field2);
                String title = field2.getTitle();
                if (columnWidths.containsKey(title)) {
                    gridData.setColumnWidth(i, ((Integer)columnWidths.get(title)).intValue());
                } else {
                    gridData.setColumnWidth(i, 100);
                }
                ++fieldIndex;
            }
        }
        gridData.setColumnHidden(0, true);
        gridData.setHeaderRowCount(hasStaticRow ? 2 : 1);
        return gridData;
    }

    private static GridCellData setCell(Grid2Data gridData, int row, int col, QuerySelectField field) {
        GridCellData headerCell = gridData.getGridCellData(col, row);
        headerCell.setSilverHead(true);
        headerCell.setFitFontSize(false);
        headerCell.setBottomBorderStyle(1);
        headerCell.setRightBorderStyle(1);
        headerCell.setSelectable(false);
        headerCell.setPersistenceData("fontSize", String.valueOf(12));
        headerCell.setForeGroundColor(0x444444);
        headerCell.setFontSize(13);
        headerCell.setBackGroundColor(0xF8F8F8);
        headerCell.setShowText(field.getTitle());
        headerCell.setEditText(field.getTitle());
        headerCell.setHorzAlign(3);
        headerCell.setPersistenceData("type", String.valueOf(QueryHelper.convertToHtmlDataType(field.getFiledType())));
        return headerCell;
    }

    public static Grid2Data getHFormStyle(QueryBlockDefine block, boolean withData, boolean isTree) {
        try {
            Grid2Data gridData = QueryHelper.createDetailFormStyle(block, false, false);
            if (isTree) {
                gridData.insertColumns(1, 1);
                GridCellData cell = gridData.getGridCellData(1, 0);
                cell.setShowText(" ");
                cell.setEditText(" ");
                cell.setBottomBorderStyle(1);
                cell.setRightBorderStyle(1);
                cell.setForeGroundColor(0);
                cell.setSilverHead(true);
            }
            if (withData) {
                return gridData;
            }
            return gridData;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Grid2Data getHRowDirectionFormStyle(QueryBlockDefine block) {
        try {
            Grid2Data gridData = QueryHelper.createDetailFormStyle(block, false, false);
            int fieldSize = QueryHelper.getSelectFieldsInBlock(block).stream().filter(field -> !field.isHidden()).collect(Collectors.toList()).size();
            gridData.deleteColumns(1, fieldSize);
            gridData.insertColumns(1, 1);
            GridCellData cell = gridData.getGridCellData(1, 0);
            cell.setShowText(" ");
            cell.setEditText(" ");
            cell.setBottomBorderStyle(1);
            cell.setRightBorderStyle(1);
            cell.setForeGroundColor(0);
            cell.setSilverHead(true);
            return gridData;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String getValue(FieldDefine curent, String valStr) {
        if (valStr == "\u2014\u2014" || StringUtil.isNullOrEmpty((String)valStr)) {
            return valStr;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0" + QueryHelper.getDigits(curent.getFractionDigits()));
        switch (curent.getType()) {
            case FIELD_TYPE_DECIMAL: {
                BigDecimal decimalValue = Convert.toBigDecimal((Object)valStr);
                BigDecimal deciValue = decimalValue.setScale((int)curent.getFractionDigits(), 4);
                return decimalFormat.format(deciValue);
            }
            case FIELD_TYPE_FLOAT: {
                double floatValue = Convert.toDouble((String)valStr);
                Double dbVal = Round.callFunction((Number)floatValue, (int)curent.getFractionDigits());
                return decimalFormat.format(dbVal);
            }
            case FIELD_TYPE_INTEGER: {
                Integer iVal = Convert.toInt((String)valStr);
                return decimalFormat.format(iVal);
            }
        }
        return valStr;
    }

    public static String getDigits(int size) {
        String digitStr = "";
        for (int i = 0; i < size; ++i) {
            digitStr = digitStr + "0";
        }
        if (!StringUtil.isNullOrEmpty((String)digitStr)) {
            return "." + digitStr;
        }
        return digitStr;
    }

    public static void setDataTypeByFieldType(GridCellData cell, FieldDefine field, Boolean isCustomer) {
        int digit = 2;
        int fieldType = 1;
        if (field == null) {
            cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
            cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.Text)));
        } else {
            fieldType = field.getType().getValue();
            digit = field.getFractionDigits();
        }
        String digitStr = "";
        for (int i = 0; i < digit; ++i) {
            if (i == 0) {
                digitStr = digitStr + ".";
            }
            digitStr = digitStr + "0";
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
                cell.setFormatter("yyyy-mm-dd");
                cell.setPersistenceData("formatter", "yyyy-mm-dd");
                cell.setPersistenceData("dataType", String.valueOf(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime)));
                break;
            }
            case 6: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
                cell.setFormatter("yyyy/m/d h:mm");
                cell.setPersistenceData("formatter", "yyyy/m/d h:mm");
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

    @Deprecated
    public static Map<String, DimensionInfor> getMasterDimensions(QueryBlockDefine block) {
        LinkedHashMap<String, DimensionInfor> allDimension = new LinkedHashMap<String, DimensionInfor>();
        try {
            String[] keys;
            for (String key : keys = block.getQueryMasterKeys().split(";")) {
                EntityViewDefine entityView = QueryHelper.getEntityView(key);
                IEntityDefine iEntityDefine = entityMetaService.queryEntity(key);
                if (entityView == null) continue;
                TableModelDefine tableDefine = queryEntityUtil.getEntityTablelDefineByView(key);
                DimensionInfor dim = new DimensionInfor();
                String dwDimName = dataAssist.getDimensionName(entityView);
                dim.setTitle(tableDefine.getTitle() == null ? iEntityDefine.getTitle() : tableDefine.getTitle());
                dim.setName(dwDimName);
                dim.setTableName(tableDefine.getName());
                dim.setTableTitle(tableDefine.getTitle());
                LinkedHashMap<String, IEntityRow> dimensionValueMap = new LinkedHashMap<String, IEntityRow>();
                if (queryEntityUtil.getEntityTablelKindByView(key) != TableKind.TABLE_KIND_ENTITY_PERIOD) {
                    IEntityTable et = QueryHelper.getEntityTable(entityView);
                    List rows = et.getAllRows();
                    for (IEntityRow row : rows) {
                        String dwId = row.getEntityKeyData();
                        if (dimensionValueMap.containsKey(dwId)) continue;
                        dimensionValueMap.put(dwId, row);
                    }
                } else {
                    allDimension.put("PERIOD_DIM", dim);
                }
                if (dimensionValueMap.size() > 0) {
                    dim.setDimensionValue(dimensionValueMap);
                }
                if (allDimension.containsKey(dwDimName)) continue;
                allDimension.put(dwDimName, dim);
            }
            return allDimension;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static boolean isNumField(Object field) {
        if (field == null) {
            return false;
        }
        FieldType type = null;
        if (field instanceof FieldDefine) {
            type = ((FieldDefine)field).getType();
        }
        if (field instanceof QuerySelectField) {
            QuerySelectField selectField;
            if (((QuerySelectField)field).getCustom()) {
                type = FieldType.FIELD_TYPE_DECIMAL;
            }
            if ((type = (selectField = (QuerySelectField)field).getFiledType()) == null) {
                try {
                    TableDefine tableDefine = dataDefinitionRuntimeController.queryTableDefine(selectField.getTableKey());
                    FieldDefine fieldDefine = dataDefinitionRuntimeController.queryFieldByCodeInTable(selectField.getFileExtension(), tableDefine.getKey());
                    if ("DW".equals(selectField.getFileExtension()) && fieldDefine == null) {
                        fieldDefine = dataDefinitionRuntimeController.queryFieldByCodeInTable("MDCODE", tableDefine.getKey());
                    }
                    if (fieldDefine != null) {
                        type = fieldDefine.getType();
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    return false;
                }
            }
        }
        try {
            switch (Objects.requireNonNull(type)) {
                case FIELD_TYPE_FLOAT: 
                case FIELD_TYPE_INTEGER: 
                case FIELD_TYPE_DECIMAL: {
                    return true;
                }
            }
            return false;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static Map<String, String> getAllPeriodsData() {
        PeriodWrapper pw;
        String code;
        Calendar dataTime = Calendar.getInstance();
        LinkedHashMap<String, String> dateList = new LinkedHashMap<String, String>();
        int year = dataTime.get(1);
        for (int i = year - 11; i <= year; ++i) {
            code = i + "N0001";
            pw = PeriodUtil.getPeriodWrapper((String)code);
            dateList.put(code, pw.toTitleString());
            for (int l = 1; l <= 12; ++l) {
                code = i + "Y000" + l;
                pw = PeriodUtil.getPeriodWrapper((String)code);
                dateList.put(code, pw.toTitleString());
            }
        }
        for (int l = 1; l <= 365; ++l) {
            code = year + "R000" + l;
            pw = PeriodUtil.getPeriodWrapper((String)code);
            dateList.put(code, pw.toTitleString());
        }
        return dateList;
    }

    public static Map<String, String> getPeriodsData(PeriodType ptype) {
        int l;
        int i;
        PeriodWrapper pw;
        String code;
        int i2;
        Calendar dataTime = Calendar.getInstance();
        LinkedHashMap<String, String> dateList = new LinkedHashMap<String, String>();
        int year = dataTime.get(1);
        for (i2 = year - 11; i2 <= year; ++i2) {
            code = i2 + "N0001";
            pw = PeriodUtil.getPeriodWrapper((String)code);
            dateList.put(code, pw.toTitleString());
        }
        for (int j = year; j <= year; ++j) {
            for (i = 1; i <= 2; ++i) {
                code = j + "H000" + i;
                pw = PeriodUtil.getPeriodWrapper((String)code);
                dateList.put(code, pw.toTitleString());
            }
        }
        for (i2 = year - 2; i2 <= year; ++i2) {
            for (int l2 = 1; l2 <= 4; ++l2) {
                code = i2 + "J000" + l2;
                pw = PeriodUtil.getPeriodWrapper((String)code);
                dateList.put(code, pw.toTitleString());
            }
        }
        for (l = 1; l <= 12; ++l) {
            code = year + (l < 10 ? "Y000" : "Y00") + l;
            pw = PeriodUtil.getPeriodWrapper((String)code);
            dateList.put(code, pw.toTitleString());
        }
        for (l = 1; l <= 36; ++l) {
            code = year + (l >= 10 ? "X00" : "X000") + l;
            pw = PeriodUtil.getPeriodWrapper((String)code);
            dateList.put(code, pw.toTitleString());
        }
        for (l = 1; l <= 365; ++l) {
            code = year + (l < 10 ? "R000" : "R00") + l;
            pw = PeriodUtil.getPeriodWrapper((String)code);
            dateList.put(code, pw.toTitleString());
        }
        if (ptype == PeriodType.CUSTOM && customPeriodTable != null) {
            List periodItems = customPeriodTable.getPeriodItems();
            for (i = 0; i < periodItems.size(); ++i) {
                IPeriodRow row = (IPeriodRow)periodItems.get(i);
                code = row.getCode();
                pw = PeriodUtil.getPeriodWrapper((String)code);
                dateList.put(code, pw.toTitleString());
            }
        }
        return dateList;
    }

    public static Map<String, String> parseToPeriod(String time, PeriodType periodType) {
        try {
            LinkedHashMap<String, String> periods = new LinkedHashMap<String, String>();
            switch (periodType.type()) {
                case 0: 
                case 1: {
                    int yr = Integer.parseInt(time.substring(0, 4));
                    String code = yr + "N0001";
                    String title = yr + "\u5e74";
                    periods.put(code, title);
                    break;
                }
                case 2: {
                    int year = Integer.parseInt(time.substring(0, 4));
                    int half = Integer.parseInt(time.substring(time.length() - 2, time.length()));
                    if (half == 1) {
                        periods.put(year + "H000" + half, year + "\u5e74\u4e0a\u534a\u5e74");
                        break;
                    }
                    periods.put(year + "H000" + half, year + "\u5e74\u4e0b\u534a\u5e74");
                    break;
                }
                case 3: {
                    int year = Integer.parseInt(time.substring(0, 4));
                    int season = Integer.parseInt(time.substring(time.length() - 2, time.length()));
                    periods.put(year + "J000" + season, year + "\u5e74\u7b2c" + season + "\u5b63");
                    break;
                }
                case 4: {
                    int year = Integer.parseInt(time.substring(0, 4));
                    int month = Integer.parseInt(time.substring(time.length() - 2, time.length()));
                    periods.put(year + "Y00" + (month < 10 ? "0" + month : Integer.valueOf(month)), year + "\u5e74" + month + "\u6708");
                    break;
                }
                case 8: {
                    int year = Integer.parseInt(time.substring(0, 4));
                    int month = Integer.parseInt(time.substring(time.length() - 2, time.length()));
                    periods.put(year + "B00" + (month < 10 ? "0" + month : Integer.valueOf(month)), year + "\u5e74\u7b2c" + month + "\u671f");
                }
            }
            return periods;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Map<String, String> getTimeFromToEnd(String startTime, String endTime, PeriodType periodType) throws Exception {
        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        if (startTime == "" && endTime == "") {
            return QueryHelper.getPeriodsData(periodType);
        }
        int yearStart = Integer.parseInt(startTime.substring(0, 4));
        int yearEnd = Integer.parseInt(endTime.substring(0, 4));
        switch (periodType.type()) {
            case 0: 
            case 1: {
                for (int i = yearStart; i <= yearEnd; ++i) {
                    result.put(i + "N0001", i + "\u5e74");
                }
                break;
            }
            case 4: {
                int monthStart = Integer.parseInt(startTime.substring(startTime.length() - 2, startTime.length()));
                int monthEnd = Integer.parseInt(endTime.substring(endTime.length() - 2, endTime.length()));
                for (int i = yearStart; i <= yearEnd; ++i) {
                    for (int j = 1; j <= 12; ++j) {
                        String code;
                        if (yearStart == yearEnd) {
                            if (monthStart > j || j > monthEnd) continue;
                            code = i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j));
                            result.put(code, i + "\u5e74" + j + "\u6708");
                            continue;
                        }
                        if (yearStart == i) {
                            if (monthStart > j) continue;
                            code = i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j));
                            result.put(code, i + "\u5e74" + j + "\u6708");
                            continue;
                        }
                        if (i == yearEnd) {
                            if (j > monthEnd) continue;
                            code = i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j));
                            result.put(code, i + "\u5e74" + j + "\u6708");
                            continue;
                        }
                        code = i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j));
                        result.put(code, i + "\u5e74" + j + "\u6708");
                    }
                }
                break;
            }
            case 8: {
                int monthStart = Integer.parseInt(startTime.substring(startTime.length() - 2, startTime.length()));
                int monthEnd = Integer.parseInt(endTime.substring(endTime.length() - 2, endTime.length()));
                if (customPeriodTable == null) break;
                List periodItems = customPeriodTable.getPeriodItems();
                for (int i = 0; i < periodItems.size(); ++i) {
                    IPeriodRow row = (IPeriodRow)periodItems.get(i);
                    String code = row.getCode();
                    int curYear = Integer.parseInt(code.substring(0, 4));
                    int curMonth = Integer.parseInt(code.substring(code.length() - 2, code.length()));
                    if (curYear < yearStart || curYear > yearEnd || (curYear == yearStart || curYear == yearEnd) && (curMonth < monthStart || curMonth > monthEnd)) continue;
                    PeriodWrapper pw = PeriodUtil.getPeriodWrapper((String)code);
                    result.put(code, pw.toTitleString());
                }
                break;
            }
            default: {
                return QueryHelper.getPeriodsData(periodType);
            }
        }
        return result;
    }

    public static Map<String, String> getAllTimeFromToEnd(String startTime, String endTime) throws Exception {
        int i;
        int dayStart;
        int dayEnd;
        int monthEnd;
        int monthStart;
        int yearEnd;
        int yearStart;
        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        GregorianCalendar start = PeriodUtil.period2Calendar((String)startTime);
        GregorianCalendar end = PeriodUtil.period2Calendar((String)endTime);
        PeriodWrapper pw = null;
        Calendar ca = Calendar.getInstance();
        int sysYear = ca.get(1);
        int sysMonth = ca.get(2);
        int sysDay = ca.get(6);
        if (startTime == "" && endTime == "") {
            String code = sysYear + "N0001";
            result.put(code, sysYear + "\u5e74");
        } else {
            for (int i2 = Integer.parseInt(startTime.substring(0, 4)); i2 <= Integer.parseInt(endTime.substring(0, 4)); ++i2) {
                result.put(i2 + "N0001", i2 + "\u5e74");
            }
        }
        if (startTime == "" && endTime == "") {
            yearStart = sysYear;
            yearEnd = sysYear;
            monthStart = sysMonth + 1;
            monthEnd = sysMonth + 1;
            dayStart = dayEnd = sysDay;
        } else {
            GregorianCalendar dayc1 = new GregorianCalendar();
            Date startDate = start.getTime();
            dayc1.setTime(startDate);
            yearStart = dayc1.get(1);
            monthStart = dayc1.get(2) + 1;
            dayStart = dayc1.get(6);
            Date endDate = end.getTime();
            dayc1.setTime(endDate);
            yearEnd = dayc1.get(1);
            monthEnd = dayc1.get(2) + 1;
            dayEnd = dayc1.get(6);
        }
        for (i = yearStart; i <= yearEnd; ++i) {
            for (int j = 1; j <= 12; ++j) {
                if (yearStart == yearEnd) {
                    if (monthStart > j || j > monthEnd) continue;
                    result.put(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)), i + "\u5e74" + j + "\u6708");
                    continue;
                }
                if (yearStart == i) {
                    if (monthStart > j) continue;
                    result.put(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)), i + "\u5e74" + j + "\u6708");
                    continue;
                }
                if (i == yearEnd) {
                    if (j > monthEnd) continue;
                    result.put(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)), i + "\u5e74" + j + "\u6708");
                    continue;
                }
                result.put(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)), i + "\u5e74" + j + "\u6708");
            }
        }
        for (i = yearStart; i <= yearEnd; ++i) {
            for (int j = 1; j <= 365; ++j) {
                String code;
                if (yearStart == yearEnd) {
                    if (dayStart > j || j > dayEnd) continue;
                    code = i + (j < 10 ? "R000" : (j > 99 ? "R0" : "R00")) + j;
                    pw = PeriodUtil.getPeriodWrapper((String)code);
                    result.put(code, pw.toTitleString());
                    continue;
                }
                if (yearStart == i) {
                    if (dayStart > j) continue;
                    code = i + (j < 10 ? "R000" : (j > 99 ? "R0" : "R00")) + j;
                    pw = PeriodUtil.getPeriodWrapper((String)code);
                    result.put(code, pw.toTitleString());
                    continue;
                }
                if (i == yearEnd) {
                    if (j > dayEnd) continue;
                    code = i + (j < 10 ? "R000" : (j > 99 ? "R0" : "R00")) + j;
                    pw = PeriodUtil.getPeriodWrapper((String)code);
                    result.put(code, pw.toTitleString());
                    continue;
                }
                code = i + (j < 10 ? "R000" : (j > 99 ? "R0" : "R00")) + j;
                pw = PeriodUtil.getPeriodWrapper((String)code);
                result.put(code, pw.toTitleString());
            }
        }
        return result;
    }

    public static FieldDefine getReferentField(String fieldCode) throws Exception {
        ColumnModelDefine columnModel;
        FieldDefine field = null;
        field = dataDefinitionRuntimeController.queryFieldDefine(fieldCode);
        if (field == null && (columnModel = dataModelService.getColumnModelDefineByID(fieldCode)) != null) {
            field = columnModelFinder.findFieldDefine(columnModel);
        }
        if (field == null) {
            return null;
        }
        if (field.getEntityKey() != null) {
            return QueryHelper.getReferentField(field.getEntityKey());
        }
        return field;
    }

    public static boolean checkIsFilterMode(QuerySelectField selectItem) {
        boolean isFilter = false;
        QueryItemSortDefine sort = selectItem.getSort();
        if (sort != null) {
            ArrayList<Object> fv = sort.getFilterValues();
            boolean bl = isFilter = fv != null && fv.size() > 0;
            if (!isFilter) {
                List<QueryFilterDefine> filters = sort.getFilterCondition();
                isFilter = filters != null && filters.size() > 0;
            }
        }
        return isFilter;
    }

    public static void cleanSortData(List<QuerySelectField> selectFields) {
        for (QuerySelectField selectField : selectFields) {
            if (selectField.getSort() == null) continue;
            ArrayList<Object> fvs = selectField.getSort().getFilterValues();
            List<QueryFilterDefine> filters = selectField.getSort().getFilterCondition();
            if (filters != null && filters.size() > 0) {
                QueryHelper.cleanFieldSortData(selectField);
            } else if (fvs != null && fvs.size() > 0) continue;
            QueryHelper.cleanFieldSortData(selectField);
        }
    }

    public static void cleanFieldSortData(QuerySelectField field) {
        QueryItemSortDefine sortInfor = null;
        sortInfor = field.getSort();
        if (sortInfor != null) {
            sortInfor.setData(new LinkedHashMap<String, String>());
        }
    }

    public static Grid2Data transposeGrid(QueryBlockDefine block, Grid2Data gridData) throws Exception {
        int colSpan;
        int headColCount = gridData.getHeaderColumnCount();
        int columnCount = gridData.getColumnCount();
        int rowCount = gridData.getRowCount();
        int headerRowCount = gridData.getHeaderRowCount();
        QueryGridExtension gridExtension = new QueryGridExtension(block.getBlockExtension());
        GridBlockType gridBlockType = gridExtension.getGridBlockType();
        Grid2Data gridFormData = null;
        if (block.getHasUserForm() && block.getFormdata() != null) {
            ObjectMapper mapper = new ObjectMapper();
            block.setGridData(block.getFormdata());
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Grid2Data.class, (JsonDeserializer)new Grid2DataDeserialize());
            module.addDeserializer(GridCellData.class, (JsonDeserializer)new GridCellDataDeserialize());
            mapper.registerModule((Module)module);
            gridFormData = (Grid2Data)mapper.readValue(block.getGridData(), Grid2Data.class);
            if (gridFormData.getHeaderRowCount() > 1) {
                gridFormData.deleteRows(0, 1);
            }
        } else {
            gridFormData = QueryHelper.createDetailFormStyle(block, true, true);
        }
        gridFormData.setColumnCount(rowCount);
        int col = gridFormData.getRowCount();
        gridFormData.insertRows(0, columnCount - col, 0);
        if (GridBlockType.HORIZONALED.equals((Object)gridBlockType)) {
            gridFormData.setHeaderRowCount(headColCount);
        }
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                GridCellData cell = gridData.getGridCellData(j, i);
                GridCellData newCell = gridFormData.getGridCellData(i, j);
                newCell.copyCellData(cell);
                newCell.copyCellStyle(cell);
                if (!newCell.isMerged() && newCell.getRowSpan() <= 1 && newCell.getColSpan() <= 1 && newCell.getMergeInfo() == null) continue;
                gridFormData.unmergeCell(i, j);
                newCell.setMergeInfo(null);
            }
        }
        while (gridFormData.merges().count() != 0) {
            gridFormData.merges().remove(0);
        }
        gridFormData.setRowHidden(0, true);
        for (int j = 0; j < rowCount; ++j) {
            for (int i = 0; i < columnCount; ++i) {
                GridCellData headCell = gridFormData.getGridCellData(j, i);
                GridCellData cellData = gridData.getGridCellData(i, j);
                if (cellData.getRowSpan() <= 1 && cellData.getColSpan() <= 1) continue;
                int rowSpan = cellData.getRowSpan();
                colSpan = cellData.getColSpan();
                headCell.setRowSpan(colSpan);
                headCell.setColSpan(rowSpan);
            }
        }
        int newRowCount = columnCount;
        int newColumnCont = rowCount;
        if (GridBlockType.DETAILED.equals((Object)gridBlockType)) {
            newColumnCont = headerRowCount + 1;
            newRowCount = columnCount;
        }
        for (int j = 0; j < newColumnCont; ++j) {
            for (int i = 0; i < newRowCount; ++i) {
                GridCellData headCell = gridFormData.getGridCellData(j, i);
                if (headCell.getRowSpan() <= 1 && headCell.getColSpan() <= 1) continue;
                colSpan = headCell.getColSpan();
                int rowSpan = headCell.getRowSpan();
                gridFormData.mergeCells(j, i, colSpan + j - 1, rowSpan + i - 1);
                headCell.setHorzAlign(3);
            }
        }
        if (GridBlockType.HORIZONALED.equals((Object)gridBlockType)) {
            gridFormData.setHeaderColumnCount(headerRowCount);
        }
        return gridFormData;
    }

    public static String serialize(Object value) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            return baos.toString();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public IGroupingTable getMultiPeriodTable(IGroupingQuery groupQuery, QueryBlockDefine block, List<String> periodList) {
        String formScemeKey = null;
        List<QuerySelectField> fields = block.getBlockInfo().getQueryDimensions().get(0).getSelectFields();
        for (QuerySelectField sf : fields) {
            if (sf.getFormSchemeId() == null) continue;
            formScemeKey = sf.getFormSchemeId();
            break;
        }
        ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.viewController, dataDefinitionRuntimeController, entityViewRunTimeController, formScemeKey);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        IMultiPeriodQuery mquery = dataQueryProvider.newMultiPeriodQuery();
        try {
            IGroupingTable table = mquery.getMultiPeriodTable(executorContext, periodList, groupQuery);
            return table;
        }
        catch (Exception ex) {
            logger.error("getMultiPeriodTable error!", ex);
            return null;
        }
    }

    public List<Object> initGroupQuery(QueryBlockDefine block, Map<String, Integer> fieldIndex, boolean isDetail, boolean isExport, Map<String, QueryDimensionDefine> conditions, Map<String, DataLinkDefine> fieldMapLink, boolean hasSum, List<String> periods, List<String> customExpress, boolean isSimpleQuery) {
        ArrayList<Object> result = new ArrayList<Object>();
        try {
            boolean flag;
            TaskDefine taskDefine;
            TaskGatherType taskGatherType;
            QuerySelectField field;
            String taskId;
            Optional<QuerySelectField> numFieldOptional;
            if (conditions == null) {
                conditions = new LinkedHashMap<String, QueryDimensionDefine>();
            }
            boolean isWantDetail = isDetail;
            List<QueryDimensionDefine> dimensions = block.getQueryDimensions();
            IGroupingQuery groupQuery = dataAccessProvider.newGroupingQuery(null);
            groupQuery.setOldQueryModule(true);
            groupQuery.setWantDetail(isWantDetail);
            boolean isGather = false;
            boolean isSorted = false;
            QueryGridExtension gridExtension = new QueryGridExtension(block.getBlockExtension());
            isGather = gridExtension.isShowGather();
            List<QuerySelectField> selectedFields = QueryHelper.getSelectFieldsInBlock(block);
            this.isAutoGather = this.autoGatherCheck(selectedFields, isWantDetail);
            ReloadTreeInfo reloadTreeInfo = null;
            SummaryScheme sumScheme = null;
            sumScheme = gridExtension.getSumSchemeObject();
            if (sumScheme != null) {
                reloadTreeInfo = new SummarySchemeUtils().toReloadTreeInfo(sumScheme);
            }
            LinkedHashMap<String, QuerySelectField> masterOrGroupingFields = new LinkedHashMap<String, QuerySelectField>();
            LinkedHashMap<String, QuerySelectField> allDimFields = new LinkedHashMap<String, QuerySelectField>();
            String fieldCondition = "";
            String blockCondition = null;
            if (selectedFields == null || selectedFields.size() == 0) {
                selectedFields = new ArrayList<QuerySelectField>();
            }
            boolean isFilterMode = false;
            LinkedHashMap<String, String> refFields = new LinkedHashMap<String, String>();
            int entityDimCount = (int)dimensions.stream().filter(d -> d != null && (d.getDimensionType() == QueryDimensionType.QDT_ENTITY || d.getDimensionType() == QueryDimensionType.QDT_DICTIONARY) && d.getLayoutType() != QueryLayoutType.LYT_CONDITION).count();
            boolean hasSortField = selectedFields.stream().anyMatch(sf -> !Boolean.parseBoolean(sf.getIsMaster()) && sf.isSorted);
            boolean fieldSort = hasSortField && !hasSum;
            for (QuerySelectField querySelectField : selectedFields) {
                FieldDefine refField;
                QueryItemSortDefine sortDefine;
                if (StringUtils.isEmpty((String)querySelectField.getCode())) continue;
                if (!hasSortField && "true".equals(querySelectField.getIsMaster()) && !hasSum && querySelectField.getSort() == null) {
                    sortDefine = new QueryItemSortDefine();
                    sortDefine.setSortType(QuerySortType.SORT_ASC);
                    querySelectField.setSort(sortDefine);
                }
                if (!isSorted && (sortDefine = querySelectField.getSort()) != null) {
                    isSorted = querySelectField.getIsSorted();
                }
                boolean isMasterOrGroupingField = Boolean.parseBoolean(querySelectField.getIsMaster()) || querySelectField.getIsGroupField();
                FieldDefine field2 = dataDefinitionRuntimeController.queryFieldDefine(querySelectField.getCode());
                if (this.isAutoGather && Boolean.parseBoolean(querySelectField.getIsMaster())) {
                    field2 = QueryHelper.getReferentField(field2.getKey());
                }
                if (field2 != null) {
                    String dimName = null;
                    dimName = querySelectField.getDimensionName() != null ? querySelectField.getDimensionName() : dataAssist.getDimensionName(field2);
                    allDimFields.put(dimName, querySelectField);
                    if (isMasterOrGroupingField && entityDimCount >= 2) {
                        masterOrGroupingFields.put(dimName, querySelectField);
                        continue;
                    }
                }
                if ((refField = QueryHelper.getReferentField(querySelectField.getCode())) != null) {
                    refFields.put(refField.getCode(), querySelectField.getCode());
                }
                String dimensionName = querySelectField.getDimensionName();
                boolean isConditionField = false;
                if (dimensionName != null && querySelectField.isHidden()) {
                    isConditionField = dimensions.stream().anyMatch(dim -> dim.getLayoutType() == QueryLayoutType.LYT_CONDITION && dimensionName.equals(dim.getDimensionName()));
                }
                if ((querySelectField.isHidden() || isMasterOrGroupingField) && hasSum && field2 != null) {
                    if (querySelectField.getIsGroupField() && hasSum) {
                        int colindex = groupQuery.addGroupColumn(field2);
                        fieldIndex.put(field2.getKey(), colindex);
                        continue;
                    }
                    Optional<QuerySelectField> entityField = selectedFields.stream().filter(e -> Boolean.parseBoolean(e.getIsMaster())).findFirst();
                    String[] split = block.getQueryMastersStr().split(";");
                    String key = split[0];
                    Optional<QueryDimensionDefine> dimensionDefine = dimensions.stream().filter(e -> e.getDimensionType() == QueryDimensionType.QDT_ENTITY && key.equals(e.getViewId())).findFirst();
                    QueryDimensionDefine entityDim = null;
                    if (dimensionDefine.isPresent()) {
                        entityDim = dimensionDefine.get();
                    }
                    boolean isEntityGatherField = this.isAutoGather && entityDim != null && querySelectField.getDimensionName().equals(entityDim.getDimensionName());
                    boolean isMasterField = Boolean.parseBoolean(querySelectField.getIsMaster());
                    int colindex = isMasterField && !isSimpleQuery || isEntityGatherField ? groupQuery.addGroupColumn(field2) : groupQuery.addColumn(field2);
                    fieldIndex.put(field2.getKey(), colindex);
                    continue;
                }
                if (field2 != null && isConditionField) {
                    int colindex = groupQuery.addColumn(field2);
                    fieldIndex.put(field2.getKey(), colindex);
                }
                if (!isFilterMode) {
                    isFilterMode = QueryHelper.checkIsFilterMode(querySelectField);
                }
                if (querySelectField.getCustom()) {
                    int colindex = groupQuery.addExpressionColumn(querySelectField.getCustomValue());
                    fieldIndex.put(querySelectField.getCustomValue(), colindex);
                    groupQuery.setGatherType(colindex, querySelectField.getGatherType());
                    continue;
                }
                SetColumnReturn returnObj = this.setColumn(groupQuery, querySelectField, isDetail, isGather, fieldCondition, fieldIndex, fieldSort, refField);
                fieldIndex = this.setStaticticsColumn(groupQuery, querySelectField, fieldIndex);
                fieldCondition = returnObj.condition;
                fieldIndex = returnObj.columnIndex;
            }
            ArrayList<Integer> customFieldIndex = new ArrayList<Integer>();
            if (customExpress != null && customExpress.size() > 0) {
                for (String express : customExpress) {
                    int colindex = groupQuery.addExpressionColumn(express);
                    fieldIndex.put(express, colindex);
                    customFieldIndex.add(colindex);
                }
            }
            ArrayList<GradeTotalItem> arrayList = new ArrayList<GradeTotalItem>();
            for (int i = 0; i < dimensions.size(); ++i) {
                String[] structs;
                boolean isTreeStruct;
                QueryDimensionDefine dimsion = dimensions.get(i);
                if (dimsion == null || dimsion.getDimensionType() == QueryDimensionType.QDT_FIELD || dimsion.isHidden() || dimsion.getDimensionType() == QueryDimensionType.QDT_CUSTOM || dimsion.getDimensionType() == QueryDimensionType.QDT_GRIDFIELD) continue;
                TaskDefine taskDefine2 = this.viewController.queryTaskDefine(dimsion.getTaskId());
                IEntityDefine entityDefine = entityMetaService.queryEntity(taskDefine2.getDw());
                EntityViewDefine entityView = entityViewRunTimeController.buildEntityView(entityDefine.getId());
                String dimensionName = "";
                TableModelDefine entityTableDefine = null;
                String treeStruct = "";
                if (entityView != null) {
                    entityTableDefine = queryEntityUtil.getEntityTablelDefineByView(entityView.getEntityId());
                    treeStruct = queryEntityUtil.getDicTreeStructByView(entityView.getEntityId());
                }
                if (entityDimCount >= 2 && dimsion.getLayoutType() != QueryLayoutType.LYT_CONDITION && (dimsion.getDimensionType() == QueryDimensionType.QDT_ENTITY || dimsion.getDimensionType() == QueryDimensionType.QDT_DICTIONARY)) {
                    QuerySelectField masterSef = (QuerySelectField)masterOrGroupingFields.get(dimensionName);
                    if (masterSef != null) {
                        FieldDefine refField = QueryHelper.getReferentField(masterSef.getCode());
                        if (refField != null) {
                            refFields.put(refField.getCode(), masterSef.getCode());
                        }
                    }
                    if (hasSum) {
                        QueryItemSortDefine sortDefine;
                        ArrayList<Object> fvs;
                        int colindex;
                        FieldDefine field3 = dataDefinitionRuntimeController.queryFieldDefine(masterSef.getCode());
                        if (masterSef.getIsGroupField()) {
                            colindex = groupQuery.addGroupColumn(field3);
                            fieldIndex.put(field3.getKey(), colindex);
                        } else {
                            colindex = groupQuery.addColumn(field3);
                            fieldIndex.put(field3.getKey(), colindex);
                        }
                        fieldIndex = this.setStaticticsColumn(groupQuery, masterSef, fieldIndex);
                        if (masterSef.getIsSorted() && (fvs = (sortDefine = masterSef.getSort()).getFilterValues()) != null && fvs.size() > 0) {
                            if (Boolean.parseBoolean(masterSef.getIsMaster())) {
                                String dimName = masterSef.getDimensionName();
                                if (masterSef.getDimensionName() == null) {
                                    dimName = dataAssist.getDimensionName(field3);
                                }
                                DimensionValueSet masterFilerSet = new DimensionValueSet();
                                masterFilerSet.setValue(dimName, fvs);
                                groupQuery.setMasterKeys(masterFilerSet);
                            } else {
                                groupQuery.setColumnFilterValueList(colindex, fvs);
                            }
                        }
                    }
                }
                boolean bl = isTreeStruct = !StringUtils.isEmpty((String)treeStruct);
                if (!isTreeStruct || dimsion.getLayoutType() == QueryLayoutType.LYT_CONDITION || (structs = treeStruct.split(",|;")).length <= 1) continue;
                try {
                    ArrayList<Integer> levels = new ArrayList<Integer>();
                    for (int l = 1; l <= structs.length; ++l) {
                        levels.add(l);
                    }
                    FieldDefine dimensionField = dataAssist.getDimensionField(entityTableDefine.getName(), dimensionName);
                    String field4 = (String)refFields.get(dimensionField.getCode());
                    GradeLinkItem linkItem = new GradeLinkItem();
                    linkItem.setEntityView(entityView);
                    linkItem.setKey(fieldMapLink.get(field4).getKey());
                    linkItem.setLinkExpression(field4);
                    GradeTotalItem item2 = new GradeTotalItem(linkItem, fieldIndex.get(field4).intValue(), levels);
                    arrayList.add(item2);
                    continue;
                }
                catch (Exception e2) {
                    logger.error("\u521b\u5efa\u5206\u7ec4\u67e5\u8be2\u2014\u521d\u59cb\u5316\u5206\u7ea7\u5408\u8ba1\u914d\u7f6e\u65f6\u62a5\u9519");
                }
            }
            DataRegTotalInfo regTotalInfo = new DataRegTotalInfo(arrayList);
            for (GradeTotalItem item3 : arrayList) {
                groupQuery.addGroupColumn(item3.getColumnIndex().intValue());
            }
            groupQuery.setDataRegTotalInfo(regTotalInfo);
            blockCondition = this.setConditions(groupQuery, block, blockCondition, conditions, allDimFields, fieldIndex, periods, reloadTreeInfo, this.isAutoGather);
            groupQuery.setWantDetail(block.isShowDetail() || isDetail);
            if (!StringUtil.isNullOrEmpty((String)fieldCondition)) {
                blockCondition = StringUtil.isNullOrEmpty((String)blockCondition) ? fieldCondition : String.format("{1} OR {2}", blockCondition, fieldCondition);
            }
            if (!StringUtil.isNullOrEmpty((String)blockCondition)) {
                groupQuery.setRowFilter(blockCondition);
            }
            if ((numFieldOptional = selectedFields.stream().filter(e -> QueryHelper.isNumField(e)).findFirst()).isPresent() && isWantDetail && (taskId = (field = numFieldOptional.get()).getTaskId()) != null && !"".equals(taskId) && (taskGatherType = (taskDefine = this.viewController.queryTaskDefine(taskId)).getTaskGatherType()) == TaskGatherType.TASK_GATHER_AUTO) {
                this.isAutoGather = true;
                Optional<QueryDimensionDefine> dimensionDefineOptional = dimensions.stream().filter(dim -> dim.getDimensionType() == QueryDimensionType.QDT_ENTITY).findFirst();
                if (dimensionDefineOptional.isPresent()) {
                    QueryDimensionDefine queryDimensionDefine = dimensionDefineOptional.get();
                    EntityViewDefine entityView = entityViewRunTimeController.buildEntityView(queryDimensionDefine.getViewId());
                    Optional<QueryDimensionDefine> optional = dimensions.stream().filter(e -> queryDimensionDefine.getDimensionName().equals(e.getDimensionName()) && !CollectionUtils.isEmpty(e.getSelectItems())).findFirst();
                    if (optional.isPresent()) {
                        QueryDimensionDefine define = optional.get();
                        QuerySelectItem querySelectItem = define.getSelectItems().get(0);
                        String entity = querySelectItem.getCode();
                        int index = 0;
                        Optional<QuerySelectField> fieldOptional = selectedFields.stream().filter(item -> Boolean.parseBoolean(field.getIsMaster())).findFirst();
                        if (fieldOptional.isPresent()) {
                            QuerySelectField masterField = fieldOptional.get();
                            index = fieldIndex.get(masterField.getCode());
                        }
                        groupQuery.setEntityLevelGather(entity, index, entityView, null, reloadTreeInfo);
                    }
                }
            }
            groupQuery.setSortGroupingAndDetailRows(true);
            boolean bl = flag = isDetail && !isExport;
            if (hasSum) {
                groupQuery.setSummarizingMethod(SummarizingMethod.RollUp);
            }
            result.add(groupQuery);
            result.add(fieldIndex);
            result.add(isSorted);
            result.add(customFieldIndex);
            return result;
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u521d\u59cb\u5316\u5206\u7ec4\u67e5\u8be2\u5f02\u5e38", (String)ex.getMessage());
            logger.error("\u521b\u5efa\u5206\u7ec4\u67e5\u8be2\u5f02\u5e38", (Object)ex.getMessage());
            return null;
        }
    }

    private List<QuerySelectItem> getSelectItem(List<QuerySelectItem> dimItems, List<QuerySelectItem> conditionItem) {
        if (conditionItem != null && conditionItem.size() >= 0) {
            if (dimItems == null || dimItems.size() == 0) {
                dimItems = conditionItem;
            } else {
                dimItems.retainAll(conditionItem);
            }
        }
        return dimItems;
    }

    public void initPeriods(List<String> periodCodes, Map<String, String> periodTitles, QueryDimensionDefine conditionDim, QueryDimensionDefine periodDim) {
    }

    public String getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        Date currYearFirst = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(currYearFirst);
        return dateStr;
    }

    public List<String> queryPeriodsInDim(List<QueryDimensionDefine> dims, String defaultStartTime, String defaultEndTime, String periodType) {
        try {
            PeriodType ptype;
            ArrayList<String> selectDateList;
            DataQueryHelper queryHelper;
            block22: {
                Optional<QueryDimensionDefine> dataTimeConDim;
                block23: {
                    String endTime;
                    Object startTime;
                    Object ptype2;
                    List<QuerySelectItem> timeSelect;
                    if (StringUtil.isNullOrEmpty((String)defaultStartTime) || defaultStartTime.equals("null")) {
                        Calendar currCal = Calendar.getInstance();
                        int currentYear = currCal.get(1);
                        defaultStartTime = this.getYearFirst(currentYear - 10);
                        defaultEndTime = this.getYearFirst(currentYear + 10);
                    }
                    queryHelper = new DataQueryHelper();
                    Optional<QueryDimensionDefine> dataTimeDim = dims.stream().filter(d -> d.getDimensionType() != QueryDimensionType.QDT_FIELD && d.isPeriodDim() && d.getLayoutType() != QueryLayoutType.LYT_CONDITION).findFirst();
                    dataTimeConDim = dims.stream().filter(d -> d.getDimensionType() != QueryDimensionType.QDT_FIELD && TableKind.TABLE_KIND_ENTITY_PERIOD.toString().equals(d.getTableKind()) && d.getLayoutType() == QueryLayoutType.LYT_CONDITION && d.getPeriodType() != null).findFirst();
                    selectDateList = null;
                    if (dataTimeDim.isPresent() && dataTimeDim.get().getSelectItems() != null && !dataTimeDim.get().getSelectItems().isEmpty()) {
                        switch (QuerySelectionType.MULTIITES) {
                            case MULTIITES: 
                            case SINGLE: {
                                selectDateList = new ArrayList();
                                timeSelect = dataTimeDim.get().getSelectItems();
                                for (QuerySelectItem seItem : timeSelect) {
                                    selectDateList.add(seItem.getCode());
                                }
                                break;
                            }
                            case RANGE: 
                            case NONE: {
                                ptype2 = PeriodType.valueOf((String)periodType.toUpperCase());
                                startTime = dataTimeDim.get().getSelectItems().get(0).getCode();
                                endTime = dataTimeDim.get().getSelectItems().get(1).getCode();
                                selectDateList = PeriodUtil.getPeriodCodes((String)startTime, (String)endTime, (PeriodType)ptype2);
                                break;
                            }
                        }
                    }
                    if (!dataTimeConDim.isPresent() || dataTimeConDim.get().getSelectItems() == null || dataTimeConDim.get().getSelectItems().isEmpty()) break block22;
                    if (selectDateList != null) break block23;
                    switch (dataTimeConDim.get().getSelectType()) {
                        case MULTIITES: 
                        case SINGLE: {
                            selectDateList = new ArrayList<String>();
                            timeSelect = dataTimeConDim.get().getSelectItems();
                            for (QuerySelectItem seItem : timeSelect) {
                                selectDateList.add(seItem.getCode());
                            }
                            break block22;
                        }
                        default: {
                            ptype2 = PeriodType.valueOf((String)periodType.toUpperCase());
                            startTime = dataTimeConDim.get().getSelectItems().get(0).getCode();
                            endTime = dataTimeConDim.get().getSelectItems().get(1).getCode();
                            selectDateList = PeriodUtil.getPeriodCodes((String)startTime, (String)endTime, (PeriodType)ptype2);
                        }
                    }
                    break block22;
                }
                ArrayList conditionDateList = new ArrayList();
                switch (dataTimeConDim.get().getSelectType()) {
                    case MULTIITES: 
                    case SINGLE: {
                        List<QuerySelectItem> timeSelect = dataTimeConDim.get().getSelectItems();
                        for (QuerySelectItem seItem : timeSelect) {
                            if (!selectDateList.contains(seItem.getCode())) continue;
                            conditionDateList.add(seItem.getCode());
                        }
                        selectDateList = conditionDateList;
                        break;
                    }
                    case RANGE: 
                    case NONE: {
                        ptype = PeriodType.valueOf((String)periodType.toUpperCase());
                        String startTime = dataTimeConDim.get().getSelectItems().get(0).getCode();
                        String endTime = dataTimeConDim.get().getSelectItems().get(1).getCode();
                        conditionDateList = PeriodUtil.getPeriodCodes((String)startTime, (String)endTime, (PeriodType)ptype);
                        ArrayList<String> dateList = new ArrayList<String>();
                        for (String date : conditionDateList) {
                            if (!selectDateList.contains(date)) continue;
                            dateList.add(date);
                        }
                        selectDateList = dateList;
                        break;
                    }
                }
            }
            if (selectDateList == null) {
                if (StringUtil.isNullOrEmpty((String)defaultStartTime) || StringUtil.isNullOrEmpty((String)defaultEndTime)) {
                    PeriodType ptype3 = PeriodType.valueOf((String)periodType);
                    selectDateList = queryHelper.getDefaultPeriodsDataFromBlock(defaultStartTime, defaultEndTime, ptype3, customPeriodTable);
                } else {
                    PeriodWrapper pw = new PeriodWrapper(defaultStartTime);
                    int type = pw.getType();
                    ptype = PeriodType.fromType((int)type);
                    selectDateList = PeriodUtil.getPeriodCodes((String)defaultStartTime, (String)defaultEndTime, (PeriodType)ptype);
                }
            }
            return selectDateList;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String queryCoverFieldDimVSet(QueryBlockDefine block) {
        try {
            List<QueryDimensionDefine> dims = block.getBlockInfo().getQueryDimensions();
            List masterFields = dims.get(0).getSelectFields().stream().filter(e -> "true".equals(e.getIsMaster())).collect(Collectors.toList());
            StringBuffer filterStr = new StringBuffer();
            int index = 0;
            for (QueryDimensionDefine dim : dims) {
                List<QuerySelectItem> items;
                boolean isCoverFieldDim;
                if (QueryDimensionType.QDT_FIELD.equals((Object)dim.getDimensionType()) || !(isCoverFieldDim = this.isRelatedWithMaster(block, dim)) || (items = dim.getSelectItems()) == null || items.isEmpty()) continue;
                Optional<QuerySelectField> coverFieldOptional = dims.get(0).getSelectFields().stream().filter(item -> item.getDimensionName() != null && item.getDimensionName().equals(dim.getDimensionName())).findFirst();
                QuerySelectField field = coverFieldOptional.get();
                if (index != 0) {
                    filterStr.append(" and ");
                }
                if (items.size() == 1) {
                    filterStr = filterStr.append(field.getTableName()).append("[").append(field.getFileExtension()).append("]=").append("'").append(items.get(0).getCode()).append("'");
                } else {
                    filterStr = filterStr.append("inlist(").append(field.getTableName()).append("[").append(field.getFileExtension()).append("]").append(",");
                    for (int i = 0; i < items.size(); ++i) {
                        filterStr = i == 0 ? filterStr.append("'").append(items.get(i).getCode()).append("'") : filterStr.append(",").append("'").append(items.get(i).getCode()).append("'");
                        if (i != items.size() - 1) continue;
                        filterStr.append(")");
                    }
                }
                ++index;
            }
            logger.debug(filterStr.toString());
            return filterStr.toString();
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
            return null;
        }
    }

    private boolean isRelatedWithMaster(QueryBlockDefine block, QueryDimensionDefine dim) {
        String dimensionName = dim.getDimensionName();
        List<QuerySelectField> fields = QueryHelper.getSelectFieldsInBlock(block);
        List masterFields = fields.stream().filter(e -> "true".equals(e.getIsMaster())).collect(Collectors.toList());
        Optional<QuerySelectField> coverFieldOptional = fields.stream().filter(item -> item.getDimensionName() != null && item.getDimensionName().equals(dimensionName)).findFirst();
        if (coverFieldOptional.isPresent()) {
            QuerySelectField querySelectField = coverFieldOptional.get();
            boolean isEntityTable = false;
            if (querySelectField.getTableName() != null) {
                try {
                    TableDefine table = dataDefinitionRuntimeController.queryTableDefine(querySelectField.getTableKey());
                    if (table != null) {
                        if (TableKind.TABLE_KIND_ENTITY.equals((Object)table.getKind())) {
                            isEntityTable = true;
                        }
                    } else {
                        TableModelDefine tableModelDefine = dataModelService.getTableModelDefineById(querySelectField.getTableKey());
                        if (TableKind.TABLE_KIND_ENTITY.equals((Object)tableModelDefine.getKind())) {
                            isEntityTable = true;
                        }
                    }
                }
                catch (Exception e1) {
                    logger.error(e1.getMessage(), e1);
                }
            }
            if (!isEntityTable) {
                return false;
            }
            String fieldKey = querySelectField.getCode();
            try {
                FieldDefine fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                for (QuerySelectField masterField : masterFields) {
                    String tableKey = this.getTableKey(masterField.getCode());
                    if (!tableKey.equals(fieldDefine.getOwnerTableKey())) continue;
                    return true;
                }
            }
            catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
            }
        }
        return false;
    }

    private String getTableKey(String fieldCode) {
        try {
            FieldDefine field = dataDefinitionRuntimeController.queryFieldDefine(fieldCode);
            if (field.getEntityKey() != null) {
                return this.getTableKey(field.getEntityKey());
            }
            return field.getOwnerTableKey();
        }
        catch (Exception e) {
            logger.error("\u901a\u8fc7\u4e3b\u952e\u6307\u6807\u83b7\u53d6\u5bf9\u5e94\u8868\u540d\u51fa\u9519:\uff1a", (Object)e.getMessage());
            return null;
        }
    }

    /*
     * Unable to fully structure code
     */
    public String setConditions(IGroupingQuery groupQuery, QueryBlockDefine block, String blockCondition, Map<String, QueryDimensionDefine> conditions, Map<String, QuerySelectField> fieldMap, Map<String, Integer> fieldIndex, List<String> periods, ReloadTreeInfo reloadTreeInfo, Boolean isAutoGather) {
        block86: {
            try {
                dimValPeriod = new DimensionValueSet();
                if (periods.size() > 0) {
                    dimValPeriod.setValue("DATATIME", (Object)periods.get(periods.size() - 1));
                }
                this.isAutoGather = isAutoGather;
                coverFieldFilter = this.queryCoverFieldDimVSet(block);
                queryHelper = new DataQueryHelper();
                dims = block.getQueryDimensions();
                dimFiends = new LinkedHashMap<K, V>();
                if (dims == null) break block86;
                dimV = new DimensionValueSet();
                dimV.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
                if (dims.size() == 1) {
                    for (String viewId : masterKeys = block.getMasterKeyValue("masterKeys").split(";")) {
                        try {
                            entitys = new ArrayList<String>();
                            entityView = QueryHelper.getEntityView(viewId);
                            periodView = this.periodEntityAdapter.isPeriodEntity(viewId);
                            if (periodView) continue;
                            et = QueryHelper.getEntityTable(entityView);
                            if (null == et || QueryHelper.queryEntityUtil.getEntityTablelKindByView(viewId) == TableKind.TABLE_KIND_ENTITY_PERIOD) continue;
                            dimName = QueryHelper.dataAssist.getDimensionName(entityView);
                            for (IEntityRow row : et.getRootRows()) {
                                rows = this.getEntityRows(et, row);
                                entitys.addAll(rows);
                            }
                            dimV.setValue(dimName, entitys);
                        }
                        catch (Exception e) {
                            // empty catch block
                        }
                    }
                    if (periods != null && periods.size() > 0) {
                        dimV.setValue("DATATIME", periods);
                    }
                    this.getDimVIntersection(groupQuery, dimV);
                    groupQuery.setMasterKeys(dimV);
                    return blockCondition;
                }
                masterKeys = block.getMasterKeyValue("masterKeys").split(";");
                firstDim = dims.stream().filter((Predicate<QueryDimensionDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$setConditions$19(java.lang.String[] com.jiuqi.nr.query.block.QueryDimensionDefine ), (Lcom/jiuqi/nr/query/block/QueryDimensionDefine;)Z)((String[])masterKeys)).findFirst();
                if (!firstDim.isPresent()) {
                    for (String viewId : masterKeys) {
                        entitys = new ArrayList<String>();
                        periodView = this.periodEntityAdapter.isPeriodEntity(viewId);
                        entityView = QueryHelper.getEntityView(viewId);
                        et = QueryHelper.getEntityTable(entityView);
                        if (QueryHelper.queryEntityUtil.getEntityTablelKindByView(viewId) == TableKind.TABLE_KIND_ENTITY_PERIOD) continue;
                        dimName = QueryHelper.dataAssist.getDimensionName(entityView);
                        for (IEntityRow row : et.getRootRows()) {
                            rows = this.getEntityRows(et, row);
                            entitys.addAll(rows);
                        }
                        dimV.setValue(dimName, entitys);
                    }
                }
                dataTimeDim = dims.stream().filter((Predicate<QueryDimensionDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$setConditions$20(com.jiuqi.nr.query.block.QueryDimensionDefine ), (Lcom/jiuqi/nr/query/block/QueryDimensionDefine;)Z)()).findFirst();
                dataTimeConDim = dims.stream().filter((Predicate<QueryDimensionDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$setConditions$21(com.jiuqi.nr.query.block.QueryDimensionDefine ), (Lcom/jiuqi/nr/query/block/QueryDimensionDefine;)Z)()).findFirst();
                selectNewTree = null;
                selectDateList = null;
                if (!dataTimeDim.isPresent() && !dataTimeConDim.isPresent()) {
                    dateList = null;
                    dwDimName = "DATATIME";
                    try {
                        startTime = block.getTaskDefStartPeriod();
                        endTime = block.getTaskDefEndPeriod();
                        if (StringUtil.isNullOrEmpty((String)startTime) || StringUtil.isNullOrEmpty((String)endTime)) {
                            ptype = PeriodType.valueOf((String)block.getPeriodTypeInCache());
                            dateList = queryHelper.getDefaultPeriodsDataFromBlock(startTime, endTime, ptype, QueryHelper.customPeriodTable);
                        } else {
                            pw = new PeriodWrapper(startTime);
                            type = pw.getType();
                            ptype = PeriodType.fromType((int)type);
                            dateList = PeriodUtil.getPeriodCodes((String)startTime, (String)endTime, (PeriodType)ptype);
                        }
                    }
                    catch (Exception e) {
                        QueryHelper.logger.error(e.getMessage(), e);
                    }
                    if (dateList != null && dateList.size() > 0 && !dimV.hasValue(dwDimName)) {
                        dimV.setValue(dwDimName, (Object)dateList);
                    }
                }
                if ((entityDim = dims.stream().filter((Predicate<QueryDimensionDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$setConditions$22(com.jiuqi.nr.query.block.QueryDimensionDefine ), (Lcom/jiuqi/nr/query/block/QueryDimensionDefine;)Z)()).findFirst()).isPresent() && entityDim.get().getSelectItems() != null && !entityDim.get().getSelectItems().isEmpty()) {
                    selectNewTree = queryHelper.queryEntityData(entityDim.get().getSelectItems());
                }
                if (dataTimeDim.isPresent() && dataTimeDim.get().getSelectItems() != null && !dataTimeDim.get().getSelectItems().isEmpty()) {
                    switch (1.$SwitchMap$com$jiuqi$nr$query$common$QuerySelectionType[QuerySelectionType.MULTIITES.ordinal()]) {
                        case 1: 
                        case 2: {
                            selectDateList = new ArrayList<String>();
                            timeSelect = dataTimeDim.get().getSelectItems();
                            for (QuerySelectItem seItem : timeSelect) {
                                selectDateList.add(seItem.getCode());
                            }
                            break;
                        }
                        case 3: 
                        case 4: {
                            ptype = PeriodType.valueOf((String)block.getPeriodTypeInCache());
                            startTime = dataTimeDim.get().getSelectItems().get(0).getCode();
                            endTime = dataTimeDim.get().getSelectItems().get(1).getCode();
                            selectDateList = PeriodUtil.getPeriodCodes((String)startTime, (String)endTime, (PeriodType)ptype);
                            break;
                        }
                    }
                }
                block30: for (QueryDimensionDefine dim : dims) {
                    if (dim.getViewId() == null || dim.getDimensionType() == QueryDimensionType.QDT_FIELD) continue;
                    if (dim.getDimensionType() == QueryDimensionType.QDT_CUSTOM) {
                        items = dim.getSelectItems();
                        if (items == null || items.size() <= 0) continue;
                        hasDot = false;
                        formulaStr = "";
                        for (QuerySelectItem selectItem : items) {
                            if (StringUtils.isEmpty((String)selectItem.getCode())) continue;
                            formula = selectItem.getCode();
                            if (hasDot) {
                                formulaStr = formulaStr + ", ";
                            }
                            hasDot = true;
                            formulaStr = formulaStr + formula;
                        }
                        if (formulaStr.endsWith(",")) {
                            blockCondition = formulaStr;
                            continue;
                        }
                        if (blockCondition != null) {
                            blockCondition = blockCondition + " AND (" + formulaStr + ")";
                            continue;
                        }
                        blockCondition = formulaStr;
                        continue;
                    }
                    if (dim.getDimensionType() == QueryDimensionType.QDT_ENTITY) {
                        entitys = new ArrayList<E>();
                        entityView = QueryHelper.getEntityView(dim.getViewId());
                        tableTitle = QueryHelper.queryEntityUtil.getTableTitleByViewKey(dim.getViewId());
                        isPeriodUnit = QueryHelper.queryEntityUtil.isPeriodUnit(dim.getViewId());
                        if (dim.getTitle() == null) {
                            dim.setTitle(tableTitle);
                        }
                        dimItems = dim.getSelectItems();
                        dwDimName = QueryHelper.dataAssist.getDimensionName(entityView);
                        if (isPeriodUnit) {
                            if (dimItems != null && dimItems.size() > 0) {
                                block87: {
                                    try {
                                        if (!dataTimeConDim.isPresent()) break block87;
                                        if (dim.getLayoutType() != QueryLayoutType.LYT_CONDITION) continue;
                                        if (selectDateList == null) {
                                            selectFieldsInBlock = QueryHelper.getSelectFieldsInBlock(block);
                                            existSelectByField = selectFieldsInBlock.stream().anyMatch((Predicate<QuerySelectField>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$setConditions$23(com.jiuqi.nr.query.block.QuerySelectField ), (Lcom/jiuqi/nr/query/block/QuerySelectField;)Z)());
                                            switch (1.$SwitchMap$com$jiuqi$nr$query$common$QuerySelectionType[dim.getSelectType().ordinal()]) {
                                                case 1: 
                                                case 2: {
                                                    selectDateList = new ArrayList<String>();
                                                    timeSelect = dim.getSelectItems();
                                                    var32_80 = timeSelect.iterator();
                                                    while (var32_80.hasNext()) {
                                                        seItem = (QuerySelectItem)var32_80.next();
                                                        selectDateList.add(seItem.getCode());
                                                    }
                                                    break block87;
                                                }
                                                default: {
                                                    ptype = PeriodType.valueOf((String)block.getPeriodTypeInCache());
                                                    startTime = dim.getSelectItems().get(0).getCode();
                                                    v0 = endTime = dim.getSelectItems().size() > 1 ? dim.getSelectItems().get(1).getCode() : startTime;
                                                    if (existSelectByField) {
                                                        selectDateList = queryHelper.getAllTimeFromToEnd(startTime, endTime);
                                                        ** break;
                                                    }
                                                    groupQuery.setQueryPeriod(startTime, endTime, ptype);
                                                    if (!block.getIsDataSet() || (datasetPeriods = PeriodUtil.getPeriodCodes((String)startTime, (String)endTime, (PeriodType)ptype)).size() <= 0) break block87;
                                                    dimV.setValue(dwDimName, (Object)datasetPeriods);
lbl163:
                                                    // 2 sources

                                                    break block87;
                                                }
                                            }
                                        }
                                        conditionDateList = new ArrayList();
                                        switch (1.$SwitchMap$com$jiuqi$nr$query$common$QuerySelectionType[dim.getSelectType().ordinal()]) {
                                            case 1: 
                                            case 2: {
                                                timeSelect = dim.getSelectItems();
                                                for (QuerySelectItem seItem : timeSelect) {
                                                    if (!selectDateList.contains(seItem.getCode())) continue;
                                                    conditionDateList.add(seItem.getCode());
                                                }
                                                selectDateList = conditionDateList;
                                                break;
                                            }
                                            case 3: 
                                            case 4: {
                                                ptype = PeriodType.valueOf((String)block.getPeriodTypeInCache());
                                                startTime = dim.getSelectItems().get(0).getCode();
                                                endTime = dim.getSelectItems().get(1).getCode();
                                                conditionDateList = PeriodUtil.getPeriodCodes((String)startTime, (String)endTime, (PeriodType)ptype);
                                                dateList = new ArrayList<String>();
                                                for (String date : conditionDateList) {
                                                    if (!selectDateList.contains(date)) continue;
                                                    dateList.add(date);
                                                }
                                                selectDateList = dateList;
                                                break;
                                            }
                                        }
                                    }
                                    catch (Exception ex) {
                                        QueryHelper.logger.error(ex.getMessage(), ex);
                                    }
                                }
                                if (selectDateList == null || selectDateList.size() <= 0) continue;
                                dimV.setValue(dwDimName, (Object)selectDateList);
                                continue;
                            }
                            if (dataTimeConDim.isPresent()) continue;
                            dateList = null;
                            try {
                                startTime = block.getTaskDefStartPeriod();
                                endTime = block.getTaskDefEndPeriod();
                                if (StringUtil.isNullOrEmpty((String)startTime) || StringUtil.isNullOrEmpty((String)endTime)) {
                                    ptype = PeriodType.valueOf((String)block.getPeriodTypeInCache());
                                    dateList = queryHelper.getDefaultPeriodsDataFromBlock(startTime, endTime, ptype, QueryHelper.customPeriodTable);
                                } else {
                                    pw = new PeriodWrapper(startTime);
                                    type = pw.getType();
                                    ptype = PeriodType.fromType((int)type);
                                    dateList = PeriodUtil.getPeriodCodes((String)startTime, (String)endTime, (PeriodType)ptype);
                                }
                            }
                            catch (Exception e) {
                                QueryHelper.logger.error(e.getMessage(), e);
                            }
                            if (dateList == null || dateList.size() <= 0 || dimV.hasValue(dwDimName)) continue;
                            dimV.setValue(dwDimName, (Object)dateList);
                            continue;
                        }
                        if (dimItems != null && dimItems.size() > 0) {
                            try {
                                define = dims.stream().filter((Predicate<QueryDimensionDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$setConditions$24(com.jiuqi.nr.query.block.QueryDimensionDefine com.jiuqi.nr.query.block.QueryDimensionDefine ), (Lcom/jiuqi/nr/query/block/QueryDimensionDefine;)Z)((QueryDimensionDefine)dim)).findFirst();
                                entitys = this.queryEntityOrDicNodes(selectNewTree, dim, define.isPresent(), dimValPeriod, coverFieldFilter, reloadTreeInfo);
                                if (entitys == null) continue;
                                if (entitys.size() > 0) {
                                    dimV.setValue(dwDimName, entitys);
                                    continue;
                                }
                                entitys.add("00000000-0000-0000-0000-000000000000");
                                dimV.setValue(dwDimName, entitys);
                            }
                            catch (Exception ex) {
                                QueryHelper.logger.error(ex.getMessage(), ex);
                            }
                            continue;
                        }
                        if (dimV.getValue(dwDimName) != null) continue;
                        et = coverFieldFilter == null || coverFieldFilter.isEmpty() ? QueryHelper.getEntityTable(entityView, dimValPeriod) : QueryHelper.getEntityTable(entityView, dimValPeriod, coverFieldFilter);
                        if (et.getAllRows().size() < 10000) {
                            for (IEntityRow row : et.getRootRows()) {
                                rows = this.getEntityRows(et, row);
                                entitys.addAll(rows);
                            }
                        } else {
                            for (IEntityRow row : et.getRootRows()) {
                                entityRows = et.getChildRows(row.getEntityKeyData());
                                rows = entityRows.stream().map((Function<IEntityRow, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, lambda$setConditions$25(com.jiuqi.nr.entity.engine.intf.IEntityRow ), (Lcom/jiuqi/nr/entity/engine/intf/IEntityRow;)Ljava/lang/String;)()).collect(Collectors.toList());
                                entitys.addAll(rows);
                            }
                        }
                        if (entitys.size() <= 0) continue;
                        dimV.setValue(dwDimName, entitys);
                        continue;
                    }
                    if (dim.getDimensionType() == QueryDimensionType.QDT_DICTIONARY) {
                        dicSelectNewTree = null;
                        filtervalues = null;
                        if (!dim.getLayoutType().equals((Object)QueryLayoutType.LYT_CONDITION)) {
                            dicCon = dims.stream().filter((Predicate<QueryDimensionDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$setConditions$26(com.jiuqi.nr.query.block.QueryDimensionDefine com.jiuqi.nr.query.block.QueryDimensionDefine ), (Lcom/jiuqi/nr/query/block/QueryDimensionDefine;)Z)((QueryDimensionDefine)dim)).findFirst();
                            if (dicCon.isPresent()) continue;
                            dicSelectNewTree = queryHelper.queryEntityData(dim.getSelectItems());
                            filtervalues = (ArrayList)this.queryEntityOrDicNodes(dicSelectNewTree, dim, dicCon.isPresent(), dimValPeriod, coverFieldFilter, reloadTreeInfo);
                        } else {
                            dicEntity = dims.stream().filter((Predicate<QueryDimensionDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$setConditions$27(com.jiuqi.nr.query.block.QueryDimensionDefine com.jiuqi.nr.query.block.QueryDimensionDefine ), (Lcom/jiuqi/nr/query/block/QueryDimensionDefine;)Z)((QueryDimensionDefine)dim)).findFirst();
                            if (dicEntity.isPresent()) {
                                if (dicEntity.get().getSelectItems() != null && !dicEntity.get().getSelectItems().isEmpty()) {
                                    dicSelectNewTree = queryHelper.queryEntityData(dicEntity.get().getSelectItems());
                                    filtervalues = (ArrayList)this.queryEntityOrDicNodes(dicSelectNewTree, dim, true, dimValPeriod, coverFieldFilter, reloadTreeInfo);
                                }
                            } else {
                                filtervalues = (ArrayList)this.queryEntityOrDicNodes(null, dim, true, dimValPeriod, coverFieldFilter, reloadTreeInfo);
                            }
                        }
                        if (filtervalues == null || filtervalues.isEmpty()) {
                            // empty if block
                        }
                        field = fieldMap.get(dim.getDimensionName());
                        if (this.isRelatedWithMaster(block, dim)) {
                            try {
                                fields = QueryHelper.getSelectFieldsInBlock(block);
                                fieldOptional = fields.stream().filter((Predicate<QuerySelectField>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$setConditions$28(com.jiuqi.nr.query.block.QueryDimensionDefine com.jiuqi.nr.query.block.QuerySelectField ), (Lcom/jiuqi/nr/query/block/QuerySelectField;)Z)((QueryDimensionDefine)dim)).findFirst();
                                coverFieldTableKey = null;
                                masterSelectField = null;
                                if (fieldOptional.isPresent()) {
                                    querySelectField = fieldOptional.get();
                                    table = QueryHelper.dataDefinitionRuntimeController.queryTableDefine(querySelectField.getTableKey());
                                    coverFieldTableKey = table.getKey();
                                }
                                masterFields = fields.stream().filter((Predicate<QuerySelectField>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$setConditions$29(com.jiuqi.nr.query.block.QuerySelectField ), (Lcom/jiuqi/nr/query/block/QuerySelectField;)Z)()).collect(Collectors.toList());
                                for (QuerySelectField masterField : masterFields) {
                                    tableKey = this.getTableKey(masterField.getCode());
                                    if (!tableKey.equals(coverFieldTableKey)) continue;
                                    masterSelectField = masterField;
                                    break;
                                }
                                masterKeys = block.getMasterKeyValue("masterKeys").split(";");
                                et = null;
                                for (String masterKey : masterKeys) {
                                    entityView = QueryHelper.getEntityView(masterKey);
                                    if (entityView == null || !QueryHelper.queryEntityUtil.getTableKeyByView(masterKey).equals(coverFieldTableKey) || entityView == null) continue;
                                    dimName = QueryHelper.getDimName(entityView);
                                    et = QueryHelper.getEntityTable(entityView, null, coverFieldFilter);
                                    entitys = et.getAllRows().stream().map((Function<IEntityRow, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, getEntityKeyData(), (Lcom/jiuqi/nr/entity/engine/intf/IEntityRow;)Ljava/lang/String;)()).collect(Collectors.toList());
                                    if (dimV.hasValue(dimName)) {
                                        value = dimV.getValue(dimName);
                                        if (!(value instanceof ArrayList)) continue block30;
                                        intersectionList = CollectionUtils.intersection(entitys, (ArrayList)value);
                                        dimV.setValue(dimName, intersectionList);
                                        continue block30;
                                    }
                                    dimV.setValue(dimName, entitys);
                                    continue block30;
                                }
                                continue;
                            }
                            catch (Exception e) {
                                QueryHelper.logger.error(e.getMessage(), e);
                                QueryHelper.logger.info("\u6682\u4e0d\u652f\u6301\u6309\u5c01\u9762\u7b5b\u9009\u5355\u4f4d\uff01");
                                continue;
                            }
                        }
                        if (filtervalues == null || filtervalues.isEmpty()) continue;
                        if (block.getIsDataSet()) {
                            dimV.setValue(dim.getDimensionName(), (Object)filtervalues);
                            continue;
                        }
                        if (field == null || !fieldIndex.containsKey(field.getCode())) continue;
                        colIndex = fieldIndex.get(field.getCode());
                        groupQuery.setColumnFilterValueList(colIndex, new ArrayList<E>(filtervalues));
                        continue;
                    }
                    if (dim.getDimensionType() == QueryDimensionType.QDT_UPLOADSTATUS) {
                        uploadDim = (UploadStateDimension)dim.getExtend(UploadStateDimension.class);
                        if (uploadDim == null || (filter = uploadDim.getFilterFormula(dim.getSelectItems(), block.getBlockInfo().getFormSchemeKey())) == null) continue;
                        if (blockCondition != null) {
                            blockCondition = blockCondition + " AND (" + filter + ")";
                            continue;
                        }
                        blockCondition = filter;
                        continue;
                    }
                    if (dim.getDimensionType() == QueryDimensionType.QDT_GRIDFIELD) {
                        if (!CollectionUtils.isEmpty(dim.getSelectFields()) || (field = QueryHelper.dataDefinitionRuntimeController.queryFieldDefine(dim.getViewId())) == null) continue;
                        dim.setIsNum(QueryHelper.isNumField(field));
                        dim.setFieldConditionType(this.getFieldConditionType(field));
                        condition = dim.getSelectCondition();
                        if (CollectionUtils.isEmpty(condition)) continue;
                        blockCondition = this.getFieldConditions(blockCondition, condition, field);
                        continue;
                    }
                    if (dim.getDimensionType() != QueryDimensionType.QDT_NULL || CollectionUtils.isEmpty(dim.getSelectItems())) continue;
                    field = QueryHelper.dataDefinitionRuntimeController.queryFieldDefine(dim.getViewId());
                    if (field == null) {
                        field = QueryHelper.dataDefinitionRuntimeController.queryFieldDefine(dim.getFieldKey());
                    }
                    deployInfoByDataFieldKeys = QueryHelper.iRuntimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{field.getKey()});
                    deployInfoByColumnKey = new DataFieldDeployInfoDO();
                    if (deployInfoByDataFieldKeys.size() > 0) {
                        deployInfoByColumnKey = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
                    }
                    fieldCode = field.getCode();
                    tableName = deployInfoByColumnKey.getTableName();
                    if (field == null) continue;
                    value = "";
                    if (dim.getSelectItems().size() == 0) {
                        return null;
                    }
                    if (dim.getSelectItems().size() == 1) {
                        value = " = '" + dim.getSelectItems().get(0).getCode() + "'";
                    } else {
                        value = dim.getSelectItems().stream().map((Function<QuerySelectItem, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, lambda$setConditions$30(com.jiuqi.nr.query.block.QuerySelectItem ), (Lcom/jiuqi/nr/query/block/QuerySelectItem;)Ljava/lang/String;)()).collect(Collectors.joining(","));
                        value = " in {" + value + "}";
                    }
                    if ((filter = tableName + "[" + fieldCode + "]" + value) == null) continue;
                    if (blockCondition != null) {
                        blockCondition = blockCondition + " AND (" + filter + ")";
                        continue;
                    }
                    blockCondition = filter;
                }
                this.getDimVIntersection(groupQuery, dimV);
                groupQuery.setMasterKeys(dimV);
            }
            catch (Exception e) {
                LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u521d\u59cb\u5316\u67e5\u8be2\u6761\u4ef6\u5f02\u5e38", (String)e.getMessage());
                QueryHelper.logger.error("\u521d\u59cb\u5316\u67e5\u8be2\u6761\u4ef6\u5f02\u5e38", e);
            }
        }
        return blockCondition;
    }

    private void getDimVIntersection(IGroupingQuery groupQuery, DimensionValueSet dimV) {
        DimensionValueSet dimValueSet = groupQuery.getMasterKeys();
        if (null != dimValueSet) {
            for (int i = 0; i < dimValueSet.size(); ++i) {
                String dimName = dimValueSet.getName(i);
                if (dimV.hasValue(dimName)) {
                    Object value = dimV.getValue(dimName);
                    Object originValues = dimValueSet.getValue(dimName);
                    if (!(value instanceof ArrayList) || !(originValues instanceof ArrayList)) continue;
                    Collection intersectionList = CollectionUtils.intersection((ArrayList)originValues, (ArrayList)value);
                    dimV.setValue(dimName, intersectionList);
                    continue;
                }
                dimV.setValue(dimName, dimValueSet.getValue(dimName));
            }
        }
    }

    private String getFieldConditionType(FieldDefine field) {
        switch (field.getType()) {
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_INTEGER: 
            case FIELD_TYPE_DECIMAL: {
                return "Number";
            }
            case FIELD_TYPE_STRING: 
            case FIELD_TYPE_TEXT: {
                return "String";
            }
            case FIELD_TYPE_DATE: {
                return "Date";
            }
            case FIELD_TYPE_TIME: {
                return "Time";
            }
            case FIELD_TYPE_DATE_TIME: {
                return "DateTime";
            }
        }
        return "String";
    }

    private List<String> getEntityRows(IEntityTable et, IEntityRow curRow) {
        ArrayList<String> rows = new ArrayList<String>();
        rows.add(curRow.getEntityKeyData());
        List childRows = et.getChildRows(curRow.getEntityKeyData());
        if (childRows != null && childRows.size() > 0) {
            for (IEntityRow row : childRows) {
                List<String> crows = this.getEntityRows(et, row);
                rows.addAll(crows);
            }
        }
        return rows;
    }

    public List<String> queryEntityOrDicNodes(List<QueryEntityData> selectNewTree, QueryDimensionDefine dim, boolean hasCondition, DimensionValueSet dimValPeriod, String coverFieldFilter, ReloadTreeInfo reloadTreeInfo) {
        block71: {
            ArrayList<String> entitys = new ArrayList<String>();
            DataQueryHelper queryHelper = new DataQueryHelper();
            try {
                if (selectNewTree != null && dim.getLayoutType() == QueryLayoutType.LYT_CONDITION || selectNewTree != null && !hasCondition) {
                    ArrayList<QuerySelectItem> filteredItems = new ArrayList<QuerySelectItem>();
                    EntityViewDefine entView = QueryHelper.getEntityView(dim.getViewId());
                    IEntityTable rs = QueryHelper.getEntityTable(entView, dimValPeriod, reloadTreeInfo);
                    List allRows = rs.getAllRows();
                    for (IEntityRow row : allRows) {
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            if (!selectItem.getCode().equals(row.getEntityKeyData())) continue;
                            filteredItems.add(selectItem);
                        }
                    }
                    selectNewTree = queryHelper.queryEntityData(filteredItems);
                    block1 : switch (DimensionItemScop.getType(dim.getItemScop())) {
                        case 2: {
                            QuerySelectItem selectItem;
                            String currNodeCode;
                            Object currData;
                            Iterator iterator = filteredItems.iterator();
                            while (iterator.hasNext() && ((QueryEntityData)(currData = queryHelper.findSelf(currNodeCode = (selectItem = (QuerySelectItem)iterator.next()).getCode(), selectNewTree))).getChilds() != null) {
                                for (QueryEntityData selectData : ((QueryEntityData)currData).getChilds()) {
                                    entitys.add(selectData.getId());
                                }
                            }
                            break;
                        }
                        case 3: {
                            for (QuerySelectItem selectItem : filteredItems) {
                                List<QueryEntityData> samelevelNodes = queryHelper.findSamelevelNode(selectItem.getCode(), selectNewTree);
                                for (QueryEntityData data : samelevelNodes) {
                                    entitys.add(data.getId());
                                }
                            }
                            break;
                        }
                        case 4: {
                            for (QuerySelectItem selectItem : filteredItems) {
                                entitys.add(selectItem.getCode());
                            }
                            break;
                        }
                        case 5: {
                            String currNodeCode;
                            Object currData;
                            for (QuerySelectItem selectItem : filteredItems) {
                                currNodeCode = selectItem.getCode();
                                currData = queryHelper.findSelf(currNodeCode, selectNewTree);
                                queryHelper.findLeafChilds((QueryEntityData)currData, entitys);
                            }
                            break;
                        }
                        case 6: {
                            String currNodeCode;
                            Object currData;
                            for (QuerySelectItem selectItem : filteredItems) {
                                currNodeCode = selectItem.getCode();
                                entitys.add(currNodeCode);
                                currData = queryHelper.findSelf(currNodeCode, selectNewTree);
                                if (((QueryEntityData)currData).getChilds() == null) break block1;
                                for (QueryEntityData selectData : ((QueryEntityData)currData).getChilds()) {
                                    entitys.add(selectData.getId());
                                }
                            }
                            break;
                        }
                        case 0: 
                        case 7: {
                            String currNodeCode;
                            Object currData;
                            if (dim.getSelectType() == QuerySelectionType.MULTIITES) {
                                for (QuerySelectItem selectItem : filteredItems) {
                                    entitys.add(selectItem.getCode());
                                }
                            } else {
                                for (QuerySelectItem selectItem : filteredItems) {
                                    currNodeCode = selectItem.getCode();
                                    currData = queryHelper.findSelf(currNodeCode, selectNewTree);
                                    queryHelper.findChilds((QueryEntityData)currData, entitys);
                                }
                            }
                            break;
                        }
                        default: {
                            String currNodeCode;
                            Object currData;
                            if (dim.getSelectType() == QuerySelectionType.MULTIITES) {
                                for (QuerySelectItem selectItem : filteredItems) {
                                    entitys.add(selectItem.getCode());
                                }
                            } else {
                                for (QuerySelectItem selectItem : filteredItems) {
                                    currNodeCode = selectItem.getCode();
                                    currData = queryHelper.findSelf(currNodeCode, selectNewTree);
                                    if (((QueryEntityData)currData).getChilds() == null || ((QueryEntityData)currData).getChilds().isEmpty()) continue;
                                    ((QueryEntityData)currData).getChilds().forEach(a -> queryHelper.findChilds((QueryEntityData)a, (List<String>)entitys));
                                }
                            }
                        }
                    }
                    return entitys;
                }
                if (dim.getLayoutType() != QueryLayoutType.LYT_CONDITION) break block71;
                if (selectNewTree != null) {
                    return null;
                }
                EntityViewDefine entView = QueryHelper.getEntityView(dim.getViewId());
                IEntityTable rs = QueryHelper.getEntityTable(entView, dimValPeriod, reloadTreeInfo);
                switch (DimensionItemScop.getType(dim.getItemScop())) {
                    case 2: {
                        if (this.isAutoGather) {
                            List collect = rs.getAllChildRows(dim.getSelectItems().get(0).getCode()).stream().filter(p -> {
                                boolean hasChild;
                                boolean bl = hasChild = rs.getAllChildCount(p.getEntityKeyData()) > 0;
                                return !hasChild;
                            }).map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                            entitys.addAll(collect);
                            break;
                        }
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            List rows = rs.getChildRows(selectItem.getCode().toString());
                            for (IEntityRow row : rows) {
                                entitys.add(row.getEntityKeyData());
                            }
                        }
                        this.getRandomEnumType(dim.getDimensionType(), entitys, dim.getSelectItems());
                        break;
                    }
                    case 3: {
                        LinkedHashMap<String, String> parentKeys = new LinkedHashMap<String, String>();
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            IEntityRow row;
                            if (selectItem != null && rs.findByEntityKey(selectItem.getCode()) != null) {
                                entitys.add(selectItem.getCode());
                            }
                            if ((row = rs.findByEntityKey(selectItem.getCode().toString())) == null) continue;
                            String parentKey = row.getParentEntityKey();
                            List rows = rs.getChildRows(parentKey);
                            if (StringUtil.isNullOrEmpty((String)parentKey)) {
                                rows = rs.getRootRows();
                            }
                            if (parentKeys.containsKey(parentKey)) continue;
                            for (IEntityRow prow : rows) {
                                entitys.add(prow.getEntityKeyData());
                            }
                            parentKeys.put(parentKey, parentKey);
                        }
                        break;
                    }
                    case 4: {
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            if (selectItem == null || rs.findByEntityKey(selectItem.getCode()) == null) continue;
                            entitys.add(selectItem.getCode());
                        }
                        if (!this.isAutoGather || dim.getSelectItems().size() != 1) break;
                        List collect = rs.getAllChildRows(dim.getSelectItems().get(0).getCode()).stream().filter(p -> {
                            boolean hasChild;
                            boolean bl = hasChild = rs.getAllChildCount(p.getEntityKeyData()) > 0;
                            return !hasChild;
                        }).map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                        entitys.addAll(collect);
                        break;
                    }
                    case 5: {
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            List rows = rs.getAllChildRows(selectItem.getCode().toString());
                            for (IEntityRow row : rows) {
                                boolean hasChild = rs.getAllChildCount(row.getEntityKeyData()) > 0;
                                if (hasChild) continue;
                                entitys.add(row.getEntityKeyData());
                            }
                        }
                        this.getRandomEnumType(dim.getDimensionType(), entitys, dim.getSelectItems());
                        break;
                    }
                    case 6: {
                        if (this.isAutoGather) {
                            List collect = rs.getAllChildRows(dim.getSelectItems().get(0).getCode()).stream().filter(p -> {
                                boolean hasChild;
                                boolean bl = hasChild = rs.getAllChildCount(p.getEntityKeyData()) > 0;
                                return !hasChild;
                            }).map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                            entitys.addAll(collect);
                            break;
                        }
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            if (selectItem != null && rs.findByEntityKey(selectItem.getCode()) != null) {
                                entitys.add(selectItem.getCode());
                            }
                            List rows = rs.getChildRows(selectItem.getCode().toString());
                            for (IEntityRow row : rows) {
                                entitys.add(row.getEntityKeyData());
                            }
                        }
                        break;
                    }
                    case 0: 
                    case 7: {
                        if (dim.getSelectType() == QuerySelectionType.MULTIITES) {
                            for (QuerySelectItem selectItem : dim.getSelectItems()) {
                                entitys.add(selectItem.getCode());
                            }
                        } else if (this.isAutoGather && dim.getSelectItems().size() == 1) {
                            for (QuerySelectItem selectItem : dim.getSelectItems()) {
                                if (selectItem != null && rs.findByEntityKey(selectItem.getCode()) != null) {
                                    boolean hasChild;
                                    boolean bl = hasChild = rs.getAllChildCount(selectItem.getCode()) > 0;
                                    if (!hasChild) {
                                        entitys.add(selectItem.getCode());
                                    }
                                }
                                List collect = rs.getAllChildRows(dim.getSelectItems().get(0).getCode()).stream().filter(p -> {
                                    boolean hasChild;
                                    boolean bl = hasChild = rs.getAllChildCount(p.getEntityKeyData()) > 0;
                                    return !hasChild;
                                }).map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                                entitys.addAll(collect);
                            }
                        } else {
                            for (QuerySelectItem selectItem : dim.getSelectItems()) {
                                if (selectItem != null && rs.findByEntityKey(selectItem.getCode()) != null) {
                                    entitys.add(selectItem.getCode());
                                }
                                List rows = rs.getAllChildRows(selectItem.getCode().toString());
                                for (IEntityRow row : rows) {
                                    entitys.add(row.getEntityKeyData());
                                }
                            }
                        }
                        break;
                    }
                    default: {
                        if (dim.getSelectType() == QuerySelectionType.MULTIITES) {
                            for (QuerySelectItem selectItem : dim.getSelectItems()) {
                                entitys.add(selectItem.getCode());
                            }
                            break;
                        }
                        if (this.isAutoGather && dim.getSelectItems().size() == 1) {
                            for (QuerySelectItem selectItem : dim.getSelectItems()) {
                                if (selectItem != null && rs.findByEntityKey(selectItem.getCode()) != null) {
                                    boolean hasChild;
                                    boolean bl = hasChild = rs.getAllChildCount(selectItem.getCode()) > 0;
                                    if (!hasChild) {
                                        entitys.add(selectItem.getCode());
                                    }
                                }
                                List collect = rs.getAllChildRows(dim.getSelectItems().get(0).getCode()).stream().filter(p -> {
                                    boolean hasChild;
                                    boolean bl = hasChild = rs.getAllChildCount(p.getEntityKeyData()) > 0;
                                    return !hasChild;
                                }).map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                                entitys.addAll(collect);
                            }
                            break;
                        }
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            List rows = rs.getAllChildRows(selectItem.getCode().toString());
                            for (IEntityRow row : rows) {
                                entitys.add(row.getEntityKeyData());
                            }
                        }
                        this.getRandomEnumType(dim.getDimensionType(), entitys, dim.getSelectItems());
                    }
                }
                return entitys;
            }
            catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
            }
        }
        return null;
    }

    private void getRandomEnumType(QueryDimensionType type, List<String> entitys, List<QuerySelectItem> selectItem) {
        try {
            if (type == QueryDimensionType.QDT_DICTIONARY && entitys.isEmpty() && !selectItem.isEmpty()) {
                entitys.add(String.valueOf(System.currentTimeMillis()));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void setConditionDimensionTitle(QueryDimensionDefine dim, IEntityTable rs) {
        if (dim.getLayoutType() != QueryLayoutType.LYT_CONDITION || !StringUtils.isEmpty((String)dim.getConditionTitle())) {
            return;
        }
        if (dim.getSelectItems() != null && !dim.getSelectItems().isEmpty()) {
            String dimTitle = "";
            for (QuerySelectItem item : dim.getSelectItems()) {
                IEntityRow entityRow = rs.findByEntityKey(item.getCode());
                if (entityRow == null) continue;
                String title = entityRow.getTitle();
                dimTitle = dimTitle + " " + title;
            }
            dim.setConditionTitle(dimTitle);
        }
    }

    private IEntityRow getEntityRowData(QueryDimensionDefine dim, IEntityTable entityTable, QuerySelectItem selectItem) {
        if (dim.getDimensionType().equals((Object)QueryDimensionType.QDT_ENTITY)) {
            return entityTable.findByEntityKey(selectItem.getCode());
        }
        if (dim.getDimensionType().equals((Object)QueryDimensionType.QDT_DICTIONARY)) {
            return entityTable.findByCode(selectItem.getCode());
        }
        return null;
    }

    public List<IEntityRow> getEntityRows(QueryDimensionDefine dim, IEntityTable entityTable) {
        ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>();
        DataQueryHelper queryHelper = new DataQueryHelper();
        try {
            switch (DimensionItemScop.getType(dim.getItemScop())) {
                case 2: {
                    for (QuerySelectItem selectItem : dim.getSelectItems()) {
                        rows.addAll(entityTable.getChildRows(selectItem.getCode().toString()));
                    }
                    break;
                }
                case 3: {
                    LinkedHashMap<String, String> parentKeys = new LinkedHashMap<String, String>();
                    for (QuerySelectItem selectItem : dim.getSelectItems()) {
                        IEntityRow row = entityTable.findByEntityKey(selectItem.getCode().toString());
                        if (row == null) continue;
                        String parentKey = row.getParentEntityKey();
                        if (StringUtil.isNullOrEmpty((String)parentKey)) {
                            rows.addAll(entityTable.getRootRows());
                        }
                        if (parentKeys.containsKey(parentKey)) continue;
                        rows.addAll(entityTable.getChildRows(parentKey));
                        parentKeys.put(parentKey, parentKey);
                    }
                    break;
                }
                case 4: {
                    for (QuerySelectItem selectItem : dim.getSelectItems()) {
                        rows.add(this.getEntityRowData(dim, entityTable, selectItem));
                    }
                    break;
                }
                case 5: {
                    for (QuerySelectItem selectItem : dim.getSelectItems()) {
                        List allChilds = entityTable.getAllChildRows(selectItem.getCode().toString());
                        for (int i = 0; i < allChilds.size(); ++i) {
                            IEntityRow child = (IEntityRow)allChilds.get(i);
                            if (entityTable.getAllChildCount(child.getEntityKeyData()) != 0) continue;
                            rows.add(child);
                        }
                    }
                    break;
                }
                case 6: {
                    for (QuerySelectItem selectItem : dim.getSelectItems()) {
                        rows.add(this.getEntityRowData(dim, entityTable, selectItem));
                        rows.addAll(entityTable.getChildRows(selectItem.getCode().toString()));
                    }
                    break;
                }
                case 0: 
                case 7: {
                    if (dim.getSelectType() == QuerySelectionType.MULTIITES) {
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            rows.add(this.getEntityRowData(dim, entityTable, selectItem));
                        }
                    } else {
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            rows.add(this.getEntityRowData(dim, entityTable, selectItem));
                            rows.addAll(entityTable.getAllChildRows(selectItem.getCode().toString()));
                        }
                    }
                    break;
                }
                default: {
                    if (dim.getSelectType() == QuerySelectionType.MULTIITES) {
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            rows.add(this.getEntityRowData(dim, entityTable, selectItem));
                        }
                    } else {
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            rows.addAll(entityTable.getAllChildRows(selectItem.getCode().toString()));
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return rows;
    }

    public List<String> getEntityRows2(QueryDimensionDefine dim, QueryDimensionDefine curDim) {
        ArrayList<String> entitys = new ArrayList<String>();
        DataQueryHelper queryHelper = new DataQueryHelper();
        List<QueryEntityData> selectNewTree = queryHelper.queryEntityData(dim.getSelectItems());
        try {
            block1 : switch (DimensionItemScop.getType(curDim.getItemScop())) {
                case 2: {
                    for (QuerySelectItem selectItem : curDim.getSelectItems()) {
                        String currNodeCode = selectItem.getCode();
                        QueryEntityData currData = queryHelper.findSelf(currNodeCode, selectNewTree);
                        if (currData.getChilds() == null) break block1;
                        for (QueryEntityData selectData : currData.getChilds()) {
                            entitys.add(selectData.getId());
                        }
                    }
                    break;
                }
                case 3: {
                    for (QuerySelectItem selectItem : curDim.getSelectItems()) {
                        List<QueryEntityData> samelevelNodes = queryHelper.findSamelevelNode(selectItem.getCode(), selectNewTree);
                        for (QueryEntityData data : samelevelNodes) {
                            entitys.add(data.getId());
                        }
                    }
                    break;
                }
                case 4: {
                    for (QuerySelectItem selectItem : curDim.getSelectItems()) {
                        entitys.add(selectItem.getCode());
                    }
                    break;
                }
                case 5: {
                    for (QuerySelectItem selectItem : curDim.getSelectItems()) {
                        String currNodeCode = selectItem.getCode();
                        QueryEntityData currData = queryHelper.findSelf(currNodeCode, selectNewTree);
                        queryHelper.findLeafChilds(currData, entitys);
                    }
                    break;
                }
                case 6: {
                    for (QuerySelectItem selectItem : curDim.getSelectItems()) {
                        String currNodeCode = selectItem.getCode();
                        entitys.add(currNodeCode);
                        QueryEntityData currData = queryHelper.findSelf(currNodeCode, selectNewTree);
                        if (currData.getChilds() == null) break block1;
                        for (QueryEntityData selectData : currData.getChilds()) {
                            entitys.add(selectData.getId());
                        }
                    }
                    break;
                }
                case 0: 
                case 7: {
                    if (dim.getSelectType() == QuerySelectionType.MULTIITES) {
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            entitys.add(selectItem.getCode());
                        }
                    } else {
                        for (QuerySelectItem selectItem : curDim.getSelectItems()) {
                            String currNodeCode = selectItem.getCode();
                            QueryEntityData currData = queryHelper.findSelf(currNodeCode, selectNewTree);
                            queryHelper.findChilds(currData, entitys);
                        }
                    }
                    break;
                }
                default: {
                    if (dim.getSelectType() == QuerySelectionType.MULTIITES) {
                        for (QuerySelectItem selectItem : dim.getSelectItems()) {
                            entitys.add(selectItem.getCode());
                        }
                    } else {
                        for (QuerySelectItem selectItem : curDim.getSelectItems()) {
                            String currNodeCode = selectItem.getCode();
                            QueryEntityData currData = queryHelper.findSelf(currNodeCode, selectNewTree);
                            if (currData.getChilds() == null || currData.getChilds().isEmpty()) continue;
                            currData.getChilds().forEach(a -> queryHelper.findChilds((QueryEntityData)a, (List<String>)entitys));
                        }
                    }
                    break;
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return entitys;
    }

    private Map<String, Integer> setStaticticsColumn(IGroupingQuery groupQuery, QuerySelectField sf, Map<String, Integer> fieldIndex) {
        List<QueryStatisticsItem> statisticsFields = sf.getStatisticsFields();
        if (statisticsFields != null && statisticsFields.size() > 0) {
            for (int i = 0; i < statisticsFields.size(); ++i) {
                QueryStatisticsItem item = statisticsFields.get(i);
                int colindex = groupQuery.addExpressionColumn(item.getFormulaExpression());
                if (fieldIndex.containsKey(item.getFormulaExpression())) continue;
                fieldIndex.put(item.getFormulaExpression(), colindex);
            }
        }
        return fieldIndex;
    }

    private SetColumnReturn setColumn(IGroupingQuery groupQuery, QuerySelectField sf, boolean isDetail, boolean isGather, String fieldCondition, Map<String, Integer> fieldIndex, boolean fieldSort, FieldDefine refField) {
        SetColumnReturn returnObj = new SetColumnReturn();
        try {
            int colIndex = 0;
            FieldDefine field = dataDefinitionRuntimeController.queryFieldDefine(sf.getCode());
            if (sf.getCode() == null && sf.getTitle() != null) {
                int index = groupQuery.addExpressionColumn(sf.getTitle());
                fieldIndex.put(sf.getTitle(), index);
                returnObj.condition = fieldCondition;
                returnObj.columnIndex = fieldIndex;
                return returnObj;
            }
            if (field != null) {
                boolean isMaster = Boolean.parseBoolean(sf.getIsMaster());
                if (!isDetail && sf.getIsGroupField()) {
                    colIndex = groupQuery.addGroupColumn(field);
                    fieldIndex.put(field.getKey(), colIndex);
                } else if (!sf.isHidden() || isMaster) {
                    colIndex = groupQuery.addColumn(field);
                    fieldIndex.put(field.getKey(), colIndex);
                }
                if (isGather) {
                    if (!sf.isHidden()) {
                        if (sf.getGatherType() != null) {
                            groupQuery.setGatherType(colIndex, sf.getGatherType());
                        } else {
                            groupQuery.setGatherType(colIndex, field.getGatherType());
                        }
                    }
                } else if (!sf.isHidden()) {
                    groupQuery.setGatherType(colIndex, field.getGatherType());
                }
                if (!(!sf.getIsSorted() || fieldSort && Boolean.parseBoolean(sf.getIsMaster()))) {
                    QueryItemSortDefine sortDefine = sf.getSort();
                    if (!Boolean.parseBoolean(sf.getIsMaster()) && sortDefine.getSortType() != null) {
                        groupQuery.addOrderByItem(field, sortDefine.getSortType() == QuerySortType.SORT_DESC);
                    }
                    fieldCondition = this.getFilterConditions(fieldCondition, sortDefine, field);
                    ArrayList<Object> fvs = sortDefine.getFilterValues();
                    if (fvs != null && fvs.size() > 0) {
                        if (Boolean.parseBoolean(sf.getIsMaster())) {
                            String dimName = sf.getDimensionName();
                            if (sf.getDimensionName() == null) {
                                dimName = dataAssist.getDimensionName(field);
                            }
                            DimensionValueSet masterFilerSet = new DimensionValueSet();
                            masterFilerSet.setValue(dimName, fvs);
                            groupQuery.setMasterKeys(masterFilerSet);
                        } else {
                            groupQuery.setColumnFilterValueList(colIndex, fvs);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u5206\u7ec4\u67e5\u8be2\u589e\u52a0\u5217\u5f02\u5e38", (String)ex.getMessage());
        }
        returnObj.condition = fieldCondition;
        returnObj.columnIndex = fieldIndex;
        return returnObj;
    }

    public CompareType getSymbol(FilterSymbols symbol) {
        switch (symbol.toString()) {
            case "MoreThan": {
                return CompareType.MORE_THAN;
            }
            case "LessThan": {
                return CompareType.LESS_THAN;
            }
            case "MoreAndEque": {
                return CompareType.MORE_THAN_OR_EQUAL;
            }
            case "LessAndEque": {
                return CompareType.LESS_THAN_OR_EQUAL;
            }
            case "Eque": {
                return CompareType.EQUAL;
            }
            case "NotEque": {
                return CompareType.NOT_EQUAL;
            }
            case "Between": {
                return CompareType.BETWEEN;
            }
            case "NotBetween": {
                return CompareType.NOT_BETWEEN;
            }
            case "Contain": {
                return CompareType.CONTAINS;
            }
            case "NotContain": {
                return CompareType.NOT_CONTAINS;
            }
        }
        return CompareType.EQUAL;
    }

    private String getFilterConditions(String condition, QueryItemSortDefine sortDefine, FieldDefine field) {
        List<QueryFilterDefine> filters;
        List deployInfoByDataFieldKeys = iRuntimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{field.getKey()});
        DataFieldDeployInfoDO deployInfoByColumnKey = new DataFieldDeployInfoDO();
        if (deployInfoByDataFieldKeys.size() > 0) {
            deployInfoByColumnKey = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
        }
        if ((filters = sortDefine.getFilterCondition()) == null) {
            return condition;
        }
        String itemCondition = "";
        boolean isConditionNull = StringUtil.isNullOrEmpty((String)condition);
        int i = 0;
        for (QueryFilterDefine filter : filters) {
            if (filter.getSymbol() == null || filter.getValue() == null) continue;
            String expression = "";
            if (QueryHelper.isNumField(field)) {
                DataValidationExpression exp = DataValidationExpressionFactory.createExpression((FieldDefine)field, (CompareType)this.getSymbol(filter.getSymbol()), (Object)filter.getValue());
                expression = exp.toFormula();
            } else {
                StringBuilder fieldStr = new StringBuilder();
                fieldStr.append(deployInfoByColumnKey.getTableName()).append("[").append(deployInfoByColumnKey.getFieldName()).append("]");
                String fieldCondition = fieldStr.toString();
                switch (filter.getSymbol().toString()) {
                    case "Start": {
                        expression = fieldCondition + " like '" + filter.getValue() + "%' ";
                        break;
                    }
                    case "NotStart": {
                        expression = fieldCondition + " not like '" + filter.getValue() + "%' ";
                        break;
                    }
                    case "End": {
                        expression = fieldCondition + " like '%" + filter.getValue() + "'";
                        break;
                    }
                    case "NotEnd": {
                        expression = fieldCondition + " not like '%" + filter.getValue() + "' ";
                        break;
                    }
                    case "Contain": {
                        expression = fieldCondition + " like '%" + filter.getValue() + "%'";
                        break;
                    }
                    case "NotContain": {
                        expression = fieldCondition + " not like '%" + filter.getValue() + "%' ";
                    }
                }
            }
            itemCondition = i > 0 ? itemCondition + String.format(" %s %s%s", sortDefine.getFilterRelation(), expression, isConditionNull ? "" : ")") : (isConditionNull ? itemCondition + expression : itemCondition + String.format(" AND (%s", expression));
            ++i;
        }
        if (StringUtil.isNullOrEmpty((String)condition)) {
            return itemCondition;
        }
        condition = condition + String.format(" OR (%s)", itemCondition);
        return condition;
    }

    private String getFieldConditions(String condition, List<QueryFieldCondition> conditions, FieldDefine field) {
        List deployInfoByDataFieldKeys = iRuntimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{field.getKey()});
        DataFieldDeployInfoDO deployInfoByColumnKey = new DataFieldDeployInfoDO();
        if (deployInfoByDataFieldKeys.size() > 0) {
            deployInfoByColumnKey = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
        }
        if (conditions == null) {
            return condition;
        }
        String itemCondition = "";
        for (QueryFieldCondition queryFieldCondition : conditions) {
            if (queryFieldCondition.getSymbol() == null || queryFieldCondition.getValue() == null || queryFieldCondition.getValue() == null || "".equals(queryFieldCondition.getValue())) continue;
            String expression = "";
            if (field.getType().equals((Object)FieldType.FIELD_TYPE_DATE)) {
                Object[] ymd;
                String date = queryFieldCondition.getValue().toString();
                Object[] oYmd = ymd = date.split("-");
                String message = "Date({0},{1},{2})";
                String dataFormula = MessageFormat.format(message, oYmd);
                DataValidationExpression exp = DataValidationExpressionFactory.createExpression((FieldDefine)field, (CompareType)this.getSymbol(queryFieldCondition.getSymbol()), (Object)dataFormula);
                expression = exp.toFormula();
            } else if (QueryHelper.isNumField(field)) {
                DataValidationExpression exp = DataValidationExpressionFactory.createExpression((FieldDefine)field, (CompareType)this.getSymbol(queryFieldCondition.getSymbol()), (Object)queryFieldCondition.getValue());
                expression = exp.toFormula();
            } else {
                StringBuilder fieldStr = new StringBuilder();
                fieldStr.append(deployInfoByColumnKey.getTableName()).append("[").append(deployInfoByColumnKey.getFieldName()).append("]");
                String fieldCondition = fieldStr.toString();
                switch (queryFieldCondition.getSymbol().toString()) {
                    case "Start": {
                        expression = fieldCondition + " like '" + queryFieldCondition.getValue() + "%' ";
                        break;
                    }
                    case "NotStart": {
                        expression = fieldCondition + " not " + deployInfoByColumnKey.getFieldName() + " like '" + queryFieldCondition.getValue() + "%'";
                        break;
                    }
                    case "End": {
                        expression = fieldCondition + " like '%" + queryFieldCondition.getValue() + "'";
                        break;
                    }
                    case "NotEnd": {
                        expression = fieldCondition + " not " + deployInfoByColumnKey.getFieldName() + " like '%" + queryFieldCondition.getValue() + "'";
                        break;
                    }
                    case "Contain": {
                        expression = "position('" + queryFieldCondition.getValue() + "'," + fieldCondition + ")>=1";
                        break;
                    }
                    case "NotContain": {
                        expression = "position('" + queryFieldCondition.getValue() + "'," + fieldCondition + ")<1";
                    }
                }
            }
            if (StringUtil.isNullOrEmpty((String)condition) && StringUtil.isNullOrEmpty((String)itemCondition)) {
                itemCondition = itemCondition + expression;
                continue;
            }
            if (!StringUtil.isNullOrEmpty((String)itemCondition)) {
                itemCondition = itemCondition + String.format(" AND %s", expression);
                continue;
            }
            itemCondition = itemCondition + expression;
        }
        if (StringUtil.isNullOrEmpty((String)condition)) {
            return itemCondition;
        }
        if (!StringUtil.isNullOrEmpty((String)itemCondition)) {
            condition = condition + String.format(" AND (%s)", itemCondition);
        }
        return condition;
    }

    public static Boolean checkFormula(String formula) {
        ReportFormulaParser parser = dataAssist.createFormulaParser(false);
        try {
            IExpression expression = parser.parseEval(formula, (IContext)qContext);
            return true;
        }
        catch (ParseException e) {
            logger.error("\u516c\u5f0f[" + formula + "]\u89e3\u6790\u9519\u8bef\uff1a" + e.getMessage());
            return false;
        }
    }

    public IGroupingTable getDataTable(IGroupingQuery groupQuery, QueryBlockDefine block, boolean isDetail, boolean isExport) {
        try {
            boolean flag;
            String formScemeKey = null;
            List<QuerySelectField> fields = block.getBlockInfo().getQueryDimensions().get(0).getSelectFields();
            for (QuerySelectField sf : fields) {
                if (sf.getFormSchemeId() == null) continue;
                formScemeKey = sf.getFormSchemeId();
                break;
            }
            ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.viewController, dataDefinitionRuntimeController, entityViewRunTimeController, formScemeKey);
            QueryGridExtension gridExtension = new QueryGridExtension(block.getBlockExtension());
            String penetrationType = gridExtension.getPenetrationType();
            if (null != penetrationType) {
                SummaryScheme sumScheme = null;
                sumScheme = gridExtension.getSumSchemeObject();
                ReloadTreeInfo reloadTreeInfo = null;
                if (sumScheme != null) {
                    reloadTreeInfo = new SummarySchemeUtils().toReloadTreeInfo(sumScheme);
                }
                String formKey = gridExtension.getFormKey();
                gridExtension.getFormSchemeId();
                String measureMap = gridExtension.getMeasureMap();
                String key = null;
                String value = null;
                String decimal = null;
                if (!StringUtils.isEmpty((String)gridExtension.getDecimal())) {
                    decimal = gridExtension.getDecimal();
                }
                try {
                    JSONObject object = new JSONObject(measureMap);
                    for (Map.Entry stringObjectEntry : object.toMap().entrySet()) {
                        key = (String)stringObjectEntry.getKey();
                        value = stringObjectEntry.getValue().toString();
                        logger.info("");
                    }
                }
                catch (Exception e) {
                    logger.error("\u91cf\u7eb2\u83b7\u53d6\u5f02\u5e38\uff01", e);
                }
                DimensionValueSet masterKeys = groupQuery.getMasterKeys();
                try {
                    MeasureFieldValueProcessor measureFieldValueProcessor = new MeasureFieldValueProcessor(formKey, key, value, masterKeys, reloadTreeInfo, decimal);
                    environment.setFieldValueProcessor((IFieldValueProcessor)measureFieldValueProcessor);
                    executorContext.setEnv((IFmlExecEnvironment)environment);
                }
                catch (Exception e) {
                    logger.error("\u91cf\u7eb2\u8bbe\u7f6e\u5f02\u5e38\uff01", e);
                }
            } else {
                executorContext.setEnv((IFmlExecEnvironment)environment);
            }
            IGroupingTable groupTable = groupQuery.executeReader(executorContext);
            int totalCount = groupTable.getTotalCount();
            boolean bl = flag = isDetail && !isExport;
            if (flag) {
                int totalPage = (int)Math.ceil((double)totalCount / (double)block.getPageData().getPageSize());
                if (totalPage == 0) {
                    totalPage = 1;
                }
                totalCount = groupTable.getCount();
                block.getPageData().setTotalPage(totalPage);
            } else {
                block.getPageData().setTotalPage(1);
            }
            return groupTable;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u5206\u7ec4\u67e5\u8be2\u5f02\u5e38", (String)ex.getMessage());
            return null;
        }
    }

    private String[] getDefaultDate(PeriodType ptype) {
        Calendar dataTime = Calendar.getInstance();
        String[] dateList = new String[2];
        int year = dataTime.get(1);
        switch (ptype.type()) {
            case 0: 
            case 1: {
                dateList[0] = year - 11 + "N0001";
                dateList[1] = year + "N0001";
                break;
            }
            case 2: {
                dateList[0] = year - 5 + "H0001";
                dateList[1] = year + "H0001";
                break;
            }
            case 3: {
                dateList[0] = year - 2 + "J0001";
                dateList[1] = year + "H0004";
                break;
            }
            case 4: {
                dateList[0] = year + "Y0001";
                dateList[1] = year + "Y0012";
                break;
            }
            case 5: {
                break;
            }
            case 6: {
                dateList[0] = year + "D0001";
                dateList[1] = year + "D0365";
                break;
            }
            case 7: {
                break;
            }
            default: {
                dateList[0] = year + "Y0001";
                dateList[1] = year + "Y0012";
            }
        }
        return dateList;
    }

    public static String getCaption(IEntityRow row, DataLinkDefine linkDefine) {
        String rowCaption = row.getTitle();
        try {
            rowCaption = "";
            if (!StringUtils.isEmpty((String)linkDefine.getCaptionFieldsString())) {
                String[] fields = linkDefine.getCaptionFieldsString().split(";");
                for (int i = 0; i < fields.length; ++i) {
                    String fieldKey = fields[i];
                    if (fieldKey == null) continue;
                    com.jiuqi.nr.entity.engine.data.AbstractData value = row.getValue(fieldKey);
                    if (rowCaption.length() > 0) {
                        rowCaption = rowCaption + "|";
                    }
                    rowCaption = rowCaption + value.getAsString();
                }
            } else {
                rowCaption = " ";
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return rowCaption;
    }

    public static String getCaption(IEntityRow row, DataLinkDefine linkDefine, boolean isdropcaption) {
        String rowCaption = row.getTitle();
        try {
            if (linkDefine.getEnumLinkageStatus()) {
                rowCaption = "";
                if (isdropcaption || !StringUtils.isEmpty((String)linkDefine.getCaptionFieldsString())) {
                    String[] fields = isdropcaption ? linkDefine.getDropDownFieldsString().split(";") : linkDefine.getCaptionFieldsString().split(";");
                    for (int i = 0; i < fields.length; ++i) {
                        String fieldKey = fields[i];
                        if (fieldKey == null) continue;
                        com.jiuqi.nr.entity.engine.data.AbstractData value = row.getValue(fieldKey);
                        if (rowCaption.length() > 0) {
                            rowCaption = rowCaption + "|";
                        }
                        rowCaption = rowCaption + value.getAsString();
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return rowCaption;
    }

    public List<DataLinkDefine> getAllLinkDefinesInRegion(String regionKey) {
        return this.viewController.getAllLinksInRegion(regionKey);
    }

    public DataLinkDefine queryLinkDefineByKey(String linkKey) {
        return this.viewController.queryDataLinkDefine(linkKey);
    }

    public QueryDimensionDefine createDimensionByLink(String linkKey) throws Exception {
        DataLinkDefine link = this.queryLinkDefineByKey(linkKey);
        QueryDimensionDefine dimension = new QueryDimensionDefine();
        if (link != null) {
            dimension.setFieldKey(link.getLinkExpression());
            dimension.setTitle(link.getTitle());
            String linkExpression = link.getLinkExpression();
            FieldDefine fieldDefine1 = dataDefinitionRuntimeController.queryFieldDefine(linkExpression);
            DataField fieldDefine = iRuntimeDataSchemeService.getDataField(linkExpression);
            String entityId = fieldDefine.getDataTableKey();
            dimension.setViewId(entityId);
            EntityViewDefine entityView = QueryHelper.getEntityView(entityId);
            if (entityView != null) {
                List deployInfoByDataFieldKeys = iRuntimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine.getKey()});
                if (deployInfoByDataFieldKeys.size() > 0) {
                    DataFieldDeployInfo dataFieldDeployInfo = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
                    String tableName = dataFieldDeployInfo.getTableName();
                    dimension.setDimensionName(tableName);
                    dimension.setDimensionType(QueryDimensionType.QDT_DICTIONARY);
                    if (StringUtil.isNullOrEmpty((String)dimension.getTitle())) {
                        dimension.setTitle(fieldDefine.getTitle());
                    }
                    dimension.setIstree("true");
                }
            } else {
                String dimName;
                boolean numField = QueryHelper.isNumField(fieldDefine);
                dimension.setIsNum(numField);
                dimension.setFieldConditionType(this.getFieldConditionType(fieldDefine1));
                if (StringUtil.isNullOrEmpty((String)dimension.getTitle())) {
                    dimension.setTitle(fieldDefine.getTitle());
                }
                if ((dimName = QueryHelper.getDimName(fieldDefine1)) == null || "".equals(dimName)) {
                    dimName = fieldDefine.getCode();
                }
                dimension.setDimensionName(dimName);
                dimension.setDimensionType(QueryDimensionType.QDT_GRIDFIELD);
                dimension.setViewId(link.getLinkExpression());
                dimension.setIstree("false");
            }
        } else {
            dimension.setFieldKey(linkKey);
            dimension.setTitle("");
            dimension.setViewId(linkKey);
        }
        return dimension;
    }

    public QueryDimensionDefine createDimensionByEntityFieldKey(String fieldKey) {
        QueryDimensionDefine dimension = new QueryDimensionDefine();
        FieldDefine field = null;
        try {
            field = dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        dimension.setTitle(field.getTitle());
        dimension.setFieldKey(fieldKey);
        EntityViewDefine entityView = null;
        if (field.getEntityKey() != null && !"".equals(field.getEntityKey())) {
            entityView = this.getRelationEnum(field.getEntityKey());
        }
        if (field.getEntityKey() == null) {
            dimension.setViewId(fieldKey);
            boolean numField = QueryHelper.isNumField(field);
            dimension.setIsNum(numField);
            dimension.setFieldConditionType(this.getFieldConditionType(field));
            dimension.setDimensionName(field.getCode());
            dimension.setDimensionType(QueryDimensionType.QDT_GRIDFIELD);
            dimension.setIstree("false");
        } else {
            String dimName = QueryHelper.getDimName(entityView);
            dimension.setDimensionName(dimName);
            dimension.setViewId(field.getEntityKey());
            dimension.setDimensionType(QueryDimensionType.QDT_DICTIONARY);
            dimension.setIstree("true");
        }
        return dimension;
    }

    private EntityViewDefine getRelationEnum(String referFieldKey) {
        FieldDefine relationField = null;
        try {
            relationField = dataDefinitionRuntimeController.queryFieldDefine(referFieldKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (relationField == null) {
            return null;
        }
        if (relationField.getEntityKey() != null) {
            this.getRelationEnum(relationField.getEntityKey());
        } else {
            try {
                String entityKey = relationField.getEntityKey();
                return entityViewRunTimeController.buildEntityView(entityKey);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    @Deprecated
    public static String getStates(UploadState state) {
        switch (state) {
            case ORIGINAL: 
            case ORIGINAL_UPLOAD: 
            case ORIGINAL_SUBMIT: {
                return "'start'";
            }
            case SUBMITED: {
                return "'act_submit','batch_act_submit','cus_submit'";
            }
            case RETURNED: {
                return "'act_return','batch_act_return','cus_return'";
            }
            case UPLOADED: {
                return "'act_upload','batch_act_upload','cus_upload'";
            }
            case CONFIRMED: {
                return "'act_confirm','batch_act_confirm','cus_confirm'";
            }
            case REJECTED: {
                return "'act_reject','batch_act_reject','cus_reject'";
            }
        }
        return "'start'";
    }

    public boolean autoGatherCheck(List<QuerySelectField> selectedFields, boolean isQueryDetail) {
        try {
            Optional<QuerySelectField> numFieldOptional = selectedFields.stream().filter(e -> QueryHelper.isNumField(e)).findFirst();
            if (numFieldOptional.isPresent() && isQueryDetail) {
                TaskDefine taskDefine;
                TaskGatherType taskGatherType;
                QuerySelectField field = numFieldOptional.get();
                String taskId = field.getTaskId();
                List fields = selectedFields.stream().filter(e -> !Boolean.parseBoolean(e.getIsMaster())).collect(Collectors.toList());
                long count = fields.stream().map(e -> e.getTaskId()).collect(Collectors.toList()).stream().count();
                if (taskId != null && !"".equals(taskId) && count == (long)fields.size() && (taskGatherType = (taskDefine = this.viewController.queryTaskDefine(taskId)).getTaskGatherType()) == TaskGatherType.TASK_GATHER_AUTO) {
                    return true;
                }
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return false;
    }

    public boolean autoGatherCheck(List<QuerySelectField> selectedFields) {
        try {
            Optional<QuerySelectField> numFieldOptional = selectedFields.stream().filter(QueryHelper::isNumField).findFirst();
            if (numFieldOptional.isPresent()) {
                TaskDefine taskDefine;
                TaskGatherType taskGatherType;
                QuerySelectField field = numFieldOptional.get();
                String taskId = field.getTaskId();
                List fields = selectedFields.stream().filter(e -> !Boolean.parseBoolean(e.getIsMaster())).collect(Collectors.toList());
                long count = fields.stream().map(e -> e.getTaskId()).collect(Collectors.toList()).stream().count();
                if (taskId != null && !"".equals(taskId) && count == (long)fields.size() && (taskGatherType = (taskDefine = this.viewController.queryTaskDefine(taskId)).getTaskGatherType()) == TaskGatherType.TASK_GATHER_AUTO) {
                    return true;
                }
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return false;
    }

    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List dest = (List)in.readObject();
        return dest;
    }

    public static String getCurrentPeriod(int periodType, int periodOffset, String pfromPeriod, String ptoPeriod, String viewId) {
        JSONObject period = new JSONObject();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(DateTime.now().toDate());
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)calendar, (int)periodType, (int)periodOffset);
        if (8 == periodType) {
            IEntityTable entityDatas = QueryHelper.getEntityTable(viewId);
            String currValue = "";
            List rows = entityDatas.getAllRows();
            if (rows.size() > 0) {
                IEntityRow entityData = (IEntityRow)rows.get(rows.size() - 1);
                currValue = entityData.getEntityKeyData();
                period.put("code", (Object)currValue);
                period.put("title", (Object)entityData.getTitle());
            }
        } else {
            PeriodWrapper fromPeriodWrapper = null;
            PeriodWrapper toPeriodWrapper = null;
            try {
                fromPeriodWrapper = new PeriodWrapper(pfromPeriod);
                toPeriodWrapper = new PeriodWrapper(ptoPeriod);
            }
            catch (Exception rows) {
                // empty catch block
            }
            if (!StringUtils.isEmpty((String)pfromPeriod) && !StringUtils.isEmpty((String)ptoPeriod)) {
                GregorianCalendar currentCalendar = PeriodUtil.period2Calendar((PeriodWrapper)currentPeriod);
                GregorianCalendar fromCalendar = PeriodUtil.period2Calendar((String)pfromPeriod);
                GregorianCalendar toCalendar = PeriodUtil.period2Calendar((String)ptoPeriod);
                if (currentCalendar.compareTo(fromCalendar) < 0 || currentCalendar.compareTo(toCalendar) > 0) {
                    GregorianCalendar nowGregorianCalendar = new GregorianCalendar();
                    currentPeriod = nowGregorianCalendar.after(toCalendar) ? toPeriodWrapper : fromPeriodWrapper;
                }
                try {
                    if (periodOffset > 0) {
                        if (currentPeriod.compareTo((Object)toPeriodWrapper) > 0) {
                            currentPeriod = new PeriodWrapper(ptoPeriod);
                        }
                    } else if (periodOffset < 0 && currentPeriod.compareTo((Object)fromPeriodWrapper) < 0) {
                        currentPeriod = new PeriodWrapper(pfromPeriod);
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            period.put("code", (Object)currentPeriod.toString());
            period.put("title", (Object)currentPeriod.toTitleString());
        }
        return period.toString();
    }

    public static int maxperiodType(int a, int b) {
        if (a == 8 || b == 8) {
            return 8;
        }
        if ((a == 7 || b == 7) && b + a == 15) {
            return 7;
        }
        return Math.min(a, b);
    }

    public static int strToPeriod(String periodStr) {
        char c = periodStr.charAt(4);
        String typeString = String.valueOf(c);
        int strType = 6;
        switch (typeString) {
            case "N": {
                strType = 1;
                break;
            }
            case "H": {
                strType = 2;
                break;
            }
            case "J": {
                strType = 3;
                break;
            }
            case "Y": {
                strType = 4;
                break;
            }
            case "X": {
                strType = 5;
                break;
            }
            case "R": {
                strType = 6;
                break;
            }
            case "Z": {
                strType = 7;
                break;
            }
            case "B": {
                strType = 8;
            }
        }
        return strType;
    }

    public List<Object> initDataQuery(String regionKey, FieldDefine fieldDefine, DimensionValueSet masterKey) {
        ArrayList<Object> objects = new ArrayList<Object>();
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setRegionKey(regionKey);
        DataRegionDefine regionDefine = this.viewController.queryDataRegionDefine(regionKey);
        FormDefine formDefine = this.viewController.queryFormById(regionDefine.getFormKey());
        queryEnvironment.setFormKey(formDefine.getKey());
        queryEnvironment.setFormCode(formDefine.getFormCode());
        IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
        if (StringUtils.isNotEmpty((String)regionDefine.getInputOrderFieldKey())) {
            FieldDefine inputOrderField = null;
            try {
                inputOrderField = dataDefinitionRuntimeController.queryFieldDefine(regionDefine.getInputOrderFieldKey());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            if (inputOrderField != null) {
                DataFieldDeployInfo deployInfoByColumnKey = iRuntimeDataSchemeService.getDeployInfoByColumnKey(inputOrderField.getKey());
                dataQuery.setDefaultGroupName(deployInfoByColumnKey.getTableName());
            }
        }
        int column = dataQuery.addColumn(fieldDefine);
        dataQuery.setMasterKeys(masterKey);
        ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
        try {
            IReadonlyTable table = dataQuery.executeReader(executorContext);
            objects.add(table);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return objects;
    }

    public Boolean rowDuQuery(String regionKey, DimensionValueSet masterKey) {
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setRegionKey(regionKey);
        DataRegionDefine regionDefine = this.viewController.queryDataRegionDefine(regionKey);
        FormDefine formDefine = this.viewController.queryFormById(regionDefine.getFormKey());
        queryEnvironment.setFormKey(formDefine.getKey());
        queryEnvironment.setFormCode(formDefine.getFormCode());
        IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
        if (StringUtils.isNotEmpty((String)regionDefine.getInputOrderFieldKey())) {
            FieldDefine inputOrderField = null;
            try {
                inputOrderField = dataDefinitionRuntimeController.queryFieldDefine(regionDefine.getInputOrderFieldKey());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            if (inputOrderField != null) {
                DataFieldDeployInfo deployInfoByColumnKey = iRuntimeDataSchemeService.getDeployInfoByColumnKey(inputOrderField.getKey());
                dataQuery.setDefaultGroupName(deployInfoByColumnKey.getTableName());
                int n = dataQuery.addColumn(inputOrderField);
            }
        }
        if (dataQuery.getColumnSize() == 0) {
            try {
                List fieldKeysInRegion = this.viewController.getFieldKeysInRegion(regionKey);
                List fieldDefines = dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)fieldKeysInRegion);
                for (FieldDefine fieldDefine : fieldDefines) {
                    dataQuery.addColumn(fieldDefine);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        dataQuery.setMasterKeys(masterKey);
        ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
        try {
            IReadonlyTable table = dataQuery.executeReader(executorContext);
            if (table.getCount() > 0) {
                return false;
            }
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public Double queryFloatOrder(String regionKey, FieldDefine fieldDefine, DimensionValueSet masterKey) {
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setRegionKey(regionKey);
        DataRegionDefine regionDefine = this.viewController.queryDataRegionDefine(regionKey);
        FormDefine formDefine = this.viewController.queryFormById(regionDefine.getFormKey());
        queryEnvironment.setFormKey(formDefine.getKey());
        queryEnvironment.setFormCode(formDefine.getFormCode());
        IGroupingQuery dataQuery = dataAccessProvider.newGroupingQuery(queryEnvironment);
        if (StringUtils.isNotEmpty((String)regionDefine.getInputOrderFieldKey())) {
            FieldDefine inputOrderField = null;
            try {
                inputOrderField = dataDefinitionRuntimeController.queryFieldDefine(regionDefine.getInputOrderFieldKey());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            if (inputOrderField != null) {
                DataFieldDeployInfo deployInfoByColumnKey = iRuntimeDataSchemeService.getDeployInfoByColumnKey(inputOrderField.getKey());
                dataQuery.setDefaultGroupName(deployInfoByColumnKey.getTableName());
            }
        }
        int column = dataQuery.addColumn(fieldDefine);
        dataQuery.setGatherType(column, FieldGatherType.FIELD_GATHER_MAX);
        dataQuery.setMasterKeys(masterKey);
        ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
        try {
            IGroupingTable table = dataQuery.executeReader(executorContext);
            IDataRow dataRow = table.getItem(0);
            AbstractData value = dataRow.getValue(fieldDefine);
            if (value.isNull) {
                return null;
            }
            return value.getAsFloat();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private static /* synthetic */ String lambda$setConditions$30(QuerySelectItem e) {
        return e.getCode();
    }

    private static /* synthetic */ boolean lambda$setConditions$29(QuerySelectField e) {
        return "true".equals(e.getIsMaster());
    }

    private static /* synthetic */ boolean lambda$setConditions$28(QueryDimensionDefine dim, QuerySelectField item) {
        return item.getDimensionName() != null && item.getDimensionName().equals(dim.getDimensionName());
    }

    private static /* synthetic */ boolean lambda$setConditions$27(QueryDimensionDefine dim, QueryDimensionDefine dimension) {
        return dimension.getLayoutType() == QueryLayoutType.LYT_CONDITION && dim.getViewId().equals(dimension.getViewId()) && dimension.getSelectItems() != null && dimension.getSelectItems().size() > 0;
    }

    private static /* synthetic */ boolean lambda$setConditions$26(QueryDimensionDefine dim, QueryDimensionDefine dimension) {
        return dimension.getLayoutType() == QueryLayoutType.LYT_CONDITION && dimension.getViewId().equals(dim.getViewId()) && dimension.getSelectItems() != null && dimension.getSelectItems().size() > 0;
    }

    private static /* synthetic */ String lambda$setConditions$25(IEntityRow i) {
        return i.getEntityKeyData();
    }

    private static /* synthetic */ boolean lambda$setConditions$24(QueryDimensionDefine dim, QueryDimensionDefine dimension) {
        return dimension.getLayoutType() == QueryLayoutType.LYT_CONDITION && dimension.getViewId().equals(dim.getViewId()) && dimension.getSelectItems() != null && dimension.getSelectItems().size() > 0;
    }

    private static /* synthetic */ boolean lambda$setConditions$23(QuerySelectField selectField) {
        return selectField.isSelectByField();
    }

    private static /* synthetic */ boolean lambda$setConditions$22(QueryDimensionDefine d) {
        return d.getDimensionType() == QueryDimensionType.QDT_ENTITY && d.getLayoutType() != QueryLayoutType.LYT_CONDITION && !d.isPeriodDim();
    }

    private static /* synthetic */ boolean lambda$setConditions$21(QueryDimensionDefine d) {
        return d.getDimensionType() != QueryDimensionType.QDT_FIELD && TableKind.TABLE_KIND_ENTITY_PERIOD.toString().equals(d.getTableKind()) && d.getLayoutType() == QueryLayoutType.LYT_CONDITION && d.getPeriodType() != null;
    }

    private static /* synthetic */ boolean lambda$setConditions$20(QueryDimensionDefine d) {
        return d.getDimensionType() != QueryDimensionType.QDT_FIELD && d.isPeriodDim() && d.getLayoutType() != QueryLayoutType.LYT_CONDITION;
    }

    private static /* synthetic */ boolean lambda$setConditions$19(String[] masterKeys, QueryDimensionDefine p) {
        return p.getDimensionType() != QueryDimensionType.QDT_FIELD && p.getViewId() != null && p.getViewId().equals(masterKeys[0]);
    }

    static {
        queryEntityUtil = (QueryEntityUtil)BeanUtil.getBean(QueryEntityUtil.class);
        pattern = Pattern.compile(COLOR16_REGEX);
    }

    class SetColumnReturn {
        public String condition;
        public Map<String, Integer> columnIndex = new LinkedHashMap<String, Integer>();

        SetColumnReturn() {
        }
    }
}

