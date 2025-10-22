/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.ReportTagDefine;
import com.jiuqi.nr.definition.internal.impl.ReportTagDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RuntimeReportTagDao
extends BaseDao {
    private Class<ReportTagDefineImpl> implClz = ReportTagDefineImpl.class;

    public Class<?> getClz() {
        return this.implClz;
    }

    public List<ReportTagDefine> queryByRptKey(String rptKey) {
        return super.list(new String[]{"rptKey"}, new Object[]{rptKey}, this.implClz);
    }
}

