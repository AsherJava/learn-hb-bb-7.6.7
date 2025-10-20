/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.ActionCategory
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.model.ModelException
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.feign.util.LogUtil
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.ActionCategory;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class TempSaveAction
extends BillActionBase {
    public String getName() {
        return "bill-temp-save";
    }

    public String getTitle() {
        return "\u6682\u5b58";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_zancun";
    }

    public String getActionPriority() {
        return "002";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
        Object schemeCodes = params.get("schemeCodes");
        if (!ObjectUtils.isEmpty(schemeCodes)) {
            model.getContext().setContextValue("allViews", schemeCodes);
        }
        Object tempstep = params.get("TEMPSTEP");
        DataRow master = model.getMaster();
        if (!ObjectUtils.isEmpty(tempstep) && model.getMasterTable().getFields().find("TEMPSTEP") != null) {
            master.setValue("TEMPSTEP", tempstep);
        }
        LogUtil.add((String)"\u5355\u636e", (String)"\u6682\u5b58", (String)model.getDefine().getName(), (String)model.getMaster().getString("BILLCODE"), null);
        Object billState = model.getMaster().getValue("BILLSTATE");
        if (billState != null && ((Integer)Convert.cast((Object)billState, Integer.TYPE)).intValue() != BillState.TEMPORARY.getValue()) {
            throw new ModelException(BillCoreI18nUtil.getMessage("va.billcore.tempsaveaction.unabletempsave"));
        }
        master.setValue("BILLSTATE", (Object)BillState.TEMPORARY.getValue());
        model.getData().save();
    }

    @Override
    public String[] getModelParams() {
        return BillConsts.ACTION_PARAM_EDIT;
    }

    public ActionCategory getActionCategory() {
        return ActionCategory.SAVE;
    }
}

