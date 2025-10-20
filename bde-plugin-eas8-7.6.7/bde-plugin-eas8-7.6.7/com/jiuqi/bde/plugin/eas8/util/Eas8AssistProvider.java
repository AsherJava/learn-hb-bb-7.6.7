/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.eas8.util;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.eas8.Eas8PluginType;
import com.jiuqi.bde.plugin.eas8.util.AssistPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class Eas8AssistProvider
implements IAssistProvider<AssistPojo> {
    @Autowired
    private Eas8PluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<AssistPojo> listAssist(String dataSourceCode) {
        DataSourceService dataSourceService = (DataSourceService)ApplicationContextRegister.getBean(DataSourceService.class);
        List assistList = dataSourceService.query(dataSourceCode, "SELECT ASS.FName_L2 AS NAME, ASS.FMappingFieldName AS CODE, ASS.FGLASSTACTTYPEGRPID AS GROUPID, FREALTIONDATAOBJECT AS TABLENAME From T_BD_AsstActType ASS", null, (RowMapper)new BeanPropertyRowMapper(AssistPojo.class));
        return assistList;
    }
}

