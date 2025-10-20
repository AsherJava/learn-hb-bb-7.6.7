/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.vo;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.field.NodeIconVO;
import java.util.List;
import java.util.Map;

public class GcOrgTreeVO {
    private List<GcOrgCacheVO> tree;
    private Map<String, NodeIconVO> nodeIconMap;

    public List<GcOrgCacheVO> getTree() {
        return this.tree;
    }

    public void setTree(List<GcOrgCacheVO> tree) {
        this.tree = tree;
    }

    public Map<String, NodeIconVO> getNodeIconMap() {
        return this.nodeIconMap;
    }

    public void setNodeIconMap(Map<String, NodeIconVO> nodeIconMap) {
        this.nodeIconMap = nodeIconMap;
    }
}

