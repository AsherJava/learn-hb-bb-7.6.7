/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.transfer.vo.TransferColumnVo
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.clbr.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrNotConfirmBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition;
import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import java.util.List;
import java.util.Set;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.clbr.api.ClbrProcessClient", name="${feignClient.gcreportClbr.name:gcreport-clbr-service}", url="${feignClient.gcreportClbr.url:}", path="${feignClient.gcreportClbr.path:}", primary=false)
public interface ClbrProcessClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/clbr/bill/";

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/initiator/notconfirm/listByRelationAndTime"})
    public BusinessResponseEntity<PageInfo<ClbrNotConfirmBillVO>> queryInitiatorNotConfirm(@RequestBody ClbrProcessCondition var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/receiver/notconfirm/listByRelationAndTime"})
    public BusinessResponseEntity<PageInfo<ClbrNotConfirmBillVO>> queryReceiverNotConfirm(@RequestBody ClbrProcessCondition var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/confirm/listByRelationAndTime"})
    public BusinessResponseEntity<PageInfo<ClbrBillCheckVO>> queryConfirm(@RequestBody ClbrProcessCondition var1, @RequestParam(value="tabFlag", required=false) String var2);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/confirm/cancalByAmountCheckIds"})
    public BusinessResponseEntity<Object> cancalByAmountCheckIds(@RequestBody Set<String> var1);

    @GetMapping(value={"/api/gcreport/v1/clbr/bill/confirm/getConfirmByClbrBillCode/{clbrBillCode}"})
    public BusinessResponseEntity<ClbrBillVO> getConfirmByClbrBillCode(@PathVariable(value="clbrBillCode") String var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/confirm/initiator/queryByClbrCode"})
    public BusinessResponseEntity<PageInfo<ClbrBillVO>> queryInitiatorClbrBillByClbrCode(@RequestBody ClbrProcessCondition var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill//confirm/receiver/queryByClbrCode"})
    public BusinessResponseEntity<PageInfo<ClbrBillVO>> queryReceiverClbrBillByClbrCode(@RequestBody ClbrProcessCondition var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill//queryAllShowFields"})
    public BusinessResponseEntity<List<TransferColumnVo>> queryAllShowFields(@RequestBody String var1);
}

