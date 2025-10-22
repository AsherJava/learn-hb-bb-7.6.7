/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.annotation.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="\u5220\u9664\u6279\u6ce8\u6216\u5220\u9664\u8bc4\u8bba\u7684\u8bf7\u6c42\u4fe1\u606f", description="\u5220\u9664\u6279\u6ce8\u6216\u5220\u9664\u8bc4\u8bba\u7684\u8bf7\u6c42\u4fe1\u606f")
public class FormAnnotationDeleteInfo {
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848Key", name="formSchemeKey", required=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u4e3b\u952eid\u96c6\u5408", name="ids", required=true)
    private List<String> ids = new ArrayList<String>();
    @ApiModelProperty(value="\u5f53\u524d\u767b\u5f55\u7528\u6237name", name="userName", required=true)
    private String userName;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getIds() {
        return this.ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

