/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataException
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.constants.BizState
 *  com.jiuqi.va.domain.workflow.NodeRejectType
 *  com.jiuqi.va.domain.workflow.retract.RetractTypeEnum
 *  com.jiuqi.va.feign.client.BillSyncDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.trans.service.VaTransMessageService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.bill.mq.listener;

import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.intf.data.DataException;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.constants.BizState;
import com.jiuqi.va.domain.workflow.NodeRejectType;
import com.jiuqi.va.domain.workflow.retract.RetractTypeEnum;
import com.jiuqi.va.feign.client.BillSyncDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.trans.service.VaTransMessageService;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class VaBillQueueMsgHandler {
    private static final Logger log = LoggerFactory.getLogger(VaBillQueueMsgHandler.class);
    @Autowired
    private VaTransMessageService vaTransMessageService;
    @Autowired
    private BillDefineService billDefineService;

    @Transactional(rollbackFor={Exception.class})
    public void updateBillState(Map<String, Object> param) {
        String messageId = param.get("msgId").toString();
        String content = param.get("content").toString();
        Map contentMap = JSONUtil.parseMap((String)content);
        String defineCode = BillUtils.valueToString(contentMap.get("bizDefine"));
        String billCode = BillUtils.valueToString(contentMap.get("bizCode"));
        int billState = (Integer)contentMap.get("updateBillState");
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setDisableVerify(true);
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)contextImpl, defineCode);
        model.loadByCode(billCode);
        for (BillState billstateEnum : BillState.values()) {
            if (billstateEnum.getValue() != billState) continue;
            this.updateBillState(billstateEnum, billCode, model);
        }
        this.vaTransMessageService.doneTransMessage(messageId, null);
    }

    @Transactional(rollbackFor={Exception.class})
    public void billCommitAfter(Map<String, Object> param) {
        String messageId = param.get("msgId").toString();
        String content = param.get("content").toString();
        Map contentMap = JSONUtil.parseMap((String)content);
        int processStatus = (Integer)contentMap.get("processStatus");
        String defineCode = BillUtils.valueToString(contentMap.get("bizDefine"));
        String billCode = BillUtils.valueToString(contentMap.get("bizCode"));
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setContextValue("action", "bill-commit");
        contextImpl.setDisableVerify(true);
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)contextImpl, defineCode);
        model.loadByCode(billCode);
        if (1 == processStatus) {
            this.updateBillState(BillState.AUDITPASSED, billCode, model);
            model.afterApproval();
        } else {
            this.updateBill(BillState.COMMITTED, false, billCode, model);
        }
        this.vaTransMessageService.doneTransMessage(messageId, null);
    }

    @Transactional(rollbackFor={Exception.class})
    public void billCompleteAfter(Map<String, Object> param) {
        String messageId = param.get("msgId").toString();
        String content = param.get("content").toString();
        Map contentMap = JSONUtil.parseMap((String)content);
        Object processStatus = contentMap.get("PROCESSSTATUS");
        String defineCode = BillUtils.valueToString(contentMap.get("bizDefine"));
        String billCode = BillUtils.valueToString(contentMap.get("bizCode"));
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setContextValue("action", "bill-complete");
        contextImpl.setDisableVerify(true);
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)contextImpl, defineCode);
        model.loadByCode(billCode);
        if (processStatus == null || 0 == (Integer)processStatus) {
            this.updateBillState(BillState.AUDITING, billCode, model);
        } else {
            this.updateBillState(BillState.AUDITPASSED, billCode, model);
            model.afterApproval();
        }
        this.vaTransMessageService.doneTransMessage(messageId, null);
    }

    @Transactional(rollbackFor={Exception.class})
    public void billRejectAfter(Map<String, Object> param) {
        DataFieldDefine bizsource;
        String messageId = param.get("msgId").toString();
        String content = param.get("content").toString();
        Map contentMap = JSONUtil.parseMap((String)content);
        Object processStatus = contentMap.get("PROCESSSTATUS");
        String defineCode = BillUtils.valueToString(contentMap.get("bizDefine"));
        String billCode = BillUtils.valueToString(contentMap.get("bizCode"));
        int rejectType = (Integer)contentMap.get("rejectType");
        Object gotoLastReject = contentMap.get("gotoLastReject");
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setContextValue("action", "bill-reject");
        contextImpl.setDisableVerify(true);
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)contextImpl, defineCode);
        model.loadByCode(billCode);
        if (processStatus != null && (Integer)processStatus == 1) {
            if (rejectType == NodeRejectType.REJECT_TERMINATE.getValue()) {
                this.updateBillState(BillState.REJECT, billCode, model);
            } else if (gotoLastReject != null && ((Boolean)gotoLastReject).booleanValue()) {
                this.updateBill(BillState.SENDBACK, true, billCode, model);
            } else {
                this.updateBillState(BillState.SENDBACK, billCode, model);
            }
        }
        if (contentMap.containsKey("subProcessRejectSubmit") && ((Boolean)contentMap.get("subProcessRejectSubmit")).booleanValue()) {
            this.updateBillState(BillState.SENDBACK, billCode, model);
        }
        if ((bizsource = (DataFieldDefine)model.getMasterTable().getDefine().getFields().find("BIZSOURCE")) != null && "02".equals(model.getMaster().getString("BIZSOURCE"))) {
            BillSyncDataClient fssBillClient = (BillSyncDataClient)ApplicationContextRegister.getBean(BillSyncDataClient.class);
            HashMap<String, Object> billMsg = new HashMap<String, Object>();
            billMsg.put("BILLID", model.getMaster().getString("ID"));
            billMsg.put("BIZSOURCE", model.getMaster().getString("BIZSOURCE"));
            billMsg.put("DEFINECODE", model.getDefine().getName());
            billMsg.put("BILLCODE", model.getMaster().getString("BILLCODE"));
            billMsg.put("FEEDBACKENUMCODE", 6);
            billMsg.put("FEEDBACKENUMDESC", "\u5de5\u4f5c\u6d41\u9a73\u56de");
            billMsg.put("OPERATERESULT", BillUtils.valueToString(contentMap.get("approvalComment")));
            TenantDO tenantMagDO = new TenantDO();
            tenantMagDO.addExtInfo("FEEDBACKMSG", (Object)JSONUtil.toJSONString(billMsg));
            fssBillClient.sendFeedbackMsg(tenantMagDO);
        }
        this.vaTransMessageService.doneTransMessage(messageId, null);
    }

    @Transactional(rollbackFor={Exception.class})
    public void billRetractAfter(Map<String, Object> param) {
        String messageId = param.get("msgId").toString();
        String content = param.get("content").toString();
        Map contentMap = JSONUtil.parseMap((String)content);
        Object commitedFlag = contentMap.get("commitedFlag");
        Object hasRejectRecord = contentMap.get("hasRejectRecord");
        Object processStatus = contentMap.get("processStatus");
        String defineCode = BillUtils.valueToString(contentMap.get("bizDefine"));
        String billCode = BillUtils.valueToString(contentMap.get("bizCode"));
        String RetractType = BillUtils.valueToString(contentMap.get("RetractType"));
        String changedBizState = BillUtils.valueToString(contentMap.get("changedBizState"));
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setContextValue("action", "bill-retract");
        contextImpl.setDisableVerify(true);
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)contextImpl, defineCode);
        model.loadByCode(billCode);
        if (processStatus != null && (Integer)processStatus == 4) {
            if (hasRejectRecord != null && ((Boolean)hasRejectRecord).booleanValue()) {
                Object gotoLastReject = contentMap.get("gotoLastReject");
                if (gotoLastReject != null && ((Boolean)gotoLastReject).booleanValue()) {
                    this.updateBill(BillState.SENDBACK, true, billCode, model);
                } else {
                    this.updateBillState(BillState.SENDBACK, billCode, model);
                }
            } else {
                this.updateBillState(BillState.SAVED, billCode, model);
            }
        } else if (commitedFlag != null && ((Boolean)commitedFlag).booleanValue()) {
            this.updateBillState(BillState.COMMITTED, billCode, model);
        } else if (Objects.equals(RetractType, RetractTypeEnum.RETRACT_REJECT.name())) {
            if (Objects.equals(changedBizState, BizState.COMMITTED.name())) {
                this.updateBillState(BillState.COMMITTED, billCode, model);
            } else if (Objects.equals(changedBizState, BizState.AUDITING.name())) {
                this.updateBillState(BillState.AUDITING, billCode, model);
            }
        }
        if (contentMap.containsKey("subProcessRetractSubmit") && ((Boolean)contentMap.get("subProcessRetractSubmit")).booleanValue()) {
            this.updateBillState(BillState.SENDBACK, billCode, model);
        }
        this.vaTransMessageService.doneTransMessage(messageId, null);
    }

    protected void updateBillState(BillState billstate, String billcode, BillModel model) {
        try {
            model.getData().edit();
            model.getMaster().setValue("BILLSTATE", (Object)billstate);
            model.getData().save();
        }
        catch (DataException e) {
            log.error("{}\u4fee\u6539\u5355\u636e\u72b6\u6001\u5931\u8d25", (Object)billcode, (Object)e);
            if ("\u6570\u636e\u5df2\u53d1\u751f\u53d8\u5316".equals(e.getMessage())) {
                for (int i = 0; i < 10; ++i) {
                    try {
                        model.loadByCode(billcode);
                        model.getData().edit();
                        model.getMaster().setValue("BILLSTATE", (Object)billstate);
                        model.getData().save();
                        return;
                    }
                    catch (DataException e1) {
                        if (!"\u6570\u636e\u5df2\u53d1\u751f\u53d8\u5316".equals(e1.getMessage())) continue;
                        try {
                            Thread.sleep(200L);
                        }
                        catch (InterruptedException e2) {
                            log.error(e2.getMessage(), e2);
                            Thread.currentThread().interrupt();
                        }
                        continue;
                    }
                }
                throw new RuntimeException(e.getMessage(), e);
            }
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected void updateBill(BillState billstate, boolean gotoLastReject, String billcode, BillModel model) {
        DataFieldDefine gotoLastRejectField = (DataFieldDefine)model.getMasterTable().getDefine().getFields().find("GOTOLASTREJECT");
        try {
            model.getData().edit();
            model.getMaster().setValue("BILLSTATE", (Object)billstate);
            if (gotoLastRejectField != null) {
                if (gotoLastReject) {
                    model.getMaster().setValue("GOTOLASTREJECT", (Object)1);
                } else {
                    model.getMaster().setValue("GOTOLASTREJECT", null);
                }
            }
            model.getData().save();
        }
        catch (DataException e) {
            log.error("{}\u4fee\u6539\u5355\u636e\u72b6\u6001\u5931\u8d25", (Object)billcode, (Object)e);
            if ("\u6570\u636e\u5df2\u53d1\u751f\u53d8\u5316".equals(e.getMessage())) {
                for (int i = 0; i < 10; ++i) {
                    try {
                        model.loadByCode(billcode);
                        model.getData().edit();
                        model.getMaster().setValue("BILLSTATE", (Object)billstate);
                        if (gotoLastReject && gotoLastRejectField != null) {
                            model.getMaster().setValue("GOTOLASTREJECT", (Object)1);
                        }
                        model.getData().save();
                        return;
                    }
                    catch (DataException e1) {
                        if (!"\u6570\u636e\u5df2\u53d1\u751f\u53d8\u5316".equals(e1.getMessage())) continue;
                        try {
                            Thread.sleep(200L);
                        }
                        catch (InterruptedException e2) {
                            log.error(e2.getMessage(), e2);
                            Thread.currentThread().interrupt();
                        }
                        continue;
                    }
                }
                throw new RuntimeException(e.getMessage(), e);
            }
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

