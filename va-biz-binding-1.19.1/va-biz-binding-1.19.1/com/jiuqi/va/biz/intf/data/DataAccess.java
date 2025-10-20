/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableNodeContainer;
import com.jiuqi.va.biz.intf.data.DataUpdate;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.intf.Formula;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DataAccess {
    public Map<String, List<Map<String, Object>>> load(DataTableNodeContainer<? extends DataTableDefine> var1, Map<String, Object> var2);

    public Map<String, List<Map<String, Object>>> load(ModelDefine var1, DataTableNodeContainer<? extends DataTableDefine> var2, Map<String, Object> var3, Map<UUID, Formula> var4);

    public List<Map<String, Object>> load(DataTableDefine var1, String var2);

    public long save(DataTableNodeContainer<? extends DataTableDefine> var1, Map<String, DataUpdate> var2, List<Map<String, Object>> var3);

    public void delete(DataTableNodeContainer<? extends DataTableDefine> var1, List<Map<String, Object>> var2);

    public List<Map<String, Object>> load(DataTableDefine var1, Map<String, Object> var2);

    public void insert(DataTableDefine var1, List<Map<String, Object>> var2);

    public void update(DataTableDefine var1, List<Map<String, Object>> var2);

    public int update(DataTableDefine var1, Map<String, Object> var2, Map<String, Object> var3);

    public int delete(DataTableDefine var1, Map<String, Object> var2);

    public Long loadVer(DataTableDefine var1, Object var2);
}

