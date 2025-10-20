/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.billextract.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO;
import com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.billextract.client.BillExtractSettingClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface BillExtractSettingClient {
    public static final String API_PATH = "/api/gcreport/v1//fetch";

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/metaInfos"})
    public BusinessResponseEntity<List<MetaInfoDim>> listMetaInfo(@RequestParam(name="billMetaType", required=true) String var1);

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/metaInfo/{uniqueCode}"})
    public BusinessResponseEntity<MetaInfoDTO> getMetaInfoByUniqueCode(@PathVariable(name="uniqueCode", required=true) String var1);

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/masterTableName/{uniqueCode}"})
    public BusinessResponseEntity<String> getMasterTableName(@RequestParam(name="uniqueCode", required=true) String var1);

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/dataModel/{dataModelName}"})
    public BusinessResponseEntity<DataModelDO> getDataModelByName(@PathVariable(name="dataModelName", required=true) String var1);

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/dataModelColumn/get"})
    public BusinessResponseEntity<DataModelColumn> getDataModelColumn(@RequestParam(name="dataModelName", required=true) String var1, @RequestParam(name="columnName", required=true) String var2);

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/bills/{uniqueCode}"})
    public BusinessResponseEntity<List<Map<String, Object>>> listBills(@PathVariable(name="defineName", required=true) String var1, @RequestBody BillExtractLisDTO var2);

    @PostMapping(value={"/api/gcreport/v1//fetch/bill/get"})
    public BusinessResponseEntity<Map<String, Object>> getBill(@RequestParam(name="defineName", required=true) String var1, @RequestParam(name="billCode", required=true) String var2);

    @GetMapping(value={"/api/gcreport/v1//fetch/bill/orgType"})
    public BusinessResponseEntity<String> getOrgType(@RequestParam(name="defineName", required=true) String var1);

    @GetMapping(value={"/api/gcreport/v1//fetch/bill/getSchemeConfigByOrgId"})
    public BusinessResponseEntity<BillSchemeConfigDTO> getSchemeByOrgId(@RequestParam(name="defineName", required=true) String var1, @RequestParam(name="unitCode", required=true) String var2);
}

