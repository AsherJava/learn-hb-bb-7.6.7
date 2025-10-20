/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizRefDO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.attachment.dao;

import com.jiuqi.va.attachment.domain.AttachmentBizRefDO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface VaAttachmentBizRefDao
extends BaseOptMapper<AttachmentBizRefDO> {
    @UpdateProvider(type=SqlProvider.class, method="addRefCount")
    public int addRefCount(AttachmentBizRefDO var1);

    @DeleteProvider(type=SqlProvider.class, method="deleteRefCount")
    public int deleteRefCount(AttachmentBizRefDO var1);

    public static class SqlProvider {
        public String addRefCount(AttachmentBizRefDO param) {
            SQL sql = new SQL();
            sql.UPDATE("ATT_BIZ_REF");
            sql.SET("refcount = refcount + 1");
            sql.WHERE("ID = #{id}");
            return sql.toString();
        }

        public String deleteRefCount(AttachmentBizRefDO param) {
            SQL sql = new SQL();
            sql.UPDATE("ATT_BIZ_REF");
            sql.SET("refcount = refcount - 1");
            sql.WHERE("ID = #{id}");
            return sql.toString();
        }
    }
}

