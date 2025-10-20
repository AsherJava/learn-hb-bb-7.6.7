/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.organization.dao;

import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaOrgVersionDao
extends BaseOptMapper<OrgVersionDO> {
    @SelectProvider(type=OrgVersionDaoProvider.class, method="findByCondition")
    public OrgVersionDO find(OrgVersionDTO var1);

    @SelectProvider(type=OrgVersionDaoProvider.class, method="findByCondition")
    public List<OrgVersionDO> list(OrgVersionDTO var1);

    @SelectProvider(type=OrgVersionDaoProvider.class, method="dataExist")
    public int dataExist(OrgVersionDO var1);

    @SelectProvider(type=OrgVersionDaoProvider.class, method="validtimeExist")
    public int validtimeExist(OrgVersionDO var1);

    @SelectProvider(type=OrgVersionDaoProvider.class, method="invalidtimeExist")
    public int invalidtimeExist(OrgVersionDO var1);

    @DeleteProvider(type=OrgVersionDaoProvider.class, method="removeData")
    public int removeData(OrgVersionDO var1);

    @UpdateProvider(type=OrgVersionDaoProvider.class, method="updateLastInvalidtime")
    public int updateLastInvalidtime(OrgVersionDO var1);

    @UpdateProvider(type=OrgVersionDaoProvider.class, method="updateNextValidtime")
    public int updateNextValidtime(OrgVersionDO var1);

    public static class OrgVersionDaoProvider {
        public String findByCondition(OrgVersionDTO param) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("MD_ORG_VERSION");
            if (StringUtils.hasText(param.getCategoryname())) {
                sql.WHERE("CATEGORYNAME = #{categoryname}");
            } else {
                sql.WHERE("CATEGORYNAME ='MD_ORG'");
            }
            if (param.getId() != null) {
                sql.WHERE("ID = #{id, jdbcType=VARCHAR}");
            }
            if (StringUtils.hasText(param.getTitle())) {
                sql.WHERE("TITLE = #{title}");
            }
            if (param.getValidtime() != null) {
                sql.WHERE("VALIDTIME = #{validtime, jdbcType=TIMESTAMP}");
            }
            if (param.getInvalidtime() != null) {
                sql.WHERE("INVALIDTIME = #{invalidtime, jdbcType=TIMESTAMP}");
            }
            if (param.getVersionDate() != null) {
                sql.WHERE("VALIDTIME <= #{versionDate, jdbcType=TIMESTAMP}");
                sql.WHERE("INVALIDTIME > #{versionDate, jdbcType=TIMESTAMP}");
            }
            sql.ORDER_BY("VALIDTIME desc");
            return sql.toString();
        }

        public String dataExist(OrgVersionDO param) {
            return "select count(0) as cnt from " + param.getCategoryname() + " where VALIDTIME = #{validtime, jdbcType=TIMESTAMP} or INVALIDTIME = #{validtime, jdbcType=TIMESTAMP}";
        }

        public String validtimeExist(OrgVersionDO param) {
            return "select count(0) as cnt from " + param.getCategoryname() + " where VALIDTIME = #{validtime, jdbcType=TIMESTAMP}";
        }

        public String invalidtimeExist(OrgVersionDO param) {
            return "select count(0) as cnt from " + param.getCategoryname() + " where INVALIDTIME = #{validtime, jdbcType=TIMESTAMP}";
        }

        public String removeData(OrgVersionDO param) {
            return "delete from " + param.getCategoryname() + " where VALIDTIME = #{validtime, jdbcType=TIMESTAMP} and INVALIDTIME = #{invalidtime, jdbcType=TIMESTAMP}";
        }

        public String updateLastInvalidtime(OrgVersionDO param) {
            return "update " + param.getCategoryname() + " set VER=" + System.currentTimeMillis() + ",INVALIDTIME = #{invalidtime, jdbcType=TIMESTAMP} where INVALIDTIME = #{validtime, jdbcType=TIMESTAMP}";
        }

        public String updateNextValidtime(OrgVersionDO param) {
            return "update " + param.getCategoryname() + " set VER=" + System.currentTimeMillis() + ",VALIDTIME = #{invalidtime, jdbcType=TIMESTAMP} where VALIDTIME = #{validtime, jdbcType=TIMESTAMP}";
        }
    }
}

