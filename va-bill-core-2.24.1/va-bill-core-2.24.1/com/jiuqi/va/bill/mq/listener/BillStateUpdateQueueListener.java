/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.trans.service.VaBizErrorService
 */
package com.jiuqi.va.bill.mq.listener;

import com.jiuqi.va.bill.mq.listener.AbstractBillAfterQueueListener;
import com.jiuqi.va.bill.mq.listener.VaBillQueueMsgHandler;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.trans.service.VaBizErrorService;
import java.util.Map;

public class BillStateUpdateQueueListener
extends AbstractBillAfterQueueListener {
    @Override
    protected void doMessage(Map<String, Object> param) {
        this.vaBillQueueMsgHandler.updateBillState(param);
    }

    @Override
    protected String getErrorMessage() {
        return BillCoreI18nUtil.getMessage("va.billcore.billqueuereceiver.updatestatefailed");
    }

    public BillStateUpdateQueueListener() {
    }

    public BillStateUpdateQueueListener(String joinName, VaBillQueueMsgHandler vaBillQueueMsgHandler, VaBizErrorService vaBizErrorService) {
        this.joinName = joinName;
        this.vaBillQueueMsgHandler = vaBillQueueMsgHandler;
        this.vaBizErrorService = vaBizErrorService;
    }
}

