/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.excel.FitToPageMode
 *  com.jiuqi.bi.syntax.excel.OrientationEnum
 *  com.jiuqi.bi.syntax.excel.PaperSizeEnum
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.print;

import com.jiuqi.bi.syntax.excel.FitToPageMode;
import com.jiuqi.bi.syntax.excel.OrientationEnum;
import com.jiuqi.bi.syntax.excel.PaperSizeEnum;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class ExcelInfo
implements Cloneable,
Serializable {
    private static final long serialVersionUID = -5442836697877992634L;
    private boolean printEnabled;
    private boolean exportHidden;
    private OrientationEnum orientation = OrientationEnum.DEFAULT;
    private PaperSizeEnum paperSize = PaperSizeEnum.DEFAULT;
    private FitToPageMode fitMode = FitToPageMode.DEFAULT;
    private static final String EXCL_EXP_HIDDEN = "expHidden";
    private static final String EXCL_PRT_ENABLE = "enabled";
    private static final String EXCL_PRT_ORIENTAION = "orientation";
    private static final String EXCL_PRT_PAPER_SIZE = "paperSize";
    private static final String EXCL_PRT_FIT_MODE = "fitMode";

    public boolean isExportHidden() {
        return this.exportHidden;
    }

    public void setExportHidden(boolean exportHidden) {
        this.exportHidden = exportHidden;
    }

    public boolean isPrintEnabled() {
        return this.printEnabled;
    }

    @Deprecated
    public void setPrintEnabled(boolean enabled) {
        this.printEnabled = enabled;
    }

    @Deprecated
    public OrientationEnum getOrientation() {
        return this.orientation;
    }

    @Deprecated
    public void setOrientation(OrientationEnum orientation) {
        this.orientation = orientation;
    }

    @Deprecated
    public PaperSizeEnum getPaperSize() {
        return this.paperSize;
    }

    @Deprecated
    public void setPaperSize(PaperSizeEnum paperSize) {
        this.paperSize = paperSize;
    }

    @Deprecated
    public FitToPageMode getFitMode() {
        return this.fitMode;
    }

    @Deprecated
    public void setFitMode(FitToPageMode fitMode) {
        this.fitMode = fitMode;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put(EXCL_EXP_HIDDEN, this.exportHidden);
        obj.put(EXCL_PRT_ENABLE, this.printEnabled);
        obj.put(EXCL_PRT_ORIENTAION, (Object)this.orientation);
        obj.put(EXCL_PRT_PAPER_SIZE, (Object)this.paperSize);
        obj.put(EXCL_PRT_FIT_MODE, (Object)this.fitMode);
        return obj;
    }

    public void fromJSON(JSONObject obj) throws JSONException {
        if (obj == null) {
            return;
        }
        if (obj.has(EXCL_PRT_ENABLE)) {
            this.exportHidden = obj.optBoolean(EXCL_EXP_HIDDEN, true);
        }
        if (obj.has(EXCL_PRT_ENABLE)) {
            this.printEnabled = obj.optBoolean(EXCL_PRT_ENABLE, false);
        }
        if (obj.has(EXCL_PRT_ORIENTAION)) {
            this.orientation = OrientationEnum.valueOf((String)obj.optString(EXCL_PRT_ORIENTAION));
        }
        if (obj.has(EXCL_PRT_PAPER_SIZE)) {
            this.paperSize = PaperSizeEnum.valueOf((String)obj.optString(EXCL_PRT_PAPER_SIZE));
        }
        if (obj.has(EXCL_PRT_FIT_MODE)) {
            this.fitMode = FitToPageMode.valueOf((String)obj.optString(EXCL_PRT_FIT_MODE));
        }
    }
}

