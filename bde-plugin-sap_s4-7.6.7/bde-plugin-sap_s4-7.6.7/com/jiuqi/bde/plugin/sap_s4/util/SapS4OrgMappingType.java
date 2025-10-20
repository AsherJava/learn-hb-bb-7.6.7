/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.bde.plugin.sap_s4.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;

public enum SapS4OrgMappingType {
    DEFAULT("DEFAULT", "\u9ed8\u8ba4\u7b56\u7565\uff08\u516c\u53f8\uff09", "\u9ed8\u8ba4\u7b56\u7565\uff08\u516c\u53f8\uff09"){

        @Override
        public String buildAdvanceSql(DataSchemeDTO dto) {
            return "SELECT BUKRS AS ID, BUKRS AS CODE, MAX(BUTXT) AS NAME FROM T001 GROUP BY BUKRS ";
        }
    }
    ,
    KOSTL("M", "\u6210\u672c\u4e2d\u5fc3", "\u6309\u7167\u6210\u672c\u4e2d\u5fc3\u7c92\u5ea6\u53d6\u6570"){

        @Override
        public String buildAdvanceSql(DataSchemeDTO dto) {
            IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)((DataSourceService)ApplicationContextRegister.getBean(DataSourceService.class)).getDbType(dto.getDataSourceCode()));
            StringBuffer kostlMappingSql = new StringBuffer();
            kostlMappingSql.append(String.format("SELECT %1$s AS ID,  \n", sqlHandler.concatBySeparator("|", new String[]{sqlHandler.toChar("T001.BUKRS"), sqlHandler.toChar("CSKS.KOSTL")})));
            kostlMappingSql.append("       T001.BUKRS AS CODE,  \n");
            kostlMappingSql.append("       MAX(T001.BUTXT) AS NAME,  \n");
            kostlMappingSql.append("       CSKS.KOSTL AS ASSISTCODE,  \n");
            kostlMappingSql.append("       MAX(CSKT.LTEXT)  AS ASSISTNAME \n");
            kostlMappingSql.append("  FROM T001  \n");
            kostlMappingSql.append("  JOIN CSKS  \n");
            kostlMappingSql.append("    ON T001.MANDT = CSKS.MANDT  \n");
            kostlMappingSql.append("   AND T001.BUKRS = CSKS.BUKRS  \n");
            kostlMappingSql.append("  JOIN CSKT  \n");
            kostlMappingSql.append("    ON CSKT.MANDT = CSKS.MANDT  \n");
            kostlMappingSql.append("   AND CSKT.KOSTL = CSKS.KOSTL  \n");
            kostlMappingSql.append(" GROUP BY T001.BUKRS, CSKS.KOSTL  \n");
            return kostlMappingSql.toString();
        }
    };

    private final String code;
    private final String name;
    private final String desc;

    private SapS4OrgMappingType(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }

    public abstract String buildAdvanceSql(DataSchemeDTO var1);

    public static SapS4OrgMappingType fromCode(String code) {
        Assert.isNotEmpty((String)code);
        for (SapS4OrgMappingType type : SapS4OrgMappingType.values()) {
            if (!type.getCode().equals(code)) continue;
            return type;
        }
        throw new BusinessRuntimeException(String.format("\u4e0d\u652f\u6301\u7684\u5355\u4f4d\u6620\u5c04\u7c7b\u578b\u3010%1$s\u3011", code));
    }
}

