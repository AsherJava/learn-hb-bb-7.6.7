/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.monitor.api.vo.execute;

import com.jiuqi.gcreport.monitor.api.inf.RouterRedirect;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonitorShowDataVO {
    private String orgId;
    private String parentId;
    private String orgCode;
    private String orgTitle;
    private String label;
    private Map<String, Boolean> isLinkMap;
    private Map<String, Integer> dataMap;
    private Map<String, String> dataTitleMap;
    private Map<String, Integer> totalDataMap;
    private Map<String, Integer> totalDataTitleMap;
    private Map<String, String> colorMap;
    private RouterRedirect url;
    private List<MonitorShowDataVO> children;

    public MonitorShowDataVO() {
    }

    public MonitorShowDataVO(GcOrgCacheVO org) {
        this.setLabel(org.getTitle());
        this.setOrgId(org.getId());
        this.setParentId(org.getParentId());
        this.setOrgCode(org.getCode());
        this.setOrgTitle(org.getTitle());
        this.setIsLinkMap(new HashMap<String, Boolean>());
        this.setDataMap(new HashMap<String, Integer>());
        this.setTotalDataMap(new HashMap<String, Integer>());
        this.setDataTitleMap(new HashMap<String, String>());
        this.setTotalDataTitleMap(new HashMap<String, Integer>());
        this.setColorMap(new HashMap<String, String>());
        this.setChildren(new ArrayList<MonitorShowDataVO>());
        if (org.getChildren() != null && org.getChildren().size() > 0) {
            this.setChildren(org.getChildren().stream().map(vo -> new MonitorShowDataVO((GcOrgCacheVO)vo)).collect(Collectors.toList()));
        }
    }

    public Map<String, String> getDataTitleMap() {
        return this.dataTitleMap;
    }

    public void setDataTitleMap(Map<String, String> dataTitleMap) {
        this.dataTitleMap = dataTitleMap;
    }

    public Map<String, Integer> getTotalDataTitleMap() {
        return this.totalDataTitleMap;
    }

    public void setTotalDataTitleMap(Map<String, Integer> totalDataTitleMap) {
        this.totalDataTitleMap = totalDataTitleMap;
    }

    public Map<String, Integer> getTotalDataMap() {
        return this.totalDataMap;
    }

    public void setTotalDataMap(Map<String, Integer> totalDataMap) {
        this.totalDataMap = totalDataMap;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public Map<String, Integer> getDataMap() {
        return this.dataMap;
    }

    public void setDataMap(Map<String, Integer> dataMap) {
        this.dataMap = dataMap;
    }

    public Map<String, String> getColorMap() {
        return this.colorMap;
    }

    public void setColorMap(Map<String, String> colorMap) {
        this.colorMap = colorMap;
    }

    public RouterRedirect getUrl() {
        return this.url;
    }

    public void setUrl(RouterRedirect url) {
        this.url = url;
    }

    public List<MonitorShowDataVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<MonitorShowDataVO> children) {
        this.children = children;
    }
}

