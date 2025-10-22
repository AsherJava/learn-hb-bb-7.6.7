/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.attachment.param;

import com.jiuqi.nr.data.attachment.param.FileFilter;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public class ExpParams {
    private DimensionCollection dimensions;
    private List<String> formKeys;
    private String formSchemeKey;
    private FileFilter filter;
    private String filePath;

    public DimensionCollection getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(DimensionCollection dimensions) {
        this.dimensions = dimensions;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public FileFilter getFilter() {
        return this.filter;
    }

    public void setFilter(FileFilter filter) {
        this.filter = filter;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

