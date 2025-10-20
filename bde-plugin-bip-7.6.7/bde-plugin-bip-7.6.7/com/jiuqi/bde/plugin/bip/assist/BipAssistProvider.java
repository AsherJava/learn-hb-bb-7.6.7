/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.bde.plugin.nc6.assist.Nc6AssistPojo
 *  com.jiuqi.bde.plugin.nc6.assist.Nc6AssistProvider
 */
package com.jiuqi.bde.plugin.bip.assist;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.bip.BdeBipPluginType;
import com.jiuqi.bde.plugin.nc6.assist.Nc6AssistPojo;
import com.jiuqi.bde.plugin.nc6.assist.Nc6AssistProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BipAssistProvider
implements IAssistProvider<Nc6AssistPojo> {
    @Autowired
    private BdeBipPluginType pluginType;
    @Autowired
    private Nc6AssistProvider nc6AssistProvider;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<Nc6AssistPojo> listAssist(String dataSourceCode) {
        return this.nc6AssistProvider.listAssist(dataSourceCode);
    }
}

