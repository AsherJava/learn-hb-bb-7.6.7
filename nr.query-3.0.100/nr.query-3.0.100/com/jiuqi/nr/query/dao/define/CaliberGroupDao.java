/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.query.dao.define;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.query.caliber.CaliberGroup;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CaliberGroupDao
extends BaseDao {
    private Class<CaliberGroup> implClass = CaliberGroup.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public int insertCaliberGroup(CaliberGroup caliberGroup) throws Exception {
        return this.insert(caliberGroup);
    }

    public int updateCaliberGroup(CaliberGroup caliberGroup) throws Exception {
        return this.update(caliberGroup);
    }

    public int deleteCaliberGroupById(String id) throws Exception {
        return this.delete(id);
    }

    public CaliberGroup getCaliberGroupById(String id) throws Exception {
        return (CaliberGroup)this.getByKey(id, this.implClass);
    }

    public List<CaliberGroup> getAllCaliberGroups() throws Exception {
        return this.list(this.implClass);
    }
}

