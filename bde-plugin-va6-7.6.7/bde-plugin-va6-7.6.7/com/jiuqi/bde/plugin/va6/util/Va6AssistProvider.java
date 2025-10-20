/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.va6.util;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.va6.BdeVa6PluginType;
import com.jiuqi.bde.plugin.va6.util.Va6AssistPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class Va6AssistProvider
implements IAssistProvider<Va6AssistPojo> {
    @Autowired
    private BdeVa6PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<Va6AssistPojo> listAssist(String dataSourceCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.STDCODE AS CODE,   \n");
        sql.append("       MAX(T.STDNAME) AS NAME,  \n");
        sql.append("       MAX(T.MDTABLE) AS TABLENAME  \n");
        sql.append("  FROM MD_ASSISTDIM T  \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append("    AND T.ISPUBLISHED = 1  \n");
        sql.append("    AND T.ISASSISTQUERY = 1  \n");
        sql.append("    AND T.ISEFFECTBALAN = 1  \n");
        sql.append(" GROUP BY T.STDCODE, T.SORTORDER  \n");
        sql.append(" ORDER BY T.SORTORDER  \n");
        List assistList = this.dataSourceService.query(dataSourceCode, sql.toString(), null, (RowMapper)new BeanPropertyRowMapper(Va6AssistPojo.class));
        return assistList;
    }
}

