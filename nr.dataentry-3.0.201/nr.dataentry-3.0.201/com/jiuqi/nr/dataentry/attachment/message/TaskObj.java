/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.dataentry.attachment.message;

import com.jiuqi.nr.dataentry.attachment.message.PeriodObj;
import com.jiuqi.nr.dataentry.attachment.message.SceneSubjectInfo;
import com.jiuqi.nr.dataentry.attachment.message.SchemeObj;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.List;

public class TaskObj {
    private String key;
    private String title;
    private List<SchemeObj> schemeObjs;
    private String fromPeriod;
    private String toPeriod;
    private PeriodObj periodObj;
    private String entityId;
    private String DWDimentionName;
    private String DWDimentionTitle;
    private List<SceneSubjectInfo> sceneSubjectInfoList;
    private String dataSchemeKey;
    private boolean openAdjust = false;

    public TaskObj() {
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public TaskObj(TaskDefine taskDefine) {
        if (taskDefine != null) {
            this.key = taskDefine.getKey();
            this.title = taskDefine.getTitle();
            this.fromPeriod = taskDefine.getFromPeriod();
            this.toPeriod = taskDefine.getToPeriod();
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SchemeObj> getSchemeObjs() {
        return this.schemeObjs;
    }

    public void setSchemeObjs(List<SchemeObj> schemeObjs) {
        this.schemeObjs = schemeObjs;
    }

    public PeriodObj getPeriodObj() {
        return this.periodObj;
    }

    public void setPeriodObj(PeriodObj periodObj) {
        this.periodObj = periodObj;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getDWDimentionName() {
        return this.DWDimentionName;
    }

    public void setDWDimentionName(String DWDimentionName) {
        this.DWDimentionName = DWDimentionName;
    }

    public String getDWDimentionTitle() {
        return this.DWDimentionTitle;
    }

    public void setDWDimentionTitle(String DWDimentionTitle) {
        this.DWDimentionTitle = DWDimentionTitle;
    }

    public List<SceneSubjectInfo> getSceneSubjectInfoList() {
        return this.sceneSubjectInfoList;
    }

    public void setSceneSubjectInfoList(List<SceneSubjectInfo> sceneSubjectInfoList) {
        this.sceneSubjectInfoList = sceneSubjectInfoList;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public boolean isOpenAdjust() {
        return this.openAdjust;
    }

    public void setOpenAdjust(boolean openAdjust) {
        this.openAdjust = openAdjust;
    }
}

