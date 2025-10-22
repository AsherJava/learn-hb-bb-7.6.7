/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.output;

import com.jiuqi.nr.annotation.output.ExpAnnotationComment;
import com.jiuqi.nr.annotation.output.ExpAnnotationRel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpAnnotationResult {
    private String id;
    private Map<String, String> dimNameValue;
    private String content;
    private String userName;
    private long date;
    private List<ExpAnnotationRel> relations = new ArrayList<ExpAnnotationRel>();
    private List<ExpAnnotationComment> comments = new ArrayList<ExpAnnotationComment>();
    private List<String> typeCode;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getDimNameValue() {
        return this.dimNameValue;
    }

    public void setDimNameValue(Map<String, String> dimNameValue) {
        this.dimNameValue = dimNameValue;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<ExpAnnotationRel> getRelations() {
        return this.relations;
    }

    public void setRelations(List<ExpAnnotationRel> relations) {
        this.relations = relations;
    }

    public List<ExpAnnotationComment> getComments() {
        return this.comments;
    }

    public void setComments(List<ExpAnnotationComment> comments) {
        this.comments = comments;
    }

    public List<String> getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(List<String> typeCode) {
        this.typeCode = typeCode;
    }
}

