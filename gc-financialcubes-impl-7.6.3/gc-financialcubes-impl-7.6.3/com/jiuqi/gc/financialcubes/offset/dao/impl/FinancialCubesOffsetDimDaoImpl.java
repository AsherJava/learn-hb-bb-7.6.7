/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 *  com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gc.financialcubes.offset.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.gc.financialcubes.offset.dao.FinancialCubesOffsetDimDao;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialCubesOffsetDimDaoImpl
extends BaseDataCenterDaoImpl
implements FinancialCubesOffsetDimDao {
    @Override
    public List<Map<String, Object>> queryOffsetData(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ##UUIDFUNCTION## AS ID, OFFSETITEM.UNITID AS UNITCODE, OFFSETITEM.OPPUNITID AS OPPUNITCODE,\n");
        sql.append(" OFFSETITEM.OFFSETCURR AS MD_CURRENCY, OFFSETITEM.OFFSETCURR AS ORGNCURRENCY, %2$s AS MD_AUDITTRAIL, OFFSETITEM.SUBJECTCODE AS SUBJECTCODE,\n");
        sql.append(" SUM(OFFSETITEM.OFFSET_DEBIT) AS DEBIT, SUM(OFFSETITEM.OFFSET_CREDIT) AS CREDIT,\n");
        sql.append(" SUM(OFFSETITEM.OFFSET_DEBIT) AS DEBITSUM, SUM(OFFSETITEM.OFFSET_CREDIT) AS CREDITSUM,\n");
        sql.append(" SUM((OFFSETITEM.OFFSET_DEBIT-OFFSETITEM.OFFSET_CREDIT)) AS CF,\n");
        sql.append(" SUM(OFFSETITEM.OFFSET_DEBIT) AS ORGNDEBIT, SUM(OFFSETITEM.OFFSET_CREDIT) AS ORGNCREDIT,\n");
        sql.append(" SUM(OFFSETITEM.OFFSET_DEBIT) AS ORGNDEBITSUM, SUM(OFFSETITEM.OFFSET_CREDIT) AS ORGNCREDITSUM,\n");
        sql.append(" SUM((OFFSETITEM.OFFSET_DEBIT-OFFSETITEM.OFFSET_CREDIT)) AS ORGNCF \n");
        sql.append(" %3$s \n");
        sql.append("  FROM ").append("GC_OFFSETVCHRITEM").append(" OFFSETITEM \n");
        sql.append("  LEFT JOIN ").append("MD_ACCTSUBJECT").append(" S ON S.CODE = OFFSETITEM.SUBJECTCODE \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND OFFSETITEM.DATATIME = ?  \n");
        sql.append("   AND (OFFSETITEM.ADJUST = '0' or OFFSETITEM.ADJUST IS NULL OR OFFSETITEM.ADJUST='' )");
        sql.append("   AND OFFSETITEM.DISABLEFLAG <> 1 \n");
        sql.append("   AND (S.GENERALTYPE <> '" + SubjectClassEnum.CASH.getCode() + "' or S.GENERALTYPE IS NULL)\n");
        sql.append("   AND OFFSETITEM.UNITID IN (?,?)  \n");
        sql.append("   AND OFFSETITEM.OPPUNITID IN (?,?) \n");
        sql.append("   AND OFFSETITEM.SYSTEMID = ?  \n");
        sql.append("   AND OFFSETITEM.MD_GCORGTYPE in (?,'NONE')   \n");
        sql.append(" GROUP BY OFFSETITEM.DATATIME,OFFSETITEM.UNITID,OFFSETITEM.OPPUNITID,OFFSETITEM.MD_GCORGTYPE, \n");
        sql.append("          OFFSETITEM.OFFSETCURR,OFFSETITEM.GCBUSINESSTYPECODE,OFFSETITEM.SUBJECTCODE \n");
        sql.append("          %1$s \n");
        StringBuilder srcTypeSql = new StringBuilder();
        srcTypeSql.append("\n CASE WHEN " + sqlHandler.judgeEmpty("OFFSETITEM.GCBUSINESSTYPECODE", true) + " THEN '2#'  \n");
        srcTypeSql.append("        ELSE " + sqlHandler.concat(new String[]{"'2'", sqlHandler.toChar("OFFSETITEM.GCBUSINESSTYPECODE")}) + " END  \n");
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
        String formatSql = String.format(sql.toString(), fieldStr, srcTypeSql, selectFieldStr);
        formatSql = formatSql.replace("##UUIDFUNCTION##", sqlHandler.newUUID());
        return this.getJdbcTemplate().query(formatSql, new Object[]{offsetTaskDto.getDataTime(), offsetTaskDto.getUnitCode(), offsetTaskDto.getOppUnitCode(), offsetTaskDto.getUnitCode(), offsetTaskDto.getOppUnitCode(), offsetTaskDto.getSystemId(), offsetTaskDto.getOrgType()}, (rs, rowNum) -> {
            HashMap<String, Object> row = new HashMap<String, Object>();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; ++i) {
                String columnName = metaData.getColumnName(i);
                Object value = rs.getObject(i);
                row.put(columnName, value);
            }
            return row;
        });
    }

    @Override
    public List<Map<String, Object>> queryOffsetDataByRebuild(FinancialCubesRebuildDTO rebuildDTO, Set<String> dimensionCodeSet, String subjectTempGroupId) {
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
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ##UUIDFUNCTION## AS ID, OFFSETITEM.UNITID AS UNITCODE, OFFSETITEM.OPPUNITID AS OPPUNITCODE,\n");
        sql.append(" OFFSETITEM.OFFSETCURR AS MD_CURRENCY, OFFSETITEM.OFFSETCURR AS ORGNCURRENCY, %1$s AS MD_AUDITTRAIL, OFFSETITEM.SUBJECTCODE AS SUBJECTCODE,\n");
        sql.append(" SUM(OFFSETITEM.OFFSET_DEBIT) AS DEBIT, SUM(OFFSETITEM.OFFSET_CREDIT) AS CREDIT,\n");
        sql.append(" SUM(OFFSETITEM.OFFSET_DEBIT) AS DEBITSUM, SUM(OFFSETITEM.OFFSET_CREDIT) AS CREDITSUM,\n");
        sql.append(" SUM((OFFSETITEM.OFFSET_DEBIT-OFFSETITEM.OFFSET_CREDIT)) AS CF,\n");
        sql.append(" SUM(OFFSETITEM.OFFSET_DEBIT) AS ORGNDEBIT, SUM(OFFSETITEM.OFFSET_CREDIT) AS ORGNCREDIT,\n");
        sql.append(" SUM(OFFSETITEM.OFFSET_DEBIT) AS ORGNDEBITSUM, SUM(OFFSETITEM.OFFSET_CREDIT) AS ORGNCREDITSUM,\n");
        sql.append(" SUM((OFFSETITEM.OFFSET_DEBIT-OFFSETITEM.OFFSET_CREDIT)) AS ORGNCF \n");
        sql.append(" %2$s \n");
        sql.append("  FROM ").append("GC_OFFSETVCHRITEM").append(" OFFSETITEM \n");
        sql.append("  %3$s \n");
        sql.append("  LEFT JOIN ").append("MD_ACCTSUBJECT").append(" S ON S.CODE = OFFSETITEM.SUBJECTCODE \n");
        sql.append("  JOIN ").append(orgTable).append("  BFUNITTABLE ON (OFFSETITEM.UNITID = BFUNITTABLE.CODE)\n");
        sql.append("  JOIN ").append(orgTable).append("  DFUNITTABLE ON (OFFSETITEM.OPPUNITID = DFUNITTABLE.CODE)\n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND OFFSETITEM.DATATIME = ?  \n");
        sql.append("   AND (OFFSETITEM.ADJUST = '0' or OFFSETITEM.ADJUST IS NULL OR OFFSETITEM.ADJUST='' )");
        sql.append("   AND OFFSETITEM.DISABLEFLAG <> 1 \n");
        sql.append("   AND (S.GENERALTYPE <> '" + SubjectClassEnum.CASH.getCode() + "'  OR S.GENERALTYPE  IS NULL)\n");
        sql.append("   AND OFFSETITEM.MD_GCORGTYPE in (?,'NONE')   \n");
        sql.append("   AND (substr(bfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append("))\n");
        sql.append("and bfUnitTable.parents like ? \n");
        sql.append("and dfUnitTable.parents like ? \n");
        sql.append("and bfUnitTable.validtime<? and bfUnitTable.invalidtime>=? \n");
        sql.append("and dfUnitTable.validtime<? and dfUnitTable.invalidtime>=? \n");
        sql.append(" GROUP BY OFFSETITEM.DATATIME,OFFSETITEM.UNITID,OFFSETITEM.OPPUNITID,OFFSETITEM.MD_GCORGTYPE");
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            sql.append(",OFFSETITEM.OFFSETCURR,OFFSETITEM.GCBUSINESSTYPECODE,OFFSETITEM.SUBJECTCODE");
            sql.append(",%4$s \n");
        } else {
            sql.append(",OFFSETITEM.OFFSETCURR,OFFSETITEM.GCBUSINESSTYPECODE,OFFSETITEM.SUBJECTCODE \n");
        }
        StringBuilder MD_AUDITTRAILSql = new StringBuilder();
        MD_AUDITTRAILSql.append("\n CASE WHEN " + sqlHandler.judgeEmpty("OFFSETITEM.GCBUSINESSTYPECODE", true) + " THEN '2#'  \n");
        MD_AUDITTRAILSql.append("        ELSE " + sqlHandler.concat(new String[]{"'2'", sqlHandler.toChar("OFFSETITEM.GCBUSINESSTYPECODE")}) + " END  \n");
        StringBuilder fieldStr = new StringBuilder();
        StringBuilder groupFieldStr = new StringBuilder();
        StringBuilder selectFieldStr = new StringBuilder();
        if (!CollectionUtils.isEmpty(dimensionCodeSet)) {
            ArrayList<String> groupFields = new ArrayList<String>();
            for (String assistDimCode : dimensionCodeSet) {
                fieldStr.append(",").append(assistDimCode).append(" ");
                groupFields.add("OFFSETITEM." + assistDimCode);
                selectFieldStr.append(",").append(sqlHandler.nullToValue("OFFSETITEM." + assistDimCode, "'#'")).append(" AS ").append(assistDimCode).append("\n");
            }
            groupFieldStr.append(String.join((CharSequence)",", groupFields));
        }
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder subjectSql = new StringBuilder();
        if (!CollectionUtils.isEmpty((Collection)rebuildDTO.getSubjectCodeList())) {
            subjectSql.append("JOIN GC_IDTEMPORARY TEMP ON OFFSETITEM.SUBJECTCODE LIKE ").append(this.getDbSqlHandler().concat(new String[]{"TEMP.TBCODE", "'%%'"}));
        }
        args.add(rebuildDTO.getDataTime());
        args.add(rebuildDTO.getOrgType());
        args.add(parentGuids + "%");
        args.add(parentGuids + "%");
        args.add(date);
        args.add(date);
        args.add(date);
        args.add(date);
        String formatSql = String.format(sql.toString(), MD_AUDITTRAILSql, selectFieldStr, subjectSql, groupFieldStr);
        formatSql = formatSql.replace("##UUIDFUNCTION##", sqlHandler.newUUID());
        return this.getJdbcTemplate().query(formatSql, args.toArray(), (rs, rowNum) -> {
            HashMap<String, Object> row = new HashMap<String, Object>();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; ++i) {
                String columnName = metaData.getColumnName(i);
                Object value = rs.getObject(i);
                row.put(columnName, value);
            }
            return row;
        });
    }
}

