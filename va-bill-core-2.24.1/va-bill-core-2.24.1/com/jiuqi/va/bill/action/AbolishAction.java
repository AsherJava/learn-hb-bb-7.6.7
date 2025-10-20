/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.BillSyncDataClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
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
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.BillSyncDataClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.trans.domain.VaTransMessageDTO;
import com.jiuqi.va.trans.service.VaTransMessageService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AbolishAction
extends BillActionBase {
    private static final Logger logger = LoggerFactory.getLogger(AbolishAction.class);
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private VaTransMessageService vaTransMessageService;

    public String getName() {
        return "bill-abolish";
    }

    public String getTitle() {
        return "\u5e9f\u6b62";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_feizhi";
    }

    public String getActionPriority() {
        return "023";
    }

    @Override
    public void execute(BillModel model, Map<String, Object> params) {
        String billCode = (String)model.getMaster().getValue("BILLCODE", String.class);
        LogUtil.add((String)"\u5355\u636e", (String)"\u5e9f\u6b62", (String)model.getDefine().getName(), (String)billCode, null);
        VaTransMessageDTO vaTransMessageDTO = new VaTransMessageDTO();
        vaTransMessageDTO.setBizcode(billCode);
        vaTransMessageDTO.setStatus(Integer.valueOf(0));
        vaTransMessageDTO.setFilterRoot(true);
        List transMsgs = this.vaTransMessageService.listTransMessage(vaTransMessageDTO);
        if (!CollectionUtils.isEmpty(transMsgs)) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.workflowaction.processing"));
        }
        model.getData().edit();
        model.getMaster().setValue("BILLSTATE", (Object)BillState.DELETED);
        DataField abuser = (DataField)model.getMasterTable().getFields().find("ABOLISHUSER");
        DataField abtime = (DataField)model.getMasterTable().getFields().find("ABOLISHTIME");
        if (abuser != null && abtime != null) {
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                model.getMaster().setValue("ABOLISHUSER", (Object)user.getId());
            }
            model.getMaster().setValue("ABOLISHTIME", (Object)System.currentTimeMillis());
        }
        model.getData().save();
        DataFieldDefine bizsource = (DataFieldDefine)model.getMasterTable().getDefine().getFields().find("BIZSOURCE");
        if (bizsource == null) {
            return;
        }
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("BIZCODE", (Object)billCode);
        tenantDO.setTraceId(Utils.getTraceId());
        R r = this.todoClient.deleteTask(tenantDO);
        if (r.getCode() == 1) {
            logger.error(r.getMsg());
        }
        if (!"02".equals(model.getMaster().getString("BIZSOURCE"))) {
            return;
        }
        BillSyncDataClient fssBillClient = (BillSyncDataClient)ApplicationContextRegister.getBean(BillSyncDataClient.class);
        HashMap<String, Object> billMsg = new HashMap<String, Object>();
        billMsg.put("BILLID", model.getMaster().getString("ID"));
        billMsg.put("BIZSOURCE", model.getMaster().getString("BIZSOURCE"));
        billMsg.put("DEFINECODE", model.getDefine().getName());
        billMsg.put("BILLCODE", model.getMaster().getString("BILLCODE"));
        billMsg.put("FEEDBACKENUMCODE", 8);
        billMsg.put("FEEDBACKENUMDESC", "\u5e9f\u6b62");
        billMsg.put("OPERATERESULT", "\u5355\u636e\u5e9f\u6b62\u6210\u529f");
        TenantDO tenantMagDO = new TenantDO();
        tenantMagDO.addExtInfo("FEEDBACKMSG", (Object)JSONUtil.toJSONString(billMsg));
        R sendFeedbackR = fssBillClient.sendFeedbackMsg(tenantMagDO);
        if (sendFeedbackR.getCode() == 1) {
            logger.error(r.getMsg());
        }
    }
}

