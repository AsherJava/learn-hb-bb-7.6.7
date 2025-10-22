/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 */
package nr.single.para.parain.maping2;

import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.maping2.SingleMappingTransOption;

public interface ITaskFileMapingTansService {
    public void splictJioConifg(TaskImportContext var1, String var2) throws Exception;

    public void copyAndUploadJioConfig(TaskImportContext var1, String var2, byte[] var3, String var4, SingleMappingTransOption var5, boolean var6) throws Exception;

    public void copyAndUploadJioConfigByFileKey(TaskImportContext var1, String var2, String var3, String var4, SingleMappingTransOption var5, boolean var6) throws Exception;

    public MappingScheme findMappingScheme(TaskImportContext var1);
}

