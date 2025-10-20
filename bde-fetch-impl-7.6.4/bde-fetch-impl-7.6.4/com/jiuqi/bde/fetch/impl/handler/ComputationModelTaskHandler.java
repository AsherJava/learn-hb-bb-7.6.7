/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.base.intf.FetchResultDim
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.define.IBizModelExecute
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelExecuteGather
 *  com.jiuqi.bde.bizmodel.define.gather.impl.BdeBizModelGather
 *  com.jiuqi.bde.bizmodel.execute.service.FetchDataResultService
 *  com.jiuqi.bde.bizmodel.impl.model.service.BizModelService
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.OptionItemEnum
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.dto.FetchDataExecuteContext
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.intf.FetchSettingCacheKey
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.LogUtil
 *  com.jiuqi.bde.common.util.Pair
 *  com.jiuqi.bde.log.enums.FetchDimType
 *  com.jiuqi.bde.log.enums.FetchTaskType
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.base.common.utils.CompressUtil
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.core.intf.impl.BaseTaskHandler
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.fetch.impl.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.base.intf.FetchResultDim;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.define.IBizModelExecute;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelExecuteGather;
import com.jiuqi.bde.bizmodel.define.gather.impl.BdeBizModelGather;
import com.jiuqi.bde.bizmodel.execute.service.FetchDataResultService;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.OptionItemEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.dto.FetchDataExecuteContext;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.intf.FetchSettingCacheKey;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.LogUtil;
import com.jiuqi.bde.common.util.Pair;
import com.jiuqi.bde.log.enums.FetchDimType;
import com.jiuqi.bde.log.enums.FetchTaskType;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.base.common.utils.CompressUtil;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.core.intf.impl.BaseTaskHandler;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ComputationModelTaskHandler
extends BaseTaskHandler {
    @Autowired
    private FetchDataResultService fetchResultService;
    @Autowired
    private IBizModelExecuteGather bizModelExecuteGather;
    @Autowired
    protected IOrgMappingServiceProvider orgMappingProvider;
    @Autowired
    private BizModelService bizModelService;
    @Autowired
    private BdeBizModelGather bizModelGather;

    public String getName() {
        return FetchTaskType.NR_FETCH_BDE_COMPUT.getCode();
    }

    public String getTitle() {
        return FetchTaskType.NR_FETCH_BDE_COMPUT.getTitle();
    }

    public String getPreTask() {
        return FetchTaskType.NR_FETCH_BDE_EXECUTE.getCode();
    }

    public Map<String, String> getHandleParams(String preParam) {
        Map<String, FetchDataExecuteContext> fetchCtxMap = ComputationModelTaskHandler.readValue(preParam, new TypeReference<Map<String, FetchDataExecuteContext>>(){});
        if (fetchCtxMap.isEmpty()) {
            return CollectionUtils.newHashMap();
        }
        HashMap<String, String> handleParams = new HashMap<String, String>();
        for (Map.Entry<String, FetchDataExecuteContext> fetchCtxEntry : fetchCtxMap.entrySet()) {
            HashMap<String, FetchDataExecuteContext> handleContextMap = new HashMap<String, FetchDataExecuteContext>();
            FetchDataExecuteContext transParam = fetchCtxEntry.getValue();
            Iterator<Object> iterator = transParam.getFixedSettingList().iterator();
            while (iterator.hasNext()) {
                ExecuteSettingVO executeSettingVO;
                executeSettingVO.setOptimizeRuleGroup(StringUtils.isEmpty((String)(executeSettingVO = (ExecuteSettingVO)iterator.next()).getOptimizeRuleGroup()) ? "{}" : executeSettingVO.getOptimizeRuleGroup());
                handleContextMap.computeIfAbsent(executeSettingVO.getOptimizeRuleGroup(), key -> {
                    FetchDataExecuteContext executeContext = (FetchDataExecuteContext)BeanConvertUtil.convert((Object)transParam, FetchDataExecuteContext.class, (String[])new String[]{"fixedSettingList"});
                    executeContext.setOptimizeRuleGroup(executeSettingVO.getOptimizeRuleGroup());
                    return executeContext;
                });
                ((FetchDataExecuteContext)handleContextMap.get(executeSettingVO.getOptimizeRuleGroup())).addFixedSetting(executeSettingVO);
            }
            for (Map.Entry entry : handleContextMap.entrySet()) {
                String handleCtxCompressed = CompressUtil.compress((String)this.writeValueAsString(entry.getValue()));
                String dimCodeCompressed = this.getDimCode((FetchDataExecuteContext)entry.getValue(), (String)entry.getKey());
                handleParams.put(handleCtxCompressed, dimCodeCompressed);
            }
        }
        return handleParams;
    }

    private String getDimCode(FetchDataExecuteContext ctx, String optimCode) {
        StringBuilder dimCode = new StringBuilder(ctx.getFormId());
        dimCode.append(",");
        if (optimCode.length() > 200) {
            dimCode.append(optimCode.substring(0, 200));
            return dimCode.toString();
        }
        return dimCode.toString();
    }

    @Transactional(rollbackFor={Exception.class})
    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        Pair result;
        FetchDataExecuteContext executeContext = ComputationModelTaskHandler.readValue(CompressUtil.deCompress((String)param), FetchDataExecuteContext.class);
        FetchTaskContext context = (FetchTaskContext)BeanConvertUtil.convert((Object)executeContext, FetchTaskContext.class, (String[])new String[0]);
        FetchSettingCacheKey fetchSettingCacheKey = (FetchSettingCacheKey)BeanConvertUtil.convert((Object)executeContext, FetchSettingCacheKey.class, (String[])new String[0]);
        List fixedSettingList = executeContext.getFixedSettingList();
        IBizModelExecute bizModelExecute = this.bizModelExecuteGather.getModelExecuteByCode(executeContext.getComputationModelCode());
        IBizComputationModel computationModel = this.bizModelGather.getComputationModelByCode(executeContext.getComputationModelCode());
        this.rewriteContext(context, executeContext.getOptimizeRuleGroup());
        try {
            if (executeContext.getFloatSetting() != null && !StringUtils.isEmpty((String)executeContext.getFloatSetting().getQueryType())) {
                List<Map<String, String>> floatResults = this.getFloatResults(executeContext.getRequestTaskId(), executeContext, executeContext.getRouteNum());
                if (!CollectionUtils.isEmpty(floatResults)) {
                    Pair result2 = bizModelExecute.doFloatExecute(context, fetchSettingCacheKey, floatResults, fixedSettingList);
                    this.fetchResultService.insertFloatColResult((FetchResultDim)result2.getFirst(), (List)result2.getSecond());
                }
            } else {
                result = bizModelExecute.doFixedExecute(context, fetchSettingCacheKey, fixedSettingList);
                this.fetchResultService.insertFixedResult((FetchResultDim)result.getFirst(), (List)result.getSecond());
            }
        }
        catch (Exception e) {
            String errorMsg = e.getMessage();
            if (BdeLogUtil.isDebug()) {
                errorMsg = LogUtil.getExceptionStackStr((Throwable)e);
            }
            BdeLogUtil.forceRecordLog((String)context.getRequestTaskId(), (String)"\u6a21\u578b\u53d6\u6570\u6267\u884c\u51fa\u9519", (Object)param, (String)(BdeLogUtil.isDebug() ? errorMsg : LogUtil.getExceptionStackStr((Throwable)e)));
            throw new BusinessRuntimeException(String.format("\u3010%1$s\u3011%2$s\u53d6\u6570\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u539f\u56e0\uff1a%3$s", computationModel.getName(), this.getFieldDefineInfo(fixedSettingList), errorMsg));
        }
        result = new TaskHandleResult();
        result.setPreParam(param);
        result.appendLog(String.format("%1$s\u6a21\u578b\u53d6\u6570\u5b8c\u6210\n", computationModel.getName()));
        return result;
    }

    private String getFieldDefineInfo(List<ExecuteSettingVO> fixedSettingList) {
        if (CollectionUtils.isEmpty(fixedSettingList)) {
            return "";
        }
        ExecuteSettingVO executeSettingVO = fixedSettingList.get(0);
        if (executeSettingVO == null) {
            return "";
        }
        StringBuffer info = new StringBuffer();
        if (!StringUtils.isEmpty((String)executeSettingVO.getFieldDefineTitle())) {
            info.append("\u6307\u6807\uff1a").append("\u3010").append(executeSettingVO.getFieldDefineTitle()).append("\u3011");
        }
        return info.toString();
    }

    protected void rewriteContext(FetchTaskContext fetchTaskContext, String optimizeRuleGroup) {
        String datasourceCode;
        String orgCode;
        String bizModelCode;
        Map optimizeMap = BdeCommonUtil.parseOptimizeRuleToMap((String)optimizeRuleGroup);
        String acctYear = (String)optimizeMap.get("acctYear");
        String acctPeriod = (String)optimizeMap.get("acctPeriod");
        BdeCommonUtil.handleOptimizeDateOffset((FetchTaskContext)fetchTaskContext, (String)acctYear, (int)1);
        BdeCommonUtil.handleOptimizeDateOffset((FetchTaskContext)fetchTaskContext, (String)acctPeriod, (int)2);
        if (!StringUtils.isEmpty((String)acctYear) || !StringUtils.isEmpty((String)acctPeriod)) {
            fetchTaskContext.setStartAdjustPeriod("");
            fetchTaskContext.setEndAdjustPeriod("");
        }
        if (!StringUtils.isEmpty((String)(bizModelCode = (String)optimizeMap.get("FETCH_SOURCE_CODE")))) {
            CustomBizModelDTO customBizModel;
            BizModelDTO bizModel = this.bizModelService.get(bizModelCode);
            if (ComputationModelEnum.CUSTOMFETCH.getCode().equals(bizModel.getComputationModelCode()) && !StringUtils.isEmpty((String)(customBizModel = (CustomBizModelDTO)bizModel).getDataSourceCode())) {
                fetchTaskContext.getOrgMapping().setDataSourceCode(customBizModel.getDataSourceCode());
            }
        }
        if (!StringUtils.isEmpty((String)(orgCode = (String)optimizeMap.get("orgCode")))) {
            OrgMappingDTO orgMapping = this.orgMappingProvider.getByCode(fetchTaskContext.getBblx()).getOrgMapping(orgCode);
            fetchTaskContext.setOrgMapping(orgMapping);
            fetchTaskContext.setUnitCode(orgCode);
        }
        String string = datasourceCode = optimizeMap.get(OptionItemEnum.DATASOURCECODE.getCode()) == null ? null : optimizeMap.get(OptionItemEnum.DATASOURCECODE.getCode()).toString();
        if (!StringUtils.isEmpty(datasourceCode)) {
            fetchTaskContext.getOrgMapping().setDataSourceCode(datasourceCode);
        }
    }

    private List<Map<String, String>> getFloatResults(String requestTaskId, FetchDataExecuteContext transParam, Integer routeNum) {
        if (transParam.getFloatSetting() == null) {
            return CollectionUtils.newArrayList();
        }
        ArrayList<Map<String, String>> floatResultsList = new ArrayList<Map<String, String>>();
        List floatRowMapList = this.fetchResultService.getFloatRowResultsWithType(new FetchResultDim(requestTaskId, transParam.getFormId(), transParam.getRegionId(), routeNum));
        if (CollectionUtils.isEmpty((Collection)floatRowMapList)) {
            return floatResultsList;
        }
        for (Map floatMap : floatRowMapList) {
            HashMap map = new HashMap();
            for (Map.Entry entry : floatMap.entrySet()) {
                map.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            floatResultsList.add(map);
        }
        return floatResultsList;
    }

    public String getModule() {
        return "BDE_FETCH";
    }

    public IDimType getDimType() {
        return FetchDimType.COMPUTAT;
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.FOLLOW;
    }

    public static <T> T readValue(String json, Class<T> beanType) {
        return (T)JsonUtils.readValue((String)json, beanType);
    }

    public static <T> T readValue(String json, TypeReference<T> valueTypeRef) {
        return (T)JsonUtils.readValue((String)json, valueTypeRef);
    }

    protected String writeValueAsString(Object obj) {
        return JsonUtils.writeValueAsString((Object)obj);
    }
}

