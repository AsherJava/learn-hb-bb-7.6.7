/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.plugin.nc6.BdeNc6PluginType
 *  com.jiuqi.dc.base.common.enums.LicenceSymbolEnum
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 */
package com.jiuqi.bde.plugin.bip;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.nc6.BdeNc6PluginType;
import com.jiuqi.dc.base.common.enums.LicenceSymbolEnum;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeBipPluginType
extends IBdePluginType {
    private static final String SYMBOL = "BIP";
    private static final String TITLE = "\u3010\u7528\u53cb\u3011BIP_\u9ad8\u7ea7\u7248";
    @Autowired
    private BdeNc6PluginType pluginType;

    public String getSymbol() {
        return SYMBOL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getLicenceSymbol() {
        return LicenceSymbolEnum.YONYOU.getSymbol();
    }

    public String storageType() {
        return this.pluginType.storageType();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        return this.pluginType.subjectField(dataSchemeDTO);
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        return this.pluginType.currencyField(dataSchemeDTO);
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        return this.pluginType.cfItemField(dataSchemeDTO);
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        return this.pluginType.listAssistField(dataSchemeDTO);
    }

    public Integer getOrder() {
        return 340;
    }
}

