/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.efdcdatacheck.client.vo;

import com.jiuqi.gcreport.efdcdatacheck.client.vo.ConversionSystemItemInfoVO;
import java.util.List;

public class TaskFormGroupFieldsInfoVO {
    private String title;
    private List<ConversionSystemItemInfoVO> dataList;
    private boolean childFlag = false;
    private List<TaskFormGroupFieldsInfoVO> children;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ConversionSystemItemInfoVO> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<ConversionSystemItemInfoVO> dataList) {
        this.dataList = dataList;
    }

    public boolean isChildFlag() {
        return this.childFlag;
    }

    public void setChildFlag(boolean childFlag) {
        this.childFlag = childFlag;
    }

    public List<TaskFormGroupFieldsInfoVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<TaskFormGroupFieldsInfoVO> children) {
        this.children = children;
    }
}

