/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate
 */
package com.jiuqi.gcreport.financialcheckImpl.reltxquery.dao.impl;

import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.dao.GcRelTxSubjectMappingDao;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.entity.GCRelTxSubjectMappingEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GcRelTxSubjectMappingDaoImpl
extends AbstractEntDbSqlGenericDAO<GCRelTxSubjectMappingEO>
implements GcRelTxSubjectMappingDao {
    public GcRelTxSubjectMappingDaoImpl() {
        super(GCRelTxSubjectMappingEO.class, (FEntSqlTemplate)new FcNativeSqlTemplate());
        this.setTableName("GC_RELTX_SUBJECT_MAPPING");
    }
}

