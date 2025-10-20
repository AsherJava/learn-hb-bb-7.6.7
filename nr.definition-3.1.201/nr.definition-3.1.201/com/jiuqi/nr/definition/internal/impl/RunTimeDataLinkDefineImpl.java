/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLinks
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 */
package com.jiuqi.nr.definition.internal.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeDataRegionDefineImpl;
import com.jiuqi.nr.definition.util.DataLinkHelper;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DBAnno.DBTable(dbTable="NR_PARAM_DATALINK")
@DBAnno.DBLinks(value={@DBAnno.DBLink(linkWith=RunTimeDataRegionDefineImpl.class, linkField="key", field="regionKey"), @DBAnno.DBLink(linkWith=RunTimeEntityViewDefineImpl.class, linkField="key", field="selectViewKey")})
public class RunTimeDataLinkDefineImpl
implements DataLinkDefine {
    private static final Logger logger = LoggerFactory.getLogger(RunTimeDataLinkDefineImpl.class);
    private static final long serialVersionUID = 6906553571570362813L;
    public static final String TABLE_NAME = "NR_PARAM_DATALINK";
    public static final String FIELD_NAME_KEY = "DL_KEY";
    public static final String FIELD_NAME_REGION_KEY = "DL_REGION_KEY";
    @DBAnno.DBField(dbField="DL_REGION_KEY", isPk=false)
    private String regionKey;
    @DBAnno.DBField(dbField="dl_expression")
    private String linkExpression;
    @DBAnno.DBField(dbField="dl_field_key")
    private String linkFieldKey;
    @DBAnno.DBField(dbField="dl_binding_expression")
    private String bindingExpression;
    @DBAnno.DBField(dbField="dl_posX")
    private int posX;
    @DBAnno.DBField(dbField="dl_posy")
    private int posY;
    @DBAnno.DBField(dbField="dl_col_num")
    private int colNum;
    @DBAnno.DBField(dbField="dl_row_num")
    private int rowNum;
    @DBAnno.DBField(dbField="dl_edit_mode", tranWith="transDataLinkEditMode", dbType=Integer.class, appType=DataLinkEditMode.class)
    private DataLinkEditMode editMode;
    @DBAnno.DBField(dbField="dl_display_mode", tranWith="transEnumDisplayMode", dbType=Integer.class, appType=EnumDisplayMode.class)
    private EnumDisplayMode displayMode;
    private List<String> dataValidation;
    @DBAnno.DBField(dbField="dl_caption_fields")
    private String captionFieldsString;
    @DBAnno.DBField(dbField="dl_drop_down_Fields")
    private String dropDownFieldsString;
    @DBAnno.DBField(dbField="DL_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="dl_title")
    private String title;
    @DBAnno.DBField(dbField="dl_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="dl_version")
    private String version;
    @DBAnno.DBField(dbField="dl_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="dl_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, notUpdate=true)
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
    @DBAnno.DBField(dbField="dl_enum_linkage_status", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean enumLinkageStatus;
    @DBAnno.DBField(dbField="dl_filter_expression")
    private String filterExpression;
    @DBAnno.DBField(dbField="dl_ignore_permissions", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean ignorePermissions;
    @DBAnno.DBField(dbField="dl_format_properties", tranWith="transFormatProperties", dbType=String.class, appType=FormatProperties.class)
    private FormatProperties formatProperties;
    private String fieldTableId;
    @DBAnno.DBField(dbField="dl_filter_template")
    private String filterTemplateID;
    @DBAnno.DBField(dbField="dl_measureunit")
    private String measureUnit;

    @Override
    public boolean getEnumLinkageStatus() {
        return this.enumLinkageStatus;
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
            this.dataValidation = DataLinkHelper.getValidationRulesStr(this);
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

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public void setLinkExpression(String expression) {
        if (this.type == DataLinkType.DATA_LINK_TYPE_FIELD || this.type == DataLinkType.DATA_LINK_TYPE_INFO) {
            this.linkFieldKey = expression;
            this.linkExpression = null;
        } else {
            this.linkExpression = expression;
            this.linkFieldKey = null;
        }
    }

    public void setBindingExpression(String bindingExpression) {
        this.bindingExpression = bindingExpression;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public void setEditMode(DataLinkEditMode editMode) {
        this.editMode = editMode;
    }

    public void setDisplayMode(EnumDisplayMode displayMode) {
        this.displayMode = displayMode;
    }

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

    public void setCaptionFieldsString(String keys) {
        this.captionFieldsString = keys;
    }

    public void setDropDownFieldsString(String keys) {
        this.dropDownFieldsString = keys;
    }

    @Override
    public Boolean getAllowUndefinedCode() {
        if (this.allowUndefinedCode == null || this.allowUndefinedCode.booleanValue()) {
            Boolean fieldAllowUndefinedCode = DataLinkHelper.getAllowUndefinedCode(this);
            return fieldAllowUndefinedCode;
        }
        return false;
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
        if (this.formatProperties == null) {
            return DataLinkHelper.getFormatProperties(this);
        }
        return this.formatProperties;
    }

    public void setFormatProperties(FormatProperties formatProperties) {
        this.formatProperties = formatProperties;
    }

    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    public void setAllowNullAble(Boolean allowNullAble) {
        this.allowNullAble = allowNullAble;
    }

    @Override
    public Boolean getAllowNullAble() {
        if (this.allowNullAble != null && !this.allowNullAble.booleanValue()) {
            return false;
        }
        Boolean fieldAllowNullAble = DataLinkHelper.getAllowNullAble(this);
        if (fieldAllowNullAble != null) {
            return fieldAllowNullAble;
        }
        if (this.fieldTableId == null) {
            String fieldTableIds;
            this.fieldTableId = fieldTableIds = DataLinkHelper.getFieldTableId(this);
        }
        Boolean fieldAllowNullAbleForForm = DataLinkHelper.getAllowNullAble(this.fieldTableId, this.linkFieldKey);
        return fieldAllowNullAbleForForm;
    }

    public void setAllowNotLeafNodeRefer(boolean allowNotLeafNodeRefer) {
        this.allowNotLeafNodeRefer = allowNotLeafNodeRefer;
    }

    @Override
    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    @Override
    public String getEnumShowFullPath() {
        return this.isEnumShowFullPath;
    }

    public void setEnumShowFullPath(String isShowFullPath) {
        this.isEnumShowFullPath = isShowFullPath;
    }

    @Override
    public DataLinkType getType() {
        return this.type;
    }

    @Override
    public String getEnumTitleField() {
        return this.enumTitleField;
    }

    public void setEnumTitleField(String enumTitleField) {
        this.enumTitleField = enumTitleField;
    }

    @Override
    public String getEnumLinkage() {
        return this.enumLinkage;
    }

    public void setEnumLinkage(String enumLinkage) {
        this.enumLinkage = enumLinkage;
    }

    @Override
    public int getEnumCount() {
        return this.enumCount;
    }

    @Override
    public String getEnumPos() {
        return this.enumPos;
    }

    @Override
    public String getFilterExpression() {
        return this.filterExpression;
    }

    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }

    @Override
    public boolean isIgnorePermissions() {
        return this.ignorePermissions;
    }

    @Override
    public String getFilterTemplate() {
        return this.filterTemplateID;
    }

    @Override
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    private void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public void setFilterTemplate(String filterTemplateID) {
        this.filterTemplateID = filterTemplateID;
    }

    public void setIgnorePermissions(boolean ignorePermissions) {
        this.ignorePermissions = ignorePermissions;
    }

    public void setType(DataLinkType type) {
        String newExpression = this.getLinkExpression();
        this.type = type;
        this.setLinkExpression(newExpression);
    }

    public List<String> getSelfDataValidation() {
        return this.dataValidation;
    }

    public Boolean getSelfAllowNullAble() {
        return this.allowNullAble;
    }

    public FormatProperties getSelfFormatProperties() {
        return this.formatProperties;
    }

    public Boolean getSelfAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }
}

