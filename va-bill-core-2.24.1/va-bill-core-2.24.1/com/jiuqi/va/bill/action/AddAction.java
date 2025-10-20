/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.feign.util.LogUtil
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AddAction
extends BillActionBase {
    public String getName() {
        return "bill-add";
    }

    public String getTitle() {
        return "\u65b0\u5efa";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_xinjian";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
        if (params != null) {
            model.add(params);
        } else {
            model.add();
        }
        LogUtil.add((String)"\u5355\u636e", (String)"\u65b0\u5efa", (String)model.getDefine().getName(), (String)model.getMaster().getString("BILLCODE"), null);
    }

    @Override
    public String[] getModelParams() {
        return BillConsts.ACTION_PARAM_DEFINE;
    }

    public String getActionPriority() {
        return "000";
    }
}

