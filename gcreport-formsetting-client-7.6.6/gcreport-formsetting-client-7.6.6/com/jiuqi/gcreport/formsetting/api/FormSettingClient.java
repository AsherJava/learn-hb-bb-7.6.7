/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.np.definition.facade.TableDefine
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.formsetting.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.formsetting.vo.FormSettingVO;
import com.jiuqi.gcreport.formsetting.vo.SettingVO;
import com.jiuqi.np.definition.facade.TableDefine;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.formsetting.api.FormSettingClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface FormSettingClient {
    public static final String FORMSET_API_BASE_PATH = "/api/gcreport/v1/formSetting";

    @PostMapping(value={"/api/gcreport/v1/formSetting"})
    public BusinessResponseEntity<String> create(@RequestBody FormSettingVO var1);

    @PostMapping(value={"/api/gcreport/v1/formSetting/update"})
    public BusinessResponseEntity<String> updateSetting(@RequestBody SettingVO var1);

    @GetMapping(value={"/api/gcreport/v1/formSetting/queryOwnTable/{fieldId}"})
    public BusinessResponseEntity<TableDefine> updateSetting(@PathVariable(value="fieldId") String var1);

    @GetMapping(value={"/api/gcreport/v1/formSetting/getInputDataNameByTaskId/{taskId}"})
    public BusinessResponseEntity<String> getInputDataNameByTaskId(@PathVariable(value="taskId") String var1);

    @GetMapping(value={"/api/gcreport/v1/formSetting/table/allFields/{tableName}"})
    public BusinessResponseEntity<Map<String, Object>> queryAllFieldDefine(@PathVariable(value="tableName") String var1);
}

