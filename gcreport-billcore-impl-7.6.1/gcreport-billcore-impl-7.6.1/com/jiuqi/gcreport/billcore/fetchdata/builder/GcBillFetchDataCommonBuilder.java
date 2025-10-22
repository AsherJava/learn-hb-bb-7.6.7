/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.data.DataRow
 */
package com.jiuqi.gcreport.billcore.fetchdata.builder;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.enums.GcBillStatusEnum;
import com.jiuqi.gcreport.billcore.fetchdata.builder.GcBillFetchDataBuilder;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.data.DataRow;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GcBillFetchDataCommonBuilder
implements GcBillFetchDataBuilder {
    @Override
    public String getBillModelType() {
        return "defalut";
    }

    @Override
    public void buildSubDatas(BillModelImpl model, List<Map<String, Object>> vchrSubItems, String subTableName) {
        String subBizFiledCode = this.getSubBizFiledCode();
        List rowDataList = ((DataTableImpl)model.getData().getTables().get(subTableName)).getRowList();
        List<DataRowImpl> hasVchrCodeBillRowData = rowDataList.stream().filter(rowData -> !StringUtils.isNull((String)rowData.getString(subBizFiledCode))).collect(Collectors.toList());
        Set billHasVchrUniqueCodeSet = rowDataList.stream().map(item -> (String)item.getValue(subBizFiledCode)).collect(Collectors.toSet());
        billHasVchrUniqueCodeSet.remove(null);
        Map<String, List<Map>> vchrUniqueCode2SubItems = vchrSubItems.stream().collect(Collectors.groupingBy(item -> (String)item.get(subBizFiledCode)));
        Set<String> vchrUniqueCodeSet = vchrUniqueCode2SubItems.keySet();
        rowDataList.forEach(rowData -> {
            if (vchrUniqueCodeSet.contains(rowData.getString(subBizFiledCode)) && (GcBillStatusEnum.UNHANDLED.getCode().equals(rowData.getValue("STATUS")) || GcBillStatusEnum.DEPRECATED.getCode().equals(rowData.getValue("STATUS")))) {
                List needUpdateSubtems = (List)vchrUniqueCode2SubItems.get(rowData.getString(subBizFiledCode));
                Map needUpdateSubtem = (Map)needUpdateSubtems.get(0);
                List<String> list = Arrays.asList("UNITCODE", "INVESTEDUNIT");
                needUpdateSubtem.forEach((key, val) -> {
                    if (!list.contains(key)) {
                        rowData.setValue(key, val);
                    }
                });
            }
            if (!StringUtils.isNull((String)rowData.getString(subBizFiledCode)) && !vchrUniqueCodeSet.contains(rowData.getString(subBizFiledCode))) {
                ((DataTableImpl)model.getData().getTables().get(subTableName)).deleteRow((DataRow)rowData);
            }
        });
        List<Map> appendRowList = vchrSubItems.stream().filter(vchrSubItem -> !billHasVchrUniqueCodeSet.contains(vchrSubItem.get(subBizFiledCode))).collect(Collectors.toList());
        appendRowList.forEach(row -> {
            row.put("STATUS", GcBillStatusEnum.UNHANDLED.getCode());
            model.getTable(subTableName).appendRow(row);
        });
        hasVchrCodeBillRowData.forEach(rowData -> {
            if (!vchrUniqueCodeSet.contains(rowData.getString(subBizFiledCode))) {
                ((DataTableImpl)model.getData().getTables().get(subTableName)).deleteRow((DataRow)rowData);
            }
        });
    }

    public String getSubBizFiledCode() {
        return "VCHRUNIQUECODE";
    }

    private void delRowList(BillModelImpl model, List<Map<String, Object>> vchrSubItems, String subTableName) {
        Set vchrUniqueCodeSet = vchrSubItems.stream().map(vchrSubItem -> (String)vchrSubItem.get("VCHRUNIQUECODE")).collect(Collectors.toSet());
        List rowDataList = ((DataTableImpl)model.getData().getTables().get("GC_INVESTBILLITEM")).getRowList();
        List<DataRowImpl> hasVchrCodeRowData = rowDataList.stream().filter(rowData -> !StringUtils.isNull((String)rowData.getString("VCHRUNIQUECODE"))).collect(Collectors.toList());
        hasVchrCodeRowData.forEach(rowData -> {
            if (!vchrUniqueCodeSet.contains(rowData.getString("VCHRUNIQUECODE"))) {
                ((DataTableImpl)model.getData().getTables().get("GC_INVESTBILLITEM")).deleteRow((DataRow)rowData);
            }
        });
    }

    private void updateRowList(BillModelImpl model, List<Map<String, Object>> vchrSubItems, String subTableName) {
        List rowDataList = ((DataTableImpl)model.getData().getTables().get(subTableName)).getRowList();
        Map<String, List<Map>> vchrUniqueCode2SubItems = vchrSubItems.stream().collect(Collectors.groupingBy(item -> (String)item.get("VCHRUNIQUECODE")));
        Set<String> vchrUniqueCodeSet = vchrUniqueCode2SubItems.keySet();
        rowDataList.forEach(rowData -> {
            if (vchrUniqueCodeSet.contains(rowData.getString("VCHRUNIQUECODE")) && (GcBillStatusEnum.UNHANDLED.getCode().equals(rowData.getValue("STATUS")) || GcBillStatusEnum.DEPRECATED.getCode().equals(rowData.getValue("STATUS")))) {
                List needUpdateSubtems = (List)vchrUniqueCode2SubItems.get(rowData.getString("VCHRUNIQUECODE"));
                Map needUpdateSubtem = (Map)needUpdateSubtems.get(0);
                List<String> list = Arrays.asList("UNITCODE", "INVESTEDUNIT");
                needUpdateSubtem.forEach((key, val) -> {
                    if (!list.contains(key)) {
                        rowData.setValue(key, val);
                    }
                });
            }
        });
    }

    private void appendRowList(BillModelImpl model, List<Map<String, Object>> vchrSubItems, String subTableName) {
        List rowList = ((DataTableImpl)model.getData().getTables().get("GC_INVESTBILLITEM")).getRowList();
        Set vchrUniqueCodeSet = rowList.stream().map(item -> (String)item.getValue("VCHRUNIQUECODE")).collect(Collectors.toSet());
        vchrUniqueCodeSet.remove(null);
        List<Map> appendRowList = vchrSubItems.stream().filter(vchrSubItem -> !vchrUniqueCodeSet.contains(vchrSubItem.get("VCHRUNIQUECODE"))).collect(Collectors.toList());
        appendRowList.forEach(row -> model.getTable("GC_INVESTBILLITEM").appendRow(row));
    }

    @Override
    public void buildMasteData(BillModelImpl model, Map<String, Object> vchrMaster) {
        model.add();
        model.getMaster().setData(vchrMaster);
    }
}

