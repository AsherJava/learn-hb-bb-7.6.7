/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.query.dao.define;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.query.caliber.CaliberItem;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CaliberItemDao
extends BaseDao {
    private Class<CaliberItem> implClass = CaliberItem.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public int insertCaliberItem(CaliberItem caliberItem) throws Exception {
        return this.insert(caliberItem);
    }

    public int updateCaliberItem(CaliberItem caliberItem) throws Exception {
        return this.update(caliberItem);
    }

    public int deleteCaliberItemById(String id) throws Exception {
        return this.delete(id);
    }

    public CaliberItem getCaliberItemById(String id) throws Exception {
        return (CaliberItem)this.getByKey(id, this.implClass);
    }

    public List<CaliberItem> getAllCaliberItems() throws Exception {
        return this.list(this.implClass);
    }
}

