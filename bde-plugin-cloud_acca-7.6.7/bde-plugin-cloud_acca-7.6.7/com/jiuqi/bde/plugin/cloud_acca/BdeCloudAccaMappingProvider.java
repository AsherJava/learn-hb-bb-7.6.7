/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.util.SchemeInitUtil
 */
package com.jiuqi.bde.plugin.cloud_acca;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.plugin.cloud_acca.BdeCloudAccaPluginType;
import com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
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
public class BdeCloudAccaMappingProvider
extends BaseMappingProvider {
    @Autowired
    private BdeCloudAccaPluginType bdeCloudAccaPluginType;

    public IBdePluginType getPluginType() {
        return this.bdeCloudAccaPluginType;
    }

    public List<OrgMappingTypeDTO> listMappingType(DataSchemeDTO dto) {
        ArrayList<OrgMappingTypeDTO> list = new ArrayList<OrgMappingTypeDTO>(1);
        OrgMappingTypeDTO orgMappingTypeDTO = new OrgMappingTypeDTO("CloudAcca", "\u5355\u4f4d\u7f16\u53f7\u5bf9\u5e94\u62a5\u8868\u5355\u4f4d", "\u6309\u7167\u4e91\u6838\u7b97\u7ef4\u5ea6\u8fdb\u884c\u5904\u7406");
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dto.getDataSourceCode()));
        BaseDataMappingDefineDTO orgMappingDefineDTO = new BaseDataMappingDefineDTO();
        orgMappingDefineDTO.setCode("MD_ORG");
        orgMappingDefineDTO.setName("\u7ec4\u7ec7\u673a\u6784");
        orgMappingDefineDTO.setAdvancedSql("SELECT " + sqlHandler.concat(new String[]{"T.UNITCODE", "'|'", "T.BOOKCODE"}) + " AS ID,        T.UNITCODE AS CODE,        O.NAME AS NAME,        T.BOOKCODE AS BOOKCODE FROM        GL_ACCTBOOK T LEFT JOIN MD_ORG_FIN O ON T.UNITCODE = O.CODE ORDER BY T.CODE,T.BOOKCODE ");
        orgMappingDefineDTO.setRuleType(RuleType.ALL.getCode());
        orgMappingDefineDTO.setAutoMatchDim("CODE");
        orgMappingDefineDTO.setDataSchemeCode(dto.getCode());
        List fieldMappingDefineDTOList = SchemeInitUtil.commonFieldInfo();
        fieldMappingDefineDTOList.add(new FieldMappingDefineDTO("BOOKCODE", "\u8d26\u7c3f", "BOOKCODE"));
        orgMappingDefineDTO.setItems(fieldMappingDefineDTOList);
        orgMappingTypeDTO.setBaseDataMappingDefine(orgMappingDefineDTO);
        list.add(orgMappingTypeDTO);
        list.add(this.buildMainbodyOrgMappingType(dto));
        return list;
    }

    private OrgMappingTypeDTO buildMainbodyOrgMappingType(DataSchemeDTO dto) {
        OrgMappingTypeDTO orgMappingTypeDTO = new OrgMappingTypeDTO("GlMainBody", "\u6838\u7b97\u4e3b\u4f53", "\u5c06\u5355\u4f4d\u548c\u6838\u7b97\u4e3b\u4f53\u5173\u8054\u6620\u5c04");
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dto.getDataSourceCode()));
        BaseDataMappingDefineDTO orgMappingDefineDTO = new BaseDataMappingDefineDTO();
        orgMappingDefineDTO.setCode("MD_ORG");
        orgMappingDefineDTO.setName("\u7ec4\u7ec7\u673a\u6784");
        orgMappingDefineDTO.setAdvancedSql("SELECT " + sqlHandler.concat(new String[]{"T.UNITCODE", "'|'", "T.BOOKCODE", "'|'", "GL.CODE"}) + " AS ID,        T.UNITCODE AS CODE,        O.NAME AS NAME,        T.BOOKCODE AS BOOKCODE,        GL.CODE AS ASSISTCODE,        GL.NAME as ASSISTNAME FROM GL_ACCTBOOK T LEFT JOIN MD_ORG_FIN O ON T.UNITCODE = O.CODE  join MD_GL_MAINBODY GL on T.UNITCODE = GL.BELONGUNITCODE  ORDER BY T.CODE,T.BOOKCODE, GL.CODE ");
        orgMappingDefineDTO.setRuleType(RuleType.ALL.getCode());
        orgMappingDefineDTO.setAutoMatchDim("CODE");
        orgMappingDefineDTO.setDataSchemeCode(dto.getCode());
        List fieldMappingDefineDTOList = SchemeInitUtil.commonFieldInfo();
        fieldMappingDefineDTOList.add(new FieldMappingDefineDTO("BOOKCODE", "\u8d26\u7c3f", "BOOKCODE"));
        fieldMappingDefineDTOList.add(new FieldMappingDefineDTO("ASSISTCODE", "\u5355\u4f4d\u6269\u5c55\u7ef4\u5ea6\u4ee3\u7801", "ASSISTCODE"));
        fieldMappingDefineDTOList.add(new FieldMappingDefineDTO("ASSISTNAME", "\u5355\u4f4d\u6269\u5c55\u7ef4\u5ea6\u540d\u79f0", "ASSISTNAME"));
        orgMappingDefineDTO.setItems(fieldMappingDefineDTOList);
        orgMappingTypeDTO.setBaseDataMappingDefine(orgMappingDefineDTO);
        return orgMappingTypeDTO;
    }
}

