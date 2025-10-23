/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.multcheck2.web.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.multcheck2.web.vo.FormSchemeVO;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import java.util.List;

public class TaskTreeNodeVO
implements INode {
    private String key;
    private String code;
    private String title;
    private String type;
    private String dataScheme;
    private List<FormSchemeVO> formSchemes;
    private List<MCLabel> orgLinks;

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

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public List<FormSchemeVO> getFormSchemes() {
        return this.formSchemes;
    }

    public void setFormSchemes(List<FormSchemeVO> formSchemes) {
        this.formSchemes = formSchemes;
    }

    public List<MCLabel> getOrgLinks() {
        return this.orgLinks;
    }

    public void setOrgLinks(List<MCLabel> orgLinks) {
        this.orgLinks = orgLinks;
    }
}

