/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.paraout.service;

import nr.single.para.paraout.bean.TaskExportContext;

public interface ITaskDefineEportService {
    public void exportTaskGroupDefines(TaskExportContext var1);

    public void exportTaskDefine(TaskExportContext var1);

    public void initCahce(TaskExportContext var1);
}

