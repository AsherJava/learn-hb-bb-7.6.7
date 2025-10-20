/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.ebs_r12.assist;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.ebs_r12.BdeEbsR12PluginType;
import com.jiuqi.bde.plugin.ebs_r12.assist.EbsR12AssistPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class EbsR12AssistProvider
implements IAssistProvider<EbsR12AssistPojo> {
    @Autowired
    private BdeEbsR12PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;
    private static final String LIST_ASSIST_SQL = "SELECT DISTINCT APPLICATION_COLUMN_NAME AS code,       FLEX_VALUE_SET_NAME AS flexValueSetName,       SEGMENT_NAME AS name   FROM CUX_GL_ACCOUNT_STR";

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<EbsR12AssistPojo> listAssist(String dataSourceCode) {
        return this.dataSourceService.query(dataSourceCode, LIST_ASSIST_SQL, null, (RowMapper)new BeanPropertyRowMapper(EbsR12AssistPojo.class));
    }
}

