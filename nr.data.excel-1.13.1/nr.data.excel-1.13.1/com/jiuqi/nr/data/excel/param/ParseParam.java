/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.excel.service.internal.IDimensionDataAdapterProvider;
import com.jiuqi.nr.data.excel.service.internal.IFormFinder;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;

public class ParseParam {
    private String directory;
    private String fileName;
    private String sheetName;
    private FormSchemeDefine formSchemeDefine;
    private IDimensionDataAdapterProvider dimensionDataAdapterProvider;
    private IFormFinder formFinder;
    private String splitCharSetting;
    private String formShowSetting;
    private String dwShowSetting;

    public String getSplitCharSetting() {
        return this.splitCharSetting;
    }

    public void setSplitCharSetting(String splitCharSetting) {
        this.splitCharSetting = splitCharSetting;
    }

    public String getFormShowSetting() {
        return this.formShowSetting;
    }

    public void setFormShowSetting(String formShowSetting) {
        this.formShowSetting = formShowSetting;
    }

    public String getDwShowSetting() {
        return this.dwShowSetting;
    }

    public void setDwShowSetting(String dwShowSetting) {
        this.dwShowSetting = dwShowSetting;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public void setFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    public IDimensionDataAdapterProvider getDimensionDataAdapterProvider() {
        return this.dimensionDataAdapterProvider;
    }

    public void setDimensionDataAdapterProvider(IDimensionDataAdapterProvider dimensionDataAdapterProvider) {
        this.dimensionDataAdapterProvider = dimensionDataAdapterProvider;
    }

    public IFormFinder getFormFinder() {
        return this.formFinder;
    }

    public void setFormFinder(IFormFinder formFinder) {
        this.formFinder = formFinder;
    }
}

