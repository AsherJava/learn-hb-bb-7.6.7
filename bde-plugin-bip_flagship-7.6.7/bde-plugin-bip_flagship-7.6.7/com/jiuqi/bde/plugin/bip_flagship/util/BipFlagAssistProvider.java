/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.bip_flagship.util;

import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.plugin.bip_flagship.BdeBipFlagShipPluginType;
import com.jiuqi.bde.plugin.bip_flagship.util.AssistPojo;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BipFlagAssistProvider
implements IAssistProvider<AssistPojo> {
    @Autowired
    private BdeBipFlagShipPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<AssistPojo> listAssist(String dataSourceCode) {
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dataSourceCode));
        ArrayList<AssistPojo> assistPojoList = new ArrayList<AssistPojo>();
        StringBuilder assquery = new StringBuilder();
        assquery.append("  SELECT\n");
        assquery.append("      DIMEXT.SOURCEDOCCODE AS CODE,\n");
        assquery.append("      MAX(DIMEXT.NAME) AS NAME,\n");
        assquery.append(String.format("      %1$s AS DOCNUM,\n", sqlHandler.SubStr("DIMEXT.VRS", "3", "")));
        assquery.append("      MAX(AUX_REGISTER.FIELD_TABLE) AS TABLENAME,\n");
        assquery.append("      MAX(AUX_REGISTER.FIELD_ID) AS TABLEID,\n");
        assquery.append("      MAX(AUX_REGISTER.FIELD_CODE) AS CODEFIELD,\n");
        assquery.append("      MAX(AUX_REGISTER.FIELD_NAME) AS NAMEFIELD\n");
        assquery.append("  FROM \n");
        assquery.append("      EPUB_MULTIDIMENSION_EXT DIMEXT \n");
        assquery.append("  LEFT JOIN FI_AUXILIARY_REGISTER AUX_REGISTER ON\n");
        assquery.append("      AUX_REGISTER.CODE = DIMEXT.SOURCEDOCCODE\n");
        assquery.append("  WHERE\n");
        assquery.append("      1 = 1\n");
        assquery.append("      AND DIMEXT.DR = 0\n");
        assquery.append("  GROUP BY\n");
        assquery.append("      DIMEXT.SOURCEDOCCODE, DIMEXT.VRS\n");
        StringBuilder attributeSql = new StringBuilder();
        attributeSql.append("  SELECT\n");
        attributeSql.append(sqlHandler.concat(new String[]{"AUX_ATTRIBUTE.TARGET_DOC_CODE", "'-'", "AUX_ATTRIBUTE.CODE"})).append(" AS CODE,\n");
        attributeSql.append("      AUX_ATTRIBUTE.NAME AS NAME,\n");
        attributeSql.append("      '' AS DOCNUM,\n");
        attributeSql.append("      REGISTER.FIELD_TABLE AS TABLENAME,\n");
        attributeSql.append("      REGISTER.FIELD_ID AS TABLEID,\n");
        attributeSql.append("      REGISTER.FIELD_CODE AS CODEFIELD,\n");
        attributeSql.append("      REGISTER.FIELD_NAME AS NAMEFIELD,\n");
        attributeSql.append("      AUX_ATTRIBUTE.MDDRELATEFIELD AS PKFIELD,\n");
        attributeSql.append("      REGISTER2.FIELD_TABLE AS MAINTABLENAME,\n");
        attributeSql.append("      REGISTER2.FIELD_ID AS MAINTABLEID,\n");
        attributeSql.append("      REGISTER2.CODE AS ASSCODE\n");
        attributeSql.append("  FROM\n");
        attributeSql.append("      FI_AUXILIARY_ATTRIBUTE AUX_ATTRIBUTE\n");
        attributeSql.append("  INNER JOIN FI_AUXILIARY_REGISTER REGISTER ON\n");
        attributeSql.append("      AUX_ATTRIBUTE.CODE = REGISTER.CODE\n");
        attributeSql.append("  INNER JOIN (SELECT CODE,NAME,FIELD_TABLE,FIELD_ID FROM FI_AUXILIARY_REGISTER) REGISTER2 ON\n");
        attributeSql.append("      REGISTER2.CODE = AUX_ATTRIBUTE.TARGET_DOC_CODE\n");
        List assistPojos = this.dataSourceService.query(dataSourceCode, assquery.toString(), null, (RowMapper)new BeanPropertyRowMapper(AssistPojo.class));
        List attributeAssistPojos = this.dataSourceService.query(dataSourceCode, attributeSql.toString(), null, (RowMapper)new BeanPropertyRowMapper(AssistPojo.class));
        assistPojoList.addAll(assistPojos);
        Map assistPojoMap = assistPojos.stream().collect(Collectors.toMap(BaseAcctAssist::getCode, Function.identity(), (K1, K2) -> K1));
        for (AssistPojo attributeAssistPojo : attributeAssistPojos) {
            AssistPojo assistPojo;
            if (assistPojoMap.get(attributeAssistPojo.getCode()) != null || (assistPojo = (AssistPojo)((Object)assistPojoMap.get(attributeAssistPojo.getAssCode()))) == null) continue;
            attributeAssistPojo.setDocNum(assistPojo.getDocNum());
            attributeAssistPojo.setName(String.format("%1$s(%2$s)", attributeAssistPojo.getName(), assistPojo.getName()));
            assistPojoList.add(attributeAssistPojo);
        }
        return assistPojoList;
    }
}

