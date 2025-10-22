/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncServerInfoClient
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.dto.PasswordDTO
 *  com.jiuqi.np.user.extend.IPasswordEncoderManage
 *  com.jiuqi.np.user.feign.client.NvwaPasswordClient
 *  com.jiuqi.np.user.service.PasswordService
 *  com.jiuqi.va.feign.util.FeignUtil
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncServerInfoClient;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncServerListDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncServerListEO;
import com.jiuqi.gcreport.reportdatasync.enums.ReportDataServerType;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncTypeScheduler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncAuthUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.dto.PasswordDTO;
import com.jiuqi.np.user.extend.IPasswordEncoderManage;
import com.jiuqi.np.user.feign.client.NvwaPasswordClient;
import com.jiuqi.np.user.service.PasswordService;
import com.jiuqi.va.feign.util.FeignUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportDataSyncServerListServiceImpl
implements ReportDataSyncServerListService {
    @Autowired
    private ReportDataSyncServerListDao reportDataSyncServerListDao;
    @Autowired
    private NvwaPasswordClient nvwaPasswordClient;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private IPasswordEncoderManage passwordEncoderManage;
    @Autowired
    private List<ISyncMethodScheduler> iSyncMethodScheduler;
    @Autowired
    private List<ISyncTypeScheduler> iSyncTypeScheduler;

    @Override
    public PasswordDTO queryPasswordDTOByUsername(String username) {
        User user = ReportDataSyncAuthUtils.getUserByUserName(username);
        PasswordDTO passwordDTO = this.nvwaPasswordClient.getPassword(user.getId());
        return passwordDTO;
    }

    @Override
    public List<ReportDataSyncServerInfoVO> listServerInfos() {
        List<ReportDataSyncServerListEO> serverInfoEOList = this.reportDataSyncServerListDao.listServerInfos();
        return serverInfoEOList.stream().map(this::convertEO2VO).collect(Collectors.toList());
    }

    @Override
    public List<ReportDataSyncServerInfoVO> listServerInfos(SyncTypeEnums type) {
        if (type == null) {
            return this.listServerInfos();
        }
        List<ReportDataSyncServerListEO> serverInfoEOList = this.reportDataSyncServerListDao.listServerInfos();
        return serverInfoEOList.stream().filter(v -> v.getSyncType().contains(type.getCode())).map(this::convertEO2VO).collect(Collectors.toList());
    }

    @Override
    public PageInfo<ReportDataSyncServerInfoVO> listServerInfosByPage(String keywords, Integer pageSize, Integer pageNum) {
        PageInfo<ReportDataSyncServerListEO> pageInfo = this.reportDataSyncServerListDao.listServerInfosByPage(keywords, pageSize, pageNum);
        List serverInfoVOS = pageInfo.getList().stream().map(this::convertEO2VO).collect(Collectors.toList());
        PageInfo pageInfoVO = PageInfo.of(serverInfoVOS, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
        return pageInfoVO;
    }

    @Override
    public ReportDataSyncServerInfoVO queryServerInfoByOrgCode(String orgCode) {
        ReportDataSyncServerListEO eo = this.reportDataSyncServerListDao.queryServerInfoByOrgCode(orgCode);
        ReportDataSyncServerInfoVO reportDataSyncServerInfoVO = this.convertEO2VO(eo);
        return reportDataSyncServerInfoVO;
    }

    @Override
    public List<ReportDataSyncServerInfoVO> queryServerInfoByOrgCodes(List<String> orgCodes) {
        List<ReportDataSyncServerListEO> eos = this.reportDataSyncServerListDao.queryServerInfoByOrgCodes(orgCodes);
        if (eos == null) {
            return Collections.emptyList();
        }
        List<ReportDataSyncServerInfoVO> reportDataSyncServerInfoVOS = eos.stream().map(eo -> this.convertEO2VO((ReportDataSyncServerListEO)((Object)eo))).collect(Collectors.toList());
        return reportDataSyncServerInfoVOS;
    }

    @Override
    public Boolean updateServerInfoState(Map<String, Object> params) {
        String ids = (String)params.get("ids");
        List<String> serverInfoIdList = Arrays.asList(ids.split(","));
        Boolean startFlag = (Boolean)params.get("startflag");
        String operation = Boolean.TRUE.equals(startFlag) ? "\u542f\u7528" : "\u505c\u7528";
        List<ReportDataSyncServerListEO> serverInfoList = this.reportDataSyncServerListDao.listServerInfoStateByIds(serverInfoIdList);
        serverInfoList.forEach(serverInfo -> {
            serverInfo.setStartFlag(startFlag != false ? 1 : 0);
            serverInfo.setSupportAll(1);
            LogHelper.info((String)"\u5408\u5e76-\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406", (String)(operation + "-" + serverInfo.getUrl()), (String)"");
        });
        this.reportDataSyncServerListDao.updateBatch(serverInfoList);
        return true;
    }

    @Override
    public String updateSyncModifyFlag(Map<String, Object> params) {
        String ids = (String)params.get("ids");
        List<String> serverInfoIdList = Arrays.asList(ids.split(","));
        List<ReportDataSyncServerListEO> serverInfoList = this.reportDataSyncServerListDao.listServerInfoStateByIds(serverInfoIdList);
        Boolean startFlag = (Boolean)params.get("startflag");
        ArrayList<String> logs = new ArrayList<String>();
        for (ReportDataSyncServerListEO serverInfoEO : serverInfoList) {
            try {
                ReportDataSyncServerInfoVO serverInfoVO = this.convertEO2VO(serverInfoEO);
                ReportDataSyncAuthUtils.initNvwaFeignClientTokenEnv(serverInfoVO);
                ReportDataSyncServerInfoClient client = (ReportDataSyncServerInfoClient)FeignUtil.getDynamicClient(ReportDataSyncServerInfoClient.class, (String)serverInfoVO.getUrl());
                client.syncModifyFlagUpdate(startFlag);
                serverInfoEO.setSyncModifyFlag(startFlag != false ? 1 : 0);
                this.reportDataSyncServerListDao.update((BaseEntity)serverInfoEO);
            }
            catch (Exception e) {
                logs.add("\u670d\u52a1" + serverInfoEO.getUrl() + "\u8bbe\u7f6e\u5931\u8d25\uff0c\u7f51\u7edc\u4e0d\u901a\u3002");
            }
        }
        return "\u8bbe\u7f6e\u5b8c\u6210\n" + String.join((CharSequence)"\n", logs);
    }

    @Override
    public Boolean updateManageUser(Map<String, Object> params) {
        String id = ConverterUtils.getAsString((Object)params.get("id"));
        List<String> serverInfoIdList = Arrays.asList(id.split(","));
        String manageuserIds = ConverterUtils.getAsString((Object)params.get("manageuserids"));
        String manageusers = ConverterUtils.getAsString((Object)params.get("manageusers"));
        List<ReportDataSyncServerListEO> serverListEOS = this.reportDataSyncServerListDao.listServerInfoStateByIds(serverInfoIdList);
        for (ReportDataSyncServerListEO eo : serverListEOS) {
            eo.setManageUserIds(manageuserIds);
            eo.setManageUsers(manageusers);
            eo.setSupportAll(1);
            LogHelper.info((String)"\u5408\u5e76-\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406", (String)("\u6307\u6d3e\u8d1f\u8d23\u4eba-" + eo.getUrl()), (String)"");
        }
        this.reportDataSyncServerListDao.updateBatch(serverListEOS);
        return true;
    }

    @Override
    public Boolean registerServerInfo(ReportDataSyncServerInfoVO serverInfoVO) {
        ReportDataSyncServerListEO serverInfo = (ReportDataSyncServerListEO)this.reportDataSyncServerListDao.get((Serializable)((Object)serverInfoVO.getId()));
        boolean isAdd = serverInfo == null;
        this.checkOrgCode(serverInfoVO, isAdd);
        if (isAdd) {
            serverInfo = new ReportDataSyncServerListEO();
            BeanUtils.copyProperties(serverInfoVO, (Object)serverInfo);
            serverInfo.setSyncModifyFlag(serverInfoVO.getSyncModifyFlag() != false ? 1 : 0);
            serverInfo.setSyncParamFlag(serverInfoVO.getSyncParamFlag() != false ? 1 : 0);
            serverInfo.setManageOrgCodes(String.join((CharSequence)";", serverInfoVO.getManageOrgCodes()));
            serverInfo.setStartFlag(0);
            serverInfo.setCreateTime(new Date());
            serverInfo.setSupportAll(1);
            serverInfo.setServerType(ReportDataServerType.AUTO.getCode());
            serverInfo.setSyncType("reportdata;paramdata");
            this.reportDataSyncServerListDao.add((BaseEntity)serverInfo);
        } else {
            Boolean startFlag = ConverterUtils.getAsBoolean((Object)serverInfo.getStartFlag(), (Boolean)true);
            String manageUserIds = serverInfo.getManageUserIds();
            BeanUtils.copyProperties(serverInfoVO, (Object)serverInfo);
            serverInfo.setSyncModifyFlag(serverInfoVO.getSyncModifyFlag() != false ? 1 : 0);
            serverInfo.setSyncParamFlag(serverInfoVO.getSyncParamFlag() != false ? 1 : 0);
            serverInfo.setManageOrgCodes(String.join((CharSequence)";", serverInfoVO.getManageOrgCodes()));
            serverInfo.setStartFlag(startFlag != false ? 1 : 0);
            serverInfo.setManageUserIds(manageUserIds);
            serverInfo.setModifyTime(new Date());
            serverInfo.setSupportAll(1);
            serverInfo.setServerType(ReportDataServerType.AUTO.getCode());
            serverInfo.setSyncType("reportdata;paramdata");
            this.reportDataSyncServerListDao.update((BaseEntity)serverInfo);
        }
        LogHelper.info((String)"\u5408\u5e76-\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406", (String)("\u8fdc\u7a0b\u6ce8\u518c-" + serverInfo.getUrl()), (String)"");
        return true;
    }

    @Override
    public Boolean updateServerInfo(ReportDataSyncServerInfoVO serverInfoVO) {
        boolean isAdd = StringUtils.isEmpty((String)serverInfoVO.getId());
        this.checkOrgCode(serverInfoVO, isAdd);
        if (isAdd) {
            ReportDataSyncServerListEO serverInfo = new ReportDataSyncServerListEO();
            BeanUtils.copyProperties(serverInfoVO, (Object)serverInfo);
            serverInfo.setSyncType(String.join((CharSequence)";", serverInfoVO.getSyncType()));
            serverInfo.setManageOrgCodes(String.join((CharSequence)";", serverInfoVO.getManageOrgCodes()));
            serverInfo.setSourceType(serverInfoVO.getSourceType());
            String id = UUIDUtils.newUUIDStr();
            serverInfoVO.setId(id);
            serverInfo.setId(id);
            serverInfo.setStartFlag(0);
            serverInfo.setCreateTime(new Date());
            serverInfo.setSupportAll(1);
            serverInfo.setSyncParamFlag(serverInfoVO.getSyncParamFlag() != false ? 1 : 0);
            serverInfo.setServerType(ReportDataServerType.MANUAL.getCode());
            this.reportDataSyncServerListDao.add((BaseEntity)serverInfo);
            LogHelper.info((String)"\u5408\u5e76-\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406", (String)("\u624b\u52a8\u65b0\u589e-" + serverInfo.getUrl()), (String)"");
        } else {
            ReportDataSyncServerListEO serverInfo = (ReportDataSyncServerListEO)this.reportDataSyncServerListDao.get((Serializable)((Object)serverInfoVO.getId()));
            BeanUtils.copyProperties(serverInfoVO, (Object)serverInfo);
            serverInfo.setSyncType(String.join((CharSequence)";", serverInfoVO.getSyncType()));
            serverInfo.setManageOrgCodes(String.join((CharSequence)";", serverInfoVO.getManageOrgCodes()));
            serverInfo.setModifyTime(new Date());
            serverInfo.setSourceType(serverInfoVO.getSourceType());
            serverInfo.setSyncParamFlag(serverInfoVO.getSyncParamFlag() != false ? 1 : 0);
            serverInfo.setSupportAll(1);
            serverInfo.setServerType(ReportDataServerType.MANUAL.getCode());
            this.reportDataSyncServerListDao.update((BaseEntity)serverInfo);
            LogHelper.info((String)"\u5408\u5e76-\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406", (String)("\u4fee\u6539-" + serverInfo.getUrl()), (String)"");
        }
        return true;
    }

    private void checkOrgCode(ReportDataSyncServerInfoVO serverInfoVO, boolean isAdd) {
        if (!CollectionUtils.isEmpty((Collection)serverInfoVO.getManageOrgCodes())) {
            serverInfoVO.getManageOrgCodes().remove(serverInfoVO.getOrgCode());
        }
        List<String> currOrgCodes = ReportDataSyncUtils.getOrgCodesByServerInfo(serverInfoVO);
        List<ReportDataSyncServerListEO> serverInfoList = this.reportDataSyncServerListDao.listServerInfos();
        ArrayList<String> allOrgCodes = new ArrayList<String>();
        for (ReportDataSyncServerListEO serverInfo : serverInfoList) {
            if (serverInfo.getId().equals(serverInfoVO.getId())) continue;
            allOrgCodes.addAll(ReportDataSyncUtils.getOrgCodesByServerInfo(serverInfo));
        }
        List repeatCodes = currOrgCodes.stream().filter(allOrgCodes::contains).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(repeatCodes)) {
            throw new BusinessRuntimeException("\u5355\u4f4d\u4ee3\u7801:" + String.join((CharSequence)";", repeatCodes) + "\u5df2\u5b58\u5728\uff0c" + (isAdd ? "\u65b0\u589e" : "\u4fee\u6539") + "\u5931\u8d25\u3002");
        }
    }

    @Override
    public Boolean deleteServerInfo(List<String> ids) {
        for (String id : ids) {
            ReportDataSyncServerListEO syncServerListEO = (ReportDataSyncServerListEO)this.reportDataSyncServerListDao.get((Serializable)((Object)id));
            if (syncServerListEO == null) continue;
            LogHelper.info((String)"\u5408\u5e76-\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406", (String)("\u5220\u9664-" + syncServerListEO.getUrl()), (String)"");
            this.reportDataSyncServerListDao.delete((BaseEntity)syncServerListEO);
        }
        return true;
    }

    private ReportDataSyncServerInfoVO convertEO2VO(ReportDataSyncServerListEO serverListEO) {
        if (serverListEO == null) {
            return null;
        }
        ReportDataSyncServerInfoVO serverInfoVO = new ReportDataSyncServerInfoVO();
        BeanUtils.copyProperties((Object)serverListEO, serverInfoVO);
        serverInfoVO.setSyncType(serverListEO.getSyncType() == null ? new ArrayList() : Arrays.asList(serverListEO.getSyncType().split(";")));
        serverInfoVO.setManageOrgCodes(StringUtils.isEmpty((String)serverListEO.getManageOrgCodes()) ? new ArrayList() : Arrays.asList(serverListEO.getManageOrgCodes().split(";")));
        serverInfoVO.setSyncParamFlag(serverListEO.getSyncParamFlag() != null && serverListEO.getSyncParamFlag().equals(1) ? Boolean.TRUE : Boolean.FALSE);
        serverInfoVO.setSyncModifyFlag(serverListEO.getSyncModifyFlag() != null && serverListEO.getSyncModifyFlag().equals(1) ? Boolean.TRUE : Boolean.FALSE);
        serverInfoVO.setStartFlag(serverListEO.getStartFlag() != null && serverListEO.getStartFlag().equals(1) ? Boolean.TRUE : Boolean.FALSE);
        ArrayList manageOrgMap = new ArrayList();
        serverInfoVO.getManageOrgCodes().forEach(unitCode -> {
            OrgToJsonVO unit = GcOrgBaseTool.getInstance().getOrgByCode(unitCode);
            if (Objects.nonNull(unit)) {
                HashMap<String, String> orgMap = new HashMap<String, String>();
                orgMap.put("code", (String)unitCode);
                orgMap.put("title", unit.getTitle());
                manageOrgMap.add(orgMap);
            }
        });
        serverInfoVO.setManageOrgMap(manageOrgMap);
        return serverInfoVO;
    }

    @Override
    public ReportDataSyncServerInfoVO getServerInfoByOrgCode(String orgType, String periodStr, String orgCode) {
        Assert.isNotNull((Object)orgType, (String)"\u5355\u4f4d\u7c7b\u578b\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)periodStr, (String)"\u65f6\u671f\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)orgCode, (String)"\u5355\u4f4d\u4e3a\u7a7a", (Object[])new Object[0]);
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = instance.getOrgByCode(orgCode);
        List<String> orgParents = Arrays.asList(orgCacheVO.getParents());
        List<Object> syncServerInfoVOS = this.listServerInfos();
        syncServerInfoVOS = syncServerInfoVOS.stream().filter(info -> orgParents.contains(info.getOrgCode())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(syncServerInfoVOS)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5f53\u524d\u5355\u4f4d\u5bf9\u5e94\u7684\u670d\u52a1");
        }
        ReportDataSyncServerInfoVO serverInfoVO = (ReportDataSyncServerInfoVO)syncServerInfoVOS.get(0);
        if (!serverInfoVO.getStartFlag().booleanValue()) {
            throw new BusinessRuntimeException("\u5f53\u524d\u5355\u4f4d\u6240\u5c5e\u670d\u52a1\u670d\u52a1\u5df2\u505c\u7528\u3002");
        }
        serverInfoVO.setUserName(NpContextHolder.getContext().getUserName());
        serverInfoVO.setPwd(this.passwordService.get(NpContextHolder.getContext().getUserId()));
        serverInfoVO.setEncryptType(this.passwordEncoderManage.getEncodeType());
        return serverInfoVO;
    }
}

