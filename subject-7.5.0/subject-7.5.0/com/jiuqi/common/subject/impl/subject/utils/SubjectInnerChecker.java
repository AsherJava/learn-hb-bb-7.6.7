/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.common.subject.impl.subject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.data.SubjectChecker;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.enums.OrientEnum;
import com.jiuqi.common.subject.impl.subject.exception.CheckRuntimeException;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectInnerChecker
implements SubjectChecker {
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private DimensionService dimensionService;

    @Override
    public void doCreateCheck(SubjectDTO dto) {
        this.check(dto);
    }

    @Override
    public void doModifyCheck(SubjectDTO dto) {
        this.check(dto);
    }

    @Override
    public void doDeleteCheck(SubjectDTO dto) {
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    public void check(SubjectDTO dto) {
        if (StringUtils.isEmpty((String)dto.getCode())) {
            throw new CheckRuntimeException("\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)dto.getName())) {
            throw new CheckRuntimeException("\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)dto.getParentcode())) {
            throw new CheckRuntimeException("\u7236\u7ea7\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        }
        this.checkGeneralType(dto);
        this.checkOrient(dto);
        this.checkAssType(dto);
        if (!"-".equals(dto.getParentcode())) {
            BaseDataDTO param = new BaseDataDTO();
            param.setTableName("MD_ACCTSUBJECT");
            param.setCode(dto.getParentcode());
            param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
            PageVO parentVo = this.baseDataService.list(param);
            if (CollectionUtils.isEmpty((Collection)parentVo.getRows())) {
                throw new CheckRuntimeException(String.format("\u7236\u7ea7\u4ee3\u7801\u3010%s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", dto.getParentcode()));
            }
        }
        if (!StringUtils.isEmpty((String)dto.getRemark()) && dto.getRemark().length() > 200) {
            throw new CheckRuntimeException("\u5907\u6ce8\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e200");
        }
    }

    private void checkGeneralType(SubjectDTO dto) {
        String generalType = dto.getGeneralType();
        if (StringUtils.isEmpty((String)generalType)) {
            throw new CheckRuntimeException("\u79d1\u76ee\u5927\u7c7b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (generalType.equals(dto.getCode())) {
            throw new CheckRuntimeException("\u79d1\u76ee\u4ee3\u7801\u4e0e\u79d1\u76ee\u5927\u7c7b\u4ee3\u7801\u4e0d\u80fd\u76f8\u540c");
        }
        BaseDataDTO generalTypeCondi = new BaseDataDTO();
        generalTypeCondi.setTableName("MD_ACCTSUBJECTCLASS");
        generalTypeCondi.setCode(generalType);
        List generalTypeList = this.baseDataService.list(generalTypeCondi).getRows();
        if (CollectionUtils.isEmpty((Collection)generalTypeList)) {
            throw new CheckRuntimeException(String.format("\u79d1\u76ee\u5927\u7c7b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", generalType));
        }
    }

    private void checkOrient(SubjectDTO dto) {
        Integer orient = dto.getOrient();
        if (orient == null) {
            throw new CheckRuntimeException("\u79d1\u76ee\u65b9\u5411\u4e0d\u80fd\u4e3a\u7a7a");
        }
        OrientEnum orientEnum = OrientEnum.fromCode(dto.getOrient());
        if (orientEnum == null) {
            throw new CheckRuntimeException(String.format("\u79d1\u76ee\u65b9\u5411\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", orient));
        }
    }

    private void checkAssType(SubjectDTO dto) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Integer> assTypeMap = dto.getAssTypeMap();
        if (assTypeMap == null || assTypeMap.isEmpty()) {
            String assType = null;
            try {
                assType = objectMapper.writeValueAsString((Object)CollectionUtils.newHashMap());
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            dto.setAssType(assType);
            return;
        }
        Map<String, DimensionVO> assistDimMap = this.dimensionService.loadAllDimensions().stream().collect(Collectors.toMap(DimensionVO::getCode, item -> item, (k1, k2) -> k2));
        for (Map.Entry<String, Integer> assTypeEntry : assTypeMap.entrySet()) {
            if (assistDimMap.get(assTypeEntry.getKey()) != null) continue;
            throw new CheckRuntimeException(String.format("\u7ef4\u5ea6\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", assTypeEntry.getKey()));
        }
        String writeValueAsString = null;
        try {
            writeValueAsString = objectMapper.writeValueAsString(assTypeMap);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        dto.setAssType(writeValueAsString);
    }
}

