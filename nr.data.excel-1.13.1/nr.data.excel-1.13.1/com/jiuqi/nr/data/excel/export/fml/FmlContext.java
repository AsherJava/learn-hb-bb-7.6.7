/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.nr.data.excel.export.fml;

import com.jiuqi.nr.data.excel.export.fml.FmlExportServiceImpl;
import com.jiuqi.nr.data.excel.export.grid.GridAreaInfo;
import com.jiuqi.nr.data.excel.obj.ExportCache;
import com.jiuqi.nr.data.excel.obj.SheetInfo;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;

public class FmlContext {
    private FmlExportServiceImpl fmlExportService;
    private IRunTimeViewController runTimeViewController;
    private GridAreaInfo currentGridAreaInfo;
    private SheetInfo currentSheetInfo;
    private ExportCache exportCache;

    public FmlContext(GridAreaInfo currentGridAreaInfo, SheetInfo currentSheetInfo, ExportCache exportCache, FmlExportServiceImpl fmlExportService, IRunTimeViewController runTimeViewController) {
        this.currentGridAreaInfo = currentGridAreaInfo;
        this.currentSheetInfo = currentSheetInfo;
        this.exportCache = exportCache;
        this.fmlExportService = fmlExportService;
        this.runTimeViewController = runTimeViewController;
    }

    public GridAreaInfo getCurrentGridAreaInfo() {
        return this.currentGridAreaInfo;
    }

    public void setCurrentGridAreaInfo(GridAreaInfo currentGridAreaInfo) {
        this.currentGridAreaInfo = currentGridAreaInfo;
    }

    public SheetInfo getCurrentSheetInfo() {
        return this.currentSheetInfo;
    }

    public void setCurrentSheetInfo(SheetInfo currentSheetInfo) {
        this.currentSheetInfo = currentSheetInfo;
    }

    public ExportCache getExportCache() {
        return this.exportCache;
    }

    public void setExportCache(ExportCache exportCache) {
        this.exportCache = exportCache;
    }

    public FmlExportServiceImpl getFmlExportService() {
        return this.fmlExportService;
    }

    public void setFmlExportService(FmlExportServiceImpl fmlExportService) {
        this.fmlExportService = fmlExportService;
    }

    public IRunTimeViewController getRunTimeViewController() {
        return this.runTimeViewController;
    }

    public void setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
    }

    public GridAreaInfo getGridAreaInfo(String formKey) {
        GridAreaInfo gridAreaInfo;
        GridAreaInfo currentGridAreaInfoInMethod = this.getCurrentGridAreaInfo();
        if (currentGridAreaInfoInMethod.getFormKey().equals(formKey)) {
            gridAreaInfo = currentGridAreaInfoInMethod;
        } else {
            FmlExportServiceImpl fmlExportServiceInMethod = this.getFmlExportService();
            SheetInfo currentSheetInfoInMethod = this.getCurrentSheetInfo();
            ExportCache exportCacheInMethod = this.getExportCache();
            gridAreaInfo = fmlExportServiceInMethod.cacheGridAreaInfo(formKey, currentSheetInfoInMethod, exportCacheInMethod);
        }
        return gridAreaInfo;
    }
}

