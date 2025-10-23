/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.certification.dto.NvwaAppRequestDTO
 *  com.jiuqi.nvwa.certification.manage.INvwaAppRequestManage
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  com.jiuqi.nvwa.subsystem.core.SubSystemException
 *  com.jiuqi.nvwa.subsystem.core.manage.ISubServerManager
 *  com.jiuqi.nvwa.subsystem.core.model.SubServer
 *  com.jiuqi.nvwa.subsystem.core.util.SubServerUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageClient
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.http.client.utils.URIBuilder
 *  org.apache.http.entity.ContentType
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.MediaType
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.transmission.data.domain.SyncEntityLastHistoryDO;
import com.jiuqi.nr.transmission.data.dto.SyncEntityLastHistoryDTO;
import com.jiuqi.nr.transmission.data.intf.EntityInfoParam;
import com.jiuqi.nr.transmission.data.intf.RejectMessageResult;
import com.jiuqi.nr.transmission.data.reject.RejectParamDTO;
import com.jiuqi.nr.transmission.data.service.IReportParamService;
import com.jiuqi.nr.transmission.data.service.ISyncEntityLastHistoryService;
import com.jiuqi.nr.transmission.data.service.RejectService;
import com.jiuqi.nr.transmission.data.vo.RejectParamVO;
import com.jiuqi.nvwa.certification.dto.NvwaAppRequestDTO;
import com.jiuqi.nvwa.certification.manage.INvwaAppRequestManage;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import com.jiuqi.nvwa.subsystem.core.SubSystemException;
import com.jiuqi.nvwa.subsystem.core.manage.ISubServerManager;
import com.jiuqi.nvwa.subsystem.core.model.SubServer;
import com.jiuqi.nvwa.subsystem.core.util.SubServerUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageClient;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RejectServiceImpl
implements RejectService {
    private static final Logger logger = LoggerFactory.getLogger(RejectServiceImpl.class);
    @Autowired
    private VaMessageClient messageClient;
    @Autowired
    private INvwaAppRequestManage requestManage;
    @Autowired
    private ISubServerManager subServerManager;
    @Qualifier(value="syncRestTemplate")
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private ISyncEntityLastHistoryService syncEntityLastHistoryService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private IReportParamService reportParamService;

    @Override
    public void sendRejectAction(RejectParamDTO rejectParamDTO) {
        List<String> unitIds = rejectParamDTO.getUnitIds();
        if (CollectionUtils.isEmpty(unitIds)) {
            return;
        }
        List<SubServer> targetServes = this.getTargetServes();
        if (CollectionUtils.isEmpty(targetServes)) {
            return;
        }
        Map<String, List<String>> serveToOrgMap = this.buildServeToOrgMap(targetServes, unitIds);
        for (SubServer targetServe : targetServes) {
            List<String> entityKeyDatas = serveToOrgMap.get(targetServe.getGuid());
            if (entityKeyDatas == null) continue;
            this.sendMessageByUser(rejectParamDTO, entityKeyDatas, targetServe);
        }
    }

    private void sendMessageByUser(RejectParamDTO rejectParamDTO, List<String> entityKeyDatas, SubServer targetServe) {
        Map<String, Map<String, List<String>>> userToEntityMap = this.getUserToEntityMap(rejectParamDTO, entityKeyDatas);
        Set<String> userIds = userToEntityMap.keySet();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(rejectParamDTO.getFormSchemeKey());
        WorkFlowType startType = formScheme.getFlowsSetting().getWordFlowType();
        String uri = targetServe.getBackendAddr();
        boolean check = false;
        try {
            check = this.check(rejectParamDTO, targetServe);
        }
        catch (Exception e) {
            logger.info("\u5f53\u524d\u670d\u52a1\u7684\u5b50\u670d\u52a1\uff1a\u201d" + targetServe.getTitle() + "\u201c\u6ca1\u6709\u542f\u52a8");
        }
        for (String userId : userIds) {
            Map<String, List<String>> entityKeys = userToEntityMap.get(userId);
            RejectParamVO childrenParam = new RejectParamVO();
            childrenParam.setFormSchemeKey(rejectParamDTO.getFormSchemeKey());
            childrenParam.setPeriod(rejectParamDTO.getPeriod());
            if (WorkFlowType.ENTITY.equals((Object)startType)) {
                childrenParam.setUnitIds(new ArrayList<String>(entityKeys.keySet()));
            } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                childrenParam.setUnitIds(new ArrayList<String>(entityKeys.keySet()));
                childrenParam.setUnitToflow(entityKeys);
            } else {
                childrenParam.setUnitIds(new ArrayList<String>(entityKeys.keySet()));
                childrenParam.setUnitToflow(entityKeys);
            }
            childrenParam.setUser(userId);
            childrenParam.setUserName(rejectParamDTO.getUserName());
            if (check) {
                logger.info("\u591a\u7ea7\u90e8\u7f72\u540c\u6b65\u7ba1\u7406\u5728\u7ebf\u540c\u6b65\u666e\u901a\u7528\u6237\u4e0a\u62a5\u62a5\u8fc7\u7684\u5355\u4f4d\u9000\u56de\uff0c\u6821\u9a8c\u4e0b\u7ea7\u670d\u52a1\u542f\u52a8\uff0c\u53d1\u9001\u9000\u56de\u6d88\u606f");
                this.sendMessage(childrenParam, targetServe);
                continue;
            }
            String message = "  \u4e0b\u7ea7\u670d\u52a1\uff1a\u201d" + targetServe.getTitle() + "\u201c\u8fde\u63a5\u5f02\u5e38\uff0c\u591a\u7ea7\u90e8\u7f72\u4e0a\u7ea7\u9000\u56de\u53d1\u9001\u6b64\u90ae\u4ef6\uff01";
            logger.info("\u591a\u7ea7\u90e8\u7f72\u540c\u6b65\u7ba1\u7406\u5728\u7ebf\u540c\u6b65\u666e\u901a\u7528\u6237\u4e0a\u62a5\u62a5\u8fc7\u7684\u5355\u4f4d\u9000\u56de\uff0c\u6821\u9a8c\u4e0b\u7ea7\u670d\u52a1\u672a\u542f\u52a8\uff0c\u53d1\u9001\u90ae\u4ef6");
            this.acceptRejectAction(childrenParam, VaMessageOption.MsgChannel.EMAIL, message);
        }
    }

    private void sendMessage(RejectParamVO rejectParamVo, SubServer targetServe) {
        this.setContext(rejectParamVo.getUserName());
        NvwaAppRequestDTO authInfo = this.requestManage.getAuthInfo(targetServe.getName());
        Map headerMap = authInfo.getHeaderMap();
        String uri = targetServe.getBackendAddr();
        String api = "api/v1/sync/scheme/reject/accept";
        HttpHeaders headers = new HttpHeaders();
        Set keySet = headerMap.keySet();
        for (String key : keySet) {
            headers.set(key, (String)headerMap.get(key));
        }
        headers.set("tenant", "__default_tenant__");
        headers.setContentType(MediaType.parseMediaType((String)"application/json; charset=UTF-8"));
        headers.add("Accept", ContentType.APPLICATION_JSON.toString());
        HttpEntity httpEntity = new HttpEntity((Object)JacksonUtils.objectToJson((Object)rejectParamVo), (MultiValueMap)headers);
        try {
            URI url = new URIBuilder(uri).setPath(api).build();
            RejectMessageResult rejectMessageResult = (RejectMessageResult)this.restTemplate.postForObject(url, (Object)httpEntity, RejectMessageResult.class);
        }
        catch (Exception e) {
            String message = "  \u4e0b\u7ea7\u670d\u52a1\uff1a\u201d" + targetServe.getTitle() + "\u201c\u8fde\u63a5\u5f02\u5e38\uff0c\u591a\u7ea7\u90e8\u7f72\u4e0a\u7ea7\u9000\u56de\u53d1\u9001\u6b64\u90ae\u4ef6\uff01";
            this.acceptRejectAction(rejectParamVo, VaMessageOption.MsgChannel.EMAIL, message);
            logger.error(e.getMessage(), e);
        }
    }

    private Boolean check(RejectParamDTO rejectParamdto, SubServer targetServe) {
        this.setContext(rejectParamdto.getUserName());
        NvwaAppRequestDTO authInfo = this.requestManage.getAuthInfo(targetServe.getName());
        Map headerMap = authInfo.getHeaderMap();
        String uri = targetServe.getBackendAddr();
        String api = "api/v1/sync/scheme/reject/subserver_exist";
        HttpHeaders headers = new HttpHeaders();
        Set keySet = headerMap.keySet();
        for (String key : keySet) {
            headers.set(key, (String)headerMap.get(key));
        }
        headers.set("tenant", "__default_tenant__");
        headers.setContentType(MediaType.parseMediaType((String)"application/json; charset=UTF-8"));
        headers.add("Accept", ContentType.APPLICATION_JSON.toString());
        try {
            URI url = new URIBuilder(uri).setPath(api).build();
            HttpEntity httpEntity = new HttpEntity((MultiValueMap)headers);
            RejectMessageResult checkResult = (RejectMessageResult)this.restTemplate.postForObject(url, (Object)httpEntity, RejectMessageResult.class);
            if (checkResult == null) {
                return false;
            }
            return true;
        }
        catch (Exception t) {
            logger.info("\u591a\u7ea7\u90e8\u7f72\u540c\u6b65\u7ba1\u7406\u5728\u7ebf\u540c\u6b65\u666e\u901a\u7528\u6237\u4e0a\u62a5\u62a5\u8fc7\u7684\u5355\u4f4d\u9000\u56de\uff0c\u6821\u9a8c\u4e0b\u7ea7\u670d\u52a1\u672a\u542f\u52a8" + t.getMessage());
            return false;
        }
    }

    @Override
    public void acceptRejectAction(RejectParamDTO rejectParamDTO, VaMessageOption.MsgChannel msgChannel, String message) {
        VaMessageSendDTO messageDTO = new VaMessageSendDTO();
        ArrayList<String> users = new ArrayList<String>(1);
        users.add(rejectParamDTO.getUser());
        messageDTO.setReceiveUserIds(users);
        messageDTO.setMsgChannel(msgChannel);
        messageDTO.setGrouptype("\u901a\u77e5\u5f85\u529e");
        messageDTO.setTitle("\u4e0a\u7ea7\u9000\u56de\u901a\u77e5");
        messageDTO.setMsgtype("\u9000\u56de\u901a\u77e5");
        String s = this.buildMessage(rejectParamDTO);
        messageDTO.setContent(message + s);
        this.messageClient.addMsg(messageDTO);
    }

    private String buildMessage(RejectParamDTO rejectParamDTO) {
        StringBuffer sbs = new StringBuffer();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(rejectParamDTO.getFormSchemeKey());
        WorkFlowType startType = formScheme.getFlowsSetting().getWordFlowType();
        String dw = formScheme.getDw();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String dimensionName = this.entityMetaService.getDimensionName(dw);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(dimensionName, rejectParamDTO.getUnitIds());
        dimensionValueSet.setValue("DATATIME", (Object)rejectParamDTO.getPeriod());
        List<EntityInfoParam> entityList = this.reportParamService.getEntityList(dimensionValueSet, rejectParamDTO.getFormSchemeKey(), AuthorityType.None, true);
        sbs.append("\u3010").append(taskDefine.getTitle()).append("\u3011\u4e2d\u7684\u65f6\u671f\u4e3a\u3010").append(rejectParamDTO.getPeriod());
        int index = 0;
        if (WorkFlowType.ENTITY.equals((Object)startType)) {
            sbs.append("\u3011\u7684\u5355\u4f4d:");
            for (EntityInfoParam infoParam : entityList) {
                String title = infoParam.getTitle();
                sbs.append(title).append("[").append(infoParam.getEntityKeyData()).append("]");
                if (index < entityList.size() - 1) {
                    sbs.append("\u3001");
                }
                ++index;
            }
        } else if (WorkFlowType.GROUP.equals((Object)startType)) {
            Map<String, List<String>> unitToflow = rejectParamDTO.getUnitToflow();
            sbs.append("\u3011\u4e0b\u5355\u4f4d\u7684\u5206\u7ec4:");
            for (EntityInfoParam infoParam : entityList) {
                String title = infoParam.getTitle();
                sbs.append(title).append("[").append(infoParam.getEntityKeyData()).append("]\uff1a");
                List<String> formGroupKeys = unitToflow.get(infoParam.getEntityKeyData());
                int j = 0;
                for (String formGroupKey : formGroupKeys) {
                    FormGroupDefine groupDefines = this.runTimeViewController.queryFormGroup(formGroupKey);
                    sbs.append(groupDefines.getTitle());
                    if (j < formGroupKeys.size() - 1) {
                        sbs.append("\u3001");
                    }
                    ++j;
                }
                if (index < entityList.size() - 1) {
                    sbs.append("\uff0c");
                }
                ++index;
            }
        } else {
            Map<String, List<String>> unitToflow = rejectParamDTO.getUnitToflow();
            sbs.append("\u3011\u4e0b\u5355\u4f4d\u7684\u62a5\u8868:");
            for (EntityInfoParam infoParam : entityList) {
                String title = infoParam.getTitle();
                sbs.append(title).append("[").append(infoParam.getEntityKeyData()).append("]\uff1a");
                List<String> formKeys = unitToflow.get(infoParam.getEntityKeyData());
                int j = 0;
                List formDefines = this.runTimeViewController.queryFormsById(formKeys);
                for (FormDefine formDefine : formDefines) {
                    sbs.append(formDefine.getTitle()).append("[").append(formDefine.getFormCode()).append("]");
                    if (j < formDefines.size() - 1) {
                        sbs.append("\u3001");
                    }
                    ++j;
                }
                if (index < entityList.size() - 1) {
                    sbs.append("\uff0c");
                }
                ++index;
            }
        }
        sbs.append(" \u7684\u5df2\u4e0a\u62a5\u6570\u636e\u88ab\u4e0a\u7ea7\u670d\u52a1\u9000\u56de\u4e86\uff0c\u9700\u8981\u60a8\u5728\u5f53\u524d\u670d\u52a1\u6267\u884c\u76f8\u540c\u7684\u9000\u56de\u64cd\u4f5c\u3002");
        return sbs.toString();
    }

    private Map<String, String> getEntityTitleMap(String period, String periodView, String entityId, List<String> entityKeys) {
        HashMap<String, String> titleMap = new HashMap<String, String>(entityKeys.size());
        String dimensionName = this.entityMetaService.getDimensionName(entityId);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(dimensionName, entityKeys);
        dimensionValueSet.setValue("DATATIME", (Object)period);
        entityQuery.setMasterKeys(dimensionValueSet);
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId));
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setPeriodView(periodView);
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        List allRows = iEntityTable.getAllRows();
        for (IEntityRow row : allRows) {
            titleMap.put(row.getEntityKeyData(), row.getTitle());
        }
        return titleMap;
    }

    private List<SubServer> getTargetServes() {
        List subServers = null;
        try {
            subServers = this.subServerManager.getSubServers();
            SubServer currectSubServer = this.subServerManager.getCurrectSubServer();
            if (subServers != null && currectSubServer != null) {
                subServers = subServers.stream().filter(e -> !e.getGuid().equals(currectSubServer.getGuid())).collect(Collectors.toList());
            }
        }
        catch (SubSystemException e2) {
            throw new RuntimeException("\u67e5\u8be2\u4e0d\u5230\u5b50\u670d\u52a1\u5217\u8868\uff0c\u8bf7\u68c0\u67e5\u4e3b\u5b50\u670d\u52a1\u914d\u7f6e");
        }
        return subServers;
    }

    private Map<String, List<String>> buildServeToOrgMap(List<SubServer> subServers, List<String> entityIds) {
        HashMap<String, List<String>> serveMap = new HashMap<String, List<String>>(subServers.size());
        for (SubServer subServer : subServers) {
            List orgIdList = SubServerUtil.getConfigOrgIdList((String)subServer.getGuid());
            if (CollectionUtils.isEmpty(orgIdList)) continue;
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCategoryname("MD_ORG");
            orgDTO.setOrgCodes(entityIds);
            PageVO list = this.orgDataClient.list(orgDTO);
            List rows = list.getRows();
            Map<String, String> idToCode = rows.stream().collect(Collectors.toMap(e -> e.getId().toString(), e -> e.getCode()));
            HashMap<String, List<String>> idToParent = new HashMap<String, List<String>>();
            for (OrgDO row : rows) {
                String parents = row.getParents();
                String[] parentCodes = parents.split("/");
                idToParent.put(row.getId().toString(), Arrays.asList(parentCodes));
            }
            List ids = rows.stream().map(e -> e.getId().toString()).collect(Collectors.toList());
            List serveOrgCodes = ids.stream().filter(e -> {
                boolean flag = orgIdList.contains(e);
                if (!flag) {
                    List parentCodes = (List)idToParent.get(e);
                    List<OrgDO> orgData = this.getOrgData(parentCodes);
                    flag = orgData.stream().anyMatch(e1 -> orgIdList.contains(e1.getId().toString()));
                }
                return flag;
            }).map(e -> (String)idToCode.get(e)).collect(Collectors.toList());
            serveMap.put(subServer.getGuid(), serveOrgCodes);
        }
        return serveMap;
    }

    private List<OrgDO> getOrgData(List<String> entityIds) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setOrgCodes(entityIds);
        PageVO list = this.orgDataClient.list(orgDTO);
        List rows = list.getRows();
        return rows;
    }

    private Map<String, Map<String, List<String>>> getUserToEntityMap(RejectParamDTO rejectParamDTO, List<String> entityKeyDatas) {
        HashMap userToEntityMap = new HashMap(16);
        HashMap<String, Map<String, List<String>>> userToEntityToFlowsMaps = new HashMap<String, Map<String, List<String>>>(16);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(rejectParamDTO.getFormSchemeKey());
        WorkFlowType startType = formScheme.getFlowsSetting().getWordFlowType();
        SyncEntityLastHistoryDO syncEntityLastHistoryDO = new SyncEntityLastHistoryDO();
        syncEntityLastHistoryDO.setTaskKey(rejectParamDTO.getTask());
        syncEntityLastHistoryDO.setPeriod(rejectParamDTO.getPeriod());
        List<SyncEntityLastHistoryDTO> list = this.syncEntityLastHistoryService.lists(syncEntityLastHistoryDO, entityKeyDatas);
        Set userIdSet = list.stream().map(SyncEntityLastHistoryDO::getUserId).collect(Collectors.toSet());
        userIdSet.stream().forEach(userId -> {
            Map cfr_ignored_0 = userToEntityToFlowsMaps.put((String)userId, new HashMap());
        });
        if (WorkFlowType.ENTITY.equals((Object)startType)) {
            Map<String, String> entityToUserId = list.stream().collect(Collectors.toMap(SyncEntityLastHistoryDO::getEntity, SyncEntityLastHistoryDO::getUserId, (key1, key2) -> key1));
            for (Map.Entry<String, String> stringStringEntry : entityToUserId.entrySet()) {
                String entity = stringStringEntry.getKey();
                String userId2 = stringStringEntry.getValue();
                Map stringListMap = (Map)userToEntityToFlowsMaps.get(userId2);
                stringListMap.computeIfAbsent(entity, key -> new ArrayList()).add(entity);
            }
        } else if (WorkFlowType.GROUP.equals((Object)startType)) {
            Map<String, List<SyncEntityLastHistoryDTO>> entityToUploadObj = list.stream().collect(Collectors.groupingBy(SyncEntityLastHistoryDO::getEntity));
            for (Map.Entry<String, List<SyncEntityLastHistoryDTO>> stringListEntry : entityToUploadObj.entrySet()) {
                String entity = stringListEntry.getKey();
                List<SyncEntityLastHistoryDTO> UploadObjForEntity = stringListEntry.getValue();
                for (SyncEntityLastHistoryDTO syncEntityLastHistoryDTO : UploadObjForEntity) {
                    String userId3 = syncEntityLastHistoryDTO.getUserId();
                    String formGroupKey = syncEntityLastHistoryDTO.getFormKey();
                    if (!rejectParamDTO.getFromGroupKeys().contains(formGroupKey)) continue;
                    Map stringListMap = (Map)userToEntityToFlowsMaps.get(userId3);
                    stringListMap.computeIfAbsent(entity, key -> new ArrayList()).add(formGroupKey);
                }
            }
        } else {
            Map<String, List<SyncEntityLastHistoryDTO>> entityToUploadObj = list.stream().collect(Collectors.groupingBy(SyncEntityLastHistoryDO::getEntity));
            for (Map.Entry<String, List<SyncEntityLastHistoryDTO>> stringListEntry : entityToUploadObj.entrySet()) {
                String entity = stringListEntry.getKey();
                List<SyncEntityLastHistoryDTO> UploadObjForEntity = stringListEntry.getValue();
                for (SyncEntityLastHistoryDTO syncEntityLastHistoryDTO : UploadObjForEntity) {
                    String userId4 = syncEntityLastHistoryDTO.getUserId();
                    String formKey = syncEntityLastHistoryDTO.getFormKey();
                    if (!rejectParamDTO.getFormKeys().contains(formKey)) continue;
                    Map stringListMap = (Map)userToEntityToFlowsMaps.get(userId4);
                    stringListMap.computeIfAbsent(entity, key -> new ArrayList()).add(formKey);
                }
            }
        }
        return userToEntityToFlowsMaps;
    }

    private String getLastUploadUserId(RejectParamDTO rejectParamDTO, String entityKeyData) {
        SyncEntityLastHistoryDO syncEntityLastHistoryDO = new SyncEntityLastHistoryDO();
        syncEntityLastHistoryDO.setTaskKey(rejectParamDTO.getTask());
        syncEntityLastHistoryDO.setPeriod(rejectParamDTO.getPeriod());
        syncEntityLastHistoryDO.setEntity(entityKeyData);
        List<SyncEntityLastHistoryDTO> list = this.syncEntityLastHistoryService.list(syncEntityLastHistoryDO);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0).getUserId();
        }
        return "";
    }

    private Map<String, List<SyncEntityLastHistoryDTO>> getAllLastUploadUserId(RejectParamDTO rejectParamDTO, List<String> entityKeyData) {
        SyncEntityLastHistoryDO syncEntityLastHistoryDO = new SyncEntityLastHistoryDO();
        syncEntityLastHistoryDO.setTaskKey(rejectParamDTO.getTask());
        syncEntityLastHistoryDO.setPeriod(rejectParamDTO.getPeriod());
        List<SyncEntityLastHistoryDTO> list = this.syncEntityLastHistoryService.lists(syncEntityLastHistoryDO, entityKeyData);
        HashMap<String, List<SyncEntityLastHistoryDTO>> entityToUpload = new HashMap();
        entityToUpload = list.stream().collect(Collectors.groupingBy(SyncEntityLastHistoryDO::getEntity));
        return entityToUpload;
    }

    private void setContext(String username) {
        NpContextImpl npContext = null;
        try {
            npContext = this.buildContext(username);
        }
        catch (JQException e) {
            throw new RuntimeException(e);
        }
        NpContextHolder.setContext((NpContext)npContext);
    }

    private NpContextImpl buildContext(String userName) throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = this.buildUserContext(userName);
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = this.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        return npContext;
    }

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getFullname());
        return identity;
    }

    private NpContextUser buildUserContext(String userName) throws JQException {
        NpContextUser userContext = new NpContextUser();
        User user = this.getUserByUserName(userName);
        if (user == null) {
            throw new JQException((ErrorEnum)PlanTaskError.QUERY_USER);
        }
        userContext.setId(user.getId());
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private User getUserByUserName(String userName) {
        if (StringUtils.isEmpty((CharSequence)userName)) {
            return null;
        }
        Optional user = this.userService.find(userName);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = this.systemUserService.find(userName);
        if (sysUser.isPresent()) {
            return (User)sysUser.get();
        }
        return null;
    }
}

