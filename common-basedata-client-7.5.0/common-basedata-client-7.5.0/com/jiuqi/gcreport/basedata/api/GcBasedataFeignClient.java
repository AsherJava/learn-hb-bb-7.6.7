/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.gcreport.basedata.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataTableVO;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(contextId="com.jiuqi.gcreport.basedata.client.feign.GcBasedataFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GcBasedataFeignClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/basedatas";

    @RequestMapping(value={"/api/gcreport/v1/basedatas/listBaseData/{tbCode}"}, method={RequestMethod.GET})
    public BusinessResponseEntity<List<BaseDataVO>> listBaseData(@PathVariable(value="tbCode") String var1);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/consolidatedTreeBaseData/"}, method={RequestMethod.POST})
    public BusinessResponseEntity<List<BaseDataVO>> treeBaseData(@RequestBody Map<String, String> var1);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/treeBaseData/{tbCode}"}, method={RequestMethod.GET})
    public BusinessResponseEntity<List<BaseDataVO>> treeBaseData(@PathVariable(value="tbCode") String var1);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/queryAllBasedateTables"}, method={RequestMethod.GET})
    public BusinessResponseEntity<List<BaseDataTableVO>> queryAllBasedateTables();

    @RequestMapping(value={"/api/gcreport/v1/basedatas/listBaseDataBySearch/{tbCode}/{searchText}"}, method={RequestMethod.GET})
    public BusinessResponseEntity<List<BaseDataVO>> listBaseDataBySearch(@PathVariable(value="tbCode") String var1, @PathVariable(value="searchText") String var2);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/getBaseDataByCode/{tbCode}/{code}"}, method={RequestMethod.GET})
    public BusinessResponseEntity<BaseDataVO> getBaseDataByCode(@PathVariable(value="tbCode") String var1, @PathVariable(value="code") String var2);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/listRootBaseData/{tbCode}"}, method={RequestMethod.GET})
    public BusinessResponseEntity<List<BaseDataVO>> listRootBaseData(@PathVariable(value="tbCode") String var1);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/listBaseDataByParent/{tbCode}/{parentCode}"}, method={RequestMethod.GET})
    public BusinessResponseEntity<List<BaseDataVO>> listBaseDataByParent(@PathVariable(value="tbCode") String var1, @PathVariable(value="parentCode") String var2);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/listAllBaseDataByParent/{tbCode}/{parentCode}"}, method={RequestMethod.GET})
    public BusinessResponseEntity<List<BaseDataVO>> listAllBaseDataByParent(@PathVariable(value="tbCode") String var1, @PathVariable(value="parentCode") String var2);

    @PostMapping(value={"/api/gcreport/v1/basedatas/external/save/{tableName}"})
    public BusinessResponseEntity<String> saveBaseDatas(@PathVariable(value="tableName") String var1, @RequestBody Map<String, Object> var2, @RequestHeader(value="Authorization") String var3);

    @PostMapping(value={"/api/gcreport/v1/basedatas/getBaseDataByCodes"})
    public BusinessResponseEntity<List<BaseDataVO>> getBaseDataByCodes(@RequestBody Map<String, Object> var1);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/levelTree"}, method={RequestMethod.POST})
    public BusinessResponseEntity<Object> getBaseDataByParent(@RequestBody BaseDataParam var1);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/searchKey"}, method={RequestMethod.POST})
    public BusinessResponseEntity<List<GcBaseDataVO>> getBaseDataBySearch(@RequestBody BaseDataParam var1);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/singleData"}, method={RequestMethod.POST})
    public BusinessResponseEntity<GcBaseDataVO> getBaseDataByCode(@RequestBody BaseDataParam var1);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/allLevelTree"}, method={RequestMethod.POST})
    public BusinessResponseEntity<Object> listAllBaseDataByParent(@RequestBody BaseDataParam var1);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/listData"}, method={RequestMethod.POST})
    public BusinessResponseEntity<List<GcBaseDataVO>> listBaseData(@RequestBody BaseDataParam var1);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/levelData"}, method={RequestMethod.POST})
    public BusinessResponseEntity<List<GcBaseDataVO>> levelBaseData(@RequestBody BaseDataParam var1);

    @RequestMapping(value={"/api/gcreport/v1/basedatas/getMaxDepth"}, method={RequestMethod.POST})
    public BusinessResponseEntity<Integer> getBaseDataMaxDepth(@RequestBody BaseDataParam var1);
}

