/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.task.Comment
 */
package com.jiuqi.nr.bpm.impl.activiti6.common;

import com.jiuqi.nr.bpm.common.TaskComment;
import java.util.Date;
import org.activiti.engine.task.Comment;
import org.springframework.util.Assert;

class TaskCommentWrapper
implements TaskComment {
    private final Comment innerComment;

    public TaskCommentWrapper(Comment comment) {
        Assert.notNull((Object)comment, "'comment' must not be null.");
        this.innerComment = comment;
    }

    @Override
    public String getId() {
        return this.innerComment.getId();
    }

    @Override
    public String getMessage() {
        return this.innerComment.getFullMessage();
    }

    @Override
    public String getUserId() {
        return this.innerComment.getUserId();
    }

    @Override
    public Date getTime() {
        return this.innerComment.getTime();
    }

    @Override
    public String getTaskId() {
        return this.innerComment.getTaskId();
    }

    @Override
    public String getProcessInstanceId() {
        return this.innerComment.getProcessInstanceId();
    }
}

