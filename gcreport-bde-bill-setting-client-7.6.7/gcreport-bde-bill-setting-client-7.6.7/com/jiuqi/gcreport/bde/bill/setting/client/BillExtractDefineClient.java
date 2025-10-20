/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.billextract.client.vo.TreeVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.bde.bill.setting.client;

import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillDefineDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractDefineDTO;
import com.jiuqi.gcreport.billextract.client.vo.TreeVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.bde.bill.setting.client.BillExtractGroupClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface BillExtractDefineClient {
    public static final String API_PATH = "/api/gcreport/v1/fetch";

    @GetMapping(value={"/api/gcreport/v1/fetch/bill/define/tree"})
    public BusinessResponseEntity<List<TreeVO>> getExtractDefineTree();

    @GetMapping(value={"/api/gcreport/v1/fetch/bill/define/list"})
    public BusinessResponseEntity<List<SelectOptionVO>> getBillDefineList(@RequestParam(name="modelType", required=true) String var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/billDefineListWithState"})
    public BusinessResponseEntity<List<BillDefineDTO>> getBillDefineListWithState(@RequestParam(name="modelType", required=true) String var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/define/save/{billSettingType}"})
    public BusinessResponseEntity<String> saveExtractDefine(@PathVariable String var1, @RequestBody List<BillExtractDefineDTO> var2);

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/define/exchangeOrdinal"})
    public BusinessResponseEntity<Boolean> exchangeExtractDefineOrdinal(@RequestParam(name="srcBillDefine", required=true) String var1, @RequestParam(name="targetBillDefine", required=true) String var2);

    @GetMapping(value={"/api/gcreport/v1/fetch/bill/dataModel/list/byBillUniqueCode"})
    public BusinessResponseEntity<List<DataModelColumn>> getBillDataModelListByBillId(@RequestParam(name="billUniqueCode", required=true) String var1);
}

