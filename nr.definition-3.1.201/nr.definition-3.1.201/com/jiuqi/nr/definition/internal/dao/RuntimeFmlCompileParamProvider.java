/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.dao.DataLinkDO;
import com.jiuqi.nr.definition.internal.dao.DataLinkRowMapper;
import com.jiuqi.nr.definition.internal.dao.FormDO;
import com.jiuqi.nr.definition.internal.dao.FormRowMapper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class RuntimeFmlCompileParamProvider {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DesignDataModelService dataModelService;

    public List<ColumnModelDefine> getAllZBColumnModelInScheme(String dataSchemeKey) {
        String sql = "SELECT C.FIELD_ID,C.TABLE_ID,C.FIELD_CODE,C.FIELD_NAME,C.FIELD_DATATYPE,C.FIELD_REF_TABLE_ID,C.FIELD_REF_FIELD_ID FROM NR_DATASCHEME_DEPLOY_INFO T LEFT JOIN NVWA_COLUMNMODEL C ON T.DS_CM_KEY=C.FIELD_ID LEFT JOIN NR_DATASCHEME_FIELD F ON T.DS_DF_KEY = F.DF_KEY WHERE T.DS_DS_KEY = ? AND F.DF_DS_KEY=? AND F.DF_KIND = 1";
        return this.jdbcTemplate.query(sql, this.getColumnRowMapper(), new Object[]{dataSchemeKey, dataSchemeKey});
    }

    public Map<String, ColumnModelDefine> getAllDataFieldInFormScheme(String dataSchemeKey) {
        String sql = "SELECT T.DS_DF_KEY,C.FIELD_ID,C.TABLE_ID,C.FIELD_CODE,C.FIELD_NAME,C.FIELD_DATATYPE,C.FIELD_REF_TABLE_ID,C.FIELD_REF_FIELD_ID FROM NR_DATASCHEME_DEPLOY_INFO T LEFT JOIN NVWA_COLUMNMODEL C ON T.DS_CM_KEY=C.FIELD_ID WHERE T.DS_DS_KEY = ? ";
        HashMap<String, ColumnModelDefine> map = new HashMap<String, ColumnModelDefine>();
        this.jdbcTemplate.query(sql, this.getColumnRowMapper(map), new Object[]{dataSchemeKey});
        return map;
    }

    public List<ColumnModelDefine> getColumnModelsInTable(String tableID) {
        String sql = "SELECT C.FIELD_ID,C.TABLE_ID,C.FIELD_CODE,C.FIELD_NAME,C.FIELD_DATATYPE,C.FIELD_REF_TABLE_ID,C.FIELD_REF_FIELD_ID FROM NVWA_COLUMNMODEL C WHERE C.TABLE_ID = ?";
        return this.jdbcTemplate.query(sql, this.getColumnRowMapper(), new Object[]{tableID});
    }

    public Map<String, List<DataLinkDefine>> getAllDataLinkInFormScheme(String formSchemeKey) {
        HashMap<String, List<DataLinkDefine>> result = new HashMap<String, List<DataLinkDefine>>();
        String sql = "SELECT T_DATALINK.DL_KEY,T_DATALINK.DL_UNIQUE_CODE,T_DATALINK.DL_REGION_KEY,T_DATALINK.DL_EXPRESSION,T_DATALINK.DL_FIELD_KEY,T_DATALINK.DL_POSX,T_DATALINK.DL_POSY,T_DATALINK.DL_COL_NUM,T_DATALINK.DL_ROW_NUM,T_DATALINK.DL_TYPE,T_FORM.FM_KEY FROM NR_PARAM_DATALINK T_DATALINK INNER JOIN NR_PARAM_DATAREGION T_REGION ON T_DATALINK.DL_REGION_KEY=T_REGION.DR_KEY INNER JOIN NR_PARAM_FORM T_FORM ON T_REGION.DR_FORM_KEY=T_FORM.FM_KEY WHERE T_FORM.FM_FORMSCHEME=? ";
        List query = this.jdbcTemplate.query(sql, (RowMapper)new DataLinkRowMapper(), new Object[]{formSchemeKey});
        Map<String, List<DataLinkDO>> collect = query.stream().filter(o -> StringUtils.hasText(o.getFormKey())).collect(Collectors.groupingBy(DataLinkDO::getFormKey));
        for (Map.Entry<String, List<DataLinkDO>> entry : collect.entrySet()) {
            ArrayList<DataLinkDefine> list = new ArrayList<DataLinkDefine>();
            for (DataLinkDO dataLinkDO : entry.getValue()) {
                list.add(DataLinkDO.toDataLinkDefine(dataLinkDO));
            }
            result.put(entry.getKey(), list);
        }
        return result;
    }

    private RowMapper<ColumnModelDefine> getColumnRowMapper() {
        RowMapper columnModelDefineRowMapper = (rs, rowNum) -> {
            DesignColumnModelDefine columnModelDefine = this.dataModelService.createColumnModelDefine();
            columnModelDefine.setID(rs.getString("FIELD_ID"));
            columnModelDefine.setCode(rs.getString("FIELD_CODE"));
            columnModelDefine.setName(rs.getString("FIELD_NAME"));
            columnModelDefine.setColumnType(ColumnModelType.forValue((int)rs.getInt("FIELD_DATATYPE")));
            columnModelDefine.setTableID(rs.getString("TABLE_ID"));
            columnModelDefine.setReferTableID(rs.getString("FIELD_REF_TABLE_ID"));
            columnModelDefine.setReferColumnID(rs.getString("FIELD_REF_FIELD_ID"));
            return columnModelDefine;
        };
        return columnModelDefineRowMapper;
    }

    private RowMapper<ColumnModelDefine> getColumnRowMapper(Map<String, ColumnModelDefine> map) {
        return (rs, rowNum) -> {
            DesignColumnModelDefine columnModelDefine = this.dataModelService.createColumnModelDefine();
            columnModelDefine.setID(rs.getString("FIELD_ID"));
            columnModelDefine.setCode(rs.getString("FIELD_CODE"));
            columnModelDefine.setName(rs.getString("FIELD_NAME"));
            columnModelDefine.setColumnType(ColumnModelType.forValue((int)rs.getInt("FIELD_DATATYPE")));
            columnModelDefine.setTableID(rs.getString("TABLE_ID"));
            columnModelDefine.setReferTableID(rs.getString("FIELD_REF_TABLE_ID"));
            columnModelDefine.setReferColumnID(rs.getString("FIELD_REF_FIELD_ID"));
            map.put(rs.getString("DS_DF_KEY"), (ColumnModelDefine)columnModelDefine);
            return columnModelDefine;
        };
    }

    public List<FormDefine> queryFormInScheme(List<String> formSchemeKeys) {
        ArrayList<FormDefine> result = new ArrayList<FormDefine>();
        for (String formSchemeKey : formSchemeKeys) {
            String sql = "SELECT FM_KEY,FM_CODE,FM_TITLE,FM_TYPE FROM NR_PARAM_FORM WHERE FM_FORMSCHEME=? ";
            List query = this.jdbcTemplate.query(sql, (RowMapper)new FormRowMapper(), new Object[]{formSchemeKey});
            if (CollectionUtils.isEmpty(query)) continue;
            for (FormDO formDO : query) {
                result.add(FormDO.toFormDefine(formDO, formSchemeKey));
            }
        }
        return result;
    }
}

