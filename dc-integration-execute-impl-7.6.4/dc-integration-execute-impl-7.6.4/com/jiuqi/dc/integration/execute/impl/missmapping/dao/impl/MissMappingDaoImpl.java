/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Table
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.dc.integration.execute.impl.missmapping.dao.impl;

import com.google.common.collect.Table;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.integration.execute.impl.data.DataConvertDim;
import com.jiuqi.dc.integration.execute.impl.missmapping.dao.MissMappingDao;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.shiro.util.ThreadContext;
import org.springframework.stereotype.Repository;

@Repository
public class MissMappingDaoImpl
extends BaseDataCenterDaoImpl
implements MissMappingDao {
    @Override
    public void insertMissMappingData(Table<String, String, Set<String>> missMappingTable, DataConvertDim convertDim, DataSchemeDTO dataSchemeDTO, String taskType) {
        if (Objects.isNull(missMappingTable) || missMappingTable.isEmpty()) {
            return;
        }
        String taskId = (String)ThreadContext.get((Object)"TASKHANDLER_RUNNERID_KEY");
        Date now = DateUtils.now();
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO DC_LOG_MISSMAPPING \n");
        sql.append("(ID, RUNNERID, TASKTYPE, ACCTYEAR, ACCTPERIOD, UNITCODE, DATASCHEMECODE, VCHRID, VCHRNUM, DIMCODE, DIMVALUE) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ArrayList paramList = CollectionUtils.newArrayList();
        missMappingTable.rowMap().forEach((vchrInfo, map) -> {
            String[] vchrInfoArray = vchrInfo.split("\\|\\|");
            map.forEach((dimCode, dimValues) -> {
                for (String dimValue : dimValues) {
                    Object[] params = new Object[11];
                    int index = 0;
                    params[index++] = UUIDUtils.newUUIDStr();
                    params[index++] = taskId;
                    params[index++] = taskType;
                    params[index++] = convertDim.getAcctYear();
                    params[index++] = Objects.equals(taskType, "BizBalanceInitConvert") ? 0 : convertDim.getDcAcctPeriod();
                    params[index++] = convertDim.getUnitCode();
                    params[index++] = dataSchemeDTO.getCode();
                    params[index++] = vchrInfoArray[0];
                    params[index++] = vchrInfoArray[1];
                    params[index++] = dimCode;
                    params[index++] = dimValue;
                    paramList.add(params);
                }
            });
        });
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql.toString(), (List)paramList);
    }
}

