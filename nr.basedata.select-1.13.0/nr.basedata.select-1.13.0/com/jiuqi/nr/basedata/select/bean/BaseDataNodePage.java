/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.basedata.select.bean;

import com.jiuqi.nr.common.params.PagerInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="BaseDataNodePage", description="\u57fa\u7840\u6570\u636e\u5b9a\u4f4d\u6240\u9700\u7c7b")
public class BaseDataNodePage {
    @ApiModelProperty(value="\u7236\u7ea7\u5b9e\u4f53\u4ee3\u7801", name="parentKeyData")
    private String parentKeyData;
    @ApiModelProperty(value="\u5b50\u7ea7\u5b9e\u4f53\u4ee3\u7801", name="childKeyData")
    private String childKeyData;
    @ApiModelProperty(value="\u5b50\u7ea7\u5e94\u5c55\u5f00\u6570\u91cf", name="pagerInfo")
    private PagerInfo pagerInfo;

    public String getParentKeyData() {
        return this.parentKeyData;
    }

    public void setParentKeyData(String parentKeyData) {
        this.parentKeyData = parentKeyData;
    }

    public String getChildKeyData() {
        return this.childKeyData;
    }

    public void setChildKeyData(String childKeyData) {
        this.childKeyData = childKeyData;
    }

    public PagerInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }
}

