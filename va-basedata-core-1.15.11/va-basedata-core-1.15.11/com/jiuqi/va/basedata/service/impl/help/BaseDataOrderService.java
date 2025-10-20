/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.common.BaseDataContext
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$OrderType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.jiuqi.va.basedata.common.BaseDataContext;
import com.jiuqi.va.basedata.service.impl.help.BaseDataParamService;
import com.jiuqi.va.domain.basedata.BaseDataCacheDO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaBaseDataOrderService")
public class BaseDataOrderService {
    @Autowired
    private BaseDataParamService baseDataParamService;

    public void orderList(List<BaseDataDO> dataList, BaseDataDTO param) {
        if (dataList == null || dataList.size() < 2) {
            return;
        }
        if (param.getOrderBy() != null) {
            this.orderByCustom(dataList, param);
            return;
        }
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)param);
        int defineStructtype = defineDO.getStructtype();
        if (defineStructtype == 0) {
            this.orderByOrdinal(dataList);
            return;
        }
        if (defineStructtype == 1) {
            this.orderByGroup(dataList, param);
            return;
        }
        if (defineStructtype == 2) {
            this.orderByTree(dataList, param);
            return;
        }
        if (defineStructtype == 3) {
            Collections.sort(dataList, (o1, o2) -> o1.getCode().compareTo(o2.getCode()));
        }
    }

    private void orderByCustom(List<BaseDataDO> dataList, BaseDataDTO param) {
        HashMap colIndexMap;
        List orderBy = param.getOrderBy();
        if (orderBy.isEmpty()) {
            return;
        }
        boolean canIndex = dataList.get(0) instanceof BaseDataCacheDO;
        HashMap hashMap = colIndexMap = canIndex ? new HashMap() : null;
        if (canIndex) {
            String tenantName = param.getTenantName();
            String tablename = param.getTableName().toUpperCase();
            Map indexMap = BaseDataContext.getColIndexMap((String)tenantName, (String)tablename);
            if (colIndexMap != null && indexMap != null) {
                colIndexMap.putAll(indexMap);
            }
        }
        Collator comparator = Collator.getInstance(Locale.CHINA);
        Collections.sort(dataList, (o1, o2) -> {
            int compare = 0;
            int colIndex = -1;
            Object t1 = null;
            Object t2 = null;
            for (BaseDataSortDTO sort : orderBy) {
                colIndex = -1;
                if (canIndex && colIndexMap.containsKey(sort.getColumn())) {
                    colIndex = (Integer)colIndexMap.get(sort.getColumn());
                }
                if (canIndex && colIndex > -1) {
                    t1 = ((BaseDataCacheDO)o1).getFieldValue((Object)sort.getColumn(), colIndex, true);
                    t2 = ((BaseDataCacheDO)o2).getFieldValue((Object)sort.getColumn(), colIndex, true);
                } else {
                    t1 = o1.get((Object)sort.getColumn());
                    t2 = o2.get((Object)sort.getColumn());
                }
                compare = t1 == null && t2 == null ? 0 : (t1 == null && t2 != null ? -1 : (t1 != null && t2 == null ? 1 : (t1 instanceof String ? comparator.compare(t1.toString(), t2.toString()) : (t1 instanceof Comparable ? ((Comparable)t1).compareTo((Comparable)t2) : comparator.compare(t1.toString(), t2.toString())))));
                if (compare != 0 && sort.getOrder() == BaseDataOption.OrderType.DESC) {
                    compare *= -1;
                }
                if (compare == 0) continue;
                break;
            }
            return compare;
        });
    }

    private void orderByOrdinal(List<BaseDataDO> dataList) {
        Collections.sort(dataList, (o1, o2) -> {
            if (o1.getOrdinal() == null && o2.getOrdinal() == null) {
                return 0;
            }
            if (o1.getOrdinal() == null && o2.getOrdinal() != null) {
                return -1;
            }
            if (o1.getOrdinal() != null && o2.getOrdinal() == null) {
                return 1;
            }
            return o1.getOrdinal().compareTo(o2.getOrdinal());
        });
    }

    private void orderByGroup(List<BaseDataDO> dataList, BaseDataDTO param) {
        HashMap<String, Integer> groupNameIndex;
        List groupNames;
        HashMap colIndexMap;
        String groupFieldName = param.getGroupFieldName();
        if (!StringUtils.hasText(groupFieldName)) {
            this.orderByOrdinal(dataList);
            return;
        }
        boolean canIndex = dataList.get(0) instanceof BaseDataCacheDO;
        HashMap hashMap = colIndexMap = canIndex ? new HashMap() : null;
        if (canIndex) {
            String tenantName = param.getTenantName();
            String tablename = param.getTableName().toUpperCase();
            Map indexMap = BaseDataContext.getColIndexMap((String)tenantName, (String)tablename);
            if (colIndexMap != null && indexMap != null) {
                colIndexMap.putAll(indexMap);
            }
        }
        boolean isMuiltiGroups = (groupNames = param.getGroupNames()) != null && groupNames.size() > 1;
        HashMap<String, Integer> hashMap2 = groupNameIndex = isMuiltiGroups ? new HashMap<String, Integer>() : null;
        if (isMuiltiGroups) {
            String groupName = null;
            for (int i = 0; i < groupNames.size(); ++i) {
                groupName = (String)groupNames.get(i);
                if (groupNameIndex.containsKey(groupName)) continue;
                groupNameIndex.putIfAbsent(groupName, i);
            }
        }
        String currGroupFieldName = groupFieldName.toLowerCase();
        int groupFieldIndex = !canIndex ? -1 : (colIndexMap != null && colIndexMap.containsKey(currGroupFieldName) ? (Integer)colIndexMap.get(currGroupFieldName) : -1);
        Collections.sort(dataList, (o1, o2) -> {
            String group2Str;
            String group1Str;
            Object group1 = null;
            Object group2 = null;
            if (canIndex && groupFieldIndex > -1) {
                group1 = ((BaseDataCacheDO)o1).getFieldValue((Object)currGroupFieldName, groupFieldIndex, true);
                group2 = ((BaseDataCacheDO)o2).getFieldValue((Object)currGroupFieldName, groupFieldIndex, true);
            } else {
                group1 = o1.get((Object)currGroupFieldName);
                group2 = o2.get((Object)currGroupFieldName);
            }
            if (group1 == null && group2 != null) {
                return -1;
            }
            if (group1 != null && group2 == null) {
                return 1;
            }
            if (group1 != null && group2 != null && !(group1Str = group1.toString()).equals(group2Str = group2.toString())) {
                int index2;
                int index1;
                int flag = 0;
                flag = isMuiltiGroups ? ((index1 = ((Integer)groupNameIndex.get(group1Str)).intValue()) > (index2 = ((Integer)groupNameIndex.get(group2Str)).intValue()) ? 1 : -1) : group1Str.compareTo(group2Str);
                return flag;
            }
            if (o1.getOrdinal() == null && o2.getOrdinal() == null) {
                return 0;
            }
            if (o1.getOrdinal() == null && o2.getOrdinal() != null) {
                return -1;
            }
            if (o1.getOrdinal() != null && o2.getOrdinal() == null) {
                return 1;
            }
            return o1.getOrdinal().compareTo(o2.getOrdinal());
        });
    }

    private void orderByTree(List<BaseDataDO> dataList, BaseDataDTO param) {
        if (StringUtils.hasText(param.getParentcode()) || StringUtils.hasText(param.getCode()) && BaseDataOption.QueryChildrenType.DIRECT_CHILDREN == param.getQueryChildrenType()) {
            this.orderByOrdinal(dataList);
            return;
        }
        HashMap<String, String> codeOrder = new HashMap<String, String>();
        BigDecimal order = null;
        for (BaseDataDO data : dataList) {
            order = data.getOrdinal();
            String orderVal = order == null ? "-" : String.format("%020f", order);
            codeOrder.put(data.getCode(), orderVal);
        }
        HashMap<String, String> endOrder = new HashMap<String, String>();
        String[] parents = null;
        StringBuilder sb = new StringBuilder();
        String pts = null;
        for (BaseDataDO data : dataList) {
            pts = data.getParents();
            if (!StringUtils.hasText(pts)) {
                endOrder.put(data.getObjectcode(), "-");
                continue;
            }
            if (sb.length() > 0) {
                sb.setLength(0);
            }
            for (String parentCode : parents = pts.split("\\/")) {
                if (codeOrder.containsKey(parentCode)) {
                    sb.append((String)codeOrder.get(parentCode)).append("#");
                    continue;
                }
                sb.append("-#");
            }
            endOrder.put(data.getObjectcode(), sb.toString());
        }
        Collections.sort(dataList, (o1, o2) -> ((String)endOrder.get(o1.getObjectcode())).compareTo((String)endOrder.get(o2.getObjectcode())));
    }
}

