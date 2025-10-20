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
package com.jiuqi.bde.plugin.gs5;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider;
import com.jiuqi.bde.plugin.gs5.BdeGs5PluginType;
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
public class BdeGs5FieldMappingProvider
extends BaseMappingProvider {
    public static final String CODE = "GS5ASSIST";
    @Autowired
    private BdeGs5PluginType pluginType;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public List<OrgMappingTypeDTO> listMappingType(DataSchemeDTO dto) {
        ArrayList<OrgMappingTypeDTO> list = new ArrayList<OrgMappingTypeDTO>(1);
        OrgMappingTypeDTO orgMappingTypeDTO = new OrgMappingTypeDTO("CloudAcca", "\u5355\u4f4d\u7f16\u53f7\u5bf9\u5e94\u62a5\u8868\u5355\u4f4d", "\u6309\u7167\u4e91\u6838\u7b97\u7ef4\u5ea6\u8fdb\u884c\u5904\u7406");
        BaseDataMappingDefineDTO orgMappingDefineDTO = new BaseDataMappingDefineDTO();
        orgMappingDefineDTO.setCode("MD_ORG");
        orgMappingDefineDTO.setName("\u7ec4\u7ec7\u673a\u6784");
        orgMappingDefineDTO.setAdvancedSql("SELECT LSBZDW_DWBH AS ID, LSBZDW_DWBH AS CODE, LSBZDW_DWMC AS  NAME FROM LSBZDW");
        orgMappingDefineDTO.setRuleType(RuleType.ALL.getCode());
        orgMappingDefineDTO.setAutoMatchDim("CODE");
        orgMappingDefineDTO.setDataSchemeCode(dto.getCode());
        List fieldMappingDefineDTOList = SchemeInitUtil.commonFieldInfo();
        orgMappingDefineDTO.setItems(fieldMappingDefineDTOList);
        orgMappingTypeDTO.setBaseDataMappingDefine(orgMappingDefineDTO);
        list.add(orgMappingTypeDTO);
        return list;
    }
}

