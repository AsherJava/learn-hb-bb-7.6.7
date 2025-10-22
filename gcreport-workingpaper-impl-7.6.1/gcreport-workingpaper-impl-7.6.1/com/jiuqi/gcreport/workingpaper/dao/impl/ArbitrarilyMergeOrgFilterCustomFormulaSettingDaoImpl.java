/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.workingpaper.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeOrgFilterCustomFormulaSettingDao;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOrgFilterCustomFormulaSettingEO;
import org.springframework.stereotype.Repository;

@Repository
public class ArbitrarilyMergeOrgFilterCustomFormulaSettingDaoImpl
extends GcDbSqlGenericDAO<ArbitrarilyMergeOrgFilterCustomFormulaSettingEO, String>
implements ArbitrarilyMergeOrgFilterCustomFormulaSettingDao {
    public ArbitrarilyMergeOrgFilterCustomFormulaSettingDaoImpl() {
        super(ArbitrarilyMergeOrgFilterCustomFormulaSettingEO.class);
    }
}

