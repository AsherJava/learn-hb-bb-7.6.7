/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monthcalcscheme.vo;

import java.util.ArrayList;
import java.util.List;

public class MonthCalcSchemeVO {
    private String id;
    private String code;
    private String parentId;
    private String title;
    private String label;
    private String taskId_Y;
    private String taskId_J;
    private String taskId_H;
    private List<String> taskId_N;
    private boolean leafFlag;
    private boolean startFlag;
    private List<MonthCalcSchemeVO> children = new ArrayList<MonthCalcSchemeVO>();

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

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTaskId_Y() {
        return this.taskId_Y;
    }

    public void setTaskId_Y(String taskId_Y) {
        this.taskId_Y = taskId_Y;
    }

    public String getTaskId_J() {
        return this.taskId_J;
    }

    public void setTaskId_J(String taskId_J) {
        this.taskId_J = taskId_J;
    }

    public String getTaskId_H() {
        return this.taskId_H;
    }

    public void setTaskId_H(String taskId_H) {
        this.taskId_H = taskId_H;
    }

    public List<String> getTaskId_N() {
        return this.taskId_N;
    }

    public void setTaskId_N(List<String> taskId_N) {
        this.taskId_N = taskId_N;
    }

    public boolean isLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(boolean leafFlag) {
        this.leafFlag = leafFlag;
    }

    public boolean isStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(boolean startFlag) {
        this.startFlag = startFlag;
    }

    public List<MonthCalcSchemeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<MonthCalcSchemeVO> children) {
        this.children = children;
    }
}

