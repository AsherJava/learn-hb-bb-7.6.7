/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller;

import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.util.List;

public class FieldContext {
    private IEntityAttribute masterCode;
    private IEntityAttribute masterBizKey;
    private IFMDMAttribute defDWDMField;
    private IFMDMAttribute defBBLXField;
    private List<TaskLinkDefine> taskLinkDefines;
    private IFMDMAttribute DWMC;
    private IFMDMAttribute DWDM;
    private IFMDMAttribute SNDM;
    private IFMDMAttribute XBYS;
    private boolean isDWDMWithBBLX;

    public boolean isDWDMWithBBLX() {
        return this.isDWDMWithBBLX;
    }

    public void setDWDMWithBBLX(boolean isDWDMWithBBLX) {
        this.isDWDMWithBBLX = isDWDMWithBBLX;
    }

    public IEntityAttribute getMasterCode() {
        return this.masterCode;
    }

    public void setMasterCode(IEntityAttribute masterCode) {
        this.masterCode = masterCode;
    }

    public IEntityAttribute getMasterBizKey() {
        return this.masterBizKey;
    }

    public void setMasterBizKey(IEntityAttribute masterBizKey) {
        this.masterBizKey = masterBizKey;
    }

    public IFMDMAttribute getDefDWDMField() {
        return this.defDWDMField;
    }

    public void setDefDWDMField(IFMDMAttribute defDWDMField) {
        this.defDWDMField = defDWDMField;
    }

    public IFMDMAttribute getDefBBLXField() {
        return this.defBBLXField;
    }

    public void setDefBBLXField(IFMDMAttribute defBBLXField) {
        this.defBBLXField = defBBLXField;
    }

    public List<TaskLinkDefine> getTaskLinkDefines() {
        return this.taskLinkDefines;
    }

    public void setTaskLinkDefines(List<TaskLinkDefine> taskLinkDefines) {
        this.taskLinkDefines = taskLinkDefines;
    }

    public IFMDMAttribute getDWMC() {
        return this.DWMC;
    }

    public void setDWMC(IFMDMAttribute dwmc) {
        this.DWMC = dwmc;
    }

    public IFMDMAttribute getDWDM() {
        return this.DWDM;
    }

    public void setDWDM(IFMDMAttribute dwdm) {
        this.DWDM = dwdm;
    }

    public IFMDMAttribute getSNDM() {
        return this.SNDM;
    }

    public void setSNDM(IFMDMAttribute sndm) {
        this.SNDM = sndm;
    }

    public IFMDMAttribute getXBYS() {
        return this.XBYS;
    }

    public void setXBYS(IFMDMAttribute xbys) {
        this.XBYS = xbys;
    }
}

