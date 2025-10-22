/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.annotation.message.RegionAnnotationResult
 *  com.jiuqi.nr.file.FileInfo
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.annotation.message.RegionAnnotationResult;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.jtable.params.base.CalcDimensionLink;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="RegionDataSet", description="\u533a\u57df\u6570\u636e\u67e5\u8be2\u7ed3\u679c")
public class RegionDataSet {
    @ApiModelProperty(value="\u6570\u636e\u603b\u884c\u6570", name="totalCount")
    private int totalCount;
    @ApiModelProperty(value="\u5f53\u524d\u67e5\u8be2\u7684\u9875\u6570", name="currentPage")
    private int currentPage = 0;
    @ApiModelProperty(value="\u662f\u5426\u662f\u6c47\u603b\u6570\u636e", name="sumData")
    private boolean sumData = false;
    @ApiModelProperty(value="\u533a\u57df\u4e0b\u94fe\u63a5key\u5217\u8868", name="cells")
    private Map<String, List<String>> cells = new HashMap<String, List<String>>();
    @ApiModelProperty(value="\u5206\u7ea7\u5408\u8ba1\u4e0b\u6839\u6570\u636e\u7d22\u5f15", name="root")
    private List<Integer> root = new ArrayList<Integer>();
    @ApiModelProperty(value="\u5206\u7ea7\u5408\u8ba1\u4e0b \u6570\u636e\u884c\u5173\u7cfb[index, [childrenIndex], deep,parent, type(\u6c47\u603b\u6570\u636e:1,\u660e\u7ec6\u6570\u636e:2)]", name="rel")
    private List<List<Object>> rel = new ArrayList<List<Object>>();
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u7ed3\u679c\u96c6(\u6570\u636e\u7d22\u5f15\u4e0ecells\u7d22\u5f15\u4e00\u81f4)", name="data")
    private List<List<Object>> data = new ArrayList<List<Object>>();
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u683c\u5f0f\u5316\u7ed3\u679c\u96c6(\u6570\u636e\u7d22\u5f15\u4e0ecells\u7d22\u5f15\u4e00\u81f4)", name="dataFormat")
    private List<List<Object>> dataFormat = new ArrayList<List<Object>>();
    @ApiModelProperty(value="\u6570\u636e\u4e2d\u7528\u5230\u7684\u57fa\u7840\u6570\u636e\u679a\u4e3e\u9879", name="entityDataMap")
    private Map<String, EntityReturnInfo> entityDataMap = new HashMap<String, EntityReturnInfo>();
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u4e2d\u9644\u4ef6\u5217\u8868", name="fileDataMap")
    private Map<String, List<FileInfo>> fileDataMap = new HashMap<String, List<FileInfo>>();
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u4e2d\u56fe\u7247\u5217\u8868", name="imgDataMap")
    private Map<String, List<byte[]>> imgDataMap = new HashMap<String, List<byte[]>>();
    @ApiModelProperty(value="\u4e00\u4e2a\u533a\u57df\u7684\u6240\u6709\u6279\u6ce8", name="annotationResult")
    private RegionAnnotationResult annotationResult;
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u4e2d\u5408\u5e76\u5355\u5143\u683c\u5217\u8868", name="mergeCells")
    private Map<String, List<List<Integer>>> mergeCells = new HashMap<String, List<List<Integer>>>();
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u4e2d\u8fd0\u7b97\u5355\u5143\u683c\u5217\u8868", name="calcDimensionLinks")
    private List<CalcDimensionLink> calcDimensionLinks = new ArrayList<CalcDimensionLink>();
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u4e2d\u53ea\u6709\u679a\u4e3e\u586b\u5145\u6570\u636e", name="regionOnlyHasExtentGridData")
    private boolean regionOnlyHasExtentGridData = true;

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isSumData() {
        return this.sumData;
    }

    public void setSumData(boolean sumData) {
        this.sumData = sumData;
    }

    public Map<String, List<String>> getCells() {
        return this.cells;
    }

    public void setCells(Map<String, List<String>> cells) {
        this.cells = cells;
    }

    public List<Integer> getRoot() {
        return this.root;
    }

    public void setRoot(List<Integer> root) {
        this.root = root;
    }

    public List<List<Object>> getRel() {
        return this.rel;
    }

    public void setRel(List<List<Object>> rel) {
        this.rel = rel;
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

    public RegionAnnotationResult getAnnotationResult() {
        return this.annotationResult;
    }

    public void setAnnotationResult(RegionAnnotationResult annotationResult) {
        this.annotationResult = annotationResult;
    }

    public Map<String, List<byte[]>> getImgDataMap() {
        return this.imgDataMap;
    }

    public void setImgDataMap(Map<String, List<byte[]>> imgDataMap) {
        this.imgDataMap = imgDataMap;
    }

    public Map<String, List<List<Integer>>> getMergeCells() {
        return this.mergeCells;
    }

    public void setMergeCells(Map<String, List<List<Integer>>> mergeCells) {
        this.mergeCells = mergeCells;
    }

    public List<CalcDimensionLink> getCalcDimensionLinks() {
        return this.calcDimensionLinks;
    }

    public void setCalcDimensionLinks(List<CalcDimensionLink> calcDimensionLinks) {
        this.calcDimensionLinks = calcDimensionLinks;
    }

    public boolean isRegionOnlyHasExtentGridData() {
        return this.regionOnlyHasExtentGridData;
    }

    public void setRegionOnlyHasExtentGridData(boolean regionOnlyHasExtentGridData) {
        this.regionOnlyHasExtentGridData = regionOnlyHasExtentGridData;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<List<Object>> getDataFormat() {
        return this.dataFormat;
    }

    public void setDataFormat(List<List<Object>> dataFormat) {
        this.dataFormat = dataFormat;
    }
}

