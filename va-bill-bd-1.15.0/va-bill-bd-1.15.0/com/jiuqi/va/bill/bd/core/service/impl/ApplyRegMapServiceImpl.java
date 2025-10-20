/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.bill.bd.core.service.impl;

import com.jiuqi.va.bill.bd.core.dao.ApplyRegMapDao;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.bill.bd.core.service.ApplyRegMapService;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.R;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplyRegMapServiceImpl
implements ApplyRegMapService {
    @Autowired
    ApplyRegMapDao mapDao;

    @Override
    public List<ApplyRegMapDO> getMap(ApplyRegMapDO mapDO) {
        return this.mapDao.select((Object)mapDO);
    }

    @Override
    public R createMap(ApplyRegMapDO mapDO) {
        ApplyRegMapDO DO = mapDO;
        DO.setId(UUID.randomUUID().toString());
        DO.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        DO.setCreatetime(new Date());
        this.mapDao.insert((Object)DO);
        return R.ok();
    }

    @Override
    public R updateMap(ApplyRegMapDO mapDO) {
        if (mapDO.getId() == null) {
            return R.error((String)"\u8bf7\u6c42\u53c2\u6570\u6709\u8bef");
        }
        ApplyRegMapDO billDO = mapDO;
        this.mapDao.updateByPrimaryKey((Object)billDO);
        return R.ok();
    }

    @Override
    public R deleteMap(ApplyRegMapDO mapDO) {
        if (mapDO.getId() == null) {
            return R.error((String)"\u8bf7\u6c42\u53c2\u6570\u6709\u8bef");
        }
        ApplyRegMapDO billDO = new ApplyRegMapDO();
        billDO.setId(mapDO.getId());
        this.mapDao.delete((Object)billDO);
        return R.ok();
    }
}

