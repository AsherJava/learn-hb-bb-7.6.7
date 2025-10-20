/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 */
package com.jiuqi.va.attachment.dao;

import com.jiuqi.va.attachment.domain.AttachmentSchemeDO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface VaAttachmentSchemeDao
extends BaseOptMapper<AttachmentSchemeDO> {
    @SelectProvider(type=LDAOProvider.class, method="getAllSchemeList")
    public List<AttachmentSchemeDTO> getAllSchemeList(AttachmentSchemeDTO var1);

    public static class LDAOProvider {
        public String getAllSchemeList(AttachmentSchemeDTO attachmentSchemeDTO) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT scheme.id ,scheme.name ,scheme.title,scheme.storemode,");
            sql.append("  scheme.degree ,scheme.startflag ,scheme.createtime,scheme.createuser, \n");
            sql.append("  scheme.modifytime ,scheme.modifyuser ,scheme.config   \n");
            sql.append(" FROM   ATT_SCHEME   scheme    \n");
            sql.append("  where 1=1    ");
            return sql.toString();
        }
    }
}

