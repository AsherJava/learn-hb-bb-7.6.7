/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.option.OptionDO
 *  com.jiuqi.va.domain.task.OptionRegisterTask
 */
package com.jiuqi.va.bizmeta.task;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.option.OptionDO;
import com.jiuqi.va.domain.task.OptionRegisterTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaBizMetaOptionRegisterTask
implements OptionRegisterTask {
    public List<OptionDO> getOptions() {
        ArrayList<OptionDO> list = new ArrayList<OptionDO>();
        HashMap<String, String> mainParam = new HashMap<String, String>();
        mainParam.put("groupname", "meta");
        mainParam.put("listApi", "/api/biz/meta/option/list");
        mainParam.put("updateApi", "/api/biz/meta/option/update");
        list.add(this.initOption("VaMetaOption", "\u5143\u6570\u636e\u63a7\u5236\u53c2\u6570", null, "VaOptionManage", "VaOptionList", JSONUtil.toJSONString(mainParam), 9));
        return list;
    }
}

