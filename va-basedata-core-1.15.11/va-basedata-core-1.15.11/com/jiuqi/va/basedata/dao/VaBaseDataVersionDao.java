/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.basedata.dao;

import com.jiuqi.va.basedata.common.FormatValidationUtil;
import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaBaseDataVersionDao
extends BaseOptMapper<BaseDataVersionDO> {
    @SelectProvider(type=SqlProvider.class, method="findByCondition")
    public BaseDataVersionDO get(BaseDataVersionDTO var1);

    @SelectProvider(type=SqlProvider.class, method="findByCondition")
    public List<BaseDataVersionDO> list(BaseDataVersionDTO var1);

    @SelectProvider(type=SqlProvider.class, method="dataExist")
    public int dataExist(BaseDataVersionDO var1);

    @SelectProvider(type=SqlProvider.class, method="validtimeExist")
    public int validtimeExist(BaseDataVersionDO var1);

    @SelectProvider(type=SqlProvider.class, method="invalidtimeExist")
    public int invalidtimeExist(BaseDataVersionDO var1);

    @DeleteProvider(type=SqlProvider.class, method="removeData")
    public int removeData(BaseDataVersionDO var1);

    @UpdateProvider(type=SqlProvider.class, method="updateLastInvalidtime")
    public int updateLastInvalidtime(BaseDataVersionDO var1);

    @UpdateProvider(type=SqlProvider.class, method="updateNextValidtime")
    public int updateNextValidtime(BaseDataVersionDO var1);

    public static class SqlProvider {
        public String findByCondition(BaseDataVersionDTO param) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("BASEDATA_VERSION");
            sql.WHERE("TABLENAME = #{tablename}");
            if (param.getId() != null) {
                sql.WHERE("ID = #{id}");
            }
            if (StringUtils.hasText(param.getName())) {
                sql.WHERE("NAME = #{name}");
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

        public String dataExist(BaseDataVersionDO baseDataVersionDO) {
            return "select count(0) as cnt from " + FormatValidationUtil.checkInjection(baseDataVersionDO.getTablename()) + " where VALIDTIME = #{validtime, jdbcType=TIMESTAMP} or INVALIDTIME = #{validtime, jdbcType=TIMESTAMP}";
        }

        public String validtimeExist(BaseDataVersionDO param) {
            return "select count(0) as cnt from " + param.getTablename() + " where VALIDTIME = #{validtime, jdbcType=TIMESTAMP}";
        }

        public String invalidtimeExist(BaseDataVersionDO param) {
            return "select count(0) as cnt from " + param.getTablename() + " where INVALIDTIME = #{validtime, jdbcType=TIMESTAMP}";
        }

        public String removeData(BaseDataVersionDO param) {
            return "delete from " + param.getTablename() + " where VALIDTIME = #{validtime, jdbcType=TIMESTAMP} and INVALIDTIME = #{invalidtime, jdbcType=TIMESTAMP}";
        }

        public String updateLastInvalidtime(BaseDataVersionDO param) {
            return "update " + param.getTablename() + " set VER=" + System.currentTimeMillis() + ",INVALIDTIME = #{invalidtime, jdbcType=TIMESTAMP} where INVALIDTIME = #{validtime, jdbcType=TIMESTAMP}";
        }

        public String updateNextValidtime(BaseDataVersionDO param) {
            return "update " + param.getTablename() + " set VER=" + System.currentTimeMillis() + ",VALIDTIME = #{invalidtime, jdbcType=TIMESTAMP} where VALIDTIME = #{validtime, jdbcType=TIMESTAMP}";
        }
    }
}

