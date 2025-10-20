/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
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
package com.jiuqi.va.basedata.controller;

import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.common.BasedataExcelUtils;
import com.jiuqi.va.basedata.domain.BaseDataImportTemplateDO;
import com.jiuqi.va.basedata.domain.BaseDataImportTemplateDTO;
import com.jiuqi.va.basedata.service.BaseDataImportTemplateService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheCoordinationService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController(value="vaBaseDataImportTemplateController")
@RequestMapping(value={"/baseData/template/binary"})
public class BaseDataImportTemplateController {
    @Autowired
    private BaseDataImportTemplateService baseDataImportTemplateService;
    @Autowired
    private BaseDataCacheCoordinationService coordinationService;
    private static RestTemplate restTemplate = new RestTemplate();

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object add(@RequestBody byte[] binaryData) {
        BaseDataImportTemplateDTO template = (BaseDataImportTemplateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, BaseDataImportTemplateDTO.class));
        if (template == null || !StringUtils.hasText(template.getCode()) || !StringUtils.hasText(template.getName())) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        R rs = null;
        if (this.baseDataImportTemplateService.add(template) > 0) {
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5bfc\u5165\u6a21\u677f", (String)"\u65b0\u589e", (String)template.getCode(), (String)template.getName(), (String)"");
            rs = R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object update(@RequestBody byte[] binaryData) {
        BaseDataImportTemplateDTO template = (BaseDataImportTemplateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, BaseDataImportTemplateDTO.class));
        if (template == null || !StringUtils.hasText(template.getCode()) || !StringUtils.hasText(template.getName())) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        R rs = null;
        if (this.baseDataImportTemplateService.update(template) > 0) {
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5bfc\u5165\u6a21\u677f", (String)"\u66f4\u65b0", (String)template.getCode(), (String)template.getName(), (String)"");
            rs = R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/delete"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object delete(@RequestBody byte[] binaryData) {
        BaseDataImportTemplateDTO template = (BaseDataImportTemplateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, BaseDataImportTemplateDTO.class));
        if (template == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        R rs = null;
        if (this.baseDataImportTemplateService.delete(template) > 0) {
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5bfc\u5165\u6a21\u677f", (String)"\u5220\u9664", (String)template.getCode(), (String)template.getName(), (String)"");
            rs = R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody byte[] binaryData) {
        BaseDataImportTemplateDTO template = (BaseDataImportTemplateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, BaseDataImportTemplateDTO.class));
        PageVO rs = new PageVO(true);
        if (template == null) {
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        List<BaseDataImportTemplateDO> list = this.baseDataImportTemplateService.list(template);
        for (BaseDataImportTemplateDO baseDataImportTemplateDO : list) {
            baseDataImportTemplateDO.setTemplatedata(JSONUtil.parseArray((String)baseDataImportTemplateDO.getTemplatedata().toString()));
        }
        rs.setRows(list);
        rs.setTotal(list.size());
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
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

    @PostMapping(value={"/importCheck"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    R importCheck(@RequestParam MultipartFile multipartFile, BaseDataImportTemplateDTO template) {
        this.baseDataImportTemplateService.importCheck(template, multipartFile);
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
    }

    @PostMapping(value={"/exportResultInfo"})
    R exportResultInfo(@RequestBody BaseDataImportTemplateDTO template) {
        return this.baseDataImportTemplateService.exportResultInfo(template);
    }

    @PostMapping(value={"/getImportResult"})
    Object getImportResult(@RequestBody byte[] binaryData) {
        BaseDataImportTemplateDTO template = (BaseDataImportTemplateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, BaseDataImportTemplateDTO.class));
        R rs = this.baseDataImportTemplateService.getImportResult(template);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/importSave"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object importSave(@RequestBody byte[] binaryData) {
        BaseDataImportTemplateDTO template = (BaseDataImportTemplateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, BaseDataImportTemplateDTO.class));
        R rs = null;
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
            rs = (R)restTemplate.postForEntity(url + "baseData/template/binary/importSave", (Object)httpEntity, R.class, new Object[0]).getBody();
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        try {
            ShiroUtil.bindUser((UserLoginDTO)ShiroUtil.getUser());
            this.baseDataImportTemplateService.importSave(template);
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u5bfc\u5165\u6570\u636e", (String)template.getCode(), (String)"", (String)"");
        }
        catch (Exception e) {
            rs = R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
            Object object = MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
            return object;
        }
        finally {
            ShiroUtil.unbindUser();
        }
        rs = R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }
}

