/*
 * Decompiled with CFR 0.152.
 */
package nr.single.data.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.data.bean.CheckResultNode;

public class CheckNodeList {
    private List<CheckResultNode> entiyNodes;
    private Map<String, CheckResultNode> entityCodesMap;
    private Map<String, CheckResultNode> hasChild;
    private Map<String, CheckResultNode> hasBBRec;
    private Map<String, String> fjdMap;
    private Map<String, CheckResultNode> fjdList;
    private Map<String, CheckResultNode> fjdFjdList;
    private List<CheckResultNode> allNodes;
    private Map<String, CheckResultNode> allEntityCodesMap;

    public void add(CheckResultNode node) {
        this.getEntiyNodes().add(node);
        this.getEntityCodesMap().put(node.getUnitCode(), node);
        this.getAllNodes().add(node);
        this.getAllEntityCodesMap().put(node.getUnitCode(), node);
    }

    public void addOther(CheckResultNode node) {
        if (!this.getAllEntityCodesMap().containsKey(node.getUnitCode())) {
            this.getAllNodes().add(node);
            this.getAllEntityCodesMap().put(node.getUnitCode(), node);
        }
    }

    public Map<String, CheckResultNode> getEntityCodesMap() {
        if (this.entityCodesMap == null) {
            this.entityCodesMap = new HashMap<String, CheckResultNode>();
        }
        return this.entityCodesMap;
    }

    public List<CheckResultNode> getEntiyNodes() {
        if (this.entiyNodes == null) {
            this.entiyNodes = new ArrayList<CheckResultNode>();
        }
        return this.entiyNodes;
    }

    public void setEntiyNodes(List<CheckResultNode> entiyNodes) {
        this.entiyNodes = entiyNodes;
    }

    public List<CheckResultNode> getAllNodes() {
        if (this.allNodes == null) {
            this.allNodes = new ArrayList<CheckResultNode>();
        }
        return this.allNodes;
    }

    public void setAllNodes(List<CheckResultNode> allNodes) {
        this.allNodes = allNodes;
    }

    public Map<String, CheckResultNode> getAllEntityCodesMap() {
        if (this.allEntityCodesMap == null) {
            this.allEntityCodesMap = new HashMap<String, CheckResultNode>();
        }
        return this.allEntityCodesMap;
    }

    public void setAllEntityCodesMap(Map<String, CheckResultNode> allEntityCodesMap) {
        this.allEntityCodesMap = allEntityCodesMap;
    }

    public void setEntityCodesMap(Map<String, CheckResultNode> entityCodesMap) {
        this.entityCodesMap = entityCodesMap;
    }

    public Map<String, CheckResultNode> getHasChild() {
        if (this.hasChild == null) {
            this.hasChild = new HashMap<String, CheckResultNode>();
        }
        return this.hasChild;
    }

    public void setHasChild(Map<String, CheckResultNode> hasChild) {
        this.hasChild = hasChild;
    }

    public Map<String, CheckResultNode> getHasBBRec() {
        if (this.hasBBRec == null) {
            this.hasBBRec = new HashMap<String, CheckResultNode>();
        }
        return this.hasBBRec;
    }

    public void setHasBBRec(Map<String, CheckResultNode> hasBBRec) {
        this.hasBBRec = hasBBRec;
    }

    public Map<String, String> getFjdMap() {
        if (this.fjdMap == null) {
            this.fjdMap = new HashMap<String, String>();
        }
        return this.fjdMap;
    }

    public void setFjdMap(Map<String, String> fjdMap) {
        this.fjdMap = fjdMap;
    }

    public Map<String, CheckResultNode> getFjdList() {
        if (this.fjdList == null) {
            this.fjdList = new HashMap<String, CheckResultNode>();
        }
        return this.fjdList;
    }

    public void setFjdList(Map<String, CheckResultNode> fjdList) {
        this.fjdList = fjdList;
    }

    public Map<String, CheckResultNode> getFjdFjdList() {
        if (this.fjdFjdList == null) {
            this.fjdFjdList = new HashMap<String, CheckResultNode>();
        }
        return this.fjdFjdList;
    }

    public void setFjdFjdList(Map<String, CheckResultNode> fjdFjdList) {
        this.fjdFjdList = fjdFjdList;
    }
}

