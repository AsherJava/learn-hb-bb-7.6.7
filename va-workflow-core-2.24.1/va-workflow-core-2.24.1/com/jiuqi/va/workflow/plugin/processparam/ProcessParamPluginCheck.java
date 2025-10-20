/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.domain.PluginCheckResultDTO
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 *  com.jiuqi.va.biz.domain.PluginCheckType
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginCheck
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.domain.workflow.ValueType
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.workflow.plugin.processparam;

import com.jiuqi.va.biz.domain.PluginCheckResultDTO;
import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.biz.domain.PluginCheckType;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginCheck;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.domain.workflow.ValueType;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.workflow.domain.WorkflowOption;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;
import com.jiuqi.va.workflow.service.WorkflowPublicParamService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ProcessParamPluginCheck
implements PluginCheck {
    public String getName() {
        return "processParamPlugin";
    }

    public Class<? extends PluginDefine> getPluginDefine() {
        return ProcessParamPluginDefine.class;
    }

    public PluginCheckResultVO checkPlugin(PluginDefine pluginDefine, ModelDefine modelDefine) {
        PluginCheckResultVO pluginCheckResultVO = new PluginCheckResultVO();
        pluginCheckResultVO.setPluginName("processParamPlugin");
        ProcessParamPluginDefine processParamPluginDefine = (ProcessParamPluginDefine)pluginDefine;
        ArrayList<PluginCheckResultDTO> checkResults = new ArrayList<PluginCheckResultDTO>();
        WorkflowPublicParamService publicParamService = (WorkflowPublicParamService)ApplicationContextRegister.getBean(WorkflowPublicParamService.class);
        WorkflowPublicParamDTO publicParamDTO = new WorkflowPublicParamDTO();
        publicParamDTO.setSearchtitle(false);
        publicParamDTO.setSearchdata(false);
        List<WorkflowPublicParamDTO> publicParamList = publicParamService.list(publicParamDTO);
        List<ProcessParam> processParamList = processParamPluginDefine.getProcessParam();
        List publicParamNames = processParamList.stream().filter(p -> WorkflowOption.WorkflowParamType.CUSTOM.getType().equals(p.getOwnershipNature()) || p.getOwnershipNature() == null).map(ProcessParam::getParamName).collect(Collectors.toList());
        publicParamList.removeIf(p -> publicParamNames.contains(p.getParamname()));
        List processPublicParamNames = processParamList.stream().filter(processParam -> WorkflowOption.WorkflowParamType.PUBLIC.getType().equals(processParam.getOwnershipNature())).map(ProcessParam::getParamName).collect(Collectors.toList());
        Set paramNameSet = publicParamList.stream().map(WorkflowPublicParamDO::getParamname).filter(paramname -> !processPublicParamNames.contains(paramname)).collect(Collectors.toSet());
        HashSet<String> paramTitleSet = new HashSet<String>();
        int size = processParamPluginDefine.getProcessParam().size();
        for (int i = 0; i < size; ++i) {
            PluginCheckResultDTO checkResultDTO;
            int index;
            ProcessParam processParam2 = processParamPluginDefine.getProcessParam().get(i);
            String paramName = processParam2.getParamName();
            String paramTitle = processParam2.getParamTitle();
            ValueType paramType = processParam2.getParamType();
            int n = index = processParam2.getIndex() == null ? i : processParam2.getIndex();
            if (paramType == null) {
                checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u53c2\u6570\u8bbe\u7f6e\u7b2c[" + (index + 1) + "]\u884c\u53c2\u6570\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a:" + paramName, paramName);
                checkResults.add(checkResultDTO);
            }
            if (!StringUtils.hasText(paramName)) {
                checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u53c2\u6570\u8bbe\u7f6e\u7b2c[" + (index + 1) + "]\u884c\u53c2\u6570\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a:" + paramTitle, "");
                checkResults.add(checkResultDTO);
            } else if (!paramNameSet.add(paramName)) {
                checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u53c2\u6570\u8bbe\u7f6e\u7b2c[" + (index + 1) + "]\u884c\u53c2\u6570\u6807\u8bc6\u91cd\u590d:" + paramName, paramName);
                checkResults.add(checkResultDTO);
            }
            if (!StringUtils.hasText(paramTitle)) {
                checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u53c2\u6570\u8bbe\u7f6e\u7b2c[" + (index + 1) + "]\u884c\u53c2\u6570\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a:" + paramName, paramName);
                checkResults.add(checkResultDTO);
                continue;
            }
            if (paramTitleSet.add(paramTitle)) continue;
            checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.WARN, "\u53c2\u6570\u8bbe\u7f6e\u7b2c[" + (index + 1) + "]\u884c\u53c2\u6570\u540d\u79f0\u91cd\u590d:" + paramTitle, paramName);
            checkResults.add(checkResultDTO);
        }
        pluginCheckResultVO.setCheckResults(checkResults);
        return pluginCheckResultVO;
    }

    private PluginCheckResultDTO getPluginCheckResultDTO(PluginCheckType checkType, String message, String objectPath) {
        PluginCheckResultDTO pluginCheckResultDTO = new PluginCheckResultDTO();
        pluginCheckResultDTO.setObjectpath(objectPath);
        pluginCheckResultDTO.setType(checkType);
        pluginCheckResultDTO.setMessage(message);
        return pluginCheckResultDTO;
    }
}

