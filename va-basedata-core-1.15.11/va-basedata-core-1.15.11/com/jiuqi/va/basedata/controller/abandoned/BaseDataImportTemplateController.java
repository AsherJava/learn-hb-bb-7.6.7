/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.util.LogUtil
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.MediaType
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.basedata.controller.abandoned;

import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.common.BasedataExcelUtils;
import com.jiuqi.va.basedata.domain.BaseDataImportTemplateDO;
import com.jiuqi.va.basedata.domain.BaseDataImportTemplateDTO;
import com.jiuqi.va.basedata.service.BaseDataImportTemplateService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheCoordinationService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Deprecated
@ConditionalOnProperty(name={"nvwa.basedata.binary.compatible"}, havingValue="true", matchIfMissing=true)
@RequestMapping(value={"/baseData/template"})
public class BaseDataImportTemplateController {
    @Autowired
    private BaseDataImportTemplateService baseDataImportTemplateService;
    @Autowired
    private BaseDataCacheCoordinationService coordinationService;
    private static RestTemplate restTemplate = new RestTemplate();

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    R add(@RequestBody BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
        if (baseDataImportTemplateDTO == null || !StringUtils.hasText(baseDataImportTemplateDTO.getCode()) || !StringUtils.hasText(baseDataImportTemplateDTO.getName())) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        if (this.baseDataImportTemplateService.add(baseDataImportTemplateDTO) > 0) {
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5bfc\u5165\u6a21\u677f", (String)"\u65b0\u589e", (String)baseDataImportTemplateDTO.getCode(), (String)baseDataImportTemplateDTO.getName(), (String)"");
            return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
        }
        return R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    R update(@RequestBody BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
        if (baseDataImportTemplateDTO == null || !StringUtils.hasText(baseDataImportTemplateDTO.getCode()) || !StringUtils.hasText(baseDataImportTemplateDTO.getName())) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        if (this.baseDataImportTemplateService.update(baseDataImportTemplateDTO) > 0) {
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5bfc\u5165\u6a21\u677f", (String)"\u66f4\u65b0", (String)baseDataImportTemplateDTO.getCode(), (String)baseDataImportTemplateDTO.getName(), (String)"");
            return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
        }
        return R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
    }

    @PostMapping(value={"/delete"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    R delete(@RequestBody BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
        if (baseDataImportTemplateDTO == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        if (this.baseDataImportTemplateService.delete(baseDataImportTemplateDTO) > 0) {
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5bfc\u5165\u6a21\u677f", (String)"\u5220\u9664", (String)baseDataImportTemplateDTO.getCode(), (String)baseDataImportTemplateDTO.getName(), (String)"");
            return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
        }
        return R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
    }

    @PostMapping(value={"/list"})
    PageVO<BaseDataImportTemplateDO> list(@RequestBody BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
        PageVO res = new PageVO(true);
        if (baseDataImportTemplateDTO == null) {
            return res;
        }
        List<BaseDataImportTemplateDO> list = this.baseDataImportTemplateService.list(baseDataImportTemplateDTO);
        for (BaseDataImportTemplateDO baseDataImportTemplateDO : list) {
            baseDataImportTemplateDO.setTemplatedata(JSONUtil.parseArray((String)baseDataImportTemplateDO.getTemplatedata().toString()));
        }
        res.setRows(list);
        res.setTotal(list.size());
        return res;
    }

    @GetMapping(value={"/exportTemplate"})
    void exportTemplate(BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
        if (baseDataImportTemplateDTO == null || baseDataImportTemplateDTO.getId() == null) {
            return;
        }
        this.baseDataImportTemplateService.exportTemplate(baseDataImportTemplateDTO);
    }

    @GetMapping(value={"/exportData"})
    void exportData(BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
        if (baseDataImportTemplateDTO == null || baseDataImportTemplateDTO.getId() == null) {
            return;
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u5bfc\u51fa\u6570\u636e", (String)baseDataImportTemplateDTO.getCode(), (String)"", (String)"");
        this.baseDataImportTemplateService.exportData(baseDataImportTemplateDTO);
    }

    @GetMapping(value={"/exportDataByBaseDataDefine"})
    void exportDataByBaseDataDefine(BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
        String tableName = baseDataImportTemplateDTO.getName();
        if (!StringUtils.hasText(tableName)) {
            return;
        }
        BaseDataDefineDO mainDefine = BasedataExcelUtils.getDefine(tableName);
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u5bfc\u51fa\u6570\u636e", (String)tableName, (String)"", (String)"");
        BasedataExcelUtils.exportData(new String[]{tableName}, null, null, mainDefine.getTitle(), new Object[0]);
    }

    @PostMapping(value={"/exportResultInfo"})
    R exportResultInfo(@RequestBody BaseDataImportTemplateDTO template) {
        return this.baseDataImportTemplateService.exportResultInfo(template);
    }

    @PostMapping(value={"/getImportResult"})
    R getImportResult(@RequestBody BaseDataImportTemplateDTO template) {
        return this.baseDataImportTemplateService.getImportResult(template);
    }

    @PostMapping(value={"/importCheck"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    R importCheck(@RequestParam MultipartFile multipartFile, BaseDataImportTemplateDTO template) {
        this.baseDataImportTemplateService.importCheck(template, multipartFile);
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/importSave"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    R importSave(@RequestBody BaseDataImportTemplateDTO template) {
        String tableName = template.getCode();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            String url = this.coordinationService.getAddrByOtherNode(tableName);
            if (url == null) {
                throw new RuntimeException("\u8bf7\u6c42\u65e0\u6cd5\u5904\u7406");
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", ShiroUtil.getSubjct().getSession().getId().toString());
            HttpEntity httpEntity = new HttpEntity((Object)JSONUtil.toJSONString((Object)((Object)template)), (MultiValueMap)headers);
            return (R)restTemplate.postForEntity(url + "baseData/template/importSave", (Object)httpEntity, R.class, new Object[0]).getBody();
        }
        try {
            ShiroUtil.bindUser((UserLoginDTO)ShiroUtil.getUser());
            this.baseDataImportTemplateService.importSave(template);
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u5bfc\u5165\u6570\u636e", (String)template.getCode(), (String)"", (String)"");
        }
        catch (Exception e) {
            R r = R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
            return r;
        }
        finally {
            ShiroUtil.unbindUser();
        }
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
    }

    @PostMapping(value={"/exist"})
    R exist(@RequestBody BaseDataImportTemplateDTO template) {
        BaseDataImportTemplateDTO param = new BaseDataImportTemplateDTO();
        param.setName(template.getName());
        boolean exist = this.baseDataImportTemplateService.exist(template);
        return exist ? R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg()) : R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
    }
}

