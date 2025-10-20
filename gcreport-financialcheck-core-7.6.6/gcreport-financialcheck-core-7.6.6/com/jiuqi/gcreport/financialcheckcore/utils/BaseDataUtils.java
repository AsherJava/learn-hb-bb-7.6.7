/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 */
package com.jiuqi.gcreport.financialcheckcore.utils;

import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class BaseDataUtils {
    public static Set<String> getAllChildrenContainSelfByCodes(String tableName, Collection<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return Collections.emptySet();
        }
        HashSet<String> allCodes = new HashSet<String>();
        codes.forEach(code -> allCodes.addAll(BaseDataUtils.getAllChildrenContainSelf(tableName, code)));
        return allCodes;
    }

    public static List<String> getAllChildrenContainSelf(String tableName, String subjectCode) {
        ArrayList<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(subjectCode)) {
            return list;
        }
        list.add(subjectCode);
        list.addAll(BaseDataUtils.queryAllCodesByParentid(tableName, subjectCode));
        return list;
    }

    private static List<String> queryAllCodesByParentid(String tableName, String entityKeyData) {
        ArrayList<String> allCodes = new ArrayList<String>();
        List allRows = GcBaseDataCenterTool.getInstance().queryAllBasedataItemsByParentid(tableName, entityKeyData);
        for (GcBaseData iEntityRow : allRows) {
            allCodes.add(iEntityRow.getCode());
        }
        return allCodes;
    }

    public static List<String> getBaseDataCodeOnlyParent(List<String> codes, String tableName) {
        HashSet<String> baseDataCodes = new HashSet<String>();
        if (CollectionUtils.isEmpty(codes)) {
            return new ArrayList<String>(baseDataCodes);
        }
        baseDataCodes.addAll(codes);
        ArrayList removedCodes = new ArrayList();
        codes.forEach(subCode -> {
            if (removedCodes.contains(subCode)) {
                return;
            }
            List<String> childrenAndSelfCodes = BaseDataUtils.queryAllCodesByParentid(tableName, subCode);
            ArrayList thisTimeNeedRemovedCodes = new ArrayList();
            childrenAndSelfCodes.forEach(code -> {
                if (!code.equals(subCode) && baseDataCodes.contains(code)) {
                    thisTimeNeedRemovedCodes.add(code);
                }
            });
            removedCodes.addAll(thisTimeNeedRemovedCodes);
            thisTimeNeedRemovedCodes.forEach(baseDataCodes::remove);
        });
        return new ArrayList<String>(baseDataCodes);
    }

    public static List<GcBaseData> listOneLevelBaseData(String tableName, List<String> codes) {
        List allOneLevelBaseData = GcBaseDataCenterTool.getInstance().queryBasedataItemsByParentid(tableName, "-");
        Map allOneLevelBaseDataMap = allOneLevelBaseData.stream().collect(Collectors.toMap(GcBaseData::getCode, Function.identity()));
        LinkedHashSet allRows = new LinkedHashSet();
        codes.forEach(code -> {
            GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode(tableName, code);
            if (gcBaseData != null) {
                String parents = gcBaseData.getParents();
                if (parents.contains("/")) {
                    String[] parentCodes = parents.split("/");
                    String oneLevelParentCode = parentCodes[0];
                    if (allOneLevelBaseDataMap.containsKey(oneLevelParentCode)) {
                        allRows.add(allOneLevelBaseDataMap.get(oneLevelParentCode));
                    }
                } else {
                    allRows.add(gcBaseData);
                }
            }
        });
        return new ArrayList<GcBaseData>(allRows);
    }
}

