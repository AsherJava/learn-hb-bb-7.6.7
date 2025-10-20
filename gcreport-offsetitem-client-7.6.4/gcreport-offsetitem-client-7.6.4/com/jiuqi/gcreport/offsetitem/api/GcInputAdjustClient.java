/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustDelCondi
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustQueryCondi
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.offsetitem.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO;
import com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustDelCondi;
import com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustQueryCondi;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.offsetitem.api.GcInputAdjustClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface GcInputAdjustClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/inputAdjust/";

    @PostMapping(value={"/api/gcreport/v1/inputAdjust/add"})
    public BusinessResponseEntity<Object> addInputAdjust(@RequestBody List<List<GcInputAdjustVO>> var1);

    @PostMapping(value={"/api/gcreport/v1/inputAdjust/query"})
    public BusinessResponseEntity<List<GcInputAdjustVO>> queryDetailByID(@RequestBody GcInputAdjustQueryCondi var1);

    @PostMapping(value={"/api/gcreport/v1/inputAdjust/queryByMRecidList"})
    public BusinessResponseEntity<List<List<GcInputAdjustVO>>> queryDetailByMRecidList(@RequestBody GcInputAdjustQueryCondi var1);

    @PostMapping(value={"/api/gcreport/v1/inputAdjust/delete"})
    public BusinessResponseEntity<Object> deleteBySrcId(@RequestBody GcInputAdjustDelCondi var1);

    @PostMapping(value={"/api/gcreport/v1/inputAdjust/queryByGroupIds"})
    public BusinessResponseEntity<Map<String, Map<String, Object>>> queryByGroupIds(@RequestBody Map<String, Object> var1, HttpServletRequest var2);

    @PostMapping(value={"/api/gcreport/v1/inputAdjust/consFormulaCalc"})
    public BusinessResponseEntity<GcInputAdjustVO> consFormulaCalc(@RequestBody GcInputAdjustVO var1);
}

