/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.base.common.env.EnvCenter
 *  com.jiuqi.dc.base.common.utils.AsyncCallBackUtil
 *  com.jiuqi.dc.integration.execute.client.dto.ConvertExecuteDTO
 *  com.jiuqi.dc.integration.execute.client.dto.ConvertRefDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.ModelTypeEnum
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService
 *  com.jiuqi.np.log.LogHelper
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.integration.execute.impl.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.base.common.env.EnvCenter;
import com.jiuqi.dc.base.common.utils.AsyncCallBackUtil;
import com.jiuqi.dc.integration.execute.client.dto.ConvertExecuteDTO;
import com.jiuqi.dc.integration.execute.client.dto.ConvertRefDefineDTO;
import com.jiuqi.dc.integration.execute.impl.domain.ConvertLogDO;
import com.jiuqi.dc.integration.execute.impl.intf.IBizDataConvertHandler;
import com.jiuqi.dc.integration.execute.impl.intf.IBizDataConvertHandlerGather;
import com.jiuqi.dc.integration.execute.impl.mq.BaseDataExecuteParam;
import com.jiuqi.dc.integration.execute.impl.mq.BizDataExecuteParam;
import com.jiuqi.dc.integration.execute.impl.service.ConvertLogService;
import com.jiuqi.dc.integration.execute.impl.service.ConvertService;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.ModelTypeEnum;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import com.jiuqi.np.log.LogHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConvertServiceImpl
implements ConvertService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BaseDataRefDefineService baseDataDefineService;
    @Autowired
    private BizDataRefDefineService bizDataDefineService;
    @Autowired
    private ConvertLogService convertLogservice;
    @Autowired
    private TaskLogService taskLogService;
    @Autowired
    private IBizDataConvertHandlerGather gather;
    @Autowired
    private IPluginTypeGather pluginTypeGather;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Map<String, String> convert(ConvertExecuteDTO executeParam, Boolean automaticTaskFlag) {
        this.beforeConvert(executeParam);
        String baseDataConvertLogId = this.convertBaseData(executeParam, automaticTaskFlag);
        Map<String, String> resultMap = this.convertBizData(executeParam, automaticTaskFlag);
        if (!StringUtils.isEmpty((String)baseDataConvertLogId)) {
            resultMap.put("BaseDataConvert", baseDataConvertLogId);
        }
        return resultMap;
    }

    private void beforeConvert(ConvertExecuteDTO executeParam) {
        Assert.isNotNull((Object)executeParam);
        Assert.isNotNull((Object)executeParam.getDataScheme(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)executeParam.getDataScheme().getCode(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)executeParam.getDataScheme().getPluginType(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u63d2\u4ef6\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        if (CollectionUtils.isEmpty((Collection)executeParam.getBaseDataRefs()) && CollectionUtils.isEmpty((Collection)executeParam.getBizDataRefs())) {
            throw new IllegalArgumentException("\u57fa\u7840\u6570\u636e\u8303\u56f4\u548c\u4e1a\u52a1\u6570\u636e\u8303\u56f4\u4e0d\u80fd\u540c\u65f6\u4e3a\u7a7a\uff0c\u8bf7\u81f3\u5c11\u9009\u62e9\u5176\u4e00");
        }
    }

    private String convertBaseData(ConvertExecuteDTO executeParam, Boolean automaticTaskFlag) {
        if (CollectionUtils.isEmpty((Collection)executeParam.getBaseDataRefs())) {
            return null;
        }
        ArrayList<BaseDataExecuteParam> convertParams = new ArrayList<BaseDataExecuteParam>();
        BaseDataMappingDefineDTO define = null;
        StringJoiner operationLog = new StringJoiner(",");
        int index = 0;
        for (ConvertRefDefineDTO bizDataRef : executeParam.getBaseDataRefs()) {
            Assert.isNotNull((Object)bizDataRef.getCode(), (String)"\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            define = this.baseDataDefineService.findByCode(executeParam.getDataScheme().getCode(), bizDataRef.getCode());
            if (define == null) {
                this.logger.error("\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u3010%1$s\u3011\u4e0d\u5b58\u5728", (Object)bizDataRef.getCode());
                continue;
            }
            convertParams.add(new BaseDataExecuteParam(executeParam.getDataScheme().getCode(), define.getCode(), define.getName()));
            if (index++ >= 5) continue;
            operationLog.add(define.getName());
        }
        String convertDataName = operationLog.toString() + (index < 5 ? "" : "\u7b49" + index + "\u4e2a\u57fa\u7840\u6570\u636e");
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        String baseDataConvertParam = JsonUtils.writeValueAsString(convertParams);
        if (automaticTaskFlag.booleanValue()) {
            String logId = (String)taskHandlerClient.startTask("BaseDataConvert", baseDataConvertParam).getData();
            AsyncCallBackUtil.asyncCall(() -> {
                taskHandlerClient.waitTaskFinished(logId);
                this.taskLogService.updateTaskResult(logId, "\u57fa\u7840\u6570\u636e\u8f6c\u6362\u5b8c\u6210");
            });
            return logId;
        }
        ConvertLogDO convertLogDO = new ConvertLogDO(UUIDUtils.newHalfGUIDStr(), executeParam.getDataScheme().getCode(), ModelTypeEnum.BASEDATA.getCode(), convertDataName, null, EnvCenter.getContextUserName(), DataHandleState.EXECUTING.getState(), new Date());
        this.convertLogservice.insertLog(convertLogDO);
        AsyncCallBackUtil.asyncCall(() -> {
            String logId = null;
            boolean success = true;
            try {
                logId = (String)taskHandlerClient.startTask("BaseDataConvert", baseDataConvertParam).getData();
                this.convertLogservice.updateRunnerIdById(convertLogDO.getId(), logId);
            }
            catch (Exception e) {
                success = false;
                this.convertLogservice.updateMessageAndState(convertLogDO.getId(), e.getMessage(), DataHandleState.FAILURE.getState());
            }
            taskHandlerClient.waitTaskFinished(logId);
            this.taskLogService.updateTaskResult(logId, "\u57fa\u7840\u6570\u636e\u8f6c\u6362\u5b8c\u6210");
            if (success) {
                this.convertLogservice.updateExecuteById(convertLogDO.getId());
            }
        });
        return convertLogDO.getId();
    }

    private Map<String, String> convertBizData(ConvertExecuteDTO executeParam, Boolean automaticTaskFlag) {
        if (CollectionUtils.isEmpty((Collection)executeParam.getBizDataRefs())) {
            return CollectionUtils.newHashMap();
        }
        HashMap resultMap = CollectionUtils.newHashMap();
        ArrayList<ConvertLogDO> convertLogList = new ArrayList<ConvertLogDO>(executeParam.getBizDataRefs().size());
        String operationLog = executeParam.getBizDataRefs().stream().map(ConvertRefDefineDTO::getName).collect(Collectors.joining("+"));
        LogHelper.info((String)DcFunctionModuleEnum.DATAINTEGRATION.getFullModuleName(), (String)("\u6267\u884c-\u4e1a\u52a1\u6570\u636e-" + operationLog), (String)JsonUtils.writeValueAsString((Object)executeParam));
        for (ConvertRefDefineDTO bizDataRef : executeParam.getBizDataRefs()) {
            Assert.isNotNull((Object)bizDataRef.getCode(), (String)"\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            Assert.isNotNull((Object)bizDataRef.getName(), (String)String.format("\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u3010%1$s\u3011\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a", bizDataRef.getCode()), (Object[])new Object[0]);
            DataMappingDefineDTO define = this.bizDataDefineService.findByCode(executeParam.getDataScheme().getCode(), bizDataRef.getCode());
            if (define == null) continue;
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
            String message = JsonUtils.writeValueAsString((Object)new BizDataExecuteParam(null, executeParam.getDataScheme().getCode(), bizDataRef.getCode(), bizDataRef.getParam()));
            if (automaticTaskFlag.booleanValue()) {
                String logId = (String)taskHandlerClient.startTask(define.getCode(), message).getData();
                if (StringUtils.isEmpty((String)logId)) continue;
                resultMap.put(bizDataRef.getCode(), logId);
                AsyncCallBackUtil.asyncCall(() -> {
                    taskHandlerClient.waitTaskFinished(logId);
                    this.taskLogService.updateTaskResult(logId, define.getName() + "\u8f6c\u6362\u5b8c\u6210");
                });
                continue;
            }
            ConvertLogDO convertLog = new ConvertLogDO(UUIDUtils.newHalfGUIDStr(), executeParam.getDataScheme().getCode(), ModelTypeEnum.BIZDATA.getCode(), bizDataRef.getName(), null, EnvCenter.getContextUserName(), DataHandleState.EXECUTING.getState(), new Date());
            this.convertLogservice.insertLog(convertLog);
            convertLogList.add(convertLog);
            resultMap.put(bizDataRef.getCode(), convertLog.getId());
            AsyncCallBackUtil.asyncCall(() -> {
                String logId = null;
                boolean success = true;
                try {
                    logId = (String)taskHandlerClient.startTask(define.getCode(), message).getData();
                    this.convertLogservice.updateRunnerIdById(convertLog.getId(), logId);
                }
                catch (Exception e) {
                    success = false;
                    this.convertLogservice.updateMessageAndState(convertLog.getId(), e.getMessage(), DataHandleState.FAILURE.getState());
                }
                if (!StringUtils.isEmpty((String)logId)) {
                    taskHandlerClient.waitTaskFinished(logId);
                    this.taskLogService.updateTaskResult(logId, define.getName() + "\u8f6c\u6362\u5b8c\u6210");
                }
                if (success) {
                    this.convertLogservice.updateExecuteById(convertLog.getId());
                }
            });
        }
        return resultMap;
    }

    @Override
    public String getSettingTemplate(DataMappingDefineDTO dataMappingDefineDTO) {
        String fetchDataType = Optional.ofNullable(dataMappingDefineDTO.getDataSchemeCode()).map(e -> this.dataSchemeService.getByCode(e)).map(DataSchemeDTO::getSourceDataType).orElse("");
        IBizDataConvertHandler handler = this.gather.getHandler(this.pluginTypeGather.getPluginType(dataMappingDefineDTO.getPluginType()), dataMappingDefineDTO.getCode(), fetchDataType);
        return Optional.ofNullable(handler).map(e -> e.getSettingTemplate(dataMappingDefineDTO.getDataSchemeCode())).orElse(null);
    }
}

