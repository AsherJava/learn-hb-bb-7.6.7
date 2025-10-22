/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.bpm.de.dataflow.step.bean;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.LinkedHashSet;
import java.util.List;

public class StepTree {
    private String id;
    private String code;
    private String title;
    private String selectParentId;
    private String parentId;
    private boolean parented;
    private boolean leafed;
    private List<StepTree> selectChildren;
    private List<IEntityRow> directChildren;
    private IEntityTable entityTable;
    private LinkedHashSet<String> resourceMap = new LinkedHashSet();
    private int level;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public IEntityTable getEntityTable() {
        return this.entityTable;
    }

    public void setEntityTable(IEntityTable entityTable) {
        this.entityTable = entityTable;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<StepTree> getSelectChildren() {
        return this.selectChildren;
    }

    public void setSelectChildren(List<StepTree> selectChildren) {
        this.selectChildren = selectChildren;
    }

    public boolean isLeafed() {
        return this.leafed;
    }

    public void setLeafed(boolean leafed) {
        this.leafed = leafed;
    }

    public LinkedHashSet<String> getResourceMap() {
        return this.resourceMap;
    }

    public void setResourceMap(LinkedHashSet<String> resourceMap) {
        this.resourceMap = resourceMap;
    }

    public boolean isParented() {
        return this.parented;
    }

    public void setParented(boolean parented) {
        this.parented = parented;
    }

    public String getSelectParentId() {
        return this.selectParentId;
    }

    public void setSelectParentId(String selectParentId) {
        this.selectParentId = selectParentId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<IEntityRow> getDirectChildren() {
        return this.directChildren;
    }

    public void setDirectChildren(List<IEntityRow> directChildren) {
        this.directChildren = directChildren;
    }
}

