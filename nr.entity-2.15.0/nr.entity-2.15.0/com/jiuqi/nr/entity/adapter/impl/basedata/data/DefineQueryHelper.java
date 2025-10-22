/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.nr.entity.internal.model.impl.EntityAttributeImpl;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class DefineQueryHelper {
    private static ObjectMapper mapper = new ObjectMapper();
    private static final Set<String> disableField = new HashSet<String>(10);

    public static IEntityAttribute getKeyColumn(DataModelDO dataModel, Map<String, IEntityAttribute> attributeCodeMapping) {
        if (CollectionUtils.isEmpty(dataModel.getColumns())) {
            return null;
        }
        Optional<DataModelColumn> keyColumn = dataModel.getColumns().stream().filter(column -> Boolean.TRUE.equals(column.isPkey())).findFirst();
        if (!keyColumn.isPresent()) {
            keyColumn = dataModel.getColumns().stream().filter(column -> column.getColumnName().equals("ID")).findFirst();
        }
        String keyName = keyColumn.get().getColumnName();
        return attributeCodeMapping.get(keyName);
    }

    public static List<IEntityAttribute> getShowFields(List<IEntityAttribute> attributes) {
        ArrayList<IEntityAttribute> showFields = new ArrayList<IEntityAttribute>();
        for (IEntityAttribute attribute : attributes) {
            if (disableField.contains(attribute.getCode())) continue;
            showFields.add(attribute);
        }
        return showFields;
    }

    public static EntityAttributeImpl convertColumn2Attribute(IEntityAttribute attribute, String bizKey, Map<String, BaseDataField> showFields) {
        BaseDataField showField;
        if (attribute == null) {
            return null;
        }
        EntityAttributeImpl entityAttribute = (EntityAttributeImpl)attribute;
        if ("NAME".equalsIgnoreCase(attribute.getCode())) {
            entityAttribute.setSupportI18n(true);
        }
        if ("PARENTCODE".equalsIgnoreCase(attribute.getCode())) {
            entityAttribute.setReferTableID(attribute.getTableID());
            entityAttribute.setReferColumnID(bizKey);
        }
        if (!CollectionUtils.isEmpty(showFields) && (showField = showFields.get(attribute.getCode().toUpperCase())) != null) {
            if (StringUtils.hasText(showField.getTitle())) {
                entityAttribute.setTitle(showField.getTitle());
            }
            entityAttribute.setMultival(showField.isMultiple());
            entityAttribute.setMasked(showField.getMasked());
        }
        return entityAttribute;
    }

    public static Map<String, BaseDataField> getBaseDataShowFields(String define) {
        HashMap<String, BaseDataField> fieldMap = new HashMap<String, BaseDataField>(16);
        if (define != null) {
            try {
                ObjectNode jsonNodes = (ObjectNode)mapper.readValue(define, ObjectNode.class);
                if (jsonNodes != null) {
                    ArrayNode showFields = jsonNodes.withArray("showFields");
                    for (JsonNode showField : showFields) {
                        String columnName = showField.get("columnName").asText().toUpperCase();
                        String columnTitle = showField.get("columnTitle").asText();
                        BaseDataField dataField = new BaseDataField(columnName, columnTitle);
                        if (showField.has("multiple")) {
                            dataField.setMultiple(showField.get("multiple").asBoolean());
                        }
                        fieldMap.put(columnName, dataField);
                    }
                    ArrayNode fieldProps = jsonNodes.withArray("fieldProps");
                    if (fieldProps != null) {
                        for (JsonNode fieldProp : fieldProps) {
                            String columnName;
                            BaseDataField field;
                            if (!fieldProp.has("fieldSensitive") || (field = (BaseDataField)fieldMap.get(columnName = fieldProp.get("columnName").asText().toUpperCase())) == null) continue;
                            String fieldSensitive = fieldProp.get("fieldSensitive").asText();
                            field.setMasked(fieldSensitive);
                        }
                    }
                }
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return fieldMap;
    }

    static {
        disableField.add("ID");
        disableField.add("VER");
        disableField.add("OBJECTCODE");
        disableField.add("VALIDTIME");
        disableField.add("INVALIDTIME");
        disableField.add("STOPFLAG");
        disableField.add("RECOVERYFLAG");
        disableField.add("ORDINAL");
        disableField.add("CREATEUSER");
        disableField.add("CREATETIME");
        disableField.add("PARENTS");
        disableField.add("UNITCODE");
    }

    public static class BaseDataField {
        private String name;
        private String title;
        private boolean multiple;
        private String masked;

        public BaseDataField() {
        }

        public BaseDataField(String name, String title) {
            this.name = name;
            this.title = title;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isMultiple() {
            return this.multiple;
        }

        public void setMultiple(boolean multiple) {
            this.multiple = multiple;
        }

        public String getMasked() {
            return this.masked;
        }

        public void setMasked(String masked) {
            this.masked = masked;
        }
    }
}

