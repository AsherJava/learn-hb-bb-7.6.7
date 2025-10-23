/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.multcheck2.web.result.MOrgTreeNode;
import com.jiuqi.nr.multcheck2.web.result.MultiplSchemeInfo;
import com.jiuqi.nr.multcheck2.web.result.ResultItemPMVO;
import com.jiuqi.nr.multcheck2.web.result.SchemeTreeNode;
import java.util.List;
import java.util.Map;

public class MultiplScheme {
    private String desc;
    private List<ITree<MOrgTreeNode>> orgTree;
    private Map<String, MultiplSchemeInfo> schemeInfoMap;
    private Map<String, ResultItemPMVO> itemPM;
    private List<ITree<SchemeTreeNode>> schemeTree;
    private int failed;

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ITree<MOrgTreeNode>> getOrgTree() {
        return this.orgTree;
    }

    public void setOrgTree(List<ITree<MOrgTreeNode>> orgTree) {
        this.orgTree = orgTree;
    }

    public Map<String, MultiplSchemeInfo> getSchemeInfoMap() {
        return this.schemeInfoMap;
    }

    public void setSchemeInfoMap(Map<String, MultiplSchemeInfo> schemeInfoMap) {
        this.schemeInfoMap = schemeInfoMap;
    }

    public Map<String, ResultItemPMVO> getItemPM() {
        return this.itemPM;
    }

    public void setItemPM(Map<String, ResultItemPMVO> itemPM) {
        this.itemPM = itemPM;
    }

    public List<ITree<SchemeTreeNode>> getSchemeTree() {
        return this.schemeTree;
    }

    public void setSchemeTree(List<ITree<SchemeTreeNode>> schemeTree) {
        this.schemeTree = schemeTree;
    }

    public int getFailed() {
        return this.failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }
}

