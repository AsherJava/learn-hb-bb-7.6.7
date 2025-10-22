/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.output;

import com.jiuqi.nr.annotation.message.CellAnnotationRelation;
import com.jiuqi.nr.annotation.output.CellAnnotationComment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CellAnnotationContent
implements Comparable<CellAnnotationContent> {
    private String id;
    private Map<String, String> typeCodeTitleMap;
    private String content;
    private String userName;
    private long date;
    private List<CellAnnotationRelation> relations = new ArrayList<CellAnnotationRelation>();
    private List<CellAnnotationComment> comments = new ArrayList<CellAnnotationComment>();
    private boolean canEditOrDelete;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getTypeCodeTitleMap() {
        return this.typeCodeTitleMap;
    }

    public void setTypeCodeTitleMap(Map<String, String> typeCodeTitleMap) {
        this.typeCodeTitleMap = typeCodeTitleMap;
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

    public List<CellAnnotationComment> getComments() {
        return this.comments;
    }

    public void setComments(List<CellAnnotationComment> comments) {
        this.comments = comments;
    }

    public List<CellAnnotationRelation> getRelations() {
        return this.relations;
    }

    public void setRelations(List<CellAnnotationRelation> relations) {
        this.relations = relations;
    }

    public boolean isCanEditOrDelete() {
        return this.canEditOrDelete;
    }

    public void setCanEditOrDelete(boolean canEditOrDelete) {
        this.canEditOrDelete = canEditOrDelete;
    }

    @Override
    public int compareTo(CellAnnotationContent o) {
        return o.getDate() > this.date ? 1 : -1;
    }
}

