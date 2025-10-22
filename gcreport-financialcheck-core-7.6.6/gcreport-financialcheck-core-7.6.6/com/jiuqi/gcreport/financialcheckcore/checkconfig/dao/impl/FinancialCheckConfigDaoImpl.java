/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 */
package com.jiuqi.gcreport.financialcheckcore.checkconfig.dao.impl;

import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckcore.checkconfig.dao.FinancialCheckConfigDao;
import com.jiuqi.gcreport.financialcheckcore.checkconfig.entity.FinancialCheckConfigEO;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialCheckConfigDaoImpl
extends AbstractEntDbSqlGenericDAO<FinancialCheckConfigEO>
implements FinancialCheckConfigDao {
    public FinancialCheckConfigDaoImpl() {
        super(FinancialCheckConfigEO.class);
    }
}

