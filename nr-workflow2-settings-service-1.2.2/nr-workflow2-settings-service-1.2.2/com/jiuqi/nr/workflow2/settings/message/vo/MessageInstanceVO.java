/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.message.vo;

public class MessageInstanceVO {
    public static final String KEY_OF_RECEIVER_STRATEGY = "strategy";
    public static final String KEY_OF_RECEIVER_PARAM = "param";
    public static final String KEY_OF_RECEIVER_PARAM_USER = "user";
    public static final String KEY_OF_RECEIVER_PARAM_role = "role";
    private String title;
    private String subject;
    private String content;
    private Object receiver;
    private boolean userSelectable;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getReceiver() {
        return this.receiver;
    }

    public void setReceiver(Object receiver) {
        this.receiver = receiver;
    }

    public boolean isUserSelectable() {
        return this.userSelectable;
    }

    public void setUserSelectable(boolean userSelectable) {
        this.userSelectable = userSelectable;
    }
}

