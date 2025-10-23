/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.paging.OrderField
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper
 *  com.jiuqi.nr.zbquery.model.PageInfo
 *  com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.user.service.NvwaUserService
 *  com.jiuqi.nvwa.authority.user.vo.UserDTORes
 *  com.jiuqi.nvwa.authority.user.vo.UserQueryReq
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$AuthorityState
 *  com.jiuqi.nvwa.authority.vo.AuthorityPrivilegeSaveReq
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.organization.service.impl.help.OrgDataCacheService
 */
package com.jiuqi.nr.singlequeryimport.auth.share.service.impl;

import com.jiuqi.bi.database.paging.OrderField;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.singlequeryimport.auth.FinalaccountQueryAuthResource;
import com.jiuqi.nr.singlequeryimport.auth.FinalaccountQueryAuthResourceType;
import com.jiuqi.nr.singlequeryimport.auth.service.impl.FinalaccountQueryAuthServiceImpl;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.AuthShareParams;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.AuthShareUserParams;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.ModalNodeInfo;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.ReturnObject;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.SingleQueryAuthShareCacheUserInfo;
import com.jiuqi.nr.singlequeryimport.auth.share.service.AuthShareService;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.bean.QueryModelNode;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nr.zbquery.model.PageInfo;
import com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.user.service.NvwaUserService;
import com.jiuqi.nvwa.authority.user.vo.UserDTORes;
import com.jiuqi.nvwa.authority.user.vo.UserQueryReq;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.AuthorityPrivilegeSaveReq;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthShareServiceImpl
implements AuthShareService {
    private static final Logger logger = LoggerFactory.getLogger(AuthShareServiceImpl.class);
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    EntityQueryHelper entityQueryHelper;
    @Autowired
    private NvwaUserService nvwaUserService;
    @Autowired
    private QueryModeleDao queryModeleDao;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private FinalaccountQueryAuthServiceImpl finalaccountQueryAuthService;
    private FinalaccountQueryAuthResource finalaccountQueryAuthResource;
    @Autowired
    private DefaultAuthQueryService defaultAuthQueryService;
    private DefaultResourceCategory defaultResourceCategory;
    @Autowired
    OrgDataCacheService orgDataCacheService;
    @Autowired
    OrgIdentityService orgIdentityService;

    @Override
    public ReturnObject getChildUsers(AuthShareParams params) {
        ReturnObject result = new ReturnObject();
        logger.info("\u5f53\u524d\u7528\u6237\u662f\u5426\u662f\u7ba1\u7406\u5458-->{},\u662f\u5426\u662f\u4e1a\u52a1\u7ba1\u7406\u5458--{},\u7528\u6237\u7c7b\u578b--->{}", this.systemIdentityService.isAdmin(), this.systemIdentityService.isBusinessManager(), this.getCurrUserType());
        try {
            result = this.systemIdentityService.isAdmin() ? this.getAllUser(params) : (this.systemIdentityService.isBusinessManager() ? this.getUserInMulRole(params) : this.getUserInCurUserCode(params));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        List<SingleQueryAuthShareCacheUserInfo> userList = result.getObj();
        if (!params.getModelIds().isEmpty() && null != params.getModelIds().get(0)) {
            for (SingleQueryAuthShareCacheUserInfo info : userList) {
                String resourceId = FinalaccountQueryAuthResourceType.FQ_MODEL_NODE.toResourceId(params.getModelIds().get(0));
                String identityId = info.getIdentityid();
                info.setCanAuthorize(this.finalaccountQueryAuthService.canAuthorizeModelByIdentityId(params.getModelIds().get(0), identityId));
                info.setCanEdit(this.finalaccountQueryAuthService.canWriteModelByIdentityId(params.getModelIds().get(0), identityId));
                info.setCanRead(this.finalaccountQueryAuthService.canReadModelByIdentityId(params.getModelIds().get(0), identityId));
            }
        }
        return result;
    }

    @Override
    public List<QueryModelNode> getModelGroupWithAuth(String taskKey) throws Exception {
        ArrayList<QueryModelNode> modelNodeList = new ArrayList<QueryModelNode>();
        List<QueryModel> groupModel = this.queryModeleDao.getGroup(taskKey);
        if (!groupModel.isEmpty()) {
            FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(groupModel.get(0).getFormschemeKey());
            for (QueryModel group : groupModel) {
                String key = group.getGroup();
                if (!this.finalaccountQueryAuthService.canAuthorizeGroup(formScheme.getKey() + "#" + key)) continue;
                QueryModelNode myModelNode = new QueryModelNode();
                myModelNode.setId(key);
                myModelNode.setTitle(key);
                myModelNode.setOrg(formScheme.getDw());
                myModelNode.setCanRead(this.finalaccountQueryAuthService.canReadGroup(formScheme.getKey() + "#" + key));
                myModelNode.setCanWrite(this.finalaccountQueryAuthService.canWriteGroup(formScheme.getKey() + "#" + key));
                myModelNode.setCanAuthorize(true);
                modelNodeList.add(myModelNode);
            }
        }
        return modelNodeList;
    }

    @Override
    public List<QueryModelNode> getModelWithAuth(String formSchemeKey, String groupKey) throws Exception {
        List<QueryModel> allModelList = this.queryModeleDao.getModel(formSchemeKey, groupKey);
        ArrayList<QueryModelNode> resultList = new ArrayList<QueryModelNode>();
        for (QueryModel model : allModelList) {
            if (!this.finalaccountQueryAuthService.canAuthorizeModel(model.getKey()) || null == model.getItemTitle()) continue;
            FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(model.getFormschemeKey());
            QueryModelNode myModelNode = new QueryModelNode();
            myModelNode.setOrg(formScheme.getDw());
            myModelNode.setId(model.getKey());
            myModelNode.setTitle(model.getItemTitle());
            myModelNode.setCustom(model.getCustom());
            myModelNode.setOrder(model.getOrder());
            myModelNode.setItem(model.getItem());
            myModelNode.setDisUse(model.getDisUse());
            myModelNode.setCanRead(this.finalaccountQueryAuthService.canReadModel(model.getKey()));
            myModelNode.setCanWrite(this.finalaccountQueryAuthService.canWriteModel(model.getKey()));
            myModelNode.setCanAuthorize(this.finalaccountQueryAuthService.canAuthorizeModel(model.getKey()));
            resultList.add(myModelNode);
        }
        return resultList;
    }

    private List<SingleQueryAuthShareCacheUserInfo> getAuthByModalAndUser(List<String> modalIds, List<String> userKeys) {
        ArrayList<SingleQueryAuthShareCacheUserInfo> result = new ArrayList<SingleQueryAuthShareCacheUserInfo>();
        for (String modalId : modalIds) {
            for (String userKey : userKeys) {
                SingleQueryAuthShareCacheUserInfo info = new SingleQueryAuthShareCacheUserInfo();
                info.setCanAuthorize(this.finalaccountQueryAuthService.canAuthorizeModelByIdentityId(modalId, userKey));
                info.setCanEdit(this.finalaccountQueryAuthService.canWriteModelByIdentityId(modalId, userKey));
                info.setCanRead(this.finalaccountQueryAuthService.canReadModelByIdentityId(modalId, userKey));
                result.add(info);
            }
        }
        return result;
    }

    private List<OrderField> getOrderFields() {
        ArrayList<OrderField> result = new ArrayList<OrderField>();
        OrderField orderField = new OrderField(String.format("b.%s", "BQ_USER_KEY"));
        orderField.setOrderMode("ASC");
        result.add(orderField);
        return result;
    }

    @Override
    public boolean setUsersWithModelAuth(AuthShareUserParams params) {
        try {
            List<String> modalIds = this.getModalIds(this.getAuthShareParams(params));
            List<AuthorityPrivilegeSaveReq> saveReqs = this.getuthorityPrivilegeSaveReqs(modalIds, params, null);
            for (AuthorityPrivilegeSaveReq saveReq : saveReqs) {
                if (this.finalaccountQueryAuthResource == null) {
                    this.finalaccountQueryAuthResource = (FinalaccountQueryAuthResource)((Object)BeanUtil.getBean(FinalaccountQueryAuthResource.class));
                }
                this.finalaccountQueryAuthResource.privilegeSave(saveReq);
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean batchSetUsersWithModelAuth(AuthShareUserParams params, AsyncTaskMonitor monitor) {
        if (this.defaultResourceCategory == null) {
            this.defaultResourceCategory = (DefaultResourceCategory)BeanUtil.getBean(FinalaccountQueryAuthResource.class);
        }
        try {
            List<String> modalIds = this.getModalIds(this.getAuthShareParams(params));
            List<AuthorityPrivilegeSaveReq> saveReqs = this.getuthorityPrivilegeSaveReqs(modalIds, params, monitor);
            if (saveReqs.size() == 0) {
                return true;
            }
            double step = 0.5 / (double)saveReqs.size();
            int index = 1;
            for (AuthorityPrivilegeSaveReq saveReq : saveReqs) {
                this.defaultResourceCategory.privilegeSave(saveReq);
                if (monitor == null) continue;
                if (monitor.isCancel()) break;
                monitor.progressAndMessage(0.5 + (double)index * step, "\u8bbe\u7f6e\u6743\u9650");
                ++index;
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public SingleQueryAuthShareCacheUserInfo getCurUserAuthByModals(String formSchemeKey, List<String> modalIds) {
        SingleQueryAuthShareCacheUserInfo result = new SingleQueryAuthShareCacheUserInfo();
        result.setCanRead(true);
        result.setCanEdit(true);
        result.setCanAuthorize(true);
        ArrayList<String> userKeys = new ArrayList<String>();
        if (!"sys_user_admin".equals(NpContextHolder.getContext().getUserId())) {
            userKeys.add(NpContextHolder.getContext().getUserId());
            List<SingleQueryAuthShareCacheUserInfo> userAuths = this.getAuthByModalAndUser(modalIds, userKeys);
            for (SingleQueryAuthShareCacheUserInfo userInfo : userAuths) {
                if (!userInfo.getCanRead()) {
                    result.setCanRead(false);
                }
                if (!userInfo.getCanEdit()) {
                    result.setCanEdit(false);
                }
                if (!userInfo.getCanAuthorize()) {
                    result.setCanAuthorize(false);
                }
                if (result.getCanRead() || result.getCanEdit() || result.getCanAuthorize()) continue;
                return result;
            }
        }
        return result;
    }

    @Override
    public SingleQueryAuthShareCacheUserInfo getCurUserAuthByGroup(String formSchemeKey, List<String> groupKeys) {
        String resourceId = formSchemeKey + "#" + groupKeys.get(0);
        boolean a = this.finalaccountQueryAuthService.canAuthorizeGroup(resourceId);
        boolean r = this.finalaccountQueryAuthService.canReadGroup(resourceId);
        boolean w = this.finalaccountQueryAuthService.canWriteGroup(resourceId);
        SingleQueryAuthShareCacheUserInfo result = new SingleQueryAuthShareCacheUserInfo();
        result.setCanRead(r);
        result.setCanEdit(w);
        result.setCanAuthorize(a);
        return result;
    }

    @Override
    public boolean addCurUserPrivilege(String modelId, FinalaccountQueryAuthResourceType resourceType) {
        AuthorityPrivilegeSaveReq saveReq = this.getPrivilegeSaveReq(modelId, resourceType);
        if (this.finalaccountQueryAuthResource == null) {
            this.finalaccountQueryAuthResource = (FinalaccountQueryAuthResource)((Object)BeanUtil.getBean(FinalaccountQueryAuthResource.class));
        }
        this.finalaccountQueryAuthResource.privilegeSave(saveReq);
        return true;
    }

    @Override
    public boolean addCurUserGroupPrivilege(String formSchemeKey, String groupKey, FinalaccountQueryAuthResourceType resourceType) {
        AuthorityPrivilegeSaveReq saveReq = this.getGroupPrivilegeSaveReq(formSchemeKey, groupKey, resourceType);
        if (this.finalaccountQueryAuthResource == null) {
            this.finalaccountQueryAuthResource = (FinalaccountQueryAuthResource)((Object)BeanUtil.getBean(FinalaccountQueryAuthResource.class));
        }
        this.finalaccountQueryAuthResource.privilegeSave(saveReq);
        return true;
    }

    @Override
    public boolean addCurUserGroupReadPrivilege(String formSchemeKey, String groupKey, FinalaccountQueryAuthResourceType resourceType) {
        AuthorityPrivilegeSaveReq saveReq = this.getGroupReadPrivilegeSaveReq(formSchemeKey, groupKey, resourceType);
        if (this.finalaccountQueryAuthResource == null) {
            this.finalaccountQueryAuthResource = (FinalaccountQueryAuthResource)((Object)BeanUtil.getBean(FinalaccountQueryAuthResource.class));
        }
        this.finalaccountQueryAuthResource.privilegeSave(saveReq);
        return true;
    }

    private AuthorityPrivilegeSaveReq getPrivilegeSaveReq(String modelId, FinalaccountQueryAuthResourceType resourceType) {
        ArrayList<String> modelIds = new ArrayList<String>();
        modelIds.add(modelId);
        List<ModalNodeInfo> modalNodeInfos = this.queryModeleDao.getModalNodeInfosByKeys(modelIds);
        SingleQueryAuthShareCacheUserInfo userInfo = new SingleQueryAuthShareCacheUserInfo();
        userInfo.setUserKey(NpContextHolder.getContext().getUserId());
        userInfo.setCanRead(true);
        userInfo.setCanEdit(true);
        userInfo.setCanAuthorize(true);
        return this.getAuthorityPrivilegeSaveReq(modalNodeInfos, userInfo, resourceType);
    }

    private AuthorityPrivilegeSaveReq getGroupPrivilegeSaveReq(String formSchemeKey, String groupKey, FinalaccountQueryAuthResourceType resourceType) {
        SingleQueryAuthShareCacheUserInfo userInfo = new SingleQueryAuthShareCacheUserInfo();
        userInfo.setUserKey(NpContextHolder.getContext().getUserId());
        userInfo.setCanRead(true);
        userInfo.setCanEdit(true);
        userInfo.setCanAuthorize(true);
        return this.getGroupAuthorityPrivilegeSaveReq(formSchemeKey, groupKey, userInfo, resourceType);
    }

    private AuthorityPrivilegeSaveReq getGroupReadPrivilegeSaveReq(String formSchemeKey, String groupKey, FinalaccountQueryAuthResourceType resourceType) {
        SingleQueryAuthShareCacheUserInfo userInfo = new SingleQueryAuthShareCacheUserInfo();
        userInfo.setUserKey(NpContextHolder.getContext().getUserId());
        userInfo.setCanRead(true);
        userInfo.setCanEdit(false);
        userInfo.setCanAuthorize(false);
        return this.getGroupAuthorityPrivilegeSaveReq(formSchemeKey, groupKey, userInfo, resourceType);
    }

    private AuthShareParams getAuthShareParams(AuthShareUserParams params) {
        AuthShareParams result = new AuthShareParams();
        result.setTaskKey(params.getTaskKey());
        result.setFormSchemeKey(params.getFormSchemeKey());
        result.getGroupKeys().addAll(params.getGroupKeys());
        result.getModelIds().addAll(params.getModelIds());
        return result;
    }

    private List<String> getModalIds(AuthShareParams params) {
        List<String> modelIds = params.getModelIds();
        if (params.getModelIds() != null && params.getModelIds().size() > 0) {
            return modelIds;
        }
        if (StringUtils.hasText(params.getTaskKey() + params.getFormSchemeKey()) || params.getGroupKeys().size() > 0) {
            Set<Object> tmpModelIds = new HashSet();
            if (StringUtils.hasText(params.getTaskKey())) {
                tmpModelIds = this.queryModeleDao.getModalKeyByTaskKey(params.getTaskKey());
            } else if (StringUtils.hasText(params.getFormSchemeKey()) && params.getGroupKeys().size() == 0) {
                tmpModelIds = this.queryModeleDao.getModalKeyByFormSchemeKey(params.getFormSchemeKey());
            } else if (params.getGroupKeys().size() > 0) {
                tmpModelIds = this.queryModeleDao.getModalKeyByGroupKey(params.getFormSchemeKey(), params.getGroupKeys().get(0));
            }
            modelIds.clear();
            for (String string : tmpModelIds) {
                if (!this.finalaccountQueryAuthService.canAuthorizeModel(string) || params.getRead() && !this.finalaccountQueryAuthService.canReadModel(string) || params.getEdit() && !this.finalaccountQueryAuthService.canWriteModel(string)) continue;
                modelIds.add(string);
            }
        }
        return modelIds;
    }

    private List<AuthorityPrivilegeSaveReq> getuthorityPrivilegeSaveReqs(List<String> modelIds, AuthShareUserParams params, AsyncTaskMonitor monitor) {
        ArrayList<AuthorityPrivilegeSaveReq> result = new ArrayList<AuthorityPrivilegeSaveReq>();
        List<ModalNodeInfo> modalNodeInfos = this.queryModeleDao.getModalNodeInfosByKeys(modelIds);
        if (monitor != null) {
            monitor.progressAndMessage(0.1, "\u8bbe\u7f6e\u6743\u9650");
        }
        if (modalNodeInfos.isEmpty() && null != monitor) {
            monitor.progressAndMessage(0.4, "\u8bbe\u7f6e\u6743\u9650");
            return result;
        }
        List<SingleQueryAuthShareCacheUserInfo> userInfos = params.getUserInfos();
        if (userInfos.isEmpty()) {
            AuthShareParams authShareParams = new AuthShareParams();
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPageIndex(0);
            authShareParams.setPageInfo(pageInfo);
            try {
                ReturnObject returnObject = this.getAllUser(authShareParams);
                userInfos.addAll(returnObject.getObj());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        double step = 0.3 / (double)params.getUserInfos().size();
        int index = 1;
        HashSet addedGroupKey = new HashSet();
        for (SingleQueryAuthShareCacheUserInfo userInfo : userInfos) {
            AuthorityPrivilegeSaveReq authorityPrivilegeSaveReq = this.getAuthorityPrivilegeSaveReq(modalNodeInfos, userInfo, FinalaccountQueryAuthResourceType.FQ_MODEL_NODE);
            result.add(authorityPrivilegeSaveReq);
            if (monitor == null) continue;
            if (monitor.isCancel()) {
                result.clear();
                return result;
            }
            monitor.progressAndMessage(0.1 + (double)index * step, "\u8bbe\u7f6e\u6743\u9650");
            ++index;
        }
        return result;
    }

    private Map<String, Map<String, AuthorityConst.AuthorityState>> getAuthority(List<ModalNodeInfo> modalNodeInfos, SingleQueryAuthShareCacheUserInfo userInfo, FinalaccountQueryAuthResourceType resourceType) {
        HashMap<String, AuthorityConst.AuthorityState> authorityStateMap = new HashMap<String, AuthorityConst.AuthorityState>();
        if (userInfo.getCanRead()) {
            authorityStateMap.put("finalaccountquery_auth_resource_read", AuthorityConst.AuthorityState.ALLOW);
        } else {
            authorityStateMap.put("finalaccountquery_auth_resource_read", AuthorityConst.AuthorityState.NONE);
        }
        if (userInfo.getCanEdit()) {
            authorityStateMap.put("finalaccountquery_auth_resource_write", AuthorityConst.AuthorityState.ALLOW);
        } else {
            authorityStateMap.put("finalaccountquery_auth_resource_write", AuthorityConst.AuthorityState.NONE);
        }
        if (userInfo.getCanAuthorize()) {
            authorityStateMap.put("finalaccountquery_auth_resource_accredit", AuthorityConst.AuthorityState.ALLOW);
        } else {
            authorityStateMap.put("finalaccountquery_auth_resource_accredit", AuthorityConst.AuthorityState.NONE);
        }
        HashMap<String, Map<String, AuthorityConst.AuthorityState>> result = new HashMap<String, Map<String, AuthorityConst.AuthorityState>>();
        for (ModalNodeInfo modalNodeInfo : modalNodeInfos) {
            result.put(resourceType.toResourceId(modalNodeInfo.getModalId()), authorityStateMap);
        }
        return result;
    }

    private Map<String, Map<String, AuthorityConst.AuthorityState>> getAuthorityByGroup(String groupkey, String formkey, SingleQueryAuthShareCacheUserInfo userInfo, FinalaccountQueryAuthResourceType resourceType) {
        HashMap<String, AuthorityConst.AuthorityState> authorityStateMap = new HashMap<String, AuthorityConst.AuthorityState>();
        if (userInfo.getCanRead()) {
            authorityStateMap.put("finalaccountquery_auth_resource_read", AuthorityConst.AuthorityState.ALLOW);
        } else {
            authorityStateMap.put("finalaccountquery_auth_resource_read", AuthorityConst.AuthorityState.NONE);
        }
        if (userInfo.getCanEdit()) {
            authorityStateMap.put("finalaccountquery_auth_resource_write", AuthorityConst.AuthorityState.ALLOW);
        } else {
            authorityStateMap.put("finalaccountquery_auth_resource_write", AuthorityConst.AuthorityState.NONE);
        }
        if (userInfo.getCanAuthorize()) {
            authorityStateMap.put("finalaccountquery_auth_resource_accredit", AuthorityConst.AuthorityState.ALLOW);
        } else {
            authorityStateMap.put("finalaccountquery_auth_resource_accredit", AuthorityConst.AuthorityState.NONE);
        }
        HashMap<String, Map<String, AuthorityConst.AuthorityState>> result = new HashMap<String, Map<String, AuthorityConst.AuthorityState>>();
        result.put(resourceType.toResourceId(formkey + "#" + groupkey), authorityStateMap);
        return result;
    }

    private Map<String, String> getSaveReqResourceTitles(List<ModalNodeInfo> modalNodeInfos) {
        HashMap<String, String> result = new HashMap<String, String>();
        for (ModalNodeInfo nodeInfo : modalNodeInfos) {
            result.put(FinalaccountQueryAuthResourceType.FQ_MODEL_NODE.toResourceId(nodeInfo.getModalId()), nodeInfo.getTitle());
        }
        return result;
    }

    private AuthorityPrivilegeSaveReq getAuthorityPrivilegeSaveReq(List<ModalNodeInfo> modalNodeInfos, SingleQueryAuthShareCacheUserInfo userInfo, FinalaccountQueryAuthResourceType resourceType) {
        AuthorityPrivilegeSaveReq authorityPrivilegeSaveReq = new AuthorityPrivilegeSaveReq();
        GranteeInfo granteeInfo = new GranteeInfo();
        granteeInfo.setOwnerId(userInfo.getUserKey());
        authorityPrivilegeSaveReq.setGranteeInfo(granteeInfo);
        authorityPrivilegeSaveReq.setAuthority(this.getAuthority(modalNodeInfos, userInfo, resourceType));
        authorityPrivilegeSaveReq.setResCategoryId("finalaccountquery-auth-resource-category");
        authorityPrivilegeSaveReq.setResourceTitles(this.getSaveReqResourceTitles(modalNodeInfos));
        return authorityPrivilegeSaveReq;
    }

    private AuthorityPrivilegeSaveReq getGroupAuthorityPrivilegeSaveReq(String formSchemeKey, String groupKey, SingleQueryAuthShareCacheUserInfo userInfo, FinalaccountQueryAuthResourceType resourceType) {
        AuthorityPrivilegeSaveReq authorityPrivilegeSaveReq = new AuthorityPrivilegeSaveReq();
        GranteeInfo granteeInfo = new GranteeInfo();
        granteeInfo.setOwnerId(userInfo.getUserKey());
        authorityPrivilegeSaveReq.setGranteeInfo(granteeInfo);
        authorityPrivilegeSaveReq.setAuthority(this.getAuthorityByGroup(groupKey, formSchemeKey, userInfo, resourceType));
        authorityPrivilegeSaveReq.setResCategoryId("finalaccountquery-auth-resource-category");
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(FinalaccountQueryAuthResourceType.FQ_GROUP.toResourceId(formSchemeKey + "#" + groupKey), groupKey);
        authorityPrivilegeSaveReq.setResourceTitles(result);
        return authorityPrivilegeSaveReq;
    }

    private int getCurrUserType() {
        int userType = NpContextHolder.getContext().getUser().getType();
        return userType;
    }

    private ReturnObject getAllUser(AuthShareParams params) throws JQException {
        List<SingleQueryAuthShareCacheUserInfo> allUsers = this.getUserInfo(params);
        ReturnObject returnObject = new ReturnObject();
        returnObject.setObj(allUsers);
        returnObject.setRecordCount(this.getAllUserCount(params));
        return returnObject;
    }

    private int getAllUserCount(AuthShareParams params) throws JQException {
        UserQueryReq userQueryReq = new UserQueryReq();
        userQueryReq.setMaxResult(Integer.valueOf(params.getPageInfo().getPageSize()));
        userQueryReq.setPage(Integer.valueOf(params.getPageInfo().getPageIndex()));
        userQueryReq.setOrgType(this.getCategoryName(params));
        userQueryReq.setOrgCode("-");
        userQueryReq.setKeyword(params.getSearchText());
        long number = this.nvwaUserService.queryUserCount(userQueryReq, false);
        return (int)number;
    }

    private List<SingleQueryAuthShareCacheUserInfo> getUserInfo(AuthShareParams params) throws JQException {
        List<String> modelIds = this.getModalIds(params);
        ArrayList<SingleQueryAuthShareCacheUserInfo> result = new ArrayList<SingleQueryAuthShareCacheUserInfo>();
        List<UserDTORes> users = this.getFilterUsers(params);
        ArrayList<String> userKeys = new ArrayList<String>();
        for (UserDTORes res : users) {
            userKeys.add(res.getId());
            SingleQueryAuthShareCacheUserInfo userInfo = new SingleQueryAuthShareCacheUserInfo(res.getId(), res.getNickname(), res.getName(), res.getId());
            result.add(userInfo);
        }
        return result;
    }

    private List<UserDTORes> getFilterUsers(AuthShareParams params) throws JQException {
        UserQueryReq userQueryReq = new UserQueryReq();
        userQueryReq.setMaxResult(Integer.valueOf(params.getPageInfo().getPageSize()));
        userQueryReq.setPage(Integer.valueOf(params.getPageInfo().getPageIndex()));
        userQueryReq.setOrgType(this.getCategoryName(params));
        userQueryReq.setOrgCode("-");
        userQueryReq.setKeyword(params.getSearchText());
        return this.nvwaUserService.queryUser(userQueryReq, false);
    }

    private List<SingleQueryAuthShareCacheUserInfo> getUserByCode(AuthShareParams params) throws JQException {
        ArrayList<SingleQueryAuthShareCacheUserInfo> result = new ArrayList<SingleQueryAuthShareCacheUserInfo>();
        ContextUser user = NpContextHolder.getContext().getUser();
        String categoryName = this.getCategoryName(params);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setAuthType(OrgDataOption.AuthType.MANAGE);
        if (!StringUtils.hasText(categoryName)) {
            logger.error(String.format("%s\u83b7\u53d6\u7ec4\u7ec7\u7ed3\u6784\u7c7b\u578b\u5931\u8d25", params.getFormSchemeKey()));
            return result;
        }
        orgDTO.setCategoryname(categoryName);
        orgDTO.setCode(user.getOrgCode());
        orgDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
        Set refCodeList = this.orgDataCacheService.getRefCodeList(orgDTO);
        if (refCodeList != null && !refCodeList.isEmpty()) {
            UserQueryReq queryReq = new UserQueryReq();
            ArrayList orgCodes = new ArrayList();
            orgCodes.addAll(refCodeList);
            queryReq.setOrgCodes(orgCodes);
            queryReq.setOrgCodeChildren(2);
            queryReq.setOrgType(categoryName);
            if (params.getPageInfo() != null && params.getPageInfo().getPageIndex() > 0) {
                queryReq.setPage(Integer.valueOf(params.getPageInfo().getPageIndex()));
                queryReq.setMaxResult(Integer.valueOf(params.getPageInfo().getPageSize()));
            }
            if (StringUtils.hasText(params.getSearchText())) {
                queryReq.setKeyword(params.getSearchText());
            }
            ArrayList<String> showAttr = new ArrayList<String>();
            showAttr.add("securityLevel");
            queryReq.setShowAttr(showAttr);
            List resList = this.nvwaUserService.queryUser(queryReq, false);
            for (UserDTORes userDTORes : resList) {
                SingleQueryAuthShareCacheUserInfo info = new SingleQueryAuthShareCacheUserInfo();
                info.setUserKey(userDTORes.getId());
                info.setUserCode(userDTORes.getName());
                info.setUserName(StringUtils.hasText(userDTORes.getFullname()) ? userDTORes.getFullname() : userDTORes.getNickname());
                info.setIdentityid(userDTORes.getId());
                result.add(info);
            }
        }
        return result;
    }

    private Integer getUserCountByCode(AuthShareParams params) throws JQException {
        OrgDTO orgDTO = new OrgDTO();
        String categoryName = this.getCategoryName(params);
        orgDTO.setAuthType(OrgDataOption.AuthType.MANAGE);
        orgDTO.setCategoryname(categoryName);
        ContextUser user = NpContextHolder.getContext().getUser();
        orgDTO.setCode(user.getOrgCode());
        orgDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
        Set refCodeList = this.orgDataCacheService.getRefCodeList(orgDTO);
        if (!refCodeList.isEmpty()) {
            UserQueryReq queryReq = new UserQueryReq();
            ArrayList orgCodes = new ArrayList();
            orgCodes.addAll(refCodeList);
            queryReq.setOrgCodes(orgCodes);
            queryReq.setOrgCodeChildren(2);
            queryReq.setOrgType(categoryName);
            if (StringUtils.hasText(params.getSearchText())) {
                queryReq.setKeyword(params.getSearchText());
            }
            ArrayList<String> showAttr = new ArrayList<String>();
            showAttr.add("securityLevel");
            queryReq.setShowAttr(showAttr);
            long number = this.nvwaUserService.queryUserCount(queryReq, false);
            return (int)number;
        }
        return 0;
    }

    private ReturnObject getUserInMulRole(AuthShareParams params) throws Exception {
        return this.getUserInBusinessManager(params);
    }

    private ReturnObject getUserInBusinessManager(AuthShareParams params) throws JQException {
        List<SingleQueryAuthShareCacheUserInfo> allUsers = this.getUserInfo(params);
        ReturnObject returnObject = new ReturnObject();
        returnObject.setObj(allUsers);
        returnObject.setRecordCount(this.getAllUserCount(params));
        return returnObject;
    }

    private ReturnObject getUserInCurUserCode(AuthShareParams params) throws Exception {
        List<SingleQueryAuthShareCacheUserInfo> allUsers = this.getUserByCode(params);
        ReturnObject returnObject = new ReturnObject();
        returnObject.setObj(allUsers);
        returnObject.setRecordCount(this.getUserCountByCode(params));
        return returnObject;
    }

    private IEntityTable getEntityTable(String formSchemeKey, String period) throws Exception {
        IEntityQuery query = this.entityQueryHelper.getEntityQuery(formSchemeKey, period);
        return this.entityQueryHelper.buildEntityTable(query, formSchemeKey, false);
    }

    private int getUserCount(UserQueryReq userQueryReq) {
        try {
            long number = this.nvwaUserService.queryUserCount(userQueryReq, false);
            int i = (int)number;
            return i;
        }
        catch (JQException e) {
            return 0;
        }
    }

    private String getCategoryName(AuthShareParams params) {
        FormSchemeDefine define = this.iRunTimeViewController.getFormScheme(params.getFormSchemeKey());
        if (define != null) {
            return define.getDw().replace("@ORG", "");
        }
        return null;
    }
}

