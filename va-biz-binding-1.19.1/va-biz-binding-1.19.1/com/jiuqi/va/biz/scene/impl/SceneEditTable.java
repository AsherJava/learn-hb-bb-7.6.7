/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.scene.impl;

import com.jiuqi.va.biz.scene.impl.SceneEditPropImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class SceneEditTable
extends SceneEditPropImpl {
    private final String type = "table";
    private List<Map<String, Object>> tables = new ArrayList<Map<String, Object>>();

    @Override
    public String getType() {
        return this.type;
    }

    public List<Map<String, Object>> getTables() {
        return this.tables;
    }

    @Override
    public boolean isEnable() {
        if (!StringUtils.hasText(this.getExpression())) {
            return false;
        }
        return !CollectionUtils.isEmpty(this.tables);
    }
}

