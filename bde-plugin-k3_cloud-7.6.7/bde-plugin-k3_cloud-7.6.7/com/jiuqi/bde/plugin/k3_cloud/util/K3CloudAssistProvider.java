/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.k3_cloud.util;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.k3_cloud.BdeK3CloudPluginType;
import com.jiuqi.bde.plugin.k3_cloud.util.AssistPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class K3CloudAssistProvider
implements IAssistProvider<AssistPojo> {
    @Autowired
    private BdeK3CloudPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<AssistPojo> listAssist(String dataSourceCode) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("          ASSTABLE.FVALUESOURCE AS CODE,\n");
        query.append("          FNAME AS NAME,\n");
        query.append("          ASSTABLE.FFLEXNUMBER AS ASSFIELD,\n");
        query.append("          TABLEINFO.FTABLENAME AS TABLENAME,\n");
        query.append("          TABLEINFO.FPKFIELDNAME AS PKFIELD,\n");
        query.append("          TABLEINFO.FNUMBERFIELDNAME AS CODEFIELD,\n");
        query.append("          TABLEINFO.FNAMEFIELDNAME AS NAMEFIELD\n");
        query.append("  FROM\n");
        query.append("          T_META_LOOKUPCLASS TABLEINFO\n");
        query.append("  INNER JOIN T_BD_FLEXITEMPROPERTY ASSTABLE ON\n");
        query.append("          TABLEINFO.FFORMID = ASSTABLE.FVALUESOURCE\n");
        query.append("  INNER JOIN T_BD_FLEXITEMPROPERTY_L LANG ON\n");
        query.append("          ASSTABLE.FID = LANG.FID\n");
        query.append("  WHERE\n");
        query.append("          LANG.FLOCALEID = 2052\n");
        List assistList = this.dataSourceService.query(dataSourceCode, query.toString(), null, (RowMapper)new BeanPropertyRowMapper(AssistPojo.class));
        assistList.forEach(item -> item.setTableName("T_" + item.getCode()));
        return assistList;
    }
}

