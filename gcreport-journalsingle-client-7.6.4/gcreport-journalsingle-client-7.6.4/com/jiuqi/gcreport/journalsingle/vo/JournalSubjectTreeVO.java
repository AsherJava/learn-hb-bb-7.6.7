/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.journalsingle.vo;

import java.util.ArrayList;
import java.util.List;

public class JournalSubjectTreeVO {
    private String id;
    private String parentId;
    private String[] parents;
    private String code;
    private String title;
    private List<JournalSubjectTreeVO> children = new ArrayList<JournalSubjectTreeVO>();
    private String dataType;
    private Boolean expand;

    public JournalSubjectTreeVO() {
    }

    public JournalSubjectTreeVO(String id, String code, String title, String parentId) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.parentId = parentId;
    }

    public JournalSubjectTreeVO(String id, String code, String title, String parentId, String[] parents) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.parentId = parentId;
        this.parents = parents;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<JournalSubjectTreeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<JournalSubjectTreeVO> children) {
        this.children = children;
    }

    public void addChildren(JournalSubjectTreeVO simpleTreeVo) {
        this.children.add(simpleTreeVo);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Boolean getExpand() {
        return this.expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String[] getParents() {
        return this.parents;
    }

    public void setParents(String[] parents) {
        this.parents = parents;
    }
}

