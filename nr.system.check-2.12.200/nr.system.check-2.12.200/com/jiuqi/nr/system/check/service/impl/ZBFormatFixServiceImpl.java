/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.system.check.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.nr.system.check.exception.FormatReadeException;
import com.jiuqi.nr.system.check.service.ZBFormatFixService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZBFormatFixServiceImpl
implements ZBFormatFixService {
    private static final Integer FORMAT_TYPE = 4;
    private static final String LINK_SELECT_SQL = "SELECT DL_KEY, DL_FORMAT_PROPERTIES FROM %s WHERE DL_FORMAT_PROPERTIES IS NOT NULL;";
    private static final String LINK_UPDATE_SQL = "UPDATE %s SET DL_FORMAT_PROPERTIES =? WHERE DL_KEY =?";
    private static final String FIELD_SELECT_SQL = "SELECT DF_KEY,DF_SHOWFORMAT FROM %s WHERE DF_SHOWFORMAT IS NOT NULL;";
    private static final String FIELD_UPDATE_SQL = "UPDATE %s SET DF_SHOWFORMAT =? WHERE DF_KEY =?;";
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void fixDataSchemeZB() {
        this.fix("NR_DATASCHEME_FIELD_DES", FIELD_SELECT_SQL, FIELD_UPDATE_SQL);
        this.fix("NR_DATASCHEME_FIELD", FIELD_SELECT_SQL, FIELD_UPDATE_SQL);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void fixTaskLink() {
        this.fix("NR_PARAM_DATALINK_DES", LINK_SELECT_SQL, LINK_UPDATE_SQL);
        this.fix("NR_PARAM_DATALINK", LINK_SELECT_SQL, LINK_UPDATE_SQL);
    }

    private void fix(String name, String selectSql, String updateSql) {
        List query = this.jdbcTemplate.query(String.format(selectSql, name), (rs, rowNum) -> {
            FormatObject formatObject = new FormatObject();
            formatObject.setKey(rs.getString(1));
            formatObject.setFormat(rs.getString(2));
            return formatObject;
        });
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (FormatObject formatObject : query) {
            String pattern;
            JsonNode jsonNode = null;
            try {
                jsonNode = mapper.readTree(formatObject.getFormat());
            }
            catch (JsonProcessingException e) {
                throw new FormatReadeException("\u6307\u6807\u683c\u5f0f\u4fe1\u606f\u89e3\u6790\u5931\u8d25\uff1a", e);
            }
            int formatType = jsonNode.get("formatType").asInt(0);
            if (formatType != FORMAT_TYPE || !(pattern = jsonNode.get("pattern").asText("")).contains("%") || !pattern.contains(".")) continue;
            int dotIndex = pattern.indexOf(46);
            String prefix = pattern.substring(0, dotIndex);
            String suffix = pattern.substring(dotIndex + 1);
            if (suffix.length() >= 3) {
                suffix = suffix.substring(0, suffix.length() - 3) + "%";
            }
            String newPattern = prefix + (suffix.equals("%") ? "" : ".") + suffix;
            ((ObjectNode)jsonNode).put("pattern", newPattern);
            batchArgs.add(new Object[]{jsonNode.toString(), formatObject.getKey()});
        }
        this.jdbcTemplate.batchUpdate(String.format(updateSql, name), batchArgs);
    }

    private static class FormatObject {
        private String key;
        private String format;

        private FormatObject() {
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getFormat() {
            return this.format;
        }

        public void setFormat(String format) {
            this.format = format;
        }
    }
}

