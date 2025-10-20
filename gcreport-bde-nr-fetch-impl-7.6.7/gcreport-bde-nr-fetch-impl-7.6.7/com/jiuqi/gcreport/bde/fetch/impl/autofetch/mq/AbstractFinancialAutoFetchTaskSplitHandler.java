/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.common.constant.ArgumentValueEnum
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRangResult
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.fetch.client.OrgMappingClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.core.intf.impl.BaseTaskHandler
 *  com.jiuqi.dc.taskscheduling.lockmgr.domain.TaskManageDO
 *  com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.gc.financialcubes.mergesummary.dto.FinancialCubesMergeSummaryTaskDto
 *  com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrSchemeConfigDTO
 *  com.jiuqi.gcreport.nrextracteditctrl.intf.INrExtractSchemeConfigService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.mq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO;
import com.jiuqi.bde.common.dto.fetch.init.FetchRangResult;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.fetch.client.OrgMappingClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.core.intf.impl.BaseTaskHandler;
import com.jiuqi.dc.taskscheduling.lockmgr.domain.TaskManageDO;
import com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gc.financialcubes.mergesummary.dto.FinancialCubesMergeSummaryTaskDto;
import com.jiuqi.gcreport.bde.common.constant.FloatResultQueryTypeEnum;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FetchRangCondition;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FinancialFetchCondition;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FinancialFetchInitTaskDTO;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.service.FetchParamProcessService;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.service.FinancialAutoFetchService;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.service.handler.IFinancialAutoFetchTaskSplitHandler;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.utils.FinancialAutoFetchUtil;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchItemTaskLogEO;
import com.jiuqi.gcreport.bde.fetch.impl.enums.FetchExecuteStateEnum;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchTaskLogService;
import com.jiuqi.gcreport.bde.fetch.impl.utils.BdeSystemOptionUtil;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrSchemeConfigDTO;
import com.jiuqi.gcreport.nrextracteditctrl.intf.INrExtractSchemeConfigService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractFinancialAutoFetchTaskSplitHandler
extends BaseTaskHandler
implements IFinancialAutoFetchTaskSplitHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFinancialAutoFetchTaskSplitHandler.class);
    @Autowired
    private FetchSettingService fetchSettingService;
    @Autowired
    private FetchFloatSettingService fetchFloatSettingService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    protected FinancialAutoFetchService financialAutoFetchService;
    @Autowired(required=false)
    private INrExtractSchemeConfigService formulaSchemeConfigService;
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    @Autowired
    protected FetchParamProcessService fetchParamProcessService;
    @Autowired
    private GcFetchTaskLogService taskLogService;
    @Autowired
    private TaskManageService taskService;
    @Autowired
    protected RunTimeAuthViewController runTimeAuthViewController;
    @Value(value="${jiuqi.np.user.system[0].name:}")
    protected String userName;
    @Autowired
    private BdeSystemOptionUtil optionUtil;
    @Autowired
    protected OrgMappingClient orgMappingClient;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IEntityMetaService entityMetaService;

    public String getName() {
        return "FINANCIAL_COLLECTION_AUTO_FETCH_" + this.getFinancialTableName();
    }

    public String getTitle() {
        return "\u3010\u591a\u7ef4\u81ea\u52a8\u53d6\u6570\u3011\u591a\u7ef4\u8ba1\u7b97";
    }

    public String getPreTask() {
        StringJoiner stringJoiner = new StringJoiner(";");
        for (String preTaskName : this.getPreTaskNames()) {
            stringJoiner.add(preTaskName);
        }
        return stringJoiner.toString();
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.FOLLOW;
    }

    public String getModule() {
        return "GC";
    }

    public IDimType getDimType() {
        return new IDimType(){

            public String getTitle() {
                return "\u53d6\u6570\u65b9\u6848";
            }

            public String getName() {
                return "FETCH_SCHEME";
            }
        };
    }

    public boolean enable(String preTaskName, String preParam) {
        return this.openAutoFetch();
    }

    public Map<String, String> getHandleParams(String s) {
        List financialCubesMergeSummaryTasks = (List)JsonUtils.readValue((String)s, (TypeReference)new TypeReference<List<FinancialCubesMergeSummaryTaskDto>>(){});
        if (CollectionUtils.isEmpty((Collection)financialCubesMergeSummaryTasks)) {
            return Collections.emptyMap();
        }
        ArrayList<FinancialFetchCondition> financialFetchConditions = new ArrayList<FinancialFetchCondition>();
        FinancialCubesMergeSummaryTaskDto firstTask = (FinancialCubesMergeSummaryTaskDto)financialCubesMergeSummaryTasks.get(0);
        String unitCode = firstTask.getUnitCode();
        String orgType = firstTask.getOrgType();
        boolean orgTypeIsNone = "NONE".equals(orgType);
        BdeCommonUtil.initNpUser((String)(StringUtils.isEmpty((String)this.userName) ? "admin" : this.userName));
        this.taskService.initTaskManageByUnitCodes(this.getName(), Collections.singletonList(unitCode), new Date());
        List fetchSchemeVOList = this.fetchSchemeService.listFetchSchemeByBizType(BizTypeEnum.NR.getCode());
        List formSchemes = fetchSchemeVOList.stream().map(FetchSchemeVO::getFormSchemeId).distinct().collect(Collectors.toList());
        for (FinancialCubesMergeSummaryTaskDto financialCubesMergeSummaryTask : financialCubesMergeSummaryTasks) {
            for (String formSchemeId : formSchemes) {
                FetchSchemeVO fetchScheme;
                List<OrgDO> orgs;
                FormSchemeDefine formScheme = this.getFormSchemeDefine(financialCubesMergeSummaryTask, formSchemeId);
                if (formScheme == null) continue;
                if (orgTypeIsNone) {
                    TaskDefine taskDefine = this.getTaskDefine(formScheme);
                    if (taskDefine == null) continue;
                    orgType = taskDefine.getDw().substring(0, taskDefine.getDw().indexOf("@"));
                }
                if (CollectionUtils.isEmpty(orgs = AbstractFinancialAutoFetchTaskSplitHandler.getAccessibleOrganizations(unitCode, orgType))) continue;
                Object currencyCodeObj = orgs.get(0).getValueOf("CURRENCYID");
                Assert.isInstanceOf(String.class, (Object)currencyCodeObj);
                String currencyCode = (String)currencyCodeObj;
                NrSchemeConfigDTO formulaScheme = this.getFormulaSchemeConfig(financialCubesMergeSummaryTask, formSchemeId, currencyCode, orgType);
                if (formulaScheme == null || StringUtils.isEmpty((String)formulaScheme.getSchemeId()) || (fetchScheme = this.fetchSchemeService.getFetchScheme(formulaScheme.getFetchSchemeId())) == null) continue;
                FinancialFetchCondition financialFetchCondition = new FinancialFetchCondition();
                financialFetchCondition.setTaskId(formulaScheme.getTaskId());
                financialFetchCondition.setOrgType(orgType);
                financialFetchCondition.setDataTime(financialCubesMergeSummaryTask.getDataTime());
                financialFetchCondition.setEndBatchNum(financialCubesMergeSummaryTask.getBatchNum());
                financialFetchCondition.setPeriodType(financialCubesMergeSummaryTask.getPeriodType());
                financialFetchCondition.setOrgCode(financialCubesMergeSummaryTask.getUnitCode());
                financialFetchCondition.setFormSchemeId(formScheme.getKey());
                financialFetchCondition.setFormSchemeName(formScheme.getTitle());
                financialFetchCondition.setFetchSchemeId(formulaScheme.getFetchSchemeId());
                financialFetchCondition.setFetchSchemeName(fetchScheme.getName());
                financialFetchCondition.setCurrencyCode(currencyCode);
                financialFetchConditions.add(financialFetchCondition);
            }
        }
        return financialFetchConditions.stream().collect(Collectors.toMap(JsonUtils::writeValueAsString, FinancialFetchCondition::getFetchSchemeId, (k1, k2) -> k1));
    }

    private FormSchemeDefine getFormSchemeDefine(FinancialCubesMergeSummaryTaskDto financialCubesMergeSummaryTask, String formSchemeId) {
        try {
            FormSchemeDefine formScheme = this.getFormSchemeDefineByPeriodType(financialCubesMergeSummaryTask.getPeriodType(), formSchemeId);
            if (formScheme == null) {
                LOGGER.warn("\u300c{}\u300d\u6784\u5efa\u4efb\u52a1\u53c2\u6570\u5f02\u5e38\uff1a\u672a\u627e\u5230\u300c\u6307\u5b9a\u65f6\u671f\u7c7b\u578b\u300d\u7684\u300c\u62a5\u8868\u65b9\u6848\u300d\uff0c\u8df3\u8fc7\u8be5\u4efb\u52a1\uff0cformSchemeId: {}\uff0cperiodType: {}", this.getTitle(), formSchemeId, financialCubesMergeSummaryTask.getPeriodType());
            }
            return formScheme;
        }
        catch (Exception e) {
            LOGGER.warn("\u300c{}\u300d\u6784\u5efa\u4efb\u52a1\u53c2\u6570\u5f02\u5e38\uff1a\u83b7\u53d6\u300c\u6307\u5b9a\u65f6\u671f\u7c7b\u578b\u300d\u7684\u300c\u62a5\u8868\u65b9\u6848\u300d\u5931\u8d25\uff0c\u8df3\u8fc7\u8be5\u4efb\u52a1\uff0cformSchemeId: {}\uff0cperiodType: {}, \u5f02\u5e38\uff1a{}", this.getTitle(), formSchemeId, financialCubesMergeSummaryTask.getPeriodType(), e);
            return null;
        }
    }

    private TaskDefine getTaskDefine(FormSchemeDefine formScheme) {
        try {
            TaskDefine taskDefine = this.runTimeAuthViewController.queryTaskDefine(formScheme.getTaskKey());
            if (taskDefine == null) {
                LOGGER.error("\u300c{}\u300d\u6784\u5efa\u4efb\u52a1\u53c2\u6570\u5f02\u5e38\uff1a\u672a\u627e\u5230\u4efb\u52a1\u5b9a\u4e49, taskKey: {}", (Object)this.getTitle(), (Object)formScheme.getTaskKey());
            }
            return taskDefine;
        }
        catch (Exception e) {
            LOGGER.error("\u300c{}\u300d\u6784\u5efa\u4efb\u52a1\u53c2\u6570\u5f02\u5e38\uff1a\u83b7\u53d6\u4efb\u52a1\u5b9a\u4e49\u5931\u8d25, taskKey: {}, \u5f02\u5e38: {}", this.getTitle(), formScheme.getTaskKey(), e);
            return null;
        }
    }

    private NrSchemeConfigDTO getFormulaSchemeConfig(FinancialCubesMergeSummaryTaskDto financialCubesMergeSummaryTaskDto, String formSchemeId, String currencyCode, String orgType) {
        Map<String, String> assistDim = AbstractFinancialAutoFetchTaskSplitHandler.buildAssistDimParamMap(financialCubesMergeSummaryTaskDto, currencyCode, orgType);
        String entityId = orgType + "@ORG";
        return this.formulaSchemeConfigService.getSchemeByOrgId(formSchemeId, entityId, financialCubesMergeSummaryTaskDto.getUnitCode(), assistDim);
    }

    private static Map<String, String> buildAssistDimParamMap(FinancialCubesMergeSummaryTaskDto financialCubesMergeSummaryTaskDto, String currencyCode, String orgType) {
        HashMap<String, String> assistDim = new HashMap<String, String>();
        assistDim.put("DATATIME", financialCubesMergeSummaryTaskDto.getDataTime());
        assistDim.put(ArgumentValueEnum.MD_GCADJTYPE.getCode(), "BEFOREADJ");
        assistDim.put("MD_CURRENCY", currencyCode);
        assistDim.put("MD_GCORGTYPE", orgType);
        assistDim.put("MD_ORG", financialCubesMergeSummaryTaskDto.getUnitCode());
        return assistDim;
    }

    private static ReadWriteAccessDesc getReadWriteAccessDesc(String dataTime, String formSchemeId, String unitCode, String currencyCode, String orgType, String taskKey) {
        DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)unitCode, (Object)dataTime, (Object)currencyCode, (Object)orgType, (String)"0", (String)taskKey);
        UploadState queryUploadState = FinancialAutoFetchUtil.queryUnitUploadState(formSchemeId, dimensionValueSet);
        return FinancialAutoFetchUtil.writeable(queryUploadState);
    }

    private static List<OrgDO> getAccessibleOrganizations(String unitCode, String orgType) {
        OrgDTO orgParam = new OrgDTO();
        orgParam.setCode(unitCode);
        orgParam.setCategoryname(orgType);
        orgParam.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        PageVO list = ((OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class)).list(orgParam);
        return list == null ? Collections.emptyList() : list.getRows();
    }

    private FormSchemeDefine getFormSchemeDefineByPeriodType(FinancialCubesPeriodTypeEnum taskPeriodType, String formSchemeId) throws Exception {
        FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeId);
        return formScheme == null || !FinancialAutoFetchUtil.isSamePeriod(formScheme.getPeriodType(), taskPeriodType) ? null : formScheme;
    }

    public TaskHandleResult handleTask(String param, ITaskProgressMonitor iTaskProgressMonitor) {
        TaskHandleResult result = new TaskHandleResult();
        FinancialFetchCondition financialFetchCondition = (FinancialFetchCondition)JsonUtils.readValue((String)param, FinancialFetchCondition.class);
        String formSchemeId = financialFetchCondition.getFormSchemeId();
        String orgCode = financialFetchCondition.getOrgCode();
        String taskId = financialFetchCondition.getTaskId();
        String currencyCode = financialFetchCondition.getCurrencyCode();
        String dataTime = financialFetchCondition.getDataTime();
        String orgType = financialFetchCondition.getOrgType();
        int endNum = financialFetchCondition.getEndBatchNum();
        BdeCommonUtil.initNpUser((String)(StringUtils.isEmpty((String)this.userName) ? "admin" : this.userName));
        FetchTaskUtil.buildNrCtxByOrgType((String)financialFetchCondition.getOrgType());
        this.taskService.updateBeginHandle(this.getName(), orgCode, new Date());
        TaskManageDO task = this.taskService.getTaskManageByName(this.getName(), orgCode);
        int startNum = task.getBatchNum();
        if (endNum <= startNum) {
            result.appendLog(String.format("\u5f53\u524d\u51ed\u8bc1\u6279\u6b21\u3010%s\u3011\u4e0b\u7684\u6570\u636e\u5df2\u7ecf\u56de\u5199\u5b8c\u6210", endNum));
            this.taskService.updateEndHandle(this.getName(), orgCode, endNum);
            result.setPreParam(JsonUtils.writeValueAsString((Object)((Object)new FinancialFetchInitTaskDTO())));
            return result;
        }
        ReadWriteAccessDesc readWriteAccessDesc = AbstractFinancialAutoFetchTaskSplitHandler.getReadWriteAccessDesc(dataTime, formSchemeId, orgCode, currencyCode, orgType, taskId);
        if (!readWriteAccessDesc.getAble().booleanValue()) {
            result.appendLog(String.format("\u3010\u62a5\u8868\u65b9\u6848 ID\uff1a%s\u3011\u5f53\u524d\u62a5\u8868\u65b9\u6848\u6ca1\u6709\u5199\u6743\u9650\uff0c\u8df3\u8fc7\u8be5\u62a5\u8868\u65b9\u6848\uff0c\u539f\u56e0\uff1a%s", formSchemeId, readWriteAccessDesc.getDesc()));
            result.setSuccess(Boolean.valueOf(true));
            result.setPreParam(JsonUtils.writeValueAsString((Object)((Object)new FinancialFetchInitTaskDTO())));
            this.taskService.updateEndHandle(this.getName(), orgCode, endNum);
            return result;
        }
        FetchRangCondition fetchRangeCondition = this.buildFetchRangeCondition(financialFetchCondition, endNum, startNum);
        List<String> mainCodes = this.getRootMainCodesByLike(this.getMainCodes(fetchRangeCondition));
        if (CollectionUtils.isEmpty(mainCodes)) {
            result.appendLog("\u672a\u67e5\u8be2\u5230\u5bf9\u5e94\u4e3b\u7ef4\u5ea6\u8303\u56f4");
            result.setSendPostTaskMsgWhileHandleTask(Boolean.valueOf(true));
            result.setSuccess(Boolean.valueOf(true));
            result.setPreParam(JsonUtils.writeValueAsString((Object)((Object)new FinancialFetchInitTaskDTO())));
            this.taskService.updateEndHandle(this.getName(), orgCode, endNum);
            return result;
        }
        financialFetchCondition.setMainDimCode(mainCodes);
        FetchRangResult fetchRangResult = this.getFetchRange(financialFetchCondition);
        if (CollectionUtils.isEmpty((Collection)fetchRangResult.getForms())) {
            result.setSendPostTaskMsgWhileHandleTask(Boolean.valueOf(true));
            result.appendLog("\u672a\u67e5\u8be2\u5230\u4e3b\u7ef4\u5ea6\u5bf9\u5e94\u7684\u62a5\u8868\u8303\u56f4");
            this.taskService.updateEndHandle(this.getName(), orgCode, endNum);
            result.setSuccess(Boolean.valueOf(true));
            result.setPreParam(JsonUtils.writeValueAsString((Object)((Object)new FinancialFetchInitTaskDTO())));
            return result;
        }
        String adjustCode = null;
        if (DimensionUtils.isExistAdjust((String)taskId)) {
            adjustCode = "0";
        }
        Map dimensionSet = DimensionUtils.buildDimensionMap((String)taskId, (String)currencyCode, (String)dataTime, (String)orgType, (String)orgCode, (String)adjustCode);
        StringJoiner forms = new StringJoiner(";");
        new HashSet(fetchRangResult.getForms()).forEach(forms::add);
        EfdcInfo efdcInfo = new EfdcInfo();
        efdcInfo.setDimensionSet(dimensionSet);
        efdcInfo.setTaskKey(taskId);
        efdcInfo.setFormKey(forms.toString());
        efdcInfo.setFormSchemeKey(formSchemeId);
        efdcInfo.setVariableMap(new HashMap(8));
        StringBuilder log = new StringBuilder();
        String gcTaskId = UUIDUtils.newUUIDStr();
        FinancialFetchInitTaskDTO fetchInitTaskDTO = this.fetchParamProcessService.getFetchParam(efdcInfo, log, gcTaskId, fetchRangResult);
        if (fetchInitTaskDTO == null) {
            fetchInitTaskDTO = new FinancialFetchInitTaskDTO();
            result.setSuccess(Boolean.valueOf(false));
            result.appendLog("\u53c2\u6570\u89e3\u6790\u5931\u8d25\uff1a");
            result.appendLog(log.toString());
        } else {
            result.setSuccess(Boolean.valueOf(true));
            fetchInitTaskDTO.setFetchRangResult(fetchRangResult);
            fetchInitTaskDTO.setTaskId(financialFetchCondition.getTaskId());
            fetchInitTaskDTO.setGcTaskId(gcTaskId);
        }
        OrgMappingDTO orgMappingDto = (OrgMappingDTO)BdeClientUtil.parseResponse((BusinessResponseEntity)this.orgMappingClient.getOrgMappingByUnitCode(this.getBblx(efdcInfo, financialFetchCondition.getFetchSchemeId()), orgCode));
        fetchInitTaskDTO.setOrgMapping(orgMappingDto);
        String fetchFormInfoStr = FinancialAutoFetchUtil.fetchFormInfoStr(fetchInitTaskDTO);
        result.appendLog(fetchFormInfoStr);
        result.setPreParam(JsonUtils.writeValueAsString((Object)((Object)fetchInitTaskDTO)));
        this.taskService.updateEndHandle(this.getName(), financialFetchCondition.getOrgCode(), endNum);
        return result;
    }

    protected String getBblx(EfdcInfo efdcInfo, String fetchSchemeId) {
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(efdcInfo.getFormSchemeKey());
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(efdcInfo.getDimensionSet());
        jtableContext.setFormSchemeKey(efdcInfo.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(fetchSchemeId);
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)jtableContext);
        if (dimensionValueSet.getValue(dwEntity.getDimensionName()) == null) {
            return null;
        }
        String unitKey = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        String unitBblx = "";
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(dwEntity.getKey());
        IEntityAttribute bblxField = dwEntityModel.getBblxField();
        if (bblxField != null) {
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setContext(jtableContext);
            entityQueryByKeyInfo.setEntityKey(unitKey);
            entityQueryByKeyInfo.setEntityViewKey(dwEntity.getKey());
            entityQueryByKeyInfo.getCaptionFields().add(bblxField.getCode());
            EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
            int bblxIndex = queryEntityDataByKey.getCells().indexOf(bblxField.getCode());
            EntityData entity = queryEntityDataByKey.getEntity();
            if (entity != null && bblxIndex >= 0) {
                unitBblx = (String)entity.getData().get(bblxIndex);
            }
        }
        return unitBblx;
    }

    private FetchRangCondition buildFetchRangeCondition(FinancialFetchCondition financialFetchCondition, int endNum, int startNum) {
        FetchRangCondition fetchRangeCondition = new FetchRangCondition();
        fetchRangeCondition.setDataTime(financialFetchCondition.getDataTime());
        fetchRangeCondition.setFinancialTableName(this.getFinancialTableName());
        fetchRangeCondition.setEndBatchNum(endNum);
        fetchRangeCondition.setStartBatchNum(startNum);
        fetchRangeCondition.setPeriodType(financialFetchCondition.getPeriodType());
        fetchRangeCondition.setOrgCode(financialFetchCondition.getOrgCode());
        return fetchRangeCondition;
    }

    public void afterSubtasksHandle(String param) {
        FinancialFetchInitTaskDTO fetchInitTaskDTO = (FinancialFetchInitTaskDTO)((Object)JsonUtils.readValue((String)param, FinancialFetchInitTaskDTO.class));
        BdeCommonUtil.initNpUser((String)(StringUtils.isEmpty((String)this.userName) ? "admin" : this.userName));
        List<GcFetchItemTaskLogEO> errorItemTaskList = this.taskLogService.getErrorItemTaskList(fetchInitTaskDTO.getRequestRunnerId());
        this.fetchParamProcessService.calcAll(fetchInitTaskDTO.getEfdcInfo(), fetchInitTaskDTO.getForms(), fetchInitTaskDTO.getFormulaSchemeList());
        if (!CollectionUtils.isEmpty(errorItemTaskList)) {
            this.taskLogService.updateFetchLog(fetchInitTaskDTO.getEfdcInfo(), fetchInitTaskDTO.getGcTaskId(), fetchInitTaskDTO.getRequestRunnerId(), FetchExecuteStateEnum.FAILED.getStateNum(), "\u81ea\u52a8\u53d6\u6570\u6267\u884c\u5931\u8d25");
        } else {
            this.taskLogService.updateFetchLog(fetchInitTaskDTO.getEfdcInfo(), fetchInitTaskDTO.getGcTaskId(), fetchInitTaskDTO.getRequestRunnerId(), FetchExecuteStateEnum.SUCCESS.getStateNum(), "\u81ea\u52a8\u53d6\u6570\u6267\u884c\u6210\u529f");
        }
    }

    @Override
    public FetchRangResult getFetchRange(FinancialFetchCondition financialFetchCondition) {
        String formSchemeId = financialFetchCondition.getFormSchemeId();
        List formDefineList = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeId);
        ArrayList<DataRegionDefine> fixRegions = new ArrayList<DataRegionDefine>();
        ArrayList<DataRegionDefine> floatRegions = new ArrayList<DataRegionDefine>();
        for (FormDefine formDefine : formDefineList) {
            List regions = this.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
            fixRegions.addAll(regions.stream().filter(item -> DataRegionKind.DATA_REGION_SIMPLE.equals((Object)item.getRegionKind())).collect(Collectors.toList()));
            floatRegions.addAll(regions.stream().filter(item -> DataRegionKind.DATA_REGION_ROW_LIST.equals((Object)item.getRegionKind())).collect(Collectors.toList()));
        }
        if (StringUtils.isEmpty((String)ShiroUtil.getToken())) {
            ShiroUtil.bindToken((String)"SHIRO_TOKEN_KEY");
        }
        List bizModels = FetchSettingNrUtil.getBizModelDTOs();
        Map<String, BizModelDTO> code2BizMoelMap = bizModels.stream().collect(Collectors.toMap(BizModelDTO::getCode, Function.identity(), (k1, k2) -> k1));
        return new FetchRangResult(this.filterMainDimCodeMatchedRegions(financialFetchCondition, floatRegions, code2BizMoelMap), this.filterMainDimCodeMatchedDataLinks(financialFetchCondition, fixRegions, code2BizMoelMap));
    }

    protected Map<String, Set<String>> filterMainDimCodeMatchedDataLinks(FinancialFetchCondition financialFetchCondition, List<DataRegionDefine> fixRegionDefines, Map<String, BizModelDTO> code2BizMoelMap) {
        HashMap<String, Set<String>> regionDataLinkMap = new HashMap<String, Set<String>>();
        FetchSettingCond fixCondition = new FetchSettingCond();
        fixCondition.setFetchSchemeId(financialFetchCondition.getFetchSchemeId());
        fixCondition.setFormSchemeId(financialFetchCondition.getFormSchemeId());
        List fetchSettings = this.fetchSettingService.listFetchSettingByFormId(fixCondition);
        Map<String, List<FetchSettingVO>> fetchSettingMap = fetchSettings.stream().collect(Collectors.groupingBy(FetchSettingVO::getRegionId));
        for (DataRegionDefine dataRegionDefine : fixRegionDefines) {
            if (fetchSettingMap.get(dataRegionDefine.getKey()) == null) continue;
            Set dataLinkSet = regionDataLinkMap.getOrDefault(dataRegionDefine.getFormKey(), new HashSet());
            block1: for (FetchSettingVO fetchSetting : fetchSettingMap.get(dataRegionDefine.getKey())) {
                for (FixedAdaptSettingVO fixedAdaptSetting : fetchSetting.getFixedSettingData()) {
                    if (!this.mainDimCodeMatchedFixed(fixedAdaptSetting, financialFetchCondition, code2BizMoelMap)) continue;
                    dataLinkSet.add(fetchSetting.getDataLinkId());
                    if (CollectionUtils.isEmpty((Collection)dataLinkSet)) continue block1;
                    regionDataLinkMap.put(dataRegionDefine.getFormKey(), dataLinkSet);
                    continue block1;
                }
            }
        }
        return regionDataLinkMap;
    }

    protected Set<String> getComputationSet(FinancialFetchCondition condition) {
        return this.getComputationSet();
    }

    protected boolean mainDimCodeMatchedFixed(FixedAdaptSettingVO fixedAdaptSetting, FinancialFetchCondition financialFetchCondition, Map<String, BizModelDTO> code2BizMoelMap) {
        for (Map.Entry entry : fixedAdaptSetting.getBizModelFormula().entrySet()) {
            List fixSettingList;
            if (code2BizMoelMap.get(entry.getKey()) == null || !this.getComputationSet(financialFetchCondition).contains(code2BizMoelMap.get(entry.getKey()).getComputationModelCode()) || CollectionUtils.isEmpty((Collection)(fixSettingList = (List)entry.getValue())) || !this.mainDimCodeMatchedFixed(fixSettingList, financialFetchCondition.getMainDimCode())) continue;
            return true;
        }
        return false;
    }

    protected boolean mainDimCodeMatchedFixed(List<FixedFetchSourceRowSettingVO> fixedFetchSettings, List<String> mainDimCodes) {
        return fixedFetchSettings.stream().anyMatch(setting -> this.mainDimCodeMatchedFixed((FixedFetchSourceRowSettingVO)setting, mainDimCodes));
    }

    protected boolean mainDimCodeMatchedFixed(FixedFetchSourceRowSettingVO fixedFetchSetting, List<String> mainDimCodes) {
        if (ComputationModelEnum.XJLLBALANCE.getCode().equals(fixedFetchSetting.getBizModelCode())) {
            return mainDimCodes.stream().anyMatch(dimCode -> AbstractFinancialAutoFetchTaskSplitHandler.match(fixedFetchSetting.getCashCode(), dimCode));
        }
        return mainDimCodes.stream().anyMatch(dimCode -> AbstractFinancialAutoFetchTaskSplitHandler.match(fixedFetchSetting.getSubjectCode(), dimCode));
    }

    protected Map<String, Set<String>> filterMainDimCodeMatchedRegions(FinancialFetchCondition financialFetchCondition, List<DataRegionDefine> floatRegionDefines, Map<String, BizModelDTO> code2BizMoelMap) {
        HashMap<String, Set<String>> formRegionsMap = new HashMap<String, Set<String>>();
        FetchSettingCond floatCondition = new FetchSettingCond();
        floatCondition.setFetchSchemeId(financialFetchCondition.getFetchSchemeId());
        floatCondition.setFormSchemeId(financialFetchCondition.getFormSchemeId());
        List floatRegionConfigs = this.fetchFloatSettingService.listFetchFloatSettingByFormId(floatCondition);
        List fetchSettings = this.fetchSettingService.listFetchSettingByFormId(floatCondition);
        Map<String, List<FetchSettingVO>> regionId2FetchSettingsMap = fetchSettings.stream().collect(Collectors.groupingBy(FetchSettingVO::getRegionId));
        Map regionId2FloatRegionConfigMap = floatRegionConfigs.stream().collect(Collectors.toMap(FloatRegionConfigVO::getRegionId, Function.identity(), (k1, k2) -> k1));
        for (DataRegionDefine dataRegionDefine : floatRegionDefines) {
            FloatRegionConfigVO floatRegionConfig = (FloatRegionConfigVO)regionId2FloatRegionConfigMap.get(dataRegionDefine.getKey());
            List<FetchSettingVO> curFetchSettings = regionId2FetchSettingsMap.get(dataRegionDefine.getKey());
            if (floatRegionConfig == null && CollectionUtils.isEmpty(curFetchSettings) || !this.mainDimCodeMatchedFloat(floatRegionConfig, code2BizMoelMap, financialFetchCondition) && !this.mainDimCodeMatchedFixed(financialFetchCondition, curFetchSettings, code2BizMoelMap)) continue;
            Set floatRegions = formRegionsMap.computeIfAbsent(dataRegionDefine.getFormKey(), k -> new HashSet());
            floatRegions.add(dataRegionDefine.getKey());
        }
        return formRegionsMap;
    }

    protected boolean mainDimCodeMatchedFloat(FloatRegionConfigVO floatRegionConfig, Map<String, BizModelDTO> code2BizMoelMap, FinancialFetchCondition financialFetchCondition) {
        if (floatRegionConfig == null) {
            return false;
        }
        if (!FloatResultQueryTypeEnum.SIMPLE_FETCHSOURCE.getCode().equals(floatRegionConfig.getQueryType())) {
            return false;
        }
        if (StringUtils.isEmpty((String)floatRegionConfig.getQueryConfigInfo().getPluginData())) {
            return false;
        }
        SimpleCustomComposePluginDataVO simpleCustomComposePluginDataVO = (SimpleCustomComposePluginDataVO)JSONUtil.parseObject((String)floatRegionConfig.getQueryConfigInfo().getPluginData(), SimpleCustomComposePluginDataVO.class);
        BizModelDTO bizModelDTO = code2BizMoelMap.get(simpleCustomComposePluginDataVO.getFetchSourceCode());
        if (bizModelDTO == null) {
            return false;
        }
        if (!this.getComputationSet(financialFetchCondition).contains(bizModelDTO.getComputationModelCode())) {
            return false;
        }
        String mainDimCode = bizModelDTO.getComputationModelCode().equals(ComputationModelEnum.XJLLBALANCE.getCode()) ? "CFITEMCODE" : "SUBJECTCODE";
        for (Dimension dimension : simpleCustomComposePluginDataVO.getDimensionMapping()) {
            if (!mainDimCode.equals(dimension.getDimCode())) continue;
            return financialFetchCondition.getMainDimCode().stream().anyMatch(dimCode -> AbstractFinancialAutoFetchTaskSplitHandler.match(dimension.getDimValue(), dimCode));
        }
        return false;
    }

    private boolean mainDimCodeMatchedFixed(FinancialFetchCondition financialFetchCondition, List<FetchSettingVO> fetchSettings, Map<String, BizModelDTO> code2BizMoelMap) {
        if (CollectionUtils.isEmpty(fetchSettings)) {
            return false;
        }
        for (FetchSettingVO fetchSetting : fetchSettings) {
            for (FixedAdaptSettingVO fixedAdaptSetting : fetchSetting.getFixedSettingData()) {
                if (!this.mainDimCodeMatchedFixed(fixedAdaptSetting, financialFetchCondition, code2BizMoelMap)) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean match(String setting, String code) {
        Assert.isNotEmpty((String)setting);
        String string = code = code == null ? "" : code;
        if (setting.contains(":")) {
            return FinancialAutoFetchUtil.matchRange(setting, code);
        }
        if (setting.contains(",")) {
            return FinancialAutoFetchUtil.matchMultiple(setting, code);
        }
        return FinancialAutoFetchUtil.matchSingle(setting, code);
    }

    protected List<String> getRootMainCodesByLike(List<String> mainCodes) {
        if (CollectionUtils.isEmpty(mainCodes)) {
            return new ArrayList<String>();
        }
        mainCodes.sort(String::compareTo);
        ArrayList<String> optimizationList = new ArrayList<String>();
        String markSubject = mainCodes.get(0);
        optimizationList.add(markSubject);
        for (String result : mainCodes) {
            if (result.startsWith(markSubject)) continue;
            markSubject = result;
            optimizationList.add(result);
        }
        return optimizationList;
    }

    protected boolean openAutoFetch() {
        return this.optionUtil.enableAutoFetch();
    }
}

