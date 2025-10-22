/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.SingleMappingConfig
 */
package nr.single.para.parain.maping;

import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.SingleMappingConfig;
import nr.single.para.parain.internal.cache.TaskImportContext;

public interface ITaskFileMapingService {
    public SingleMappingConfig MakeSingleToMaping(String var1, String var2, String var3, String var4, byte[] var5) throws Exception;

    public SingleMappingConfig copyMapConfig(TaskImportContext var1);

    public void saveMapConfig(String var1, byte[] var2, SingleMappingConfig var3);

    public void copyMapConfigForm(ISingleMappingConfig var1, ISingleMappingConfig var2, boolean var3);

    public void updateMapConfig(String var1, byte[] var2, SingleMappingConfig var3);

    public byte[] splitParaData(String var1, byte[] var2) throws Exception;
}

