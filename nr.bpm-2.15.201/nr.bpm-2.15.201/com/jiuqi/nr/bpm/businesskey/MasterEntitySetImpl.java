/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.businesskey;

import com.jiuqi.nr.bpm.businesskey.MasterEntity;
import com.jiuqi.nr.bpm.businesskey.MasterEntityImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySet;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySetInfo;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.exception.ParameterUtils;
import java.util.ArrayList;
import java.util.List;

public class MasterEntitySetImpl
implements MasterEntitySet {
    private int pos = -1;
    private final List<MasterEntity> masterEntities = new ArrayList<MasterEntity>();

    public MasterEntitySetImpl() {
    }

    public MasterEntitySetImpl(MasterEntitySetInfo masterEntitySetInfo) {
        this();
        ParameterUtils.AssertNotNull("masterEntitySetInfo", masterEntitySetInfo);
        while (masterEntitySetInfo.next()) {
            this.addMasterEntity(masterEntitySetInfo.getCurrent());
        }
    }

    @Override
    public boolean next() {
        if (this.pos < this.masterEntities.size() - 1) {
            ++this.pos;
            return true;
        }
        return false;
    }

    @Override
    public MasterEntityInfo getCurrent() {
        this.checkCurrentPosAviliable();
        return this.masterEntities.get(this.pos);
    }

    @Override
    public MasterEntitySet setMasterEntityDimessionValue(String dimessionName, String entityKey) {
        this.checkCurrentPosAviliable();
        this.masterEntities.get(this.pos).setMasterEntityDimessionValue(dimessionName, entityKey);
        return this;
    }

    @Override
    public MasterEntitySet addMasterEntity(MasterEntityInfo masterEntityInfo) {
        this.masterEntities.add(masterEntityInfo instanceof MasterEntity ? (MasterEntity)masterEntityInfo : new MasterEntityImpl(masterEntityInfo));
        return this;
    }

    @Override
    public void removeCurrent() {
        this.checkCurrentPosAviliable();
        this.masterEntities.remove(this.pos);
        --this.pos;
    }

    void checkCurrentPosAviliable() {
        if (this.pos < 0) {
            throw new BpmException("no current master entity. use metherd next() to fetch next master entity.");
        }
    }

    @Override
    public int size() {
        return this.masterEntities.size();
    }

    @Override
    public void reset() {
        this.pos = -1;
    }
}

