/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface CheckItem
extends RulerItem {
    @Override
    default public FormulaType getFormulaType() {
        return FormulaType.CHECK;
    }

    @Override
    default public Map<String, Map<String, Boolean>> getTriggerFields(ModelDefine modelDefine) {
        return null;
    }

    @Override
    default public Map<String, Map<String, Boolean>> getAssignFields(ModelDefine modelDefine) {
        return null;
    }

    public void execute(Model var1, Stream<TriggerEvent> var2, List<CheckResult> var3);

    @Override
    default public void execute(Model model, Stream<TriggerEvent> events) {
        throw new UnsupportedOperationException();
    }
}

