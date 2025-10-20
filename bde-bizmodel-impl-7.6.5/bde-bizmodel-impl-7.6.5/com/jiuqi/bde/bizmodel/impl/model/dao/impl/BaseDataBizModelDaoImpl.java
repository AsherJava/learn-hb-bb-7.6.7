/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.StopFlagEnum
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 */
package com.jiuqi.bde.bizmodel.impl.model.dao.impl;

import com.jiuqi.bde.bizmodel.impl.model.dao.BaseDataBizModelDao;
import com.jiuqi.bde.bizmodel.impl.model.entity.BaseDataBizModelEO;
import com.jiuqi.dc.base.common.enums.StopFlagEnum;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDataBizModelDaoImpl
extends BaseDataCenterDaoImpl
implements BaseDataBizModelDao {
    @Override
    public List<BaseDataBizModelEO> loadAll() {
        String sql = "  SELECT DIM.ID,DIM.CODE,DIM.NAME,DIM.COMPUTATIONMODELCODE,DIM.STOPFLAG,DIM.ORDINAL,DIM.BASEDATADEFINE,DIM.FETCHFIELDS  FROM BDE_BIZMODEL_BASEDATA DIM  ORDER BY ORDINAL";
        List baseDataBizModelDTOS = this.getJdbcTemplate().query(sql, (rs, row) -> {
            BaseDataBizModelEO baseDataBizModelDTO = new BaseDataBizModelEO();
            baseDataBizModelDTO.setId(rs.getString(1));
            baseDataBizModelDTO.setCode(rs.getString(2));
            baseDataBizModelDTO.setName(rs.getString(3));
            baseDataBizModelDTO.setComputationModelCode(rs.getString(4));
            baseDataBizModelDTO.setStopFlag(rs.getInt(5));
            baseDataBizModelDTO.setOrdinal(rs.getBigDecimal(6));
            baseDataBizModelDTO.setBaseDataDefine(rs.getString(7));
            baseDataBizModelDTO.setFetchFields(rs.getString(8));
            return baseDataBizModelDTO;
        });
        return baseDataBizModelDTOS;
    }

    @Override
    public void save(BaseDataBizModelEO baseDataBizModelData) {
        String sql = "INSERT INTO BDE_BIZMODEL_BASEDATA (ID,CODE,NAME,COMPUTATIONMODELCODE,STOPFLAG,ORDINAL,BASEDATADEFINE,FETCHFIELDS) VALUES(?,?,?,?,?,?,?,?)";
        Object[] args = new Object[]{baseDataBizModelData.getId(), baseDataBizModelData.getCode(), baseDataBizModelData.getName(), baseDataBizModelData.getComputationModelCode(), StopFlagEnum.START.getCode(), System.currentTimeMillis(), baseDataBizModelData.getBaseDataDefine(), baseDataBizModelData.getFetchFields()};
        this.getJdbcTemplate().update(sql, args);
    }

    @Override
    public void update(BaseDataBizModelEO baseDataBizModelData) {
        String sql = "UPDATE BDE_BIZMODEL_BASEDATA SET COMPUTATIONMODELCODE = ? ,NAME = ? ,BASEDATADEFINE = ? ,FETCHFIELDS = ? WHERE ID = ?";
        Object[] args = new Object[]{baseDataBizModelData.getComputationModelCode(), baseDataBizModelData.getName(), baseDataBizModelData.getBaseDataDefine(), baseDataBizModelData.getFetchFields(), baseDataBizModelData.getId()};
        this.getJdbcTemplate().update(sql, args);
    }
}

