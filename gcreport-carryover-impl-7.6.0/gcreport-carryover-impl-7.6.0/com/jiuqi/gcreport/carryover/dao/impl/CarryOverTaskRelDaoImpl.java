/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.np.asynctask.TaskState
 */
package com.jiuqi.gcreport.carryover.dao.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.carryover.dao.CarryOverTaskRelDao;
import com.jiuqi.gcreport.carryover.entity.CarryOverTaskRelEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.np.asynctask.TaskState;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class CarryOverTaskRelDaoImpl
extends GcDbSqlGenericDAO<CarryOverTaskRelEO, String>
implements CarryOverTaskRelDao {
    public CarryOverTaskRelDaoImpl() {
        super(CarryOverTaskRelEO.class);
    }

    @Override
    public List<CarryOverTaskRelEO> listByGroupIdAndState(String groupId, String taskState) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CARRYOVER_TASK_REL", (String)"t");
        String sql = "select " + allFieldsSQL + " from " + "GC_CARRYOVER_TASK_REL" + " t where t.GROUP_ID = ? and t.TASKSTATE = ?";
        return this.selectEntity(sql, new Object[]{groupId, taskState});
    }

    @Override
    public Map<String, Integer> getAsyncTaskId2State(String groupId) {
        String sql = "select e.BJI_INSTANCEID as task_id, e.BJI_STATE as state, e.BJI_RESULT as result from BI_JOBS_INSTANCES e where e.BJI_INSTANCEID in ( select asyncTaskId from GC_CARRYOVER_TASK_REL i where i.GROUP_ID = ? and i.TASKSTATE = 'EXECUTING')";
        HashMap<String, Integer> asyncTaskId2State = new HashMap<String, Integer>();
        List maps = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{groupId});
        maps.stream().forEach(map -> {
            TaskState taskState = TaskState.convertRealTimeTaskState((int)ConverterUtils.getAsInteger(map.get("STATE")), (int)ConverterUtils.getAsInteger(map.get("RESULT")));
            asyncTaskId2State.put(ConverterUtils.getAsString(map.get("TASK_ID")), taskState.getValue());
        });
        return asyncTaskId2State;
    }
}

