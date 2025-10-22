/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 */
package com.jiuqi.nr.io.params.base;

import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegionData {
    private String key;
    private String formKey;
    private String formCode;
    private UUID parentKey;
    private int type;
    private int regionLeft;
    private int regionRight;
    private int regionTop;
    private int regionBottom;
    private String title;
    private String order;
    private String floatNumber;
    private boolean keyIsUnique;
    private boolean keyCanNull;
    private int summeryType;
    private String numberStructure;
    private String orderFieldKey;
    private int pageSize;
    private String filterCondition;
    private String tablekey;
    private boolean autoAddRow;
    private int autoAddRowSpan;
    private boolean canInsertRow;
    private boolean canDeleteRow;
    private boolean minRowNumActive;
    private String cardFormInfo;
    private String gradingSumInfo;
    private String rowExpandField;
    private String expandFilter;
    private List<RegionData> childrenRegions = new ArrayList<RegionData>();
    private String readOnlyCondition;
    private boolean readOnly = false;
    private List<EntityDefaultValue> regionEntityDefaultValue = new ArrayList<EntityDefaultValue>();
    private int defaultRowCount;
    private int maxRowCount;
    private boolean allowDuplicateKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public UUID getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(UUID parentKey) {
        this.parentKey = parentKey;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRegionLeft() {
        return this.regionLeft;
    }

    public void setRegionLeft(int regionLeft) {
        this.regionLeft = regionLeft;
    }

    public int getRegionRight() {
        return this.regionRight;
    }

    public void setRegionRight(int regionRight) {
        this.regionRight = regionRight;
    }

    public int getRegionTop() {
        return this.regionTop;
    }

    public void setRegionTop(int regionTop) {
        this.regionTop = regionTop;
    }

    public int getRegionBottom() {
        return this.regionBottom;
    }

    public void setRegionBottom(int regionBottom) {
        this.regionBottom = regionBottom;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFloatNumber() {
        return this.floatNumber;
    }

    public void setFloatNumber(String floatNumber) {
        this.floatNumber = floatNumber;
    }

    public boolean isKeyIsUnique() {
        return this.keyIsUnique;
    }

    public void setKeyIsUnique(boolean keyIsUnique) {
        this.keyIsUnique = keyIsUnique;
    }

    public boolean isKeyCanNull() {
        return this.keyCanNull;
    }

    public void setKeyCanNull(boolean keyCanNull) {
        this.keyCanNull = keyCanNull;
    }

    public int getSummeryType() {
        return this.summeryType;
    }

    public void setSummeryType(int summeryType) {
        this.summeryType = summeryType;
    }

    public String getNumberStructure() {
        return this.numberStructure;
    }

    public void setNumberStructure(String numberStructure) {
        this.numberStructure = numberStructure;
    }

    public String getOrderFieldKey() {
        return this.orderFieldKey;
    }

    public void setOrderFieldKey(String orderFieldKey) {
        this.orderFieldKey = orderFieldKey;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getTablekey() {
        return this.tablekey;
    }

    public void setTablekey(String tablekey) {
        this.tablekey = tablekey;
    }

    public int getAutoAddRowSpan() {
        return this.autoAddRowSpan;
    }

    public void setAutoAddRowSpan(int autoAddRowSpan) {
        this.autoAddRowSpan = autoAddRowSpan;
    }

    public boolean isCanInsertRow() {
        return this.canInsertRow;
    }

    public void setCanInsertRow(boolean canInsertRow) {
        this.canInsertRow = canInsertRow;
    }

    public boolean isCanDeleteRow() {
        return this.canDeleteRow;
    }

    public void setCanDeleteRow(boolean canDeleteRow) {
        this.canDeleteRow = canDeleteRow;
    }

    public String getCardFormInfo() {
        return this.cardFormInfo;
    }

    public void setCardFormInfo(String cardFormInfo) {
        this.cardFormInfo = cardFormInfo;
    }

    public String getGradingSumInfo() {
        return this.gradingSumInfo;
    }

    public void setGradingSumInfo(String gradingSumInfo) {
        this.gradingSumInfo = gradingSumInfo;
    }

    public String getRowExpandField() {
        return this.rowExpandField;
    }

    public void setRowExpandField(String rowExpandField) {
        this.rowExpandField = rowExpandField;
    }

    public String getExpandFilter() {
        return this.expandFilter;
    }

    public void setExpandFilter(String expandFilter) {
        this.expandFilter = expandFilter;
    }

    public boolean isAutoAddRow() {
        return this.autoAddRow;
    }

    public void setAutoAddRow(boolean autoAddRow) {
        this.autoAddRow = autoAddRow;
    }

    public boolean isMinRowNumActive() {
        return this.minRowNumActive;
    }

    public void setMinRowNumActive(boolean minRowNumActive) {
        this.minRowNumActive = minRowNumActive;
    }

    public List<RegionData> getChildrenRegions() {
        return this.childrenRegions;
    }

    public void setChildrenRegions(List<RegionData> childrenRegions) {
        this.childrenRegions = childrenRegions;
    }

    public String getReadOnlyCondition() {
        return this.readOnlyCondition;
    }

    public void setReadOnlyCondition(String readOnlyCondition) {
        this.readOnlyCondition = readOnlyCondition;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public int getDefaultRowCount() {
        return this.defaultRowCount;
    }

    public void setDefaultRowCount(int defaultRowCount) {
        this.defaultRowCount = defaultRowCount;
    }

    public int getMaxRowCount() {
        return this.maxRowCount;
    }

    public void setMaxRowCount(int maxRowCount) {
        this.maxRowCount = maxRowCount;
    }

    public boolean getAllowDuplicateKey() {
        return this.allowDuplicateKey;
    }

    public void setAllowDuplicateKey(boolean allowDuplicateKey) {
        this.allowDuplicateKey = allowDuplicateKey;
    }

    public List<EntityDefaultValue> getRegionEntityDefaultValue() {
        return this.regionEntityDefaultValue;
    }

    public void setRegionEntityDefaultValue(List<EntityDefaultValue> regionEntityDefaultValue) {
        this.regionEntityDefaultValue = regionEntityDefaultValue;
    }

    public void initialize(DataRegionDefine dataRegionDefine) {
        this.key = dataRegionDefine.getKey();
        this.formKey = dataRegionDefine.getFormKey();
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        FormDefine formDefine = runTimeViewController.queryFormById(this.formKey);
        this.formCode = formDefine.getFormCode();
        this.parentKey = null;
        this.type = dataRegionDefine.getRegionKind() != null ? dataRegionDefine.getRegionKind().getValue() : DataRegionKind.DATA_REGION_SIMPLE.getValue();
        this.regionLeft = dataRegionDefine.getRegionLeft();
        this.regionRight = dataRegionDefine.getRegionRight();
        this.regionTop = dataRegionDefine.getRegionTop();
        this.regionBottom = dataRegionDefine.getRegionBottom();
        this.title = dataRegionDefine.getTitle();
        this.order = dataRegionDefine.getOrder();
        this.floatNumber = "";
        this.keyIsUnique = false;
        this.keyCanNull = true;
        this.summeryType = 0;
        this.numberStructure = "";
        this.defaultRowCount = dataRegionDefine.getRowsInFloatRegion();
        this.maxRowCount = dataRegionDefine.getMaxRowCount();
        if (dataRegionDefine.getInputOrderFieldKey() != null) {
            this.orderFieldKey = dataRegionDefine.getInputOrderFieldKey().toString();
        }
        this.pageSize = dataRegionDefine.getPageSize();
        this.filterCondition = dataRegionDefine.getFilterCondition();
        this.readOnlyCondition = dataRegionDefine.getReadOnlyCondition();
        this.tablekey = "";
        this.autoAddRow = false;
        this.autoAddRowSpan = 0;
        this.canInsertRow = dataRegionDefine.getCanInsertRow();
        this.canDeleteRow = dataRegionDefine.getCanDeleteRow();
        this.minRowNumActive = false;
        this.cardFormInfo = "";
        this.gradingSumInfo = dataRegionDefine.getGatherSetting();
        this.rowExpandField = "";
        this.expandFilter = "";
        this.allowDuplicateKey = dataRegionDefine.getAllowDuplicateKey();
        RegionSettingDefine regionSettingDefine = runTimeViewController.getRegionSetting(this.key);
        if (regionSettingDefine != null) {
            this.regionEntityDefaultValue = regionSettingDefine.getEntityDefaultValue();
        }
    }
}

