/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="EnumLink", description="\u679a\u4e3e\u8054\u52a8")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class EnumLink {
    @ApiModelProperty(value="\u679a\u4e3e\u8054\u52a8\u94fe\u63a5", name="link")
    private String link;
    @ApiModelProperty(value="\u679a\u4e3e\u8054\u52a8\u7ea7\u6b21", name="level")
    private String level;
    @ApiModelProperty(value="\u679a\u4e3e\u8054\u52a8\u524d\u94fe\u63a5(\u53ef\u80fd\u5b58\u5728\u591a\u4e2a)", name="preLinks")
    private List<String> preLinks = new ArrayList<String>();
    @ApiModelProperty(value="\u679a\u4e3e\u8054\u52a8\u540e\u94fe\u63a5(\u53ef\u80fd\u5b58\u5728\u591a\u4e2a)", name="nextLinks")
    private List<String> nextLinks = new ArrayList<String>();
    @ApiModelProperty(value="\u679a\u4e3e\u8054\u52a8\u7c7b\u578b", name="type")
    private String type;
    @ApiModelProperty(value="\u679a\u4e3e\u8054\u52a8\u516c\u5f0f", name="formula")
    private String formula;

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<String> getPreLinks() {
        return this.preLinks;
    }

    public void setPreLinks(List<String> preLinks) {
        this.preLinks = preLinks;
    }

    public List<String> getNextLinks() {
        return this.nextLinks;
    }

    public void setNextLinks(List<String> nextLinks) {
        this.nextLinks = nextLinks;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}

