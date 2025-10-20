/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.ebs_r11.assist;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.ebs_r11.BdeEbsR11PluginType;
import com.jiuqi.bde.plugin.ebs_r11.assist.EbsR11AssistPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class EbsR11AssistProvider
implements IAssistProvider<EbsR11AssistPojo> {
    @Autowired
    private BdeEbsR11PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;
    private static final String LIST_ASSIST_SQL = "SELECT DISTINCT APPLICATION_COLUMN_NAME AS code,       FLEX_VALUE_SET_NAME AS flexValueSetName,       SEGMENT_NAME AS name   FROM CUX_GL_ACCOUNT_STR";

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<EbsR11AssistPojo> listAssist(String dataSourceCode) {
        return this.dataSourceService.query(dataSourceCode, LIST_ASSIST_SQL, null, (RowMapper)new BeanPropertyRowMapper(EbsR11AssistPojo.class));
    }
}

