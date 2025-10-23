/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.param.transfer.definition.dao;

import com.jiuqi.nr.param.transfer.definition.dao.AttachmentRuleDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AttachmentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    final String dlKeyFieldName = "DL_KEY";
    final String bdDataFieldName = "BD_DATA";
    final String code = "'ATTACHMENT'";

    public List<AttachmentRuleDTO> getAttachments(String formKey) {
        String sql = "select a.DL_KEY,b.BD_DATA from NR_PARAM_DATALINK_DES a left join NR_PARAM_BIGDATATABLE_DES b on a.DL_KEY = b.BD_KEY inner join NR_PARAM_DATAREGION_DES c on a.DL_REGION_KEY = c.DR_KEY where b.BD_CODE = 'ATTACHMENT' and c.DR_FORM_KEY = ?";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            AttachmentRuleDTO atta = new AttachmentRuleDTO();
            atta.setDlKey(rs.getString("DL_KEY"));
            atta.setAttachment(rs.getBytes("BD_DATA"));
            return atta;
        }, new Object[]{formKey});
    }
}

