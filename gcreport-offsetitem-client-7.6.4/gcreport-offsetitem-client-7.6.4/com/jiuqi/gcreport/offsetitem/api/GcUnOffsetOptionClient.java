/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcUnOffsetOptionVo
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.offsetitem.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO;
import com.jiuqi.gcreport.offsetitem.vo.GcUnOffsetOptionVo;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.offsetitem.api.GcUnOffsetOptionClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface GcUnOffsetOptionClient {
    public static final String UNOFFSETOPTION_API_BASE_PATH = "/api/gcreport/v1/unoffsetoption";

    @GetMapping(value={"/api/gcreport/v1/unoffsetoption/getContentById"})
    public BusinessResponseEntity<List<GcInputAdjustVO>> queryContentById(String var1);

    @GetMapping(value={"/api/gcreport/v1/unoffsetoption/listUnOffsetConfigDatas"})
    public BusinessResponseEntity<List<Map<String, Object>>> listUnOffsetConfigDatas(String var1);

    @PostMapping(value={"/api/gcreport/v1/unoffsetoption/exchangeSort/{id}/{dataSource}/{pageCode}/{step}"})
    public BusinessResponseEntity<String> exchangeSort(@PathVariable(value="id") String var1, @PathVariable(value="dataSource") String var2, @PathVariable(value="pageCode") String var3, @PathVariable(value="step") int var4);

    @PostMapping(value={"/api/gcreport/v1/unoffsetoption/delete"})
    public BusinessResponseEntity<String> delete(@RequestBody GcUnOffsetOptionVo var1);

    @PostMapping(value={"/api/gcreport/v1/unoffsetoption/save"})
    public BusinessResponseEntity<String> save(@RequestBody List<GcUnOffsetOptionVo> var1);

    @PostMapping(value={"/api/gcreport/v1/unoffsetoption/listSelectOption"})
    public BusinessResponseEntity<Map<String, String>> listSelectOption();

    @GetMapping(value={"/api/gcreport/v1/unoffsetoption/listSelectOptionForTab/{dataSource}"})
    public BusinessResponseEntity<List<Map<String, Object>>> listSelectOptionForTab(@PathVariable(value="dataSource") String var1);

    @PostMapping(value={"/api/gcreport/v1/unoffsetoption/saveSelectOption/{dataSource}"})
    public BusinessResponseEntity<String> saveSelectOption(@RequestBody List<Map<String, Object>> var1, @PathVariable(value="dataSource") String var2);

    @PostMapping(value={"/api/gcreport/v1/unoffsetoption/listShowTypeForPage/{pageCode}"})
    public BusinessResponseEntity<List<Map<String, String>>> listShowTypeForPage(@PathVariable(value="pageCode") String var1);

    @GetMapping(value={"/api/gcreport/v1/unoffsetoption/getCurDataSource"})
    public BusinessResponseEntity<String> getCurDataSource();

    @PostMapping(value={"/api/gcreport/v1/unoffsetoption/listUnoffsetConfigForPage/{pageType}/{dataSource}"})
    public BusinessResponseEntity<List<Map<String, Object>>> listUnoffsetConfigForPage(@PathVariable(value="pageType") String var1, @PathVariable(value="dataSource") String var2);
}

