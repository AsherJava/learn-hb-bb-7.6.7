/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.RegionEnterNext
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineImpl
 *  com.jiuqi.nr.definition.util.LevelSetting
 *  com.jiuqi.nr.param.transfer.definition.dto.form.RegionSettingDTO
 */
package com.jiuqi.nr.nrdx.param.task.dto.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.RegionEnterNext;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineImpl;
import com.jiuqi.nr.definition.util.LevelSetting;
import com.jiuqi.nr.param.transfer.definition.dto.form.RegionSettingDTO;
import java.util.Date;

public class NrdxFormReginDTO {
    private String code;
    private String formKey;
    private int regionLeft;
    private int regionRight;
    private int regionTop;
    private int regionBottom;
    private DataRegionKind regionKind;
    private String inputOrderFieldKey;
    private String sortFieldsList;
    private int rowsInFloatRegion;
    private String gatherFields;
    private String gatherSetting;
    private String regionSettingKey;
    private String filterCondition;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private int maxRowCount;
    private boolean canDeleteRow;
    private boolean canInsertRow;
    private int pageSize;
    private String readOnlyCondition;
    private boolean showGatherDetailRows;
    private boolean showGatherDetailRowByOne;
    private boolean showGatherSummaryRow;
    private boolean allowDuplicateKey;
    private String showAddress;
    private boolean canFold;
    private String hideZeroGatherFields;
    private RegionSettingDTO regionSetting;
    private RegionEnterNext regionEnterNext;
    private LevelSetting levelSetting;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
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

    public DataRegionKind getRegionKind() {
        return this.regionKind;
    }

    public void setRegionKind(DataRegionKind regionKind) {
        this.regionKind = regionKind;
    }

    public String getInputOrderFieldKey() {
        return this.inputOrderFieldKey;
    }

    public void setInputOrderFieldKey(String inputOrderFieldKey) {
        this.inputOrderFieldKey = inputOrderFieldKey;
    }

    public String getSortFieldsList() {
        return this.sortFieldsList;
    }

    public void setSortFieldsList(String sortFieldsList) {
        this.sortFieldsList = sortFieldsList;
    }

    public int getRowsInFloatRegion() {
        return this.rowsInFloatRegion;
    }

    public void setRowsInFloatRegion(int rowsInFloatRegion) {
        this.rowsInFloatRegion = rowsInFloatRegion;
    }

    public String getGatherFields() {
        return this.gatherFields;
    }

    public void setGatherFields(String gatherFields) {
        this.gatherFields = gatherFields;
    }

    public String getGatherSetting() {
        return this.gatherSetting;
    }

    public void setGatherSetting(String gatherSetting) {
        this.gatherSetting = gatherSetting;
    }

    public String getRegionSettingKey() {
        return this.regionSettingKey;
    }

