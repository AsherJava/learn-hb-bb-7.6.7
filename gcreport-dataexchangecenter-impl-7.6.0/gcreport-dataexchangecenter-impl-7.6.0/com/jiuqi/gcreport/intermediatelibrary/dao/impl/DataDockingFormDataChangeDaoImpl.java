/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 */
package com.jiuqi.gcreport.intermediatelibrary.dao.impl;

import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.intermediatelibrary.dao.DataDockingFormDataChangeDao;
import com.jiuqi.gcreport.intermediatelibrary.entity.DataDockingFormDataChangeEO;
import org.springframework.stereotype.Repository;

@Repository
public class DataDockingFormDataChangeDaoImpl
extends AbstractEntDbSqlGenericDAO<DataDockingFormDataChangeEO>
implements DataDockingFormDataChangeDao {
    public DataDockingFormDataChangeDaoImpl() {
        super(DataDockingFormDataChangeEO.class);
    }
}

