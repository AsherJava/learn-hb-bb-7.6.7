/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.financialcheckapi.offset.MdAgingDTO
 *  com.jiuqi.gcreport.financialcheckapi.offset.MdSubjectAgingDTO
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.financialcheckapi.offset.MdAgingDTO;
import com.jiuqi.gcreport.financialcheckapi.offset.MdSubjectAgingDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class MdAgeLoader {
    public static Map<String, MdSubjectAgingDTO> getCode2SubjectAgingMap(String taskPeriodCode) {
        GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
        Map<String, MdAgingDTO> code2MdAgingMap = MdAgeLoader.getCode2MdAgingMap();
        List subjectAgeList = tool.queryBasedataItems("MD_SUBJECT_AGING");
        HashMap<String, MdSubjectAgingDTO> code2SubjectAgingMap = new HashMap<String, MdSubjectAgingDTO>();
        for (GcBaseData subjectAge : subjectAgeList) {
            MdSubjectAgingDTO subjectAgingDTO = new MdSubjectAgingDTO();
            subjectAgingDTO.setCode(subjectAge.getCode());
            subjectAgingDTO.setTitle(subjectAge.getTitle());
            String subjectCode = (String)subjectAge.getFieldVal("SUBJECTCODE");
            subjectAgingDTO.setSubjectCode(subjectCode);
            List taskPeriodList = (List)subjectAge.getFieldVal("TASKPERIOD");
            boolean match = subjectAgingDTO.setAndMatchTaskPeriods(taskPeriodList, taskPeriodCode);
            if (!match) continue;
            String defaultAgingCode = (String)subjectAge.getFieldVal("DEFAULTVAL");
            subjectAgingDTO.setDefaultAgingCode(defaultAgingCode);
            List agingCodeList = (List)subjectAge.getFieldVal("AGINGCODE");
            List<MdAgingDTO> agingList = MdAgeLoader.getSortedAgingList(agingCodeList, code2MdAgingMap);
            subjectAgingDTO.setAgingList(agingList);
            code2SubjectAgingMap.put(subjectAgingDTO.getSubjectCode(), subjectAgingDTO);
        }
        return code2SubjectAgingMap;
    }

    @NotNull
    private static List<MdAgingDTO> getSortedAgingList(List<String> agingCodeList, Map<String, MdAgingDTO> code2MdAgingMap) {
        if (null == agingCodeList) {
            return Collections.emptyList();
        }
        ArrayList<MdAgingDTO> agingList = new ArrayList<MdAgingDTO>();
        for (String agingCode : agingCodeList) {
            MdAgingDTO mdAgingDTO = code2MdAgingMap.get(agingCode);
            if (null == mdAgingDTO) continue;
            agingList.add(mdAgingDTO);
        }
        MdAgeLoader.sort(agingList);
        return agingList;
    }

    private static void sort(List<MdAgingDTO> agingList) {
        agingList.sort((item1, item2) -> {
            Integer beginPeriod1 = item1.getBeginPeriod();
            if (beginPeriod1 == null) {
                return -1;
            }
            Integer beginPeriod2 = item2.getBeginPeriod();
            if (beginPeriod2 == null) {
                return 1;
            }
            return beginPeriod1 - beginPeriod2;
        });
    }

    private static Map<String, MdAgingDTO> getCode2MdAgingMap() {
        GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
        List ageList = tool.queryBasedataItems("MD_AGING");
        HashMap<String, MdAgingDTO> code2MdAgingMap = new HashMap<String, MdAgingDTO>();
        for (GcBaseData ageBaseData : ageList) {
            MdAgingDTO mdAgingDTO = new MdAgingDTO();
            mdAgingDTO.setCode(ageBaseData.getCode());
            mdAgingDTO.setTitle(ageBaseData.getTitle());
            Integer beginPeriod = ConverterUtils.getAsInteger((Object)ageBaseData.getFieldVal("BEGINPERIOD"));
            mdAgingDTO.setBeginPeriod(beginPeriod);
            Integer endPeriod = ConverterUtils.getAsInteger((Object)ageBaseData.getFieldVal("ENDPERIOD"));
            mdAgingDTO.setEndPeriod(endPeriod);
            String periodType = (String)ageBaseData.getFieldVal("PERIODTYPE");
            Assert.isNotEmpty((String)periodType, (String)"\u8d26\u9f84MD_AGING\u57fa\u7840\u6570\u636e\u7684\u533a\u95f4\u7c7b\u578b\u5b57\u6bb5\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            mdAgingDTO.setPeriodType(periodType);
            code2MdAgingMap.put(mdAgingDTO.getCode(), mdAgingDTO);
        }
        return code2MdAgingMap;
    }

    @Deprecated
    public static Map<String, MdSubjectAgingDTO> getSubjectCode2SubjectAgingMap(String periodType) {
        GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
        Map<String, MdAgingDTO> code2MdAgingMap = MdAgeLoader.getCode2MdAgingMap();
        HashMap<String, MdSubjectAgingDTO> subjectCode2SubjectAgingMap = new HashMap<String, MdSubjectAgingDTO>();
        List subjectAgeList = tool.queryBasedataItems("MD_SUBJECT_AGING");
        if (periodType == null) {
            return subjectCode2SubjectAgingMap;
        }
        for (GcBaseData gcBaseData : subjectAgeList) {
            MdSubjectAgingDTO subjectAgingDTO = new MdSubjectAgingDTO();
            subjectAgingDTO.setCode(gcBaseData.getCode());
            subjectAgingDTO.setTitle(gcBaseData.getTitle());
            String subjectCode = (String)gcBaseData.getFieldVal("SUBJECTCODE");
            subjectAgingDTO.setSubjectCode(subjectCode);
            String defaultAgingCode = (String)gcBaseData.getFieldVal("DEFAULTVAL");
            subjectAgingDTO.setDefaultAgingCode(defaultAgingCode);
            List agingCodeList = (List)gcBaseData.getFieldVal("AGINGCODE");
            List agingDTOList = agingCodeList.stream().map(code2MdAgingMap::get).filter(Objects::nonNull).filter(x -> periodType.equals(x.getPeriodType())).collect(Collectors.toList());
            subjectAgingDTO.setAgingList(agingDTOList);
            subjectCode2SubjectAgingMap.put(subjectAgingDTO.getSubjectCode(), subjectAgingDTO);
        }
        return subjectCode2SubjectAgingMap;
    }
}

