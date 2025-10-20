/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 */
package com.jiuqi.gcreport.formsetting.vo;

import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import java.util.List;

public class DataLinkSettingVO {
    private String id;
    private String title;
    private String viewId;
    private List<BaseDataVO> canSelected;
    private String expression;
    private String fieldTypeSelect;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewId() {
        return this.viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public List<BaseDataVO> getCanSelected() {
        return this.canSelected;
    }

    public void setCanSelected(List<BaseDataVO> canSelected) {
        this.canSelected = canSelected;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getFieldTypeSelect() {
        return this.fieldTypeSelect;
    }

    public void setFieldTypeSelect(String fieldTypeSelect) {
        this.fieldTypeSelect = fieldTypeSelect;
    }
}

