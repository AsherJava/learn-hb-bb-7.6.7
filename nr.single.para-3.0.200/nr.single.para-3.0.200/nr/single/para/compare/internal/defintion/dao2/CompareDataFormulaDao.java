/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package nr.single.para.compare.internal.defintion.dao2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Function;
import nr.single.para.compare.definition.CompareDataFormulaDTO;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.internal.defintion.dao.impl.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CompareDataFormulaDao {
    private static final String TABLE_NAME = "NR_SINGLE_COMPARE_FORMULA";
    private static final String KEY = "CD_KEY";
    private static final String INFOKEY = "CD_INFOKEY";
    private static final String DATATYPE = "CD_DATATYPE";
    private static final String SINGLECODE = "CD_SINGLECODE";
    private static final String SINGLETITLE = "CD_SINGLETITLE";
    private static final String SINGLEDATA = "CD_SINGLEDATA";
    private static final String MATCHKEY = "CD_MATCHKEY";
    private static final String NETKEY = "CD_NETKEY";
    private static final String NETCODE = "CD_NETCODE";
    private static final String NETTITLE = "CD_NETTITLE";
    private static final String NETDATA = "CD_NETDATA";
    private static final String CHANGETYPE = "CD_CHANGETYPE";
    private static final String UPDATETYPE = "CD_UPDATETYPE";
    private static final String FMLSCHEME_COMPAREKEY = "CD_SCHEME_COMPAREKEY";
    private static final String FMLFORM_COMPAREKEY = "CD_FORM_COMPAREKEY";
    private static final String MESSAGE = "CD_MESSAGE";
    private static final String ORDER = "CD_ORDER";
    private static final String UPDATETIME = "CD_UPDATETIME";
    private static final String ALL_FIELD = "CD_INFOKEY,CD_DATATYPE,CD_SINGLECODE,CD_SINGLETITLE,CD_SINGLEDATA,CD_MATCHKEY,CD_NETKEY,CD_NETCODE,CD_NETTITLE,CD_NETDATA,CD_CHANGETYPE,CD_UPDATETYPE,CD_SCHEME_COMPAREKEY,CD_FORM_COMPAREKEY,CD_MESSAGE,CD_ORDER,CD_UPDATETIME,CD_KEY";
    private static final Function<ResultSet, CompareDataFormulaDTO> ENTITY = rs -> {
        CompareDataFormulaDTO bean = new CompareDataFormulaDTO();
        int index = 1;
        try {
            Object updateType;
            bean.setInfoKey(rs.getString(index++));
            Object datatype = TransUtil.parseInteger(rs.getObject(index++));
            if (datatype != null) {
                bean.setDataType(CompareDataType.valueOf(Integer.parseInt(datatype.toString())));
            }
            bean.setSingleCode(rs.getString(index++));
            bean.setSingleTitle(rs.getString(index++));
            bean.setSingleData(rs.getString(index++));
            bean.setMatchKey(rs.getString(index++));
            bean.setNetKey(rs.getString(index++));
            bean.setNetCode(rs.getString(index++));
            bean.setNetTitle(rs.getString(index++));
            bean.setNetData(rs.getString(index++));
            Object changeType = TransUtil.parseInteger(rs.getObject(index++));
            if (datatype != null) {
                bean.setChangeType(CompareChangeType.valueOf(Integer.parseInt(changeType.toString())));
            }
            if ((updateType = TransUtil.parseInteger(rs.getObject(index++))) != null) {
                bean.setUpdateType(CompareUpdateType.valueOf(Integer.parseInt(updateType.toString())));
            }
            bean.setFmlSchemeCompareKey(rs.getString(index++));
            bean.setFmlFormCompareKey(rs.getString(index++));
            bean.setFmlSchemeCompareKey(rs.getString(index++));
            bean.setOrder(rs.getString(index++));
            Timestamp updateTime = rs.getTimestamp(index++);
            bean.setUpdateTime(updateTime != null ? updateTime.toInstant() : null);
            bean.setKey(rs.getString(index++));
        }
        catch (SQLException e) {
            throw new RuntimeException("Read MidstoreOrgData Error.", e);
        }
        return bean;
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CompareDataFormulaDTO> getByScheme(String infoKey, String fmlSchemeCompareKey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?", ALL_FIELD, TABLE_NAME, INFOKEY, FMLSCHEME_COMPAREKEY);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY.apply(rs), new Object[]{infoKey, fmlSchemeCompareKey});
    }

    @Transactional
    public void batchAdd(List<CompareDataFormulaDTO> beans) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ?, ?, ?, ? ?, ?, ?, ?)", TABLE_NAME, ALL_FIELD);
        this.batchSQL(beans, SQL_INSERT);
    }

    @Transactional
    public void batchUpdate(List<CompareDataFormulaDTO> sources) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", TABLE_NAME, INFOKEY, DATATYPE, SINGLECODE, SINGLETITLE, SINGLEDATA, MATCHKEY, NETKEY, NETCODE, NETTITLE, NETDATA, CHANGETYPE, UPDATETYPE, FMLSCHEME_COMPAREKEY, FMLFORM_COMPAREKEY, MESSAGE, ORDER, UPDATETIME, KEY);
        this.batchSQL(sources, SQL_UPDATE);
    }

    @Transactional
    public void batchDelete(List<String> targets) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, KEY);
        int batchSize = 500;
        ArrayList<Object[]> args = new ArrayList<Object[]>(Math.min(targets.size(), batchSize));
        for (int i = 0; i < targets.size(); ++i) {
            String r = targets.get(i);
            args.add(new Object[]{r});
            if (i <= 0 || i % batchSize != 0) continue;
            this.jdbcTemplate.batchUpdate(SQL_DELETE, args);
            args.clear();
        }
        if (!args.isEmpty()) {
            this.jdbcTemplate.batchUpdate(SQL_DELETE, args);
        }
    }

    private void batchSQL(List<CompareDataFormulaDTO> beans, String SQL_INSERT) {
        int batchSize = 500;
        ArrayList<Object[]> args = new ArrayList<Object[]>(Math.min(beans.size(), batchSize));
        for (int i = 0; i < beans.size(); ++i) {
            CompareDataFormulaDTO bean = beans.get(i);
            args.add(new Object[]{bean.getInfoKey(), bean.getDataType().getValue(), bean.getSingleCode(), bean.getSingleTitle(), bean.getSingleData(), bean.getNetKey(), bean.getNetCode(), bean.getNetTitle(), bean.getNetData(), bean.getChangeType() != null ? Integer.valueOf(bean.getChangeType().getValue()) : null, bean.getUpdateType() != null ? Integer.valueOf(bean.getUpdateType().getValue()) : null, bean.getFmlSchemeCompareKey(), bean.getFmlFormCompareKey(), bean.getMessage(), bean.getOrder(), bean.getUpdateTime() != null ? Timestamp.from(bean.getUpdateTime()) : null, bean.getKey()});
            if (i <= 0 || i % batchSize != 0) continue;
            this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
            args.clear();
        }
        if (!args.isEmpty()) {
            this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
        }
    }

    public Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis() + (long)TimeZone.getDefault().getOffset(System.currentTimeMillis()));
    }
}

