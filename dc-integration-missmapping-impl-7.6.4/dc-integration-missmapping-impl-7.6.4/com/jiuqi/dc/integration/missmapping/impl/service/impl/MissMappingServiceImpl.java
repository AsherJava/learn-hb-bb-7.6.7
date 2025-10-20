/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashBasedTable
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.SetMultimap
 *  com.google.common.collect.Table
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO
 *  com.jiuqi.dc.base.common.vo.BaseDataShowVO
 *  com.jiuqi.dc.base.impl.assistdim.util.AssistDimUtil
 *  com.jiuqi.dc.base.impl.rpunitmapping.service.Org2RpunitMappingService
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.integration.execute.impl.utils.UnitRefConvertUtil
 *  com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDO
 *  com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDTO
 *  com.jiuqi.dc.integration.missmapping.client.dto.MissMappingGatherDTO
 *  com.jiuqi.dc.integration.missmapping.client.vo.MissMappingDimVO
 *  com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.integration.missmapping.impl.service.impl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Table;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO;
import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import com.jiuqi.dc.base.impl.assistdim.util.AssistDimUtil;
import com.jiuqi.dc.base.impl.rpunitmapping.service.Org2RpunitMappingService;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.integration.execute.impl.utils.UnitRefConvertUtil;
import com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDO;
import com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDTO;
import com.jiuqi.dc.integration.missmapping.client.dto.MissMappingGatherDTO;
import com.jiuqi.dc.integration.missmapping.client.vo.MissMappingDimVO;
import com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO;
import com.jiuqi.dc.integration.missmapping.impl.dao.MissMappingDao;
import com.jiuqi.dc.integration.missmapping.impl.enums.UnitTypeEnum;
import com.jiuqi.dc.integration.missmapping.impl.service.MissMappingService;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissMappingServiceImpl
implements MissMappingService {
    @Autowired
    private MissMappingDao missMappingDao;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private BaseDataRefDefineService defineService;
    @Autowired
    private DataSchemeService dataSchemeService;

    @Override
    public List<BaseDataShowVO> getMissMappingDim(MissMappingQueryVO missMappingQueryVO) {
        List unitCodes = missMappingQueryVO.getUnitCodes();
        if (CollectionUtils.isEmpty((Collection)unitCodes) || StringUtils.isEmpty((String)((String)unitCodes.get(0)))) {
            missMappingQueryVO.setUnitCodes((List)CollectionUtils.newArrayList());
        } else {
            List<String> unitCodesByUnitType = this.getUnitCodesByUnitType(missMappingQueryVO);
            if (CollectionUtils.isEmpty(unitCodesByUnitType) && !Objects.equals(missMappingQueryVO.getUnitType(), UnitTypeEnum.UNITCODE.getCode())) {
                return CollectionUtils.newArrayList();
            }
            missMappingQueryVO.setUnitCodes(unitCodesByUnitType);
        }
        List<String> dimCodes = this.missMappingDao.queryDim(missMappingQueryVO);
        if (CollectionUtils.isEmpty(dimCodes)) {
            return CollectionUtils.newArrayList();
        }
        Map<String, String> titleMap = this.getDimTitleMap();
        return dimCodes.stream().map(dimCode -> {
            BaseDataShowVO baseDataShowVO = new BaseDataShowVO();
            baseDataShowVO.setCode(dimCode);
            baseDataShowVO.setTitle(titleMap.getOrDefault(dimCode, (String)dimCode));
            return baseDataShowVO;
        }).collect(Collectors.toList());
    }

    public Map<String, String> getDimTitleMap() {
        Map<String, String> titleMap = AssistDimUtil.listPublished().stream().collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getTitle, (k1, k2) -> k1));
        titleMap.put("MD_ACCTSUBJECT", "\u79d1\u76ee");
        titleMap.put("MD_CURRENCY", "\u5e01\u522b");
        titleMap.put("MD_CFITEM", "\u73b0\u6d41\u9879\u76ee");
        return titleMap;
    }

    @Override
    public PageVO<MissMappingDTO> getMissMappingDetail(MissMappingQueryVO missMappingQueryVO) {
        List unitCodes = missMappingQueryVO.getUnitCodes();
        if (CollectionUtils.isEmpty((Collection)unitCodes) || StringUtils.isEmpty((String)((String)unitCodes.get(0)))) {
            missMappingQueryVO.setUnitCodes((List)CollectionUtils.newArrayList());
        } else {
            List<String> unitCodesByUnitType = this.getUnitCodesByUnitType(missMappingQueryVO);
            if (CollectionUtils.isEmpty(unitCodesByUnitType) && !Objects.equals(missMappingQueryVO.getUnitType(), UnitTypeEnum.UNITCODE.getCode())) {
                return new PageVO();
            }
            missMappingQueryVO.setUnitCodes(unitCodesByUnitType);
        }
        Integer detailTotal = this.missMappingDao.getDetailTotal(missMappingQueryVO);
        if (detailTotal == 0) {
            return new PageVO();
        }
        List<MissMappingDO> totalList = this.missMappingDao.getDetail(missMappingQueryVO);
        Table<String, String, String> unit2OdsCodeMap = this.getUnit2OdsCodeMap(totalList);
        Table<String, String, String> dimId2CodeMap = this.getDimId2CodeMap(totalList);
        Map<String, List<MissMappingDO>> missMappingGroup = totalList.stream().collect(Collectors.groupingBy(e -> e.getDataSchemeCode() + "|" + e.getUnitCode() + "|" + e.getVchrNum()));
        ArrayList detailList = new ArrayList(missMappingGroup.size());
        missMappingGroup.forEach((k, v) -> {
            MissMappingDTO missMappingDTO = new MissMappingDTO();
            missMappingDTO.setUnitCode((String)unit2OdsCodeMap.get((Object)((MissMappingDO)v.get(0)).getDataSchemeCode(), (Object)((MissMappingDO)v.get(0)).getUnitCode()));
            missMappingDTO.setDataSchemeCode(((MissMappingDO)v.get(0)).getDataSchemeCode());
            missMappingDTO.setVchrNum(((MissMappingDO)v.get(0)).getVchrNum());
            HashMultimap missMappingDimIdMap = HashMultimap.create();
            HashMultimap missMappingDimCodeMap = HashMultimap.create();
            v.forEach(arg_0 -> MissMappingServiceImpl.lambda$null$3((SetMultimap)missMappingDimIdMap, dimId2CodeMap, missMappingDTO, (SetMultimap)missMappingDimCodeMap, arg_0));
            MissMappingDimVO[] missMappingDimVOCode = new MissMappingDimVO[missMappingDimIdMap.asMap().size()];
            AtomicInteger indexCode = new AtomicInteger();
            missMappingDimIdMap.asMap().forEach((arg_0, arg_1) -> MissMappingServiceImpl.lambda$null$4((SetMultimap)missMappingDimCodeMap, missMappingDimVOCode, indexCode, arg_0, arg_1));
            missMappingDTO.setDimVOs(missMappingDimVOCode);
            detailList.add(missMappingDTO);
        });
        List sortedList = detailList.stream().sorted(Comparator.comparing(MissMappingDTO::getDataSchemeCode).thenComparing(MissMappingDTO::getUnitCode).thenComparing(MissMappingDTO::getVchrNum)).collect(Collectors.toList());
        return new PageVO(sortedList, detailTotal.intValue());
    }

    @Override
    public PageVO<MissMappingGatherDTO> getMissMappingGather(MissMappingQueryVO missMappingQueryVO) {
        List unitCodes = missMappingQueryVO.getUnitCodes();
        if (CollectionUtils.isEmpty((Collection)unitCodes) || StringUtils.isEmpty((String)((String)unitCodes.get(0)))) {
            missMappingQueryVO.setUnitCodes((List)CollectionUtils.newArrayList());
        } else {
            List<String> unitCodesByUnitType = this.getUnitCodesByUnitType(missMappingQueryVO);
            if (CollectionUtils.isEmpty(unitCodesByUnitType) && !Objects.equals(missMappingQueryVO.getUnitType(), UnitTypeEnum.UNITCODE.getCode())) {
                return new PageVO();
            }
            missMappingQueryVO.setUnitCodes(unitCodesByUnitType);
        }
        Integer gatherTotal = this.missMappingDao.getGatherTotal(missMappingQueryVO);
        if (gatherTotal == 0) {
            return new PageVO();
        }
        List<MissMappingDO> totalList = this.missMappingDao.getGather(missMappingQueryVO);
        Table<String, String, String> dimId2CodeMap = this.getDimId2CodeMap(totalList);
        ArrayList gatherList = new ArrayList(totalList.size());
        Map<String, String> titleMap = this.getDimTitleMap();
        totalList.forEach(missMappingDO -> {
            MissMappingGatherDTO missMappingGatherDTO = new MissMappingGatherDTO();
            missMappingGatherDTO.setDataSchemeCode(missMappingDO.getDataSchemeCode());
            MissMappingDimVO dimVO = new MissMappingDimVO();
            dimVO.setCode(missMappingDO.getDimCode());
            dimVO.setId(missMappingDO.getDimValue());
            String value = (String)dimId2CodeMap.get((Object)(missMappingDO.getDataSchemeCode() + "|" + missMappingDO.getDimCode()), (Object)missMappingDO.getDimValue());
            if (!StringUtils.isEmpty((String)value)) {
                dimVO.setDimValue(value);
            }
            dimVO.setTitle((String)titleMap.get(missMappingDO.getDimCode()));
            missMappingGatherDTO.setDimVO(dimVO);
            gatherList.add(missMappingGatherDTO);
        });
        return new PageVO(gatherList, gatherTotal.intValue());
    }

    private Table<String, String, String> getUnit2OdsCodeMap(List<MissMappingDO> totalList) {
        HashMultimap schemeUnitMap = HashMultimap.create();
        totalList.forEach(arg_0 -> MissMappingServiceImpl.lambda$getUnit2OdsCodeMap$7((SetMultimap)schemeUnitMap, arg_0));
        HashBasedTable unit2OdsCodeMap = HashBasedTable.create();
        schemeUnitMap.forEach((arg_0, arg_1) -> MissMappingServiceImpl.lambda$getUnit2OdsCodeMap$8((Table)unit2OdsCodeMap, arg_0, arg_1));
        return unit2OdsCodeMap;
    }

    private Table<String, String, String> getDimId2CodeMap(List<MissMappingDO> totalList) {
        HashMultimap dimIdValueMap = HashMultimap.create();
        totalList.forEach(arg_0 -> MissMappingServiceImpl.lambda$getDimId2CodeMap$9((SetMultimap)dimIdValueMap, arg_0));
        HashBasedTable dimId2CodeMap = HashBasedTable.create();
        dimIdValueMap.forEach((arg_0, arg_1) -> this.lambda$getDimId2CodeMap$11((Table)dimId2CodeMap, arg_0, arg_1));
        return dimId2CodeMap;
    }

    public List<String> getUnitCodesByUnitType(MissMappingQueryVO missMappingQueryVO) {
        List unitCodes = missMappingQueryVO.getUnitCodes();
        if (CollectionUtils.isEmpty((Collection)unitCodes)) {
            return CollectionUtils.newArrayList();
        }
        if (UnitTypeEnum.UNITCODE.getCode().equals(missMappingQueryVO.getUnitType())) {
            return unitCodes;
        }
        if (UnitTypeEnum.REPORT_UNITCODE.getCode().equals(missMappingQueryVO.getUnitType())) {
            HashSet orgCodes = new HashSet();
            Org2RpunitMappingQueryVO queryParam = new Org2RpunitMappingQueryVO();
            queryParam.setAcctYear(Integer.valueOf(DateUtils.getYearOfDate((Date)new Date())));
            queryParam.setOrgs(unitCodes);
            queryParam.setPageNum(Integer.valueOf(-1));
            for (int period = 1; period <= 12; ++period) {
                queryParam.setAcctPeriod(Integer.valueOf(period));
                orgCodes.addAll(((Org2RpunitMappingService)ApplicationContextRegister.getBean(Org2RpunitMappingService.class)).getAllOrgCodeByRpUnitCode(queryParam));
            }
            return new ArrayList<String>(orgCodes);
        }
        return this.missMappingDao.getDcOrg((String)unitCodes.get(0));
    }

    private static String joinAndSortWithComma(Collection<String> values) {
        HashSet<String> valueSet = new HashSet<String>(values);
        ArrayList<String> sortedValues = new ArrayList<String>(valueSet);
        sortedValues.sort(Comparator.naturalOrder());
        return String.join((CharSequence)", ", sortedValues);
    }

    private /* synthetic */ void lambda$getDimId2CodeMap$11(Table dimId2CodeMap, String key, String id) {
        String[] split = key.split("\\|");
        String dataSchemeCode = split[0];
        String dimCode = split[1];
        if (StringUtils.isEmpty((String)id)) {
            return;
        }
        String advancedSql = Optional.ofNullable(this.defineService.findByCode(dataSchemeCode, dimCode)).map(BaseDataMappingDefineDTO::getAdvancedSql).orElse("");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, CODE FROM ( ").append(advancedSql).append(" ) T \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"ID", new ArrayList<String>(Collections.singleton(id)))).append(" \n");
        String dataSourceCode = Optional.ofNullable(this.dataSchemeService.getByCode(dataSchemeCode)).map(DataSchemeDTO::getDataSourceCode).orElse("");
        this.dataSourceService.query(dataSourceCode, sql.toString(), null, (rs, rownNum) -> {
            dimId2CodeMap.put((Object)key, (Object)rs.getString(1), (Object)rs.getString(2));
            return null;
        });
        dimId2CodeMap.put((Object)key, (Object)"#", (Object)"#");
    }

    private static /* synthetic */ void lambda$getDimId2CodeMap$9(SetMultimap dimIdValueMap, MissMappingDO e) {
        dimIdValueMap.put((Object)(e.getDataSchemeCode() + "|" + e.getDimCode()), (Object)e.getDimValue());
    }

    private static /* synthetic */ void lambda$getUnit2OdsCodeMap$8(Table unit2OdsCodeMap, String scheme, String unit) {
        Map unitCodeRefMap = UnitRefConvertUtil.getUnitCodeRefMap((String)scheme);
        unit2OdsCodeMap.put((Object)scheme, (Object)unit, (Object)(((DataRefDTO)unitCodeRefMap.get(unit)).getOdsCode() + "|" + ((DataRefDTO)unitCodeRefMap.get(unit)).getOdsName()));
    }

    private static /* synthetic */ void lambda$getUnit2OdsCodeMap$7(SetMultimap schemeUnitMap, MissMappingDO e) {
        schemeUnitMap.put((Object)e.getDataSchemeCode(), (Object)e.getUnitCode());
    }

    private static /* synthetic */ void lambda$null$4(SetMultimap missMappingDimCodeMap, MissMappingDimVO[] missMappingDimVOCode, AtomicInteger indexCode, String dimCode, Collection dimIdList) {
        MissMappingDimVO missMappingDimVO = new MissMappingDimVO();
        Set codeList = missMappingDimCodeMap.get((Object)dimCode);
        missMappingDimVO.setId(MissMappingServiceImpl.joinAndSortWithComma(dimIdList));
        if (CollectionUtils.isEmpty((Collection)codeList)) {
            missMappingDimVO.setDimValue("");
        } else {
            missMappingDimVO.setDimValue(MissMappingServiceImpl.joinAndSortWithComma(codeList));
        }
        missMappingDimVO.setCode(dimCode);
        missMappingDimVOCode[indexCode.getAndIncrement()] = missMappingDimVO;
    }

    private static /* synthetic */ void lambda$null$3(SetMultimap missMappingDimIdMap, Table dimId2CodeMap, MissMappingDTO missMappingDTO, SetMultimap missMappingDimCodeMap, MissMappingDO e) {
        missMappingDimIdMap.put((Object)e.getDimCode(), (Object)e.getDimValue());
        String value = (String)dimId2CodeMap.get((Object)(missMappingDTO.getDataSchemeCode() + "|" + e.getDimCode()), (Object)e.getDimValue());
        if (!StringUtils.isEmpty((String)value)) {
            missMappingDimCodeMap.put((Object)e.getDimCode(), (Object)value);
        }
    }
}

