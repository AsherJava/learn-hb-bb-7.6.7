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
public class BillOptionRegisterTask
implements OptionRegisterTask {
    public List<OptionDO> getOptions() {
        ArrayList<OptionDO> list = new ArrayList<OptionDO>();
        HashMap<String, String> mainParam = new HashMap<String, String>();
        mainParam.put("listApi", "/api/bill/option/list");
        mainParam.put("updateApi", "/api/bill/option/update");
        list.add(this.initOption("VaBillOption", "\u5355\u636e\u8054\u67e5\u53c2\u6570", null, "VaOptionManage", "VaOptionList", JSONUtil.toJSONString(mainParam), 1));
        return list;
    }
}

