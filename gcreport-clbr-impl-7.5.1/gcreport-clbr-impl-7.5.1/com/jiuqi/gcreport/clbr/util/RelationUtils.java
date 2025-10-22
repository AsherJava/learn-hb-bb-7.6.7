/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 */
package com.jiuqi.gcreport.clbr.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.clbr.dao.temptable.ClbrIdRealTempDao;
import com.jiuqi.gcreport.clbr.dao.temptable.TempUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RelationUtils {
    private static final GcBaseDataService gcBaseDataService = GcBaseDataCenterTool.getInstance().getBasedataService();

    private RelationUtils() {
    }

    public static List<String> getUnitCodeOnlyParent(List<String> relations) {
        ArrayList<String> allCodes = new ArrayList<String>();
        ArrayList childCodes = new ArrayList();
        for (String relation : relations) {
            List allGcBaseData = gcBaseDataService.queryAllWithSelfItemsByParentid("MD_RELATION", relation, AuthType.NONE);
            allCodes.addAll(allGcBaseData.stream().map(GcBaseData::getObjectCode).collect(Collectors.toSet()));
            List childGcBaseData = gcBaseDataService.queryAllBasedataItemsByParentid("MD_RELATION", relation, AuthType.NONE);
            childCodes.addAll(childGcBaseData.stream().map(GcBaseData::getObjectCode).collect(Collectors.toSet()));
        }
        allCodes.removeAll(childCodes);
        return allCodes;
    }

    public static List<String> queryAllItems(List<String> relations) {
        HashSet allItems = new HashSet();
        for (String str : relations) {
            List gcBaseDataList = gcBaseDataService.queryAllWithSelfItemsByParentid("MD_RELATION", str, AuthType.NONE);
            allItems.addAll(gcBaseDataList.stream().map(GcBaseData::getObjectCode).collect(Collectors.toList()));
        }
        return new ArrayList<String>(allItems);
    }

    public static TempTableCondition getTempTableCondition(ClbrIdRealTempDao clbrIdRealTempDao, String code, AuthType authType) {
        List baseDatas;
        Set<String> codes = null;
        if (StringUtils.isEmpty((String)code) && !CollectionUtils.isEmpty((Collection)(baseDatas = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_RELATION", authType)))) {
            codes = baseDatas.stream().map(GcBaseData::getCode).collect(Collectors.toSet());
            return TempUtils.getTempCond(clbrIdRealTempDao, codes, " ");
        }
        GcBaseData baseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", code);
        if (null == baseData) {
            return null;
        }
        List baseDatas2 = GcBaseDataCenterTool.getInstance().queryAllWithSelfItemsByParentid("MD_RELATION", code, authType);
        if (!CollectionUtils.isEmpty((Collection)baseDatas2)) {
            codes = baseDatas2.stream().map(GcBaseData::getCode).collect(Collectors.toSet());
            return TempUtils.getTempCond(clbrIdRealTempDao, codes, " ");
        }
        return null;
    }
}

