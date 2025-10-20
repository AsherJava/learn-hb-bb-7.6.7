/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.impl.functionEditor;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.dao.functionEditor.FunctionEditorDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.functionEditor.GcFunctionEditorEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FunctionEditorDaoImpl
extends GcDbSqlGenericDAO<GcFunctionEditorEO, String>
implements FunctionEditorDao {
    public FunctionEditorDaoImpl() {
        super(GcFunctionEditorEO.class);
    }

    @Override
    public List<GcFunctionEditorEO> queryByUserId(String userId, Integer pageSize, Integer pageNum) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(GcFunctionEditorEO.class, (String)"f") + " from " + "GC_FUNCTIONEDITOR" + "  f \n  where f.userId =  '" + userId + "' \n";
        return this.selectEntityByPaging(sql, (pageNum - 1) * pageSize, pageSize, new Object[0]);
    }

    @Override
    public int queryCountByUserId(String userId) {
        String sql = "  select count(*) from GC_FUNCTIONEDITOR  f \n  where f.userId =  '" + userId + "' \n";
        return this.count(sql, new Object[0]);
    }
}

