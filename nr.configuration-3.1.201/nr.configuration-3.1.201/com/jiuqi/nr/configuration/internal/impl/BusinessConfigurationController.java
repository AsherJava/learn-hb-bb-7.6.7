/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.configuration.internal.impl;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.configuration.controller.IBusinessConfigurationController;
import com.jiuqi.nr.configuration.db.IBusinessConfigurationDao;
import com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine;
import com.jiuqi.nr.configuration.internal.impl.BusinessConfigurationDefineImpl;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BusinessConfigurationController
implements IBusinessConfigurationController {
    private static final Logger logger = LoggerFactory.getLogger(BusinessConfigurationController.class);
    @Autowired
    private IBusinessConfigurationDao configDao;

    @Override
    public BusinessConfigurationDefine createConfiguration() {
        BusinessConfigurationDefineImpl configurationDefine = new BusinessConfigurationDefineImpl();
        configurationDefine.setKey(UUIDUtils.getKey());
        return configurationDefine;
    }

    @Override
    public void addConfiguration(BusinessConfigurationDefine config) {
        try {
            this.configDao.insertConfiguration(config);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<BusinessConfigurationDefine> getConfigurationByTaskWithoutContent(String taskKey) {
        try {
            return this.configDao.getConfigurationsByTask(taskKey, false);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<BusinessConfigurationDefine>();
        }
    }

    @Override
    public List<BusinessConfigurationDefine> getConfigurationByCategoryWithoutContent(String taskKey, String category) {
        try {
            return this.configDao.getConfigurationsByCategory(category, taskKey, null, false);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<BusinessConfigurationDefine>();
        }
    }

    @Override
    public BusinessConfigurationDefine getConfiguration(String taskKey, String code) {
        try {
            return this.configDao.getConfigurationByCode(code, taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public BusinessConfigurationDefine getConfiguration(String taskKey, String formSchemeKey, String code) {
        try {
            return this.configDao.getConfigurationByCode(code, taskKey, formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String getConfigurationContent(String taskKey, String formSchemeKey, String code) {
        try {
            BusinessConfigurationDefine configurationDefine = this.configDao.getConfigurationByCode(code, taskKey, formSchemeKey);
            return configurationDefine == null ? null : configurationDefine.getConfigurationContent();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void updateConfigurationContent(String taskKey, String code, String content) {
        try {
            this.configDao.updateConfiguration(taskKey, null, code, content);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void updateConfiguration(BusinessConfigurationDefine config) {
        try {
            this.configDao.updateConfiguration(config);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteConfiguration(String configKey) {
        try {
            this.configDao.deleteConfiguration(configKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteConfiguration(String taskKey, String code) {
        try {
            this.configDao.deleteConfigurationByCode(code, taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

