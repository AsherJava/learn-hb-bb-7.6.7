/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.RegionEnterNext;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeDataLinkDefineImpl;
import com.jiuqi.nr.definition.util.DataRegionHelper;
import com.jiuqi.nr.definition.util.LevelSetting;
import com.jiuqi.nr.definition.util.SerializeUtils;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_DATAREGION")
@DBAnno.DBLink(linkWith=RunTimeDataLinkDefineImpl.class, linkField="regionKey", field="key")
public class RunTimeDataRegionDefineImpl
implements DataRegionDefine {
    private static final long serialVersionUID = 3410154056297454228L;
    public static final String TABLE_NAME = "NR_PARAM_DATAREGION";
    public static final String FIELD_NAME_KEY = "DR_KEY";
    public static final String FIELD_NAME_FORM_KEY = "DR_FORM_KEY";
    @DBAnno.DBField(dbField="dr_code")
    private String code;
    @DBAnno.DBField(dbField="DR_FORM_KEY")
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
    private int rowsInFloatRegion;
    @DBAnno.DBField(dbField="dr_gather_Fields")
    private String gatherFields;
    @DBAnno.DBField(dbField="dr_gather_setting")
    private String gatherSetting;
    @DBAnno.DBField(dbField="dr_region_setting")
    private String multipleLevelSettingKey;
    @DBAnno.DBField(dbField="dr_filter")
    private String filterCondition;
    @DBAnno.DBField(dbField="DR_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="dr_title")
    private String title;
    @DBAnno.DBField(dbField="dr_order")
    private String order;
    @DBAnno.DBField(dbField="dr_version")
    private String version;
    @DBAnno.DBField(dbField="dr_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="dr_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, notUpdate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="dr_max_Row_count")
    private int maxRowCount;
    @DBAnno.DBField(dbField="dr_can_delete_row", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean canDeleteRow;
    @DBAnno.DBField(dbField="dr_can_insert_row", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean canInsertRow;
    @DBAnno.DBField(dbField="dr_page_size")
    private int pageSize;
    @DBAnno.DBField(dbField="dr_readonly_condition")
    private String readOnlyCondition;
    @DBAnno.DBField(dbField="dr_show_detail_rows", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean showGatherDetailRows;
    @DBAnno.DBField(dbField="dr_show_summary_rows", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean showGatherSummaryRow;
    private Boolean allowDuplicateKey;
    @DBAnno.DBField(dbField="dr_show_address")
    private String showAddress;
    @DBAnno.DBField(dbField="dr_level_isfold", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean isCanFold;
    @DBAnno.DBField(dbField="dr_hide_gather_fields")
    private String hideZeroGatherFields;
    private String bizkeyFields = null;
    @DBAnno.DBField(dbField="dr_enter_next", tranWith="transRegionEnterNext", dbType=Integer.class, appType=RegionEnterNext.class)
    private RegionEnterNext regionEnterNext;
    @DBAnno.DBField(dbField="dr_show_detail_rows_one", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean showGatherDetailRowByOne;
    private LevelSetting levelSetting;
    @DBAnno.DBField(dbField="dr_display_level")
    private String displayLevel;

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

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public void setRegionLeft(int regionLeft) {
        this.regionLeft = regionLeft;
    }

    public void setRegionRight(int regionRight) {
        this.regionRight = regionRight;
    }

    public void setRegionTop(int regionTop) {
        this.regionTop = regionTop;
    }

    public void setRegionBottom(int regionBottom) {
        this.regionBottom = regionBottom;
    }

    public void setRegionKind(DataRegionKind regionKind) {
        this.regionKind = regionKind;
    }

    public void setBindingExpression(String bindingExpression) {
    }

    public void setInputOrderFieldKey(String inputOrderFieldKey) {
        this.inputOrderFieldKey = inputOrderFieldKey;
    }

    public void setSortFieldsList(String fieldsList) {
        this.sortFieldsList = fieldsList;
    }

    public void setRowsInFloatRegion(int rowCount) {
        this.rowsInFloatRegion = rowCount;
    }

    public void setGatherFields(String fields) {
        this.gatherFields = fields;
    }

    public void setGatherSetting(String setting) {
        this.gatherSetting = setting;
    }

    public void setRegionSettingKey(String settingKey) {
        this.multipleLevelSettingKey = settingKey;
    }

    public void setCardInputFormKey(String cardFormKey) {
    }

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

    public void setMaxRowCount(int count) {
        this.maxRowCount = count;
    }

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

    public void setCanInsertRow(boolean canInsertRow) {
        this.canInsertRow = canInsertRow;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setMasterEntitiesKey(String masterEntitiesKey) {
    }

    @Override
    public String getReadOnlyCondition() {
        return this.readOnlyCondition;
    }

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
        if (null == this.allowDuplicateKey) {
            this.allowDuplicateKey = DataRegionHelper.allowRepeatCode(this);
        }
        return this.allowDuplicateKey;
    }

    public void setShowGatherDetailRows(boolean showGatherDetailRows) {
        this.showGatherDetailRows = showGatherDetailRows;
    }

    public void setShowGatherSummaryRow(boolean showGatherSummaryRow) {
        this.showGatherSummaryRow = showGatherSummaryRow;
    }

    public void setAllowDuplicateKey(boolean allowDuplicateKey) {
        this.allowDuplicateKey = allowDuplicateKey;
    }

    @Override
    public String getShowAddress() {
        return this.showAddress;
    }

    @Override
    public boolean getIsCanFold() {
        return this.isCanFold;
    }

    public void setHideZeroGatherFields(String hideZeroGatherFields) {
        this.hideZeroGatherFields = hideZeroGatherFields;
    }

    @Override
    public String getHideZeroGatherFields() {
        return this.hideZeroGatherFields;
    }

    @Override
    public String getBizKeyFields() {
        if (null == this.bizkeyFields) {
            this.bizkeyFields = DataRegionHelper.getBizKeyFields(this);
        }
        return this.bizkeyFields;
    }

    @Override
    public RegionEnterNext getRegionEnterNext() {
        return this.regionEnterNext;
    }

    @Override
    public boolean getShowGatherDetailRowByOne() {
        return this.showGatherDetailRowByOne;
    }

    public void setShowGatherDetailRowByOne(boolean showGatherDetailRowByOne) {
        this.showGatherDetailRowByOne = showGatherDetailRowByOne;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getDisplayLevel() {
        return this.displayLevel;
    }

    public void setDisplayLevel(String displayLevel) {
        this.displayLevel = displayLevel;
    }
}

