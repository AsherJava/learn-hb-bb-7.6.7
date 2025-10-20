/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.enums.DataRefFilterType
 *  com.jiuqi.dc.datamapping.client.vo.DataRefListVO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.organization.service.OrgAuthService
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.dc.datamapping.impl.service.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.enums.DataRefFilterType;
import com.jiuqi.dc.datamapping.client.vo.DataRefListVO;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao;
import com.jiuqi.dc.datamapping.impl.service.DataRefListConfigureService;
import com.jiuqi.dc.datamapping.impl.utils.IsolationUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.organization.service.OrgAuthService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="BDEDataRefConfigureService")
public class BDEDataRefConfigureServiceImpl
implements DataRefListConfigureService {
    @Autowired
    private DataRefConfigureDao dao;
    @Autowired
    private BaseDataRefDefineService defineService;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private OrgAuthService orgAuthService;

    @Override
    @OuterTransaction
    public DataRefListVO list(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefListDTO dto) {
        dto.setPagination(Boolean.TRUE.equals(dto.getPagination()));
        BaseDataMappingDefineDTO define = this.defineService.getByCode(dto.getDataSchemeCode(), dto.getTableName());
        DataRefFilterType filterType = DataRefFilterType.fromCode((String)dto.getFilterType());
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.getByCode(dto.getDataSchemeCode());
        Map<Object, Object> refDataMap = CollectionUtils.newHashMap();
        int count = 0;
        List<Object> dataRefList = new ArrayList();
        switch (filterType) {
            case ALL: {
                List<DataRefDTO> selectAllRefData = this.dao.selectAllRefData(define);
                if (!CollectionUtils.isEmpty(selectAllRefData)) {
                    refDataMap = selectAllRefData.stream().collect(Collectors.groupingBy(item -> IsolationUtil.buildIsolationParam(item, define), Collectors.toMap(DataRefDTO::getOdsCode, Function.identity(), (existing, replacement) -> existing)));
                }
                if (this.dao.countResultByDataSource(dataSchemeDTO.getDataSourceCode(), define, dto) == 0 && CollectionUtils.isEmpty(selectAllRefData)) break;
                List<DataRefDTO> tempDataRefList = this.dao.getResultByDataSource(dataSchemeDTO.getDataSourceCode(), define, dto);
                List extendFieldDefines = define.getItems().stream().filter(item -> {
                    String fieldName = item.getFieldName();
                    return !"ID".equals(fieldName) && !"CODE".equals(fieldName) && !"NAME".equals(fieldName);
                }).collect(Collectors.toList());
                HashSet refIds = CollectionUtils.newHashSet();
                for (DataRefDTO dataRefDTO : tempDataRefList) {
                    DataRefDTO hasRefDTO;
                    Map isolationData = (Map)refDataMap.get(IsolationUtil.buildIsolationParam(dataRefDTO, define));
                    if (Objects.isNull(isolationData) || isolationData.isEmpty() || Objects.isNull(hasRefDTO = (DataRefDTO)isolationData.get(dataRefDTO.getOdsCode()))) continue;
                    refIds.add(hasRefDTO.getId());
                    dataRefDTO.setId(hasRefDTO.getId());
                    dataRefDTO.setCode(hasRefDTO.getCode());
                    dataRefDTO.setName(hasRefDTO.getName());
                    dataRefDTO.setHandleStatus(hasRefDTO.getHandleStatus());
                    dataRefDTO.setOperator(hasRefDTO.getOperator());
                    dataRefDTO.setOperateTime(hasRefDTO.getOperateTime());
                    for (FieldMappingDefineDTO defineDTO : extendFieldDefines) {
                        String fieldName = "ODS_" + defineDTO.getFieldName();
                        if (hasRefDTO.get((Object)fieldName) == null) continue;
                        dataRefDTO.put((Object)fieldName, hasRefDTO.get((Object)fieldName));
                    }
                }
                selectAllRefData.stream().filter(data -> !refIds.contains(data.getId())).forEach(tempDataRefList::add);
                dataRefList = Objects.nonNull(dto.getFilterParam()) && !dto.getFilterParam().isEmpty() ? tempDataRefList.stream().filter(this.buildFilterChain(dto.getFilterParam(), true, define.getIsolationStrategy())).collect(Collectors.toList()) : tempDataRefList;
                count = dataRefList.size();
                if (!Boolean.TRUE.equals(dto.getPagination())) break;
                dataRefList = dataRefList.stream().skip((dto.getPageNum() - 1) * dto.getPageSize()).limit(dto.getPageSize().intValue()).collect(Collectors.toList());
                break;
            }
            case UNREF: {
                int initialCapacity = this.dao.countResultByDataSource(dataSchemeDTO.getDataSourceCode(), define, dto);
                if (initialCapacity == 0) break;
                List<DataRefDTO> selectAllRefData = this.dao.selectAllRefData(define);
                if (!CollectionUtils.isEmpty(selectAllRefData)) {
                    refDataMap = selectAllRefData.stream().collect(Collectors.groupingBy(item -> IsolationUtil.buildIsolationParam(item, define), Collectors.toMap(DataRefDTO::getOdsCode, Function.identity(), (existing, replacement) -> existing)));
                }
                List<DataRefDTO> noPaginDataList = this.dao.getResultByDataSource(dataSchemeDTO.getDataSourceCode(), define, dto, initialCapacity);
                for (DataRefDTO dataRefDTO : noPaginDataList) {
                    Map isolationData = (Map)refDataMap.get(IsolationUtil.buildIsolationParam(dataRefDTO, define));
                    if (Objects.isNull(isolationData) || isolationData.isEmpty()) {
                        dataRefList.add(dataRefDTO);
                        continue;
                    }
                    if (isolationData.containsKey(dataRefDTO.getOdsCode())) continue;
                    dataRefList.add(dataRefDTO);
                }
                if (Objects.nonNull(dto.getFilterParam()) && !dto.getFilterParam().isEmpty()) {
                    dataRefList = dataRefList.stream().filter(this.buildFilterChain(dto.getFilterParam(), false, define.getIsolationStrategy())).collect(Collectors.toList());
                }
                count = dataRefList.size();
                if (!Boolean.TRUE.equals(dto.getPagination())) break;
                dataRefList = dataRefList.stream().skip((dto.getPageNum() - 1) * dto.getPageSize()).limit(dto.getPageSize().intValue()).collect(Collectors.toList());
                break;
            }
            case HASREF: {
                count = this.dao.countHasref(define, dto);
                dataRefList = this.dao.selectHasref(define, dto);
                break;
            }
            default: {
                count = this.dao.countAutoMatch(define, dto);
                dataRefList = this.dao.selectAutoMatch(define, dto);
            }
        }
        if (Objects.nonNull(dto.getCustomFlag()) && dto.getCustomFlag().booleanValue()) {
            dataRefList.sort(Comparator.comparing(DataRefDTO::getOdsCode));
        }
        DataRefListVO vo = new DataRefListVO();
        vo.setPageVo(new PageVO(dataRefList, count));
        return vo;
    }

    private Predicate<DataRefDTO> buildFilterChain(Map<String, String> filterParam, boolean allData, String isolationStrategy) {
        Predicate<DataRefDTO> filter = p -> true;
        for (Map.Entry<String, String> entry : filterParam.entrySet()) {
            if (StringUtils.isEmpty((String)entry.getValue())) continue;
            if ("CODE".equals(entry.getKey())) {
                filter = filter.and(p -> !StringUtils.isEmpty((String)p.getCode()) && p.getCode().contains((CharSequence)entry.getValue()));
                continue;
            }
            if ("ODS_CODE".equals(entry.getKey())) {
                filter = filter.and(p -> p.getOdsCode().contains((CharSequence)entry.getValue()));
                continue;
            }
            if ("DC_UNITCODE".equals(entry.getKey())) {
                filter = filter.and(p -> Arrays.asList(((String)entry.getValue()).split(",")).contains(p.getValueStr("DC_UNITCODE")));
                continue;
            }
            if (!allData) continue;
            if ("HANDLESTATUS".equals(entry.getKey())) {
                filter = filter.and(p -> Arrays.asList(((String)entry.getValue()).split(",")).contains(p.getHandleStatus()));
                continue;
            }
            if ("STARTTIME".equals(entry.getKey())) {
                filter = filter.and(p -> Objects.nonNull(p.getOperateTime()) && !p.getOperateTime().before(DateUtils.parse((String)((String)entry.getValue()), (String)"yyyy-MM-dd HH:mm:ss")));
                continue;
            }
            if ("ENDTIME".equals(entry.getKey())) {
                filter = filter.and(p -> Objects.nonNull(p.getOperateTime()) && !p.getOperateTime().after(DateUtils.parse((String)((String)entry.getValue()), (String)"yyyy-MM-dd HH:mm:ss")));
                continue;
            }
            filter = filter.and(p -> Objects.nonNull(p.get(entry.getKey())) && p.get(entry.getKey()).toString().contains((CharSequence)entry.getValue()));
        }
        if (IsolationStrategy.getIsolationFieldByCode((String)isolationStrategy).contains("DC_UNITCODE") && StringUtils.isEmpty((String)filterParam.get("DC_UNITCODE"))) {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setTenantName(ShiroUtil.getTenantName());
            orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
            orgDTO.setCategoryname("MD_ORG");
            Set authCodes = this.orgAuthService.listAuthOrg((UserDO)ShiroUtil.getUser(), orgDTO);
            Predicate<DataRefDTO> authFilter = p -> authCodes.contains(p.getValueStr("DC_UNITCODE"));
            if (IsolationStrategy.SHARE_ISOLATION.getCode().equals(isolationStrategy)) {
                authFilter = authFilter.or(p -> "-".equals(p.getValueStr("DC_UNITCODE")));
            }
            filter = filter.and(authFilter);
        }
        return filter;
    }
}

