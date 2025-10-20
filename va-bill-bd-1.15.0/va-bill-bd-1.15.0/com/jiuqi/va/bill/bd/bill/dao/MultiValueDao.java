/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.SelectProvider
 */
package com.jiuqi.va.bill.bd.bill.dao;

import com.jiuqi.va.bill.bd.bill.domain.MultiValueDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.util.StringUtils;

public interface MultiValueDao
extends BaseOptMapper<MultiValueDTO> {
    @SelectProvider(type=MultiValueDaoProvider.class, method="getMultiValue")
    public List<MultiValueDTO> getMultiValue(MultiValueDTO var1);

    public static class MultiValueDaoProvider {
        public String getMultiValue(MultiValueDTO requestDTO) {
            StringBuilder querySql = new StringBuilder();
            querySql.append("SELECT T.ID,T.GROUPID,T.MASTERID,T.BINDINGID,T.BINDINGVALUE,T.ORDERNUM ");
            querySql.append("FROM ").append(requestDTO.getSrcTablename()).append("_M").append(" T ");
            querySql.append("WHERE 1=1 ");
            if (StringUtils.hasText(requestDTO.getGroupid())) {
                querySql.append("AND T.GROUPID = '").append(requestDTO.getGroupid()).append("' ");
            }
            if (StringUtils.hasText(requestDTO.getBindingid())) {
                querySql.append("AND T.BINDINGID = '").append(requestDTO.getBindingid()).append("' ");
            } else {
                querySql.append("AND 1 > 1 ");
            }
            if (StringUtils.hasText(requestDTO.getId())) {
                querySql.append("AND T.ID = '").append(requestDTO.getId()).append("' ");
            }
            if (StringUtils.hasText(requestDTO.getMasterid())) {
                querySql.append("AND T.MASTERID = '").append(requestDTO.getMasterid()).append("' ");
            }
            return querySql.toString();
        }
    }
}

