/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.multcheck2.web.vo;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.common.CheckSource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class MCRunVO
implements Serializable {
    private String task;
    private String period;
    private String scheme;
    private List<String> orgCodes;
    private Map<String, DimensionValue> dimSetMap;
    List<MultcheckItem> items;
    private String formScheme;
    private CheckSource source;
    private String org;

    public MCRunVO() {
    }

    public MCRunVO(MCRunVO vo) {
        this.task = vo.getTask();
        this.period = vo.getPeriod();
        this.scheme = vo.getScheme();
        this.orgCodes = new ArrayList<String>(vo.getOrgCodes());
        this.dimSetMap = new HashMap<String, DimensionValue>(vo.getDimSetMap());
        this.items = CollectionUtils.isEmpty(vo.getItems()) ? new ArrayList<MultcheckItem>() : new ArrayList<MultcheckItem>(vo.getItems());
        this.formScheme = vo.getFormScheme();
        this.source = vo.getSource();
        this.org = vo.getOrg();
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public Map<String, DimensionValue> getDimSetMap() {
        return this.dimSetMap;
    }

    public void setDimSetMap(Map<String, DimensionValue> dimSetMap) {
        this.dimSetMap = dimSetMap;
    }

    public List<MultcheckItem> getItems() {
        return this.items;
    }

    public void setItems(List<MultcheckItem> items) {
        this.items = items;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public CheckSource getSource() {
        return this.source;
    }

    public void setSource(CheckSource source) {
        this.source = source;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}

