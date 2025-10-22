/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.parain.service;

import nr.single.para.parain.internal.cache.TaskImportContext;

public interface IFormDefineImportService {
    public String importFormGroupDefines(TaskImportContext var1, String var2) throws Exception;

    public void deleteOtherFormGroups(TaskImportContext var1, String var2) throws Exception;

    public void importFormDefines(TaskImportContext var1, String var2) throws Exception;
}

