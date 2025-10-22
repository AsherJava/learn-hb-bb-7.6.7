/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.onekeymerge.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.onekeymerge.dao.OnekeyMergeConfigDao;
import com.jiuqi.gcreport.onekeymerge.entity.GcOnekeyMergeConfigEO;
import org.springframework.stereotype.Repository;

@Repository
public class OnekeyMergeConfigDaoImpl
extends GcDbSqlGenericDAO<GcOnekeyMergeConfigEO, String>
implements OnekeyMergeConfigDao {
    public OnekeyMergeConfigDaoImpl() {
        super(GcOnekeyMergeConfigEO.class);
    }
}

