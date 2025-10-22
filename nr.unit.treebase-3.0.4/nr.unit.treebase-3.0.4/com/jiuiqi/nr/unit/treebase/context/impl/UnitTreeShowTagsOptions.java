/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  org.json.JSONObject
 */
package com.jiuiqi.nr.unit.treebase.context.impl;

import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.List;
import org.json.JSONObject;

public class UnitTreeShowTagsOptions {
    private boolean showNodeTags;
    List<String> showTagKeys;
    private int showTagsPloy;
    private static final String SHOW_TAGS_OPTIONS_KEY = "showTagsOptions";

    public boolean isShowNodeTags() {
        return this.showNodeTags;
    }

    public void setShowNodeTags(boolean showNodeTags) {
        this.showNodeTags = showNodeTags;
    }

    public List<String> getShowTagKeys() {
        return this.showTagKeys;
    }

    public void setShowTagKeys(List<String> showTagKeys) {
        this.showTagKeys = showTagKeys;
    }

    public int getShowTagsPloy() {
        return this.showTagsPloy;
    }

    public void setShowTagsPloy(int showTagsPloy) {
        this.showTagsPloy = showTagsPloy;
    }

    public static UnitTreeShowTagsOptions translate2ShowTagsOptions(JSONObject customVariable) {
        return (UnitTreeShowTagsOptions)JavaBeanUtils.toJavaBean((JSONObject)customVariable, (String)SHOW_TAGS_OPTIONS_KEY, UnitTreeShowTagsOptions.class);
    }
}

