/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.gcreport.assist;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.gcreport.BdeGcreportPluginType;
import com.jiuqi.bde.plugin.gcreport.assist.GcreportAssistPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class GcreportAssistProvider
implements IAssistProvider<GcreportAssistPojo> {
    @Autowired
    private BdeGcreportPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<GcreportAssistPojo> listAssist(String dataSourceCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DIM.CODE, DIM.NAME, DIM.BASEDATACODE TABLENAME\n");
        sql.append("FROM BUD_MODEL M\n");
        sql.append("         JOIN BUD_MODELLINK ML ON M.CODE = ML.MODELCODE\n");
        sql.append("         JOIN BUD_DIMENSION DIM ON ML.FIELDCODE = DIM.CODE\n");
        sql.append("WHERE M.CODE = 'GC_OFFSETVCHRITEM'\n");
        sql.append("  AND M.PUBLISHSTATE = 'PUBLISHED'\n");
        sql.append("  AND ML.FIELDTYPE = 'dim'\n");
        sql.append("UNION ALL\n");
        sql.append("SELECT MEAS.CODE, MEAS.NAME, NULL AS TABLENAME \n");
        sql.append("FROM BUD_MODEL M\n");
        sql.append("         JOIN BUD_MODELLINK ML ON M.CODE = ML.MODELCODE\n");
        sql.append("         JOIN BUD_MEASUREMENT MEAS ON ML.FIELDCODE = MEAS.CODE\n");
        sql.append("WHERE M.CODE = 'GC_OFFSETVCHRITEM'\n");
        sql.append("  AND M.PUBLISHSTATE = 'PUBLISHED'\n");
        sql.append("  AND ML.FIELDTYPE = 'meas'");
        return this.dataSourceService.query(dataSourceCode, sql.toString(), null, (RowMapper)new BeanPropertyRowMapper(GcreportAssistPojo.class));
    }
}

