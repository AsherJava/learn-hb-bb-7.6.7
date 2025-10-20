/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.ruler.impl.RulerImpl
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.trans.domain.VaTransMessageDTO
 *  com.jiuqi.va.trans.service.VaTransMessageService
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.trans.domain.VaTransMessageDTO;
import com.jiuqi.va.trans.service.VaTransMessageService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DeleteAction
extends BillActionBase {
    @Autowired
    private VaTransMessageService vaTransMessageService;
    @Autowired
    private TodoClient todoClient;

    public String getName() {
        return "bill-delete";
    }

    public String getTitle() {
        return "\u5220\u9664";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_shanchu";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
        String billCode = (String)model.getMaster().getValue("BILLCODE", String.class);
        LogUtil.add((String)"\u5355\u636e", (String)"\u5220\u9664", (String)model.getDefine().getName(), (String)billCode, null);
        int billstate = model.getMaster().getInt("BILLSTATE");
        if (billstate == BillState.TEMPORARY.getValue()) {
            model.getData().delete();
        } else {
            List checkMessages = ((RulerImpl)model.getRuler()).getRulerExecutor().beforeAction("delete");
            if (checkMessages != null && checkMessages.size() > 0) {
                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.deletefailed"), checkMessages);
            }
            VaTransMessageDTO vaTransMessageDTO = new VaTransMessageDTO();
            vaTransMessageDTO.setBizcode(billCode);
            vaTransMessageDTO.setStatus(Integer.valueOf(0));
            vaTransMessageDTO.setFilterRoot(true);
            List transMsgs = this.vaTransMessageService.listTransMessage(vaTransMessageDTO);
            if (!CollectionUtils.isEmpty(transMsgs)) {
                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.workflowaction.processing"));
            }
            model.delete();
            if (billstate == BillState.SENDBACK.getValue()) {
                TenantDO tenantDO = new TenantDO();
                tenantDO.addExtInfo("BIZCODE", (Object)billCode);
                this.todoClient.deleteTask(tenantDO);
            }
        }
    }

    public String getActionPriority() {
        return "004";
    }
}

