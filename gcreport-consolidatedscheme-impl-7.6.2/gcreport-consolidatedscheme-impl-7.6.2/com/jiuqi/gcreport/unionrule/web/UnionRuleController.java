/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.common.util.UrlUtils
 *  com.jiuqi.gcreport.unionrule.api.UnionRuleClient
 *  com.jiuqi.gcreport.unionrule.vo.BaseRuleVO
 *  com.jiuqi.gcreport.unionrule.vo.DragRuleVO
 *  com.jiuqi.gcreport.unionrule.vo.ITree
 *  com.jiuqi.gcreport.unionrule.vo.ImportMessageVO
 *  com.jiuqi.gcreport.unionrule.vo.SelectFloatLineOptionTreeVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.http.ResponseEntity$BodyBuilder
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.unionrule.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.common.util.UrlUtils;
import com.jiuqi.gcreport.unionrule.api.UnionRuleClient;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.cache.UnionRuleCacheService;
import com.jiuqi.gcreport.unionrule.service.UnionRuleFileService;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.gcreport.unionrule.vo.BaseRuleVO;
import com.jiuqi.gcreport.unionrule.vo.DragRuleVO;
import com.jiuqi.gcreport.unionrule.vo.ITree;
import com.jiuqi.gcreport.unionrule.vo.ImportMessageVO;
import com.jiuqi.gcreport.unionrule.vo.SelectFloatLineOptionTreeVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.context.NpContextHolder;
import io.swagger.annotations.ApiOperation;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Primary
@RestController
public class UnionRuleController
implements UnionRuleClient {
    @Autowired
    private UnionRuleService ruleService;
    @Autowired
    private UnionRuleFileService fileService;
    @Autowired
    private RuleManagerFactory ruleManagerFactory;
    @Autowired
    private UnionRuleCacheService unionRuleCacheService;

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u4f53\u7cfb\u7684\u89c4\u5219\u6811", httpMethod="GET")
    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<UnionRuleVO>> selectRuleTreeByReportSysId(@PathVariable(value="reportSystem") String reportSystem) {
        List<UnionRuleVO> ruleVOList = this.ruleService.selectRuleTreeByReportSystem(reportSystem, false);
        return BusinessResponseEntity.ok(ruleVOList);
    }

    @ApiOperation(value="\u6dfb\u52a0\u89c4\u5219", httpMethod="POST")
    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<UnionRuleVO> addUnionRule(@Valid @RequestBody UnionRuleVO unionRuleVO, HttpServletRequest request) {
        unionRuleVO.setUpdator(NpContextHolder.getContext().getUser().getName() + ":" + UrlUtils.getIp());
        UnionRuleVO ruleVO = this.ruleService.saveOrUpdateRule(unionRuleVO);
        return BusinessResponseEntity.ok((Object)ruleVO);
    }

    @ApiOperation(value="\u67e5\u8be2\u89c4\u5219", httpMethod="GET")
    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<UnionRuleVO> selectUnionRuleById(@PathVariable(value="id") String id) {
        UnionRuleVO ruleVO = this.ruleService.selectUnionRuleById(id);
        return BusinessResponseEntity.ok((Object)ruleVO);
    }

    @ApiOperation(value="\u67e5\u8be2\u5206\u7ec4\uff08\u8282\u70b9\u5d4c\u5957\uff09", httpMethod="GET")
    public BusinessResponseEntity<UnionRuleVO> selectByGroupId(@PathVariable(value="id") String id) {
        UnionRuleVO ruleVO = this.ruleService.selectUnionRuleAndChildrenById(id, false);
        return BusinessResponseEntity.ok((Object)ruleVO);
    }

    @ApiOperation(value="\u5220\u9664\u5206\u7ec4/\u89c4\u5219", httpMethod="POST")
    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> deleteUnionRuleById(@PathVariable(value="id") String id) {
        this.ruleService.deleteUnionRule(id);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f\uff01");
    }

    @ApiOperation(value="\u4fee\u6539\u5206\u7ec4\u540d\u79f0/\u4fee\u6539\u8282\u70b9\u540d\u79f0", httpMethod="POST")
    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> updateGroupName(@PathVariable(value="id") String id, @RequestParam(value="title") String title) {
        this.ruleService.updateNodeName(id, title);
        return BusinessResponseEntity.ok((Object)"\u4fee\u6539\u6210\u529f\uff01");
    }

    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u67e5\u8be2\u89c4\u5219\u6811", httpMethod="GET")
    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<UnionRuleVO>> selectRuleTreeByTaskId(@PathVariable(value="systemId") String systemId) {
        List<UnionRuleVO> ruleVOList = this.ruleService.selectRuleTreeBySystemId(systemId);
        return BusinessResponseEntity.ok(ruleVOList);
    }

    public BusinessResponseEntity<List<UnionRuleVO>> selectRuleTreeWithoutInitRuleByTaskId(String taskId, @PathVariable(value="periodStr") String periodStr, String orgType, String orgCode) {
        List<UnionRuleVO> ruleVOList = this.ruleService.selectRuleTreeByTaskIdAndRules(taskId, periodStr, null, true, orgType, orgCode);
        return BusinessResponseEntity.ok(ruleVOList);
    }

    public BusinessResponseEntity<List<BaseRuleVO>> findAllRuleTitles(String systemId) {
        List<BaseRuleVO> ruleVOList = this.ruleService.findAllRuleTitles(systemId);
        return BusinessResponseEntity.ok(ruleVOList);
    }

    @ApiOperation(value="\u542f\u7528/\u505c\u7528\u89c4\u5219", httpMethod="POST")
    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> setUnionRuleStatus(@PathVariable(value="id") String id, @PathVariable(value="startFlag") Boolean startFlag) {
        this.ruleService.stopUnionRule(id, startFlag);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> moveNode(@PathVariable(value="id") String id, @PathVariable(value="step") Integer step) {
        String preOrNextNodeId = this.ruleService.moveRuleNode(id, step);
        return BusinessResponseEntity.ok((Object)preOrNextNodeId);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> dragNode(@Valid @RequestBody DragRuleVO dragRuleVO) {
        this.ruleService.dragRuleNode(dragRuleVO);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<UnionRuleVO> copyOrPasteNode(@PathVariable(value="id") String id, @PathVariable(value="parentId") String parentId, @RequestParam(value="action") String action) {
        UnionRuleVO ruleVO = this.ruleService.cutOrPaste(id, parentId, action);
        return BusinessResponseEntity.ok((Object)ruleVO);
    }

    @Transactional(rollbackFor={Exception.class})
    public ResponseEntity<Resource> downloadFile(@PathVariable String reportSystem, Map<String, Object> params, HttpServletRequest request) {
        Resource resource = this.fileService.exportJson(reportSystem, params);
        String encode = "rules.json";
        try {
            encode = URLEncoder.encode("\u5408\u5e76\u89c4\u5219.json", "UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", new String[]{"attachment; filename=\"" + encode + "\""})).body((Object)resource);
    }

    @Transactional(rollbackFor={Exception.class})
    public void exportRuleToExcel(@PathVariable String reportSystem, Map<String, Object> params, HttpServletResponse response) {
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        this.fileService.exportRuleToExcel(reportSystem, params, response);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> uploadFile(@PathVariable String reportSystem, @RequestParam boolean isOverwrite, @RequestParam MultipartFile multipartFile) {
        Set<ImportMessageVO> result = this.fileService.importJson(reportSystem, multipartFile, isOverwrite);
        return BusinessResponseEntity.ok(result);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<UnionRuleVO>> initRuleTree(@PathVariable(value="systemId") String systemId, @PathVariable(value="ruleTypes") String ruleTypes) {
        List<UnionRuleVO> ruleVOList = this.ruleService.selectInitRuleBySystemIdAndRuleTypes(systemId, ruleTypes);
        return BusinessResponseEntity.ok(ruleVOList);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<UnionRuleVO>> allInitRuleTree(@PathVariable(value="systemId") String systemId) {
        List<UnionRuleVO> ruleVOList = this.ruleService.selectInitRuleBySystemIdAndRuleTypes(systemId, "");
        return BusinessResponseEntity.ok(ruleVOList);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<SelectOptionVO>> getManagementDimensionVOByReportSystem(@PathVariable(value="reportSystem") String reportSystem) {
        return BusinessResponseEntity.ok(this.ruleService.getManagementDimensionVOByReportSystem(reportSystem));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Map<String, List<String>>> getFilterRepeatFetchSubject(@RequestBody Map<String, Object> fetchSubjectMap) {
        return BusinessResponseEntity.ok(this.ruleService.getFilterRepeatFetchSubject(fetchSubjectMap));
    }

    public BusinessResponseEntity<List<String>> getDataSourceSubject(@PathVariable String ruleId) {
        List<String> dataSourceSubject = this.ruleService.getDataSourceSubject(ruleId);
        return BusinessResponseEntity.ok(dataSourceSubject);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<SelectFloatLineOptionTreeVO>> getFloatLineRuleOption(@PathVariable(value="reportSystem") String reportSystem) {
        return BusinessResponseEntity.ok(this.ruleService.getFloatLineRuleOption(reportSystem));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<SelectOptionVO>> getDataRegionFieldOption(@PathVariable(value="dataRegionKey") String dataRegionKey) {
        return BusinessResponseEntity.ok(this.ruleService.getDataRegionFieldOption(dataRegionKey));
    }

    public List<UnionRuleVO> selectUnionRuleChildrenByGroup(String id) {
        return this.ruleService.selectUnionRuleChildrenByGroup(id);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Map<String, Object>> getChangeRatioOption(@PathVariable(value="reportSystem") String reportSystem) {
        return BusinessResponseEntity.ok(this.ruleService.getChangeRatioOption(reportSystem));
    }

    public BusinessResponseEntity<List<Map<String, String>>> getAllRuleType() {
        return BusinessResponseEntity.ok(this.ruleManagerFactory.getAllRuleType().stream().map(RuleManagerFactory::convertRuleType2Map).collect(Collectors.toList()));
    }

    public BusinessResponseEntity<List<String>> getFormulaFetchDataSourceTable(String ruleType, String systemId) {
        UnionRuleManager ruleManager = UnionRuleUtils.getManagerByRuleTypeCode(ruleType);
        return BusinessResponseEntity.ok(ruleManager.getRuleHandler().getFormulaFetchDataSourceTable(systemId));
    }

    public BusinessResponseEntity<List<ITree<UnionRuleVO>>> listRuleTree(Map<String, Object> param) {
        return BusinessResponseEntity.ok(this.ruleService.listRuleTree(param));
    }

    public BusinessResponseEntity reloadCache(String systemId) {
        if (StringUtils.isEmpty((String)systemId)) {
            throw new BusinessRuntimeException("\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a");
        }
        this.unionRuleCacheService.clearCache(systemId);
        return BusinessResponseEntity.ok();
    }
}

