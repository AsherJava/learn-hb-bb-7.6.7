/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlsetting;

import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingBaseDataVO;
import java.util.List;

public class SameCtrlChagSubjectMapVO {
    private String id;
    private String schemeMappingId;
    private SameCtrlChagSettingBaseDataVO currYearSubject;
    private List<SameCtrlChagSettingBaseDataVO> lastYearSubjects;

    public SameCtrlChagSettingBaseDataVO getCurrYearSubject() {
        return this.currYearSubject;
    }

    public void setCurrYearSubject(SameCtrlChagSettingBaseDataVO currYearSubject) {
        this.currYearSubject = currYearSubject;
    }

    public List<SameCtrlChagSettingBaseDataVO> getLastYearSubjects() {
        return this.lastYearSubjects;
    }

    public void setLastYearSubjects(List<SameCtrlChagSettingBaseDataVO> lastYearSubjects) {
        this.lastYearSubjects = lastYearSubjects;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchemeMappingId() {
        return this.schemeMappingId;
    }

    public void setSchemeMappingId(String schemeMappingId) {
        this.schemeMappingId = schemeMappingId;
    }
}

