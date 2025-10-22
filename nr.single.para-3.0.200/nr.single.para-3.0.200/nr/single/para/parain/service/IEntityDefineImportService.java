/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 */
package nr.single.para.parain.service;

import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import nr.single.para.parain.internal.cache.TaskImportContext;

public interface IEntityDefineImportService {
    public String getType();

    public String importCorpEntity(TaskImportContext var1) throws Exception;

    public String updateCorpEntity(TaskImportContext var1) throws Exception;

    public void CheckAddVersionEntity(DesignTaskDefine var1) throws Exception;
}

