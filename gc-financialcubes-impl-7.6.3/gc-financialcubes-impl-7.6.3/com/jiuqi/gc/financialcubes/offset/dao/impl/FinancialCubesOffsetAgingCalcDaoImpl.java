/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesAuditTrailEnum
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 *  com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.taskscheduling.core.jdbc.RecordSqlJdbcTemplate
 *  com.jiuqi.dc.taskscheduling.core.jdbc.RecordSqlJdbcTemplateFactory
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gc.financialcubes.offset.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesAuditTrailEnum;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.taskscheduling.core.jdbc.RecordSqlJdbcTemplate;
import com.jiuqi.dc.taskscheduling.core.jdbc.RecordSqlJdbcTemplateFactory;
import com.jiuqi.gc.financialcubes.common.dao.impl.GcFinancialCubesBaseDaoImpl;
import com.jiuqi.gc.financialcubes.offset.dao.FinancialCubesOffsetAgingCalcDao;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public class FinancialCubesOffsetAgingCalcDaoImpl
extends GcFinancialCubesBaseDaoImpl
implements FinancialCubesOffsetAgingCalcDao {
    private static final String AGING_CATEGORY = "GENERAL_AGING";

    @Override
    public int insertTempTable(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler();
        String offsetAgingRangeFiled = sqlHandler.concat(new String[]{"'AGE_'", "AGE.CODE"});
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO %1$s \n ");
        sql.append("(ID,UNITCODE,OPPUNITCODE,MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL, \n");
        sql.append(" SUBJECTCODE,AGINGRANGE,CF %2$s )\n");
        sql.append(" SELECT ID,UNITCODE,OPPUNITCODE,MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL,\n");
        sql.append("        SUBJECTCODE,AGINGRANGE,CF %2$s \n");
        sql.append("   FROM ( \n");
        sql.append("         SELECT ##UUIDFUNCTION## AS ID,OFFSETITEM.UNITID AS UNITCODE,OFFSETITEM.OPPUNITID AS OPPUNITCODE, \n");
        sql.append("                OFFSETITEM.OFFSETCURR AS MD_CURRENCY,OFFSETITEM.OFFSETCURR AS ORGNCURRENCY, (%3$s) AS MD_AUDITTRAIL,  \n");
        sql.append("                OFFSETITEM.SUBJECTCODE AS SUBJECTCODE, AGE.CODE AS AGINGRANGE, \n");
        sql.append("                (##CFSQL##) AS CF \n");
        sql.append("                %4$s \n");
        sql.append("           FROM ").append("GC_OFFSETVCHRITEM").append(" OFFSETITEM \n");
        sql.append("           JOIN ").append("MD_AGING").append(" AGE ON 1=1 \n");
        sql.append("           JOIN ").append("MD_ACCTSUBJECT").append(" S ON S.CODE = OFFSETITEM.SUBJECTCODE");
        sql.append("          WHERE 1 = 1  \n");
        sql.append("            AND OFFSETITEM.DATATIME = ?  \n");
        sql.append("            AND (OFFSETITEM.ADJUST = '0' or OFFSETITEM.ADJUST IS NULL OR OFFSETITEM.ADJUST='' ) \n");
        sql.append("            AND OFFSETITEM.DISABLEFLAG <> 1 \n");
        sql.append("            AND OFFSETITEM.UNITID IN (?,?)  \n");
        sql.append("            AND OFFSETITEM.OPPUNITID IN (?,?) \n");
        sql.append("            AND OFFSETITEM.SYSTEMID = ?  \n");
        sql.append("            AND OFFSETITEM.MD_GCORGTYPE in (?,'NONE')   \n");
        sql.append("          GROUP BY AGE.CODE,OFFSETITEM.DATATIME,OFFSETITEM.UNITID,OFFSETITEM.OPPUNITID,OFFSETITEM.MD_GCORGTYPE,\n");
        sql.append("                   OFFSETITEM.OFFSETCURR,OFFSETITEM.GCBUSINESSTYPECODE,OFFSETITEM.SUBJECTCODE, \n");
        sql.append("                   S.ORIENT  %2$s \n");
        sql.append("        ) T \n");
        sql.append(" WHERE 1 = 1 AND T.CF IS NOT NULL \n");
        StringBuilder srcTypeSql = new StringBuilder();
        srcTypeSql.append("\n CASE WHEN " + sqlHandler.judgeEmpty("OFFSETITEM.GCBUSINESSTYPECODE", true) + " THEN '#'  \n");
        srcTypeSql.append("        ELSE " + sqlHandler.concat(new String[]{"'2'", sqlHandler.toChar("OFFSETITEM.GCBUSINESSTYPECODE")}) + " END  \n");
        StringBuilder cfSql = new StringBuilder(" CASE ");
        Set<String> agingCodes = FinancialCubesOffsetAgingCalcDaoImpl.listAgingCode();
        for (String agingCode : agingCodes) {
            String agingRangeCode = "AGE_" + agingCode;
            cfSql.append(" WHEN ").append(offsetAgingRangeFiled).append("='" + (String)agingRangeCode + "'").append(" THEN SUM(OFFSETITEM.").append(agingRangeCode).append(")\n");
        }
        cfSql.append(" ELSE NULL END \n");
        StringBuilder fieldStr = new StringBuilder();
        StringBuilder selectFieldStr = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                fieldStr.append(",").append(assistDimCode).append(" ");
                selectFieldStr.append(",").append(sqlHandler.nullToValue(assistDimCode, "'#'")).append(" AS ").append(assistDimCode).append("\n");
            }
        }
        String formatSql = String.format(sql.toString(), "GC_FINCUBES_AGING_TEMP", fieldStr, srcTypeSql, selectFieldStr);
        formatSql = formatSql.replace("##UUIDFUNCTION##", sqlHandler.newUUID());
        formatSql = formatSql.replace("##CFSQL##", cfSql.toString());
        ArrayList<String> args = new ArrayList<String>();
        args.add(offsetTaskDto.getDataTime());
        args.add(offsetTaskDto.getUnitCode());
        args.add(offsetTaskDto.getOppUnitCode());
        args.add(offsetTaskDto.getUnitCode());
        args.add(offsetTaskDto.getOppUnitCode());
        args.add(offsetTaskDto.getSystemId());
        args.add(offsetTaskDto.getOrgType());
        RecordSqlJdbcTemplate recordSqlJdbcTemplate = RecordSqlJdbcTemplateFactory.getRecordSqlJdbcTemplate((JdbcTemplate)this.getJdbcTemplate());
        return recordSqlJdbcTemplate.update(formatSql, args.toArray());
    }

    @Override
    public int insertNotExistsDataByTemp(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO %1$s \n ");
        sql.append("(ID,SN,DATATIME,MDCODE,UNITCODE,OPPUNITCODE,MD_GCORGTYPE,MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL, \n");
        sql.append(" SUBJECTCODE,SUBJECTORIENT,AGINGCATEGORY,AGINGRANGE,CF %2$s )\n");
        sql.append(" SELECT ##UUIDFUNCTION## AS ID, ? ,?,?,TEMP.UNITCODE,TEMP.OPPUNITCODE,?,TEMP.MD_CURRENCY,\n");
        sql.append("        TEMP.ORGNCURRENCY,TEMP.MD_AUDITTRAIL, TEMP.SUBJECTCODE, S.ORIENT,?, TEMP.AGINGRANGE, TEMP.CF \n");
        sql.append("        %3$s \n");
        sql.append("  FROM ").append("GC_FINCUBES_UPDATE_AGING_TEMP").append(" TEMP \n");
        sql.append("  JOIN ").append("MD_ACCTSUBJECT").append(" S ON S.CODE = TEMP.SUBJECTCODE \n");
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
        formatSql = formatSql.replace("##UUIDFUNCTION##", this.getDbSqlHandler().newUUID());
        RecordSqlJdbcTemplate recordSqlJdbcTemplate = RecordSqlJdbcTemplateFactory.getRecordSqlJdbcTemplate((JdbcTemplate)this.getJdbcTemplate());
        return recordSqlJdbcTemplate.update(formatSql, args.toArray());
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
            subjectSql.append("AND EXISTS (SELECT 1 FROM GC_IDTEMPORARY SUBJECT WHERE F.SUBJECTCODE LIKE ").append(this.getDbSqlHandler().concat(new String[]{"SUBJECT.TBCODE", "'%%'"}));
            subjectSql.append(" AND SUBJECT.GROUP_ID = ? ) \n");
            args.add(offsetTaskDto.getSubjectTempGroupId());
        }
        String formatSql = String.format(sql.toString(), FinancialCubesCommonUtil.getFinancialCubesAgingTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), "GC_FINCUBES_AGING_TEMP", existsFieldsEqualsSql, subjectSql);
        formatSql = formatSql.replace("##UNITWHERESQL## ", unitWhereSql);
        RecordSqlJdbcTemplate recordSqlJdbcTemplate = RecordSqlJdbcTemplateFactory.getRecordSqlJdbcTemplate((JdbcTemplate)this.getJdbcTemplate());
        return recordSqlJdbcTemplate.update(formatSql, args.toArray());
    }

    @Override
    public int updateFinancialCubes(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE %1$s F \n");
        sql.append("   SET (SN,SUBJECTORIENT,CF) = \n");
        sql.append("       (SELECT ?,S.ORIENT,TEMP.CF \n");
        sql.append("          FROM %2$s TEMP \n");
        sql.append("          JOIN ").append("MD_ACCTSUBJECT").append(" S ON TEMP.SUBJECTCODE = S.CODE \n");
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
        sql.append("                        JOIN ").append("MD_ACCTSUBJECT").append(" S ON TEMP.SUBJECTCODE = S.CODE \n");
        sql.append("                       WHERE 1 = 1 \n");
        sql.append("                         AND TEMP.MD_AUDITTRAIL LIKE ? ");
        sql.append("                         AND TEMP.UNITCODE = F.UNITCODE AND TEMP.OPPUNITCODE = F.OPPUNITCODE \n");
        sql.append("                         AND TEMP.MD_CURRENCY = F.MD_CURRENCY AND TEMP.AGINGRANGE = F.AGINGRANGE  \n");
        sql.append("                         AND TEMP.ORGNCURRENCY = F.ORGNCURRENCY AND TEMP.MD_AUDITTRAIL = F.MD_AUDITTRAIL \n");
        sql.append("                         AND TEMP.SUBJECTCODE = F.SUBJECTCODE \n");
        sql.append("                         %3$s \n");
        sql.append("                         AND (TEMP.CF <> F.CF OR S.ORIENT <> F.SUBJECTORIENT) )\n");
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
            subjectSql.append("AND EXISTS (SELECT 1 FROM GC_IDTEMPORARY SUBJECT WHERE F.SUBJECTCODE LIKE ").append(this.getDbSqlHandler().concat(new String[]{"SUBJECT.TBCODE", "'%%'"}));
            subjectSql.append(" AND SUBJECT.GROUP_ID = ? ) \n");
            args.add(offsetTaskDto.getSubjectTempGroupId());
        }
        args.add(FinancialCubesAuditTrailEnum.OFFSET.getCode() + "%");
        String formatSql = String.format(sql.toString(), FinancialCubesCommonUtil.getFinancialCubesAgingTableName((FinancialCubesPeriodTypeEnum)offsetTaskDto.getPeriodTypeEnum()), "GC_FINCUBES_AGING_TEMP", existsFieldsEqualsSql, assistDimsFields, subjectSql);
        formatSql = formatSql.replace("##UNITWHERESQL## ", unitWhereSql);
        RecordSqlJdbcTemplate recordSqlJdbcTemplate = RecordSqlJdbcTemplateFactory.getRecordSqlJdbcTemplate((JdbcTemplate)this.getJdbcTemplate());
        return recordSqlJdbcTemplate.update(formatSql, args.toArray());
    }

    @Override
    public int insertTempTableByRebuild(FinancialCubesRebuildDTO rebuildDTO, Set<String> dimensionCodeSet, String subjectTempGroupId) {
        YearPeriodObject yp = new YearPeriodObject(null, rebuildDTO.getDataTime());
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)rebuildDTO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        Date date = yp.formatYP().getEndDate();
        GcOrgCacheVO mergeUnitByDifference = orgService.getMergeUnitByDifference(rebuildDTO.getUnitCode());
        String parentGuids = mergeUnitByDifference.getParentStr();
        String gcParentStr = mergeUnitByDifference.getGcParentStr();
        String orgTable = orgService.getCurrOrgType().getTable();
        int orgCodeLength = orgService.getOrgCodeLength();
        int len = gcParentStr.length();
        IDbSqlHandler sqlHandler = this.getDbSqlHandler();
        String offsetAgingRangeFiled = sqlHandler.concat(new String[]{"'AGE_'", "AGE.CODE"});
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO %1$s \n ");
        sql.append("(ID,UNITCODE,OPPUNITCODE,MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL, \n");
        sql.append(" SUBJECTCODE,AGINGRANGE,CF %2$s )\n");
        sql.append(" SELECT ID,UNITCODE,OPPUNITCODE,MD_CURRENCY,ORGNCURRENCY,MD_AUDITTRAIL,SUBJECTCODE,AGINGRANGE,CF %2$s \n");
        sql.append("   FROM ( \n");
        sql.append("         SELECT ##UUIDFUNCTION## AS ID,OFFSETITEM.UNITID AS UNITCODE,OFFSETITEM.OPPUNITID AS OPPUNITCODE, \n");
        sql.append("                OFFSETITEM.OFFSETCURR AS MD_CURRENCY,OFFSETITEM.OFFSETCURR AS ORGNCURRENCY, (%3$s) AS MD_AUDITTRAIL,  \n");
        sql.append("                OFFSETITEM.SUBJECTCODE AS SUBJECTCODE, AGE.CODE AS AGINGRANGE, \n");
        sql.append("                (##CFSQL##) AS CF \n");
        sql.append("                %4$s \n");
        sql.append("           FROM ").append("GC_OFFSETVCHRITEM").append(" OFFSETITEM \n");
        sql.append("           JOIN ").append("MD_AGING").append(" AGE ON 1=1 \n");
        sql.append("           JOIN ").append("MD_ACCTSUBJECT").append(" S ON S.CODE = OFFSETITEM.SUBJECTCODE \n");
        sql.append("           %5$s \n");
        sql.append("           JOIN ").append(orgTable).append("  BFUNITTABLE ON (OFFSETITEM.UNITID = BFUNITTABLE.CODE)\n");
        sql.append("           JOIN ").append(orgTable).append("  DFUNITTABLE ON (OFFSETITEM.OPPUNITID = DFUNITTABLE.CODE)\n");
        sql.append("          WHERE 1 = 1  \n");
        sql.append("            AND OFFSETITEM.DATATIME = ?  \n");
        sql.append("            AND (OFFSETITEM.ADJUST = '0' or OFFSETITEM.ADJUST IS NULL OR OFFSETITEM.ADJUST='' ) \n");
        sql.append("            AND OFFSETITEM.DISABLEFLAG <> 1 \n");
        sql.append("            AND OFFSETITEM.MD_GCORGTYPE in (?,'NONE')   \n");
        sql.append("            AND (substr(bfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append("))\n");
        sql.append("            and bfUnitTable.parents like ? \n");
        sql.append("            and dfUnitTable.parents like ? \n");
        sql.append("            and bfUnitTable.validtime<? and bfUnitTable.invalidtime>=? \n");
        sql.append("            and dfUnitTable.validtime<? and dfUnitTable.invalidtime>=? \n");
        sql.append("          GROUP BY AGE.CODE,OFFSETITEM.UNITID,OFFSETITEM.OPPUNITID,OFFSETITEM.MD_GCORGTYPE,\n");
        sql.append("                   OFFSETITEM.OFFSETCURR,OFFSETITEM.GCBUSINESSTYPECODE,OFFSETITEM.SUBJECTCODE \n");
        sql.append("                   %6$s \n");
        sql.append("        ) T \n");
        sql.append(" WHERE 1 = 1 AND T.CF IS NOT NULL \n");
        StringBuilder srcTypeSql = new StringBuilder();
        srcTypeSql.append("\n CASE WHEN " + sqlHandler.judgeEmpty("OFFSETITEM.GCBUSINESSTYPECODE", true) + " THEN '#'  \n");
        srcTypeSql.append("        ELSE " + sqlHandler.concat(new String[]{"'2'", sqlHandler.toChar("OFFSETITEM.GCBUSINESSTYPECODE")}) + " END  \n");
        StringBuilder cfSql = new StringBuilder(" CASE ");
        Set<String> agingCodes = FinancialCubesOffsetAgingCalcDaoImpl.listAgingCode();
        for (String agingCode : agingCodes) {
            String agingRangeCode = "AGE_" + agingCode;
            cfSql.append(" WHEN ").append(offsetAgingRangeFiled).append("='" + agingRangeCode + "'").append(" THEN SUM(OFFSETITEM.").append(agingRangeCode).append(")\n");
        }
        cfSql.append(" ELSE NULL END \n");
        StringBuilder fieldStr = new StringBuilder();
        StringBuilder groupFieldStr = new StringBuilder();
        StringBuilder selectFieldStr = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            for (String assistDimCode : dimensionCodeSet) {
                fieldStr.append(",").append(assistDimCode).append(" ");
                groupFieldStr.append(",OFFSETITEM.").append(assistDimCode).append(" ");
                selectFieldStr.append(",").append(sqlHandler.nullToValue("OFFSETITEM." + assistDimCode, "'#'")).append(" AS ").append(assistDimCode).append("\n");
            }
        }
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder subjectSql = new StringBuilder();
        if (!CollectionUtils.isEmpty((Collection)rebuildDTO.getSubjectCodeList())) {
            subjectSql.append("JOIN GC_IDTEMPORARY TEMP ON OFFSETITEM.SUBJECTCODE LIKE ").append(this.getDbSqlHandler().concat(new String[]{"TEMP.TBCODE", "'%%'"}));
            subjectSql.append("AND TEMP.GROUP_ID = ? \n");
            args.add(subjectTempGroupId);
        }
        args.add(rebuildDTO.getDataTime());
        args.add(rebuildDTO.getOrgType());
        args.add(parentGuids + "%");
        args.add(parentGuids + "%");
        args.add(date);
        args.add(date);
        args.add(date);
        args.add(date);
        String formatSql = String.format(sql.toString(), "GC_FINCUBES_AGING_TEMP", fieldStr, srcTypeSql, selectFieldStr, subjectSql, groupFieldStr);
        formatSql = formatSql.replace("##UUIDFUNCTION##", sqlHandler.newUUID());
        formatSql = formatSql.replace("##DIFFUNIT##", rebuildDTO.getUnitCode());
        formatSql = formatSql.replace("##MD_GCORGTYPE##", rebuildDTO.getOrgType());
        formatSql = formatSql.replace("##CFSQL##", cfSql.toString());
        RecordSqlJdbcTemplate recordSqlJdbcTemplate = RecordSqlJdbcTemplateFactory.getRecordSqlJdbcTemplate((JdbcTemplate)this.getJdbcTemplate());
        return recordSqlJdbcTemplate.update(formatSql, args.toArray());
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
        RecordSqlJdbcTemplate recordSqlJdbcTemplate = RecordSqlJdbcTemplateFactory.getRecordSqlJdbcTemplate((JdbcTemplate)this.getJdbcTemplate());
        return recordSqlJdbcTemplate.update(formatSql, args.toArray());
    }

    private static Set<String> listAgingCode() {
        GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
        HashSet<String> subjectAgingSet = new HashSet<String>();
        List subjectAgingList = tool.queryBasedataItems("MD_SUBJECT_AGING");
        for (GcBaseData gcBaseData : subjectAgingList) {
            List agingCodeList = (List)gcBaseData.getFieldVal("AGINGCODE");
            String defaultValCode = ConverterUtils.getAsString((Object)gcBaseData.getFieldVal("DEFAULTVAL"));
            subjectAgingSet.addAll(agingCodeList);
            subjectAgingSet.add(defaultValCode);
        }
        subjectAgingSet.removeIf(Objects::isNull);
        return subjectAgingSet;
    }

    @Override
    public void deleteTempDataByUpdateTempTable() {
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE ").append("GC_FINCUBES_AGING_TEMP");
        sql.append(" WHERE ID IN ( SELECT ID FROM ").append("GC_FINCUBES_UPDATE_AGING_TEMP").append(" )");
        this.getJdbcTemplate().update(sql.toString());
    }

    @Override
    public void cleanTempTable() {
        this.getJdbcTemplate().update("DELETE FROM GC_FINCUBES_AGING_TEMP WHERE 1 = 1");
    }

    @Override
    public void cleanUpdateTempTable() {
        this.getJdbcTemplate().update("DELETE FROM GC_FINCUBES_UPDATE_AGING_TEMP WHERE 1 = 1");
    }
}

