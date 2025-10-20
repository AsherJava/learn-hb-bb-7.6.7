/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.result;

import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.result.CellRestrictionInfo;
import com.jiuqi.bi.quickreport.engine.result.DataBarInfo;
import com.jiuqi.bi.quickreport.engine.result.FoldingInfo;
import com.jiuqi.bi.quickreport.engine.result.InteractiveInfo;
import com.jiuqi.bi.quickreport.engine.result.TraceInfo;
import com.jiuqi.bi.quickreport.model.JSONHelper;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class CellResultInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private Position rawCell;
    private Object rawValue;
    private String excelFormula;
    private String comment;
    private boolean styled;
    private String iconName;
    private int iconPos;
    private DataBarInfo dataBarInfo;
    private List<CellRestrictionInfo> restrictions;
    private InteractiveInfo interactiveInfo;
    private FoldingInfo foldingInfo;
    private List<TraceInfo> traceInfos;
    private static final String RET_RAWCELL = "cell";
    private static final String RET_RAWVALUE = "raw";
    private static final String RET_EXCEL = "excel";
    private static final String RET_ICON = "icon";
    private static final String RET_ICON_POS = "icon_pos";
    private static final String RET_BARINFO = "bar";
    private static final String RET_INTERACTIVE = "inter";
    private static final String RET_FOLDING = "folding";
    private static final String RET_RESTRICTIONS = "restrictions";
    private static final String RET_STYLED = "styled";
    private static final String RET_TRACEINFOS = "traceInfos";
    private static final String RET_COMMENT = "comment";

    public CellResultInfo() {
        this.restrictions = new ArrayList<CellRestrictionInfo>();
    }

    public CellResultInfo(CellValue value) {
        this.rawCell = value._bindingInfo == null || value._bindingInfo.getCellMap() == null ? null : value._bindingInfo.getCellMap().getPosition();
        this.rawValue = value.value;
        this.comment = value.getComment();
        this.styled = value.styleValue != null;
        this.iconName = value.styleValue == null || value.styleValue.getIconStyle() == null ? null : value.styleValue.getIconStyle().getIconName();
        this.iconPos = value.styleValue == null || value.styleValue.getIconStyle() == null ? 0 : value.styleValue.getIconStyle().getPosition();
        this.dataBarInfo = value.getDataBarInfo();
        this.interactiveInfo = value.interactiveInfo;
        this.setTraceInfos(value.getTraceInfos());
    }

    public Position getRawCell() {
        return this.rawCell;
    }

    public void setRawCell(Position rawCell) {
        this.rawCell = rawCell;
    }

    public Object getRawValue() {
        return this.rawValue;
    }

    public void setRawValue(Object rawValue) {
        this.rawValue = rawValue;
    }

    public String getExcelFormula() {
        return this.excelFormula;
    }

    public void setExcelFormula(String excelFormula) {
        this.excelFormula = excelFormula;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isStyled() {
        return this.styled;
    }

    public void setStyled(boolean styled) {
        this.styled = styled;
    }

    public String getIconName() {
        if (StringUtils.isNotEmpty((String)this.iconName) && this.iconName.toLowerCase().endsWith(".gif")) {
            return this.iconName.substring(0, this.iconName.lastIndexOf(46)) + ".png";
        }
        return this.iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getIconPos() {
        return this.iconPos;
    }

    public void setIconPos(int iconPos) {
        this.iconPos = iconPos;
    }

    public DataBarInfo getDataBarInfo() {
        return this.dataBarInfo;
    }

    public void setDataBarInfo(DataBarInfo dataBarInfo) {
        this.dataBarInfo = dataBarInfo;
    }

    public InteractiveInfo getInteractiveInfo() {
        return this.interactiveInfo;
    }

    public void setInteractiveInfo(InteractiveInfo interactiveInfo) {
        this.interactiveInfo = interactiveInfo;
    }

    public FoldingInfo getFoldingInfo() {
        return this.foldingInfo;
    }

    public void setFoldingInfo(FoldingInfo foldingInfo) {
        this.foldingInfo = foldingInfo;
    }

    public List<CellRestrictionInfo> getRestrictions() {
        if (this.restrictions == null) {
            this.restrictions = new ArrayList<CellRestrictionInfo>();
        }
        return this.restrictions;
    }

    public List<TraceInfo> getTraceInfos() {
        return this.traceInfos;
    }

    public void setTraceInfos(List<TraceInfo> traceInfos) {
        this.traceInfos = traceInfos;
    }

    public Object clone() {
        CellResultInfo resultInfo;
        try {
            resultInfo = (CellResultInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
        if (this.dataBarInfo != null) {
            resultInfo.dataBarInfo = this.dataBarInfo.clone();
        }
        if (this.restrictions != null) {
            resultInfo.restrictions = this.restrictions.stream().map(CellRestrictionInfo::clone).collect(Collectors.toList());
        }
        if (this.interactiveInfo != null) {
            resultInfo.interactiveInfo = this.interactiveInfo.clone();
        }
        if (this.foldingInfo != null) {
            resultInfo.foldingInfo = this.foldingInfo.clone();
        }
        return resultInfo;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[').append(this.rawValue);
        if (!StringUtils.isEmpty((String)this.excelFormula)) {
            buffer.append(", ").append(this.excelFormula);
        }
        if (this.styled) {
            buffer.append(", styled");
        }
        if (!StringUtils.isEmpty((String)this.iconName)) {
            buffer.append(", ").append(this.iconName).append('(').append(this.iconPos == 0 ? "left" : "right").append(')');
        }
        if (this.dataBarInfo != null) {
            buffer.append(", ").append(this.dataBarInfo);
        }
        if (this.interactiveInfo != null) {
            buffer.append(", ").append(this.interactiveInfo);
        }
        if (this.foldingInfo != null) {
            buffer.append(", ").append(this.foldingInfo);
        }
        if (this.restrictions != null) {
            buffer.append(", ").append(this.restrictions);
        }
        buffer.append(']');
        return buffer.toString();
    }

    public JSONObject toJSON(List<DataBarInfo> infos) throws JSONException {
        JSONObject info = new JSONObject();
        if (this.rawCell != null) {
            info.put(RET_RAWCELL, (Object)this.rawCell.toString());
        }
        if (this.rawValue != null) {
            info.put(RET_RAWVALUE, (Object)JSONHelper.toTypedValue(this.rawValue));
        }
        if (!StringUtils.isEmpty((String)this.comment)) {
            info.put(RET_COMMENT, (Object)this.comment);
        }
        if (!StringUtils.isEmpty((String)this.excelFormula)) {
            info.put(RET_EXCEL, (Object)this.excelFormula);
        }
        if (this.styled) {
            info.put(RET_STYLED, true);
        }
        if (!StringUtils.isEmpty((String)this.iconName)) {
            info.put(RET_ICON, (Object)this.iconName);
            info.put(RET_ICON_POS, this.iconPos);
        }
        if (this.dataBarInfo != null) {
            int index = infos.indexOf(this.dataBarInfo);
            if (index == -1) {
                index = infos.size();
                infos.add(this.dataBarInfo);
            }
            info.put(RET_BARINFO, index);
        }
        if (this.interactiveInfo != null) {
            JSONObject interInfo = this.interactiveInfo.toJSON();
            info.put(RET_INTERACTIVE, (Object)interInfo);
        }
        if (this.foldingInfo != null) {
            JSONObject foldInfo = this.foldingInfo.toJSON();
            info.put(RET_FOLDING, (Object)foldInfo);
        }
        if (this.restrictions != null && !this.restrictions.isEmpty()) {
            JSONArray arr = new JSONArray();
            for (CellRestrictionInfo restriction : this.restrictions) {
                arr.put((Object)restriction.toJSON());
            }
            info.put(RET_RESTRICTIONS, (Object)arr);
        }
        if (this.traceInfos != null) {
            JSONArray arr = new JSONArray();
            for (TraceInfo traceInfo : this.traceInfos) {
                arr.put((Object)traceInfo.toJSON());
            }
            info.put(RET_TRACEINFOS, (Object)arr);
        }
        return info;
    }

    public void fromJSON(JSONObject obj, List<DataBarInfo> infos) throws JSONException {
        int i;
        JSONObject valObj;
        String cellStr = obj.optString(RET_RAWCELL);
        if (!StringUtils.isEmpty((String)cellStr)) {
            this.rawCell = Position.valueOf((String)cellStr);
        }
        if ((valObj = obj.optJSONObject(RET_RAWVALUE)) != null) {
            this.rawValue = JSONHelper.fromTypedValue(valObj);
        }
        this.excelFormula = obj.optString(RET_EXCEL);
        this.comment = obj.optString(RET_COMMENT);
        this.styled = obj.optBoolean(RET_STYLED);
        this.iconName = obj.optString(RET_ICON);
        this.iconPos = obj.optInt(RET_ICON_POS, 0);
        if (obj.has(RET_BARINFO)) {
            int index = obj.getInt(RET_BARINFO);
            this.dataBarInfo = infos.get(index);
        }
        if (obj.has(RET_INTERACTIVE)) {
            JSONObject interInfo = obj.optJSONObject(RET_INTERACTIVE);
            this.interactiveInfo = new InteractiveInfo();
            this.interactiveInfo.fromJSON(interInfo);
        }
        if (obj.has(RET_FOLDING)) {
            JSONObject foldInfo = obj.optJSONObject(RET_FOLDING);
            this.foldingInfo = new FoldingInfo();
            this.foldingInfo.fromJSON(foldInfo);
        }
        if (obj.has(RET_RESTRICTIONS)) {
            JSONArray arr = obj.optJSONArray(RET_RESTRICTIONS);
            this.restrictions = new ArrayList<CellRestrictionInfo>(arr.length());
            for (i = 0; i < arr.length(); ++i) {
                CellRestrictionInfo restriction = new CellRestrictionInfo();
                restriction.fromJSON(arr.optJSONObject(i));
                this.restrictions.add(restriction);
            }
        }
        if (obj.has(RET_TRACEINFOS)) {
            JSONArray arr = obj.getJSONArray(RET_TRACEINFOS);
            this.traceInfos = new ArrayList<TraceInfo>(arr.length());
            for (i = 0; i < arr.length(); ++i) {
                TraceInfo traceInfo = new TraceInfo();
                traceInfo.fromJSONObject(arr.getJSONObject(i));
                this.traceInfos.add(traceInfo);
            }
        } else {
            this.traceInfos = null;
        }
    }
}

