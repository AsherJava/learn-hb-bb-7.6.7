/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.query.dao.define;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.query.caliber.CaliberDefine;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CaliberDefineDao
extends BaseDao {
    private static String group_Id = "groupid";
    private Class<CaliberDefine> implClass = CaliberDefine.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public int insertDefine(CaliberDefine caliberDefine) throws Exception {
        return this.insert(caliberDefine);
    }

    public int updateDefine(CaliberDefine caliberDefine) throws Exception {
        return this.update(caliberDefine);
    }

    public int deleteDefineById(String id) throws Exception {
        return this.delete(id);
    }

    public CaliberDefine getDefineById(String id) throws Exception {
        return (CaliberDefine)this.getByKey(id, this.implClass);
    }

    public List<CaliberDefine> getCaliberslist() throws Exception {
        return this.list(this.implClass);
    }

    public List<CaliberDefine> getDefinesByGroupId(String groupId) throws Exception {
        return this.list(new String[]{group_Id}, new Object[]{groupId}, this.implClass);
    }
}

