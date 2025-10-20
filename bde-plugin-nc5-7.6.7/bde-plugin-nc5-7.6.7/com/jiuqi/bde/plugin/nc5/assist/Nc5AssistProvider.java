/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.nc5.assist;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.nc5.BdeNc5PluginType;
import com.jiuqi.bde.plugin.nc5.assist.Nc5AssistPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class Nc5AssistProvider
implements IAssistProvider<Nc5AssistPojo> {
    @Autowired
    private BdeNc5PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<Nc5AssistPojo> listAssist(String dataSourceCode) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("        PK_BDINFO AS CODE,");
        sql.append("        BDNAME AS NAME,");
        sql.append("        BASEDOCTABLENAME AS BASEDOCTABLENAME,");
        sql.append("        TABLENAME AS TABLENAME,");
        sql.append("        TABLEPKNAME AS TABLEPK,");
        sql.append("        CODEFIELDNAME AS CODEFIELD,");
        sql.append("        NAMEFIELDNAME AS NAMEFIELD,");
        sql.append("        PK_DEFDEF AS DEFINE");
        sql.append(" FROM   BD_BDINFO ");
        sql.append(" WHERE  (DR=0 OR DR IS NULL ) AND REFSYSTEM='gl'  AND TABLENAME IS NOT NULL ");
        return this.dataSourceService.query(dataSourceCode, sql.toString(), null, (RowMapper)new BeanPropertyRowMapper(Nc5AssistPojo.class));
    }
}

