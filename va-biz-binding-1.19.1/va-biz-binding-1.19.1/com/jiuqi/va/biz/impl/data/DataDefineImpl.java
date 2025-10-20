/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.value.MissingObjectException;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DataDefineImpl
extends PluginDefineImpl
implements DataDefine {
    private List<DataTableDefineImpl> tables = new ArrayList<DataTableDefineImpl>();
    private transient DataTableNodeContainerImpl<? extends DataTableDefineImpl> tableContainer;
    private transient Map<UUID, String> maskFieldTableMap = new HashMap<UUID, String>();
    private transient Map<String, List<String>> encryptedTableFieldMap = new LinkedHashMap<String, List<String>>();

    @Override
    protected void setType(String type) {
        super.setType(type);
    }

    public DataTableNodeContainerImpl<? extends DataTableDefineImpl> getTables() {
        if (this.tableContainer == null) {
            this.tableContainer = new DataTableNodeContainerImpl<DataTableDefineImpl>(this.tables);
        }
        return this.tableContainer;
    }

    void addTable(DataTableDefineImpl table) {
        this.tables.add(table);
        this.tableContainer = null;
    }

    public Map<UUID, String> getMaskFieldTableMap() {
        return this.maskFieldTableMap;
    }

    public Map<String, List<String>> getEncryptedTableFieldMap() {
        return this.encryptedTableFieldMap;
    }

    public List<DataTableDefineImpl> getTableList() {
        this.requireNotLocked();
        return this.tables;
    }

    public DataFieldDefineImpl getField(UUID id) {
        for (int i = 0; i < this.tables.size(); ++i) {
            DataTableDefineImpl table = this.tables.get(i);
            for (int j = 0; j < ((NamedContainerImpl)table.getFields()).size(); ++j) {
                DataFieldDefineImpl field = (DataFieldDefineImpl)((NamedContainerImpl)table.getFields()).get(j);
                if (!field.getId().equals(id)) continue;
                return field;
            }
        }
        throw new MissingObjectException(BizBindingI18nUtil.getMessage("va.bizbinding.datadefineimpl.fieldnotfound") + id.toString());
    }
}

