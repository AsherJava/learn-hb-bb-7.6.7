/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  com.jiuqi.va.query.sql.vo.QueryRelationDTO
 *  com.jiuqi.va.query.sql.vo.ResultVO
 *  com.jiuqi.va.query.template.plugin.BaseInfoPlugin
 *  com.jiuqi.va.query.template.plugin.DataSourcePlugin
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.query.sql.web;

import com.jiuqi.va.query.cache.QueryCacheManage;
import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.common.service.UpgradeService;
import com.jiuqi.va.query.sql.service.QueryRelateCheckService;
import com.jiuqi.va.query.sql.service.SqlQueryService;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.sql.vo.QueryRelationDTO;
import com.jiuqi.va.query.sql.vo.ResultVO;
import com.jiuqi.va.query.template.plugin.BaseInfoPlugin;
import com.jiuqi.va.query.template.plugin.DataSourcePlugin;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuerySqlController {
    private static final String QUERY_SQL_BASE_API = "/api/datacenter/v1/userDefined/sql";
    @Autowired
    private SqlQueryService sqlQueryService;
    @Autowired
    private QueryRelateCheckService relatedCheckService;
    @Autowired
    private UpgradeService upgradeService;
    @Autowired
    private QueryCacheManage queryCacheManage;

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/parsingSql"})
    @Deprecated
    public BusinessResponseEntity<Object> parsingSql(@RequestBody TemplateContentVO templateContent) {
        if (templateContent.getDataSourceSet() == null || DCQueryStringHandle.isEmpty(templateContent.getDataSourceSet().getDefineSql())) {
            return BusinessResponseEntity.error((String)"\u6267\u884csql\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (templateContent.getTemplate() == null || DCQueryStringHandle.isEmpty(templateContent.getTemplate().getDatasourceCode())) {
            return BusinessResponseEntity.error((String)"\u6570\u636e\u6e90\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok(this.sqlQueryService.parsingSql(templateContent));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/parseSql"})
    public BusinessResponseEntity<Object> parseSql(@RequestBody QueryTemplate queryTemplate) {
        BaseInfoPlugin baseInfo = (BaseInfoPlugin)queryTemplate.getPluginByName("baseInfo", BaseInfoPlugin.class);
        DataSourcePlugin dataSource = (DataSourcePlugin)queryTemplate.getPluginByName("dataSource", DataSourcePlugin.class);
        if (!StringUtils.hasText(dataSource.getDefineSql())) {
            return BusinessResponseEntity.error((String)"\u6267\u884csql\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (!StringUtils.hasText(baseInfo.getBaseInfo().getDatasourceCode())) {
            return BusinessResponseEntity.error((String)"\u6570\u636e\u6e90\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok((Object)this.sqlQueryService.parseSql(queryTemplate));
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/sql/previewSql/{templateId}"})
    public BusinessResponseEntity<Object> previewSql(@PathVariable(value="templateId") String templateId) {
        return BusinessResponseEntity.ok((Object)this.sqlQueryService.previewSql(templateId));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/execSql/{templateId}/{pageNum}/{pageSize}"})
    public BusinessResponseEntity<Object> execSql(@PathVariable(value="templateId") String templateId, @PathVariable(value="pageNum") int pageNum, @PathVariable(value="pageSize") int pageSize, @RequestBody Map<String, Object> params) {
        if (DCQueryStringHandle.isEmpty(templateId)) {
            return BusinessResponseEntity.error((String)"templateId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok((Object)this.sqlQueryService.execSql(templateId, pageNum, pageSize, params, true));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/execSqlForTotal/{templateId}"})
    BusinessResponseEntity<ResultVO> execSqlForTotal(@PathVariable(value="templateId") String templateId, @RequestBody Map<String, Object> params) {
        if (DCQueryStringHandle.isEmpty(templateId)) {
            return BusinessResponseEntity.error((String)"templateId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok((Object)this.sqlQueryService.execSqlForTotal(templateId, params));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/execSql/preview"})
    public BusinessResponseEntity<Object> execPreviewSql(@RequestBody QueryParamVO queryParamVO) {
        if (Objects.isNull(queryParamVO.getPageSize()) || Objects.isNull(queryParamVO.getPageNum())) {
            ResultVO resultVO = new ResultVO();
            return BusinessResponseEntity.ok((Object)resultVO);
        }
        return BusinessResponseEntity.ok((Object)this.sqlQueryService.execSql(queryParamVO));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/sumRow/{templateId}"})
    public BusinessResponseEntity<Object> getSumRowData(@PathVariable(value="templateId") String templateId, @RequestBody Map<String, Object> params) {
        if (DCQueryStringHandle.isEmpty(templateId)) {
            return BusinessResponseEntity.error((String)"templateId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok(this.sqlQueryService.getSumRowData(templateId, params, true));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/sumRow/preview"})
    public BusinessResponseEntity<Object> getPreviewSumRowData(@RequestBody QueryParamVO queryParamVO) {
        return BusinessResponseEntity.ok(this.sqlQueryService.getSumRowData(queryParamVO, true));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/execSql/export/{templateId}"})
    public void export(@PathVariable(value="templateId") String templateId, @RequestBody Map<String, Object> params) {
        this.sqlQueryService.export(templateId, params);
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/execSql/export/preview"})
    public void exportPreview(@RequestBody QueryParamVO queryParamVO) {
        this.sqlQueryService.exportPreview(queryParamVO);
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/sql/execSql/test/{templateId}"})
    public BusinessResponseEntity<Object> upgrade(@PathVariable(value="templateId") String templateId) {
        this.upgradeService.parserDefine(templateId);
        return BusinessResponseEntity.error((String)"\u4e0d\u652f\u6301\u7684\u64cd\u4f5c");
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/clearCache"})
    public BusinessResponseEntity<Object> clearCache() {
        this.queryCacheManage.clearAllCache();
        return BusinessResponseEntity.ok();
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/setValue"})
    public BusinessResponseEntity<Object> setValue(@RequestBody QueryParamVO queryParamVO) {
        return BusinessResponseEntity.ok(this.sqlQueryService.setValue(queryParamVO));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/expandAll"})
    public BusinessResponseEntity<Object> expandAll(@RequestBody QueryParamVO queryParamVO) {
        return BusinessResponseEntity.ok((Object)this.sqlQueryService.expandAll(queryParamVO));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/relate/check/{templateId}"})
    public BusinessResponseEntity<Object> checkRelateEnable(@PathVariable(value="templateId") String templateId, @RequestBody QueryRelationDTO relationVO) {
        return BusinessResponseEntity.ok((Object)this.relatedCheckService.checkRelateEnable(relationVO, templateId));
    }
}

