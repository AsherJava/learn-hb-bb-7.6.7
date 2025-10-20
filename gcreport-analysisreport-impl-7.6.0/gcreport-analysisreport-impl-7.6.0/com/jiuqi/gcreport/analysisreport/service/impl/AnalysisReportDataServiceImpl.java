/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.file.entity.CommonFileClearEO
 *  com.jiuqi.common.file.service.CommonFileClearService
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDataDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDimensionDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportGeneratorDocDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportParseContextDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportWorkFlowDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisTempAndRefOrgDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.AnalysisReportDataExportExecutorParamDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqAddAnalysisReportDataVersionDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqOpenAnalysisReportDataDocParamDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqQueryAnalysisReportDatasDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqSaveAnalysisReportDataDocPageDTO
 *  com.jiuqi.gcreport.analysisreport.dto.resp.OpenAnalysisReportDocParamDTO
 *  com.jiuqi.gcreport.analysisreport.dto.resp.RespOpenAnalysisReportDataDocPageDTO
 *  com.jiuqi.gcreport.analysisreport.enums.AnalysisReportVersionState
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.todo.TaskDTO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 *  com.jiuqi.va.todo.controller.VaTodoAuditController
 *  com.jiuqi.va.todo.service.VaTodoTaskService
 *  com.jiuqi.va.workflow.service.WorkflowBusinessService
 *  com.zhuozhengsoft.pageoffice.FileSaver
 *  com.zhuozhengsoft.pageoffice.OpenModeType
 *  com.zhuozhengsoft.pageoffice.PageOfficeCtrl
 *  com.zhuozhengsoft.pageoffice.ThemeType
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.analysisreport.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.file.entity.CommonFileClearEO;
import com.jiuqi.common.file.service.CommonFileClearService;
import com.jiuqi.gcreport.analysisreport.authority.AnalysisReportTemplateAuthorityProvider;
import com.jiuqi.gcreport.analysisreport.converter.AnalysisReportDataConverter;
import com.jiuqi.gcreport.analysisreport.dao.AnalysisReportDataDao;
import com.jiuqi.gcreport.analysisreport.dao.AnalysisReportTemplateDao;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDataDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDimensionDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportGeneratorDocDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportParseContextDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportWorkFlowDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisTempAndRefOrgDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.AnalysisReportDataExportExecutorParamDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqAddAnalysisReportDataVersionDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqOpenAnalysisReportDataDocParamDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqQueryAnalysisReportDatasDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqSaveAnalysisReportDataDocPageDTO;
import com.jiuqi.gcreport.analysisreport.dto.resp.OpenAnalysisReportDocParamDTO;
import com.jiuqi.gcreport.analysisreport.dto.resp.RespOpenAnalysisReportDataDocPageDTO;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportDataEO;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportEO;
import com.jiuqi.gcreport.analysisreport.enums.AnalysisReportVersionState;
import com.jiuqi.gcreport.analysisreport.service.AnalysisReportDataService;
import com.jiuqi.gcreport.analysisreport.service.AnalysisReportTemplateService;
import com.jiuqi.gcreport.analysisreport.utils.AnalysisReportUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.todo.TaskDTO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import com.jiuqi.va.todo.controller.VaTodoAuditController;
import com.jiuqi.va.todo.service.VaTodoTaskService;
import com.jiuqi.va.workflow.service.WorkflowBusinessService;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.ThemeType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AnalysisReportDataServiceImpl
implements AnalysisReportDataService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AnalysisReportDataDao dataDao;
    @Autowired
    private AnalysisReportTemplateDao templateDao;
    @Autowired
    private CommonFileClearService commonFileClearService;
    @Autowired
    private AnalysisReportTemplateService templateService;
    @Autowired
    protected WorkflowServerClient workflowServerClient;
    @Autowired
    private WorkflowBusinessService workflowBusinessService;
    @Autowired
    private VaTodoAuditController vaTodoAuditController;
    @Autowired
    private VaTodoTaskService vaTodoTaskService;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private AnalysisReportTemplateAuthorityProvider authorityProvider;
    private final String BIZ_DEFINE = "ANALYSIS_REPORT";
    private final String BIZ_TYPE = "GC_ANALYSIS";

    @Override
    public AnalysisReportDTO queryAnalysisReportTree() {
        AnalysisReportDTO analysisReportDTO = this.templateService.queryAnalysisReportTree(true);
        return analysisReportDTO;
    }

    @Override
    public PageInfo<AnalysisReportDataDTO> queryAnalysisReportDatas(ReqQueryAnalysisReportDatasDTO queryAnalysisReportDatasDTO) {
        String templateId = queryAnalysisReportDatasDTO.getTemplateId();
        if (StringUtils.isEmpty((String)templateId)) {
            return PageInfo.empty();
        }
        List dimensions = queryAnalysisReportDatasDTO.getDimensions();
        Boolean showAllChilds = queryAnalysisReportDatasDTO.getShowAllChilds();
        List<OrgDataInfo> gcOrgCacheVOS = this.getDimensionChildOrgsContainsSelf(showAllChilds, dimensions);
        List<String> orgIds = gcOrgCacheVOS.stream().map(OrgDataInfo::getCode).collect(Collectors.toList());
        String dimensionsLikeCondition = AnalysisReportUtils.buildDataDimensionsLikeConditionIngoreOrgDim(dimensions);
        PageInfo<AnalysisReportDataEO> savedDataEOsPageInfo = this.dataDao.queryByTemplateIdAndLikeDimValues(templateId, orgIds, dimensionsLikeCondition, queryAnalysisReportDatasDTO);
        List<AnalysisReportDataDTO> savedDataDTOS = AnalysisReportDataConverter.convertEO2DTOs(savedDataEOsPageInfo.getList());
        int pageNum = savedDataEOsPageInfo.getPageNum();
        int pageSize = savedDataEOsPageInfo.getPageSize();
        int savedDataSize = savedDataEOsPageInfo.getSize();
        ArrayList<AnalysisReportDataDTO> realTimeDatas = new ArrayList<AnalysisReportDataDTO>();
        if (queryAnalysisReportDatasDTO.getShowLastestVersion().booleanValue()) {
            if (CollectionUtils.isEmpty(orgIds)) {
                AnalysisReportDataDTO realTimeDataDTO = this.buildRealTimeAnalysisReportDataDTO(templateId, null, dimensions);
                realTimeDatas.add(realTimeDataDTO);
            } else {
                for (OrgDataInfo orgCacheVO : gcOrgCacheVOS) {
                    AnalysisReportDataDTO realTimeDataDTO = this.buildRealTimeAnalysisReportDataDTO(templateId, orgCacheVO, dimensions);
                    realTimeDatas.add(realTimeDataDTO);
                }
            }
        }
        ArrayList<AnalysisReportDataDTO> dtos = new ArrayList<AnalysisReportDataDTO>(pageSize);
        dtos.addAll(realTimeDatas);
        dtos.addAll(savedDataDTOS);
        PageInfo pageInfoDTOs = PageInfo.of(dtos, (int)pageNum, (int)pageSize, (int)(savedDataSize + realTimeDatas.size()));
        return pageInfoDTOs;
    }

    private List<OrgDataInfo> getDimensionChildOrgsContainsSelf(Boolean showAllChilds, List<AnalysisReportDimensionDTO> dimensions) {
        if (CollectionUtils.isEmpty(dimensions)) {
            return Collections.emptyList();
        }
        Optional<AnalysisReportDimensionDTO> orgDimensionOptional = dimensions.stream().filter(dimension -> dimension.getViewKey().contains("@ORG")).findFirst();
        String orgDimId = null;
        String orgType = null;
        if (orgDimensionOptional.isPresent()) {
            AnalysisReportDimensionDTO orgDimensionDTO = orgDimensionOptional.get();
            orgDimId = orgDimensionDTO.getCode();
            orgType = (String)StringUtils.split((String)orgDimensionDTO.getViewKey(), (String)"@").get(0);
        }
        if (StringUtils.isEmpty(orgDimId)) {
            return Collections.emptyList();
        }
        List<OrgDataInfo> orgs = new ArrayList<OrgDataInfo>();
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgDTO.setCategoryname(orgType);
        orgDTO.setCode(orgDimId);
        orgDTO.setStopflag(Integer.valueOf(0));
        orgDTO.setRecoveryflag(Integer.valueOf(0));
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        if (Boolean.TRUE.equals(showAllChilds)) {
            orgDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
            PageVO list = this.orgDataClient.list(orgDTO);
            if (list.getRows() != null && list.getRows().size() > 0) {
                orgs = list.getRows().stream().map(v -> new OrgDataInfo(v.getCode(), v.getShowTitle(), orgDTO.getCategoryname())).collect(Collectors.toList());
            }
        } else {
            OrgDO orgDO = this.orgDataClient.get(orgDTO);
            if (orgDO == null) {
                throw new BusinessRuntimeException("\u7c7b\u578b\u4e3a" + orgType + ",\u4ee3\u7801\u4e3a" + orgDimId + "\u7684\u5355\u4f4d\u4e0d\u5b58\u5728\u3002");
            }
            orgs.add(new OrgDataInfo(orgDO.getCode(), orgDO.getShowTitle(), orgDTO.getCategoryname()));
        }
        return orgs;
    }

    private AnalysisReportDataDTO buildRealTimeAnalysisReportDataDTO(String templateId, OrgDataInfo orgCacheVO, List<AnalysisReportDimensionDTO> dimensions) {
        AnalysisReportEO analysisReportEO = (AnalysisReportEO)this.templateDao.get((Serializable)((Object)templateId));
        AnalysisReportDataDTO dataDTO = new AnalysisReportDataDTO();
        dataDTO.setTemplateId(templateId);
        if (analysisReportEO != null) {
            dataDTO.setTemplateTitle(analysisReportEO.getTitle());
        }
        dataDTO.setId(null);
        dataDTO.setVersionName("");
        dataDTO.setCreator(NpContextHolder.getContext().getUserName());
        dataDTO.setCreateTime(new Date());
        dataDTO.setFlowState(UploadState.ORIGINAL_UPLOAD.name());
        String dimensionsValue = AnalysisReportUtils.buildDataDimensionsValueIngoreOrgDim(dimensions);
        dataDTO.setDimensions(dimensionsValue);
        if (orgCacheVO != null) {
            dataDTO.setOrgId(orgCacheVO.getCode());
            dataDTO.setOrgTitle(orgCacheVO.getTitle());
            String orgType = orgCacheVO.getOrgtypeid();
            dataDTO.setOrgType(orgType);
        }
        return dataDTO;
    }

    @Override
    public AnalysisReportGeneratorDocDTO addAnalysisReportDataVersion(HttpServletRequest request, HttpServletResponse response, ReqAddAnalysisReportDataVersionDTO reportDataVersionDTO) throws Exception {
        AnalysisReportGeneratorDocDTO docDTO;
        if (StringUtils.isEmpty((String)reportDataVersionDTO.getVersionName())) {
            throw new BusinessRuntimeException("\u53e6\u5b58\u4e3a\u7248\u672c\u540d\u79f0\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        AnalysisReportDataDTO originData = reportDataVersionDTO.getOriginData();
        Objects.requireNonNull(originData, "\u6e90\u6570\u636e\u4e3a\u7a7a\u65f6\u4e0d\u5141\u8bb8\u8fdb\u884c\u53e6\u5b58\u4e3a\u64cd\u4f5c\u3002");
        String versionName = reportDataVersionDTO.getVersionName();
        boolean existsByTemplateIdAndDimValuesAndVersion = this.dataDao.isExistsByTemplateIdAndDimValuesAndVersion(originData.getTemplateId(), originData.getOrgId(), originData.getDimensions(), versionName);
        if (existsByTemplateIdAndDimValuesAndVersion) {
            throw new BusinessRuntimeException("\u540c\u4e00\u7ef4\u5ea6\u4e0b\u7684\u5206\u6790\u62a5\u544a\u7248\u672c\u540d\u79f0[" + versionName + "]\u5df2\u7ecf\u5b58\u5728\uff0c\u4e0d\u5141\u8bb8\u91cd\u590d\u4fdd\u5b58\u3002");
        }
        String originDocFileKey = reportDataVersionDTO.getOriginData().getDocFileKey();
        String currentDocFileKey = null;
        if (StringUtils.isEmpty((String)originDocFileKey)) {
            AnalysisReportParseContextDTO parseContextDTO = reportDataVersionDTO.getContext();
            docDTO = this.generatorRealTimeAnalysisReportDoc(request, parseContextDTO, originData.getTemplateId());
            String confirmMessage = docDTO.getConfirmMessage();
            ObjectInfo objectInfo = docDTO.getObjectInfo();
            if (!StringUtils.isEmpty((String)confirmMessage) && !reportDataVersionDTO.isConfirmOnPartTemplateReportData()) {
                return docDTO;
            }
            if (objectInfo != null) {
                currentDocFileKey = objectInfo.getKey();
            }
        } else {
            docDTO = new AnalysisReportGeneratorDocDTO();
            currentDocFileKey = UUIDUtils.newUUIDStr();
            ObjectInfo objectInfo = AnalysisReportUtils.copyAsUploadOssFile(originDocFileKey, currentDocFileKey);
            docDTO.setObjectInfo(objectInfo);
        }
        AnalysisReportDataEO analysisReportDataEO = new AnalysisReportDataEO();
        BeanUtils.copyProperties(originData, (Object)analysisReportDataEO);
        analysisReportDataEO.setId(UUIDUtils.newUUIDStr());
        analysisReportDataEO.setVersionName(versionName);
        analysisReportDataEO.setCreator(NpContextHolder.getContext().getUserName());
        analysisReportDataEO.setCreateTime(new Date());
        analysisReportDataEO.setSortOrder(System.currentTimeMillis());
        analysisReportDataEO.setFlowState(UploadState.ORIGINAL_UPLOAD.name());
        analysisReportDataEO.setDocFileKey(currentDocFileKey);
        this.dataDao.save(analysisReportDataEO);
        return docDTO;
    }

    @Override
    public Boolean deleteAnalysisReportData(Set<String> analysisReportDataIds) {
        List<AnalysisReportDataEO> deleteEOs = this.dataDao.queryByIds(analysisReportDataIds);
        if (CollectionUtils.isEmpty(deleteEOs)) {
            return false;
        }
        this.dataDao.deleteBatch(deleteEOs);
        Set docFileKeys = deleteEOs.stream().map(AnalysisReportDataEO::getDocFileKey).filter(Objects::nonNull).collect(Collectors.toSet());
        this.commonFileClearService.addFileClearData(docFileKeys.stream().map(docFileKey -> new CommonFileClearEO("ONLINE_OFFICE", docFileKey, "\u5408\u5e76\u62a5\u8868\u5206\u6790\u62a5\u544a-\u5220\u9664\u5206\u6790\u62a5\u544a\u540e\u5f85\u6e05\u7406\u7684\u6587\u4ef6")).collect(Collectors.toList()));
        return true;
    }

    @Override
    public Boolean upAnalysisReportData(String analysisReportDataId) {
        if (StringUtils.isEmpty((String)analysisReportDataId)) {
            throw new BusinessRuntimeException("\u5b9e\u65f6\u7248\u672c\u4e0d\u5141\u8bb8\u4e0a\u79fb\u3002");
        }
        AnalysisReportDataEO currentNodeEO = (AnalysisReportDataEO)this.dataDao.get((Serializable)((Object)analysisReportDataId));
        if (currentNodeEO == null) {
            return true;
        }
        AnalysisReportDataEO previousNode = this.dataDao.queryPreviousItemById(currentNodeEO.getId(), currentNodeEO.getTemplateId(), currentNodeEO.getDimensions());
        if (previousNode == null) {
            return true;
        }
        Long currentSortOrder = currentNodeEO.getSortOrder();
        Long previousSortOrder = previousNode.getSortOrder();
        currentNodeEO.setSortOrder(previousSortOrder);
        previousNode.setSortOrder(currentSortOrder);
        this.dataDao.updateBatch(Arrays.asList(currentNodeEO, previousNode));
        return Boolean.TRUE;
    }

    @Override
    public Boolean downAnalysisReportData(String analysisReportDataId) {
        if (StringUtils.isEmpty((String)analysisReportDataId)) {
            throw new BusinessRuntimeException("\u5b9e\u65f6\u7248\u672c\u4e0d\u5141\u8bb8\u4e0b\u79fb\u3002");
        }
        AnalysisReportDataEO currentNodeEO = (AnalysisReportDataEO)this.dataDao.get((Serializable)((Object)analysisReportDataId));
        if (currentNodeEO == null) {
            return true;
        }
        AnalysisReportDataEO nextNode = this.dataDao.queryNextItemById(analysisReportDataId, currentNodeEO.getTemplateId(), currentNodeEO.getDimensions());
        if (nextNode == null) {
            return true;
        }
        Long currentSortOrder = currentNodeEO.getSortOrder();
        Long nextSortOrder = nextNode.getSortOrder();
        currentNodeEO.setSortOrder(nextSortOrder);
        nextNode.setSortOrder(currentSortOrder);
        this.dataDao.updateBatch(Arrays.asList(currentNodeEO, nextNode));
        return Boolean.TRUE;
    }

    @Override
    public Boolean uploadAnalysisReportData(String analysisReportDataId) {
        if (StringUtils.isEmpty((String)analysisReportDataId)) {
            throw new BusinessRuntimeException("\u5b9e\u65f6\u7248\u672c\u4e0d\u5141\u8bb8\u4e0a\u62a5\u3002");
        }
        AnalysisReportDataEO analysisReportDataEO = (AnalysisReportDataEO)this.dataDao.get((Serializable)((Object)analysisReportDataId));
        Objects.requireNonNull(analysisReportDataEO, "\u5206\u6790\u62a5\u544a\u7ed3\u679c\u6570\u636e\u5df2\u5931\u6548\uff0c\u8bf7\u5237\u65b0\u91cd\u8bd5\u3002");
        analysisReportDataEO.setUpdator(NpContextHolder.getContext().getUserName());
        analysisReportDataEO.setUpdateTime(new Date());
        if (this.isStartProcess(analysisReportDataEO).booleanValue()) {
            return this.startProcess(analysisReportDataEO);
        }
        return this.audit(analysisReportDataEO, 1);
    }

    private Boolean isStartProcess(AnalysisReportDataEO analysisReportDataEO) {
        if (Objects.equals(analysisReportDataEO.getFlowState(), UploadState.ORIGINAL_UPLOAD.name())) {
            return true;
        }
        if (Objects.equals(analysisReportDataEO.getFlowState(), UploadState.REJECTED.name())) {
            return this.getIsProcess(analysisReportDataEO.getId());
        }
        return false;
    }

    private Boolean getIsProcessEnd(AnalysisReportDataEO analysisReportDataEO) {
        if (Objects.equals(analysisReportDataEO.getFlowState(), UploadState.ORIGINAL_UPLOAD.name())) {
            return false;
        }
        return this.getIsProcess(analysisReportDataEO.getId());
    }

    private Boolean getIsProcess(String analysisReportDataId) {
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(analysisReportDataId);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        return processDO == null;
    }

    private Boolean completeTask(AnalysisReportDataEO analysisReportDataEO, int taskStatus) {
        WorkflowDTO workflowDTO = new WorkflowDTO();
        workflowDTO.setTaskId(this.getProcessTaskId(analysisReportDataEO));
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(analysisReportDataEO.getId());
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        String uniqueCode = this.getUniqueCodeForBusinessCode();
        workflowDTO.setUniqueCode(uniqueCode);
        workflowDTO.setBizCode(analysisReportDataEO.getId());
        workflowDTO.setBizType("GC_ANALYSIS");
        workflowDTO.setBizDefine("ANALYSIS_REPORT");
        workflowDTO.setProcessInstanceId(processDO.getId());
        workflowDTO.setApprovalResult(Integer.valueOf(taskStatus));
        HashMap<String, String> key2Value = new HashMap<String, String>();
        key2Value.put("CREATEUSER", ShiroUtil.getUser().getId());
        workflowDTO.setExtInfo(key2Value);
        R r = this.workflowServerClient.completeTask(workflowDTO);
        if (0 != r.getCode()) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u5ba1\u6279\u5931\u8d25\uff1a" + r.getMsg());
            throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u5ba1\u6279\u5931\u8d25\uff1a" + r.getMsg());
        }
        return this.updateWorkFlowStatus(analysisReportDataEO, taskStatus);
    }

    private String getProcessTaskId(AnalysisReportDataEO analysisReportDataEO) {
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(analysisReportDataEO.getId());
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO == null) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u6d41\u7a0b\u5df2\u7ed3\u675f\u6216\u672a\u5f00\u59cb," + analysisReportDataEO.getId());
            throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u6d41\u7a0b\u5df2\u7ed3\u675f\u6216\u672a\u5f00\u59cb");
        }
        TaskDTO taskDTOTemp = new TaskDTO();
        taskDTOTemp.setParticipant(ShiroUtil.getUser().getId());
        taskDTOTemp.setProcessId(processDO.getId());
        List mapList = this.vaTodoTaskService.list(taskDTOTemp);
        if (mapList.size() == 0) {
            this.logger.error("\u5f53\u524d\u7528\u6237\u6ca1\u6709\u64cd\u4f5c\u6743\u9650\u3002");
            throw new BusinessRuntimeException("\u5f53\u524d\u7528\u6237\u6ca1\u6709\u64cd\u4f5c\u6743\u9650\u3002");
        }
        return ((Map)mapList.get(0)).get("TASKID").toString();
    }

    private Boolean audit(AnalysisReportDataEO analysisReportDataEO, int taskStatus) {
        WorkflowDTO workflowDTO = new WorkflowDTO();
        if (taskStatus == 1) {
            workflowDTO.setApprovalComment("\u540c\u610f");
        } else {
            workflowDTO.setApprovalComment("\u4e0d\u540c\u610f");
        }
        workflowDTO.setApprovalResult(Integer.valueOf(taskStatus));
        workflowDTO.setCommonComment(false);
        workflowDTO.setTaskId(this.getProcessTaskId(analysisReportDataEO));
        R r = this.vaTodoAuditController.audit(workflowDTO);
        if (0 != r.getCode()) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u5ba1\u6279\u5904\u7406\u5931\u8d25\uff1a" + r.getMsg());
            throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u5ba1\u6279\u5904\u7406\u5931\u8d25\uff1a" + r.getMsg());
        }
        return this.updateWorkFlowStatus(analysisReportDataEO, taskStatus);
    }

    private Boolean startProcess(AnalysisReportDataEO analysisReportDataEO) {
        String uniqueCode = this.getUniqueCodeForBusinessCode();
        WorkflowDTO workflowDTO = new WorkflowDTO();
        workflowDTO.setUniqueCode(uniqueCode);
        workflowDTO.setBizType("GC_ANALYSIS");
        workflowDTO.setBizDefine("ANALYSIS_REPORT");
        workflowDTO.setBizCode(analysisReportDataEO.getId());
        HashMap<String, String> key2Value = new HashMap<String, String>();
        key2Value.put("CREATEUSER", ShiroUtil.getUser().getId());
        workflowDTO.setExtInfo(key2Value);
        R r = this.workflowServerClient.startProcess(workflowDTO);
        if (0 != r.getCode()) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u4e0a\u62a5\u5931\u8d25\uff1a" + r.getMsg());
            throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u4e0a\u62a5\u5931\u8d25\uff1a" + r.getMsg());
        }
        return this.updateWorkFlowStatus(analysisReportDataEO, 1);
    }

    private Boolean updateWorkFlowStatus(AnalysisReportDataEO analysisReportDataEO, int taskStatus) {
        if (taskStatus == 1) {
            if (this.getIsProcessEnd(analysisReportDataEO).booleanValue()) {
                analysisReportDataEO.setFlowState(UploadState.CONFIRMED.name());
            } else {
                Map<String, Object> dataMap = this.currNodeProperties(analysisReportDataEO);
                if (!dataMap.containsKey("documentation")) {
                    this.logger.error("\u5206\u6790\u62a5\u544a\u83b7\u53d6\u8282\u70b9\u63cf\u8ff0\u4fe1\u606f\u4e3a\u7a7a,\u8bf7\u8bbe\u7f6e\u8282\u70b9\u4fe1\u606f\u3002");
                    throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u83b7\u53d6\u8282\u70b9\u63cf\u8ff0\u4fe1\u606f\u4e3a\u7a7a,\u8bf7\u8bbe\u7f6e\u8282\u70b9\u4fe1\u606f\u3002");
                }
                String documentation = dataMap.get("documentation").toString();
                analysisReportDataEO.setFlowState(documentation);
            }
        } else {
            analysisReportDataEO.setFlowState(UploadState.REJECTED.name());
        }
        return this.dataDao.updateAnalysisReportDataById(analysisReportDataEO);
    }

    private Map<String, Object> currNodeProperties(AnalysisReportDataEO analysisReportDataEO) {
        WorkflowDTO workflowDTO = new WorkflowDTO();
        workflowDTO.setBizCode(analysisReportDataEO.getId());
        R r = this.workflowServerClient.currNodeProperties(workflowDTO);
        if (0 != r.getCode()) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u83b7\u53d6\u8282\u70b9\u4fe1\u606f\u5931\u8d25\uff1a" + r.getMsg());
            throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u83b7\u53d6\u8282\u70b9\u4fe1\u606f\u5931\u8d25\uff1a" + r.getMsg());
        }
        return (Map)r.get((Object)"nodeProperties");
    }

    @Override
    public String getUniqueCodeForBusinessCode() {
        WorkflowBusinessDTO business = new WorkflowBusinessDTO();
        business.setBusinesscode("ANALYSIS_REPORT");
        business.setShowTitle(true);
        WorkflowBusinessDTO workflowBusinessDTO = this.workflowBusinessService.get(business, true);
        if (workflowBusinessDTO == null || workflowBusinessDTO.getWorkflows().size() == 0) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u83b7\u53d6\u5de5\u4f5c\u6d41\u5931\u8d25\u3002");
            throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u83b7\u53d6\u5de5\u4f5c\u6d41\u5931\u8d25\u3002");
        }
        List ja = workflowBusinessDTO.getWorkflows();
        Map billWorkflowRelation = (Map)ja.get(0);
        if (billWorkflowRelation.containsKey("workflowdefinekey")) {
            return String.valueOf(billWorkflowRelation.get("workflowdefinekey"));
        }
        this.logger.error("\u5206\u6790\u62a5\u544a\u672a\u7ed1\u5b9a\u5de5\u4f5c\u6d41\uff0c\u8bf7\u91cd\u65b0\u7ed1\u5b9a\u3002");
        throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u672a\u7ed1\u5b9a\u5de5\u4f5c\u6d41\uff0c\u8bf7\u91cd\u65b0\u7ed1\u5b9a\u3002");
    }

    @Override
    public Boolean rejectAnalysisReportData(String analysisReportDataId) {
        if (StringUtils.isEmpty((String)analysisReportDataId)) {
            throw new BusinessRuntimeException("\u5b9e\u65f6\u7248\u672c\u4e0d\u5141\u8bb8\u9000\u56de\u3002");
        }
        AnalysisReportDataEO analysisReportDataEO = (AnalysisReportDataEO)this.dataDao.get((Serializable)((Object)analysisReportDataId));
        Objects.requireNonNull(analysisReportDataEO, "\u5206\u6790\u62a5\u544a\u7ed3\u679c\u6570\u636e\u5df2\u5931\u6548\uff0c\u8bf7\u5237\u65b0\u91cd\u8bd5\u3002");
        analysisReportDataEO.setUpdator(NpContextHolder.getContext().getUserName());
        analysisReportDataEO.setUpdateTime(new Date());
        return this.completeTask(analysisReportDataEO, 2);
    }

    @Override
    public Boolean confirmAnalysisReportData(String analysisReportDataId) {
        if (StringUtils.isEmpty((String)analysisReportDataId)) {
            throw new BusinessRuntimeException("\u5b9e\u65f6\u7248\u672c\u4e0d\u5141\u8bb8\u786e\u8ba4\u3002");
        }
        AnalysisReportDataEO analysisReportDataEO = (AnalysisReportDataEO)this.dataDao.get((Serializable)((Object)analysisReportDataId));
        Objects.requireNonNull(analysisReportDataEO, "\u5206\u6790\u62a5\u544a\u7ed3\u679c\u6570\u636e\u5df2\u5931\u6548\uff0c\u8bf7\u5237\u65b0\u91cd\u8bd5\u3002");
        analysisReportDataEO.setUpdator(NpContextHolder.getContext().getUserName());
        analysisReportDataEO.setUpdateTime(new Date());
        analysisReportDataEO.setFlowState(UploadState.CONFIRMED.name());
        return Boolean.TRUE;
    }

    @Override
    public OpenAnalysisReportDocParamDTO generatorOpenAnalysisReportDocParam(HttpServletRequest request, HttpServletResponse response, ReqOpenAnalysisReportDataDocParamDTO envDTO) throws Exception {
        boolean isShowSaveButton;
        boolean isReadOnly;
        String docFileKey;
        String dataId = envDTO.getDataId();
        String confirmMessage = null;
        if (StringUtils.isEmpty((String)dataId)) {
            AnalysisReportGeneratorDocDTO docDTO = this.generatorRealTimeAnalysisReportDoc(request, envDTO.getContext(), envDTO.getTemplateId());
            ObjectInfo wordOssFileInfo = docDTO.getObjectInfo();
            docFileKey = wordOssFileInfo.getKey();
            confirmMessage = docDTO.getConfirmMessage();
            isReadOnly = true;
            isShowSaveButton = false;
            this.commonFileClearService.addFileClearData(Arrays.asList(new CommonFileClearEO("ONLINE_OFFICE", wordOssFileInfo.getKey(), "\u5408\u5e76\u62a5\u8868\u5206\u6790\u62a5\u544a-\u7f16\u8f91\u5b9e\u65f6\u7248\u672c\u65f6\u751f\u6210\u7684\u4e34\u65f6\u6587\u4ef6")));
        } else {
            AnalysisReportDataEO analysisReportDataEO = (AnalysisReportDataEO)this.dataDao.get((Serializable)((Object)dataId));
            Objects.requireNonNull(analysisReportDataEO, "\u5206\u6790\u62a5\u544a\u7ed3\u679c\u6570\u636e\u5df2\u5931\u6548\uff0c\u8bf7\u5237\u65b0\u91cd\u8bd5\u3002");
            docFileKey = analysisReportDataEO.getDocFileKey();
            Objects.requireNonNull(docFileKey, "\u5206\u6790\u62a5\u544a\u7ed3\u679c\u6570\u636e\u5173\u8054\u7684\u6587\u6863\u5df2\u5931\u6548\uff0c\u8bf7\u5237\u65b0\u91cd\u8bd5\u3002");
            String flowState = analysisReportDataEO.getFlowState();
            if (UploadState.ORIGINAL_UPLOAD.name().equals(flowState) || UploadState.REJECTED.name().equals(flowState)) {
                isReadOnly = false;
                isShowSaveButton = true;
            } else {
                isReadOnly = true;
                isShowSaveButton = false;
            }
        }
        OpenAnalysisReportDocParamDTO openAnalysisReportDocParamDTO = new OpenAnalysisReportDocParamDTO();
        openAnalysisReportDocParamDTO.setDocFileKey(docFileKey);
        openAnalysisReportDocParamDTO.setShowSaveButton(isShowSaveButton);
        openAnalysisReportDocParamDTO.setReadOnly(isReadOnly);
        openAnalysisReportDocParamDTO.setDataId(dataId);
        openAnalysisReportDocParamDTO.setConfirmMessage(confirmMessage);
        return openAnalysisReportDocParamDTO;
    }

    private AnalysisReportGeneratorDocDTO generatorRealTimeAnalysisReportDoc(HttpServletRequest request, AnalysisReportParseContextDTO context, String templateId) throws Exception {
        ObjectInfo wordOssFileInfo;
        Objects.requireNonNull(templateId, "\u5206\u6790\u62a5\u544a\u6a21\u677f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        AnalysisReportEO analysisReportEO = this.templateService.queryTemplateByTemplateId(templateId);
        Objects.requireNonNull(analysisReportEO, "\u5206\u6790\u62a5\u544a\u6a21\u677f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        List chooseUnits = context.getChooseUnits();
        ArrayList<AnalysisReportDimensionDTO> dimensions = new ArrayList<AnalysisReportDimensionDTO>();
        if (!CollectionUtils.isEmpty(chooseUnits)) {
            chooseUnits.stream().forEach(chooseUnit -> {
                AnalysisReportDimensionDTO dimensionDTO = new AnalysisReportDimensionDTO();
                dimensionDTO.setViewKey(chooseUnit.getViewKey());
                dimensionDTO.setTitle(chooseUnit.getTitle());
                dimensionDTO.setKey(chooseUnit.getKey());
                dimensionDTO.setCode(chooseUnit.getCode());
                dimensions.add(dimensionDTO);
            });
        }
        Optional<AnalysisReportDimensionDTO> orgDimensionOptional = dimensions.stream().filter(dimension -> dimension.getViewKey().contains("@ORG")).findFirst();
        String orgDimId = null;
        if (orgDimensionOptional.isPresent()) {
            AnalysisReportDimensionDTO orgDimensionDTO = orgDimensionOptional.get();
            orgDimId = orgDimensionDTO.getCode();
        }
        ArrayList<String> orgIds = new ArrayList<String>();
        if (!StringUtils.isEmpty(orgDimId)) {
            orgIds.add(orgDimId);
        }
        String dimensionsLikeCondition = AnalysisReportUtils.buildDataDimensionsLikeConditionIngoreOrgDim(dimensions);
        AnalysisReportVersionState versionState = AnalysisReportVersionState.getEnumByCode((String)analysisReportEO.getVersionState());
        StringBuilder confirmMessage = new StringBuilder();
        switch (versionState) {
            case LATEST_SAVED: 
            case LATEST_CONFIRMED: {
                LinkedHashMap itemTemplateId2TitleMap = new LinkedHashMap();
                AnalysisReportDTO root = this.templateService.queryAnalysisReportTree(false);
                String refIdsStr = analysisReportEO.getRefIds();
                List refIds = StringUtils.split((String)refIdsStr, (String)",");
                refIds.stream().forEach(refId -> {
                    AnalysisReportDTO nodeById = AnalysisReportUtils.findNodeById(refId, root);
                    if (nodeById == null) {
                        return;
                    }
                    if ("item".equals(nodeById.getNodeType())) {
                        itemTemplateId2TitleMap.put(nodeById.getId(), nodeById.getTitle());
                        return;
                    }
                    if ("group".equals(nodeById.getNodeType())) {
                        List<AnalysisReportDTO> allLeafNodesByParentId = AnalysisReportUtils.findAllLeafNodesByParentId(nodeById.getId(), root);
                        if (CollectionUtils.isEmpty(allLeafNodesByParentId)) {
                            return;
                        }
                        allLeafNodesByParentId.stream().forEach(analysisReportDTO -> itemTemplateId2TitleMap.put(analysisReportDTO.getId(), analysisReportDTO.getTitle()));
                        return;
                    }
                });
                Set itemTemplateIds = itemTemplateId2TitleMap.keySet();
                List<AnalysisReportDataEO> versionDataEOs = this.dataDao.queryByTemplateIdsAndLikeDimValuesAndVersionState(new ArrayList<String>(itemTemplateIds), orgIds, dimensionsLikeCondition, versionState);
                Set versionDataTemplateIds = versionDataEOs.stream().map(AnalysisReportDataEO::getTemplateId).filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new));
                itemTemplateIds.removeAll(versionDataTemplateIds);
                if (!CollectionUtils.isEmpty(itemTemplateIds)) {
                    String message = itemTemplateIds.stream().map(notDataItemTemplateId -> (String)itemTemplateId2TitleMap.get(notDataItemTemplateId)).filter(Objects::nonNull).collect(Collectors.joining("\u3001"));
                    confirmMessage.append(message).append("\u6a21\u677f\u6ca1\u6709").append(versionState.getTitle()).append("\u6570\u636e\uff0c\u662f\u5426\u7ee7\u7eed");
                }
                List<String> confirmOssFileKeys = versionDataEOs.stream().map(AnalysisReportDataEO::getDocFileKey).filter(Objects::nonNull).collect(Collectors.toList());
                wordOssFileInfo = AnalysisReportUtils.generatorRealTimeMergeDocFileBySubOssFileKeys(templateId, confirmOssFileKeys);
                break;
            }
            default: {
                List<AnalysisTempAndRefOrgDTO> analysisTempAndRefOrgDTOS = this.templateService.queryAnalysisTempsByTemplateId(templateId);
                wordOssFileInfo = AnalysisReportUtils.generatorMergeDocFileByRealTimeSubTemplate(request, context, templateId, analysisTempAndRefOrgDTOS);
            }
        }
        if (wordOssFileInfo == null) {
            throw new BusinessRuntimeException("\u65e0\u7b26\u5408\u7684\u5206\u6790\u62a5\u544a\u3002");
        }
        AnalysisReportGeneratorDocDTO docDTO = new AnalysisReportGeneratorDocDTO(wordOssFileInfo, confirmMessage.toString());
        return docDTO;
    }

    @Override
    public RespOpenAnalysisReportDataDocPageDTO openAnalysisReportDataDocPage(HttpServletRequest request, HttpServletResponse response, String paramsOpenAnalysisReportDocParamDTO) throws Exception {
        OpenAnalysisReportDocParamDTO openAnalysisReportDocParamDTO = (OpenAnalysisReportDocParamDTO)JsonUtils.readValue((String)paramsOpenAnalysisReportDocParamDTO, OpenAnalysisReportDocParamDTO.class);
        String docFileKey = openAnalysisReportDocParamDTO.getDocFileKey();
        boolean isReadOnly = openAnalysisReportDocParamDTO.isReadOnly();
        boolean showSaveButton = openAnalysisReportDocParamDTO.isShowSaveButton();
        PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
        String wordFileOpenUrl = AnalysisReportUtils.generatorWordOpenFileUrl(docFileKey);
        poCtrl.setCaption("\u5206\u6790\u62a5\u544a\u7ed3\u679c\u67e5\u770b");
        if (showSaveButton) {
            poCtrl.addCustomToolButton("\u4fdd\u5b58", "Save", 1);
        }
        if (isReadOnly) {
            poCtrl.setOfficeToolbars(false);
        }
        poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
        poCtrl.addCustomToolButton("\u6253\u5370\u8bbe\u7f6e", "PrintSet", 0);
        poCtrl.addCustomToolButton("\u6253\u5370", "PrintFile", 6);
        poCtrl.addCustomToolButton("\u5168\u5c4f/\u8fd8\u539f", "IsFullScreen", 4);
        poCtrl.setTheme(ThemeType.Office2010);
        poCtrl.setTitlebar(false);
        ReqSaveAnalysisReportDataDocPageDTO reqSaveAnalysisReportDataDocPageDTO = new ReqSaveAnalysisReportDataDocPageDTO();
        reqSaveAnalysisReportDataDocPageDTO.setDataId(openAnalysisReportDocParamDTO.getDataId());
        String saveFilePageUrl = AnalysisReportUtils.generatorWordSaveFilePageUrl(reqSaveAnalysisReportDataDocPageDTO);
        poCtrl.setSaveFilePage(saveFilePageUrl);
        poCtrl.webOpen(wordFileOpenUrl, isReadOnly ? OpenModeType.docReadOnly : OpenModeType.docNormalEdit, NpContextHolder.getContext().getUserName());
        poCtrl.setTagId("PageOfficeCtrl1");
        String docHtmlCode = poCtrl.getHtmlCode("PageOfficeCtrl1");
        RespOpenAnalysisReportDataDocPageDTO analysisReportDataDocPageDTO = new RespOpenAnalysisReportDataDocPageDTO();
        analysisReportDataDocPageDTO.setDocHtmlCode(docHtmlCode);
        analysisReportDataDocPageDTO.setDocHtmlTitle("\u5206\u6790\u62a5\u544a\u7ed3\u679c\u67e5\u770b");
        return analysisReportDataDocPageDTO;
    }

    @Override
    public void saveAnalysisReportDataDocPage(HttpServletRequest request, HttpServletResponse response, String params) {
        ReqSaveAnalysisReportDataDocPageDTO saveAnalysisReportDataDocPageDTO = (ReqSaveAnalysisReportDataDocPageDTO)JsonUtils.readValue((String)params, ReqSaveAnalysisReportDataDocPageDTO.class);
        String dataId = saveAnalysisReportDataDocPageDTO.getDataId();
        if (StringUtils.isEmpty((String)dataId)) {
            throw new BusinessRuntimeException("\u5b9e\u65f6\u7248\u672c\u4e0d\u5141\u8bb8\u4fdd\u5b58\u3002");
        }
        AnalysisReportDataEO dataEO = (AnalysisReportDataEO)this.dataDao.get((Serializable)((Object)dataId));
        if (dataEO == null) {
            throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u7ed3\u679c\u6570\u636e\u5df2\u5931\u6548\uff0c\u8bf7\u5237\u65b0\u91cd\u8bd5\u3002");
        }
        String docFileKey = StringUtils.isEmpty((String)dataEO.getDocFileKey()) ? UUIDUtils.newUUIDStr() : dataEO.getDocFileKey();
        String docFilename = docFileKey + ".doc";
        FileSaver fs = new FileSaver(request, response);
        byte[] fileBytes = fs.getFileBytes();
        AnalysisReportUtils.uploadFileToOss(docFileKey, docFilename, fileBytes);
        dataEO.setUpdateTime(new Date());
        dataEO.setUpdator(NpContextHolder.getContext().getUserName());
        dataEO.setDocFileKey(docFileKey);
        this.dataDao.update((BaseEntity)dataEO);
        fs.setCustomSaveResult("ok");
        fs.close();
    }

    @Override
    public void exportFile(HttpServletRequest request, HttpServletResponse response, AnalysisReportDataExportExecutorParamDTO exportParamDTO) throws Exception {
        Set dataIds = exportParamDTO.getDataIds();
        String docFileKey = null;
        if (dataIds.size() == 1) {
            AnalysisReportGeneratorDocDTO docDTO;
            String dataId = (String)new ArrayList(dataIds).get(0);
            if (!StringUtils.isEmpty((String)dataId)) {
                AnalysisReportDataEO analysisReportDataEO = (AnalysisReportDataEO)this.dataDao.get((Serializable)((Object)dataId));
                Objects.requireNonNull(analysisReportDataEO, "\u5206\u6790\u62a5\u544a\u7ed3\u679c\u6570\u636e\u5df2\u5931\u6548\uff0c\u8bf7\u5237\u65b0\u91cd\u8bd5\u3002");
                docFileKey = analysisReportDataEO.getDocFileKey();
            }
            if (StringUtils.isEmpty(docFileKey) && (docDTO = this.generatorRealTimeAnalysisReportDoc(request, exportParamDTO.getContext(), exportParamDTO.getTemplateId())).getObjectInfo() != null) {
                ObjectInfo wordOssFileInfo = docDTO.getObjectInfo();
                docFileKey = wordOssFileInfo.getKey();
                CommonFileClearService fileClearService = (CommonFileClearService)SpringContextUtils.getBean(CommonFileClearService.class);
                fileClearService.addFileClearData(Arrays.asList(new CommonFileClearEO("ONLINE_OFFICE", docFileKey, "\u5408\u5e76\u62a5\u8868\u5206\u6790\u62a5\u544a-\u5bfc\u51fa\u5b9e\u65f6\u7248\u672c\u65f6\u751f\u6210\u7684\u4e34\u65f6\u6587\u4ef6")));
            }
            switch (exportParamDTO.getCurrType()) {
                case "word": {
                    AnalysisReportUtils.downloadOssFile(request, response, docFileKey);
                    break;
                }
                case "pdf": {
                    AnalysisReportUtils.downloadOssFileToPdf(request, response, docFileKey);
                    break;
                }
            }
            return;
        }
        List<AnalysisReportDataEO> dataEOS = this.dataDao.queryByIds(dataIds);
        AnalysisReportUtils.downloadZipFile(request, response, exportParamDTO, dataEOS);
    }

    @Override
    public Boolean executeWorkFlowAnalysisReportData(String analysisReportDataId, String nodeCode) {
        Boolean isSuccess;
        if (StringUtils.isEmpty((String)analysisReportDataId)) {
            throw new BusinessRuntimeException("\u5b9e\u65f6\u7248\u672c\u4e0d\u5141\u8bb8\u6d41\u7a0b\u5904\u7406\u3002");
        }
        if (nodeCode.equals("TODO")) {
            isSuccess = this.uploadAnalysisReportData(analysisReportDataId);
        } else if (nodeCode.equals("REJECT")) {
            isSuccess = this.rejectAnalysisReportData(analysisReportDataId);
        } else {
            this.logger.error("\u5206\u6790\u62a5\u544a\u5de5\u4f5c\u6d41\u6267\u884c\u52a8\u4f5c\u5339\u914d\u5931\u8d25");
            throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u5de5\u4f5c\u6d41\u6267\u884c\u52a8\u4f5c\u5339\u914d\u5931\u8d25");
        }
        return isSuccess;
    }

    @Override
    public List<AnalysisReportWorkFlowDTO> listAnalysisReportWorkFlowButtons(String analysisReportDataId) {
        if (StringUtils.isEmpty((String)analysisReportDataId)) {
            return Collections.emptyList();
        }
        ArrayList<AnalysisReportWorkFlowDTO> analysisReportWorkFlowEOList = new ArrayList<AnalysisReportWorkFlowDTO>();
        AnalysisReportDataEO analysisReportDataEO = (AnalysisReportDataEO)this.dataDao.get((Serializable)((Object)analysisReportDataId));
        if (analysisReportDataEO.getFlowState().equals(UploadState.ORIGINAL_UPLOAD.name())) {
            List<LinkedHashMap<String, Object>> nodeList = this.getNodeList();
            analysisReportWorkFlowEOList.add(this.getTodoButtonData(nodeList.get(0).get("name").toString(), true, true));
        } else if (analysisReportDataEO.getFlowState().equals(UploadState.UPLOADED.name())) {
            String nodeName = this.getNodeName(analysisReportDataEO);
            analysisReportWorkFlowEOList.add(this.getTodoButtonData(nodeName, true, true));
            analysisReportWorkFlowEOList.add(this.getRejectButtonData(true, true));
        } else if (analysisReportDataEO.getFlowState().equals(UploadState.REJECTED.name())) {
            if (this.isStartProcess(analysisReportDataEO).booleanValue()) {
                List<LinkedHashMap<String, Object>> nodeList = this.getNodeList();
                analysisReportWorkFlowEOList.add(this.getTodoButtonData(nodeList.get(0).get("name").toString(), true, true));
            } else {
                String nodeName = this.getNodeName(analysisReportDataEO);
                analysisReportWorkFlowEOList.add(this.getTodoButtonData(nodeName, true, true));
                analysisReportWorkFlowEOList.add(this.getRejectButtonData(true, true));
            }
        }
        return analysisReportWorkFlowEOList;
    }

    @Override
    public void queryByLeafTemplateIdLatestConfirmedWord(HttpServletRequest request, HttpServletResponse response, String templateId) {
        boolean isAuth = this.authorityProvider.canRead(templateId);
        if (!isAuth) {
            throw new BusinessRuntimeException("\u5f53\u524d\u7528\u6237\u6ca1\u6709\u8be5\u5206\u6790\u62a5\u544a\u6a21\u677f\u6743\u9650\u3002");
        }
        AnalysisReportDataEO analysisReportDataEO = this.dataDao.queryLatestConfirmedByTemplateId(templateId);
        if (analysisReportDataEO == null) {
            Objects.requireNonNull(analysisReportDataEO, "\u627e\u4e0d\u5230\u5df2\u786e\u8ba4\u7684\u5206\u6790\u62a5\u544a\u6570\u636e\uff0c\u65e0\u6cd5\u9884\u89c8\u3002");
            return;
        }
        String docFileKey = analysisReportDataEO.getDocFileKey();
        if (StringUtils.isEmpty((String)docFileKey)) {
            Objects.requireNonNull(analysisReportDataEO, "\u627e\u4e0d\u5230\u5df2\u786e\u8ba4\u7684\u5206\u6790\u62a5\u544a\u6570\u636e\u6587\u6863\uff0c\u65e0\u6cd5\u9884\u89c8\u3002");
            return;
        }
        AnalysisReportUtils.downloadOssFile(request, response, docFileKey);
    }

    private String getNodeName(AnalysisReportDataEO analysisReportDataEO) {
        Map<String, Object> nodeMap = this.currNodeProperties(analysisReportDataEO);
        if (!nodeMap.containsKey("name")) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u83b7\u53d6\u8282\u70b9\u540d\u79f0\u4e3a\u7a7a,\u8bf7\u8bbe\u7f6e\u8282\u70b9\u4fe1\u606f\u3002");
            throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u83b7\u53d6\u8282\u70b9\u540d\u79f0\u4e3a\u7a7a,\u8bf7\u8bbe\u7f6e\u8282\u70b9\u4fe1\u606f\u3002");
        }
        return nodeMap.get("name").toString();
    }

    private List<LinkedHashMap<String, Object>> getNodeList() {
        String uniqueCode = this.getUniqueCodeForBusinessCode();
        WorkflowDTO workflowDTONode = new WorkflowDTO();
        workflowDTONode.setUniqueCode(uniqueCode);
        R nodeR = this.workflowServerClient.getNode(workflowDTONode);
        if (0 != nodeR.getCode()) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u672a\u7ed1\u5b9a\u5de5\u4f5c\u6d41\uff1a" + nodeR.getMsg());
            throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u672a\u7ed1\u5b9a\u5de5\u4f5c\u6d41\uff1a" + nodeR.getMsg());
        }
        List nodeList = (List)nodeR.get((Object)"nodes");
        if (nodeList.size() < 2) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u5de5\u4f5c\u6d41\u6700\u5c11\u5305\u542b\u4e24\u4e2a\u8282\u70b9");
            throw new BusinessRuntimeException("\u5206\u6790\u62a5\u544a\u5de5\u4f5c\u6d41\u6700\u5c11\u5305\u542b\u4e24\u4e2a\u8282\u70b9");
        }
        return nodeList;
    }

    private AnalysisReportWorkFlowDTO getTodoButtonData(String nodeName, boolean isVisible, boolean isEnable) {
        AnalysisReportWorkFlowDTO analysisReportWorkFlowEO = new AnalysisReportWorkFlowDTO();
        analysisReportWorkFlowEO.setNodeCode("TODO");
        analysisReportWorkFlowEO.setNodeTitle(nodeName);
        analysisReportWorkFlowEO.setIsVisible(isVisible);
        analysisReportWorkFlowEO.setIsEnable(isEnable);
        analysisReportWorkFlowEO.setIcon("#icon-_GJHshangbao");
        return analysisReportWorkFlowEO;
    }

    private AnalysisReportWorkFlowDTO getRejectButtonData(boolean isVisible, boolean isEnable) {
        AnalysisReportWorkFlowDTO analysisReportWorkFlowEO = new AnalysisReportWorkFlowDTO();
        analysisReportWorkFlowEO.setNodeCode("REJECT");
        analysisReportWorkFlowEO.setNodeTitle("\u9a73\u56de");
        analysisReportWorkFlowEO.setIsVisible(isVisible);
        analysisReportWorkFlowEO.setIsEnable(isEnable);
        analysisReportWorkFlowEO.setIcon("#icon-_GJZtuihui");
        return analysisReportWorkFlowEO;
    }

    public static class OrgDataInfo {
        private String code;
        private String title;
        private String orgtypeid;

        public OrgDataInfo(String code, String title, String orgtypeid) {
            this.code = code;
            this.title = title;
            this.orgtypeid = orgtypeid;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOrgtypeid() {
            return this.orgtypeid;
        }

        public void setOrgtypeid(String orgtypeid) {
            this.orgtypeid = orgtypeid;
        }
    }
}

