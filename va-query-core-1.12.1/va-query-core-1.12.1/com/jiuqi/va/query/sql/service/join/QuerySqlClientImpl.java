/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  com.jiuqi.va.query.sql.vo.ResultVO
 *  com.jiuqi.va.query.sql.web.QuerySqlClient
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.query.sql.service.join;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.sql.service.SqlQueryService;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.sql.vo.ResultVO;
import com.jiuqi.va.query.sql.web.QuerySqlClient;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
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
public class QuerySqlClientImpl
implements QuerySqlClient {
    @Autowired
    private SqlQueryService sqlQueryService;

    public BusinessResponseEntity<List<TemplateFieldSettingVO>> parsingSql(@RequestBody TemplateContentVO templateContent) {
        if (templateContent.getDataSourceSet() == null || DCQueryStringHandle.isEmpty(templateContent.getDataSourceSet().getDefineSql())) {
            return BusinessResponseEntity.error((String)"\u6267\u884csql\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (templateContent.getTemplate() == null || DCQueryStringHandle.isEmpty(templateContent.getTemplate().getDatasourceCode())) {
            return BusinessResponseEntity.error((String)"\u6570\u636e\u6e90\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok(this.sqlQueryService.parsingSql(templateContent));
    }

    public BusinessResponseEntity<ResultVO> previewSql(@PathVariable(value="templateId") String templateId) {
        return BusinessResponseEntity.ok((Object)this.sqlQueryService.previewSql(templateId));
    }

    public BusinessResponseEntity<ResultVO> execSql(@PathVariable(value="templateId") String templateId, @PathVariable(value="pageNum") int pageNum, @PathVariable(value="pageSize") int pageSize, @RequestBody Map<String, Object> params) {
        if (DCQueryStringHandle.isEmpty(templateId)) {
            return BusinessResponseEntity.error((String)"templateId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok((Object)this.sqlQueryService.execSql(templateId, pageNum, pageSize, params, true));
    }

    public BusinessResponseEntity<ResultVO> execSqlForTotal(String templateId, Map<String, Object> params) {
        if (DCQueryStringHandle.isEmpty(templateId)) {
            return BusinessResponseEntity.error((String)"templateId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok((Object)this.sqlQueryService.execSqlForTotal(templateId, params));
    }

    public BusinessResponseEntity<Map<String, Object>> getSumRowData(@PathVariable(value="templateId") String templateId, @RequestBody Map<String, Object> params) {
        if (DCQueryStringHandle.isEmpty(templateId)) {
            return BusinessResponseEntity.error((String)"templateId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok(this.sqlQueryService.getSumRowData(templateId, params, true));
    }

    public BusinessResponseEntity<Map<String, Object>> getSumRowDataUseQueryTemplate(String templateId, Map<String, Object> params) {
        return BusinessResponseEntity.ok(this.sqlQueryService.getSumRowDataUseQueryTemplate(templateId, params));
    }

    public BusinessResponseEntity<Object> execPreviewSql(QueryParamVO queryParamVO) {
        return BusinessResponseEntity.ok((Object)this.sqlQueryService.execSql(queryParamVO));
    }
}

