/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Region
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.quickreport.model.CellStyle;
import com.jiuqi.bi.quickreport.model.DataBarDispStyle;
import com.jiuqi.bi.quickreport.model.DataBarMode;
import com.jiuqi.bi.quickreport.model.DataBarStyle;
import com.jiuqi.bi.quickreport.model.IconStyle;
import com.jiuqi.bi.syntax.cell.Region;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class StyleRegion
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private Region region;
    private boolean enableDataBar;
    private DataBarMode dataBarMode = DataBarMode.AUTO;
    private DataBarDispStyle dataBarDispStyle = DataBarDispStyle.BAND;
    private boolean dataBarOnly;
    private boolean dataBarGradual;
    private List<DataBarStyle> dataBarStyles = new ArrayList<DataBarStyle>();
    private boolean enableCellStyle;
    private List<CellStyle> cellStyles = new ArrayList<CellStyle>();
    private boolean enableIconStyle;
    private List<IconStyle> iconStyles = new ArrayList<IconStyle>();
    private static final String SR_REGION = "region";
    private static final String SR_ENABLEDATABAR = "enableDataBar";
    private static final String SR_DATABARMODE = "dataBarMode";
    private static final String SR_DATABARDISPSTYLE = "dataBarDispStyle";
    private static final String SR_DATABARONLY = "dataBarOnly";
    private static final String SR_DATABARGRADUAL = "dataBarGradual";
    private static final String SR_DATABARSTYLES = "dataBarStyles";
    @Deprecated
    private static final String SR_DATABARCOLOR = "dataBarColor";
    private static final String SR_ENABLECELLSTYLE = "enableCellStyle";
    private static final String SR_CELLSTYLES = "cellStyles";
    private static final String SR_ENABLEICONSTYLE = "enableIconStyle";
    private static final String SR_ICONSTYLES = "iconStyles";

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public boolean isEnableDataBar() {
        return this.enableDataBar;
    }

    public void setEnableDataBar(boolean enableDataBar) {
        this.enableDataBar = enableDataBar;
    }

    public DataBarMode getDataBarMode() {
        return this.dataBarMode;
    }

    public void setDataBarMode(DataBarMode dataBarMode) {
        this.dataBarMode = dataBarMode;
    }

    public DataBarDispStyle getDataBarDispStyle() {
        return this.dataBarDispStyle;
    }

    public void setDataBarDispStyle(DataBarDispStyle dataBarDispStyle) {
        this.dataBarDispStyle = dataBarDispStyle;
    }

    public boolean isDataBarOnly() {
        return this.dataBarOnly;
    }

    public void setDataBarOnly(boolean dataBarOnly) {
        this.dataBarOnly = dataBarOnly;
    }

    public boolean isDataBarGradual() {
        return this.dataBarGradual;
    }

    public void setDataBarGradual(boolean dataBarGradual) {
        this.dataBarGradual = dataBarGradual;
    }

    public List<DataBarStyle> getDataBarStyles() {
        return this.dataBarStyles;
    }

    @Deprecated
    public int getDataBarColor() {
        if (this.dataBarStyles.isEmpty()) {
            return DataBarStyle.COLOR_DEFAULT;
        }
        return this.dataBarStyles.get(0).getForegroundColor();
    }

    @Deprecated
    public void setDataBarColor(int color) {
        this.dataBarStyles.forEach(s -> s.setForegroundColor(color));
    }

    public boolean isEnableCellStyle() {
        return this.enableCellStyle;
    }

    public void setEnableCellStyle(boolean enableCellStyle) {
        this.enableCellStyle = enableCellStyle;
    }

    public List<CellStyle> getCellStyles() {
        return this.cellStyles;
    }

    public boolean isEnableIconStyle() {
        return this.enableIconStyle;
    }

    public void setEnableIconStyle(boolean enableIconStyle) {
        this.enableIconStyle = enableIconStyle;
    }

    public List<IconStyle> getIconStyles() {
        return this.iconStyles;
    }

    public Object clone() {
        StyleRegion result;
        try {
            result = (StyleRegion)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
        result.cellStyles = new ArrayList<CellStyle>(this.cellStyles.size());
        for (CellStyle cs : this.cellStyles) {
            result.cellStyles.add((CellStyle)cs.clone());
        }
        result.iconStyles = new ArrayList<IconStyle>(this.iconStyles.size());
        for (IconStyle is : this.iconStyles) {
            result.iconStyles.add((IconStyle)is.clone());
        }
        result.dataBarStyles = new ArrayList<DataBarStyle>(this.dataBarStyles.size());
        for (DataBarStyle ds : this.dataBarStyles) {
            result.dataBarStyles.add((DataBarStyle)ds.clone());
        }
        return result;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject styleObj;
        JSONArray styles;
        JSONObject json = new JSONObject();
        json.put(SR_REGION, (Object)this.region.toString());
        json.put(SR_ENABLEDATABAR, this.enableDataBar);
        if (this.enableDataBar) {
            json.put(SR_DATABARMODE, (Object)this.dataBarMode.toString());
            json.put(SR_DATABARDISPSTYLE, (Object)this.dataBarDispStyle);
            json.put(SR_DATABARONLY, this.dataBarOnly);
            json.put(SR_DATABARGRADUAL, this.dataBarGradual);
            styles = new JSONArray();
            for (DataBarStyle dataBarStyle : this.dataBarStyles) {
                styleObj = dataBarStyle.toJSON();
                styles.put((Object)styleObj);
            }
            json.put(SR_DATABARSTYLES, (Object)styles);
        }
        json.put(SR_ENABLECELLSTYLE, this.enableCellStyle);
        if (this.enableCellStyle) {
            styles = new JSONArray();
            for (CellStyle cellStyle : this.cellStyles) {
                styleObj = cellStyle.toJSON();
                styles.put((Object)styleObj);
            }
            json.put(SR_CELLSTYLES, (Object)styles);
        }
        json.put(SR_ENABLEICONSTYLE, this.enableIconStyle);
        if (this.enableIconStyle) {
            styles = new JSONArray();
            for (IconStyle iconStyle : this.iconStyles) {
                styleObj = iconStyle.toJSON();
                styles.put((Object)styleObj);
            }
            json.put(SR_ICONSTYLES, (Object)styles);
        }
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        int i;
        this.region = Region.createRegion((String)json.optString(SR_REGION));
        this.enableDataBar = json.optBoolean(SR_ENABLEDATABAR);
        if (this.enableDataBar) {
            DataBarStyle dbStyle;
            this.dataBarMode = DataBarMode.valueOf(json.optString(SR_DATABARMODE));
            this.dataBarDispStyle = (DataBarDispStyle)json.optEnum(DataBarDispStyle.class, SR_DATABARDISPSTYLE, (Enum)DataBarDispStyle.BAND);
            this.dataBarOnly = json.optBoolean(SR_DATABARONLY);
            this.dataBarGradual = json.optBoolean(SR_DATABARGRADUAL);
            JSONArray stylesArr = json.optJSONArray(SR_DATABARSTYLES);
            if (stylesArr == null) {
                int dataBarColor = json.optInt(SR_DATABARCOLOR, DataBarStyle.COLOR_DEFAULT);
                dbStyle = new DataBarStyle();
                dbStyle.setForegroundColor(dataBarColor);
                this.dataBarStyles.add(dbStyle);
            } else {
                for (i = 0; i < stylesArr.length(); ++i) {
                    dbStyle = new DataBarStyle();
                    dbStyle.fromJSON(stylesArr.getJSONObject(i));
                    this.dataBarStyles.add(dbStyle);
                }
            }
        }
        this.enableCellStyle = json.optBoolean(SR_ENABLECELLSTYLE);
        if (this.enableCellStyle) {
            JSONArray cellStyleArr = json.optJSONArray(SR_CELLSTYLES);
            for (i = 0; i < cellStyleArr.length(); ++i) {
                CellStyle cellStyle = new CellStyle();
                cellStyle.fromJSON(cellStyleArr.optJSONObject(i));
                this.cellStyles.add(cellStyle);
            }
        }
        this.enableIconStyle = json.optBoolean(SR_ENABLEICONSTYLE);
        if (this.enableIconStyle) {
            JSONArray iconStyleArr = json.optJSONArray(SR_ICONSTYLES);
            for (i = 0; i < iconStyleArr.length(); ++i) {
                IconStyle iconStyle = new IconStyle();
                iconStyle.fromJSON(iconStyleArr.optJSONObject(i));
                this.iconStyles.add(iconStyle);
            }
        }
    }
}

