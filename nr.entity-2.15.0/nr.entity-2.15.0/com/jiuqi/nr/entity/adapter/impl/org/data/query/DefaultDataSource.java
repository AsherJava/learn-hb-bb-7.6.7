/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.nr.entity.adapter.impl.org.data.query;

import com.jiuqi.nr.entity.adapter.impl.org.data.OrgDataSource;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgDataClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataSource
implements OrgDataSource {
    @Autowired
    protected OrgDataClient orgDataClient;

    @Override
    public boolean enable() {
        return true;
    }

    @Override
    public double order() {
        return 10.0;
    }

    @Override
    public PageVO<OrgDO> list(OrgDTO orgDTO) {
        return this.orgDataClient.list(orgDTO);
    }

    @Override
    public OrgDO get(OrgDTO orgDTO) {
        return this.orgDataClient.get(orgDTO);
    }

    @Override
    public PageVO<OrgDO> listSubordinate(OrgDTO orgDTO) {
        return this.orgDataClient.listSubordinate(orgDTO);
    }

    @Override
    public int count(OrgDTO orgDTO) {
        return this.orgDataClient.count(orgDTO);
    }
}

