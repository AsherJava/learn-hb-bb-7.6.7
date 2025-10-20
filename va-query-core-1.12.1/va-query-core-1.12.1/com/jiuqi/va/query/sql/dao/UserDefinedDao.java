/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 */
package com.jiuqi.va.query.sql.dao;

import com.jiuqi.va.query.sql.dto.PageSqlExecConditionDTO;
import com.jiuqi.va.query.sql.dto.SqlExecConditionDTO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import java.util.List;
import java.util.Map;

public interface UserDefinedDao {
    public List<TemplateFieldSettingVO> parsingSql(String var1, SqlExecConditionDTO var2);

    public List<Map<String, Object>> execSql(String var1, PageSqlExecConditionDTO var2);

    public Map<String, Object> calcTotalLine(String var1, PageSqlExecConditionDTO var2);

    public int getTotalCount(String var1, SqlExecConditionDTO var2);

    public List<String[]> getFetchResultRowDatas(String var1, PageSqlExecConditionDTO var2);

    public void batchInsertTempTable(String var1, String var2, String var3, List<String> var4);

    public void clearTempTable(String var1, String var2);
}

