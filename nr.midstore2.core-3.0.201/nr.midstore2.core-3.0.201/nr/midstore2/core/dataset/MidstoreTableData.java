/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 */
package nr.midstore2.core.dataset;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MidstoreTableData {
    private String key;
    private String dataTableKey;
    private String dataTableCode;
    private String taskKey;
    private UUID parentKey;
    private int type;
    private String title;
    private String order;
    private String floatNumber;
    private boolean keyIsUnique;
    private boolean keyCanNull;
    private int summeryType;
    private String numberStructure;
    private String orderFieldKey;
    private int pageSize;
    private String filterCondition;
    private String tablekey;
    private boolean autoAddRow;
    private int autoAddRowSpan;
    private boolean canInsertRow;
    private boolean canDeleteRow;
    private boolean minRowNumActive;
    private String cardFormInfo;
    private String gradingSumInfo;
    private String rowExpandField;
    private String expandFilter;
    private String readOnlyCondition;
    private boolean readOnly = false;
    private List<EntityDefaultValue> regionEntityDefaultValue = new ArrayList<EntityDefaultValue>();
    private int defaultRowCount;
    private int maxRowCount;
    private boolean allowDuplicateKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UUID getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(UUID parentKey) {
        this.parentKey = parentKey;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFloatNumber() {
        return this.floatNumber;
    }

    public void setFloatNumber(String floatNumber) {
        this.floatNumber = floatNumber;
    }

    public boolean isKeyIsUnique() {
        return this.keyIsUnique;
    }

    public void setKeyIsUnique(boolean keyIsUnique) {
        this.keyIsUnique = keyIsUnique;
    }

    public boolean isKeyCanNull() {
        return this.keyCanNull;
    }

    public void setKeyCanNull(boolean keyCanNull) {
        this.keyCanNull = keyCanNull;
    }

    public int getSummeryType() {
        return this.summeryType;
    }

    public void setSummeryType(int summeryType) {
        this.summeryType = summeryType;
    }

    public String getNumberStructure() {
        return this.numberStructure;
    }

    public void setNumberStructure(String numberStructure) {
        this.numberStructure = numberStructure;
    }

    public String getOrderFieldKey() {
        return this.orderFieldKey;
    }

    public void setOrderFieldKey(String orderFieldKey) {
        this.orderFieldKey = orderFieldKey;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getTablekey() {
        return this.tablekey;
    }

    public void setTablekey(String tablekey) {
        this.tablekey = tablekey;
    }

    public int getAutoAddRowSpan() {
        return this.autoAddRowSpan;
    }

    public void setAutoAddRowSpan(int autoAddRowSpan) {
        this.autoAddRowSpan = autoAddRowSpan;
    }

    public boolean isCanInsertRow() {
        return this.canInsertRow;
    }

    public void setCanInsertRow(boolean canInsertRow) {
        this.canInsertRow = canInsertRow;
    }

    public boolean isCanDeleteRow() {
        return this.canDeleteRow;
    }

    public void setCanDeleteRow(boolean canDeleteRow) {
        this.canDeleteRow = canDeleteRow;
    }

    public String getCardFormInfo() {
        return this.cardFormInfo;
    }

    public void setCardFormInfo(String cardFormInfo) {
        this.cardFormInfo = cardFormInfo;
    }

    public String getGradingSumInfo() {
        return this.gradingSumInfo;
    }

    public void setGradingSumInfo(String gradingSumInfo) {
        this.gradingSumInfo = gradingSumInfo;
    }

    public String getRowExpandField() {
        return this.rowExpandField;
    }

    public void setRowExpandField(String rowExpandField) {
        this.rowExpandField = rowExpandField;
    }

    public String getExpandFilter() {
        return this.expandFilter;
    }

    public void setExpandFilter(String expandFilter) {
        this.expandFilter = expandFilter;
    }

    public boolean isAutoAddRow() {
        return this.autoAddRow;
    }

    public void setAutoAddRow(boolean autoAddRow) {
        this.autoAddRow = autoAddRow;
    }

    public boolean isMinRowNumActive() {
        return this.minRowNumActive;
    }

    public void setMinRowNumActive(boolean minRowNumActive) {
        this.minRowNumActive = minRowNumActive;
    }

    public String getReadOnlyCondition() {
        return this.readOnlyCondition;
    }

    public void setReadOnlyCondition(String readOnlyCondition) {
        this.readOnlyCondition = readOnlyCondition;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public int getDefaultRowCount() {
        return this.defaultRowCount;
    }

    public void setDefaultRowCount(int defaultRowCount) {
        this.defaultRowCount = defaultRowCount;
    }

    public int getMaxRowCount() {
        return this.maxRowCount;
    }

    public void setMaxRowCount(int maxRowCount) {
        this.maxRowCount = maxRowCount;
    }

    public boolean getAllowDuplicateKey() {
        return this.allowDuplicateKey;
    }

    public void setAllowDuplicateKey(boolean allowDuplicateKey) {
        this.allowDuplicateKey = allowDuplicateKey;
    }

    public List<EntityDefaultValue> getRegionEntityDefaultValue() {
        return this.regionEntityDefaultValue;
    }

    public void setRegionEntityDefaultValue(List<EntityDefaultValue> regionEntityDefaultValue) {
        this.regionEntityDefaultValue = regionEntityDefaultValue;
    }

    public void initialize(DataTable datatable) {
        this.key = datatable.getKey();
        this.dataTableKey = datatable.getKey();
        this.dataTableCode = datatable.getCode();
        this.parentKey = null;
        if (datatable.getDataTableType() == DataTableType.TABLE) {
            this.type = DataTableType.TABLE.getValue();
        } else if (datatable.getDataTableType() == DataTableType.DETAIL) {
            this.type = DataTableType.DETAIL.getValue();
        }
        this.title = datatable.getTitle();
        this.order = datatable.getOrder();
        this.floatNumber = "";
        this.keyIsUnique = false;
        this.keyCanNull = true;
        this.summeryType = 0;
        this.numberStructure = "";
        this.defaultRowCount = 10;
        this.maxRowCount = 100000;
        this.pageSize = 1000;
        this.filterCondition = "";
        this.readOnlyCondition = "";
        this.tablekey = "";
        this.autoAddRow = false;
        this.autoAddRowSpan = 0;
        this.canInsertRow = true;
        this.canDeleteRow = true;
        this.minRowNumActive = false;
        this.cardFormInfo = "";
        this.rowExpandField = "";
        this.expandFilter = "";
        this.allowDuplicateKey = datatable.getRepeatCode();
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public String getDataTableCode() {
        return this.dataTableCode;
    }

    public void setDataTableCode(String dataTableCode) {
        this.dataTableCode = dataTableCode;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

