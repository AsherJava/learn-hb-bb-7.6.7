/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gc.financialcubes.offset.dao.impl;

import com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum;
import com.jiuqi.gc.financialcubes.offset.dao.FinancialCubesOffsetDao;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesOffsetDaoImpl
implements FinancialCubesOffsetDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FinancialCubesOffsetTaskDto> listFinancialCubesOffsetTaskDtoByIdList(Collection<String> idList, boolean isCf) {
        String deleteId = idList.iterator().next();
        String sql = " SELECT UNITID,OPPUNITID,SYSTEMID,DATATIME,MD_GCORGTYPE \n      FROM GC_OFFSETVCHRITEM T \n       %1$s \n     WHERE " + SqlUtils.getConditionOfMulStrUseOr(idList, (String)"T.ID") + " \n       %2$s \n     GROUP BY UNITID,OPPUNITID,SYSTEMID,DATATIME,MD_GCORGTYPE \n";
        sql = isCf ? String.format(sql, " JOIN MD_ACCTSUBJECT S ON S.CODE = T.SUBJECTCODE ", "AND S.GENERALTYPE ='" + SubjectClassEnum.CASH.getCode() + "'") : String.format(sql, "", "");
        return (List)this.jdbcTemplate.query(sql, rs -> {
            ArrayList<FinancialCubesOffsetTaskDto> dtoList = new ArrayList<FinancialCubesOffsetTaskDto>();
            HashSet<String> equalsStringSet = new HashSet<String>();
            while (rs.next()) {
                FinancialCubesOffsetTaskDto dto = new FinancialCubesOffsetTaskDto();
                dto.setUnitCode(rs.getString(1));
                dto.setOppUnitCode(rs.getString(2));
                dto.setSystemId(rs.getString(3));
                dto.setDataTime(rs.getString(4));
                dto.setOrgType(rs.getString(5));
                String equalsString = dto.getEqualsString();
                if (equalsStringSet.contains(equalsString)) continue;
                equalsStringSet.add(equalsString);
                dto.setDeleteId(deleteId);
                dtoList.add(dto);
            }
            return dtoList;
        });
    }

    @Override
    public List<FinancialCubesOffsetTaskDto> listFinancialCubesOffsetTaskDtoAgingByIdList(Collection<String> idList, List<String> ageFieldCodeList) {
        String deleteId = idList.iterator().next();
        String sql = " SELECT UNITID,OPPUNITID,SYSTEMID,DATATIME,MD_GCORGTYPE \n      FROM GC_OFFSETVCHRITEM t \n     WHERE " + SqlUtils.getConditionOfMulStrUseOr(idList, (String)"T.ID") + " \n       AND (%1$s) \n     GROUP BY UNITID,OPPUNITID,SYSTEMID,DATATIME,MD_GCORGTYPE \n";
        StringJoiner ageFieldSql = new StringJoiner(" OR ");
        for (String ageField : ageFieldCodeList) {
            ageFieldSql.add(ageField + " <> 0 ");
        }
        sql = String.format(sql, ageFieldSql);
        return (List)this.jdbcTemplate.query(sql, rs -> {
            ArrayList<FinancialCubesOffsetTaskDto> dtoList = new ArrayList<FinancialCubesOffsetTaskDto>();
            HashSet<String> equalsStringSet = new HashSet<String>();
            while (rs.next()) {
                FinancialCubesOffsetTaskDto dto = new FinancialCubesOffsetTaskDto();
                dto.setUnitCode(rs.getString(1));
                dto.setOppUnitCode(rs.getString(2));
                dto.setSystemId(rs.getString(3));
                dto.setDataTime(rs.getString(4));
                dto.setOrgType(rs.getString(5));
                String equalsString = dto.getEqualsString();
                if (equalsStringSet.contains(equalsString)) continue;
                equalsStringSet.add(equalsString);
                dto.setDeleteId(deleteId);
                dtoList.add(dto);
            }
            return dtoList;
        });
    }
}

