/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.data;

public enum SubjectTreeNodeType {
    ROOT("-"),
    SUBJECT_CLASS("subjectClass"),
    SUBJECT("subject");

    private String code;

    private SubjectTreeNodeType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

