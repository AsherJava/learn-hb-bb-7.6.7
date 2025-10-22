/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.file.SingleFile
 */
package nr.single.para.parain.controller;

import com.jiuqi.nr.single.core.file.SingleFile;
import nr.single.para.parain.controller.SingleParaImportOption;

public interface ISingleParaImportController {
    public String ImportSingeTask(String var1) throws Exception;

    public String ImportSingleToTask(String var1, String var2) throws Exception;

    public String ImportSingleToFormScheme(String var1, String var2, String var3, SingleParaImportOption var4) throws Exception;

    public SingleFile GetSingleFile(String var1) throws Exception;
}

