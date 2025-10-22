/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  org.json.JSONObject
 */
package com.jiuiqi.nr.unit.treebase.context.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.List;
import org.json.JSONObject;

public class UnitTreeFilterCondition {
    private static final String FILTER_CONDITION_KEY = "filterCondition";
    private List<String> bblx;
    private List<String> upload;
    private List<String> tags;
    private String uselector;

    public List<String> getBblx() {
        return this.bblx;
    }

    public void setBblx(List<String> bblx) {
        this.bblx = bblx;
    }

    public List<String> getUpload() {
        return this.upload;
    }

    public void setUpload(List<String> upload) {
        this.upload = upload;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getUselector() {
        return this.uselector;
    }

    public void setUselector(String uselector) {
        this.uselector = uselector;
    }

    public boolean isValidFilter() {
        return this.bblx != null && !this.bblx.isEmpty() || this.upload != null && !this.upload.isEmpty() || this.tags != null && !this.tags.isEmpty() || StringUtils.isNotEmpty((String)this.uselector);
    }

    public static UnitTreeFilterCondition translate2FilterCondition(JSONObject customVariable) {
        return (UnitTreeFilterCondition)JavaBeanUtils.toJavaBean((JSONObject)customVariable, (String)FILTER_CONDITION_KEY, UnitTreeFilterCondition.class);
    }
}

