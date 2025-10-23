/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.ActionEventRegisteration
 */
package com.jiuqi.nr.workflow2.events.spring.config;

import com.jiuqi.nr.workflow2.engine.core.event.ActionEventRegisteration;
import com.jiuqi.nr.workflow2.events.CheckUnitNodeEvent;
import com.jiuqi.nr.workflow2.events.CompleteCalculationEvent;
import com.jiuqi.nr.workflow2.events.CompleteReviewEvent;
import com.jiuqi.nr.workflow2.events.MadeDataSnapshotVersionEvent;
import com.jiuqi.nr.workflow2.events.SendMessageNoticeEvent;
import com.jiuqi.nr.workflow2.events.StepByStepApplyRejectEvent;
import com.jiuqi.nr.workflow2.events.StepByStepCancelConfirmEvent;
import com.jiuqi.nr.workflow2.events.StepByStepRejectEvent;
import com.jiuqi.nr.workflow2.events.StepByStepRetrieveRejectEvent;
import com.jiuqi.nr.workflow2.events.StepByStepUploadEvent;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class Workflow2EventBeanConfig {
    @Bean(value={"com.jiuqi.nr.workflow2.events.CompleteCalculationEvent"})
    public ActionEventRegisteration getCompleteCalculationEvent() {
        return new CompleteCalculationEvent();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.events.CheckUnitNodeEvent"})
    public ActionEventRegisteration getCheckUnitNodeEvent() {
        return new CheckUnitNodeEvent();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.events.CompleteReviewEvent"})
    public ActionEventRegisteration getCompleteReviewEvent() {
        return new CompleteReviewEvent();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.events.MadeDataSnapshotVersionEvent"})
    public ActionEventRegisteration getMadeDataSnapshotVersionEvent() {
        return new MadeDataSnapshotVersionEvent();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.events.SendMessageNoticeEvent"})
    public ActionEventRegisteration getSendMessageNoticeEvent() {
        return new SendMessageNoticeEvent();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.events.StepByStepUploadEvent"})
    public ActionEventRegisteration getStepByStepUploadEvent() {
        return new StepByStepUploadEvent();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.events.StepByStepRejectEvent"})
    public ActionEventRegisteration getStepByStepRejectEvent() {
        return new StepByStepRejectEvent();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.events.StepByStepApplyRejectEvent"})
    public ActionEventRegisteration getStepByStepApplyRejectEvent() {
        return new StepByStepApplyRejectEvent();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.events.StepByStepRetrieveRejectEvent"})
    public StepByStepRetrieveRejectEvent getStepByStepRetrieveRejectEvent() {
        return new StepByStepRetrieveRejectEvent();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.events.StepByStepCancelConfirmEvent"})
    public StepByStepCancelConfirmEvent getStepByStepCancelConfirmEvent() {
        return new StepByStepCancelConfirmEvent();
    }
}

