/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.option.OptionDO
 *  com.jiuqi.va.domain.task.OptionRegisterTask
 */
package com.jiuqi.va.bill.task;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.option.OptionDO;
import com.jiuqi.va.domain.task.OptionRegisterTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BillRuleOptionRegisterTask
implements OptionRegisterTask {
    public List<OptionDO> getOptions() {
        ArrayList<OptionDO> list = new ArrayList<OptionDO>();
        HashMap<String, Object> mainParam = new HashMap<String, Object>();
        mainParam.put("groupname", "billRule");
        mainParam.put("listApi", "/api/bill/rule/option/list");
        mainParam.put("updateApi", "/api/bill/rule/option/update");
        mainParam.put("controlModel", true);
        mainParam.put("orgCategory", "MD_ORG");
        list.add(this.initOption("BillRuleOption", "\u5355\u636e\u89c4\u5219\u63a7\u5236\u53c2\u6570", "/api/bill/anon/bill-rule-option.js", "BillRuleOption", "BillRuleOptionMgr", JSONUtil.toJSONString(mainParam), 7));
        return list;
    }
}

