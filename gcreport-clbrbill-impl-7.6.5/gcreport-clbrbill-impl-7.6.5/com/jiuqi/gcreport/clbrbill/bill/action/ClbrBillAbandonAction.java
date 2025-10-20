/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.clbrbill.enums.ClbrStatesEnum
 *  com.jiuqi.va.bill.impl.BillActionBase
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillConsts
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.gcreport.clbrbill.bill.action;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.clbrbill.enums.ClbrStatesEnum;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ClbrBillAbandonAction
extends BillActionBase {
    private final Logger logger = LoggerFactory.getLogger(ClbrBillAbandonAction.class);

    public String getName() {
        return "clbrbill-abandon";
    }

    public String getTitle() {
        return "\u4f5c\u5e9f\u534f\u540c\u5355";
    }

    public String getDesc() {
        return "\u4f5c\u5e9f\u534f\u540c\u5355\u3002";
    }

    public String getIcon() {
        return null;
    }

    public void execute(BillModel model, Map<String, Object> params) {
        String billcode = (String)model.getMaster().getValue("BILLCODE", String.class);
        if (StringUtils.isEmpty((String)billcode)) {
            throw new BusinessRuntimeException("\u5f53\u524d\u534f\u540c\u5355\u534f\u540c\u7801\u4e3a\u7a7a\uff0c\u4e0d\u5141\u8bb8\u4f5c\u5e9f\u3002");
        }
        if (ClbrStatesEnum.ABANDON.name().equals(model.getMaster().getValue("CLBRSTATE", String.class))) {
            throw new BusinessRuntimeException("\u5f53\u524d\u534f\u540c\u5355\u5df2\u4f5c\u5e9f\uff0c\u4e0d\u5141\u8bb8\u91cd\u590d\u4f5c\u5e9f\u3002");
        }
        String userName = ShiroUtil.getUser().getUsername();
        this.logger.info("\u7528\u6237\uff1a{}\uff0c\u4f5c\u5e9f[{}]\u534f\u540c\u5355\u5f00\u59cb\u6267\u884c", (Object)userName, (Object)billcode);
        BillModelImpl billModel = (BillModelImpl)model;
        billModel.getContext().setContextValue("disableVerify", (Object)true);
        billModel.getData().edit();
        billModel.getMaster().setValue("CLBRSTATE", (Object)ClbrStatesEnum.ABANDON.name());
        billModel.getMaster().setValue("ABANDONUSER", (Object)userName);
        billModel.getMaster().setValue("ABANDONTIME", (Object)new Date());
        try {
            billModel.save();
        }
        catch (Exception e) {
            this.logger.error("\u7528\u6237\uff1a{}\uff0c\u4f5c\u5e9f[{}]\u534f\u540c\u5355\u6267\u884c\u5931\u8d25\u3002\u8be6\u60c5\uff1a{}", userName, billcode, e.getMessage(), e);
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        this.logger.info("\u7528\u6237\uff1a{}\uff0c\u4f5c\u5e9f[{}]\u534f\u540c\u5355\u6267\u884c\u5b8c\u6210\u3002", (Object)userName, (Object)billcode);
    }

    public String[] getModelParams() {
        return BillConsts.ACTION_PARAM_DEFINE;
    }

    public String getActionPriority() {
        return "000";
    }

    public Class<? extends BillModel> getDependModel() {
        return BillModelImpl.class;
    }
}

