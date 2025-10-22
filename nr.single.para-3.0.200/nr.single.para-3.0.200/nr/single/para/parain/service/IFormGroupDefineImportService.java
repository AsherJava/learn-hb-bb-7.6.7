/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 */
package nr.single.para.parain.service;

import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import java.util.List;
import nr.single.para.parain.internal.cache.TaskImportContext;

public interface IFormGroupDefineImportService {
    public String importFormGroupDefines(TaskImportContext var1, String var2) throws Exception;

    public void updateFormGroup(List<DesignFormGroupDefine> var1, boolean var2, String var3);

    public void importDataGroups(TaskImportContext var1) throws Exception;

    public DesignDataGroup createNewDataGroup(String var1, String var2, String var3, String var4, String var5, String var6, String var7) throws Exception;

    public void deleteOtherFormgGroups(TaskImportContext var1, String var2) throws Exception;
}

