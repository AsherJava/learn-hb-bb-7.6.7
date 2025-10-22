/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.definition.auth.authz2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class TaskFieldAuthorityHelper {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private static final String QUERY_SQL = "SELECT DISTINCT(r.DR_FORM_KEY),l.DL_FIELD_KEY FROM NR_PARAM_DATAREGION r INNER JOIN NR_PARAM_DATALINK l ON l.DL_REGION_KEY = r.DR_KEY WHERE l.dl_field_key IN (:ids) ";
    private static final String FORM_KEY = "DR_FORM_KEY";
    private static final String FIELD_KEY = "DL_FIELD_KEY";

    protected Map<String, Set<String>> getFormFieldMap(List<String> fieldKeys) {
        int number = 1000;
        int limit = (fieldKeys.size() + number - 1) / number;
        ArrayList<FieldAuthorityInfo> fieldAuthorityInfos = new ArrayList<FieldAuthorityInfo>();
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            Set resource = fieldKeys.stream().skip(i * number).limit(number).collect(Collectors.toSet());
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("ids", resource);
            fieldAuthorityInfos.addAll(this.jdbcTemplate.query(QUERY_SQL, (SqlParameterSource)parameterSource, (rs, rowNum) -> {
                FieldAuthorityInfo fieldAuthorityInfo = new FieldAuthorityInfo();
                fieldAuthorityInfo.setFormKey(rs.getString(FORM_KEY));
                fieldAuthorityInfo.setFieldKey(rs.getString(FIELD_KEY));
                return fieldAuthorityInfo;
            }));
        });
        return this.buildFormFieldMap(fieldAuthorityInfos, fieldKeys);
    }

    private Map<String, Set<String>> buildFormFieldMap(List<FieldAuthorityInfo> fieldAuthorityInfos, List<String> fieldKeys) {
        HashMap<String, Set<String>> formFieldMap = new HashMap<String, Set<String>>();
        ArrayList<String> hasFormFields = new ArrayList<String>();
        for (FieldAuthorityInfo fieldAuthorityInfo : fieldAuthorityInfos) {
            if (formFieldMap.get(fieldAuthorityInfo.getFormKey()) == null) {
                HashSet<String> fields = new HashSet<String>();
                fields.add(fieldAuthorityInfo.getFieldKey());
                formFieldMap.put(fieldAuthorityInfo.getFormKey(), fields);
            } else {
                Set formField = (Set)formFieldMap.get(fieldAuthorityInfo.getFormKey());
                formField.add(fieldAuthorityInfo.getFieldKey());
                formFieldMap.put(fieldAuthorityInfo.getFormKey(), formField);
            }
            hasFormFields.add(fieldAuthorityInfo.getFieldKey());
        }
        Set noFormField = fieldKeys.stream().filter(a -> !hasFormFields.contains(a)).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(noFormField)) {
            formFieldMap.put("NO_FORM_FIELD", noFormField);
        }
        return formFieldMap;
    }

    static class FieldAuthorityInfo {
        private String formKey;
        private String fieldKey;

        FieldAuthorityInfo() {
        }

        public String getFormKey() {
            return this.formKey;
        }

        public void setFormKey(String formKey) {
            this.formKey = formKey;
        }

        public String getFieldKey() {
            return this.fieldKey;
        }

        public void setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
        }
    }
}

