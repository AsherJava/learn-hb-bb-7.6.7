/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.service;

import com.jiuqi.nr.mapping.bean.FormulaMapping;
import java.util.List;

public interface FormulaMappingService {
    public List<FormulaMapping> findByMSFormulaForm(String var1, String var2, String var3);

    public List<FormulaMapping> findByMS(String var1);

    public void save(String var1, String var2, String var3, List<FormulaMapping> var4);

    public void clear(String var1, String var2, String var3);
}

