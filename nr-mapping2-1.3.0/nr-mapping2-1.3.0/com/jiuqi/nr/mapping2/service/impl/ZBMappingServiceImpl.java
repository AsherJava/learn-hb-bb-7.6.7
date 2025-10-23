/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.mapping.dao.MappingSchemeDao
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.mapping2.service.impl;

import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.dao.ZBMappingDao;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nvwa.mapping.dao.MappingSchemeDao;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    public List<ZBMapping> findByMSAndMapping(String msKey, String mapping) {
        return this.zBMappingDao.findByMSAndMapping(msKey, mapping);
    }

    @Override
    public List<ZBMapping> findByMSAndMapping(String msKey, List<String> mappings) {
        if (mappings.size() > 1000) {
            List<List<String>> lists = ZBMappingServiceImpl.chunkList(mappings, 1000);
            ArrayList<ZBMapping> zbList = new ArrayList<ZBMapping>();
            for (List<String> list : lists) {
                List<ZBMapping> daoList = this.zBMappingDao.findByMSAndMapping(msKey, list);
                if (CollectionUtils.isEmpty(daoList)) continue;
                zbList.addAll(daoList);
            }
            return zbList;
        }
        return this.zBMappingDao.findByMSAndMapping(msKey, mappings);
    }

    public static <T> List<List<T>> chunkList(List<T> list, int size) {
        ArrayList<List<T>> chunks = new ArrayList<List<T>>();
        for (int i = 0; i < list.size(); i += size) {
            int end = Math.min(list.size(), i + size);
            chunks.add(list.subList(i, end));
        }
        return chunks;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(String msKey, String formCode, List<ZBMapping> zbMappingList) {
        this.zBMappingDao.deleteByMSAndForm(msKey, formCode);
        HashSet<String> keySet = new HashSet<String>();
        ArrayList<ZBMapping> saveList = new ArrayList<ZBMapping>();
        for (ZBMapping zbm : zbMappingList) {
            if (!StringUtils.hasText(zbm.getMapping())) continue;
            zbm.setKey(UUID.randomUUID().toString());
            zbm.setMsKey(msKey);
            if (!keySet.add(zbm.getForm() + zbm.getRegionCode() + zbm.getZbCode())) continue;
            saveList.add(zbm);
        }
        this.batchAdd(saveList);
        this.msDao.modifyTime(msKey);
    }

    @Override
    public void batchAdd(List<ZBMapping> zbMappingList) {
        int size = zbMappingList.size();
        if (size <= 1000) {
            this.zBMappingDao.batchAdd(zbMappingList);
        } else {
            for (int i = 0; i < size; i += 1000) {
                int end = Math.min(i + 1000, size);
                this.zBMappingDao.batchAdd(zbMappingList.subList(i, end));
            }
        }
    }

    @Override
    public void clearByMs(String msKey) {
        this.zBMappingDao.deleteByMS(msKey);
    }

    @Override
    public void deleteByMS(String msKey) {
        this.zBMappingDao.deleteByMS(msKey);
    }

    @Override
    public void clearByMSAndForm(String msKey, String formCode) {
        this.zBMappingDao.deleteByMSAndForm(msKey, formCode);
    }

    @Override
    public void deleteByKeys(List<String> keys) {
        this.zBMappingDao.batchDeleteByKey(keys);
    }
}

