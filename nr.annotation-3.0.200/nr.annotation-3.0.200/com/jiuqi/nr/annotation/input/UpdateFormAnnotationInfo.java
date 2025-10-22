/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.annotation.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(value="\u4fee\u6539\u6279\u6ce8\u7684\u8bf7\u6c42\u4fe1\u606f", description="\u4fee\u6539\u6279\u6ce8\u7684\u8bf7\u6c42\u4fe1\u606f")
public class UpdateFormAnnotationInfo {
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848Key", name="formSchemeKey", required=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u7ef4\u5ea6\u4fe1\u606f", name="dimensionCombination", required=true)
    private DimensionCombination dimensionCombination;
    @ApiModelProperty(value="\u6279\u6ce8id", name="id", required=true)
    private String id;
    @ApiModelProperty(value="\u6279\u6ce8\u7c7b\u578b", name="types")
    private List<String> types;
    @ApiModelProperty(value="\u6279\u6ce8\u5185\u5bb9", name="content", required=true)
    private String content;
    @ApiModelProperty(value="\u5f53\u524d\u767b\u5f55\u7528\u6237name", name="userName", required=true)
    private String userName;
    @ApiModelProperty(value="\u4e0a\u4e0b\u6587\u4e2d\u7684\u7528\u6237\u59d3\u540d", name="userFullname", required=true)
    private String userFullname;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTypes() {
        return this.types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullname() {
        return this.userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }
}

