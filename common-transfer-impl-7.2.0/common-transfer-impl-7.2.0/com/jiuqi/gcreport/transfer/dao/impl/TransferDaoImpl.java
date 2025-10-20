/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.transfer.dao.impl;

import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.transfer.dao.TransferDao;
import com.jiuqi.gcreport.transfer.entity.TransferEo;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class TransferDaoImpl
extends AbstractEntDbSqlGenericDAO<TransferEo>
implements TransferDao {
    public TransferDaoImpl() {
        super(TransferEo.class);
    }

    public List<TransferEo> selectList(TransferEo exampleEntity) {
        if (exampleEntity == null || StringUtils.isEmpty(exampleEntity.getPath())) {
            return null;
        }
        if (exampleEntity.getUserId() != null) {
            String sql = "  select  " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_TRANSFERINFO", (String)"scheme") + " \n  from  " + "GC_TRANSFERINFO" + "   scheme  \n  where   scheme.userId = ? \n  and  scheme.urlPath = ? \n";
            return this.selectEntity(sql, new Object[]{exampleEntity.getUserId(), exampleEntity.getPath()});
        }
        String sql = "  select  " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_TRANSFERINFO", (String)"scheme") + " \n  from  " + "GC_TRANSFERINFO" + "   scheme  \n  where scheme.urlPath = ? \n";
        return this.selectEntity(sql, new Object[]{exampleEntity.getPath()});
    }
}

