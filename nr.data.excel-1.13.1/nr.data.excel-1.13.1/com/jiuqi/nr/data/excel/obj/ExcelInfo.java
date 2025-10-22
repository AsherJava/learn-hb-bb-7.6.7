/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.definition.facade.print.common.define.DefaultPageNumberGenerateStrategy
 *  com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy
 */
package com.jiuqi.nr.data.excel.obj;

import com.jiuqi.nr.data.excel.export.grid.GridAreaInfo;
import com.jiuqi.nr.data.excel.obj.ExportOps;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.definition.facade.print.common.define.DefaultPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

public class ExcelInfo {
    private ExportOps exportOps;
    private List<String> formKeys;
    private Map<String, String> formSheetInfo;
    private final Map<String, GridAreaInfo> formAreaInfo = new HashMap<String, GridAreaInfo>();
    private String excelName;
    private IPageNumberGenerateStrategy pageNumberGenerateStrategy;
    private final Map<String, RegionGradeInfo> regionGradeInfoMap = new HashMap<String, RegionGradeInfo>();

    public ExportOps getExportOps() {
        return this.exportOps;
    }

    public void setExportOps(ExportOps exportOps) {
        this.exportOps = exportOps;
    }

    @NonNull
    public List<String> getFormKeys() {
        if (this.formKeys == null) {
            return Collections.emptyList();
        }
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public String getExcelName() {
        return this.excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public Map<String, String> getFormSheetInfo() {
        return this.formSheetInfo;
    }

    public void setFormSheetInfo(Map<String, String> formSheetInfo) {
        this.formSheetInfo = formSheetInfo;
    }

    @NonNull
    public Map<String, GridAreaInfo> getFormAreaInfo() {
        return this.formAreaInfo;
    }

    @NonNull
    public IPageNumberGenerateStrategy getPageNumberGenerateStrategy() {
        if (this.pageNumberGenerateStrategy == null) {
            this.pageNumberGenerateStrategy = new DefaultPageNumberGenerateStrategy();
        }
        return this.pageNumberGenerateStrategy;
    }

    @Nullable
    public RegionGradeInfo getRegionGradeInfo(String dataRegionKey, RegionRelationFactory regionRelationFactory) {
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
}

