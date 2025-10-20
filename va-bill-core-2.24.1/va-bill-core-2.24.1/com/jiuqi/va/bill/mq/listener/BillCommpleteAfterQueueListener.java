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

public class BillCommpleteAfterQueueListener
extends AbstractBillAfterQueueListener {
    @Override
    protected void doMessage(Map<String, Object> param) {
        this.vaBillQueueMsgHandler.billCompleteAfter(param);
    }

    @Override
    protected String getErrorMessage() {
        return BillCoreI18nUtil.getMessage("va.billcore.billqueuereceiver.auditactionfailed");
    }

    public BillCommpleteAfterQueueListener() {
    }

    public BillCommpleteAfterQueueListener(String joinName, int maxConsumers, VaBillQueueMsgHandler vaBillQueueMsgHandler, VaBizErrorService vaBizErrorService) {
        this.joinName = joinName;
        this.maxConsumers = maxConsumers;
        this.vaBillQueueMsgHandler = vaBillQueueMsgHandler;
        this.vaBizErrorService = vaBizErrorService;
    }

    public int getMaxConsumers() {
        return this.maxConsumers;
    }
}

