/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.fetch.vo.FetchQueryResultVO
 *  com.jiuqi.va.query.fetch.web.FetchQueryClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.query.fetch.service.join;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.fetch.vo.FetchQueryResultVO;
import com.jiuqi.va.query.fetch.web.FetchQueryClient;
import com.jiuqi.va.query.sql.service.SqlQueryService;
import com.jiuqi.va.query.template.service.TemplateContentService;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Primary
public class FetchQueryClientImpl
implements FetchQueryClient {
    @Autowired
    private SqlQueryService sqlQueryService;
    @Autowired
    private TemplateContentService templateContentService;

    public BusinessResponseEntity<FetchQueryResultVO> execSql(@PathVariable(value="templateCode") String templateCode, @RequestBody Map<String, Object> params) {
        if (DCQueryStringHandle.isEmpty(templateCode)) {
            return BusinessResponseEntity.error((String)"templateCode\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok((Object)this.sqlQueryService.getFetchResult(templateCode, params));
    }

    public BusinessResponseEntity<List<FetchQueryFiledVO>> getQueryParams(@PathVariable(value="templateCode") String templateCode) {
        return BusinessResponseEntity.ok(this.templateContentService.getSimpleTemplateParams(templateCode));
    }

    public BusinessResponseEntity<List<FetchQueryFiledVO>> getQueryFields(@PathVariable(value="templateCode") String templateCode) {
        return BusinessResponseEntity.ok(this.templateContentService.getSimpleTemplateFields(templateCode));
    }
}

