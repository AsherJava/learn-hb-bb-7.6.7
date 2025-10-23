/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.service.impl;

import com.jiuqi.nr.mapping.bean.JIOConfig;
import com.jiuqi.nr.mapping.dao.JIOConfigDao;
import com.jiuqi.nr.mapping.dto.MappingSchemeWrapDTO;
import com.jiuqi.nr.mapping.service.MappingSchemeJIOService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingSchemeJIOServiceImpl
implements MappingSchemeJIOService {
    @Autowired
    private JIOConfigDao JIOConfigDao;

    @Override
    public void buildMapping(MappingSchemeWrapDTO wrap) {
    }

    @Override
    public JIOConfig getJIOConfig(String msKey) {
        return this.JIOConfigDao.findByMS(msKey);
    }

    @Override
    public void deleteJIOConfig(String msKey) {
        this.JIOConfigDao.deleteByMS(msKey);
    }

    @Override
    public void deleteJIOConfigs(List<String> msKeys) {
        this.JIOConfigDao.batchDeleteByMS(msKeys);
    }

    @Override
    public void saveJIOConfig(JIOConfig jioConfig) {
        this.JIOConfigDao.add(jioConfig);
    }

    @Override
    public void updateJIOConfig(String msKey, JIOConfig jioConfig) {
        this.JIOConfigDao.updateByMS(msKey, jioConfig);
    }
}

