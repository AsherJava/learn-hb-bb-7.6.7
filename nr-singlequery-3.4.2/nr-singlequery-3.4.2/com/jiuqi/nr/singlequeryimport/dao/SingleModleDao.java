/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.singlequeryimport.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.singlequeryimport.bean.SingleModle;
import org.springframework.stereotype.Repository;

@Repository
public class SingleModleDao
extends BaseDao {
    private Class<SingleModle> implClass = SingleModle.class;

    public Class<?> getClz() {
        return this.implClass;
    }
}

