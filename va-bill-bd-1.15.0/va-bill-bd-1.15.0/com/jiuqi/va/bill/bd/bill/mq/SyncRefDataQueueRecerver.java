/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.join.api.domain.ReplyStatus
 *  com.jiuqi.va.join.api.domain.ReplyTo
 *  com.jiuqi.va.trans.service.VaBizErrorService
 *  com.jiuqi.va.trans.service.VaTransMessageService
 */
package com.jiuqi.va.bill.bd.bill.mq;

import com.jiuqi.va.bill.bd.bill.domain.CreateBillEntry;
import com.jiuqi.va.bill.bd.bill.impl.BillToMasterHandle;
import com.jiuqi.va.bill.bd.bill.service.MaintainBillService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.trans.service.VaBizErrorService;
import com.jiuqi.va.trans.service.VaTransMessageService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SyncRefDataQueueRecerver
implements JoinListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncRefDataQueueRecerver.class);
    @Autowired
    MaintainBillService billBdService;
    @Autowired
    private VaBizErrorService vaBizErrorService;
    @Autowired
    private VaTransMessageService vaTransMessageService;
    @Autowired
    private BillToMasterHandle billToMasterHandle;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public R syncRefData(Map<String, Object> param) {
        ShiroUtil.unbindUser();
        String messageId = null;
        String globMsgId = null;
        try {
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
            if (cbEntry.getDelData() != null) {
                this.billToMasterHandle.delMasterData(cbEntry.getDelData(), cbEntry.getDefineName());
            } else {
                this.billToMasterHandle.syncMasterData(cbEntry.getSyncData(), cbEntry.getDefineName());
            }
            try {
                if (StringUtils.hasText(messageId)) {
                    this.vaTransMessageService.doneTransMessage(messageId, null);
                }
                if (StringUtils.hasText(cbEntry.getMsgId())) {
                    this.vaTransMessageService.doneTransMessage(cbEntry.getMsgId(), null);
                }
            }
            catch (Exception e) {
                throw new BillException("\u66f4\u65b0\u4e8b\u52a1\u5931\u8d25:" + e.getMessage(), (Throwable)e);
            }
            R r = R.ok();
            return r;
        }
        catch (Exception e) {
            LOGGER.error("\u540c\u6b65\u57fa\u7840\u6570\u636e\u6d88\u8d39\u5904\u7406\u5931\u8d25{}", (Object)e.getMessage(), (Object)e);
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
        return "VA_BILL_BD_SYNCREFDATA";
    }

    public ReplyTo onMessage(String message) {
        try {
            Map map = JSONUtil.parseMap((String)message);
            R deleteBill = this.syncRefData(map);
            return new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)deleteBill));
        }
        catch (Exception e) {
            LOGGER.error("\u76d1\u542c\u5230\u540c\u6b65\u5f15\u7528\u6570\u636e\u5904\u7406\u5931\u8d25{}", (Object)e.getMessage(), (Object)e);
            return new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)R.error((String)e.getMessage())));
        }
    }
}

