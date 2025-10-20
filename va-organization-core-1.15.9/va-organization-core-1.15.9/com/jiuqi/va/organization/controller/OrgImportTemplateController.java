/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.util.LogUtil
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.organization.controller;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.domain.OrgImportTemplateDO;
import com.jiuqi.va.organization.domain.OrgImportTemplateDTO;
import com.jiuqi.va.organization.service.OrgImportTemplateService;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController(value="vaOrgImportTemplateController")
@RequestMapping(value={"/org/template/binary"})
public class OrgImportTemplateController {
    @Autowired
    private OrgImportTemplateService orgImportTemplateService;

    @PostMapping(value={"/list"})
    Object list(@RequestBody byte[] binaryData) {
        OrgImportTemplateDTO orgImportTemplateDTO = (OrgImportTemplateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, OrgImportTemplateDTO.class));
        PageVO res = new PageVO(true);
        if (orgImportTemplateDTO == null) {
            return MonoVO.just((Object)JSONUtil.toBytes((Object)res));
        }
        List<OrgImportTemplateDO> list = this.orgImportTemplateService.list(orgImportTemplateDTO);
        for (OrgImportTemplateDO OrgImportTemplateDO2 : list) {
            OrgImportTemplateDO2.setTemplatedata(JSONUtil.parseArray((String)OrgImportTemplateDO2.getTemplatedata().toString()));
        }
        res.setRows(list);
        res.setTotal(list.size());
        return MonoVO.just((Object)JSONUtil.toBytes((Object)res));
    }

    @PostMapping(value={"/add"})
    Object add(@RequestBody byte[] binaryData) {
        OrgImportTemplateDTO orgImportTemplateDTO = (OrgImportTemplateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, OrgImportTemplateDTO.class));
        R rs = null;
        if (orgImportTemplateDTO == null || !StringUtils.hasText(orgImportTemplateDTO.getCode()) || !StringUtils.hasText(orgImportTemplateDTO.getName())) {
            rs = R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        if (this.orgImportTemplateService.add(orgImportTemplateDTO) > 0) {
            LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\u6a21\u677f", (String)"\u65b0\u589e", (String)orgImportTemplateDTO.getCode(), (String)orgImportTemplateDTO.getName(), (String)"");
            rs = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update"})
    Object update(@RequestBody byte[] binaryData) {
        OrgImportTemplateDTO orgImportTemplateDTO = (OrgImportTemplateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, OrgImportTemplateDTO.class));
        R rs = null;
        if (orgImportTemplateDTO == null || !StringUtils.hasText(orgImportTemplateDTO.getCode()) || !StringUtils.hasText(orgImportTemplateDTO.getName())) {
            rs = R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        if (this.orgImportTemplateService.update(orgImportTemplateDTO) > 0) {
            LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\u6a21\u677f", (String)"\u66f4\u65b0", (String)orgImportTemplateDTO.getCode(), (String)orgImportTemplateDTO.getName(), (String)"");
            rs = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/delete"})
    Object delete(@RequestBody byte[] binaryData) {
        OrgImportTemplateDTO orgImportTemplateDTO = (OrgImportTemplateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, OrgImportTemplateDTO.class));
        R rs = null;
        if (orgImportTemplateDTO == null) {
            rs = R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        if (this.orgImportTemplateService.delete(orgImportTemplateDTO) > 0) {
            LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\u6a21\u677f", (String)"\u5220\u9664", (String)orgImportTemplateDTO.getCode(), (String)orgImportTemplateDTO.getName(), (String)"");
            rs = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @GetMapping(value={"/exportTemplate"})
    void exportTemplate(OrgImportTemplateDTO template) {
        if (template == null || template.getId() == null) {
            return;
        }
        this.orgImportTemplateService.exportTemplate(template);
    }

    @GetMapping(value={"/exportData"})
    void exportData(OrgImportTemplateDTO template) {
        if (template == null || template.getId() == null) {
            return;
        }
        LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)"\u5bfc\u51fa\u6570\u636e", (String)template.getCode(), (String)"", (String)"");
        this.orgImportTemplateService.exportData(template);
    }

    @PostMapping(value={"/importCheck"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    R importCheck(@RequestParam MultipartFile multipartFile, OrgImportTemplateDTO template) {
        this.orgImportTemplateService.importCheck(template, multipartFile);
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    @PostMapping(value={"/exportResultInfo"})
    void exportResultInfo(@RequestBody OrgImportTemplateDTO template) {
        this.orgImportTemplateService.exportResultInfo(template);
    }

    @PostMapping(value={"/getImportResult"})
    Object getImportResult(@RequestBody byte[] binaryData) {
        OrgImportTemplateDTO template = (OrgImportTemplateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, OrgImportTemplateDTO.class));
        R rs = this.orgImportTemplateService.getImportResult(template);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/importSave"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object importSave(@RequestBody byte[] binaryData) {
        OrgImportTemplateDTO template = (OrgImportTemplateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, OrgImportTemplateDTO.class));
        R rs = null;
        try {
            ShiroUtil.bindUser((UserLoginDTO)ShiroUtil.getUser());
            this.orgImportTemplateService.importSave(template);
        }
        catch (Exception e) {
            rs = R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
            Object object = MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
            return object;
        }
        finally {
            ShiroUtil.unbindUser();
        }
        rs = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }
}

