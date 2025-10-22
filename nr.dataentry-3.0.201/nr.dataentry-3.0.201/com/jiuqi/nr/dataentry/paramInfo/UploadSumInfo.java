/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.common.UploadSumNew
 *  com.jiuqi.nr.jtable.params.output.EntityData
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.bpm.common.UploadSumNew;
import com.jiuqi.nr.jtable.params.output.EntityData;
import java.util.Map;

public class UploadSumInfo {
    private String id;
    private String code;
    private String title;
    private boolean leaf;
    private int childCount;
    private UploadSumNew sum;
    private Map<String, String> keyTitle;
    private Map<String, String> keyTitleCustomColor;
    private boolean confirmBeforeUpload;

    public UploadSumInfo(EntityData entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.title = entity.getTitle();
        this.leaf = entity.isLeaf();
        this.childCount = entity.getChildrenCount();
    }

    public UploadSumInfo(String id, String code, String title, int childCount) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.leaf = childCount <= 0;
        this.childCount = childCount;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public UploadSumNew getSum() {
        return this.sum;
    }

    public void setSum(UploadSumNew sum) {
        this.sum = sum;
    }

    public int getChildCount() {
        return this.childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public Map<String, String> getKeyTitle() {
        return this.keyTitle;
    }

    public void setKeyTitle(Map<String, String> keyTitle) {
        this.keyTitle = keyTitle;
    }

    public boolean isConfirmBeforeUpload() {
        return this.confirmBeforeUpload;
    }

    public void setConfirmBeforeUpload(boolean confirmBeforeUpload) {
        this.confirmBeforeUpload = confirmBeforeUpload;
    }

    public Map<String, String> getKeyTitleCustomColor() {
        return this.keyTitleCustomColor;
    }

    public void setKeyTitleCustomColor(Map<String, String> keyTitleCustomColor) {
        this.keyTitleCustomColor = keyTitleCustomColor;
    }
}

