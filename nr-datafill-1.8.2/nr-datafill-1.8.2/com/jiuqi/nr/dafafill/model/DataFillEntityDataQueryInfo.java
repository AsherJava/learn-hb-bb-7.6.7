/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="DataFillEntityDataQueryInfo", description="\u67e5\u8be2\u5355\u5143\u683c\u53ef\u9009\u503c\u4fe1\u606f")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataFillEntityDataQueryInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u8fd0\u884c\u671f\u73af\u5883", name="context", required=true)
    private DataFillContext context;
    @ApiModelProperty(value="\u5168\u6807\u8bc6", name="fullCode", required=true)
    private String fullCode;
    @ApiModelProperty(value="\u7236\u8282\u70b9\u4e3b\u952e", name="parentKey")
    private String parentKey;
    @ApiModelProperty(value="\u641c\u7d22\u5b57\u7b26", name="search")
    private String search;
    @ApiModelProperty(value="\u6839\u636e\u6807\u8bc6\u641c\u7d22", name="code")
    private String code;
    @ApiModelProperty(value="\u5c55\u793a\u8def\u5f84", name="showFullPath", required=false)
    private boolean showFullPath;
    @ApiModelProperty(value="\u67e5\u6240\u6709\u5b50\u8282\u70b9", name="allChildren", required=false)
    private boolean allChildren = false;

    public DataFillContext getContext() {
        return this.context;
    }

    public void setContext(DataFillContext context) {
        this.context = context;
    }

    public String getFullCode() {
        return this.fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isAllChildren() {
        return this.allChildren;
    }

    public void setAllChildren(boolean allChildren) {
        this.allChildren = allChildren;
    }

    public boolean isShowFullPath() {
        return this.showFullPath;
    }

    public void setShowFullPath(boolean showFullPath) {
        this.showFullPath = showFullPath;
    }
}

