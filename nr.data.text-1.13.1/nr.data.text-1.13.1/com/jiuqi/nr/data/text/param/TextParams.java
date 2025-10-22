/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.text.param;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.text.param.TextFilter;
import com.jiuqi.nr.data.text.param.TextType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.ArrayList;
import java.util.List;

public class TextParams {
    private String formSchemeKey;
    private List<String> formKeys;
    private DimensionCollection dimensionSet;
    private TextType textType = TextType.TEXTTYPE_CSV;
    private String split = ",";
    private List<String> splitGather = new ArrayList<String>();
    private AsyncTaskMonitor monitor;
    private TextFilter filter;
    private String filePath;
    private String secretLevelTitle = "";
    private String secretLevelTitleHigher;
    private int floatImpOpt;
    private boolean isDataMasking = false;

    public String getSecretLevelTitle() {
        return this.secretLevelTitle;
    }

    public void setSecretLevelTitle(String secretLevelTitle) {
        this.secretLevelTitle = secretLevelTitle;
    }

    public String getSecretLevelTitleHigher() {
        return this.secretLevelTitleHigher;
    }

    public void setSecretLevelTitleHigher(String secretLevelTitleHigher) {
        this.secretLevelTitleHigher = secretLevelTitleHigher;
    }

    public List<String> getSplitGather() {
        return this.splitGather;
    }

    public void setSplitGather(List<String> splitGather) {
        this.splitGather = splitGather;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public DimensionCollection getDimensionSet() {
        return this.dimensionSet;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public void setDimensionSet(DimensionCollection dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public TextType getTextType() {
        return this.textType;
    }

    public String getSplit() {
        return this.split;
    }

    public AsyncTaskMonitor getMonitor() {
        return this.monitor;
    }

    public TextFilter getFilter() {
        return this.filter;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setTextType(TextType textType) {
        this.textType = textType;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public void setMonitor(AsyncTaskMonitor monitor) {
        this.monitor = monitor;
    }

    public void setFilter(TextFilter filter) {
        this.filter = filter;
    }

    public int getFloatImpOpt() {
        return this.floatImpOpt;
    }

    public void setFloatImpOpt(int floatImpOpt) {
        this.floatImpOpt = floatImpOpt;
    }

    public TextParams(String formSchemeKey, List<String> formKeys, DimensionCollection dimensionSet, TextType textType, String split, AsyncTaskMonitor monitor, TextFilter filter) {
        this.formSchemeKey = formSchemeKey;
        this.formKeys = formKeys;
        this.dimensionSet = dimensionSet;
        this.textType = textType;
        this.split = split;
        this.monitor = monitor;
        this.filter = filter;
    }

    public TextParams() {
    }

    public void check() {
        if (this.getDimensionSet() == null || this.getFormKeys() == null || this.getFormKeys().isEmpty() || this.getFormSchemeKey() == null || this.getFormSchemeKey().equals("")) {
            throw new IllegalArgumentException("\u53c2\u6570\u4e3a\u7a7a,\u8bf7\u68c0\u67e5.");
        }
    }

    public boolean isDataMasking() {
        return this.isDataMasking;
    }

    public void setDataMasking(boolean dataMasking) {
        this.isDataMasking = dataMasking;
    }
}

