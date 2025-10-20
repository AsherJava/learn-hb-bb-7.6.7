/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.rewritesetting;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.rewritesetting.vo.FloatRegionTreeVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldInfoVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldMappingVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteSettingVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteSubjectSettingVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.rewritesetting.api.RewriteSettingClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface RewriteSettingClient {
    public static final String API_PATH = "/api/gcreport/v1/rewriteSetting";

    @PostMapping(value={"/api/gcreport/v1/rewriteSetting/deleteRewriteSetting/{deleteIds}"})
    public BusinessResponseEntity<String> deleteRewriteSettingByIds(@PathVariable(value="deleteIds", required=false) List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/rewriteSetting/queryDatas/{schemeId}"})
    public BusinessResponseEntity<List<RewriteSettingVO>> queryRewriteSettings(@PathVariable(value="schemeId") String var1);

    @PostMapping(value={"/api/gcreport/v1/rewriteSetting/saveRewriteSetting"})
    public BusinessResponseEntity<String> saveRewriteSetting(@RequestBody RewriteSettingVO var1);

    @PostMapping(value={"/api/gcreport/v1/rewriteSetting/getFloatRegionTree/{schemeId}"})
    public BusinessResponseEntity<List<FloatRegionTreeVO>> getFloatRegionTree(@PathVariable(value="schemeId") String var1);

    @PostMapping(value={"/api/gcreport/v1/rewriteSetting/saveRewriteSubjectSettings/{schemeId}"})
    public BusinessResponseEntity<String> saveRewriteSubjectSettings(@PathVariable(value="schemeId") String var1, @RequestBody List<RewriteSubjectSettingVO> var2);

    @PostMapping(value={"/api/gcreport/v1/rewriteSetting/queryRewriteSubjectSettings/{schemeId}"})
    public BusinessResponseEntity<List<RewriteSubjectSettingVO>> queryRewriteSubjectSettings(@PathVariable(value="schemeId") String var1);

    @PostMapping(value={"/api/gcreport/v1/rewriteSetting/queryOffsetDataModel/{groupId}"})
    public BusinessResponseEntity<List<RewriteFieldInfoVO>> queryOffsetDataModel(@PathVariable(value="groupId") String var1);

    @PostMapping(value={"/api/gcreport/v1/rewriteSetting/queryFieldMappingSettings/{groupId}"})
    public BusinessResponseEntity<List<RewriteFieldMappingVO>> queryFieldMappingSettings(@PathVariable(value="groupId") String var1);
}

