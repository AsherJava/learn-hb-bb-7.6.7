/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.snapshot.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.snapshot.consts.JudgeNameType;
import java.util.List;

public class BatchCreateSnapshotParam {
    private String title;
    private String describe;
    private String taskKey;
    private String formSchemeKey;
    private DimensionCollection dimensionCollection;
    private List<String> formKeys;
    private JudgeNameType judgeNameType;

    public BatchCreateSnapshotParam() {
    }

    public BatchCreateSnapshotParam(String title, String describe, String taskKey, String formSchemeKey, DimensionCollection dimensionCollection, List<String> formKeys, JudgeNameType judgeNameType) {
        this.title = title;
        this.describe = describe;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.dimensionCollection = dimensionCollection;
        this.formKeys = formKeys;
        this.judgeNameType = judgeNameType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public JudgeNameType getJudgeNameType() {
        return this.judgeNameType;
    }

    public void setJudgeNameType(JudgeNameType judgeNameType) {
        this.judgeNameType = judgeNameType;
    }
}

