/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="PasteFormatReturnInfo", description="\u683c\u5f0f\u5316\u6570\u636e\u7ed3\u679c")
public class PasteFormatReturnInfo {
    @ApiModelProperty(value="\u683c\u5f0f\u5316\u6570\u636e\u7ed3\u679c\uff08key:\u94fe\u63a5key, value: \u683c\u5f0f\u5316\u6570\u636e\u7ed3\u679c\u5217\u8868\uff09", name="dataLinkMaps", required=true)
    private Map<String, List<String>> dataLinkMaps = new HashMap<String, List<String>>();
    @ApiModelProperty(value="\u6570\u636e\u4e2d\u7528\u5230\u7684\u57fa\u7840\u6570\u636e\u679a\u4e3e\u9879", name="entityDataMap")
    private Map<String, EntityReturnInfo> entityDataMap = new HashMap<String, EntityReturnInfo>();

    public Map<String, List<String>> getDataLinkMaps() {
        return this.dataLinkMaps;
    }

    public void setDataLinkMaps(Map<String, List<String>> dataLinkMaps) {
        this.dataLinkMaps = dataLinkMaps;
    }

    public Map<String, EntityReturnInfo> getEntityDataMap() {
        return this.entityDataMap;
    }

    public void setEntityDataMap(Map<String, EntityReturnInfo> entityDataMap) {
        this.entityDataMap = entityDataMap;
    }
}

