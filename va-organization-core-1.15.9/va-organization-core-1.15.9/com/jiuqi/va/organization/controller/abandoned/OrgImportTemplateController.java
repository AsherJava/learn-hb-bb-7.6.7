/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.util.LogUtil
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.organization.controller.abandoned;

import com.jiuqi.va.domain.common.JSONUtil;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/org/template"})
@Deprecated
@ConditionalOnProperty(name={"nvwa.organization.binary.compatible"}, havingValue="true", matchIfMissing=true)
public class OrgImportTemplateController {
    @Autowired
    private OrgImportTemplateService orgImportTemplateService;

    @PostMapping(value={"/add"})
    R add(@RequestBody OrgImportTemplateDTO orgImportTemplateDTO) {
        if (orgImportTemplateDTO == null || !StringUtils.hasText(orgImportTemplateDTO.getCode()) || !StringUtils.hasText(orgImportTemplateDTO.getName())) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
        }
        if (this.orgImportTemplateService.add(orgImportTemplateDTO) > 0) {
            LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\u6a21\u677f", (String)"\u65b0\u589e", (String)orgImportTemplateDTO.getCode(), (String)orgImportTemplateDTO.getName(), (String)"");
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    @PostMapping(value={"/update"})
    R update(@RequestBody OrgImportTemplateDTO orgImportTemplateDTO) {
        if (orgImportTemplateDTO == null || !StringUtils.hasText(orgImportTemplateDTO.getCode()) || !StringUtils.hasText(orgImportTemplateDTO.getName())) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
        }
        if (this.orgImportTemplateService.update(orgImportTemplateDTO) > 0) {
            LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\u6a21\u677f", (String)"\u66f4\u65b0", (String)orgImportTemplateDTO.getCode(), (String)orgImportTemplateDTO.getName(), (String)"");
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    @PostMapping(value={"/delete"})
    R delete(@RequestBody OrgImportTemplateDTO orgImportTemplateDTO) {
        if (orgImportTemplateDTO == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
        }
        if (this.orgImportTemplateService.delete(orgImportTemplateDTO) > 0) {
            LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\u6a21\u677f", (String)"\u5220\u9664", (String)orgImportTemplateDTO.getCode(), (String)orgImportTemplateDTO.getName(), (String)"");
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    @PostMapping(value={"/list"})
    PageVO<OrgImportTemplateDO> list(@RequestBody OrgImportTemplateDTO orgImportTemplateDTO) {
        PageVO res = new PageVO(true);
        if (orgImportTemplateDTO == null) {
            return res;
        }
        List<OrgImportTemplateDO> list = this.orgImportTemplateService.list(orgImportTemplateDTO);
        for (OrgImportTemplateDO OrgImportTemplateDO2 : list) {
            OrgImportTemplateDO2.setTemplatedata(JSONUtil.parseArray((String)OrgImportTemplateDO2.getTemplatedata().toString()));
        }
        res.setRows(list);
        res.setTotal(list.size());
        return res;
    }

    @PostMapping(value={"/getImportResult"})
    R getImportResult(@RequestBody OrgImportTemplateDTO template) {
        return this.orgImportTemplateService.getImportResult(template);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/importSave"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    R importSave(@RequestBody OrgImportTemplateDTO template) {
        try {
            ShiroUtil.bindUser((UserLoginDTO)ShiroUtil.getUser());
            this.orgImportTemplateService.importSave(template);
        }
        catch (Exception e) {
            R r = R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
            return r;
        }
        finally {
            ShiroUtil.unbindUser();
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    @PostMapping(value={"/exist"})
    R exist(@RequestBody OrgImportTemplateDTO template) {
        OrgImportTemplateDTO param = new OrgImportTemplateDTO();
        param.setName(template.getName());
        List<OrgImportTemplateDO> list = this.orgImportTemplateService.list(template);
        boolean exist = list != null && list.size() == 1;
        return exist ? R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])) : R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }
}

