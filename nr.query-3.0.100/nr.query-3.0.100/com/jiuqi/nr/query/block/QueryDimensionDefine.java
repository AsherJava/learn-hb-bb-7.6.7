/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.fasterxml.jackson.core.JsonParseException
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  io.netty.util.internal.StringUtil
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.query.block.DimensionExtension;
import com.jiuqi.nr.query.block.DimensionItemScop;
import com.jiuqi.nr.query.block.FieldSelectScop;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QueryFieldCondition;
import com.jiuqi.nr.query.block.QuerySelectField;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.block.SuperLinkInfor;
import com.jiuqi.nr.query.common.BusinessObject;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.common.QuerySelectionType;
import com.jiuqi.nr.query.deserializer.QueryDimensionDeserializer;
import com.jiuqi.nr.query.serializer.QueryDimensionSerializer;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonSerialize(using=QueryDimensionSerializer.class)
@JsonDeserialize(using=QueryDimensionDeserializer.class)
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class QueryDimensionDefine
extends BusinessObject
implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(QueryDimensionDefine.class);
    public static final String DIMENSION_VIEWID = "viewId";
    public static final String DIMENSION_BLOCKID = "blockid";
    public static final String DIMENSION_TITLE = "dimensiontitle";
    public static final String DIMENSION_ISHIDDEN = "ishidden";
    public static final String DIMENSION_SHOWSUM = "showsumn";
    public static final String DIMENSION_PREPOSESUM = "preposesum";
    public static final String DIMENSION_LAYOUTTYPE = "layouttype";
    public static final String DIMENSION_DIMENSIONTYPE = "dimensiontype";
    public static final String DIMENSION_SELECTITEMS = "selectItems";
    public static final String DIMENSION_DEFAULTITEMS = "defaultItems";
    public static final String DIMENSION_SELECTFIELDS = "selectfields";
    public static final String DIMENSION_DIMENSIONEXTENSION = "dimensionextension";
    public static final String DIMENSION_TABLEKIND = "dimensiontablekind";
    public static final String DIMENSION_ISTREE = "dimensionistree";
    public static final String DIMENSION_PERIODTYPE = "periodType";
    public static final String DIMENSION_ITEMSCOP = "itemScop";
    public static final String DIMENSION_NAME = "dimensionName";
    public static final String DIMENSION_ISPERIODDIM = "isPeriodDim";
    public static final String DIMENSION_TASKID = "taskId";
    public static final String DIMENSION_FORMSCHEMEID = "formSchemeId";
    public static final String DIMENSION_LINKS = "links";
    public static final String DIMENSION_DEFAULTCONDITION = "defaultCondition";
    public static final String DIMENSION_SELECTCONDITION = "selectCondition";
    public static final String DIMENSION_EXTENDS = "extends";
    public static final String DIMENSION_CONDITIONTITLE = "conditionTitle";
    public static final String DIMENSION_FIELDSELECTSCOP = "fieldscop";
    public static final String DIMENSION_FIELDKEY = "fieldKey";
    private String viewId;
    private String dimName;
    private String blockId;
    private boolean isHidden = false;
    private boolean showSum = true;
    private boolean preposeSum = true;
    private QueryLayoutType layoutType = QueryLayoutType.LYT_COL;
    private QueryDimensionType dimensionType = QueryDimensionType.QDT_NULL;
    private String dimensionName;
    private boolean isPeriodDim;
    private String taskId;
    private String formSchemeId;
    private String tableKind;
    private String istree;
    private String periodType;
    private List<QueryFieldCondition> defaultCondition;
    private List<QueryFieldCondition> selectCondition;
    private List<QuerySelectItem> selectItems;
    private List<QuerySelectField> selectFields;
    private List<QuerySelectItem> defaultItems;
    private String fieldKey;
    private DimensionExtension dimensionExtension;
    private DimensionItemScop itemScop;
    private ObjectMapper mapper = new ObjectMapper();
    private List<SuperLinkInfor> linkes;
    private String extendSets;
    private String conditionTitle;
    private FieldSelectScop selectScop;

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public boolean isPeriodDim() {
        return this.isPeriodDim;
    }

    public void setIsPeriodDim(boolean isPeriodDim) {
        this.isPeriodDim = isPeriodDim;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getTableKind() {
        return this.tableKind;
    }

    public void setTableKind(String tableKind) {
        this.tableKind = tableKind;
    }

    public String getIstree() {
        return this.istree;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public void setIstree(String istree) {
        this.istree = istree;
    }

    public static Logger getLogger() {
        return logger;
    }

    public List<QueryFieldCondition> getDefaultCondition() {
        return this.defaultCondition;
    }

    public void setDefaultCondition(List<QueryFieldCondition> defaultCondition) {
        this.defaultCondition = defaultCondition;
    }

    public List<QueryFieldCondition> getSelectCondition() {
        return this.selectCondition;
    }

    public void setSelectCondition(List<QueryFieldCondition> selectCondition) {
        this.selectCondition = selectCondition;
    }

    public void setDefaultItems(List<QuerySelectItem> items) {
        this.defaultItems = items;
    }

    public List<QuerySelectItem> getDefaultItems() {
        return this.defaultItems;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fKey) {
        this.fieldKey = fKey;
    }

    public String getViewId() {
        return this.viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getBlockId() {
        return this.blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public boolean isShowSum() {
        return this.showSum;
    }

    public void setShowSum(boolean showSum) {
        this.showSum = showSum;
    }

    public boolean isPreposeSum() {
        return this.preposeSum;
    }

    public void setPreposeSum(boolean preposeSum) {
        this.preposeSum = preposeSum;
    }

    public QueryLayoutType getLayoutType() {
        return this.layoutType;
    }

    public void setLayoutType(QueryLayoutType layoutType) {
        this.layoutType = layoutType;
    }

    public QueryDimensionType getDimensionType() {
        return this.dimensionType;
    }

    public void setDimensionType(QueryDimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    public DimensionItemScop getItemScop() {
        return this.itemScop;
    }

    public void setItemScop(DimensionItemScop itemScop) {
        this.itemScop = itemScop;
    }

    public List<QuerySelectItem> getSelectItems() {
        return this.selectItems == null || this.selectItems.size() == 0 ? new ArrayList<QuerySelectItem>() : this.selectItems;
    }

    public String getSelectItemsStr() {
        try {
            ArrayList result = this.selectItems == null || this.selectItems.size() == 0 ? new ArrayList() : this.selectItems;
            return this.mapper.writeValueAsString(result);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void setSelectItems(List<QuerySelectItem> selectItems) {
        this.selectItems = selectItems;
    }

    public void setSelectItems(String selectItemsStr) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{QuerySelectItem.class});
            this.selectItems = (List)objectMapper.readValue(selectItemsStr, javaType);
        }
        catch (Exception e) {
            logger.error("selectItems\u672a\u9644\u521d\u59cb\u503c\u6216\u5e8f\u5217\u5316\u9519\u8bef\uff1a" + e.getMessage(), e);
        }
    }

    public void setDefaultItems(String selectItemsStr) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{QuerySelectItem.class});
            this.defaultItems = (List)objectMapper.readValue(selectItemsStr, javaType);
        }
        catch (Exception e) {
            logger.error("selectItems\u672a\u9644\u521d\u59cb\u503c\u6216\u5e8f\u5217\u5316\u9519\u8bef\uff1a" + e.getMessage(), e);
        }
    }

    public List<QuerySelectField> getSelectFields() {
        return this.selectFields == null || this.selectFields.size() == 0 ? new ArrayList<QuerySelectField>() : this.selectFields;
    }

    public String getSelectFieldsStr() {
        try {
            ArrayList result = this.selectFields == null || this.selectFields.size() == 0 ? new ArrayList() : this.selectFields;
            return this.mapper.writeValueAsString(result);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void setSelectFields(List<QuerySelectField> selectFields) {
        this.selectFields = selectFields;
    }

    public void setSelectFields(String selectFieldsStr) {
        try {
            if (StringUtil.isNullOrEmpty((String)selectFieldsStr)) {
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{QuerySelectField.class});
            this.selectFields = (List)objectMapper.readValue(selectFieldsStr, javaType);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public String getDimensionExtension() throws JsonProcessingException {
        return this.mapper.writeValueAsString((Object)this.dimensionExtension);
    }

    public QuerySelectionType getSelectType() {
        if (this.dimensionExtension != null) {
            return this.dimensionExtension.getQuerySelectionType();
        }
        return QuerySelectionType.NONE;
    }

    public void setDimensionExtension(String dimensionExtension) throws JsonParseException, JsonMappingException, IOException {
        if (StringUtil.isNullOrEmpty((String)dimensionExtension)) {
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        this.dimensionExtension = (DimensionExtension)objectMapper.readValue(dimensionExtension, DimensionExtension.class);
    }

    public String getExtensionFilter() {
        if (this.dimensionExtension == null) {
            return "";
        }
        return this.dimensionExtension.getFilter();
    }

    public QuerySelectionType getExtensionSelectionType() {
        if (this.dimensionExtension == null) {
            return QuerySelectionType.NONE;
        }
        return this.dimensionExtension.getQuerySelectionType();
    }

    public String getExtensionStatisticsDimensions() {
        if (this.dimensionExtension == null) {
            return "";
        }
        return this.dimensionExtension.getStatisticsDimensions();
    }

    public QueryDimensionDefine clone() {
        QueryDimensionDefine queryDimensionDefine = null;
        try {
            queryDimensionDefine = (QueryDimensionDefine)super.clone();
        }
        catch (CloneNotSupportedException e) {
            logger.error(e.getMessage(), e);
        }
        return queryDimensionDefine;
    }

    public void setLinkes(List<SuperLinkInfor> linkes) {
        this.linkes = linkes;
    }

    public void setLinkes(String linkes) {
        if (StringUtil.isNullOrEmpty((String)linkes) || "{}".equals(linkes)) {
            this.linkes = new ArrayList<SuperLinkInfor>();
            return;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{SuperLinkInfor.class});
            this.linkes = (List)objectMapper.readValue(linkes, javaType);
        }
        catch (Exception e) {
            logger.error("\u5e8f\u5217\u5316\u9519\u8bef\uff1a" + e.getMessage(), e);
        }
    }

    public List<SuperLinkInfor> getLinkes() {
        return this.linkes;
    }

    public void setSelectCondition(String selectConditionStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{QueryFieldCondition.class});
            this.selectCondition = (List)objectMapper.readValue(selectConditionStr, javaType);
        }
        catch (IOException e) {
            logger.error("\u5e8f\u5217\u5316\u9519\u8bef\uff1a" + e.getMessage(), e);
        }
    }

    public void setDefaultCondition(String defaultConditionStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{QueryFieldCondition.class});
            this.defaultCondition = (List)objectMapper.readValue(defaultConditionStr, javaType);
        }
        catch (IOException e) {
            logger.error("\u5e8f\u5217\u5316\u9519\u8bef\uff1a" + e.getMessage(), e);
        }
    }

    public void setIsNum(Boolean isNum) {
        if (isNum != null) {
            if (this.dimensionExtension == null) {
                this.dimensionExtension = new DimensionExtension();
            }
            this.dimensionExtension.setNum(isNum);
        }
    }

    public void setFieldConditionType(String type) {
        if (!StringUtils.isEmpty((CharSequence)type)) {
            if (this.dimensionExtension == null) {
                this.dimensionExtension = new DimensionExtension();
            }
            this.dimensionExtension.setFieldConditionType(type);
        }
    }

    public void setExtend(String extendSets) {
        this.extendSets = extendSets;
    }

    public Object getExtend(Class<?> ctype) throws JsonParseException, JsonMappingException, IOException {
        if (StringUtil.isNullOrEmpty((String)this.extendSets)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(this.extendSets, ctype);
    }

    public String getExtend() {
        return this.extendSets;
    }

    public String getConditionTitle() {
        return this.conditionTitle;
    }

    public void setConditionTitle(String conditionTitle) {
        this.conditionTitle = conditionTitle;
    }

    public void setSelectScop(String selectScop) {
        if (!StringUtil.isNullOrEmpty((String)selectScop)) {
            this.selectScop = FieldSelectScop.valueOf(selectScop);
        }
    }

    public FieldSelectScop getSelectScop() {
        return this.selectScop;
    }
}

