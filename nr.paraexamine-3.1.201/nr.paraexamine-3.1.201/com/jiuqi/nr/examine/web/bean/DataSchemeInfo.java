/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.examine.web.bean;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.examine.web.bean.TaskInfo;
import java.util.ArrayList;
import java.util.List;

public class DataSchemeInfo {
    private String dataSchemeKey;
    private String dataSchemeTitle;
    List<TaskInfo> taskInfos;

    public DataSchemeInfo() {
    }

    public DataSchemeInfo(DataScheme dataScheme, List<TaskDefine> taskDefines) {
        this.dataSchemeKey = dataScheme.getKey();
        this.dataSchemeTitle = dataScheme.getTitle();
        ArrayList<TaskInfo> taskInfoList = new ArrayList<TaskInfo>();
        for (TaskDefine taskDefine : taskDefines) {
            taskInfoList.add(new TaskInfo(taskDefine));
        }
        this.taskInfos = taskInfoList;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataSchemeTitle() {
        return this.dataSchemeTitle;
    }

    public void setDataSchemeTitle(String dataSchemeTitle) {
        this.dataSchemeTitle = dataSchemeTitle;
    }

    public List<TaskInfo> getTaskInfos() {
        return this.taskInfos;
    }

    public void setTaskInfos(List<TaskInfo> taskInfos) {
        this.taskInfos = taskInfos;
    }
}

