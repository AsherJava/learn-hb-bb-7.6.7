/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.examine.task;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.examine.common.ExamineEnvironment;
import com.jiuqi.nr.examine.common.ParaType;
import java.io.Serializable;

public class ExamineTask
implements Serializable {
    private static final long serialVersionUID = -3170919178399767216L;
    private ParaType paraType;
    private int taskType;
    private String key;
    private String checkInfoKey;
    private boolean needWriteDB = true;
    private ExamineEnvironment env;

    public ExamineTask() {
        this.checkInfoKey = UUIDUtils.getKey();
    }

    public ExamineTask(ExamineTask task) {
        this.paraType = task.getParaType();
        this.taskType = task.getTaskType();
        this.key = task.getKey();
        this.env = task.getEnv();
        this.needWriteDB = task.isNeedWriteDB();
        this.checkInfoKey = task.getCheckInfoKey();
    }

    public ParaType getParaType() {
        return this.paraType;
    }

    public void setParaType(ParaType paraType) {
        this.paraType = paraType;
    }

    public int getTaskType() {
        return this.taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ExamineEnvironment getEnv() {
        return this.env;
    }

    public void setEnv(ExamineEnvironment env) {
        this.env = env;
    }

    public String getCheckInfoKey() {
        return this.checkInfoKey;
    }

    public void setCheckInfoKey(String checkInfoKey) {
        this.checkInfoKey = checkInfoKey;
    }

    public boolean isNeedWriteDB() {
        return this.needWriteDB;
    }

    public void setNeedWriteDB(boolean needWriteDB) {
        this.needWriteDB = needWriteDB;
    }
}

