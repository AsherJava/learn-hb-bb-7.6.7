/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.query.sql.web;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.sql.vo.ResultVO;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(primary=false, contextId="querySqlClient", name="${feignClient.queryMgr.name}", path="${feignClient.queryMgr.path}", url="${feignClient.queryMgr.url}")
public interface QuerySqlClient {
    public static final String QUERY_SQL_BASE_API = "/api/datacenter/v1/userDefined/sql";

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/parsingSql"})
    public BusinessResponseEntity<List<TemplateFieldSettingVO>> parsingSql(@RequestBody TemplateContentVO var1);

    @GetMapping(value={"/api/datacenter/v1/userDefined/sql/previewSql/{templateId}"})
    public BusinessResponseEntity<ResultVO> previewSql(@PathVariable(value="templateId") String var1);

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/execSql/{templateId}/{pageNum}/{pageSize}"})
    public BusinessResponseEntity<ResultVO> execSql(@PathVariable(value="templateId") String var1, @PathVariable(value="pageNum") int var2, @PathVariable(value="pageSize") int var3, @RequestBody Map<String, Object> var4);

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/execSqlForTotal/{templateId}"})
    public BusinessResponseEntity<ResultVO> execSqlForTotal(@PathVariable(value="templateId") String var1, @RequestBody Map<String, Object> var2);

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/sumRow/{templateId}"})
    public BusinessResponseEntity<Map<String, Object>> getSumRowData(@PathVariable(value="templateId") String var1, @RequestBody Map<String, Object> var2);

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/sumRow/{templateId}"})
    public BusinessResponseEntity<Map<String, Object>> getSumRowDataUseQueryTemplate(@PathVariable(value="templateId") String var1, @RequestBody Map<String, Object> var2);

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/execSql/preview"})
    public BusinessResponseEntity<Object> execPreviewSql(@RequestBody QueryParamVO var1);
}

