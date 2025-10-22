/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.workingpaper.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeQuerySchemeDao;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeQuerySchemeEO;
import org.springframework.stereotype.Repository;

@Repository
public class ArbitrarilyMergeQuerySchemeDaoImpl
extends GcDbSqlGenericDAO<ArbitrarilyMergeQuerySchemeEO, String>
implements ArbitrarilyMergeQuerySchemeDao {
    public ArbitrarilyMergeQuerySchemeDaoImpl() {
        super(ArbitrarilyMergeQuerySchemeEO.class);
    }
}

