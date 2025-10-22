/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  org.json.JSONObject
 */
package com.jiuqi.nr.query.querymodal;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.common.IBusinessObject;
import com.jiuqi.nr.query.querymodal.QueryModelCategory;
import com.jiuqi.nr.query.querymodal.QueryModelExtension;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nr.query.querymodal.QueryType;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@DBAnno.DBTable(dbTable="SYS_QUERYMODELDEFINE")
public class QueryModalDefine
implements IBusinessObject {
    private static final Logger log = LoggerFactory.getLogger(QueryModalDefine.class);
    @DBAnno.DBField(dbField="QMD_ID", dbType=String.class, isPk=true)
    private String id;
    @DBAnno.DBField(dbField="QMD_TITLE")
    private String title;
    @DBAnno.DBField(dbField="QMD_ORDER", isOrder=true)
    private double order;
    @DBAnno.DBField(dbField="QMD_TASKID", dbType=String.class, isPk=false)
    private String taskId;
    @DBAnno.DBField(dbField="QMD_GROUPID", dbType=String.class, isPk=false)
    private String groupId;
    private List<QueryDimensionDefine> conditionDimensions;
    @DBAnno.DBField(dbField="QMD_CONDITIONS", dbType=Clob.class)
    public String conditions;
    private List<QueryBlockDefine> blocks;
    @DBAnno.DBField(dbField="QMD_CREATOR", dbType=String.class, isPk=false)
    private String creator;
    @DBAnno.DBField(dbField="QMD_LAYOUT", dbType=Clob.class)
    private String layout;
    @DBAnno.DBField(dbField="QMD_DESCRIPTION")
    private String description;
    @DBAnno.DBField(dbField="QMD_SCHEMEID", dbType=String.class, isPk=false)
    private String schemeId;
    @DBAnno.DBField(dbField="QMD_TYPE", tranWith="transModelType", dbType=String.class, appType=QueryModelType.class)
    private QueryModelType modelType = QueryModelType.OWNER;
    private boolean isNew;
    private boolean isDirty;
    private boolean isDeleted;
    @Autowired
    private ObjectMapper objectMapper;
    private QueryType queryType;
    @DBAnno.DBField(dbField="UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;
    private QueryModelExtension queryModelExtension;
    @DBAnno.DBField(dbField="QMD_EXTENSION")
    private String modelExtension;
    private QueryModelCategory modelCategory = QueryModelCategory.DATAQUERY;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getOrder() {
        return this.order;
    }

    public void setOrder(double order) {
        this.order = order;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public void setModelType(QueryModelType type) {
        this.modelType = type;
    }

    public QueryModelType getModelType() {
        return this.modelType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<QueryDimensionDefine> getConditionDimensions() {
        return this.conditionDimensions;
    }

    public void setConditionDimensions(List<QueryDimensionDefine> conditionDimensions) {
        this.conditionDimensions = conditionDimensions;
    }

    @Override
    public void IsNew(boolean value) {
        this.isNew = value;
    }

    public boolean getIsNew() {
        return this.isNew;
    }

    @Override
    public void IsDirty(boolean value) {
        this.isDirty = value;
    }

    public boolean getIsDirty() {
        return this.isDirty;
    }

    @Override
    public void IsDeleted(boolean value) {
        this.isDeleted = value;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public String getConditions() {
        return this.conditions;
    }

    public void setConditions() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.conditions = mapper.writeValueAsString(this.conditionDimensions);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }

    public QueryType getQueryType() {
        return this.queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public void jsonTomodelCondition(String conditions) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{QueryDimensionDefine.class});
            this.setConditionDimensions((List)mapper.readValue(conditions, javaType));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public List<QueryBlockDefine> getBlocks() {
        return this.blocks;
    }

    public void setBlocks(List<QueryBlockDefine> blocks) {
        this.blocks = blocks;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLayout() {
        return this.layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getModelExtension() {
        return this.modelExtension;
    }

    public void setModelExtension() {
        try {
            this.modelExtension = this.convertModelExtensionToJson();
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void JsonToModelExtension(String extension) {
        this.queryModelExtension = this.convertJsonToModelExtension(extension);
    }

    public void setQueryModelExtension(QueryModelExtension extension) {
        this.queryModelExtension = extension;
    }

    public QueryModelExtension getQueryModelExtension() {
        return this.queryModelExtension;
    }

    public QueryModelCategory getModelCategory() {
        return this.modelCategory;
    }

    public void setModelCategory(QueryModelCategory modelCategory) {
        this.modelCategory = modelCategory;
    }

    private String convertModelExtensionToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String modelExtensionJson = objectMapper.writeValueAsString((Object)this.queryModelExtension);
        return modelExtensionJson;
    }

    private QueryModelExtension convertJsonToModelExtension(String extension) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            QueryModelExtension item = (QueryModelExtension)objectMapper.readValue(extension, QueryModelExtension.class);
            return item;
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public void UpdateModelID(String newModelID, String[] blockIDs) throws IOException {
        if (blockIDs.length != this.blocks.size()) {
            throw new IOException("\u5757ID\u6570\u7ec4\u9519\u8bef");
        }
        HashMap<String, String> preBlockIDs = new HashMap<String, String>();
        for (int id = 0; id < this.blocks.size(); ++id) {
            QueryBlockDefine block = this.blocks.get(id);
            block.setModelID(newModelID);
            preBlockIDs.put(block.getId(), blockIDs[id]);
            block.setId(blockIDs[id]);
            for (int dimesionID = 0; dimesionID < block.getQueryDimensions().size(); ++dimesionID) {
                QueryDimensionDefine dimensionObject = block.getQueryDimensions().get(dimesionID);
                dimensionObject.setBlockId(block.getId());
            }
        }
        this.setId(newModelID);
        JSONObject layoutObject = (JSONObject)this.objectMapper.readValue(this.layout, JSONObject.class);
        JavaType javaType = this.objectMapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{JSONObject.class});
        List layoutItems = (List)this.objectMapper.readValue(layoutObject.get("items").toString(), javaType);
        for (int i = 0; i < layoutItems.size(); ++i) {
            JSONObject item = (JSONObject)layoutItems.get(i);
            String preID = (String)item.get("blockId");
            if (preBlockIDs.get(preID) == null) continue;
            item.put("blockID", preBlockIDs.get(preID));
        }
        layoutObject.put("Items", (Collection)layoutItems);
        this.layout = this.objectMapper.writeValueAsString((Object)layoutObject);
    }
}

