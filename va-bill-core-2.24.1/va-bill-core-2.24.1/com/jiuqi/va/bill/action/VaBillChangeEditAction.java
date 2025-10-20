/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.trans.domain.VaTransMessageDTO
 *  com.jiuqi.va.trans.service.VaTransMessageService
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.VerifyUtils;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.trans.domain.VaTransMessageDTO;
import com.jiuqi.va.trans.service.VaTransMessageService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class VaBillChangeEditAction
extends BillActionBase {
    @Autowired
    private VaTransMessageService vaTransMessageService;

    public String getName() {
        return "va-billChange-edit";
    }

    public String getTitle() {
        return "\u5355\u636e\u53d8\u66f4\u7f16\u8f91";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_xiugai";
    }

    public boolean isInner() {
        return true;
    }

    public String getActionPriority() {
        return "999";
    }

    @Override
    public Object executeReturn(BillModel model, Map<String, Object> params) {
        BillModelImpl billModel = (BillModelImpl)model;
        this.handleEdit(billModel);
        return null;
    }

    private void handleEdit(BillModelImpl billModel) {
        DataRow master = billModel.getMaster();
        LogUtil.add((String)"\u5355\u636e", (String)"\u8d85\u7ea7\u4fee\u6539", (String)billModel.getDefine().getName(), (String)master.getString("BILLCODE"), null);
        String billCode = (String)master.getValue("BILLCODE", String.class);
        VaTransMessageDTO vaTransMessageDTO = new VaTransMessageDTO();
        vaTransMessageDTO.setBizcode(billCode);
        vaTransMessageDTO.setStatus(Integer.valueOf(0));
        vaTransMessageDTO.setFilterRoot(true);
        List transMsgs = this.vaTransMessageService.listTransMessage(vaTransMessageDTO);
        if (!CollectionUtils.isEmpty(transMsgs)) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.workflowaction.processing"));
        }
        this.checkAdmin(billModel.getContext());
        VerifyUtils.verifyBill(billModel, 2);
        billModel.getData().edit();
    }

    private void checkAdmin(BillContext context) {
        UserLoginDTO user;
        String triggerOrigin = context.getTriggerOrigin();
        if (StringUtils.hasText(triggerOrigin) && (user = ShiroUtil.getUser()) != null && "super".equalsIgnoreCase(user.getMgrFlag())) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.bill.core.admin.no.permission"));
        }
    }
}

