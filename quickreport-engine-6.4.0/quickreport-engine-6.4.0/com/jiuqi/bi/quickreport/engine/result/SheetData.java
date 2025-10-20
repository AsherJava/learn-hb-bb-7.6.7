/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.office.excel.print.PrintSetting
 *  com.jiuqi.bi.syntax.cell.Position
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.result;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.office.excel.print.PrintSetting;
import com.jiuqi.bi.quickreport.engine.result.CellLinkInfo;
import com.jiuqi.bi.quickreport.engine.result.CellRestrictionInfo;
import com.jiuqi.bi.quickreport.engine.result.CellResultInfo;
import com.jiuqi.bi.quickreport.engine.result.DataBarInfo;
import com.jiuqi.bi.quickreport.engine.result.PagingInfo;
import com.jiuqi.bi.syntax.cell.Position;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class SheetData
implements Externalizable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private String sheetName;
    private GridData gridData;
    private PrintSetting printSetting;
    private List<PagingInfo> pagingInfos;
    private List<CellLinkInfo> cellLinkInfos;
    private transient List<Map<String, Object>> cellLinkParams;
    private static final String SHEET_NAME = "name";
    private static final String SHEET_GRID = "grid";
    private static final String SHEET_INFOS = "infos";
    private static final String SHEET_INFO_POS = "pos";
    private static final String SHEET_BARINFOS = "barInfos";
    private static final String SHEET_PRINTSETTING = "printSetting";
    private static final String SHEET_CELLLINKINFOS = "cellLinkInfos";
    private static final String SHEET_CELLLINKPARMS = "cellLinkParams";

    public SheetData() {
        this.printSetting = new PrintSetting();
        this.pagingInfos = new ArrayList<PagingInfo>();
        this.cellLinkInfos = new ArrayList<CellLinkInfo>();
        this.cellLinkParams = new ArrayList<Map<String, Object>>();
    }

    public SheetData(String sheetName, GridData gridData) {
        this();
        this.sheetName = sheetName;
        this.gridData = gridData;
    }

    public SheetData(String sheetName, GridData gridData, PrintSetting printSetting) {
        this.sheetName = sheetName;
        this.gridData = gridData;
        this.printSetting = printSetting;
        this.pagingInfos = new ArrayList<PagingInfo>();
        this.cellLinkInfos = new ArrayList<CellLinkInfo>();
        this.cellLinkParams = new ArrayList<Map<String, Object>>();
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public GridData getGridData() {
        return this.gridData;
    }

    public PrintSetting getPrintSetting() {
        return this.printSetting;
    }

    public List<PagingInfo> getPagingInfos() {
        return this.pagingInfos;
    }

    public List<CellLinkInfo> getLinkInfos() {
        return this.cellLinkInfos;
    }

    public List<Map<String, Object>> getLinkParams() {
        return this.cellLinkParams;
    }

    public Object clone() {
        SheetData result;
        try {
            result = (SheetData)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
        result.gridData = (GridData)this.gridData.clone();
        for (int col = 0; col < this.gridData.getColCount(); ++col) {
            for (int row = 0; row < this.gridData.getRowCount(); ++row) {
                Object obj = this.gridData.getObj(col, row);
                if (obj == null) continue;
                result.gridData.setObj(col, row, obj);
            }
        }
        result.printSetting = (PrintSetting)this.printSetting.clone();
        result.pagingInfos = new ArrayList<PagingInfo>(this.pagingInfos.size());
        for (PagingInfo pagingInfo : this.pagingInfos) {
            result.pagingInfos.add(pagingInfo.clone());
        }
        result.cellLinkInfos = this.cellLinkInfos.stream().map(CellLinkInfo::clone).collect(Collectors.toList());
        result.cellLinkParams = new ArrayList<Map<String, Object>>(this.cellLinkParams);
        return result;
    }

    public JSONObject toJSON() throws JSONException {
        JSONArray arr;
        JSONObject json = new JSONObject();
        json.put(SHEET_NAME, (Object)this.sheetName);
        String gridStr = GridData.gridToBase64((GridData)this.gridData);
        json.put(SHEET_GRID, (Object)gridStr);
        ArrayList<DataBarInfo> barInfos = new ArrayList<DataBarInfo>();
        JSONArray infos = this.getInfoArray(barInfos);
        json.put(SHEET_INFOS, (Object)infos);
        JSONArray barArr = this.toBarArray(barInfos);
        json.put(SHEET_BARINFOS, (Object)barArr);
        if (this.printSetting != null) {
            JSONObject printObj = this.printSetting.toJson();
            json.put(SHEET_PRINTSETTING, (Object)printObj);
        }
        if (!this.cellLinkInfos.isEmpty()) {
            arr = new JSONArray(this.cellLinkInfos.size());
            for (CellLinkInfo cellLinkInfo : this.cellLinkInfos) {
                arr.put((Object)cellLinkInfo.toJSON());
            }
            json.put(SHEET_CELLLINKINFOS, (Object)arr);
        }
        if (!this.cellLinkParams.isEmpty()) {
            arr = new JSONArray(this.cellLinkParams.size());
            for (Map map : this.cellLinkParams) {
                arr.put((Object)new JSONObject(map));
            }
            json.put(SHEET_CELLLINKPARMS, (Object)arr);
        }
        return json;
    }

    private JSONArray toBarArray(List<DataBarInfo> barInfos) throws JSONException {
        JSONArray arr = new JSONArray();
        for (DataBarInfo barInfo : barInfos) {
            arr.put((Object)barInfo.toJSON());
        }
        return arr;
    }

    private JSONArray getInfoArray(List<DataBarInfo> barInfos) throws JSONException {
        JSONArray arr = new JSONArray();
        for (int row = 1; row < this.gridData.getRowCount(); ++row) {
            for (int col = 1; col < this.gridData.getColCount(); ++col) {
                CellResultInfo info = (CellResultInfo)this.gridData.getObj(col, row);
                if (info == null) continue;
                JSONObject cellInfo = info.toJSON(barInfos);
                cellInfo.put(SHEET_INFO_POS, (Object)Position.toString((int)col, (int)row));
                arr.put((Object)cellInfo);
            }
        }
        return arr;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        this.sheetName = json.optString(SHEET_NAME);
        String gridStr = json.optString(SHEET_GRID);
        this.gridData = GridData.base64ToGrid((String)gridStr);
        JSONArray barArr = json.optJSONArray(SHEET_BARINFOS);
        List<DataBarInfo> barInfos = this.toBarList(barArr);
        JSONArray infos = json.optJSONArray(SHEET_INFOS);
        this.setInfoArray(infos, barInfos);
        this.printSetting = new PrintSetting();
        JSONObject printObj = json.optJSONObject(SHEET_PRINTSETTING);
        if (printObj != null) {
            this.printSetting.fromJson(printObj);
        }
        this.cellLinkInfos.clear();
        JSONArray linkInfoArr = json.optJSONArray(SHEET_CELLLINKINFOS);
        if (linkInfoArr != null) {
            for (int i = 0; i < linkInfoArr.length(); ++i) {
                JSONObject obj = linkInfoArr.getJSONObject(i);
                CellLinkInfo linkInfo = new CellLinkInfo();
                linkInfo.fromJSON(obj);
                this.cellLinkInfos.add(linkInfo);
            }
        }
        this.cellLinkParams.clear();
        JSONArray linkParamArr = json.optJSONArray(SHEET_CELLLINKPARMS);
        if (linkParamArr != null) {
            for (int i = 0; i < linkParamArr.length(); ++i) {
                JSONObject obj = linkParamArr.getJSONObject(i);
                Map<String, Object> params = obj.keySet().stream().collect(Collectors.toMap(k -> k, k -> {
                    Object value = obj.opt(k);
                    if (value instanceof JSONArray) {
                        return ((JSONArray)value).toList();
                    }
                    return value;
                }));
                this.cellLinkParams.add(Collections.unmodifiableMap(params));
            }
        }
    }

    private List<DataBarInfo> toBarList(JSONArray barArr) throws JSONException {
        ArrayList<DataBarInfo> barInfos = new ArrayList<DataBarInfo>();
        for (int i = 0; i < barArr.length(); ++i) {
            JSONObject obj = barArr.optJSONObject(i);
            DataBarInfo info = new DataBarInfo();
            info.fromJSON(obj);
            barInfos.add(info);
        }
        return barInfos;
    }

    private void setInfoArray(JSONArray infos, List<DataBarInfo> barInfos) throws JSONException {
        for (int i = 0; i < infos.length(); ++i) {
            JSONObject cellInfo = infos.optJSONObject(i);
            Position pos = Position.valueOf((String)cellInfo.optString(SHEET_INFO_POS));
            CellResultInfo info = new CellResultInfo();
            info.fromJSON(cellInfo, barInfos);
            this.gridData.setObj(pos.col(), pos.row(), (Object)info);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.cellLinkParams.size());
        for (Map<String, Object> params : this.cellLinkParams) {
            out.writeInt(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                out.writeUTF(entry.getKey());
                CellRestrictionInfo.writeValue(out, entry.getValue());
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.cellLinkParams.clear();
        int len = in.readInt();
        for (int i = 0; i < len; ++i) {
            HashMap<String, Object> params = new HashMap<String, Object>();
            int size = in.readInt();
            for (int j = 0; j < size; ++j) {
                String key = in.readUTF();
                Object value = CellRestrictionInfo.readValue(in);
                params.put(key, value);
            }
            this.cellLinkParams.add(params);
        }
    }

    public SheetData getPagedData(int pageNum) {
        GridData pageGrid = this.gridData.getPagedGridData(pageNum - 1);
        SheetData pageData = new SheetData(this.sheetName, pageGrid, this.printSetting);
        pageData.getPagingInfos().addAll(this.pagingInfos);
        pageData.getLinkInfos().addAll(this.cellLinkInfos);
        pageData.getLinkParams().addAll(this.cellLinkParams);
        return pageData;
    }

    public String toString() {
        return this.sheetName;
    }
}

