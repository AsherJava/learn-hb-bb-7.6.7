/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

import com.jiuqi.budget.domain.BudCalChartLink;
import com.jiuqi.budget.domain.BudCalChartNodeData;
import com.jiuqi.budget.domain.BudCalChartNodeItem;
import java.util.List;
import java.util.Map;

public class BudCalChartNodes {
    private String rootId;
    private List<BudCalChartNodeItem<BudCalChartNodeData>> nodes;
    private List<BudCalChartLink> links;
    private List<BudCalChartLink> lines;
    private Map<String, String> relations;
    private String showLevel;
    private Integer width;
    private Integer height;
    private String imageId;

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getImageId() {
        return this.imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getRootId() {
        return this.rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public List<BudCalChartLink> getLinks() {
        return this.links;
    }

    public void setLinks(List<BudCalChartLink> links) {
        this.links = links;
        this.lines = links;
    }

    public List<BudCalChartNodeItem<BudCalChartNodeData>> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<BudCalChartNodeItem<BudCalChartNodeData>> nodes) {
        this.nodes = nodes;
    }

    public Map<String, String> getRelations() {
        return this.relations;
    }

    public void setRelations(Map<String, String> relations) {
        this.relations = relations;
    }

    public String getShowLevel() {
        return this.showLevel;
    }

    public void setShowLevel(String showLevel) {
        this.showLevel = showLevel;
    }

    public List<BudCalChartLink> getLines() {
        return this.lines;
    }

    public void setLines(List<BudCalChartLink> lines) {
        this.lines = lines;
    }

    public String toString() {
        return "BudCalChartNodes [rootId=" + this.rootId + ", nodes=" + this.nodes + ", links=" + this.links + ", lines=" + this.lines + ", relations=" + this.relations + ", showLevel=" + this.showLevel + "]";
    }
}

