/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.CleanUselessAttachmentDTO
 *  com.jiuqi.va.mapper.common.CustomOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.InsertProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.attachment.dao;

import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.CleanUselessAttachmentDTO;
import com.jiuqi.va.attachment.entity.BizDataDO;
import com.jiuqi.va.mapper.common.CustomOptMapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaAttachmentBizDao
extends CustomOptMapper {
    @SelectProvider(type=SqlProvider.class, method="findByCondition")
    public AttachmentBizDTO get(AttachmentBizDO var1);

    @SelectProvider(type=SqlProvider.class, method="getAttNumByQuotecode")
    public int getAttNumByQuotecode(AttachmentBizDO var1);

    @SelectProvider(type=SqlProvider.class, method="findByCondition")
    public List<AttachmentBizDTO> list(AttachmentBizDO var1);

    @SelectProvider(type=SqlProvider.class, method="listAttsAndSchemeTitle")
    public List<AttachmentBizDTO> listAttsAndSchemeTitle(AttachmentBizDO var1);

    @InsertProvider(type=SqlProvider.class, method="insertSql")
    public int add(AttachmentBizDO var1);

    @UpdateProvider(type=SqlProvider.class, method="updateSql")
    public int update(AttachmentBizDO var1);

    @DeleteProvider(type=SqlProvider.class, method="deleteSql")
    public int remove(AttachmentBizDO var1);

    @SelectProvider(type=SqlProvider.class, method="uselessSql")
    public List<AttachmentBizDTO> listUseless(CleanUselessAttachmentDTO var1);

    @UpdateProvider(type=SqlProvider.class, method="updateOrdinal")
    public int updateOrdinal(AttachmentBizDO var1);

    @SelectProvider(type=SqlProvider.class, method="confirmeDeletedSql")
    public List<AttachmentBizDO> listConfirmeDeleted(CleanUselessAttachmentDTO var1);

    @SelectProvider(type=SqlProvider.class, method="bizDataCount")
    public int bizDataCount(BizDataDO var1);

    @SelectProvider(type=SqlProvider.class, method="quoteCodeQuery")
    public List<Map<String, Object>> quoteCodeQuery(BizDataDO var1);

    @UpdateProvider(type=SqlProvider.class, method="updateAllAtt")
    public int updateAllAtt(AttachmentBizDO var1);

    public static class SqlProvider {
        public String bizDataCount(final BizDataDO param) {
            SQL sql = new SQL(){
                {
                    this.SELECT("count(*)");
                    this.FROM(param.getTablenName());
                    this.WHERE("id = #{id}");
                }
            };
            return sql.toString();
        }

        public String quoteCodeQuery(final BizDataDO param) {
            SQL sql = new SQL(){
                {
                    this.SELECT(param.getQuoteCode());
                    this.FROM(param.getTablenName());
                    this.WHERE("id = #{id}");
                }
            };
            return sql.toString();
        }

        public String findByCondition(final AttachmentBizDO param) {
            SQL sql = new SQL(){
                {
                    this.SELECT("*");
                    this.FROM("bizattachment_" + param.getSuffix());
                    if (param.getId() != null) {
                        this.WHERE("id = #{id}");
                    }
                    if (StringUtils.hasText(param.getQuotecode())) {
                        this.WHERE("quotecode = #{quotecode}");
                    }
                    if (param.getStatus() != null) {
                        this.WHERE("status = #{status}");
                    } else {
                        this.WHERE("status in (0, 1, 2)");
                    }
                    if (param.getModename() != null) {
                        this.WHERE("modename = #{modename}");
                    }
                    this.ORDER_BY("ordinal, createtime");
                }
            };
            return sql.toString();
        }

        public String listAttsAndSchemeTitle(AttachmentBizDO param) {
            SQL sql = new SQL();
            sql.SELECT("a.*, b.Title as schemetitle");
            sql.FROM("bizattachment_" + param.getSuffix() + " a");
            if (StringUtils.hasText(param.getQuotecode())) {
                sql.WHERE("a.quotecode = #{quotecode}");
            }
            sql.LEFT_OUTER_JOIN("ATT_SCHEME b on a.schemename = b.name");
            sql.ORDER_BY("a.ordinal, a.createtime");
            return sql.toString();
        }

        public String getAttNumByQuotecode(final AttachmentBizDO param) {
            SQL sql = new SQL(){
                {
                    this.SELECT("COUNT(*)");
                    this.FROM("bizattachment_" + param.getSuffix());
                    if (StringUtils.hasText(param.getQuotecode())) {
                        this.WHERE("quotecode = #{quotecode}");
                    }
                }
            };
            return sql.toString();
        }

        public String insertSql(final AttachmentBizDO param) {
            SQL sql = new SQL(){
                {
                    this.INSERT_INTO("bizattachment_" + param.getSuffix());
                    this.INTO_COLUMNS(new String[]{"id", "name", "quotecode", "filepath", "filesize", "status", "createtime", "createuser", "schemename", "modename", "ordinal", "sourceid"});
                    if (param.getCreatetime() == null) {
                        this.INTO_VALUES(new String[]{"#{id}", "#{name}", "#{quotecode}", "#{filepath}", "#{filesize}", "#{status}", "CURRENT_TIMESTAMP", "#{createuser}", "#{schemename}", "#{modename}", "#{ordinal}", "#{sourceid}"});
                    } else {
                        this.INTO_VALUES(new String[]{"#{id}", "#{name}", "#{quotecode}", "#{filepath}", "#{filesize}", "#{status}", "#{createtime}", "#{createuser}", "#{schemename}", "#{modename}", "#{ordinal}", "#{sourceid}"});
                    }
                }
            };
            return sql.toString();
        }

        public String updateSql(final AttachmentBizDO param) {
            SQL sql = new SQL(){
                {
                    this.UPDATE("bizattachment_" + param.getSuffix());
                    this.SET("status=#{status}");
                    if (param.getId() != null) {
                        this.WHERE("id = #{id}");
                    } else if (StringUtils.hasText(param.getQuotecode())) {
                        this.WHERE("quotecode = #{quotecode}");
                    }
                }
            };
            return sql.toString();
        }

        public String updateAllAtt(final AttachmentBizDO param) {
            SQL sql = new SQL(){
                {
                    this.UPDATE("bizattachment_" + param.getSuffix());
                    this.SET("status=#{status}");
                    this.WHERE("quotecode = #{quotecode}");
                    this.WHERE("status != -1");
                }
            };
            return sql.toString();
        }

        public String deleteSql(final AttachmentBizDO param) {
            SQL sql = new SQL(){
                {
                    this.DELETE_FROM("bizattachment_" + param.getSuffix());
                    if (param.getId() != null) {
                        this.WHERE("id = #{id}");
                    } else if (StringUtils.hasText(param.getQuotecode())) {
                        this.WHERE("quotecode = #{quotecode}");
                    }
                }
            };
            return sql.toString();
        }

        public String uselessSql(final CleanUselessAttachmentDTO param) {
            SQL sql = new SQL(){
                {
                    this.SELECT("*");
                    this.FROM("bizattachment_" + param.getSuffix());
                    ((SQL)this.AND()).WHERE("status = 2");
                    ((SQL)this.AND()).WHERE("createtime < #{endDate}");
                }
            };
            return sql.toString();
        }

        public String confirmeDeletedSql(final CleanUselessAttachmentDTO param) {
            SQL sql = new SQL(){
                {
                    this.SELECT("*");
                    this.FROM("bizattachment_" + param.getSuffix());
                    ((SQL)this.AND()).WHERE("status = 3");
                    ((SQL)this.AND()).WHERE("createtime <= #{endDate}");
                }
            };
            return sql.toString();
        }

        public String updateOrdinal(final AttachmentBizDO param) {
            SQL sql = new SQL(){
                {
                    this.UPDATE("bizattachment_" + param.getSuffix());
                    this.SET("ordinal=#{ordinal}");
                    this.WHERE("id = #{id}");
                }
            };
            return sql.toString();
        }
    }
}

