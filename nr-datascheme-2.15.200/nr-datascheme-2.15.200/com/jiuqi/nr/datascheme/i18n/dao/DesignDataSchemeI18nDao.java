/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.PreparedStatementSetter
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.datascheme.i18n.dao;

import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.i18n.dao.AbstractDataSchemeI18nDao;
import com.jiuqi.nr.datascheme.i18n.dto.DesignDataFieldInfoDTO;
import com.jiuqi.nr.datascheme.i18n.dto.DesignDataSchemeI18nDTO;
import com.jiuqi.nr.datascheme.i18n.entity.DesignDataSchemeI18nDO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class DesignDataSchemeI18nDao
extends AbstractDataSchemeI18nDao<DesignDataSchemeI18nDO> {
    private String i18nQuerySql = String.format("SELECT DF.%s, DF.%s, DF.%s, DF.%s, DF.%s, DT.%s, DI.%s, DI.%s, DI.%s FROM %s", "DF_KEY", "DF_DS_KEY", "DF_CODE", "DF_TITLE", "DF_DESC", "DT_CODE", "DI_TYPE", "DI_TITLE", "DI_DESC", "NR_DATASCHEME_FIELD_DES");
    private RowMapper<DesignDataSchemeI18nDTO> i18nRowMapper = new RowMapper<DesignDataSchemeI18nDTO>(){

        public DesignDataSchemeI18nDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            DesignDataSchemeI18nDTO dto = new DesignDataSchemeI18nDTO();
            dto.setKey(rs.getString(1));
            dto.setDataSchemeKey(rs.getString(2));
            dto.setFieldCode(rs.getString(3));
            dto.setFieldTitle(rs.getString(4));
            dto.setFieldDesc(rs.getString(5));
            dto.setTableCode(rs.getString(6));
            dto.setType(rs.getString(7));
            dto.setTitle(rs.getString(8));
            dto.setDesc(rs.getString(9));
            return dto;
        }
    };

    @Override
    public Class<DesignDataSchemeI18nDO> getClz() {
        return DesignDataSchemeI18nDO.class;
    }

    public void update(final List<DesignDataSchemeI18nDO> dos) {
        if (null == dos || dos.isEmpty()) {
            return;
        }
        String sql = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=? ", "NR_DATASCHEME_I18N_DES", "DI_KEY", "DI_TYPE", "DI_TITLE", "DI_DESC", "DI_DS_KEY", "DI_KEY", "DI_TYPE");
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, ((DesignDataSchemeI18nDO)dos.get(i)).getKey());
                ps.setString(2, ((DesignDataSchemeI18nDO)dos.get(i)).getType());
                ps.setString(3, ((DesignDataSchemeI18nDO)dos.get(i)).getTitle());
                ps.setString(4, ((DesignDataSchemeI18nDO)dos.get(i)).getDesc());
                ps.setString(5, ((DesignDataSchemeI18nDO)dos.get(i)).getDataSchemeKey());
                ps.setString(6, ((DesignDataSchemeI18nDO)dos.get(i)).getKey());
                ps.setString(7, ((DesignDataSchemeI18nDO)dos.get(i)).getType());
            }

            public int getBatchSize() {
                return dos.size();
            }
        });
    }

    public void deleteBySchemeKey(String schemeKey) {
        super.deleteBy(new String[]{"DI_DS_KEY"}, new Object[]{schemeKey});
    }

    public void deleteByTableKey(String tableKey) {
        String sql = String.format("DELETE FROM %s WHERE %s IN (SELECT %s FROM %s WHERE %s=?) ", "NR_DATASCHEME_I18N_DES", "DI_KEY", "DF_KEY", "NR_DATASCHEME_FIELD_DES", "DF_DT_KEY");
        this.jdbcTemplate.update(sql, new Object[]{tableKey});
    }

    public void deleteByTableKey(String tableKey, String type) {
        String sql = String.format("DELETE FROM %s WHERE %s IN (SELECT %s FROM %s WHERE %s=?) AND %s=? ", "NR_DATASCHEME_I18N_DES", "DI_KEY", "DF_KEY", "NR_DATASCHEME_FIELD_DES", "DF_DT_KEY", "DI_TYPE");
        this.jdbcTemplate.update(sql, new Object[]{tableKey, type});
    }

    public void deleteByFieldKey(String fieldKey) {
        super.deleteBy(new String[]{"DI_KEY"}, new Object[]{fieldKey});
    }

    public void deleteByFieldKey(List<String> fieldKeys) {
        if (null == fieldKeys || fieldKeys.isEmpty()) {
            return;
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String key : fieldKeys) {
            batchArgs.add(new Object[]{key});
        }
        String sql = String.format("delete from %s where %s=? ", "NR_DATASCHEME_I18N_DES", "DI_KEY");
        this.jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    public void delete(List<DesignDataSchemeI18nDO> dos) {
        if (null == dos || dos.isEmpty()) {
            return;
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (DesignDataSchemeI18nDO d : dos) {
            batchArgs.add(new Object[]{d.getKey(), d.getType()});
        }
        String sql = String.format("delete from %s where %s=? and %s=? ", "NR_DATASCHEME_I18N_DES", "DI_TYPE", "DI_KEY");
        this.jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    public List<DesignDataSchemeI18nDTO> getBySchemeKey(final String schemeKey) {
        StringBuilder sql = new StringBuilder(this.i18nQuerySql);
        sql.append(" DF LEFT JOIN ").append("NR_DATASCHEME_TABLE_DES").append(" DT ON DF.").append("DF_DT_KEY").append("=DT.").append("DT_KEY").append(" LEFT JOIN ").append("NR_DATASCHEME_I18N_DES").append(" DI ON DF.").append("DF_KEY").append("=DI.").append("DI_KEY").append(" WHERE DF.").append("DF_DS_KEY").append("=? AND DF.").append("DF_KIND").append(" IN (?,?,?) ORDER BY DF.").append("DF_ORDER");
        return this.jdbcTemplate.query(sql.toString(), new PreparedStatementSetter(){

            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, schemeKey);
                ps.setInt(2, DataFieldKind.FIELD.getValue());
                ps.setInt(3, DataFieldKind.FIELD_ZB.getValue());
                ps.setInt(4, DataFieldKind.TABLE_FIELD_DIM.getValue());
            }
        }, this.i18nRowMapper);
    }

    public List<DesignDataSchemeI18nDTO> getByTableKey(final String tableKey, final String type) {
        StringBuilder sql = new StringBuilder(this.i18nQuerySql);
        sql.append(" DF LEFT JOIN ").append("NR_DATASCHEME_TABLE_DES").append(" DT ON DF.").append("DF_DT_KEY").append("=DT.").append("DT_KEY").append(" LEFT JOIN ").append("NR_DATASCHEME_I18N_DES").append(" DI ON DF.").append("DF_KEY").append("=DI.").append("DI_KEY").append(" AND DI.").append("DI_TYPE").append("=? ").append(" WHERE DF.").append("DF_DT_KEY").append("=? AND DF.").append("DF_KIND").append(" IN (?,?,?) ORDER BY DF.").append("DF_ORDER");
        return this.jdbcTemplate.query(sql.toString(), new PreparedStatementSetter(){

            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, type);
                ps.setString(2, tableKey);
                ps.setInt(3, DataFieldKind.FIELD.getValue());
                ps.setInt(4, DataFieldKind.FIELD_ZB.getValue());
                ps.setInt(5, DataFieldKind.TABLE_FIELD_DIM.getValue());
            }
        }, (RowMapper)new RowMapper<DesignDataSchemeI18nDTO>(){

            public DesignDataSchemeI18nDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                DesignDataSchemeI18nDTO dto = new DesignDataSchemeI18nDTO();
                dto.setKey(rs.getString(1));
                dto.setDataSchemeKey(rs.getString(2));
                dto.setFieldCode(rs.getString(3));
                dto.setFieldTitle(rs.getString(4));
                dto.setFieldDesc(rs.getString(5));
                dto.setTableCode(rs.getString(6));
                dto.setType(StringUtils.hasLength(rs.getString(7)) ? rs.getString(7) : type);
                dto.setTitle(rs.getString(8));
                dto.setDesc(rs.getString(9));
                return dto;
            }
        });
    }

    public List<DesignDataFieldInfoDTO> getFieldInfo(final String schemeKey) {
        String sql = String.format("SELECT DF.%s, DF.%s, DT.%s FROM %s DF LEFT JOIN %s DT ON DF.%s=DT.%s WHERE DF.%s IN (?, ?, ?) AND DF.%s=? ", "DF_KEY", "DF_CODE", "DT_CODE", "NR_DATASCHEME_FIELD_DES", "NR_DATASCHEME_TABLE_DES", "DF_DT_KEY", "DT_KEY", "DF_KIND", "DF_DS_KEY");
        return this.jdbcTemplate.query(sql, new PreparedStatementSetter(){

            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, DataFieldKind.FIELD.getValue());
                ps.setInt(2, DataFieldKind.FIELD_ZB.getValue());
                ps.setInt(3, DataFieldKind.TABLE_FIELD_DIM.getValue());
                ps.setString(4, schemeKey);
            }
        }, (RowMapper)new RowMapper<DesignDataFieldInfoDTO>(){

            public DesignDataFieldInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                DesignDataFieldInfoDTO dto = new DesignDataFieldInfoDTO();
                dto.setFieldKey(rs.getString(1));
                dto.setFieldCode(rs.getString(2));
                dto.setTableCode(rs.getString(3));
                return dto;
            }
        });
    }
}

