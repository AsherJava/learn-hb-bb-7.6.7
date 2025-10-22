/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.basedata.select.param;

import com.jiuqi.nr.basedata.select.param.BaseDataQueryInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(value="BaseDataQueryInfoExtend", description="\u57fa\u7840\u6570\u636e\u8bf7\u6c42\u53c2\u6570\u6269\u5c55")
public class BaseDataQueryInfoExtend {
    @ApiModelProperty(value="\u57fa\u7840\u6570\u636e\u6761\u76eekey\u96c6\u5408", name="keys", required=true)
    private List<String> keys;
    @ApiModelProperty(value="\u8981\u683c\u5f0f\u5316\u7684\u5c5e\u6027code(\u4f8b\u5982\uff1a\u83b7\u53d6\u8be5\u5c5e\u6027\u7684\u503c\u548c\u83b7\u53d6\u8be5\u5c5e\u6027\u7684\u8def\u5f84)", name="formatCode")
    private List<String> formatCode;
    @ApiModelProperty(value="\u67e5\u8be2\u53c2\u6570", name="baseDataQueryInfo", required=true)
    private BaseDataQueryInfo baseDataQueryInfo;

    public BaseDataQueryInfo getBaseDataQueryInfo() {
        return this.baseDataQueryInfo;
    }

    public void setBaseDataQueryInfo(BaseDataQueryInfo baseDataQueryInfo) {
        this.baseDataQueryInfo = baseDataQueryInfo;
    }

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<String> getFormatCode() {
        return this.formatCode;
    }

    public void setFormatCode(List<String> formatCode) {
        this.formatCode = formatCode;
    }
}

