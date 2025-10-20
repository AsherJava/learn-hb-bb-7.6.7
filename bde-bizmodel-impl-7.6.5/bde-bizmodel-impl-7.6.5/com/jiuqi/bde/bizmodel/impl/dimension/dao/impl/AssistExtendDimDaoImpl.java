/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.enums.StopFlagEnum
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.bde.bizmodel.impl.dimension.dao.impl;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.bizmodel.impl.dimension.dao.AssistExtendDimDao;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.enums.StopFlagEnum;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AssistExtendDimDaoImpl
implements AssistExtendDimDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static final String TABLE_NAME = "BDE_ASSISTEXTENDDIM";

    @Override
    public AssistExtendDimVO getAssistExtendDimByCode(String code) {
        String sql = "SELECT DIM.ID,DIM.ASSISTDIMCODE,DIM.REFFIELD,DIM.CODE,DIM.NAME,DIM.MATCHRULE,DIM.STOPFLAG,DIM.ORDINAL FROM BDE_ASSISTEXTENDDIM DIM WHERE DIM.CODE = ?  ORDER BY ORDINAL ";
        return (AssistExtendDimVO)this.jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                AssistExtendDimVO assistExtendDimVO = new AssistExtendDimVO();
                assistExtendDimVO.setId(rs.getString(1));
                assistExtendDimVO.setAssistDimCode(rs.getString(2));
                assistExtendDimVO.setRefField(rs.getString(3));
                assistExtendDimVO.setCode(rs.getString(4));
                assistExtendDimVO.setName(rs.getString(5));
                assistExtendDimVO.setMatchRule(rs.getString(6));
                assistExtendDimVO.setStopFlagNum(Integer.valueOf(rs.getInt(7)));
                assistExtendDimVO.setOrdinal(rs.getBigDecimal(8));
                return assistExtendDimVO;
            }
            return null;
        }, new Object[]{code});
    }

    @Override
    public void save(AssistExtendDimVO assistExtendDimVO) {
        String sql = "INSERT INTO BDE_ASSISTEXTENDDIM (ID,ASSISTDIMCODE,REFFIELD,CODE,NAME,MATCHRULE,STOPFLAG,ORDINAL)  VALUES(?,?,?,?,?,?,?,?)";
        Object[] args = new Object[]{UUIDUtils.newUUIDStr(), assistExtendDimVO.getAssistDimCode(), assistExtendDimVO.getRefField(), assistExtendDimVO.getCode(), assistExtendDimVO.getName(), assistExtendDimVO.getMatchRule(), StopFlagEnum.START.getCode(), System.currentTimeMillis()};
        this.jdbcTemplate.update(sql, args);
    }

    @Override
    public void update(AssistExtendDimVO assistExtendDimVO) {
        String sql = "UPDATE BDE_ASSISTEXTENDDIM SET MATCHRULE=? WHERE ID=? ";
        this.jdbcTemplate.update(sql, new Object[]{assistExtendDimVO.getMatchRule(), assistExtendDimVO.getId()});
    }

    @Override
    public void updateAssistExtendDimStopFlag(String id, StopFlagEnum stopFlagEnum) {
        String sql = "UPDATE BDE_ASSISTEXTENDDIM SET STOPFLAG=? WHERE ID=? ";
        this.jdbcTemplate.update(sql, new Object[]{stopFlagEnum.getCode(), id});
    }

    @Override
    public List<AssistExtendDimVO> getAllAssistExtendDim() {
        String sql = "SELECT DIM.ID,DIM.ASSISTDIMCODE,DIM.REFFIELD,DIM.CODE,DIM.NAME,DIM.MATCHRULE,DIM.STOPFLAG,DIM.ORDINAL FROM BDE_ASSISTEXTENDDIM DIM ORDER BY ORDINAL DESC";
        return this.jdbcTemplate.query(sql, (rs, row) -> {
            AssistExtendDimVO assistExtendDimVO = new AssistExtendDimVO();
            assistExtendDimVO.setId(rs.getString(1));
            assistExtendDimVO.setAssistDimCode(rs.getString(2));
            assistExtendDimVO.setRefField(rs.getString(3));
            assistExtendDimVO.setCode(rs.getString(4));
            assistExtendDimVO.setName(rs.getString(5));
            assistExtendDimVO.setMatchRule(rs.getString(6));
            assistExtendDimVO.setStopFlagNum(Integer.valueOf(rs.getInt(7)));
            assistExtendDimVO.setOrdinal(rs.getBigDecimal(8));
            return assistExtendDimVO;
        });
    }

    @Override
    public List<AssistExtendDimVO> getAllStartAssistExtendDim() {
        String sql = "SELECT DIM.ID,DIM.ASSISTDIMCODE,DIM.REFFIELD,DIM.CODE,DIM.NAME,DIM.MATCHRULE,DIM.STOPFLAG FROM BDE_ASSISTEXTENDDIM DIM  WHERE DIM.STOPFLAG=0 ORDER BY ORDINAL DESC";
        return this.jdbcTemplate.query(sql, (rs, row) -> {
            AssistExtendDimVO assistExtendDimVO = new AssistExtendDimVO();
            assistExtendDimVO.setId(rs.getString(1));
            assistExtendDimVO.setAssistDimCode(rs.getString(2));
            assistExtendDimVO.setRefField(rs.getString(3));
            assistExtendDimVO.setCode(rs.getString(4));
            assistExtendDimVO.setName(rs.getString(5));
            assistExtendDimVO.setMatchRule(rs.getString(6));
            assistExtendDimVO.setStopFlagNum(Integer.valueOf(rs.getInt(7)));
            return assistExtendDimVO;
        });
    }
}

