/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.billcore.enums.GcBillStatusEnum
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.va.datamodel.service.VaDataModelPublishedService
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 */
package com.jiuqi.gcreport.invest.investbill.dto;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.billcore.enums.GcBillStatusEnum;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GcInvestBillGroupDTO {
    private DefaultTableEntity master;
    private List<DefaultTableEntity> items;
    private List<DefaultTableEntity> currYearItems;

    public GcInvestBillGroupDTO(DefaultTableEntity master, List<DefaultTableEntity> items) {
        this.master = master;
        this.items = items;
    }

    public GcInvestBillGroupDTO(DefaultTableEntity master, List<DefaultTableEntity> items, boolean calcCurrYear) {
        this.master = master;
        if (!CollectionUtils.isEmpty(items)) {
            items = items.stream().filter(item -> null == item.getFieldValue("STATUS") || GcBillStatusEnum.HANDLED.getCode().equals(item.getFieldValue("STATUS"))).collect(Collectors.toList());
            items = this.getAggregatedSubItems(items);
        }
        this.items = this.sortItems(items);
        Object acctYear = master.getFieldValue("ACCTYEAR");
        if (acctYear == null) {
            return;
        }
        int masterYear = ConverterUtils.getAsInteger((Object)acctYear);
        if (calcCurrYear) {
            this.currYearItems = this.filterYear(items, masterYear);
        }
    }

    private List<DefaultTableEntity> getAggregatedSubItems(List<DefaultTableEntity> items) {
        if (CollectionUtils.isEmpty(items)) {
            return new ArrayList<DefaultTableEntity>();
        }
        Map<Integer, Map<String, List<DefaultTableEntity>>> subitemsGroupedByMonthAndChangeScenario = items.stream().collect(Collectors.groupingBy(record -> {
            Date date = (Date)record.getFieldValue("CHANGEDATE");
            if (date != null) {
                return DateUtils.getDateFieldValue((Date)date, (int)2);
            }
            return null;
        }, Collectors.groupingBy(record -> (String)record.getFieldValue("CHANGESCENARIO"))));
        ArrayList<DefaultTableEntity> newSubItems = new ArrayList<DefaultTableEntity>();
        for (Map.Entry<Integer, Map<String, List<DefaultTableEntity>>> subitemsGroupedByMonthAndChangeScenarioEntry : subitemsGroupedByMonthAndChangeScenario.entrySet()) {
            Map<String, List<DefaultTableEntity>> changesScenario2SubItemsMap = subitemsGroupedByMonthAndChangeScenarioEntry.getValue();
            for (Map.Entry<String, List<DefaultTableEntity>> changesScenario2SubItemsEntrySet : changesScenario2SubItemsMap.entrySet()) {
                List<DefaultTableEntity> subItems = changesScenario2SubItemsEntrySet.getValue();
                if (subItems.size() == 1) {
                    newSubItems.add(subItems.get(0));
                    continue;
                }
                DefaultTableEntity subItem = subItems.get(0);
                Map<String, Integer[]> columnName2PrecisionMap = this.getNumericColumnName2PrecisionMap();
                for (Map.Entry<String, Integer[]> columnName2PrecisionEntry : columnName2PrecisionMap.entrySet()) {
                    String columnName = columnName2PrecisionEntry.getKey();
                    Integer[] lengths = columnName2PrecisionEntry.getValue();
                    int fractionDigits = lengths.length == 2 ? lengths[1] : 0;
                    BigDecimal sumAmt = BigDecimal.ZERO;
                    for (DefaultTableEntity entity : subItems) {
                        Object value = entity.getFieldValue(columnName);
                        if (null == value) continue;
                        sumAmt = NumberUtils.sum((BigDecimal)sumAmt, (BigDecimal)ConverterUtils.getAsBigDecimal((Object)value));
                    }
                    subItem.getFields().put(columnName, NumberUtils.round((double)sumAmt.doubleValue(), (int)fractionDigits));
                }
                newSubItems.add(subItem);
            }
        }
        return newSubItems;
    }

    private Map<String, Integer[]> getNumericColumnName2PrecisionMap() {
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_INVESTBILLITEM");
        DataModelDO dataModelDO = ((VaDataModelPublishedService)SpringContextUtils.getBean(VaDataModelPublishedService.class)).get(dataModelDTO);
        List columns = dataModelDO.getColumns();
        Map<String, Integer[]> columnName2LengthsMap = columns.stream().filter(item -> !DataModelType.ColumnAttr.SYSTEM.equals((Object)item.getColumnAttr()) && DataModelType.ColumnType.NUMERIC.equals((Object)item.getColumnType())).collect(Collectors.toMap(item -> item.getColumnName(), item -> item.getLengths()));
        return columnName2LengthsMap;
    }

    public DefaultTableEntity getMaster() {
        return this.master;
    }

    public void setMaster(DefaultTableEntity master) {
        this.master = master;
    }

    public List<DefaultTableEntity> getItems() {
        return this.items;
    }

    public void setItems(List<DefaultTableEntity> items) {
        this.items = items;
    }

    public List<DefaultTableEntity> getCurrYearItems() {
        return this.currYearItems;
    }

    public void setCurrYearItems(List<DefaultTableEntity> currYearItems) {
        this.currYearItems = currYearItems;
    }

    private List<DefaultTableEntity> filterYear(List<DefaultTableEntity> items, int masterYear) {
        Calendar calendar = Calendar.getInstance();
        ArrayList<DefaultTableEntity> currYearItems = new ArrayList<DefaultTableEntity>(16);
        if (!CollectionUtils.isEmpty(items)) {
            items.forEach(item -> {
                Object changeDateObj = item.getFieldValue("CHANGEDATE");
                if (null == changeDateObj) {
                    return;
                }
                calendar.setTime((Date)changeDateObj);
                int itemYear = calendar.get(1);
                if (masterYear == itemYear) {
                    currYearItems.add((DefaultTableEntity)item);
                }
            });
        }
        return currYearItems;
    }

    private List sortItems(List<DefaultTableEntity> items) {
        if (CollectionUtils.isEmpty(items)) {
            return new ArrayList();
        }
        items.sort((o1, o2) -> {
            Object o2ChangeDate = o2.getFieldValue("CHANGEDATE");
            if (o2ChangeDate == null) {
                return -1;
            }
            Object o1ChangeDate = o1.getFieldValue("CHANGEDATE");
            if (o1ChangeDate == null) {
                return 1;
            }
            return ((Date)o2ChangeDate).compareTo((Date)o1ChangeDate);
        });
        return items;
    }
}

