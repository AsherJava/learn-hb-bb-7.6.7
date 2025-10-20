/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.define.IPluginType
 *  com.jiuqi.dc.mappingscheme.impl.util.SchemeInitUtil
 */
package com.jiuqi.bde.plugin.gs_cloud;

import com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider;
import com.jiuqi.bde.plugin.gs_cloud.BdeGsCloudPluginType;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.util.SchemeInitUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeGsCloudFieldMappingProvider
extends BaseMappingProvider {
    public static final String CODE = "GsCloudAssist";
    @Autowired
    private BdeGsCloudPluginType pluginType;

    public IPluginType getPluginType() {
        return this.pluginType;
    }

    public List<OrgMappingTypeDTO> listMappingType(DataSchemeDTO dto) {
        ArrayList<OrgMappingTypeDTO> list = new ArrayList<OrgMappingTypeDTO>(1);
        OrgMappingTypeDTO orgMappingTypeDTO = new OrgMappingTypeDTO("GSCLOUD", "\u5355\u4f4d\u7f16\u53f7\u5bf9\u5e94\u62a5\u8868\u5355\u4f4d", "\u6309\u7167GsCloud\u8fdb\u884c\u5904\u7406");
        BaseDataMappingDefineDTO orgMappingDefineDTO = new BaseDataMappingDefineDTO();
        orgMappingDefineDTO.setCode("MD_ORG");
        orgMappingDefineDTO.setName("\u7ec4\u7ec7\u673a\u6784");
        orgMappingDefineDTO.setAdvancedSql("SELECT ORG.ID AS ID, ORG.CODE AS CODE, ORG.NAME_CHS AS NAME FROM BFACCOUNTINGORGANIZATION ORG");
        orgMappingDefineDTO.setRuleType(RuleType.ALL.getCode());
        orgMappingDefineDTO.setAutoMatchDim("CODE");
        orgMappingDefineDTO.setDataSchemeCode(dto.getCode());
        List fieldMappingDefineDTOList = SchemeInitUtil.commonFieldInfo();
        orgMappingDefineDTO.setItems(fieldMappingDefineDTOList);
        orgMappingTypeDTO.setBaseDataMappingDefine(orgMappingDefineDTO);
        OrgMappingTypeDTO accountBook = new OrgMappingTypeDTO("BOOKCODE", "\u6e90\u7cfb\u7edf\u8d26\u7c3f", "\u8d26\u7c3f\u53d6\u6570");
        BaseDataMappingDefineDTO accountBookDefineDTO = new BaseDataMappingDefineDTO();
        accountBookDefineDTO.setCode("MD_BOOK");
        accountBookDefineDTO.setName("\u6e90\u7cfb\u7edf\u8d26\u7c3f");
        accountBookDefineDTO.setAdvancedSql("SELECT BOOK.ID AS ID, ORG.CODE AS CODE, ORG.NAME_CHS AS NAME, BOOK.CODE AS BOOKCODE\n  FROM BFACCOUNTINGORGANIZATION ORG\n  LEFT JOIN BFLEDGER BOOK\n    ON BOOK.ACCORGID = ORG.ID ");
        accountBookDefineDTO.setRuleType(RuleType.ALL.getCode());
        accountBookDefineDTO.setAutoMatchDim("CODE");
        accountBookDefineDTO.setDataSchemeCode(dto.getCode());
        List fieldMappingDefineDTOS = SchemeInitUtil.commonFieldInfo();
        fieldMappingDefineDTOS.add(new FieldMappingDefineDTO("BOOKCODE", "\u6e90\u7cfb\u7edf\u8d26\u7c3f", "BOOKCODE"));
        accountBookDefineDTO.setItems(fieldMappingDefineDTOS);
        accountBook.setBaseDataMappingDefine(accountBookDefineDTO);
        list.add(orgMappingTypeDTO);
        list.add(accountBook);
        return list;
    }
}

