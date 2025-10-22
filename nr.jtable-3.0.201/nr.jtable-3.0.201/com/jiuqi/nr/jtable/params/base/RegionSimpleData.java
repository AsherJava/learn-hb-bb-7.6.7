/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.LinkSimpleData;
import com.jiuqi.nr.jtable.params.base.RecordCardData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionEdgeStyle;
import com.jiuqi.nr.jtable.params.base.RegionExtentGridData;
import com.jiuqi.nr.jtable.params.base.RegionNumber;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.input.RegionGradeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApiModel(value="RegionSimpleData", description="\u533a\u57df\u5c5e\u6027")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class RegionSimpleData {
    @ApiModelProperty(value="\u533a\u57dfid", name="id")
    private String id;
    @ApiModelProperty(value="\u533a\u57df\u7c7b\u578b,\u53c2\u7167\uff08com.jiuqi.nr.definition.common.DataRegionKind\uff09", name="type")
    private int type;
    @ApiModelProperty(value="\u533a\u57df\u5de6\u8fb9\u754c", name="left")
    private int left;
    @ApiModelProperty(value="\u533a\u57df\u53f3\u8fb9\u754c", name="right")
    private int right;
    @ApiModelProperty(value="\u533a\u57df\u4e0a\u8fb9\u754c", name="top")
    private int top;
    @ApiModelProperty(value="\u533a\u57df\u4e0b\u8fb9\u754c", name="bottom")
    private int bottom;
    @ApiModelProperty(value="\u533a\u57df\u6807\u9898", name="title")
    private String title;
    @ApiModelProperty(value="\u6d6e\u52a8\u5206\u9875\u5927\u5c0f", name="limit")
    private int limit;
    @ApiModelProperty(value="\u6d6e\u52a8\u533a\u57df\u53ef\u4ee5\u65b0\u589e", name="cadd")
    private boolean cadd;
    @ApiModelProperty(value="\u6d6e\u52a8\u533a\u57df\u53ef\u4ee5\u5220\u9664", name="cdel")
    private boolean cdel;
    @ApiModelProperty(value="\u5b50\u533a\u57df\u5217\u8868", name="children")
    private List<RegionSimpleData> children = new ArrayList<RegionSimpleData>();
    @ApiModelProperty(value="\u94fe\u63a5\u5217\u8868", name="links")
    private List<LinkSimpleData> links = new ArrayList<LinkSimpleData>();
    @ApiModelProperty(value="\u6d6e\u52a8\u533a\u57df\u5206\u9875\u7b7e\u5217\u8868", name="tabs")
    private List<RegionTab> tabs = new ArrayList<RegionTab>();
    @ApiModelProperty(value="\u533a\u57df\u53ea\u8bfb", name="read")
    private boolean read = false;
    @ApiModelProperty(value="\u533a\u57df\u5e8f\u53f7\u8bbe\u7f6e", name="serl")
    private RegionNumber serl;
    @ApiModelProperty(value="\u533a\u57df\u9ed8\u8ba4\u884c\u6570", name="defcnt")
    private int defcnt;
    @ApiModelProperty(value="\u533a\u57df\u6700\u5927\u884c\u6570", name="maxcnt")
    private int maxcnt;
    @ApiModelProperty(value="\u533a\u57df\u5206\u7ea7\u4fe1\u606f", name="grade")
    private RegionGradeInfo grade;
    @ApiModelProperty(value="\u533a\u57df\u672b\u884c\u8fb9\u6846\u6837\u5f0f", name="lastRowStyle")
    private RegionEdgeStyle lastRowStyle;
    @ApiModelProperty(value="\u533a\u57df\u53c2\u6570\u9519\u8bef\u4fe1\u606f", name="errors")
    private String errors;
    @ApiModelProperty(value="\u533a\u57df\u81ea\u52a8\u5c55\u5f00\u94fe\u63a5\u5217\u8868", name="fillLinks")
    private List<String> fillLinks;
    @ApiModelProperty(value="\u533a\u57df\u5361\u7247\u5f55\u5165\u7684\u8bbe\u7f6e", name="cR")
    private RecordCardData cR;
    @ApiModelProperty(value="\u679a\u4e3e\u586b\u5145\u7279\u6b8a\u8868\u6837", name="egd")
    private RegionExtentGridData egd;
    @ApiModelProperty(value="\u533a\u57df\u56de\u8f66\u952e\u7684\u65b9\u5411", name="eNext")
    private int eNext;
    @ApiModelProperty(value="\u6d6e\u52a8\u533a\u57df\u7ef4\u5ea6\u540d", name="dimensionNames")
    private List<String> dimensionNames;
    @ApiModelProperty(value="\u6d6e\u52a8\u533a\u57df\u884c\u4e3b\u952e\u6307\u6807key", name="bizFieldKeys")
    private List<String> bizFieldKeys;
    @ApiModelProperty(value="\u591a\u7ef4\u6d6e\u52a8\u94fe\u63a5\u7ea7\u6b21", name="linkLevelMap")
    private Map<String, Integer> linkLevelMap;

    public RegionSimpleData(RegionData region) {
        this.id = region.getKey();
        this.type = region.getType();
        this.left = region.getRegionLeft();
        this.right = region.getRegionRight();
        this.top = region.getRegionTop();
        this.bottom = region.getRegionBottom();
        this.title = region.getTitle();
        this.limit = region.getPageSize();
        this.cadd = region.isCanInsertRow();
        this.cdel = region.isCanDeleteRow();
        if (region.getChildrenRegions() != null && !region.getChildrenRegions().isEmpty()) {
            this.children = new ArrayList<RegionSimpleData>();
            for (RegionData child : region.getChildrenRegions()) {
                this.children.add(new RegionSimpleData(child));
            }
        }
        this.fillLinks = region.getFillLinks();
        if (region.getDataLinks() != null && !region.getDataLinks().isEmpty()) {
            this.links = new ArrayList<LinkSimpleData>();
            for (LinkData link : region.getDataLinks()) {
                LinkSimpleData linkSimpleData = new LinkSimpleData(link);
                if (this.fillLinks != null && this.fillLinks.contains(linkSimpleData.getId())) {
                    linkSimpleData.setRead(true);
                    linkSimpleData.setReadReasion("\u679a\u4e3e\u81ea\u52a8\u586b\u5145\u5355\u5143\u683c\u53ea\u8bfb");
                }
                this.links.add(linkSimpleData);
            }
        }
        if (region.getTabs() != null && !region.getTabs().isEmpty()) {
            this.tabs = region.getTabs();
        }
        this.read = region.isReadOnly();
        this.serl = region.getRegionNumber();
        this.defcnt = region.getDefaultRowCount();
        this.maxcnt = region.getMaxRowCount();
        this.grade = region.getGrade();
        this.lastRowStyle = region.getLastRowStyle();
        this.errors = region.getErrors();
        this.cR = region.getCardRecord();
        if (this.cR != null) {
            this.cR.setSurveyCard(null);
        }
        this.egd = region.getExtentGridData();
        this.eNext = region.getEnterNext();
        if (region.getLinkLevelMap() != null) {
            this.linkLevelMap = region.getLinkLevelMap();
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLeft() {
        return this.left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return this.right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return this.bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isCadd() {
        return this.cadd;
    }

    public void setCadd(boolean cadd) {
        this.cadd = cadd;
    }

    public boolean isCdel() {
        return this.cdel;
    }

    public void setCdel(boolean cdel) {
        this.cdel = cdel;
    }

    public List<RegionSimpleData> getChildren() {
        return this.children;
    }

    public void setChildren(List<RegionSimpleData> children) {
        this.children = children;
    }

    public List<LinkSimpleData> getLinks() {
        return this.links;
    }

    public void setLinks(List<LinkSimpleData> links) {
        this.links = links;
    }

    public List<RegionTab> getTabs() {
        return this.tabs;
    }

    public void setTabs(List<RegionTab> tabs) {
        this.tabs = tabs;
    }

    public boolean isRead() {
        return this.read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public RegionNumber getSerl() {
        return this.serl;
    }

    public void setSerl(RegionNumber serl) {
        this.serl = serl;
    }

    public int getDefcnt() {
        return this.defcnt;
    }

    public void setDefcnt(int defcnt) {
        this.defcnt = defcnt;
    }

    public int getMaxcnt() {
        return this.maxcnt;
    }

    public void setMaxcnt(int maxcnt) {
        this.maxcnt = maxcnt;
    }

    public RegionGradeInfo getGrade() {
        return this.grade;
    }

    public void setGrade(RegionGradeInfo grade) {
        this.grade = grade;
    }

    public RegionEdgeStyle getLastRowStyle() {
        return this.lastRowStyle;
    }

    public void setLastRowStyle(RegionEdgeStyle lastRowStyle) {
        this.lastRowStyle = lastRowStyle;
    }

    public String getErrors() {
        return this.errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public RecordCardData getcR() {
        return this.cR;
    }

    public void setcR(RecordCardData cR) {
        this.cR = cR;
    }

    public RegionExtentGridData getEgd() {
        return this.egd;
    }

    public void setEgd(RegionExtentGridData egd) {
        this.egd = egd;
    }

    public int geteNext() {
        return this.eNext;
    }

    public void seteNext(int eNext) {
        this.eNext = eNext;
    }

    public List<String> getDimensionNames() {
        return this.dimensionNames;
    }

    public void setDimensionNames(List<String> dimensionNames) {
        this.dimensionNames = dimensionNames;
    }

    public List<String> getBizFieldKeys() {
        return this.bizFieldKeys;
    }

    public void setBizFieldKeys(List<String> bizFieldKeys) {
        this.bizFieldKeys = bizFieldKeys;
    }

    public List<String> getFillLinks() {
        return this.fillLinks;
    }

    public void setFillLinks(List<String> fillLinks) {
        this.fillLinks = fillLinks;
    }

    public Map<String, Integer> getLinkLevelMap() {
        return this.linkLevelMap;
    }

    public void setLinkLevelMap(Map<String, Integer> linkLevelMap) {
        this.linkLevelMap = linkLevelMap;
    }
}

