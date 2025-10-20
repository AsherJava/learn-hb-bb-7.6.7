/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.cloud_acca.assist;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.cloud_acca.BdeCloudAccaPluginType;
import com.jiuqi.bde.plugin.cloud_acca.assist.CloudAccaAssistPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CloudAccaAssistProvider
implements IAssistProvider<CloudAccaAssistPojo> {
    @Autowired
    private BdeCloudAccaPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<CloudAccaAssistPojo> listAssist(String dataSourceCode) {
        List assistList = this.dataSourceService.query(dataSourceCode, "SELECT CODE,NAME,VALUETYPE FROM MD_ASSISTDIM WHERE  PUBLISHFLAG=1 and STOPFLAG=0 ", null, (RowMapper)new BeanPropertyRowMapper(CloudAccaAssistPojo.class));
        return assistList;
    }
}

