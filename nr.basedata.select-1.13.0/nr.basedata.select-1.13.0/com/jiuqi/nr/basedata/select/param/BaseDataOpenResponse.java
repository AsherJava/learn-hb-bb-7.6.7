/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.basedata.select.param;

import com.jiuqi.nr.basedata.select.bean.BaseDataAttribute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="BaseDataOpenResponse", description="\u57fa\u7840\u6570\u636e\u5f39\u51fa\u6253\u5f00\u8bf7\u6c42\u54cd\u5e94")
public class BaseDataOpenResponse
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u6811\u5f62\u6700\u5927\u6df1\u5ea6", name="maxDepth")
    private int maxDepth = 0;
    @ApiModelProperty(value="\u57fa\u7840\u6570\u636e\u5b57\u6bb5\u96c6\u5408", name="baseDataAttributes")
    private List<BaseDataAttribute> baseDataAttributes = new ArrayList<BaseDataAttribute>();
    @ApiModelProperty(value="\u57fa\u7840\u6570\u636e\u4e0b\u62c9\u52fe\u9009\u5b57\u6bb5\u96c6\u5408", name="baseDataAttributes")
    private List<String> showAttributes = new ArrayList<String>();
    @ApiModelProperty(value="\u8fd4\u56de\u4fe1\u606f(\u8fd4\u56desuccess\u4e3a\u6210\u529f\u5176\u4ed6\u4e3a\u9519\u8bef\u4fe1\u606f)", name="message")
    private String message;

    public BaseDataOpenResponse() {
    }

    public BaseDataOpenResponse(int maxDepth, List<BaseDataAttribute> baseDataAttributes, List<String> showAttributes, String message) {
        this.maxDepth = maxDepth;
        this.baseDataAttributes = baseDataAttributes;
        this.showAttributes = showAttributes;
        this.message = message;
    }

    public int getMaxDepth() {
        return this.maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public List<BaseDataAttribute> getBaseDataAttributes() {
        return this.baseDataAttributes;
    }

    public void setBaseDataAttributes(List<BaseDataAttribute> baseDataAttributes) {
        this.baseDataAttributes = baseDataAttributes;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getShowAttributes() {
        return this.showAttributes;
    }

    public void setShowAttributes(List<String> showAttributes) {
        this.showAttributes = showAttributes;
    }
}

