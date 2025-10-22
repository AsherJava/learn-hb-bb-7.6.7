/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.history;

import com.jiuqi.nr.bpm.history.CommentQuery;
import com.jiuqi.nr.bpm.history.ProcessActivityQuery;

public interface HistoryService {
    public CommentQuery createCommentQuery();

    public ProcessActivityQuery createProcessActivityQuery();
}

