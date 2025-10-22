/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.billextract.client.BillExtractSettingClient
 *  com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO
 *  com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.billextract.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.billextract.client.BillExtractSettingClient;
import com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO;
import com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO;
import com.jiuqi.gcreport.billextract.impl.service.BillExtractSettingService;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillExtractSettingController
implements BillExtractSettingClient {
    @Autowired
    private BillExtractSettingService settingService;

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/metaInfos"})
    public BusinessResponseEntity<List<MetaInfoDim>> listMetaInfo(@RequestParam(name="billMetaType", required=true) String billMetaType) {
        return BusinessResponseEntity.ok(this.settingService.listMetaInfo(billMetaType));
    }

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/metaInfo/{uniqueCode}"})
    public BusinessResponseEntity<MetaInfoDTO> getMetaInfoByUniqueCode(@PathVariable(name="uniqueCode", required=true) String uniqueCode) {
        return BusinessResponseEntity.ok((Object)this.settingService.getMetaInfoByUniqueCode(uniqueCode));
    }

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/masterTableName/{uniqueCode}"})
    public BusinessResponseEntity<String> getMasterTableName(@RequestParam(name="uniqueCode", required=true) String uniqueCode) {
        return BusinessResponseEntity.ok((Object)this.settingService.getMasterTableName(uniqueCode));
    }

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/dataModel/{dataModelName}"})
    public BusinessResponseEntity<DataModelDO> getDataModelByName(@PathVariable(name="dataModelName", required=true) String dataModelName) {
        return BusinessResponseEntity.ok((Object)this.settingService.getDataModelByName(dataModelName));
    }

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/dataModelColumn/get"})
    public BusinessResponseEntity<DataModelColumn> getDataModelColumn(@RequestParam(name="dataModelName", required=true) String dataModelName, @RequestParam(name="columnName", required=true) String columnName) {
        return BusinessResponseEntity.ok((Object)this.settingService.getDataModelColumn(dataModelName, columnName));
    }

    public BusinessResponseEntity<String> getOrgType(@RequestParam(name="defineName", required=true) String defineName) {
        return BusinessResponseEntity.ok((Object)this.settingService.getOrgType(defineName));
    }

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/bills/{uniqueCode}"})
    public BusinessResponseEntity<List<Map<String, Object>>> listBills(@PathVariable(name="defineName", required=true) String defineName, @RequestBody BillExtractLisDTO qeuryCondi) {
        return BusinessResponseEntity.ok(this.settingService.listBills(defineName, qeuryCondi));
    }

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/get"})
    public BusinessResponseEntity<Map<String, Object>> getBill(@RequestParam(name="defineName", required=true) String defineName, @RequestParam(name="billCode", required=true) String billCode) {
        return BusinessResponseEntity.ok(this.settingService.getBill(defineName, billCode));
    }

    @GetMapping(value={"/api/gcreport/v1//fetch/bill/getSchemeConfigByOrgId"})
    public BusinessResponseEntity<BillSchemeConfigDTO> getSchemeByOrgId(@RequestParam(name="defineName", required=true) String defineName, @RequestParam(name="unitCode", required=true) String unitCode) {
        return BusinessResponseEntity.ok((Object)this.settingService.getSchemeByOrgId(defineName, unitCode));
    }
}

