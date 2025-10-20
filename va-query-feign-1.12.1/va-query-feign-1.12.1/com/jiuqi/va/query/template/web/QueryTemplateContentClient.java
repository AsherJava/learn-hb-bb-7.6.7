/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 */
package com.jiuqi.va.query.template.web;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import com.jiuqi.va.query.tree.vo.TableHeaderVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(primary=false, contextId="queryTemplateContentClient", name="${feignClient.queryMgr.name}", path="${feignClient.queryMgr.path}", url="${feignClient.queryMgr.url}")
public interface QueryTemplateContentClient {
    public static final String QUERY_TEMPLATE_BASE_API = "/api/datacenter/v1/userDefined/template";

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/getTableHeader/{templateId}"})
    public BusinessResponseEntity<List<TableHeaderVO>> getTableHeader(@PathVariable(value="templateId") String var1);

    @Deprecated
    @GetMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/query/{templateId}"})
    public BusinessResponseEntity<TemplateContentVO> getTemplateContent(@PathVariable(value="templateId") String var1);

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/query/design/{templateId}"})
    public BusinessResponseEntity<QueryTemplate> getTemplate(@PathVariable(value="templateId") String var1);

    @Deprecated
    @GetMapping(value={"/api/datacenter/v1/userDefined/template/templateContent/queryByCode/{code}"})
    public BusinessResponseEntity<TemplateContentVO> getTemplateContentByCode(@PathVariable(value="code") String var1);

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/template/queryByCode/{code}"})
    public BusinessResponseEntity<QueryTemplate> getQueryTemplateByCode(@PathVariable(value="code") String var1);

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/template/biz/queryByCode/{code}"})
    public BusinessResponseEntity<QueryTemplate> getBizTemplate(@PathVariable(value="code") String var1);

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/templateParams/query/{templateId}"})
    public BusinessResponseEntity<List<TemplateParamsVO>> getTemplateParams(@PathVariable(value="templateId") String var1);

    @GetMapping(value={"/api/datacenter/v1/userDefined/template/relateQuery/{templateId}"})
    public BusinessResponseEntity<List<TemplateRelateQueryVO>> getTemplateRelateQuery(@PathVariable(value="templateId") String var1);
}

