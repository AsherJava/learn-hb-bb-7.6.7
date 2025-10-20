/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.service.WorkflowPublicParamRelationService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/param/publicRelation"})
public class WorkflowPublicParamRelationController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowPublicParamRelationController.class);
    @Autowired
    private WorkflowPublicParamRelationService publicParamRelationService;
    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    @PostMapping(value={"/handlePublicParam"})
    public R handlePublicParam(@RequestBody TenantDO tenantDO) {
        try {
            String designData = (String)tenantDO.getExtInfo("designData");
            Object workflowMetaInfoObj = tenantDO.getExtInfo("workflowMetaInfo");
            MetaInfoDTO workflowMetaInfo = (MetaInfoDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)workflowMetaInfoObj, (String)DATEFORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS), MetaInfoDTO.class);
            String newDesign = this.publicParamRelationService.handleWorkflowPublicParam(designData, workflowMetaInfo);
            R r = new R();
            r.put("designData", (Object)newDesign);
            return r;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/deletePublicParamRel"})
    public R deletePublicParamRel(@RequestBody MetaInfoEditionDO editionDO) {
        try {
            String uniqueCode = editionDO.getUniqueCode();
            if (!StringUtils.hasText(uniqueCode)) {
                return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
            }
            WorkflowPublicParamRelationDTO dto = new WorkflowPublicParamRelationDTO();
            dto.setDefineversion(editionDO.getVersionNO());
            dto.setDefinekey(editionDO.getUniqueCode());
            dto.setUsername(editionDO.getUserName());
            this.publicParamRelationService.deletePublicParamRel(dto);
            return R.ok();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/workflowPublishUpdate"})
    public R workflowPublishUpdate(@RequestBody MetaInfoEditionDO editionDO) {
        R ok = R.ok();
        List ids = (List)editionDO.getExtInfo("ids");
        if (ids == null || ids.isEmpty()) {
            return ok;
        }
        try {
            this.publicParamRelationService.workflowPublishUpdate(ids, editionDO);
            return ok;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }
}

