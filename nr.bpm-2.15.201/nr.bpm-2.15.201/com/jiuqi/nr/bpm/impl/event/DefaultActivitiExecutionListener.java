/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  org.activiti.engine.delegate.DelegateExecution
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.event.ActivitiExecutionEndEvent;
import com.jiuqi.nr.bpm.event.ActivitiExecutionEventListener;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.process.dao.ProcessStateHistoryDao;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultActivitiExecutionListener
implements ActivitiExecutionEventListener {
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    ProcessStateHistoryDao processStateHistoryDao;

    @Override
    public void onStart(ActivitiExecutionEndEvent event) {
    }

    @Override
    public void onEnd(ActivitiExecutionEndEvent event) {
        this.onEnd(event.getExecution());
    }

    private void onEnd(DelegateExecution execution) {
        Object object = execution.getVariable(this.nrParameterUtils.getMapKey());
        if (object == null) {
            return;
        }
        Object businessKeyVar = execution.getVariable("businessKey");
        if (businessKeyVar == null) {
            return;
        }
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(businessKeyVar.toString());
        this.processStateHistoryDao.updateState(businessKey, "end", object.toString(), false, "end");
    }
}

