/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesAuditTrailEnum
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil
 *  com.jiuqi.gc.financialcubes.offset.dao.impl.FinancialCubesOffsetAgingBizDaoImpl
 *  com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto
 */
package com.jiuqi.gc.extend.financialcubes.postgresql.dao;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesAuditTrailEnum;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil;
import com.jiuqi.gc.financialcubes.offset.dao.impl.FinancialCubesOffsetAgingBizDaoImpl;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import java.util.ArrayList;
import java.util.Set;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class FinancialCubesOffsetAgingBizPostgreDaoImpl
extends FinancialCubesOffsetAgingBizDaoImpl {
    public int updateFinancialCubes(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE %1$s F \n");
        sql.append("   SET SN=?,CF = \n");
        sql.append("       (SELECT TEMP.CF  \n");
        sql.append("          FROM %2$s TEMP \n");
        sql.append("         WHERE 1 = 1 \n");
        sql.append("           AND TEMP.MD_AUDITTRAIL LIKE ? ");
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
        sql.append("   AND EXISTS (SELECT 1 FROM %2$s TEMP");
        sql.append("                       WHERE 1 = 1 \n");
        sql.append("                         AND TEMP.MD_AUDITTRAIL LIKE ? ");
        sql.append("                         AND TEMP.UNITCODE = F.UNITCODE AND TEMP.OPPUNITCODE = F.OPPUNITCODE \n");
        sql.append("                         AND TEMP.MD_CURRENCY = F.MD_CURRENCY AND TEMP.AGINGRANGE = F.AGINGRANGE  \n");
        sql.append("                         AND TEMP.ORGNCURRENCY = F.ORGNCURRENCY AND TEMP.MD_AUDITTRAIL = F.MD_AUDITTRAIL \n");
        sql.append("                         AND TEMP.SUBJECTCODE = F.SUBJECTCODE \n");
        sql.append("                         %3$s \n");
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
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        String formatSql = String.format(sql.toString(), FinancialCubesCommonUtil.getFinancialCubesAgingTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), "GC_FINCUBES_AGING_TEMP", existsFieldsEqualsSql, assistDimsFields);
        formatSql = formatSql.replace("##UNITWHERESQL## ", unitWhereSql);
        return this.update(formatSql, args.toArray());
    }
}

