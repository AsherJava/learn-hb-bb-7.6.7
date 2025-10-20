/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.k3.util;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.k3.BdeK3PluginType;
import com.jiuqi.bde.plugin.k3.util.AssistPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class K3AssistProvider
implements IAssistProvider<AssistPojo> {
    @Autowired
    private BdeK3PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<AssistPojo> listAssist(String dataSourceCode) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("          TABLEINFO.FNumber AS CODE,\n");
        query.append("          TABLEINFO.FNAME AS NAME,\n");
        query.append("          TABLEINFO.FSQLTABLENAME AS TABLENAME,\n");
        query.append("          TABLEINFO.FITEMCLASSID AS ASSID,\n");
        query.append("  FROM\n");
        query.append("          T_ITEMCLASS TABLEINFO\n");
        List assistList = this.dataSourceService.query(dataSourceCode, query.toString(), null, (RowMapper)new BeanPropertyRowMapper(AssistPojo.class));
        assistList.forEach(item -> item.setTableName("T_" + item.getCode()));
        return assistList;
    }
}

