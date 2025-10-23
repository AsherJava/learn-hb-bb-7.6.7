/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.mapping.web.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.mapping.web.vo.SelectOptionVO;
import java.util.List;

public class TaskTreeNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String type;
    private List<SelectOptionVO> formSchemes;

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

    public List<SelectOptionVO> getFormSchemes() {
        return this.formSchemes;
    }

    public void setFormSchemes(List<SelectOptionVO> formSchemes) {
        this.formSchemes = formSchemes;
    }
}

