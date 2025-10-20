/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import com.jiuqi.va.biz.intf.data.DataException;
import com.jiuqi.va.biz.intf.data.DataTableNode;
import com.jiuqi.va.biz.intf.data.DataTableNodeContainer;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.intf.value.MissingObjectException;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DataTableNodeContainerImpl<T extends DataTableNode>
extends ListContainerImpl<T>
implements DataTableNodeContainer<T> {
    private Map<String, T> nameMap;
    private Map<UUID, T> idMap;
    private Map<UUID, ListContainerImpl<T>> parentMap;
    private T masterTable;

    public DataTableNodeContainerImpl(List<T> list) {
        super(list);
    }

    private Map<String, T> getNameMap() {
        if (this.nameMap == null) {
            HashMap nameMap = new HashMap();
            this.list.forEach((? super T o) -> nameMap.put(o.getName(), o));
            this.nameMap = nameMap;
        }
        return this.nameMap;
    }

    private Map<UUID, T> getIdMap() {
        if (this.idMap == null) {
            HashMap idMap = new HashMap();
            this.list.forEach((? super T o) -> idMap.put(o.getId(), o));
            this.idMap = idMap;
        }
        return this.idMap;
    }

    private Map<UUID, ListContainerImpl<T>> getParentMap() {
        if (this.parentMap == null) {
            HashMap<UUID, ListContainerImpl<T>> parentMap = new HashMap<UUID, ListContainerImpl<T>>();
            HashMap<UUID, ArrayList<DataTableNode>> map = new HashMap<UUID, ArrayList<DataTableNode>>();
            for (DataTableNode t : this.list) {
                ArrayList<DataTableNode> list = (ArrayList<DataTableNode>)map.get(t.getParentId());
                if (list == null) {
                    list = new ArrayList<DataTableNode>();
                    map.put(t.getParentId(), list);
                    parentMap.put(t.getParentId(), new ListContainerImpl(list));
                }
                list.add(t);
            }
            this.parentMap = parentMap;
        }
        return this.parentMap;
    }

    @Override
    public T find(String name) {
        return (T)((DataTableNode)this.getNameMap().get(name));
    }

    @Override
    public T get(String name) {
        DataTableNode t = (DataTableNode)this.getNameMap().get(name);
        if (t == null) {
            throw new MissingObjectException(BizBindingI18nUtil.getMessage("va.bizbinding.getobjfailed") + name);
        }
        return (T)t;
    }

    @Override
    public T find(UUID id) {
        return (T)((DataTableNode)this.getIdMap().get(id));
    }

    @Override
    public T get(UUID id) {
        DataTableNode t = (DataTableNode)this.getIdMap().get(id);
        if (t == null) {
            throw new MissingObjectException(BizBindingI18nUtil.getMessage("va.bizbinding.getobjfailed") + id);
        }
        return (T)t;
    }

    @Override
    public ListContainer<? extends T> getDetailTables(UUID id) {
        ListContainerImpl detailTables = this.getParentMap().get(id);
        return detailTables == null ? new ListContainerImpl(Collections.emptyList()) : detailTables;
    }

    @Override
    public T getMasterTable() {
        if (this.masterTable == null) {
            ListContainerImpl<T> tables = this.getParentMap().get(null);
            DataTableNode table = null;
            for (int i = 0; i < tables.size(); ++i) {
                if (((DataTableNode)tables.get(i)).getTableType() != DataTableType.DATA && ((DataTableNode)tables.get(i)).getTableType() != DataTableType.REFER) continue;
                if (table == null) {
                    table = (DataTableNode)tables.get(i);
                    continue;
                }
                table = null;
                break;
            }
            if (table == null) {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.cannotdeterminemastertable"));
            }
            this.masterTable = table;
        }
        return this.masterTable;
    }
}

