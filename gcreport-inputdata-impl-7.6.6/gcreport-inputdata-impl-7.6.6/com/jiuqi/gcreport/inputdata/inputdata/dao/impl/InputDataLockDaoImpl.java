/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataLockDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataLockEO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class InputDataLockDaoImpl
extends GcDbSqlGenericDAO<InputDataLockEO, String>
implements InputDataLockDao {
    public InputDataLockDaoImpl() {
        super(InputDataLockEO.class);
    }

    @Override
    public void deleteByLockId(String lockId) {
        String sql = "  delete from GC_INPUTDATALOCK   where lockId = ? \n";
        this.execute("  delete from GC_INPUTDATALOCK   where lockId = ? \n", new Object[]{lockId});
    }

    public List<InputDataLockEO> saveAll(List<InputDataLockEO> inputDataLocks) throws DataAccessException {
        String sql = "  insert into GC_INPUTDATALOCK (id,recver,lockId,inputItemId,offsetGroupId,checkGroupId,createTime,lockSrc,ipAddress,userName,currencyCode) \n  values (?,?,?,?,?,?,?,?,?,?,?) \n";
        List param = inputDataLocks.stream().map(m -> Arrays.asList(StringUtils.isEmpty((String)m.getId()) ? UUIDUtils.newUUIDStr() : m.getId(), m.getRecver(), m.getLockId(), m.getInputItemId(), m.getOffsetGroupId(), m.getCheckGroupId(), m.getCreateTime(), m.getLockSrc(), m.getIpAddress(), m.getUserName(), m.getCurrencyCode())).collect(Collectors.toList());
        this.executeBatch("  insert into GC_INPUTDATALOCK (id,recver,lockId,inputItemId,offsetGroupId,checkGroupId,createTime,lockSrc,ipAddress,userName,currencyCode) \n  values (?,?,?,?,?,?,?,?,?,?,?) \n", param);
        return inputDataLocks;
    }

    @Override
    public List<InputDataLockEO> queryExpiredData(long expiredTime) {
        String sql = "  select l.lockId  lockId \n  from GC_INPUTDATALOCK  l \n   where l.createTime < ? \n";
        return this.selectEntity("  select l.lockId  lockId \n  from GC_INPUTDATALOCK  l \n   where l.createTime < ? \n", new Object[]{expiredTime});
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<InputDataLockEO> queryUserNameByInputItemId(Collection<String> inputItemIds) {
        TempTableCondition conditionOfIds = SqlUtils.getConditionOfIds(inputItemIds, (String)"l.inputItemId");
        String sql = "  select l.userName  userName \n    from GC_INPUTDATALOCK  l \n   where " + conditionOfIds.getCondition() + " \n ";
        ArrayList<InputDataLockEO> locks = new ArrayList();
        try {
            locks = this.selectEntity(sql, new Object[0]);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)conditionOfIds.getTempGroupId());
        }
        return locks;
    }

    @Override
    public List<InputDataLockEO> queryByLockId(String lockId) {
        String sql = "select l.* from GC_INPUTDATALOCK l where l.lockid=?";
        return this.selectEntity(sql, new Object[]{lockId});
    }

    @Override
    public void updateGroupId(List<InputDataLockEO> inputLocks) {
        if (CollectionUtils.isEmpty(inputLocks)) {
            return;
        }
        String sql = "update GC_INPUTDATALOCK set offsetGroupId=?,checkGroupId=? where inputItemId=? ";
        ArrayList params = new ArrayList();
        for (InputDataLockEO inputLock : inputLocks) {
            ArrayList<String> rowParams = new ArrayList<String>();
            params.add(rowParams);
            rowParams.add(inputLock.getOffsetGroupId());
            rowParams.add(inputLock.getCheckGroupId());
            rowParams.add(inputLock.getInputItemId());
        }
        this.executeBatch(sql, params);
    }
}

