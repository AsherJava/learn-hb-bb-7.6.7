/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.base.common.exception.CheckRuntimeException
 *  com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO
 *  com.jiuqi.dc.datamapping.client.dto.RefChangeHandleParamDTO
 *  com.jiuqi.dc.datamapping.client.dto.RefChangePairDTO
 *  com.jiuqi.dc.datamapping.client.enums.DataRefFilterType
 *  com.jiuqi.dc.datamapping.client.vo.DataRefAutoMatchVO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.dc.mappingscheme.impl.enums.SchemeBaseDataRefType
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.datamapping.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.base.common.exception.CheckRuntimeException;
import com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO;
import com.jiuqi.dc.datamapping.client.dto.RefChangeHandleParamDTO;
import com.jiuqi.dc.datamapping.client.dto.RefChangePairDTO;
import com.jiuqi.dc.datamapping.client.enums.DataRefFilterType;
import com.jiuqi.dc.datamapping.client.vo.DataRefAutoMatchVO;
import com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao;
import com.jiuqi.dc.datamapping.impl.enums.RefDynamicField;
import com.jiuqi.dc.datamapping.impl.enums.RefHandleStatus;
import com.jiuqi.dc.datamapping.impl.gather.IAutoMatchServiceGather;
import com.jiuqi.dc.datamapping.impl.gather.impl.DataRefConfigureServiceGather;
import com.jiuqi.dc.datamapping.impl.service.AutoMatchService;
import com.jiuqi.dc.datamapping.impl.service.DataRefConfigureService;
import com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider;
import com.jiuqi.dc.datamapping.impl.utils.DataRefChkResult;
import com.jiuqi.dc.datamapping.impl.utils.IDataRefChcker;
import com.jiuqi.dc.datamapping.impl.utils.IDataRefChckerGather;
import com.jiuqi.dc.datamapping.impl.utils.IsolationUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.dc.mappingscheme.impl.enums.SchemeBaseDataRefType;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataRefConfigureServiceImpl
implements DataRefConfigureService {
    @Autowired
    private DataRefConfigureDao dao;
    @Autowired
    private BaseDataRefDefineService defineService;
    @Autowired
    private IDataRefChckerGather checkerGather;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private DataRefConfigureServiceGather dataRefConfigureServiceGather;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private IAutoMatchServiceGather autoMatchServiceGather;
    @Autowired
    private ServiceConfigProperties prop;
    @Autowired
    private IsolateRefDefineCacheProvider cacheProvider;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;

    @Override
    public List<DataMappingDefineDTO> listDefine(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a") String dataSchemeCode) {
        return this.defineService.listBySchemeCode(dataSchemeCode).stream().filter(item -> !"CURRENCYCODE".equals(item.getCode()) && !"MD_CURRENCY".equals(item.getCode())).sorted((e1, e2) -> {
            String code1 = e1.getCode();
            String code2 = e2.getCode();
            SchemeBaseDataRefType type1 = null;
            SchemeBaseDataRefType type2 = null;
            for (SchemeBaseDataRefType type : SchemeBaseDataRefType.values()) {
                if (type.getCode().equals(code1)) {
                    type1 = type;
                }
                if (!type.getCode().equals(code2)) continue;
                type2 = type;
            }
            if (type1 != null && type2 != null) {
                return Integer.compare(type1.getOrder(), type2.getOrder());
            }
            if (type1 == null && type2 == null) {
                return code1.compareTo(code2);
            }
            return type1 != null ? -1 : 1;
        }).collect(Collectors.toList());
    }

    @Override
    public List<DataMappingDefineDTO> listAllDefine() {
        Collection<BaseDataMappingDefineDTO> values = this.defineService.list(new DataRefDefineListDTO()).stream().filter(item -> RuleType.isItemByItem((String)item.getRuleType())).collect(Collectors.toMap(DataMappingDefineDTO::getCode, obj -> obj, (k1, k2) -> k1)).values();
        return new ArrayList<BaseDataMappingDefineDTO>(values);
    }

    @Override
    public DataRefDTO getByOdsId(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a") String dataSchemeCode, @NotBlank(message="\u57fa\u7840\u6570\u636e\u9879\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u57fa\u7840\u6570\u636e\u9879\u4e0d\u80fd\u4e3a\u7a7a") String tableName, @NotBlank(message="\u6e90\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6e90\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String odsId) {
        ArrayList<String> odsIdList = new ArrayList<String>();
        odsIdList.add(odsId);
        List<DataRefDTO> refList = this.dao.selectByOdsIdListAndScheme(dataSchemeCode, tableName, odsIdList);
        if (CollectionUtils.isEmpty(refList)) {
            return null;
        }
        return refList.get(0);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public DataRefSaveVO save(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefSaveDTO dto) {
        Assert.isNotEmpty((String)dto.getDataSchemeCode(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getTableName(), (String)"\u57fa\u7840\u6570\u636e\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((Collection)dto.getData(), (String)"\u4fdd\u5b58\u7684\u6570\u636e\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        BaseDataMappingDefineDTO define = this.defineService.getByCode(dto.getDataSchemeCode(), dto.getTableName());
        IDataRefChcker checker = this.checkerGather.getChecker(define.getCode());
        if (checker == null) {
            throw new BusinessRuntimeException(String.format("\u4ee3\u7801\u3010%1$s\u3011\u7684\u6821\u9a8c\u5668\u4e0d\u5b58\u5728", define.getCode()));
        }
        StringBuilder logTitle = new StringBuilder();
        StringBuilder logDetail = new StringBuilder();
        DataRefChkResult result = checker.check(define, dto);
        this.delete((DataMappingDefineDTO)define, result.getDeleteData(), result.getSavedDataList(), logTitle, logDetail);
        this.create(define, result.getCreateData(), logTitle, logDetail);
        this.modify(define, result.getModifyData(), result.getSavedDataList(), logTitle, logDetail);
        DataRefSaveVO vo = new DataRefSaveVO();
        vo.setTotal(Integer.valueOf(dto.getData().size()));
        vo.setSuccess(Integer.valueOf(result.getCreateData().size() + result.getModifyData().size() + result.getDeleteData().size()));
        vo.setCreateData(result.getCreateData());
        vo.setUpdateData(result.getModifyData());
        vo.setDeleteData(result.getDeleteData());
        vo.setErrorMessage(result.getErrorMessage());
        this.syncCache(dto.getDataSchemeCode(), define);
        LogHelper.info((String)DcFunctionModuleEnum.DATAMAPPING.getFullModuleName(), (String)logTitle.toString(), (String)logDetail.toString());
        if ("DC".equals(this.prop.getServiceName())) {
            this.refChangeHandle(dto, result);
        }
        return vo;
    }

    private void refChangeHandle(DataRefSaveDTO refSaveDTO, DataRefChkResult result) {
        RefChangeHandleParamDTO changeParam = new RefChangeHandleParamDTO();
        changeParam.setDataSchemeCode(refSaveDTO.getDataSchemeCode());
        changeParam.setTableName(refSaveDTO.getTableName());
        changeParam.setCustomFlag(refSaveDTO.getCustomFlag());
        changeParam.setRefChangePairDatas((List)CollectionUtils.newArrayList());
        if (!CollectionUtils.isEmpty(result.getCreateData())) {
            for (DataRefDTO dto : result.getCreateData()) {
                changeParam.getRefChangePairDatas().add(new RefChangePairDTO(null, dto));
            }
        }
        if (!CollectionUtils.isEmpty(result.getDeleteData())) {
            for (DataRefDTO dto : result.getDeleteData()) {
                changeParam.getRefChangePairDatas().add(new RefChangePairDTO(dto, null));
            }
        }
        this.taskHandlerFactory.getMainTaskHandlerClient().startTask("RefChangeHandle", JsonUtils.writeValueAsString((Object)changeParam));
    }

    @OuterTransaction
    private void delete(DataMappingDefineDTO define, List<DataRefDTO> list, List<DataRefDTO> savedList, StringBuilder logTitle, StringBuilder logDetail) {
        this.dao.batchDelete(define.getCode(), list);
        if (!CollectionUtils.isEmpty(list)) {
            if ("MD_ORG".equals(define.getCode())) {
                List dimList = this.defineService.listBySchemeCode(define.getDataSchemeCode());
                for (BaseDataMappingDefineDTO dim : dimList) {
                    if (!IsolationStrategy.getIsolationFieldByCode((String)dim.getIsolationStrategy()).contains("DC_UNITCODE")) continue;
                    this.dao.deleteByDcUnitCode(dim.getCode(), define.getDataSchemeCode(), list.stream().map(DataRefDTO::getCode).collect(Collectors.toList()));
                }
            }
            logTitle.append(StringUtils.join((Object[])new String[]{"\u5220\u9664", define.getDataSchemeCode(), define.getName()}, (String)"-")).append("\n");
            logDetail.append("\u5220\u9664\u6620\u5c04\u8be6\u7ec6\u4fe1\u606f\uff1a\n");
            Map<String, DataRefDTO> savedByIdMap = savedList.stream().collect(Collectors.toMap(DataRefDTO::getId, item -> item, (k1, k2) -> k2));
            for (DataRefDTO ref : list) {
                logDetail.append("\u201c\u6e90\u4ee3\u7801").append(savedByIdMap.get(ref.getId()).getOdsCode()).append("-\u6620\u5c04\u4ee3\u7801").append(savedByIdMap.get(ref.getId()).getCode()).append("\u201d;");
            }
            logDetail.append("\n\n");
        }
    }

    @OuterTransaction
    private void create(BaseDataMappingDefineDTO define, List<DataRefDTO> list, StringBuilder logTitle, StringBuilder logDetail) {
        list.forEach(item -> {
            item.setId(UUIDUtils.newUUIDStr());
            item.setDataSchemeCode(define.getDataSchemeCode());
            if (!RefHandleStatus.IMPORTED.getCode().equals(item.getHandleStatus())) {
                item.setHandleStatus(RefHandleStatus.CREATED.getCode());
            }
            item.setOperator(NpContextHolder.getContext().getUserName());
            item.setOperateTime(DateUtils.now());
        });
        this.dao.batchInsert(define, list);
        if (!CollectionUtils.isEmpty(list)) {
            logTitle.append(StringUtils.join((Object[])new String[]{"\u65b0\u589e", define.getDataSchemeCode(), define.getName()}, (String)"-")).append("\n");
            logDetail.append("\u65b0\u589e\u6620\u5c04\u8be6\u7ec6\u4fe1\u606f\uff1a\n").append("\u65b0\u589e\u6620\u5c04").append(list.size()).append("\u6761\n\n");
        }
    }

    @OuterTransaction
    private void modify(BaseDataMappingDefineDTO define, List<DataRefDTO> list, List<DataRefDTO> savedList, StringBuilder logTitle, StringBuilder logDetail) {
        list.forEach(item -> {
            item.setDataSchemeCode(define.getDataSchemeCode());
            item.setHandleStatus(RefHandleStatus.UPDATED.getCode());
            item.setOperator(NpContextHolder.getContext().getUserName());
            item.setOperateTime(DateUtils.now());
        });
        this.dao.batchUpdate(define, list);
        if (!CollectionUtils.isEmpty(list)) {
            logTitle.append(StringUtils.join((Object[])new String[]{"\u4fee\u6539", define.getDataSchemeCode(), define.getName()}, (String)"-")).append("\n");
            logDetail.append("\u4fee\u6539\u6620\u5c04\u8be6\u7ec6\u4fe1\u606f\uff1a\n\u4fee\u6539\u524d");
            Map<String, DataRefDTO> savedByIdMap = savedList.stream().collect(Collectors.toMap(DataRefDTO::getId, item -> item, (k1, k2) -> k2));
            for (DataRefDTO ref : list) {
                logDetail.append("\u201c\u6e90\u4ee3\u7801").append(savedByIdMap.get(ref.getId()).getOdsCode()).append("-\u6620\u5c04\u4ee3\u7801").append(savedByIdMap.get(ref.getId()).getCode()).append("\u201d;");
            }
            logDetail.append("\n\n");
        }
    }

    @Override
    @OuterTransaction
    public Integer clean(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefSaveDTO dto) {
        Assert.isNotEmpty((String)dto.getDataSchemeCode(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getTableName(), (String)"\u57fa\u7840\u6570\u636e\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        if ("DC".equals(this.prop.getServiceName())) {
            throw new CheckRuntimeException("\u4e00\u672c\u8d26\u670d\u52a1\u4e0d\u5141\u8bb8\u5168\u6e05");
        }
        BaseDataMappingDefineDTO define = this.defineService.getByCode(dto.getDataSchemeCode(), dto.getTableName());
        int count = this.dao.deleteByBaseDataRefDefine((DataMappingDefineDTO)define);
        if (count == 0) {
            throw new CheckRuntimeException("\u4e0d\u5b58\u5728\u9700\u8981\u5220\u9664\u7684\u6570\u636e");
        }
        this.syncCache(dto.getDataSchemeCode(), define);
        String logTitle = StringUtils.join((Object[])new String[]{"\u5168\u6e05", define.getDataSchemeCode(), define.getName()}, (String)"-");
        StringBuilder logDetail = new StringBuilder();
        logDetail.append("\u5168\u6e05\u6620\u5c04\u8be6\u7ec6\u4fe1\u606f\uff1a\n").append("\u5168\u6e05\u6620\u5c04").append(count).append("\u6761\n\n");
        LogHelper.info((String)DcFunctionModuleEnum.DATAMAPPING.getFullModuleName(), (String)logTitle, (String)logDetail.toString());
        return count;
    }

    @Override
    @OuterTransaction
    public void deleteByBaseDataRefDefine(@NotNull(message="\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO define) {
        this.dao.deleteByBaseDataRefDefine(define);
        BaseDataMappingDefineDTO baseDataDefine = (BaseDataMappingDefineDTO)define;
        this.syncCache(define.getDataSchemeCode(), baseDataDefine);
    }

    private void syncCache(String dataSchemeCode, BaseDataMappingDefineDTO define) {
        String isolationStrategy = "MD_ORG".equals(define.getCode()) && IsolationUtil.listDynamicField(define).contains("ODS_ASSISTCODE") ? IsolationStrategy.ORG_ASSISTCODE.getCode() : (StringUtils.isEmpty((String)define.getIsolationStrategy()) ? IsolationStrategy.SHARE.getCode() : define.getIsolationStrategy());
        this.cacheProvider.syncCache(dataSchemeCode, define.getCode(), isolationStrategy);
    }

    @Override
    public List<SelectOptionVO> matchRuleList() {
        return this.autoMatchServiceGather.listAll().stream().map(e -> {
            SelectOptionVO selectOptionVO = new SelectOptionVO();
            selectOptionVO.setCode(e.getCode());
            selectOptionVO.setName(e.getName());
            return selectOptionVO;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public DataRefAutoMatchVO autoMatch(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefAutoMatchDTO dto) {
        Assert.isNotEmpty((String)dto.getDataSchemeCode(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)dto.getTableName(), (String)"\u57fa\u7840\u6570\u636e\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        BaseDataMappingDefineDTO define = this.defineService.getByCode(dto.getDataSchemeCode(), dto.getTableName());
        if ("#".equals(define.getAutoMatchDim()) || StringUtils.isEmpty((String)define.getAutoMatchDim())) {
            throw new BusinessRuntimeException("\u5f53\u524d\u6570\u636e\u7684\u81ea\u52a8\u5339\u914d\u89c4\u5219\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u81ea\u52a8\u6620\u5c04\uff0c\u8bf7\u5728\u6570\u636e\u6620\u5c04\u65b9\u6848\u529f\u80fd\u4e2d\u914d\u7f6e\u540e\u518d\u64cd\u4f5c");
        }
        switch (define.getCode()) {
            case "MD_ORG": {
                return this.autoMatchOrgData(define, dto);
            }
        }
        return this.autoMatchBaseData(define, dto);
    }

    private DataRefAutoMatchVO autoMatchOrgData(BaseDataMappingDefineDTO define, DataRefAutoMatchDTO dto) {
        String[] matchDimArr = define.getAutoMatchDim().split(",");
        if (matchDimArr.length == 0) {
            throw new BusinessRuntimeException(String.format("\u3010%1$s\u3011\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u7684\u81ea\u52a8\u6620\u5c04\u5b57\u6bb5\u4e3a\u7a7a\uff0c\u8bf7\u91cd\u65b0\u914d\u7f6e", dto.getTableName()));
        }
        DataRefListDTO condi = new DataRefListDTO();
        condi.setDataSchemeCode(dto.getDataSchemeCode());
        condi.setTableName(dto.getTableName());
        condi.setFilterType(DataRefFilterType.UNREF.getCode());
        condi.setPagination(false);
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.getByCode(dto.getDataSchemeCode());
        List unRefData = this.dataRefConfigureServiceGather.getDataRefConfigureServiceBySourceDataType(dataSchemeDTO.getSourceDataType()).list(condi).getPageVo().getRows();
        OrgDTO orgDataCondi = new OrgDTO();
        orgDataCondi.setAuthType(OrgDataOption.AuthType.NONE);
        PageVO pageVo = this.orgDataClient.list(orgDataCondi);
        DataRefAutoMatchVO vo = new DataRefAutoMatchVO();
        LinkedList dataRefList = CollectionUtils.newLinkedList();
        for (String matchDim : matchDimArr) {
            AutoMatchService serviceByCode = this.autoMatchServiceGather.getServiceByCode(matchDim);
            List<Map<String, Object>> baseDataList = pageVo.getRows().stream().map(e -> e).collect(Collectors.toList());
            List<DataRefDTO> resultList = serviceByCode.autoMatch(define, unRefData, baseDataList, dto);
            vo.record(matchDim, Integer.valueOf(resultList.size()));
            dataRefList.addAll(resultList);
        }
        if (!CollectionUtils.isEmpty((Collection)dataRefList)) {
            DataRefSaveDTO saveDto = new DataRefSaveDTO();
            saveDto.setData((List)dataRefList);
            saveDto.setDataSchemeCode(define.getDataSchemeCode());
            saveDto.setTableName(define.getCode());
            vo.setTempData(saveDto);
        }
        return vo;
    }

    private DataRefAutoMatchVO autoMatchBaseData(BaseDataMappingDefineDTO define, DataRefAutoMatchDTO dto) {
        String[] matchDimArr = define.getAutoMatchDim().split(",");
        if (matchDimArr.length == 0) {
            throw new BusinessRuntimeException(String.format("\u3010%1$s\u3011\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u7684\u81ea\u52a8\u6620\u5c04\u5b57\u6bb5\u4e3a\u7a7a\uff0c\u8bf7\u91cd\u65b0\u914d\u7f6e", dto.getTableName()));
        }
        DataRefListDTO condi = new DataRefListDTO();
        condi.setDataSchemeCode(dto.getDataSchemeCode());
        condi.setTableName(dto.getTableName());
        condi.setFilterType(DataRefFilterType.UNREF.getCode());
        condi.setPagination(false);
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.getByCode(dto.getDataSchemeCode());
        List unRefData = this.dataRefConfigureServiceGather.getDataRefConfigureServiceBySourceDataType(dataSchemeDTO.getSourceDataType()).list(condi).getPageVo().getRows();
        BaseDataDTO baseDataCondi = new BaseDataDTO();
        baseDataCondi.setTableName(dto.getTableName());
        baseDataCondi.setPagination(Boolean.FALSE);
        baseDataCondi.setAuthType(BaseDataOption.AuthType.NONE);
        PageVO pageVo = this.baseDataClient.list(baseDataCondi);
        DataRefAutoMatchVO vo = new DataRefAutoMatchVO();
        LinkedList dataRefList = CollectionUtils.newLinkedList();
        for (String matchDim : matchDimArr) {
            AutoMatchService serviceByCode = this.autoMatchServiceGather.getServiceByCode(matchDim);
            List<Map<String, Object>> baseDataList = pageVo.getRows().stream().map(e -> e).collect(Collectors.toList());
            List<DataRefDTO> resultList = serviceByCode.autoMatch(define, unRefData, baseDataList, dto);
            vo.record(matchDim, Integer.valueOf(resultList.size()));
            dataRefList.addAll(resultList);
        }
        if (!CollectionUtils.isEmpty((Collection)dataRefList)) {
            DataRefSaveDTO saveDto = new DataRefSaveDTO();
            saveDto.setData((List)dataRefList);
            saveDto.setDataSchemeCode(define.getDataSchemeCode());
            saveDto.setTableName(define.getCode());
            vo.setTempData(saveDto);
        }
        return vo;
    }

    @Override
    public String getDataSchemeCodeByUnitCode(String unitCode) {
        return this.dao.getDataSchemeCodeByUnitCode(unitCode);
    }

    @Override
    public List<BaseDataMappingDefineDTO> listDefineBySchemeCode(List<String> codes) {
        Assert.isNotEmpty(codes, (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        List<Object> result = CollectionUtils.newArrayList();
        HashMap dataCodes = CollectionUtils.newHashMap();
        HashMap extendFieldMap = CollectionUtils.newHashMap();
        for (String code : codes) {
            result.addAll(this.defineService.listBySchemeCode(code));
        }
        result = result.stream().filter(item -> {
            if (StringUtils.isEmpty((String)item.getAdvancedSql()) || "MD_CURRENCY".equals(item.getCode())) {
                return false;
            }
            List extendFieldDefineList = item.getItems().stream().filter(dto -> {
                if (RefDynamicField.containsRefFieldName(dto.getFieldName(), "MD_ORG".equals(item.getCode()) ? null : Integer.valueOf(1)) && !StringUtils.isEmpty((String)dto.getOdsFieldName())) {
                    dto.setFieldName(RefDynamicField.getFieldName(dto.getFieldName()));
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            extendFieldMap.computeIfAbsent(item.getCode(), v -> CollectionUtils.newHashSet()).addAll(extendFieldDefineList);
            ((Set)extendFieldMap.get(item.getCode())).addAll(IsolationUtil.buildIsolationFieldDefine(item.getIsolationStrategy()));
            if (dataCodes.containsKey(item.getCode())) {
                if (!StringUtils.isEmpty((String)((String)dataCodes.get(item.getCode()))) && !((String)dataCodes.get(item.getCode())).equals(item.getRuleType())) {
                    dataCodes.put(item.getCode(), null);
                }
                return false;
            }
            dataCodes.put(item.getCode(), item.getRuleType());
            return true;
        }).collect(Collectors.toList());
        result.sort((e1, e2) -> {
            String code1 = e1.getCode();
            String code2 = e2.getCode();
            SchemeBaseDataRefType type1 = null;
            SchemeBaseDataRefType type2 = null;
            for (SchemeBaseDataRefType type : SchemeBaseDataRefType.values()) {
                if (type.getCode().equals(code1)) {
                    type1 = type;
                }
                if (!type.getCode().equals(code2)) continue;
                type2 = type;
            }
            if (type1 != null && type2 != null) {
                return Integer.compare(type1.getOrder(), type2.getOrder());
            }
            if (type1 == null && type2 == null) {
                return code1.compareTo(code2);
            }
            return type1 != null ? -1 : 1;
        });
        result.forEach(item -> {
            item.setItems(new ArrayList((Collection)extendFieldMap.get(item.getCode())));
            item.setRuleType((String)dataCodes.get(item.getCode()));
        });
        return result;
    }
}

