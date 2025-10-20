/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.template.vo.ConfigureExportVO
 *  com.jiuqi.va.query.template.vo.QueryConfigureImportVO
 *  com.jiuqi.va.query.template.vo.QueryPluginCheckVO
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.query.template.web;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.common.UpgradeDataTool;
import com.jiuqi.va.query.common.service.UpgradeService;
import com.jiuqi.va.query.template.check.TemplateDesignCheckService;
import com.jiuqi.va.query.template.dao.QueryTemplateInfoDao;
import com.jiuqi.va.query.template.service.TemplateContentService;
import com.jiuqi.va.query.template.service.TemplateDesignService;
import com.jiuqi.va.query.template.vo.ConfigureExportVO;
import com.jiuqi.va.query.template.vo.QueryConfigureImportVO;
import com.jiuqi.va.query.template.vo.QueryPluginCheckVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class QueryTemplateContentController {
    private static final String QUERY_TEMPLATE_BASE_API = "/api/datacenter/v1/userDefined/template";
    @Autowired
    private TemplateContentService templateContentService;
    @Autowired
    private TemplateDesignService templateDesignService;
    @Autowired
    private TemplateDesignCheckService templateDesignCheckService;

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/showOverflowTooltip"})
    public List<Map<String, Object>> showOverflowTooltip() {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> allow = new HashMap<String, Object>();
        allow.put("title", "\u662f");
        allow.put("key", 1);
        result.add(allow);
        HashMap<String, Object> notAllow = new HashMap<String, Object>();
        notAllow.put("title", "\u5426");
        notAllow.put("key", 0);
        result.add(notAllow);
        return result;
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/getTableHeader/{templateId}"})
    public BusinessResponseEntity<Object> getTableHeader(@PathVariable(value="templateId") String templateId) {
        return BusinessResponseEntity.ok(this.templateContentService.getTableHeader(templateId));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/template/getTableHeader/preview"})
    public BusinessResponseEntity<Object> getPreviewTableHeader(@RequestBody List<TemplateFieldSettingVO> fields) {
        return BusinessResponseEntity.ok(this.templateContentService.getPreviewTableHeader(fields));
    }

    @Deprecated
    @PostMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/save"})
    public BusinessResponseEntity<Object> templateContentSave(@RequestBody TemplateContentVO templateContentVO) {
        return BusinessResponseEntity.ok((Object)this.templateContentService.templateContentSave(templateContentVO));
    }

    @Deprecated
    @PostMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/update"})
    public BusinessResponseEntity<Object> templateContentUpdate(@RequestBody TemplateContentVO templateContentVO) {
        this.templateContentService.templateContentUpdate(templateContentVO);
        return BusinessResponseEntity.ok();
    }

    @Deprecated
    @GetMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/query/{templateId}"})
    public BusinessResponseEntity<Object> getTemplateContent(@PathVariable(value="templateId") String templateId) {
        return BusinessResponseEntity.ok((Object)this.templateContentService.getTemplateContent(templateId));
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/queryByCode/{code}"})
    public BusinessResponseEntity<Object> getTemplateContentByCode(@PathVariable(value="code") String code) {
        return BusinessResponseEntity.ok((Object)this.templateContentService.getTemplateContentByCode(code));
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/templateParams/query/{templateId}"})
    public BusinessResponseEntity<Object> getTemplateParams(@PathVariable(value="templateId") String templateId) {
        return BusinessResponseEntity.ok(this.templateContentService.getTemplateParams(templateId));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/template/configureImport/preview"})
    public BusinessResponseEntity<Object> configureImportPreview(@RequestParam(value="uploadFile") MultipartFile uploadFile) {
        return BusinessResponseEntity.ok((Object)this.templateContentService.configureImportPreview(uploadFile));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/template/configureImport"})
    public BusinessResponseEntity<Object> configureImport(@RequestBody ConfigureExportVO configureExportVO) {
        QueryConfigureImportVO queryConfigureImportVO = this.templateContentService.configureImport(configureExportVO);
        return BusinessResponseEntity.ok((Object)queryConfigureImportVO);
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/template/configureExport"})
    public BusinessResponseEntity<Object> configureExport(@RequestBody List<String> templateIds) {
        return BusinessResponseEntity.ok((Object)this.templateContentService.configureExport(templateIds));
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/relateQuery/{templateId}"})
    public BusinessResponseEntity<Object> getTemplateRelateQuery(@PathVariable(value="templateId") String templateId) {
        return BusinessResponseEntity.ok(this.templateContentService.getTemplateRelateQuery(templateId));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/copy/{templateId}/{groupId}"})
    public BusinessResponseEntity<String> templateCopy(@PathVariable(value="templateId") String templateId, @PathVariable(value="groupId") String groupId, @RequestBody TemplateInfoVO templateInfoVO) {
        return BusinessResponseEntity.ok((Object)this.templateContentService.templateCopy(templateId, groupId, templateInfoVO));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/move/{templateId}/{groupId}"})
    public BusinessResponseEntity<String> templateMove(@PathVariable(value="templateId") String templateId, @PathVariable(value="groupId") String groupId) {
        return BusinessResponseEntity.ok((Object)this.templateContentService.templateMove(templateId, groupId));
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/migration/{code}"})
    public BusinessResponseEntity migration(@PathVariable(value="code") String code) {
        QueryTemplateInfoDao queryTemplateInfoDao = DCQuerySpringContextUtils.getBean(QueryTemplateInfoDao.class);
        TemplateInfoVO templatesByCode = queryTemplateInfoDao.getTemplatesByCode(code);
        UpgradeService upgradeService = DCQuerySpringContextUtils.getBean(UpgradeService.class);
        upgradeService.templateMigration(templatesByCode.getId());
        return BusinessResponseEntity.ok();
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/migration/all"})
    public BusinessResponseEntity migrationAll() {
        UpgradeDataTool upgradeDataTool = DCQuerySpringContextUtils.getBean(UpgradeDataTool.class);
        upgradeDataTool.execute(null);
        return BusinessResponseEntity.ok();
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/query/design/{templateId}"})
    public BusinessResponseEntity<QueryTemplate> getTemplate(@PathVariable(value="templateId") String templateId) {
        QueryTemplate queryTemplate = this.templateDesignService.getTemplate(templateId);
        return BusinessResponseEntity.ok((Object)queryTemplate);
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/query/biz/{templateId}"})
    public BusinessResponseEntity<QueryTemplate> getBizTemplate(@PathVariable(value="templateId") String templateId) {
        QueryTemplate queryTemplate = this.templateDesignService.getBizTemplate(templateId);
        return BusinessResponseEntity.ok((Object)queryTemplate);
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/query/biz/preview"})
    public BusinessResponseEntity<QueryTemplate> getBizTemplatePreview(@RequestBody QueryTemplate queryTemplate) {
        return BusinessResponseEntity.ok((Object)this.templateDesignService.getBizTemplate(queryTemplate));
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/template/queryByCode/{code}"})
    public BusinessResponseEntity<QueryTemplate> getQueryTemplateByCode(@PathVariable(value="code") String code) {
        return BusinessResponseEntity.ok((Object)this.templateDesignService.getTemplateByCode(code));
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/template/biz/queryByCode/{code}"})
    public BusinessResponseEntity<QueryTemplate> getBizTemplateByCode(@PathVariable(value="code") String code) {
        QueryTemplate templateByCode = this.templateDesignService.getTemplateByCode(code);
        QueryTemplate bizTemplate = this.templateDesignService.getBizTemplate(templateByCode.getId());
        return BusinessResponseEntity.ok((Object)bizTemplate);
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/query/save"})
    public BusinessResponseEntity<Object> saveTemplate(@RequestBody QueryTemplate queryTemplate) {
        this.templateDesignService.saveTemplate(queryTemplate, true);
        return BusinessResponseEntity.ok();
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/query/remove/{templateId}"})
    public BusinessResponseEntity removeTemplate(@PathVariable(value="templateId") String templateId) {
        this.templateDesignService.removeTemplate(templateId);
        return BusinessResponseEntity.ok();
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/biz/check"})
    public BusinessResponseEntity<List<QueryPluginCheckVO>> checkTemplate(@RequestBody QueryTemplate queryTemplate) {
        List<QueryPluginCheckVO> queryPluginCheckVOS = this.templateDesignCheckService.checkPlugin(queryTemplate);
        return BusinessResponseEntity.ok(queryPluginCheckVOS);
    }
}

