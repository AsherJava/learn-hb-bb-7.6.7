/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.service.execute.runtime;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.service.common.ColumnOrientedTable;
import com.jiuqi.nr.workflow2.service.common.IProcessStringTable;
import com.jiuqi.nr.workflow2.service.common.RowOrientedOperateTable;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateTable;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EventOperateResult
implements IEventOperateResult {
    protected static final String COLUMN_LEVEL = "level";
    protected static final String COLUMN_PARENT = "parent";
    protected final IEventOperateTable checkInfoTable;
    protected final IProcessStringTable checkStateTable;
    protected final Map<Integer, Set<Integer>> level2RowIndexes = new HashMap<Integer, Set<Integer>>();
    protected final Map<IEventOperateColumn, IOperateResultSet> operateResultSetMap = new LinkedHashMap<IEventOperateColumn, IOperateResultSet>();
    protected final Map<IEventOperateColumn, Object> operateMergeResult = new HashMap<IEventOperateColumn, Object>();
    protected final Map<IBusinessObject, Integer> businessObjectIndexes = new HashMap<IBusinessObject, Integer>();
    protected final Map<Integer, IBusinessObject> index2BusinessObject = new HashMap<Integer, IBusinessObject>();

    public EventOperateResult() {
        this.checkStateTable = new ColumnOrientedTable(new String[]{COLUMN_PARENT, COLUMN_LEVEL}, 1);
        this.checkInfoTable = new RowOrientedOperateTable(new String[0], this.checkStateTable.getRowCount());
    }

    public EventOperateResult(IBusinessObject businessObject) {
        this.checkStateTable = new ColumnOrientedTable(new String[]{COLUMN_PARENT, COLUMN_LEVEL}, 1);
        this.checkInfoTable = new RowOrientedOperateTable(new String[0], this.checkStateTable.getRowCount());
        this.businessObjectIndexes.put(businessObject, 0);
        this.index2BusinessObject.put(0, businessObject);
    }

    public EventOperateResult(IBusinessObjectCollection businessObjectCollection) {
        int rowIndex = 0;
        for (IBusinessObject businessObject : businessObjectCollection) {
            this.businessObjectIndexes.put(businessObject, rowIndex);
            this.index2BusinessObject.put(rowIndex, businessObject);
            ++rowIndex;
        }
        this.checkStateTable = new ColumnOrientedTable(new String[]{COLUMN_PARENT, COLUMN_LEVEL}, rowIndex);
        this.checkInfoTable = new RowOrientedOperateTable(new String[0], this.checkStateTable.getRowCount());
    }

    public void appendBusinessObject(IBusinessObject businessObject) {
        this.checkStateTable.incrementRowCount(1);
        this.checkInfoTable.incrementRowCount(1);
        this.businessObjectIndexes.put(businessObject, this.checkStateTable.getRowCount() - 1);
        this.index2BusinessObject.put(this.checkStateTable.getRowCount() - 1, businessObject);
    }

    @Override
    public Integer getLevel(IBusinessObject businessObject) {
        Integer rowIndex = this.findBusinessObjectIndex(businessObject);
        if (rowIndex != null) {
            String cellValue = this.checkStateTable.getCellValue(rowIndex, COLUMN_LEVEL);
            return StringUtils.isEmpty((String)cellValue) ? null : Integer.valueOf(Integer.parseInt(cellValue));
        }
        return null;
    }

    @Override
    public Integer getParentRowIndex(IBusinessObject businessObject) {
        Integer rowIndex = this.findBusinessObjectIndex(businessObject);
        if (rowIndex != null) {
            String cellValue = this.checkStateTable.getCellValue(rowIndex, COLUMN_PARENT);
            return StringUtils.isEmpty((String)cellValue) ? null : Integer.valueOf(Integer.parseInt(cellValue));
        }
        return null;
    }

    @Override
    public IOperateResultSet getOperateResultSet(IEventOperateColumn event) {
        this.operateResultSetMap.computeIfAbsent(event, x$0 -> new OperateResultSetImpl((IEventOperateColumn)x$0));
        return this.operateResultSetMap.get(event);
    }

    @Override
    public IBusinessObject findBusinessObject(Integer rowIndex) {
        if (rowIndex != null && rowIndex >= 0) {
            return this.index2BusinessObject.get(rowIndex);
        }
        return null;
    }

    public Integer findBusinessObjectIndex(IBusinessObject businessObject) {
        return this.businessObjectIndexes.get(businessObject);
    }

    @Override
    public List<IBusinessObject> allCheckPassBusinessObjects() {
        ArrayList<IBusinessObject> businessObjects = new ArrayList<IBusinessObject>();
        List eventColumns = this.operateResultSetMap.keySet().stream().filter(column -> column.getAffectStatus() == EventExecutionAffect.IMPACT_REPORTING_CHECK).collect(Collectors.toList());
        int rowCount = this.checkStateTable.getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            IBusinessObject businessObject = this.index2BusinessObject.get(rowIndex);
            boolean isCheckPass = true;
            for (IEventOperateColumn eventColumn : eventColumns) {
                IOperateResultSet operateResultSet = this.getOperateResultSet(eventColumn);
                WFMonitorCheckResult checkStatus = operateResultSet.getCheckStatus(businessObject);
                if (WFMonitorCheckResult.CHECK_UN_PASS != checkStatus && WFMonitorCheckResult.UN_CHECK != checkStatus) continue;
                isCheckPass = false;
                break;
            }
            if (!isCheckPass) continue;
            businessObjects.add(businessObject);
        }
        return businessObjects;
    }

    public boolean hasCheckPassed(IBusinessObject businessObject) {
        List eventColumns = this.operateResultSetMap.keySet().stream().filter(column -> column.getAffectStatus() == EventExecutionAffect.IMPACT_REPORTING_CHECK).collect(Collectors.toList());
        for (IEventOperateColumn eventColumn : eventColumns) {
            IOperateResultSet operateResultSet = this.getOperateResultSet(eventColumn);
            WFMonitorCheckResult checkStatus = operateResultSet.getCheckStatus(businessObject);
            if (WFMonitorCheckResult.CHECK_UN_PASS != checkStatus) continue;
            return false;
        }
        return true;
    }

    public List<IBusinessObject> getAllBusinessObject() {
        ArrayList<IBusinessObject> businessObjects = new ArrayList<IBusinessObject>();
        List sortedRowIndexes = this.index2BusinessObject.keySet().stream().sorted().collect(Collectors.toList());
        for (Integer rowIndex : sortedRowIndexes) {
            businessObjects.add(this.index2BusinessObject.get(rowIndex));
        }
        return businessObjects;
    }

    @Override
    public List<IEventOperateColumn> allEventOperateColumns() {
        return new ArrayList<IEventOperateColumn>(this.operateResultSetMap.keySet());
    }

    public void setOperateResult(IEventOperateColumn operateColumn, IBusinessObject businessObject, IEventOperateInfo operateInfo) {
        Integer rowIndex = this.findBusinessObjectIndex(businessObject);
        if (rowIndex != null) {
            this.setOperateResult(rowIndex, operateColumn.getColumnName(), operateInfo);
        } else {
            this.appendBusinessObject(businessObject);
            rowIndex = this.findBusinessObjectIndex(businessObject);
            if (rowIndex != null) {
                this.setOperateResult(rowIndex, operateColumn.getColumnName(), operateInfo);
            }
        }
    }

    public void removeBusinessObjects(List<IBusinessObject> removeObjects) {
        int rowCount = this.checkStateTable.getRowCount();
        for (int rowIndex = rowCount - 1; rowIndex >= 0; --rowIndex) {
            IBusinessObject businessObject = this.index2BusinessObject.get(rowIndex);
            if (!removeObjects.contains(businessObject)) continue;
            this.checkStateTable.removeRowData(rowIndex);
            this.checkInfoTable.removeRowData(rowIndex);
            this.businessObjectIndexes.remove(businessObject);
            this.index2BusinessObject.remove(rowIndex);
            for (int i = rowIndex + 1; i < rowCount; ++i) {
                IBusinessObject bo = this.index2BusinessObject.get(i);
                if (bo == null) continue;
                this.businessObjectIndexes.put(bo, i - 1);
                this.index2BusinessObject.put(i - 1, bo);
                this.index2BusinessObject.remove(i);
            }
            --rowCount;
        }
    }

    protected void setOperateResult(Integer rowIndex, String columnName, IEventOperateInfo operateInfo) {
        if (operateInfo != null && operateInfo.getCheckResult() != null) {
            this.checkStateTable.setCellValue(rowIndex, columnName, operateInfo.getCheckResult().toString());
        }
        this.checkInfoTable.setCellValue(rowIndex, columnName, operateInfo);
    }

    @Override
    public Object toOutputDetail() {
        return null;
    }

    @Override
    public String toResultMessage() {
        return "";
    }

    @Override
    public AsyncJobResult toAsyncJobResult() {
        return null;
    }

    protected final class OperateResultSetImpl
    implements IOperateResultSet {
        private final IEventOperateColumn operateColumn;

        public OperateResultSetImpl(IEventOperateColumn eventOperateColumn) {
            this.operateColumn = eventOperateColumn;
            EventOperateResult.this.checkStateTable.incrementColNames(new String[]{eventOperateColumn.getColumnName()});
            EventOperateResult.this.checkInfoTable.incrementColNames(new String[]{eventOperateColumn.getColumnName()});
        }

        public void setOperateResult(Object operateResult) {
            EventOperateResult.this.operateMergeResult.put(this.operateColumn, operateResult);
        }

        public void setOperateResult(IBusinessObject businessObject, IEventOperateInfo operateInfo) {
            EventOperateResult.this.setOperateResult(this.operateColumn, businessObject, operateInfo);
        }

        public void setOtherOperateResult(IBusinessObject businessObject, IEventOperateInfo operateInfo) {
            EventOperateResult.this.getOperateResultSet(IEventOperateColumn.DEF_OPT_COLUMN).setOperateResult(businessObject, operateInfo);
        }

        public void setLevel(IBusinessObject businessObject, Integer level) {
            Integer rowIndex = this.findBusinessObjectIndex(businessObject);
            if (rowIndex != null && level != null) {
                EventOperateResult.this.checkStateTable.setCellValue(rowIndex, EventOperateResult.COLUMN_LEVEL, level.toString());
                EventOperateResult.this.level2RowIndexes.computeIfAbsent(level, k -> new HashSet());
                EventOperateResult.this.level2RowIndexes.get(level).add(rowIndex);
            }
        }

        public void setParentRowIndex(IBusinessObject businessObject, Integer parentRowIndex) {
            Integer rowIndex = this.findBusinessObjectIndex(businessObject);
            if (rowIndex != null && parentRowIndex != null && !rowIndex.equals(parentRowIndex)) {
                EventOperateResult.this.checkStateTable.setCellValue(rowIndex, EventOperateResult.COLUMN_PARENT, parentRowIndex.toString());
            }
        }

        public boolean containsBusinessObject(IBusinessObject businessObject) {
            return EventOperateResult.this.businessObjectIndexes.containsKey(businessObject);
        }

        public boolean hasCheckPassed(IBusinessObject businessObject) {
            if (WFMonitorCheckResult.CHECK_UN_PASS == this.getCheckStatus(businessObject)) {
                return false;
            }
            return EventOperateResult.this.hasCheckPassed(businessObject);
        }

        public Integer findBusinessObjectIndex(IBusinessObject businessObject) {
            if (businessObject != null) {
                return EventOperateResult.this.findBusinessObjectIndex(businessObject);
            }
            return null;
        }

        public Integer getParentRowIndex(IBusinessObject businessObject) {
            return EventOperateResult.this.getParentRowIndex(businessObject);
        }

        public WFMonitorCheckResult getCheckStatus(IBusinessObject businessObject) {
            if (this.containsBusinessObject(businessObject)) {
                Integer rowIndex = this.findBusinessObjectIndex(businessObject);
                return WFMonitorCheckResult.valueOfName((String)EventOperateResult.this.checkStateTable.getCellValue(rowIndex, this.operateColumn.getColumnName()));
            }
            return WFMonitorCheckResult.UN_CHECK;
        }

        public IBusinessObject findBusinessObject(Integer rowIndex) {
            return EventOperateResult.this.findBusinessObject(rowIndex);
        }

        public List<IBusinessObject> allCheckPassBusinessObjects() {
            return EventOperateResult.this.allCheckPassBusinessObjects();
        }

        public IEventOperateInfo getOperateResult(IBusinessObject businessObject) {
            Integer rowIndex = this.findBusinessObjectIndex(businessObject);
            if (rowIndex != null) {
                IEventOperateInfo cellValue = EventOperateResult.this.checkInfoTable.getCellValue(rowIndex, this.operateColumn.getColumnName());
                return cellValue != null ? cellValue : new EventOperateInfo(WFMonitorCheckResult.UN_CHECK, "\u4e8b\u4ef6\u672a\u505a\u68c0\u67e5\uff01\uff01");
            }
            return new EventOperateInfo(WFMonitorCheckResult.UN_CHECK, "\u4e8b\u4ef6\u672a\u505a\u68c0\u67e5\uff01\uff01");
        }

        public List<IBusinessObject> filterBusinessObject(WFMonitorCheckResult checkResult) {
            ArrayList<IBusinessObject> businessObjects = new ArrayList<IBusinessObject>();
            int rowCount = EventOperateResult.this.checkStateTable.getRowCount();
            for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
                String cellValue = EventOperateResult.this.checkStateTable.getCellValue(rowIndex, this.operateColumn.getColumnName());
                if (!StringUtils.isNotEmpty((String)cellValue) || !cellValue.equals(checkResult.name())) continue;
                businessObjects.add(EventOperateResult.this.index2BusinessObject.get(rowIndex));
            }
            return businessObjects;
        }

        public List<IBusinessObject> getAllBusinessObject() {
            return EventOperateResult.this.getAllBusinessObject();
        }

        public Map<Integer, Set<Integer>> getLevel2RowIndexes() {
            return EventOperateResult.this.level2RowIndexes;
        }

        public void removeBusinessObjects(List<IBusinessObject> businessObjects) {
            EventOperateResult.this.removeBusinessObjects(businessObjects);
        }
    }
}

