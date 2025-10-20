/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.option.OptionDO
 *  com.jiuqi.va.domain.task.OptionRegisterTask
 */
package com.jiuqi.va.workflow.task;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.option.OptionDO;
import com.jiuqi.va.domain.task.OptionRegisterTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowOptionRegisterTask
implements OptionRegisterTask {
    public List<OptionDO> getOptions() {
        ArrayList<OptionDO> list = new ArrayList<OptionDO>();
        HashMap<String, String> mainParam = new HashMap<String, String>();
        mainParam.put("groupname", "workflow");
        mainParam.put("listApi", "/api/workflow/option/list");
        mainParam.put("updateApi", "/api/workflow/option/update");
        list.add(this.initOption("VaWorkflowOption", "\u5de5\u4f5c\u6d41\u63a7\u5236\u53c2\u6570", "/api/workflow/anon/workflow-option.js", "VaWorkflowOption", "VaWorkflowOptionMgr", JSONUtil.toJSONString(mainParam), 7));
        return list;
    }
}

