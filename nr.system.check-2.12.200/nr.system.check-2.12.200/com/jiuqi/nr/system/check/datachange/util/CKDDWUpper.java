/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.internal.obj.EntityData
 *  com.jiuqi.nr.data.logic.internal.util.CheckResultUtil
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.system.check.datachange.util;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.system.check.common.DBUtils;
import com.jiuqi.nr.system.check.datachange.bean.DataChangeRecord;
import com.jiuqi.nr.system.check.datachange.util.FetchReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

public class CKDDWUpper
extends FetchReader {
    public CKDDWUpper(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<DataChangeRecord> exec(String tableName, EntityData dwEntity, EntityData periodEntity, List<EntityData> dimEntities) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append("MDCODE").append(",").append("PERIOD");
        sql.append(",").append("CKD_RECID");
        sql.append(",").append("CKD_FORMULASCHEMEKEY");
        sql.append(",").append("CKD_FORMKEY");
        sql.append(",").append("CKD_FORMULACODE");
        sql.append(",").append("CKD_GLOBROW");
        sql.append(",").append("CKD_GLOBCOL");
        sql.append(",").append("CKD_DIMSTR");
        if (dimEntities != null && !dimEntities.isEmpty()) {
            for (EntityData dimEntity : dimEntities) {
                sql.append(",").append(dimEntity.getDimensionName());
            }
        }
        sql.append(" FROM ").append(tableName);
        sql.append(" WHERE ").append(DBUtils.buildCondition("MDCODE"));
        return (List)this.query(sql.toString(), null, rs -> {
            ArrayList<DataChangeRecord> records = new ArrayList<DataChangeRecord>();
            ArrayList<CKDObj> ckdObjs = new ArrayList<CKDObj>();
            while (rs.next()) {
                ckdObjs.add(this.convert(rs, dwEntity, periodEntity, dimEntities));
                if (ckdObjs.size() != 1000) continue;
                records.addAll(this.update(tableName, ckdObjs));
                ckdObjs.clear();
            }
            if (!ckdObjs.isEmpty()) {
                records.addAll(this.update(tableName, ckdObjs));
                ckdObjs.clear();
            }
            return records;
        });
    }

    private List<DataChangeRecord> update(String tableName, final List<CKDObj> ckdObjs) {
        final ArrayList<DataChangeRecord> records = new ArrayList<DataChangeRecord>();
        String sql = "UPDATE " + tableName + " SET " + "MDCODE" + "=?," + "CKD_RECID" + "=? WHERE " + "CKD_RECID" + "=?";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CKDObj ckdObj = (CKDObj)ckdObjs.get(i);
                ps.setString(1, ckdObj.getNewMdCode());
                String newRecid = CheckResultUtil.buildRECID((String)ckdObj.getFormulaSchemeKey(), (String)ckdObj.getFormKey(), (String)ckdObj.getFormulaKey(), (int)ckdObj.getGlobRow(), (int)ckdObj.getGlobCol(), ckdObj.getDimensionSet());
                ps.setString(2, newRecid);
                ps.setString(3, ckdObj.getOldRecid());
                DataChangeRecord dataChangeRecord = new DataChangeRecord();
                dataChangeRecord.setId(UUID.randomUUID().toString());
                dataChangeRecord.setPeriod(ckdObj.getPeriod());
                dataChangeRecord.setOldUnitCode(ckdObj.getOldMdCode());
                dataChangeRecord.setNewUnitCode(ckdObj.getNewMdCode());
                dataChangeRecord.setRecordType("ckdData");
                records.add(dataChangeRecord);
            }

            public int getBatchSize() {
                return ckdObjs.size();
            }
        });
        return records;
    }

    private CKDObj convert(ResultSet rs, EntityData dwEntity, EntityData periodEntity, List<EntityData> dimEntities) throws SQLException {
        CKDObj ckdObj = new CKDObj();
        String oldMdCode = rs.getString(1);
        ckdObj.setOldMdCode(oldMdCode);
        ckdObj.setNewMdCode(oldMdCode.toUpperCase(Locale.ROOT));
        ckdObj.setPeriod(rs.getString(2));
        ckdObj.setOldRecid(rs.getString(3));
        ckdObj.setFormulaSchemeKey(rs.getString(4));
        ckdObj.setFormKey(rs.getString(5));
        ckdObj.setFormulaKey(rs.getString(6).substring(0, 36));
        ckdObj.setGlobRow(rs.getInt(7));
        ckdObj.setGlobCol(rs.getInt(8));
        String dimStr = rs.getString(9);
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        ckdObj.setDimensionSet(dimensionSet);
        DimensionValue dw = new DimensionValue();
        dw.setName(dwEntity.getDimensionName());
        dw.setValue(ckdObj.getNewMdCode());
        dimensionSet.put(dwEntity.getDimensionName(), dw);
        DimensionValue period = new DimensionValue();
        period.setName(periodEntity.getDimensionName());
        period.setValue(ckdObj.getPeriod());
        dimensionSet.put(periodEntity.getDimensionName(), period);
        if (dimEntities != null && !dimEntities.isEmpty()) {
            for (int i = 0; i < dimEntities.size(); ++i) {
                EntityData entityData = dimEntities.get(i);
                DimensionValue dim = new DimensionValue();
                dim.setName(entityData.getDimensionName());
                dim.setValue(rs.getString(10 + i));
                dimensionSet.put(entityData.getDimensionName(), dim);
            }
        }
        if (StringUtils.hasText(dimStr)) {
            String[] dims;
            for (String dim : dims = dimStr.split(";")) {
                String[] dimValues = dim.split(":");
                if (dimValues.length != 2) continue;
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dimValues[0]);
                dimensionValue.setValue(dimValues[1]);
                dimensionSet.put(dimValues[0], dimensionValue);
            }
        }
        return ckdObj;
    }

    private static class CKDObj {
        private String formulaSchemeKey;
        private String formKey;
        private String formulaKey;
        private int globRow;
        private int globCol;
        private String oldMdCode;
        private String newMdCode;
        private String period;
        private Map<String, DimensionValue> dimensionSet;
        private String oldRecid;

        private CKDObj() {
        }

        public String getOldRecid() {
            return this.oldRecid;
        }

        public void setOldRecid(String oldRecid) {
            this.oldRecid = oldRecid;
        }

        public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
            this.dimensionSet = dimensionSet;
        }

        public void setFormKey(String formKey) {
            this.formKey = formKey;
        }

        public void setFormulaKey(String formulaKey) {
            this.formulaKey = formulaKey;
        }

        public void setFormulaSchemeKey(String formulaSchemeKey) {
            this.formulaSchemeKey = formulaSchemeKey;
        }

        public void setGlobCol(int globCol) {
            this.globCol = globCol;
        }

        public void setGlobRow(int globRow) {
            this.globRow = globRow;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public Map<String, DimensionValue> getDimensionSet() {
            return this.dimensionSet;
        }

        public String getFormKey() {
            return this.formKey;
        }

        public String getFormulaKey() {
            return this.formulaKey;
        }

        public String getFormulaSchemeKey() {
            return this.formulaSchemeKey;
        }

        public int getGlobCol() {
            return this.globCol;
        }

        public int getGlobRow() {
            return this.globRow;
        }

        public String getPeriod() {
            return this.period;
        }

        public String getNewMdCode() {
            return this.newMdCode;
        }

        public void setNewMdCode(String newMdCode) {
            this.newMdCode = newMdCode;
        }

        public String getOldMdCode() {
            return this.oldMdCode;
        }

        public void setOldMdCode(String oldMdCode) {
            this.oldMdCode = oldMdCode;
        }
    }
}

