/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.jtable.params.input.EntityQueryInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="EntityQueryInfo", description="\u4e3b\u4f53\u6570\u636e\u6839\u636e\u679a\u4e3e\u6761\u76ee\u4e3b\u952e\u67e5\u8be2\u6761\u4ef6\u53c2\u6570")
public class EntityQueryByKeyInfo
extends EntityQueryInfo {
    @ApiModelProperty(value="\u679a\u4e3e\u6761\u76ee\u4e3b\u952e", name="entityKey")
    private String entityKey;

    public EntityQueryByKeyInfo() {
        this.setReadAuth(false);
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }
}

