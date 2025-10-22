/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datawarning.dao.impl;

import com.jiuqi.nr.datawarning.dao.IDataWarningDao;
import com.jiuqi.nr.datawarning.defines.DataWarningDefine;
import com.jiuqi.nr.datawarning.defines.DataWarningDefineDao;
import com.jiuqi.nr.datawarning.defines.DataWarningIdentify;
import com.jiuqi.nr.datawarning.defines.DataWarningType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DataWarningDaoImpl
implements IDataWarningDao {
    private static final Logger log = LoggerFactory.getLogger(DataWarningDaoImpl.class);
    @Autowired
    private DataWarningDefineDao dao;

    @Override
    public Boolean Insert(DataWarningDefine warningDefine) {
        Assert.notNull((Object)warningDefine, "'warningDefine' must not be null");
        try {
            this.dao.insertDefine(warningDefine);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean Update(DataWarningDefine warningDefine) {
        Assert.notNull((Object)warningDefine, "'warningDefine' must not be null");
        try {
            this.dao.updateDefine(warningDefine);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try {
            this.dao.deleteDefineById(id);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public DataWarningDefine QueryById(String id) {
        try {
            return this.dao.getDefineById(id);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataWarningDefine> QueryDataWarnigs(String key) {
        try {
            return this.dao.getDefinesByKey(key);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataWarningDefine> QueryDataWarnigs(String key, DataWarningIdentify identify) {
        try {
            return this.dao.getDefinesByKeyAndIdentify(key, identify);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataWarningDefine> QueryDataWarnigs(String key, DataWarningType type, DataWarningIdentify identify) {
        try {
            return this.dao.getDefinesByKeyAndTypeAndIden(key, type, identify);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataWarningDefine> QueryDataWarnigs(DataWarningIdentify identify) {
        try {
            return this.dao.getDefinesByIdentify(identify);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataWarningDefine> QueryDataWarnigs(DataWarningType type, DataWarningIdentify identify) {
        try {
            return this.dao.getDefinesByTypeAndIdentify(type, identify);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataWarningDefine> QueryDataWarnigsByKeyAndFieldCode(String key, String fieldCode) {
        try {
            return this.dao.getDefinesByKeyAndFieldCode(key, fieldCode);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataWarningDefine> QueryDataWarnigsByKeyAndFieldSettingCode(String key, String fieldSettingCode) {
        try {
            return this.dao.getDefinesByKeyAndFieldSettingCode(key, fieldSettingCode);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Boolean Update(List<DataWarningDefine> warningDefines) {
        try {
            this.dao.updateDefineByList(warningDefines);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean Delete(String key, Boolean isSave) {
        try {
            this.dao.deleteDefineByKeyAndIsSave(key, isSave);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}

