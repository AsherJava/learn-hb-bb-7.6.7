/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.parain.util;

import java.util.List;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.util.SingleSchemePeriodObj;

public interface ISingleSchemePeriodUtil {
    public void saveSchemePeriodLinks(List<SingleSchemePeriodObj> var1, String var2) throws Exception;

    public void createSchemePeriodLink(TaskImportContext var1, String var2, String var3, String var4, String var5) throws Exception;
}

