/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaParam;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.RulerDefine;
import java.util.List;

public interface Ruler {
    public RulerDefine getDefine();

    public List<CheckResult> execute(String var1);

    public List<CheckResult> execute(List<FormulaParam> var1, String var2);

    public List<CheckResult> execute(ModelDataContext var1, FormulaImpl var2);

    public Object evaluate(ModelDataContext var1, FormulaImpl var2);
}

