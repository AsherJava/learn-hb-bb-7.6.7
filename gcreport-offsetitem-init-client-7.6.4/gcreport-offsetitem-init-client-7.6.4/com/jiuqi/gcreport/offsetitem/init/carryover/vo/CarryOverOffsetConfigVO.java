/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.vo;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetRuleVO;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectMappingVO;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CarryOverOffsetConfigVO {
    private List<CarryOverOffsetRuleVO> carryOverRuleVos = new ArrayList<CarryOverOffsetRuleVO>();
    private Boolean carryOverConformRuleAdjustOffsets = false;
    private List<CarryOverOffsetSubjectVO> carryOverSubjectVos = new ArrayList<CarryOverOffsetSubjectVO>();
    private CarryOverOffsetSubjectVO undistributedProfitSubjectVo;
    private Map<String, List<CarryOverOffsetSubjectMappingVO>> subjectMappingSet = new HashMap<String, List<CarryOverOffsetSubjectMappingVO>>();
    private List<CarryOverOffsetRuleVO> carryOverSumRuleIds = new ArrayList<CarryOverOffsetRuleVO>();
    private List<String> carryOverSumColumns = new ArrayList<String>();

    public List<CarryOverOffsetRuleVO> getCarryOverRuleVos() {
        return this.carryOverRuleVos;
    }

    public void setCarryOverRuleVos(List<CarryOverOffsetRuleVO> carryOverRuleVos) {
        this.carryOverRuleVos = carryOverRuleVos;
    }

    public Boolean getCarryOverConformRuleAdjustOffsets() {
        return this.carryOverConformRuleAdjustOffsets;
    }

    public void setCarryOverConformRuleAdjustOffsets(Boolean carryOverConformRuleAdjustOffsets) {
        this.carryOverConformRuleAdjustOffsets = carryOverConformRuleAdjustOffsets;
    }

    public List<CarryOverOffsetSubjectVO> getCarryOverSubjectVos() {
        return this.carryOverSubjectVos;
    }

    public void setCarryOverSubjectVos(List<CarryOverOffsetSubjectVO> carryOverSubjectVos) {
        this.carryOverSubjectVos = carryOverSubjectVos;
    }

    public CarryOverOffsetSubjectVO getUndistributedProfitSubjectVo() {
        return this.undistributedProfitSubjectVo;
    }

    public void setUndistributedProfitSubjectVo(CarryOverOffsetSubjectVO undistributedProfitSubjectVo) {
        this.undistributedProfitSubjectVo = undistributedProfitSubjectVo;
    }

    public Map<String, List<CarryOverOffsetSubjectMappingVO>> getSubjectMappingSet() {
        return this.subjectMappingSet;
    }

    public Map<String, String> getSubjectMappingSetByDestSystemId(String destSystemId) {
        if (StringUtils.isEmpty((String)destSystemId)) {
            return Collections.emptyMap();
        }
        if (!this.subjectMappingSet.containsKey(destSystemId)) {
            return Collections.emptyMap();
        }
        List<CarryOverOffsetSubjectMappingVO> carryOverOffsetSubjectMappingVOS = this.subjectMappingSet.get(destSystemId);
        if (carryOverOffsetSubjectMappingVOS == null) {
            return Collections.emptyMap();
        }
        TreeMap<String, String> map = new TreeMap<String, String>((key1, key2) -> {
            int lengthCompare = Integer.compare(key1.length(), key2.length());
            if (lengthCompare == 0) {
                return key1.compareTo((String)key2);
            }
            return lengthCompare;
        });
        carryOverOffsetSubjectMappingVOS.forEach(mappingVO -> map.put(mappingVO.getSrcSubjectCode(), mappingVO.getDestSubjectCode()));
        return map;
    }

    public void setSubjectMappingSet(Map<String, List<CarryOverOffsetSubjectMappingVO>> subjectMappingSet) {
        this.subjectMappingSet = subjectMappingSet;
    }

    public List<CarryOverOffsetRuleVO> getCarryOverSumRuleIds() {
        return this.carryOverSumRuleIds;
    }

    public void setCarryOverSumRuleIds(List<CarryOverOffsetRuleVO> carryOverSumRuleIds) {
        this.carryOverSumRuleIds = carryOverSumRuleIds;
    }

    public List<String> getCarryOverSumColumns() {
        return this.carryOverSumColumns;
    }

    public void setCarryOverSumColumns(List<String> carryOverSumColumns) {
        this.carryOverSumColumns = carryOverSumColumns;
    }
}

