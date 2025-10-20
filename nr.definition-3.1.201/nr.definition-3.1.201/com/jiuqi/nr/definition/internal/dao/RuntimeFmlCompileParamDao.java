/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.dao.DataFieldDO;
import com.jiuqi.nr.definition.internal.dao.DataFieldRowMapper;
import com.jiuqi.nr.definition.internal.dao.DataLinkDO;
import com.jiuqi.nr.definition.internal.dao.DataLinkRowMapper;
import com.jiuqi.nr.definition.internal.dao.FormDO;
import com.jiuqi.nr.definition.internal.dao.FormRowMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class RuntimeFmlCompileParamDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, List<DataLinkDefine>> getAllDataLinkInFormScheme(String formSchemeKey) {
        HashMap<String, List<DataLinkDefine>> result = new HashMap<String, List<DataLinkDefine>>();
        String sql = "SELECT T_DATALINK.DL_KEY,T_DATALINK.DL_UNIQUE_CODE,T_DATALINK.DL_REGION_KEY,T_DATALINK.DL_EXPRESSION,T_DATALINK.DL_FIELD_KEY,T_DATALINK.DL_POSX,T_DATALINK.DL_POSY,T_DATALINK.DL_COL_NUM,T_DATALINK.DL_ROW_NUM,T_DATALINK.DL_TYPE,T_FORM.FM_KEY FROM NR_PARAM_DATALINK T_DATALINK INNER JOIN NR_PARAM_DATAREGION T_REGION ON T_DATALINK.DL_REGION_KEY=T_REGION.DR_KEY INNER JOIN NR_PARAM_FORM T_FORM ON T_REGION.DR_FORM_KEY=T_FORM.FM_KEY WHERE T_FORM.FM_FORMSCHEME=? ";
        List query = this.jdbcTemplate.query(sql, (RowMapper)new DataLinkRowMapper(), new Object[]{formSchemeKey});
        Map<String, List<DataLinkDO>> collect = query.stream().collect(Collectors.groupingBy(DataLinkDO::getFormKey));
        for (Map.Entry<String, List<DataLinkDO>> entry : collect.entrySet()) {
            ArrayList<DataLinkDefine> list = new ArrayList<DataLinkDefine>();
            for (DataLinkDO dataLinkDO : entry.getValue()) {
                list.add(DataLinkDO.toDataLinkDefine(dataLinkDO));
            }
            result.put(entry.getKey(), list);
        }
        return result;
    }

    public List<FieldDefine> getAllDataFieldInFormScheme(String formSchemeKey) {
        String sql = "SELECT T_FIELD.DF_KEY,T_FIELD.DF_CODE,T_FIELD.DF_TITLE,T_FIELD.DF_DEFAULT,T_FIELD.DF_DT_KEY,T_FIELD.DF_REF_FIELD_ID,T_FIELD.DF_MEASUREUNIT,T_FIELD.DF_DATATYPE,T_FIELD.DF_APPLY_TYPE,T_FIELD.DF_AGGRTYPE,T_FIELD.DF_NULLABLE,T_FIELD.DF_REF_ENTITY_ID,T_DEPLOY.DS_FIELD_NAME,T_DEPLOY.DS_TABLE_NAME FROM NR_DATASCHEME_FIELD T_FIELD INNER JOIN NR_PARAM_DATALINK T_LINK ON T_FIELD.DF_KEY=T_LINK.DL_FIELD_KEY INNER JOIN NR_DATASCHEME_DEPLOY_INFO T_DEPLOY ON T_DEPLOY.DS_DF_KEY=T_LINK.DL_FIELD_KEY INNER JOIN NR_PARAM_DATAREGION T_REGION ON T_LINK.DL_REGION_KEY=T_REGION.DR_KEY INNER JOIN NR_PARAM_FORM T_FORM ON T_REGION.DR_FORM_KEY=T_FORM.FM_KEY WHERE T_FORM.FM_FORMSCHEME=? ";
        ArrayList<FieldDefine> result = new ArrayList<FieldDefine>();
        List query = this.jdbcTemplate.query(sql, (RowMapper)new DataFieldRowMapper(), new Object[]{formSchemeKey});
        for (DataFieldDO dataFieldDO : query) {
            result.add(DataFieldDO.toFieldDefine(dataFieldDO));
        }
        return result;
    }

    public List<FieldDefine> getAllZBKindDataFieldInFormScheme(String formSchemeKey) {
        String sql = "SELECT T_FIELD.DF_KEY,T_FIELD.DF_CODE,T_FIELD.DF_TITLE,T_FIELD.DF_DEFAULT,T_FIELD.DF_DT_KEY,T_FIELD.DF_REF_FIELD_ID,T_FIELD.DF_MEASUREUNIT,T_FIELD.DF_DATATYPE,T_FIELD.DF_APPLY_TYPE,T_FIELD.DF_AGGRTYPE,T_FIELD.DF_NULLABLE,T_FIELD.DF_REF_ENTITY_ID,T_DEPLOY.DS_FIELD_NAME,T_DEPLOY.DS_TABLE_NAME FROM NR_DATASCHEME_FIELD T_FIELD INNER JOIN NR_PARAM_DATALINK T_LINK ON T_FIELD.DF_KEY=T_LINK.DL_FIELD_KEY INNER JOIN NR_DATASCHEME_DEPLOY_INFO T_DEPLOY ON T_DEPLOY.DS_DF_KEY=T_LINK.DL_FIELD_KEY INNER JOIN NR_PARAM_DATAREGION T_REGION ON T_LINK.DL_REGION_KEY=T_REGION.DR_KEY INNER JOIN NR_PARAM_FORM T_FORM ON T_REGION.DR_FORM_KEY=T_FORM.FM_KEY WHERE T_FORM.FM_FORMSCHEME=? AND T_FIELD.DF_KIND=1 ";
        ArrayList<FieldDefine> result = new ArrayList<FieldDefine>();
        List query = this.jdbcTemplate.query(sql, (RowMapper)new DataFieldRowMapper(), new Object[]{formSchemeKey});
        for (DataFieldDO dataFieldDO : query) {
            result.add(DataFieldDO.toFieldDefine(dataFieldDO));
        }
        return result;
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

