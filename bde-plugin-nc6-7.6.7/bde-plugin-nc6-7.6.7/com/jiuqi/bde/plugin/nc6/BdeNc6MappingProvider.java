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
package com.jiuqi.bde.plugin.nc6;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider;
import com.jiuqi.bde.plugin.nc6.BdeNc6PluginType;
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
public class BdeNc6MappingProvider
extends BaseMappingProvider {
    @Autowired
    private BdeNc6PluginType pluginType;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public List<OrgMappingTypeDTO> listMappingType(DataSchemeDTO dto) {
        ArrayList<OrgMappingTypeDTO> list = new ArrayList<OrgMappingTypeDTO>(1);
        OrgMappingTypeDTO defaultUnit = new OrgMappingTypeDTO("DEFAULT", "\u9ed8\u8ba4\u7b56\u7565", "\u8d26\u7c3f\u53d6\u6570");
        BaseDataMappingDefineDTO orgMappingDefineDTO = new BaseDataMappingDefineDTO();
        orgMappingDefineDTO.setCode("MD_ORG");
        orgMappingDefineDTO.setName("\u7ec4\u7ec7\u673a\u6784");
        orgMappingDefineDTO.setDataSchemeCode(dto.getCode());
        orgMappingDefineDTO.setAdvancedSql("SELECT ORG.PK_ORG AS ID, ORG.CODE AS CODE, ORG.NAME AS NAME, BOOK.CODE AS BOOKCODE\n  FROM ORG_ORGS ORG\n  LEFT JOIN ORG_ACCOUNTINGBOOK BOOK\n    ON BOOK.PK_RELORG = ORG.PK_ORG ");
        orgMappingDefineDTO.setRuleType(RuleType.ALL.getCode());
        orgMappingDefineDTO.setAutoMatchDim("CODE");
        List fieldMappingDefineDTOList = SchemeInitUtil.commonFieldInfo();
        fieldMappingDefineDTOList.add(new FieldMappingDefineDTO("BOOKCODE", "\u6e90\u7cfb\u7edf\u8d26\u7c3f", "BOOKCODE"));
        orgMappingDefineDTO.setItems(fieldMappingDefineDTOList);
        defaultUnit.setBaseDataMappingDefine(orgMappingDefineDTO);
        OrgMappingTypeDTO businessUnit = new OrgMappingTypeDTO("BUSINESSUNIT", "\u4e1a\u52a1\u5355\u5143", "\u4e1a\u52a1\u5355\u5143");
        BaseDataMappingDefineDTO businessUnitDefineDTO = new BaseDataMappingDefineDTO();
        businessUnitDefineDTO.setCode("MD_ORG");
        businessUnitDefineDTO.setName("\u4e1a\u52a1\u5355\u5143");
        businessUnitDefineDTO.setDataSchemeCode(dto.getCode());
        businessUnitDefineDTO.setAdvancedSql("SELECT\n        ORG.PK_ORG AS ID,\n        ORG.CODE AS CODE,\n        ORG.NAME AS NAME,\n        BOOK.CODE AS BOOKCODE\nFROM\n        ORG_ORGS ORG\nLEFT JOIN ORG_ACCOUNTINGBOOK BOOK\n    ON\n        BOOK.PK_RELORG = ORG.PK_fatherORG\nWHERE\n        ISBUSINESSUNIT = 'Y'");
        businessUnitDefineDTO.setRuleType(RuleType.ALL.getCode());
        businessUnitDefineDTO.setAutoMatchDim("CODE");
        List fieldMappingDefineDTOS = SchemeInitUtil.commonFieldInfo();
        fieldMappingDefineDTOS.add(new FieldMappingDefineDTO("BOOKCODE", "\u6e90\u7cfb\u7edf\u8d26\u7c3f", "BOOKCODE"));
        businessUnitDefineDTO.setItems(fieldMappingDefineDTOS);
        businessUnit.setBaseDataMappingDefine(businessUnitDefineDTO);
        OrgMappingTypeDTO orgMappingTypeDTO = new OrgMappingTypeDTO("UNIT", "\u7ec4\u7ec7\u673a\u6784", "\u7ec4\u7ec7\u673a\u6784\u53d6\u6570");
        BaseDataMappingDefineDTO orgDefine = new BaseDataMappingDefineDTO();
        orgDefine.setCode("MD_ORG");
        orgDefine.setName("\u7ec4\u7ec7\u673a\u6784");
        orgDefine.setDataSchemeCode(dto.getCode());
        orgDefine.setAdvancedSql("SELECT ORG.PK_ORG AS ID, ORG.CODE AS CODE, ORG.NAME AS NAME, BOOK.CODE AS BOOKCODE\n  FROM ORG_ORGS ORG\n  LEFT JOIN ORG_ACCOUNTINGBOOK BOOK\n    ON BOOK.PK_RELORG = ORG.PK_ORG ");
        orgDefine.setRuleType(RuleType.ALL.getCode());
        orgDefine.setAutoMatchDim("CODE");
        List fieldInfoeld = SchemeInitUtil.commonFieldInfo();
        fieldInfoeld.add(new FieldMappingDefineDTO("BOOKCODE", "\u6e90\u7cfb\u7edf\u8d26\u7c3f", "BOOKCODE"));
        orgDefine.setItems(fieldMappingDefineDTOS);
        orgMappingTypeDTO.setBaseDataMappingDefine(orgDefine);
        list.add(defaultUnit);
        list.add(orgMappingTypeDTO);
        list.add(businessUnit);
        return list;
    }
}

