/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.dao.impl;

import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.dao.FcAbnormalDataDao;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.entity.FcAbnormalDataEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FcAbnormalDataDaoImpl
extends AbstractEntDbSqlGenericDAO<FcAbnormalDataEO>
implements FcAbnormalDataDao {
    public FcAbnormalDataDaoImpl() {
        super(FcAbnormalDataEO.class, (FEntSqlTemplate)new FcNativeSqlTemplate());
        this.setTableName("GC_FC_ABNORMALDATA");
    }
}

