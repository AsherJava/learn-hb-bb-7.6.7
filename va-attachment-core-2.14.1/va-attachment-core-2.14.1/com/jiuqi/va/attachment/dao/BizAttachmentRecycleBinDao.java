/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDO
 *  com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 *  org.apache.shiro.util.StringUtils
 */
package com.jiuqi.va.attachment.dao;

import com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDO;
import com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.util.StringUtils;

@Mapper
public interface BizAttachmentRecycleBinDao
extends BaseOptMapper<BizAttachmentRecycleBinDO> {
    @SelectProvider(type=BizAttachmentRecycleBinDaoProvider.class, method="selectByDeleteTime")
    public List<BizAttachmentRecycleBinDO> selectByDeleteTime(BizAttachmentRecycleBinDO var1);

    @SelectProvider(type=BizAttachmentRecycleBinDaoProvider.class, method="listErrorData")
    public List<BizAttachmentRecycleBinDTO> listErrorData(BizAttachmentRecycleBinDTO var1);

    @SelectProvider(type=BizAttachmentRecycleBinDaoProvider.class, method="listNothingData")
    public List<BizAttachmentRecycleBinDTO> listNothingData(BizAttachmentRecycleBinDTO var1);

    @DeleteProvider(type=BizAttachmentRecycleBinDaoProvider.class, method="deleteById")
    public int deleteById(BizAttachmentRecycleBinDO var1);

    @UpdateProvider(type=BizAttachmentRecycleBinDaoProvider.class, method="updateDeleteTypeByPrimaryKey")
    public int updateDeleteTypeByPrimaryKey(BizAttachmentRecycleBinDTO var1);

    @SelectProvider(type=BizAttachmentRecycleBinDaoProvider.class, method="selectNoThingCount")
    public int selectNoThingCount(BizAttachmentRecycleBinDO var1);

    @SelectProvider(type=BizAttachmentRecycleBinDaoProvider.class, method="selectErrorCount")
    public int selectErrorCount(BizAttachmentRecycleBinDO var1);

    public static class BizAttachmentRecycleBinDaoProvider {
        public String selectByDeleteTime(BizAttachmentRecycleBinDO bizAttachmentRecycleBinDO) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("BIZATTACHMENT_RECYCLE_BIN");
            sql.WHERE("DELETETIME < #{deletetime}");
            sql.WHERE("DELETETYPE = 0");
            sql.ORDER_BY("DELETETIME DESC");
            return sql.toString();
        }

        public String listErrorData(BizAttachmentRecycleBinDTO bizAttachmentRecycleBinDTO) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("BIZATTACHMENT_RECYCLE_BIN");
            if (bizAttachmentRecycleBinDTO.getSearchDeletetype() != null) {
                sql.WHERE("DELETETYPE = #{searchDeletetype}");
            } else {
                sql.WHERE("(DELETETYPE = 1 or DELETETYPE = 2)");
            }
            return this.getSearchSql(bizAttachmentRecycleBinDTO, sql);
        }

        public String listNothingData(BizAttachmentRecycleBinDTO bizAttachmentRecycleBinDTO) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("BIZATTACHMENT_RECYCLE_BIN");
            if (bizAttachmentRecycleBinDTO.getSearchDeletetype() != null) {
                sql.WHERE("DELETETYPE = #{searchDeletetype}");
            } else {
                sql.WHERE("DELETETYPE = 3");
            }
            return this.getSearchSql(bizAttachmentRecycleBinDTO, sql);
        }

        private String getSearchSql(BizAttachmentRecycleBinDTO bizAttachmentRecycleBinDTO, SQL sql) {
            String search;
            if (StringUtils.hasText((String)bizAttachmentRecycleBinDTO.getSearchQuoteId())) {
                search = "concat(concat('%', #{searchQuoteId, jdbcType=VARCHAR}), '%')";
                sql.WHERE("QUOTEID like " + search);
            }
            if (StringUtils.hasText((String)bizAttachmentRecycleBinDTO.getSearchFileName())) {
                search = "concat(concat('%', #{searchFileName, jdbcType=VARCHAR}), '%')";
                sql.WHERE("FILENAME like " + search);
            }
            if (StringUtils.hasText((String)bizAttachmentRecycleBinDTO.getSearchQuoteCode())) {
                search = "concat(concat('%', #{searchQuoteCode, jdbcType=VARCHAR}), '%')";
                sql.WHERE("QUOTECODE like " + search);
            }
            sql.ORDER_BY("DELETETIME DESC");
            return sql.toString();
        }

        public String deleteById(BizAttachmentRecycleBinDO bizAttachmentRecycleBinDO) {
            SQL sql = new SQL();
            sql.DELETE_FROM("BIZATTACHMENT_RECYCLE_BIN");
            sql.WHERE("QUOTEID = #{quoteid}");
            return sql.toString();
        }

        public String updateDeleteTypeByPrimaryKey(BizAttachmentRecycleBinDTO update) {
            SQL sql = new SQL();
            sql.UPDATE("BIZATTACHMENT_RECYCLE_BIN");
            sql.SET("DELETETYPE = #{deletetype}");
            sql.WHERE("ID = #{id}");
            return sql.toString();
        }

        public String selectNoThingCount(BizAttachmentRecycleBinDO bizAttachmentRecycleBinDO) {
            SQL sql = new SQL();
            sql.SELECT("count(1)");
            sql.FROM("BIZATTACHMENT_RECYCLE_BIN");
            sql.WHERE("DELETETYPE = 3");
            return sql.toString();
        }

        public String selectErrorCount(BizAttachmentRecycleBinDO bizAttachmentRecycleBinDO) {
            SQL sql = new SQL();
            sql.SELECT("count(1)");
            sql.FROM("BIZATTACHMENT_RECYCLE_BIN");
            sql.WHERE("DELETETYPE = 1 or DELETETYPE = 2");
            return sql.toString();
        }
    }
}

