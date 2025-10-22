/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.multcheck2.web.vo.MCLabel
 */
package com.jiuqi.nr.datacheck.dataanalyze.vo;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.datacheck.dataanalyze.vo.AnalyzeErrorInfo;
import com.jiuqi.nr.datacheck.dataanalyze.vo.ErrorNode;
import com.jiuqi.nr.datacheck.dataanalyze.vo.ResourceNodeVO;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import java.util.List;
import java.util.Map;

public class AnalyzeResult {
    private Map<String, String> entitys;
    private List<AnalyzeErrorInfo> errors;
    private List<ResourceNodeVO> tabs;
    private List<ITree<ErrorNode>> models;
    private boolean hasEntityLabels;
    private Map<String, List<MCLabel>> entityLabels;
    private Map<String, String> entityLabelValues;

    public Map<String, String> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(Map<String, String> entitys) {
        this.entitys = entitys;
    }

    public List<AnalyzeErrorInfo> getErrors() {
        return this.errors;
    }

    public void setErrors(List<AnalyzeErrorInfo> errors) {
        this.errors = errors;
    }

    public List<ResourceNodeVO> getTabs() {
        return this.tabs;
    }

    public void setTabs(List<ResourceNodeVO> tabs) {
        this.tabs = tabs;
    }

    public List<ITree<ErrorNode>> getModels() {
        return this.models;
    }

    public boolean isHasEntityLabels() {
        return this.hasEntityLabels;
    }

    public void setHasEntityLabels(boolean hasEntityLabels) {
        this.hasEntityLabels = hasEntityLabels;
    }

    public void setModels(List<ITree<ErrorNode>> models) {
        this.models = models;
    }

    public Map<String, List<MCLabel>> getEntityLabels() {
        return this.entityLabels;
    }

    public void setEntityLabels(Map<String, List<MCLabel>> entityLabels) {
        this.entityLabels = entityLabels;
    }

    public Map<String, String> getEntityLabelValues() {
        return this.entityLabelValues;
    }

    public void setEntityLabelValues(Map<String, String> entityLabelValues) {
        this.entityLabelValues = entityLabelValues;
    }
}

