/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.listedcompanyauthz.domain;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.ArrayList;
import java.util.List;

public class ListedCompanySaveParam<T extends DefaultTableEntity> {
    private final List<T> insertDatas = new ArrayList<T>();
    private final List<T> updateDatas = new ArrayList<T>();
    private final List<T> deleteDatas = new ArrayList<T>();

    public void addInsert(T t) {
        t.setId(UUIDUtils.newUUIDStr());
        this.insertDatas.add(t);
    }

    public void addUpdate(T t) {
        this.updateDatas.add(t);
    }

    public void addDelete(T t) {
        this.deleteDatas.add(t);
    }

    public int size() {
        return this.insertDatas.size();
    }

    public List<T> getDeleteDatas() {
        return this.deleteDatas;
    }

    public List<T> getInsertDatas() {
        return this.insertDatas;
    }

    public List<T> getUpdateDatas() {
        return this.updateDatas;
    }
}

