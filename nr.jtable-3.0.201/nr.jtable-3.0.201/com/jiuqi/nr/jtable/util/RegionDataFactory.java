/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.datastatus.facade.obj.ClearStatusPar
 *  com.jiuqi.nr.datastatus.facade.service.IDataStatusService
 *  com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.datastatus.facade.obj.ClearStatusPar;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.jtable.dataset.AbstractAutoSumQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionGroupingDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.AccountFloatRegionDataCommitStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatAutoSumDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatAutoSumQueryTabeStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatGroupingQueryTabeStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionDataCommitStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionGroupingDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionQueryTabeStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionSingleDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionSingleQueryTabeStrategy;
import com.jiuqi.nr.jtable.dataset.impl.GroupingRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.SimpleAutoSumDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.impl.SimpleAutoSumQueryTabeStrategy;
import com.jiuqi.nr.jtable.dataset.impl.SimpleRegionDataCommitStrategy;
import com.jiuqi.nr.jtable.dataset.impl.SimpleRegionDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.impl.SimpleRegionQueryTabeStrategy;
import com.jiuqi.nr.jtable.dataset.impl.SimpleRegionRelationEvn;
import com.jiuqi.nr.jtable.exception.AutoSumSaveException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.FloatOrderStructure;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.input.RegionGradeInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionRestructureInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataCount;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.params.output.RegionSingleDataSet;
import com.jiuqi.nr.jtable.params.output.SaveResult;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionDataFactory {
    private IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
    private IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
    private static final Logger logger = LoggerFactory.getLogger(RegionDataFactory.class);
    private boolean jsonData = false;
    private boolean ignoreAnnoData = false;
    private static String autoSumType = "AUTOSUMTYPE";
    private static String gradeType = "GRADETYPE";
    private static String normalType = "NORMALTYPE";
    private IDataStatusService dataStatusService;
    private DimensionCollectionUtil dimensionCollectionUtil;

    private RegionQueryInfo initRegionQueryInfo(RegionData region, RegionQueryInfo regionQueryInfo) {
        if (regionQueryInfo == null) {
            regionQueryInfo = new RegionQueryInfo();
        }
        if (!region.getCells().isEmpty() && regionQueryInfo.getFilterInfo().getCellQuerys().isEmpty()) {
            regionQueryInfo.getFilterInfo().setCellQuerys(region.getCells());
        }
        JtableContext jtableContext = new JtableContext(regionQueryInfo.getContext());
        regionQueryInfo.setContext(jtableContext);
        List<String> filterFormulaList = regionQueryInfo.getFilterInfo().getFilterFormula();
        if ((filterFormulaList == null || filterFormulaList.size() == 0) && region.getTabs().size() > 0) {
            ArrayList<String> filterFormulas = new ArrayList<String>();
            filterFormulas.add(region.getTabs().get(0).getFilter());
            regionQueryInfo.getFilterInfo().setFilterFormula(filterFormulas);
        }
        if (regionQueryInfo.getRestructureInfo().getGrade() == null) {
            regionQueryInfo.getRestructureInfo().setGrade(region.getGrade());
        }
        if (regionQueryInfo.getPagerInfo() == null && region.getPageSize() > 0) {
            PagerInfo pagerInfo = new PagerInfo();
            pagerInfo.setOffset(0);
            pagerInfo.setLimit(region.getPageSize());
            regionQueryInfo.setPagerInfo(pagerInfo);
        }
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        FormData form = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
        IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        TaskDefine taskDefine = runtimeView.queryTaskDefine(formScheme.getTaskKey());
        if (jtableContext.getDimensionSet().containsKey("DATATIME") && !FormType.FORM_TYPE_NEWFMDM.name().equals(form.getFormType()) && !FormType.FORM_TYPE_ENTITY.name().equals(form.getFormType()) && 8 != formScheme.getPeriodType().type()) {
            boolean periodAutoSum = false;
            RegionGradeInfo grade = regionQueryInfo.getRestructureInfo().getGrade();
            DimensionValue dimensionValue = jtableContext.getDimensionSet().get("DATATIME");
            String[] values = dimensionValue.getValue().split(";");
            HashSet<Integer> periodTypeSet = new HashSet<Integer>();
            for (String value : values) {
                int periodType = new PeriodWrapper(value).getType();
                if (periodType == formScheme.getPeriodType().type()) continue;
                periodTypeSet.add(periodType);
            }
            if (!periodTypeSet.isEmpty()) {
                if (grade == null) {
                    grade = new RegionGradeInfo();
                    regionQueryInfo.getRestructureInfo().setGrade(grade);
                }
                periodTypeSet.add(formScheme.getPeriodType().type());
                grade.getPeriod().addAll(periodTypeSet);
                periodAutoSum = true;
            }
            regionQueryInfo.getRestructureInfo().setPeriodAutoSum(periodAutoSum);
        }
        return regionQueryInfo;
    }

    public AbstractRegionRelationEvn createRegionRelationEvn(RegionData region, JtableContext jtableContext) {
        AbstractRegionRelationEvn regionRelationEvn = null;
        regionRelationEvn = region.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() ? new SimpleRegionRelationEvn(region, jtableContext) : new FloatRegionRelationEvn(region, jtableContext);
        regionRelationEvn.setIgnoreAnnoData(this.ignoreAnnoData);
        return regionRelationEvn;
    }

    public RegionDataSet getRegionDataSet(RegionData region, RegionQueryInfo regionQueryInfo) {
        return this.getRegionDataSet(region, regionQueryInfo, false);
    }

    public RegionDataSet getRegionDataSet(RegionData region, RegionQueryInfo regionQueryInfo, boolean isVersionData) {
        AbstractRegionRelationEvn regionRelationEvn = this.createRegionRelationEvn(region, regionQueryInfo.getContext());
        if (regionRelationEvn.getDataLinkFieldMap().isEmpty() && regionRelationEvn.getDataLinkFormulaMap().isEmpty()) {
            logger.info("\u533a\u57df" + regionRelationEvn.getRegionData().getTitle() + "\u6ca1\u6709\u6570\u636e\u94fe\u63a5\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570");
            RegionDataSet regionDataSet = new RegionDataSet();
            ArrayList cells = new ArrayList();
            regionDataSet.getCells().put(regionRelationEvn.getRegionData().getKey().toString(), cells);
            return regionDataSet;
        }
        JtableContext jtableContext = new JtableContext(regionQueryInfo.getContext());
        DataFormaterCache dataFormaterCache = regionRelationEvn.getDataFormaterCache();
        if (this.isJsonData()) {
            dataFormaterCache.jsonData();
        }
        regionQueryInfo = this.initRegionQueryInfo(region, regionQueryInfo);
        GroupingRelationEvn groupingRelationEvn = new GroupingRelationEvn(region, regionQueryInfo);
        if (regionQueryInfo.getFilterInfo() != null) {
            regionRelationEvn.setPaginate(true);
        }
        RegionRestructureInfo restructureInfo = regionQueryInfo.getRestructureInfo();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext, restructureInfo.isUnitAutoSum());
        RegionSettingUtil.rebuildMasterKeyByRegion(region, dimensionValueSet, regionRelationEvn);
        String queryType = normalType;
        if (restructureInfo.isNoSumData()) {
            queryType = normalType;
        } else if (restructureInfo.isUnitAutoSum() || restructureInfo.isPeriodAutoSum()) {
            queryType = autoSumType;
        } else if (restructureInfo.getGrade() != null && (!restructureInfo.getGrade().getGradeCells().isEmpty() || restructureInfo.getGrade().isSum())) {
            queryType = gradeType;
        }
        if (autoSumType.equals(queryType)) {
            AbstractRegionGroupingDataSetStrategy dataSetStrategy;
            AbstractAutoSumQueryTableStrategy queryTabeStrategy;
            if (restructureInfo.getGrade() == null) {
                RegionGradeInfo grade = new RegionGradeInfo();
                restructureInfo.setGrade(grade);
            }
            IGroupingQuery groupingQuery = this.jtableDataEngineService.getGroupingQuery(jtableContext, region.getKey());
            if (restructureInfo.isPeriodAutoSum()) {
                FormData form = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
                IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
                FormSchemeDefine formScheme = runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
                PeriodType periodType = PeriodType.fromType((int)formScheme.getPeriodType().type());
                DimensionValue dimensionValue = jtableContext.getDimensionSet().get("DATATIME");
                String value = dimensionValue.getValue();
                groupingQuery.setQueryPeriod(value, value, periodType);
                dimensionValueSet.clearValue("DATATIME");
            }
            groupingQuery.setMasterKeys(dimensionValueSet);
            if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                queryTabeStrategy = new FloatAutoSumQueryTabeStrategy(groupingQuery, regionRelationEvn, groupingRelationEvn, regionQueryInfo);
                dataSetStrategy = new FloatAutoSumDataSetStrategy(regionRelationEvn, queryTabeStrategy, dataFormaterCache, regionQueryInfo);
                return ((FloatAutoSumDataSetStrategy)dataSetStrategy).getRegionDataSet();
            }
            queryTabeStrategy = new SimpleAutoSumQueryTabeStrategy(groupingQuery, regionRelationEvn, groupingRelationEvn, regionQueryInfo);
            dataSetStrategy = new SimpleAutoSumDataSetStrategy(regionRelationEvn, queryTabeStrategy, dataFormaterCache, regionQueryInfo);
            return ((SimpleAutoSumDataSetStrategy)dataSetStrategy).getRegionDataSet();
        }
        if (gradeType.equals(queryType)) {
            IGroupingQuery groupingQuery = this.jtableDataEngineService.getGroupingQuery(jtableContext, region.getKey());
            groupingQuery.setMasterKeys(dimensionValueSet);
            FloatGroupingQueryTabeStrategy queryTabeStrategy = new FloatGroupingQueryTabeStrategy(groupingQuery, regionRelationEvn, groupingRelationEvn, regionQueryInfo);
            FloatRegionGroupingDataSetStrategy dataSetStrategy = new FloatRegionGroupingDataSetStrategy(regionRelationEvn, queryTabeStrategy, dataFormaterCache, regionQueryInfo);
            return dataSetStrategy.getRegionDataSet();
        }
        IDataQuery dataQuery = this.jtableDataEngineService.getDataQuery(jtableContext, region.getKey());
        dataQuery.setMasterKeys(dimensionValueSet);
        AbstractRegionQueryTableStrategy queryTabeStrategy = null;
        if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            queryTabeStrategy = new FloatRegionQueryTabeStrategy(dataQuery, regionRelationEvn, regionQueryInfo);
            FloatRegionDataSetStrategy dataSetStrategy = new FloatRegionDataSetStrategy(regionRelationEvn, queryTabeStrategy, dataFormaterCache, regionQueryInfo);
            return dataSetStrategy.getRegionDataSet();
        }
        queryTabeStrategy = new SimpleRegionQueryTabeStrategy(dataQuery, regionRelationEvn, regionQueryInfo);
        SimpleRegionDataSetStrategy dataSetStrategy = new SimpleRegionDataSetStrategy(regionRelationEvn, queryTabeStrategy, dataFormaterCache, regionQueryInfo);
        return dataSetStrategy.getRegionDataSet(isVersionData);
    }

    public RegionSingleDataSet getRegionSingleDataSet(RegionData region, RegionQueryInfo regionQueryInfo) {
        AbstractRegionRelationEvn regionRelationEvn = this.createRegionRelationEvn(region, regionQueryInfo.getContext());
        RegionSingleDataSet regionSingleDataSet = new RegionSingleDataSet();
        ArrayList cells = new ArrayList();
        regionSingleDataSet.getCells().put(regionRelationEvn.getRegionData().getKey().toString(), cells);
        if (region.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            logger.info("\u533a\u57df" + region.getTitle() + "\u662f\u56fa\u5b9a\u533a\u57df");
            return regionSingleDataSet;
        }
        if (regionRelationEvn.getDataLinkFieldMap().isEmpty() && regionRelationEvn.getDataLinkFormulaMap().isEmpty()) {
            logger.info("\u533a\u57df" + regionRelationEvn.getRegionData().getTitle() + "\u6ca1\u6709\u6570\u636e\u94fe\u63a5\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570");
            return regionSingleDataSet;
        }
        RegionRestructureInfo restructureInfo = regionQueryInfo.getRestructureInfo();
        if (StringUtils.isEmpty((String)restructureInfo.getDataID()) && StringUtils.isEmpty((String)restructureInfo.getFloatOrder())) {
            logger.info("dataID\u4e0efolatOrder\u90fd\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u67e5\u8be2\u5355\u6761\u6570\u636e");
            return regionSingleDataSet;
        }
        JtableContext jtableContext = regionQueryInfo.getContext();
        DataFormaterCache dataFormaterCache = regionRelationEvn.getDataFormaterCache();
        if (this.isJsonData()) {
            dataFormaterCache.jsonData();
        }
        regionQueryInfo = this.initRegionQueryInfo(region, regionQueryInfo);
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext, restructureInfo.isUnitAutoSum());
        IDataQuery dataQuery = this.jtableDataEngineService.getDataQuery(jtableContext, region.getKey());
        RegionSettingUtil.rebuildMasterKeyByRegion(region, dimensionValueSet, regionRelationEvn);
        dataQuery.setMasterKeys(dimensionValueSet);
        FloatRegionSingleQueryTabeStrategy queryTabeStrategy = new FloatRegionSingleQueryTabeStrategy(dataQuery, regionRelationEvn, regionQueryInfo);
        FloatRegionSingleDataSetStrategy singledataSetStrategy = new FloatRegionSingleDataSetStrategy(regionRelationEvn, queryTabeStrategy, dataFormaterCache, regionQueryInfo);
        if (StringUtils.isNotEmpty((String)restructureInfo.getDataID())) {
            return singledataSetStrategy.getLocatDataByID(restructureInfo.getDataID());
        }
        if (StringUtils.isNotEmpty((String)restructureInfo.getFloatOrder())) {
            return singledataSetStrategy.getLocatDataByFloatOrder(restructureInfo.getFloatOrder(), restructureInfo.getOffset());
        }
        return regionSingleDataSet;
    }

    public PagerInfo floatDataLocate(RegionData region, RegionQueryInfo regionQueryInfo) {
        PagerInfo locateInfo = new PagerInfo();
        locateInfo.setOffset(-1);
        locateInfo.setTotal(0);
        if (region.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            logger.info("\u533a\u57df" + region.getTitle() + "\u662f\u56fa\u5b9a\u533a\u57df\uff0c\u4e0d\u7528\u5b9a\u4f4d");
            return locateInfo;
        }
        AbstractRegionRelationEvn regionRelationEvn = this.createRegionRelationEvn(region, regionQueryInfo.getContext());
        if (regionRelationEvn.getDataLinkFieldMap().isEmpty() && regionRelationEvn.getDataLinkFormulaMap().isEmpty()) {
            logger.info("\u533a\u57df" + regionRelationEvn.getRegionData().getTitle() + "\u6ca1\u6709\u6570\u636e\u94fe\u63a5\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570");
            return locateInfo;
        }
        RegionRestructureInfo restructureInfo = regionQueryInfo.getRestructureInfo();
        if (StringUtils.isEmpty((String)restructureInfo.getDataID())) {
            logger.info("\u5b9a\u4f4ddataID\u4e3a\u7a7a");
            return locateInfo;
        }
        JtableContext jtableContext = regionQueryInfo.getContext();
        DataFormaterCache dataFormaterCache = regionRelationEvn.getDataFormaterCache();
        if (this.isJsonData()) {
            dataFormaterCache.jsonData();
        }
        regionQueryInfo = this.initRegionQueryInfo(region, regionQueryInfo);
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext, restructureInfo.isUnitAutoSum());
        IDataQuery dataQuery = this.jtableDataEngineService.getDataQuery(jtableContext, region.getKey());
        RegionSettingUtil.rebuildMasterKeyByRegion(region, dimensionValueSet, regionRelationEvn);
        dataQuery.setMasterKeys(dimensionValueSet);
        FloatRegionQueryTabeStrategy queryTabeStrategy = new FloatRegionQueryTabeStrategy(dataQuery, regionRelationEvn, regionQueryInfo);
        FloatRegionDataSetStrategy dataSetStrategy = new FloatRegionDataSetStrategy(regionRelationEvn, queryTabeStrategy, dataFormaterCache, regionQueryInfo);
        return dataSetStrategy.getLocatDataById(restructureInfo.getDataID());
    }

    public void clearRegionDataSet(RegionData region, RegionQueryInfo regionQueryInfo) {
        if (region.isReadOnly()) {
            return;
        }
        AbstractRegionRelationEvn regionRelationEvn = this.createRegionRelationEvn(region, regionQueryInfo.getContext());
        regionRelationEvn.getDataLinkFormulaMap().clear();
        if (regionRelationEvn.getDataLinkFieldMap().isEmpty()) {
            logger.info("\u533a\u57df" + regionRelationEvn.getRegionData().getTitle() + "\u6ca1\u6709\u6570\u636e\u94fe\u63a5\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570");
            return;
        }
        JtableContext jtableContext = regionQueryInfo.getContext();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext.getDimensionSet());
        IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        EntityViewData unitViewData = this.jtableParamService.getEntity(formScheme.getDw());
        EntityViewData periodViewData = this.jtableParamService.getEntity(formScheme.getDateTime());
        String unitValue = jtableContext.getDimensionSet().get(unitViewData.getDimensionName()).getValue();
        String periodValue = jtableContext.getDimensionSet().get(periodViewData.getDimensionName()).getValue();
        Date queryVersionStartDate = Consts.DATE_VERSION_MIN_VALUE;
        Date queryVersionEndDate = Consts.DATE_VERSION_MAX_VALUE;
        if (StringUtils.isNotEmpty((String)periodValue)) {
            String[] periodList = PeriodUtil.getTimesArr((String)periodValue);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (periodList != null && periodList.length == 2) {
                try {
                    queryVersionStartDate = simpleDateFormat.parse(periodList[0]);
                    queryVersionEndDate = simpleDateFormat.parse(periodList[1]);
                }
                catch (ParseException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        boolean canWriteEntity = false;
        if (StringUtils.isNotEmpty((String)unitValue)) {
            String[] units = unitValue.split(";");
            IEntityAuthorityService entityAuthorityService = (IEntityAuthorityService)BeanUtil.getBean(IEntityAuthorityService.class);
            boolean systemIdentity = entityAuthorityService.isSystemIdentity();
            if (!systemIdentity) {
                for (String unit : units) {
                    try {
                        canWriteEntity = entityAuthorityService.isEnableAuthority(formScheme.getDw()) ? entityAuthorityService.canWriteEntity(formScheme.getDw(), unit, queryVersionStartDate, queryVersionEndDate) : true;
                    }
                    catch (UnauthorizedEntityException e) {
                        logger.error(e.getMessage(), e);
                    }
                    if (canWriteEntity) continue;
                    logger.info("\u7528\u6237\u5bf9\u5355\u4f4d\u6ca1\u6709\u5199\u6743\u9650\uff1a" + unit);
                }
            } else {
                canWriteEntity = true;
            }
        }
        if (!canWriteEntity) {
            return;
        }
        regionQueryInfo = this.initRegionQueryInfo(region, regionQueryInfo);
        IDataQuery dataQuery = this.jtableDataEngineService.getDataQuery(jtableContext, region.getKey());
        RegionSettingUtil.rebuildMasterKeyByRegion(region, dimensionValueSet, regionRelationEvn);
        dataQuery.setMasterKeys(dimensionValueSet);
        AbstractRegionQueryTableStrategy queryTabeStrategy = null;
        queryTabeStrategy = region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue() ? new FloatRegionQueryTabeStrategy(dataQuery, regionRelationEvn, regionQueryInfo) : new SimpleRegionQueryTabeStrategy(dataQuery, regionRelationEvn, regionQueryInfo);
        queryTabeStrategy.clearRegionTable();
        ClearStatusPar clearStatusPar = new ClearStatusPar();
        clearStatusPar.setFormSchemeKey(formScheme.getKey());
        clearStatusPar.setFormKey(region.getFormKey());
        clearStatusPar.setDimensionCollection(this.getDimensionCollectionUtil().getDimensionCollection(dimensionValueSet, formScheme.getKey()));
        try {
            this.getDataStatusService().clearDataStatusByForm(clearStatusPar);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private IDataStatusService getDataStatusService() {
        if (this.dataStatusService == null) {
            this.dataStatusService = (IDataStatusService)BeanUtil.getBean(IDataStatusService.class);
        }
        return this.dataStatusService;
    }

    private DimensionCollectionUtil getDimensionCollectionUtil() {
        if (this.dimensionCollectionUtil == null) {
            this.dimensionCollectionUtil = (DimensionCollectionUtil)BeanUtil.getBean(DimensionCollectionUtil.class);
        }
        return this.dimensionCollectionUtil;
    }

    public SaveResult commitRegionData(RegionData region, RegionDataCommitSet regionDataCommitSet) {
        AbstractRegionRelationEvn regionRelationEvn = this.createRegionRelationEvn(region, regionDataCommitSet.getContext());
        if (regionRelationEvn.getDataLinkFieldMap().isEmpty() && regionRelationEvn.getDataLinkFormulaMap().isEmpty()) {
            logger.info("\u533a\u57df" + regionRelationEvn.getRegionData().getTitle() + "\u6ca1\u6709\u6570\u636e\u94fe\u63a5\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570");
            return null;
        }
        JtableContext jtableContext = regionDataCommitSet.getContext();
        DataFormaterCache dataFormaterCache = regionRelationEvn.getDataFormaterCache();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        RegionSettingUtil.rebuildMasterKeyByRegion(region, dimensionValueSet, regionRelationEvn);
        IDataQuery dataQuery = this.jtableDataEngineService.getDataQuery(jtableContext, region.getKey());
        dataQuery.setMasterKeys(dimensionValueSet);
        RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
        regionQueryInfo.setContext(jtableContext);
        regionQueryInfo = this.initRegionQueryInfo(region, regionQueryInfo);
        if (regionQueryInfo.getRestructureInfo().isUnitAutoSum() || regionQueryInfo.getRestructureInfo().isPeriodAutoSum()) {
            throw new AutoSumSaveException(new String[]{"\u81ea\u52a8\u6c47\u603b\u6570\u636e\u4e0d\u80fd\u4fdd\u5b58"});
        }
        regionRelationEvn.setCommitData(true);
        if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            FloatRegionQueryTabeStrategy queryTabeStrategy = new FloatRegionQueryTabeStrategy(dataQuery, regionRelationEvn, regionQueryInfo);
            FormData formData = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
            this.setNewDataFloatOrder(region, regionDataCommitSet);
            if (FormType.FORM_TYPE_ACCOUNT.name().equals(formData.getFormType())) {
                AccountFloatRegionDataCommitStrategy dataCommitStrategy = new AccountFloatRegionDataCommitStrategy(regionRelationEvn, queryTabeStrategy, dataFormaterCache);
                return dataCommitStrategy.commitRegionData(regionDataCommitSet);
            }
            FloatRegionDataCommitStrategy dataCommitStrategy = new FloatRegionDataCommitStrategy(regionRelationEvn, queryTabeStrategy, dataFormaterCache);
            return dataCommitStrategy.commitRegionData(regionDataCommitSet);
        }
        SimpleRegionQueryTabeStrategy queryTabeStrategy = new SimpleRegionQueryTabeStrategy(dataQuery, regionRelationEvn, regionQueryInfo);
        SimpleRegionDataCommitStrategy dataCommitStrategy = new SimpleRegionDataCommitStrategy(regionRelationEvn, queryTabeStrategy, dataFormaterCache);
        return dataCommitStrategy.commitRegionData(regionDataCommitSet);
    }

    public RegionDataCount regionDataCount(RegionData region, RegionQueryInfo regionQueryInfo) {
        AbstractRegionRelationEvn regionRelationEvn = this.createRegionRelationEvn(region, regionQueryInfo.getContext());
        if (regionRelationEvn.getDataLinkFieldMap().isEmpty() && regionRelationEvn.getDataLinkFormulaMap().isEmpty()) {
            logger.info("\u533a\u57df" + regionRelationEvn.getRegionData().getTitle() + "\u6ca1\u6709\u6570\u636e\u94fe\u63a5\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570");
            RegionDataCount regionDataCout = new RegionDataCount();
            regionDataCout.setDataType(regionRelationEvn.getRegionData().getType());
            regionDataCout.setTotalCount(0);
            return regionDataCout;
        }
        JtableContext jtableContext = regionQueryInfo.getContext();
        DataFormaterCache dataFormaterCache = regionRelationEvn.getDataFormaterCache();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        regionQueryInfo = this.initRegionQueryInfo(region, regionQueryInfo);
        IDataQuery dataQuery = this.jtableDataEngineService.getDataQuery(jtableContext, region.getKey());
        dataQuery.setMasterKeys(dimensionValueSet);
        AbstractRegionQueryTableStrategy queryTabeStrategy = null;
        if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            queryTabeStrategy = new FloatRegionQueryTabeStrategy(dataQuery, regionRelationEvn, regionQueryInfo);
            FloatRegionDataSetStrategy dataSetStrategy = new FloatRegionDataSetStrategy(regionRelationEvn, queryTabeStrategy, dataFormaterCache, regionQueryInfo);
            return dataSetStrategy.getRegionDataCount();
        }
        queryTabeStrategy = new SimpleRegionQueryTabeStrategy(dataQuery, regionRelationEvn, regionQueryInfo);
        SimpleRegionDataSetStrategy dataSetStrategy = new SimpleRegionDataSetStrategy(regionRelationEvn, queryTabeStrategy, dataFormaterCache, regionQueryInfo);
        return dataSetStrategy.getRegionDataCount();
    }

    private void setNewDataFloatOrder(RegionData region, RegionDataCommitSet regionDataCommitSet) {
        FloatOrderStructure floatOrderStructure;
        List<List<List<Object>>> newdata = regionDataCommitSet.getNewdata();
        if (newdata != null && newdata.size() > 0 && (floatOrderStructure = regionDataCommitSet.getFloatOrderStructure()) != null) {
            String lastFloatOrder;
            List<String> afterInsertRowIds;
            String firstFloatOrder;
            List<String> beforeInsertRowIds = floatOrderStructure.getBeforeInsertRowIds();
            if (beforeInsertRowIds != null && beforeInsertRowIds.size() > 0 && StringUtils.isNotEmpty((String)(firstFloatOrder = floatOrderStructure.getFirstFloatOrder()))) {
                this.calcFloatOrder(region, regionDataCommitSet, -1);
            }
            if ((afterInsertRowIds = floatOrderStructure.getAfterInsertRowIds()) != null && afterInsertRowIds.size() > 0 && StringUtils.isNotEmpty((String)(lastFloatOrder = floatOrderStructure.getLastFloatOrder()))) {
                this.calcFloatOrder(region, regionDataCommitSet, 1);
            }
        }
    }

    private void calcFloatOrder(RegionData region, RegionDataCommitSet regionDataCommitSet, int offset) {
        String preOrNextFloatOrder;
        List<Object> preOrNextRowData;
        List<List<List<Object>>> newdata = regionDataCommitSet.getNewdata();
        FloatOrderStructure floatOrderStructure = regionDataCommitSet.getFloatOrderStructure();
        List<String> insertRowIds = floatOrderStructure.getAfterInsertRowIds();
        String markFloatOrder = floatOrderStructure.getLastFloatOrder();
        if (offset == -1) {
            insertRowIds = floatOrderStructure.getBeforeInsertRowIds();
            markFloatOrder = floatOrderStructure.getFirstFloatOrder();
        }
        RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
        RegionRestructureInfo restructureInfo = new RegionRestructureInfo();
        restructureInfo.setOffset(offset);
        restructureInfo.setFloatOrder(markFloatOrder);
        regionQueryInfo.setRestructureInfo(restructureInfo);
        regionQueryInfo.setContext(regionDataCommitSet.getContext());
        regionQueryInfo.setRegionKey(region.getKey());
        RegionSingleDataSet regionSingleDataSet = this.getRegionSingleDataSet(region, regionQueryInfo);
        List<List<Object>> rowData = regionSingleDataSet.getData();
        if (rowData != null && rowData.size() > 0 && (preOrNextRowData = rowData.get(0)) != null && preOrNextRowData.size() > 0 && preOrNextRowData.get(1) != null && StringUtils.isNotEmpty((String)(preOrNextFloatOrder = String.valueOf(preOrNextRowData.get(1))))) {
            String startIndex = markFloatOrder;
            String endIndex = preOrNextFloatOrder;
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(5);
            block0: for (String id : insertRowIds) {
                double a = Double.valueOf(startIndex);
                double b = Double.valueOf(endIndex);
                if (offset == -1) {
                    a = Double.valueOf(endIndex);
                    b = Double.valueOf(startIndex);
                }
                double c = (b - a) / (double)(insertRowIds.size() + 1);
                String cFormat = nf.format(c);
                double d = a + Double.valueOf(cFormat);
                for (List<List<Object>> row : newdata) {
                    String rowId;
                    List<Object> rowIdList = row.get(0);
                    if (rowIdList == null || rowIdList.size() <= 0 || rowIdList.get(0) == null || !(rowId = String.valueOf(rowIdList.get(0))).equals(id)) continue;
                    List<Object> floatorderList = row.get(1);
                    if (floatorderList == null || floatorderList.size() <= 0) continue block0;
                    floatorderList.set(0, String.valueOf(d));
                    floatorderList.set(1, String.valueOf(d));
                    if (offset == -1) {
                        endIndex = String.valueOf(d);
                        continue block0;
                    }
                    startIndex = String.valueOf(d);
                    continue block0;
                }
            }
        }
    }

    public boolean isJsonData() {
        return this.jsonData;
    }

    public void setJsonData(boolean jsonData) {
        this.jsonData = jsonData;
    }

    public boolean isIgnoreAnnoData() {
        return this.ignoreAnnoData;
    }

    public void setIgnoreAnnoData(boolean ignoreAnnoData) {
        this.ignoreAnnoData = ignoreAnnoData;
    }
}

