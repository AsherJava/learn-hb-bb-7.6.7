/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.util.LevelSetting
 */
package com.jiuqi.nr.task.form.region.dto;

import com.jiuqi.nr.definition.util.LevelSetting;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.ext.face.ConfigExt;
import com.jiuqi.nr.task.form.region.dto.DataRegionDTO;
import com.jiuqi.nr.task.form.region.dto.RegionExtensionDTO;
import java.util.List;

public class DataRegionSettingDTO
extends DataRegionDTO
implements ConfigExt {
    private String inputOrderFieldKey;
    private String sortFieldsList;
    private String linkExpression;
    private Integer rowsInFloatRegion = 1;
    private String gatherFields;
    private LevelSetting levelSetting;
    private Boolean showGatherDetailRows = false;
    private Boolean showGatherDetailRowByOne = false;
    private Boolean showGatherSummaryRow = false;
    private Integer maxRowCount = 0;
    private Boolean canDeleteRow = false;
    private Boolean canInsertRow = false;
    private Integer pageSize = 0;
    private Boolean allowDuplicateKey = false;
    private Boolean isCanFold = false;
    private String hideZeroGatherFields;
    private String bizKeyFields;
    private List<ConfigDTO> configData;
    private String displayLevelFields;
    private String readOnlyCondition;
    private String showAddress;
    private String filterCondition;
    private RegionExtensionDTO regionExtension;

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

    public String getLinkExpression() {
        return this.linkExpression;
    }

    public void setLinkExpression(String linkExpression) {
        this.linkExpression = linkExpression;
    }

    public Integer getRowsInFloatRegion() {
        return this.rowsInFloatRegion;
    }

    public void setRowsInFloatRegion(Integer rowsInFloatRegion) {
        this.rowsInFloatRegion = rowsInFloatRegion == null ? 1 : rowsInFloatRegion;
    }

    public String getGatherFields() {
        return this.gatherFields;
    }

    public void setGatherFields(String gatherFields) {
        this.gatherFields = gatherFields;
    }

    public LevelSetting getLevelSetting() {
        return this.levelSetting;
    }

    public void setLevelSetting(LevelSetting levelSetting) {
        this.levelSetting = levelSetting;
    }

    public Boolean getShowGatherDetailRows() {
        return this.showGatherDetailRows;
    }

    public void setShowGatherDetailRows(Boolean showGatherDetailRows) {
        this.showGatherDetailRows = showGatherDetailRows != null && showGatherDetailRows != false;
    }

    public Boolean getShowGatherDetailRowByOne() {
        return this.showGatherDetailRowByOne;
    }

    public void setShowGatherDetailRowByOne(Boolean showGatherDetailRowByOne) {
        this.showGatherDetailRowByOne = showGatherDetailRowByOne != null && showGatherDetailRowByOne != false;
    }

    public Boolean getShowGatherSummaryRow() {
        return this.showGatherSummaryRow;
    }

    public void setShowGatherSummaryRow(Boolean showGatherSummaryRow) {
        this.showGatherSummaryRow = showGatherSummaryRow != null && showGatherSummaryRow != false;
    }

    public Integer getMaxRowCount() {
        return this.maxRowCount;
    }

    public void setMaxRowCount(Integer maxRowCount) {
        this.maxRowCount = maxRowCount == null ? 0 : maxRowCount;
    }

    public Boolean getCanDeleteRow() {
        return this.canDeleteRow;
    }

    public void setCanDeleteRow(Boolean canDeleteRow) {
        this.canDeleteRow = canDeleteRow;
    }

    public Boolean getCanInsertRow() {
        return this.canInsertRow;
    }

    public void setCanInsertRow(Boolean canInsertRow) {
        this.canInsertRow = canInsertRow;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize == null ? 0 : pageSize;
    }

    public Boolean getAllowDuplicateKey() {
        return this.allowDuplicateKey;
    }

    public void setAllowDuplicateKey(Boolean allowDuplicateKey) {
        this.allowDuplicateKey = allowDuplicateKey != null && allowDuplicateKey != false;
    }

    public Boolean getCanFold() {
        return this.isCanFold;
    }

    public void setCanFold(Boolean canFold) {
        this.isCanFold = canFold != null && canFold != false;
    }

    public String getHideZeroGatherFields() {
        return this.hideZeroGatherFields;
    }

    public void setHideZeroGatherFields(String hideZeroGatherFields) {
        this.hideZeroGatherFields = hideZeroGatherFields;
    }

    public String getBizKeyFields() {
        return this.bizKeyFields;
    }

    public void setBizKeyFields(String bizKeyFields) {
        this.bizKeyFields = bizKeyFields;
    }

    @Override
    public List<ConfigDTO> getConfigData() {
        return this.configData;
    }

    public void setConfigData(List<ConfigDTO> configData) {
        this.configData = configData;
    }

    public String getReadOnlyCondition() {
        return this.readOnlyCondition;
    }

    public void setReadOnlyCondition(String readOnlyCondition) {
        this.readOnlyCondition = readOnlyCondition;
    }

    public String getShowAddress() {
        return this.showAddress;
    }

    public void setShowAddress(String showAddress) {
        this.showAddress = showAddress;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    @Override
    public RegionExtensionDTO getRegionExtension() {
        return this.regionExtension;
    }

    @Override
    public void setRegionExtension(RegionExtensionDTO regionExtension) {
        this.regionExtension = regionExtension;
    }

    public String getDisplayLevelFields() {
        return this.displayLevelFields;
    }

    public void setDisplayLevelFields(String displayLevelFields) {
        this.displayLevelFields = displayLevelFields;
    }
}

