/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.util.SchemeInitUtil
 */
package com.jiuqi.bde.plugin.standard;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider;
import com.jiuqi.bde.plugin.standard.BdeStandardPluginType;
import com.jiuqi.bde.plugin.standard.util.AssistPojo;
import com.jiuqi.bde.plugin.standard.util.StandardAssistProvider;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.util.SchemeInitUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeStandardFieldMappingProvider
extends BaseMappingProvider {
    @Autowired
    private BdeStandardPluginType pluginType;
    @Autowired
    private StandardAssistProvider assistProvider;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public List<OrgMappingTypeDTO> listMappingType(DataSchemeDTO dto) {
        ArrayList<OrgMappingTypeDTO> list = new ArrayList<OrgMappingTypeDTO>(1);
        OrgMappingTypeDTO orgMappingType = new OrgMappingTypeDTO("DEFAULT", "\u9ed8\u8ba4\u7b56\u7565", "\u9ed8\u8ba4\u7b56\u7565");
        BaseDataMappingDefineDTO businessUnitDefineDTO = new BaseDataMappingDefineDTO();
        businessUnitDefineDTO.setCode("MD_ORG");
        businessUnitDefineDTO.setName("\u9ed8\u8ba4\u7b56\u7565");
        businessUnitDefineDTO.setDataSchemeCode(dto.getCode());
        StringBuilder orgSql = new StringBuilder();
        orgSql.append("SELECT RWID AS ID,   \n");
        orgSql.append("       ORG.STDCODE AS CODE,  \n");
        orgSql.append("       ORG.STDNAME AS NAME  \n");
        orgSql.append("  FROM JC_ORGUNIT ORG  \n");
        orgSql.append(" WHERE 1 = 1  \n");
        businessUnitDefineDTO.setAdvancedSql(orgSql.toString());
        businessUnitDefineDTO.setRuleType(RuleType.ALL.getCode());
        businessUnitDefineDTO.setAutoMatchDim("CODE");
        List fieldMappingDefineDTOList = SchemeInitUtil.commonFieldInfo();
        businessUnitDefineDTO.setItems(fieldMappingDefineDTOList);
        orgMappingType.setBaseDataMappingDefine(businessUnitDefineDTO);
        list.add(orgMappingType);
        List<AssistPojo> assistList = this.assistProvider.listAssist(dto.getDataSourceCode());
        String title = "";
        for (AssistPojo assistPojo : assistList) {
            title = "\u7ec4\u7ec7\u673a\u6784+" + assistPojo.getName();
            OrgMappingTypeDTO orgMappingTypeDTO = new OrgMappingTypeDTO(assistPojo.getCode(), title, title);
            BaseDataMappingDefineDTO orgMappingDefineDto = new BaseDataMappingDefineDTO();
            orgMappingDefineDto.setCode("MD_ORG");
            orgMappingDefineDto.setName(title);
            orgMappingDefineDto.setDataSchemeCode(dto.getCode());
            StringBuilder orgDefineSql = new StringBuilder();
            orgDefineSql.append("SELECT RWID AS ID,   \n");
            orgDefineSql.append("       ORG.STDCODE AS CODE,  \n");
            orgDefineSql.append("       ORG.STDNAME AS NAME,  \n");
            orgDefineSql.append("       '' AS ASSISTCODE,  \n");
            orgDefineSql.append("       '' AS ASSISTNAME  \n");
            orgDefineSql.append("  FROM JC_ORGUNIT ORG  \n");
            orgDefineSql.append(" WHERE 1 = 1  \n");
            orgMappingDefineDto.setAdvancedSql(orgDefineSql.toString());
            orgMappingDefineDto.setRuleType(RuleType.ALL.getCode());
            orgMappingDefineDto.setAutoMatchDim("CODE");
            List fieldMappingDefineDtos = SchemeInitUtil.commonFieldInfo();
            orgMappingDefineDto.setItems(fieldMappingDefineDtos);
            orgMappingTypeDTO.setBaseDataMappingDefine(orgMappingDefineDto);
            list.add(orgMappingTypeDTO);
        }
        return list;
    }
}

