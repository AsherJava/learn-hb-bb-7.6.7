/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.reportsync.util.CommonAuthUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncServerListClient
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.feign.client.NvwaPasswordClient
 *  com.jiuqi.va.feign.util.FeignUtil
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.reportsync.util.CommonAuthUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncServerListClient;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncServerInfoDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncServerInfoEO;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerInfoService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncAuthUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.feign.client.NvwaPasswordClient;
import com.jiuqi.va.feign.util.FeignUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportDataSyncServerInfoServiceImpl
implements ReportDataSyncServerInfoService {
    @Autowired
    private NvwaPasswordClient nvwaPasswordClient;
    @Autowired
    private ReportDataSyncServerInfoDao reportDataSyncServerInfoDao;
    @Autowired
    List<ISyncMethodScheduler> syncMethodSchedulerList;
    @Autowired
    ReportDataSyncServerListService listService;

    @Override
    public ReportDataSyncServerInfoVO saveServerInfo(ReportDataSyncServerInfoVO serverInfoVO) {
        ReportDataSyncServerInfoEO serverInfoEO;
        this.checkAndEncryptServerInfo(serverInfoVO);
        boolean isAdd = StringUtils.isEmpty((String)serverInfoVO.getId());
        if (isAdd) {
            serverInfoEO = new ReportDataSyncServerInfoEO();
        } else {
            serverInfoEO = (ReportDataSyncServerInfoEO)this.reportDataSyncServerInfoDao.get((Serializable)((Object)serverInfoVO.getId()));
            if (serverInfoEO == null) {
                isAdd = true;
            }
        }
        if (isAdd) {
            BeanUtils.copyProperties(serverInfoVO, (Object)serverInfoEO);
            serverInfoEO.setSyncParamFlag(serverInfoVO.getSyncParamFlag() != false ? 1 : 0);
            serverInfoEO.setSyncModifyFlag(serverInfoVO.getSyncModifyFlag() != false ? 1 : 0);
            serverInfoEO.setId(UUIDOrderUtils.newUUIDStr());
            serverInfoEO.setCreateTime(new Date());
            this.reportDataSyncServerInfoDao.add((BaseEntity)serverInfoEO);
            LogHelper.info((String)"\u5408\u5e76-\u76ee\u6807\u670d\u52a1\u5668\u8bbe\u7f6e", (String)"\u65b0\u589e-\u76ee\u6807\u670d\u52a1\u5668\u4fe1\u606f", (String)"");
        } else {
            Date createTime = serverInfoEO.getCreateTime();
            BeanUtils.copyProperties(serverInfoVO, (Object)serverInfoEO);
            serverInfoEO.setSyncParamFlag(serverInfoVO.getSyncParamFlag() != false ? 1 : 0);
            serverInfoEO.setSyncModifyFlag(serverInfoVO.getSyncModifyFlag() != false ? 1 : 0);
            serverInfoEO.setModifyTime(new Date());
            serverInfoEO.setCreateTime(createTime);
            this.reportDataSyncServerInfoDao.update((BaseEntity)serverInfoEO);
            LogHelper.info((String)"\u5408\u5e76-\u76ee\u6807\u670d\u52a1\u5668\u8bbe\u7f6e", (String)"\u4fee\u6539-\u76ee\u6807\u670d\u52a1\u5668\u4fe1\u606f", (String)"");
        }
        return this.convertEO2VO(serverInfoEO);
    }

    @Override
    public void checkAndEncryptServerInfo(ReportDataSyncServerInfoVO serverInfoVO) {
        if (!serverInfoVO.getTargetUrl().endsWith("/")) {
            serverInfoVO.setTargetUrl(serverInfoVO.getTargetUrl() + "/");
        }
        if (!serverInfoVO.getUrl().endsWith("/")) {
            serverInfoVO.setUrl(serverInfoVO.getUrl() + "/");
        }
        serverInfoVO.setOrgCode(serverInfoVO.getOrgCode().trim());
        String username = serverInfoVO.getUserName();
        String password = serverInfoVO.getPwd();
        User user = ReportDataSyncAuthUtils.getUserByUserName(username);
        if (user == null) {
            throw new BusinessRuntimeException("\u672c\u5730\u670d\u52a1\u5668\u7528\u6237[" + username + "]\u4e0d\u5b58\u5728\u3002");
        }
        boolean validateUserPwd = this.nvwaPasswordClient.validate(user.getId(), password);
        if (!validateUserPwd) {
            throw new BusinessRuntimeException("\u672c\u5730\u670d\u52a1\u5668\u7528\u6237[" + username + "]\u548c\u5bc6\u7801\u4e0d\u6b63\u786e\u3002");
        }
    }

    @Override
    public ReportDataSyncServerInfoVO queryServerInfo() {
        ReportDataSyncServerInfoEO serverInfoEO = this.reportDataSyncServerInfoDao.queryServerInfo();
        if (serverInfoEO == null) {
            return null;
        }
        return this.convertEO2VO(serverInfoEO);
    }

    private ReportDataSyncServerInfoVO convertEO2VO(ReportDataSyncServerInfoEO serverInfoEO) {
        ReportDataSyncServerInfoVO serverInfoVO = new ReportDataSyncServerInfoVO();
        BeanUtils.copyProperties((Object)serverInfoEO, serverInfoVO);
        serverInfoVO.setSyncParamFlag(serverInfoEO.getSyncParamFlag() != null && serverInfoEO.getSyncParamFlag().equals(1) ? Boolean.TRUE : Boolean.FALSE);
        serverInfoVO.setSyncModifyFlag(serverInfoEO.getSyncModifyFlag() != null && serverInfoEO.getSyncModifyFlag().equals(1) ? Boolean.TRUE : Boolean.FALSE);
        return serverInfoVO;
    }

    @Override
    public ReportDataSyncServerInfoVO register(ReportDataSyncServerInfoVO serverInfoVO) {
        this.checkAndEncryptServerInfo(serverInfoVO);
        ReportDataSyncServerInfoVO newSaveInfoVO = new ReportDataSyncServerInfoVO();
        BeanUtils.copyProperties(serverInfoVO, newSaveInfoVO);
        newSaveInfoVO.setUrl(serverInfoVO.getTargetUrl());
        newSaveInfoVO.setUserName(serverInfoVO.getTargetUserName());
        newSaveInfoVO.setPwd(serverInfoVO.getTargetPwd());
        newSaveInfoVO.setEncryptType(serverInfoVO.getTargetEncryptType());
        this.listService.updateServerInfo(newSaveInfoVO);
        serverInfoVO.setId(newSaveInfoVO.getId());
        CommonAuthUtils.validateUserPassword((String)serverInfoVO.getTargetUrl(), (String)serverInfoVO.getTargetUserName(), (String)serverInfoVO.getTargetPwd(), (String)(serverInfoVO.getTargetEncryptType() == null ? "" : serverInfoVO.getTargetEncryptType()));
        ReportDataSyncServerListClient client = (ReportDataSyncServerListClient)FeignUtil.getDynamicClient(ReportDataSyncServerListClient.class, (String)serverInfoVO.getTargetUrl());
        BusinessResponseEntity responseEntity = client.registerServerInfo(serverInfoVO);
        if (!responseEntity.isSuccess()) {
            throw new BusinessRuntimeException("\u8fde\u63a5\u5931\u8d25\uff0c\u8be6\u60c5\uff1a" + responseEntity.getErrorMessage());
        }
        return newSaveInfoVO;
    }

    @Override
    public Boolean connection(ReportDataSyncServerInfoVO serverInfoVO) {
        String syncMethod = serverInfoVO.getSyncMethod();
        ISyncMethodScheduler extendService = this.syncMethodSchedulerList.stream().filter(v -> v.code().equals(syncMethod)).findFirst().orElse(null);
        if (extendService != null) {
            return extendService.testConnection(serverInfoVO);
        }
        return false;
    }
}

