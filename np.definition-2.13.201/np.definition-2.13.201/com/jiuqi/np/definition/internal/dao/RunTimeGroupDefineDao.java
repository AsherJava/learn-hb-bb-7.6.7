/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.dao;

import com.jiuqi.np.definition.facade.TableGroupDefine;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.np.definition.internal.impl.RunTimeTableGroupDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeGroupDefineDao
extends BaseDao {
    private static String ATTR_PARENT = "parentKey";
    private Class<RunTimeTableGroupDefineImpl> implClass = RunTimeTableGroupDefineImpl.class;

    @Override
    public Class<?> getClz() {
        return this.implClass;
    }

    public RunTimeTableGroupDefineImpl getGroup(String tableGroupKey) throws Exception {
        return this.getByKey(tableGroupKey, this.implClass);
    }

    public List<TableGroupDefine> queryChildGroups(String tableGroupKey) throws Exception {
        return this.list(new String[]{ATTR_PARENT}, new Object[]{tableGroupKey}, this.implClass);
    }
}

