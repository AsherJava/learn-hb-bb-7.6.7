/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="DataFillEntityDataBase", description="\u5355\u5143\u683c\u53ef\u9009\u503c")
public class DataFillEntityDataBase
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76eeid", name="id")
    private String id;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76eecode", name="code")
    private String code;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u6807\u9898", name="title")
    private String title;
    @ApiModelProperty(value="\u679a\u4e3e\u8def\u5f84", name="path")
    private String path;
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u53f6\u5b50\u8282\u70b9", name="leaf")
    private boolean leaf;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

