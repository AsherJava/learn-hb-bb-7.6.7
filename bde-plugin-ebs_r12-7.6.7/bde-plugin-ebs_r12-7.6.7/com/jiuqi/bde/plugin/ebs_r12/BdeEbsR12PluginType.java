/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.dc.base.common.enums.LicenceSymbolEnum
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.StorageType
 */
package com.jiuqi.bde.plugin.ebs_r12;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.ebs_r12.assist.EbsR12AssistPojo;
import com.jiuqi.bde.plugin.ebs_r12.assist.EbsR12AssistProvider;
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
public class BdeEbsR12PluginType
extends IBdePluginType {
    private static final String SYMBOL = "EBS_R12";
    private static final String TITLE = "\u3010Oracle\u3011EBS_R12";
    @Autowired
    private EbsR12AssistProvider assistProvider;
    public static final String KM_INIT_SQL = "SELECT DISTINCT V.FLEX_VALUE AS ID,        V.FLEX_VALUE AS CODE,        V.DESCRIPTION AS NAME\n  FROM FND_FLEX_VALUES_VL V\n  JOIN FND_FLEX_VALUE_SETS S\n    ON S.FLEX_VALUE_SET_ID = V.FLEX_VALUE_SET_ID\n   AND S.FLEX_VALUE_SET_NAME = 'HDPI_ACCOUNTING'\n  JOIN FND_ID_FLEX_SEGMENTS_VL FIFS ON FIFS.FLEX_VALUE_SET_ID = S.FLEX_VALUE_SET_ID \n   AND FIFS.APPLICATION_COLUMN_NAME = 'SEGMENT3'\n ORDER BY V.FLEX_VALUE";
    public static final String QUERY_ASSIST_ITEM_SQL = "SELECT V.FLEX_VALUE AS ID,        V.FLEX_VALUE AS CODE,        V.DESCRIPTION AS NAME\n  FROM FND_FLEX_VALUES_VL V\n  JOIN FND_FLEX_VALUE_SETS S\n    ON S.FLEX_VALUE_SET_ID = V.FLEX_VALUE_SET_ID\n   AND S.FLEX_VALUE_SET_NAME = '%1$S'";

    public String getSymbol() {
        return SYMBOL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getLicenceSymbol() {
        return LicenceSymbolEnum.EBS.getSymbol();
    }

    public Integer getOrder() {
        return 720;
    }

    public String storageType() {
        return StorageType.ID.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO fieldDTO = this.buildBasicField("MD_ACCTSUBJECT", "\u79d1\u76ee");
        fieldDTO.setTableName("MD_ACCTSUBJECT");
        fieldDTO.setName("\u79d1\u76ee");
        fieldDTO.setAdvancedSql(KM_INIT_SQL);
        fieldDTO.setRuleType(RuleType.NONE.getCode());
        return fieldDTO;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        List<EbsR12AssistPojo> assistList = this.assistProvider.listAssist(dataSchemeDTO.getDataSourceCode());
        ArrayList<FieldDTO> fieldDTOList = new ArrayList<FieldDTO>();
        for (EbsR12AssistPojo assistPojo : assistList) {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setName(assistPojo.getCode());
            fieldDTO.setTitle(assistPojo.getName());
            fieldDTO.setTableName(assistPojo.getCode());
            fieldDTO.setRuleType(RuleType.NONE.getCode());
            fieldDTO.setAdvancedSql(String.format(QUERY_ASSIST_ITEM_SQL, assistPojo.getFlexValueSetName()));
            fieldDTOList.add(fieldDTO);
        }
        return fieldDTOList;
    }
}

