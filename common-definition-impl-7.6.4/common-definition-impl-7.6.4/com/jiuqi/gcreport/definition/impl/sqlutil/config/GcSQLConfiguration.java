/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport.definition.impl.sqlutil.config;

import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.temporary.dao.IdTemporaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScans(value={@ComponentScan(value={"com.jiuqi.gcreport.definition.impl.sqlutil"})})
@EnableTransactionManagement
public class GcSQLConfiguration {
    @Bean
    @Lazy(value=false)
    public IdTemporaryTableUtils initIdTemporaryTableUtils(@Autowired IdTemporaryDao idTempDao) {
        IdTemporaryTableUtils utils = IdTemporaryTableUtils.getInstance();
        utils.ini(idTempDao);
        return utils;
    }
}

