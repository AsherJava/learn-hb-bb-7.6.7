/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.fetch.vo.FetchQueryResultVO
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  com.jiuqi.va.query.sql.vo.QueryParseResultVO
 *  com.jiuqi.va.query.sql.vo.ResultVO
 *  com.jiuqi.va.query.template.vo.QueryResultVO
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 */
package com.jiuqi.va.query.sql.service;

import com.jiuqi.va.query.fetch.vo.FetchQueryResultVO;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.sql.vo.QueryParseResultVO;
import com.jiuqi.va.query.sql.vo.ResultVO;
import com.jiuqi.va.query.template.vo.QueryResultVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import java.util.List;
import java.util.Map;

public interface SqlQueryService {
    public List<TemplateFieldSettingVO> parsingSql(TemplateContentVO var1);

    public ResultVO previewSql(String var1);

    public ResultVO execSql(String var1, int var2, int var3, Map<String, Object> var4, boolean var5);

    public ResultVO execSql(QueryParamVO var1);

    public ResultVO execSqlForTotal(String var1, Map<String, Object> var2);

    public Map<String, Object> getSumRowData(String var1, Map<String, Object> var2, boolean var3);

    public Map<String, Object> getSumRowDataUseQueryTemplate(String var1, Map<String, Object> var2);

    public Map<String, Object> getSumRowData(QueryParamVO var1, boolean var2);

    public void export(String var1, Map<String, Object> var2);

    public void exportPreview(QueryParamVO var1);

    public void verifySpecialSQL(String var1);

    public FetchQueryResultVO getFetchResult(String var1, Map<String, Object> var2);

    public QueryParseResultVO parseSql(QueryTemplate var1);

    public QueryResultVO nativeExecSql(QueryTemplate var1, int var2, int var3, Map<String, Object> var4);

    public Map<String, Object> setValue(QueryParamVO var1);

    public ResultVO expandAll(QueryParamVO var1);
}

