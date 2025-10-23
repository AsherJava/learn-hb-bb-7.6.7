/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.mapping2.web.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.mapping2.web.vo.SelectOptionVO;
import java.util.List;

public class TaskTreeNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String type;
    private String orgName;
    private List<SelectOptionVO> formSchemes;
    private List<SelectOptionVO> orgLinks;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<SelectOptionVO> getFormSchemes() {
        return this.formSchemes;
    }

    public void setFormSchemes(List<SelectOptionVO> formSchemes) {
        this.formSchemes = formSchemes;
    }

    public List<SelectOptionVO> getOrgLinks() {
        return this.orgLinks;
    }

    public void setOrgLinks(List<SelectOptionVO> orgLinks) {
        this.orgLinks = orgLinks;
    }
}

