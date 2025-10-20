/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlsetting;

import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingBaseDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingZbAttributeVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.TaskAndSchemeMapping;
import java.util.List;
import javax.validation.constraints.NotNull;

public class SameCtrlChagSettingOptionVO {
    private String id;
    @NotNull(message="\u4efb\u52a1Id\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u4efb\u52a1Id\u4e0d\u80fd\u4e3a\u7a7a") String taskId;
    @NotNull(message="\u62a5\u8868\u65b9\u6848Id\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u62a5\u8868\u65b9\u6848Id\u4e0d\u80fd\u4e3a\u7a7a") String schemeId;
    private List<SameCtrlChagSettingZbAttributeVO> zbAttributes;
    private SameCtrlChagSettingBaseDataVO netProfitSubject;
    private SameCtrlChagSettingBaseDataVO undividendProfitSubject;
    private List<TaskAndSchemeMapping> taskAndSchemeMappings;
    private List<SameCtrlChagSettingBaseDataVO> disposalScenes;
    private Boolean enableSameCtr;
    private Boolean enableInvestDisposeCopy;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public List<SameCtrlChagSettingZbAttributeVO> getZbAttributes() {
        return this.zbAttributes;
    }

    public void setZbAttributes(List<SameCtrlChagSettingZbAttributeVO> zbAttributes) {
        this.zbAttributes = zbAttributes;
    }

    public SameCtrlChagSettingBaseDataVO getNetProfitSubject() {
        return this.netProfitSubject;
    }

    public void setNetProfitSubject(SameCtrlChagSettingBaseDataVO netProfitSubject) {
        this.netProfitSubject = netProfitSubject;
    }

    public SameCtrlChagSettingBaseDataVO getUndividendProfitSubject() {
        return this.undividendProfitSubject;
    }

    public void setUndividendProfitSubject(SameCtrlChagSettingBaseDataVO undividendProfitSubject) {
        this.undividendProfitSubject = undividendProfitSubject;
    }

    public List<TaskAndSchemeMapping> getTaskAndSchemeMappings() {
        return this.taskAndSchemeMappings;
    }

    public void setTaskAndSchemeMappings(List<TaskAndSchemeMapping> taskAndSchemeMappings) {
        this.taskAndSchemeMappings = taskAndSchemeMappings;
    }

    public List<SameCtrlChagSettingBaseDataVO> getDisposalScenes() {
        return this.disposalScenes;
    }

    public void setDisposalScenes(List<SameCtrlChagSettingBaseDataVO> disposalScenes) {
        this.disposalScenes = disposalScenes;
    }

    public Boolean getEnableSameCtr() {
        return this.enableSameCtr;
    }

    public void setEnableSameCtr(Boolean enableSameCtr) {
        this.enableSameCtr = enableSameCtr;
    }

    public Boolean getEnableInvestDisposeCopy() {
        return this.enableInvestDisposeCopy;
    }

    public void setEnableInvestDisposeCopy(Boolean enableInvestDisposeCopy) {
        this.enableInvestDisposeCopy = enableInvestDisposeCopy;
    }
}

