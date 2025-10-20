/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.plugin.nc6.assist;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.nc6.BdeNc6PluginType;
import com.jiuqi.bde.plugin.nc6.assist.Nc6AssistPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class Nc6AssistProvider
implements IAssistProvider<Nc6AssistPojo> {
    @Autowired
    private BdeNc6PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;
    private static final String TB_GL_DOCFREE = "GL_DOCFREE%d";

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<Nc6AssistPojo> listAssist(String dataSourceCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT FREEMAP.DOCCODE        AS CODE,   \n");
        sql.append("       FREEMAP.DOCNAME        AS NAME,  \n");
        sql.append("       MDCLS.DEFAULTTABLENAME AS TABLENAME,  \n");
        sql.append("       FREEMAP.NUM            AS DOCFREETABLENUM  \n");
        sql.append("  FROM BD_ACCASSITEM ASSISTITEM  \n");
        sql.append("  JOIN FI_FREEMAP FREEMAP  \n");
        sql.append("    ON FREEMAP.PK_CHECKTYPE = ASSISTITEM.PK_ACCASSITEM  \n");
        sql.append("   AND FREEMAP.MOD = '00'  \n");
        sql.append("   AND ISDEF = 'N'  \n");
        sql.append("  JOIN MD_CLASS MDCLS  \n");
        sql.append("    ON MDCLS.ID = ASSISTITEM.CLASSID  \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append(" GROUP BY FREEMAP.DOCCODE,  \n");
        sql.append("          FREEMAP.DOCNAME,  \n");
        sql.append("          MDCLS.DEFAULTTABLENAME,  \n");
        sql.append("          FREEMAP.NUM  \n");
        sql.append(" ORDER BY FREEMAP.NUM  \n");
        List assistList = this.dataSourceService.query(dataSourceCode, sql.toString(), null, (RowMapper)new BeanPropertyRowMapper(Nc6AssistPojo.class));
        assistList.forEach(assist -> {
            assist.setDocFreeTableName(Nc6AssistProvider.getDocFreeNameByNum(assist.getDocFreeTableNum()));
            if ("bd_project".equals(assist.getTableName())) {
                assist.setTablePk("project_code");
                return;
            }
            if ("\u5ba2\u5546".equals(assist.getName()) || "\u5185\u90e8\u5ba2\u5546".equals(assist.getName())) {
                assist.setTablePk("pk_cust_sup");
            } else {
                assist.setTablePk("PK_" + assist.getTableName().substring(assist.getTableName().indexOf("_") + 1));
            }
        });
        return assistList;
    }

    private static String getDocFreeNameByNum(int num) {
        return String.format(TB_GL_DOCFREE, (Integer.valueOf(num) - 1) / 30 + 1);
    }
}

