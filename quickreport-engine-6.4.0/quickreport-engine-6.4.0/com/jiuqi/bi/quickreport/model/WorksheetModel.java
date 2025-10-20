/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.office.excel.print.PrintSetting
 *  com.jiuqi.bi.util.StreamException
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.office.excel.print.PrintSetting;
import com.jiuqi.bi.quickreport.model.CellMap;
import com.jiuqi.bi.quickreport.model.FilterInfo;
import com.jiuqi.bi.quickreport.model.JSONHelper;
import com.jiuqi.bi.quickreport.model.ReportModelException;
import com.jiuqi.bi.quickreport.model.StyleRegion;
import com.jiuqi.bi.util.StreamException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class WorksheetModel
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private String name;
    private GridData griddata = new GridData(11, 31);
    private PrintSetting printSetting;
    private List<CellMap> cellMaps = new ArrayList<CellMap>();
    private List<StyleRegion> styleRegions = new ArrayList<StyleRegion>();
    private List<FilterInfo> colFilters = new ArrayList<FilterInfo>();
    private List<FilterInfo> rowFilters = new ArrayList<FilterInfo>();
    public static final String SHEETS_DIR = "worksheets";
    public static final String GRID_EXT = ".grid";
    public static final String PRINTSETTING_EXT = ".printSetting";
    public static final String MAPS_EXT = ".maps";
    private static final String MAP_CELLMAPS = "cellMaps";
    private static final String MAP_STYLEREGIONS = "styleRegions";
    private static final String MAP_COLFILTERS = "colFilters";
    private static final String MAP_ROWFILTERS = "rowFilters";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GridData getGriddata() {
        return this.griddata;
    }

    public void setGriddata(GridData griddata) {
        this.griddata = griddata;
    }

    public void setPrintSetting(PrintSetting printSetting) {
        this.printSetting = printSetting;
    }

    public PrintSetting getPrintSetting() {
        return this.printSetting;
    }

    public List<CellMap> getCellMaps() {
        return this.cellMaps;
    }

    public List<StyleRegion> getStyleRegions() {
        return this.styleRegions;
    }

    public List<FilterInfo> getColFilters() {
        return this.colFilters;
    }

    public List<FilterInfo> getRowFilters() {
        return this.rowFilters;
    }

    public Object clone() {
        WorksheetModel result;
        try {
            result = (WorksheetModel)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
        result.griddata = (GridData)this.griddata.clone();
        result.printSetting = this.printSetting == null ? null : (PrintSetting)this.printSetting.clone();
        result.cellMaps = new ArrayList<CellMap>(this.cellMaps.size());
        for (CellMap cell : this.cellMaps) {
            result.cellMaps.add((CellMap)cell.clone());
        }
        result.styleRegions = new ArrayList<StyleRegion>(this.styleRegions.size());
        for (StyleRegion region : this.styleRegions) {
            result.styleRegions.add((StyleRegion)region.clone());
        }
        result.colFilters = new ArrayList<FilterInfo>(this.colFilters.size());
        for (FilterInfo info : this.colFilters) {
            result.colFilters.add((FilterInfo)info.clone());
        }
        result.rowFilters = new ArrayList<FilterInfo>(this.rowFilters.size());
        for (FilterInfo info : this.rowFilters) {
            result.rowFilters.add((FilterInfo)info.clone());
        }
        return result;
    }

    public void save(ZipOutputStream zip) throws IOException, JSONException, ReportModelException {
        this.saveGridData(zip);
        this.savePrintSetting(zip);
        this.saveCellMaps(zip);
    }

    private void saveGridData(ZipOutputStream zip) throws ReportModelException, IOException {
        zip.putNextEntry(new ZipEntry("worksheets/" + this.name + GRID_EXT));
        try {
            try {
                this.griddata.saveToStream((OutputStream)zip);
            }
            catch (StreamException e) {
                throw new ReportModelException(e);
            }
        }
        finally {
            zip.closeEntry();
        }
    }

    private void savePrintSetting(ZipOutputStream zip) throws IOException {
        if (this.printSetting == null) {
            return;
        }
        zip.putNextEntry(new ZipEntry("worksheets/" + this.name + PRINTSETTING_EXT));
        try {
            JSONHelper.writeJSONObject(zip, this.printSetting.toJson());
        }
        finally {
            zip.closeEntry();
        }
    }

    private void saveCellMaps(ZipOutputStream zip) throws IOException, JSONException {
        zip.putNextEntry(new ZipEntry("worksheets/" + this.name + MAPS_EXT));
        try {
            JSONObject maps = this.mapsToJSON();
            JSONHelper.writeJSONObject(zip, maps);
        }
        finally {
            zip.closeEntry();
        }
    }

    private JSONObject mapsToJSON() throws JSONException {
        JSONObject maps = new JSONObject();
        JSONArray cells = new JSONArray();
        for (CellMap cellMap : this.cellMaps) {
            JSONObject jSONObject = cellMap.toJSON();
            cells.put((Object)jSONObject);
        }
        maps.put(MAP_CELLMAPS, (Object)cells);
        JSONArray regions = new JSONArray();
        for (StyleRegion styleRegion : this.styleRegions) {
            JSONObject regionObj = styleRegion.toJSON();
            regions.put((Object)regionObj);
        }
        maps.put(MAP_STYLEREGIONS, (Object)regions);
        JSONArray jSONArray = new JSONArray();
        for (FilterInfo filter : this.colFilters) {
            JSONObject filterObj = filter.toJSON();
            jSONArray.put((Object)filterObj);
        }
        maps.put(MAP_COLFILTERS, (Object)jSONArray);
        JSONArray jSONArray2 = new JSONArray();
        for (FilterInfo filter : this.rowFilters) {
            JSONObject filterObj = filter.toJSON();
            jSONArray2.put((Object)filterObj);
        }
        maps.put(MAP_ROWFILTERS, (Object)jSONArray2);
        return maps;
    }

    public void load(ZipInputStream zip, String extName) throws ReportModelException, IOException, JSONException {
        if (GRID_EXT.equalsIgnoreCase(extName)) {
            this.loadGridData(zip);
        } else if (PRINTSETTING_EXT.equalsIgnoreCase(extName)) {
            this.loadPrintSetting(zip);
        } else if (MAPS_EXT.equalsIgnoreCase(extName)) {
            this.loadCellMaps(zip);
        }
    }

    private void loadGridData(ZipInputStream zip) throws ReportModelException {
        try {
            this.griddata.loadFromStream((InputStream)zip);
        }
        catch (StreamException e) {
            throw new ReportModelException(e);
        }
    }

    private void loadPrintSetting(ZipInputStream zip) throws IOException, JSONException {
        JSONObject json = JSONHelper.readJSONObject(zip);
        this.printSetting = new PrintSetting();
        this.printSetting.fromJson(json);
    }

    private void loadCellMaps(ZipInputStream zip) throws IOException, JSONException {
        JSONObject json = JSONHelper.readJSONObject(zip);
        this.mapsFromJSON(json);
    }

    private void mapsFromJSON(JSONObject json) throws JSONException {
        JSONArray rows;
        JSONArray cells = json.optJSONArray(MAP_CELLMAPS);
        for (int i = 0; i < cells.length(); ++i) {
            CellMap cellmap = new CellMap();
            cellmap.fromJSON(cells.optJSONObject(i));
            this.cellMaps.add(cellmap);
        }
        JSONArray regions = json.optJSONArray(MAP_STYLEREGIONS);
        for (int i = 0; i < regions.length(); ++i) {
            StyleRegion styleRegion = new StyleRegion();
            styleRegion.fromJSON(regions.optJSONObject(i));
            this.styleRegions.add(styleRegion);
        }
        JSONArray cols = json.optJSONArray(MAP_COLFILTERS);
        if (cols != null) {
            for (int i = 0; i < cols.length(); ++i) {
                FilterInfo filter = new FilterInfo();
                filter.fromJSON(cols.optJSONObject(i));
                this.colFilters.add(filter);
            }
        }
        if ((rows = json.optJSONArray(MAP_ROWFILTERS)) != null) {
            for (int i = 0; i < rows.length(); ++i) {
                FilterInfo filter = new FilterInfo();
                filter.fromJSON(rows.optJSONObject(i));
                this.rowFilters.add(filter);
            }
        }
    }

    public String toString() {
        return this.getName();
    }
}

