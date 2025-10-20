/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 */
package com.jiuqi.dc.mappingscheme.impl.define;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;

public interface IFieldMappingSqlBuilder {
    public IRuleType getRuleType();

    public String buildOdsFieldSql(FieldMappingDefineDTO var1);

    public String buildSelectSql(FieldMappingDefineDTO var1);

    public String buildJoinSql(FieldMappingDefineDTO var1);

    public String buildGroupSql(FieldMappingDefineDTO var1);

    default public String getTableNameWithoutRef(String itemFieldName) {
        return DataRefUtil.getTableName((String)itemFieldName);
    }

    public String buildDirectGroupSql(FieldMappingDefineDTO var1);

    public String buildDirectSelectSql(FieldMappingDefineDTO var1);

    public String buildDirectJoinSql(FieldMappingDefineDTO var1);

    public void setSqlHandler(IDbSqlHandler var1);

    public String buildBaseCodeSql(DataMappingDefineDTO var1);
}

