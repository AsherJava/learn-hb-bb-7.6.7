/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.cache.dao;

import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgQueryDao;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;

public interface FGcOrgEditDao
extends FGcOrgQueryDao {
    public boolean add(GcOrgParam var1);

    public boolean relAdd(GcOrgParam var1);

    public boolean update(GcOrgParam var1);

    public boolean changeState(GcOrgParam var1);

    public boolean remove(GcOrgParam var1);

    public boolean recovery(GcOrgParam var1);

    public boolean upOrDown(GcOrgParam var1, boolean var2);

    public boolean move(GcOrgParam var1);
}

