/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.LicenceSymbolEnum
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.StorageType
 */
package com.jiuqi.bde.plugin.standard;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.plugin.standard.util.AssistPojo;
import com.jiuqi.bde.plugin.standard.util.ShareTypeEnum;
import com.jiuqi.bde.plugin.standard.util.StandardAssistProvider;
import com.jiuqi.bde.plugin.standard.util.StandardFetchUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.LicenceSymbolEnum;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeStandardPluginType
extends IBdePluginType {
    private static final String SYMBOL = "STANDARD";
    private static final String TITLE = "\u3010\u4e45\u5176\u3011\u6807\u51c6\u4e2d\u95f4\u5e93";
    @Autowired
    private StandardAssistProvider assistProvider;

    public String getSymbol() {
        return SYMBOL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getLicenceSymbol() {
        return LicenceSymbolEnum.STANDARD.getSymbol();
    }

    public Integer getOrder() {
        return 510;
    }

    public String storageType() {
        return StorageType.ID.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO subjectField = this.buildBasicField("MD_ACCTSUBJECT", "\u79d1\u76ee");
        subjectField.setRuleType(RuleType.ID_TO_CODE.getCode());
        StringBuilder subjectSql = new StringBuilder();
        subjectSql.append("SELECT T.RWID AS ID,   \n");
        subjectSql.append("       T.STDCODE AS CODE,  \n");
        subjectSql.append("       T.STDNAME AS NAME,  \n");
        subjectSql.append("       CASE WHEN T.YEORIENT = 1 THEN 1 ELSE -1 END AS ORIENT  \n");
        subjectSql.append("  FROM ${TABLENAME} T  \n");
        subjectSql.append(" WHERE 1 = 1  \n");
        subjectSql.append("   AND (  \n");
        subjectSql.append("            T.ORGID = '${EMPTYUUUID}'  \n");
        subjectSql.append("         OR T.ORGID = '#unitCode#' \n");
        subjectSql.append("       )  \n");
        subjectSql.append("   AND T.ZTYEAR = #year# \n");
        Variable variable = new Variable();
        variable.put("TABLENAME", "JC_KM");
        variable.put("EMPTYUUUID", "00000000000000000000000000000000");
        subjectField.setAdvancedSql(VariableParseUtil.parse((String)subjectSql.toString(), (Map)variable.getVariableMap()));
        return subjectField;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO currencyField = this.buildBasicField("MD_CURRENCY", "\u5e01\u522b");
        currencyField.setRuleType(RuleType.ID_TO_CODE.getCode());
        AssistPojo assistPojo = new AssistPojo();
        assistPojo.setTableName("JC_HB");
        assistPojo.setShareType(ShareTypeEnum.SHARE_ISOLATION.name());
        currencyField.setAdvancedSql(StandardFetchUtil.getBaseDataSql(assistPojo));
        return currencyField;
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        ArrayList<FieldDTO> fieldDTOList = new ArrayList<FieldDTO>();
        Map<String, AssistPojo> assistMap = this.assistProvider.listAssist(dataSchemeDTO.getDataSourceCode()).stream().collect(Collectors.toMap(BaseAcctAssist::getCode, item -> item, (k1, k2) -> k1));
        for (AssistPojo assistPojo : assistMap.values()) {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setName(assistPojo.getCode());
            fieldDTO.setTitle(assistPojo.getName());
            fieldDTO.setTableName(assistPojo.getTableName());
            fieldDTO.setRuleType(StringUtils.isEmpty((String)assistPojo.getTableName()) ? RuleType.NONE.getCode() : RuleType.ID_TO_CODE.getCode());
            fieldDTO.setAdvancedSql(StringUtils.isEmpty((String)assistPojo.getTableName()) ? "" : StandardFetchUtil.getBaseDataSql(assistPojo));
            fieldDTOList.add(fieldDTO);
        }
        return fieldDTOList;
    }
}

