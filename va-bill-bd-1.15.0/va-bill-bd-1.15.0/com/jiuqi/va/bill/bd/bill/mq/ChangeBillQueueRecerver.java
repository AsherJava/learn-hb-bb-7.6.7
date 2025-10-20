/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.action.SaveAction
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.intf.BillState
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.data.DataRow
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
import com.jiuqi.va.bill.bd.bill.domain.MapInfoDTO;
import com.jiuqi.va.bill.bd.bill.model.RegistrationBillModel;
import com.jiuqi.va.bill.bd.bill.service.MaintainBillService;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.bill.bd.core.service.MaintainBillExceptionService;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.DataRow;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChangeBillQueueRecerver
implements JoinListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeBillQueueRecerver.class);
    @Autowired
    MaintainBillService billBdService;
    @Autowired
    ModelDefineService modelDefineService;
    @Autowired
    MaintainBillExceptionService exceptionHanderService;
    @Autowired
    private VaBizErrorService vaBizErrorService;
    @Autowired
    private SaveAction saveAction;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public R ChangeBill(Map<String, Object> param) {
        ShiroUtil.unbindUser();
        RegistrationBillModel regBillModal = null;
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
            ApplyRegMapDO define = cbEntry.getDefine();
            BillContextImpl context = new BillContextImpl();
            context.setDisableVerify(true);
            context.setTenantName(tenantName);
            try {
                regBillModal = (RegistrationBillModel)this.modelDefineService.createModel((ModelContext)context, define.getBilldefinecode());
                regBillModal.getRuler().getRulerExecutor().setEnable(true);
            }
            catch (Exception e) {
                this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
                R r = R.error((String)("\u52a0\u8f7d\u76ee\u6807\u5355\u636e\u5b9a\u4e49\u5931\u8d25:" + e.getMessage()));
                if (regBillModal != null) {
                    regBillModal.getRuler().getRulerExecutor().setEnable(false);
                }
                ShiroUtil.unbindTenantName();
                ShiroUtil.resetApiAuth();
                ShiroUtil.unbindUser();
                return r;
            }
            if (define.getCreatetype() == 2 || define.getCreatetype() == 4) {
                Map applyTablesData = (Map)content.get("tableData");
                try {
                    MapInfoDTO mapInfoDTO = (MapInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)JSONUtil.parseArray((String)define.getMapinfos()).get(0)), MapInfoDTO.class);
                    regBillModal.loadByCode(((Map)((List)applyTablesData.get(mapInfoDTO.getSrctablename())).get(0)).get(define.getWritebackname()).toString());
                }
                catch (Exception e) {
                    LOGGER.error("\u52a0\u8f7d\u76ee\u6807\u5355\u636e\u6570\u636e\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u6620\u5c04\u914d\u7f6e\u7684\u53d8\u66f4\u4f9d\u636e\u5b57\u6bb5\u662f\u5426\u6709\u503c" + e.getMessage(), e);
                    this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
                    R r = R.error((String)("\u52a0\u8f7d\u76ee\u6807\u5355\u636e\u6570\u636e\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u6620\u5c04\u914d\u7f6e\u7684\u53d8\u66f4\u4f9d\u636e\u5b57\u6bb5\u662f\u5426\u6709\u503c" + e.getMessage()));
                    if (regBillModal != null) {
                        regBillModal.getRuler().getRulerExecutor().setEnable(false);
                    }
                    ShiroUtil.unbindTenantName();
                    ShiroUtil.resetApiAuth();
                    ShiroUtil.unbindUser();
                    return r;
                }
                DataRow srcBillMasterData = regBillModal.getMaster();
                srcBillMasterData.setValue("BILLSTATE", (Object)BillState.AUDITPASSEDCANEDIT);
                regBillModal.edit();
                List mapinfos = JSONUtil.parseMapArray((String)define.getMapinfos());
                for (Map stringObjectMap : mapinfos) {
                    MapInfoDTO mapinfo = (MapInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)stringObjectMap), MapInfoDTO.class);
                    this.billBdService.changeBillByMatser(mapinfo, regBillModal, applyTablesData);
                }
                try {
                    regBillModal.setMsgId(messageId);
                    ActionRequest actionRequest = new ActionRequest();
                    actionRequest.setParams(new HashMap());
                    regBillModal.executeAction((Action)this.saveAction, actionRequest, new ActionResponse());
                }
                catch (Exception e) {
                    LOGGER.error("\u4e3b\u8868\u53d8\u66f4\u5931\u8d25\uff0c\u76ee\u6807\u5355\u636e\u4fdd\u5b58\u5931\u8d25:" + e.getMessage(), e);
                    this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
                    R r = R.error((String)("\u4e3b\u8868\u53d8\u66f4\u5931\u8d25\uff0c\u76ee\u6807\u5355\u636e\u4fdd\u5b58\u5931\u8d25:" + e.getMessage()));
                    if (regBillModal != null) {
                        regBillModal.getRuler().getRulerExecutor().setEnable(false);
                    }
                    ShiroUtil.unbindTenantName();
                    ShiroUtil.resetApiAuth();
                    ShiroUtil.unbindUser();
                    return r;
                }
            }
            if (define.getCreatetype() == 3 || define.getCreatetype() == 5) {
                this.billBdService.changeBillByDetail(regBillModal, messageId, cbEntry, cbEntry.getApplyitemValue());
            }
            R r = R.ok();
            return r;
        }
        catch (Exception e) {
            LOGGER.error("\u53d8\u66f4\u8fc7\u7a0b\u51fa\u73b0\u5f02\u5e38\uff0c\u6d88\u8d39\u5931\u8d25" + e.getMessage(), e);
            this.vaBizErrorService.insertBizError(globMsgId, messageId, (Throwable)e);
            R r = R.error((String)("\u53d8\u66f4\u8fc7\u7a0b\u51fa\u73b0\u5f02\u5e38\uff0c\u6d88\u8d39\u5931\u8d25" + e.getMessage()));
            return r;
        }
        finally {
            if (regBillModal != null) {
                regBillModal.getRuler().getRulerExecutor().setEnable(false);
            }
            ShiroUtil.unbindTenantName();
            ShiroUtil.resetApiAuth();
            ShiroUtil.unbindUser();
        }
    }

    public String getJoinName() {
        return "VA_BILL_BD_CHANGEBILL";
    }

    public ReplyTo onMessage(String message) {
        try {
            Map map = JSONUtil.parseMap((String)message);
            R r = this.ChangeBill(map);
            return new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)r));
        }
        catch (Exception e) {
            LOGGER.error("\u76d1\u542c\u5230\u53d8\u66f4\u6d88\u606f\u5904\u7406\u5931\u8d25" + e.getMessage(), e);
            return new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)R.error((String)e.getMessage())));
        }
    }
}

