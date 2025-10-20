/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataNoDependServiceDao;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class InputDataNoDependServiceDaoImpl
extends GcDbSqlGenericDAO<InputDataEO, String>
implements InputDataNoDependServiceDao {
    private InputDataNameProvider inputDataNameProvider;
    private ConsolidatedTaskService taskService;

    public InputDataNoDependServiceDaoImpl(InputDataNameProvider inputDataNameProvider, ConsolidatedTaskService taskService) {
        super(InputDataEO.class);
        this.inputDataNameProvider = inputDataNameProvider;
        this.taskService = taskService;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<InputDataEO> queryCheckOffsetGroupId(Collection<String> ids, InputWriteNecLimitCondition inputWriteNecLimitCondition) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        String sql = "  select case when i.offsetState = '1' then i.offsetGroupId else null end  offsetGroupId, case when i.checkState = '1' then i.checkGroupId else null end checkGroupId,i.id  id,i.MD_CURRENCY MD_CURRENCY  \n    from %2$s  i \n   where %3$s \n     and i.DATATIME = ? \n" + (StringUtils.isEmpty((String)inputWriteNecLimitCondition.getLeafOrgId()) ? " " : " and i.MDCODE = ? \n") + "     and %1$s \n";
        TempTableCondition tempTableCondition = SqlUtils.getNewConditionOfIds(ids, (String)"i.id");
        String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(inputWriteNecLimitCondition.getTaskId(), inputWriteNecLimitCondition.getNrPeriod());
        if (StringUtils.isEmpty((String)systemId)) {
            systemId = "\u4e0d\u5b58\u5728";
        }
        String systemCond = " i.reportSystemId = '" + systemId + "' ";
        try {
            String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputWriteNecLimitCondition.getTaskId());
            if (!StringUtils.isEmpty((String)inputWriteNecLimitCondition.getLeafOrgId())) {
                List list = this.selectEntity(String.format(sql, tempTableCondition.getCondition(), tableName, systemCond), new Object[]{inputWriteNecLimitCondition.getNrPeriod(), inputWriteNecLimitCondition.getLeafOrgId()});
                return list;
            }
            List list = this.selectEntity(String.format(sql, tempTableCondition.getCondition(), tableName, systemCond), new Object[]{inputWriteNecLimitCondition.getNrPeriod()});
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public List<InputDataEO> queryCheckOffsetGroupIdByLockId(String lockId, String taskId) {
        String sql = "  select case when i.offsetState = '1' then i.offsetGroupId else null end  offsetGroupId, case when i.checkState = '1' then i.checkGroupId else null end checkGroupId, i.id  id \n    from %1$s  i \njoin GC_INPUTDATALOCK l on i.id=l.inputItemId \nwhere l.lockid=?\n";
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        return this.selectEntity(String.format("  select case when i.offsetState = '1' then i.offsetGroupId else null end  offsetGroupId, case when i.checkState = '1' then i.checkGroupId else null end checkGroupId, i.id  id \n    from %1$s  i \njoin GC_INPUTDATALOCK l on i.id=l.inputItemId \nwhere l.lockid=?\n", tableName), new Object[]{lockId});
    }
}

