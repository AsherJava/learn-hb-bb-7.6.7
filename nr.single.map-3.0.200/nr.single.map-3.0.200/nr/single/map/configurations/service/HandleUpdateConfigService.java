/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.service;

import java.util.List;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.configurations.bean.ZbMapping;
import nr.single.map.configurations.internal.bean.IllegalData;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFormulaItem;

public interface HandleUpdateConfigService {
    public ISingleMappingConfig saveFormulas(String var1, String var2, List<SingleFileFormulaItem> var3, IllegalData var4);

    public UnitMapping saveEntity(UnitMapping var1, ISingleMappingConfig var2, IllegalData var3);

    public void saveZb(List<ZbMapping> var1, ISingleMappingConfig var2);

    public void saveEnumItem(List<SingleFileEnumInfo> var1, ISingleMappingConfig var2);

    public List<SingleFileFieldInfo> updateConfigZb(String var1, List<SingleFileFieldInfo> var2, List<SingleFileFieldInfo> var3);
}

