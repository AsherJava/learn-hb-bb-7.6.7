/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl
 *  com.jiuqi.va.biz.intf.data.DataDefine
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataRowState
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.data.DataTableDefine
 *  com.jiuqi.va.biz.intf.data.DataTableType
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.ruler.intf.RulerFields
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 *  com.jiuqi.va.biz.ruler.intf.TriggerEvent
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.intf.RulerFields;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BillDateChangeListener
implements RulerItem {
    private Map<String, Set<String>> fieldsMap;

    public String getName() {
        return "BillDateChangeListener";
    }

    public String getTitle() {
        return "\u76d1\u542c\u5355\u636e\u65e5\u671f\u53d8\u5316";
    }

    public String getRulerType() {
        return "Fixed";
    }

    public Set<String> getTriggerTypes() {
        return Stream.of("after-set-value").collect(Collectors.toSet());
    }

    public Map<String, Map<String, Boolean>> getTriggerFields(ModelDefine define) {
        DataDefine data = (DataDefine)define.getPlugins().get("data");
        String masterTableName = ((DataTableDefine)data.getTables().getMasterTable()).getName();
        return RulerFields.build().field(masterTableName, "BILLDATE", true).fields();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<String, Map<String, Boolean>> getAssignFields(ModelDefine define) {
        DataDefine data = (DataDefine)define.getPlugins().get("data");
        if (this.fieldsMap == null) {
            BillDateChangeListener billDateChangeListener = this;
            synchronized (billDateChangeListener) {
                HashMap<String, Set<String>> fieldsMap = new HashMap<String, Set<String>>();
                HashMap tableStartVersionMap = new HashMap();
                data.getTables().stream().forEach(table -> table.getFields().forEachName((x, y) -> {
                    Boolean isStartVersion;
                    if (y.getRefTableType() == 1) {
                        tableStartVersionMap.computeIfAbsent(y.getRefTableName(), value -> this.isStartVersion(y.getRefTableName()));
                    }
                    if ((isStartVersion = (Boolean)tableStartVersionMap.get(y.getRefTableName())) != null && isStartVersion.booleanValue()) {
                        Set fields = fieldsMap.computeIfAbsent(table.getTableName(), key -> new HashSet());
                        fields.add(x);
                    }
                }));
                this.fieldsMap = fieldsMap;
            }
        }
        RulerFields rulerFields = RulerFields.build();
        for (Map.Entry<String, Set<String>> entry : this.fieldsMap.entrySet()) {
            String tableName = entry.getKey();
            ArrayList fields = new ArrayList(entry.getValue());
            rulerFields.fields(tableName, fields, false);
        }
        return rulerFields.fields();
    }

    public void execute(Model model, Stream<TriggerEvent> events) {
        BillModelImpl billModel = (BillModelImpl)model;
        DataRow master = billModel.getMaster();
        if (DataRowState.INITIAL == master.getState()) {
            return;
        }
        this.getAssignFields(model.getDefine());
        DataTableNodeContainerImpl dataTable = billModel.getData().getTables();
        for (int i = 0; i < dataTable.size(); ++i) {
            DataTable itemTable = (DataTable)dataTable.get(i);
            if (!this.fieldsMap.containsKey(itemTable.getName())) continue;
            Set<String> fields = this.fieldsMap.get(itemTable.getName());
            if (itemTable.getTableType() != DataTableType.DATA) continue;
            itemTable.getRows().stream().forEach(row -> {
                for (String field : fields) {
                    row.setValue(field, null);
                }
            });
        }
    }

    private Boolean isStartVersion(String tableName) {
        BaseDataDefineClient baseDataDefineClient = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        BaseDataDefineDTO basedataDO = new BaseDataDefineDTO();
        basedataDO.setName(tableName);
        PageVO list = baseDataDefineClient.list(basedataDO);
        if (list != null && list.getRows() != null && list.getRows().size() > 0) {
            Integer versionflag = ((BaseDataDefineDO)list.getRows().get(0)).getVersionflag();
            if (versionflag == null) {
                return false;
            }
            return versionflag == 1;
        }
        return false;
    }
}

