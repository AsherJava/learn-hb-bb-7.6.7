/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datacheck.dataanalyze.vo.ErrorNode
 *  com.jiuqi.nr.datacheck.dataanalyze.vo.ResourceNodeVO
 */
package com.jiuqi.nr.singlequery.multcheck.vo;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.datacheck.dataanalyze.vo.ErrorNode;
import com.jiuqi.nr.datacheck.dataanalyze.vo.ResourceNodeVO;
import com.jiuqi.nr.singlequery.multcheck.vo.SingleQueryErrorInfo;
import java.util.List;
import java.util.Map;

public class SingleQueryResult {
    private Map<String, String> entitys;
    private List<SingleQueryErrorInfo> errors;
    private List<ResourceNodeVO> tabs;
    private List<ITree<ErrorNode>> models;

    public Map<String, String> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(Map<String, String> entitys) {
        this.entitys = entitys;
    }

    public List<SingleQueryErrorInfo> getErrors() {
        return this.errors;
    }

    public void setErrors(List<SingleQueryErrorInfo> errors) {
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

    public void setModels(List<ITree<ErrorNode>> models) {
        this.models = models;
    }
}

