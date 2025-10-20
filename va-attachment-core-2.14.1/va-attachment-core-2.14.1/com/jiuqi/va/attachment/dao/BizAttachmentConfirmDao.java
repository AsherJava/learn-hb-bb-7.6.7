/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.BizAttachmentConfirmDO
 *  com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.InsertProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.attachment.dao;

import com.jiuqi.va.attachment.domain.BizAttachmentConfirmDO;
import com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface BizAttachmentConfirmDao
extends BaseOptMapper<BizAttachmentConfirmDO> {
    @UpdateProvider(type=BizAttachmentConfirmDaoProvider.class, method="confirmByBizcode")
    public int confirmByBizcode(BizAttachmentConfirmDO var1);

    @InsertProvider(type=BizAttachmentConfirmDaoProvider.class, method="insertData")
    public int insertData(BizAttachmentConfirmDO var1);

    @UpdateProvider(type=BizAttachmentConfirmDaoProvider.class, method="updateUpdateTime")
    public int updateUpdateTime(BizAttachmentConfirmDO var1);

    @UpdateProvider(type=BizAttachmentConfirmDaoProvider.class, method="deleteByQuotecode")
    public int deleteByQuotecode(BizAttachmentConfirmDO var1);

    @SelectProvider(type=BizAttachmentConfirmDaoProvider.class, method="selectByUpdatetime")
    public List<BizAttachmentConfirmDTO> selectByUpdatetime(BizAttachmentConfirmDTO var1);

    public static class BizAttachmentConfirmDaoProvider {
        public String confirmByBizcode(BizAttachmentConfirmDO newData) {
            return "update BIZATTACHMENT_CONFIRM set UPDATETIME = CURRENT_TIMESTAMP, CONFIRMTIME = CURRENT_TIMESTAMP where BIZCODE = #{bizcode}";
        }

        public String insertData(BizAttachmentConfirmDO bizAttachmentConfirmDO) {
            SQL sql = new SQL();
            sql.INSERT_INTO("BIZATTACHMENT_CONFIRM");
            sql.INTO_COLUMNS(new String[]{"ID, QUOTECODE, UPDATETIME, CONFIRMTIME, BIZTYPE, BIZCODE, EXTDATA"});
            sql.INTO_VALUES(new String[]{"#{id}, #{quotecode}, CURRENT_TIMESTAMP, null, #{biztype}, #{bizcode}, #{extdata}"});
            return sql.toString();
        }

        public String updateUpdateTime(BizAttachmentConfirmDO bizAttachmentConfirmDO) {
            return "update BIZATTACHMENT_CONFIRM set UPDATETIME = CURRENT_TIMESTAMP where QUOTECODE = #{quotecode}";
        }

        public String deleteByQuotecode(BizAttachmentConfirmDO bizAttachmentConfirm) {
            return "delete from BIZATTACHMENT_CONFIRM where QUOTECODE = #{quotecode}";
        }

        public String selectByUpdatetime(BizAttachmentConfirmDTO param) {
            SQL sql = new SQL();
            sql.SELECT("ID, QUOTECODE, UPDATETIME, CONFIRMTIME, BIZTYPE, BIZCODE, EXTDATA");
            sql.FROM("BIZATTACHMENT_CONFIRM");
            sql.WHERE("UPDATETIME <= #{updatetime}");
            sql.ORDER_BY("UPDATETIME DESC");
            return sql.toString();
        }
    }
}

