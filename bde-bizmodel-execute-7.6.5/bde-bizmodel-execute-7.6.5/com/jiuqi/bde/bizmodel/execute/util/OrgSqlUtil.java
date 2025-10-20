/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 */
package com.jiuqi.bde.bizmodel.execute.util;

import com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class OrgSqlUtil {
    public static UnitAndBookValue getUnitAndBookValue(OrgMappingDTO orgMappingDTO) {
        HashSet<String> unitCodes = new HashSet<String>();
        HashSet<String> bookCodes = new HashSet<String>();
        unitCodes.add(orgMappingDTO.getAcctOrgCode());
        bookCodes.add(orgMappingDTO.getAcctBookCode());
        if (!CollectionUtils.isEmpty((Collection)orgMappingDTO.getOrgMappingItems())) {
            unitCodes.addAll(orgMappingDTO.getOrgMappingItems().stream().map(OrgMappingItem::getAcctOrgCode).collect(Collectors.toList()));
            bookCodes.addAll(orgMappingDTO.getOrgMappingItems().stream().map(OrgMappingItem::getAcctBookCode).collect(Collectors.toList()));
        }
        return new UnitAndBookValue(new ArrayList<String>(unitCodes), new ArrayList<String>(bookCodes));
    }

    public static String getOrgConditionSql(String field, List<String> values) {
        if (CollectionUtils.isEmpty(values)) {
            return "";
        }
        return " AND " + SqlBuildUtil.getStrInCondi((String)field, values);
    }
}

