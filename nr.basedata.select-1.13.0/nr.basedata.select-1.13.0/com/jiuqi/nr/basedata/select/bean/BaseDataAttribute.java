/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.basedata.select.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="BaseDataAttribute", description="\u57fa\u7840\u6570\u636e\u5b57\u6bb5\u5c5e\u6027")
public class BaseDataAttribute
implements Serializable {
    private static final long serialVersionUID = -2058500977418401894L;
    @ApiModelProperty(value="\u57fa\u7840\u6570\u636e\u5b57\u6bb5\u6807\u8bc6", name="key")
    private String key;
    @ApiModelProperty(value="\u57fa\u7840\u6570\u636e\u5b57\u6bb5\u540d\u79f0", name="title")
    private String title;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

