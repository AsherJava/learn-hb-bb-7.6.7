/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.bpm.common.UploadRecordDetail
 *  com.jiuqi.nr.bpm.common.UploadStatusDetail
 *  com.jiuqi.nr.bpm.common.UploadSumNew
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.TreeRangeQuery
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.dataentry.internal.overview;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.common.UploadRecordDetail;
import com.jiuqi.nr.bpm.common.UploadStatusDetail;
import com.jiuqi.nr.bpm.common.UploadSumNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.dataentry.internal.overview.OverviewRootDataRow;
import com.jiuqi.nr.dataentry.internal.overview.OverviewUtil;
import com.jiuqi.nr.dataentry.paramInfo.BatchQueryUpload;
import com.jiuqi.nr.dataentry.paramInfo.ExportExcelState;
import com.jiuqi.nr.dataentry.paramInfo.UploadActionInfo;
import com.jiuqi.nr.dataentry.paramInfo.UploadSumInfo;
import com.jiuqi.nr.dataentry.web.BatchUploadStateController;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.TreeRangeQuery;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Primary
@Component(value="OverviewVirtualNodeServiceImpl")
public class OverviewVirtualNodeServiceImpl
extends BatchUploadStateController {
    private static final Logger logger = LoggerFactory.getLogger(OverviewVirtualNodeServiceImpl.class);
    private static final String DW_FIELD = "MDCODE";
    private static final String PERIOD_FIELD = "PERIOD";
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private OverviewUtil overviewUtil;
    private BiFunction<String, Map<String, User>, String> findUserNameById = (processor, userMap) -> {
        if (StringUtils.isEmpty((String)processor)) {
            return processor;
        }
        if (processor.equals("00000000-0000-0000-0000-000000000000")) {
            processor = "\u7cfb\u7edf\u7ba1\u7406\u5458";
        } else {
            User user = (User)userMap.get(processor);
            if (user != null) {
                processor = user.getNickname();
            }
        }
        return processor;
    };

    @Override
    public List<UploadSumInfo> batchUploadState(BatchQueryUpload batchQueryUpload) {
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(batchQueryUpload.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(batchQueryUpload.getDimensionSet());
        String dwId = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        if ("all-unit".equals(dwId)) {
            return this.queryVirtualNodeState(batchQueryUpload);
        }
        return super.batchUploadState(batchQueryUpload);
    }

    /*
     * WARNING - void declaration
     */
    private List<UploadSumInfo> queryVirtualNodeState(BatchQueryUpload batchQueryUpload) {
        if (batchQueryUpload == null) {
            return new ArrayList<UploadSumInfo>();
        }
        if (batchQueryUpload.getFormSchemeKey() == null || batchQueryUpload.getFormSchemeKey().isEmpty()) {
            return new ArrayList<UploadSumInfo>();
        }
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(batchQueryUpload.getFormSchemeKey());
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        ArrayList<UploadSumInfo> uploadSums = new ArrayList<UploadSumInfo>();
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(batchQueryUpload.getDimensionSet());
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        WorkFlowType queryStartType = this.dataentryFlowService.queryStartType(formScheme.getKey());
        JtableContext context = new JtableContext();
        context.setDimensionSet(batchQueryUpload.getDimensionSet());
        context.setFormKey(batchQueryUpload.getFormKey());
        context.setFormSchemeKey(batchQueryUpload.getFormSchemeKey());
        context.setTaskKey(batchQueryUpload.getTaskKey());
        List<IEntityRow> rootDatas = this.getRootData(batchQueryUpload.getFormSchemeKey(), batchQueryUpload.getDimensionSet());
        if (rootDatas == null || rootDatas.size() == 0) {
            return uploadSums;
        }
        List<String> rootKeys = rootDatas.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
        IEntityTable iEntityTable = this.getEntityDataList(batchQueryUpload.getFormSchemeKey(), batchQueryUpload.getDimensionSet(), rootKeys);
        ArrayList<UploadActionInfo> uploadActions = new ArrayList<UploadActionInfo>();
        Calendar abortTime = this.deSetTimeProvide.queryAllowDelayDeadlineTime(formScheme, period);
        this.getUploadActions(rootDatas.get(0), dimensionValueSet, batchQueryUpload, formScheme, uploadActions, dwEntity, iEntityTable);
        boolean confirmBrforeUpload = this.overviewUtil.confirmBrforeUpload(formScheme.getKey());
        this.getUploadSumNew(rootDatas, dimensionValueSet, batchQueryUpload.getFormKey(), formScheme, uploadSums, batchQueryUpload.getSummaryScope(), context, uploadActions, defaultWorkflow, queryStartType, iEntityTable, batchQueryUpload.isLeafEntity(), batchQueryUpload.isFilterDiffUnit(), batchQueryUpload.isOnlyDirectChild(), abortTime, true, confirmBrforeUpload);
        List<Object> entitypage = new ArrayList();
        String formKey = batchQueryUpload.getFormKey();
        String filter = batchQueryUpload.getFilter();
        List delayUploadRecord = Collections.emptyList();
        if (batchQueryUpload.getSummaryScope() > 1) {
            void var22_25;
            List<IEntityRow> rowList = this.getCountChildEntitys(iEntityTable, batchQueryUpload.getSummaryScope(), batchQueryUpload.isOnlyDirectChild(), rootDatas, true);
            ArrayList arrayList = new ArrayList();
            if (!StringUtils.isEmpty((String)formKey)) {
                List<IEntityRow> list = this.overViewBaseService.filterAuthByEntity(context, rowList, formKey, queryStartType);
            } else {
                List<IEntityRow> list = rowList;
            }
            if (StringUtils.isNotEmpty((String)batchQueryUpload.getFilter())) {
                List<IEntityRow> filterEntity = new ArrayList<IEntityRow>();
                if (filter.equals(UploadStateEnum.DELAY.getCode())) {
                    this.filterDelayUnitData((List<IEntityRow>)var22_25, filterEntity, formScheme, dwEntity.getDimensionName(), formKey, abortTime);
                    if (this.isDelay(abortTime)) {
                        batchQueryUpload.setFilter(UploadStateEnum.ORIGINAL_UPLOAD.getCode());
                        this.filterUnitData((List<IEntityRow>)var22_25, dimensionValueSet, dwEntity, formScheme, batchQueryUpload, queryStartType, defaultWorkflow, filterEntity);
                        filterEntity = this.mergeDelayUnit(filterEntity);
                        batchQueryUpload.setFilter(UploadStateEnum.DELAY.getCode());
                    }
                } else {
                    this.filterUnitData((List<IEntityRow>)var22_25, dimensionValueSet, dwEntity, formScheme, batchQueryUpload, queryStartType, defaultWorkflow, filterEntity);
                }
                entitypage = this.subList(filterEntity, batchQueryUpload.getPageIndex(), batchQueryUpload.getSize());
            } else {
                entitypage = this.subList((List<IEntityRow>)var22_25, batchQueryUpload.getPageIndex(), batchQueryUpload.getSize());
            }
        } else {
            List<IEntityRow> childEntity = rootDatas;
            ArrayList<IEntityRow> arrayList = new ArrayList<IEntityRow>(childEntity);
            List<Object> acessChildEntity = new ArrayList();
            acessChildEntity = !StringUtils.isEmpty((String)formKey) ? this.overViewBaseService.filterAuthByEntity(context, arrayList, formKey, queryStartType) : arrayList;
            if (StringUtils.isNotEmpty((String)batchQueryUpload.getFilter())) {
                List<IEntityRow> filterEntity = new ArrayList<IEntityRow>();
                if (filter.equals(UploadStateEnum.DELAY.getCode())) {
                    this.filterDelayUnitData(acessChildEntity, filterEntity, formScheme, dwEntity.getDimensionName(), formKey, abortTime);
                    if (this.isDelay(abortTime)) {
                        batchQueryUpload.setFilter(UploadStateEnum.ORIGINAL_UPLOAD.getCode());
                        this.filterUnitData(acessChildEntity, dimensionValueSet, dwEntity, formScheme, batchQueryUpload, queryStartType, defaultWorkflow, filterEntity);
                        filterEntity = this.mergeDelayUnit(filterEntity);
                        batchQueryUpload.setFilter(UploadStateEnum.DELAY.getCode());
                    }
                } else {
                    this.filterUnitData(acessChildEntity, dimensionValueSet, dwEntity, formScheme, batchQueryUpload, queryStartType, defaultWorkflow, filterEntity);
                }
                entitypage = this.subList(filterEntity, batchQueryUpload.getPageIndex(), batchQueryUpload.getSize());
            } else {
                entitypage = this.subList(acessChildEntity, batchQueryUpload.getPageIndex(), batchQueryUpload.getSize());
            }
        }
        for (IEntityRow iEntityRow : entitypage) {
            ArrayList<UploadActionInfo> uploadActionInfos = new ArrayList<UploadActionInfo>();
            this.getUploadActions(iEntityRow, dimensionValueSet, batchQueryUpload, formScheme, uploadActionInfos, dwEntity, iEntityTable);
            this.getUploadSum(iEntityRow, dimensionValueSet, batchQueryUpload.getFormKey(), formScheme, uploadSums, batchQueryUpload.getSummaryScope(), context, uploadActionInfos, defaultWorkflow, queryStartType, iEntityTable, batchQueryUpload.isLeafEntity(), batchQueryUpload.isFilterDiffUnit(), batchQueryUpload.isOnlyDirectChild(), abortTime, false, confirmBrforeUpload);
        }
        if (uploadSums.size() > 0) {
            ((UploadSumInfo)uploadSums.get(0)).setKeyTitle(this.overViewBaseService.getTitleMap(batchQueryUpload.getFormSchemeKey(), period, defaultWorkflow));
        }
        return uploadSums;
    }

    public List<IEntityRow> getRootData(String formSchemeKey, Map<String, DimensionValue> dimensionValueSet) {
        IEntityTable entityDataList = this.getEntityDataList(formSchemeKey, dimensionValueSet);
        return entityDataList.getRootRows();
    }

    public void getUploadSumNew(List<IEntityRow> rootDatas, DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme, List<UploadSumInfo> uploadSums, int summaryScope, JtableContext jtableContext, List<UploadActionInfo> uploadActions, boolean flowsType, WorkFlowType queryStartType, IEntityTable iEntityTable, boolean leafEntity, boolean filterDiffUnit, boolean onlyDirectChild, Calendar abortTime, boolean self, boolean confirmBrforeUpload) {
        int directChildCount = rootDatas.size();
        OverviewRootDataRow rootDataRow = OverviewRootDataRow.getRootDataRow();
        UploadSumInfo uploadSumInfo = new UploadSumInfo(rootDataRow.getKey(), rootDataRow.getCode(), rootDataRow.getTitle(), directChildCount);
        uploadSumInfo.setConfirmBeforeUpload(confirmBrforeUpload);
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        List<IEntityRow> childEntitys = this.getCountChildEntitys(iEntityTable, summaryScope, onlyDirectChild, rootDatas, false);
        List<String> entityIds = childEntitys.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
        DimensionValueSet dimension = new DimensionValueSet(dimensionValueSet);
        dimension.setValue(dwEntity.getDimensionName(), entityIds);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimension);
        jtableContext.setDimensionSet(dimensionSet);
        DimensionValueSet dimensionAuth = new DimensionValueSet(dimensionValueSet);
        List<String> filterAuth = entityIds;
        filterAuth = this.overViewBaseService.filterAuth(jtableContext, entityIds, formKey, dwEntity, queryStartType);
        dimensionAuth.setValue(dwEntity.getDimensionName(), filterAuth);
        if (WorkFlowType.ENTITY.equals((Object)queryStartType)) {
            formKey = null;
        }
        EntityViewDefine entityViewDefine = dwEntity.getEntityViewDefine();
        int rejectCount = 0;
        int updateCount = 0;
        UploadSumNew uploadSum = null;
        block2: for (IEntityRow rootData : rootDatas) {
            String entityKeyData = rootData.getEntityKeyData();
            try {
                uploadSum = this.queryUploadStateService.queryVirtualUploadSumNew(dimensionAuth, formKey, formScheme, flowsType, entityKeyData, dwEntity.getDimensionName(), entityViewDefine, iEntityTable, leafEntity, filterDiffUnit, onlyDirectChild, abortTime);
            }
            catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
            }
            if (uploadActions == null || uploadActions.size() <= 0) continue;
            for (UploadActionInfo uploadActionInfo : uploadActions) {
                if (!uploadActionInfo.getId().equals(entityKeyData)) continue;
                rejectCount += uploadActionInfo.getRejectedCount();
                updateCount += uploadActionInfo.getUploadedCount();
                continue block2;
            }
        }
        uploadSum.setRejectedCount(rejectCount);
        uploadSum.setUploadNums(updateCount);
        List<String> entityIdsSecond = entityIds;
        DimensionValueSet dimension1 = new DimensionValueSet(dimensionValueSet);
        dimension1.setValue(dwEntity.getDimensionName(), entityIdsSecond);
        Map dimensionSet1 = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimension1);
        JtableContext context = new JtableContext(jtableContext);
        context.setDimensionSet(dimensionSet1);
        filterAuth = entityIdsSecond;
        filterAuth = this.overViewBaseService.filterAuth(context, entityIdsSecond, formKey, dwEntity, queryStartType);
        uploadSumInfo.setSum(uploadSum);
        uploadSumInfo.setChildCount(filterAuth.size());
        uploadSums.add(uploadSumInfo);
    }

    @Override
    public IEntityTable getEntityDataList(String formSchemeKey, Map<String, DimensionValue> dimensionValueSet) {
        IEntityTable iEntityTable = null;
        try {
            FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeDefine.getKey());
            EntityViewDefine entityViewDefine = dwEntity.getEntityViewDefine();
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView(entityViewDefine);
            HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
            dimensionValueMap.put("DATATIME", dimensionValueSet.get("DATATIME"));
            iEntityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionValueMap));
            ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.runtimeController, this.iEntityViewRunTimeController, formSchemeKey);
            executorContext.setEnv((IFmlExecEnvironment)environment);
            executorContext.setPeriodView(formSchemeDefine.getDateTime());
            iEntityQuery.sorted(true);
            iEntityTable = this.dataEntityFullService.executeEntityFullBuild(iEntityQuery, executorContext, entityViewDefine, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return iEntityTable;
    }

    public IEntityTable getEntityDataList(String formSchemeKey, Map<String, DimensionValue> dimensionValueSet, List<String> roots) {
        IEntityTable iEntityTable = null;
        try {
            FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeDefine.getKey());
            EntityViewDefine entityViewDefine = this.runtimeView.getViewByFormSchemeKey(formSchemeDefine.getKey());
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView(entityViewDefine);
            HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
            dimensionValueMap.put("DATATIME", dimensionValueSet.get("DATATIME"));
            iEntityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionValueMap));
            ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.runtimeController, this.iEntityViewRunTimeController, formSchemeKey);
            executorContext.setEnv((IFmlExecEnvironment)environment);
            executorContext.setPeriodView(formSchemeDefine.getDateTime());
            TreeRangeQuery rangeQuery = new TreeRangeQuery();
            ArrayList<String> parentKey = new ArrayList<String>();
            parentKey.addAll(roots);
            rangeQuery.setParentKey(parentKey);
            iEntityQuery.sorted(true);
            iEntityTable = this.dataEntityFullService.executeEntityRangeBuild(iEntityQuery, executorContext, entityViewDefine, (RangeQuery)rangeQuery, formSchemeDefine.getKey()).getEntityTable();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return iEntityTable;
    }

    @Override
    public void exportUploadState(ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) throws IOException {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(exportExcelState.getFormSchemeKey());
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(exportExcelState.getDimensionSet());
        String dwId = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        if ("all-unit".equals(dwId)) {
            this.exportUploadData(exportExcelState, response, request, dwEntity);
        } else {
            super.exportUploadState(exportExcelState, response, request);
        }
    }

    public void exportUploadData(ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request, EntityViewData dwEntity) throws IOException {
        if (exportExcelState == null) {
            return;
        }
        if (exportExcelState.getFormSchemeKey() == null || exportExcelState.getFormSchemeKey().isEmpty()) {
            return;
        }
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(exportExcelState.getFormSchemeKey());
        boolean flowsType = this.workflow.isDefaultWorkflow(formScheme.getKey());
        ArrayList<UploadSumInfo> uploadSums = new ArrayList<UploadSumInfo>();
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(exportExcelState.getDimensionSet());
        String period = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        Calendar abortTime = this.deSetTimeProvide.queryAllowDelayDeadlineTime(formScheme, period);
        JtableContext context = new JtableContext();
        context.setDimensionSet(exportExcelState.getDimensionSet());
        context.setFormKey(exportExcelState.getFormKey());
        context.setFormSchemeKey(exportExcelState.getFormSchemeKey());
        context.setTaskKey(exportExcelState.getTaskKey());
        DimensionValueSet dimensionValueSet1 = new DimensionValueSet();
        dimensionValueSet1.setValue("DATATIME", (Object)exportExcelState.getDimensionSet().get("DATATIME").getValue());
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet1);
        List<IEntityRow> rootDatas = this.getRootData(formScheme.getKey(), dimensionSet);
        List<String> rootKeys = rootDatas.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
        IEntityTable entitySelf = this.getEntityDataList(formScheme.getKey(), (Map<String, DimensionValue>)dimensionSet, rootKeys);
        ArrayList<Object> entitypage = new ArrayList();
        List<Object> acessChildEntity = new ArrayList();
        String formKey = exportExcelState.getFormKey();
        IEntityTable iEntityTable = this.getEntityDataList(exportExcelState.getFormSchemeKey(), exportExcelState.getDimensionSet());
        List<IEntityRow> childEntitys = rootDatas;
        List<IEntityRow> rowList = this.getChildEntitys(iEntityTable, exportExcelState.getSummaryScope(), exportExcelState.isOnlyDirectChild(), rootDatas, false);
        childEntitys = rowList;
        if (childEntitys.size() == 0) {
            for (String key : rootKeys) {
                IEntityRow findEntity = entitySelf.findByEntityKey(key);
                childEntitys.add(findEntity);
            }
        }
        WorkFlowType queryStartType = this.dataentryFlowService.queryStartType(formScheme.getKey());
        acessChildEntity = !StringUtils.isEmpty((String)formKey) ? this.overViewBaseService.filterAuthByEntity(context, childEntitys, formKey, queryStartType) : childEntitys;
        String filter = exportExcelState.getFilter();
        if (StringUtils.isNotEmpty((String)filter)) {
            List<IEntityRow> filterEntity = new ArrayList<IEntityRow>();
            if (filter.equals(UploadStateEnum.DELAY.getCode())) {
                this.filterDelayUnitData(acessChildEntity, filterEntity, formScheme, dwEntity.getDimensionName(), formKey, abortTime);
                if (this.isDelay(abortTime)) {
                    exportExcelState.setFilter(UploadStateEnum.ORIGINAL_UPLOAD.getCode());
                    this.filterUnitExportData(acessChildEntity, dimensionValueSet, dwEntity, formScheme, exportExcelState, queryStartType, flowsType, filterEntity, filter);
                    filterEntity = this.mergeDelayUnit(filterEntity);
                    exportExcelState.setFilter(UploadStateEnum.DELAY.getCode());
                }
            } else {
                this.filterUnitExportData(acessChildEntity, dimensionValueSet, dwEntity, formScheme, exportExcelState, queryStartType, flowsType, filterEntity, filter);
            }
            entitypage = filterEntity;
        } else {
            entitypage = acessChildEntity;
        }
        boolean confirmBrforeUpload = this.overviewUtil.confirmBrforeUpload(formScheme.getKey());
        for (IEntityRow entityChildData : entitypage) {
            ArrayList<UploadActionInfo> uploadActions = new ArrayList<UploadActionInfo>();
            this.getUploadAction(entityChildData, dimensionValueSet, exportExcelState, formScheme, uploadActions, dwEntity, iEntityTable);
            this.getUploadSum(entityChildData, dimensionValueSet, formKey, formScheme, uploadSums, exportExcelState.getSummaryScope(), context, uploadActions, flowsType, queryStartType, iEntityTable, exportExcelState.isLeafEntity(), exportExcelState.isFilterDiffUnit(), exportExcelState.isOnlyDirectChild(), abortTime, false, confirmBrforeUpload);
        }
        this.exportExcel(exportExcelState, uploadSums, response);
    }

    @Override
    public void exportUploadState2(ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) throws Exception {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(exportExcelState.getFormSchemeKey());
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(exportExcelState.getDimensionSet());
        String dwId = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        if ("all-unit".equals(dwId)) {
            this.exportUploadDetail(exportExcelState, response, request);
        } else {
            super.exportUploadState2(exportExcelState, response, request);
        }
    }

    private void exportUploadDetail(ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) throws Exception {
        ArrayList<UploadStatusDetail> uploadDeatils = new ArrayList<UploadStatusDetail>();
        ArrayList<String> formGroupNames = new ArrayList<String>();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(exportExcelState.getFormSchemeKey());
        WorkFlowType wordFlowType = formScheme.getFlowsSetting().getWordFlowType();
        this.setDefaultValue(exportExcelState.getDimensionSet(), wordFlowType);
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(exportExcelState.getDimensionSet());
        if (wordFlowType.equals((Object)WorkFlowType.GROUP)) {
            String groupKey = exportExcelState.getFormKey();
            if (groupKey.equals("allForm")) {
                JtableContext jtableContext = new JtableContext();
                jtableContext.setFormSchemeKey(exportExcelState.getFormSchemeKey());
                jtableContext.setDimensionSet(exportExcelState.getDimensionSet());
                List<String> formGroupList = this.overViewBaseService.getAuthFormGroup(jtableContext);
                for (String key : formGroupList) {
                    exportExcelState.setFormKey(key);
                    UploadStatusDetail uploadDeatil = this.getVirtualUploadDeatils(exportExcelState);
                    uploadDeatils.add(uploadDeatil);
                    FormGroupDefine formGroupDefine = this.runtimeFormGroupService.queryFormGroup(key);
                    formGroupNames.add(formGroupDefine.getTitle());
                }
            } else {
                FormGroupDefine group = this.runtimeFormGroupService.queryFormGroup(groupKey);
                formGroupNames.add(group.getTitle());
                exportExcelState.setFormKey(group.getKey());
                UploadStatusDetail uploadDeatil = this.getVirtualUploadDeatils(exportExcelState);
                uploadDeatils.add(uploadDeatil);
            }
            this.exportDetail(exportExcelState, uploadDeatils, formGroupNames, response);
        } else if (wordFlowType.equals((Object)WorkFlowType.FORM)) {
            String formKey = exportExcelState.getFormKey();
            List<FormDefine> formDefines = null;
            if (formKey.equals("allForm")) {
                formDefines = this.runtimeView.queryAllFormDefinesByFormScheme(exportExcelState.getFormSchemeKey());
            } else {
                String[] forms;
                formDefines = new ArrayList();
                for (String fm : forms = formKey.split(";")) {
                    FormDefine form = this.runtimeView.queryFormById(fm);
                    formDefines.add(form);
                }
            }
            int index = 0;
            JtableContext context = new JtableContext();
            context.setDimensionSet(exportExcelState.getDimensionSet());
            context.setFormKey(exportExcelState.getFormKey());
            context.setFormSchemeKey(exportExcelState.getFormSchemeKey());
            context.setTaskKey(exportExcelState.getTaskKey());
            DimensionValueSet dimensionValueSet1 = new DimensionValueSet();
            dimensionValueSet1.setValue("DATATIME", (Object)exportExcelState.getDimensionSet().get("DATATIME").getValue());
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet1);
            List<IEntityRow> rootDatas = this.getRootData(formScheme.getKey(), dimensionSet);
            List<String> rootKeys = rootDatas.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
            IEntityTable iEntityTable = this.getEntityDataList(formScheme.getKey(), (Map<String, DimensionValue>)dimensionSet, rootKeys);
            ArrayList<String> entityIds = new ArrayList<String>();
            List<IEntityRow> childEntitys = this.getExportChildEntitys(iEntityTable, exportExcelState.getSummaryScope(), exportExcelState.isOnlyDirectChild(), rootDatas, false);
            for (IEntityRow childEntity : childEntitys) {
                entityIds.add(childEntity.getEntityKeyData());
            }
            DimensionValueSet dimension = new DimensionValueSet(dimensionValueSet);
            dimension.setValue(dwEntity.getDimensionName(), entityIds);
            LinkedHashMap formToSum = new LinkedHashMap();
            WorkFlowType queryStartType = this.dataentryFlowService.queryStartType(formScheme.getKey());
            JtableContext jtableContext = new JtableContext();
            HashMap<String, DimensionValue> dm = new HashMap<String, DimensionValue>();
            dm.put("DATATIME", exportExcelState.getDimensionSet().get("DATATIME"));
            DimensionValue value = new DimensionValue();
            value.setName(dwEntity.getDimensionName());
            String v = "";
            for (String i : entityIds) {
                v = v + i + ";";
            }
            value.setValue(v);
            dm.put(dwEntity.getDimensionName(), value);
            jtableContext.setDimensionSet(dm);
            jtableContext.setFormKey(exportExcelState.getFormKey());
            jtableContext.setFormSchemeKey(exportExcelState.getFormSchemeKey());
            jtableContext.setTaskKey(exportExcelState.getTaskKey());
            HashMap<String, String> filterForm = new HashMap<String, String>();
            for (String key : rootKeys) {
                Map<String, String> filterForm1 = this.overViewBaseService.getFilterForm(queryStartType, key, dwEntity.getDimensionName(), jtableContext);
                filterForm.putAll(filterForm1);
            }
            for (FormDefine formDefine : formDefines) {
                String formKeysInFilter = (String)filterForm.get(formDefine.getKey());
                if (!StringUtils.isNotEmpty((String)formKeysInFilter)) continue;
                exportExcelState.setFormKey(formDefine.getKey());
                UploadStatusDetail uploadDeatil = this.getVirtualUploadDeatils(exportExcelState);
                uploadDeatils.add(uploadDeatil);
                if (formDefine.getTitle().length() >= 20 || formGroupNames.indexOf(formDefine.getTitle()) != -1) {
                    formGroupNames.add("(" + index + ")" + formDefine.getTitle());
                } else {
                    formGroupNames.add(formDefine.getTitle());
                }
                ++index;
            }
            this.exportDetail(exportExcelState, uploadDeatils, formGroupNames, response);
        } else {
            UploadStatusDetail uploadDeatil = this.getVirtualUploadDeatils(exportExcelState);
            uploadDeatils.add(uploadDeatil);
            formGroupNames.add("\u4e3b\u4f53");
            this.exportDetail(exportExcelState, uploadDeatils, formGroupNames, response);
        }
    }

    /*
     * WARNING - void declaration
     */
    private UploadStatusDetail getVirtualUploadDeatils(ExportExcelState exportExcelState) throws Exception {
        TableModelDefine uploadStateTable = null;
        TableModelDefine uploadHistoryTable = null;
        List uploadHistoryFields = new ArrayList();
        List uploadStateFields = null;
        FormSchemeDefine formScheme = null;
        UploadStatusDetail uploadStatusDetail = new UploadStatusDetail();
        WorkFlowType wordFlowType = null;
        try {
            formScheme = this.runtimeView.getFormScheme(exportExcelState.getFormSchemeKey());
            wordFlowType = formScheme.getFlowsSetting().getWordFlowType();
            uploadStateTable = this.dataModelService.getTableModelDefineByName("SYS_UP_ST_" + formScheme.getFormSchemeCode());
            uploadHistoryTable = this.dataModelService.getTableModelDefineByName("SYS_UP_HI_" + formScheme.getFormSchemeCode());
            uploadStateFields = this.dataModelService.getColumnModelDefinesByTable(uploadStateTable.getID());
            if (uploadHistoryTable != null) {
                uploadHistoryFields = this.dataModelService.getColumnModelDefinesByTable(uploadHistoryTable.getID());
            }
        }
        catch (Exception e2) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e2.getMessage(), e2);
            return uploadStatusDetail;
        }
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(formScheme.getKey());
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.runtimeController);
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(formScheme.getKey());
        EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(exportExcelState.getDimensionSet());
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(executorContext);
        String period = dimensionValueSet.getValue(periodEntityInfo.getDimensionName()).toString();
        String entityName = targetEntityInfo.getDimensionName();
        ColumnModelDefine prevevent = null;
        int preveventIndex = -1;
        int entityFieldIndex = -1;
        ArrayList<String> filteredValues = new ArrayList<String>();
        String formKey = exportExcelState.getFormKey();
        if (formKey != null && wordFlowType != null && (wordFlowType.equals((Object)WorkFlowType.GROUP) || wordFlowType.equals((Object)WorkFlowType.FORM))) {
            filteredValues.add(formKey);
        }
        ColumnModelDefine entityField = null;
        String bizKeyFieldsStr = uploadStateTable.getBizKeys();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        JtableContext context = new JtableContext();
        context.setDimensionSet(exportExcelState.getDimensionSet());
        context.setFormKey(exportExcelState.getFormKey());
        context.setFormSchemeKey(exportExcelState.getFormSchemeKey());
        context.setTaskKey(exportExcelState.getTaskKey());
        int summaryScope = exportExcelState.getSummaryScope();
        Object units = new HashMap<String, String>();
        HashMap<String, String> unitsState = new HashMap<String, String>();
        List<String> entityIds = new ArrayList<String>();
        HashSet<String> fields = new HashSet<String>();
        fields.add("ALL");
        boolean flowsType = this.workflow.isDefaultWorkflow(formScheme.getKey());
        List<Object> allEntitys = new ArrayList();
        List<IEntityRow> rootDatas = this.getRootData(formScheme.getKey(), exportExcelState.getDimensionSet());
        allEntitys = rootDatas;
        List<String> rootKeys = rootDatas.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
        IEntityTable iEntityTable = this.getEntityDataList(formScheme.getKey(), exportExcelState.getDimensionSet(), rootKeys);
        ArrayList tempRows = new ArrayList();
        List<IEntityRow> childEntitys2 = this.getExportChildEntitys(iEntityTable, exportExcelState.getSummaryScope(), exportExcelState.isOnlyDirectChild(), rootDatas, false);
        allEntitys = childEntitys2;
        List childIds = allEntitys.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
        String formKeyStr = "";
        if (wordFlowType.equals((Object)WorkFlowType.GROUP)) {
            List allForms = this.runtimeView.getAllFormsInGroup(formKey);
            List formIds = allForms.stream().map(e -> e.getKey()).collect(Collectors.toList());
            formKeyStr = String.join((CharSequence)";", formIds);
        } else {
            formKeyStr = formKey;
        }
        JtableContext jcontext = new JtableContext();
        jcontext.setFormSchemeKey(exportExcelState.getFormSchemeKey());
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        Map<String, DimensionValue> map = exportExcelState.getDimensionSet();
        for (Map.Entry<String, DimensionValue> item : map.entrySet()) {
            dimensionSet.put(item.getKey(), item.getValue());
        }
        DimensionValue dv = new DimensionValue();
        dv.setName(entityName);
        dv.setValue(String.join((CharSequence)";", childIds));
        dimensionSet.put(entityName, dv);
        dimensionSet.put("DATATIME", exportExcelState.getDimensionSet().get("DATATIME"));
        jcontext.setDimensionSet(dimensionSet);
        jcontext.setTaskKey(exportExcelState.getTaskKey());
        List<String> FormKeyList = new ArrayList();
        if (formKeyStr == null) {
            FormKeyList = this.runtimeView.queryAllFormKeysByFormScheme(exportExcelState.getFormSchemeKey());
        } else {
            for (String key : formKeyStr.split(";")) {
                FormKeyList.add(key);
            }
        }
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(exportExcelState.getTaskKey(), exportExcelState.getFormSchemeKey());
        DimensionCollection dimCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection(dimensionSet, (String)exportExcelState.getFormSchemeKey());
        IBatchAccessResult batchAccessResult = dataAccessService.getVisitAccess(dimCollection, FormKeyList);
        HashMap dimensionValueFormInfoMap = new HashMap();
        List dimCollectionList = dimCollection.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimCollectionList) {
            String[] dimValuesAry;
            Map currDimensionSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionCombination.toDimensionValueSet());
            String dimValues = ((DimensionValue)currDimensionSet.get(entityName)).getValue();
            for (String entityKey : dimValuesAry = dimValues.split(";")) {
                ArrayList<String> accessForms = new ArrayList<String>();
                for (String key : FormKeyList) {
                    IAccessResult accessResult = batchAccessResult.getAccess(dimensionCombination, key);
                    if (!accessResult.haveAccess()) continue;
                    accessForms.add(key);
                }
                if (dimensionValueFormInfoMap.containsKey(entityKey)) {
                    ((List)dimensionValueFormInfoMap.get(entityKey)).addAll(accessForms);
                    continue;
                }
                dimensionValueFormInfoMap.put(entityKey, accessForms);
            }
        }
        for (IEntityRow iEntityRow : allEntitys) {
            List accessForms;
            if (wordFlowType.equals((Object)WorkFlowType.GROUP) && CollectionUtils.isEmpty(accessForms = (List)dimensionValueFormInfoMap.get(iEntityRow.getEntityKeyData()))) continue;
            entityIds.add(iEntityRow.getEntityKeyData());
            units.put(iEntityRow.getEntityKeyData(), iEntityRow.getTitle());
        }
        if (allEntitys == null || allEntitys.size() == 0) {
            for (IEntityRow iEntityRow : rootDatas) {
                entityIds.add(iEntityRow.getEntityKeyData());
                units.put(iEntityRow.getEntityKeyData(), iEntityRow.getTitle());
            }
        }
        if (wordFlowType.equals((Object)WorkFlowType.FORM)) {
            JtableContext jtableContext = new JtableContext();
            jtableContext.setFormSchemeKey(exportExcelState.getFormSchemeKey());
            jtableContext.setDimensionSet(exportExcelState.getDimensionSet());
            List<String> list = this.overViewBaseService.filterAuth(jtableContext, entityIds, formKey, targetEntityInfo, WorkFlowType.FORM);
            entityIds = list;
            HashMap units2 = new HashMap();
            for (String string : list) {
                units2.put(string, units.get(string));
            }
            units = units2;
        }
        dimensionValueSet.setValue(entityName, entityIds);
        int index = 0;
        boolean bl = false;
        for (ColumnModelDefine columnModelDefine : uploadStateFields) {
            void var52_64;
            if (bizKeyFieldsStr.contains(columnModelDefine.getID())) {
                OrderByItem orderByItem = new OrderByItem(columnModelDefine);
                orderByItem.setDesc(true);
                queryModel.getOrderByItems().add(index, orderByItem);
                ++index;
            }
            if (columnModelDefine.getCode().equals("PREVEVENT")) {
                prevevent = columnModelDefine;
                preveventIndex = var52_64;
            }
            if (columnModelDefine.getCode().equals("FORMID") && wordFlowType != null && (wordFlowType.equals((Object)WorkFlowType.GROUP) || wordFlowType.equals((Object)WorkFlowType.FORM))) {
                queryModel.getColumnFilters().put(columnModelDefine, filteredValues);
            }
            if (columnModelDefine.getCode().equals(DW_FIELD)) {
                String referTableID;
                if (columnModelDefine.getCode().equals(DW_FIELD)) {
                    entityField = columnModelDefine;
                    entityFieldIndex = var52_64;
                }
                if ((referTableID = columnModelDefine.getReferTableID()) == null) {
                    Object value = dimensionValueSet.getValue(columnModelDefine.getCode().equals(PERIOD_FIELD) ? "DATATIME" : columnModelDefine.getCode());
                    if (value != null) {
                        queryModel.getColumnFilters().put(columnModelDefine, value);
                    }
                } else {
                    TableModelDefine modelDefine = this.dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID());
                    IEntityDefine entityByCode = this.iEntityMetaService.queryEntityByCode(modelDefine.getCode());
                    Object value = dimensionValueSet.getValue(entityByCode.getDimensionName());
                    if (value != null) {
                        queryModel.getColumnFilters().put(columnModelDefine, value);
                    }
                }
            }
            if (columnModelDefine.getCode().equals(PERIOD_FIELD)) {
                queryModel.getColumnFilters().put(columnModelDefine, dimensionValueSet.getValue("DATATIME"));
            }
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            ++var52_64;
        }
        queryModel.setMainTableName(uploadStateTable.getName());
        INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet dataSet = dataAccess.executeQuery(dataAccessContext);
        if (entityField == null) {
            logger.error("\u4e0a\u62a5\u72b6\u6001\u8868\u4e3b\u4f53\u6307\u6807\u83b7\u53d6\u5931\u8d25\uff0c\u65e0\u6cd5\u5bfc\u51fa");
            return uploadStatusDetail;
        }
        int totalCount = dataSet.size();
        for (int i = 0; i < totalCount; ++i) {
            DataRow item = dataSet.get(i);
            String value = item.getString(preveventIndex);
            String unitKey = item.getString(entityFieldIndex);
            this.countStatus(uploadStatusDetail, value, unitKey, unitsState);
        }
        uploadStatusDetail.setUnitCount(entityIds.size());
        if (StringUtils.isNotEmpty((String)exportExcelState.getFilter())) {
            ArrayList<String> filterEntity = new ArrayList<String>();
            DimensionValueSet dim = new DimensionValueSet();
            WorkFlowType queryStartType = this.dataentryFlowService.queryStartType(formScheme.getKey());
            for (String entity : entityIds) {
                DataEntryParam param = new DataEntryParam();
                dim.setValue(entityName, (Object)entity);
                dim.setValue("DATATIME", (Object)period);
                if (!filteredValues.isEmpty() && !wordFlowType.equals((Object)WorkFlowType.ENTITY)) {
                    dim.setValue("FORMID", filteredValues.stream().findFirst().get());
                    param.setFormKey(filteredValues.stream().findFirst().get().toString());
                    ArrayList<String> formKeys = new ArrayList<String>();
                    formKeys.add(filteredValues.stream().findFirst().get().toString());
                    if (wordFlowType.equals((Object)WorkFlowType.GROUP)) {
                        param.setGroupKeys(formKeys);
                        if (!formKeys.isEmpty() && formKeys.size() == 1) {
                            param.setGroupKey((String)formKeys.get(0));
                        }
                    } else {
                        param.setFormKeys(formKeys);
                        if (!formKeys.isEmpty() && formKeys.size() == 1) {
                            param.setFormKey((String)formKeys.get(0));
                        }
                    }
                }
                param.setDim(dim);
                param.setFormSchemeKey(formScheme.getKey());
                ActionState actionStates = this.dataentryFlowService.queryState(param);
                ActionStateBean actionState = null;
                switch (queryStartType) {
                    case FORM: {
                        actionState = actionStates.getFormState();
                        break;
                    }
                    case GROUP: {
                        actionState = actionStates.getGroupState();
                        break;
                    }
                    default: {
                        actionState = actionStates.getUnitState();
                    }
                }
                String filter = exportExcelState.getFilter();
                if (exportExcelState.isForceUpoload()) {
                    if (!actionState.isForceUpload()) continue;
                    if (!flowsType && actionState != null && filter.equals(UploadStateEnum.ORIGINAL_UPLOAD.getCode()) && actionState.getCode().equals(UploadStateEnum.SUBMITED.getCode())) {
                        filterEntity.add(entity);
                    }
                    if ((actionState != null || !UploadStateEnum.ORIGINAL_SUBMIT.getCode().equals(filter) && !UploadStateEnum.ORIGINAL_UPLOAD.getCode().equals(filter)) && (actionState == null || !actionState.getCode().equals(filter))) continue;
                    filterEntity.add(entity);
                    continue;
                }
                if (!flowsType && actionState != null && filter.equals(UploadStateEnum.ORIGINAL_UPLOAD.getCode()) && actionState.getCode().equals(UploadStateEnum.SUBMITED.getCode())) {
                    filterEntity.add(entity);
                }
                if ((actionState != null || !UploadStateEnum.ORIGINAL_SUBMIT.getCode().equals(filter) && !UploadStateEnum.ORIGINAL_UPLOAD.getCode().equals(filter)) && (actionState == null || !actionState.getCode().equals(filter))) continue;
                filterEntity.add(entity);
            }
            entityIds = filterEntity;
            dimensionValueSet.setValue(entityName, entityIds);
        }
        String bizKeyHisFieldsStr = uploadHistoryTable.getBizKeys();
        NvwaQueryModel queryModelHis = new NvwaQueryModel();
        DataAccessContext dataAccessContextHis = new DataAccessContext(this.dataModelService);
        ColumnModelDefine time_1 = null;
        ColumnModelDefine curevent = null;
        ColumnModelDefine operator = null;
        entityField = null;
        int time_1Index = -1;
        int cureventIndex = -1;
        int operatorIndex = -1;
        int historyIndex = 0;
        for (ColumnModelDefine columnModelDefine : uploadHistoryFields) {
            if ("TIME_".equals(columnModelDefine.getCode())) {
                OrderByItem item = new OrderByItem(columnModelDefine, false);
                queryModelHis.getOrderByItems().add(item);
            }
            if (columnModelDefine.getCode().equals("TIME_")) {
                time_1 = columnModelDefine;
                time_1Index = historyIndex;
            }
            if (columnModelDefine.getCode().equals("CUREVENT")) {
                curevent = columnModelDefine;
                cureventIndex = historyIndex;
            }
            if (columnModelDefine.getCode().equals("OPERATOR")) {
                operator = columnModelDefine;
                operatorIndex = historyIndex;
            }
            if (columnModelDefine.getCode().equals("FORMID") && wordFlowType != null && (wordFlowType.equals((Object)WorkFlowType.GROUP) || wordFlowType.equals((Object)WorkFlowType.FORM))) {
                queryModelHis.getColumnFilters().put(columnModelDefine, filteredValues);
            }
            if (columnModelDefine.getCode().equals(DW_FIELD)) {
                String referTableID;
                if (columnModelDefine.getCode().equals(DW_FIELD)) {
                    entityField = columnModelDefine;
                    entityFieldIndex = historyIndex;
                }
                if ((referTableID = columnModelDefine.getReferTableID()) == null) {
                    Object value = dimensionValueSet.getValue(columnModelDefine.getCode().equals(PERIOD_FIELD) ? "DATATIME" : columnModelDefine.getCode());
                    if (value != null) {
                        queryModelHis.getColumnFilters().put(columnModelDefine, value);
                    }
                } else {
                    TableModelDefine modelDefine = this.dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID());
                    IEntityDefine entityByCode = this.iEntityMetaService.queryEntityByCode(modelDefine.getCode());
                    Object value = dimensionValueSet.getValue(entityByCode.getDimensionName());
                    if (value != null) {
                        queryModelHis.getColumnFilters().put(columnModelDefine, value);
                    }
                }
            }
            if (columnModelDefine.getCode().equals(PERIOD_FIELD)) {
                queryModelHis.getColumnFilters().put(columnModelDefine, dimensionValueSet.getValue("DATATIME"));
            }
            queryModelHis.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            ++historyIndex;
        }
        queryModelHis.setMainTableName(uploadHistoryTable.getName());
        if (entityField == null) {
            logger.error("\u4e0a\u62a5\u5386\u53f2\u8868\u4e3b\u4f53\u6307\u6807\u83b7\u53d6\u5931\u8d25\uff0c\u65e0\u6cd5\u5bfc\u51fa");
            return uploadStatusDetail;
        }
        queryModelHis.setMainTableName(uploadHistoryTable.getName());
        INvwaDataAccess dataAccessHis = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModelHis);
        MemoryDataSet dataSetHis = dataAccessHis.executeQuery(dataAccessContextHis);
        ArrayList<UploadRecordDetail> uploadRecordDetails = new ArrayList<UploadRecordDetail>();
        HashSet<String> userIds = new HashSet<String>();
        uploadStatusDetail.setUploadRecordDetail(uploadRecordDetails);
        UploadRecordDetail record = new UploadRecordDetail();
        int hisCount = dataSetHis.size();
        String currUnit = "";
        HashMap<String, UploadRecordDetail> recordUnit = new HashMap<String, UploadRecordDetail>();
        for (int indexHis = 0; indexHis < entityIds.size(); ++indexHis) {
            record = new UploadRecordDetail();
            recordUnit.put(entityIds.get(indexHis), record);
            uploadRecordDetails.add(record);
            this.setRecords((Map<String, String>)units, unitsState, record, "start", "", entityIds.get(indexHis), "");
        }
        for (int i = 0; i < hisCount; ++i) {
            DataRow item = dataSetHis.get(i);
            String value = item.getString(cureventIndex);
            String processor = item.getString(operatorIndex);
            String unitKey = item.getString(entityFieldIndex);
            Calendar date = item.getDate(time_1Index);
            SimpleDateFormat dfDT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = date != null ? dfDT.format(date.getTime()) : null;
            userIds.add(processor);
            if (unitKey.equals(currUnit)) {
                this.setRecords((Map<String, String>)units, unitsState, record, value, processor, unitKey, time);
                continue;
            }
            currUnit = unitKey;
            int indexOf = entityIds.indexOf(unitKey);
            if (indexOf == -1) continue;
            record = (UploadRecordDetail)uploadRecordDetails.get(indexOf);
            if (recordUnit.containsKey(unitKey)) {
                ((UploadRecordDetail)recordUnit.get(unitKey)).setUnitKey(null);
                record = (UploadRecordDetail)uploadRecordDetails.get(uploadRecordDetails.indexOf(recordUnit.get(unitKey)));
            }
            this.setRecords((Map<String, String>)units, unitsState, record, value, processor, unitKey, time);
        }
        if (!flowsType) {
            uploadStatusDetail.setOriginalNum(uploadStatusDetail.getSubmitedNum() + uploadStatusDetail.getReturnedNum());
            uploadStatusDetail.setSubmitedNum(0);
            uploadStatusDetail.setReturnedNum(0);
            List uploadRecordDetail = uploadStatusDetail.getUploadRecordDetail();
            for (UploadRecordDetail item : uploadRecordDetail) {
                if (!item.getState().equals("\u5df2\u9000\u5ba1") && !item.getState().equals("\u5df2\u9001\u5ba1") && !item.getState().equals("\u672a\u9001\u5ba1")) continue;
                item.setState("\u672a\u4e0a\u62a5");
            }
        }
        uploadStatusDetail.setOriginalNum(uploadStatusDetail.getUnitCount() - (uploadStatusDetail.getConfirmedNum() + uploadStatusDetail.getRejectedNum() + uploadStatusDetail.getReturnedNum() + uploadStatusDetail.getSubmitedNum() + uploadStatusDetail.getUploadedNum()));
        this.setRecordUser(userIds, uploadRecordDetails);
        return uploadStatusDetail;
    }

    private Map<String, User> getAllUser(Set<String> userIds) {
        userIds = userIds.stream().filter(e -> StringUtils.isNotEmpty((String)e)).collect(Collectors.toSet());
        List users = this.userService.get((String[])userIds.stream().toArray(String[]::new));
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity(), (o, n) -> n));
        return userMap;
    }

    private void setRecordUser(Set<String> userIds, List<UploadRecordDetail> uploadRecordDetails) {
        Map<String, User> userMap = this.getAllUser(userIds);
        uploadRecordDetails.stream().forEach(e -> {
            e.setInitialUpdateProcessor(this.findUserNameById.apply(e.getInitialUpdateProcessor(), userMap));
            e.setInitialRejectProcessor(this.findUserNameById.apply(e.getInitialRejectProcessor(), userMap));
            e.setLastUpdateProcessor(this.findUserNameById.apply(e.getLastUpdateProcessor(), userMap));
            e.setLastRejectProcessor(this.findUserNameById.apply(e.getLastRejectProcessor(), userMap));
        });
    }

    private void setRecords(Map<String, String> units, Map<String, String> unitsState, UploadRecordDetail record, String value, String processor, String unitKey, String time) {
        UploadState state;
        if (record.getUnitKey() == null) {
            record.setUnitKey(unitKey);
            record.setUnit(units.get(unitKey));
            record.setState(unitsState.get(unitKey) == null ? "\u672a\u4e0a\u62a5" : unitsState.get(unitKey));
        }
        if ((state = this.getState(value)).equals((Object)UploadState.UPLOADED)) {
            if (record.getInitialUpdateTime() == null) {
                record.setInitialUpdateTime(time);
            }
            if (record.getInitialUpdateProcessor() == null) {
                record.setInitialUpdateProcessor(processor);
            }
            record.setLastUpdateTime(time);
            record.setLastUpdateProcessor(processor);
            record.incrementUploadCount();
        }
        if (state.equals((Object)UploadState.REJECTED)) {
            if (record.getInitialRejectTime() == null) {
                record.setInitialRejectTime(time);
            }
            if (record.getInitialRejectProcessor() == null) {
                record.setInitialRejectProcessor(processor);
            }
            record.setLastRejectTime(time);
            record.setLastRejectProcessor(processor);
            record.incrementRejectCount();
        }
    }

    private void countStatus(UploadStatusDetail uploadStatusDetail, String state, String unitKey, Map<String, String> unitsState) {
        UploadState uploadState = this.getState(state);
        switch (uploadState) {
            case SUBMITED: {
                uploadStatusDetail.incrementSubmitedNum();
                unitsState.put(unitKey, "\u5df2\u9001\u5ba1");
                break;
            }
            case RETURNED: {
                uploadStatusDetail.incrementReturnedNum();
                unitsState.put(unitKey, "\u5df2\u9000\u5ba1");
                break;
            }
            case UPLOADED: {
                uploadStatusDetail.incrementUploadedNum();
                unitsState.put(unitKey, "\u5df2\u4e0a\u62a5");
                break;
            }
            case CONFIRMED: {
                uploadStatusDetail.incrementConfirmedNum();
                unitsState.put(unitKey, "\u5df2\u786e\u8ba4");
                break;
            }
            case REJECTED: {
                uploadStatusDetail.incrementRejectedNum();
                unitsState.put(unitKey, "\u5df2\u9000\u56de");
                break;
            }
            case ORIGINAL_UPLOAD: {
                uploadStatusDetail.incrementOriginalNum();
                unitsState.put(unitKey, "\u672a\u4e0a\u62a5");
                break;
            }
            default: {
                uploadStatusDetail.incrementOriginalNum();
                unitsState.put(unitKey, "\u672a\u4e0a\u62a5");
            }
        }
    }

    private UploadState getState(String state) {
        switch (state) {
            case "act_submit": 
            case "cus_submit": {
                return UploadState.SUBMITED;
            }
            case "act_return": 
            case "cus_return": {
                return UploadState.RETURNED;
            }
            case "act_upload": 
            case "cus_upload": 
            case "act_cancel_confirm": {
                return UploadState.UPLOADED;
            }
            case "act_confirm": 
            case "cus_confirm": {
                return UploadState.CONFIRMED;
            }
            case "act_reject": 
            case "cus_reject": {
                return UploadState.REJECTED;
            }
            case "start": {
                return UploadState.ORIGINAL_UPLOAD;
            }
        }
        return UploadState.ORIGINAL;
    }

    private List<IEntityRow> getCountChildEntitys(IEntityTable entityTable, int summaryScope, boolean directChild, List<IEntityRow> rootDatas, boolean addDirectChild) {
        ArrayList<IEntityRow> allRows = new ArrayList<IEntityRow>();
        for (IEntityRow rootData : rootDatas) {
            if (directChild) {
                allRows.add(rootData);
                if (summaryScope <= 1 || !addDirectChild) continue;
                List childRows = entityTable.getChildRows(rootData.getEntityKeyData());
                allRows.addAll(childRows);
                continue;
            }
            allRows.add(rootData);
            List allChildRows = entityTable.getAllChildRows(rootData.getEntityKeyData());
            allRows.addAll(allChildRows);
        }
        return allRows;
    }

    private List<IEntityRow> getChildEntitys(IEntityTable entityTable, int summaryScope, boolean directChild, List<IEntityRow> rootDatas, boolean addDirectChild) {
        ArrayList<IEntityRow> allRows = new ArrayList<IEntityRow>();
        for (IEntityRow rootData : rootDatas) {
            if (summaryScope > 1) {
                allRows.add(rootData);
                List allChildRows = entityTable.getAllChildRows(rootData.getEntityKeyData());
                allRows.addAll(allChildRows);
                continue;
            }
            allRows.add(rootData);
        }
        return allRows;
    }

    private List<IEntityRow> getExportChildEntitys(IEntityTable entityTable, int summaryScope, boolean directChild, List<IEntityRow> rootDatas, boolean addDirectChild) {
        ArrayList<IEntityRow> allRows = new ArrayList<IEntityRow>();
        for (IEntityRow rootData : rootDatas) {
            if (directChild) {
                allRows.add(rootData);
                if (summaryScope <= 1 || !addDirectChild) continue;
                List childRows = entityTable.getChildRows(rootData.getEntityKeyData());
                allRows.addAll(childRows);
                continue;
            }
            allRows.add(rootData);
            List allChildRows = entityTable.getAllChildRows(rootData.getEntityKeyData());
            allRows.addAll(allChildRows);
        }
        return allRows;
    }
}

