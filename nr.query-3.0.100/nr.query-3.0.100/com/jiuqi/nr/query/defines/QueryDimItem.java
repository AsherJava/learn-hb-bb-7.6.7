/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataRow
 */
package com.jiuqi.nr.query.defines;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.nr.query.defines.DimensionItemType;
import java.util.ArrayList;
import java.util.List;

public class QueryDimItem
implements Cloneable {
    private String showTitle;
    private String editTitle;
    private int depth;
    private int childSize;
    private int childDataSize;
    private int ownDataSize;
    private List<QueryDimItem> childItems;
    private DimensionItemType itemType;
    private String dimensionValueSet;
    private DimensionValueSet dimValueSet;
    private String dimensionName;
    private boolean isDrillDown;
    private boolean isDrawThisRow;
    private boolean isDispel;
    private Integer dataIndex;
    boolean isNotTreeStruct = true;
    boolean isTree;
    int dimLevel;
    boolean isMaster;
    List<IDataRow> detailRows;
    IDataRow staticticsRow;
    boolean isShowSubTotal;
    boolean isSubTotalInFront;
    boolean isDraw;
    boolean isSubTotalItem;
    boolean childItemhasField;
    boolean childHasDetailRow;
    boolean hasWriteTotal;
    public boolean hasChildItem = false;
    boolean pageEnd = false;
    public int totalSize;
    public boolean isFinish = false;
    public boolean isFirst = false;
    public int itemPageStart = 0;
    private boolean isLastDimension = false;
    private int childItemSize;

    public static QueryDimItem newItem(String showTitle, String editTitle, int depth, int ownDataSize, DimensionItemType itemType) {
        QueryDimItem item = new QueryDimItem();
        item.setShowTitle(showTitle);
        item.setEditTitle(editTitle);
        item.setDepth(depth);
        item.setItemType(itemType);
        item.setChildItems(new ArrayList<QueryDimItem>());
        item.setDetailRows(new ArrayList<IDataRow>());
        return item;
    }

    public int getChildItemSize() {
        return this.childItemSize;
    }

    public void setChildItemSize(int childItemSize) {
        this.childItemSize = childItemSize;
    }

    public boolean hasWriteTotal() {
        return this.hasWriteTotal;
    }

    public void setHasWriteTotal(boolean hasWriteTotal) {
        this.hasWriteTotal = hasWriteTotal;
    }

    public void setChildHasDetailRow(boolean childHasDetailRow) {
        this.childHasDetailRow = childHasDetailRow;
    }

    public boolean getChildHasDetailRow() {
        return this.childHasDetailRow;
    }

    public void setChildItemhasField(boolean childItemhasField) {
        this.childItemhasField = childItemhasField;
    }

    public boolean getChildItemhasField() {
        return this.childItemhasField;
    }

    public void setIsSubTotalItem(boolean isSubTotalItem) {
        this.isSubTotalItem = isSubTotalItem;
    }

    public boolean getIsSubTotalItem() {
        return this.isSubTotalItem;
    }

    public void setChildDataSize(int childDataSize) {
        this.childDataSize = childDataSize;
    }

    public int getChildDataSize() {
        return this.childDataSize;
    }

    public boolean isPageEnd() {
        return this.pageEnd;
    }

    public void setPageEnd(boolean pageEnd) {
        this.pageEnd = pageEnd;
    }

    public void setIsDraw(boolean isDraw) {
        this.isDraw = isDraw;
    }

    public boolean getIsDraw() {
        return this.isDraw;
    }

    public void setIsSubTotalInFront(boolean isSubTotalInFront) {
        this.isSubTotalInFront = isSubTotalInFront;
    }

    public boolean getIsSubTotalInFront() {
        return this.isSubTotalInFront;
    }

    public void setIsShowSubTotal(boolean isShowSubTotal) {
        this.isShowSubTotal = isShowSubTotal;
    }

    public boolean getIsShowSubTotal() {
        return this.isShowSubTotal;
    }

    public void setStaticticsRow(IDataRow staticticsRow) {
        this.staticticsRow = staticticsRow;
    }

    public IDataRow getStaticticsRow() {
        return this.staticticsRow;
    }

    public void setDetailRows(List<IDataRow> detailRows) {
        this.detailRows = detailRows;
    }

    public List<IDataRow> getDetailRows() {
        return this.detailRows;
    }

    public void setIsTree(boolean isTree) {
        this.isTree = isTree;
    }

    public boolean getIsTree() {
        return this.isTree;
    }

    public void setIsNotTreeStruct(boolean isNotTreeStruct) {
        this.isNotTreeStruct = isNotTreeStruct;
    }

    public boolean getIsNotTreeStruct() {
        return this.isNotTreeStruct;
    }

    public void setIsMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }

    public boolean getIsMaster() {
        return this.isMaster;
    }

    public void setDataIndex(Integer dataIndex) {
        this.dataIndex = dataIndex;
    }

    public Integer getDataIndex() {
        return this.dataIndex;
    }

    public void setDimensionValueSet(String dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public DimensionValueSet getDimValueSet() {
        if (this.dimValueSet == null) {
            this.dimValueSet = new DimensionValueSet();
            this.dimValueSet.parseString(this.dimensionValueSet);
        }
        return this.dimValueSet;
    }

    public String getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setOwnDataSize(int ownDataSize) {
        this.ownDataSize = ownDataSize;
    }

    public int getOwnDataSize() {
        return this.ownDataSize;
    }

    public void setChildItems(List<QueryDimItem> childItems) {
        this.childItems = childItems;
    }

    public List<QueryDimItem> getChildItems() {
        return this.childItems;
    }

    public void setItemType(DimensionItemType itemType) {
        this.itemType = itemType;
    }

    public DimensionItemType getItemType() {
        return this.itemType;
    }

    public void setEditTitle(String editTitle) {
        this.editTitle = editTitle;
    }

    public String getEditTitle() {
        return this.editTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getShowTitle() {
        return this.showTitle;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return this.depth;
    }

    public int getChildSize() {
        if (this.childSize == 0 && this.childItems != null) {
            this.childSize = this.calcSize(this.childItems);
        }
        return this.childSize;
    }

    private int calcSize(List<QueryDimItem> items) {
        int count = 0;
        for (int i = 0; i < items.size(); ++i) {
            ++count;
            QueryDimItem item = items.get(i);
            if (item.getChildItems() == null) continue;
            count += this.calcSize(item.getChildItems());
        }
        return count;
    }

    public QueryDimItem clone() throws CloneNotSupportedException {
        return (QueryDimItem)super.clone();
    }

    public boolean isLastDimension() {
        return this.isLastDimension;
    }

    public void setLastDimension(boolean lastDimension) {
        this.isLastDimension = lastDimension;
    }
}

