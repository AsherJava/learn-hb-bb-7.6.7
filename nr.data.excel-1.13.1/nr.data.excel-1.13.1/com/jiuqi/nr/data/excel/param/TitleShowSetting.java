/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.excel.obj.DWShow;
import com.jiuqi.nr.data.excel.obj.FormShow;
import com.jiuqi.nr.data.excel.obj.SplitChar;

public class TitleShowSetting {
    private FormShow[] formShows;
    private int formShowIndex = 0;
    private DWShow[] dwShows;
    private int dwShowIndex = 0;
    private SplitChar splitChar;
    private Boolean simplifyExpFileHierarchy;

    public TitleShowSetting addFormShow(FormShow formShow) {
        if (this.formShows == null) {
            this.formShows = new FormShow[3];
        }
        this.formShows[this.formShowIndex++] = formShow;
        return this;
    }

    public TitleShowSetting addDwShow(DWShow dwShow) {
        if (this.dwShows == null) {
            this.dwShows = new DWShow[4];
        }
        this.dwShows[this.dwShowIndex++] = dwShow;
        return this;
    }

    public void setSplitChar(SplitChar splitChar) {
        this.splitChar = splitChar;
    }

    public String getFormShowSetting() {
        if (this.formShows == null) {
            return null;
        }
        StringBuilder s = new StringBuilder("[");
        boolean flag = false;
        for (FormShow formShow : this.formShows) {
            if (formShow == null) continue;
            s.append(formShow.getKey()).append(",");
            flag = true;
        }
        if (flag) {
            s.setLength(s.length() - 1);
        }
        s.append("]");
        return s.toString();
    }

    public String getDwShowSetting() {
        if (this.dwShows == null) {
            return null;
        }
        StringBuilder s = new StringBuilder("[");
        boolean flag = false;
        for (DWShow dwShow : this.dwShows) {
            if (dwShow == null) continue;
            s.append(dwShow.getKey()).append(",");
            flag = true;
        }
        if (flag) {
            s.setLength(s.length() - 1);
        }
        s.append("]");
        return s.toString();
    }

    public String getSplitCharSetting() {
        if (this.splitChar == null) {
            return null;
        }
        return this.splitChar.getKey();
    }

    public Boolean getSimplifyExpFileHierarchy() {
        return this.simplifyExpFileHierarchy;
    }

    public void setSimplifyExpFileHierarchy(Boolean simplifyExpFileHierarchy) {
        this.simplifyExpFileHierarchy = simplifyExpFileHierarchy;
    }
}

