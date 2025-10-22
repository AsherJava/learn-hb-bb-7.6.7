/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.conversion.conversionsystem.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class ConversionSystemDaoImpl
extends GcDbSqlGenericDAO<ConversionSystemEO, String>
implements ConversionSystemDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ConversionSystemDaoImpl() {
        super(ConversionSystemEO.class);
    }

    @Override
    public List<ConversionSystemEO> getAllSystemList() {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(ConversionSystemEO.class, (String)"scheme") + "  from  " + "GC_CONV_SYSTEM" + "   scheme  \n  where   1=1 \n  order by scheme.createTime \n";
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public List<ConversionSystemEO> getAllSystemListByTaskId(String taskId) {
        String sql = "  select DISTINCT" + SqlUtils.getColumnsSqlByEntity(ConversionSystemEO.class, (String)"scheme") + "  from  " + "GC_CONV_SYSTEM" + "   scheme  \n  join " + "GC_CONV_SYSTEM_TS" + "   t \ton scheme.id = t.systemid  where   1=1 \n  and  t.TASKID = ? \n  order by scheme.createTime \n";
        return this.selectEntity(sql, new Object[]{taskId});
    }

    @Override
    public List<ConversionSystemTaskSchemeVO> getSystemTaskSchemes() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select ts.id   as schemetaskid, ");
        sqlBuilder.append("\t\t  ts.systemId   as systemId, ");
        sqlBuilder.append("       ts.rateSchemeCode  as rateSchemeCode, ");
        sqlBuilder.append("       ts.createTime  as createTime, ");
        sqlBuilder.append("       task.tk_key     as taskid, ");
        sqlBuilder.append("       task.tk_code    as taskcode, ");
        sqlBuilder.append("       task.tk_title   as tasktitle, ");
        sqlBuilder.append("       scheme.fc_key   as schemeid, ");
        sqlBuilder.append("       scheme.fc_code  as schemecode, ");
        sqlBuilder.append("       scheme.fc_title as schemetitle ");
        sqlBuilder.append("from gc_conv_system_ts  ts ");
        sqlBuilder.append("         left join NR_PARAM_TASK  task on ts.taskid = task.tk_key ");
        sqlBuilder.append("         left join NR_PARAM_FORMSCHEME  scheme on ts.schemeid = scheme.fc_key ");
        sqlBuilder.append("order by ts.createTime ");
        List list = this.jdbcTemplate.queryForList(sqlBuilder.toString());
        List<Object> systemTaskSchemeVOs = new ArrayList<ConversionSystemTaskSchemeVO>();
        if (CollectionUtils.isEmpty(list)) {
            return systemTaskSchemeVOs;
        }
        systemTaskSchemeVOs = list.stream().map(mapper -> {
            ConversionSystemTaskSchemeVO systemTaskSchemeVO = new ConversionSystemTaskSchemeVO();
            systemTaskSchemeVO.setSchemeTaskId(ConverterUtils.getAsString(mapper.get("SCHEMETASKID"), (String)""));
            systemTaskSchemeVO.setSystemId(ConverterUtils.getAsString(mapper.get("SYSTEMID"), (String)""));
            systemTaskSchemeVO.setRateSchemeCode(ConverterUtils.getAsString(mapper.get("RATESCHEMECODE"), (String)""));
            systemTaskSchemeVO.setTaskId(ConverterUtils.getAsString(mapper.get("TASKID")));
            systemTaskSchemeVO.setTaskCode(ConverterUtils.getAsString(mapper.get("TASKCODE"), (String)"\u65e0"));
            systemTaskSchemeVO.setTaskTitle(ConverterUtils.getAsString(mapper.get("TASKTITLE"), (String)"\u65e0"));
            systemTaskSchemeVO.setSchemeId(ConverterUtils.getAsString(mapper.get("SCHEMEID")));
            systemTaskSchemeVO.setSchemeCode(ConverterUtils.getAsString(mapper.get("SCHEMECODE"), (String)"\u65e0"));
            systemTaskSchemeVO.setSchemeTitle(ConverterUtils.getAsString(mapper.get("SCHEMETITLE"), (String)"\u65e0"));
            systemTaskSchemeVO.setCreatetime(this.getAsDate(mapper.get("CREATETIME")));
            return systemTaskSchemeVO;
        }).collect(Collectors.toList());
        return systemTaskSchemeVOs;
    }

    private Date getAsDate(Object dateObject) {
        if (dateObject == null) {
            return null;
        }
        Date date = null;
        if (dateObject instanceof Date) {
            date = (Date)dateObject;
        } else if (dateObject instanceof LocalDateTime) {
            date = Date.from(((LocalDateTime)dateObject).atZone(ZoneId.systemDefault()).toInstant());
        } else if (dateObject instanceof LocalDate) {
            date = Date.from(((LocalDate)dateObject).atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else {
            throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + dateObject.getClass());
        }
        return date;
    }

    @Override
    public ConversionSystemEO querySystemByName(String systemName) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(ConversionSystemEO.class, (String)"scheme") + "  from  " + "GC_CONV_SYSTEM" + "   scheme  \n  where   1=1 \n  and   scheme.systemName = ? \n";
        List conversionSystemEOs = this.selectEntity(sql, new Object[]{systemName});
        if (CollectionUtils.isEmpty(conversionSystemEOs)) {
            return null;
        }
        return (ConversionSystemEO)((Object)conversionSystemEOs.get(0));
    }

    @Override
    public ConversionSystemEO querySystemById(String systemId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(ConversionSystemEO.class, (String)"scheme") + "  from  " + "GC_CONV_SYSTEM" + "   scheme  \n  where   1=1 \n  and   scheme.id = ? \n";
        List conversionSystemEOs = this.selectEntity(sql, new Object[]{systemId});
        if (CollectionUtils.isEmpty(conversionSystemEOs)) {
            return null;
        }
        return (ConversionSystemEO)((Object)conversionSystemEOs.get(0));
    }
}

