/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.conversion.conversionsystem.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionLogInfoPageVo;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemItemBatchIndexVo;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemItemVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversonSystemFormTreeVo;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.conversion.conversionsystem.api.ConversionSystemClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ConversionSystemClient {
    public static final String CONVERSION_SYSTEM_API_BASE_PATH = "/api/gcreport/v1/conversionsystem";

    @GetMapping(value={"/api/gcreport/v1/conversionsystem/systemtaskschemes"})
    public BusinessResponseEntity<List<ConversionSystemTaskSchemeVO>> getSystemTaskSchemes();

    @PostMapping(value={"/api/gcreport/v1/conversionsystem/taskScheme"})
    public BusinessResponseEntity<List<ConversionSystemTaskVO>> saveTaskScheme(@RequestBody List<ConversionSystemTaskVO> var1);

    @PostMapping(value={"/api/gcreport/v1/conversionsystem/taskScheme/{id}"})
    public BusinessResponseEntity<ConversionSystemTaskVO> deleteTaskScheme(@PathVariable(value="id") String var1);

    @GetMapping(value={"/api/gcreport/v1/conversionsystem/loginfos"})
    public BusinessResponseEntity<ConversionLogInfoPageVo> getConversionLoginfos(@RequestParam(value="pageSize") Integer var1, @RequestParam(value="pageNum") Integer var2, @RequestParam(value="showQueryCount") boolean var3);

    @GetMapping(value={"/api/gcreport/v1/conversionsystem/get/{formId}/{indexId}"})
    public BusinessResponseEntity<ConversionSystemItemVO> getSystemItemByFormIdAndIndexId(@PathVariable(value="formId") String var1, @PathVariable(value="indexId") String var2);

    @PostMapping(value={"/api/gcreport/v1/conversionsystem/batchGetSystemItemsByFormIdAndIndexIds"})
    public BusinessResponseEntity<List<ConversionSystemItemVO>> batchGetSystemItemsByFormIdAndIndexIds(@RequestBody ConversionSystemItemBatchIndexVo var1);

    @PostMapping(value={"/api/gcreport/v1/conversionsystem/saveConversionSystemItemIndexInfo"})
    public BusinessResponseEntity<ConversionSystemItemVO> saveConversionSystemItemIndexInfo(@RequestBody ConversionSystemItemVO var1);

    @PostMapping(value={"/api/gcreport/v1/conversionsystem/batchSaveConversionSystemItemIndexInfo"})
    public BusinessResponseEntity<List<ConversionSystemItemVO>> batchSaveConversionSystemItemIndexInfo(@RequestBody List<ConversionSystemItemVO> var1);

    @GetMapping(value={"/api/gcreport/v1/conversionsystem/queryFormTree/{formSchemeKey}"})
    public BusinessResponseEntity<List<ConversonSystemFormTreeVo>> queryFormTree(@PathVariable(value="formSchemeKey") String var1);

    @GetMapping(value={"/api/gcreport/v1/conversionsystem/queryFormData/{formKey}"})
    public BusinessResponseEntity<String> queryFormData(@PathVariable(value="formKey") String var1);
}

