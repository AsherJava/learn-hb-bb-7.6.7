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
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 */
package com.jiuqi.gc.financialcubes.offset.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesAuditTrailEnum;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.gc.financialcubes.offset.dao.FinancialCubesOffsetCfBizDao;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialCubesOffsetCfBizDaoImpl
extends BaseDataCenterDaoImpl
implements FinancialCubesOffsetCfBizDao {
    @Override
    public int insertTempTable(List<Map<String, Object>> resultList, Set<String> dimensionCodeSet) {
        return this.batchInsertToTempCFTable(resultList, dimensionCodeSet);
    }

    @Override
    public void updateTempTableByPriorPeriod(FinancialCubesOffsetTaskDto offsetTaskDto, String priorPeriod, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE %1$s TEMP \n");
        sql.append("   SET (NC,BF,DEBIT,CREDIT, \n");
        sql.append("        ORGNNC,ORGNBF,ORGNDEBIT,ORGNCREDIT) = \n");
        sql.append("       (SELECT SUM(F.NC),SUM(F.CF), \n");
        sql.append("               (TEMP.DEBITSUM-SUM(F.DEBITSUM)),(TEMP.CREDITSUM-SUM(F.CREDITSUM)), \n");
        sql.append("               SUM(F.ORGNNC),SUM(F.ORGNCF),\n");
        sql.append("               (TEMP.ORGNDEBITSUM-SUM(F.ORGNDEBITSUM)), (TEMP.ORGNCREDITSUM-SUM(F.ORGNCREDITSUM)) \n");
        sql.append("          FROM %2$s F \n");
        sql.append("         WHERE 1 = 1  \n");
        sql.append("           AND F.DATATIME = '##PRIORPERIOD##'  \n");
        sql.append("           ##UNITWHERESQL##  \n");
        sql.append("           AND F.MDCODE = ? \n");
        sql.append("           AND F.MD_AUDITTRAIL LIKE ?\n");
        sql.append("           AND F.MD_GCORGTYPE = ?\n");
        sql.append("           AND F.CFITEMCODE = TEMP.CFITEMCODE \n");
        sql.append("           AND F.UNITCODE = TEMP.UNITCODE AND F.OPPUNITCODE = TEMP.OPPUNITCODE \n");
        sql.append("           AND F.ORGNCURRENCY = TEMP.ORGNCURRENCY AND F.MD_CURRENCY = TEMP.MD_CURRENCY \n");
        sql.append("           AND F.SUBJECTCODE = TEMP.SUBJECTCODE AND F.MD_AUDITTRAIL = TEMP.MD_AUDITTRAIL\n");
        sql.append("            %3$s \n");
        sql.append("          GROUP BY F.CFITEMCODE,F.DATATIME,F.MDCODE,F.UNITCODE,F.OPPUNITCODE,F.MD_GCORGTYPE,  \n");
        sql.append("                   F.MD_CURRENCY,F.ORGNCURRENCY,F.MD_AUDITTRAIL,F.SUBJECTCODE  \n");
        sql.append("                   %4$s  \n");
        sql.append("        )\n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND EXISTS (SELECT 1 FROM %2$s F \n");
        sql.append("                WHERE 1 = 1  \n");
        sql.append("                  AND F.DATATIME = '##PRIORPERIOD##'  \n");
        sql.append("                  ##UNITWHERESQL##  \n");
        sql.append("                  AND F.MDCODE = ? \n");
        sql.append("                  AND F.MD_AUDITTRAIL LIKE ?\n");
        sql.append("                  AND F.MD_GCORGTYPE = ?\n");
        sql.append("                  AND F.CFITEMCODE = TEMP.CFITEMCODE \n");
        sql.append("                  AND F.UNITCODE = TEMP.UNITCODE AND F.OPPUNITCODE = TEMP.OPPUNITCODE \n");
        sql.append("                  AND F.ORGNCURRENCY = TEMP.ORGNCURRENCY AND F.MD_CURRENCY = TEMP.MD_CURRENCY \n");
        sql.append("                  AND F.SUBJECTCODE = TEMP.SUBJECTCODE AND F.MD_AUDITTRAIL = TEMP.MD_AUDITTRAIL\n");
        sql.append("                   %3$s) \n");
        sql.append("   %5$s \n");
        StringBuilder existsFieldsEqualsSql = new StringBuilder();
        StringBuilder assistDimsFields = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                existsFieldsEqualsSql.append(" AND F.").append(assistDimCode).append(" = TEMP.").append(assistDimCode).append(" \n");
                assistDimsFields.append(",").append(assistDimCode);
            }
        }
        ArrayList<String> args = new ArrayList<String>();
        String unitWhereSql = "";
        if (!offsetTaskDto.isRebuildFlag()) {
            unitWhereSql = "  AND F.UNITCODE IN (?,?) AND F.OPPUNITCODE IN (?,?) \n";
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
            args.add(offsetTaskDto.getDiffUnitId());
            args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
            args.add("NONE");
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
            args.add(offsetTaskDto.getDiffUnitId());
            args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
            args.add("NONE");
        } else {
            args.add(offsetTaskDto.getDiffUnitId());
            args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
            args.add("NONE");
            args.add(offsetTaskDto.getDiffUnitId());
            args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
            args.add("NONE");
        }
        StringBuilder subjectSql = new StringBuilder();
        if (!CollectionUtils.isEmpty(offsetTaskDto.getSubjectCodes())) {
            subjectSql.append("AND EXISTS (SELECT 1 FROM DC_TEMP_CODE SUBJECT WHERE TEMP.CFITEMCODE LIKE ").append(OuterDataSourceUtils.getJdbcTemplate().getIDbSqlHandler().concat(new String[]{"SUBJECT.CODE", "'%%'"}));
        }
        String formatSql = String.format(sql.toString(), "GC_FINCUBES_CF_TEMP", FinancialCubesCommonUtil.getFinancialCubesCfTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), existsFieldsEqualsSql, assistDimsFields, subjectSql);
        formatSql = formatSql.replace("##PRIORPERIOD##", priorPeriod);
        formatSql = formatSql.replace("##UNITWHERESQL##", unitWhereSql);
        this.update(formatSql, args.toArray());
    }

    @Override
    public int insertNotExistsDataByTemp(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO %1$s \n ");
        sql.append("(ID,SN,CFITEMCODE,DATATIME,MDCODE,UNITCODE,OPPUNITCODE,MD_GCORGTYPE,MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL, \n");
        sql.append(" SUBJECTCODE,NC,BF,DEBIT,CREDIT,DEBITSUM,CREDITSUM,CF, \n");
        sql.append(" ORGNNC,ORGNBF,ORGNDEBIT,ORGNCREDIT,ORGNDEBITSUM,ORGNCREDITSUM,ORGNCF \n");
        sql.append(" %2$s )\n");
        sql.append(" SELECT ##UUIDFUNCTION## AS ID,?,TEMP.CFITEMCODE,?,?,TEMP.UNITCODE,TEMP.OPPUNITCODE,?,\n");
        sql.append(" TEMP.MD_CURRENCY,TEMP.ORGNCURRENCY,TEMP.MD_AUDITTRAIL,?, \n");
        sql.append(" TEMP.NC,TEMP.BF,TEMP.DEBIT,TEMP.CREDIT,TEMP.DEBITSUM,TEMP.CREDITSUM,TEMP.CF,TEMP.ORGNNC, \n");
        sql.append(" TEMP.ORGNBF,TEMP.ORGNDEBIT,TEMP.ORGNCREDIT,TEMP.ORGNDEBITSUM,TEMP.ORGNCREDITSUM,TEMP.ORGNCF \n");
        sql.append(" %3$s \n");
        sql.append("  FROM ").append("GC_FINCUBES_UPDATE_CF_TEMP").append(" TEMP \n");
        sql.append(" WHERE 1 = 1 AND TEMP.MD_AUDITTRAIL LIKE ? ");
        StringBuilder insertFields = new StringBuilder();
        StringBuilder valueFields = new StringBuilder();
        StringBuilder existsFields = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                valueFields.append(", TEMP.").append(assistDimCode).append(" ");
                insertFields.append(", ").append(assistDimCode);
                existsFields.append(" AND TEMP.").append(assistDimCode).append(" = F.").append(assistDimCode).append(" \n");
            }
        }
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(offsetTaskDto.getBatchNum());
        args.add(offsetTaskDto.getDataTime());
        args.add(offsetTaskDto.getDiffUnitId());
        args.add(offsetTaskDto.getOrgType());
        args.add("#");
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        String formatSql = String.format(sql.toString(), FinancialCubesCommonUtil.getFinancialCubesCfTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), insertFields, valueFields, existsFields);
        formatSql = formatSql.replace("##UUIDFUNCTION##", OuterDataSourceUtils.getJdbcTemplate().getIDbSqlHandler().newUUID());
        return this.update(formatSql, args.toArray());
    }

    @Override
    public int deleteOffsetByTemp(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE %1$s F \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND F.DATATIME = ? \n");
        sql.append("   ##UNITWHERESQL##  \n");
        sql.append("   AND F.MDCODE = ? \n");
        sql.append("   AND F.MD_AUDITTRAIL like ? \n");
        sql.append("   AND F.MD_GCORGTYPE = ? \n");
        sql.append("   AND NOT EXISTS (SELECT 1 FROM %2$s TEMP \n");
        sql.append("                    WHERE 1 = 1 \n");
        sql.append("                      AND TEMP.MD_CURRENCY = F.MD_CURRENCY AND TEMP.ORGNCURRENCY = F.ORGNCURRENCY\n");
        sql.append("                      AND F.UNITCODE = TEMP.UNITCODE AND F.OPPUNITCODE = TEMP.OPPUNITCODE \n");
        sql.append("                      AND TEMP.SUBJECTCODE = F.SUBJECTCODE AND F.MD_AUDITTRAIL = TEMP.MD_AUDITTRAIL\n");
        sql.append("                      AND F.CFITEMCODE = TEMP.CFITEMCODE \n");
        sql.append("                        %3$s) \n");
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
        StringBuilder subjectSql = new StringBuilder();
        if (!CollectionUtils.isEmpty(offsetTaskDto.getSubjectCodes())) {
            subjectSql.append("AND EXISTS (SELECT 1 FROM DC_TEMP_CODE SUBJECT WHERE F.CFITEMCODE LIKE ").append(OuterDataSourceUtils.getJdbcTemplate().getIDbSqlHandler().concat(new String[]{"SUBJECT.CODE", "'%%'"}));
        }
        String formatSql = String.format(sql.toString(), FinancialCubesCommonUtil.getFinancialCubesCfTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), "GC_FINCUBES_CF_TEMP", existsFieldsEqualsSql, subjectSql);
        formatSql = formatSql.replace("##UNITWHERESQL## ", unitWhereSql);
        return this.update(formatSql, args.toArray());
    }

    @Override
    public int updateFinancialCubes(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE %1$s F \n");
        sql.append("   SET (SN,NC,BF,DEBIT,CREDIT,DEBITSUM,CREDITSUM,CF,ORGNNC, \n");
        sql.append("        ORGNBF,ORGNDEBIT,ORGNCREDIT,ORGNDEBITSUM,ORGNCREDITSUM,ORGNCF) = \n");
        sql.append("       (SELECT ?,TEMP.NC,TEMP.BF,TEMP.DEBIT,TEMP.CREDIT,TEMP.DEBITSUM, \n");
        sql.append("               TEMP.CREDITSUM,TEMP.CF,TEMP.ORGNNC,TEMP.ORGNBF,TEMP.ORGNDEBIT, \n");
        sql.append("               TEMP.ORGNCREDIT,TEMP.ORGNDEBITSUM,TEMP.ORGNCREDITSUM,TEMP.ORGNCF  \n");
        sql.append("          FROM %2$s TEMP \n");
        sql.append("         WHERE 1 = 1  \n");
        sql.append("           AND TEMP.MD_AUDITTRAIL LIKE ? \n");
        sql.append("           AND TEMP.UNITCODE = F.UNITCODE AND TEMP.OPPUNITCODE = F.OPPUNITCODE \n");
        sql.append("           AND TEMP.SUBJECTCODE = F.SUBJECTCODE AND TEMP.MD_CURRENCY = F.MD_CURRENCY \n");
        sql.append("           AND TEMP.ORGNCURRENCY = F.ORGNCURRENCY AND TEMP.MD_AUDITTRAIL = F.MD_AUDITTRAIL \n");
        sql.append("           AND F.CFITEMCODE = TEMP.CFITEMCODE \n");
        sql.append("            %3$s \n");
        sql.append("        )\n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append("   AND F.DATATIME = ? \n");
        sql.append("   ##UNITWHERESQL##");
        sql.append("   AND F.MDCODE = ? \n");
        sql.append("   AND F.MD_AUDITTRAIL like ? \n");
        sql.append("   AND F.MD_GCORGTYPE = ?  \n");
        sql.append("   %4$s \n");
        sql.append("   AND EXISTS (SELECT 1 FROM %2$s TEMP \n");
        sql.append("                       WHERE 1 = 1 \n");
        sql.append("                         AND F.MD_AUDITTRAIL LIKE ? \n");
        sql.append("                         AND TEMP.UNITCODE = F.UNITCODE AND TEMP.OPPUNITCODE = F.OPPUNITCODE \n");
        sql.append("                         AND TEMP.SUBJECTCODE = F.SUBJECTCODE AND TEMP.MD_CURRENCY = F.MD_CURRENCY \n");
        sql.append("                         AND TEMP.ORGNCURRENCY = F.ORGNCURRENCY AND TEMP.MD_AUDITTRAIL = F.MD_AUDITTRAIL \n");
        sql.append("                         AND F.CFITEMCODE = TEMP.CFITEMCODE \n");
        sql.append("                         %3$s\n");
        sql.append("                         AND (TEMP.NC <> F.NC OR TEMP.BF <> F.BF OR TEMP.DEBIT <> F.DEBIT OR TEMP.CREDIT <> F.CREDIT \n");
        sql.append("                              OR TEMP.DEBITSUM <> F.DEBITSUM OR TEMP.CREDITSUM <> F.CREDITSUM OR TEMP.CF <> F.CF\n");
        sql.append("                              OR TEMP.ORGNNC <> F.ORGNNC OR TEMP.ORGNBF <> F.ORGNBF OR TEMP.ORGNDEBIT <> F.ORGNDEBIT \n");
        sql.append("                              OR TEMP.ORGNCREDIT <> F.ORGNCREDIT OR TEMP.ORGNDEBITSUM <> F.ORGNDEBITSUM \n");
        sql.append("                              OR TEMP.ORGNCREDITSUM <> F.ORGNCREDITSUM OR TEMP.ORGNCF <> F.ORGNCF )) \n");
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
            subjectSql.append("AND EXISTS (SELECT 1 FROM DC_TEMP_CODE SUBJECT WHERE F.CFITEMCODE LIKE ").append(OuterDataSourceUtils.getJdbcTemplate().getIDbSqlHandler().concat(new String[]{"SUBJECT.CODE", "'%%'"}));
        }
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        String formatSql = String.format(sql.toString(), FinancialCubesCommonUtil.getFinancialCubesCfTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), "GC_FINCUBES_CF_TEMP", existsFieldsEqualsSql, subjectSql);
        formatSql = formatSql.replace("##UNITWHERESQL## ", unitWhereSql);
        return this.update(formatSql, args.toArray());
    }

    @Override
    public int insertDataToUpdateTempTable(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO %1$s \n ");
        sql.append("(ID,UNITCODE,OPPUNITCODE,MD_CURRENCY,ORGNCURRENCY, MD_AUDITTRAIL, \n");
        sql.append(" CFITEMCODE,SUBJECTCODE,NC,BF,DEBIT,CREDIT,DEBITSUM,CREDITSUM,CF, \n");
        sql.append(" ORGNNC,ORGNBF,ORGNDEBIT,ORGNCREDIT,ORGNDEBITSUM,ORGNCREDITSUM,ORGNCF  \n");
        sql.append(" %2$s )\n");
        sql.append("SELECT TEMP.ID,TEMP.UNITCODE,TEMP.OPPUNITCODE,TEMP.MD_CURRENCY,TEMP.ORGNCURRENCY,TEMP.MD_AUDITTRAIL,\n");
        sql.append("       TEMP.CFITEMCODE,?,TEMP.NC,TEMP.BF,TEMP.DEBIT,TEMP.CREDIT,TEMP.DEBITSUM,TEMP.CREDITSUM,TEMP.CF,\n");
        sql.append("       TEMP.ORGNNC,TEMP.ORGNBF,TEMP.ORGNDEBIT,TEMP.ORGNCREDIT,TEMP.ORGNDEBITSUM,TEMP.ORGNCREDITSUM,TEMP.ORGNCF \n");
        sql.append("       %3$s \n");
        sql.append("  FROM %4$s TEMP \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append("   AND TEMP.MD_AUDITTRAIL LIKE ?  \n");
        sql.append("   AND NOT EXISTS (SELECT 1 FROM %5$s F \n");
        sql.append("                           WHERE 1 = 1 \n");
        sql.append("                             ##UNITWHERESQL## ");
        sql.append("                             AND F.DATATIME = ? AND F.MDCODE = ? \n");
        sql.append("                             AND F.MD_AUDITTRAIL like ? AND F.MD_GCORGTYPE = ? \n");
        sql.append("                             AND TEMP.UNITCODE = F.UNITCODE AND TEMP.OPPUNITCODE = F.OPPUNITCODE \n");
        sql.append("                             AND TEMP.SUBJECTCODE = F.SUBJECTCODE AND TEMP.MD_CURRENCY = F.MD_CURRENCY \n");
        sql.append("                             AND TEMP.ORGNCURRENCY = F.ORGNCURRENCY AND TEMP.MD_AUDITTRAIL = F.MD_AUDITTRAIL \n");
        sql.append("                             AND F.CFITEMCODE = TEMP.CFITEMCODE \n");
        sql.append("                             %6$s) \n");
        StringBuilder fieldStr = new StringBuilder();
        StringBuilder tempFieldStr = new StringBuilder();
        StringBuilder existsFieldsEqualsSql = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                fieldStr.append(",").append(assistDimCode).append(" ");
                tempFieldStr.append(",TEMP.").append(assistDimCode).append(" ");
                existsFieldsEqualsSql.append(" AND TEMP.").append(assistDimCode).append(" = F.").append(assistDimCode).append(" \n");
            }
        }
        String formatSql = String.format(sql.toString(), "GC_FINCUBES_UPDATE_CF_TEMP", fieldStr, tempFieldStr, "GC_FINCUBES_CF_TEMP", FinancialCubesCommonUtil.getFinancialCubesCfTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), existsFieldsEqualsSql);
        ArrayList<String> args = new ArrayList<String>();
        args.add("#");
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        String unitWhereSql = "";
        if (!offsetTaskDto.isRebuildFlag()) {
            unitWhereSql = "  AND F.UNITCODE IN (?,?) AND F.OPPUNITCODE IN (?,?) \n";
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
        }
        args.add(offsetTaskDto.getDataTime());
        args.add(offsetTaskDto.getUnitCode());
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        args.add(offsetTaskDto.getOrgType());
        formatSql = formatSql.replace("##UNITWHERESQL##", unitWhereSql);
        return this.update(formatSql, args.toArray());
    }

    @Override
    public void deleteTempDataByUpdateTempTable() {
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE ").append("GC_FINCUBES_CF_TEMP");
        sql.append(" WHERE ID IN ( SELECT ID FROM ").append("GC_FINCUBES_UPDATE_CF_TEMP").append(" )");
        this.update(sql.toString(), new Object[0]);
    }

    @Override
    public void cleanTempTable() {
        this.update("DELETE FROM GC_FINCUBES_CF_TEMP WHERE 1 = 1", new Object[0]);
    }

    @Override
    public void cleanUpdateTempTable() {
        this.update("DELETE FROM GC_FINCUBES_UPDATE_CF_TEMP WHERE 1 = 1", new Object[0]);
    }

    private int batchInsertToTempCFTable(final List<Map<String, Object>> resultList, final Set<String> dimensionCodeSet) {
        if (CollectionUtils.isEmpty(resultList)) {
            return 0;
        }
        StringBuilder fieldStr = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                fieldStr.append(",").append(assistDimCode);
            }
        }
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ").append("GC_FINCUBES_CF_TEMP").append(" \n");
        insertSql.append("(ID,CFITEMCODE,UNITCODE,OPPUNITCODE,MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL,SUBJECTCODE,\n");
        insertSql.append(" DEBIT,CREDIT,DEBITSUM,CREDITSUM,CF,\n");
        insertSql.append(" ORGNDEBIT,ORGNCREDIT,ORGNDEBITSUM,ORGNCREDITSUM,ORGNCF");
        insertSql.append((CharSequence)fieldStr).append(" ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?");
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (int i = 0; i < dimensionCodeSet.size(); ++i) {
                insertSql.append(",?");
            }
        }
        insertSql.append(")");
        int[] updateCounts = this.batchUpdate(insertSql.toString(), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map row = (Map)resultList.get(i);
                int paramIndex = 1;
                ps.setObject(paramIndex++, row.get("ID"));
                ps.setObject(paramIndex++, row.get("CFITEMCODE"));
                ps.setObject(paramIndex++, row.get("UNITCODE"));
                ps.setObject(paramIndex++, row.get("OPPUNITCODE"));
                ps.setObject(paramIndex++, row.get("MD_CURRENCY"));
                ps.setObject(paramIndex++, row.get("ORGNCURRENCY"));
                ps.setObject(paramIndex++, row.get("MD_AUDITTRAIL"));
                ps.setObject(paramIndex++, row.get("SUBJECTCODE"));
                ps.setObject(paramIndex++, row.get("DEBIT"));
                ps.setObject(paramIndex++, row.get("CREDIT"));
                ps.setObject(paramIndex++, row.get("DEBITSUM"));
                ps.setObject(paramIndex++, row.get("CREDITSUM"));
                ps.setObject(paramIndex++, row.get("CF"));
                ps.setObject(paramIndex++, row.get("ORGNDEBIT"));
                ps.setObject(paramIndex++, row.get("ORGNCREDIT"));
                ps.setObject(paramIndex++, row.get("ORGNDEBITSUM"));
                ps.setObject(paramIndex++, row.get("ORGNCREDITSUM"));
                ps.setObject(paramIndex++, row.get("ORGNCF"));
                if (!CollectionUtils.isEmpty((Collection)dimensionCodeSet)) {
                    for (String dimensionCode : dimensionCodeSet) {
                        ps.setObject(paramIndex++, row.get(dimensionCode));
                    }
                }
            }

            public int getBatchSize() {
                return resultList.size();
            }
        });
        int totalInserted = 0;
        for (int count : updateCounts) {
            if (count <= 0) continue;
            totalInserted += count;
        }
        return totalInserted;
    }
}

