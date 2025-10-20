/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.carryover.dao.impl;

import com.jiuqi.gcreport.carryover.dao.CarryOverConfigDao;
import com.jiuqi.gcreport.carryover.entity.CarryOverConfigEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CarryOverConfigDaoImpl
extends GcDbSqlGenericDAO<CarryOverConfigEO, String>
implements CarryOverConfigDao {
    public CarryOverConfigDaoImpl() {
        super(CarryOverConfigEO.class);
    }

    @Override
    public List<CarryOverConfigEO> listAllConfigEO() {
        String sql = "select " + SqlUtils.getColumnsSqlByEntity(CarryOverConfigEO.class, (String)"e") + " from " + this.getTableName() + " e \n order by e.ordinal desc";
        return this.selectEntity(sql, new Object[0]);
    }
}

