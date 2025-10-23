/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.extend.env;

import com.jiuqi.nr.workflow2.todo.enumeration.CommentType;

public class WorkFlowNode {
    private String code;
    private String title;
    private boolean isOpenComment;
    private CommentType commentType;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOpenComment() {
        return this.isOpenComment;
    }

    public void setOpenComment(boolean openComment) {
        this.isOpenComment = openComment;
    }

    public CommentType getCommentType() {
        return this.commentType;
    }

    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }
}

