/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.biz.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class GrammarTreeVO {
    private String title;
    private String name;
    private List<GrammarTreeVO> children;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String warnMsg;

    public GrammarTreeVO(String title) {
        this.title = title;
        this.name = title;
        this.children = new ArrayList<GrammarTreeVO>();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GrammarTreeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<GrammarTreeVO> chrildren) {
        this.children = chrildren;
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(this.title) && StringUtils.isEmpty(this.name) && this.children == null;
    }

    public void addLeftChildren(GrammarTreeVO leftChildren) {
        if (this.children.size() == 0) {
            this.children.add(leftChildren);
        } else {
            this.children.set(0, leftChildren);
        }
    }

    public void addRightChildren(GrammarTreeVO rightChildren) {
        if (this.children.size() < 2) {
            this.children.add(rightChildren);
        } else {
            this.children.set(1, rightChildren);
        }
    }

    public void addChildren(GrammarTreeVO children) {
        this.children.add(children);
    }

    public String getWarnMsg() {
        return this.warnMsg;
    }

    public void setWarnMsg(String warnMsg) {
        this.warnMsg = warnMsg;
    }
}

