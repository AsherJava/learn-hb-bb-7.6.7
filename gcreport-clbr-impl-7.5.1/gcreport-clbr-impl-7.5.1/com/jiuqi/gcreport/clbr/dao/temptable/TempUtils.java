/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.definition.impl.sqlutil.temporary.entity.IdTemporary
 */
package com.jiuqi.gcreport.clbr.dao.temptable;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.clbr.dao.temptable.ClbrIdRealTempDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.definition.impl.sqlutil.temporary.entity.IdTemporary;
import java.util.ArrayList;
import java.util.Collection;

public class TempUtils {
    public static TempTableCondition getTempCond(ClbrIdRealTempDao clbrIdRealTempDao, Collection<String> ids, String fieldName) {
        return TempUtils.getTempCond(clbrIdRealTempDao, ids, fieldName, true);
    }

    public static TempTableCondition getTempCond(ClbrIdRealTempDao clbrIdRealTempDao, Collection<String> ids, String fieldName, boolean positive) {
        if (CollectionUtils.isEmpty(ids)) {
            return new TempTableCondition(" 1=2 ", null);
        }
        String groupId = UUIDUtils.newUUIDStr();
        ArrayList<IdTemporary> idTemporaryEntities = new ArrayList<IdTemporary>();
        ids.forEach(id -> {
            IdTemporary idTempEntity = new IdTemporary();
            idTempEntity.setGroupId(groupId);
            idTempEntity.setTbId(id);
            idTemporaryEntities.add(idTempEntity);
        });
        clbrIdRealTempDao.insert(idTemporaryEntities);
        String inFilter = positive ? "in" : "not in";
        String condition = fieldName + " " + inFilter + " (select idtemp.tbId as id from " + clbrIdRealTempDao.getTableName() + " idtemp where idtemp.GROUP_ID = '" + groupId + "') \n";
        return new TempTableCondition(condition, groupId);
    }
}

