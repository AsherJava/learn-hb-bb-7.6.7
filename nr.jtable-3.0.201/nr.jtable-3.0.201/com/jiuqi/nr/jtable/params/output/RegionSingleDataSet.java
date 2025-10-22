/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.file.FileInfo
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="RegionSingleDataSet", description="\u533a\u57df\u5355\u6761\u6570\u636e\u67e5\u8be2\u7ed3\u679c")
public class RegionSingleDataSet {
    @ApiModelProperty(value="\u5b9a\u4f4d\u4fe1\u606f", name="pagerInfo")
    private PagerInfo pagerInfo = new PagerInfo();
    @ApiModelProperty(value="\u533a\u57df\u4e0b\u94fe\u63a5key\u5217\u8868", name="cells")
    private Map<String, List<String>> cells = new HashMap<String, List<String>>();
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u7ed3\u679c\u96c6(\u6570\u636e\u7d22\u5f15\u4e0ecells\u7d22\u5f15\u4e00\u81f4)", name="data")
    private List<List<Object>> data = new ArrayList<List<Object>>();
    @ApiModelProperty(value="\u6570\u636e\u4e2d\u7528\u5230\u7684\u57fa\u7840\u6570\u636e\u679a\u4e3e\u9879", name="entityDataMap")
    private Map<String, EntityReturnInfo> entityDataMap = new HashMap<String, EntityReturnInfo>();
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u4e2d\u9644\u4ef6\u5217\u8868", name="fileDataMap")
    private Map<String, List<FileInfo>> fileDataMap = new HashMap<String, List<FileInfo>>();
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u4e2d\u56fe\u7247\u5217\u8868", name="imgDataMap")
    private Map<String, List<byte[]>> imgDataMap = new HashMap<String, List<byte[]>>();

    public PagerInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }

    public Map<String, List<String>> getCells() {
        return this.cells;
    }

    public void setCells(Map<String, List<String>> cells) {
        this.cells = cells;
    }

    public List<List<Object>> getData() {
        return this.data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

    public Map<String, EntityReturnInfo> getEntityDataMap() {
        return this.entityDataMap;
    }

    public void setEntityDataMap(Map<String, EntityReturnInfo> entityDataMap) {
        this.entityDataMap = entityDataMap;
    }

    public Map<String, List<FileInfo>> getFileDataMap() {
        return this.fileDataMap;
    }

    public void setFileDataMap(Map<String, List<FileInfo>> fileDataMap) {
        this.fileDataMap = fileDataMap;
    }

    public Map<String, List<byte[]>> getImgDataMap() {
        return this.imgDataMap;
    }

    public void setImgDataMap(Map<String, List<byte[]>> imgDataMap) {
        this.imgDataMap = imgDataMap;
    }
}

