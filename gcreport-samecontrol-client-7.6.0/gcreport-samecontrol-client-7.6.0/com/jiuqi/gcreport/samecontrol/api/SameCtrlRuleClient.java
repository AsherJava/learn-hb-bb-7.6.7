/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.servlet.http.HttpServletRequest
 *  javax.validation.Valid
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.samecontrol.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleDragRuleVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(contextId="com.jiuqi.gcreport.samecontrol.api.SameCtrlRuleClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface SameCtrlRuleClient {
    public static final String SAME_CONTROL_RULE_PATH = "/api/gcreport/v1/samecontrol/rule";

    @GetMapping(value={"/api/gcreport/v1/samecontrol/rule/ruleTree/{reportSystem}/{taskId}"})
    public BusinessResponseEntity<List<SameCtrlRuleVO>> listRuleTree(@PathVariable(value="reportSystem") String var1, @PathVariable(value="taskId") String var2);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/rule"})
    public BusinessResponseEntity<SameCtrlRuleVO> saveSameCtrlRule(@Valid @RequestBody SameCtrlRuleVO var1, HttpServletRequest var2);

    @GetMapping(value={"/api/gcreport/v1/samecontrol/rule/{id}"})
    public BusinessResponseEntity<SameCtrlRuleVO> getSameCtrlRuleById(@PathVariable(value="id") String var1);

    @GetMapping(value={"/api/gcreport/v1/samecontrol/rule/group/{id}"})
    public BusinessResponseEntity<SameCtrlRuleVO> getSameCtrlRuleGroupByGroupId(@PathVariable(value="id") String var1);

    @GetMapping(value={"/api/gcreport/v1/samecontrol/rule/delete/{id}"})
    public BusinessResponseEntity<String> deleteSameCtrlRuleOrGroupById(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/rule/update/{id}"})
    public BusinessResponseEntity<String> updateSameCtrlRuleOrGroupNameById(@PathVariable(value="id") String var1, @RequestParam(value="title") String var2);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/rule/enableOrStop/{id}/{startFlag}"})
    public BusinessResponseEntity<String> setSameCtrlRuleStatus(@PathVariable(value="id") String var1, @PathVariable(value="startFlag") Boolean var2);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/rule/uploadFile/{reportSystem}/{taskId}"})
    public BusinessResponseEntity<Object> uploadFile(@PathVariable String var1, @PathVariable String var2, @RequestParam boolean var3, @RequestParam MultipartFile var4);

    @GetMapping(value={"/api/gcreport/v1/samecontrol/rule/downloadFile/{reportSystem}/{taskId}"})
    public ResponseEntity<Resource> downloadFile(@PathVariable String var1, @PathVariable String var2, HttpServletRequest var3);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/rule/{id}/parent/{parentId}"})
    public BusinessResponseEntity<SameCtrlRuleVO> copyOrPasteNode(@PathVariable(value="id") String var1, @PathVariable(value="parentId") String var2, @RequestParam(value="action") String var3);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/rule/drag"})
    public BusinessResponseEntity<Object> dragNode(@RequestBody SameCtrlRuleDragRuleVO var1);
}

