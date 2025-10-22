/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.service;

import java.util.List;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.configurations.bean.UnitCustomMapping;

public interface SingleMappingService {
    public List<SingleConfigInfo> getAllMappingInTask(String var1);

    public ISingleMappingConfig getConfigByKey(String var1);

    public ISingleMappingConfig getConfigByKeyAndType(String var1, int var2);

    public List<SingleConfigInfo> getAllMappingInReport(String var1);

    public List<SingleConfigInfo> getAllMapping();

    public List<SingleConfigInfo> getConfigInSingleTask(String var1);

    public SingleConfigInfo getMappingByKey(String var1);

    public void saveEntityUnitCustomMapping(String var1, List<UnitCustomMapping> var2);
}

