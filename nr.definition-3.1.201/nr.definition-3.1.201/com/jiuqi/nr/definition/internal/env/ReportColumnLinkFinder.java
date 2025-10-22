/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.EntityResetCacheService
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
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
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.list.UnmodifiableList;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.CollectionUtils;

public class ReportColumnLinkFinder
implements IDataModelLinkFinder {
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private IRunTimeViewController controller;
    private IEntityViewRunTimeController viewRunTimeController;
    private IDataDefinitionRuntimeController runtimeController;
    private IPeriodEntityAdapter periodEntityAdapter;
    private IEntityMetaService entityMetaService;
    private String formSchemeKey;
    private DataModelService dataModelService;
    private Map<String, Map<String, DataField>> reportLinkFieldMap = new HashMap<String, Map<String, DataField>>();
    private Map<String, ReportCache> reportInfoMp = new HashMap<String, ReportCache>();
    private Map<String, RegionCache> regionMp = new HashMap<String, RegionCache>();
    private Map<String, List<Object>> linkUnitMap = new HashMap<String, List<Object>>();
    private Map<String, LinkColumnCache> linkColumnCacheMap = new HashMap<String, LinkColumnCache>();
    private static final Logger logger = LogFactory.getLogger(ReportColumnLinkFinder.class);
    private IConnectionProvider connectionProvider;
    private IDataAccessProvider dataAccessProvider;
    private IEntityDataService entityDataService;
    private Map<String, String> regionConditionMap = new HashMap<String, String>();
    private static final String DATATIME = "DATATIME";
    private static final String BJKEY = "BJKEY";
    private static final String SEP = "@";
    private IDataAssist dataAssist;

    public ReportColumnLinkFinder(IRunTimeViewController controller, String formSchemeKey) {
        this.controller = controller;
        this.formSchemeKey = formSchemeKey;
        this.runtimeController = BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.connectionProvider = BeanUtil.getBean(IConnectionProvider.class);
        this.dataAccessProvider = BeanUtil.getBean(IDataAccessProvider.class);
        this.entityDataService = BeanUtil.getBean(IEntityDataService.class);
        this.periodEntityAdapter = BeanUtil.getBean(IPeriodEntityAdapter.class);
        this.entityMetaService = BeanUtil.getBean(IEntityMetaService.class);
        this.dataModelService = BeanUtil.getBean(DataModelService.class);
        this.runtimeDataSchemeService = BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.viewRunTimeController = BeanUtil.getBean(IEntityViewRunTimeController.class);
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        try {
            DataLinkDefine dataLink = isGridPosition ? this.controller.queryDataLinkDefineByXY(reportInfo.getReportKey(), colIndex, rowIndex) : this.controller.queryDataLinkDefineByColRow(reportInfo.getReportKey(), colIndex, rowIndex);
            return this.getDataModelLinkColumn(reportInfo, dataLink);
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u94fe\u63a5\u4fe1\u606f\u5931\u8d25\uff01", (Throwable)e);
            return null;
        }
    }

    private DataModelLinkColumn getEntityFieldLink(ReportInfo reportInfo, DataLinkDefine link) {
        FormDefine formDefine = this.controller.queryFormById(reportInfo.getReportKey());
        FormSchemeDefine formScheme = this.controller.getFormScheme(formDefine.getFormScheme());
        TableModelDefine tableModel = this.entityMetaService.getTableModel(formScheme.getDw());
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), link.getLinkExpression());
        if (columnModelDefine != null) {
            return this.getDataLinkColumn(reportInfo, link, columnModelDefine);
        }
        return null;
    }

    private DataModelLinkColumn getDataLinkColumn(ReportInfo reportInfo, DataLinkDefine dataLink, ColumnModelDefine columnModelDefine) {
        DataModelLinkColumn column = new DataModelLinkColumn(columnModelDefine);
        if (dataLink != null) {
            column.setDataLinkCode(dataLink.getUniqueCode());
            if (dataLink.getColNum() > 0 && dataLink.getRowNum() > 0) {
                column.setGridPosition(new Position(dataLink.getPosX(), dataLink.getPosY()));
                column.setDataPosition(new Position(dataLink.getColNum(), dataLink.getRowNum()));
            }
            RegionCache regionCache = this.regionMp.get(dataLink.getRegionKey());
            DataRegionDefine region = null;
            if (regionCache == null) {
                region = this.controller.queryDataRegionDefine(dataLink.getRegionKey());
                regionCache = new RegionCache(region);
                this.regionMp.put(dataLink.getRegionKey(), regionCache);
            } else {
                region = regionCache.getRegion();
            }
            if (region != null) {
                column.setRegion(region.getKey());
                if (region.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST || region.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST) {
                    if (regionCache.getExpendDims() != null) {
                        column.setExpandDims(regionCache.expendDims);
                    } else {
                        String fillLinkStr;
                        RegionSettingDefine regionSetting = this.controller.getRegionSetting(region.getKey());
                        ExecutorContext context = new ExecutorContext(this.runtimeController);
                        IDataAssist dataAssist = this.getDataAssist(context);
                        if (regionSetting != null && !StringUtils.isEmpty((String)(fillLinkStr = regionSetting.getDictionaryFillLinks()))) {
                            String[] fillLinks = fillLinkStr.split(";");
                            List<String> dims = Arrays.stream(fillLinks).map(fieldKey -> {
                                FieldDefine fieldDefine;
                                try {
                                    fieldDefine = this.runtimeController.queryFieldDefine(fieldKey);
                                }
                                catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                return fieldDefine == null ? null : dataAssist.getDimensionName(fieldDefine);
                            }).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
                            column.setExpandDims(dims);
                            regionCache.setExpendDims(dims);
                        }
                    }
                }
            }
        }
        this.fillLinkColumn(reportInfo, column);
        return column;
    }

    private void fillLinkColumn(ReportInfo reportInfo, DataModelLinkColumn column) {
        if (reportInfo != null) {
            column.setReportInfo(reportInfo);
            if (reportInfo.getPeriodModifier() != null) {
                column.setPeriodModifier(reportInfo.getPeriodModifier());
            }
            if (!StringUtils.isEmpty((String)reportInfo.getPeriodDim())) {
                DimensionValueSet dimension = new DimensionValueSet();
                dimension.setValue(DATATIME, (Object)reportInfo.getPeriodDim());
                column.setDimensionRestriction(dimension);
            }
        }
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, String dataLinkCode) {
        try {
            DataLinkDefine dataLink = this.controller.queryDataLinkDefineByUniquecode(reportInfo.getReportKey(), dataLinkCode);
            return this.getDataModelLinkColumn(reportInfo, dataLink);
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u94fe\u63a5\u4fe1\u606f\u5931\u8d25\uff01", (Throwable)e);
            return null;
        }
    }

    private DataModelLinkColumn getDataModelLinkColumn(ReportInfo reportInfo, DataLinkDefine dataLink) {
        if (dataLink == null || dataLink.getLinkExpression() == null || dataLink.getType() == DataLinkType.DATA_LINK_TYPE_FORMULA) {
            return null;
        }
        if (dataLink.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
            return this.getEntityFieldLink(reportInfo, dataLink);
        }
        ReportCache reportCache = this.reportInfoMp.get(reportInfo.getReportKey());
        if (reportCache == null) {
            reportCache = new ReportCache(reportInfo);
            FormDefine form = this.controller.queryFormById(reportInfo.getReportKey());
            FormSchemeDefine formScheme = this.controller.getFormScheme(form.getFormScheme());
            TaskDefine taskDefine = this.controller.queryTaskDefine(formScheme.getTaskKey());
            reportCache.setDataSchemeKey(taskDefine.getDataScheme());
            this.reportInfoMp.put(form.getKey(), reportCache);
        }
        ColumnModelDefine columnModelDefine = null;
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKey(reportCache.getDataSchemeKey(), dataLink.getLinkExpression());
        if (!CollectionUtils.isEmpty(deployInfos)) {
            columnModelDefine = this.dataModelService.getColumnModelDefineByID(((DataFieldDeployInfo)deployInfos.get(0)).getColumnModelKey());
        }
        if (columnModelDefine == null) {
            logger.warn("\u6839\u636e\u5355\u5143\u683c\u5173\u8054\u6307\u6807key:" + dataLink.getLinkExpression() + "\u6ca1\u6709\u627e\u5230\u6307\u6807\u5b9a\u4e49");
            return null;
        }
        return this.getDataLinkColumn(reportInfo, dataLink, columnModelDefine);
    }

    public DataModelLinkColumn findDataColumnByFieldName(ExecutorContext context, ReportInfo reportInfo, String fieldName) {
        try {
            LinkColumnCache linkColumnCache;
            TableModelDefine tableModel;
            if (context != null && StringUtils.isNotEmpty((String)context.getCurrentEntityId())) {
                tableModel = this.entityMetaService.getTableModel(context.getCurrentEntityId());
                if (tableModel == null) {
                    return null;
                }
                ColumnModelDefine modelDefineByCode = this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), fieldName);
                if (modelDefineByCode != null) {
                    return new DataModelLinkColumn(modelDefineByCode);
                }
            }
            if (StringUtils.isEmpty((String)this.formSchemeKey)) {
                if (context == null || StringUtils.isEmpty((String)context.getOrgEntityId())) {
                    return null;
                }
                tableModel = this.entityMetaService.getTableModel(context.getOrgEntityId());
                if (tableModel == null) {
                    return null;
                }
                return new DataModelLinkColumn(this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), fieldName));
            }
            String cacheKey = BJKEY;
            if (reportInfo != null) {
                cacheKey = StringUtils.isNotEmpty((String)reportInfo.getLinkAlias()) ? reportInfo.getReportKey() + SEP + reportInfo.getLinkAlias() : reportInfo.getReportKey();
            }
            if ((linkColumnCache = this.linkColumnCacheMap.get(cacheKey)) != null) {
                if (linkColumnCache.hasKey(fieldName)) {
                    DataModelLinkColumn linkColumn = linkColumnCache.findLinkColumn(fieldName);
                    return linkColumn;
                }
            } else {
                linkColumnCache = new LinkColumnCache();
                this.linkColumnCacheMap.put(cacheKey, linkColumnCache);
            }
            if (reportInfo == null) {
                FormSchemeDefine formScheme;
                TaskDefine taskDefine;
                FormDefine fmdmFormDefine = this.controller.queryFmdmFormDefineByFormScheme(this.formSchemeKey);
                DataModelLinkColumn linkColumn = this.getFMLinkColumn(reportInfo, fmdmFormDefine, (taskDefine = this.controller.queryTaskDefine((formScheme = this.controller.getFormScheme(this.formSchemeKey)).getTaskKey())).getDataScheme(), fieldName);
                if (linkColumn != null) {
                    return linkColumnCache.putLinkColumn(fieldName, linkColumn);
                }
                DataField mdInfoField = this.runtimeDataSchemeService.getDataFieldFromMdInfoByCode(taskDefine.getDataScheme(), fieldName);
                if (mdInfoField != null) {
                    DataModelLinkColumn dataModelLinkColumn = new DataModelLinkColumn(this.getColumnModel(mdInfoField));
                    return linkColumnCache.putLinkColumn(fieldName, dataModelLinkColumn);
                }
                ColumnModelDefine columnModelDefine = this.getMDField(fieldName);
                if (columnModelDefine != null) {
                    DataModelLinkColumn dataModelLinkColumn = new DataModelLinkColumn(columnModelDefine);
                    return linkColumnCache.putLinkColumn(fieldName, dataModelLinkColumn);
                }
                DataField dataField = this.runtimeDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(taskDefine.getDataScheme(), fieldName);
                if (null == dataField) {
                    return linkColumnCache.putLinkColumn(fieldName, null);
                }
                columnModelDefine = this.getColumnModel(dataField);
                if (columnModelDefine == null) {
                    return linkColumnCache.putLinkColumn(fieldName, null);
                }
                DataModelLinkColumn dataModelLinkColumn = new DataModelLinkColumn(columnModelDefine);
                return linkColumnCache.putLinkColumn(fieldName, dataModelLinkColumn);
            }
            FormDefine formDefine = this.controller.queryFormById(reportInfo.getReportKey());
            if (StringUtils.isNotEmpty((String)reportInfo.getLinkAlias()) && formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                DataModelLinkColumn linkColumn = this.findFMDMColumnByLinkAlias(context, fieldName, reportInfo.getLinkAlias());
                return linkColumnCache.putLinkColumn(fieldName, linkColumn);
            }
            FormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
            TaskDefine taskDefine = this.controller.queryTaskDefine(formScheme.getTaskKey());
            DataModelLinkColumn linkColumn = this.getFMLinkColumn(reportInfo, formDefine, taskDefine.getDataScheme(), fieldName);
            if (linkColumn != null) {
                return linkColumnCache.putLinkColumn(fieldName, linkColumn);
            }
            DataField dataField = this.getDataFieldInFormByCode(taskDefine.getDataScheme(), fieldName, formDefine.getFormScheme(), formDefine.getKey());
            if (null != dataField) {
                DataLinkDefine dataLink = this.controller.getDataLinkByFormAndField(formDefine.getFormScheme(), formDefine.getKey(), dataField.getKey());
                DataModelLinkColumn dataLinkColumn = this.getDataLinkColumn(reportInfo, dataLink, this.getColumnModel(dataField));
                return linkColumnCache.putLinkColumn(fieldName, dataLinkColumn);
            }
            dataField = this.runtimeDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(taskDefine.getDataScheme(), fieldName);
            if (dataField != null) {
                DataTable tableForMdInfo = this.runtimeDataSchemeService.getDataTableForMdInfo(taskDefine.getDataScheme());
                if (tableForMdInfo == null || !dataField.getDataTableKey().equals(tableForMdInfo.getKey())) {
                    DataLinkDefine dataLink = this.controller.getDataLinkByFormAndField(formDefine.getFormScheme(), formDefine.getKey(), dataField.getKey());
                    DataModelLinkColumn dataLinkColumn = this.getDataLinkColumn(null, dataLink, this.getColumnModel(dataField));
                    return linkColumnCache.putLinkColumn(fieldName, dataLinkColumn);
                }
                linkColumn = this.getFMLinkColumn(null, this.controller.queryFmdmFormDefineByFormScheme(this.formSchemeKey), taskDefine.getDataScheme(), fieldName);
                if (linkColumn != null) {
                    return linkColumnCache.putLinkColumn(fieldName, linkColumn);
                }
                DataModelLinkColumn dataLinkColumn = this.getDataLinkColumn(null, null, this.getColumnModel(dataField));
                return linkColumnCache.putLinkColumn(fieldName, dataLinkColumn);
            }
            linkColumn = this.getFMLinkColumn(null, this.controller.queryFmdmFormDefineByFormScheme(this.formSchemeKey), taskDefine.getDataScheme(), fieldName);
            if (linkColumn != null) {
                return linkColumnCache.putLinkColumn(fieldName, linkColumn);
            }
            ColumnModelDefine mdField = this.getMDField(fieldName);
            if (mdField != null) {
                DataModelLinkColumn dataLinkColumn = this.getDataLinkColumn(reportInfo, null, mdField);
                return linkColumnCache.putLinkColumn(fieldName, dataLinkColumn);
            }
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u94fe\u63a5\u4fe1\u606f\u5931\u8d25\uff01", (Throwable)e);
        }
        return null;
    }

    private DataField getDataFieldInFormByCode(String dataSchemeKey, String dataFieldCode, String formSchemeKey, String formKey) {
        return (DataField)this.reportLinkFieldMap.computeIfAbsent(formKey, k -> {
            HashMap<String, DataField> codeFields = new HashMap<String, DataField>();
            Set<String> linkExpressions = this.controller.listLinkExpressionByFormKey(formSchemeKey, formKey);
            List dataFields = this.runtimeDataSchemeService.getDataFields(dataSchemeKey, new ArrayList<String>(linkExpressions));
            for (DataField field : dataFields) {
                codeFields.put(field.getCode(), field);
            }
            return codeFields;
        }).get(dataFieldCode);
    }

    public DataModelLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        return this.findDataColumnByFieldName(null, reportInfo, fieldName);
    }

    private ColumnModelDefine getColumnModel(DataField dataField) {
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKey(dataField.getDataSchemeKey(), dataField.getKey());
        if (CollectionUtils.isEmpty(deployInfos)) {
            return null;
        }
        return this.dataModelService.getColumnModelDefineByID(((DataFieldDeployInfo)deployInfos.get(0)).getColumnModelKey());
    }

    public DataModelLinkColumn findFMDMColumnByLinkAlias(ExecutorContext context, String fieldName, String linkAlias) {
        TaskLinkDefine linkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias);
        ColumnModelDefine columnModelDefine = this.getMDField(context, linkDefine, fieldName);
        if (columnModelDefine != null) {
            List<DataLinkDefine> links;
            Optional<DataLinkDefine> linkDefineOptional;
            FormDefine fmdmFormDefine = this.controller.queryFmdmFormDefineByFormScheme(linkDefine.getRelatedFormSchemeKey());
            if (fmdmFormDefine != null && (linkDefineOptional = (links = this.controller.getAllLinksInForm(fmdmFormDefine.getKey())).stream().filter(l -> l.getType() == DataLinkType.DATA_LINK_TYPE_FMDM).filter(l -> l.getLinkExpression().equalsIgnoreCase(fieldName)).findFirst()).isPresent()) {
                ReportInfo fmdmReportInfo = this.getReportInfo(fmdmFormDefine);
                PeriodShiftInfo periodShiftInfo = this.getPeriodShiftInfo(linkAlias);
                if (periodShiftInfo != null) {
                    if (periodShiftInfo.periodDim != null) {
                        fmdmReportInfo.setPeriodDim(periodShiftInfo.periodDim);
                    }
                    if (periodShiftInfo.modifier != null) {
                        fmdmReportInfo.setPeriodModifier(periodShiftInfo.modifier);
                    }
                }
                return this.getDataLinkColumn(fmdmReportInfo, linkDefineOptional.get(), columnModelDefine);
            }
            PeriodShiftInfo periodShiftInfo = this.getPeriodShiftInfo(linkAlias);
            DataModelLinkColumn dataLinkColumn = new DataModelLinkColumn(columnModelDefine);
            if (periodShiftInfo != null) {
                if (periodShiftInfo.periodDim != null) {
                    DimensionValueSet dimension = new DimensionValueSet();
                    dimension.setValue(DATATIME, (Object)periodShiftInfo.periodDim);
                    dataLinkColumn.setDimensionRestriction(dimension);
                }
                if (periodShiftInfo.modifier != null) {
                    dataLinkColumn.setPeriodModifier(periodShiftInfo.modifier);
                }
            }
            return dataLinkColumn;
        }
        FormDefine fmdmFormDefine = this.controller.queryFmdmFormDefineByFormScheme(linkDefine.getRelatedFormSchemeKey());
        if (fmdmFormDefine == null) {
            return null;
        }
        TaskDefine taskDefine = null;
        taskDefine = StringUtils.isEmpty((String)linkDefine.getRelatedTaskKey()) ? this.controller.queryTaskDefine(this.controller.getFormScheme(linkDefine.getRelatedFormSchemeKey()).getTaskKey()) : this.controller.queryTaskDefine(linkDefine.getRelatedTaskKey());
        DataField dataField = this.runtimeDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(taskDefine.getDataScheme(), fieldName);
        if (null == dataField) {
            return null;
        }
        columnModelDefine = this.getColumnModel(dataField);
        if (columnModelDefine == null) {
            return null;
        }
        List<DataLinkDefine> links = this.controller.getAllLinksInForm(fmdmFormDefine.getKey());
        Optional<DataLinkDefine> linkDefineOptional = links.stream().filter(l -> l.getType() == DataLinkType.DATA_LINK_TYPE_FIELD || l.getType() == DataLinkType.DATA_LINK_TYPE_INFO).filter(l -> l.getLinkExpression().equals(dataField.getKey())).findFirst();
        if (linkDefineOptional.isPresent()) {
            ReportInfo fmdmReportInfo = this.getReportInfo(fmdmFormDefine);
            PeriodShiftInfo periodShiftInfo = this.getPeriodShiftInfo(linkAlias);
            if (periodShiftInfo != null) {
                if (periodShiftInfo.periodDim != null) {
                    fmdmReportInfo.setPeriodDim(periodShiftInfo.periodDim);
                }
                if (periodShiftInfo.modifier != null) {
                    fmdmReportInfo.setPeriodModifier(periodShiftInfo.modifier);
                }
            }
            return this.getDataLinkColumn(fmdmReportInfo, linkDefineOptional.get(), columnModelDefine);
        }
        return null;
    }

    public ReportInfo findReportInfo(String reportName) {
        return this.findReportInfoByFormScheme(reportName, this.formSchemeKey);
    }

    public ReportInfo findReportInfo(String linkAlias, String reportName) {
        try {
            String schemeKey = null;
            PeriodShiftInfo periodShiftInfo = this.getPeriodShiftInfo(linkAlias);
            TaskLinkDefine linkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias);
            if (linkDefine == null) {
                FormSchemeDefine schemeDefine = this.controller.getFormschemeByCode(linkAlias);
                if (schemeDefine != null) {
                    schemeKey = schemeDefine.getKey();
                }
            } else {
                schemeKey = linkDefine.getRelatedFormSchemeKey();
            }
            ReportInfo reportInfo = this.findReportInfoByFormScheme(reportName, schemeKey);
            if (reportInfo == null) {
                return null;
            }
            reportInfo = ReportColumnLinkFinder.copyReportInfo(reportInfo);
            reportInfo.setLinkAlias(linkAlias);
            if (periodShiftInfo.periodDim != null) {
                reportInfo.setPeriodDim(periodShiftInfo.periodDim);
            }
            if (periodShiftInfo.modifier != null) {
                reportInfo.setPeriodModifier(periodShiftInfo.modifier);
            }
            return reportInfo;
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u62a5\u8868\u5931\u8d25:" + e.getMessage());
            return null;
        }
    }

    private static ReportInfo copyReportInfo(ReportInfo reportInfo) {
        ReportInfo info = new ReportInfo(reportInfo.getReportKey(), reportInfo.getReportName(), reportInfo.getReportTitle(), reportInfo.getMinRow(), reportInfo.getMaxRow(), reportInfo.getMinCol(), reportInfo.getMaxCol());
        info.setReportSolution(reportInfo.getReportSolution());
        info.setValidCols(reportInfo.getValidCols());
        info.setValidRows(reportInfo.getValidRows());
        info.setPeriodModifier(reportInfo.getPeriodModifier());
        info.setPeriodDim(reportInfo.getPeriodDim());
        info.setLinkAlias(reportInfo.getLinkAlias());
        return info;
    }

    public List<ReportInfo> findAllRelatedReportInfo(String reportName) {
        ArrayList<ReportInfo> reportInfos = new ArrayList<ReportInfo>();
        List<TaskLinkDefine> links = this.controller.queryLinksByCurrentFormScheme(this.formSchemeKey);
        for (TaskLinkDefine linkDefine : links) {
            String schemeKey = linkDefine.getRelatedFormSchemeKey();
            PeriodModifier periodModifier = null;
            String periodDim = "";
            switch (linkDefine.getConfiguration()) {
                case PERIOD_TYPE_OFFSET: {
                    periodModifier = PeriodModifier.parse((String)linkDefine.getPeriodOffset());
                    break;
                }
                case PERIOD_TYPE_PREYEAR: {
                    periodModifier = PeriodModifier.parse((String)"-1N");
                    periodModifier.union(PeriodModifier.parse((String)"N"));
                    break;
                }
                case PERIOD_TYPE_SPECIFIED: {
                    periodDim = linkDefine.getSpecified();
                    break;
                }
            }
            ReportInfo reportInfo = this.findReportInfoByFormScheme(reportName, schemeKey);
            if (reportInfo == null) continue;
            reportInfo.setPeriodModifier(periodModifier);
            reportInfo.setPeriodDim(periodDim);
            this.findReportInfoByFormScheme(reportName, this.formSchemeKey);
            reportInfos.add(reportInfo);
        }
        return reportInfos;
    }

    public Map<Object, List<Object>> findRelatedUnitKeyMap(ExecutorContext context, String linkAlias, String dimensionName, List<Object> unitKeys) {
        HashMap<Object, List<Object>> result = new HashMap<Object, List<Object>>();
        if (unitKeys == null || unitKeys.isEmpty()) {
            return result;
        }
        ArrayList<Object> noCacheUnit = new ArrayList<Object>();
        unitKeys.forEach(u -> {
            if (this.linkUnitMap.containsKey(linkAlias + u.toString())) {
                result.put(u, new UnmodifiableList<Object>(this.linkUnitMap.get(linkAlias + u)));
            } else {
                noCacheUnit.add(u);
            }
        });
        if (noCacheUnit.isEmpty()) {
            return result;
        }
        noCacheUnit.forEach(u -> {
            List cfr_ignored_0 = this.linkUnitMap.put(linkAlias + u, new ArrayList());
        });
        try {
            this.excludeNotCurrentUnit(context, noCacheUnit, linkAlias, result);
            if (noCacheUnit.isEmpty()) {
                return result;
            }
            TaskLinkDefine taskLinkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias);
            EntityViewDefine relatedEntityView = this.getUnitView(context, taskLinkDefine);
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(relatedEntityView);
            entityQuery.setQueryVersionDate(this.getRelatedVersionDate(context, taskLinkDefine));
            entityQuery.setAuthorityOperations(AuthorityType.None);
            entityQuery.setIgnoreViewFilter(true);
            ExecutorContext queryContext = this.initQueryContext(context);
            String orgEntityId = context.getOrgEntityId();
            TaskLinkMatchingType matchingType = StringUtils.isEmpty((String)orgEntityId) ? taskLinkDefine.getMatchingType() : taskLinkDefine.getRelatedEntity(orgEntityId).getMatchingType();
            switch (matchingType) {
                case MATCHING_TYPE_PRIMARYKEY: {
                    this.findByKey(context, linkAlias, result, noCacheUnit, relatedEntityView, entityQuery, queryContext);
                    break;
                }
                case MATCHING_TYPE_TITLE: {
                    this.findByTitle(context, linkAlias, result, noCacheUnit, relatedEntityView, entityQuery, queryContext);
                    break;
                }
                case FORM_TYPE_EXPRESSION: {
                    this.findByExpression(context, linkAlias, result, noCacheUnit, taskLinkDefine, relatedEntityView, entityQuery, queryContext);
                    break;
                }
                default: {
                    this.findByCode(context, linkAlias, result, noCacheUnit, relatedEntityView, entityQuery);
                    break;
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return result;
    }

    private void findByCode(ExecutorContext context, String linkAlias, Map<Object, List<Object>> result, List<Object> noCacheUnit, EntityViewDefine relatedEntityView, IEntityQuery entityQuery) throws Exception {
        List<IEntityRow> currentUnits = this.getCurrentUnits(context, noCacheUnit);
        IEntityTable entityTable = entityQuery.executeReader((IContext)context);
        for (IEntityRow row : currentUnits) {
            IEntityRow targetEntityRow = entityTable.findByCode(row.getCode());
            ArrayList<String> datas = new ArrayList<String>();
            if (targetEntityRow != null) {
                datas.add(targetEntityRow.getEntityKeyData());
            }
            this.linkUnitMap.put(linkAlias + row.getEntityKeyData(), datas);
            result.put(row.getEntityKeyData(), new UnmodifiableList(datas));
        }
    }

    private void findByExpression(ExecutorContext context, String linkAlias, Map<Object, List<Object>> result, List<Object> noCacheUnit, TaskLinkDefine taskLinkDefine, EntityViewDefine relatedEntityView, IEntityQuery entityQuery, ExecutorContext queryContext) throws Exception {
        EntityViewDefine currentEntityView = this.getUnitView(context, this.formSchemeKey);
        ArrayList<String> expressions = new ArrayList<String>();
        expressions.add(taskLinkDefine.getRelatedEntity(currentEntityView.getEntityId()).getTargetFormula());
        ExpressionEvaluatorImpl evaluatorImpl = new ExpressionEvaluatorImpl(this.getQueryParam());
        Map currentEvalValue = evaluatorImpl.evalBatch(expressions, context, this.initDimensionValueSet(context, noCacheUnit, currentEntityView));
        expressions.clear();
        expressions.add(taskLinkDefine.getRelatedEntity(currentEntityView.getEntityId()).getSourceFormula());
        IEntityTable entityTable = entityQuery.executeFullBuild((IContext)queryContext);
        List allRows = entityTable.getAllRows();
        List<Object> relatedUnitKeys = allRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        DimensionValueSet dimensionValueSet = this.initDimensionValueSet(context, relatedUnitKeys, relatedEntityView);
        String periodStr = this.getRelatedDateStr(context, taskLinkDefine);
        if (periodStr != null) {
            dimensionValueSet.setValue(DATATIME, (Object)periodStr);
        }
        ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
        ReportFmlExecEnvironment fmlEnv = new ReportFmlExecEnvironment(this.controller, this.runtimeController, this.viewRunTimeController, taskLinkDefine.getRelatedFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)fmlEnv);
        executorContext.setOrgEntityId(taskLinkDefine.getRelatedEntity(currentEntityView.getEntityId()).getSourceEntity());
        Map relateEvalValue = evaluatorImpl.evalBatch(expressions, executorContext, dimensionValueSet);
        Set entrySet = relateEvalValue.entrySet();
        HashMap<String, List> relatedMap = new HashMap<String, List>();
        for (Map.Entry entry : entrySet) {
            String key = (String)((Object[])entry.getValue())[0];
            if (key == null) continue;
            List list = relatedMap.computeIfAbsent(key, k -> new ArrayList());
            list.add(entry.getKey());
        }
        Set currentEntrys = currentEvalValue.entrySet();
        block6: for (Map.Entry entry : currentEntrys) {
            String key = (String)((Object[])entry.getValue())[0];
            Set keySet = relatedMap.keySet();
            switch (taskLinkDefine.getExpressionType()) {
                case INCLUDE: {
                    List list;
                    for (String key1 : keySet) {
                        if (!key1.contains(key)) continue;
                        list = (ArrayList)relatedMap.get(key1);
                        if (list == null) {
                            list = new ArrayList();
                        }
                        this.linkUnitMap.get(linkAlias + (String)entry.getKey()).addAll(list);
                        result.put(entry.getKey(), new UnmodifiableList(list));
                    }
                    continue block6;
                }
                case END_WITH: {
                    List list;
                    for (String key1 : keySet) {
                        if (!key1.endsWith(key)) continue;
                        list = (List)relatedMap.get(key1);
                        if (list == null) {
                            list = new ArrayList();
                        }
                        this.linkUnitMap.get(linkAlias + (String)entry.getKey()).addAll(list);
                        result.put(entry.getKey(), new UnmodifiableList(list));
                    }
                    continue block6;
                }
                case BEGIN_WITH: {
                    List list;
                    for (String key1 : keySet) {
                        if (!key1.startsWith(key)) continue;
                        list = (List)relatedMap.get(key1);
                        if (list == null) {
                            list = new ArrayList();
                        }
                        this.linkUnitMap.get(linkAlias + (String)entry.getKey()).addAll(list);
                        result.put(entry.getKey(), new UnmodifiableList(list));
                    }
                    continue block6;
                }
                default: {
                    List list;
                    for (String key1 : keySet) {
                        if (!key1.equals(key)) continue;
                        list = (List)relatedMap.get(key1);
                        if (list == null) {
                            list = new ArrayList();
                        }
                        this.linkUnitMap.get(linkAlias + (String)entry.getKey()).addAll(list);
                        result.put(entry.getKey(), new UnmodifiableList(list));
                    }
                    continue block6;
                }
            }
        }
    }

    private void findByTitle(ExecutorContext context, String linkAlias, Map<Object, List<Object>> result, List<Object> noCacheUnit, EntityViewDefine relatedEntityView, IEntityQuery entityQuery, ExecutorContext queryContext) throws Exception {
        IEntityModel entityModel = this.entityMetaService.getEntityModel(relatedEntityView.getEntityId());
        EntityViewDefine currentEntityView = this.getUnitView(context, this.formSchemeKey);
        IEntityModel currentEntityModel = this.entityMetaService.getEntityModel(currentEntityView.getEntityId());
        ArrayList<String> expressions = new ArrayList<String>();
        expressions.add(currentEntityModel.getNameField().getCode());
        ExpressionEvaluatorImpl evaluatorImpl = new ExpressionEvaluatorImpl(this.getQueryParam());
        Map currentEvalValue = evaluatorImpl.evalBatch(expressions, context, this.initDimensionValueSet(context, noCacheUnit, currentEntityView));
        expressions.clear();
        expressions.add(entityModel.getNameField().getCode());
        IEntityTable entityTable = entityQuery.executeFullBuild((IContext)queryContext);
        List allRows = entityTable.getAllRows();
        Map<String, List<IEntityRow>> relatedUnitKeys = allRows.stream().collect(Collectors.groupingBy(IEntityItem::getTitle));
        Set currentEntrys = currentEvalValue.entrySet();
        for (Map.Entry entry : currentEntrys) {
            String key = (String)((Object[])entry.getValue())[0];
            List<IEntityRow> iEntityRows = relatedUnitKeys.get(key);
            List<Object> list = new ArrayList();
            if (iEntityRows != null) {
                list = iEntityRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            }
            this.linkUnitMap.get(linkAlias + (String)entry.getKey()).addAll(list);
            result.put(entry.getKey(), new UnmodifiableList(list));
        }
    }

    private void findByKey(ExecutorContext context, String linkAlias, Map<Object, List<Object>> result, List<Object> noCacheUnit, EntityViewDefine relatedEntityView, IEntityQuery entityQuery, ExecutorContext queryContext) throws Exception {
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue(this.dataAccessProvider.newDataAssist(context).getDimensionName(relatedEntityView), noCacheUnit);
        entityQuery.setMasterKeys(masterKeys);
        IEntityTable entityTable = entityQuery.executeReader((IContext)queryContext);
        List targetAllRows = entityTable.getAllRows();
        for (IEntityRow row : targetAllRows) {
            List<Object> datas = this.linkUnitMap.get(linkAlias + row.getEntityKeyData());
            datas.add(row.getEntityKeyData());
            result.put(row.getEntityKeyData(), new UnmodifiableList<Object>(datas));
        }
    }

    private void excludeNotCurrentUnit(ExecutorContext context, List<Object> noCacheUnit, String linkAlias, Map<Object, List<Object>> result) throws Exception {
        List<IEntityRow> currentUnits = this.getCurrentUnits(context, noCacheUnit);
        Set existUnit = currentUnits.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toSet());
        for (int i = noCacheUnit.size() - 1; i >= 0; --i) {
            String unitKey = (String)noCacheUnit.get(i);
            if (!existUnit.add(unitKey)) continue;
            ArrayList<String> datas = new ArrayList<String>();
            datas.add(unitKey);
            this.linkUnitMap.put(linkAlias + unitKey, datas);
            result.put(unitKey, new UnmodifiableList(datas));
            noCacheUnit.remove(i);
        }
    }

    @NotNull
    private ExecutorContext initQueryContext(ExecutorContext context) {
        ExecutorContext queryContext = new ExecutorContext(context.getRuntimeController());
        queryContext.setPeriodView(context.getPeriodView());
        List allVars = context.getAllVars();
        if (!CollectionUtils.isEmpty(allVars)) {
            VariableManager variableManager = queryContext.getVariableManager();
            for (Variable variable : allVars) {
                variableManager.add(variable);
            }
        }
        return queryContext;
    }

    private Date getRelatedVersionDate(ExecutorContext context, TaskLinkDefine linkDefine) {
        Date result = null;
        FormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
        IPeriodProvider periodAdapter = this.periodEntityAdapter.getPeriodProvider(formScheme.getDateTime());
        if (periodAdapter == null) {
            periodAdapter = context.getPeriodAdapter();
        }
        try {
            String periodStr = (String)context.getVarDimensionValueSet().getValue(DATATIME);
            PeriodType periodType = formScheme.getPeriodType();
            switch (linkDefine.getConfiguration()) {
                case PERIOD_TYPE_OFFSET: {
                    String periodModiferStr = linkDefine.getPeriodOffset();
                    periodStr = periodAdapter.modify(periodStr, PeriodModifier.parse((String)periodModiferStr));
                    break;
                }
                case PERIOD_TYPE_SPECIFIED: {
                    periodStr = linkDefine.getSpecified();
                    break;
                }
                case PERIOD_TYPE_NEXT: {
                    String periodModiferStr = "+1" + (char)PeriodConsts.typeToCode((int)periodType.type());
                    periodStr = periodAdapter.modify(periodStr, PeriodModifier.parse((String)periodModiferStr));
                    break;
                }
                case PERIOD_TYPE_PREVIOUS: {
                    String periodModiferStr = "-1" + (char)PeriodConsts.typeToCode((int)periodType.type());
                    periodStr = periodAdapter.modify(periodStr, PeriodModifier.parse((String)periodModiferStr));
                    break;
                }
                case PERIOD_TYPE_PREYEAR: {
                    FormSchemeDefine targetFormScheme = this.controller.getFormScheme(linkDefine.getRelatedFormSchemeKey());
                    PeriodModifier periodModifier = PeriodModifier.parse((String)"-1N");
                    periodModifier.union(PeriodModifier.parse((String)"N"));
                    periodStr = periodAdapter.modify(periodStr, periodModifier, (IPeriodAdapter)this.periodEntityAdapter.getPeriodProvider(targetFormScheme.getDateTime()));
                    break;
                }
            }
            Date[] periodDateRegion = periodAdapter.getPeriodDateRegion(new PeriodWrapper(periodStr));
            result = periodDateRegion[1];
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            result = Consts.DATE_VERSION_FOR_ALL;
        }
        return result;
    }

    private String getRelatedDateStr(ExecutorContext context, TaskLinkDefine linkDefine) {
        String result = null;
        FormSchemeDefine formScheme = this.controller.getFormScheme(linkDefine.getRelatedFormSchemeKey());
        IPeriodProvider periodAdapter = this.periodEntityAdapter.getPeriodProvider(formScheme.getDateTime());
        if (periodAdapter == null) {
            periodAdapter = context.getPeriodAdapter();
        }
        try {
            result = (String)context.getVarDimensionValueSet().getValue(DATATIME);
            PeriodType periodType = formScheme.getPeriodType();
            switch (linkDefine.getConfiguration()) {
                case PERIOD_TYPE_OFFSET: {
                    String periodModiferStr = linkDefine.getPeriodOffset();
                    result = periodAdapter.modify(result, PeriodModifier.parse((String)periodModiferStr));
                    break;
                }
                case PERIOD_TYPE_SPECIFIED: {
                    result = linkDefine.getSpecified();
                    break;
                }
                case PERIOD_TYPE_NEXT: {
                    String periodModiferStr = "+1" + (char)PeriodConsts.typeToCode((int)periodType.type());
                    result = periodAdapter.modify(result, PeriodModifier.parse((String)periodModiferStr));
                    break;
                }
                case PERIOD_TYPE_PREVIOUS: {
                    String periodModiferStr = "-1" + (char)PeriodConsts.typeToCode((int)periodType.type());
                    result = periodAdapter.modify(result, PeriodModifier.parse((String)periodModiferStr));
                    break;
                }
                case PERIOD_TYPE_PREYEAR: {
                    FormSchemeDefine targetFormScheme = this.controller.getFormScheme(linkDefine.getRelatedFormSchemeKey());
                    PeriodModifier periodModifier = PeriodModifier.parse((String)"-1N");
                    periodModifier.union(PeriodModifier.parse((String)"N"));
                    result = periodAdapter.modify(result, periodModifier, (IPeriodAdapter)this.periodEntityAdapter.getPeriodProvider(targetFormScheme.getDateTime()));
                    break;
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
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
        TaskLinkDefine linkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias);
        EntityViewDefine entityView = this.getUnitView(context, linkDefine);
        IDataAssist dataAssist = this.getDataAssist(context);
        return dataAssist.getDimensionName(entityView);
    }

    public Map<String, List<Object>> expandByDimensions(ExecutorContext context, DataModelLinkColumn dataModelLinkColumn) {
        String fillLinkStr;
        String region = dataModelLinkColumn.getRegion();
        RegionSettingDefine regionSetting = this.controller.getRegionSetting(region);
        IDataAssist dataAssist = this.getDataAssist(context);
        HashMap<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
        if (regionSetting != null && !StringUtils.isEmpty((String)(fillLinkStr = regionSetting.getDictionaryFillLinks()))) {
            String[] fillLinks = fillLinkStr.split(";");
            Arrays.stream(fillLinks).forEach(fieldKey -> {
                try {
                    FieldDefine fieldDefine = this.runtimeController.queryFieldDefine(fieldKey);
                    IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
                    try {
                        String periodStr = (String)context.getVarDimensionValueSet().getValue(DATATIME);
                        IPeriodAdapter periodAdapter = context.getPeriodAdapter();
                        Date[] periodDateRegion = periodAdapter.getPeriodDateRegion(new PeriodWrapper(periodStr));
                        entityQuery.setQueryVersionDate(periodDateRegion[1]);
                    }
                    catch (ParseException e1) {
                        entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
                    }
                    entityQuery.setAuthorityOperations(AuthorityType.None);
                    entityQuery.setIgnoreViewFilter(true);
                    RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
                    entityViewDefine.setEntityId(fieldDefine.getEntityKey());
                    entityQuery.setEntityView((EntityViewDefine)entityViewDefine);
                    ExecutorContext queryContext = new ExecutorContext(context.getRuntimeController());
                    IEntityTable entityTable = entityQuery.executeReader((IContext)queryContext);
                    List rows = entityTable.getAllRows();
                    List datas = null;
                    if (rows != null && rows.size() > 0) {
                        datas = rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                    }
                    resultMap.put(dataAssist.getDimensionName(fieldDefine), datas);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), (Throwable)e);
                }
            });
        }
        return resultMap;
    }

    public boolean is1V1Related(ExecutorContext context, String linkAlias) {
        return true;
    }

    public boolean hasRegionCondition(ExecutorContext context, String region) {
        RegionSettingDefine regionSetting;
        List<EntityDefaultValue> entityDefaultValueList;
        if (StringUtils.isEmpty((String)region)) {
            return false;
        }
        if (this.regionConditionMap.containsKey(region)) {
            return this.regionConditionMap.get(region) != null;
        }
        RegionCache regionCache = this.regionMp.get(region);
        DataRegionDefine regionDefine = null;
        if (regionCache == null) {
            regionDefine = this.controller.queryDataRegionDefine(region);
            regionCache = new RegionCache(regionDefine);
            this.regionMp.put(region, regionCache);
        } else {
            regionDefine = regionCache.getRegion();
        }
        if (regionDefine == null || regionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
            this.regionConditionMap.put(region, null);
            return false;
        }
        StringBuilder sb = new StringBuilder();
        String filterCondition = regionDefine.getFilterCondition();
        if (StringUtils.isNotEmpty((String)filterCondition)) {
            sb.append("(").append(filterCondition).append(")");
        }
        List<EntityDefaultValue> list = entityDefaultValueList = (regionSetting = this.controller.getRegionSetting(region)) == null ? null : regionSetting.getEntityDefaultValue();
        if (!CollectionUtils.isEmpty(entityDefaultValueList)) {
            Map entityDefaultValueMap = entityDefaultValueList.stream().collect(Collectors.toMap(EntityDefaultValue::getFieldKey, Function.identity()));
            List<DataLinkDefine> links = this.controller.getAllLinksInRegion(region);
            Optional<DataLinkDefine> linkDefineOpt = links.stream().filter(l -> l.getType() == DataLinkType.DATA_LINK_TYPE_FIELD).findFirst();
            if (linkDefineOpt.isPresent()) {
                DataLinkDefine linkDefine = linkDefineOpt.get();
                DataField dataField = this.runtimeDataSchemeService.getDataField(linkDefine.getLinkExpression());
                List bizFields = this.runtimeDataSchemeService.getBizDataFieldByTableKey(dataField.getDataTableKey());
                Map<String, SoftColumnModel> columnModelMap = bizFields.stream().collect(Collectors.toMap(Basic::getKey, b -> {
                    SoftColumnModel columnModel = new SoftColumnModel();
                    columnModel.fieldCode = b.getCode();
                    List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{b.getDataSchemeKey(), b.getKey()});
                    columnModel.fieldTableName = ((DataFieldDeployInfo)deployInfos.get(0)).getTableName();
                    return columnModel;
                }));
                StringBuilder bizSb = new StringBuilder();
                entityDefaultValueMap.entrySet().forEach(e -> {
                    SoftColumnModel columnModel = (SoftColumnModel)columnModelMap.get(e.getKey());
                    if (columnModel != null) {
                        if (bizSb.length() > 0) {
                            bizSb.append(" and ");
                        }
                        bizSb.append(" ").append(columnModel.fieldTableName).append("[").append(columnModel.fieldCode).append("]='").append(((EntityDefaultValue)e.getValue()).getItemValue()).append("' ");
                    }
                });
                if (bizSb.length() > 0) {
                    if (sb.length() > 0) {
                        sb.append(" and ");
                    }
                    sb.append("(");
                    sb.append((CharSequence)bizSb);
                    sb.append(")");
                }
            }
        }
        if (sb.length() > 0) {
            this.regionConditionMap.put(region, sb.toString());
            return true;
        }
        this.regionConditionMap.put(region, null);
        return false;
    }

    public String getRegionCondition(ExecutorContext context, String region) {
        if (this.hasRegionCondition(context, region)) {
            return this.regionConditionMap.get(region);
        }
        return null;
    }

    public List<String> getTableInnerKeys(ExecutorContext context, String tableName) {
        List deployInfoByTableName = this.runtimeDataSchemeService.getDeployInfoByTableName(tableName);
        Map fieldDeployInfoMap = deployInfoByTableName.stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, Function.identity(), (o, n) -> o));
        List dataFields = this.runtimeDataSchemeService.getDataFieldByTable(((DataFieldDeployInfo)deployInfoByTableName.get(0)).getDataTableKey());
        return dataFields.stream().sorted().filter(f -> f.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM).map(f -> ((DataFieldDeployInfo)fieldDeployInfoMap.get(f.getKey())).getColumnModelKey()).collect(Collectors.toList());
    }

    public ReportInfo findPeriodInfo(ExecutorContext context, String linkAlias) {
        try {
            PeriodShiftInfo periodShiftInfo = this.getPeriodShiftInfo(linkAlias);
            ReportInfo reportInfo = new ReportInfo(linkAlias, linkAlias, linkAlias, -1, -1);
            if (periodShiftInfo != null) {
                if (periodShiftInfo.periodDim != null) {
                    reportInfo.setPeriodDim(periodShiftInfo.periodDim);
                }
                if (periodShiftInfo.modifier != null) {
                    reportInfo.setPeriodModifier(periodShiftInfo.modifier);
                }
            }
            return reportInfo;
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u62a5\u8868\u5931\u8d25:" + e.getMessage());
            return null;
        }
    }

    public Map<String, String> getDimValuesByLinkAlias(ExecutorContext context, String linkAlias) {
        TaskLinkDefine linkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias);
        HashMap<String, String> dimMap = new HashMap<String, String>();
        Map<String, String> dimEntityMap = linkDefine.getDimMapping();
        Set<String> entityIds = dimEntityMap.keySet();
        entityIds.forEach(e -> {
            String dimensionName = this.entityMetaService.getDimensionName(e);
            dimMap.put(dimensionName, (String)dimEntityMap.get(e));
        });
        return dimMap;
    }

    public DataModelLinkColumn findDataColumnByTableFieldName(ExecutorContext context, String tableCode, String fieldName) {
        List dataFields = this.runtimeDataSchemeService.getDataFieldByTableCodeAndKind(tableCode, new DataFieldKind[0]);
        DataModelLinkColumn dataLinkColumn = null;
        if (CollectionUtils.isEmpty(dataFields)) {
            return dataLinkColumn;
        }
        Optional<DataField> fieldOptional = dataFields.parallelStream().filter(f -> f.getCode().equals(fieldName)).findFirst();
        if (fieldOptional.isPresent()) {
            DataField dataField = fieldOptional.get();
            String reportName = context.getDefaultGroupName();
            ColumnModelDefine columnModel = this.getColumnModel(dataField);
            if (columnModel == null) {
                return dataLinkColumn;
            }
            if (StringUtils.isNotEmpty((String)reportName)) {
                try {
                    FormDefine formDefine = this.controller.queryFormByCodeInScheme(this.formSchemeKey, reportName);
                    DataLinkDefine link = this.controller.getDataLinkByFormAndField(this.formSchemeKey, formDefine.getKey(), dataField.getKey());
                    dataLinkColumn = this.getDataLinkColumn(this.getReportInfo(formDefine), link, columnModel);
                }
                catch (Exception e) {
                    logger.error("\u516c\u5f0f\u89e3\u6790\u51fa\u9519\uff01", (Throwable)e);
                    dataLinkColumn = new DataModelLinkColumn(columnModel);
                }
            } else {
                dataLinkColumn = new DataModelLinkColumn(columnModel);
            }
        }
        return dataLinkColumn;
    }

    private ColumnModelDefine getMDField(String fieldCode) {
        return this.getMDField(this.formSchemeKey, fieldCode);
    }

    private ColumnModelDefine getMDField(String schemKey, String fieldCode) {
        FormSchemeDefine formScheme = this.controller.getFormScheme(schemKey);
        TableModelDefine tableModel = this.entityMetaService.getTableModel(formScheme.getDw());
        return this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), fieldCode);
    }

    private ColumnModelDefine getMDField(ExecutorContext context, TaskLinkDefine taskLinkDefine, String fieldCode) {
        String orgEntityId = context.getOrgEntityId();
        String sourceEntityID = "";
        if (StringUtils.isNotEmpty((String)orgEntityId)) {
            sourceEntityID = taskLinkDefine.getRelatedEntity(orgEntityId).getSourceEntity();
        } else {
            FormSchemeDefine formScheme = this.controller.getFormScheme(taskLinkDefine.getRelatedFormSchemeKey());
            sourceEntityID = formScheme.getDw();
        }
        TableModelDefine tableModel = this.entityMetaService.getTableModel(sourceEntityID);
        return this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), fieldCode);
    }

    private ReportInfo getReportInfo(FormDefine form) {
        if (this.reportInfoMp.containsKey(form.getKey())) {
            return this.reportInfoMp.get(form.getKey()).getReportInfo();
        }
        TreeSet<Integer> rowSet = new TreeSet<Integer>();
        TreeSet<Integer> colSet = new TreeSet<Integer>();
        try {
            List<DataLinkDefine> allLinks = this.controller.getAllLinksInForm(form.getKey());
            for (DataLinkDefine link : allLinks) {
                rowSet.add(link.getRowNum());
                colSet.add(link.getColNum());
            }
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u94fe\u63a5\u4fe1\u606f\u5931\u8d25\uff01", (Throwable)e);
        }
        int[] rowArr = rowSet.stream().mapToInt(Integer::intValue).toArray();
        int[] colArr = colSet.stream().mapToInt(Integer::intValue).toArray();
        ReportInfo report = new ReportInfo(form.getKey(), form.getFormCode(), form.getTitle(), colArr, rowArr);
        ReportCache reportCache = new ReportCache(report);
        FormSchemeDefine formScheme = this.controller.getFormScheme(form.getFormScheme());
        TaskDefine taskDefine = this.controller.queryTaskDefine(formScheme.getTaskKey());
        reportCache.setDataSchemeKey(taskDefine.getDataScheme());
        this.reportInfoMp.put(form.getKey(), reportCache);
        return report;
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
                TaskDefine taskDefine;
                FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
                if (formScheme != null && (taskDefine = this.controller.queryTaskDefine(formScheme.getTaskKey())) != null && taskDefine.getTaskType() == TaskType.TASK_TYPE_ANALYSIS && (indexOf = (taskCode = taskDefine.getTaskCode()).indexOf("_AL_")) > 0 && (rptTaskDefine = this.controller.queryTaskDefineByCode(rptTaskCode = taskCode.substring(0, indexOf))) != null) {
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
            logger.error("\u83b7\u53d6\u62a5\u8868\u4fe1\u606f\u5931\u8d25\uff01", (Throwable)e);
            return null;
        }
    }

    private QueryParam getQueryParam() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController);
        queryParam.setCacheManager(BeanUtil.getBean(NedisCacheManager.class));
        queryParam.setEntityResetCacheService(BeanUtil.getBean(EntityResetCacheService.class));
        return queryParam;
    }

    private List<IEntityRow> getCurrentUnits(ExecutorContext context, List<Object> unitKeys) throws Exception {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine unitView = this.getUnitView(context, this.formSchemeKey);
        entityQuery.setEntityView(unitView);
        try {
            String periodStr = (String)context.getVarDimensionValueSet().getValue(DATATIME);
            IPeriodAdapter periodAdapter = context.getPeriodAdapter();
            Date[] periodDateRegion = periodAdapter.getPeriodDateRegion(new PeriodWrapper(periodStr));
            entityQuery.setQueryVersionDate(periodDateRegion[1]);
        }
        catch (ParseException e1) {
            entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
        }
        entityQuery.setAuthorityOperations(AuthorityType.None);
        DimensionValueSet masterKeys = this.initDimensionValueSet(context, unitKeys, unitView);
        entityQuery.setMasterKeys(masterKeys);
        entityQuery.setIgnoreViewFilter(true);
        IEntityTable entityTable = entityQuery.executeReader((IContext)context);
        return entityTable.getAllRows();
    }

    @NotNull
    private DimensionValueSet initDimensionValueSet(ExecutorContext context, List<Object> unitKeys, EntityViewDefine unitView) {
        IDataAssist dataAssist = this.getDataAssist(context);
        DimensionValueSet masterKeys = new DimensionValueSet();
        String dimensionName = dataAssist.getDimensionName(unitView);
        masterKeys.setValue(dimensionName, unitKeys);
        return masterKeys;
    }

    private IDataAssist getDataAssist(ExecutorContext context) {
        if (this.dataAssist == null) {
            this.dataAssist = this.dataAccessProvider.newDataAssist(context);
        }
        return this.dataAssist;
    }

    private DataModelLinkColumn getFMLinkColumn(ReportInfo reportInfo, FormDefine formDefine, String dataschemeKey, String fieldName) {
        if (formDefine == null || formDefine.getFormType() != FormType.FORM_TYPE_NEWFMDM && formDefine.getFormType() != FormType.FORM_TYPE_FMDM) {
            return null;
        }
        ReportInfo fmReportInfo = reportInfo == null ? this.getReportInfo(formDefine) : reportInfo;
        DataField dataField = this.runtimeDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(dataschemeKey, fieldName);
        DataLinkDefine linkDefine = this.controller.getDataLinkByFormAndEntityAttr(formDefine.getFormScheme(), formDefine.getKey(), fieldName);
        if (linkDefine != null) {
            if (linkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                return this.getDataLinkColumn(fmReportInfo, linkDefine, this.getMDField(fieldName));
            }
            if (dataField == null) {
                return null;
            }
            return this.getDataLinkColumn(fmReportInfo, linkDefine, this.getColumnModel(dataField));
        }
        if (dataField != null && (linkDefine = this.controller.getDataLinkByFormAndField(formDefine.getFormScheme(), formDefine.getKey(), dataField.getKey())) != null) {
            return this.getDataLinkColumn(fmReportInfo, linkDefine, this.getColumnModel(dataField));
        }
        return null;
    }

    private EntityViewDefine getUnitView(ExecutorContext context, String formSchemeKey) throws JQException {
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
        if (StringUtils.isEmpty((String)context.getOrgEntityId())) {
            entityViewDefine.setEntityId(formScheme.getDw());
        } else {
            entityViewDefine.setEntityId(context.getOrgEntityId());
        }
        entityViewDefine.setRowFilterExpression(formScheme.getFilterExpression());
        return entityViewDefine;
    }

    private EntityViewDefine getUnitView(ExecutorContext context, TaskLinkDefine taskLinkDefine) {
        String orgEntityId;
        FormSchemeDefine formScheme = this.controller.getFormScheme(taskLinkDefine.getRelatedFormSchemeKey());
        if (StringUtils.isEmpty((String)context.getCurrentEntityId())) {
            if (StringUtils.isNotEmpty((String)context.getOrgEntityId())) {
                orgEntityId = context.getOrgEntityId();
            } else {
                FormSchemeDefine curFormscheme = this.controller.getFormScheme(this.formSchemeKey);
                orgEntityId = curFormscheme.getDw();
            }
        } else {
            orgEntityId = context.getCurrentEntityId();
        }
        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
        entityViewDefine.setEntityId(taskLinkDefine.getRelatedEntity(orgEntityId).getSourceEntity());
        entityViewDefine.setRowFilterExpression(formScheme.getFilterExpression());
        return entityViewDefine;
    }

    private PeriodShiftInfo getPeriodShiftInfo(String linkAlias) {
        PeriodShiftInfo info = new PeriodShiftInfo();
        try {
            String periodDim = "";
            TaskLinkDefine linkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias);
            FormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
            PeriodModifier periodModifier = null;
            PeriodType periodType = formScheme.getPeriodType();
            if (linkDefine != null) {
                switch (linkDefine.getConfiguration()) {
                    case PERIOD_TYPE_OFFSET: {
                        periodModifier = PeriodModifier.parse((String)linkDefine.getPeriodOffset());
                        break;
                    }
                    case PERIOD_TYPE_NEXT: {
                        periodModifier = PeriodModifier.parse((String)("+1" + (char)PeriodConsts.typeToCode((int)periodType.type())));
                        break;
                    }
                    case PERIOD_TYPE_PREVIOUS: {
                        periodModifier = PeriodModifier.parse((String)("-1" + (char)PeriodConsts.typeToCode((int)periodType.type())));
                        break;
                    }
                    case PERIOD_TYPE_PREYEAR: {
                        periodModifier = PeriodModifier.parse((String)"-1N");
                        periodModifier.union(PeriodModifier.parse((String)"N"));
                        break;
                    }
                    case PERIOD_TYPE_SPECIFIED: {
                        periodDim = linkDefine.getSpecified();
                        break;
                    }
                }
            }
            info.modifier = periodModifier;
            info.periodDim = periodDim;
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u62a5\u8868\u5931\u8d25:" + e.getMessage());
        }
        return info;
    }

    class ReportCache {
        private ReportInfo reportInfo;
        private String dataSchemeKey;

        public ReportCache(ReportInfo reportInfo) {
            this.reportInfo = reportInfo;
        }

        public ReportInfo getReportInfo() {
            return this.reportInfo;
        }

        public String getDataSchemeKey() {
            return this.dataSchemeKey;
        }

        public void setDataSchemeKey(String dataSchemeKey) {
            this.dataSchemeKey = dataSchemeKey;
        }
    }

    class RegionCache {
        private DataRegionDefine region;
        private List<String> expendDims;

        public RegionCache(DataRegionDefine region) {
            this.region = region;
        }

        public void setExpendDims(List<String> expendDims) {
            this.expendDims = new ArrayList<String>(expendDims);
        }

        public List<String> getExpendDims() {
            if (this.expendDims == null) {
                return null;
            }
            return new ArrayList<String>(this.expendDims);
        }

        public DataRegionDefine getRegion() {
            return this.region;
        }
    }

    private class LinkColumnCache {
        Map<String, DataModelLinkColumn> columnMap = new HashMap<String, DataModelLinkColumn>();

        public DataModelLinkColumn findLinkColumn(String fieldName) {
            DataModelLinkColumn dataModelLinkColumn = this.columnMap.get(fieldName);
            try {
                dataModelLinkColumn = dataModelLinkColumn == null ? null : (DataModelLinkColumn)dataModelLinkColumn.clone();
            }
            catch (CloneNotSupportedException e) {
                dataModelLinkColumn = null;
            }
            return dataModelLinkColumn;
        }

        public DataModelLinkColumn putLinkColumn(String fieldName, DataModelLinkColumn linkColumn) {
            DataModelLinkColumn nLinkColumn;
            try {
                nLinkColumn = linkColumn == null ? null : (DataModelLinkColumn)linkColumn.clone();
            }
            catch (CloneNotSupportedException e) {
                nLinkColumn = null;
            }
            this.columnMap.put(fieldName, nLinkColumn);
            return linkColumn;
        }

        public boolean hasKey(String fieldName) {
            return this.columnMap.containsKey(fieldName);
        }
    }

    class PeriodShiftInfo {
        PeriodModifier modifier;
        String periodDim;

        PeriodShiftInfo() {
        }
    }

    class SoftColumnModel {
        String fieldCode;
        String fieldTableName;

        SoftColumnModel() {
        }
    }
}

