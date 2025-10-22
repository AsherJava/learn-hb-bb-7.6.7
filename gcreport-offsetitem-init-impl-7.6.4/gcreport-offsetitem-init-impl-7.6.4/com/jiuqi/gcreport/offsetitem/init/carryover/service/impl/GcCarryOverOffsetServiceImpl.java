/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetConfigVO
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectMappingVO
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.offsetitem.init.carryover.service.GcCarryOverOffsetService;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetConfigVO;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectMappingVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcCarryOverOffsetServiceImpl
implements GcCarryOverOffsetService {
    private static final Logger logger = LoggerFactory.getLogger(GcCarryOverOffsetServiceImpl.class);
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;

    @Override
    public String checkSubjectMapping(String systemId, Map<String, List<CarryOverOffsetSubjectMappingVO>> mappingVOS) {
        CarryOverOffsetConfigVO configVO = new CarryOverOffsetConfigVO();
        configVO.setSubjectMappingSet(mappingVOS);
        HashSet<String> invalidCodes = new HashSet<String>();
        List allSubjectBySystemId = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        Map<String, List<String>> codeToParentCodesMap = this.getCodeToParentCodesMap(allSubjectBySystemId);
        for (String destSystemId : mappingVOS.keySet()) {
            Map subjectMapping = configVO.getSubjectMappingSetByDestSystemId(destSystemId);
            invalidCodes.addAll(this.getInvalidCodes(codeToParentCodesMap, subjectMapping));
        }
        if (CollectionUtils.isEmpty(invalidCodes)) {
            return "";
        }
        String result = invalidCodes.stream().collect(Collectors.joining("\uff0c"));
        return "\u6e90\u79d1\u76ee\u4ee3\u7801\u914d\u7f6e\u91cd\u590d\uff0c\u8bf7\u52ff\u540c\u65f6\u914d\u7f6e\u975e\u672b\u7ea7\u79d1\u76ee\u548c\u672b\u7ea7\u79d1\u76ee\uff0c\u8bf7\u68c0\u67e5\u6e90\u79d1\u76ee\u914d\u7f6e\uff1a" + result;
    }

    private Set<String> getInvalidCodes(Map<String, List<String>> codeToParentCodesMap, Map<String, String> codeToMappingCode) {
        HashSet<String> invalidCodes = new HashSet<String>();
        for (String code : codeToMappingCode.keySet()) {
            this.hasConflictAndAddConflicts(code, codeToParentCodesMap, codeToMappingCode, invalidCodes);
        }
        return invalidCodes;
    }

    private void hasConflictAndAddConflicts(String code, Map<String, List<String>> codeToParentCodesMap, Map<String, String> codeToMappingCode, Set<String> invalidCodes) {
        boolean hasConflict = false;
        List<String> parentIds = codeToParentCodesMap.get(code);
        for (String parentId : parentIds) {
            if (!codeToMappingCode.containsKey(parentId)) continue;
            invalidCodes.add(parentId);
            hasConflict = true;
        }
        if (hasConflict) {
            invalidCodes.add(code);
        }
    }

    private Map<String, List<String>> getCodeToParentCodesMap(List<ConsolidatedSubjectEO> nodes) {
        HashMap<String, ConsolidatedSubjectEO> codeToNodeMap = new HashMap<String, ConsolidatedSubjectEO>();
        for (ConsolidatedSubjectEO node : nodes) {
            codeToNodeMap.put(node.getCode(), node);
        }
        HashMap<String, List<String>> codeToParentsMap = new HashMap<String, List<String>>();
        for (ConsolidatedSubjectEO node : nodes) {
            List<String> parents = this.findParents(node.getCode(), codeToNodeMap);
            codeToParentsMap.put(node.getCode(), parents);
        }
        return codeToParentsMap;
    }

    private List<String> findParents(String code, Map<String, ConsolidatedSubjectEO> codeToNodeMap) {
        ArrayList<String> parents = new ArrayList<String>();
        ConsolidatedSubjectEO current = codeToNodeMap.get(code);
        while (current != null && current.getParentCode() != null && !current.getParentCode().equals("-")) {
            parents.add(current.getParentCode());
            current = codeToNodeMap.get(current.getParentCode());
        }
        return parents;
    }
}

