/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 */
package com.jiuqi.bde.bizmodel.impl.model.callback.dao.impl;

import com.jiuqi.bde.bizmodel.impl.model.callback.FetchSourceUpdateEO;
import com.jiuqi.bde.bizmodel.impl.model.callback.dao.BizModelUpdateDao;
import com.jiuqi.bde.bizmodel.impl.model.entity.BaseDataBizModelEO;
import com.jiuqi.bde.bizmodel.impl.model.entity.CustomBizModelEO;
import com.jiuqi.bde.bizmodel.impl.model.entity.FinBizModelEO;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class BizModelUpdateDaoImpl
extends BaseDataCenterDaoImpl
implements BizModelUpdateDao {
    @Override
    public List<FetchSourceUpdateEO> loadAll() {
        String sql = "  SELECT DIM.BIZMODELCODE,DIM.DIMENSIONS,DIM.FETCHTYPES,DIM.CODE,DIM.NAME,dim.STOPFLAG,dim.ORDINAL,dim.BASEDATADEFINE,dim.FIXEDCONDITION,dim.CUSTOMCONDITION   FROM BDE_FETCHSOURCE DIM  ORDER BY ORDINAL,CODE";
        List fetchSourceEOS = this.getJdbcTemplate().query(sql, (rs, row) -> {
            FetchSourceUpdateEO fetchSourceEO = new FetchSourceUpdateEO();
            fetchSourceEO.setBizModelCode(rs.getString(1));
            fetchSourceEO.setDimensions(rs.getString(2));
            fetchSourceEO.setFetchTypes(rs.getString(3));
            fetchSourceEO.setCode(rs.getString(4));
            fetchSourceEO.setName(rs.getString(5));
            fetchSourceEO.setStopFlag(rs.getInt(6));
            fetchSourceEO.setOrdinal(rs.getBigDecimal(7));
            fetchSourceEO.setBaseDataDefine(rs.getString(8));
            fetchSourceEO.setFixedCondition(rs.getString(9));
            fetchSourceEO.setCustomCondition(rs.getString(10));
            return fetchSourceEO;
        });
        return fetchSourceEOS;
    }

    @Override
    public void insertFinBizModel(List<FinBizModelEO> finBizModelDatas) {
        String sql = "INSERT INTO BDE_BIZMODEL_FINDATA (ID,CODE,NAME,COMPUTATIONMODELCODE,STOPFLAG,ORDINAL,FETCHTYPES,DIMENSIONS,SELECTALL) VALUES(?,?,?,?,?,?,?,?,?)";
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (FinBizModelEO finBizModelData : finBizModelDatas) {
            Object[] args = new Object[]{finBizModelData.getId(), finBizModelData.getCode(), finBizModelData.getName(), finBizModelData.getComputationModelCode(), finBizModelData.getStopFlag(), finBizModelData.getOrdinal(), finBizModelData.getFetchTypes(), finBizModelData.getDimensions(), finBizModelData.getSelectAll()};
            batchArgs.add(args);
        }
        this.getJdbcTemplate().batchUpdate(sql, batchArgs);
    }

    @Override
    public void insertCustomBizModel(List<CustomBizModelEO> customBizModelDatas) {
        String sql = "INSERT INTO BDE_BIZMODEL_CUSTOM (ID,CODE,NAME,COMPUTATIONMODELCODE,STOPFLAG,ORDINAL,FETCHTABLE,FETCHFIELDS,FIXEDCONDITION,CUSTOMCONDITION) VALUES(?,?,?,?,?,?,?,?,?,?)";
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (CustomBizModelEO customBizModelData : customBizModelDatas) {
            Object[] args = new Object[]{customBizModelData.getId(), customBizModelData.getCode(), customBizModelData.getName(), customBizModelData.getComputationModelCode(), customBizModelData.getStopFlag(), customBizModelData.getOrdinal(), customBizModelData.getFetchTable(), customBizModelData.getFetchFields(), customBizModelData.getFixedCondition(), customBizModelData.getCustomCondition()};
            batchArgs.add(args);
        }
        this.getJdbcTemplate().batchUpdate(sql, batchArgs);
    }

    @Override
    public void insertBaseDataBizModel(List<BaseDataBizModelEO> baseDataBizModelDatas) {
        String sql = "INSERT INTO BDE_BIZMODEL_BASEDATA (ID,CODE,NAME,COMPUTATIONMODELCODE,STOPFLAG,ORDINAL,BASEDATADEFINE,FETCHFIELDS) VALUES(?,?,?,?,?,?,?,?)";
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (BaseDataBizModelEO baseDataBizModelData : baseDataBizModelDatas) {
            Object[] args = new Object[]{baseDataBizModelData.getId(), baseDataBizModelData.getCode(), baseDataBizModelData.getName(), baseDataBizModelData.getComputationModelCode(), baseDataBizModelData.getStopFlag(), baseDataBizModelData.getOrdinal(), baseDataBizModelData.getBaseDataDefine(), baseDataBizModelData.getFetchFields()};
            batchArgs.add(args);
        }
        this.getJdbcTemplate().batchUpdate(sql, batchArgs);
    }
}

