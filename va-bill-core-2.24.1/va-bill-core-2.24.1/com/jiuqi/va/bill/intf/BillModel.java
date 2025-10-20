/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.ruler.intf.Ruler
 */
package com.jiuqi.va.bill.intf;

import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.ruler.intf.Ruler;
import java.util.Map;
import org.springframework.util.StringUtils;

public interface BillModel
extends Model {
    public Data getData();

    public Ruler getRuler();

    public void loadById(Object var1);

    public void loadByCode(String var1);

    public void save();

    public void edit();

    public void add();

    public void add(Map<String, Object> var1);

    public void delete();

    public void deleteById(Object var1);

    public void deleteByCode(String var1);

    public boolean editing();

    public boolean afterApproval();

    public DataTable getMasterTable();

    public DataRow getMaster();

    public DataTable getTable(String var1);

    public BillContext getContext();

    default public String getUnitCategory(String tableName) {
        return StringUtils.isEmpty(tableName) ? "MD_ORG_FIN" : tableName;
    }
}

