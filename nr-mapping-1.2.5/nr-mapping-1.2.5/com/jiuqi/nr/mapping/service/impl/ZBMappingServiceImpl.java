/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.mapping.service.impl;

import com.jiuqi.nr.mapping.bean.ZBMapping;
import com.jiuqi.nr.mapping.dao.MappingSchemeDao;
import com.jiuqi.nr.mapping.dao.ZBMappingDao;
import com.jiuqi.nr.mapping.service.ZBMappingService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZBMappingServiceImpl
implements ZBMappingService {
    @Autowired
    private ZBMappingDao zBMappingDao;
    @Autowired
    private MappingSchemeDao msDao;

    @Override
    public List<ZBMapping> findByMS(String msKey) {
        return this.zBMappingDao.findByMS(msKey);
    }

    @Override
    public List<ZBMapping> findByMSAndForm(String msKey, String formCode) {
        return this.zBMappingDao.findByMSAndForm(msKey, formCode);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(String msKey, String formCode, List<ZBMapping> zbMappingList) {
        this.zBMappingDao.deteteByMSAndForm(msKey, formCode);
        for (ZBMapping zbMapping : zbMappingList) {
            zbMapping.setKey(UUID.randomUUID().toString());
            zbMapping.setMsKey(msKey);
        }
        this.zBMappingDao.batchAdd(zbMappingList);
        this.msDao.updateTime(msKey);
    }

    @Override
    public void clearByMs(String msKey) {
        this.zBMappingDao.deleteByMS(msKey);
    }

    @Override
    public void clearByMSAndForm(String msKey, String formCode) {
        this.zBMappingDao.deteteByMSAndForm(msKey, formCode);
    }
}

