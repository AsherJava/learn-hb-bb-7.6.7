/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.history.HistoricTaskInstance
 *  org.activiti.engine.task.Task
 */
package com.jiuqi.nr.bpm.impl.activiti6.common;

import com.jiuqi.nr.bpm.common.ProcessActivity;
import java.util.Date;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.util.Assert;

class ProcessActivityWrappers {
    ProcessActivityWrappers() {
    }

    static class ProcessActivityWrapperByTask
    implements ProcessActivity {
        private final Task task;

        public ProcessActivityWrapperByTask(Task task) {
            Assert.notNull((Object)task, "'task' must not be null.");
            this.task = task;
        }

        @Override
        public String getProcessDefinitionId() {
            return this.task.getProcessDefinitionId();
        }

        @Override
        public String getProcessInstanceId() {
            return this.task.getProcessInstanceId();
        }

        @Override
        public String getProcessTaskId() {
            return this.task.getId();
        }

        @Override
        public String getActivityId() {
            return this.task.getTaskDefinitionKey();
        }

        @Override
        public String getActUserId() {
            return null;
        }

        @Override
        public Date getStartTime() {
            return this.task.getCreateTime();
        }

        @Override
        public Date getEndTime() {
            return null;
        }

        @Override
        public boolean isFinish() {
            return false;
        }
    }

    static class ProcessActivityWrapperByHistoricTask
    implements ProcessActivity {
        private final HistoricTaskInstance historicTask;

        public ProcessActivityWrapperByHistoricTask(HistoricTaskInstance historicTask) {
            Assert.notNull((Object)historicTask, "'historicTask' must not be null.");
            this.historicTask = historicTask;
        }

        @Override
        public String getProcessDefinitionId() {
            return this.historicTask.getProcessDefinitionId();
        }

        @Override
        public String getProcessInstanceId() {
            return this.historicTask.getProcessInstanceId();
        }

        @Override
        public String getProcessTaskId() {
            return this.historicTask.getId();
        }

        @Override
        public String getActivityId() {
            return this.historicTask.getTaskDefinitionKey();
        }

        @Override
        public String getActUserId() {
            return this.historicTask.getAssignee();
        }

        @Override
        public Date getStartTime() {
            return this.historicTask.getStartTime();
        }

        @Override
        public Date getEndTime() {
            return this.historicTask.getEndTime();
        }

        @Override
        public boolean isFinish() {
            return true;
        }
    }
}

