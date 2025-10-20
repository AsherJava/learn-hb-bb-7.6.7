/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.carryover.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.carryover.vo.CarryOverConfigVO;
import com.jiuqi.gcreport.carryover.vo.CarryOverTypeVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(contextId="com.jiuqi.gcreport.carryover.api.GcCarryOverConfigClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface GcCarryOverConfigClient {
    public static final String CONFIG_API_BASE_PATH = "/api/gcreport/v1/carryover/config";

    @PostMapping(value={"/api/gcreport/v1/carryover/config/save"})
    public BusinessResponseEntity<String> saveConfig(@RequestBody CarryOverConfigVO var1);

    @PostMapping(value={"/api/gcreport/v1/carryover/config/delete/{id}"})
    public BusinessResponseEntity<String> deleteConfig(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/carryover/config/update"})
    public BusinessResponseEntity<String> updateConfig(@RequestBody CarryOverConfigVO var1);

    @GetMapping(value={"/api/gcreport/v1/carryover/config/listCarryOver"})
    public BusinessResponseEntity<List<CarryOverConfigVO>> listCarryOverConfig();

    @GetMapping(value={"/api/gcreport/v1/carryover/config/getListCarryOverConfig"})
    public String getListCarryOverConfig();

    @GetMapping(value={"/api/gcreport/v1/carryover/config/listCarryOverType"})
    public BusinessResponseEntity<List<CarryOverTypeVO>> listCarryOverType();

    @PostMapping(value={"/api/gcreport/v1/carryover/config/getConfigById/{id}"})
    public BusinessResponseEntity<String> getConfigById(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/carryover/config/{currId}/{exchangeId}"})
    public BusinessResponseEntity<Boolean> exchangeSortConfig(@PathVariable(value="currId") String var1, @PathVariable(value="exchangeId") String var2);

    @PostMapping(value={"/api/gcreport/v1/carryover/config/uploadFileByJson"})
    public BusinessResponseEntity<Object> importConfigByJson(@RequestParam boolean var1, @RequestParam MultipartFile var2);

    @GetMapping(value={"/api/gcreport/v1/carryover/config/export"})
    public ResponseEntity<Resource> exportConfig();
}

