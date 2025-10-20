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
package com.jiuqi.bde.plugin.sap.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;

public enum SapOrgMappingType {
    DEFAULT("DEFAULT", "\u9ed8\u8ba4\u7b56\u7565\uff08\u516c\u53f8\uff09", "\u9ed8\u8ba4\u7b56\u7565\uff08\u516c\u53f8\uff09"){

        @Override
        public String buildAdvanceSql(DataSchemeDTO dto) {
            return "SELECT BUKRS AS ID,BUKRS AS CODE,MAX(BUTXT) AS NAME FROM T001 GROUP BY BUKRS";
        }
    }
    ,
    PRCTR("M", "\u5229\u6da6\u4e2d\u5fc3", "\u6309\u7167\u5229\u6da6\u4e2d\u5fc3\u7c92\u5ea6\u53d6\u6570"){

        @Override
        public String buildAdvanceSql(DataSchemeDTO dto) {
            IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)((DataSourceService)ApplicationContextRegister.getBean(DataSourceService.class)).getDbType(dto.getDataSourceCode()));
            StringBuffer prctrMappingSql = new StringBuffer();
            prctrMappingSql.append(String.format("SELECT %1$s AS ID,  \n", sqlHandler.concatBySeparator("|", new String[]{sqlHandler.toChar("T001.BUKRS"), sqlHandler.toChar("CEPC_BUKRS.PRCTR")})));
            prctrMappingSql.append("       T001.BUKRS AS CODE,  \n");
            prctrMappingSql.append("       MAX(T001.BUTXT) AS NAME,  \n");
            prctrMappingSql.append("       CEPC_BUKRS.PRCTR AS ASSISTCODE,  \n");
            prctrMappingSql.append("       MAX(CEPCT.LTEXT)  AS ASSISTNAME \n");
            prctrMappingSql.append("  FROM T001  \n");
            prctrMappingSql.append("  JOIN CEPC_BUKRS  \n");
            prctrMappingSql.append("    ON T001.MANDT = CEPC_BUKRS.MANDT  \n");
            prctrMappingSql.append("   AND T001.BUKRS = CEPC_BUKRS.BUKRS  \n");
            prctrMappingSql.append("  JOIN CEPCT  \n");
            prctrMappingSql.append("    ON CEPC_BUKRS.MANDT = CEPCT.MANDT  \n");
            prctrMappingSql.append("   AND CEPC_BUKRS.PRCTR = CEPCT.PRCTR  \n");
            prctrMappingSql.append(" GROUP BY T001.BUKRS, CEPC_BUKRS.PRCTR  \n");
            return prctrMappingSql.toString();
        }
    };

    private final String code;
    private final String name;
    private final String desc;

    private SapOrgMappingType(String code, String name, String desc) {
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

    public static SapOrgMappingType fromCode(String code) {
        Assert.isNotEmpty((String)code);
        for (SapOrgMappingType type : SapOrgMappingType.values()) {
            if (!type.getCode().equals(code)) continue;
            return type;
        }
        throw new BusinessRuntimeException(String.format("\u4e0d\u652f\u6301\u7684\u5355\u4f4d\u6620\u5c04\u7c7b\u578b\u3010%1$s\u3011", code));
    }
}

