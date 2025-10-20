/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.util.SchemeInitUtil
 */
package com.jiuqi.bde.plugin.ebs_r12;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider;
import com.jiuqi.bde.plugin.ebs_r12.BdeEbsR12PluginType;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.util.SchemeInitUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeEbsR12MappingProvider
extends BaseMappingProvider {
    public static final String CODE = "EbsR12Assist";
    @Autowired
    private BdeEbsR12PluginType ebsR12PluginType;
    public static final String ORG_INIT_SQL = "SELECT DISTINCT V.FLEX_VALUE || '-' || GL.LEDGER_ID AS id,\n       V.FLEX_VALUE AS code,\n       V.DESCRIPTION AS name,\n       GL.LEDGER_ID AS bookCode\n  FROM FND_FLEX_VALUES_VL V\n  JOIN FND_FLEX_VALUE_SETS S\n    ON S.FLEX_VALUE_SET_ID = V.FLEX_VALUE_SET_ID\n   AND S.FLEX_VALUE_SET_NAME = 'HDPI_COMPANY'\n  JOIN GL_CODE_COMBINATIONS GCC ON GCC.SEGMENT1 = V.FLEX_VALUE\n  JOIN GL_LEDGERS GL ON GL.CHART_OF_ACCOUNTS_ID = GCC.CHART_OF_ACCOUNTS_ID\n WHERE V.DESCRIPTION IS NOT NULL\n ORDER BY ID";

    public IBdePluginType getPluginType() {
        return this.ebsR12PluginType;
    }

    public List<OrgMappingTypeDTO> listMappingType(DataSchemeDTO dto) {
        ArrayList<OrgMappingTypeDTO> list = new ArrayList<OrgMappingTypeDTO>(1);
        OrgMappingTypeDTO orgMappingTypeDTO = new OrgMappingTypeDTO("EbsR12", "\u5355\u4f4d\u7f16\u53f7\u5bf9\u5e94\u62a5\u8868\u5355\u4f4d", "\u6309\u7167EbsR12\u7ef4\u5ea6\u8fdb\u884c\u5904\u7406");
        BaseDataMappingDefineDTO orgMappingDefineDTO = new BaseDataMappingDefineDTO();
        orgMappingDefineDTO.setCode("MD_ORG");
        orgMappingDefineDTO.setName("\u7ec4\u7ec7\u673a\u6784");
        orgMappingDefineDTO.setAdvancedSql(ORG_INIT_SQL);
        orgMappingDefineDTO.setRuleType(RuleType.ALL.getCode());
        orgMappingDefineDTO.setAutoMatchDim("CODE");
        orgMappingDefineDTO.setDataSchemeCode(dto.getCode());
        List fieldMappingDefineDTOList = SchemeInitUtil.commonFieldInfo();
        fieldMappingDefineDTOList.add(new FieldMappingDefineDTO("BOOKCODE", "\u6e90\u7cfb\u7edf\u8d26\u7c3f", "BOOKCODE"));
        orgMappingDefineDTO.setItems(fieldMappingDefineDTOList);
        orgMappingTypeDTO.setBaseDataMappingDefine(orgMappingDefineDTO);
        list.add(orgMappingTypeDTO);
        return list;
    }

    public Integer showOrder() {
        return 1;
    }
}