    public void setRegionSettingKey(String regionSettingKey) {
        this.regionSettingKey = regionSettingKey;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getMaxRowCount() {
        return this.maxRowCount;
    }

    public void setMaxRowCount(int maxRowCount) {
        this.maxRowCount = maxRowCount;
    }

    public boolean isCanDeleteRow() {
        return this.canDeleteRow;
    }

    public void setCanDeleteRow(boolean canDeleteRow) {
        this.canDeleteRow = canDeleteRow;
    }

    public boolean isCanInsertRow() {
        return this.canInsertRow;
    }

    public void setCanInsertRow(boolean canInsertRow) {
        this.canInsertRow = canInsertRow;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getReadOnlyCondition() {
        return this.readOnlyCondition;
    }

    public void setReadOnlyCondition(String readOnlyCondition) {
        this.readOnlyCondition = readOnlyCondition;
    }

    public boolean isShowGatherDetailRows() {
        return this.showGatherDetailRows;
    }

    public void setShowGatherDetailRows(boolean showGatherDetailRows) {
        this.showGatherDetailRows = showGatherDetailRows;
    }

    public boolean isShowGatherDetailRowByOne() {
        return this.showGatherDetailRowByOne;
    }

    public void setShowGatherDetailRowByOne(boolean showGatherDetailRowByOne) {
        this.showGatherDetailRowByOne = showGatherDetailRowByOne;
    }

    public boolean isShowGatherSummaryRow() {
        return this.showGatherSummaryRow;
    }

    public void setShowGatherSummaryRow(boolean showGatherSummaryRow) {
        this.showGatherSummaryRow = showGatherSummaryRow;
    }

    public boolean isAllowDuplicateKey() {
        return this.allowDuplicateKey;
    }

    public void setAllowDuplicateKey(boolean allowDuplicateKey) {
        this.allowDuplicateKey = allowDuplicateKey;
    }

    public String getShowAddress() {
        return this.showAddress;
    }

    public void setShowAddress(String showAddress) {
        this.showAddress = showAddress;
    }

    public boolean isCanFold() {
        return this.canFold;
    }

    public void setCanFold(boolean canFold) {
        this.canFold = canFold;
    }

    public String getHideZeroGatherFields() {
        return this.hideZeroGatherFields;
    }

    public void setHideZeroGatherFields(String hideZeroGatherFields) {
        this.hideZeroGatherFields = hideZeroGatherFields;
    }

    public RegionSettingDTO getRegionSetting() {
        return this.regionSetting;
    }

    public void setRegionSetting(RegionSettingDTO regionSettingDTO) {
        this.regionSetting = regionSettingDTO;
    }

    public RegionEnterNext getRegionEnterNext() {
        return this.regionEnterNext;
    }

    public void setRegionEnterNext(RegionEnterNext regionEnterNext) {
        this.regionEnterNext = regionEnterNext;
    }

    public LevelSetting getLevelSetting() {
        return this.levelSetting;
    }

    public void setLevelSetting(LevelSetting levelSetting) {
        this.levelSetting = levelSetting;
    }

    public DesignRegionSettingDefine value2Define(DesignDataRegionDefine regionParam) {
        regionParam.setRegionSettingKey(this.getRegionSettingKey());
        regionParam.setCode(this.getCode());
        regionParam.setFormKey(this.getFormKey());
        regionParam.setRegionLeft(this.getRegionLeft());
        regionParam.setRegionRight(this.getRegionRight());
        regionParam.setRegionTop(this.getRegionTop());
        regionParam.setRegionBottom(this.getRegionBottom());
        regionParam.setRegionKind(this.getRegionKind());
        regionParam.setInputOrderFieldKey(this.getInputOrderFieldKey());
        regionParam.setSortFieldsList(this.getSortFieldsList());
        regionParam.setRowsInFloatRegion(this.getRowsInFloatRegion());
        regionParam.setGatherFields(this.getGatherFields());
        regionParam.setLevelSetting(this.getLevelSetting());
        regionParam.setFilterCondition(this.getFilterCondition());
        regionParam.setKey(this.getKey());
        regionParam.setTitle(this.getTitle());
        regionParam.setOrder(this.getOrder());
        regionParam.setVersion(this.getVersion());
        regionParam.setUpdateTime(this.getUpdateTime());
        regionParam.setMaxRowCount(this.getMaxRowCount());
        regionParam.setCanDeleteRow(this.isCanDeleteRow());
        regionParam.setCanInsertRow(this.isCanInsertRow());
        regionParam.setPageSize(this.getPageSize());
        regionParam.setReadOnlyCondition(this.getReadOnlyCondition());
        regionParam.setShowGatherDetailRows(this.isShowGatherDetailRows());
        regionParam.setShowGatherDetailRowByOne(this.isShowGatherDetailRowByOne());
        regionParam.setShowGatherSummaryRow(this.isShowGatherSummaryRow());
        regionParam.setHideZeroGatherFields(this.getHideZeroGatherFields());
        regionParam.setAllowDuplicateKey(this.isAllowDuplicateKey());
        regionParam.setShowAddress(this.getShowAddress());
        regionParam.setIsCanFold(this.isCanFold());
        regionParam.setOwnerLevelAndId(this.getOwnerLevelAndId());
        regionParam.setRegionEnterNext(this.getRegionEnterNext());
        DesignRegionSettingDefineImpl define = null;
        if (this.regionSetting != null) {
            define = new DesignRegionSettingDefineImpl();
            this.regionSetting.value2Define((DesignRegionSettingDefine)define);
        }
        return define;
    }

    public static NrdxFormReginDTO valueOf(DataRegionDefine dataRegionDefine, RegionSettingDefine regionSettingDefine) {
        NrdxFormReginDTO nrdxReginInfoDTO = new NrdxFormReginDTO();
        nrdxReginInfoDTO.setRegionSettingKey(dataRegionDefine.getRegionSettingKey());
        nrdxReginInfoDTO.setCode(dataRegionDefine.getCode());
        nrdxReginInfoDTO.setFormKey(dataRegionDefine.getFormKey());
        nrdxReginInfoDTO.setRegionLeft(dataRegionDefine.getRegionLeft());
        nrdxReginInfoDTO.setRegionRight(dataRegionDefine.getRegionRight());
        nrdxReginInfoDTO.setRegionTop(dataRegionDefine.getRegionTop());
        nrdxReginInfoDTO.setRegionBottom(dataRegionDefine.getRegionBottom());
        nrdxReginInfoDTO.setRegionKind(dataRegionDefine.getRegionKind());
        nrdxReginInfoDTO.setInputOrderFieldKey(dataRegionDefine.getInputOrderFieldKey());
        nrdxReginInfoDTO.setSortFieldsList(dataRegionDefine.getSortFieldsList());
        nrdxReginInfoDTO.setRowsInFloatRegion(dataRegionDefine.getRowsInFloatRegion());
        nrdxReginInfoDTO.setGatherFields(dataRegionDefine.getGatherFields());
        nrdxReginInfoDTO.setLevelSetting(dataRegionDefine.getLevelSetting());
        nrdxReginInfoDTO.setFilterCondition(dataRegionDefine.getFilterCondition());
        nrdxReginInfoDTO.setKey(dataRegionDefine.getKey());
        nrdxReginInfoDTO.setTitle(dataRegionDefine.getTitle());
        nrdxReginInfoDTO.setOrder(dataRegionDefine.getOrder());
        nrdxReginInfoDTO.setVersion(dataRegionDefine.getVersion());
        nrdxReginInfoDTO.setUpdateTime(dataRegionDefine.getUpdateTime());
        nrdxReginInfoDTO.setMaxRowCount(dataRegionDefine.getMaxRowCount());
        nrdxReginInfoDTO.setCanDeleteRow(dataRegionDefine.getCanDeleteRow());
        nrdxReginInfoDTO.setCanInsertRow(dataRegionDefine.getCanInsertRow());
        nrdxReginInfoDTO.setPageSize(dataRegionDefine.getPageSize());
        nrdxReginInfoDTO.setReadOnlyCondition(dataRegionDefine.getReadOnlyCondition());
        nrdxReginInfoDTO.setShowGatherDetailRows(dataRegionDefine.getShowGatherDetailRows());
        nrdxReginInfoDTO.setShowGatherDetailRowByOne(dataRegionDefine.getShowGatherDetailRowByOne());
        nrdxReginInfoDTO.setShowGatherSummaryRow(dataRegionDefine.getShowGatherSummaryRow());
        nrdxReginInfoDTO.setHideZeroGatherFields(dataRegionDefine.getHideZeroGatherFields());
        nrdxReginInfoDTO.setAllowDuplicateKey(dataRegionDefine.getAllowDuplicateKey());
        nrdxReginInfoDTO.setShowAddress(dataRegionDefine.getShowAddress());
        nrdxReginInfoDTO.setCanFold(dataRegionDefine.getIsCanFold());
        nrdxReginInfoDTO.setOwnerLevelAndId(dataRegionDefine.getOwnerLevelAndId());
        nrdxReginInfoDTO.setRegionEnterNext(dataRegionDefine.getRegionEnterNext());
        nrdxReginInfoDTO.setRegionSetting(RegionSettingDTO.valueOf((RegionSettingDefine)regionSettingDefine));
        return nrdxReginInfoDTO;
    }
}

