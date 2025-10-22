/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.carryover.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.carryover.dao.CarryOverLogExtendDao;
import com.jiuqi.gcreport.carryover.entity.CarryOverLogExtendEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class CarryOverLogExtendDaoImpl
extends GcDbSqlGenericDAO<CarryOverLogExtendEO, String>
implements CarryOverLogExtendDao {
    public CarryOverLogExtendDaoImpl() {
        super(CarryOverLogExtendEO.class);
    }

    @Override
    public Map<String, Map<String, Object>> listLogExtendInfoByIds(List<String> ids) {
        String condition = SqlUtils.getConditionOfIdsUseOr(ids, (String)"LOGID");
        String sql = " select " + SqlUtils.getColumnsSqlByEntity(CarryOverLogExtendEO.class, (String)"e \n") + " from " + "GC_CARRYOVER_LOG_EXTEND" + " e \n where " + condition;
        List extendEOS = this.selectEntity(sql, new Object[0]);
        if (CollectionUtils.isEmpty((Collection)extendEOS)) {
            return Collections.emptyMap();
        }
        HashMap<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
        for (CarryOverLogExtendEO eo : extendEOS) {
            Map<String, String> map1;
            if (map.containsKey(eo.getLogId())) {
                map1 = (Map)map.get(eo.getLogId());
                map1.put(eo.getExtendName(), eo.getExtendValue());
                map.replace(eo.getLogId(), map1);
                continue;
            }
            map1 = new HashMap<String, String>();
            map1.put(eo.getExtendName(), eo.getExtendValue());
            map.put(eo.getLogId(), map1);
        }
        return map;
    }
}

