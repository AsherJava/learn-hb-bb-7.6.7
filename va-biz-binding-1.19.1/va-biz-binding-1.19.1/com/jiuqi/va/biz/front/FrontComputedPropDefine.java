/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.front;

import com.jiuqi.va.biz.front.FrontDataDefine;
import com.jiuqi.va.biz.front.FrontModelDefine;
import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.ruler.impl.ComputedPropDefineImpl;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class FrontComputedPropDefine
extends FrontPluginDefine {
    private Map<UUID, Object> computedProps = new HashMap<UUID, Object>();
    private Map<String, Set<String>> triggerFields = new HashMap<String, Set<String>>();
    private transient FrontDataDefine frontDataDefine;
    private transient ComputedPropDefineImpl computedPropDefineImpl;

    public FrontComputedPropDefine(FrontModelDefine frontModelDefine, PluginDefine pluginDefine) {
        super(frontModelDefine, pluginDefine);
        this.computedPropDefineImpl = (ComputedPropDefineImpl)pluginDefine;
    }

    @Override
    protected void initialize() {
        this.computedProps = this.computedPropDefineImpl.getComputedProps();
        this.frontDataDefine = this.frontModelDefine.get(FrontDataDefine.class);
        this.computedPropDefineImpl.getItems().stream().forEach(o -> {
            Map<String, Map<String, Boolean>> triggerFields = o.getTriggerFields(this.frontModelDefine.getModelDefine());
            if (triggerFields != null) {
                triggerFields.forEach((tableName, fields) -> fields.keySet().forEach(fieldName -> this.addTriggerField((String)tableName, (String)fieldName)));
            }
        });
    }

    public void addTriggerField(String tableName, String fieldName) {
        Set fields = this.triggerFields.computeIfAbsent(tableName, k -> new HashSet());
        DataTableDefine tableDefine = this.frontDataDefine.getDataDefine().getTables().get(tableName);
        DataFieldDefine fieldDefine = tableDefine.getFields().get(fieldName);
        if (fieldDefine.getFieldType() == DataFieldType.DATA) {
            fields.add(fieldName);
        }
    }
}

