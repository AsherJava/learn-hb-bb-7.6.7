/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  javax.validation.Valid
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.clbr.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillGenerateQueryParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillManualConfirmParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushResultDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillReceBillCodeDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO;
import com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO;
import com.jiuqi.gcreport.clbr.vo.ClbrBillTableColumnVO;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition;
import com.jiuqi.gcreport.clbr.vo.ClbrTodoCountVO;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.clbr.api.ClbrBillClient", name="${feignClient.gcreportClbr.name:gcreport-clbr-service}", url="${feignClient.gcreportClbr.url:}", path="${feignClient.gcreportClbr.path:}", primary=false)
public interface ClbrBillClient {
    public static final String API_BASE_PATH = "/api/gcreport/v1/clbr/bill/";

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/batchVerify"})
    public BusinessResponseEntity<ClbrBillPushResultDTO> batchVerify(@RequestBody ClbrBillPushParamDTO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/batchPush"})
    public BusinessResponseEntity<ClbrBillPushResultDTO> batchPush(@RequestBody ClbrBillPushParamDTO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/batchPushJson"})
    public BusinessResponseEntity<ClbrBillPushResultDTO> batchPush(@RequestBody String var1);

    @GetMapping(value={"/api/gcreport/v1/clbr/bill/queryColumnDefines"})
    public BusinessResponseEntity<List<ClbrBillTableColumnVO>> queryColumnDefines(@RequestParam(value="sysCode") String var1);

    @GetMapping(value={"/api/gcreport/v1/clbr/bill/query"})
    public BusinessResponseEntity<ClbrBillVO> query(@RequestParam(value="sysCode") String var1, @RequestParam(value="clbrBillcode") String var2);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/initiator/notconfirm/countByUser"})
    public BusinessResponseEntity<Integer> countInitiatorNotConfirmByUser(@RequestBody ClbrTodoCountVO var1);

    @GetMapping(value={"/api/gcreport/v1/clbr/bill/initiator/notconfirm/listByUser"})
    public BusinessResponseEntity<List<ClbrBillDTO>> listInitiatorNotConfirmByUser(@RequestParam(value="sysCode", required=false) String var1, @RequestParam(value="userName") String var2, @RequestParam(value="roleCode", required=false) String var3, @RequestParam(value="relation", required=false) String var4, @RequestParam(value="clbrBillCode", required=false) String var5);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/process/initiatorNotconfirm/countByUser"})
    public BusinessResponseEntity<Integer> countProcessInitiatorNotConfirmByUser(@RequestBody ClbrTodoCountVO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/confirm/listProcessInitiatorNotConfirmByUser"})
    public BusinessResponseEntity<PageInfo<ClbrBillCheckVO>> listProcessInitiatorNotConfirmByUser(@RequestBody ClbrProcessCondition var1, @RequestParam(value="tabFlag", required=false) String var2);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/initiator/notconfirm/listBySsoCondition"})
    public BusinessResponseEntity<PageInfo<ClbrBillVO>> listByReceiverInfo(@RequestBody ClbrBillSsoConditionDTO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/initiator/confirm/listByRelation"})
    public BusinessResponseEntity<PageInfo<ClbrBillVO>> listInitiatorConfirmByRelation(@RequestBody @Valid ClbrBillGenerateQueryParamDTO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/initiator/notconfirm/listByRelation"})
    public BusinessResponseEntity<PageInfo<ClbrBillVO>> listInitiatorNotConfirmByRelation(@RequestBody @Valid ClbrBillGenerateQueryParamDTO var1);

    @GetMapping(value={"/api/gcreport/v1/clbr/bill/generate/attribute"})
    public BusinessResponseEntity<ClbrGenerateAttributeDTO> queryClbrGenerateAttribute(@RequestParam(value="sysCode") String var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/generate"})
    public BusinessResponseEntity<Object> generateOppClbrBill(@RequestBody Set<String> var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/reject"})
    public BusinessResponseEntity<Object> rejectClbrBill(@RequestBody ClbrBillRejectDTO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/cancel"})
    public BusinessResponseEntity<Object> cancelClbrBill(@RequestBody Set<String> var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/cancelSingle"})
    public BusinessResponseEntity<Object> cancelSingleClbrBill(@RequestBody Set<String> var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/groupManual"})
    public BusinessResponseEntity<Object> manualConfirmByGrouping(@RequestBody Set<String> var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/manual"})
    public BusinessResponseEntity<Object> manualConfirm(@RequestBody ClbrBillManualConfirmParamDTO var1);

    @GetMapping(value={"/api/gcreport/v1/clbr/bill/showBill"})
    public BusinessResponseEntity<Object> showBill(@RequestParam(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/updateUnClbrReceBillCode"})
    public BusinessResponseEntity<Object> updateUnClbrReceBillCode(@RequestBody ClbrBillReceBillCodeDTO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/deleteUnClbrReceBillCode"})
    public BusinessResponseEntity<Object> deleteUnClbrReceBillCode(@RequestBody ClbrBillReceBillCodeDTO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/generate/check"})
    public BusinessResponseEntity<Object> generateCheckOppClbrBill(@RequestBody Set<String> var1);
}

