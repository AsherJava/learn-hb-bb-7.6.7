/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.parain.service;

import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.IEntityDefineImportService;

public interface IDataSchemeImportService {
    public String importDataSchemeDefine(TaskImportContext var1) throws Exception;

    public String updateDataSchemeDefine(TaskImportContext var1) throws Exception;

    public String importDataSchemeDefineFromOther(TaskImportContext var1, String var2) throws Exception;

    public IEntityDefineImportService getEntityDefineServcieByEnityId(String var1);

    public IEntityDefineImportService getEntityDefineServcieByCategory(String var1);

    public String updateDimEntitys(TaskImportContext var1) throws Exception;
}

