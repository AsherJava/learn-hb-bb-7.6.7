/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.bill.setting.client.BillExtractDefineClient
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillDefineDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractDefineDTO
 *  com.jiuqi.gcreport.billextract.client.vo.TreeVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.controller;

import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.bill.setting.client.BillExtractDefineClient;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillDefineDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractDefineDTO;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillExtractDefineService;
import com.jiuqi.gcreport.billextract.client.vo.TreeVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillExtractDefineController
implements BillExtractDefineClient {
    @Autowired
    private BillExtractDefineService service;

    @GetMapping(value={"/api/gcreport/v1/fetch/bill/define/tree"})
    public BusinessResponseEntity<List<TreeVO>> getExtractDefineTree() {
        return BusinessResponseEntity.ok(this.service.getExtractDefineTree());
    }

    @GetMapping(value={"/api/gcreport/v1/fetch/bill/define/list"})
    public BusinessResponseEntity<List<SelectOptionVO>> getBillDefineList(@RequestParam(name="billSettingType", required=false) String billSettingType) {
        return BusinessResponseEntity.ok(this.service.getBillDefineList(billSettingType));
    }

    public BusinessResponseEntity<List<BillDefineDTO>> getBillDefineListWithState(String modelType) {
        return BusinessResponseEntity.ok(this.service.getBillDefineListWithState(modelType));
    }

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/define/save/{billSettingType}"})
    public BusinessResponseEntity<String> saveExtractDefine(@PathVariable String billSettingType, @RequestBody List<BillExtractDefineDTO> extractBillList) {
        String msg = this.service.saveExtractDefine(billSettingType, extractBillList);
        this.service.cleanCache(billSettingType);
        return BusinessResponseEntity.ok((Object)msg);
    }

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/define/exchangeOrdinal"})
    public BusinessResponseEntity<Boolean> exchangeExtractDefineOrdinal(@RequestParam(name="srcBillDefine", required=true) String srcBillDefine, @RequestParam(name="targetBillDefine", required=true) String targetBillDefine) {
        return BusinessResponseEntity.ok((Object)this.service.exchangeExtractDefineOrdinal(srcBillDefine, targetBillDefine));
    }

    public BusinessResponseEntity<List<DataModelColumn>> getBillDataModelListByBillId(String billUniqueCode) {
        return BusinessResponseEntity.ok(this.service.getBillDataModelListByBillId(billUniqueCode));
    }
}

