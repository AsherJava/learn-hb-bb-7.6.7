/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bizmeta.dao;

import com.jiuqi.va.bizmeta.domain.bizres.BizResInfoDO;
import com.jiuqi.va.bizmeta.domain.bizres.BizResInfoDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface IBizResInfoDao
extends BaseOptMapper<BizResInfoDO> {
    @SelectProvider(type=IBizResInfoDaoProvider.class, method="resInfoList")
    public List<BizResInfoDTO> list(BizResInfoDTO var1);

    public static class IBizResInfoDaoProvider {
        public String resInfoList(BizResInfoDTO bizResInfoDTO) {
            SQL sql = new SQL();
            sql.SELECT("a.id,a.ver,a.groupname,a.resname,a.filesize,a.uploadtime,a.etag,b.title");
            sql.FROM("biz_res_info a");
            sql.JOIN("biz_res_group b on a.groupname = b.name");
            sql.WHERE("1=1");
            if (StringUtils.hasText(bizResInfoDTO.getSearchKey())) {
                ((SQL)sql.AND()).WHERE("a.resname like concat('%',concat(#{searchKey},'%'))");
            }
            if (bizResInfoDTO.getGroupnames() != null && !"[ZYGL]".equals(bizResInfoDTO.getGroupnames().toString())) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bizResInfoDTO.getGroupnames().size() - 1; ++i) {
                    sb.append("'" + bizResInfoDTO.getGroupnames().get(i) + "',");
                }
                sb.append("'" + bizResInfoDTO.getGroupnames().get(bizResInfoDTO.getGroupnames().size() - 1) + "'");
                ((SQL)sql.AND()).WHERE("a.groupname in (" + sb.toString() + ")");
            }
            sql.ORDER_BY("a.uploadtime desc");
            return sql.toString();
        }
    }
}

