/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dao.impl;

import com.jiuqi.nr.query.caliber.CaliberGroup;
import com.jiuqi.nr.query.dao.ICaliberGroupDao;
import com.jiuqi.nr.query.dao.define.CaliberGroupDao;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CaliberGroupDaoImpl
implements ICaliberGroupDao {
    private static final Logger log = LoggerFactory.getLogger(CaliberGroupDaoImpl.class);
    @Autowired
    CaliberGroupDao dao;

    @Override
    public Boolean insertCaliberGroup(CaliberGroup caliberGroup) {
        Assert.notNull((Object)caliberGroup, "'caliberGroup' must not be null");
        try {
            this.dao.insertCaliberGroup(caliberGroup);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean updateCaliberGroup(CaliberGroup caliberGroup) {
        Assert.notNull((Object)caliberGroup, "'caliberGroup' must not be null");
        try {
            this.dao.updateCaliberGroup(caliberGroup);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean deleteCaliberGroupById(String id) {
        try {
            this.dao.deleteCaliberGroupById(id);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public CaliberGroup getCaliberGroupById(String id) {
        try {
            return this.dao.getCaliberGroupById(id);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<CaliberGroup> getAllCaliberGroups() {
        try {
            return this.dao.getAllCaliberGroups();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}

