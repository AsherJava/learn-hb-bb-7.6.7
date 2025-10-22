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
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.query.block.QueryPreWarn;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.block.QueryStatisticsItem;
import com.jiuqi.nr.query.block.SuperLinkInfor;
import com.jiuqi.nr.query.dao.impl.TransUtil;
import com.jiuqi.nr.query.deserializer.QuerySelectFieldDeserializer;
import com.jiuqi.nr.query.serializer.QuerySelectFieldSerializer;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@JsonSerialize(using=QuerySelectFieldSerializer.class)
@JsonDeserialize(using=QuerySelectFieldDeserializer.class)
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class QuerySelectField
extends QuerySelectItem {
    @Autowired
    private TransUtil util;
    private static final Logger logger = LoggerFactory.getLogger(QuerySelectField.class);
    public static final String SELECTFIELD_FILEDTYPE = "fieldType";
    public static final String SELECTFIELD_GATHERTYPE = "gatherType";
    public static final String SELECTFIELD_SHOWFORMAT = "showFormat";
    public static final String SELECTFIELD_ISHIDDEN = "ishidden";
    public static final String SELECTFIELD_PREWARNS = "preWarns";
    public static final String SELECTFIELD_STATISTICSFIELDS = "statisticsFields";
    public static final String SELECTFIELD_FILEEXTENSION = "fileExtension";
    public static final String SELECTFIELD_CODE = "code";
    public static final String SELECTFIELD_TITLE = "title";
    public static final String SELECTFIELD_ISMASTER = "ismaster";
    public static final String SELECTFIELD_REGIONKEY = "regionkey";
    public static final String SELECTFIELD_LINKS = "links";
    public static final String SELECTFIELD_ISGROUPFIELD = "isgroupfield";
    public static final String SELECTFIELD_ISSELECTBYFIELD = "isSelectByField";
    public static final String SELECTFIELD_ISDELETED = "isDeleted";
    public static final String SELECTFIELD_TABLENAME = "tableName";
    public static final String SELECTFIELD_TABLEKEY = "tableKey";
    public static final String SELECTFIELD_DATASHEET = "dataSheet";
    public static final String SELECTFIELD_FORMSCHEMEID = "formSchemeId";
    public static final String SELECTFIELD_TASKID = "taskId";
    public static final String SELECTFIELD_CUSTOM = "iscustom";
    public static final String SELECTFIELD_CUSTOMVALUE = "customvalue";
    public static final String SELECTFIELD_DIMENSIONNAME = "dimensionName";
    public static final String SELECTFIELD_ISUSERSHOW = "isusershow";
    public static final String SELECTFIELD_DOTNUM = "dotnum";
    public static final String SELECTFIELD_FORMKEY = "formkey";
    public static final String SELECTFIELD_DATALINK = "datalink";
    public static final String SELECTFIELD_ISMERGESAMECELL = "isMergeSameCell";
    public static final String SELECTFIELD_REGIONKIND = "regionKind";
    private int dotNum = 1;
    private FieldType filedType;
    private FieldGatherType gatherType;
    private String dimensionName;
    private String showFormat;
    private boolean isHidden;
    private boolean isGroupField;
    private List<QueryPreWarn> preWarns;
    private List<QueryStatisticsItem> statisticsFields;
    private String fileExtension;
    private String isMaster;
    @Autowired
    private ObjectMapper mapper;
    private List<SuperLinkInfor> linkes;
    private boolean isSelectByField;
    private boolean isDeleted;
    private String tableName;
    private String tableKey;
    private String dataSheet;
    private String taskId;
    private String formSchemeId;
    private String regionKey;
    private DataRegionKind regionKind;
    private String formKey;
    private String dataLink;
    private boolean isCustom = false;
    private String customvalue;
    private boolean isUserShow;
    private boolean isMergeSameCell;

    public void setDimensionName(String name) {
        this.dimensionName = name;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setIsGroupField(boolean isGroupField) {
        this.isGroupField = isGroupField;
    }

    public boolean getIsGroupField() {
        return this.isGroupField;
    }

    public String getIsMaster() {
        return this.isMaster;
    }

    public void setIsMaster(String isMaster) {
        this.isMaster = isMaster;
    }

    public int getDotNum() {
        return this.dotNum;
    }

    public void setDotNum(int dotNum) {
        this.dotNum = dotNum;
    }

    public FieldType getFiledType() {
        return this.filedType;
    }

    public void setFiledType(FieldType filedType) {
        this.filedType = filedType;
    }

    public FieldGatherType getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(FieldGatherType gatherType) {
        this.gatherType = gatherType;
    }

    public String getShowFormat() {
        return this.showFormat;
    }

    public void setShowFormat(String showFormat) {
        this.showFormat = showFormat;
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public List<QueryPreWarn> getPreWarns() {
        return this.preWarns;
    }

    public String getPreWarnsStr() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this.preWarns);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public void setPreWarns(List<QueryPreWarn> preWarns) {
        this.preWarns = preWarns;
    }

    public void setPreWarnsStr(String preWarnsStr) {
        try {
            if (StringUtil.isNullOrEmpty((String)preWarnsStr)) {
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{QueryPreWarn.class});
            this.setPreWarns((List)mapper.readValue(preWarnsStr, javaType));
        }
        catch (Exception e) {
            logger.error("preWarns\u672a\u9644\u521d\u59cb\u503c\u6216\u5e8f\u5217\u5316\u9519\u8bef\uff1a" + e.getMessage());
        }
    }

    public List<QueryStatisticsItem> getStatisticsFields() {
        return this.statisticsFields;
    }

    public String getStatisticsFieldsStr() {
        try {
            ArrayList result;
            List<Object> list = result = this.statisticsFields == null || this.statisticsFields.size() == 0 ? new ArrayList() : this.statisticsFields;
            if (this.mapper == null) {
                this.mapper = new ObjectMapper();
            }
            return this.mapper.writeValueAsString(result);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void setStatisticsFields(List<QueryStatisticsItem> statisticsFields) {
        this.statisticsFields = statisticsFields;
    }

    public void setStatisticsFieldsStr(String statisticsFields) {
        try {
            if (statisticsFields == null) {
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{QueryStatisticsItem.class});
            this.statisticsFields = (List)objectMapper.readValue(statisticsFields, javaType);
        }
        catch (Exception e) {
            logger.error("StatisticsFields\u672a\u9644\u521d\u59cb\u503c\u6216\u5e8f\u5217\u5316\u9519\u8bef\uff1a" + e.getMessage());
        }
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    private QueryPreWarn convertJsonToQueryPreWarn(String json) {
        try {
            if (StringUtil.isNullOrEmpty((String)json)) {
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            QueryPreWarn item = (QueryPreWarn)objectMapper.readValue(json, QueryPreWarn.class);
            return item;
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public void setLinkes(List<SuperLinkInfor> linkes) {
        this.linkes = linkes;
    }

    public void setLinkes(String linkes) {
        if (StringUtil.isNullOrEmpty((String)linkes)) {
            return;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{SuperLinkInfor.class});
            this.linkes = (List)objectMapper.readValue(linkes, javaType);
        }
        catch (Exception e) {
            logger.error("\u5e8f\u5217\u5316\u9519\u8bef\uff1a" + e.getMessage());
        }
    }

    public List<SuperLinkInfor> getLinkes() {
        return this.linkes;
    }

    public boolean isSelectByField() {
        return this.isSelectByField;
    }

    public void setSelectByField(boolean selectByField) {
        this.isSelectByField = selectByField;
    }

    public void setIsDeleted(boolean isdeleted) {
        this.isDeleted = isdeleted;
    }

    @Override
    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getDataSheet() {
        return this.dataSheet;
    }

    public void setDataSheet(String dataSheet) {
        this.dataSheet = dataSheet;
    }

    public TransUtil getUtil() {
        return this.util;
    }

    public void setUtil(TransUtil util) {
        this.util = util;
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

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public DataRegionKind getRegionKind() {
        return this.regionKind;
    }

    public void setRegionKind(DataRegionKind regionKind) {
        this.regionKind = regionKind;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setDataLink(String dataLink) {
        this.dataLink = dataLink;
    }

    public String getDataLink() {
        return this.dataLink;
    }

    @Override
    public boolean getCustom() {
        return this.isCustom;
    }

    @Override
    public void setCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    @Override
    public String getCustomValue() {
        return this.customvalue;
    }

    @Override
    public void setCustomValue(String customValue) {
        this.customvalue = customValue;
    }

    public void setIsUserShow(boolean isUserShow) {
        this.isUserShow = isUserShow;
    }

    public boolean getIsUserShow() {
        return this.isUserShow;
    }

    public void setIsMergeSameCell(boolean isMergeSameCell) {
        this.isMergeSameCell = isMergeSameCell;
    }

    public boolean getIsMergeSameCell() {
        return this.isMergeSameCell;
    }
}

