/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.reportdatasync.api.MultilevelSyncClient
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncParamSyncClient
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadClient
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadDataClient
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.MultilevelSyncVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.feign.util.FeignUtil
 */
package com.jiuqi.gcreport.reportdatasync.scheduler.impl.extend;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.reportdatasync.api.MultilevelSyncClient;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncParamSyncClient;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadClient;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadDataClient;
import com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.scheduler.MultilevelExtendHandler;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncAuthUtils;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelSyncVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.feign.util.FeignUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultExtendHandler
extends MultilevelExtendHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(DefaultExtendHandler.class);

    @Override
    public Boolean rejectReportData(ReportDataSyncServerInfoVO serverInfoVO, String content, SyncTypeEnums typeEnums) {
        ReportDataSyncUploadDataTaskVO uploadDataTaskVO = (ReportDataSyncUploadDataTaskVO)JsonUtils.readValue((String)content, ReportDataSyncUploadDataTaskVO.class);
        try {
            ReportDataSyncAuthUtils.initNvwaFeignClientTokenEnv(serverInfoVO);
            BusinessResponseEntity responseEntity = null;
            if (typeEnums.equals((Object)SyncTypeEnums.REPORTDATA)) {
                ReportDataSyncUploadClient client = (ReportDataSyncUploadClient)FeignUtil.getDynamicClient(ReportDataSyncUploadClient.class, (String)serverInfoVO.getUrl());
                responseEntity = client.rejectReportData(uploadDataTaskVO);
            } else {
                MultilevelSyncVO multilevelSyncVO = new MultilevelSyncVO();
                multilevelSyncVO.setType(typeEnums.getCode());
                multilevelSyncVO.setInfo(content);
                MultilevelSyncClient client = (MultilevelSyncClient)FeignUtil.getDynamicClient(MultilevelSyncClient.class, (String)serverInfoVO.getUrl());
                responseEntity = client.multilevelReturn(multilevelSyncVO);
            }
            if (!responseEntity.isSuccess()) {
                throw new BusinessRuntimeException(responseEntity.getErrorMessage());
            }
        }
        catch (Exception e) {
            LOGGER.error("\u5411\u4e0b\u7ea7\u5355\u4f4d\u53d1\u9001\u9000\u56de\u901a\u77e5\u6d88\u606f\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e.getMessage());
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean modifyLoadingResults(ReportDataSyncServerInfoVO serverInfoVO, ReportsyncDataLoadParam reportsyncDataLoadParam) {
        ReportDataSyncAuthUtils.initNvwaFeignClientTokenEnv(serverInfoVO);
        ReportDataSyncUploadClient client = (ReportDataSyncUploadClient)FeignUtil.getDynamicClient(ReportDataSyncUploadClient.class, (String)serverInfoVO.getUrl());
        BusinessResponseEntity responseEntity = client.modifyLoadingResults(reportsyncDataLoadParam);
        if (!responseEntity.isSuccess()) {
            throw new BusinessRuntimeException(responseEntity.getErrorMessage());
        }
        return true;
    }

    @Override
    public List<OrgDO> getOrgDO(ReportDataSyncServerInfoVO serverInfoVO, ReportDataCheckParam checkParam) {
        ReportDataSyncAuthUtils.initNvwaFeignClientTokenEnv(serverInfoVO);
        ReportDataSyncUploadDataClient client = (ReportDataSyncUploadDataClient)FeignUtil.getDynamicClient(ReportDataSyncUploadDataClient.class, (String)serverInfoVO.getTargetUrl());
        return (List)client.getOrgDO(checkParam).getData();
    }

    @Override
    public Boolean enableDataImport(ReportDataSyncServerInfoVO serverInfoVO, String taskId) {
        ReportDataSyncAuthUtils.initNvwaFeignClientTokenEnv(serverInfoVO);
        ReportDataSyncUploadDataClient client = (ReportDataSyncUploadDataClient)FeignUtil.getDynamicClient(ReportDataSyncUploadDataClient.class, (String)serverInfoVO.getUrl());
        BusinessResponseEntity responseEntity = client.enableDataImportCheck(taskId);
        if (!responseEntity.isSuccess()) {
            throw new BusinessRuntimeException(responseEntity.getErrorMessage());
        }
        return (Boolean)client.enableDataImportCheck(taskId).getData();
    }

    @Override
    public List<ReportDataSyncIssuedLogVO> fetchTargetSyncParamTaskInfos(ReportDataSyncServerInfoVO serverInfoVO) {
        ReportDataSyncAuthUtils.initNvwaFeignClientTokenEnv(serverInfoVO);
        ReportDataSyncParamSyncClient client = (ReportDataSyncParamSyncClient)FeignUtil.getDynamicClient(ReportDataSyncParamSyncClient.class, (String)serverInfoVO.getUrl());
        BusinessResponseEntity responseEntity = client.fetchSyncParamTaskInfos();
        if (!responseEntity.isSuccess()) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u76ee\u6807\u670d\u52a1\u5668\u7684\u53c2\u6570\u5305\u5217\u8868\u53d1\u751f\u5f02\u5e38\uff0c\u8be6\u60c5\uff1a" + responseEntity.getErrorMessage());
        }
        return (List)responseEntity.getData();
    }

    @Override
    public ReportDataSyncParamFileDTO fetchSyncParamFiles(ReportDataSyncServerInfoVO serverInfoVO, ReportDataSyncIssuedLogVO issuedLogVO) {
        ReportDataSyncAuthUtils.initNvwaFeignClientTokenEnv(serverInfoVO);
        ReportDataSyncParamSyncClient client = (ReportDataSyncParamSyncClient)FeignUtil.getDynamicClient(ReportDataSyncParamSyncClient.class, (String)serverInfoVO.getUrl());
        BusinessResponseEntity responseEntity = client.fetchSyncParamFiles(client.getOptions(), issuedLogVO);
        if (!responseEntity.isSuccess()) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u76ee\u6807\u670d\u52a1\u5668\u7684\u53c2\u6570\u5305\u5217\u8868\u53d1\u751f\u5f02\u5e38\uff0c\u8be6\u60c5\uff1a" + responseEntity.getErrorMessage());
        }
        return (ReportDataSyncParamFileDTO)responseEntity.getData();
    }
}

