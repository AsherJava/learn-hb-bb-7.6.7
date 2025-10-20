/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.LicenceSymbolEnum
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.StorageType
 */
package com.jiuqi.bde.plugin.nc5;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.nc5.assist.Nc5AssistPojo;
import com.jiuqi.bde.plugin.nc5.assist.Nc5AssistProvider;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.LicenceSymbolEnum;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeNc5PluginType
extends IBdePluginType {
    @Autowired
    private Nc5AssistProvider assistProvider;
    private static final String SYMBOL = "NC5X";
    private static final String TITLE = "\u3010\u7528\u53cb\u3011NC5X";

    public String getSymbol() {
        return SYMBOL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getLicenceSymbol() {
        return LicenceSymbolEnum.YONYOU.getSymbol();
    }

    public Integer getOrder() {
        return 310;
    }

    public String storageType() {
        return StorageType.ID.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO fieldDTO = this.buildBasicField("MD_ACCTSUBJECT", "\u79d1\u76ee");
        fieldDTO.setName("\u79d1\u76ee");
        StringBuilder subjectSql = new StringBuilder();
        subjectSql.append(" SELECT\n");
        subjectSql.append("     SUBJECT.PK_ACCSUBJ AS ID ,\n");
        subjectSql.append("     SUBJECT.subjcode AS CODE,\n");
        subjectSql.append("     SUBJECT.subjname AS NAME,\n");
        subjectSql.append("     SUBJECT.BEGINYEAR AS BEGINYEAR,\n");
        subjectSql.append("     SUBJECT.BEGINPERIOD AS BEGINPERIOD ,\n");
        subjectSql.append("     SUBJECT.ENDYEAR AS ENDPERIOD ,\n");
        subjectSql.append("     CASE WHEN SUBJECT.BALANORIENT = 1 THEN 1 ELSE -1 END AS ORIENT\n");
        subjectSql.append(" FROM\n");
        subjectSql.append(" BD_ACCSUBJ SUBJECT\n");
        fieldDTO.setAdvancedSql(subjectSql.toString());
        fieldDTO.setRuleType(RuleType.ID_TO_CODE.getCode());
        return fieldDTO;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        List<Nc5AssistPojo> nc5AssistPojoList = this.assistProvider.listAssist(dataSchemeDTO.getDataSourceCode());
        ArrayList<FieldDTO> fieldDTOList = new ArrayList<FieldDTO>();
        nc5AssistPojoList.forEach(item -> {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setTableName(StringUtils.isEmpty((String)item.getBaseDocTableName()) ? item.getTableName() : item.getBaseDocTableName());
            fieldDTO.setName(item.getCode());
            fieldDTO.setTitle(item.getName());
            fieldDTO.setRuleType(RuleType.ALL.getCode());
            String sql = null;
            sql = StringUtils.isEmpty((String)item.getBaseDocTableName()) ? String.format("SELECT %1$s AS ID,%2$s AS CODE , %3$s AS NAME FROM %4$s", item.getTablePk(), item.getCodeField(), item.getNameField(), item.getTableName()) : ("bd_jobbasfil".equalsIgnoreCase(item.getBaseDocTableName()) ? String.format("SELECT PK_JOBBASFIL AS ID,JOBCODE AS CODE,JOBNAME AS NAME FROM BD_JOBBASFIL WHERE PK_JOBTYPE = '%1$s'", item.getDefine()) : String.format("SELECT %1$s AS ID,%2$s AS CODE , %3$s AS NAME FROM %4$s", item.getTablePk(), item.getCodeField(), item.getNameField(), item.getBaseDocTableName()));
            fieldDTO.setAdvancedSql(sql);
            fieldDTOList.add(fieldDTO);
        });
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setTableName("BD_ACCSUBJ");
        fieldDTO.setName("BD_ACCSUBJ");
        fieldDTO.setTitle("\u79d1\u76ee");
        fieldDTO.setRuleType(RuleType.ALL.getCode());
        StringBuilder subjectSql = new StringBuilder();
        subjectSql.append(" SELECT\n");
        subjectSql.append("     SUBJECT.PK_ACCSUBJ AS ID ,\n");
        subjectSql.append("     SUBJECT.SUBJCODE AS CODE,\n");
        subjectSql.append("     SUBJECT.SUBJNAME AS NAME,\n");
        subjectSql.append("     SUBJECT.BEGINYEAR AS BEGINYEAR,\n");
        subjectSql.append("     SUBJECT.BEGINPERIOD AS BEGINPERIOD ,\n");
        subjectSql.append("     SUBJECT.ENDYEAR AS ENDPERIOD ,\n");
        subjectSql.append("     CASE WHEN SUBJECT.BALANORIENT = 1 THEN 1 ELSE -1 END AS ORIENT\n");
        subjectSql.append(" FROM\n");
        subjectSql.append(" BD_ACCSUBJ SUBJECT\n");
        fieldDTO.setAdvancedSql(subjectSql.toString());
        fieldDTOList.add(fieldDTO);
        return fieldDTOList;
    }
}

