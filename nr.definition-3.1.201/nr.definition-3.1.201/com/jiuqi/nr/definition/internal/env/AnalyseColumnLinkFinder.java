/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.EntityResetCacheService
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IEntityItem
 *  com.jiuqi.np.dataengine.intf.IEntityQuery
 *  com.jiuqi.np.dataengine.intf.IEntityRow
 *  com.jiuqi.np.dataengine.intf.IModifierEntityTable
 *  com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl
 *  com.jiuqi.np.dataengine.setting.AuthorityType
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IEntityItem;
import com.jiuqi.np.dataengine.intf.IEntityQuery;
import com.jiuqi.np.dataengine.intf.IEntityRow;
import com.jiuqi.np.dataengine.intf.IModifierEntityTable;
import com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl;
import com.jiuqi.np.dataengine.setting.AuthorityType;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.AnalyseDataLinkFinder;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class AnalyseColumnLinkFinder
implements IDataModelLinkFinder {
    private final DataModelService dataModelService;
    private final IRuntimeDataSchemeService runtimeDataSchemeService;
    private final DataFieldDeployInfoService dataFieldDeployInfoService;
    private IRunTimeViewController controller;
    private IDataDefinitionRuntimeController runtimeController;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private String formSchemeKey;
    private String sourceFormSchemeKey;
    private Map<String, ReportInfo> reportInfoMp = new HashMap<String, ReportInfo>();
    private Map<String, DataRegionDefine> regionMp = new HashMap<String, DataRegionDefine>();
    private Logger logger = LogFactory.getLogger(AnalyseDataLinkFinder.class);
    private IConnectionProvider connectionProvider;
    private IDataAccessProvider dataAccessProvider;
    private IEntityMetaService entityMetaService;

    public AnalyseColumnLinkFinder(IRunTimeViewController controller, IDataDefinitionRuntimeController runtimeController, IEntityViewRunTimeController entityViewRunTimeController, String formSchemeKey, String sourceFormSchemeKey) {
        this.controller = controller;
        this.runtimeController = runtimeController;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.formSchemeKey = formSchemeKey;
        this.sourceFormSchemeKey = sourceFormSchemeKey;
        this.connectionProvider = BeanUtil.getBean(IConnectionProvider.class);
        this.dataAccessProvider = BeanUtil.getBean(IDataAccessProvider.class);
        this.entityMetaService = BeanUtil.getBean(IEntityMetaService.class);
        this.dataModelService = BeanUtil.getBean(DataModelService.class);
        this.runtimeDataSchemeService = BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.dataFieldDeployInfoService = BeanUtil.getBean(DataFieldDeployInfoService.class);
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        try {
            DataLinkDefine dataLink = isGridPosition ? this.controller.queryDataLinkDefineByXY(reportInfo.getReportKey(), colIndex, rowIndex) : this.controller.queryDataLinkDefineByColRow(reportInfo.getReportKey(), colIndex, rowIndex);
            if (dataLink == null) {
                return null;
            }
            if (dataLink.getLinkExpression() == null) {
                this.logger.error("\u5355\u5143\u683c\u672a\u5173\u8054\u6307\u6807");
                return null;
            }
            List deployInfos = this.dataFieldDeployInfoService.getByDataFieldKeys(new String[]{dataLink.getLinkExpression()});
            ColumnModelDefine columnModelDefine = null;
            if (!CollectionUtils.isEmpty(deployInfos)) {
                columnModelDefine = this.dataModelService.getColumnModelDefineByID(((DataFieldDeployInfo)deployInfos.get(0)).getColumnModelKey());
            }
            if (columnModelDefine == null) {
                this.logger.error("\u6839\u636e\u5355\u5143\u683c\u5173\u8054\u6307\u6807key:" + dataLink.getLinkExpression() + "\u6ca1\u6709\u627e\u5230\u5973\u5a32\u6307\u6807\u5b9a\u4e49");
                return null;
            }
            return this.getDataLinkColumn(reportInfo, dataLink, columnModelDefine);
        }
        catch (Exception e) {
            this.logger.error("\u89e3\u6790\u6570\u636e\u94fe\u63a5\u51fa\u9519\uff01", (Throwable)e);
            return null;
        }
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, String dataLinkCode) {
        try {
            DataLinkDefine dataLink = this.controller.queryDataLinkDefineByUniquecode(reportInfo.getReportKey(), dataLinkCode);
            if (dataLink == null) {
                return null;
            }
            if (dataLink.getLinkExpression() == null) {
                this.logger.error("\u5355\u5143\u683c\u672a\u5173\u8054\u6307\u6807");
                return null;
            }
            List deployInfos = this.dataFieldDeployInfoService.getByDataFieldKeys(new String[]{dataLink.getLinkExpression()});
            ColumnModelDefine columnModelDefine = null;
            if (!CollectionUtils.isEmpty(deployInfos)) {
                columnModelDefine = this.dataModelService.getColumnModelDefineByID(((DataFieldDeployInfo)deployInfos.get(0)).getColumnModelKey());
            }
            if (columnModelDefine == null) {
                this.logger.error("\u6839\u636e\u5355\u5143\u683c\u5173\u8054\u6307\u6807key:" + dataLink.getLinkExpression() + "\u6ca1\u6709\u627e\u5230\u5973\u5a32\u6307\u6807\u5b9a\u4e49");
                return null;
            }
            return this.getDataLinkColumn(reportInfo, dataLink, columnModelDefine);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public DataModelLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        DataModelLinkColumn linkColumn = this.findDataColumnByFieldName(reportInfo, fieldName, this.sourceFormSchemeKey);
        if (linkColumn == null) {
            linkColumn = this.findDataColumnByFieldName(reportInfo, fieldName, this.formSchemeKey);
        }
        return linkColumn;
    }

    public ReportInfo findReportInfo(String reportName) {
        ReportInfo reportInfo = this.findReportInfoByFormScheme(reportName, this.sourceFormSchemeKey);
        if (reportInfo == null) {
            reportInfo = this.findReportInfoByFormScheme(reportName, this.formSchemeKey);
        }
        return reportInfo;
    }

    public ReportInfo findReportInfo(String linkAlias, String reportName) {
        try {
            String periodModiferStr = "";
            String periodDim = "";
            String schemeKey = null;
            TaskLinkDefine linkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.sourceFormSchemeKey, linkAlias);
            FormSchemeDefine formScheme = this.controller.getFormScheme(this.sourceFormSchemeKey);
            PeriodType periodType = formScheme.getPeriodType();
            if (linkDefine != null) {
                schemeKey = linkDefine.getRelatedFormSchemeKey();
                switch (linkDefine.getConfiguration()) {
                    case PERIOD_TYPE_OFFSET: {
                        periodModiferStr = linkDefine.getPeriodOffset();
                        break;
                    }
                    case PERIOD_TYPE_NEXT: {
                        periodModiferStr = "+1" + (char)PeriodConsts.typeToCode((int)periodType.type());
                        break;
                    }
                    case PERIOD_TYPE_PREVIOUS: {
                        periodModiferStr = "-1" + (char)PeriodConsts.typeToCode((int)periodType.type());
                        break;
                    }
                    case PERIOD_TYPE_SPECIFIED: {
                        periodDim = linkDefine.getSpecified();
                        break;
                    }
                }
            } else {
                FormSchemeDefine schemeDefine = this.controller.getFormschemeByCode(linkAlias);
                if (schemeDefine != null) {
                    schemeKey = schemeDefine.getKey();
                }
            }
            ReportInfo reportInfo = this.findReportInfoByFormScheme(reportName, schemeKey);
            reportInfo.setPeriodModifierStr(periodModiferStr);
            reportInfo.setPeriodDim(periodDim);
            return reportInfo;
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u62a5\u8868\u5931\u8d25:" + e.getMessage());
            return null;
        }
    }

    public List<ReportInfo> findAllRelatedReportInfo(String reportName) {
        ArrayList<ReportInfo> reportInfos = new ArrayList<ReportInfo>();
        ReportInfo reportInfo = null;
        List<TaskLinkDefine> links = this.controller.queryLinksByCurrentFormScheme(this.sourceFormSchemeKey);
        for (TaskLinkDefine linkDefine : links) {
            String schemeKey = linkDefine.getRelatedFormSchemeKey();
            String periodModiferStr = "";
            String periodDim = "";
            switch (linkDefine.getConfiguration()) {
                case PERIOD_TYPE_OFFSET: {
                    periodModiferStr = linkDefine.getPeriodOffset();
                    break;
                }
                case PERIOD_TYPE_SPECIFIED: {
                    periodDim = linkDefine.getSpecified();
                    break;
                }
            }
            reportInfo = this.findReportInfoByFormScheme(reportName, schemeKey);
            if (reportInfo == null) continue;
            reportInfo.setPeriodModifierStr(periodModiferStr);
            reportInfo.setPeriodDim(periodDim);
            this.findReportInfoByFormScheme(reportName, this.sourceFormSchemeKey);
            reportInfos.add(reportInfo);
        }
        return reportInfos;
    }

    public Map<Object, List<Object>> findRelatedUnitKeyMap(ExecutorContext context, String linkAlias, String dimensionName, List<Object> unitKeys) {
        HashMap<Object, List<Object>> result = new HashMap<Object, List<Object>>();
        if (unitKeys == null || unitKeys.size() == 0) {
            return result;
        }
        TaskLinkDefine linkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.sourceFormSchemeKey, linkAlias);
        TaskLinkMatchingType matchingType = linkDefine.getMatchingType();
        if (matchingType.getValue() == TaskLinkMatchingType.MATCHING_TYPE_PRIMARYKEY.getValue()) {
            return this.findRelatedUnitKeyMap(unitKeys);
        }
        String relatedFormSchemeKey = linkDefine.getRelatedFormSchemeKey();
        try {
            FormSchemeDefine formScheme = this.controller.getFormScheme(relatedFormSchemeKey);
            String dw = formScheme.getDw();
            List<IEntityRow> currentUnits = this.getCurrentUnits(context, unitKeys);
            IEntityQuery entityQuery = this.dataAccessProvider.newEntityQuery();
            entityQuery.setFilterDataByAuthority(false);
            entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
            entityQuery.setAuthorityOperations(AuthorityType.NONE);
            entityQuery.setFilterDataByAuthority(false);
            entityQuery.setIgnoreViewFilter(true);
            int i = 0;
            ExecutorContext queryContext = new ExecutorContext(context.getRuntimeController());
            for (IEntityRow row : currentUnits) {
                block1 : switch (matchingType) {
                    case MATCHING_TYPE_TITLE: {
                        entityQuery.setRowFilter(String.format("[%s] = '%s'", this.entityMetaService.getEntityModel(dw).getNameField().getCode(), row.getTitle()));
                        break;
                    }
                    case FORM_TYPE_EXPRESSION: {
                        ExpressionEvaluatorImpl evaluatorImpl = new ExpressionEvaluatorImpl(this.getQueryParam());
                        AbstractData calcData = evaluatorImpl.eval(linkDefine.getCurrentFormula(), context, row.getRowKeys());
                        switch (linkDefine.getExpressionType()) {
                            case INCLUDE: {
                                entityQuery.setRowFilter(linkDefine.getRelatedFormula() + " like '%" + (calcData == null ? "" : calcData.getAsString()) + "%'");
                                break block1;
                            }
                            case END_WITH: {
                                entityQuery.setRowFilter(linkDefine.getRelatedFormula() + " like '%" + (calcData == null ? "" : calcData.getAsString()) + "'");
                                break block1;
                            }
                            case BEGIN_WITH: {
                                entityQuery.setRowFilter(linkDefine.getRelatedFormula() + " like '" + (calcData == null ? "" : calcData.getAsString()) + "%'");
                                break block1;
                            }
                        }
                        entityQuery.setRowFilter(String.format(linkDefine.getRelatedFormula() + " = '%s'", calcData == null ? "" : calcData.getAsString()));
                        break;
                    }
                    default: {
                        entityQuery.setRowFilter(String.format("[%s] = '%s'", this.entityMetaService.getEntityModel(dw).getCodeField().getCode(), row.getCode()));
                    }
                }
                IModifierEntityTable entityTable = entityQuery.executeQuery(queryContext);
                List rows = entityTable.getAllRows();
                List datas = null;
                if (rows != null && rows.size() > 0) {
                    datas = rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                }
                result.put(unitKeys.get(i++), datas);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        return result;
    }

    public List<Object> findRelatedUnitKey(ExecutorContext context, String linkAlias, String dimensionName, Object unitKey) {
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(unitKey);
        Map<Object, List<Object>> unitKeyMap = this.findRelatedUnitKeyMap(context, linkAlias, dimensionName, list);
        return unitKeyMap.get(unitKey);
    }

    public String getRelatedUnitDimName(ExecutorContext context, String linkAlias, String dimensionName) {
        TaskLinkDefine linkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.sourceFormSchemeKey, linkAlias);
        String relatedFormSchemeKey = linkDefine.getRelatedFormSchemeKey();
        EntityViewDefine entityView = this.getUnitView(relatedFormSchemeKey);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
        return dataAssist.getDimensionName(entityView);
    }

    public Map<String, List<Object>> expandByDimensions(ExecutorContext context, DataModelLinkColumn dataModelLinkColumn) {
        String fillLinkStr;
        String region = dataModelLinkColumn.getRegion();
        RegionSettingDefine regionSetting = this.controller.getRegionSetting(region);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
        HashMap<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
        if (regionSetting != null && !StringUtils.isEmpty((String)(fillLinkStr = regionSetting.getDictionaryFillLinks()))) {
            String[] fillLinks = fillLinkStr.split(";");
            Arrays.stream(fillLinks).forEach(fieldKey -> {
                FieldDefine fieldDefine = null;
                try {
                    fieldDefine = this.runtimeController.queryFieldDefine(fieldKey);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                IEntityQuery entityQuery = this.dataAccessProvider.newEntityQuery();
                entityQuery.setFilterDataByAuthority(false);
                entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
                entityQuery.setAuthorityOperations(AuthorityType.NONE);
                entityQuery.setFilterDataByAuthority(false);
                entityQuery.setIgnoreViewFilter(true);
                ExecutorContext queryContext = new ExecutorContext(context.getRuntimeController());
                try {
                    IModifierEntityTable entityTable = entityQuery.executeQuery(queryContext);
                    List rows = entityTable.getAllRows();
                    List datas = null;
                    if (rows != null && rows.size() > 0) {
                        datas = rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                    }
                    resultMap.put(dataAssist.getDimensionName(fieldDefine), datas);
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), (Throwable)e);
                }
            });
        }
        return resultMap;
    }

    public boolean is1V1Related(ExecutorContext context, String linkAlias) {
        return false;
    }

    private DataModelLinkColumn getDataLinkColumn(ReportInfo reportInfo, DataLinkDefine dataLink, ColumnModelDefine columnModelDefine) {
        DataModelLinkColumn column = new DataModelLinkColumn(columnModelDefine);
        if (dataLink != null) {
            DataRegionDefine region;
            column.setDataLinkCode(dataLink.getUniqueCode());
            if (dataLink.getColNum() > 0 && dataLink.getRowNum() > 0) {
                column.setGridPosition(new Position(dataLink.getPosX(), dataLink.getPosY()));
                column.setDataPosition(new Position(dataLink.getColNum(), dataLink.getRowNum()));
            }
            if ((region = this.regionMp.get(dataLink.getRegionKey())) == null) {
                region = this.controller.queryDataRegionDefine(dataLink.getRegionKey());
                this.regionMp.put(dataLink.getRegionKey(), region);
            }
            if (region != null) {
                String fillLinkStr;
                column.setRegion(region.getKey());
                RegionSettingDefine regionSetting = this.controller.getRegionSetting(region.getKey());
                ExecutorContext context = new ExecutorContext(this.runtimeController);
                IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
                if (regionSetting != null && !StringUtils.isEmpty((String)(fillLinkStr = regionSetting.getDictionaryFillLinks()))) {
                    String[] fillLinks = fillLinkStr.split(";");
                    List dims = Arrays.stream(fillLinks).map(fieldKey -> {
                        FieldDefine fieldDefine = null;
                        try {
                            fieldDefine = this.runtimeController.queryFieldDefine(fieldKey);
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        return fieldDefine == null ? null : dataAssist.getDimensionName(fieldDefine);
                    }).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
                    column.setExpandDims(dims);
                }
            }
        }
        column.setReportInfo(reportInfo);
        if (reportInfo.getPeriodModifier() != null) {
            column.setPeriodModifier(reportInfo.getPeriodModifier());
        }
        if (!StringUtils.isEmpty((String)reportInfo.getPeriodDim())) {
            DimensionValueSet dimension = new DimensionValueSet();
            dimension.setValue("DATATIME", (Object)reportInfo.getPeriodDim());
            column.setDimensionRestriction(dimension);
        }
        return column;
    }

    private DataModelLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName, String schemeKey) {
        try {
            if (reportInfo == null) {
                FormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
                if ("YF".equals(fieldName) && formScheme.getPeriodType() != PeriodType.MONTH) {
                    return null;
                }
                ColumnModelDefine fmdmField = this.getFmdmField(fieldName);
                if (null != fmdmField) {
                    return new DataModelLinkColumn(fmdmField);
                }
                return null;
            }
            FieldDefine fieldDefine = this.runtimeController.queryFieldDefineByCodeInRange(this.controller.getFieldKeysInForm(reportInfo.getReportKey()), fieldName);
            if (fieldDefine == null) {
                return null;
            }
            DataLinkDefine dataLink = null;
            List<DataLinkDefine> links = this.controller.getLinksInFormByField(reportInfo.getReportKey(), fieldDefine.getKey());
            if (links != null && links.size() > 0) {
                dataLink = links.get(0);
            }
            List deployInfos = this.dataFieldDeployInfoService.getByDataFieldKeys(new String[]{fieldDefine.getKey()});
            ColumnModelDefine columnModelDefine = null;
            if (!CollectionUtils.isEmpty(deployInfos)) {
                columnModelDefine = this.dataModelService.getColumnModelDefineByID(((DataFieldDeployInfo)deployInfos.get(0)).getColumnModelKey());
            }
            if (columnModelDefine == null) {
                this.logger.error("\u6839\u636e\u5355\u5143\u683c\u5173\u8054\u6307\u6807key:" + fieldDefine.getKey() + "\u6ca1\u6709\u627e\u5230\u5973\u5a32\u6307\u6807\u5b9a\u4e49");
                return null;
            }
            return this.getDataLinkColumn(reportInfo, dataLink, columnModelDefine);
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u627e\u62a5\u8868\u51fa\u73b0\u9519\u8bef\uff01", (Throwable)e);
            return null;
        }
    }

    private ColumnModelDefine getFmdmField(String fieldCode) {
        FormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
        TableModelDefine tableModel = this.entityMetaService.getTableModel(formScheme.getDw());
        return this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), fieldCode);
    }

    private ReportInfo findReportInfoByFormScheme(String reportName, String formSchemeKey) {
        try {
            if (StringUtils.isEmpty((String)formSchemeKey) || StringUtils.isEmpty((String)reportName)) {
                return null;
            }
            FormDefine form = this.controller.queryFormByCodeInScheme(formSchemeKey, reportName);
            if (form == null) {
                String rptTaskCode;
                TaskDefine rptTaskDefine;
                String taskCode;
                int indexOf;
                FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
                TaskDefine taskDefine = this.controller.queryTaskDefine(formScheme.getTaskKey());
                if (taskDefine.getTaskType() == TaskType.TASK_TYPE_ANALYSIS && (indexOf = (taskCode = taskDefine.getTaskCode()).indexOf("_AL_")) > 0 && (rptTaskDefine = this.controller.queryTaskDefineByCode(rptTaskCode = taskCode.substring(0, indexOf))) != null) {
                    FormSchemeDefine scheme;
                    List<FormSchemeDefine> schemes = this.controller.queryFormSchemeByTask(rptTaskDefine.getKey());
                    Iterator<FormSchemeDefine> iterator = schemes.iterator();
                    while (iterator.hasNext() && (form = this.controller.queryFormByCodeInScheme((scheme = iterator.next()).getKey(), reportName)) == null) {
                    }
                }
                if (form == null) {
                    return null;
                }
            }
            return this.getReportInfo(form);
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u62a5\u8868\u4fe1\u606f\u5931\u8d25\uff01", (Throwable)e);
            return null;
        }
    }

    private ReportInfo getReportInfo(FormDefine form) {
        if (this.reportInfoMp.containsKey(form.getKey())) {
            return this.reportInfoMp.get(form.getKey());
        }
        int maxRow = 0;
        int maxCol = 0;
        try {
            List<DataLinkDefine> allLinks = this.controller.getAllLinksInForm(form.getKey());
            for (DataLinkDefine link : allLinks) {
                if (link.getRowNum() > maxRow) {
                    maxRow = link.getRowNum();
                }
                if (link.getColNum() <= maxCol) continue;
                maxCol = link.getColNum();
            }
        }
        catch (Exception e) {
            maxRow = maxRow > 0 ? maxRow : 100;
            maxCol = maxCol > 0 ? maxCol : 100;
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        ReportInfo report = new ReportInfo(form.getKey(), form.getFormCode(), form.getTitle(), 1, maxRow, 1, maxCol);
        this.reportInfoMp.put(form.getKey(), report);
        return report;
    }

    private List<IEntityRow> getCurrentUnits(ExecutorContext context, List<Object> unitKeys) throws Exception {
        ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>();
        IEntityQuery entityQuery = this.dataAccessProvider.newEntityQuery();
        entityQuery.setFilterDataByAuthority(false);
        entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
        entityQuery.setAuthorityOperations(AuthorityType.NONE);
        entityQuery.setFilterDataByAuthority(false);
        entityQuery.setIgnoreViewFilter(true);
        IModifierEntityTable entityTable = entityQuery.executeQuery(context);
        for (Object unitKey : unitKeys) {
            rows.add(entityTable.findByEntityKey(unitKey.toString()));
        }
        return rows;
    }

    private EntityViewDefine getUnitView(String formSchemeKey) {
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        String entitiesKey = formScheme.getMasterEntitiesKey();
        String entityId = entitiesKey.split(";")[0];
        RunTimeEntityViewDefineImpl entityView = new RunTimeEntityViewDefineImpl();
        entityView.setEntityId(entityId);
        return entityView;
    }

    private QueryParam getQueryParam() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController);
        queryParam.setCacheManager(BeanUtil.getBean(NedisCacheManager.class));
        queryParam.setEntityResetCacheService(BeanUtil.getBean(EntityResetCacheService.class));
        return queryParam;
    }

    private Map<Object, List<Object>> findRelatedUnitKeyMap(List<Object> unitKeys) {
        HashMap<Object, List<Object>> result = new HashMap<Object, List<Object>>();
        for (Object object : unitKeys) {
            result.put(object, Collections.singletonList(object));
        }
        return result;
    }
}

