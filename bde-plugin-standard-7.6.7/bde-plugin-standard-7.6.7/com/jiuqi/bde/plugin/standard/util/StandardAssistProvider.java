/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.standard.util;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.standard.BdeStandardPluginType;
import com.jiuqi.bde.plugin.standard.util.AssistPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class StandardAssistProvider
implements IAssistProvider<AssistPojo> {
    @Autowired
    private BdeStandardPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<AssistPojo> listAssist(String dataSourceCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.STDCODE AS CODE,   \n");
        sql.append("       T.STDNAME AS NAME,  \n");
        sql.append("       T.TABLENAME AS TABLENAME,  \n");
        sql.append("       T.SHARETYPE AS SHARETYPE  \n");
        sql.append("  FROM JC_ASSISTDIM T  \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append(" ORDER BY T.STDCODE  \n");
        List assistList = this.dataSourceService.query(dataSourceCode, sql.toString(), null, (RowMapper)new BeanPropertyRowMapper(AssistPojo.class));
        return assistList;
    }
}

