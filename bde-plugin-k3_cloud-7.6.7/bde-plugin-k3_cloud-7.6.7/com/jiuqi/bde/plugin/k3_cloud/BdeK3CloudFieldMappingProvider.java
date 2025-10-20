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
package com.jiuqi.bde.plugin.k3_cloud;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider;
import com.jiuqi.bde.plugin.k3_cloud.BdeK3CloudPluginType;
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
public class BdeK3CloudFieldMappingProvider
extends BaseMappingProvider {
    public static final String CODE = "K3CloudAssist";
    @Autowired
    private BdeK3CloudPluginType pluginType;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public List<OrgMappingTypeDTO> listMappingType(DataSchemeDTO dto) {
        ArrayList<OrgMappingTypeDTO> list = new ArrayList<OrgMappingTypeDTO>(1);
        OrgMappingTypeDTO orgMappingTypeDTO = new OrgMappingTypeDTO("DEFAULT", "\u9ed8\u8ba4\u7b56\u7565", "\u9ed8\u8ba4\u7b56\u7565");
        StringBuilder orgSql = new StringBuilder();
        orgSql.append(" SELECT\n");
        orgSql.append("     BOOKL.FPKID AS ID,\n");
        orgSql.append("     BOOKL.FBOOKID AS CODE,\n");
        orgSql.append("     BOOKL.FNAME AS NAME\n");
        orgSql.append(" FROM\n");
        orgSql.append("     T_BD_ACCOUNTBOOK BOOK\n");
        orgSql.append(" INNER JOIN T_BD_ACCOUNTBOOK_L BOOKL ON\n");
        orgSql.append("     BOOK.FBOOKID = BOOKL.FBOOKID\n");
        orgSql.append(" WHERE\n");
        orgSql.append("     BOOKL.FLOCALEID = 2052\n");
        BaseDataMappingDefineDTO orgMappingDefineDTO = new BaseDataMappingDefineDTO();
        orgMappingDefineDTO.setCode("MD_ORG");
        orgMappingDefineDTO.setName("\u7ec4\u7ec7\u673a\u6784");
        orgMappingDefineDTO.setAdvancedSql(orgSql.toString());
        orgMappingDefineDTO.setRuleType(RuleType.ALL.getCode());
        orgMappingDefineDTO.setAutoMatchDim("CODE");
        List orgFieldMappingDefineDTOList = SchemeInitUtil.commonFieldInfo();
        orgMappingDefineDTO.setItems(orgFieldMappingDefineDTOList);
        orgMappingDefineDTO.setDataSchemeCode(dto.getCode());
        orgMappingTypeDTO.setBaseDataMappingDefine(orgMappingDefineDTO);
        list.add(orgMappingTypeDTO);
        return list;
    }
}

