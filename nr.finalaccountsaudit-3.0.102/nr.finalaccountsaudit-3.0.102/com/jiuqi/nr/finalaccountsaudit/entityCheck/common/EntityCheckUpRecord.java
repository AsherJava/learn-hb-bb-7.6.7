/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpData;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpKey;
import java.io.Serializable;

public class EntityCheckUpRecord
implements Serializable {
    private EntityCheckUpKey key;
    private int currentYEntityCount;
    private int lastYEntityCount;
    private EntityCheckUpData checkUpData;

    public EntityCheckUpKey GetKey() {
        return this.key;
    }

    public void SetKey(EntityCheckUpKey _Key) {
        this.key = _Key;
    }

    public int GetCurrentEntityCount() {
        return this.currentYEntityCount;
    }

    public void SetCurrentEntityCount(int _currentYEntityCount) {
        this.currentYEntityCount = _currentYEntityCount;
    }

    public int GetLastEntityCount() {
        return this.lastYEntityCount;
    }

    public void SetLastEntityCount(int _lastYEntityCount) {
        this.lastYEntityCount = _lastYEntityCount;
    }

    public EntityCheckUpData GetCheckUpData() {
        return this.checkUpData;
    }

    public void SetCheckUpData(EntityCheckUpData _CheckUpData) {
        this.checkUpData = _CheckUpData;
    }

    public EntityCheckUpRecord Clone() {
        EntityCheckUpRecord record = new EntityCheckUpRecord();
        EntityCheckUpKey key = new EntityCheckUpKey();
        key.SetTaskKey(this.GetKey().GetTaskKey());
        key.SetPeriod(this.GetKey().GetPeriod());
        key.SetDWZDM(this.GetKey().GetDWZDM());
        key.SetCheckType(this.GetKey().GetCheckType());
        key.SetFmdmId(this.GetKey().GetFmdmId());
        record.SetKey(key);
        EntityCheckUpData item = new EntityCheckUpData();
        item.SetDWDM(this.GetCheckUpData().GetDWDM());
        item.SetDWZDM(this.GetCheckUpData().GetDWZDM());
        item.SetDWMC(this.GetCheckUpData().GetDWMC());
        item.SetErrorDescription(this.GetCheckUpData().GetErrorDescription());
        item.SetSNDM(this.GetCheckUpData().GetSNDM());
        item.SetZJYS(this.GetCheckUpData().GetZJYS());
        item.SetXBYS(this.GetCheckUpData().GetXBYS());
        item.setOrgCode(this.GetCheckUpData().getOrgCode());
        item.setParentsEntityKeyDataPath(this.GetCheckUpData().getParentsEntityKeyDataPath());
        record.SetCheckUpData(item);
        return record;
    }

    public EntityCheckUpKey getKey() {
        return this.key;
    }

    public void setKey(EntityCheckUpKey key) {
        this.key = key;
    }

    public int getCurrentYEntityCount() {
        return this.currentYEntityCount;
    }

    public void setCurrentYEntityCount(int currentYEntityCount) {
        this.currentYEntityCount = currentYEntityCount;
    }

    public int getLastYEntityCount() {
        return this.lastYEntityCount;
    }

    public void setLastYEntityCount(int lastYEntityCount) {
        this.lastYEntityCount = lastYEntityCount;
    }

    public EntityCheckUpData getCheckUpData() {
        return this.checkUpData;
    }

    public void setCheckUpData(EntityCheckUpData checkUpData) {
        this.checkUpData = checkUpData;
    }
}

