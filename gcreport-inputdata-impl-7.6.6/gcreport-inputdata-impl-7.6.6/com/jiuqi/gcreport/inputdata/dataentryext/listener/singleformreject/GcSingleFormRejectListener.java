/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.np.NpReportQueryProvider
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IMetaGroup
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.bpm.event.SingleFormRejectEventListener
 *  com.jiuqi.nr.bpm.event.UserActionBatchPrepareEvent
 *  com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectCompleteEventImpl
 *  com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectPrepareEventImpl
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.inputdata.dataentryext.listener.singleformreject;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.np.NpReportQueryProvider;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.query.base.GcDataEntryContext;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IMetaGroup;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.event.SingleFormRejectEventListener;
import com.jiuqi.nr.bpm.event.UserActionBatchPrepareEvent;
import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectCompleteEventImpl;
import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectPrepareEventImpl;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class GcSingleFormRejectListener
implements SingleFormRejectEventListener {
    private Logger logger = LoggerFactory.getLogger(GcSingleFormRejectListener.class);
    static final Set<String> LISTENINGACTIONS = new HashSet<String>();
    private static final String EXECUTE_FLAG_NAME = "EXECUTE_FLAG_NAME";
    @Lazy
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Lazy
    @Autowired
    private NpReportQueryProvider provider;
    @Lazy
    @Autowired
    private IEntityMetaService entityMetaService;
    @Lazy
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController iRunTimeViewController;
    @Autowired
    BusinessGenerator businessGenerator;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Lazy
    @Autowired
    private IWorkflow iWorkflow;

    public Integer getSequence() {
        return Integer.MAX_VALUE;
    }

    public Set<String> getListeningActionId() {
        return LISTENINGACTIONS;
    }

    public void onPrepare(SingleFormRejectPrepareEventImpl event) throws Exception {
        try {
            this.executePrepare(event);
        }
        catch (Exception e) {
            this.logger.error("\u5355\u8868\u72b6\u6001\u540c\u6b65\u5f02\u5e38\u62a5\u9519:", e);
            throw e;
        }
    }

    private void executePrepare(SingleFormRejectPrepareEventImpl event) throws Exception {
        DimensionValueSet dimensionValueSet;
        String period;
        String formSchemeKey = event.getFormSchemeKey();
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(formSchemeKey, period = (dimensionValueSet = event.getDimensionValueSet()).getValue("DATATIME").toString());
        if (StringUtils.isEmpty((String)systemId)) {
            return;
        }
        YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)formSchemeKey, (String)period);
        GcDataEntryContext context = new GcDataEntryContext(formSchemeKey, this.provider);
        if (!StringUtils.isEmpty((String)context.getOrgTableName())) {
            String entityName = this.getEntityName(formSchemeKey, context.getOrgTableName());
            String orgId = dimensionValueSet.getValue("MD_ORG").toString();
            GcOrgCacheVO cacheVO = this.getOrgByScheme(context.getOrgTableName(), yp, orgId);
            if (Objects.isNull(cacheVO)) {
                this.logger.error("\u83b7\u53d6\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0ccode=" + orgId + " mad_org=" + context.getOrgTableName() + ",\u65f6\u671f=" + period);
                return;
            }
            boolean isDefault = this.iWorkflow.isDefaultWorkflow(formSchemeKey);
            boolean isCorporate = this.consolidatedTaskService.isCorporate(this.getTaskId(formSchemeKey), period, this.getOrgTableName(formSchemeKey));
            WorkFlowType startType = this.iWorkflow.queryStartType(formSchemeKey);
            FormSchemeDefine formSchemeDefine = context.getFormScheme();
            HashMap dimensionValueSetMap = new HashMap();
            DimensionValueSet filterDims = this.dimensionUtil.fliterDimensionValueSet(dimensionValueSet, formSchemeDefine);
            if (!isCorporate) {
                ConsolidatedTaskVO consolidatedTask;
                LinkedHashSet manageCodes;
                boolean isUpload = this.isUploadByDefaultWorkflow(formSchemeKey, period, isDefault);
                if (!StringUtils.isEmpty((String)systemId) && isUpload && (CollectionUtils.isEmpty(manageCodes = (consolidatedTask = this.consolidatedTaskService.getTaskBySchemeId(formSchemeKey, period)).getManageCalcUnitCodes()) || !manageCodes.contains(orgId))) {
                    this.checkUploadManageScheme(cacheVO, formSchemeKey, period, event.getFormKeys(), startType, yp, event, null, orgId);
                }
            }
        }
    }

    private String getEntityName(String formSchemeKey, String orgTableName) {
        boolean enableTwoTree = this.enableTwoTree(formSchemeKey);
        if (enableTwoTree) {
            FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
            IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(formScheme.getDw());
            return iEntityDefine.getCode();
        }
        return orgTableName;
    }

    private boolean enableTwoTree(String formSchemeKey) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        List taskOrgLinkDefines = runTimeViewController.listTaskOrgLinkByTask(this.getTaskId(formSchemeKey));
        return taskOrgLinkDefines != null && taskOrgLinkDefines.size() != 0 && taskOrgLinkDefines.size() != 1;
    }

    private GcOrgCacheVO getOrgByScheme(String orgTableName, YearPeriodDO yp, String orgCode) {
        if (StringUtils.isEmpty((String)orgTableName)) {
            return null;
        }
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgTableName, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)yp);
        GcOrgCacheVO cacheVO = tool.getOrgByCode(orgCode);
        return cacheVO;
    }

    private String getOrgTableName(String formSchemeKey) {
        return GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)this.getTaskId(formSchemeKey));
    }

    private String getTaskId(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        return formSchemeDefine.getTaskKey();
    }

    private boolean isUploadByDefaultWorkflow(String formSchemeKey, String periodStr, boolean isDefault) {
        boolean isCorporateDefault;
        String inputSchemeId;
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskBySchemeId(formSchemeKey, periodStr);
        return consolidatedTaskVO != null && !StringUtils.isEmpty((String)(inputSchemeId = ConsolidatedSystemUtils.getSchemeIdByTaskIdAndPeriod((String)consolidatedTaskVO.getTaskKey(), (String)periodStr))) && isDefault == (isCorporateDefault = this.iWorkflow.isDefaultWorkflow(inputSchemeId));
    }

    private void checkUploadManageScheme(GcOrgCacheVO cacheVO, String manageFormSchemeKey, String periodStr, Set<String> formKeys, WorkFlowType startType, YearPeriodDO yp, SingleFormRejectPrepareEventImpl event, UserActionBatchPrepareEvent batchEvent, String orgId) {
        if (cacheVO != null) {
            ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskBySchemeId(manageFormSchemeKey, periodStr);
            if (consolidatedTaskVO == null || consolidatedTaskVO.getInputTaskInfo() == null) {
                return;
            }
            String inputTaskTitle = consolidatedTaskVO.getInputTaskInfo().getTaskTitle();
            String inputSchemeId = ConsolidatedSystemUtils.getSchemeIdByTaskIdAndPeriod((String)consolidatedTaskVO.getTaskKey(), (String)periodStr);
            if (StringUtils.isEmpty((String)inputSchemeId)) {
                return;
            }
            if (manageFormSchemeKey.equals(inputSchemeId)) {
                if (CollectionUtils.isEmpty(consolidatedTaskVO.getManageEntityList())) {
                    return;
                }
                String manageOrgTable = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)consolidatedTaskVO.getTaskKey());
                if (consolidatedTaskVO.getManageEntityList().contains(manageOrgTable)) {
                    this.checkUpload(consolidatedTaskVO.getCorporateEntity(), cacheVO, inputSchemeId, formKeys, startType, yp, event, batchEvent, inputTaskTitle + GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.notuploadmultiorgmsg"));
                }
            } else {
                String orgTableName = this.getOrgTableCodeByFormSchemeKey(inputSchemeId);
                this.checkUpload(orgTableName, cacheVO, inputSchemeId, formKeys, startType, yp, event, batchEvent, inputTaskTitle);
            }
        } else {
            this.logger.info("\u7ba1\u7406\u67b6\u6784\u65b9\u6848\u4e0b\uff0c\u5f53\u524d\u5355\u4f4d\u7684\u5355\u4f4d\u7c7b\u578b\u4e3a\u7a7a\u3002");
            if (event != null) {
                event.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.manageorgtypeemptymsg"));
            } else {
                batchEvent.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.manageorgtypeemptymsg"));
            }
        }
    }

    private void checkUpload(String orgTableName, GcOrgCacheVO cacheVO, String inputSchemeId, Set<String> formKeys, WorkFlowType startType, YearPeriodDO yp, SingleFormRejectPrepareEventImpl event, UserActionBatchPrepareEvent batchEvent, String inputTaskTitle) {
        GcOrgCacheVO inputSchemeOrg = this.getOrgByScheme(orgTableName, yp, cacheVO.getCode());
        if (!WorkFlowType.ENTITY.equals((Object)startType)) {
            if (WorkFlowType.GROUP.equals((Object)startType)) {
                this.checkFormGroupUpload(inputSchemeOrg, cacheVO.getOrgTypeId(), inputSchemeId, formKeys, inputTaskTitle, event, batchEvent);
            }
        } else if (inputSchemeOrg != null && cacheVO.getOrgTypeId().equals(inputSchemeOrg.getOrgTypeId())) {
            this.logger.info("\u4e3b\u4f53\u4e0a\u62a5\uff0c\u5355\u4f4d\u3010" + cacheVO.getCode() + "\u3011");
            Object[] args = new String[]{inputTaskTitle};
            if (event != null) {
                event.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.notuploadmsg", (Object[])args));
            } else {
                batchEvent.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.notuploadmsg", (Object[])args));
            }
        }
    }

    private void checkFormGroupUpload(GcOrgCacheVO cacheVO, String orgTypeId, String inputSchemeId, Set<String> formKeys, String inputTaskTitle, SingleFormRejectPrepareEventImpl event, UserActionBatchPrepareEvent batchEvent) {
        List rootGroupFormKeys = this.iRunTimeViewController.queryRootGroupsByFormScheme(inputSchemeId).stream().map(IMetaGroup::getTitle).collect(Collectors.toList());
        for (String formKey : formKeys) {
            List formGroups = this.iRunTimeViewController.getFormGroupsByFormKey(formKey);
            FormGroupDefine formGroupDefine = formGroups.stream().filter(a -> a.getFormSchemeKey().equals(event.getFormSchemeKey())).findFirst().orElse(null);
            FormGroupDefine groupDefine = this.iRunTimeViewController.queryFormGroup(formGroupDefine.getKey());
            if (!rootGroupFormKeys.contains(groupDefine.getTitle()) || cacheVO == null || !orgTypeId.equals(cacheVO.getOrgTypeId())) continue;
            this.logger.info("\u5f53\u524d\u62a5\u8868\u5206\u7ec4\u3010" + groupDefine.getTitle() + "\u3011\uff0c\u5355\u4f4d\u3010" + cacheVO.getCode() + "\u3011");
            Object[] args = new String[]{inputTaskTitle};
            if (event != null) {
                event.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.notuploadmsg", (Object[])args));
                continue;
            }
            batchEvent.setBreak(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.notuploadmsg", (Object[])args));
        }
    }

    private String getOrgTableCodeByFormSchemeKey(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        TableModelDefine tableDefine = this.entityMetaService.getTableModel(formSchemeDefine.getDw());
        return tableDefine.getName();
    }

    public void onComplete(SingleFormRejectCompleteEventImpl event) throws Exception {
    }

    static {
        LISTENINGACTIONS.add("single_form_reject");
        LISTENINGACTIONS.add("single_form_upload");
    }
}

