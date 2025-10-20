/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.impl.value.NamedContainerImpl
 *  com.jiuqi.va.biz.intf.model.PluginDataDel
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerDefineImpl
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import com.jiuqi.va.biz.intf.model.PluginDataDel;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RulerPluginDataDelImpl
implements PluginDataDel {
    public void pluginDataDel(PluginDefine pluginDefine, DataDefineImpl tableDefine) {
        RulerDefineImpl rulerDefine = (RulerDefineImpl)pluginDefine;
        List formulas = rulerDefine.getFormulaList();
        for (int i = 0; i < tableDefine.getTables().size(); ++i) {
            DataTableDefineImpl dataTableDefine = (DataTableDefineImpl)tableDefine.getTables().get(i);
            String tableName = dataTableDefine.getName() + "[";
            NamedContainerImpl fields = dataTableDefine.getFields();
            for (int j = formulas.size() - 1; j >= 0; --j) {
                FormulaImpl formula = (FormulaImpl)formulas.get(j);
                String objectType = formula.getObjectType();
                if (objectType.equals("field") || objectType.equals("table")) {
                    UUID objectId = formula.getObjectId();
                    List fieldDefines = fields.stream().filter(o -> o.getId().equals(objectId)).collect(Collectors.toList());
                    if (!fieldDefines.isEmpty()) {
                        formulas.remove(j);
                        continue;
                    }
                    List tableDefines = tableDefine.getTables().stream().filter(o -> o.getId().equals(objectId)).collect(Collectors.toList());
                    if (!tableDefines.isEmpty()) {
                        formulas.remove(j);
                        continue;
                    }
                }
                if (!formula.getExpression().toUpperCase().contains(tableName)) continue;
                formulas.remove(j);
            }
        }
    }

    public String getName() {
        return "ruler";
    }
}

