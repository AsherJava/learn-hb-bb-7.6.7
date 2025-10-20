/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.intf.model.PluginDataDel
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.ruler.impl.EditableFieldsDefineImpl
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.intf.model.PluginDataDel;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.ruler.impl.EditableFieldsDefineImpl;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class EditableFieldPluginDataDelImpl
implements PluginDataDel {
    public void pluginDataDel(PluginDefine pluginDefine, DataDefineImpl tableDefine) {
        EditableFieldsDefineImpl define = (EditableFieldsDefineImpl)pluginDefine;
        if (define.getDefineInfo() == null) {
            return;
        }
        List config = (List)define.getDefineInfo().get("config");
        if (config == null) {
            return;
        }
        for (int i = 0; i < tableDefine.getTables().size(); ++i) {
            DataTableDefineImpl dataTableDefine = (DataTableDefineImpl)tableDefine.getTables().get(i);
            String tableName = dataTableDefine.getName() + "[";
            for (int i1 = config.size() - 1; i1 >= 0; --i1) {
                Map stringObjectMap = (Map)config.get(i1);
                if (stringObjectMap.get("formula").toString().toUpperCase().contains(tableName)) {
                    config.remove(i1);
                    continue;
                }
                List fieldsMap = (List)stringObjectMap.get("fields");
                for (int i2 = 0; i2 < fieldsMap.size(); ++i2) {
                    if (!((Map)fieldsMap.get(i2)).get("tableName").equals(tableName)) continue;
                    config.remove(i1);
                }
            }
        }
    }

    public String getName() {
        return "editableFields";
    }
}

