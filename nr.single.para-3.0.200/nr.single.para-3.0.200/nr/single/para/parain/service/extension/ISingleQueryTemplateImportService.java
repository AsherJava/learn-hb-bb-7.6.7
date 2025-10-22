/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.parain.service.extension;

import java.util.List;
import nr.single.para.parain.bean.SingleQueryTemplateDefine;
import nr.single.para.parain.bean.exception.SingleParaImportException;
import nr.single.para.parain.bean.result.SingleQueryImportResult;

public interface ISingleQueryTemplateImportService {
    public SingleQueryImportResult importSingleQueryTemplates(List<SingleQueryTemplateDefine> var1) throws SingleParaImportException;
}

