/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.np.asynctask.TaskState
 */
package com.jiuqi.gcreport.onekeymerge.dao.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.onekeymerge.dao.MergeTaskRelDao;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskRelEO;
import com.jiuqi.np.asynctask.TaskState;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class MergeTaskRelDaoImpl
extends AbstractEntDbSqlGenericDAO<MergeTaskRelEO>
implements MergeTaskRelDao {
    public MergeTaskRelDaoImpl() {
        super(MergeTaskRelEO.class);
    }

    @Override
    public List<MergeTaskRelEO> listByGroupId(String groupId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MERGETASK_REL", (String)"t");
        String sql = "select " + allFieldsSQL + " from " + "GC_MERGETASK_REL" + " t where t.GROUP_ID='%1$s' and t.TASKSTATE='EXECUTING'";
        return this.selectEntity(String.format(sql, groupId), new Object[0]);
    }

    @Override
    public Map<String, Integer> getAsyncTaskId2StateByNP(String groupId) {
        String sql = "select task_id, state from NP_ASYNCTASK t where t.task_id in( select asyncTaskId from GC_MERGETASK_REL i where i.GROUP_ID=? and i.TASKSTATE='EXECUTING')";
        HashMap<String, Integer> asyncTaskId2State = new HashMap<String, Integer>();
        List maps = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{groupId});
        maps.stream().forEach(map -> asyncTaskId2State.put(ConverterUtils.getAsString(map.get("TASK_ID")), ConverterUtils.getAsInteger(map.get("STATE"))));
        return asyncTaskId2State;
    }

    @Override
    public Map<String, Integer> getAsyncTaskId2StateByBI(String groupId) {
        String sql = "select e.BJI_INSTANCEID as task_id, e.BJI_STATE as state, e.BJI_RESULT as result from BI_JOBS_INSTANCES e where e.BJI_INSTANCEID in ( select asyncTaskId from GC_MERGETASK_REL i where i.GROUP_ID=? and i.TASKSTATE='EXECUTING')";
        HashMap<String, Integer> asyncTaskId2State = new HashMap<String, Integer>();
        List maps = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{groupId});
        maps.stream().forEach(map -> {
            TaskState taskState = TaskState.convertRealTimeTaskState((int)ConverterUtils.getAsInteger(map.get("STATE")), (int)ConverterUtils.getAsInteger(map.get("RESULT")));
            asyncTaskId2State.put(ConverterUtils.getAsString(map.get("TASK_ID")), taskState.getValue());
        });
        return asyncTaskId2State;
    }

    @Override
    public int updateTaskStateByIds(List<String> ids, String taskState) {
        TempTableCondition condition = SqlUtils.getConditionOfIds(ids, (String)"ID");
        String sql = "update GC_MERGETASK_REL set TASKSTATE = '%1$s' where " + condition.getCondition();
        IdTemporaryTableUtils.deteteByGroupId((String)condition.getTempGroupId());
        return this.execute(String.format(sql, taskState));
    }
}

