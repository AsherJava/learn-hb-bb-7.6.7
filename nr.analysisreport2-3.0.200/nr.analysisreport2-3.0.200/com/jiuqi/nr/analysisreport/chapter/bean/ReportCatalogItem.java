/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  net.minidev.json.annotate.JsonIgnore
 */
package com.jiuqi.nr.analysisreport.chapter.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minidev.json.annotate.JsonIgnore;

public class ReportCatalogItem
implements Serializable {
    private String tagName;
    private String title;
    @JsonProperty(value="key")
    private String id;
    private String parentId;
    private String chapterId;
    private boolean isLeaf = true;
    private Set<String> path = new HashSet<String>();
    private List<ReportCatalogItem> children;
    private int indentationNum = 0;

    public ReportCatalogItem() {
        this.path.add("top");
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public List<ReportCatalogItem> getChildren() {
        return this.children;
    }

    public void setChildren(List<ReportCatalogItem> children) {
        this.children = children;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChapterId() {
        return this.chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(boolean leaf) {
        this.isLeaf = leaf;
    }

    public Set<String> getPath() {
        return this.path;
    }

    public void setPath(Set<String> path) {
        this.path = path;
    }

    public int getIndentationNum() {
        return this.indentationNum;
    }

    public void setIndentationNum(int indentationNum) {
        this.indentationNum = indentationNum;
    }
}

