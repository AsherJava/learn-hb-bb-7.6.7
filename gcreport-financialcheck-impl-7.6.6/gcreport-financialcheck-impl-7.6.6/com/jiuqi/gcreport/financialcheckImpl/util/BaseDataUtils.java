/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 */
package com.jiuqi.gcreport.financialcheckImpl.util;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BaseDataUtils {
    public static Map<String, String> getCode2ParentCodeMap(String tableName) {
        GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
        List baseDataList = tool.queryBasedataItems(tableName);
        Map<String, String> code2ParentCodeMap = baseDataList.stream().collect(Collectors.toMap(GcBaseData::getCode, item -> {
            if (item.getCode().equals(item.getParentid())) {
                return null;
            }
            return item.getParentid();
        }));
        return code2ParentCodeMap;
    }

    public static Map<String, List<String>> getSubjectCode2assTypeListMap() {
        GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
        List baseDataList = tool.queryBasedataItems("MD_ACCTSUBJECT");
        String regex = "\"(.+?)\".*?:.*?";
        Pattern pattern = Pattern.compile(regex);
        Map<String, List<String>> subjectCode2assTypeListMap = baseDataList.stream().collect(Collectors.toMap(GcBaseData::getCode, item -> {
            String assType = (String)item.getFieldVal("ASSTYPE");
            ArrayList<String> assTypeList = new ArrayList<String>();
            if (!StringUtils.isEmpty((String)assType)) {
                Matcher matcher = pattern.matcher(assType);
                while (matcher.find()) {
                    assTypeList.add(matcher.group(1).trim());
                }
            }
            return assTypeList;
        }, (o1, o2) -> o1));
        return subjectCode2assTypeListMap;
    }
}

