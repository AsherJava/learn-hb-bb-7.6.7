/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.common.np.NpReportQueryProvider
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.gcreport.common.task.vo.Scheme
 *  com.jiuqi.gcreport.common.task.vo.TaskConditionBoxVO
 *  com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncParamSyncClient
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogItemVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  feign.Request$Options
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.common.np.NpReportQueryProvider;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.common.task.vo.TaskConditionBoxVO;
import com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncParamSyncClient;
import com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO;
import com.jiuqi.gcreport.reportdatasync.enums.RetryParamSyncBatchType;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncParamSyncService;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogItemVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import feign.Request;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Primary
public class ReportDataSyncParamSyncController
implements ReportDataSyncParamSyncClient {
    @Autowired
    private ReportDataSyncParamSyncService paramSyncService;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private NpReportQueryProvider iRunTimeViewController;

    public BusinessResponseEntity<Boolean> uploadParamsUpdateInstructions(String paramSyncSchemeId) {
        return BusinessResponseEntity.ok((Object)this.paramSyncService.uploadParamsUpdateInstructions(paramSyncSchemeId));
    }

    public BusinessResponseEntity<Boolean> paramSync(String paramSyncLogId) {
        return BusinessResponseEntity.ok((Object)this.paramSyncService.paramSync(paramSyncLogId));
    }

    public BusinessResponseEntity<PageInfo<ReportDataSyncIssuedLogVO>> listXfLogs(String paramSyncSchemeId, Integer pageSize, Integer pageNum) {
        return BusinessResponseEntity.ok(this.paramSyncService.listXfLogs(paramSyncSchemeId, pageSize, pageNum));
    }

    public BusinessResponseEntity<List<ReportDataSyncIssuedLogVO>> fetchSyncParamTaskInfos() {
        return BusinessResponseEntity.ok(this.paramSyncService.fetchSyncParamTaskInfos());
    }

    public BusinessResponseEntity<ReportDataSyncParamFileDTO> fetchSyncParamFiles(Request.Options options, ReportDataSyncIssuedLogVO issuedLogVO) {
        return BusinessResponseEntity.ok((Object)this.paramSyncService.fetchSyncParamFiles(issuedLogVO));
    }

    public BusinessResponseEntity<PageInfo<ReportDataSyncIssuedLogItemVO>> listXfLogItemsByLogId(String logId, Integer pageSize, Integer pageNum) {
        return BusinessResponseEntity.ok(this.paramSyncService.listXfLogItemsByLogId(logId, pageSize, pageNum));
    }

    public BusinessResponseEntity<Boolean> retryParamSyncByLogItemIds(List<String> paramSyncLogItemIds) {
        return BusinessResponseEntity.ok((Object)this.paramSyncService.retryParamSyncByLogItemIds(paramSyncLogItemIds));
    }

    public BusinessResponseEntity<Boolean> retryParamSyncByLogIdAndBatchType(String logId, String batchType) {
        return BusinessResponseEntity.ok((Object)this.paramSyncService.retryParamSyncByLogIdAndBatchType(logId, RetryParamSyncBatchType.valueOf(batchType), null));
    }

    public BusinessResponseEntity<List<ReportDataSyncParams>> listAllParamSyncScheme() {
        return BusinessResponseEntity.ok(this.paramSyncService.listAllParamSyncScheme());
    }

    public BusinessResponseEntity<Boolean> addParamSyncScheme(MultipartFile syncDesFile, String syncParamJson) {
        ReportDataSyncParams syncParam = (ReportDataSyncParams)JsonUtils.readValue((String)syncParamJson, ReportDataSyncParams.class);
        return BusinessResponseEntity.ok((Object)this.paramSyncService.addParamSyncScheme(syncDesFile, syncParam));
    }

    public BusinessResponseEntity<Boolean> updateParamSyncScheme(MultipartFile syncDesFile, String syncParamJson) {
        ReportDataSyncParams syncParam = (ReportDataSyncParams)JsonUtils.readValue((String)syncParamJson, ReportDataSyncParams.class);
        return BusinessResponseEntity.ok((Object)this.paramSyncService.updateParamSyncScheme(syncDesFile, syncParam));
    }

    public BusinessResponseEntity<Boolean> deleteParamSyncScheme(String paramSyncSchemeId) {
        return BusinessResponseEntity.ok((Object)this.paramSyncService.deleteParamSyncScheme(paramSyncSchemeId));
    }

    public String getParamSyncGroups() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList paramSyncGroups = new ArrayList();
        HashMap<String, String> group = new HashMap<String, String>();
        group.put("key", "taskList");
        group.put("title", "\u4efb\u52a1\u5217\u8868");
        paramSyncGroups.add(group);
        group = new HashMap();
        group.put("key", "formulaScheme");
        group.put("title", "\u516c\u5f0f\u65b9\u6848");
        paramSyncGroups.add(group);
        group = new HashMap();
        group.put("key", "mergeSystem");
        group.put("title", "\u5408\u5e76\u4f53\u7cfb");
        paramSyncGroups.add(group);
        group = new HashMap();
        group.put("key", "conversionSystem");
        group.put("title", "\u6298\u7b97\u4f53\u7cfb");
        paramSyncGroups.add(group);
        group = new HashMap();
        group.put("key", "organization");
        group.put("title", "\u7ec4\u7ec7\u673a\u6784");
        paramSyncGroups.add(group);
        try {
            return mapper.writeValueAsString(paramSyncGroups);
        }
        catch (JsonProcessingException e) {
            throw new BusinessRuntimeException("\u53c2\u6570\u540c\u6b65\u529f\u80fd\u83b7\u53d6\u5c55\u793a\u5206\u7ec4\u5931\u8d25");
        }
    }

    public BusinessResponseEntity<TaskConditionBoxVO> getSchemes(String taskId) throws Exception {
        TaskDefine designTaskDefine = this.iRunTimeViewController.getRunTimeViewController().queryTaskDefine(taskId);
        if (null == designTaskDefine) {
            return null;
        }
        List<FormSchemeDefine> formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByTask(taskId).stream().filter(formSchemeDefine -> this.authorityProvider.canReadFormScheme(formSchemeDefine.getKey())).collect(Collectors.toList());
        formSchemeDefines = formSchemeDefines.stream().sorted(Comparator.comparing(IMetaItem::getUpdateTime, Comparator.reverseOrder())).collect(Collectors.toList());
        TaskConditionBoxVO boxVO = new TaskConditionBoxVO();
        boxVO.setTaskId(taskId);
        boxVO.setTaskTitle(designTaskDefine.getTitle());
        this.convertObjs2VO(boxVO, formSchemeDefines);
        return BusinessResponseEntity.ok((Object)boxVO);
    }

    private TaskConditionBoxVO convertObjs2VO(TaskConditionBoxVO boxVO, List<FormSchemeDefine> schemeDefines) {
        ArrayList schemeList = new ArrayList();
        if (!CollectionUtils.isEmpty(schemeDefines)) {
            schemeDefines.forEach(schemeDefine -> {
                Scheme scheme = this.convertSchemeDefinToScheme((FormSchemeDefine)schemeDefine);
                schemeList.add(scheme);
            });
        }
        Collections.sort(schemeList, new Comparator<Scheme>(){

            @Override
            public int compare(Scheme scheme1, Scheme scheme2) {
                return scheme2.getFromPeriod().compareTo(scheme1.getFromPeriod());
            }
        });
        boxVO.setSchemeList(schemeList);
        return boxVO;
    }

    public Scheme convertSchemeDefinToScheme(FormSchemeDefine schemeDefine) {
        String currencyDefine = null;
        String gcorgtypeDefine = null;
        ArrayList<String> defines = new ArrayList<String>();
        if (!ObjectUtils.isEmpty(schemeDefine.getDims())) {
            String[] entityIds = schemeDefine.getDims().split(";");
            try {
                for (String entityId : entityIds) {
                    TableModelDefine designTableDefine = this.entityMetaService.getTableModel(entityId);
                    if (designTableDefine == null) continue;
                    String mTableName = designTableDefine.getName();
                    if ("MD_CURRENCY".equals(mTableName)) {
                        currencyDefine = mTableName;
                        continue;
                    }
                    if ("MD_GCORGTYPE".equals(mTableName)) {
                        gcorgtypeDefine = mTableName;
                        continue;
                    }
                    defines.add(designTableDefine.getName());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        String[] fromToPeriodByFormSchemeKey = FormSchemePeriodGcUtils.getFromToPeriodByFormSchemeKey((String)schemeDefine.getKey());
        Scheme scheme = new Scheme();
        scheme.setCurrencyDefine(currencyDefine);
        scheme.setGcorgtypeDefine(gcorgtypeDefine);
        scheme.setFromPeriod(fromToPeriodByFormSchemeKey[0]);
        scheme.setToPeriod(fromToPeriodByFormSchemeKey[1]);
        TaskPeriodUtils.setSchemeTimeByFormSchemeDefine((Scheme)scheme, (FormSchemeDefine)schemeDefine);
        TaskPeriodUtils.setDefaultTime((Scheme)scheme, (FormSchemeDefine)schemeDefine);
        return scheme;
    }
}

