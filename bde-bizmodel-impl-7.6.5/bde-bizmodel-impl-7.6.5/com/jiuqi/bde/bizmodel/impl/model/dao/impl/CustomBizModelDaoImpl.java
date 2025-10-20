/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.StopFlagEnum
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 */
package com.jiuqi.bde.bizmodel.impl.model.dao.impl;

import com.jiuqi.bde.bizmodel.impl.model.dao.CustomBizModelDao;
import com.jiuqi.bde.bizmodel.impl.model.entity.CustomBizModelEO;
import com.jiuqi.dc.base.common.enums.StopFlagEnum;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CustomBizModelDaoImpl
extends BaseDataCenterDaoImpl
implements CustomBizModelDao {
    @Override
    public List<CustomBizModelEO> loadAll() {
        String sql = "  SELECT DIM.ID,DIM.CODE,DIM.NAME,DIM.COMPUTATIONMODELCODE,DIM.STOPFLAG,DIM.ORDINAL,DIM.FETCHTABLE,DIM.FETCHFIELDS,DIM.FIXEDCONDITION,DIM.CUSTOMCONDITION,DIM.APPLYSCOPE,DIM.DATASOURCECODE,DIM.QUERYTEMPLATEID  FROM BDE_BIZMODEL_CUSTOM DIM  ORDER BY ORDINAL";
        List customBizModelDTOS = this.getJdbcTemplate().query(sql, (rs, row) -> {
            CustomBizModelEO customBizModelDTO = new CustomBizModelEO();
            customBizModelDTO.setId(rs.getString(1));
            customBizModelDTO.setCode(rs.getString(2));
            customBizModelDTO.setName(rs.getString(3));
            customBizModelDTO.setComputationModelCode(rs.getString(4));
            customBizModelDTO.setStopFlag(rs.getInt(5));
            customBizModelDTO.setOrdinal(rs.getBigDecimal(6));
            customBizModelDTO.setFetchTable(rs.getString(7));
            customBizModelDTO.setFetchFields(rs.getString(8));
            customBizModelDTO.setFixedCondition(rs.getString(9));
            customBizModelDTO.setCustomCondition(rs.getString(10));
            customBizModelDTO.setApplyScope(rs.getString(11));
            customBizModelDTO.setDataSourceCode(rs.getString(12));
            customBizModelDTO.setQueryTemplateId(rs.getString(13));
            return customBizModelDTO;
        });
        return customBizModelDTOS;
    }

    @Override
    public void save(CustomBizModelEO customBizModelData) {
        String sql = "INSERT INTO BDE_BIZMODEL_CUSTOM (ID,CODE,NAME,COMPUTATIONMODELCODE,STOPFLAG,ORDINAL,FETCHTABLE,FETCHFIELDS,FIXEDCONDITION,CUSTOMCONDITION,APPLYSCOPE,DATASOURCECODE,QUERYTEMPLATEID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] args = new Object[]{customBizModelData.getId(), customBizModelData.getCode(), customBizModelData.getName(), customBizModelData.getComputationModelCode(), StopFlagEnum.START.getCode(), System.currentTimeMillis(), customBizModelData.getFetchTable(), customBizModelData.getFetchFields(), customBizModelData.getFixedCondition(), customBizModelData.getCustomCondition(), customBizModelData.getApplyScope(), customBizModelData.getDataSourceCode(), customBizModelData.getQueryTemplateId()};
        this.getJdbcTemplate().update(sql, args);
    }

    @Override
    public void update(CustomBizModelEO customBizModelData) {
        String sql = "UPDATE BDE_BIZMODEL_CUSTOM SET COMPUTATIONMODELCODE = ? ,NAME = ? ,FETCHTABLE = ? ,FETCHFIELDS = ? ,FIXEDCONDITION = ?,CUSTOMCONDITION = ?,APPLYSCOPE = ? ,DATASOURCECODE = ? ,QUERYTEMPLATEID = ? WHERE ID = ?";
        Object[] args = new Object[]{customBizModelData.getComputationModelCode(), customBizModelData.getName(), customBizModelData.getFetchTable(), customBizModelData.getFetchFields(), customBizModelData.getFixedCondition(), customBizModelData.getCustomCondition(), customBizModelData.getApplyScope(), customBizModelData.getDataSourceCode(), customBizModelData.getQueryTemplateId(), customBizModelData.getId()};
        this.getJdbcTemplate().update(sql, args);
    }
}

