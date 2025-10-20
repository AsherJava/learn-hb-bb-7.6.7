/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.util.SchemeInitUtil
 */
package com.jiuqi.bde.plugin.va6_40;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.plugin.common.adaptor.BaseMappingProvider;
import com.jiuqi.bde.plugin.va6_40.BdeVa6_40PluginType;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.util.SchemeInitUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeVa6_40FieldMappingProvider
extends BaseMappingProvider {
    @Autowired
    private BdeVa6_40PluginType pluginType;
    public static final String CODE = "Assist";

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public List<OrgMappingTypeDTO> listMappingType(DataSchemeDTO dto) {
        ArrayList<OrgMappingTypeDTO> list = new ArrayList<OrgMappingTypeDTO>(1);
        OrgMappingTypeDTO orgMappingTypeDTO = new OrgMappingTypeDTO("DEFAULT", "\u9ed8\u8ba4\u7b56\u7565", "\u9ed8\u8ba4\u7b56\u7565");
        Variable variable = new Variable();
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dto.getDataSourceCode()));
        BaseDataMappingDefineDTO orgMappingDefineDTO = new BaseDataMappingDefineDTO();
        orgMappingDefineDTO.setCode("MD_ORG");
        orgMappingDefineDTO.setName("\u7ec4\u7ec7\u673a\u6784");
        orgMappingDefineDTO.setRuleType(RuleType.ALL.getCode());
        orgMappingDefineDTO.setAutoMatchDim("CODE");
        StringBuilder orgSql = new StringBuilder();
        orgSql.append("SELECT ${RECID} AS ID,   \n");
        orgSql.append("       ORG.STDCODE AS CODE,  \n");
        orgSql.append("       ${STDNAME} AS NAME,  \n");
        orgSql.append("       BOOK.STDCODE AS BOOKCODE  \n");
        orgSql.append("  FROM SM_ACCTBOOK ACCTBOOK  \n");
        orgSql.append("  JOIN MD_FINORG ORG ON ACCTBOOK.UNITID = ORG.RECID \n");
        orgSql.append("  JOIN SM_BOOK BOOK ON BOOK.RECID = ACCTBOOK.BOOKID \n");
        orgSql.append(" WHERE 1 = 1  \n");
        variable.put("RECID", sqlHandler.hex("ACCTBOOK.RECID", false));
        variable.put("STDNAME", sqlHandler.concatBySeparator("ORG.STDNAME", new String[]{"BOOK.STDNAME"}));
        variable.put("EMPTYUUUID", sqlHandler.hex("00000000000000000000000000000000", true));
        String parseSql = VariableParseUtil.parse((String)orgSql.toString(), (Map)variable.getVariableMap());
        orgMappingDefineDTO.setAdvancedSql(parseSql);
        orgMappingDefineDTO.setDataSchemeCode(dto.getCode());
        List fieldMappingDefineDTOList = SchemeInitUtil.commonFieldInfo();
        fieldMappingDefineDTOList.add(new FieldMappingDefineDTO("BOOKCODE", "\u6e90\u7cfb\u7edf\u8d26\u7c3f", "BOOKCODE"));
        orgMappingDefineDTO.setItems(fieldMappingDefineDTOList);
        orgMappingTypeDTO.setBaseDataMappingDefine(orgMappingDefineDTO);
        list.add(orgMappingTypeDTO);
        return list;
    }
}

