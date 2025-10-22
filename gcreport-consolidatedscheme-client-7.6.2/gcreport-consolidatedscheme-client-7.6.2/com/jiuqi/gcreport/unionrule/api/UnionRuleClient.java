/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
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
package com.jiuqi.gcreport.unionrule.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.unionrule.vo.BaseRuleVO;
import com.jiuqi.gcreport.unionrule.vo.DragRuleVO;
import com.jiuqi.gcreport.unionrule.vo.ITree;
import com.jiuqi.gcreport.unionrule.vo.SelectFloatLineOptionTreeVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

@FeignClient(contextId="com.jiuqi.gcreport.unionrule.api.UnionRuleClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface UnionRuleClient {
    public static final String UNIONRULE_API_BASE_PATH = "/api/gcreport/v1/unionrules";

    @GetMapping(value={"/api/gcreport/v1/unionrules/ruleTree/{reportSystem}"})
    public BusinessResponseEntity<List<UnionRuleVO>> selectRuleTreeByReportSysId(@PathVariable(value="reportSystem") String var1);

    @PostMapping(value={"/api/gcreport/v1/unionrules"})
    public BusinessResponseEntity<UnionRuleVO> addUnionRule(@Valid @RequestBody UnionRuleVO var1, HttpServletRequest var2);

    @GetMapping(value={"/api/gcreport/v1/unionrules/{id}"})
    public BusinessResponseEntity<UnionRuleVO> selectUnionRuleById(@PathVariable(value="id") String var1);

    @GetMapping(value={"/api/gcreport/v1/unionrules/group/{id}"})
    public BusinessResponseEntity<UnionRuleVO> selectByGroupId(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/unionrules/{id}"})
    public BusinessResponseEntity<String> deleteUnionRuleById(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/unionrules/group/{id}"})
    public BusinessResponseEntity<String> updateGroupName(@PathVariable(value="id") String var1, @RequestParam(value="title") String var2);

    @GetMapping(value={"/api/gcreport/v1/unionrules/ruleTree/task/{systemId}"})
    public BusinessResponseEntity<List<UnionRuleVO>> selectRuleTreeByTaskId(@PathVariable(value="systemId") String var1);

    @GetMapping(value={"/api/gcreport/v1/unionrules/ruleTreeNoInitRule/task/{taskId}/{periodStr}/{orgType}/{orgCode}"})
    public BusinessResponseEntity<List<UnionRuleVO>> selectRuleTreeWithoutInitRuleByTaskId(@PathVariable(value="taskId") String var1, @PathVariable(value="periodStr") String var2, @PathVariable(value="orgType") String var3, @PathVariable(value="orgCode") String var4);

    @GetMapping(value={"/api/gcreport/v1/unionrules/findAllRuleTitles/{systemId}"})
    public BusinessResponseEntity<List<BaseRuleVO>> findAllRuleTitles(@PathVariable(value="systemId") String var1);

    @PostMapping(value={"/api/gcreport/v1/unionrules/{id}/stop/{startFlag}"})
    public BusinessResponseEntity<Object> setUnionRuleStatus(@PathVariable(value="id") String var1, @PathVariable(value="startFlag") Boolean var2);

    @PostMapping(value={"/api/gcreport/v1/unionrules/{id}/move/{step}"})
    public BusinessResponseEntity<String> moveNode(@PathVariable(value="id") String var1, @PathVariable(value="step") Integer var2);

    @PostMapping(value={"/api/gcreport/v1/unionrules/drag"})
    public BusinessResponseEntity<Object> dragNode(@Valid @RequestBody DragRuleVO var1);

    @PostMapping(value={"/api/gcreport/v1/unionrules/{id}/parent/{parentId}"})
    public BusinessResponseEntity<UnionRuleVO> copyOrPasteNode(@PathVariable(value="id") String var1, @PathVariable(value="parentId") String var2, @RequestParam(value="action") String var3);

    @PostMapping(value={"/api/gcreport/v1/unionrules/downloadFile/{reportSystem}"})
    public ResponseEntity<Resource> downloadFile(@PathVariable String var1, @RequestBody Map<String, Object> var2, HttpServletRequest var3);

    @PostMapping(value={"/api/gcreport/v1/unionrules/exportRuleToExcel/{reportSystem}"})
    public void exportRuleToExcel(@PathVariable String var1, @RequestBody Map<String, Object> var2, HttpServletResponse var3);

    @PostMapping(value={"/api/gcreport/v1/unionrules/uploadFile/{reportSystem}"})
    public BusinessResponseEntity<Object> uploadFile(@PathVariable String var1, @RequestParam boolean var2, @RequestParam MultipartFile var3);

    @GetMapping(value={"/api/gcreport/v1/unionrules/initRuleTree/{systemId}/rules/{ruleTypes}"})
    public BusinessResponseEntity<List<UnionRuleVO>> initRuleTree(@PathVariable(value="systemId") String var1, @PathVariable(value="ruleTypes") String var2);

    @GetMapping(value={"/api/gcreport/v1/unionrules/allInitRuleTree/{systemId}"})
    public BusinessResponseEntity<List<UnionRuleVO>> allInitRuleTree(@PathVariable(value="systemId") String var1);

    @GetMapping(value={"/api/gcreport/v1/unionrules/getManagementDimensionByReportSystem/{reportSystem}"})
    public BusinessResponseEntity<List<SelectOptionVO>> getManagementDimensionVOByReportSystem(@PathVariable(value="reportSystem") String var1);

    @PostMapping(value={"/api/gcreport/v1/unionrules/getFilterRepeatFetchSubject"})
    public BusinessResponseEntity<Map<String, List<String>>> getFilterRepeatFetchSubject(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/unionrules/getDataSourceSubject/{ruleId}"})
    public BusinessResponseEntity<List<String>> getDataSourceSubject(@PathVariable(value="ruleId") String var1);

    @GetMapping(value={"/api/gcreport/v1/unionrules/getFloatLineRuleOption/{reportSystem}"})
    public BusinessResponseEntity<List<SelectFloatLineOptionTreeVO>> getFloatLineRuleOption(@PathVariable(value="reportSystem") String var1);

    @GetMapping(value={"/api/gcreport/v1/unionrules/getDataRegionFieldOption/{dataRegionKey}"})
    public BusinessResponseEntity<List<SelectOptionVO>> getDataRegionFieldOption(@PathVariable(value="dataRegionKey") String var1);

    public List<UnionRuleVO> selectUnionRuleChildrenByGroup(String var1);

    @GetMapping(value={"/api/gcreport/v1/unionrules/getChangeRatioOption/{reportSystem}"})
    public BusinessResponseEntity<Map<String, Object>> getChangeRatioOption(@PathVariable(value="reportSystem") String var1);

    @GetMapping(value={"/api/gcreport/v1/unionrules/getAllRuleType"})
    public BusinessResponseEntity<List<Map<String, String>>> getAllRuleType();

    @GetMapping(value={"/api/gcreport/v1/unionrules/getFormulaFetchDataSourceTable/{systemId}/{ruleType}"})
    public BusinessResponseEntity<List<String>> getFormulaFetchDataSourceTable(@PathVariable(value="ruleType") String var1, @PathVariable(value="systemId") String var2);

    @PostMapping(value={"/api/gcreport/v1/unionrules/listRuleTree"})
    public BusinessResponseEntity<List<ITree<UnionRuleVO>>> listRuleTree(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/unionrules/reloadCache"})
    public BusinessResponseEntity reloadCache(@RequestBody String var1);
}

