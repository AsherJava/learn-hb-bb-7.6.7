/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.message.template.VaMessageTemplateRegisterTask
 *  com.jiuqi.va.message.template.domain.VaMessageTemplateFunctionDO
 */
package com.jiuqi.va.bill.task;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.message.template.VaMessageTemplateRegisterTask;
import com.jiuqi.va.message.template.domain.VaMessageTemplateFunctionDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BillNoticeMesssageTemplateRegisterTask
implements VaMessageTemplateRegisterTask {
    public List<VaMessageTemplateFunctionDO> getTemplateFunctions() {
        ArrayList<VaMessageTemplateFunctionDO> list = new ArrayList<VaMessageTemplateFunctionDO>();
        HashMap<String, String> mainParam = new HashMap<String, String>();
        mainParam.put("urlPrefix", "bill");
        list.add(this.initTemplateFunction("VABILLNOTICE", "\u5355\u636e\u6d88\u606f\u901a\u77e5", JSONUtil.toJSONString(mainParam), 4));
        return list;
    }
}

