/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.offsetitem.init.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffsetInitLogInfoDao;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffsetInitLogInfoEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class GcOffsetInitLogInfoDaoImpl
extends GcDbSqlGenericDAO<GcOffsetInitLogInfoEO, String>
implements GcOffsetInitLogInfoDao {
    public GcOffsetInitLogInfoDaoImpl() {
        super(GcOffsetInitLogInfoEO.class);
    }

    @Override
    public List<GcOffsetInitLogInfoEO> queryLogInfoByCurrDimension(String acctYear, String orgCode) {
        String sql = " select " + SqlUtils.getColumnsSqlByEntity(GcOffsetInitLogInfoEO.class, (String)"e") + "\n from " + "GC_OFFSETINIT_LOGINFOS" + "  e \n where e.orgCode = '" + orgCode + "' and e.acctYear = " + acctYear;
        return this.selectEntity(sql, new Object[0]);
    }
}

