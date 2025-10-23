/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.Error;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;

public class TaskNotFoundError
extends Error {
    public TaskNotFoundError(TaskNotFoundErrorData errorData) {
        super(ErrorCode.TASK_NOT_FOUND, errorData);
    }

    public static class TaskNotFoundErrorData {
        private String orginalTaskId;
        private String currentTaskId;
        private String currentUserTask;
        private String currentStatus;

        public String getOrginalTaskId() {
            return this.orginalTaskId;
        }

        public void setOrginalTaskId(String orginalTaskId) {
            this.orginalTaskId = orginalTaskId;
        }

        public String getCurrentTaskId() {
            return this.currentTaskId;
        }

        public void setCurrentTaskId(String currentTaskId) {
            this.currentTaskId = currentTaskId;
        }

        public String getCurrentUserTask() {
            return this.currentUserTask;
        }

        public void setCurrentUserTask(String currentUserTask) {
            this.currentUserTask = currentUserTask;
        }

        public String getCurrentStatus() {
            return this.currentStatus;
        }

        public void setCurrentStatus(String currentStatus) {
            this.currentStatus = currentStatus;
        }
    }
}

