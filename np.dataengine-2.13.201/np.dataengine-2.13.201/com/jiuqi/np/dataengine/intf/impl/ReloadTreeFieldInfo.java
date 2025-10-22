/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.np.dataengine.intf.IEntityTable;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.List;
import java.util.Map;

public class ReloadTreeFieldInfo {
    private FieldDefine fieldDefine;
    private EntityViewDefine entityView;
    private IEntityTable entityTable;
    private List<Integer> showLevels;
    private Map<String, String> titleMap;

    public FieldDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public void setFieldDefine(FieldDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
    }

    public EntityViewDefine getEntityView() {
        return this.entityView;
    }

    public void setEntityView(EntityViewDefine entityView) {
        this.entityView = entityView;
    }

    public List<Integer> getShowLevels() {
        return this.showLevels;
    }

    public void setShowLevels(List<Integer> showLevels) {
        this.showLevels = showLevels;
    }

    protected IEntityTable getEntityTable() {
        return this.entityTable;
    }

    protected void setEntityTable(IEntityTable entityTable) {
        this.entityTable = entityTable;
    }

    public Map<String, String> getTitleMap() {
        return this.titleMap;
    }

    public void setTitleMap(Map<String, String> titleMap) {
        this.titleMap = titleMap;
    }
}

