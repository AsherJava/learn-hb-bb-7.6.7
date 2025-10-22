/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.basedata.select.param;

import com.jiuqi.nr.basedata.select.bean.BaseDataInfo;
import com.jiuqi.nr.common.itree.ITree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="BaseDataResponse", description="\u57fa\u7840\u6570\u636e\u8bf7\u6c42\u54cd\u5e94")
public class BaseDataResponse
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u57fa\u7840\u6570\u636e\u7ed3\u679c\u96c6\u5408", name="baseDataTree")
    private ITree<BaseDataInfo> baseDataTree;
    @ApiModelProperty(value="\u8fd4\u56de\u4fe1\u606f(\u8fd4\u56desuccess\u4e3a\u6210\u529f\u5176\u4ed6\u4e3a\u9519\u8bef\u4fe1\u606f)", name="message")
    private String message;

    public ITree<BaseDataInfo> getBaseDataTree() {
        return this.baseDataTree;
    }

    public void setBaseDataTree(ITree<BaseDataInfo> baseDataTree) {
        this.baseDataTree = baseDataTree;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

