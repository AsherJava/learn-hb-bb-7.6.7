/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.quickreport.model.ExpandMode;
import com.jiuqi.bi.quickreport.model.HierarchyMode;
import com.jiuqi.bi.quickreport.model.HyperlinkInfo;
import com.jiuqi.bi.quickreport.model.HyperlinkType;
import com.jiuqi.bi.quickreport.model.HyperlinkWindowInfo;
import com.jiuqi.bi.quickreport.model.OrderMode;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public final class CellMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private Position position;
    private String value;
    private String display;
    private String comment;
    private String filter;
    private ExpandMode expandMode = ExpandMode.NONE;
    private Region expandRegion;
    private OrderMode orderMode = OrderMode.NONE;
    private String orderValue;
    private int topN;
    private int options;
    private HyperlinkInfo hyperlink;
    private HierarchyMode hierarchyMode = HierarchyMode.LIST;
    private int hierarchyLevel;
    private static final String MAP_POSITION = "position";
    private static final String MAP_VALUE = "value";
    private static final String MAP_DISPLAY = "display";
    private static final String MAP_COMMENT = "comment";
    private static final String MAP_FILTER = "filter";
    private static final String MAP_EXPANDMODE = "expandMode";
    private static final String MAP_EXPANDREGION = "expandRegion";
    private static final String MAP_HIERACHYIDENT = "hierarchyIdent";
    private static final String MAP_ORDERMODE = "orderMode";
    private static final String MAP_ORDERVALUE = "orderValue";
    private static final String MAP_TOPN = "topN";
    private static final String MAP_OPTIONS = "options";
    private static final String MAP_HIERARCHYMODE = "hierMode";
    private static final String MAP_HIERARCHYLEVEL = "hierLevel";
    private static final String MAP_HYPERLINK = "hyperlink";
    @Deprecated
    private static final String MAP_HYPERLINKINFO = "linkInfo";
    @Deprecated
    private static final String MAP_HYPERLINKWININFO = "linkWinInfo";
    @Deprecated
    public static final int LINKTYPE_NONE = 0;
    @Deprecated
    public static final int LINKTYPE_COMMON = 1;
    @Deprecated
    public static final int LINKTYPE_MESSAGE = 2;
    private static final int OPT_HIERACHY_IDENT = 1;
    private static final int OPT_ROW_SORTABLE = 2;
    private static final int OPT_HIDDEN_WHEN_EMPTYR = 4;
    private static final int OPT_DENSE_RANK = 8;
    private static final int OPT_ROW_FILTERABLE = 16;

    public CellMap() {
        this.hyperlink = new HyperlinkInfo();
    }

    public CellMap(Position position) {
        this();
        this.setPosition(position);
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplay() {
        return this.display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public ExpandMode getExpandMode() {
        return this.expandMode;
    }

    public void setExpandMode(ExpandMode expandMode) {
        this.expandMode = expandMode;
    }

    public Region getExpandRegion() {
        return this.expandRegion;
    }

    public void setExpandRegion(Region expandRegion) {
        this.expandRegion = expandRegion;
    }

    public OrderMode getOrderMode() {
        return this.orderMode;
    }

    public void setOrderMode(OrderMode orderMode) {
        this.orderMode = orderMode;
    }

    public String getOrderValue() {
        return this.orderValue;
    }

    public void setOrderValue(String orderValue) {
        this.orderValue = orderValue;
    }

    public int getTopN() {
        return this.topN;
    }

    public void setTopN(int topN) {
        this.topN = topN;
    }

    @Deprecated
    public JSONObject getHyperlinkInfo() {
        if (this.hyperlink.getType() == HyperlinkType.NONE || this.hyperlink.getData() == null) {
            return null;
        }
        JSONObject data = new JSONObject(this.hyperlink.getData(), JSONObject.getNames((JSONObject)this.hyperlink.getData()));
        switch (this.hyperlink.getType()) {
            case NORMAL: {
                if (data.has("hyperLinkId")) break;
                data.put("hyperLinkId", (Object)"com.jiuqi.bi.quickreport.hyperlink.url");
                break;
            }
            case MESSAGE: {
                data.put("hyperLinkId", (Object)"com.jiuqi.bi.quickreport.hyperlink.message");
            }
        }
        return data;
    }

    @Deprecated
    public void setHyperlinkInfo(JSONObject hyperlinkInfo) {
        if (hyperlinkInfo == null) {
            this.hyperlink.setType(HyperlinkType.NONE);
            return;
        }
        String linkType = hyperlinkInfo.optString("hyperLinkId");
        this.hyperlink.setType("com.jiuqi.bi.quickreport.hyperlink.message".equalsIgnoreCase(linkType) ? HyperlinkType.MESSAGE : HyperlinkType.NORMAL);
        this.hyperlink.setData(hyperlinkInfo);
    }

    @Deprecated
    public int getHyperlinkType() {
        switch (this.hyperlink.getType()) {
            case NORMAL: {
                return 1;
            }
            case MESSAGE: {
                return 2;
            }
        }
        return 0;
    }

    @Deprecated
    public void setHyperlinkWindowInfo(HyperlinkWindowInfo hyperlinkWindowInfo) {
        if (hyperlinkWindowInfo != null) {
            hyperlinkWindowInfo.into(this.hyperlink);
        }
    }

    @Deprecated
    public HyperlinkWindowInfo getHyperlinkWindowInfo() {
        return new HyperlinkWindowInfo(this.hyperlink);
    }

    public HyperlinkInfo getHyperlink() {
        return this.hyperlink;
    }

    public void setHyperlink(HyperlinkInfo hyperlink) {
        this.hyperlink = hyperlink;
    }

    private boolean testOption(int option) {
        return (this.options & option) != 0;
    }

    private void setOption(int option, boolean value) {
        this.options = value ? (this.options |= option) : (this.options &= ~option);
    }

    public boolean isHierachyIdent() {
        return this.hierarchyMode == HierarchyMode.INDENTED;
    }

    public void setHierachyIdent(boolean hierachyIdent) {
        this.setHierarchyMode(hierachyIdent ? HierarchyMode.INDENTED : HierarchyMode.LIST);
    }

    public HierarchyMode getHierarchyMode() {
        return this.hierarchyMode;
    }

    public void setHierarchyMode(HierarchyMode hierarchyMode) {
        this.hierarchyMode = hierarchyMode;
    }

    public int getHierarchyLevel() {
        return this.hierarchyLevel;
    }

    public void setHierarchyLevel(int hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }

    public boolean isRowSortable() {
        return this.testOption(2);
    }

    public void setRowSortable(boolean rowSortable) {
        this.setOption(2, rowSortable);
    }

    public boolean isRowFilterable() {
        return this.testOption(16);
    }

    public void setRowFilterable(boolean rowFilterable) {
        this.setOption(16, rowFilterable);
    }

    public boolean isHiddenWhenEmpty() {
        return this.testOption(4);
    }

    public void setHiddenWhenEmpty(boolean hidden) {
        this.setOption(4, hidden);
    }

    public boolean isDenseRank() {
        return this.testOption(8);
    }

    public void setDenseRank(boolean denseRank) {
        this.setOption(8, denseRank);
    }

    public Object clone() {
        CellMap cellMap;
        try {
            cellMap = (CellMap)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
        cellMap.hyperlink = this.hyperlink.clone();
        return cellMap;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject map = new JSONObject();
        map.put(MAP_POSITION, (Object)this.position.toString());
        map.put(MAP_VALUE, (Object)this.value);
        map.put(MAP_DISPLAY, (Object)this.display);
        map.put(MAP_COMMENT, (Object)this.comment);
        map.put(MAP_FILTER, (Object)this.filter);
        map.put(MAP_EXPANDMODE, (Object)this.expandMode.toString());
        if (this.expandMode != ExpandMode.NONE) {
            map.put(MAP_EXPANDREGION, (Object)this.expandRegion.toString());
            map.put(MAP_ORDERMODE, (Object)this.orderMode.toString());
            if (this.orderMode != OrderMode.NONE) {
                map.put(MAP_ORDERVALUE, (Object)this.orderValue);
            }
            map.put(MAP_TOPN, this.topN);
            map.put(MAP_HIERARCHYMODE, (Object)this.hierarchyMode.toString());
            if (this.hierarchyMode == HierarchyMode.TIERED) {
                map.put(MAP_HIERARCHYLEVEL, this.hierarchyLevel);
            }
        }
        map.put(MAP_OPTIONS, this.options);
        if (this.hyperlink.getType() != HyperlinkType.NONE) {
            JSONObject hyperObj = this.hyperlink.toJSON();
            map.put(MAP_HYPERLINK, (Object)hyperObj);
        }
        return map;
    }

    public void fromJSON(JSONObject map) throws JSONException {
        this.position = Position.valueOf((String)map.optString(MAP_POSITION));
        this.value = map.optString(MAP_VALUE);
        this.display = map.optString(MAP_DISPLAY);
        this.comment = map.optString(MAP_COMMENT);
        this.filter = map.optString(MAP_FILTER);
        this.expandMode = ExpandMode.valueOf(map.optString(MAP_EXPANDMODE));
        this.options = map.optInt(MAP_OPTIONS, 0);
        if (this.expandMode != ExpandMode.NONE) {
            this.expandRegion = Region.createRegion((String)map.optString(MAP_EXPANDREGION));
            this.orderMode = OrderMode.valueOf(map.optString(MAP_ORDERMODE));
            if (this.orderMode != OrderMode.NONE) {
                this.orderValue = map.optString(MAP_ORDERVALUE);
            }
            this.topN = map.optInt(MAP_TOPN);
            String hierModeStr = map.optString(MAP_HIERARCHYMODE);
            this.hierarchyMode = StringUtils.isEmpty((String)hierModeStr) ? (map.optBoolean(MAP_HIERACHYIDENT) || (this.options & 1) != 0 ? HierarchyMode.INDENTED : HierarchyMode.LIST) : HierarchyMode.parse(hierModeStr);
            if (this.hierarchyMode == HierarchyMode.TIERED) {
                this.hierarchyLevel = map.optInt(MAP_HIERARCHYLEVEL, 0);
                if (this.hierarchyLevel == -1) {
                    this.hierarchyLevel = 0;
                }
            }
        }
        this.loadHyperlink(map);
    }

    private void loadHyperlink(JSONObject json) {
        JSONObject hyperlinkJSON = json.optJSONObject(MAP_HYPERLINK);
        if (hyperlinkJSON == null) {
            JSONObject windowObj;
            JSONObject hyperInfoObj = json.optJSONObject(MAP_HYPERLINKINFO);
            if (hyperInfoObj != null) {
                this.setHyperlinkInfo(hyperInfoObj);
            }
            if ((windowObj = json.optJSONObject(MAP_HYPERLINKWININFO)) != null) {
                HyperlinkWindowInfo windowInfo = new HyperlinkWindowInfo();
                windowInfo.fromJSON(windowObj);
                windowInfo.into(this.hyperlink);
            }
            if (hyperInfoObj == null && windowObj == null) {
                this.hyperlink.clear();
            }
        } else {
            this.hyperlink.fromJSON(hyperlinkJSON);
        }
    }

    public String toString() {
        return this.position + "=" + this.value;
    }
}

