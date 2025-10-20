/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.message.template.VaMessageTemplateRegisterTask
 *  com.jiuqi.va.message.template.domain.VaMessageTemplateFunctionDO
 */
package com.jiuqi.va.workflow.task;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.message.template.VaMessageTemplateRegisterTask;
import com.jiuqi.va.message.template.domain.VaMessageTemplateFunctionDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowMesssageTemplateRegisterTask
implements VaMessageTemplateRegisterTask {
    public List<VaMessageTemplateFunctionDO> getTemplateFunctions() {
        ArrayList<VaMessageTemplateFunctionDO> list = new ArrayList<VaMessageTemplateFunctionDO>();
        HashMap<String, String> mainParam = new HashMap<String, String>();
        mainParam.put("urlPrefix", "workflow");
        list.add(this.initTemplateFunction("VAWORKFLOW", "\u5de5\u4f5c\u6d41", JSONUtil.toJSONString(mainParam), 1));
        return list;
    }
}

