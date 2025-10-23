/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.mapping.dao.MappingSchemeDao
 *  org.springframework.transaction.annotation.Transactional
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.nr.mapping2.service.impl;

import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.mapping2.dao.PeriodMappingDao;
import com.jiuqi.nr.mapping2.service.PeriodMappingService;
import com.jiuqi.nvwa.mapping.dao.MappingSchemeDao;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class PeriodMappingServiceImpl
implements PeriodMappingService {
    @Autowired
    private MappingSchemeDao msDao;
    @Autowired
    private PeriodMappingDao dao;

    @Override
    public List<PeriodMapping> findByMS(String msKey) {
        Assert.notNull((Object)msKey, "\u67e5\u8be2\u65f6\u671f\u6620\u5c04\uff1a\u65b9\u6848key\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        return this.dao.findByMS(msKey);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void clearByMS(String msKey) {
        Assert.notNull((Object)msKey, "\u6e05\u7a7a\u65f6\u671f\u6620\u5c04\uff1a\u65b9\u6848key\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        this.dao.deleteByMS(msKey);
        this.msDao.modifyTime(msKey);
    }

    @Override
    public void deleteByMS(String msKey) {
        Assert.notNull((Object)msKey, "\u6e05\u7a7a\u65f6\u671f\u6620\u5c04\uff1a\u65b9\u6848key\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        this.dao.deleteByMS(msKey);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveByMS(String msKey, List<PeriodMapping> mappings) {
        Assert.noNullElements(mappings, "\u4fdd\u5b58\u65f6\u671f\u6620\u5c04\u4e3a\u7a7a");
        HashSet<String> periods = new HashSet<String>();
        ArrayList<PeriodMapping> periodMappings = new ArrayList<PeriodMapping>();
        for (PeriodMapping p : mappings) {
            if (!periods.add(p.getPeriod())) continue;
            if (StringUtil.isEmpty((String)p.getKey())) {
                p.setKey(UUID.randomUUID().toString());
            }
            p.setMsKey(msKey);
            periodMappings.add(p);
        }
        this.dao.deleteByMS(msKey);
        if (!CollectionUtils.isEmpty(periodMappings)) {
            this.dao.add(periodMappings);
        }
        this.msDao.modifyTime(msKey);
    }

    @Override
    public void batchAdd(List<PeriodMapping> mps) {
        if (!CollectionUtils.isEmpty(mps)) {
            this.dao.add(mps);
        }
    }
}

