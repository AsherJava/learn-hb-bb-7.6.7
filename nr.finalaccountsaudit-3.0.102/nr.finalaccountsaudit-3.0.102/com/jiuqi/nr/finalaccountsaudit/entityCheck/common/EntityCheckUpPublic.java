/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;

public class EntityCheckUpPublic {
    private String _TaskKey;
    private String _FormSchemKey;
    private String _TableName;
    private IFMDMAttribute _SJDW;
    private IFMDMAttribute _DWDM;
    private IFMDMAttribute _SNDM;
    private IFMDMAttribute _XBYS;
    private EntityViewDefine _JSYYEntityView;
    private EntityViewDefine _XBYSEntityView;
    private IFMDMAttribute _DWZDM;
    private IFMDMAttribute _DWMC;
    private IFMDMAttribute _BBLX;
    private IFMDMAttribute _LastYBBLX;
    private String _filterCondition;

    public String GetTaskKey() {
        return this._TaskKey;
    }

    public void SetTaskKey(String _TaskKey) {
        this._TaskKey = _TaskKey;
    }

    public String GetFormSchemKey() {
        return this._FormSchemKey;
    }

    public void SetFormSchemKey(String _FormSchemKey) {
        this._FormSchemKey = _FormSchemKey;
    }

    public String GetTableName() {
        return this._TableName;
    }

    public void SetTableName(String _TableName) {
        this._TableName = _TableName;
    }

    public IFMDMAttribute GetSJDW() {
        return this._SJDW;
    }

    public void SetSJDW(IFMDMAttribute _SJDW) {
        this._SJDW = _SJDW;
    }

    public IFMDMAttribute GetDWDM() {
        return this._DWDM;
    }

    public void SetDWDM(IFMDMAttribute _DWDM) {
        this._DWDM = _DWDM;
    }

    public IFMDMAttribute GetSNDM() {
        return this._SNDM;
    }

    public void SetSNDM(IFMDMAttribute _SNDM) {
        this._SNDM = _SNDM;
    }

    public IFMDMAttribute GetXBYS() {
        return this._XBYS;
    }

    public void SetXBYS(IFMDMAttribute _XBYS) {
        this._XBYS = _XBYS;
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

    public IFMDMAttribute GetDWZDM() {
        return this._DWZDM;
    }

    public void SetDWZDM(IFMDMAttribute _DWZDM) {
        this._DWZDM = _DWZDM;
    }

    public IFMDMAttribute GetDWMC() {
        return this._DWMC;
    }

    public void SetDWMC(IFMDMAttribute _DWMC) {
        this._DWMC = _DWMC;
    }

    public IFMDMAttribute GetBBLX() {
        return this._BBLX;
    }

    public void SetBBLX(IFMDMAttribute _BBLX) {
        this._BBLX = _BBLX;
    }

    public IFMDMAttribute GetLastYBBLX() {
        return this._LastYBBLX;
    }

    public void SetLastYBBLX(IFMDMAttribute _LastYBBLX) {
        this._LastYBBLX = _LastYBBLX;
    }

    public String GetFilterCondition() {
        return this._filterCondition;
    }

    public void SetFilterCondition(String _filterCondition) {
        this._filterCondition = _filterCondition;
    }
}

