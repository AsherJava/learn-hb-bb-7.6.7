/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.workingpaper.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.workingpaper.dao.WorkingPaperQueryWayDao;
import com.jiuqi.gcreport.workingpaper.entity.WorkingPaperQueryWayItemEO;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperType;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class WorkingPaperQueryWayDaoImpl
extends GcDbSqlGenericDAO<WorkingPaperQueryWayItemEO, String>
implements WorkingPaperQueryWayDao {
    public WorkingPaperQueryWayDaoImpl() {
        super(WorkingPaperQueryWayItemEO.class);
    }

    @Override
    public List<WorkingPaperQueryWayItemEO> getWorkingPaperQueryWays(WorkingPaperType workingPaperType) {
        String sql = "select " + SqlUtils.getColumnsSqlByEntity(WorkingPaperQueryWayItemEO.class, (String)"t") + "  from  " + "GC_WORKPAPER_QUERYWAY" + " t  \n  where (t.creator = ?  or t.creator = '00000000-0000-0000-0000-000000000000') and t.workType = ? order by floatOrder \n";
        List eos = this.selectEntity(sql, new Object[]{NpContextHolder.getContext().getUserId(), workingPaperType.getType()});
        if (eos == null) {
            return Collections.emptyList();
        }
        return eos;
    }
}

