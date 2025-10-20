/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.action.SaveAction
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.biz.intf.model.ModelContext
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.join.api.domain.ReplyStatus
 *  com.jiuqi.va.join.api.domain.ReplyTo
 *  com.jiuqi.va.trans.service.VaBizErrorService
 */
package com.jiuqi.va.bill.bd.bill.mq;

import com.jiuqi.va.bill.action.SaveAction;
import com.jiuqi.va.bill.bd.bill.domain.CreateBillEntry;
import com.jiuqi.va.bill.bd.bill.model.RegistrationBillModel;
import com.jiuqi.va.bill.bd.bill.service.MaintainBillService;
import com.jiuqi.va.bill.bd.bill.service.WriteBackService;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.bill.bd.core.service.MaintainBillExceptionService;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.trans.service.VaBizErrorService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteBillQueueRecerver
implements JoinListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteBillQueueRecerver.class);
    @Autowired
    MaintainBillService billBdService;
    @Autowired
    SaveAction saveAction;
    @Autowired
    ModelDefineService modelDefineService;
    @Autowired
    MaintainBillExceptionService exceptionHanderService;
    @Autowired
    WriteBackService writeBackService;
    @Autowired
    private VaBizErrorService vaBizErrorService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public R deleteBill(Map<String, Object> param) {
        ShiroUtil.unbindUser();
        String messageId = null;
        String globMsgId = null;
        try {
            RegistrationBillModel regBillModal;
            String tenantName = param.get("SECURITY_TENANT_KEY").toString();
            ShiroUtil.ignoreApiAuth();
            ShiroUtil.bindTenantName((String)tenantName);
            UserLoginDTO user = (UserLoginDTO)JSONUtil.parseObject((String)param.get("loginUser").toString(), UserLoginDTO.class);
            ShiroUtil.bindUser((UserLoginDTO)user);
            messageId = param.get("msgId").toString();
            globMsgId = String.valueOf(param.get("globMsgId"));
            Map content = JSONUtil.parseMap((String)param.get("content").toString());
            Object body = content.get("body");
            CreateBillEntry cbEntry = body instanceof CreateBillEntry ? (CreateBillEntry)body : (CreateBillEntry)JSONUtil.parseObject((String)JSONUtil.toJSONString(body), CreateBillEntry.class);
            ApplyRegMapDO define = cbEntry.getDefine();
            BillContextImpl context = new BillContextImpl();
            context.setDisableVerify(true);
            context.setTenantName(tenantName);
            try {
                regBillModal = (RegistrationBillModel)this.modelDefineService.createModel((ModelContext)context, define.getBilldefinecode());
                regBillModal.getRuler().getRulerExecutor().setEnable(true);
            }
            catch (Exception e) {
                LOGGER.error("\u52a0\u8f7d\u76ee\u6807\u5355\u636e\u5b9a\u4e49\u5931\u8d25:{}", (Object)e.getMessage(), (Object)e);
                this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
                R r = R.error((String)"\u52a0\u8f7d\u76ee\u6807\u5355\u636e\u5b9a\u4e49\u5931\u8d25");
                ShiroUtil.unbindTenantName();
                ShiroUtil.resetApiAuth();
                ShiroUtil.unbindUser();
                return r;
            }
            regBillModal.loadByCode(cbEntry.getTargetBillCode());
            regBillModal.setDeleteFlagField(define.getDeleteflagname());
            regBillModal.setMsgId(messageId);
            regBillModal.edit();
            regBillModal.save();
            R r = R.ok();
            return r;
        }
        catch (Exception e) {
            LOGGER.error("\u5355\u636e\u751f\u6210\u57fa\u7840\u6570\u636e\u5220\u9664\u6d88\u8d39\u5931\u8d25{}", (Object)e.getMessage(), (Object)e);
            this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
            R r = R.error((String)e.getMessage());
            return r;
        }
        finally {
            ShiroUtil.unbindTenantName();
            ShiroUtil.resetApiAuth();
            ShiroUtil.unbindUser();
        }
    }

    public String getJoinName() {
        return "VA_BILL_BD_DELETEEBILL";
    }

    public ReplyTo onMessage(String message) {
        try {
            Map map = JSONUtil.parseMap((String)message);
            R deleteBill = this.deleteBill(map);
            return new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)deleteBill));
        }
        catch (Exception e) {
            LOGGER.error("\u76d1\u542c\u5230\u751f\u5355\u6d88\u606f\u5904\u7406\u5931\u8d25{}", (Object)e.getMessage(), (Object)e);
            return new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)R.error((String)e.getMessage())));
        }
    }
}

