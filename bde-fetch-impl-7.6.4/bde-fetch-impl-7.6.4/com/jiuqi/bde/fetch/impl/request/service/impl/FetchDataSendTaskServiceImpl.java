/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataRequestDTO
 *  com.jiuqi.bde.bizmodel.execute.model.custommade.model.CustomFetchComputationModel
 *  com.jiuqi.bde.bizmodel.impl.model.service.BizModelService
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.BizModelExtFieldInfo
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.dto.FetchDataExecuteContext
 *  com.jiuqi.bde.common.dto.FetchSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.bde.fetch.impl.request.service.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataRequestDTO;
import com.jiuqi.bde.bizmodel.execute.model.custommade.model.CustomFetchComputationModel;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.BizModelExtFieldInfo;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.dto.FetchDataExecuteContext;
import com.jiuqi.bde.common.dto.FetchSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO;
import com.jiuqi.bde.fetch.impl.request.service.FetchDataSendTaskService;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchDataSendTaskServiceImpl
implements FetchDataSendTaskService {
    private final Logger logger = LoggerFactory.getLogger(FetchDataSendTaskServiceImpl.class);
    @Autowired
    private BizModelService bizModelService;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private CustomFetchComputationModel computationModel;

    @Override
    public void sendFetchTask(FetchDataRequestDTO fetchRequestDTO) {
        Map<String, FetchDataExecuteContext> executeContextMap = this.buildFetchCtxMap(fetchRequestDTO);
        TaskHandleMsg handleMsg = (TaskHandleMsg)ThreadContext.get((Object)"TASKHANDLEMSG_KEY");
        String taskItemId = handleMsg == null ? fetchRequestDTO.getRequestTaskId() : handleMsg.getTaskItemId();
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        TaskHandleMsg taskHandleMsg = null;
        for (Map.Entry<String, FetchDataExecuteContext> handleMessageEntry : executeContextMap.entrySet()) {
            taskHandleMsg = new TaskHandleMsg(handleMessageEntry.getKey(), null, fetchRequestDTO.getRequestInstcId(), taskItemId, JsonUtils.writeValueAsString((Object)handleMessageEntry.getValue()), 0, fetchRequestDTO.getRequestRunnerId());
            taskHandlerClient.startSubTask(handleMessageEntry.getKey(), taskHandleMsg);
        }
    }

    @Override
    public Map<String, FetchDataExecuteContext> buildFetchCtxMap(FetchDataRequestDTO fetchRequestDTO) {
        Map<String, BizModelDTO> bizModelMap = this.bizModelService.list().stream().collect(Collectors.toMap(BizModelDTO::getCode, b -> b));
        HashMap<String, FetchDataExecuteContext> executeContextMap = new HashMap<String, FetchDataExecuteContext>();
        for (FetchRequestFixedSettingDTO fixedSettingDTO : fetchRequestDTO.getFixedSetting()) {
            for (Map.Entry bizModelFormula : fixedSettingDTO.getBizModelFormula().entrySet()) {
                List fixedFetchSourceRowSettings;
                FinBizModelDTO finBizModelDTO;
                String bizModelCode = (String)bizModelFormula.getKey();
                if (!bizModelMap.containsKey(bizModelCode)) {
                    this.logger.error("\u6839\u636e\u4e1a\u52a1\u6a21\u578b\u4ee3\u7801{}\u672a\u627e\u5230\u4e1a\u52a1\u6a21\u578b\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u662f\u5426\u88ab\u5220\u9664", (Object)bizModelCode);
                    continue;
                }
                BizModelDTO bizModelDTO = bizModelMap.get(bizModelCode);
                String computationModelCode = bizModelDTO.getComputationModelCode();
                BizModelExtFieldInfo bizModelExtFieldInfo = null;
                String executeContextKey = !(bizModelDTO instanceof FinBizModelDTO) ? computationModelCode : ((bizModelExtFieldInfo = (finBizModelDTO = (FinBizModelDTO)bizModelDTO).getBizModelExtFieldInfo()) != null && finBizModelDTO.getBizModelExtFieldInfo().getId() != null ? computationModelCode + "_" + finBizModelDTO.getBizModelExtFieldInfo().getId() : computationModelCode);
                if (!executeContextMap.containsKey(executeContextKey)) {
                    FetchDataExecuteContext executeContext = (FetchDataExecuteContext)BeanConvertUtil.convert((Object)fetchRequestDTO.getFetchContext(), FetchDataExecuteContext.class, (String[])new String[0]);
                    BeanConvertUtil.copyProperties((Object)fetchRequestDTO, (Object)executeContext);
                    BeanConvertUtil.copyProperties((Object)fixedSettingDTO, (Object)executeContext);
                    executeContext.setComputationModelCode(computationModelCode);
                    executeContext.setOrgMapping(fetchRequestDTO.getOrgMapping());
                    executeContext.setRouteNum(fetchRequestDTO.getRouteNum());
                    executeContextMap.put(executeContextKey, executeContext);
                }
                if (CollectionUtils.isEmpty((Collection)(fixedFetchSourceRowSettings = (List)bizModelFormula.getValue()))) continue;
                for (FixedFetchSourceRowSettingVO rowSetting : fixedFetchSourceRowSettings) {
                    ExecuteSettingVO fixedSetting = (ExecuteSettingVO)BeanConvertUtil.convert((Object)rowSetting, ExecuteSettingVO.class, (String[])new String[0]);
                    fixedSetting.setFetchSourceCode(bizModelCode);
                    fixedSetting.setFieldDefineId(StringUtils.isEmpty((String)fixedSettingDTO.getFieldDefineId()) ? UUIDUtils.emptyUUIDStr() : fixedSettingDTO.getFieldDefineId());
                    fixedSetting.setFieldDefineTitle(fixedSettingDTO.getFieldDefineTitle());
                    fixedSetting.setFieldDefineType(fixedSettingDTO.getFieldDefineType());
                    if (ComputationModelEnum.CUSTOMFETCH.getCode().equals(computationModelCode)) {
                        fixedSetting.setOptimizeRuleGroup(this.computationModel.getOptimizeRuleGroup((FetchSettingVO)fixedSetting));
                    }
                    ((FetchDataExecuteContext)executeContextMap.get(executeContextKey)).addFixedSetting(fixedSetting);
                }
                ((FetchDataExecuteContext)executeContextMap.get(executeContextKey)).setBizModelExtFieldInfo(bizModelExtFieldInfo);
            }
        }
        return executeContextMap;
    }
}

