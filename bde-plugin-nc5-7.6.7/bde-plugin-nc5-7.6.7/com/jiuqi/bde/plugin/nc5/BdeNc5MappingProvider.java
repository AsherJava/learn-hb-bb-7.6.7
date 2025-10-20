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
package com.jiuqi.bde.plugin.nc5;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider;
import com.jiuqi.bde.plugin.nc5.BdeNc5PluginType;
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
public class BdeNc5MappingProvider
extends BaseMappingProvider {
    public static final String CODE = "NC5ASSIST";
    @Autowired
    private BdeNc5PluginType pluginType;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public List<OrgMappingTypeDTO> listMappingType(DataSchemeDTO dto) {
        ArrayList<OrgMappingTypeDTO> list = new ArrayList<OrgMappingTypeDTO>(1);
        OrgMappingTypeDTO orgMappingTypeDTO = new OrgMappingTypeDTO("DEFAULT", "\u9ed8\u8ba4\u7b56\u7565", "\u9ed8\u8ba4\u7b56\u7565");
        BaseDataMappingDefineDTO orgMappingDefineDTO = new BaseDataMappingDefineDTO();
        StringBuilder orgSql = new StringBuilder();
        orgSql.append(" SELECT ");
        orgSql.append("         ORG.PK_GLORG AS ID, \n");
        orgSql.append("         ORG.GLORGCODE AS CODE, \n");
        orgSql.append("         ORG.GLORGNAME AS NAME, \n");
        orgSql.append("         ORGBOOK.GLORGBOOKCODE AS BOOKCODE \n");
        orgSql.append(" FROM \n");
        orgSql.append("         BD_GLORGBOOK ORGBOOK \n");
        orgSql.append(" INNER JOIN BD_GLORG ORG ON \n");
        orgSql.append("         ORG.PK_GLORG = ORGBOOK.PK_GLORG \n");
        orgMappingDefineDTO.setCode("MD_ORG");
        orgMappingDefineDTO.setName("\u7ec4\u7ec7\u673a\u6784");
        orgMappingDefineDTO.setAdvancedSql(orgSql.toString());
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
        return 5;
    }
}

