/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.dto.BizModelExtFieldInfo
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.StopFlagEnum
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 */
package com.jiuqi.bde.bizmodel.impl.model.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.impl.model.dao.FinBizModelDao;
import com.jiuqi.bde.bizmodel.impl.model.entity.FinBizModelEO;
import com.jiuqi.bde.common.dto.BizModelExtFieldInfo;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.StopFlagEnum;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import java.util.List;
import org.springframework.stereotype.Component;

@Component(value="finBizModelDao")
public class FinBizModelDaoImpl
extends BaseDataCenterDaoImpl
implements FinBizModelDao {
    @Override
    public List<FinBizModelEO> loadAll() {
        String sql = "  SELECT DIM.ID,DIM.CODE,DIM.NAME,DIM.COMPUTATIONMODELCODE,DIM.STOPFLAG,DIM.ORDINAL,DIM.FETCHTYPES,DIM.DIMENSIONS,DIM.SELECTALL, DIM.EXTENSIONFIELDS  FROM BDE_BIZMODEL_FINDATA DIM  ORDER BY ORDINAL";
        List finBizModelDTOS = this.getJdbcTemplate().query(sql, (rs, row) -> {
            FinBizModelEO finBizModelDTO = new FinBizModelEO();
            finBizModelDTO.setId(rs.getString(1));
            finBizModelDTO.setCode(rs.getString(2));
            finBizModelDTO.setName(rs.getString(3));
            finBizModelDTO.setComputationModelCode(rs.getString(4));
            finBizModelDTO.setStopFlag(rs.getInt(5));
            finBizModelDTO.setOrdinal(rs.getBigDecimal(6));
            finBizModelDTO.setFetchTypes(rs.getString(7));
            finBizModelDTO.setDimensions(rs.getString(8));
            finBizModelDTO.setSelectAll(rs.getInt(9));
            String extensionFieldClob = rs.getString(10);
            if (StringUtils.isEmpty((String)extensionFieldClob)) {
                finBizModelDTO.setBizModelExtFieldInfo(null);
            } else {
                BizModelExtFieldInfo extensionFields = (BizModelExtFieldInfo)JsonUtils.readValue((String)extensionFieldClob, (TypeReference)new TypeReference<BizModelExtFieldInfo>(){});
                finBizModelDTO.setBizModelExtFieldInfo(extensionFields);
            }
            return finBizModelDTO;
        });
        return finBizModelDTOS;
    }

    @Override
    public void save(FinBizModelEO finBizModelData) {
        String sql = "INSERT INTO BDE_BIZMODEL_FINDATA (ID,CODE,NAME,COMPUTATIONMODELCODE,STOPFLAG,ORDINAL,FETCHTYPES,DIMENSIONS,SELECTALL,EXTENSIONFIELDS) VALUES(?,?,?,?,?,?,?,?,?,?)";
        Object[] args = new Object[]{finBizModelData.getId(), finBizModelData.getCode(), finBizModelData.getName(), finBizModelData.getComputationModelCode(), StopFlagEnum.START.getCode(), System.currentTimeMillis(), finBizModelData.getFetchTypes(), finBizModelData.getDimensions(), finBizModelData.getSelectAll(), finBizModelData.getBizModelExtFieldInfo() == null ? null : JsonUtils.writeValueAsString((Object)finBizModelData.getBizModelExtFieldInfo())};
        this.getJdbcTemplate().update(sql, args);
    }

    @Override
    public void update(FinBizModelEO finBizModelData) {
        String sql = "UPDATE BDE_BIZMODEL_FINDATA SET COMPUTATIONMODELCODE = ? ,NAME = ? ,FETCHTYPES = ? ,DIMENSIONS = ? ,SELECTALL = ?, EXTENSIONFIELDS = ? WHERE ID = ?";
        Object[] args = new Object[]{finBizModelData.getComputationModelCode(), finBizModelData.getName(), finBizModelData.getFetchTypes(), finBizModelData.getDimensions(), finBizModelData.getSelectAll(), finBizModelData.getBizModelExtFieldInfo() == null ? null : JsonUtils.writeValueAsString((Object)finBizModelData.getBizModelExtFieldInfo()), finBizModelData.getId()};
        this.getJdbcTemplate().update(sql, args);
    }
}

