/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.bde.plugin.gcreport.util;

import com.jiuqi.bde.plugin.gcreport.intf.UnitLengthPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;

public class GcreportPluginUtil {
    private static UnitLengthPojo unitLengthPojo = null;
    private static final String FN_QUERY_SQL = "SELECT VARIABLELENGTH,LENGTH FROM GC_UNITCODE_LENGTH \n";

    public static UnitLengthPojo getUnitLengthPojo(String dataSourceCode) {
        if (unitLengthPojo != null) {
            return unitLengthPojo;
        }
        DataSourceService dataSourceService = (DataSourceService)ApplicationContextRegister.getBean(DataSourceService.class);
        unitLengthPojo = (UnitLengthPojo)dataSourceService.query(dataSourceCode, FN_QUERY_SQL, new Object[0], rs -> {
            UnitLengthPojo pojo = new UnitLengthPojo();
            pojo.setLength(19);
            pojo.setVariableLength(true);
            if (rs.next()) {
                pojo.setVariableLength(rs.getObject(1) == null ? false : rs.getInt(1) == 1);
                pojo.setLength(rs.getObject(2) == null ? 19 : rs.getInt(2));
                return pojo;
            }
            return pojo;
        });
        return unitLengthPojo;
    }
}

