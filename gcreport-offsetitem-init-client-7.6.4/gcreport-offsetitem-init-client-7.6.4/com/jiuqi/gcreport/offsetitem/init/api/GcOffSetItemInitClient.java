/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.vo.GcBusinessTypeCountVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemInitVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrPageVo
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcOffsetItemQueryCondi
 *  com.jiuqi.gcreport.org.impl.cache.impl.GcOrgCacheInnerVO
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.offsetitem.init.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.GcBusinessTypeCountVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemInitVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrPageVo;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.query.GcOffsetItemQueryCondi;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgCacheInnerVO;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.offsetitem.api.GcOffSetItemInitClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface GcOffSetItemInitClient {
    public static final String OFFSETITEM_API_BASE_PATH = "/api/gcreport/v1/offsetitems";

    @PostMapping(value={"/api/gcreport/v1/offsetitems/adjust"})
    public BusinessResponseEntity<Object> addAdjustOffSetItem(@Valid @RequestBody List<List<GcOffSetVchrItemVO>> var1, String var2, String var3);

    @PostMapping(value={"/api/gcreport/v1/offsetitems/adjust/initSubjectVO"})
    public BusinessResponseEntity<Object> initSubjectVO(@Valid @RequestBody List<GcOffSetVchrItemInitVO> var1);

    @PostMapping(value={"/api/gcreport/v1/offsetitems/adjust/initGetOrgWDZ"})
    public BusinessResponseEntity<Object> initGetOrgWDZ(@RequestBody GcOrgCacheInnerVO var1);

    @PostMapping(value={"/api/gcreport/v1/offsetitems/adjust/initAdjustCascadeDelete/{taskId}/{orgType}/{periodStr}"})
    public BusinessResponseEntity<Object> deleteInitAdjustSameSrcId(@RequestBody List<String> var1, @PathVariable(value="taskId") String var2, @PathVariable(value="orgType") String var3, @PathVariable(value="periodStr") String var4);

    @PostMapping(value={"/api/gcreport/v1/offsetitems/adjust/deleteAll"})
    public BusinessResponseEntity<String> deleteOffsetEntrys(@Valid @RequestBody OffsetItemInitQueryParamsVO var1);

    @GetMapping(value={"/api/gcreport/v1/offsetitems/adjust/{mrecid}/{orgType}/{periodStr}"})
    public BusinessResponseEntity<GcOffSetVchrVO> queryAdjust(@PathVariable(value="mrecid") String var1, @PathVariable(value="orgType") String var2, @PathVariable(value="periodStr") String var3);

    @GetMapping(value={"/api/gcreport/v1/offsetitems/adjust/pagecondition"})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> queryAdjustByPageCondition(@RequestParam(value="pageSize") Integer var1, @RequestParam(value="pageNum") Integer var2, @RequestParam(value="showQueryCount") boolean var3, @RequestParam(value="queryConditions") @RequestBody String var4);

    @PostMapping(value={"/api/gcreport/v1/offsetitems/adjust/queryOffsetEntry"})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> queryOffsetEntry(@RequestBody OffsetItemInitQueryParamsVO var1);

    @GetMapping(value={"/api/gcreport/v1/offsetitems/adjust/condition"})
    public BusinessResponseEntity<GcOffSetVchrPageVo> queryAdjustByCondition(@RequestParam(value="queryConditions") @RequestBody String var1);

    @PostMapping(value={"/api/gcreport/v1/offsetitems/adjust/queryByInvestment"})
    public BusinessResponseEntity<Collection<List<GcOffSetVchrItemVO>>> queryByInvestment(@RequestBody GcOffsetItemQueryCondi var1);

    @PostMapping(value={"/api/gcreport/v1/offsetitems/adjust/rootBusinessTypes"})
    public BusinessResponseEntity<List<GcBusinessTypeCountVO>> rootBusinessTypes(@RequestBody OffsetItemInitQueryParamsVO var1);

    @GetMapping(value={"/api/gcreport/v1/offsetitems/adjust/downloadErrorExcel/{sn}"})
    public void downloadErrorExcel(@PathVariable(value="sn") @RequestBody String var1, HttpServletResponse var2);

    @PostMapping(value={"/api/gcreport/v1/offsetitems/adjust/updateOffsetInitDisabledFlag/{mrecids}/{orgType}/{periodStr}/{isDisabled}"})
    public BusinessResponseEntity<String> updateOffsetInitDisabledFlag(@PathVariable(value="mrecids") List<String> var1, @PathVariable(value="orgType") String var2, @PathVariable(value="periodStr") String var3, @PathVariable(value="isDisabled") boolean var4);
}

