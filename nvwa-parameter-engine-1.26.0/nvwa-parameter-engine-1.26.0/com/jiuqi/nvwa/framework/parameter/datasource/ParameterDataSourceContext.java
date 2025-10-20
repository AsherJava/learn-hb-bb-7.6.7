/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource;

import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;

public class ParameterDataSourceContext {
    private ParameterModel model;
    private ParameterCalculator calculator;
    private PageInfo pageInfo;
    private boolean showPathInSearchResult = false;

    public ParameterDataSourceContext(ParameterModel model, ParameterCalculator env) {
        this.model = model;
        this.calculator = env;
    }

    public String getUserId() {
        return this.calculator.getUserId();
    }

    public ParameterModel getModel() {
        return this.model;
    }

    public ParameterCalculator getCalculator() {
        return this.calculator;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setShowPathInSearchResult(boolean showPathInSearchResult) {
        this.showPathInSearchResult = showPathInSearchResult;
    }

    public boolean isShowPathInSearchResult() {
        return this.showPathInSearchResult;
    }

    public static class PageInfo {
        public int startRow = 1;
        public int recordSize = 100;
        public int pageIndex = 0;

        public PageInfo(int startRow, int recordSize) {
            this.startRow = startRow;
            this.recordSize = recordSize;
        }
    }
}

