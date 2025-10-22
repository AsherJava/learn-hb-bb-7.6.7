/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package nr.single.map.configurations.dao;

import java.util.List;
import javax.validation.constraints.NotNull;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.SingleConfigInfo;

public interface ConfigDao {
    public void insert(ISingleMappingConfig var1);

    public ISingleMappingConfig query(String var1);

    public void update(ISingleMappingConfig var1);

    public void delete(String var1);

    public List<SingleConfigInfo> queryConfigByTask(String var1);

    public List<SingleConfigInfo> queryConfigByScheme(String var1);

    public List<ISingleMappingConfig> getConfigByScheme(@NotNull String var1);

    public SingleConfigInfo queryConfigByKey(String var1);

    public List<SingleConfigInfo> queryAllConfigInfo();

    public List<SingleConfigInfo> queryConfigBySingleTask(String var1);

    public SingleConfigInfo queryInfo(String var1);

    public void updateInfo(SingleConfigInfo var1);
}

