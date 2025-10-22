/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.lwtree.provider.impl;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.lwtree.provider.ITreeSearchAdapter;
import com.jiuqi.nr.lwtree.query.IEntityRowQueryer;
import com.jiuqi.nr.lwtree.request.SearchParam;
import com.jiuqi.nr.lwtree.response.LightNodeData;

public class LightTreeSearchProvider
extends ITreeSearchAdapter<LightNodeData> {
    public LightTreeSearchProvider(IEntityRowQueryer queryer, SearchParam searchParam) {
        super(queryer, searchParam);
    }

    @Override
    protected LightNodeData buidDataRow(IEntityRow row) {
        LightNodeData dataObj = new LightNodeData();
        dataObj.setKey(row.getEntityKeyData());
        dataObj.setTitle(row.getTitle());
        dataObj.setCode(row.getCode());
        return dataObj;
    }
}

