/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.fetch.vo.FetchQueryResultVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.query.fetch.web;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.fetch.vo.FetchQueryResultVO;
import com.jiuqi.va.query.sql.service.SqlQueryService;
import com.jiuqi.va.query.template.service.TemplateContentService;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchQueryController {
    private static final String FETCH_QUERY_BASE_API = "/api/gcreport/v1/fetch/userDefined";
    @Autowired
    private SqlQueryService sqlQueryService;
    @Autowired
    private TemplateContentService templateContentService;

    @PostMapping(value={"/api/gcreport/v1/fetch/userDefined/execSql/{templateCode}"})
    public BusinessResponseEntity<FetchQueryResultVO> execSql(@PathVariable(value="templateCode") String templateCode, @RequestBody Map<String, Object> params) {
        if (DCQueryStringHandle.isEmpty(templateCode)) {
            return BusinessResponseEntity.error((String)"templateId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok((Object)this.sqlQueryService.getFetchResult(templateCode, params));
    }

    @GetMapping(value={"/api/gcreport/v1/fetch/userDefined/getQueryParams/{templateCode}"})
    public BusinessResponseEntity<List<FetchQueryFiledVO>> getQueryParams(@PathVariable(value="templateCode") String templateCode) {
        return BusinessResponseEntity.ok(this.templateContentService.getSimpleTemplateParams(templateCode));
    }

    @GetMapping(value={"/api/gcreport/v1/fetch/userDefined/getQueryFields/{templateCode}"})
    public BusinessResponseEntity<List<FetchQueryFiledVO>> getQueryFields(@PathVariable(value="templateCode") String templateCode) {
        return BusinessResponseEntity.ok(this.templateContentService.getSimpleTemplateFields(templateCode));
    }
}

