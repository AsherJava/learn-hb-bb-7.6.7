/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dao.impl;

import com.jiuqi.nr.query.caliber.CaliberItem;
import com.jiuqi.nr.query.dao.ICaliberItemDao;
import com.jiuqi.nr.query.dao.define.CaliberItemDao;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CaliberItemDaoImpl
implements ICaliberItemDao {
    private static final Logger log = LoggerFactory.getLogger(CaliberItemDaoImpl.class);
    @Autowired
    CaliberItemDao dao;

    @Override
    public Boolean insertCaliberItem(CaliberItem caliberItem) {
        Assert.notNull((Object)caliberItem, "'caliberItem' must not be null");
        try {
            this.dao.insertCaliberItem(caliberItem);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean updateCaliberItem(CaliberItem caliberItem) {
        Assert.notNull((Object)caliberItem, "'caliberItem' must not be null");
        try {
            this.dao.updateCaliberItem(caliberItem);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean deleteCaliberItemById(String caliberItemId) {
        try {
            this.deleteCaliberItemById(caliberItemId);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public CaliberItem getCaliberItemById(String caliberItemId) {
        try {
            return this.dao.getCaliberItemById(caliberItemId);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CaliberItem> getAllCaliberItems() {
        try {
            return this.dao.getAllCaliberItems();
        }
        catch (Exception e) {
            return null;
        }
    }
}

