/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dao.impl;

import com.jiuqi.nr.query.caliber.CaliberDefine;
import com.jiuqi.nr.query.dao.ICaliberDefineDao;
import com.jiuqi.nr.query.dao.define.CaliberDefineDao;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CaliberDefineDaoImpl
implements ICaliberDefineDao {
    private static final Logger log = LoggerFactory.getLogger(CaliberDefineDaoImpl.class);
    @Autowired
    CaliberDefineDao dao;

    @Override
    public Boolean insertCaliberDefine(CaliberDefine caliberDefine) {
        Assert.notNull((Object)caliberDefine, "'caliberDefine' must not be null");
        try {
            this.dao.insertDefine(caliberDefine);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean updateCaliberDefine(CaliberDefine caliberDefine) {
        Assert.notNull((Object)caliberDefine, "'caliberDefine' must not be null");
        try {
            this.dao.updateDefine(caliberDefine);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean deleteCaliberDefineById(String caliberDefineId) {
        try {
            this.dao.deleteDefineById(caliberDefineId);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public CaliberDefine getCaliberDefineById(String caliberDefineId) {
        try {
            return this.dao.getDefineById(caliberDefineId);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<CaliberDefine> getAllCalibers() {
        try {
            return this.dao.getCaliberslist();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<CaliberDefine> getCaliberDefinesByGroupId(String groupId) {
        try {
            return this.dao.getDefinesByGroupId(groupId);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}

