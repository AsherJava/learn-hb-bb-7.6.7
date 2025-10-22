/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.RegionEnterNext;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl;
import com.jiuqi.nr.definition.util.LevelSetting;
import com.jiuqi.nr.definition.util.SerializeUtils;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_DATAREGION_DES")
@DBAnno.DBLink(linkWith=DesignDataLinkDefineImpl.class, linkField="regionKey", field="key")
public class DesignDataRegionDefineImpl
implements DesignDataRegionDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="dr_code")
    private String code;
    @DBAnno.DBField(dbField="dr_form_key")
    private String formKey;
    @DBAnno.DBField(dbField="dr_left")
    private int regionLeft;
    @DBAnno.DBField(dbField="dr_right")
    private int regionRight;
    @DBAnno.DBField(dbField="dr_top")
    private int regionTop;
    @DBAnno.DBField(dbField="dr_bottom")
    private int regionBottom;
    @DBAnno.DBField(dbField="dr_kind", tranWith="transDataRegionKind", dbType=Integer.class, appType=DataRegionKind.class)
    private DataRegionKind regionKind;
    @DBAnno.DBField(dbField="dr_input_order_field")
    private String inputOrderFieldKey;
    @DBAnno.DBField(dbField="dr_sort_fields")
    private String sortFieldsList;
    @DBAnno.DBField(dbField="dr_rows_count")
    private int rowsInFloatRegion = 1;
    @DBAnno.DBField(dbField="dr_gather_Fields")
    private String gatherFields;
    @DBAnno.DBField(dbField="dr_gather_setting")
    private String gatherSetting;
    @DBAnno.DBField(dbField="dr_region_setting")
    private String multipleLevelSettingKey;
    @DBAnno.DBField(dbField="dr_filter")
    private String filterCondition;
    @DBAnno.DBField(dbField="dr_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="dr_title")
    private String title;
    @DBAnno.DBField(dbField="dr_order")
    private String order;
    @DBAnno.DBField(dbField="dr_version")
    private String version;
    @DBAnno.DBField(dbField="dr_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="dr_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="dr_max_Row_count")
    private int maxRowCount;
    @DBAnno.DBField(dbField="dr_can_delete_row", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean canDeleteRow = true;
    @DBAnno.DBField(dbField="dr_can_insert_row", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean canInsertRow = true;
    @DBAnno.DBField(dbField="dr_page_size")
    private int pageSize;
    @DBAnno.DBField(dbField="dr_readonly_condition")
    private String readOnlyCondition;
    @DBAnno.DBField(dbField="dr_show_detail_rows", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean showGatherDetailRows;
    @DBAnno.DBField(dbField="dr_show_summary_rows", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean showGatherSummaryRow;
    @DBAnno.DBField(dbField="dr_show_address")
    private String showAddress;
    @DBAnno.DBField(dbField="dr_level_isfold", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean isCanFold;
    @DBAnno.DBField(dbField="dr_hide_gather_fields")
    private String hideZeroGatherFields;
    @DBAnno.DBField(dbField="dr_enter_next", tranWith="transRegionEnterNext", dbType=Integer.class, appType=RegionEnterNext.class)
    private RegionEnterNext regionEnterNext = RegionEnterNext.BOTTOM;
    @DBAnno.DBField(dbField="dr_show_detail_rows_one", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean showGatherDetailRowByOne;
    private LevelSetting levelSetting;
    @DBAnno.DBField(dbField="dr_display_level")
    private String displayLevel;

    public DesignDataRegionDefineImpl() {
    }

    public DesignDataRegionDefineImpl(DesignDataRegionDefineImpl define) {
        this.code = define.code;
        this.formKey = define.formKey;
        this.regionLeft = define.regionLeft;
        this.regionRight = define.regionRight;
        this.regionTop = define.regionTop;
        this.regionBottom = define.regionBottom;
        this.regionKind = define.regionKind;
        this.inputOrderFieldKey = define.inputOrderFieldKey;
        this.sortFieldsList = define.sortFieldsList;
        this.rowsInFloatRegion = define.rowsInFloatRegion;
        this.gatherFields = define.gatherFields;
        this.gatherSetting = define.gatherSetting;
        this.multipleLevelSettingKey = define.multipleLevelSettingKey;
        this.filterCondition = define.filterCondition;
        this.key = define.key;
        this.title = define.title;
        this.order = define.order;
        this.version = define.version;
        this.ownerLevelAndId = define.ownerLevelAndId;
        this.updateTime = define.updateTime;
        this.maxRowCount = define.maxRowCount;
        this.canDeleteRow = define.canDeleteRow;
        this.canInsertRow = define.canInsertRow;
        this.pageSize = define.pageSize;
        this.readOnlyCondition = define.readOnlyCondition;
        this.showGatherDetailRows = define.showGatherDetailRows;
        this.showGatherSummaryRow = define.showGatherSummaryRow;
        this.showAddress = define.showAddress;
        this.isCanFold = define.isCanFold;
        this.hideZeroGatherFields = define.hideZeroGatherFields;
        this.regionEnterNext = define.regionEnterNext;
        this.showGatherDetailRowByOne = define.showGatherDetailRowByOne;
        this.levelSetting = define.levelSetting;
        this.displayLevel = define.displayLevel;
    }

    @Override
    public String getFormKey() {
        return this.formKey;
    }

    @Override
    public int getRegionLeft() {
        return this.regionLeft;
    }

    @Override
    public int getRegionRight() {
        return this.regionRight;
    }

    @Override
    public int getRegionTop() {
        return this.regionTop;
    }

    @Override
    public int getRegionBottom() {
        return this.regionBottom;
    }

    @Override
    public DataRegionKind getRegionKind() {
        return this.regionKind;
    }

    public int getRegionKindDB() {
        return this.regionKind == null ? DataRegionKind.DATA_REGION_ROW_LIST.getValue() : this.regionKind.getValue();
    }

    public void setRegionKindDB(Integer type) {
        this.regionKind = DataRegionKind.forValue(type);
    }

    @Override
    public String getBindingExpression() {
        return null;
    }

    @Override
    public String getInputOrderFieldKey() {
        return this.inputOrderFieldKey;
    }

    @Override
    public String getSortFieldsList() {
        return this.sortFieldsList;
    }

    @Override
    public int getRowsInFloatRegion() {
        return this.rowsInFloatRegion;
    }

    @Override
    public String getGatherFields() {
        return this.gatherFields;
    }

    @Override
    public String getGatherSetting() {
        if (this.getLevelSetting() == null) {
            return null;
        }
        return this.getLevelSetting().getCode();
    }

    @Override
    public LevelSetting getLevelSetting() {
        if (this.gatherSetting == null) {
            return null;
        }
        if (this.levelSetting == null) {
            try {
                this.levelSetting = SerializeUtils.jsonDeserialize(this.gatherSetting, LevelSetting.class);
            }
            catch (IOException e) {
                this.levelSetting = new LevelSetting(0, this.gatherSetting, "");
            }
        }
        return this.levelSetting;
    }

    @Override
    public String getRegionSettingKey() {
        return this.multipleLevelSettingKey;
    }

    @Override
    public String getCardInputFormKey() {
        return null;
    }

    @Override
    public String getFilterCondition() {
        return this.filterCondition;
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
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    @Override
    public void setRegionLeft(int regionLeft) {
        this.regionLeft = regionLeft;
    }

    @Override
    public void setRegionRight(int regionRight) {
        this.regionRight = regionRight;
    }

    @Override
    public void setRegionTop(int regionTop) {
        this.regionTop = regionTop;
    }

    @Override
    public void setRegionBottom(int regionBottom) {
        this.regionBottom = regionBottom;
    }

    @Override
    public void setRegionKind(DataRegionKind regionKind) {
        this.regionKind = regionKind;
    }

    @Override
    public void setBindingExpression(String bindingExpression) {
    }

    @Override
    public void setInputOrderFieldKey(String inputOrderFieldKey) {
        this.inputOrderFieldKey = inputOrderFieldKey;
    }

    @Override
    public void setSortFieldsList(String fieldsList) {
        this.sortFieldsList = fieldsList;
    }

    @Override
    public void setRowsInFloatRegion(int rowCount) {
        this.rowsInFloatRegion = rowCount;
    }

    @Override
    public void setGatherFields(String fields) {
        this.gatherFields = fields;
    }

    @Override
    public void setGatherSetting(String setting) {
        this.levelSetting = null;
        this.gatherSetting = setting;
    }

    @Override
    public void setLevelSetting(LevelSetting levelSetting) {
        this.levelSetting = levelSetting;
        if (null == levelSetting) {
            this.gatherSetting = null;
        } else {
            try {
                this.gatherSetting = SerializeUtils.jsonSerializeToString(levelSetting);
            }
            catch (JsonProcessingException ex) {
                this.gatherSetting = null;
            }
        }
    }

    @Override
    public void setRegionSettingKey(String settingKey) {
        this.multipleLevelSettingKey = settingKey;
    }

    @Override
    public void setCardInputFormKey(String cardFormKey) {
    }

    @Override
    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    @Override
    public int getMaxRowCount() {
        return this.maxRowCount;
    }

    @Override
    public boolean getCanDeleteRow() {
        return this.canDeleteRow;
    }

    @Override
    public void setMaxRowCount(int count) {
        this.maxRowCount = count;
    }

    @Override
    public void setCanDeleteRow(boolean canDeleteRow) {
        this.canDeleteRow = canDeleteRow;
    }

    @Override
    public boolean getCanInsertRow() {
        return this.canInsertRow;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public String getMasterEntitiesKey() {
        return null;
    }

    @Override
    public void setCanInsertRow(boolean canInsertRow) {
        this.canInsertRow = canInsertRow;
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public void setMasterEntitiesKey(String masterEntitiesKey) {
    }

    @Override
    public String getReadOnlyCondition() {
        return this.readOnlyCondition;
    }

    @Override
    public void setReadOnlyCondition(String readOnlyCondition) {
        this.readOnlyCondition = readOnlyCondition;
    }

    @Override
    public boolean getShowGatherDetailRows() {
        return this.showGatherDetailRows;
    }

    @Override
    public boolean getShowGatherSummaryRow() {
        return this.showGatherSummaryRow;
    }

    @Override
    public boolean getAllowDuplicateKey() {
        return false;
    }

    @Override
    public void setShowGatherDetailRows(boolean showGatherDetailRows) {
        this.showGatherDetailRows = showGatherDetailRows;
    }

    @Override
    public void setShowGatherSummaryRow(boolean showGatherSummaryRow) {
        this.showGatherSummaryRow = showGatherSummaryRow;
    }

    @Override
    public void setAllowDuplicateKey(boolean allowDuplicateKey) {
    }

    @Override
    public String getShowAddress() {
        return this.showAddress;
    }

    @Override
    public void setShowAddress(String showAddress) {
        this.showAddress = showAddress;
    }

    @Override
    public boolean getIsCanFold() {
        return this.isCanFold;
    }

    @Override
    public void setIsCanFold(boolean isCanFold) {
        this.isCanFold = isCanFold;
    }

    @Override
    public String getHideZeroGatherFields() {
        return this.hideZeroGatherFields;
    }

    @Override
    public void setHideZeroGatherFields(String hideZeroGatherFields) {
        this.hideZeroGatherFields = hideZeroGatherFields;
    }

    @Override
    public String getBizKeyFields() {
        return null;
    }

    @Override
    public void setBizKeyFields(String regionBizKeyFields) {
    }

    @Override
    public RegionEnterNext getRegionEnterNext() {
        return this.regionEnterNext;
    }

    @Override
    public void setRegionEnterNext(RegionEnterNext regionEnterNext) {
        this.regionEnterNext = regionEnterNext;
    }

    @Override
    public boolean getShowGatherDetailRowByOne() {
        return this.showGatherDetailRowByOne;
    }

    @Override
    public void setShowGatherDetailRowByOne(boolean showGatherDetailRowByOne) {
        this.showGatherDetailRowByOne = showGatherDetailRowByOne;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getDisplayLevel() {
        return this.displayLevel;
    }

    @Override
    public void setDisplayLevel(String level) {
        this.displayLevel = level;
    }
}

