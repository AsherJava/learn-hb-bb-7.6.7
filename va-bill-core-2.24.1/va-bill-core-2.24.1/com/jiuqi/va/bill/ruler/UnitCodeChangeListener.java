/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataFieldImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl
 *  com.jiuqi.va.biz.intf.data.DataDefine
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataRowState
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.data.DataTableDefine
 *  com.jiuqi.va.biz.intf.data.DataTableType
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.intf.RulerFields
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 *  com.jiuqi.va.biz.ruler.intf.TriggerEvent
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.OrgMngType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.intf.RulerFields;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.OrgMngType;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnitCodeChangeListener
implements RulerItem {
    private List<String> fields;

    public String getName() {
        return "UnitCodeChangeListener";
    }

    public String getTitle() {
        return "\u76d1\u542c\u7ec4\u7ec7\u673a\u6784\u53d8\u5316";
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
        return RulerFields.build().field(masterTableName, "UNITCODE", true).fields();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<String, Map<String, Boolean>> getAssignFields(ModelDefine define) {
        DataDefine data = (DataDefine)define.getPlugins().get("data");
        if (this.fields == null) {
            UnitCodeChangeListener unitCodeChangeListener = this;
            synchronized (unitCodeChangeListener) {
                ArrayList<String> fields = new ArrayList<String>();
                HashMap tableSharetypeMap = new HashMap();
                DataTableDefine masterTable = (DataTableDefine)data.getTables().getMasterTable();
                masterTable.getFields().forEachName((x, y) -> {
                    Boolean isShare;
                    if (y.getRefTableType() == 1) {
                        tableSharetypeMap.computeIfAbsent(y.getRefTableName(), value -> this.isShare(y.getRefTableName()));
                    }
                    if ((isShare = (Boolean)tableSharetypeMap.get(y.getRefTableName())) != null && !isShare.booleanValue() && !y.isCrossOrgSelection()) {
                        fields.add((String)x);
                    }
                });
                this.fields = fields;
            }
        }
        String masterTableName = ((DataTableDefine)data.getTables().getMasterTable()).getName();
        return RulerFields.build().fields(masterTableName, this.fields, true).fields();
    }

    public void execute(Model model, Stream<TriggerEvent> events) {
        BillModelImpl billModel = (BillModelImpl)model;
        DataRow master = billModel.getMaster();
        if (DataRowState.INITIAL == master.getState()) {
            return;
        }
        this.getAssignFields(model.getDefine());
        this.fields.stream().forEach(o -> {
            String unitField = null;
            DataFieldImpl field = (DataFieldImpl)billModel.getData().getMasterTable().getFields().get(o);
            DataFieldDefineImpl define = field.getDefine();
            Map shareFieldMapping = define.getShareFieldMapping();
            if (shareFieldMapping != null) {
                unitField = (String)shareFieldMapping.get("UNITCODE");
            }
            if (unitField == null) {
                unitField = define.getUnitField();
            }
            if (!(Utils.isEmpty(unitField) || "UNITCODE".equals(unitField) || master.isNull(field.getIndex()))) {
                return;
            }
            master.setValue(o, null);
        });
        DataTableNodeContainerImpl dataTable = billModel.getData().getTables();
        for (int i = 1; i < dataTable.size(); ++i) {
            FormulaImpl formula;
            int initRows;
            DataTable itemTable = (DataTable)dataTable.get(i);
            DataTableDefine define = itemTable.getDefine();
            if (itemTable.getTableType() != DataTableType.DATA) continue;
            if (!itemTable.getName().equals(((BillModelImpl)model).getMasterTable().getName() + "_M")) {
                itemTable.deleteRow(0, itemTable.getRows().size());
            }
            if (itemTable.getDefine().isSingle()) {
                itemTable.insertRow(0);
            }
            if ((initRows = ((DataTableDefineImpl)itemTable.getDefine()).getInitRows()) == -1 && (formula = (FormulaImpl)billModel.getRuler().getDefine().getFormulas().stream().filter(o -> o.getObjectId().equals(define.getId()) && "initRows".equals(o.getPropertyType())).findFirst().orElse(null)) != null && formula.isUsed()) {
                Object evaluate = FormulaUtils.evaluate((String)formula.getExpression(), (Model)billModel);
                initRows = evaluate != null ? (Integer)Convert.cast((Object)evaluate, Integer.class) : 0;
            }
            if (initRows <= 0) continue;
            itemTable.appendRow(initRows);
        }
    }

    private Boolean isShare(String tableName) {
        BaseDataDefineClient baseDataDefineClient = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        BaseDataDefineDTO basedataDO = new BaseDataDefineDTO();
        basedataDO.setName(tableName);
        PageVO list = baseDataDefineClient.list(basedataDO);
        if (list != null && list.getRows() != null && !list.getRows().isEmpty()) {
            return ((BaseDataDefineDO)list.getRows().get(0)).getSharetype().intValue() == OrgMngType.SHARE.getCode();
        }
        return false;
    }
}

