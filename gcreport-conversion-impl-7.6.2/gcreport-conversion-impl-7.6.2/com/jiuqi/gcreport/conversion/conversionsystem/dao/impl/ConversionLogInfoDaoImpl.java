/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.conversion.conversionsystem.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionLogInfoDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionLogInfoEo;
import org.springframework.stereotype.Repository;

@Repository
public class ConversionLogInfoDaoImpl
extends GcDbSqlGenericDAO<ConversionLogInfoEo, String>
implements ConversionLogInfoDao {
    public ConversionLogInfoDaoImpl() {
        super(ConversionLogInfoEo.class);
    }
}

