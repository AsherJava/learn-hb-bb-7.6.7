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
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.EntityResetCacheService
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataLinkFinder
 *  com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
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
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataLinkFinder;
import com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
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
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class ReportDataLinkFinder
implements IDataLinkFinder {
    private IRunTimeViewController controller;
    private IDataDefinitionRuntimeController runtimeController;
    private IPeriodEntityAdapter periodEntityAdapter;
    private IEntityMetaService entityMetaService;
    private String formSchemeKey;
    private Map<String, ReportInfo> reportInfoMp = new HashMap<String, ReportInfo>();
    private Map<String, DataRegionDefine> regionMp = new HashMap<String, DataRegionDefine>();
    private Map<String, List<Object>> linkUnitMap = new HashMap<String, List<Object>>();
    private final Logger logger = LogFactory.getLogger(ReportDataLinkFinder.class);
    private IConnectionProvider connectionProvider;
    private IDataAccessProvider dataAccessProvider;
    private IEntityDataService entityDataService;

    @Deprecated
    public ReportDataLinkFinder(IRunTimeViewController controller, IDataDefinitionRuntimeController runtimeController, IEntityViewRunTimeController entityViewRunTimeController, String formSchemeKey) {
        this(controller, formSchemeKey);
    }

    public ReportDataLinkFinder(IRunTimeViewController controller, String formSchemeKey) {
        this.controller = controller;
        this.formSchemeKey = formSchemeKey;
        this.runtimeController = BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.connectionProvider = BeanUtil.getBean(IConnectionProvider.class);
        this.dataAccessProvider = BeanUtil.getBean(IDataAccessProvider.class);
        this.entityDataService = BeanUtil.getBean(IEntityDataService.class);
        this.periodEntityAdapter = BeanUtil.getBean(IPeriodEntityAdapter.class);
        this.entityMetaService = BeanUtil.getBean(IEntityMetaService.class);
    }

    public DataLinkColumn findDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        try {
            DataLinkDefine dataLink = isGridPosition ? this.controller.queryDataLinkDefineByXY(reportInfo.getReportKey(), colIndex, rowIndex) : this.controller.queryDataLinkDefineByColRow(reportInfo.getReportKey(), colIndex, rowIndex);
            if (dataLink == null) {
                return null;
            }
            if (dataLink.getLinkExpression() == null) {
                this.logger.warn("\u5355\u5143\u683c\u672a\u5173\u8054\u6307\u6807");
                return null;
            }
            FormDefine formDefine = this.controller.queryFormById(reportInfo.getReportKey());
            FieldDefine fieldDefine = formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM ? this.getFmdmField(dataLink.getLinkExpression()) : this.runtimeController.queryFieldDefine(dataLink.getLinkExpression());
            if (fieldDefine == null) {
                this.logger.warn("\u6839\u636e\u5355\u5143\u683c\u5173\u8054\u6307\u6807key:" + dataLink.getLinkExpression() + "\u6ca1\u6709\u627e\u5230\u6307\u6807\u5b9a\u4e49");
                return null;
            }
            return this.getDataLinkColumn(reportInfo, dataLink, fieldDefine);
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u94fe\u63a5\u4fe1\u606f\u5931\u8d25\uff01", (Throwable)e);
            return null;
        }
    }

    public DataLinkColumn findDataColumn(ReportInfo reportInfo, String dataLinkCode) {
        try {
            DataLinkDefine dataLink = this.controller.queryDataLinkDefineByUniquecode(reportInfo.getReportKey(), dataLinkCode);
            if (dataLink == null) {
                return null;
            }
            if (dataLink.getLinkExpression() == null) {
                this.logger.warn("\u5355\u5143\u683c\u672a\u5173\u8054\u6307\u6807");
                return null;
            }
            FormDefine formDefine = this.controller.queryFormById(reportInfo.getReportKey());
            FieldDefine fieldDefine = formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM ? this.getFmdmField(dataLink.getLinkExpression()) : this.runtimeController.queryFieldDefine(dataLink.getLinkExpression());
            if (fieldDefine == null) {
                return null;
            }
            return this.getDataLinkColumn(reportInfo, dataLink, fieldDefine);
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u94fe\u63a5\u4fe1\u606f\u5931\u8d25\uff01", (Throwable)e);
            return null;
        }
    }

    public ReportInfo findReportInfo(String reportName) {
        return this.findReportInfoByFormScheme(reportName, this.formSchemeKey);
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

    private DataLinkColumn getDataLinkColumn(ReportInfo reportInfo, DataLinkDefine dataLink, FieldDefine field) {
        DataLinkColumn column = new DataLinkColumn(field);
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
                }
            }
        }
        column.setReportInfo(reportInfo);
        if (!StringUtils.isEmpty((String)reportInfo.getPeriodModifierStr())) {
            column.setPeriodModifier(PeriodModifier.parse((String)reportInfo.getPeriodModifierStr()));
        }
        if (!StringUtils.isEmpty((String)reportInfo.getPeriodDim())) {
            DimensionValueSet dimension = new DimensionValueSet();
            dimension.setValue("DATATIME", (Object)reportInfo.getPeriodDim());
            column.setDimensionRestriction(dimension);
        }
        return column;
    }

    private ReportInfo getReportInfo(FormDefine form) {
        if (this.reportInfoMp.containsKey(form.getKey())) {
            return this.reportInfoMp.get(form.getKey());
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
            this.logger.error("\u83b7\u53d6\u94fe\u63a5\u4fe1\u606f\u5931\u8d25\uff01", (Throwable)e);
        }
        int[] rowArr = rowSet.stream().mapToInt(Integer::intValue).toArray();
        int[] colArr = colSet.stream().mapToInt(Integer::intValue).toArray();
        ReportInfo report = new ReportInfo(form.getKey(), form.getFormCode(), form.getTitle(), colArr, rowArr);
        this.reportInfoMp.put(form.getKey(), report);
        return report;
    }

    public DataLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        try {
            FieldDefine fieldDefine;
            if (reportInfo == null) {
                FormDefine fmdmFormDefine = this.getFmdmFormDefine();
                if (fmdmFormDefine == null) {
                    return new DataLinkColumn(this.getFmdmField(fieldName));
                }
                FieldDefine fieldDefine2 = this.getFmdmField(fieldName);
                if (fieldDefine2 == null) {
                    return null;
                }
                DataLinkDefine dataLinkDefine = null;
                ReportInfo fmdmReportInfo = this.getReportInfo(fmdmFormDefine);
                List<DataLinkDefine> links = this.controller.getLinksInFormByField(fmdmReportInfo.getReportKey(), fieldDefine2.getCode());
                if (links != null && links.size() > 0) {
                    dataLinkDefine = links.get(0);
                }
                return this.getDataLinkColumn(fmdmReportInfo, dataLinkDefine, fieldDefine2);
            }
            FormDefine formDefine = this.controller.queryFormById(reportInfo.getReportKey());
            String linkExpression = "";
            if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                fieldDefine = this.getFmdmField(fieldName);
                if (fieldDefine == null) {
                    return null;
                }
                linkExpression = fieldDefine.getCode();
            } else {
                fieldDefine = this.runtimeController.queryFieldDefineByCodeInRange(this.controller.getFieldKeysInForm(reportInfo.getReportKey()), fieldName);
                if (fieldDefine == null) {
                    return null;
                }
                linkExpression = fieldDefine.getKey();
            }
            List<DataLinkDefine> links = this.controller.getLinksInFormByField(reportInfo.getReportKey(), linkExpression);
            DataLinkDefine dataLinkDefine = null;
            if (links != null && links.size() > 0) {
                dataLinkDefine = links.get(0);
            }
            return this.getDataLinkColumn(reportInfo, dataLinkDefine, fieldDefine);
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u94fe\u63a5\u4fe1\u606f\u5931\u8d25\uff01", (Throwable)e);
            return null;
        }
    }

    private FormDefine getFmdmFormDefine() {
        List<FormDefine> allForms = this.controller.queryAllFormDefinesByFormScheme(this.formSchemeKey);
        FormDefine form = null;
        Optional<FormDefine> first = allForms.stream().filter(form1 -> form1.getFormType() == FormType.FORM_TYPE_NEWFMDM).findFirst();
        if (first.isPresent()) {
            form = first.get();
        }
        return form;
    }

    private FieldDefine getFmdmField(String fieldName) throws Exception {
        FormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
        return null;
    }

    public ReportInfo findReportInfo(String linkAlias, String reportName) {
        try {
            String periodModiferStr = "";
            String periodDim = "";
            String schemeKey = null;
            TaskLinkDefine linkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias);
            FormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
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
        List<TaskLinkDefine> links = this.controller.queryLinksByCurrentFormScheme(this.formSchemeKey);
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
            ReportInfo reportInfo = this.findReportInfoByFormScheme(reportName, schemeKey);
            if (reportInfo == null) continue;
            reportInfo.setPeriodModifierStr(periodModiferStr);
            reportInfo.setPeriodDim(periodDim);
            this.findReportInfoByFormScheme(reportName, this.formSchemeKey);
            reportInfos.add(reportInfo);
        }
        return reportInfos;
    }

    private List<IEntityRow> getCurrentUnits(ExecutorContext context, List<Object> unitKeys) throws Exception {
        Set keys;
        Map findRows;
        ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>();
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.getUnitView(this.formSchemeKey));
        entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
        entityQuery.setAuthorityOperations(AuthorityType.None);
        entityQuery.setIgnoreViewFilter(true);
        IEntityTable entityTable = entityQuery.executeReader((IContext)context);
        if (!CollectionUtils.isEmpty(unitKeys) && !CollectionUtils.isEmpty(findRows = entityTable.findByEntityKeys(keys = unitKeys.stream().map(Object::toString).collect(Collectors.toSet())))) {
            rows.addAll(findRows.values());
        }
        return rows;
    }

    private EntityViewDefine getUnitView(String formSchemeKey) throws JQException {
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
        entityViewDefine.setEntityId(formScheme.getDw());
        entityViewDefine.setRowFilterExpression(formScheme.getFilterExpression());
        return entityViewDefine;
    }

    public Map<Object, List<Object>> findRelatedUnitKeyMap(ExecutorContext context, String linkAlias, String dimensionName, List<Object> unitKeys) {
        HashMap<Object, List<Object>> result = new HashMap<Object, List<Object>>();
        if (unitKeys == null || unitKeys.size() == 0) {
            return result;
        }
        TaskLinkDefine linkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias);
        FormSchemeDefine formScheme = this.controller.getFormScheme(linkDefine.getCurrentFormSchemeKey());
        PeriodType periodType = formScheme.getPeriodType();
        String periodStr = null;
        IPeriodProvider periodAdapter = this.periodEntityAdapter.getPeriodProvider(formScheme.getDateTime());
        if (periodAdapter == null) {
            periodAdapter = context.getPeriodAdapter();
        }
        try {
            periodStr = (String)context.getVarDimensionValueSet().getValue("DATATIME");
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
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        TaskLinkMatchingType matchingType = linkDefine.getMatchingType();
        String relatedFormSchemeKey = linkDefine.getRelatedFormSchemeKey();
        try {
            EntityViewDefine entityView = this.getUnitView(relatedFormSchemeKey);
            ArrayList<Object> noCacheUnit = new ArrayList<Object>();
            unitKeys.forEach(u -> {
                if (this.linkUnitMap.containsKey(linkAlias + u.toString())) {
                    result.put(u, this.linkUnitMap.get(linkAlias + u.toString()));
                } else {
                    noCacheUnit.add(u);
                }
            });
            List<IEntityRow> currentUnits = this.getCurrentUnits(context, noCacheUnit);
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityView);
            try {
                Date[] periodDateRegion = periodAdapter.getPeriodDateRegion(periodStr);
                entityQuery.setQueryVersionDate(periodDateRegion[1]);
            }
            catch (ParseException e1) {
                entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
            }
            entityQuery.setAuthorityOperations(AuthorityType.None);
            entityQuery.setIgnoreViewFilter(true);
            int i = 0;
            ExecutorContext queryContext = new ExecutorContext(context.getRuntimeController());
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityView.getEntityId());
            for (IEntityRow row : currentUnits) {
                block9 : switch (matchingType) {
                    case MATCHING_TYPE_PRIMARYKEY: {
                        entityQuery.setRowFilter(String.format("[%s] = '%s'", entityModel.getBizKeyField().getName(), row.getEntityKeyData()));
                        break;
                    }
                    case MATCHING_TYPE_TITLE: {
                        entityQuery.setRowFilter(String.format("[%s] = '%s'", entityModel.getNameField().getName(), row.getTitle()));
                        break;
                    }
                    case FORM_TYPE_EXPRESSION: {
                        ExpressionEvaluatorImpl evaluatorImpl = new ExpressionEvaluatorImpl(this.getQueryParam());
                        AbstractData calcData = evaluatorImpl.eval(linkDefine.getCurrentFormula(), context, row.getRowKeys());
                        switch (linkDefine.getExpressionType()) {
                            case INCLUDE: {
                                entityQuery.setRowFilter(linkDefine.getRelatedFormula() + " like '%" + (calcData == null ? "" : calcData.getAsString()) + "%'");
                                break block9;
                            }
                            case END_WITH: {
                                entityQuery.setRowFilter(linkDefine.getRelatedFormula() + " like '%" + (calcData == null ? "" : calcData.getAsString()) + "'");
                                break block9;
                            }
                            case BEGIN_WITH: {
                                entityQuery.setRowFilter(linkDefine.getRelatedFormula() + " like '" + (calcData == null ? "" : calcData.getAsString()) + "%'");
                                break block9;
                            }
                        }
                        entityQuery.setRowFilter(String.format(linkDefine.getRelatedFormula() + " = '%s'", calcData == null ? "" : calcData.getAsString()));
                        break;
                    }
                    default: {
                        entityQuery.setRowFilter(String.format("[%s] = '%s'", entityModel.getCodeField().getName(), row.getCode()));
                    }
                }
                IEntityTable entityTable = entityQuery.executeReader((IContext)queryContext);
                List rows = entityTable.getAllRows();
                List datas = null;
                if (rows != null && rows.size() > 0) {
                    datas = rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                }
                this.linkUnitMap.put(linkAlias + row.getEntityKeyData(), datas);
                result.put(row.getEntityKeyData(), datas);
                ++i;
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        return result;
    }

    private QueryParam getQueryParam() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController);
        queryParam.setCacheManager(BeanUtil.getBean(NedisCacheManager.class));
        queryParam.setEntityResetCacheService(BeanUtil.getBean(EntityResetCacheService.class));
        return queryParam;
    }

    public List<Object> findRelatedUnitKey(ExecutorContext context, String linkAlias, String dimensionName, Object unitKey) {
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(unitKey);
        Map<Object, List<Object>> unitKeyMap = this.findRelatedUnitKeyMap(context, linkAlias, dimensionName, list);
        return unitKeyMap.get(unitKey);
    }

    public String getRelatedUnitDimName(ExecutorContext context, String linkAlias, String dimensionName) {
        TaskLinkDefine linkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias);
        String relatedFormSchemeKey = linkDefine.getRelatedFormSchemeKey();
        EntityViewDefine entityView = null;
        try {
            entityView = this.getUnitView(relatedFormSchemeKey);
        }
        catch (JQException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
        return dataAssist.getDimensionName(entityView);
    }

    public Map<String, List<Object>> expandByDimensions(ExecutorContext context, DataLinkColumn dataLinkColumn) {
        String fillLinkStr;
        String region = dataLinkColumn.getRegion();
        RegionSettingDefine regionSetting = this.controller.getRegionSetting(region);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
        HashMap<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
        if (regionSetting != null && !StringUtils.isEmpty((String)(fillLinkStr = regionSetting.getDictionaryFillLinks()))) {
            String[] fillLinks = fillLinkStr.split(";");
            Arrays.stream(fillLinks).forEach(fieldKey -> {
                try {
                    FieldDefine fieldDefine = this.runtimeController.queryFieldDefine(fieldKey);
                    IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
                    entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
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
                    this.logger.error(e.getMessage(), (Throwable)e);
                }
            });
        }
        return resultMap;
    }

    public boolean is1V1Related(ExecutorContext context, String linkAlias) {
        return true;
    }
}

