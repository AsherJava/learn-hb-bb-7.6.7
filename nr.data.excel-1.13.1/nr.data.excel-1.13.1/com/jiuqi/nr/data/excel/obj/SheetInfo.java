/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.office.excel2.filter.FilterRegionCondition
 *  com.jiuqi.np.office.excel2.link.CellLink
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.datacrud.MultiDimensionalDataSet
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.excel.obj;

import com.jiuqi.np.office.excel2.filter.FilterRegionCondition;
import com.jiuqi.np.office.excel2.link.CellLink;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.data.excel.export.ExcelPrintSetup;
import com.jiuqi.nr.data.excel.export.ExpSheetGroup;
import com.jiuqi.nr.data.excel.export.grid.GridAreaInfo;
import com.jiuqi.nr.data.excel.obj.CustomGridCellStyle;
import com.jiuqi.nr.data.excel.obj.ExcelInfo;
import com.jiuqi.nr.data.excel.obj.ExportOps;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

public class SheetInfo {
    private ExcelInfo excelInfo;
    private DimensionCollection dimensionCollection;
    private String taskKey;
    private String formSchemeKey;
    private String formKey;
    private DimensionCombination dimensionCombination;
    private String sheetName;
    private String originalSheetName;
    private ExportOps exportOps;
    private List<FilterRegionCondition> filters = new ArrayList<FilterRegionCondition>();
    private List<CellLink> links = new ArrayList<CellLink>();
    List<String> startAndEndList = new ArrayList<String>();
    private final Map<String, Set<String>> canSeeSetMap = new HashMap<String, Set<String>>();
    Map<String, Integer> dataCountMap = new HashMap<String, Integer>();
    private final List<ExpSheetGroup> sheetGroups = new ArrayList<ExpSheetGroup>();
    private ExcelPrintSetup excelPrintSetup;
    private final Map<String, RegionGradeInfo> regionGradeInfoMap = new HashMap<String, RegionGradeInfo>();
    private GridAreaInfo curSheetArea;
    private Map<String, CustomGridCellStyle> customCellStyles = Collections.emptyMap();
    private MultiDimensionalDataSet multiDimensionalDataSet;
    private int upperLabelRowCount;
    private final Map<String, PagerInfo> pagerInfos = new HashMap<String, PagerInfo>(3);
    private boolean stopWrite;

    public List<CellLink> getLinks() {
        return this.links;
    }

    public void setLinks(List<CellLink> links) {
        this.links = links;
    }

    public List<FilterRegionCondition> getFilters() {
        return this.filters;
    }

    public void setFilters(List<FilterRegionCondition> filters) {
        this.filters = filters;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public ExportOps getExportOps() {
        return this.exportOps;
    }

    public void setExportOps(ExportOps exportOps) {
        this.exportOps = exportOps;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getStartAndEndList() {
        return this.startAndEndList;
    }

    public void setStartAndEndList(List<String> startAndEndList) {
        this.startAndEndList = startAndEndList;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public Map<String, Set<String>> getCanSeeSetMap() {
        return this.canSeeSetMap;
    }

    public Map<String, Integer> getDataCountMap() {
        return this.dataCountMap;
    }

    public List<ExpSheetGroup> getSheetGroups() {
        return this.sheetGroups;
    }

    @Nullable
    public ExcelInfo getExcelInfo() {
        return this.excelInfo;
    }

    public void setExcelInfo(ExcelInfo excelInfo) {
        this.excelInfo = excelInfo;
    }

    public ExcelPrintSetup getExcelPrintSetup() {
        return this.excelPrintSetup;
    }

    public void setExcelPrintSetup(ExcelPrintSetup excelPrintSetup) {
        this.excelPrintSetup = excelPrintSetup;
    }

    public String getOriginalSheetName() {
        return this.originalSheetName;
    }

    public void setOriginalSheetName(String originalSheetName) {
        this.originalSheetName = originalSheetName;
    }

    @Nullable
    public RegionGradeInfo getRegionGradeInfo(String dataRegionKey, RegionRelationFactory regionRelationFactory) {
        if (this.excelInfo != null) {
            return this.excelInfo.getRegionGradeInfo(dataRegionKey, regionRelationFactory);
        }
        if (this.regionGradeInfoMap.containsKey(dataRegionKey)) {
            return this.regionGradeInfoMap.get(dataRegionKey);
        }
        RegionGradeInfo gradeInfo = null;
        Map<String, RegionGradeInfo> gradeInfos = this.getExportOps().getGradeInfos();
        if (!CollectionUtils.isEmpty(gradeInfos)) {
            gradeInfo = gradeInfos.get(dataRegionKey);
        }
        if (gradeInfo == null) {
            RegionRelation regionRelation = regionRelationFactory.getRegionRelation(dataRegionKey);
            if (regionRelation == null) {
                this.regionGradeInfoMap.put(dataRegionKey, null);
                return null;
            }
            gradeInfo = regionRelation.getGradeInfo();
            if (gradeInfo == null) {
                this.regionGradeInfoMap.put(dataRegionKey, null);
                return null;
            }
            if (!this.getExportOps().isSumData()) {
                gradeInfo.setQueryDetails(true);
            }
        }
        this.regionGradeInfoMap.put(dataRegionKey, gradeInfo);
        return gradeInfo;
    }

    public GridAreaInfo getCurSheetArea() {
        return this.curSheetArea;
    }

    public void setCurSheetArea(GridAreaInfo curSheetArea) {
        this.curSheetArea = curSheetArea;
    }

    public Map<String, CustomGridCellStyle> getCustomCellStyles() {
        return this.customCellStyles;
    }

    public void setCustomCellStyles(Map<String, CustomGridCellStyle> customCellStyles) {
        this.customCellStyles = customCellStyles;
    }

    @Nullable
    public MultiDimensionalDataSet getMultiDimensionalDataSet() {
        return this.multiDimensionalDataSet;
    }

    public void setMultiDimensionalDataSet(MultiDimensionalDataSet multiDimensionalDataSet) {
        this.multiDimensionalDataSet = multiDimensionalDataSet;
    }

    public void setDataCountMap(Map<String, Integer> dataCountMap) {
        this.dataCountMap = dataCountMap;
    }

    public int getUpperLabelRowCount() {
        return this.upperLabelRowCount;
    }

    public void setUpperLabelRowCount(int upperLabelRowCount) {
        this.upperLabelRowCount = upperLabelRowCount;
    }

    public PagerInfo getPagerInfo(String cacheKey, int queryPageLimit) {
        if (this.pagerInfos.containsKey(cacheKey)) {
            return this.pagerInfos.get(cacheKey);
        }
        PagerInfo info = new PagerInfo();
        info.setOffset(-1);
        info.setLimit(queryPageLimit);
        info.setTotal(this.stopWrite ? Integer.MAX_VALUE : 1);
        this.pagerInfos.put(cacheKey, info);
        return info;
    }

    public boolean canWrite() {
        if (this.stopWrite) {
            return false;
        }
        if (CollectionUtils.isEmpty(this.pagerInfos)) {
            return true;
        }
        for (PagerInfo pagerInfo : this.pagerInfos.values()) {
            if (pagerInfo.getTotal() >= Integer.MAX_VALUE) continue;
            return true;
        }
        return false;
    }

    public void stopWrite() {
        this.stopWrite = true;
        for (String s : this.pagerInfos.keySet()) {
            this.pagerInfos.get(s).setTotal(Integer.MAX_VALUE);
        }
    }
}

