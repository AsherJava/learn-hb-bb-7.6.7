/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentModeDO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.attachment.dao;

import com.jiuqi.va.attachment.domain.AttachmentModeDO;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaAttachmentModeDao
extends BaseOptMapper<AttachmentModeDO> {
    @SelectProvider(type=LDAOProvider.class, method="getAttList")
    public List<AttachmentModeDTO> getAttList(AttachmentModeDO var1);

    @SelectProvider(type=LDAOProvider.class, method="getAllAttList")
    public List<AttachmentModeDTO> getAllAttList(AttachmentModeDO var1);

    @SelectProvider(type=LDAOProvider.class, method="getAttMode")
    public AttachmentModeDTO getAttMode(AttachmentModeDO var1);

    @SelectProvider(type=LDAOProvider.class, method="getAttModeByID")
    public AttachmentModeDTO getAttModeByID(AttachmentModeDO var1);

    public static class LDAOProvider {
        public String getAllAttList(AttachmentModeDO attachmentModeDO) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT attmode.id,attmode.name,attmode.title,attmode.schemename,");
            sql.append("attmode.atttype,attmode.attpath,attmode.attsize,attmode.attnum,");
            sql.append("attmode.startflag,attscheme.storemode,attscheme.title as schemetitle,attmode.config,attmode.defaultflag,templatefile.name as templatename ");
            sql.append(" FROM att_mode attmode");
            sql.append(" left join ATT_SCHEME attscheme on attmode.schemename=attscheme.name");
            sql.append(" left join bizattachment_templatefile templatefile on templatefile.masterid=attmode.id");
            return sql.toString();
        }

        public String getAttList(AttachmentModeDO attachmentModeDO) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT attmode.id,attmode.name,attmode.title,attmode.schemename,");
            sql.append("attmode.atttype,attmode.attpath,attmode.attsize,attmode.attnum,");
            sql.append("attmode.startflag,attscheme.storemode,attscheme.title as schemetitle, attmode.config, attmode.defaultflag, templatefile.name as templatename");
            sql.append(" FROM att_mode attmode");
            sql.append(" left join ATT_SCHEME attscheme on attmode.schemename=attscheme.name");
            sql.append(" left join bizattachment_templatefile templatefile on templatefile.masterid=attmode.id");
            sql.append(" where attmode.startflag=1");
            sql.append(" order by attscheme.title,attmode.ver asc");
            return sql.toString();
        }

        public String getAttMode(AttachmentModeDO attachmentModeDO) {
            SQL sql = new SQL();
            sql.SELECT(" attmode.id,attmode.name,attmode.title,attmode.schemename,attmode.atttype,attmode.attpath,attmode.attsize,attmode.attnum,attmode.startflag,attscheme.storemode, attmode.config, attmode.defaultflag, attscheme.config as schemeconfig");
            sql.FROM("att_mode  attmode");
            sql.LEFT_OUTER_JOIN("ATT_SCHEME  attscheme on attmode.schemename=attscheme.name");
            ((SQL)sql.AND()).WHERE("attmode.startflag=1");
            if (StringUtils.hasText(attachmentModeDO.getName())) {
                ((SQL)sql.AND()).WHERE("attmode.name= #{name}");
            }
            return sql.toString();
        }

        public String getAttModeByID(AttachmentModeDO attachmentModeDO) {
            SQL sql = new SQL();
            sql.SELECT(" attmode.id,attmode.name,attmode.title,attmode.schemename,attmode.atttype,attmode.attpath,attmode.attsize,attmode.attnum,attmode.startflag,attscheme.storemode, attmode.config, attmode.defaultflag");
            sql.FROM("att_mode  attmode");
            sql.LEFT_OUTER_JOIN("ATT_SCHEME  attscheme on attmode.schemename=attscheme.name");
            ((SQL)sql.AND()).WHERE("attmode.startflag=1");
            if (StringUtils.hasText(attachmentModeDO.getName())) {
                ((SQL)sql.AND()).WHERE("attmode.name= #{name}");
            }
            if (StringUtils.hasText(attachmentModeDO.getId().toString())) {
                ((SQL)sql.AND()).WHERE("attmode.id= #{id}");
            }
            return sql.toString();
        }
    }
}

