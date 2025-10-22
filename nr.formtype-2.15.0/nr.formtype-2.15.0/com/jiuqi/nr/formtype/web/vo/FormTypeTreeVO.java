/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonGetter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonSetter
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.formtype.web.vo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.formtype.common.FormTypeParamKind;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.facade.FormTypeGroupDefine;
import org.springframework.util.StringUtils;

public class FormTypeTreeVO
implements INode {
    private String key;
    private String code;
    private String title;
    private String groupKey;
    private FormTypeParamKind kind;

    public FormTypeTreeVO(FormTypeGroupDefine group) {
        this.key = group.getId();
        this.code = group.getCode();
        this.title = group.getTitle();
        this.groupKey = StringUtils.hasText(group.getGroupId()) ? group.getGroupId() : "--";
        this.kind = FormTypeParamKind.GROUP;
    }

    public FormTypeTreeVO(FormTypeDefine define) {
        this.key = define.getId();
        this.code = define.getCode();
        this.title = define.getCode() + " | " + define.getTitle();
        this.groupKey = StringUtils.hasText(define.getGroupId()) ? define.getGroupId() : "--";
        this.kind = FormTypeParamKind.FORMTYPE;
    }

    private FormTypeTreeVO() {
    }

    public static FormTypeTreeVO getRoot() {
        FormTypeTreeVO root = new FormTypeTreeVO();
        root.setKey("--");
        root.setTitle("\u5168\u90e8\u62a5\u8868\u7c7b\u578b");
        root.setKind(FormTypeParamKind.GROUP);
        return root;
    }

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

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    @JsonGetter(value="kind")
    public int getKindValue() {
        return this.kind.getValue();
    }

    @JsonIgnore
    public FormTypeParamKind getKind() {
        return this.kind;
    }

    @JsonSetter(value="kind")
    public void setKindValue(int kind) {
        this.kind = FormTypeParamKind.valueOf(kind);
    }

    @JsonIgnore
    public void setKind(FormTypeParamKind kind) {
        this.kind = kind;
    }
}

