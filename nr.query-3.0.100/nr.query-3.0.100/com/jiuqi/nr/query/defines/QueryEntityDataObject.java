/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 */
package com.jiuqi.nr.query.defines;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.query.deserializer.QueryEntityDataObjectDeserializer;
import com.jiuqi.nr.query.serializer.QueryEntityDataObjectSerializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@JsonSerialize(using=QueryEntityDataObjectSerializer.class)
@JsonDeserialize(using=QueryEntityDataObjectDeserializer.class)
public class QueryEntityDataObject
implements INode {
    public static final String WEB_NODE_KEY = "key";
    public static final String WEB_NODE_CODE = "code";
    public static final String WEB_NODE_TITLE = "title";
    public static final String WEB_NODE_CAPTION = "caption";
    public static final String WEB_ENTITY_DATA_VIEWKEY = "viewKey";
    public static final String WEB_ENTITY_DATA_PATH = "path";
    public static final String WEB_ENTITY_DATA_TAGS = "tags";
    public static final String WEB_ENTITY_DATA_CHILDCOUNT = "childCount";
    public static final String WEB_ENTITY_DATA_PARENTID = "parentid";
    public static final String WEB_ENTITY_DATA_NODETYPE = "nodeType";
    public static final String WEB_ENTITY_DATA_BEGINDATE = "versionBeginDate";
    public static final String WEB_ENTITY_DATA_ENDDATE = "versionEndDate";
    public static final String WEB_ENTITY_DATA_SUCCESS = "success";
    public static final String WEB_ENTITY_DATA_FIELDKEY = "fieldkey";
    private String key;
    private String code;
    private String title;
    private String caption;
    private String nodeType;
    private String parentId;
    private String[] path;
    private String viewKey;
    private String iconValue;
    private int childCount;
    private List<UUID> tags;
    private long versionBeginDate;
    private long versionEndDate;
    private boolean success = true;
    private Map<String, Object> valueMap = new HashMap<String, Object>();
    private String fieldkey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String[] getPath() {
        return this.path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public int getChildCount() {
        return this.childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public List<UUID> getTags() {
        return this.tags;
    }

    public String getIconValue() {
        return this.iconValue;
    }

    public void setIconValue(String iconValue) {
        this.iconValue = iconValue;
    }

    public void setTags(List<UUID> tags) {
        this.tags = tags;
    }

    public Map<String, Object> getValueMap() {
        return this.valueMap;
    }

    public void setFieldValue(String key, Object value) {
        if (key == null) {
            throw new RuntimeException("\u952e\u503c\u4e0d\u80fd\u4e3a\u7a7a!");
        }
        this.valueMap.put(key, value);
    }

    public Object getFieldValue(String key) {
        return this.valueMap.get(key);
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public long getVersionBeginDate() {
        return this.versionBeginDate;
    }

    public void setVersionBeginDate(long versionBeginDate) {
        this.versionBeginDate = versionBeginDate;
    }

    public long getVersionEndDate() {
        return this.versionEndDate;
    }

    public void setVersionEndDate(long versionEndDate) {
        this.versionEndDate = versionEndDate;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static QueryEntityDataObject buildEntityData(IEntityRow row) {
        QueryEntityDataObject dataObj = new QueryEntityDataObject();
        if (row != null) {
            dataObj.setSuccess(true);
            dataObj.setKey(row.getEntityKeyData());
            dataObj.setTitle(row.getTitle());
            dataObj.setCode(row.getCode());
            dataObj.setPath(row.getParentsEntityKeyDataPath());
            dataObj.setParentId(row.getParentEntityKey());
            dataObj.setIconValue(row.getIconValue());
            IFieldsInfo fields = row.getFieldsInfo();
            int fieldCount = fields.getFieldCount();
            for (int idx = 0; idx < fieldCount; ++idx) {
                IEntityAttribute attribute = fields.getFieldByIndex(idx);
                if (attribute == null) continue;
                String fieldCode = attribute.getCode().toLowerCase();
                Object asObject = null;
                try {
                    AbstractData value = row.getValue(fieldCode);
                    asObject = value.getAsObject();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                dataObj.setFieldValue(fieldCode, asObject);
            }
        }
        return dataObj;
    }

    public String getFieldkey() {
        return this.fieldkey;
    }

    public void setFieldkey(String fieldkey) {
        this.fieldkey = fieldkey;
    }
}

