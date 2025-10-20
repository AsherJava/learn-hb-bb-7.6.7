/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.monitor.api.vo.show;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorTableVO {
    private String parentId;
    private String orgId;
    private String orgTitle;
    private GcOrgCacheVO orgCacheVO;
    private Map<String, Boolean> isLinkMap;
    private Map<String, String> titleMap;
    private Map<String, Integer> stateMap;
    private Map<String, Integer> doneNumMap;
    private Map<String, Integer> totalNumMap;
    private Map<String, Integer> directDoneNumMap;
    private Map<String, Integer> directTotalNumMap;
    private Map<String, String> colorMap;
    private List<MonitorTableVO> children;

    public MonitorTableVO(GcOrgCacheVO org) {
        this.setOrgId(org.getId());
        this.setOrgCacheVO(org);
        this.setParentId(org.getParentId());
        this.setOrgTitle(org.getTitle());
        this.setIsLinkMap(new HashMap<String, Boolean>());
        this.setStateMap(new HashMap<String, Integer>());
        this.setTitleMap(new HashMap<String, String>());
        this.setTotalNumMap(new HashMap<String, Integer>());
        this.setDoneNumMap(new HashMap<String, Integer>());
        this.setDirectTotalNumMap(new HashMap<String, Integer>());
        this.setDirectDoneNumMap(new HashMap<String, Integer>());
        this.setColorMap(new HashMap<String, String>());
        this.setChildren(new ArrayList<MonitorTableVO>());
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public Map<String, Boolean> getIsLinkMap() {
        return this.isLinkMap;
    }

    public void setIsLinkMap(Map<String, Boolean> isLinkMap) {
        this.isLinkMap = isLinkMap;
    }

    public Map<String, String> getTitleMap() {
        return this.titleMap;
    }

    public void setTitleMap(Map<String, String> titleMap) {
        this.titleMap = titleMap;
    }

    public Map<String, Integer> getStateMap() {
        return this.stateMap;
    }

    public void setStateMap(Map<String, Integer> stateMap) {
        this.stateMap = stateMap;
    }

    public Map<String, Integer> getDoneNumMap() {
        return this.doneNumMap;
    }

    public void setDoneNumMap(Map<String, Integer> doneNumMap) {
        this.doneNumMap = doneNumMap;
    }

    public Map<String, Integer> getTotalNumMap() {
        return this.totalNumMap;
    }

    public void setTotalNumMap(Map<String, Integer> totalNumMap) {
        this.totalNumMap = totalNumMap;
    }

    public Map<String, String> getColorMap() {
        return this.colorMap;
    }

    public void setColorMap(Map<String, String> colorMap) {
        this.colorMap = colorMap;
    }

    public List<MonitorTableVO> getChildren() {
        if (null == this.children) {
            this.children = new ArrayList<MonitorTableVO>();
        }
        return this.children;
    }

    public Map<String, Integer> getDirectDoneNumMap() {
        return this.directDoneNumMap;
    }

    public void setDirectDoneNumMap(Map<String, Integer> directDoneNumMap) {
        this.directDoneNumMap = directDoneNumMap;
    }

    public Map<String, Integer> getDirectTotalNumMap() {
        return this.directTotalNumMap;
    }

    public void setDirectTotalNumMap(Map<String, Integer> directTotalNumMap) {
        this.directTotalNumMap = directTotalNumMap;
    }

    public void setChildren(List<MonitorTableVO> children) {
        this.children = children;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public GcOrgCacheVO getOrgCacheVO() {
        return this.orgCacheVO;
    }

    public void setOrgCacheVO(GcOrgCacheVO orgCacheVO) {
        this.orgCacheVO = orgCacheVO;
    }
}

