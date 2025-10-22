/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl
 *  com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataRow;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl;
import com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupDataRow
extends EntityRowImpl
implements IGroupDataRow {
    public static final String KEY_OF_UN_GROUPED = "Un-Grouped";
    public static final String TITLE_OF_UN_GROUPED = "\u672a\u5206\u7ec4";
    private String key;
    private String code;
    private String title;
    private IEntityRow data;
    private String referEntityId;
    private IGroupDataRow parent;
    private final List<IGroupDataRow> children = new ArrayList<IGroupDataRow>();
    private final Set<String> childKeys = new HashSet<String>();

    private GroupDataRow(ReadonlyTableImpl table, DimensionValueSet rowKeys, int rowIndex) {
        super(table, rowKeys, rowIndex);
    }

    public GroupDataRow(String key) {
        this(null, null, 0);
        this.key = key;
    }

    public String getEntityKeyData() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getParentEntityKey() {
        return this.parent != null ? this.parent.getEntityKeyData() : null;
    }

    public String getAsString(String code) throws RuntimeException {
        return this.data.getAsString(code);
    }

    public String[] getParentsEntityKeyDataPath() {
        String[] path = new String[]{};
        for (IGroupDataRow parent = this; parent != null; parent = parent.getParent()) {
            path = (String[])JavaBeanUtils.concatArrays((Object[])new String[]{parent.getEntityKeyData()}, (Object[][])new String[][]{path});
        }
        return path;
    }

    @Override
    public String getReferEntityId() {
        return this.referEntityId;
    }

    @Override
    public String getRowType() {
        if (this.isDimRootRow()) {
            return "node@Dim";
        }
        return "node@Group";
    }

    @Override
    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    public boolean isDimRootRow() {
        return "dim-entity".equals(this.referEntityId);
    }

    @Override
    public IEntityRow getData() {
        return this.data;
    }

    @Override
    public IGroupDataRow getParent() {
        return this.parent;
    }

    @Override
    public List<IGroupDataRow> getChildren() {
        return this.children;
    }

    public void setReferEntityId(String referEntityId) {
        this.referEntityId = referEntityId;
    }

    public void setParent(IGroupDataRow parent) {
        this.parent = parent;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setData(IEntityRow data) {
        this.data = data;
    }

    public void appendChild(IGroupDataRow child) {
        if (child != null && !this.childKeys.contains(child.getEntityKeyData())) {
            this.children.add(child);
            this.childKeys.add(child.getEntityKeyData());
        }
    }

    public static GroupDataRow getUnGroupRow() {
        GroupDataRow unGroupRow = new GroupDataRow(KEY_OF_UN_GROUPED);
        unGroupRow.setReferEntityId(KEY_OF_UN_GROUPED);
        unGroupRow.setCode(KEY_OF_UN_GROUPED);
        unGroupRow.setTitle(TITLE_OF_UN_GROUPED);
        return unGroupRow;
    }
}

