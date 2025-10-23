/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowCallbackHandler
 */
package com.jiuqi.nr.mapping2.dao;

import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class FormulaMappingDao {
    private static final String TABLENAME = "NVWA_MAPPING_NR_FORMULA";
    private static final String KEY = "MF_KEY";
    private static final String MAPPINGSCHEME = "MF_MAPPINGSCHEME";
    private static final String FORMULASCHEME = "MF_FORMULASCHEME";
    private static final String FORMCODE = "MF_FORM_CODE";
    private static final String FORMULACODE = "MF_FORMULA_CODE";
    private static final String MFORMULASCHEME = "MF_MAPPING_FORMULASCHEME";
    private static final String MFORMULACODE = "MF_MAPPING_FORMULACODE";
    private static final String MFORMULAWILDCARD = "MF_MAPPING_WILDCARD";
    private static final String FORMULA_MAPPING;
    private static final Function<ResultSet, FormulaMapping> ENTITY_READER_FORMULA_MAPPING;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<FormulaMapping> findByMSFormulaForm(String mskey, String formulaScheme, String formCode) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ? AND %s = ?", FORMULA_MAPPING, TABLENAME, MAPPINGSCHEME, FORMULASCHEME, FORMCODE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_FORMULA_MAPPING.apply(rs), new Object[]{mskey, formulaScheme, formCode});
    }

    public Map<String, List<FormulaMapping>> findByMSFormula(String mskey, String formulaScheme) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?", FORMULA_MAPPING, TABLENAME, MAPPINGSCHEME, FORMULASCHEME);
        final HashMap<String, List<FormulaMapping>> res = new HashMap<String, List<FormulaMapping>>();
        this.jdbcTemplate.query(SQL_QUERY, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                FormulaMapping formulaMapping = (FormulaMapping)ENTITY_READER_FORMULA_MAPPING.apply(rs);
                ArrayList<FormulaMapping> mappings = (ArrayList<FormulaMapping>)res.get(formulaMapping.getFormCode());
                if (mappings == null) {
                    mappings = new ArrayList<FormulaMapping>();
                    res.put(formulaMapping.getFormCode(), mappings);
                }
                mappings.add(formulaMapping);
            }
        }, new Object[]{mskey, formulaScheme});
        return res;
    }

    public List<FormulaMapping> findByMS(String mskey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", FORMULA_MAPPING, TABLENAME, MAPPINGSCHEME);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_FORMULA_MAPPING.apply(rs), new Object[]{mskey});
    }

    public void add(FormulaMapping formulaMapping) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", TABLENAME, FORMULA_MAPPING);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{formulaMapping.getKey(), formulaMapping.getMappingScheme(), formulaMapping.getFormulaScheme(), formulaMapping.getFormCode(), formulaMapping.getFormulaCode(), formulaMapping.getmFormulaScheme(), formulaMapping.getmFormulaCode(), formulaMapping.getmFormulaWildcard()});
    }

    public void batchAdd(List<FormulaMapping> fms) {
        if (CollectionUtils.isEmpty(fms)) {
            return;
        }
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", TABLENAME, FORMULA_MAPPING);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (FormulaMapping fm : fms) {
            Object[] param = new Object[]{fm.getKey(), fm.getMappingScheme(), fm.getFormulaScheme(), fm.getFormCode(), fm.getFormulaCode(), fm.getmFormulaScheme(), fm.getmFormulaCode(), fm.getmFormulaWildcard()};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
    }

    public void deleteByMS(String msKey) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLENAME, MAPPINGSCHEME);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{msKey});
    }

    public void deleteByMsFsFc(String msKey, String fsKey, String formCode) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ? AND %s = ? AND %s = ?", TABLENAME, MAPPINGSCHEME, FORMULASCHEME, FORMCODE);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{msKey, fsKey, formCode});
    }

    public void batchDeleteByMS(List<String> keys) {
        String SQL_BATCH_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLENAME, MAPPINGSCHEME);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (String key : keys) {
            Object[] param = new Object[]{key};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_BATCH_DELETE, args);
    }

    static {
        StringBuilder builder = new StringBuilder();
        FORMULA_MAPPING = builder.append(KEY).append(",").append(MAPPINGSCHEME).append(",").append(FORMULASCHEME).append(",").append(FORMCODE).append(",").append(FORMULACODE).append(",").append(MFORMULASCHEME).append(",").append(MFORMULACODE).append(",").append(MFORMULAWILDCARD).toString();
        ENTITY_READER_FORMULA_MAPPING = rs -> {
            FormulaMapping formulaMapping = new FormulaMapping();
            int index = 1;
            try {
                formulaMapping.setKey(rs.getString(index++));
                formulaMapping.setMappingScheme(rs.getString(index++));
                formulaMapping.setFormulaScheme(rs.getString(index++));
                formulaMapping.setFormCode(rs.getString(index++));
                formulaMapping.setFormulaCode(rs.getString(index++));
                formulaMapping.setmFormulaScheme(rs.getString(index++));
                formulaMapping.setmFormulaCode(rs.getString(index++));
                formulaMapping.setmFormulaWildcard(rs.getString(index));
            }
            catch (SQLException e) {
                throw new RuntimeException("read FormulaMapping error.", e);
            }
            return formulaMapping;
        };
    }
}

