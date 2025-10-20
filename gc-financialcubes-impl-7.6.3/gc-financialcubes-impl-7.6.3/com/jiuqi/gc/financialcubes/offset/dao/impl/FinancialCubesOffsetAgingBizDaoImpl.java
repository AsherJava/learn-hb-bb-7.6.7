/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesAuditTrailEnum
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 */
package com.jiuqi.gc.financialcubes.offset.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesAuditTrailEnum;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.gc.financialcubes.offset.dao.FinancialCubesOffsetAgingBizDao;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesOffsetAgingBizDaoImpl
extends BaseDataCenterDaoImpl
implements FinancialCubesOffsetAgingBizDao {
    private static final String AGING_CATEGORY = "GENERAL_AGING";
    private static final String TABLE_NAME_TEMP_CODE = "DC_TEMP_CODE";

    @Override
    public int insertTempTable(List<Object[]> offsetData, Set<String> dimensionCodeSet) {
        if (offsetData == null || offsetData.isEmpty()) {
            return 0;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO %1$s \n ");
        sql.append("(ID,UNITCODE,OPPUNITCODE,MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL, \n");
        sql.append(" SUBJECTCODE,AGINGRANGE,CF %2$s )\n");
        StringBuilder fieldStr = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                fieldStr.append(",").append(assistDimCode).append(" ");
            }
        }
        String formatSql = String.format(sql.toString(), "GC_FINCUBES_AGING_TEMP", fieldStr);
        int fixedFieldCount = 9;
        int fieldCount = 9 + dimensionCodeSet.size();
        StringBuilder placeholder = new StringBuilder();
        for (int i = 0; i < fieldCount; ++i) {
            placeholder.append("?");
            if (i >= fieldCount - 1) continue;
            placeholder.append(",");
        }
        placeholder = new StringBuilder(" VALUES (" + placeholder + ")");
        int[] counts = this.batchUpdate(formatSql = formatSql + placeholder, offsetData);
        if (counts == null || counts.length == 0) {
            return 0;
        }
        return Arrays.stream(counts).sum();
    }

    @Override
    public int insertNotExistsDataByTemp(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO %1$s \n ");
        sql.append("(ID,SN,DATATIME,MDCODE,UNITCODE,OPPUNITCODE,MD_GCORGTYPE,MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL, \n");
        sql.append(" SUBJECTCODE,AGINGCATEGORY,AGINGRANGE,CF %2$s )\n");
        sql.append(" SELECT ##UUIDFUNCTION## AS ID, ? ,?,?,TEMP.UNITCODE,TEMP.OPPUNITCODE,?,TEMP.MD_CURRENCY,\n");
        sql.append("        TEMP.ORGNCURRENCY,TEMP.MD_AUDITTRAIL, TEMP.SUBJECTCODE, ?, TEMP.AGINGRANGE, TEMP.CF \n");
        sql.append("        %3$s \n");
        sql.append("  FROM ").append("GC_FINCUBES_UPDATE_AGING_TEMP").append(" TEMP \n");
        sql.append(" WHERE 1 = 1 AND TEMP.MD_AUDITTRAIL LIKE ? \n");
        StringBuilder insertFields = new StringBuilder();
        StringBuilder valueFields = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                valueFields.append(", TEMP.").append(assistDimCode).append(" ");
                insertFields.append(", ").append(assistDimCode);
            }
        }
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(offsetTaskDto.getBatchNum());
        args.add(offsetTaskDto.getDataTime());
        args.add(offsetTaskDto.getDiffUnitId());
        args.add(offsetTaskDto.getOrgType());
        args.add(AGING_CATEGORY);
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        String formatSql = String.format(sql.toString(), FinancialCubesCommonUtil.getFinancialCubesAgingTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), insertFields, valueFields);
        IDbSqlHandler dbSqlHandler = OuterDataSourceUtils.getJdbcTemplate().getIDbSqlHandler();
        formatSql = formatSql.replace("##UUIDFUNCTION##", dbSqlHandler.newUUID());
        return this.update(formatSql, args.toArray());
    }

    @Override
    public int deleteOffsetByTemp(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE %1$s F \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND F.DATATIME = ? \n");
        sql.append("   ##UNITWHERESQL##  \n");
        sql.append("   AND F.MDCODE = ?\n");
        sql.append("   AND F.MD_AUDITTRAIL like ? \n");
        sql.append("   AND F.MD_GCORGTYPE = ? \n");
        sql.append("   AND NOT EXISTS (SELECT 1 FROM %2$s TEMP \n");
        sql.append("                    WHERE 1 = 1 \n");
        sql.append("                      AND TEMP.MD_AUDITTRAIL like ? \n");
        sql.append("                      AND TEMP.UNITCODE = F.UNITCODE AND TEMP.OPPUNITCODE = F.OPPUNITCODE \n");
        sql.append("                      AND TEMP.MD_CURRENCY = F.MD_CURRENCY AND TEMP.ORGNCURRENCY = F.ORGNCURRENCY\n");
        sql.append("                      AND TEMP.SUBJECTCODE = F.SUBJECTCODE AND TEMP.AGINGRANGE = F.AGINGRANGE   \n");
        sql.append("                      %3$s) \n");
        sql.append("   %4$s \n");
        StringBuilder existsFieldsEqualsSql = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                existsFieldsEqualsSql.append(" AND TEMP.").append(assistDimCode).append(" = F.").append(assistDimCode).append(" \n");
            }
        }
        ArrayList<String> args = new ArrayList<String>();
        args.add(offsetTaskDto.getDataTime());
        String unitWhereSql = "";
        if (!offsetTaskDto.isRebuildFlag()) {
            unitWhereSql = "  AND F.UNITCODE IN (?,?) AND F.OPPUNITCODE IN (?,?) \n";
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
        }
        args.add(offsetTaskDto.getDiffUnitId());
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        args.add(offsetTaskDto.getOrgType());
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        StringBuilder subjectSql = new StringBuilder();
        if (!CollectionUtils.isEmpty(offsetTaskDto.getSubjectCodes())) {
            IDbSqlHandler dbSqlHandler = OuterDataSourceUtils.getJdbcTemplate().getIDbSqlHandler();
            subjectSql.append(String.format("AND EXISTS (SELECT 1 FROM %s SUBJECT WHERE F.SUBJECTCODE LIKE ", TABLE_NAME_TEMP_CODE)).append(dbSqlHandler.concat(new String[]{"SUBJECT.CODE", "'%%')"}));
        }
        String formatSql = String.format(sql.toString(), FinancialCubesCommonUtil.getFinancialCubesAgingTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), "GC_FINCUBES_AGING_TEMP", existsFieldsEqualsSql, subjectSql);
        formatSql = formatSql.replace("##UNITWHERESQL## ", unitWhereSql);
        return this.update(formatSql, args.toArray());
    }

    @Override
    public int updateFinancialCubes(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE %1$s F \n");
        sql.append("   SET (SN,CF) = \n");
        sql.append("       (SELECT ?,TEMP.CF \n");
        sql.append("          FROM %2$s TEMP \n");
        sql.append("         WHERE 1 = 1 \n");
        sql.append("           AND TEMP.MD_AUDITTRAIL LIKE ? \n");
        sql.append("           AND TEMP.UNITCODE = F.UNITCODE AND TEMP.OPPUNITCODE = F.OPPUNITCODE \n");
        sql.append("           AND TEMP.MD_CURRENCY = F.MD_CURRENCY AND TEMP.AGINGRANGE = F.AGINGRANGE  \n");
        sql.append("           AND TEMP.ORGNCURRENCY = F.ORGNCURRENCY AND TEMP.MD_AUDITTRAIL = F.MD_AUDITTRAIL \n");
        sql.append("           AND TEMP.SUBJECTCODE = F.SUBJECTCODE \n");
        sql.append("            %3$s \n");
        sql.append("        )\n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND F.DATATIME = ? \n");
        sql.append("   ##UNITWHERESQL##  \n");
        sql.append("   AND F.MDCODE = ? \n");
        sql.append("   AND F.MD_AUDITTRAIL like ? \n");
        sql.append("   AND F.MD_GCORGTYPE = ?  \n");
        sql.append("   %5$s \n");
        sql.append("   AND EXISTS (SELECT 1 FROM %2$s TEMP \n");
        sql.append("                       WHERE 1 = 1 \n");
        sql.append("                         AND TEMP.MD_AUDITTRAIL LIKE ? ");
        sql.append("                         AND TEMP.UNITCODE = F.UNITCODE AND TEMP.OPPUNITCODE = F.OPPUNITCODE \n");
        sql.append("                         AND TEMP.MD_CURRENCY = F.MD_CURRENCY AND TEMP.AGINGRANGE = F.AGINGRANGE  \n");
        sql.append("                         AND TEMP.ORGNCURRENCY = F.ORGNCURRENCY AND TEMP.MD_AUDITTRAIL = F.MD_AUDITTRAIL \n");
        sql.append("                         AND TEMP.SUBJECTCODE = F.SUBJECTCODE \n");
        sql.append("                         %3$s \n");
        sql.append("                         AND (TEMP.CF <> F.CF) )\n");
        StringBuilder existsFieldsEqualsSql = new StringBuilder();
        StringBuilder assistDimsFields = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                existsFieldsEqualsSql.append(" AND TEMP.").append(assistDimCode).append(" = F.").append(assistDimCode).append(" \n");
                assistDimsFields.append(",").append(assistDimCode);
            }
        }
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(offsetTaskDto.getBatchNum());
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        args.add(offsetTaskDto.getDataTime());
        String unitWhereSql = "";
        if (!offsetTaskDto.isRebuildFlag()) {
            unitWhereSql = "  AND F.UNITCODE IN (?,?) AND F.OPPUNITCODE IN (?,?) \n";
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
        }
        args.add(offsetTaskDto.getDiffUnitId());
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        args.add(offsetTaskDto.getOrgType());
        StringBuilder subjectSql = new StringBuilder();
        if (!CollectionUtils.isEmpty(offsetTaskDto.getSubjectCodes())) {
            IDbSqlHandler dbSqlHandler = OuterDataSourceUtils.getJdbcTemplate().getIDbSqlHandler();
            subjectSql.append(String.format("AND EXISTS (SELECT 1 FROM %s SUBJECT WHERE F.SUBJECTCODE LIKE ", TABLE_NAME_TEMP_CODE)).append(dbSqlHandler.concat(new String[]{"SUBJECT.CODE", "'%%')"}));
        }
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        String formatSql = String.format(sql.toString(), FinancialCubesCommonUtil.getFinancialCubesAgingTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), "GC_FINCUBES_AGING_TEMP", existsFieldsEqualsSql, assistDimsFields, subjectSql);
        formatSql = formatSql.replace("##UNITWHERESQL## ", unitWhereSql);
        return this.update(formatSql, args.toArray());
    }

    @Override
    public int insertDataToUpdateTempTable(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO %1$s \n ");
        sql.append("(ID,UNITCODE,OPPUNITCODE,MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL,SUBJECTCODE,AGINGRANGE,CF \n");
        sql.append(" %2$s )\n");
        sql.append(" SELECT TEMP.ID,TEMP.UNITCODE,TEMP.OPPUNITCODE,TEMP.MD_CURRENCY,TEMP.ORGNCURRENCY,\n");
        sql.append(" TEMP.MD_AUDITTRAIL,TEMP.SUBJECTCODE,TEMP.AGINGRANGE,TEMP.CF \n");
        sql.append(" %3$s \n");
        sql.append("  FROM ").append("GC_FINCUBES_AGING_TEMP").append(" TEMP \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND TEMP.MD_AUDITTRAIL LIKE ? \n");
        sql.append("   AND NOT EXISTS (SELECT 1 FROM %4$s F \n");
        sql.append("                    WHERE 1 = 1 \n");
        sql.append("                      AND F.DATATIME = ? AND F.MDCODE = ? \n");
        sql.append("                      AND F.MD_AUDITTRAIL LIKE ? AND F.MD_GCORGTYPE = ? \n");
        sql.append("                      AND TEMP.MD_AUDITTRAIL = F.MD_AUDITTRAIL  \n");
        sql.append("                      AND TEMP.UNITCODE = F.UNITCODE AND TEMP.OPPUNITCODE = F.OPPUNITCODE \n");
        sql.append("                      AND TEMP.MD_CURRENCY = F.MD_CURRENCY AND TEMP.ORGNCURRENCY = F.ORGNCURRENCY\n");
        sql.append("                      AND TEMP.SUBJECTCODE = F.SUBJECTCODE AND TEMP.AGINGRANGE = F.AGINGRANGE   \n");
        sql.append("                      %5$s )\n");
        StringBuilder existsFieldsEqualsSql = new StringBuilder();
        StringBuilder insertFields = new StringBuilder();
        StringBuilder valueFields = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                valueFields.append(", TEMP.").append(assistDimCode).append(" ");
                insertFields.append(", ").append(assistDimCode);
                existsFieldsEqualsSql.append(" AND TEMP.").append(assistDimCode).append(" = F.").append(assistDimCode).append(" \n");
            }
        }
        ArrayList<String> args = new ArrayList<String>();
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        args.add(offsetTaskDto.getDataTime());
        args.add(offsetTaskDto.getDiffUnitId());
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        args.add(offsetTaskDto.getOrgType());
        String formatSql = String.format(sql.toString(), "GC_FINCUBES_UPDATE_AGING_TEMP", insertFields, valueFields, FinancialCubesCommonUtil.getFinancialCubesAgingTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), existsFieldsEqualsSql);
        return this.update(formatSql, args.toArray());
    }

    @Override
    public void deleteTempDataByUpdateTempTable() {
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE ").append("GC_FINCUBES_AGING_TEMP");
        sql.append(" WHERE ID IN ( SELECT ID FROM ").append("GC_FINCUBES_UPDATE_AGING_TEMP").append(" )");
        this.update(sql.toString(), new Object[0]);
    }

    @Override
    public void cleanTempTable() {
        this.update("DELETE FROM GC_FINCUBES_AGING_TEMP WHERE 1 = 1", new Object[0]);
    }

    @Override
    public void cleanUpdateTempTable() {
        this.update("DELETE FROM GC_FINCUBES_UPDATE_AGING_TEMP WHERE 1 = 1", new Object[0]);
    }
}

