/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Identifiable
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.resource.authority.RoleAuthority
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  javax.validation.constraints.NotEmpty
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.authz.service.impl;

import com.jiuqi.np.authz2.Identifiable;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.authz.IUserEntity;
import com.jiuqi.nr.authz.UserQueryParam;
import com.jiuqi.nr.authz.bean.UserQueryParamExtend;
import com.jiuqi.nr.authz.bean.UserTreeNode;
import com.jiuqi.nr.authz.dao.IUserQueryDao;
import com.jiuqi.nr.authz.service.IEntityService;
import com.jiuqi.nr.authz.service.IUserTreeService;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.resource.authority.RoleAuthority;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UserTreeServiceImpl
implements IUserTreeService {
    public static final int SELF = 0;
    public static final int DIRECTSUB = 1;
    public static final int ALLSUB = 2;
    @Qualifier(value="com.jiuqi.nr.authz.common.dao.UserEntityDao")
    @Autowired
    private IUserQueryDao<IUserEntity> iUserQueryDao;
    @Autowired
    private SystemIdentityService identityService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleAuthority roleAuthorityService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private IEntityService entityService;

    @Override
    public List<ITree<UserTreeNode>> getUsersByIds(@NotNull @NotEmpty List<String> userIds) {
        return this.iUserQueryDao.queryUserByIds(userIds.toArray(new String[0])).stream().filter(Objects::nonNull).map(UserTreeNode::new).map(this::buildTreeNode).collect(Collectors.toList());
    }

    @Override
    public List<ITree<UserTreeNode>> getUserByUserQueryParam(com.jiuqi.nr.authz.bean.UserQueryParam userQueryParam) throws JQException {
        this.buildQueryParam(userQueryParam);
        List<String> roleIds = userQueryParam.getRoleIds();
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        boolean entity = userQueryParam.isEntity();
        List<IUserEntity> userEntities = this.iUserQueryDao.queryUser(this.getUserQueryParam(userQueryParam));
        return userEntities.stream().filter(Objects::nonNull).map(UserTreeNode::new).peek(this.buildEntityConsumer(entity)).map(this::buildTreeNode).collect(Collectors.toList());
    }

    private Consumer<UserTreeNode> buildEntityConsumer(boolean entity) {
        return r -> {
            String orgCode;
            if (entity && (orgCode = r.getOrgCode()) != null) {
                OrgDTO orgDTO = new OrgDTO();
                orgDTO.setCategoryname("MD_ORG");
                orgDTO.setOrgcode(orgCode);
                PageVO list = this.orgDataClient.list(orgDTO);
                List rows = list.getRows();
                if (!CollectionUtils.isEmpty(rows)) {
                    r.setOrgName(((OrgDO)rows.get(0)).getName());
                }
            }
        };
    }

    private ITree<UserTreeNode> buildTreeNode(UserTreeNode r) {
        ITree tree = new ITree((INode)r);
        tree.setLeaf(true);
        return tree;
    }

    private UserQueryParam getUserQueryParam(com.jiuqi.nr.authz.bean.UserQueryParam userQueryParam) {
        UserQueryParam queryParam = new UserQueryParam();
        queryParam.setKeyword(userQueryParam.getKeyword());
        queryParam.setRoleIds(userQueryParam.getRoleIds());
        queryParam.setPage(userQueryParam.getPage());
        queryParam.setMaxResult(userQueryParam.getPageCount());
        queryParam.setEnabled(true);
        List<String> units = userQueryParam.getUnits();
        if (CollectionUtils.isEmpty(units)) {
            String entityDataKey = userQueryParam.getEntityDataKey();
            if (entityDataKey != null) {
                queryParam.setEntityKey(Collections.singletonList(entityDataKey));
            }
        } else {
            queryParam.setEntityKey(units);
        }
        return queryParam;
    }

    @Override
    public List<UserTreeNode> getUserListByUserQueryParam(com.jiuqi.nr.authz.bean.UserQueryParam userQueryParam) throws JQException {
        this.buildQueryParam(userQueryParam);
        List<String> roleIds = userQueryParam.getRoleIds();
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        boolean entity = userQueryParam.isEntity();
        return this.iUserQueryDao.queryUser(this.getUserQueryParam(userQueryParam)).stream().filter(Objects::nonNull).map(UserTreeNode::new).peek(this.buildEntityConsumer(entity)).collect(Collectors.toList());
    }

    @Override
    public long getUserCountByUserQueryParam(com.jiuqi.nr.authz.bean.UserQueryParam userQueryParam) {
        this.buildQueryParam(userQueryParam);
        List<String> roleIds = userQueryParam.getRoleIds();
        if (roleIds == null || roleIds.isEmpty()) {
            return 0L;
        }
        return this.iUserQueryDao.queryUserCount(this.getUserQueryParam(userQueryParam));
    }

    private void buildQueryParam(com.jiuqi.nr.authz.bean.UserQueryParam queryParam) {
        if (CollectionUtils.isEmpty(queryParam.getRoleIds())) {
            ArrayList roleIds = new ArrayList();
            if (this.identityService.isSystemIdentity(NpContextHolder.getContext().getIdentityId())) {
                roleIds.add("ffffffff-ffff-ffff-bbbb-ffffffffffff");
            } else {
                roleIds = this.roleService.getAllRoles().stream().filter(role -> this.roleAuthorityService.canReadRole(role)).map(Identifiable::getId).collect(Collectors.toCollection(ArrayList::new));
            }
            queryParam.setRoleIds(roleIds);
        }
        if (queryParam.getPage() == null) {
            queryParam.setPage(0);
        }
    }

    @Override
    public List<ITree<UserTreeNode>> getUsersByUserQueryParamExtend(UserQueryParamExtend userQueryParam) throws JQException {
        String entityDataKey = userQueryParam.getEntityDataKey();
        int unitType = userQueryParam.getUnitType();
        ArrayList<String> entityDataKeys = new ArrayList<String>();
        if (0 == unitType) {
            entityDataKeys.add(entityDataKey);
        } else if (1 == unitType) {
            List<String> directChildrens = this.entityService.getDirectChildrens(userQueryParam.getTaskKey(), userQueryParam.getFormSchemeKey(), entityDataKey, userQueryParam.getPeriod());
            entityDataKeys.addAll(directChildrens);
            entityDataKeys.add(entityDataKey);
        } else if (2 == unitType) {
            List<String> allChildrens = this.entityService.getAllChildrens(userQueryParam.getTaskKey(), userQueryParam.getFormSchemeKey(), entityDataKey, userQueryParam.getPeriod());
            entityDataKeys.addAll(allChildrens);
            entityDataKeys.add(entityDataKey);
        }
        com.jiuqi.nr.authz.bean.UserQueryParam queryParam = new com.jiuqi.nr.authz.bean.UserQueryParam();
        queryParam.setPage(0);
        queryParam.setUnits(entityDataKeys);
        queryParam.setRoleIds(userQueryParam.getRoleIds());
        return this.getUserByUserQueryParam(queryParam);
    }
}

