/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 */
package com.jiuqi.gcreport.inputdata.check.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.inputdata.check.dao.InputDataCheckGatherDao;
import com.jiuqi.gcreport.inputdata.check.utils.InputDataCheckColumnDataUtils;
import com.jiuqi.gcreport.inputdata.check.utils.InputDataCheckComparatorUtil;
import com.jiuqi.gcreport.inputdata.check.utils.InputDataCheckSQLUtils;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckTabEnum;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

@Service
public class InputDataCheckGatherDaoImpl
implements InputDataCheckGatherDao {
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private TemplateEntDaoCacheService templateEntDaoCacheService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;

    @Override
    public Pagination<Map<String, Object>> getSumCheckTabData(InputDataCheckCondition inputDataCheckCondition, InputDataCheckTabEnum inputDataCheckTabEnum) {
        Assert.notNull((Object)inputDataCheckCondition.getTaskId(), GcI18nUtil.getMessage((String)"gc.inputdata.check.taskisnullmsg"));
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputDataCheckCondition.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        return this.getGatherCheckTabItems(inputDataCheckCondition, inputDataCheckTabEnum, tableName, dao);
    }

    private List<Map<String, Object>> getCheckDataGroupByGatherColumns(InputDataCheckCondition inputDataCheckCondition, InputDataCheckTabEnum inputDataCheckTabEnum, String tableName, EntNativeSqlDefaultDao<InputDataEO> dao) {
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(inputDataCheckCondition.getTaskId(), inputDataCheckCondition.getDataTime());
        if (StringUtils.isEmpty((String)systemId)) {
            throw new RuntimeException("gc.inputdata.check.nonesystemid");
        }
        StringBuilder sql = new StringBuilder(512);
        String selectColumns = InputDataCheckGatherDaoImpl.getColumnsSql(inputDataCheckCondition.getCheckGatherColumns(), "record", true);
        sql.append("SELECT ").append(selectColumns).append(" from ").append(tableName).append(" record \n");
        ArrayList<Object> params = new ArrayList<Object>();
        InputDataCheckStateEnum inputDataCheckStateEnum = null;
        if (InputDataCheckTabEnum.CHECKTAB.equals((Object)inputDataCheckTabEnum)) {
            inputDataCheckStateEnum = InputDataCheckStateEnum.CHECK;
        } else if (InputDataCheckTabEnum.UNCHECKTAB.equals((Object)inputDataCheckTabEnum)) {
            inputDataCheckStateEnum = InputDataCheckStateEnum.NOTCHECK;
        }
        String joinSql = InputDataCheckSQLUtils.getQueryCheckTabDataSql(inputDataCheckCondition, params, inputDataCheckStateEnum, systemId);
        sql.append(joinSql);
        InputDataCheckSQLUtils.initOtherCondition(sql, inputDataCheckCondition.getFilterCondition(), systemId, false);
        String groupByColumns = InputDataCheckGatherDaoImpl.getColumnsSql(inputDataCheckCondition.getCheckGatherColumns(), "record", false);
        sql.append(" group by ").append(groupByColumns);
        List inputDataCheckItems = dao.selectMap(sql.toString(), params);
        return inputDataCheckItems;
    }

    private Pagination<Map<String, Object>> getGatherCheckTabItems(InputDataCheckCondition inputDataCheckCondition, InputDataCheckTabEnum inputDataCheckTabEnum, String tableName, EntNativeSqlDefaultDao<InputDataEO> dao) {
        int pageSize;
        Pagination page = new Pagination(null, Integer.valueOf(0), inputDataCheckCondition.getPageNum(), inputDataCheckCondition.getPageSize());
        List<Map<String, Object>> inputDataCheckItems = this.getCheckDataGroupByGatherColumns(inputDataCheckCondition, inputDataCheckTabEnum, tableName, dao);
        if (CollectionUtils.isEmpty(inputDataCheckItems)) {
            return page;
        }
        int pageNum = inputDataCheckCondition.getPageNum();
        int startSize = (pageNum - 1) * (pageSize = inputDataCheckCondition.getPageSize().intValue());
        startSize = startSize < 0 ? 0 : startSize;
        int endSize = pageNum * pageSize;
        int totalCount = inputDataCheckItems.size();
        List<Map<String, Object>> inputDataCheckSubItems = totalCount >= startSize ? (endSize < 0 || totalCount < endSize ? inputDataCheckItems.subList(startSize, totalCount) : inputDataCheckItems.subList(startSize, endSize)) : inputDataCheckItems;
        page.setTotalElements(Integer.valueOf(totalCount));
        ArrayList<Map<String, Object>> inputGatherCheckItems = new ArrayList<Map<String, Object>>();
        String[] selectColumns = InputDataCheckTabEnum.UNCHECKTAB.equals((Object)inputDataCheckTabEnum) ? InputDataCheckColumnDataUtils.selectUnCheckTabColumns : (InputDataCheckTabEnum.UNCHECKTAB.equals((Object)inputDataCheckTabEnum) ? InputDataCheckColumnDataUtils.selectCheckTabColumns : InputDataCheckColumnDataUtils.selectAllCheckTabColumns);
        for (Map<String, Object> item : inputDataCheckSubItems) {
            List<InputDataEO> checkDatas = this.getCheckDataGroupByGatherCheckColumns(inputDataCheckCondition, inputDataCheckTabEnum, tableName, item);
            if (CollectionUtils.isEmpty(checkDatas)) continue;
            List<Map<String, Object>> checkDataItems = InputDataCheckColumnDataUtils.getCheckTableDataByColumns(checkDatas, selectColumns, true, inputDataCheckCondition, false);
            List<Map<String, Object>> gatherCheckItems = this.sumRecordData(checkDataItems, inputDataCheckCondition);
            inputGatherCheckItems.addAll(gatherCheckItems);
        }
        List<Map<String, Object>> returnDatas = InputDataCheckComparatorUtil.setRowSpanAndSort(inputGatherCheckItems, inputDataCheckCondition.getFilterCondition());
        page.setContent(returnDatas);
        page.setPageSize(Integer.valueOf(pageSize));
        page.setCurrentPage(Integer.valueOf(pageNum));
        return page;
    }

    private List<Map<String, Object>> sumRecordData(List<Map<String, Object>> unSortedRecords, InputDataCheckCondition inputDataCheckCondition) {
        if (CollectionUtils.isEmpty(unSortedRecords)) {
            return CollectionUtils.newArrayList();
        }
        HashMap<String, List> recordGroupBySumRecordKey = new HashMap<String, List>();
        for (Map<String, Object> data : unSortedRecords) {
            String sumRecordKey = String.valueOf(data.get("RECORDKEY"));
            List datas = recordGroupBySumRecordKey.computeIfAbsent(sumRecordKey, v -> new ArrayList());
            datas.add(data);
        }
        List checkGatherColumns = inputDataCheckCondition.getCheckGatherColumns();
        if (CollectionUtils.isEmpty((Collection)checkGatherColumns)) {
            return CollectionUtils.newArrayList();
        }
        List<String> sumCheckGatherColumns = this.getCheckGatherColumns(inputDataCheckCondition.getCheckGatherColumns());
        ArrayList<Map<String, Object>> checkGatherDatas = new ArrayList<Map<String, Object>>();
        for (List recordDatas : recordGroupBySumRecordKey.values()) {
            HashMap calSumData = new HashMap();
            HashSet<String> ids = new HashSet<String>();
            HashSet<String> checkGroupIds = new HashSet<String>();
            for (int i = 0; i < recordDatas.size(); ++i) {
                Map data = (Map)recordDatas.get(i);
                for (String column : data.keySet()) {
                    Object value = data.get(column);
                    ids.add(String.valueOf(data.get("ID")));
                    if (Objects.nonNull(data.get("CHECKGROUPID"))) {
                        checkGroupIds.add(data.get("CHECKGROUPID").toString());
                    }
                    if (sumCheckGatherColumns.contains(column)) {
                        calSumData.put(column, value);
                        continue;
                    }
                    if (value instanceof Number) {
                        calSumData.put(column, NumberUtils.formatObject(calSumData.get(column)).add(NumberUtils.formatObject(value)));
                        continue;
                    }
                    calSumData.put(column, null);
                }
            }
            calSumData.put("IDS", ids);
            calSumData.put("CHECKGROUPIDS", checkGroupIds);
            checkGatherDatas.add(calSumData);
        }
        return checkGatherDatas;
    }

    private List<InputDataEO> getCheckDataGroupByGatherCheckColumns(InputDataCheckCondition inputDataCheckCondition, InputDataCheckTabEnum inputDataCheckTabEnum, String tableName, Map<String, Object> inputDataCheckSubItems) {
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        StringBuilder sql = new StringBuilder(512);
        sql.append("SELECT record.* from ").append(tableName).append(" record \n");
        ArrayList<Object> params = new ArrayList<Object>();
        InputDataCheckStateEnum inputDataCheckStateEnum = null;
        if (InputDataCheckTabEnum.CHECKTAB.equals((Object)inputDataCheckTabEnum)) {
            inputDataCheckStateEnum = InputDataCheckStateEnum.CHECK;
        } else if (InputDataCheckTabEnum.UNCHECKTAB.equals((Object)inputDataCheckTabEnum)) {
            inputDataCheckStateEnum = InputDataCheckStateEnum.NOTCHECK;
        }
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(inputDataCheckCondition.getTaskId(), inputDataCheckCondition.getDataTime());
        String joinSql = InputDataCheckSQLUtils.getQueryCheckTabDataSql(inputDataCheckCondition, params, inputDataCheckStateEnum, systemId);
        sql.append(joinSql);
        InputDataCheckSQLUtils.initOtherCondition(sql, inputDataCheckCondition.getFilterCondition(), systemId, false);
        sql.append(this.getWhereSqlCheckColumns(inputDataCheckCondition.getCheckGatherColumns(), inputDataCheckSubItems));
        return dao.selectEntity(sql.toString(), params);
    }

    private String getWhereSqlCheckColumns(List<String> gatherCheckColumns, Map<String, Object> checkItemDatas) {
        StringBuffer whereSQL = new StringBuffer();
        for (String column : gatherCheckColumns) {
            String value = ObjectUtils.isEmpty(checkItemDatas.get(column)) ? "" : checkItemDatas.get(column);
            whereSQL.append(" and ").append(SqlUtils.getConditionOfObject((Object)value, (String)("record." + column)));
        }
        return whereSQL.toString();
    }

    public static String getColumnsSql(List<String> columnNames, String alias, boolean isAsFlag) {
        if (CollectionUtils.isEmpty(columnNames)) {
            return "";
        }
        StringBuilder buf = new StringBuilder(128);
        for (String column : columnNames) {
            buf.append(alias).append(".").append(column);
            if (isAsFlag) {
                buf.append(" as ").append(column);
            }
            buf.append(",");
        }
        buf.setLength(buf.length() - 1);
        return buf.toString();
    }

    private List<String> getCheckGatherColumns(List<String> checkGatherColumns) {
        if (CollectionUtils.isEmpty(checkGatherColumns)) {
            return CollectionUtils.newArrayList();
        }
        ArrayList<String> sumCheckGatherColumns = new ArrayList<String>();
        sumCheckGatherColumns.addAll(checkGatherColumns);
        sumCheckGatherColumns.add("RECORDKEY");
        sumCheckGatherColumns.add("ID");
        sumCheckGatherColumns.add("CHECKGROUPID");
        sumCheckGatherColumns.add("DC");
        checkGatherColumns.forEach(column -> {
            switch (column) {
                case "ORGCODE": {
                    sumCheckGatherColumns.add("UNITID");
                    sumCheckGatherColumns.add("UNITTITLE");
                    break;
                }
                case "UNIONRULEID": {
                    sumCheckGatherColumns.add("RULETITLE");
                    break;
                }
                case "SUBJECTCODE": {
                    sumCheckGatherColumns.add("SUBJECTTITLE");
                    break;
                }
                case "OPPUNITID": {
                    sumCheckGatherColumns.add("OPPUNITTITLE");
                    break;
                }
                default: {
                    sumCheckGatherColumns.add(column + "TITLE");
                }
            }
        });
        return sumCheckGatherColumns;
    }
}

