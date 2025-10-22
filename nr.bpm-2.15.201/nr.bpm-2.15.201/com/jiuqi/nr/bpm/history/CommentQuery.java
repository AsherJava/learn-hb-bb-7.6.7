/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.history;

import com.jiuqi.nr.bpm.common.TaskComment;
import java.util.List;

public interface CommentQuery {
    public List<TaskComment> queryCommentByInstanceId(String var1);

    public List<TaskComment> queryCommentByTaskId(String var1);
}

