/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveContext
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveData
 *  com.jiuqi.va.bill.action.SaveAction
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.gcreport.billcore.fetchdata;

import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveContext;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveData;
import com.jiuqi.gcreport.billcore.fetchdata.builder.GcBillFetchDataBuilder;
import com.jiuqi.gcreport.billcore.fetchdata.builder.GcBillFetchDataCommonBuilder;
import com.jiuqi.va.bill.action.SaveAction;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GcBillFetchDataMainProcessor {
    private Map<String, List<Map<String, Object>>> masterbizFiledCodes2VchrSubItems;
    private Collection<GcBillFetchDataBuilder> fetchDataBuilders = SpringContextUtils.getBeans(GcBillFetchDataBuilder.class);
    private BillExtractSaveContext fetchDataContext;
    private BillExtractSaveData fetchDataResultDTO;

    public GcBillFetchDataMainProcessor newInstance(BillExtractSaveContext fetchDataContext, BillExtractSaveData fetchDataResultDTO) {
        this.fetchDataContext = fetchDataContext;
        this.fetchDataResultDTO = fetchDataResultDTO;
        return new GcBillFetchDataMainProcessor();
    }

    public GcBillFetchDataMainProcessor() {
    }

    public GcBillFetchDataMainProcessor(BillExtractSaveContext fetchDataContext, BillExtractSaveData fetchDataResultDTO) {
        this.fetchDataContext = fetchDataContext;
        this.fetchDataResultDTO = fetchDataResultDTO;
    }

    public void saveData() {
        List<Map<String, Object>> vchrResultData = this.getVhcrResultData();
        BillModelImpl billModel = this.createBillModel(this.fetchDataContext.getBillDefine());
        this.saveBill(billModel);
    }

    private void saveBill(BillModelImpl model) {
        model.loadByCode(this.fetchDataContext.getBillCode());
        GcBillFetchDataBuilder billFetchDataBuilder = this.getBillBuilderByModelType(model.getDefine().getModelType());
        model.edit();
        FetchResultDTO itemTableRes = null;
        for (String itemTable : this.fetchDataResultDTO.getItemTableSet()) {
            itemTableRes = this.fetchDataResultDTO.getItemResultByTable(itemTable);
            if (itemTableRes == null || itemTableRes.getFloatResults() == null || itemTableRes.getFloatResults().getFloatColumns() == null || itemTableRes.getFloatResults().getFloatColumns().isEmpty()) continue;
            ArrayList<Map<String, Object>> listVchrSubItems = new ArrayList<Map<String, Object>>();
            for (Object[] srcRow : itemTableRes.getFloatResults().getRowDatas()) {
                HashMap row = new HashMap(itemTableRes.getFloatResults().getFloatColumns().size());
                for (Map.Entry columnEntry : itemTableRes.getFloatResults().getFloatColumns().entrySet()) {
                    row.put(columnEntry.getKey(), srcRow[(Integer)columnEntry.getValue()]);
                }
                listVchrSubItems.add(row);
            }
            billFetchDataBuilder.buildSubDatas(model, listVchrSubItems, itemTable);
        }
        Map masterData = model.getMaster().getData();
        if (this.fetchDataResultDTO.getMasterResult() != null && this.fetchDataResultDTO.getMasterResult().getFixedResults() != null) {
            masterData.putAll(this.fetchDataResultDTO.getMasterResult().getFixedResults());
        }
        this.excuteSave(model);
    }

    private void excuteSave(BillModelImpl billModel) {
        ActionRequest request = new ActionRequest();
        request.setParams(new HashMap());
        ActionResponse response = new ActionResponse();
        SaveAction saveAction = (SaveAction)SpringContextUtils.getBean(SaveAction.class);
        System.out.println(JsonUtils.writeValueAsString((Object)request));
        billModel.executeAction((Action)saveAction, request, response);
        System.out.println(JsonUtils.writeValueAsString((Object)response));
    }

    private GcBillFetchDataBuilder getBillBuilderByModelType(String modelType) {
        Optional<GcBillFetchDataBuilder> billFetchDataBuilder = this.fetchDataBuilders.stream().filter(fetchDataBuilder -> fetchDataBuilder.getBillModelType().equals(modelType)).findFirst();
        return billFetchDataBuilder.orElse(new GcBillFetchDataCommonBuilder());
    }

    private BillModelImpl createBillModel(String billDefineName) {
        BillContextImpl billContext = new BillContextImpl();
        billContext.setTenantName(ShiroUtil.getTenantName());
        billContext.setDisableVerify(true);
        BillDefineService billDefineService = (BillDefineService)SpringContextUtils.getBean(BillDefineService.class);
        BillModelImpl model = (BillModelImpl)billDefineService.createModel((BillContext)billContext, billDefineName);
        model.getRuler().getRulerExecutor().setEnable(true);
        return model;
    }

    private List<Map<String, Object>> getVhcrResultData() {
        ArrayList<Map<String, Object>> subItems = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("UNITCODE", "8002270");
        map.put("INVESTEDUNIT", "8002272");
        map.put("CHANGESCENARIO", "0101");
        map.put("CHANGEDATE", DateUtils.dateOf((int)2024, (int)6, (int)20));
        map.put("CHANGEAMT", 321);
        map.put("VCHRUNIQUECODE", "111222");
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("UNITCODE", "8002270");
        map1.put("INVESTEDUNIT", "8002272");
        map1.put("CHANGESCENARIO", "0001");
        map1.put("CHANGEDATE", DateUtils.dateOf((int)2024, (int)6, (int)21));
        map1.put("CHANGEAMT", 123);
        map1.put("VCHRUNIQUECODE", "111666");
        subItems.add(map1);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("UNITCODE", "8002270");
        map2.put("INVESTEDUNIT", "8002272");
        map2.put("CHANGESCENARIO", "6666");
        map2.put("CHANGEDATE", DateUtils.dateOf((int)2024, (int)6, (int)22));
        map2.put("CHANGEAMT", 789);
        map2.put("VCHRUNIQUECODE", "111777");
        map1.put("ASSISTCOMB", "\u8f85\u52a9\u9879\u6d4b\u8bd52");
        subItems.add(map2);
        return subItems;
    }
}

