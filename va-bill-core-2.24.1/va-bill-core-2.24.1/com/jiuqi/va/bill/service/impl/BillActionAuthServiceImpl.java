/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.biz.view.impl.ViewDefineImpl
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.role.RoleDO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.AuthRoleClient
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.dao.BillActionAuthDAO;
import com.jiuqi.va.bill.domain.BillActionAuthDO;
import com.jiuqi.va.bill.domain.BillActionAuthDTO;
import com.jiuqi.va.bill.domain.BillActionAuthFindDTO;
import com.jiuqi.va.bill.domain.BillActionAuthVO;
import com.jiuqi.va.bill.domain.option.BillActionAuthUpdateDO;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.service.BillActionAuthService;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.view.impl.ViewDefineImpl;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.role.RoleDO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.AuthRoleClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
public class BillActionAuthServiceImpl
implements BillActionAuthService {
    @Autowired
    private ActionManager actionManager;
    @Autowired
    private ModelManager modelManager;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private BillActionAuthDAO BillActionAuthDAO;
    @Autowired
    private AuthRoleClient authRoleClient;

    @Override
    public List<BillActionAuthVO> listDetail(BillActionAuthDTO param) {
        ArrayList<BillActionAuthVO> actionAuthVOS = new ArrayList<BillActionAuthVO>();
        String defineName = param.getDefinename();
        BillDefine define = (BillDefine)this.modelDefineService.getDefine(defineName);
        ViewDefineImpl viewDefine = (ViewDefineImpl)define.getPlugins().find(ViewDefineImpl.class);
        List schemes = viewDefine.getSchemes();
        if (CollectionUtils.isEmpty(schemes)) {
            return actionAuthVOS;
        }
        ModelType modelType = (ModelType)this.modelManager.find(define.getModelType());
        List actionList = this.actionManager.getActionList(modelType.getModelClass());
        Map<String, String> actionTitle = actionList.stream().collect(Collectors.toMap(Action::getName, Action::getTitle));
        HashMap<String, BillActionAuthVO> processedActions = new HashMap<String, BillActionAuthVO>();
        BillActionAuthDO selectParam = new BillActionAuthDO();
        selectParam.setDefinename(param.getDefinename());
        selectParam.setAuthtype(param.getAuthtype());
        selectParam.setBizname(param.getBizname());
        selectParam.setBiztype(param.getBiztype());
        HashMap<String, Integer> authFlagMap = new HashMap<String, Integer>();
        List dbActions = this.BillActionAuthDAO.select((Object)selectParam);
        for (BillActionAuthDO dbAction : dbActions) {
            authFlagMap.put(dbAction.getActname(), dbAction.getAuthflag());
        }
        for (Map scheme : schemes) {
            Map props = (Map)scheme.get("template");
            this.collectActions(props, actionAuthVOS, processedActions, actionTitle, authFlagMap, param.getBiztype(), param.getBizname());
        }
        return actionAuthVOS;
    }

    private void collectActions(Map<String, Object> props, List<BillActionAuthVO> actions, Map<String, BillActionAuthVO> processedActions, Map<String, String> actionTitle, Map<String, Integer> authflagMap, Integer bizType, String bizName) {
        Map action = (Map)props.get("action");
        List childrens = (List)props.get("children");
        if (childrens != null) {
            for (Map children : childrens) {
                this.collectActions(children, actions, processedActions, actionTitle, authflagMap, bizType, bizName);
            }
        }
        if (action == null) {
            return;
        }
        Object authControl = props.get("authControl");
        if (!(authControl != null && ((Boolean)authControl).booleanValue() || (authControl = props.get("startControl")) != null && ((Boolean)authControl).booleanValue())) {
            return;
        }
        Object type = action.get("type");
        if (ObjectUtils.isEmpty(type) && childrens != null) {
            for (Map children : childrens) {
                this.collectActions(children, actions, processedActions, actionTitle, authflagMap, bizType, bizName);
            }
        }
        Object authCode = props.get("authCode");
        BillActionAuthVO processedAction = processedActions.get(type);
        String actTitle = actionTitle.get(type);
        if (!ObjectUtils.isEmpty(authCode)) {
            String childActName = this.buildSubActName((String)type, (String)authCode);
            if (processedAction == null) {
                BillActionAuthVO billActionAuth = new BillActionAuthVO();
                billActionAuth.setActname((String)type);
                billActionAuth.setActTitle(actTitle);
                billActionAuth.setBiztype(bizType);
                billActionAuth.setBizname(bizName);
                billActionAuth.setAuthflag(authflagMap.get(type));
                processedActions.put(billActionAuth.getActname(), billActionAuth);
                BillActionAuthVO child = new BillActionAuthVO();
                child.setActname(childActName);
                child.setActTitle(this.buildSubActTitle(actTitle, (String)authCode));
                child.setBiztype(bizType);
                child.setBizname(bizName);
                child.setAuthflag(authflagMap.get(childActName));
                billActionAuth.addChildren(child);
                actions.add(billActionAuth);
            } else {
                BillActionAuthVO child = new BillActionAuthVO();
                child.setActname(childActName);
                child.setActTitle(this.buildSubActTitle(actTitle, (String)authCode));
                child.setBiztype(bizType);
                child.setBizname(bizName);
                child.setAuthflag(authflagMap.get(childActName));
                processedAction.addChildren(child);
            }
        } else if (processedAction == null) {
            BillActionAuthVO billActionAuth = new BillActionAuthVO();
            billActionAuth.setActname((String)type);
            billActionAuth.setActTitle(actTitle);
            billActionAuth.setBiztype(bizType);
            billActionAuth.setBizname(bizName);
            billActionAuth.setAuthflag(authflagMap.get(type));
            processedActions.put(billActionAuth.getActname(), billActionAuth);
            actions.add(billActionAuth);
        }
    }

    private String buildSubActName(String actonName, String authCode) {
        StringBuilder sb = new StringBuilder();
        sb.append(actonName);
        sb.append("||");
        sb.append(authCode);
        return sb.toString();
    }

    private String buildSubActTitle(String actonTitle, String authCode) {
        StringBuilder sb = new StringBuilder();
        sb.append(actonTitle);
        sb.append("\uff08");
        sb.append(authCode);
        sb.append("\uff09");
        return sb.toString();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public R updateDetail(BillActionAuthUpdateDO param) {
        BillActionAuthDO deleteParam = new BillActionAuthDO();
        deleteParam.setDefinename(param.getDefinename());
        deleteParam.setAuthtype(param.getAuthtype());
        deleteParam.setBizname(param.getBizname());
        deleteParam.setBiztype(param.getBiztype());
        this.BillActionAuthDAO.delete((Object)deleteParam);
        for (BillActionAuthDO actAuth : param.getData()) {
            if (actAuth.getAuthflag() == 0) continue;
            actAuth.setDefinename(param.getDefinename());
            actAuth.setAuthtype(param.getAuthtype());
            actAuth.setBizname(param.getBizname());
            actAuth.setBiztype(param.getBiztype());
            actAuth.setId(UUID.randomUUID());
            this.BillActionAuthDAO.insert((Object)actAuth);
        }
        return R.ok();
    }

    @Override
    public Set<String> getUserAuth(BillActionAuthFindDTO param) {
        HashSet<String> endSets = new HashSet<String>();
        UserLoginDTO user = ShiroUtil.getUser();
        String[] actNames = param.getActNames();
        Object isAdmin = user.getExtInfo("isAdmin");
        if (isAdmin != null && ((Boolean)isAdmin).booleanValue()) {
            Collections.addAll(endSets, actNames);
            return endSets;
        }
        List roles = this.authRoleClient.listByUser((UserDO)user);
        BillActionAuthDTO actionAuthDTO = new BillActionAuthDTO();
        actionAuthDTO.setDefinename(param.getDefineName());
        StringBuilder sb = new StringBuilder();
        sb.append(" 1=1 ");
        sb.append(" and ( (biztype=0 and bizname in ('-'");
        if (roles != null && roles.size() > 0) {
            for (RoleDO role : roles) {
                sb.append(",'").append(role.getName()).append("'");
            }
        }
        sb.append(")) or (biztype=1 and bizname='").append(user.getUsername()).append("') ) ");
        if (actNames.length == 1) {
            actionAuthDTO.setActname(actNames[0]);
        } else {
            sb.append(" and actname in ('").append(actNames[0]).append("'");
            for (int i = 1; i < actNames.length; ++i) {
                sb.append(",'").append(actNames[i]).append("'");
            }
            sb.append(")");
        }
        actionAuthDTO.setSqlCondition(sb.toString());
        List<BillActionAuthDO> billActionAuthDOS = this.BillActionAuthDAO.listAuthority(actionAuthDTO);
        if (billActionAuthDOS == null || billActionAuthDOS.isEmpty()) {
            return endSets;
        }
        HashSet<String> refuseSets = new HashSet<String>();
        for (BillActionAuthDO item : billActionAuthDOS) {
            if (item.getAuthflag() == 1) {
                endSets.add(item.getActname());
                continue;
            }
            if (item.getAuthflag() != 2) continue;
            refuseSets.add(item.getActname());
        }
        if (!refuseSets.isEmpty()) {
            endSets.removeAll(refuseSets);
        }
        return endSets;
    }
}

