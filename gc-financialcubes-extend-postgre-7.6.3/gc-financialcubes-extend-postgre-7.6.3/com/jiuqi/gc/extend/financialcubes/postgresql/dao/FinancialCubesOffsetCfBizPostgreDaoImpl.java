/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesAuditTrailEnum
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil
 *  com.jiuqi.gc.financialcubes.offset.dao.impl.FinancialCubesOffsetCfBizDaoImpl
 *  com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto
 */
package com.jiuqi.gc.extend.financialcubes.postgresql.dao;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesAuditTrailEnum;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil;
import com.jiuqi.gc.financialcubes.offset.dao.impl.FinancialCubesOffsetCfBizDaoImpl;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import java.util.ArrayList;
import java.util.Set;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class FinancialCubesOffsetCfBizPostgreDaoImpl
extends FinancialCubesOffsetCfBizDaoImpl {
    public void updateTempTableByPriorPeriod(FinancialCubesOffsetTaskDto offsetTaskDto, String priorPeriod, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE %1$s TEMP \n");
        sql.append("   SET SN=B.SN,NC=B.NC,BF=B.BF,DEBIT=TEMP.DEBITSUM-B.DEBITSUM,CREDIT=TEMP.CREDITSUM-B.CREDITSUM, \n");
        sql.append("       ORGNNC=B.ORGNNC,ORGNBF=B.ORGNBF,ORGNDEBIT=TEMP.ORGNDEBITSUM-B.ORGNDEBITSUM,  \n");
        sql.append("       ORGNCREDIT=TEMP.ORGNCREDITSUM-B.ORGNCREDITSUM  \n");
        sql.append("  FROM (SELECT ? AS SN,SUM(NC) AS NC,SUM(CF) AS BF, \n");
        sql.append("               SUM(DEBITSUM) AS DEBITSUM,SUM(CREDITSUM) AS CREDITSUM, \n");
        sql.append("               SUM(ORGNNC) AS ORGNNC,SUM(ORGNCF) AS ORGNBF,\n");
        sql.append("               SUM(ORGNDEBITSUM) AS ORGNDEBITSUM, SUM(ORGNCREDITSUM) AS ORGNCREDITSUM, \n");
        sql.append("               CFITEMCODE,DATATIME,MDCODE,UNITCODE,OPPUNITCODE,MD_GCORGTYPE,MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL,SUBJECTCODE \n");
        sql.append("               %2$s  \n");
        sql.append("          FROM %3$s \n");
        sql.append("         WHERE 1 = 1  \n");
        sql.append("           AND DATATIME = '##PRIORPERIOD##'  \n");
        sql.append("           ##UNITWHERESQL##  \n");
        sql.append("           AND MDCODE = ? \n");
        sql.append("           AND MD_AUDITTRAIL LIKE ? \n");
        sql.append("           AND MD_GCORGTYPE = ? \n");
        sql.append("          GROUP BY CFITEMCODE,DATATIME,MDCODE,UNITCODE,OPPUNITCODE,MD_GCORGTYPE,  \n");
        sql.append("                   MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL,SUBJECTCODE  \n");
        sql.append("                   %2$s  \n");
        sql.append("        ) B\n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append("   AND TEMP.MD_AUDITTRAIL LIKE ?  \n");
        sql.append("   AND TEMP.MDCODE = ? AND B.CFITEMCODE = TEMP.CFITEMCODE  \n");
        sql.append("   AND TEMP.SUBJECTCODE = B.SUBJECTCODE  AND TEMP.MD_CURRENCY = B.MD_CURRENCY \n");
        sql.append("   AND TEMP.ORGNCURRENCY = B.ORGNCURRENCY AND TEMP.MD_AUDITTRAIL = B.MD_AUDITTRAIL \n");
        sql.append("    %4$s \n");
        StringBuilder existsFieldsEqualsSql = new StringBuilder();
        StringBuilder assistDimsFields = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                existsFieldsEqualsSql.append(" AND B.").append(assistDimCode).append(" = TEMP.").append(assistDimCode).append(" \n");
                assistDimsFields.append(",").append(assistDimCode);
            }
        }
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(offsetTaskDto.getBatchNum());
        String unitWhereSql = "";
        if (!offsetTaskDto.isRebuildFlag()) {
            unitWhereSql = "  AND UNITCODE IN (?,?) AND OPPUNITCODE IN (?,?) \n";
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
        }
        args.add(offsetTaskDto.getDiffUnitId());
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        args.add(offsetTaskDto.getOrgType());
        args.add(offsetTaskDto.getDiffUnitId());
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        String formatSql = String.format(sql.toString(), "GC_FINCUBES_CF_TEMP", assistDimsFields, FinancialCubesCommonUtil.getFinancialCubesCfTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), existsFieldsEqualsSql);
        formatSql = formatSql.replace("##PRIORPERIOD##", priorPeriod);
        formatSql = formatSql.replace("##UNITWHERESQL##", unitWhereSql);
        this.update(formatSql, args.toArray());
    }

    public int updateFinancialCubes(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE %1$s B \n");
        sql.append("   SET SN= ?,SUBJECTORIENT=TEMP.SUBJECTORIENT,NC=TEMP.NC,BF=TEMP.BF,DEBIT=TEMP.DEBIT,CREDIT=TEMP.CREDIT,DEBITSUM=TEMP.DEBITSUM, \n");
        sql.append("       CREDITSUM=TEMP.CREDITSUM,CF=TEMP.CF,ORGNNC=TEMP.ORGNNC,ORGNBF=TEMP.ORGNBF,ORGNDEBIT=TEMP.ORGNDEBIT,  \n");
        sql.append("       ORGNCREDIT=TEMP.ORGNCREDIT,ORGNDEBITSUM=TEMP.ORGNDEBITSUM,ORGNCREDITSUM=TEMP.ORGNCREDITSUM,ORGNCF=TEMP.ORGNCF  \n");
        sql.append("  FROM (SELECT SUM(NC) AS NC,SUM(BF) AS BF,SUM(DEBIT) AS DEBIT,SUM(CREDIT)  AS CREDIT,SUM(DEBITSUM)  AS DEBITSUM, \n");
        sql.append("               SUM(CREDITSUM) AS CREDITSUM,SUM(CF) AS CF,SUM(ORGNNC) AS ORGNNC,SUM(ORGNBF) AS ORGNBF,SUM(ORGNDEBIT) AS ORGNDEBIT, \n");
        sql.append("               SUM(ORGNCREDIT) AS ORGNCREDIT,SUM(ORGNDEBITSUM) AS ORGNDEBITSUM,SUM(ORGNCREDITSUM) AS ORGNCREDITSUM,SUM(ORGNCF) AS ORGNCF,  \n");
        sql.append("               CFITEMCODE,UNITCODE,MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL,SUBJECTCODE,S.ORIENT AS SUBJECTORIENT  \n");
        sql.append("               %2$s  \n");
        sql.append("          FROM %3$s T \n");
        sql.append("          JOIN ").append("MD_ACCTSUBJECT").append(" S ON T.SUBJECTCODE = S.CODE \n");
        sql.append("         WHERE 1 = 1  \n");
        sql.append("           AND DATATIME = ?  \n");
        sql.append("           ##UNITWHERESQL##  \n");
        sql.append("           AND MDCODE = ? \n");
        sql.append("           AND MD_AUDITTRAIL LIKE ? \n");
        sql.append("           AND MD_GCORGTYPE = ? \n");
        sql.append("         GROUP BY  CFITEMCODE, UNITCODE, MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL,SUBJECTCODE,S.ORIENT\n");
        sql.append("                   %2$s   \n");
        sql.append("        ) TEMP \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND B.DATATIME = ? \n");
        sql.append("   ##UNITWHERESQL##  \n");
        sql.append("   AND B.MDCODE = ? \n");
        sql.append("   AND B.MD_AUDITTRAIL LIKE ? \n");
        sql.append("   AND B.MD_GCORGTYPE = ? \n");
        sql.append("   AND TEMP.UNITCODE = B.UNITCODE AND B.CFITEMCODE = TEMP.CFITEMCODE \n");
        sql.append("   AND TEMP.MD_CURRENCY = B.MD_CURRENCY AND TEMP.SUBJECTCODE = B.SUBJECTCODE \n");
        sql.append("   AND TEMP.ORGNCURRENCY = B.ORGNCURRENCY AND TEMP.MD_AUDITTRAIL = B.MD_AUDITTRAIL \n");
        sql.append("    %4$s \n");
        StringBuilder existsFieldsEqualsSql = new StringBuilder();
        StringBuilder assistDimsFields = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                existsFieldsEqualsSql.append(" AND TEMP.").append(assistDimCode).append(" = B.").append(assistDimCode).append(" \n");
                assistDimsFields.append(",").append(assistDimCode);
            }
        }
        ArrayList<Object> args = new ArrayList<Object>();
        String unitWhereSql = "";
        args.add(offsetTaskDto.getBatchNum());
        args.add(offsetTaskDto.getDataTime());
        if (!offsetTaskDto.isRebuildFlag()) {
            unitWhereSql = "  AND B.UNITCODE IN (?,?) AND B.OPPUNITCODE IN (?,?) \n";
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
        }
        args.add(offsetTaskDto.getDiffUnitId());
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        args.add(offsetTaskDto.getOrgType());
        args.add(offsetTaskDto.getDataTime());
        if (!offsetTaskDto.isRebuildFlag()) {
            unitWhereSql = "  AND B.UNITCODE IN (?,?) AND B.OPPUNITCODE IN (?,?) \n";
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
            args.add(offsetTaskDto.getUnitCode());
            args.add(offsetTaskDto.getOppUnitCode());
        }
        args.add(offsetTaskDto.getDiffUnitId());
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        args.add(offsetTaskDto.getOrgType());
        String formatSql = String.format(sql.toString(), FinancialCubesCommonUtil.getFinancialCubesCfTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), assistDimsFields, "GC_FINCUBES_CF_TEMP", existsFieldsEqualsSql);
        formatSql = formatSql.replace("##UNITWHERESQL## ", unitWhereSql);
        return this.update(formatSql, args.toArray());
    }
}

