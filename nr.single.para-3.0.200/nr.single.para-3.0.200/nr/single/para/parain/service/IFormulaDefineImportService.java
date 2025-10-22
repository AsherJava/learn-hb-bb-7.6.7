/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package nr.single.para.parain.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import nr.single.para.parain.internal.cache.TaskImportContext;

public interface IFormulaDefineImportService {
    public void importFormulaDefines(TaskImportContext var1, String var2) throws JQException;

    public ExecutorContext initContext(TaskImportContext var1) throws Exception;
}

