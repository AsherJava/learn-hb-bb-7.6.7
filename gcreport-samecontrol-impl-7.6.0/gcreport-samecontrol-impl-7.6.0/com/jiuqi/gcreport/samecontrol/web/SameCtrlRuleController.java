/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.common.util.UrlUtils
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrlRuleClient
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleDragRuleVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO
 *  com.jiuqi.gcreport.unionrule.vo.ImportMessageVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  javax.servlet.http.HttpServletRequest
 *  javax.validation.Valid
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.http.ResponseEntity$BodyBuilder
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.samecontrol.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.util.UrlUtils;
import com.jiuqi.gcreport.samecontrol.api.SameCtrlRuleClient;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlRuleService;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleDragRuleVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO;
import com.jiuqi.gcreport.unionrule.vo.ImportMessageVO;
import com.jiuqi.np.core.context.NpContextHolder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Primary
public class SameCtrlRuleController
implements SameCtrlRuleClient {
    private SameCtrlRuleService sameCtrlRuleService;

    public SameCtrlRuleController(SameCtrlRuleService sameCtrlRuleService) {
        this.sameCtrlRuleService = sameCtrlRuleService;
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<SameCtrlRuleVO>> listRuleTree(String reportSystem, String taskId) {
        List<SameCtrlRuleVO> sameCtrlRuleTree = this.sameCtrlRuleService.listRuleTree(reportSystem, taskId);
        return BusinessResponseEntity.ok(sameCtrlRuleTree);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<SameCtrlRuleVO> saveSameCtrlRule(@Valid SameCtrlRuleVO sameCtrlRuleVO, HttpServletRequest request) {
        sameCtrlRuleVO.setUpdator(NpContextHolder.getContext().getUser().getName() + ":" + UrlUtils.getIp());
        SameCtrlRuleVO ruleVO = this.sameCtrlRuleService.saveSameCtrlRule(sameCtrlRuleVO);
        return BusinessResponseEntity.ok((Object)ruleVO);
    }

    public BusinessResponseEntity<SameCtrlRuleVO> getSameCtrlRuleById(String id) {
        SameCtrlRuleVO sameCtrlRuleVO = this.sameCtrlRuleService.getSameCtrlRuleById(id);
        return BusinessResponseEntity.ok((Object)sameCtrlRuleVO);
    }

    public BusinessResponseEntity<SameCtrlRuleVO> getSameCtrlRuleGroupByGroupId(String id) {
        SameCtrlRuleVO sameCtrlRuleVO = this.sameCtrlRuleService.getSameCtrlRuleGroupByGroupId(id);
        return BusinessResponseEntity.ok((Object)sameCtrlRuleVO);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> deleteSameCtrlRuleOrGroupById(String id) {
        this.sameCtrlRuleService.deleteSameCtrlRuleOrGroupById(id);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> updateSameCtrlRuleOrGroupNameById(String id, String title) {
        this.sameCtrlRuleService.updateSameCtrlRuleOrGroupNameById(id, title);
        return BusinessResponseEntity.ok((Object)"\u4fee\u6539\u6210\u529f");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> setSameCtrlRuleStatus(String id, Boolean startFlag) {
        this.sameCtrlRuleService.setSameCtrlRuleStatus(id, startFlag);
        return BusinessResponseEntity.ok((Object)(startFlag != false ? "\u542f\u7528\u6210\u529f" : "\u505c\u7528\u6210\u529f"));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> uploadFile(String reportSystem, String taskId, boolean isOverwrite, MultipartFile multipartFile) {
        Set<ImportMessageVO> result = this.sameCtrlRuleService.importJson(reportSystem, taskId, multipartFile, isOverwrite);
        return BusinessResponseEntity.ok(result);
    }

    @Transactional(rollbackFor={Exception.class})
    public ResponseEntity<Resource> downloadFile(String reportSystem, String taskId, HttpServletRequest request) {
        Resource resource = this.sameCtrlRuleService.exportJson(reportSystem, taskId);
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
    public BusinessResponseEntity<SameCtrlRuleVO> copyOrPasteNode(String id, String parentId, String action) {
        return BusinessResponseEntity.ok((Object)this.sameCtrlRuleService.cutOrPaste(id, parentId, action));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> dragNode(SameCtrlRuleDragRuleVO dragRuleVO) {
        this.sameCtrlRuleService.dragRuleNode(dragRuleVO);
        return BusinessResponseEntity.ok((Object)"\u62d6\u62fd\u6210\u529f");
    }
}

