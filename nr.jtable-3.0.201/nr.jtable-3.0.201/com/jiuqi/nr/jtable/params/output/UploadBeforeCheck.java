/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadBeforeCheck
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int unPassEntityNum;
    private int unPassFormsNum;
    private int unPassFormGroupNum;
    private Map<String, String> resultEntity;
    private Map<String, List<String>> resultForms;
    private Map<String, List<String>> resultFormGroup;
    private String asyncTaskId;
    private Map<String, List<Integer>> needCommentErrorStatus;
    private Map<String, List<Integer>> canIgnoreErrorStatus;
    private List<String> needCheckForms = new ArrayList<String>();

    public UploadBeforeCheck() {
        this.resultEntity = new HashMap<String, String>();
        this.resultForms = new HashMap<String, List<String>>();
        this.resultFormGroup = new HashMap<String, List<String>>();
    }

    public int getUnPassEntityNum() {
        return this.unPassEntityNum;
    }

    public void setUnPassEntityNum(int unPassEntityNum) {
        this.unPassEntityNum = unPassEntityNum;
    }

    public Map<String, String> getResultEntity() {
        return this.resultEntity;
    }

    public void setResultEntity(Map<String, String> resultEntity) {
        this.resultEntity = resultEntity;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public Map<String, List<Integer>> getNeedCommentErrorStatus() {
        return this.needCommentErrorStatus;
    }

    public void setNeedCommentErrorStatus(Map<String, List<Integer>> needCommentErrorStatus) {
        this.needCommentErrorStatus = needCommentErrorStatus;
    }

    public Map<String, List<Integer>> getCanIgnoreErrorStatus() {
        return this.canIgnoreErrorStatus;
    }

    public void setCanIgnoreErrorStatus(Map<String, List<Integer>> canIgnoreErrorStatus) {
        this.canIgnoreErrorStatus = canIgnoreErrorStatus;
    }

    public Map<String, List<String>> getResultForms() {
        return this.resultForms;
    }

    public void setResultForms(Map<String, List<String>> resultForms) {
        this.resultForms = resultForms;
    }

    public int getUnPassFormsNum() {
        return this.unPassFormsNum;
    }

    public void setUnPassFormsNum(int unPassFormsNum) {
        this.unPassFormsNum = unPassFormsNum;
    }

    public Map<String, List<String>> getResultFormGroup() {
        return this.resultFormGroup;
    }

    public void setResultFormGroup(Map<String, List<String>> resultFormGroup) {
        this.resultFormGroup = resultFormGroup;
    }

    public int getUnPassFormGroupNum() {
        return this.unPassFormGroupNum;
    }

    public void setUnPassFormGroupNum(int unPassFormGroupNum) {
        this.unPassFormGroupNum = unPassFormGroupNum;
    }

    public List<String> getNeedCheckForms() {
        return this.needCheckForms;
    }

    public void setNeedCheckForms(List<String> needCheckForms) {
        this.needCheckForms = needCheckForms;
    }
}

