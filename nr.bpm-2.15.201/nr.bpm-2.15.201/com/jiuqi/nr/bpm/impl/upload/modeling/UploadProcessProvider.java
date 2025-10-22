/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 */
package com.jiuqi.nr.bpm.impl.upload.modeling;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.ProcessProvider;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.upload.modeling.DefaultProcessBuilder;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadProcessProvider
implements ProcessProvider {
    @Autowired
    NrParameterUtils nrParameterUtils;

    @Override
    public String getProcessDefinitionKey(String businessKeyStr) {
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(businessKeyStr);
        TaskFlowsDefine flowsDefine = this.nrParameterUtils.getFlowsDefine(businessKey.getFormSchemeKey());
        if (flowsDefine.getFlowsType() != FlowsType.DEFAULT) {
            return null;
        }
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        return DefaultProcessBuilder.generateDefaultProcessId(formScheme.getFormSchemeCode());
    }
}

