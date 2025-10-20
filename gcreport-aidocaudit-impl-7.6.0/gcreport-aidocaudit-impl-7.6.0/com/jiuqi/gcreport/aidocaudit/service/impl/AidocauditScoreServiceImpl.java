/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.attachment.input.AttachmentQueryByFieldInfo
 *  com.jiuqi.nr.attachment.message.RowDataInfo
 *  com.jiuqi.nr.attachment.output.RowDataValues
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.gcreport.aidocaudit.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditLogDao;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditResultDao;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditRuleDao;
import com.jiuqi.gcreport.aidocaudit.dto.AuditParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.CheckScoredResultDTO;
import com.jiuqi.gcreport.aidocaudit.enums.ReScoreOptionEnum;
import com.jiuqi.gcreport.aidocaudit.eo.AidocAuditLogEO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditResultEO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleEO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditScoreService;
import com.jiuqi.gcreport.aidocaudit.util.AidocauditPeriodUtil;
import com.jiuqi.gcreport.aidocaudit.util.ApiKeyCache;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.attachment.input.AttachmentQueryByFieldInfo;
import com.jiuqi.nr.attachment.message.RowDataInfo;
import com.jiuqi.nr.attachment.output.RowDataValues;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AidocauditScoreServiceImpl
implements IAidocauditScoreService {
    private final Logger logger = LoggerFactory.getLogger(AidocauditScoreServiceImpl.class);
    @Autowired
    private IAidocauditRuleDao ruleDao;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFuncExecuteService iFuncExecuteService;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private IAidocauditResultDao resultDao;
    @Autowired
    private IAidocauditLogDao logDao;
    @Autowired
    private ApiKeyCache apiKeyCache;
    @Value(value="${jiuqi.gcreport.aidocaudit.scoresystem.url:10.2.45.188:8880}")
    private String ipAndPort;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String SLASH = "/";

    @Override
    public BusinessResponseEntity<Boolean> audit(AuditParamDTO param) {
        Map response;
        String token = this.apiKeyCache.getToken();
        List<Map<String, Object>> fileList = null;
        ArrayList<AidocAuditLogEO> auditLogs = new ArrayList<AidocAuditLogEO>();
        try {
            fileList = this.constructFileList(param, auditLogs);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u6570\u636e\u62a5\u9519", (Throwable)e);
        }
        HashMap<String, List<Map<String, Object>>> body = new HashMap<String, List<Map<String, Object>>>();
        body.put("file_list", fileList);
        String url = "http://" + this.ipAndPort + "/api/doc-score/jq/score_proc";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity requestEntity = new HttpEntity(body, (MultiValueMap)headers);
        ResponseEntity responseEntity = this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, (ParameterizedTypeReference)new ParameterizedTypeReference<Map<String, Object>>(){}, new Object[0]);
        if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            this.apiKeyCache.invalidateToken();
            token = this.apiKeyCache.getToken();
            headers.set("Authorization", token);
            requestEntity = new HttpEntity(body, (MultiValueMap)headers);
            responseEntity = this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, (ParameterizedTypeReference)new ParameterizedTypeReference<Map<String, Object>>(){}, new Object[0]);
        }
        if ((response = (Map)responseEntity.getBody()) != null) {
            int code = (Integer)response.get("code");
            String msg = (String)response.get("msg");
            Map data = (Map)response.get("data");
            if (code == 1) {
                String taskId = data.get("task_id").toString();
                this.logger.info("\u8bf7\u6c42\u6210\u529f\uff0c\u4efb\u52a1ID{}:,\u89c4\u5219ID:{},\u65f6\u671f:{},\u5355\u4f4d\uff1a{}", taskId, param.getRuleIds(), param.getDataTime(), param.getOrgIds());
                this.recordLog(auditLogs, taskId);
                return BusinessResponseEntity.ok((Object)true);
            }
            this.logger.error("\u8bf7\u6c42\u5931\u8d25\uff0c\u9519\u8bef\u4fe1\u606f: {}", (Object)msg);
        } else {
            this.logger.error("\u8bf7\u6c42\u5931\u8d25\uff0c\u54cd\u5e94\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok((Object)false);
    }

    @Override
    public BusinessResponseEntity<CheckScoredResultDTO> checkScoredResult(AuditParamDTO param) {
        List<String> ruleIds = param.getRuleIds();
        String dataTime = param.getDataTime();
        List<String> orgIds = param.getOrgIds();
        Integer orgTotal = orgIds.size();
        Integer scoredOrgNum = 0;
        ArrayList<String> noScoreOrgIds = new ArrayList<String>();
        ArrayList<String> scoredOrgIds = new ArrayList<String>();
        Boolean hasScored = false;
        List<AidocauditResultEO> aidocauditResultEOS = this.resultDao.queryByRuleIdsAndOrgIds(param.getTaskId(), ruleIds, dataTime, orgIds);
        Map<String, List<AidocauditResultEO>> resultOrgMap = aidocauditResultEOS.stream().collect(Collectors.groupingBy(AidocauditResultEO::getMdCode));
        int ruleNum = ruleIds.size();
        for (Map.Entry<String, List<AidocauditResultEO>> entry : resultOrgMap.entrySet()) {
            String orgId = entry.getKey();
            List<AidocauditResultEO> orgResultList = entry.getValue();
            if (orgResultList.size() != ruleNum) continue;
            scoredOrgIds.add(orgId);
            Integer n = scoredOrgNum;
            Integer n2 = scoredOrgNum = Integer.valueOf(scoredOrgNum + 1);
            hasScored = true;
        }
        noScoreOrgIds.addAll(orgIds);
        noScoreOrgIds.removeAll(scoredOrgIds);
        Integer noScoreOrgNum = orgTotal - scoredOrgNum;
        CheckScoredResultDTO checkScoredResultDTO = new CheckScoredResultDTO(hasScored, orgTotal, scoredOrgNum, noScoreOrgNum, noScoreOrgIds, scoredOrgIds);
        return BusinessResponseEntity.ok((Object)checkScoredResultDTO);
    }

    private void recordLog(List<AidocAuditLogEO> auditLogs, String taskId) {
        for (AidocAuditLogEO auditLog : auditLogs) {
            auditLog.setScoreTask(taskId);
        }
        this.logDao.addBatch(auditLogs);
    }

    private List<Map<String, Object>> constructFileList(AuditParamDTO param, List<AidocAuditLogEO> auditLogs) {
        Date startTime = new Date();
        String userId = NpContextHolder.getContext().getUserId();
        List<String> ruleIds = param.getRuleIds();
        List<AidocauditRuleEO> ruleList = this.ruleDao.queryListByIds(ruleIds);
        if (CollectionUtils.isEmpty(ruleList)) {
            throw new BusinessRuntimeException("\u5ba1\u6838\u7b56\u7565\u4e0d\u5b58\u5728");
        }
        String taskId = param.getTaskId();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskId);
        String dataScheme = taskDefine.getDataScheme();
        FormSchemeDefine formSchemeDefine = this.iFuncExecuteService.queryFormScheme(taskId, param.getDataTime());
        List formDefines = this.runTimeViewController.queryAllFormDefinesByTask(taskId);
        ArrayList<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        String orgType = taskDefine.getDw().replace("@ORG", "");
        for (AidocauditRuleEO rule : ruleList) {
            Map<String, String> parsedCode = this.parseAchmentZbCode(rule.getAchmentZbCode());
            String zbCode = parsedCode.get("zbCode");
            List<String> orgIds = param.getOrgIds();
            HashMap<String, String> orgNameMap = new HashMap<String, String>();
            GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(formSchemeDefine.getKey(), param.getDataTime()));
            for (String org : param.getOrgIds()) {
                GcOrgCacheVO baseOrgByCode = orgCenterService.getBaseOrgByCode(org);
                orgNameMap.put(org, baseOrgByCode.getTitle());
            }
            if (CollectionUtils.isEmpty(orgIds) || CollectionUtils.isEmpty(formDefines)) continue;
            DataField dataField = this.runtimeDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(dataScheme, zbCode);
            List<AidocauditResultEO> aidocauditResultEOS = this.resultDao.queryByOrgIds(param.getTaskId(), rule.getId(), param.getDataTime(), orgIds);
            Set<Object> existingOrgs = ReScoreOptionEnum.SKIP.equals((Object)param.getReScoreOption()) ? aidocauditResultEOS.stream().map(AidocauditResultEO::getMdCode).collect(Collectors.toSet()) : new HashSet();
            List<String> remainingOrgIds = orgIds.stream().filter(orgId -> !existingOrgs.contains(orgId)).collect(Collectors.toList());
            Map<String, DimensionValue> dimensionSet = this.constructDimensionSet(remainingOrgIds, orgType, param);
            List rowDatas = this.getFileDatas(taskId, dataScheme, dataField, formSchemeDefine.getKey(), ((FormDefine)formDefines.get(0)).getKey(), dimensionSet).getRowDatas();
            if (!CollectionUtils.isEmpty(rowDatas)) {
                for (RowDataInfo rowDataInfo : rowDatas) {
                    HashMap<String, Object> file = new HashMap<String, Object>();
                    file.put("temp_id", rule.getScoreTmplId());
                    file.put("file_attach_id", rowDataInfo.getFileKey());
                    file.put("file_name", rowDataInfo.getName());
                    String[] splitStr = rowDataInfo.getName().split("\\.");
                    String fileType = splitStr[splitStr.length - 1];
                    file.put("file_type", fileType);
                    file.put("file_size", rowDataInfo.getSize());
                    String filePath = rule.getRuleAttachmentName() + SLASH + (String)orgNameMap.get(rowDataInfo.getDw()) + SLASH + param.getDataTime();
                    file.put("file_path", filePath);
                    HashMap<String, String> business = new HashMap<String, String>();
                    business.put("UNIT", rowDataInfo.getDw());
                    business.put("\u5355\u4f4d", (String)orgNameMap.get(rowDataInfo.getDw()));
                    String dataTime = AidocauditPeriodUtil.formatDate(new YearPeriodObject(formSchemeDefine.getKey(), param.getDataTime()));
                    business.put("PERIOD", param.getDataTime());
                    business.put("\u65f6\u671f", dataTime);
                    business.put("TASKID", taskId);
                    business.put("USER", NpContextHolder.getContext().getUserId());
                    String logId = UUIDUtils.newUUIDStr();
                    business.put("LOGID", logId);
                    file.put("business", business);
                    fileList.add(file);
                    AidocAuditLogEO auditLog = new AidocAuditLogEO();
                    auditLog.setId(logId);
                    auditLog.setRuleId(rule.getId());
                    auditLog.setMdCode(rowDataInfo.getDw());
                    auditLog.setDataTime(param.getDataTime());
                    auditLog.setScoreTask(taskId);
                    auditLog.setStatus(0);
                    auditLog.setStartTime(startTime);
                    auditLog.setIsFeedback(0);
                    auditLog.setAuditUser(userId);
                    auditLogs.add(auditLog);
                }
            }
            List hasFileOrgs = rowDatas.stream().map(RowDataInfo::getDw).collect(Collectors.toList());
            remainingOrgIds.removeAll(hasFileOrgs);
            this.recordNoFileLog(taskId, rule.getId(), param.getDataTime(), remainingOrgIds, startTime, userId, auditLogs);
        }
        return fileList;
    }

    private void recordNoFileLog(String taskId, String id, String dataTime, List<String> remainingOrgIds, Date startTime, String userId, List<AidocAuditLogEO> auditLogs) {
        if (!CollectionUtils.isEmpty(remainingOrgIds)) {
            for (String orgId : remainingOrgIds) {
                AidocAuditLogEO auditLog = new AidocAuditLogEO();
                auditLog.setId(UUIDUtils.newUUIDStr());
                auditLog.setRuleId(id);
                auditLog.setMdCode(orgId);
                auditLog.setDataTime(dataTime);
                auditLog.setScoreTask(taskId);
                auditLog.setStatus(2);
                auditLog.setStartTime(startTime);
                auditLog.setEndTime(startTime);
                auditLog.setIsFeedback(0);
                auditLog.setAuditUser(userId);
                auditLogs.add(auditLog);
            }
        }
    }

    private Map<String, String> parseAchmentZbCode(String achmentZbCode) {
        Pattern pattern = Pattern.compile("(.+?)\\[(.+?)\\]");
        Matcher matcher = pattern.matcher(achmentZbCode);
        if (!matcher.matches()) {
            throw new BusinessRuntimeException("Invalid achmentZbCode format: " + achmentZbCode);
        }
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("reportCode", matcher.group(1));
        result.put("zbCode", matcher.group(2));
        return result;
    }

    private Map<String, DimensionValue> constructDimensionSet(List<String> orgIds, String orgType, AuditParamDTO param) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue dataTime = new DimensionValue();
        dataTime.setName("DATATIME");
        dataTime.setValue(param.getDataTime());
        dimensionSet.put("DATATIME", dataTime);
        DimensionValue gcOrgType = new DimensionValue();
        gcOrgType.setName("MD_GCORGTYPE");
        gcOrgType.setValue(orgType);
        dimensionSet.put("MD_GCORGTYPE", gcOrgType);
        DimensionValue currency = new DimensionValue();
        currency.setName("MD_CURRENCY");
        currency.setValue("CNY");
        dimensionSet.put("MD_CURRENCY", currency);
        DimensionValue org = new DimensionValue();
        org.setName("MD_ORG");
        String joinedStr = String.join((CharSequence)";", orgIds);
        org.setValue(joinedStr);
        dimensionSet.put("MD_ORG", org);
        return dimensionSet;
    }

    private RowDataValues getFileDatas(String taskId, String dataScheme, DataField dataField, String formSchemeKey, String formKey, Map<String, DimensionValue> dimensionSet) {
        AttachmentQueryByFieldInfo attachmentQueryByFieldInfo = new AttachmentQueryByFieldInfo();
        attachmentQueryByFieldInfo.setTask(taskId);
        attachmentQueryByFieldInfo.setDataSchemeKey(dataScheme);
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
            String key = entry.getKey();
            DimensionValue value = entry.getValue();
            String[] values = value.getValue().split(";");
            if (values.length <= 1) {
                builder.setEntityValue(key, null, new Object[]{value.getValue()});
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            builder.setEntityValue(key, null, new Object[]{valueList});
        }
        DimensionCollection dimensionValueCollection = builder.getCollection();
        attachmentQueryByFieldInfo.setDimensionCollection(dimensionValueCollection);
        attachmentQueryByFieldInfo.setFormscheme(formSchemeKey);
        attachmentQueryByFieldInfo.setFormKey(formKey);
        attachmentQueryByFieldInfo.setFieldKey(dataField.getKey());
        attachmentQueryByFieldInfo.setPage(false);
        return this.fileOperationService.searchFileByField(attachmentQueryByFieldInfo);
    }
}

