/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.inputdata.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckInitCondition;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckUpdateMemoVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.inputdata.api.InputDataCheckClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface InputDataCheckClient {
    public static final String DATACHECK_API_BASE_PATH = "/api/gcreport/v1/inputDataCheck";

    @PostMapping(value={"/api/gcreport/v1/inputDataCheck/checkTabDatas"})
    public BusinessResponseEntity<Object> checkTabDatas(@RequestBody InputDataCheckCondition var1);

    @PostMapping(value={"/api/gcreport/v1/inputDataCheck/unCheckTabDatas"})
    public BusinessResponseEntity<Object> unCheckTabDatas(@RequestBody InputDataCheckCondition var1);

    @PostMapping(value={"/api/gcreport/v1/inputDataCheck/allCheckTabDatas"})
    public BusinessResponseEntity<Object> allCheckTabDatas(@RequestBody InputDataCheckCondition var1);

    @PostMapping(value={"/api/gcreport/v1/inputDataCheck/initData"})
    public BusinessResponseEntity<Object> initData(@RequestBody InputDataCheckInitCondition var1);

    @PostMapping(value={"/api/gcreport/v1/inputDataCheck/autoCheck"})
    public BusinessResponseEntity<Object> autoCheck(@RequestBody InputDataCheckCondition var1);

    @GetMapping(value={"/api/gcreport/v1/inputDataCheck/start/progress/{sn}"})
    public BusinessResponseEntity<ProgressData<List<String>>> querySnStartProgress(@PathVariable(value="sn") String var1);

    @PostMapping(value={"/api/gcreport/v1/inputDataCheck/manualCheck"})
    public BusinessResponseEntity<Object> manualCheck(@RequestBody InputDataCheckCondition var1);

    @PostMapping(value={"/api/gcreport/v1/inputDataCheck/manualCheckSave"})
    public BusinessResponseEntity<Object> manualCheckSave(@RequestBody InputDataCheckCondition var1);

    @PostMapping(value={"/api/gcreport/v1/inputDataCheck/cancelCheck"})
    public BusinessResponseEntity<Object> cancelCheck(@RequestBody InputDataCheckCondition var1);

    @PostMapping(value={"/api/gcreport/v1/inputDataCheck/updateMemo"})
    public BusinessResponseEntity<Object> updateMemo(@RequestBody InputDataCheckUpdateMemoVO var1);

    @PostMapping(value={"/api/gcreport/v1/inputDataCheck/sumAmt/{taskId}"})
    public BusinessResponseEntity<Object> sumAmt(@PathVariable(value="taskId") String var1, @RequestBody List<String> var2);
}

