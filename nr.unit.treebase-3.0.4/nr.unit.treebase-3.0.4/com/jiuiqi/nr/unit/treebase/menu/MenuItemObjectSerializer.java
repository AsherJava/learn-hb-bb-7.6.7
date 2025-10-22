/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuiqi.nr.unit.treebase.menu;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.menu.MenuItemObject;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MenuItemObjectSerializer
extends JsonSerializer<MenuItemObject> {
    public void serialize(MenuItemObject value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (null != value.getDivider() && value.getDivider().booleanValue()) {
            gen.writeStartObject();
            gen.writeBooleanField("divider", value.getDivider().booleanValue());
            gen.writeEndObject();
        } else {
            List<IMenuItemObject> children;
            Map<String, Object> data;
            gen.writeStartObject();
            gen.writeStringField("key", value.getKey());
            gen.writeStringField("code", value.getCode());
            gen.writeStringField("title", value.getTitle());
            gen.writeNumberField("type", value.getType().value);
            if (StringUtils.isNotEmpty((String)value.getIcon())) {
                gen.writeStringField("icon", value.getIcon());
            }
            if (null != value.getDisabled()) {
                gen.writeBooleanField("disabled", value.getDisabled().booleanValue());
            }
            if (null != value.getChecked()) {
                gen.writeBooleanField("checked", value.getChecked().booleanValue());
            }
            if ((data = value.getData()) != null && !data.isEmpty()) {
                for (Map.Entry<String, Object> entrySet : data.entrySet()) {
                    gen.writeObjectField(entrySet.getKey(), entrySet.getValue());
                }
            }
            if (null != (children = value.getChildren()) && !children.isEmpty()) {
                gen.writeArrayFieldStart("children");
                for (IMenuItemObject m : children) {
                    gen.writeObject((Object)m);
                }
                gen.writeEndArray();
            }
            gen.writeEndObject();
        }
    }
}

