/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gc.financialcubes.offset.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.gc.financialcubes.offset.dao.FinancialCubesOffsetAgingSysDao;
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
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesOffsetAgingSysDaoImpl
extends BaseDataCenterDaoImpl
implements FinancialCubesOffsetAgingSysDao {
    private static final String TABLE_NAME_TEMP_CODE = "DC_TEMP_CODE";

    @Override
    public List<Object[]> queryOffsetData(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet) {
        StringBuilder sql = new StringBuilder();
        IDbSqlHandler sqlHandler = this.getDbSqlHandler();
        String offsetAgingRangeFiled = sqlHandler.concat(new String[]{"'AGE_'", "AGE.CODE"});
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
        Set<String> agingCodes = FinancialCubesOffsetAgingSysDaoImpl.listAgingCode();
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
        return (List)this.getJdbcTemplate().query(formatSql, rs -> {
            ArrayList<Object[]> result = new ArrayList<Object[]>();
            int cnt = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] row = new Object[cnt];
                for (int i = 0; i < cnt; ++i) {
                    row[i] = rs.getObject(i + 1);
                }
                result.add(row);
            }
            return result;
        }, args.toArray());
    }

    @Override
    public List<Object[]> queryOffsetDataByRebuild(FinancialCubesRebuildDTO rebuildDTO, Set<String> dimensionCodeSet) {
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
        Set<String> agingCodes = FinancialCubesOffsetAgingSysDaoImpl.listAgingCode();
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
        return (List)this.getJdbcTemplate().query(formatSql, rs -> {
            ArrayList<Object[]> result = new ArrayList<Object[]>();
            int cnt = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] row = new Object[cnt];
                for (int i = 0; i < cnt; ++i) {
                    row[i] = rs.getObject(i + 1);
                }
                result.add(row);
            }
            return result;
        }, args.toArray());
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
}

