/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.samecontrol.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlOrgViewDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOrgViewEO;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class SameCtrlOrgViewDaoImpl
extends GcDbSqlGenericDAO<SameCtrlOrgViewEO, String>
implements SameCtrlOrgViewDao {
    public SameCtrlOrgViewDaoImpl() {
        super(SameCtrlOrgViewEO.class);
    }

    @Override
    public String getSameCtrlViewIdByOriginalViewId(String originalViewId) {
        String sql = "  select v.sameCtrlViewId from GC_SAMECTRLORGVIEW  v \n  where v.originalViewId = ? \n";
        List viewEOList = this.selectEntity(sql, new Object[]{originalViewId});
        return CollectionUtils.isEmpty((Collection)viewEOList) ? "" : ((SameCtrlOrgViewEO)((Object)viewEOList.get(0))).getSameCtrlViewId();
    }

    @Override
    public SameCtrlOrgViewEO getSameCtrlView(String viewId) {
        String sql = "  select v.sameCtrlViewId,v.originalViewId from GC_SAMECTRLORGVIEW  v \n  where v.originalViewId = ? \n  or v.sameCtrlViewId = ? \n";
        List viewEOList = this.selectEntity(sql, new Object[]{viewId, viewId});
        return CollectionUtils.isEmpty((Collection)viewEOList) ? null : (SameCtrlOrgViewEO)((Object)viewEOList.get(0));
    }
}

