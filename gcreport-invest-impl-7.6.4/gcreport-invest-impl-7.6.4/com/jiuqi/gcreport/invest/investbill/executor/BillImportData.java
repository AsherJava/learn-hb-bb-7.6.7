/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.ruler.impl.RulerImpl
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 */
package com.jiuqi.gcreport.invest.investbill.executor;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillImportData {
    private String masterTitle;
    private Map<ArrayKey, Map<String, Object>> masterKey2Record = new HashMap<ArrayKey, Map<String, Object>>();
    private Map<ArrayKey, Integer> masterKey2Row = new HashMap<ArrayKey, Integer>();
    private Map<ArrayKey, List<Map<String, Object>>> sub1Key2RecordMap = new HashMap<ArrayKey, List<Map<String, Object>>>();
    private Map<ArrayKey, List<Map<String, Object>>> sub2Key2RecordMap = new HashMap<ArrayKey, List<Map<String, Object>>>();

    public BillImportData(String masterTitle) {
        this.masterTitle = masterTitle;
    }

    public void addMasterData(ArrayKey key, Map<String, Object> record, Integer rowNum) {
        this.masterKey2Record.put(key, record);
        this.masterKey2Row.put(key, rowNum);
    }

    public void setSub1Key2RecordMap(Map<ArrayKey, List<Map<String, Object>>> sub1Key2RecordMap) {
        this.sub1Key2RecordMap = sub1Key2RecordMap;
    }

    public void addSub1Data(ArrayKey key, Map<String, Object> record) {
        List<Map<String, Object>> records = this.sub1Key2RecordMap.get(key);
        if (null == records) {
            records = new ArrayList<Map<String, Object>>();
            this.sub1Key2RecordMap.put(key, records);
        }
        records.add(record);
    }

    public void addSub2Data(ArrayKey key, Map<String, Object> record) {
        List<Map<String, Object>> records = this.sub2Key2RecordMap.get(key);
        if (null == records) {
            records = new ArrayList<Map<String, Object>>();
            this.sub2Key2RecordMap.put(key, records);
        }
        records.add(record);
    }

    private Map<ArrayKey, List<Map<String, Object>>> getSubKey2RecordsMap(int i) {
        if (i == 0) {
            return this.sub1Key2RecordMap;
        }
        if (i == 1) {
            return this.sub2Key2RecordMap;
        }
        return null;
    }

    public StringBuilder saveData(String defineCode, GcOrgCenterService noAuthOrgTool, String ... subTableName) {
        BillDefineService billDefineService = (BillDefineService)SpringContextUtils.getBean(BillDefineService.class);
        ActionManager actionManager = (ActionManager)SpringContextUtils.getBean(ActionManager.class);
        StringBuilder log = new StringBuilder(128);
        for (Map.Entry<ArrayKey, Map<String, Object>> masterEntry : this.masterKey2Record.entrySet()) {
            ArrayKey key = masterEntry.getKey();
            try {
                Map<String, Object> masterRecord = masterEntry.getValue();
                BillContextImpl billContextImpl = new BillContextImpl();
                billContextImpl.setDisableVerify(true);
                BillModel model = billDefineService.createModel((BillContext)billContextImpl, defineCode);
                ((RulerImpl)model.getRuler()).getRulerExecutor().setEnable(true);
                if (null == masterRecord.get("ID")) {
                    model.add();
                    model.getMaster().setData(masterRecord);
                } else {
                    model.loadByCode((String)masterRecord.get("BILLCODE"));
                    model.edit();
                }
                for (int i = 0; i < subTableName.length; ++i) {
                    List<Map<String, Object>> subRecords;
                    Map<ArrayKey, List<Map<String, Object>>> subKey2RecordsMap = this.getSubKey2RecordsMap(i);
                    if (null == subKey2RecordsMap || null == (subRecords = subKey2RecordsMap.get(key))) continue;
                    if ("GC_INVESTBILLITEM".equals(subTableName[i])) {
                        this.handleInvestSubItems(masterRecord, model, i, subRecords);
                        continue;
                    }
                    for (Map<String, Object> subRecord : subRecords) {
                        DataRow dataRow = ((DataTable)model.getData().getTables().find(subTableName[i])).appendRow();
                        dataRow.setData(subRecord);
                    }
                }
                ActionResponse response = new ActionResponse();
                ActionRequest request = new ActionRequest();
                Action action = (Action)actionManager.get("bill-save");
                HashMap<String, Boolean> params1 = new HashMap<String, Boolean>();
                params1.put("datasync", true);
                request.setParams(params1);
                ((BillModelImpl)model).executeAction(action, request, response);
            }
            catch (BillException e) {
                String msg = "";
                if (!CollectionUtils.isEmpty((Collection)e.getCheckMessages())) {
                    msg = ((CheckResult)e.getCheckMessages().get(0)).getCheckMessage();
                } else if (!StringUtils.isEmpty((String)e.getMessage())) {
                    msg = e.getMessage();
                }
                log.append(String.format("%1s\u9875\u7b7e-\u7b2c%2d\u884c\uff1a%s:\u6295\u8d44\u5355\u4f4d'%s(%s)'\u3001\u88ab\u6295\u8d44\u5355\u4f4d'%s(%s)' \r\n", this.masterTitle, this.masterKey2Row.get(key), msg, noAuthOrgTool.getOrgByCode(String.valueOf(masterEntry.getKey().get(0))).getTitle(), masterEntry.getKey().get(0), noAuthOrgTool.getOrgByCode(String.valueOf(masterEntry.getKey().get(1))).getTitle(), masterEntry.getKey().get(1)));
            }
            catch (Exception e) {
                log.append(String.format("%1s\u9875\u7b7e-\u7b2c%2d\u884c\uff1a%3s \r\n", this.masterTitle, this.masterKey2Row.get(key), e.getMessage()));
            }
        }
        return log;
    }

    private void handleInvestSubItems(Map<String, Object> masterRecord, BillModel model, int i, List<Map<String, Object>> subRecords) {
        List subItems = (List)model.getData().getTablesData().get("GC_INVESTBILLITEM");
        subItems = subItems.stream().filter(item -> {
            LocalDateTime inputDate = DateUtils.convertDateToLDT((Date)((Date)item.get("INPUTDATE")));
            return inputDate.getMonth().getValue() == ConverterUtils.getAsIntValue(masterRecord.get("PERIOD"));
        }).collect(Collectors.toList());
        Map<String, List<Map>> key2SubItemsMap = subItems.stream().collect(Collectors.groupingBy(item -> {
            LocalDateTime changeDate = DateUtils.convertDateToLDT((Date)((Date)item.get("CHANGEDATE")));
            String changeDateStr = changeDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return item.get("CHANGESCENARIO") + "_" + changeDateStr;
        }));
        for (Map<String, Object> subRecord : subRecords) {
            ArrayList<Map> needUpdteSubItemList = new ArrayList<Map>();
            if (key2SubItemsMap.keySet().contains(subRecord.get("CHANGESCENARIO") + "_" + (String)subRecord.get("CHANGEDATE"))) {
                List<Map> list = key2SubItemsMap.get(subRecord.get("CHANGESCENARIO") + "_" + (String)subRecord.get("CHANGEDATE"));
                Map subItemMap = list.get(0);
                for (Map.Entry<String, Object> entry : subRecord.entrySet()) {
                    String field = entry.getKey();
                    Object fieldVal = entry.getValue();
                    if (field.equals("CHANGEDATE") || field.equals("VER") || field.equals("INPUTDATE")) continue;
                    subItemMap.put(field, fieldVal);
                }
                needUpdteSubItemList.add(subItemMap);
            } else {
                DataRow dataRow = ((DataTable)model.getData().getTables().find("GC_INVESTBILLITEM")).appendRow();
                dataRow.setData(subRecord);
            }
            model.getTable("GC_INVESTBILLITEM").updateRows(needUpdteSubItemList);
        }
    }
}

