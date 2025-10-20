/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.efdcdatacheck.impl.dao.EfdcCheckReportShareDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.EfdcCheckReportLogShareEO;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class EfdcCheckReportShareDaoImpl
extends GcDbSqlGenericDAO<EfdcCheckReportLogShareEO, String>
implements EfdcCheckReportShareDAO {
    public EfdcCheckReportShareDaoImpl() {
        super(EfdcCheckReportLogShareEO.class);
    }

    @Override
    public List<EfdcCheckReportLogShareEO> queryShareEoByUserAndFileKeys(String userName, List<String> fileKeys) {
        ArrayList<String> params = new ArrayList<String>();
        String sql = "select " + SqlUtils.getColumnsSqlByEntity(EfdcCheckReportLogShareEO.class, (String)"t") + "\n from " + "GC_EFDCCHECKREPORTLOG_SHARE" + " t \n where \n" + SqlUtils.getConditionOfIdsUseOr(fileKeys, (String)"t.file_key");
        if (!StringUtils.isEmpty((String)userName)) {
            sql = sql + " and t.shared_user = ?";
            params.add(userName);
        }
        return this.selectEntity(sql, params);
    }
}

