/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.MatchingInfo;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.util.List;

public class CheckConfigurationContent {
    private MatchingInfo matchingInfo;
    private List<String> filterValue;
    private IFMDMAttribute jsyy;
    private IFMDMAttribute xbys;
    private IFMDMAttribute sndm;
    private IFMDMAttribute dwdm;
    private IFMDMAttribute bblx;
    private IFMDMAttribute snbblx;
    private String sndmFormula;
    private EntityViewDefine _JSYYEntityView;
    private EntityViewDefine _XBYSEntityView;

    public String getSndmFormula() {
        return this.sndmFormula;
    }

    public void setSndmFormula(String sndmFormula) {
        this.sndmFormula = sndmFormula;
    }

    public EntityViewDefine GetJSYYEntityView() {
        return this._JSYYEntityView;
    }

    public void SetJSYYEntityView(EntityViewDefine _JSYYEntityView) {
        this._JSYYEntityView = _JSYYEntityView;
    }

    public EntityViewDefine GetXBYSEntityView() {
        return this._XBYSEntityView;
    }

    public void SetXBYSEntityView(EntityViewDefine _XBYSEntityView) {
        this._XBYSEntityView = _XBYSEntityView;
    }

    public MatchingInfo getMatchingInfo() {
        return this.matchingInfo;
    }

    public void setMatchingInfo(MatchingInfo matchingInfo) {
        this.matchingInfo = matchingInfo;
    }

    public List<String> getFilterValue() {
        return this.filterValue;
    }

    public void setFilterValue(List<String> filterValue) {
        this.filterValue = filterValue;
    }

    public IFMDMAttribute getJsyy() {
        return this.jsyy;
    }

    public void setJsyy(IFMDMAttribute jsyy) {
        this.jsyy = jsyy;
    }

    public IFMDMAttribute getXbys() {
        return this.xbys;
    }

    public void setXbys(IFMDMAttribute xbys) {
        this.xbys = xbys;
    }

    public IFMDMAttribute getSndm() {
        return this.sndm;
    }

    public void setSndm(IFMDMAttribute sndm) {
        this.sndm = sndm;
    }

    public IFMDMAttribute getDwdm() {
        return this.dwdm;
    }

    public void setDwdm(IFMDMAttribute dwdm) {
        this.dwdm = dwdm;
    }

    public IFMDMAttribute getBblx() {
        return this.bblx;
    }

    public void setBblx(IFMDMAttribute bblx) {
        this.bblx = bblx;
    }

    public IFMDMAttribute getSnbblx() {
        return this.snbblx;
    }

    public void setSnbblx(IFMDMAttribute snbblx) {
        this.snbblx = snbblx;
    }
}

