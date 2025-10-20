/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.workingpaper.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeOrgFilterSettingDao;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOrgFilterSettingEO;
import org.springframework.stereotype.Repository;

@Repository
public class ArbitrarilyMergeOrgFilterSettingDaoImpl
extends GcDbSqlGenericDAO<ArbitrarilyMergeOrgFilterSettingEO, String>
implements ArbitrarilyMergeOrgFilterSettingDao {
    public ArbitrarilyMergeOrgFilterSettingDaoImpl() {
        super(ArbitrarilyMergeOrgFilterSettingEO.class);
    }
}

