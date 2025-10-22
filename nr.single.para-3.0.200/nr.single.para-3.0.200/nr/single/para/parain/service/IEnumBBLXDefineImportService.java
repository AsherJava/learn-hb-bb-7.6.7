/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel
 */
package nr.single.para.parain.service;

import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.parain.internal.cache.TaskImportContext;

public interface IEnumBBLXDefineImportService {
    public void importEnumBBLXDefine(TaskImportContext var1) throws Exception;

    public void importEnumBBLXDefine(TaskImportContext var1, EnumsItemModel var2, CompareDataEnumDTO var3) throws Exception;
}

