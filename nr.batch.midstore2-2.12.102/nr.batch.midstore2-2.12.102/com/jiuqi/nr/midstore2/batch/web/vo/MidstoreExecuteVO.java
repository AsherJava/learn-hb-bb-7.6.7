/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 */
package com.jiuqi.nr.midstore2.batch.web.vo;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.midstore2.batch.web.vo.MidstoreSchemeVO;
import com.jiuqi.nr.midstore2.batch.web.vo.TaskTreeNodeVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MidstoreExecuteVO {
    private String errorMsg;
    private List<ITree<TaskTreeNodeVO>> taskTree;
    private Map<String, List<MidstoreSchemeVO>> schemes;
    private List<TaskDefine> tasks;
    private String begin;
    private String end;
    private List<String> periods;
    private String curPeriod;
    private List<Map<String, String>> customArr;
    private int periodType;
    private List<IEntityDefine> entitys;

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<ITree<TaskTreeNodeVO>> getTaskTree() {
        return this.taskTree;
    }

    public void setTaskTree(List<ITree<TaskTreeNodeVO>> taskTree) {
        this.taskTree = taskTree;
    }

    public Map<String, List<MidstoreSchemeVO>> getSchemes() {
        return this.schemes;
    }

    public void setSchemes(Map<String, List<MidstoreSchemeVO>> schemes) {
        this.schemes = schemes;
    }

    public List<TaskDefine> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<TaskDefine> tasks) {
        this.tasks = tasks;
    }

    public String getBegin() {
        return this.begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<String> getPeriods() {
        return this.periods;
    }

    public void setPeriods(List<String> periods) {
        this.periods = periods;
    }

    public String getCurPeriod() {
        return this.curPeriod;
    }

    public void setCurPeriod(String curPeriod) {
        this.curPeriod = curPeriod;
    }

    public List<Map<String, String>> getCustomArr() {
        return this.customArr;
    }

    public void setCustomArr(List<Map<String, String>> customArr) {
        this.customArr = customArr;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public List<IEntityDefine> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<IEntityDefine> entitys) {
        this.entitys = entitys;
    }

    public void addEntity(IEntityDefine entity) {
        if (this.entitys == null) {
            this.entitys = new ArrayList<IEntityDefine>();
        }
        this.entitys.add(entity);
    }
}

