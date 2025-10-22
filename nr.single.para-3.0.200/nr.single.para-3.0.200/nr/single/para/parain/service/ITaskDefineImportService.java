/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package nr.single.para.parain.service;

import com.jiuqi.np.common.exception.JQException;
import nr.single.para.parain.internal.cache.TaskImportContext;

public interface ITaskDefineImportService {
    public void importTaskGroupDefines(TaskImportContext var1);

    public String importTaskDefine(TaskImportContext var1) throws Exception;

    public void UpdateContextCache(TaskImportContext var1);

    public void UpdateTask(TaskImportContext var1) throws JQException;

    public void UpdateMapSchemeDefineByTask(TaskImportContext var1);

    public String importFormSchemeDefine(TaskImportContext var1) throws Exception;

    public void importTaskLinkDefines(TaskImportContext var1);

    public String getDataSchemKeyByTask(TaskImportContext var1, String var2);

    public void updateTaskEntitys(TaskImportContext var1) throws Exception;
}

