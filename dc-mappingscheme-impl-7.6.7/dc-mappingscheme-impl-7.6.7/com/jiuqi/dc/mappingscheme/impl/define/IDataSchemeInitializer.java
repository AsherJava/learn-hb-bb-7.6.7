/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultDataVO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 */
package com.jiuqi.dc.mappingscheme.impl.define;

import com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultDataVO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.DataSchemeInit;
import com.jiuqi.dc.mappingscheme.impl.common.DataTableInfo;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDataSchemeInitializer {
    default public IPluginType getPluginType() {
        return null;
    }

    public void doInit(DataSchemeDTO var1);

    public SchemeDefaultDataVO getDefaultSchemeData(DataSchemeDTO var1);

    public DataSchemeInit doAnalysis(DataSchemeDTO var1, String var2);

    public Map<String, Object> getRuleAndBaseData(Map<String, Object> var1, Set<String> var2, String var3);

    default public List<DataTableInfo> getTableInfoList(DataSchemeDTO dataSchemeDTO) {
        return new ArrayList<DataTableInfo>();
    }
}

