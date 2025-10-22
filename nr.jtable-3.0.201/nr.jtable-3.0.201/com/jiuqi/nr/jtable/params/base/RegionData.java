/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.RegionEnterNext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine
 *  com.jiuqi.nr.definition.facade.RowNumberSetting
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.service.RunTimeExtentStyleService
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.ExtentStyle
 *  com.jiuqi.nr.definition.util.LevelSetting
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.RegionEnterNext;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.service.RunTimeExtentStyleService;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.ExtentStyle;
import com.jiuqi.nr.definition.util.LevelSetting;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RecordCardData;
import com.jiuqi.nr.jtable.params.base.RegionEdgeStyle;
import com.jiuqi.nr.jtable.params.base.RegionExtentGridData;
import com.jiuqi.nr.jtable.params.base.RegionNumber;
import com.jiuqi.nr.jtable.params.base.RegionSettingData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.GradeCellInfo;
import com.jiuqi.nr.jtable.params.input.RegionGradeInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionData {
    private static final Logger logger = LoggerFactory.getLogger(RegionData.class);
    private String key;
    private String formKey;
    private String formCode;
    private String parentKey;
    private int type;
    private int regionLeft;
    private int regionRight;
    private int regionTop;
    private int regionBottom;
    private String title;
    private String order;
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
    private List<EntityDefaultValue> regionEntityDefaultValue = new ArrayList<EntityDefaultValue>();
    private List<RegionData> childrenRegions = new ArrayList<RegionData>();
    private List<LinkData> dataLinks = new ArrayList<LinkData>();
    private List<RegionTab> tabs = new ArrayList<RegionTab>();
    private String readOnlyCondition;
    private boolean readOnly = false;
    private RegionNumber regionNumber;
    private int defaultRowCount;
    private int maxRowCount;
    private boolean allowDuplicateKey;
    private List<CellQueryInfo> cells = new ArrayList<CellQueryInfo>();
    private RegionGradeInfo grade;
    private RegionEdgeStyle lastRowStyle;
    private RegionEdgeStyle lastColumnStyle;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String errors;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private List<String> fillLinks;
    private RecordCardData cardRecord;
    private RegionExtentGridData extentGridData;
    private int enterNext;
    private Map<String, Integer> linkLevelMap;

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

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
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

    public List<LinkData> getDataLinks() {
        return this.dataLinks;
    }

    public void setDataLinks(List<LinkData> dataLinks) {
        this.dataLinks = dataLinks;
        if (dataLinks.isEmpty()) {
            this.errors = this.errors + "\u533a\u57df\u65e0\u94fe\u63a5\u3002";
        }
    }

    public List<RegionTab> getTabs() {
        return this.tabs;
    }

    public void setTabs(List<RegionTab> tabs) {
        this.tabs = tabs;
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

    public RegionNumber getRegionNumber() {
        return this.regionNumber;
    }

    public void setRegionNumber(RegionNumber regionNumber) {
        this.regionNumber = regionNumber;
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

    public List<CellQueryInfo> getCells() {
        return this.cells;
    }

    public void setCells(List<CellQueryInfo> cells) {
        this.cells = cells;
    }

    public RegionGradeInfo getGrade() {
        return this.grade;
    }

    public void setGrade(RegionGradeInfo grade) {
        this.grade = grade;
    }

    public RegionEdgeStyle getLastRowStyle() {
        return this.lastRowStyle;
    }

    public void setLastRowStyle(RegionEdgeStyle lastRowStyle) {
        this.lastRowStyle = lastRowStyle;
    }

    public RegionEdgeStyle getLastColumnStyle() {
        return this.lastColumnStyle;
    }

    public void setLastColumnStyle(RegionEdgeStyle lastColumnStyle) {
        this.lastColumnStyle = lastColumnStyle;
    }

    public String getErrors() {
        return this.errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public List<String> getFillLinks() {
        return this.fillLinks;
    }

    public void setFillLinks(List<String> fillLinks) {
        this.fillLinks = fillLinks;
    }

    public RecordCardData getCardRecord() {
        return this.cardRecord;
    }

    public void setCardRecord(RecordCardData cardRecord) {
        this.cardRecord = cardRecord;
    }

    public RegionExtentGridData getExtentGridData() {
        return this.extentGridData;
    }

    public void setExtentGridData(RegionExtentGridData extentGridData) {
        this.extentGridData = extentGridData;
    }

    public int getEnterNext() {
        return this.enterNext;
    }

    public void setEnterNext(int enterNext) {
        this.enterNext = enterNext;
    }

    public List<EntityDefaultValue> getRegionEntityDefaultValue() {
        return this.regionEntityDefaultValue;
    }

    public void setRegionEntityDefaultValue(List<EntityDefaultValue> regionEntityDefaultValue) {
        this.regionEntityDefaultValue = regionEntityDefaultValue;
    }

    public Map<String, Integer> getLinkLevelMap() {
        return this.linkLevelMap;
    }

    public void setLinkLevelMap(Map<String, Integer> linkLevelMap) {
        this.linkLevelMap = linkLevelMap;
    }

    public void initialize(DataRegionDefine dataRegionDefine) {
        String displayLevel;
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
        this.enterNext = this.initEnterNext(dataRegionDefine);
        if (StringUtils.isNotEmpty((String)dataRegionDefine.getSortFieldsList())) {
            String sortingInfoList = dataRegionDefine.getSortFieldsList();
            String[] sortingInfos = sortingInfoList.split(";");
            for (int i = 0; i < sortingInfos.length; ++i) {
                String sortingInfo = sortingInfos[i];
                boolean sort = true;
                String fieldKey = "";
                if (sortingInfo.endsWith("+")) {
                    sort = true;
                    fieldKey = sortingInfo.substring(0, sortingInfo.length() - 1);
                } else if (sortingInfo.endsWith("-")) {
                    sort = false;
                    fieldKey = sortingInfo.substring(0, sortingInfo.length() - 1);
                } else {
                    fieldKey = sortingInfo;
                }
                CellQueryInfo cellQueryInfo = new CellQueryInfo();
                cellQueryInfo.setCellKey(fieldKey);
                cellQueryInfo.setSort(sort ? "asc" : "desc");
                this.cells.add(cellQueryInfo);
            }
        }
        this.initGrade(dataRegionDefine);
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        RegionSettingData regionSetting = jtableParamService.getRegionSetting(this.key);
        if (regionSetting != null) {
            List<RegionEdgeStyleDefine> lastRowStyles;
            List<RegionEdgeStyleDefine> lastColumnStyles;
            List<RowNumberSetting> rowNumberSetting = regionSetting.getRowNumberSetting();
            if (null != rowNumberSetting && rowNumberSetting.size() > 0) {
                this.regionNumber = new RegionNumber(rowNumberSetting.get(0));
            }
            if (null != (lastColumnStyles = regionSetting.getLastColumnStyle()) && lastColumnStyles.size() > 0) {
                this.lastColumnStyle = new RegionEdgeStyle(lastColumnStyles.get(0));
            }
            if (null != (lastRowStyles = regionSetting.getLastRowStyles()) && lastRowStyles.size() > 0) {
                this.lastRowStyle = new RegionEdgeStyle(lastRowStyles.get(0));
            }
            if (StringUtils.isNotEmpty((String)regionSetting.getDictionaryFillLinks())) {
                String[] fillFieldKey = regionSetting.getDictionaryFillLinks().split(";");
                this.fillLinks = new ArrayList<String>();
                List<LinkData> links = jtableParamService.getLinks(this.key);
                Map<String, LinkData> field2Link = links.stream().collect(Collectors.toMap(LinkData::getZbid, r -> r, (o1, o2) -> o2));
                for (String field : fillFieldKey) {
                    LinkData linkData = field2Link.get(field);
                    if (linkData == null) continue;
                    this.fillLinks.add(linkData.getKey());
                }
            }
            this.cardRecord = regionSetting.getCardRecord();
            RunTimeExtentStyleService extentStyleService = (RunTimeExtentStyleService)BeanUtil.getBean(RunTimeExtentStyleService.class);
            ExtentStyle extentStyle = extentStyleService.getExtentStyle(this.key);
            if (extentStyle != null) {
                this.extentGridData = new RegionExtentGridData(extentStyle);
            }
            this.regionEntityDefaultValue = regionSetting.getRegionEntityDefaultValue();
        }
        if (StringUtils.isNotEmpty((String)(displayLevel = dataRegionDefine.getDisplayLevel()))) {
            this.linkLevelMap = new LinkedHashMap<String, Integer>();
            String[] levelLinks = displayLevel.split(";");
            int level = 1;
            for (int i = 0; i < levelLinks.length; ++i) {
                String link = levelLinks[i];
                this.linkLevelMap.put(link, level);
                ++level;
            }
        }
    }

    private int initEnterNext(DataRegionDefine dataRegionDefine) {
        if (dataRegionDefine.getRegionEnterNext() != null) {
            return dataRegionDefine.getRegionEnterNext().getValue();
        }
        switch (dataRegionDefine.getRegionKind()) {
            case DATA_REGION_SIMPLE: 
            case DATA_REGION_COLUMN_LIST: {
                return RegionEnterNext.BOTTOM.getValue();
            }
            case DATA_REGION_ROW_LIST: 
            case DATA_REGION_SCROLL: 
            case DATA_REGION_COLUMN_AND_ROW_LIST: {
                return RegionEnterNext.RIGHT.getValue();
            }
        }
        return RegionEnterNext.BOTTOM.getValue();
    }

    private void initGrade(DataRegionDefine dataRegionDefine) {
        char yPoint;
        int x;
        String showAddress;
        this.grade = new RegionGradeInfo();
        this.grade.setFold(dataRegionDefine.getIsCanFold());
        this.grade.setSum(dataRegionDefine.getShowGatherSummaryRow());
        this.grade.setDetail(dataRegionDefine.getShowGatherDetailRows());
        this.grade.setHidenRow(dataRegionDefine.getShowGatherDetailRowByOne());
        String gatherSettingStr = dataRegionDefine.getGatherSetting();
        LevelSetting levelSetting = dataRegionDefine.getLevelSetting();
        if (dataRegionDefine.getLevelSetting() != null) {
            this.grade.setLevelSetType(levelSetting.getType());
            if (levelSetting.getType() == 0) {
                gatherSettingStr = levelSetting.getCode();
            } else {
                String[] gatherSettings;
                for (String gatherlevel : gatherSettings = levelSetting.getCode().split(";")) {
                    if (StringUtils.isEmpty((String)gatherlevel)) continue;
                    this.grade.getLevels().add(Integer.parseInt(gatherlevel));
                }
            }
        }
        if (StringUtils.isNotEmpty((String)dataRegionDefine.getGatherFields()) && StringUtils.isNotEmpty((String)gatherSettingStr)) {
            if (gatherSettingStr.contains("undefined")) {
                logger.error("\u8bbe\u7f6e\u51fa\u9519 regionKey: " + dataRegionDefine.getKey() + "|regionKeyTitle:" + dataRegionDefine.getTitle() + "|gatherFields\uff1a" + dataRegionDefine.getGatherFields() + "|gatherSettings :" + dataRegionDefine.getGatherSetting());
            }
            String[] gatherFields = dataRegionDefine.getGatherFields().split(";");
            String[] gatherSettings = gatherSettingStr.split(";");
            if (gatherFields.length > 0) {
                for (int gatherIndex = 0; gatherIndex < gatherFields.length; ++gatherIndex) {
                    String gatherSetting;
                    String gatherField = gatherFields[gatherIndex];
                    String string = gatherSetting = gatherSettings.length > gatherIndex ? gatherSettings[gatherIndex] : "";
                    if (!StringUtils.isNotEmpty((String)gatherField) || "null".equals(gatherField)) continue;
                    GradeCellInfo gradeCellInfo = new GradeCellInfo();
                    gradeCellInfo.setZbid(gatherField);
                    gradeCellInfo.setGradeStruct("");
                    gradeCellInfo.setTrim(false);
                    if (!StringUtils.isEmpty((String)gatherSetting) && levelSetting.getType() == 0) {
                        String[] gatherlevels;
                        for (String gatherlevel : gatherlevels = gatherSetting.split(",")) {
                            if (StringUtils.isEmpty((String)gatherlevel)) continue;
                            gradeCellInfo.getLevels().add(Integer.parseInt(gatherlevel));
                        }
                    }
                    this.grade.getGradeCells().add(gradeCellInfo);
                }
            }
        }
        int sumIndex = 0;
        if (StringUtils.isNotEmpty((String)dataRegionDefine.getShowAddress()) && (showAddress = dataRegionDefine.getShowAddress().toUpperCase()).length() == 2 && (x = (yPoint = showAddress.charAt(0)) - 65 + 1) >= dataRegionDefine.getRegionLeft() && x <= dataRegionDefine.getRegionRight()) {
            sumIndex = x;
        }
        if (sumIndex <= 0) {
            sumIndex = dataRegionDefine.getRegionLeft();
        }
        this.grade.setLocate(sumIndex);
        if (StringUtils.isNotEmpty((String)dataRegionDefine.getHideZeroGatherFields())) {
            String[] hideZeros;
            for (String hideZero : hideZeros = dataRegionDefine.getHideZeroGatherFields().split(";")) {
                List<GradeCellInfo> gradeCells = this.grade.getGradeCells();
                for (GradeCellInfo gradeCell : gradeCells) {
                    if (!gradeCell.getZbid().equals(hideZero)) continue;
                    gradeCell.setTrim(true);
                }
            }
        }
    }
}

