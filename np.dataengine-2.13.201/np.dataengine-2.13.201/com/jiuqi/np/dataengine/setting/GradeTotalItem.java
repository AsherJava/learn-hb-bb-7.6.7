/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.setting;

import com.jiuqi.np.dataengine.common.GradeLinkItem;
import java.util.ArrayList;

public class GradeTotalItem {
    private GradeLinkItem gradeLink;
    private Integer columnIndex;
    private boolean needEnd0;
    private ArrayList<Integer> gradeSetting;

    public GradeTotalItem(GradeLinkItem linkDefine, int columnIndex, ArrayList<Integer> gradeSetting) {
        this.gradeLink = linkDefine;
        this.columnIndex = columnIndex;
        this.gradeSetting = gradeSetting;
    }

    public GradeLinkItem getGradeLink() {
        return this.gradeLink;
    }

    public void setGradeLink(GradeLinkItem gradeLink) {
        this.gradeLink = gradeLink;
    }

    public ArrayList<Integer> getGradeSetting() {
        return this.gradeSetting;
    }

    public void setGradeSetting(ArrayList<Integer> gradeSetting) {
        this.gradeSetting = gradeSetting;
    }

    public Integer getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public boolean isNeedEnd0() {
        return this.needEnd0;
    }

    public void setNeedEnd0(boolean needEnd0) {
        this.needEnd0 = needEnd0;
    }
}

