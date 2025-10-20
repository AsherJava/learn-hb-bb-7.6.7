/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.common.file.dao.impl;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.file.dao.CommonFileClearDao;
import com.jiuqi.common.file.entity.CommonFileClearEO;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CommonFileClearDaoImpl
extends AbstractEntDbSqlGenericDAO<CommonFileClearEO>
implements CommonFileClearDao {
    public CommonFileClearDaoImpl() {
        super(CommonFileClearEO.class);
    }

    @Override
    public List<CommonFileClearEO> queryExpiredFiles() {
        long currentTime = DateUtils.now().getTime();
        long minExpiredTime = currentTime - 86400000L;
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(CommonFileClearEO.class, (String)"t") + "  from  " + "GC_COMMON_FILE_CLEAR" + " t  \n  where t.createtimestamp < ?";
        List commonFileClearEOS = this.selectEntity(sql, new Object[]{minExpiredTime});
        return commonFileClearEOS;
    }
}

