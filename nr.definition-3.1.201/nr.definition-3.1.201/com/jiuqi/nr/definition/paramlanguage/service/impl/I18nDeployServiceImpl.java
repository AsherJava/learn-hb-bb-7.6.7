/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.deploy.service.IParamDeployTimeService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.paramlanguage.service.impl;

import com.jiuqi.nr.definition.deploy.service.IParamDeployTimeService;
import com.jiuqi.nr.definition.paramlanguage.cache.ParamLanguageCache;
import com.jiuqi.nr.definition.paramlanguage.common.I18nDeploySQL;
import com.jiuqi.nr.definition.paramlanguage.service.I18nDeployService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class I18nDeployServiceImpl
implements I18nDeployService {
    private static final Logger LOGGER = LoggerFactory.getLogger(I18nDeployServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ParamLanguageCache languageCache;
    @Autowired
    private IParamDeployTimeService paramDeployTimeService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void I18nDeployByTask() {
        try {
            this.deploy(I18nDeploySQL.TASK.values());
            this.languageCache.clear();
            this.paramDeployTimeService.refreshDeployTime();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void I18nDeployByFormScheme(String formSchemeKey, boolean cleanCache) {
        try {
            this.deploy(I18nDeploySQL.FormScheme.values(), formSchemeKey);
            if (cleanCache) {
                this.languageCache.clearByFormScheme(formSchemeKey);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848\u4e0b\u591a\u8bed\u8a00\u53d1\u5e03\u5931\u8d25", e);
        }
    }

    @Override
    public void I18nDeployByForm(String formKey, boolean cleanCache) {
        try {
            this.deploy(I18nDeploySQL.Form.values(), formKey);
            if (cleanCache) {
                this.languageCache.clear();
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u8868\u5355\u4e0b\u591a\u8bed\u8a00\u53d1\u5e03\u5931\u8d25", e);
        }
    }

    private void deploy(I18nDeploySQL[] sql) {
        for (I18nDeploySQL item : sql) {
            int update = this.jdbcTemplate.update(item.getDeploySQL());
            LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c\u5f71\u54cd\u884c\u6570\uff1a{}", item.getDescription(), item.getDeploySQL(), update);
        }
    }

    private void deploy(I18nDeploySQL[] sql, String sourceKey) {
        for (I18nDeploySQL item : sql) {
            int update = this.jdbcTemplate.update(item.getDeploySQL(), new Object[]{sourceKey});
            LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c\u5f71\u54cd\u884c\u6570\uff1a{}", item.getDescription(), item.getDeploySQL(), sourceKey, update);
        }
    }
}

