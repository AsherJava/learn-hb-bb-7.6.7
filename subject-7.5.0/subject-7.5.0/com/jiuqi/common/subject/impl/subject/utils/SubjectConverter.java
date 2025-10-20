/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionManageService
 *  com.jiuqi.gcreport.dimension.vo.DimensionQueryVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.common.subject.impl.subject.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.subject.impl.subject.data.SubjectUtil;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.enums.OrientEnum;
import com.jiuqi.gcreport.dimension.internal.service.DimensionManageService;
import com.jiuqi.gcreport.dimension.vo.DimensionQueryVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubjectConverter {
    private final Map<String, BaseDataDO> generalTypeMap;
    private final Map<String, BaseDataDO> currencyMap;
    private final List<DimensionQueryVO> assistDimList;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SubjectConverter() {
        this.generalTypeMap = SubjectUtil.getGeneralTypeMap();
        this.currencyMap = SubjectUtil.getCurrencyMap();
        this.assistDimList = ((DimensionManageService)ApplicationContextRegister.getBean(DimensionManageService.class)).listDimensions();
    }

    public SubjectConverter(Map<String, BaseDataDO> generalTypeMap, Map<String, BaseDataDO> currencyMap, List<DimensionQueryVO> assistDimList) {
        this.generalTypeMap = generalTypeMap;
        this.currencyMap = currencyMap;
        this.assistDimList = assistDimList;
    }

    public SubjectDTO convert(BaseDataDO baseData) {
        SubjectDTO subject = new SubjectDTO();
        for (Map.Entry entry : baseData.entrySet()) {
            subject.put((String)entry.getKey(), entry.getValue());
        }
        this.convertGeneralType(subject);
        this.convertOrient(subject);
        this.convertAssistDim(subject);
        return subject;
    }

    private void convertGeneralType(SubjectDTO subject) {
        String generalType = subject.getGeneralType();
        if (this.generalTypeMap.get(generalType) == null) {
            subject.setGeneralTypeVo(generalType);
            return;
        }
        subject.setGeneralTypeVo(this.generalTypeMap.get(generalType).getName());
    }

    private void convertOrient(SubjectDTO subject) {
        Integer orient = subject.getOrient();
        if (orient == null) {
            subject.setOrientVo(null);
            return;
        }
        OrientEnum orientEnum = OrientEnum.fromCode(orient);
        if (orientEnum == null) {
            subject.setOrientVo(orient.toString());
            return;
        }
        subject.setOrientVo(orientEnum.getName());
    }

    private void convertAssistDim(SubjectDTO subject) {
        Map<String, Integer> assType = SubjectUtil.parseAssTypeMap(subject);
        if (assType == null || assType.isEmpty()) {
            subject.setAssTypeVo("");
            subject.setAssTypeMap(new HashMap<String, Integer>());
            subject.setAssTypeList(CollectionUtils.newArrayList());
            return;
        }
        StringBuilder assTypeVo = new StringBuilder();
        ArrayList<String> assTypeList = new ArrayList<String>(assType.entrySet().size());
        HashMap<String, Integer> assTypeMap = new HashMap<String, Integer>(assType.entrySet().size());
        for (DimensionQueryVO assistDim : this.assistDimList) {
            if (assType.get(assistDim.getCode()) == null) continue;
            assTypeVo.append(assistDim.getTitle()).append("/");
            assTypeList.add(assistDim.getCode());
            assTypeMap.put(assistDim.getCode(), assType.get(assistDim.getCode()));
        }
        if (assTypeVo.length() > 0) {
            subject.setAssTypeVo(assTypeVo.delete(assTypeVo.length() - 1, assTypeVo.length()).toString());
        }
        subject.setAssTypeList(assTypeList);
        subject.setAssTypeMap(assTypeMap);
    }
}

