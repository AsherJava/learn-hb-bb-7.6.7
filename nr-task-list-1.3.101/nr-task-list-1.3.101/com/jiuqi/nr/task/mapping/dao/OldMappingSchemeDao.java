/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.task.mapping.dao;

import com.jiuqi.nr.task.mapping.dto.MappingSchemeDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OldMappingSchemeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<MappingSchemeDTO> query(String formSchemeKey) {
        String sql = "SELECT S_ID, S_SCHEME, S_CONFIGNAME,S_ORDER FROM SYS_SINGLEMAPPING WHERE S_SCHEME = ?";
        Object[] arg = new Object[]{formSchemeKey};
        return (List)this.jdbcTemplate.query(sql, arg, rs -> {
            ArrayList<MappingSchemeDTO> list = new ArrayList<MappingSchemeDTO>();
            while (rs.next()) {
                MappingSchemeDTO dto = new MappingSchemeDTO();
                dto.setKey(rs.getString(1));
                dto.setSchemeKey(rs.getString(2));
                dto.setTitle(rs.getString(3));
                dto.setOrder(rs.getString(4));
                list.add(dto);
            }
            return list;
        });
    }

    public MappingSchemeDTO get(String key) {
        String sql = "SELECT S_ID, S_SCHEME, S_CONFIGNAME,S_ORDER FROM SYS_SINGLEMAPPING WHERE S_ID = ?";
        Object[] arg = new Object[]{key};
        return (MappingSchemeDTO)this.jdbcTemplate.query(sql, arg, rs -> {
            MappingSchemeDTO dto = null;
            while (rs.next()) {
                dto = new MappingSchemeDTO();
                dto.setKey(rs.getString(1));
                dto.setSchemeKey(rs.getString(2));
                dto.setTitle(rs.getString(3));
                dto.setOrder(rs.getString(4));
            }
            return dto;
        });
    }

    public void del(String key) {
        String sql = "DELETE FROM SYS_SINGLEMAPPING WHERE S_ID = ?";
        this.jdbcTemplate.update(sql, new Object[]{key});
    }
}

