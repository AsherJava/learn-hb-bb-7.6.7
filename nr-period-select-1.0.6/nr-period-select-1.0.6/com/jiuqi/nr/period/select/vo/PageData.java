/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.vo;

import com.jiuqi.nr.period.select.page.Page;
import java.util.List;

public class PageData {
    private List<Integer> yearRange;
    private List<Page> pageData;
    private List<String> selectPeriod;
    private String adjust;
    private String selectedTitle;
    private String panelWidth;

    public String getSelectedTitle() {
        return this.selectedTitle;
    }

    public void setSelectedTitle(String selectedTitle) {
        this.selectedTitle = selectedTitle;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }

    public String getPanelWidth() {
        return this.panelWidth;
    }

    public void setPanelWidth(String panelWidth) {
        this.panelWidth = panelWidth;
    }

    public List<String> getSelectPeriod() {
        return this.selectPeriod;
    }

    public void setSelectPeriod(List<String> selectPeriod) {
        this.selectPeriod = selectPeriod;
    }

    public List<Integer> getYearRange() {
        return this.yearRange;
    }

    public void setYearRange(List<Integer> yearRange) {
        this.yearRange = yearRange;
    }

    public List<Page> getPageData() {
        return this.pageData;
    }

    public void setPageData(List<Page> pageData) {
        this.pageData = pageData;
    }
}

