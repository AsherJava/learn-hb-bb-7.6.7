/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ArgumentValueEnum
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRangResult
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrSchemeConfigDTO
 *  com.jiuqi.gcreport.nrextracteditctrl.intf.INrExtractSchemeConfigService
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.mq;

import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchRangResult;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FetchRangCondition;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FinancialFetchCondition;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FinancialFetchInitTaskDTO;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.mq.AbstractFinancialAutoFetchTaskSplitHandler;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.utils.FinancialAutoFetchUtil;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrSchemeConfigDTO;
import com.jiuqi.gcreport.nrextracteditctrl.intf.INrExtractSchemeConfigService;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialRebuildAutoFetchTaskSplitHandler
extends AbstractFinancialAutoFetchTaskSplitHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialRebuildAutoFetchTaskSplitHandler.class);
    @Autowired(required=false)
    private INrExtractSchemeConfigService formulaSchemeConfigService;
    @Autowired
    private FetchSchemeNrService fetchSchemeService;

    @Override
    public String getTitle() {
        return "\u3010\u591a\u7ef4\u81ea\u52a8\u53d6\u6570\u3011\u591a\u7ef4\u91cd\u7b97";
    }

    @Override
    public String getName() {
        return "FINANCIAL_REBUILD_AUTO_FETCH";
    }

    @Override
    public String getPreTask() {
        return "FinancialCubesAccountingRebuildHandler;FinancialCubesAdjustRebuildHandler;FinancialCubesOffsetRebuildHandler;FinancialCubesMergeSummaryRebuildHandler";
    }

    @Override
    public Map<String, String> getHandleParams(String s) {
        FinancialCubesRebuildDTO financialCubesRebuildDTO = (FinancialCubesRebuildDTO)JsonUtils.readValue((String)s, FinancialCubesRebuildDTO.class);
        if (CollectionUtils.isEmpty((Collection)financialCubesRebuildDTO.getSubjectCodeList())) {
            return Collections.emptyMap();
        }
        BdeCommonUtil.initNpUser((String)(StringUtils.isEmpty((String)this.userName) ? "admin" : this.userName));
        boolean orgTypeIsNone = "NONE".equals(financialCubesRebuildDTO.getOrgType());
        ArrayList<FinancialFetchCondition> financialFetchConditions = new ArrayList<FinancialFetchCondition>();
        List fetchSchemeVOList = this.fetchSchemeService.listFetchSchemeByBizType(BizTypeEnum.NR.getCode());
        List formSchemeIds = fetchSchemeVOList.stream().map(FetchSchemeVO::getFormSchemeId).distinct().collect(Collectors.toList());
        String orgType = financialCubesRebuildDTO.getOrgType();
        for (String formSchemeId : formSchemeIds) {
            try {
                FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeId);
                if (formScheme == null || !FinancialAutoFetchUtil.isSamePeriod(formScheme.getPeriodType(), financialCubesRebuildDTO.getPeriodType())) continue;
                if (orgTypeIsNone) {
                    TaskDefine taskDefine = this.runTimeAuthViewController.queryTaskDefine(formScheme.getTaskKey());
                    orgType = taskDefine.getDw().substring(0, taskDefine.getDw().indexOf("@"));
                }
                OrgDTO orgParam = new OrgDTO();
                orgParam.setCode(financialCubesRebuildDTO.getUnitCode());
                orgParam.setCategoryname(orgType);
                orgParam.setAuthType(OrgDataOption.AuthType.ACCESS);
                orgParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
                PageVO page = ((OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class)).list(orgParam);
                if (CollectionUtils.isEmpty((Collection)page.getRows())) continue;
                String currencyCode = (String)((OrgDO)page.getRows().get(0)).getValueOf("CURRENCYID");
                HashMap<String, String> assistDim = new HashMap<String, String>();
                assistDim.put("DATATIME", financialCubesRebuildDTO.getDataTime());
                assistDim.put(ArgumentValueEnum.MD_GCADJTYPE.getCode(), "BEFOREADJ");
                assistDim.put("MD_CURRENCY", currencyCode);
                assistDim.put("MD_GCORGTYPE", financialCubesRebuildDTO.getOrgType());
                assistDim.put("MD_ORG", financialCubesRebuildDTO.getUnitCode());
                String entityId = orgType + "@ORG";
                NrSchemeConfigDTO formulaSchemeConfigDTO = this.formulaSchemeConfigService.getSchemeByOrgId(formSchemeId, entityId, financialCubesRebuildDTO.getUnitCode(), assistDim);
                if (formulaSchemeConfigDTO == null || StringUtils.isEmpty((String)formulaSchemeConfigDTO.getSchemeId())) continue;
                FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(formulaSchemeConfigDTO.getFetchSchemeId());
                FinancialFetchCondition financialFetchCondition = new FinancialFetchCondition();
                financialFetchCondition.setTaskId(formulaSchemeConfigDTO.getTaskId());
                financialFetchCondition.setDataTime(financialCubesRebuildDTO.getDataTime());
                financialFetchCondition.setEndBatchNum(financialFetchCondition.getEndBatchNum());
                financialFetchCondition.setOrgType(orgType);
                financialFetchCondition.setPeriodType(FinancialCubesPeriodTypeEnum.getByCode((String)financialCubesRebuildDTO.getPeriodType()));
                financialFetchCondition.setOrgCode(financialCubesRebuildDTO.getUnitCode());
                financialFetchCondition.setFormSchemeId(formulaSchemeConfigDTO.getSchemeId());
                financialFetchCondition.setFetchSchemeId(formulaSchemeConfigDTO.getFetchSchemeId());
                financialFetchCondition.setFetchSchemeName(fetchScheme.getName());
                financialFetchCondition.setMainDimCode(financialCubesRebuildDTO.getSubjectCodeList());
                financialFetchCondition.setCurrencyCode(currencyCode);
                financialFetchCondition.setRebuildScope(financialCubesRebuildDTO.getRebuildScope());
                financialFetchConditions.add(financialFetchCondition);
            }
            catch (Exception e) {
                LOGGER.error("\u591a\u7ef4\u81ea\u52a8\u53d6\u6570\u4efb\u52a1\u62c6\u5206\u83b7\u53d6\u53d6\u6570\u65b9\u6848\u5f02\u5e38", e);
            }
        }
        return financialFetchConditions.stream().collect(Collectors.toMap(JsonUtils::writeValueAsString, FinancialFetchCondition::getFetchSchemeId, (k1, k2) -> k1));
    }

    @Override
    public TaskHandleResult handleTask(String param, ITaskProgressMonitor iTaskProgressMonitor) {
        TaskHandleResult result = new TaskHandleResult();
        FinancialFetchCondition financialFetchCondition = (FinancialFetchCondition)JsonUtils.readValue((String)param, FinancialFetchCondition.class);
        String formSchemeId = financialFetchCondition.getFormSchemeId();
        String orgCode = financialFetchCondition.getOrgCode();
        String taskId = financialFetchCondition.getTaskId();
        String currencyCode = financialFetchCondition.getCurrencyCode();
        String dataTime = financialFetchCondition.getDataTime();
        String orgType = financialFetchCondition.getOrgType();
        BdeCommonUtil.initNpUser((String)(StringUtils.isEmpty((String)this.userName) ? "admin" : this.userName));
        FetchTaskUtil.buildNrCtxByOrgType((String)financialFetchCondition.getOrgType());
        EfdcInfo efdcInfo = new EfdcInfo();
        String adjustCode = null;
        if (DimensionUtils.isExistAdjust((String)financialFetchCondition.getTaskId())) {
            adjustCode = "0";
        }
        Map dimensionSet = DimensionUtils.buildDimensionMap((String)taskId, (String)currencyCode, (String)dataTime, (String)orgType, (String)orgCode, (String)adjustCode);
        ReadWriteAccessDesc readWriteAccessDesc = FinancialAutoFetchUtil.queryUnitWriteable(formSchemeId, orgCode, dataTime, currencyCode, orgType, adjustCode, taskId);
        if (!readWriteAccessDesc.getAble().booleanValue()) {
            result.appendLog(String.format("\u3010\u62a5\u8868\u65b9\u6848 ID\uff1a%s\u3011\u5f53\u524d\u62a5\u8868\u65b9\u6848\u6ca1\u6709\u5199\u6743\u9650\uff0c\u8df3\u8fc7\u8be5\u62a5\u8868\u65b9\u6848\uff0c\u539f\u56e0\uff1a%s", formSchemeId, readWriteAccessDesc.getDesc()));
            result.setSuccess(Boolean.valueOf(true));
            result.setPreParam(JsonUtils.writeValueAsString((Object)((Object)new FinancialFetchInitTaskDTO())));
            return result;
        }
        FetchRangResult fetchRangResult = this.getFetchRange(financialFetchCondition);
        if (CollectionUtils.isEmpty((Collection)fetchRangResult.getForms())) {
            result.appendLog("\u672a\u67e5\u8be2\u5230\u4e3b\u7ef4\u5ea6\u5bf9\u5e94\u7684\u62a5\u8868\u8303\u56f4");
            result.setSuccess(Boolean.valueOf(true));
            result.setPreParam(JsonUtils.writeValueAsString((Object)((Object)new FinancialFetchInitTaskDTO())));
            return result;
        }
        StringJoiner forms = new StringJoiner(";");
        fetchRangResult.getForms().forEach(forms::add);
        efdcInfo.setDimensionSet(dimensionSet);
        efdcInfo.setTaskKey(financialFetchCondition.getTaskId());
        efdcInfo.setFormKey(forms.toString());
        efdcInfo.setFormSchemeKey(financialFetchCondition.getFormSchemeId());
        efdcInfo.setVariableMap(new HashMap());
        StringBuilder log = new StringBuilder();
        String gcTaskId = UUIDUtils.newUUIDStr();
        FinancialFetchInitTaskDTO fetchInitTaskDTO = this.fetchParamProcessService.getFetchParam(efdcInfo, log, gcTaskId, fetchRangResult);
        if (fetchInitTaskDTO == null) {
            result.setSuccess(Boolean.valueOf(false));
            fetchInitTaskDTO = new FinancialFetchInitTaskDTO();
            result.appendLog("\u53c2\u6570\u89e3\u6790\u5931\u8d25\uff1a");
        } else {
            result.setSuccess(Boolean.valueOf(true));
            fetchInitTaskDTO.setTaskId(financialFetchCondition.getTaskId());
            fetchInitTaskDTO.setFetchRangResult(fetchRangResult);
            fetchInitTaskDTO.setGcTaskId(gcTaskId);
        }
        result.appendLog(log.toString());
        OrgMappingDTO orgMappingDto = (OrgMappingDTO)BdeClientUtil.parseResponse((BusinessResponseEntity)this.orgMappingClient.getOrgMappingByUnitCode(this.getBblx(efdcInfo, financialFetchCondition.getFetchSchemeId()), financialFetchCondition.getOrgCode()));
        fetchInitTaskDTO.setOrgMapping(orgMappingDto);
        String fetchFormInfoStr = FinancialAutoFetchUtil.fetchFormInfoStr(fetchInitTaskDTO);
        result.appendLog(fetchFormInfoStr);
        result.setPreParam(JsonUtils.writeValueAsString((Object)((Object)fetchInitTaskDTO)));
        return result;
    }

    @Override
    public String getFinancialTableName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getPreTaskNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getComputationSet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getMainCodes(FetchRangCondition financialFetchCondition) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean mainDimCodeMatchedFixed(FixedFetchSourceRowSettingVO fixedFetchSourceRowSettingVO, List<String> mainCodes) {
        return mainCodes.stream().anyMatch(dimCode -> FinancialRebuildAutoFetchTaskSplitHandler.match(dimCode, fixedFetchSourceRowSettingVO.getSubjectCode()));
    }

    @Override
    protected Set<String> getComputationSet(FinancialFetchCondition financialFetchCondition) {
        return this.getComputationModels(financialFetchCondition.getRebuildScope());
    }

    private Set<String> getComputationModels(List<String> rebuildRange) {
        HashSet<String> computationModels = new HashSet<String>();
        if (rebuildRange.contains("FINCUBES_AGING")) {
            computationModels.addAll(FinancialAutoFetchUtil.AGING_COMPUTATIONMODEL_LIST);
        }
        if (rebuildRange.contains("FINCUBES_DIM")) {
            computationModels.addAll(FinancialAutoFetchUtil.DIM_COMPUTATIONMODEL_LIST);
        }
        if (rebuildRange.contains("FINCUBES_CF")) {
            computationModels.addAll(FinancialAutoFetchUtil.CF_COMPUTATIONMODEL_LIST);
        }
        return computationModels;
    }
}

