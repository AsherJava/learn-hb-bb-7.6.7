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
 */
package com.jiuqi.gcreport.samecontrol.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingZbAttributeVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSubjectMapVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.TaskAndSchemeMapping;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.samecontrol.api.SameCtrlChgSettingClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface SameCtrlChgSettingClient {
    public static final String SAMT_CTRL_SETTING_PATH = "/api/gcreport/v1/samecontrol/setting";

    @PostMapping(value={"/api/gcreport/v1/samecontrol/setting/option/saveOptionData"})
    public BusinessResponseEntity<String> saveOptionData(@RequestBody SameCtrlChagSettingOptionVO var1);

    @GetMapping(value={"/api/gcreport/v1/samecontrol/setting/option/getOptionData/{taskId}/{schemeId}/{systemId}"})
    public BusinessResponseEntity<SameCtrlChagSettingOptionVO> getOptionData(@PathVariable(value="taskId") String var1, @PathVariable(value="schemeId") String var2, @PathVariable(value="systemId") String var3);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/setting/option/saveZbAttributes/{taskId}/{schemeId}"})
    public BusinessResponseEntity<String> saveZbAttribute(@PathVariable(value="taskId") String var1, @PathVariable(value="schemeId") String var2, @RequestBody List<SameCtrlChagSettingZbAttributeVO> var3);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/setting/option/saveSchemeMapping/{taskId}/{schemeId}"})
    public BusinessResponseEntity<String> saveSchemeMapping(@PathVariable(value="taskId") String var1, @PathVariable(value="schemeId") String var2, @RequestBody List<TaskAndSchemeMapping> var3);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/setting/option/deleteSchemeMappingByIds"})
    public BusinessResponseEntity<String> deleteSchemeMappingByIds(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/setting/option/listSchemeMappings/{taskId}/{schemeId}/{systemId}"})
    public BusinessResponseEntity<List<TaskAndSchemeMapping>> listSchemeMappings(@PathVariable(value="taskId") String var1, @PathVariable(value="schemeId") String var2, @PathVariable(value="systemId") String var3);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/setting/option/saveSubjectMapping/{schemeMappingId}/{taskId}/{schemeId}"})
    public BusinessResponseEntity<String> saveSubjectMapping(@PathVariable(value="schemeMappingId") String var1, @PathVariable(value="taskId") String var2, @PathVariable(value="schemeId") String var3, @RequestBody List<SameCtrlChagSubjectMapVO> var4);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/setting/option/deleteSubjectMappings"})
    public BusinessResponseEntity<String> deleteSubjectMappings(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/setting/option/listSubjectMappings/{schemeMappingId}/{systemId}"})
    public BusinessResponseEntity<List<SameCtrlChagSubjectMapVO>> listSubjectMappings(@PathVariable(value="schemeMappingId") String var1, @PathVariable(value="systemId") String var2);

    @GetMapping(value={"/api/gcreport/v1/samecontrol/setting/option/getEnableSameCtrFlag/{taskId}/{schemeId}"})
    public BusinessResponseEntity<Boolean> getEnableSameCtrFlag(@PathVariable(value="taskId") String var1, @PathVariable(value="schemeId") String var2);

    @GetMapping(value={"/api/gcreport/v1/samecontrol/setting/queryFormDataForSameCtrlSetting/{schemeId}/{formKey}"})
    public BusinessResponseEntity<String> queryFormDataForSameCtrlSetting(@PathVariable(value="schemeId") String var1, @PathVariable(value="formKey") String var2);
}

