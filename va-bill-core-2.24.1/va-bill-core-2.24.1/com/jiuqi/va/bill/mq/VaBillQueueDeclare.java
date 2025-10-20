/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.join.api.common.JoinTemplate
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent
 *  com.jiuqi.va.trans.service.VaBizErrorService
 */
package com.jiuqi.va.bill.mq;

import com.jiuqi.va.bill.mq.BillAfterQueueDeclare;
import com.jiuqi.va.bill.mq.listener.BillCommitAfterQueueListener;
import com.jiuqi.va.bill.mq.listener.BillCommpleteAfterQueueListener;
import com.jiuqi.va.bill.mq.listener.BillRejectAfterQueueListener;
import com.jiuqi.va.bill.mq.listener.BillRetractAfterQueueListener;
import com.jiuqi.va.bill.mq.listener.BillStateUpdateQueueListener;
import com.jiuqi.va.bill.mq.listener.VaBillQueueMsgHandler;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.join.api.common.JoinTemplate;
import com.jiuqi.va.join.api.domain.JoinDeclare;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent;
import com.jiuqi.va.trans.service.VaBizErrorService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class VaBillQueueDeclare
implements ApplicationListener<StorageSyncFinishedEvent> {
    @Value(value="${va.trans.queue.listener.maxcount:10}")
    public int maxConsumer;
    @Autowired
    private ModelManager modelManager;
    @Autowired
    private JoinTemplate joinTemplate;
    @Autowired
    private VaBillQueueMsgHandler vaBillQueueMsgHandler;
    @Autowired
    private VaBizErrorService vaBizErrorService;

    @Override
    public void onApplicationEvent(StorageSyncFinishedEvent event) {
        Set bizmodules;
        if (!this.joinTemplate.isRunning()) {
            return;
        }
        List modelList = this.modelManager.getModelList("bill");
        if (!CollectionUtils.isEmpty(modelList) && !CollectionUtils.isEmpty(bizmodules = modelList.stream().map(ModelType::getBizModule).collect(Collectors.toSet()))) {
            for (String bizmodule : bizmodules) {
                if (!StringUtils.hasText(bizmodule)) continue;
                String billStateUpdateQueueName = "VA_BILL_STATE_UPDATE_" + bizmodule;
                String billCommitAfterQueueName = "VA_BILL_COMMIT_AFTER_" + bizmodule;
                String billCompleteAfterQueueName = "VA_BILL_COMPLETE_AFTER_" + bizmodule;
                String billrejectAfterQueueName = "VA_BILL_REJECT_AFTER_" + bizmodule;
                String billretractAfterQueueName = "VA_BILL_RETRACT_AFTER_" + bizmodule;
                this.joinTemplate.addDeclare((JoinDeclare)new BillAfterQueueDeclare(billStateUpdateQueueName, "\u66f4\u65b0\u5355\u636e\u72b6\u6001\u961f\u5217"));
                this.joinTemplate.addDeclare((JoinDeclare)new BillAfterQueueDeclare(billCommitAfterQueueName, "\u5355\u636e\u63d0\u4ea4\u540e\u6267\u884c\u961f\u5217"));
                this.joinTemplate.addDeclare((JoinDeclare)new BillAfterQueueDeclare(billCompleteAfterQueueName, "\u5355\u636e\u5ba1\u6279\u540e\u6267\u884c\u961f\u5217"));
                this.joinTemplate.addDeclare((JoinDeclare)new BillAfterQueueDeclare(billrejectAfterQueueName, "\u5355\u636e\u9a73\u56de\u540e\u6267\u884c\u961f\u5217"));
                this.joinTemplate.addDeclare((JoinDeclare)new BillAfterQueueDeclare(billretractAfterQueueName, "\u5355\u636e\u53d6\u56de\u540e\u6267\u884c\u961f\u5217"));
                this.joinTemplate.addListener((JoinListener)new BillStateUpdateQueueListener(billStateUpdateQueueName, this.vaBillQueueMsgHandler, this.vaBizErrorService));
                this.joinTemplate.addListener((JoinListener)new BillCommitAfterQueueListener(billCommitAfterQueueName, this.maxConsumer, this.vaBillQueueMsgHandler, this.vaBizErrorService));
                this.joinTemplate.addListener((JoinListener)new BillCommpleteAfterQueueListener(billCompleteAfterQueueName, this.maxConsumer, this.vaBillQueueMsgHandler, this.vaBizErrorService));
                this.joinTemplate.addListener((JoinListener)new BillRejectAfterQueueListener(billrejectAfterQueueName, this.vaBillQueueMsgHandler, this.vaBizErrorService));
                this.joinTemplate.addListener((JoinListener)new BillRetractAfterQueueListener(billretractAfterQueueName, this.vaBillQueueMsgHandler, this.vaBizErrorService));
            }
        }
    }
}

