/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 */
package com.jiuqi.gcreport.onekeymerge.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.onekeymerge.dao.OnekeyMergeResultDao;
import com.jiuqi.gcreport.onekeymerge.entity.GcOnekeyMergeResultEO;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class OnekeyMergeResultDaoImpl
extends GcDbSqlGenericDAO<GcOnekeyMergeResultEO, String>
implements OnekeyMergeResultDao {
    public OnekeyMergeResultDaoImpl() {
        super(GcOnekeyMergeResultEO.class);
    }

    @Override
    public List<GcOnekeyMergeResultEO> findPastThreeResult(GcActionParamsVO vo) {
        String sql = "select " + SqlUtils.getColumnsSqlByEntity(GcOnekeyMergeResultEO.class, (String)" e ") + " \n  from " + "GC_ONEKEYMERGEPROCESS" + "  e \n where e.taskCodes like '%" + TaskTypeEnum.CALC.getCode().toLowerCase() + "%' and e.taskId = '" + vo.getTaskId() + "' and  e.schemeId = '" + vo.getSchemeId() + "' and  e.orgType = '" + vo.getOrgType() + "' and  e.acctYear = '" + vo.getAcctYear() + "' and  e.acctPeriod = '" + vo.getAcctPeriod() + "' and  e.currency = '" + vo.getCurrency() + "' and  e.orgId = '" + vo.getOrgId() + "' order by e.taskTime desc ";
        return this.selectEntityByPaging(sql, 0, 3, new Object[0]);
    }
}

