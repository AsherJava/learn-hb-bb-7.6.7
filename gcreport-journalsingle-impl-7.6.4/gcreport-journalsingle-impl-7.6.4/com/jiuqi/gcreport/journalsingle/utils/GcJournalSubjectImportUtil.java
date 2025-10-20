/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.ImportUtils
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONException
 */
package com.jiuqi.gcreport.journalsingle.utils;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.ImportUtils;
import com.jiuqi.gcreport.journalsingle.dao.IJournalSubjectDao;
import com.jiuqi.gcreport.journalsingle.entity.JournalSingleSchemeExcelModel;
import com.jiuqi.gcreport.journalsingle.entity.JournalSubjectEO;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONException;

public class GcJournalSubjectImportUtil
extends ImportUtils {
    public static void parse(List<JournalSingleSchemeExcelModel> excelModels, String jRelateSchemeId, StringBuffer log) throws JSONException {
        List<JournalSubjectEO> subjectEOS = GcJournalSubjectImportUtil.parseContent(excelModels, jRelateSchemeId, log);
        GcJournalSubjectImportUtil.saveData(subjectEOS, jRelateSchemeId, log);
        int successCount = subjectEOS.size();
        String logInfo = "\u5bfc\u5165\u5b8c\u6210\uff1a\u603b\u5171%1$s\u884c,\u6210\u529f%2$s\u884c\uff0c\u5931\u8d25%3$s\u884c \n";
        log.append(String.format(logInfo, String.valueOf(excelModels.size()), String.valueOf(successCount), String.valueOf(excelModels.size() - successCount)));
    }

    private static void saveData(List<JournalSubjectEO> subjectEOS, String jRelateSchemeId, StringBuffer log) {
        Map<String, JournalSubjectEO> subjectCode2SubjectEoMap = GcJournalSubjectImportUtil.subjectCode2SubjectObjectMapFromDb(jRelateSchemeId);
        HashSet<String> repetitionSubjectCode = new HashSet<String>();
        GcJournalSubjectImportUtil.removeBadSubjectFromData(subjectEOS, subjectCode2SubjectEoMap, repetitionSubjectCode, log);
        GcJournalSubjectImportUtil.repairParentId(subjectEOS, subjectCode2SubjectEoMap, jRelateSchemeId);
        GcJournalSubjectImportUtil.repairParents(subjectEOS, subjectCode2SubjectEoMap, jRelateSchemeId);
        if (repetitionSubjectCode.size() > 0) {
            ((IJournalSubjectDao)SpringContextUtils.getBean(IJournalSubjectDao.class)).deleteSubjectBySchemeIdAndSubjectCode(jRelateSchemeId, repetitionSubjectCode);
        }
        ((IJournalSubjectDao)SpringContextUtils.getBean(IJournalSubjectDao.class)).saveAll(subjectEOS);
    }

    private static void repairParents(List<JournalSubjectEO> subjectEOS, Map<String, JournalSubjectEO> subjectCode2SubjectEoMap, String jRelateSchemeId) {
        List<JournalSubjectEO> emptyParentsSubjects = subjectEOS.stream().filter(subjectEO -> StringUtils.isEmpty((String)subjectEO.getParents())).collect(Collectors.toList());
        if (emptyParentsSubjects.isEmpty()) {
            return;
        }
        HashMap<String, JournalSubjectEO> subjectId2SubjectEoMap = new HashMap<String, JournalSubjectEO>();
        for (JournalSubjectEO subjectEO2 : subjectCode2SubjectEoMap.values()) {
            subjectId2SubjectEoMap.put(subjectEO2.getId(), subjectEO2);
        }
        GcJournalSubjectImportUtil.recursiveRepairParents(emptyParentsSubjects, subjectId2SubjectEoMap);
        for (JournalSubjectEO subjectEO2 : emptyParentsSubjects) {
            if (!StringUtils.isEmpty((String)subjectEO2.getParents())) continue;
            subjectEO2.setParents(jRelateSchemeId + "/" + subjectEO2.getId());
        }
    }

    private static void recursiveRepairParents(List<JournalSubjectEO> emptyParentsSubjects, Map<String, JournalSubjectEO> subjectId2SubjectEoMap) {
        ArrayList<JournalSubjectEO> successSubjectEOS = new ArrayList<JournalSubjectEO>();
        for (JournalSubjectEO subjectEO : emptyParentsSubjects) {
            JournalSubjectEO parentSubjectEO;
            if (!subjectId2SubjectEoMap.containsKey(subjectEO.getId()) || !StringUtils.isNotEmpty((String)(parentSubjectEO = subjectId2SubjectEoMap.get(subjectEO.getId())).getParents())) continue;
            successSubjectEOS.add(subjectEO);
            subjectEO.setParents(parentSubjectEO.getParents() + "/" + subjectEO.getId());
        }
        if (!successSubjectEOS.isEmpty()) {
            emptyParentsSubjects.removeAll(successSubjectEOS);
            GcJournalSubjectImportUtil.recursiveRepairParents(emptyParentsSubjects, subjectId2SubjectEoMap);
        }
    }

    private static void repairParentId(List<JournalSubjectEO> subjectEOS, Map<String, JournalSubjectEO> subjectCode2SubjectEoMap, String jRelateSchemeId) {
        Integer[] subjectCodeStructureArr = GcJournalSubjectImportUtil.getSubjectCodeStructureArr();
        for (JournalSubjectEO subjectEO : subjectEOS) {
            String parentCode;
            if (subjectCode2SubjectEoMap.containsKey(subjectEO.getCode())) {
                subjectEO.setId(subjectCode2SubjectEoMap.get(subjectEO.getCode()).getId());
            }
            if (!StringUtils.isEmpty((String)(parentCode = GcJournalSubjectImportUtil.getParentCodeBySubjectCode(subjectEO.getCode(), subjectCodeStructureArr))) && subjectCode2SubjectEoMap.containsKey(parentCode)) {
                JournalSubjectEO parentSubjectEO = subjectCode2SubjectEoMap.get(parentCode);
                subjectEO.setParentId(parentSubjectEO.getId());
                if (!StringUtils.isNotEmpty((String)parentSubjectEO.getParents())) continue;
                subjectEO.setParents(parentSubjectEO.getParents() + "/" + subjectEO.getId());
                continue;
            }
            subjectEO.setParentId(jRelateSchemeId);
            subjectEO.setParents(jRelateSchemeId + "/" + subjectEO.getId());
        }
    }

    private static String getParentCodeBySubjectCode(String subjectCode, Integer[] subjectCodeStructureArr) {
        int i;
        if (StringUtils.isEmpty((String)subjectCode)) {
            return null;
        }
        int subjectCodeLength = subjectCode.length();
        for (i = 0; i < subjectCodeStructureArr.length; ++i) {
            if (subjectCodeStructureArr[i] < subjectCodeLength) continue;
            --i;
            break;
        }
        if (i >= 0 && subjectCodeLength > subjectCodeStructureArr[i]) {
            return subjectCode.substring(0, subjectCodeStructureArr[i]);
        }
        return null;
    }

    private String getParentCodeLength(String subjectCode, Integer[] subjectCodeStructureArr) {
        if (StringUtils.isEmpty((String)subjectCode)) {
            return null;
        }
        int subjectCodeLength = subjectCode.length();
        for (int len = 0; len < subjectCodeStructureArr.length; ++len) {
        }
        return null;
    }

    private static Integer[] getSubjectCodeStructureArr() {
        String[] subjectCodeStructures = GcJournalSubjectImportUtil.getSubjectCodeStructure().split(":");
        ArrayList<Integer> subjectCodeGoodLengthList = new ArrayList<Integer>(6);
        int goodLength = 0;
        for (String subjectCodeStructure : subjectCodeStructures) {
            subjectCodeGoodLengthList.add(goodLength += Integer.parseInt(subjectCodeStructure));
        }
        return subjectCodeGoodLengthList.toArray(new Integer[0]);
    }

    public static Set<Integer> getSubjectCodeStructureSet() {
        String[] subjectCodeStructures = GcJournalSubjectImportUtil.getSubjectCodeStructure().split(":");
        HashSet<Integer> subjectCodeGoodLengthSet = new HashSet<Integer>(6);
        int goodLength = 0;
        for (String subjectCodeStructure : subjectCodeStructures) {
            subjectCodeGoodLengthSet.add(goodLength += Integer.parseInt(subjectCodeStructure));
        }
        return subjectCodeGoodLengthSet;
    }

    public static String getSubjectCodeStructure() {
        return "2:4:2:2:2:2";
    }

    private static void removeBadSubjectFromData(List<JournalSubjectEO> subjectEOS, Map<String, JournalSubjectEO> subjectCode2SubjectIdMap, Set<String> repetitionSubjectCodeSet, StringBuffer log) {
        ArrayList<JournalSubjectEO> needRemovedSubjectEOS = new ArrayList<JournalSubjectEO>();
        Set<Integer> subjectCodeStructureSet = GcJournalSubjectImportUtil.getSubjectCodeStructureSet();
        int badSubjectLengthCount = 0;
        HashMap<String, ArrayList<JournalSubjectEO>> doubleSubjectMap = new HashMap<String, ArrayList<JournalSubjectEO>>();
        for (JournalSubjectEO subjectEO : subjectEOS) {
            boolean badSubjectLength;
            boolean doubleSubjectCode = subjectCode2SubjectIdMap.containsKey(subjectEO.getCode());
            boolean bl = badSubjectLength = !subjectCodeStructureSet.contains(subjectEO.getCode().length());
            if (badSubjectLength) {
                ++badSubjectLengthCount;
                needRemovedSubjectEOS.add(subjectEO);
                continue;
            }
            if (doubleSubjectCode) {
                ArrayList<JournalSubjectEO> doubleSubjectList = (ArrayList<JournalSubjectEO>)doubleSubjectMap.get(subjectEO.getCode());
                if (doubleSubjectList == null) {
                    doubleSubjectList = new ArrayList<JournalSubjectEO>();
                }
                doubleSubjectList.add(subjectEO);
                doubleSubjectMap.put(subjectEO.getCode(), doubleSubjectList);
                continue;
            }
            subjectCode2SubjectIdMap.put(subjectEO.getCode(), subjectEO);
        }
        if (badSubjectLengthCount > 0) {
            log.append(badSubjectLengthCount).append("\u884c\u9879\u76ee\u7f16\u7801\u4e0d\u6ee1\u8db3\u6b64\u7ed3\u6784\uff1a'" + GcJournalSubjectImportUtil.getSubjectCodeStructure() + "'<br>");
        }
        repetitionSubjectCodeSet.addAll(doubleSubjectMap.keySet());
        for (String repetitionSubjectCode : repetitionSubjectCodeSet) {
            List doubleSubjectList = (List)doubleSubjectMap.get(repetitionSubjectCode);
            subjectEOS.removeAll(doubleSubjectList);
            subjectEOS.add((JournalSubjectEO)((Object)doubleSubjectList.get(doubleSubjectList.size() - 1)));
        }
        subjectEOS.removeAll(needRemovedSubjectEOS);
    }

    private static Map<String, JournalSubjectEO> subjectCode2SubjectObjectMapFromDb(String jRelateSchemeId) {
        IJournalSubjectDao subjectDao = (IJournalSubjectDao)SpringContextUtils.getBean(IJournalSubjectDao.class);
        List<JournalSubjectEO> allSubjects = subjectDao.listAllSubjects(jRelateSchemeId);
        HashMap<String, JournalSubjectEO> subjectCode2SubjectEoMap = new HashMap<String, JournalSubjectEO>(32);
        for (JournalSubjectEO subjectEO : allSubjects) {
            subjectCode2SubjectEoMap.put(subjectEO.getCode(), subjectEO);
        }
        return subjectCode2SubjectEoMap;
    }

    private static List<JournalSubjectEO> parseContent(List<JournalSingleSchemeExcelModel> excelModels, String jRelateSchemeId, StringBuffer log) {
        ArrayList<JournalSubjectEO> subjectEOS = new ArrayList<JournalSubjectEO>();
        for (int i = 0; i < excelModels.size(); ++i) {
            JournalSingleSchemeExcelModel row = excelModels.get(i);
            JournalSubjectEO subjectEO = GcJournalSubjectImportUtil.initJournalSubject(jRelateSchemeId);
            String failReason = GcJournalSubjectImportUtil.oneRow(row, subjectEO);
            String lossImportantValue = GcJournalSubjectImportUtil.lossImportantValue(subjectEO);
            if (null != lossImportantValue) {
                log.append("excel\u884c\u53f7\u7b2c").append(i + 1).append("\u884c\u5bf9\u5e94\u6570\u636e\u51fa\u9519\uff1a").append(lossImportantValue).append("\n");
                continue;
            }
            subjectEOS.add(subjectEO);
            if (null == failReason) continue;
            log.append("excel\u884c\u53f7\u7b2c").append(i + 1).append("\u884c\u5bf9\u5e94\u6570\u636e\u51fa\u9519\uff1a").append(failReason).append("\n");
        }
        return subjectEOS;
    }

    private static String lossImportantValue(JournalSubjectEO subjectEO) {
        if (StringUtils.isEmpty((String)subjectEO.getCode())) {
            return "\u9879\u76ee\u4ee3\u7801\u4e3a\u7a7a";
        }
        return null;
    }

    private static JournalSubjectEO initJournalSubject(String jRelateSchemeId) {
        JournalSubjectEO subjectEO = new JournalSubjectEO();
        subjectEO.setOrient(OrientEnum.D.getValue());
        subjectEO.setId(UUIDUtils.newUUIDStr());
        subjectEO.setNeedShow(0);
        subjectEO.setCreateTime(new Date());
        subjectEO.setjRelateSchemeId(jRelateSchemeId);
        subjectEO.setSortOrder(OrderGenerator.newOrder());
        return subjectEO;
    }

    private static String oneRow(JournalSingleSchemeExcelModel row, JournalSubjectEO subjectEO) {
        subjectEO.setCode(row.getCode());
        subjectEO.setTitle(row.getTitle());
        int orient = "\u8d37".equals(row.getOrient()) ? OrientEnum.C.getValue() : OrientEnum.D.getValue();
        subjectEO.setOrient(orient);
        String beforeZbCode = row.getBeforeZbCode();
        if (!GcJournalSubjectImportUtil.setBeforeZbCode(subjectEO, beforeZbCode)) {
            return "\u5173\u8054\u8c03\u6574\u524d\u6307\u6807\u672a\u627e\u89c1:" + beforeZbCode;
        }
        String afterZbCode = row.getAfterZbCode();
        if (!GcJournalSubjectImportUtil.setAfterZbCode(subjectEO, afterZbCode)) {
            return "\u5173\u8054\u8c03\u6574\u540e\u6307\u6807\u672a\u627e\u89c1:" + afterZbCode;
        }
        boolean needShow = "\u662f".equals(row.getNeedShow().trim());
        subjectEO.setNeedShow(needShow ? 1 : 0);
        return null;
    }

    private static boolean setBeforeZbCode(JournalSubjectEO subjectEO, String beforeZbCode) {
        subjectEO.setBeforeZbCode(beforeZbCode);
        if (StringUtils.isEmpty((String)beforeZbCode)) {
            return true;
        }
        subjectEO.repairBeforeZbInfo();
        return beforeZbCode.equals(subjectEO.getBeforeZbCode());
    }

    private static boolean setAfterZbCode(JournalSubjectEO subjectEO, String afterZbCode) {
        subjectEO.setAfterZbCode(afterZbCode);
        if (StringUtils.isEmpty((String)afterZbCode)) {
            return true;
        }
        subjectEO.repairAfterZbInfo();
        return afterZbCode.equals(subjectEO.getAfterZbCode());
    }
}

