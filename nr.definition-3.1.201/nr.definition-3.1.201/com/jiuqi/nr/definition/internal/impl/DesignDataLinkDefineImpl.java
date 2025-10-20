/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.definition.internal.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl;
import com.jiuqi.nr.definition.log.ComparePropertyAnno;
import com.jiuqi.nr.definition.util.DataLinkHelper;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DBAnno.DBTable(dbTable="NR_PARAM_DATALINK_DES")
@DBAnno.DBLink(linkWith=DesignDataRegionDefineImpl.class, linkField="key", field="regionKey")
@ComparePropertyAnno.CompareType
public class DesignDataLinkDefineImpl
implements DesignDataLinkDefine {
    private static final Logger logger = LoggerFactory.getLogger(DesignDataLinkDefineImpl.class);
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="dl_region_key", isPk=false)
    @ComparePropertyAnno.CompareField(title="\u6240\u5c5e\u533a\u57df")
    private String regionKey;
    @DBAnno.DBField(dbField="dl_expression")
    @ComparePropertyAnno.CompareField(title="\u5173\u8054\u516c\u5f0f")
    private String linkExpression;
    @ComparePropertyAnno.CompareField(title="\u5173\u8054\u6307\u6807")
    @DBAnno.DBField(dbField="dl_field_key")
    private String linkFieldKey;
    @DBAnno.DBField(dbField="dl_binding_expression")
    private String bindingExpression;
    @ComparePropertyAnno.CompareField(title="\u7269\u7406\u5217\u5750\u6807")
    @DBAnno.DBField(dbField="dl_posX")
    private int posX;
    @ComparePropertyAnno.CompareField(title="\u7269\u7406\u884c\u5750\u6807")
    @DBAnno.DBField(dbField="dl_posy")
    private int posY;
    @ComparePropertyAnno.CompareField(title="\u6570\u636e\u5217\u5750\u6807")
    @DBAnno.DBField(dbField="dl_col_num")
    private int colNum;
    @ComparePropertyAnno.CompareField(title="\u6570\u636e\u884c\u5750\u6807")
    @DBAnno.DBField(dbField="dl_row_num")
    private int rowNum;
    @ComparePropertyAnno.CompareField(title="\u5355\u5143\u683c\u7f16\u8f91\u65b9\u5f0f")
    @DBAnno.DBField(dbField="dl_edit_mode", tranWith="transDataLinkEditMode", dbType=Integer.class, appType=DataLinkEditMode.class)
    private DataLinkEditMode editMode = DataLinkEditMode.DATA_LINK_DEFAULT;
    @DBAnno.DBField(dbField="dl_display_mode", tranWith="transEnumDisplayMode", dbType=Integer.class, appType=EnumDisplayMode.class)
    private EnumDisplayMode displayMode = EnumDisplayMode.DISPLAY_MODE_DEFAULT;
    private List<String> dataValidation;
    @DBAnno.DBField(dbField="dl_caption_fields")
    @ComparePropertyAnno.CompareField(title="\u5355\u5143\u683c\u663e\u793a\u5185\u5bb9")
    private String captionFieldsString;
    @DBAnno.DBField(dbField="dl_drop_down_Fields")
    @ComparePropertyAnno.CompareField(title="\u679a\u4e3e\u4e0b\u62c9\u663e\u793a\u5185\u5bb9")
    private String dropDownFieldsString;
    @DBAnno.DBField(dbField="dl_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="dl_title")
    private String title;
    @DBAnno.DBField(dbField="dl_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="dl_version")
    private String version;
    @DBAnno.DBField(dbField="dl_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="dl_updatetime", tranWith="transTimeStamp", autoDate=true, dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;
    @DBAnno.DBField(dbField="dl_undefine_Code", tranWith="transBooleanThreeStates", dbType=Integer.class, appType=Boolean.class)
    private Boolean allowUndefinedCode;
    @DBAnno.DBField(dbField="dl_null_able", tranWith="transBooleanThreeStates", dbType=Integer.class, appType=Boolean.class)
    private Boolean allowNullAble;
    @DBAnno.DBField(dbField="dl_not_leaf_refer", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean allowNotLeafNodeRefer;
    @DBAnno.DBField(dbField="dl_unique_code")
    private String uniqueCode;
    @DBAnno.DBField(dbField="dl_enum_show_full_path")
    private String isEnumShowFullPath;
    @DBAnno.DBField(dbField="dl_type", tranWith="transLinkType", dbType=Integer.class, appType=DataLinkType.class)
    private DataLinkType type = DataLinkType.DATA_LINK_TYPE_FIELD;
    @DBAnno.DBField(dbField="dl_enum_title_field")
    private String enumTitleField;
    @DBAnno.DBField(dbField="dl_enum_linkage")
    private String enumLinkage;
    @DBAnno.DBField(dbField="dl_enum_count")
    private int enumCount;
    @DBAnno.DBField(dbField="dl_enum_pos")
    private String enumPos;
    @DBAnno.DBField(dbField="dl_format_properties", tranWith="transFormatProperties", dbType=String.class, appType=FormatProperties.class)
    private FormatProperties formatProperties;
    @DBAnno.DBField(dbField="dl_enum_linkage_status", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean enumLinkageStatus;
    @DBAnno.DBField(dbField="dl_filter_expression")
    private String filterExpression;
    @DBAnno.DBField(dbField="dl_ignore_permissions", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean ignorePermissions;
    @DBAnno.DBField(dbField="dl_filter_template")
    private String filterTemplate;
    @DBAnno.DBField(dbField="dl_measureunit")
    private String measureUnit;

    public DesignDataLinkDefineImpl() {
    }

    public DesignDataLinkDefineImpl(DesignDataLinkDefineImpl define) {
        this.regionKey = define.regionKey;
        this.linkExpression = define.linkExpression;
        this.linkFieldKey = define.linkFieldKey;
        this.bindingExpression = define.bindingExpression;
        this.posX = define.posX;
        this.posY = define.posY;
        this.colNum = define.colNum;
        this.rowNum = define.rowNum;
        this.editMode = define.editMode;
        this.displayMode = define.displayMode;
        this.dataValidation = define.dataValidation;
        this.captionFieldsString = define.captionFieldsString;
        this.dropDownFieldsString = define.dropDownFieldsString;
        this.key = define.key;
        this.title = define.title;
        this.order = define.order;
        this.version = define.version;
        this.ownerLevelAndId = define.ownerLevelAndId;
        this.updateTime = define.updateTime;
        this.allowUndefinedCode = define.allowUndefinedCode;
        this.allowNullAble = define.allowNullAble;
        this.allowNotLeafNodeRefer = define.allowNotLeafNodeRefer;
        this.uniqueCode = define.uniqueCode;
        this.isEnumShowFullPath = define.isEnumShowFullPath;
        this.type = define.type;
        this.enumTitleField = define.enumTitleField;
        this.enumLinkage = define.enumLinkage;
        this.enumCount = define.enumCount;
        this.enumPos = define.enumPos;
        this.formatProperties = define.formatProperties;
        this.enumLinkageStatus = define.enumLinkageStatus;
        this.filterExpression = define.filterExpression;
        this.ignorePermissions = define.ignorePermissions;
        this.filterTemplate = define.filterTemplate;
        this.measureUnit = define.measureUnit;
    }

    @Override
    public boolean getEnumLinkageStatus() {
        return this.enumLinkageStatus;
    }

    @Override
    public void setEnumLinkageStatus(boolean enumLinkageStatus) {
        this.enumLinkageStatus = enumLinkageStatus;
    }

    @Override
    public String getRegionKey() {
        return this.regionKey;
    }

    @Override
    public String getLinkExpression() {
        if (this.type == DataLinkType.DATA_LINK_TYPE_FIELD || this.type == DataLinkType.DATA_LINK_TYPE_INFO) {
            return this.linkFieldKey;
        }
        return this.linkExpression;
    }

    @Override
    public String getBindingExpression() {
        return this.bindingExpression;
    }

    @Override
    public int getPosX() {
        return this.posX;
    }

    @Override
    public int getPosY() {
        return this.posY;
    }

    @Override
    public int getColNum() {
        return this.colNum;
    }

    @Override
    public int getRowNum() {
        return this.rowNum;
    }

    @Override
    public DataLinkEditMode getEditMode() {
        return this.editMode;
    }

    public int getEditModeDB() {
        return this.editMode == null ? DataLinkEditMode.DATA_LINK_DEFAULT.getValue() : this.editMode.getValue();
    }

    public void setEditModeDB(int type) {
        this.editMode = DataLinkEditMode.forValue(type);
    }

    @Override
    public EnumDisplayMode getDisplayMode() {
        return this.displayMode;
    }

    public int getRegionKindDB() {
        return this.displayMode == null ? EnumDisplayMode.DISPLAY_MODE_DEFAULT.getValue() : this.displayMode.getValue();
    }

    public void setRegionKindDB(int type) {
        this.displayMode = EnumDisplayMode.forValue(type);
    }

    @Override
    public List<String> getDataValidation() {
        if (null == this.dataValidation) {
            this.dataValidation = DataLinkHelper.getValidationsStr(this);
        }
        return this.dataValidation;
    }

    public String getDataValidationDB() {
        String expression = "";
        if (this.dataValidation != null && this.dataValidation.size() > 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                expression = objectMapper.writeValueAsString(this.dataValidation);
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return expression;
    }

    @Override
    public String getCaptionFieldsString() {
        return this.captionFieldsString;
    }

    @Override
    public String getDropDownFieldsString() {
        return this.dropDownFieldsString;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    @Override
    public void setLinkExpression(String expression) {
        if (this.type == DataLinkType.DATA_LINK_TYPE_FIELD || this.type == DataLinkType.DATA_LINK_TYPE_INFO) {
            this.linkFieldKey = expression;
            this.linkExpression = null;
        } else {
            this.linkExpression = expression;
            this.linkFieldKey = null;
        }
    }

    @Override
    public void setBindingExpression(String bindingExpression) {
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    @Override
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public void setEditMode(DataLinkEditMode editMode) {
        this.editMode = editMode;
    }

    @Override
    public void setDisplayMode(EnumDisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    @Override
    public void setDataValidation(List<String> expression) {
        this.dataValidation = expression;
    }

    public void setDataValidationDB(String expressions) {
        List<String> keys = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)expressions)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                keys = (List)objectMapper.readValue(expressions, List.class);
            }
            catch (Exception e) {
                keys.add(expressions);
            }
        }
        this.dataValidation = keys.stream().filter(s -> StringUtils.isNotEmpty((String)s)).collect(Collectors.toList());
    }

    @Override
    public void setCaptionFieldsString(String keys) {
        this.captionFieldsString = keys;
    }

    @Override
    public void setDropDownFieldsString(String keys) {
        this.dropDownFieldsString = keys;
    }

    @Override
    public Boolean getAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    @Override
    public boolean getAllowMultipleSelect() {
        return DataLinkHelper.getAllowMultipleSelect(this);
    }

    @Override
    public boolean getAllowNotLeafNodeRefer() {
        return this.allowNotLeafNodeRefer;
    }

    @Override
    public FormatProperties getFormatProperties() {
        return this.formatProperties;
    }

    @Override
    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    @Override
    public Boolean getAllowNullAble() {
        return this.allowNullAble;
    }

    @Override
    public void setAllowNullAble(Boolean allowNullAble) {
        this.allowNullAble = allowNullAble;
    }

    @Override
    public void setAllowNotLeafNodeRefer(boolean allowNotLeafNodeRefer) {
        this.allowNotLeafNodeRefer = allowNotLeafNodeRefer;
    }

    @Override
    public void setFormatProperties(FormatProperties formatProperties) {
        this.formatProperties = formatProperties;
    }

    @Override
    public String getUniqueCode() {
        return this.uniqueCode;
    }

    @Override
    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    @Override
    public String getEnumShowFullPath() {
        return this.isEnumShowFullPath;
    }

    @Override
    public void setEnumShowFullPath(String isShowFullPath) {
        this.isEnumShowFullPath = isShowFullPath;
    }

    @Override
    public DataLinkType getType() {
        return this.type;
    }

    @Override
    public void setType(DataLinkType type) {
        String newExpression = this.getLinkExpression();
        this.type = type;
        this.setLinkExpression(newExpression);
    }

    @Override
    public String getEnumTitleField() {
        return this.enumTitleField;
    }

    @Override
    public void setEnumTitleField(String enumTitleField) {
        this.enumTitleField = enumTitleField;
    }

    @Override
    public String getEnumLinkage() {
        return this.enumLinkage;
    }

    @Override
    public void setEnumLinkage(String enumLinkage) {
        this.enumLinkage = enumLinkage;
    }

    @Override
    public String getEnumLinkageMethod() {
        String enumLinkage = this.enumLinkage;
        List list = JacksonUtils.toList((String)enumLinkage, HashMap.class);
        String method = null;
        for (int i = 0; i < list.size(); ++i) {
            if (((HashMap)list.get(i)).get("method") == null) continue;
            method = (String)((HashMap)list.get(i)).get("method");
            break;
        }
        return method;
    }

    @Override
    public Map<String, String> getEnumLinkageData() {
        HashMap<String, String> EnumLinkageData = new HashMap<String, String>();
        String enumLinkage = this.enumLinkage;
        List list = JacksonUtils.toList((String)enumLinkage, HashMap.class);
        for (int i = 0; i < list.size(); ++i) {
            if (((HashMap)list.get(i)).get("method") != null) continue;
            HashMap map = (HashMap)list.get(i);
            for (Map.Entry entry : map.entrySet()) {
                String mapKey = (String)entry.getKey();
                String mapValue = (String)entry.getValue();
                EnumLinkageData.put(mapKey, mapValue);
            }
        }
        return EnumLinkageData;
    }

    @Override
    public int getEnumCount() {
        return this.enumCount;
    }

    @Override
    public void setEnumCount(int enumCount) {
        this.enumCount = enumCount;
    }

    @Override
    public String getEnumPos() {
        return this.enumPos;
    }

    @Override
    public void setEnumPos(String enumPos) {
        this.enumPos = enumPos;
    }

    @Override
    public String getFilterExpression() {
        return this.filterExpression;
    }

    @Override
    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }

    @Override
    public boolean isIgnorePermissions() {
        return this.ignorePermissions;
    }

    @Override
    public String getFilterTemplate() {
        return this.filterTemplate;
    }

    @Override
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @Override
    public void setIgnorePermissions(boolean ignorePermissions) {
        this.ignorePermissions = ignorePermissions;
    }

    @Override
    public String setFilterTemplate(String filterTemplateID) {
        this.filterTemplate = filterTemplateID;
        return this.filterTemplate;
    }

    @Override
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
}

