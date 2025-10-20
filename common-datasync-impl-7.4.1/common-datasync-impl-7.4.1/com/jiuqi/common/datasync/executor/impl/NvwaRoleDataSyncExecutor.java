/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.authz2.Identifiable
 *  com.jiuqi.np.authz2.Namable
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.vo.RoleV
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nvwa.glue.client.user.MergeDataUtil
 *  com.jiuqi.nvwa.glue.data.GlueRole
 *  com.jiuqi.nvwa.glue.data.impl.GlueRoleDTO
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.common.datasync.executor.impl;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.datasync.converter.NvwaRoleConverter;
import com.jiuqi.common.datasync.dto.CommonDataSyncSettingItemDTO;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutor;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutorContext;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaRoleDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaRoleUserRelationDTO;
import com.jiuqi.common.datasync.service.CommonDataSyncService;
import com.jiuqi.np.authz2.Identifiable;
import com.jiuqi.np.authz2.Namable;
import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.vo.RoleV;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nvwa.glue.client.user.MergeDataUtil;
import com.jiuqi.nvwa.glue.data.GlueRole;
import com.jiuqi.nvwa.glue.data.impl.GlueRoleDTO;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
public class NvwaRoleDataSyncExecutor
implements CommonDataSyncExecutor {
    private final Logger LOGGER = LoggerFactory.getLogger(NvwaRoleDataSyncExecutor.class);
    @Autowired
    private CommonDataSyncService dataSyncService;
    @Autowired
    private NvwaUserClient nvwaUserClient;
    @Autowired
    private RoleService roleService;

    @Override
    public String title() {
        return "\u5973\u5a32\u5e73\u53f0\u89d2\u8272\u6570\u636e\u540c\u6b65";
    }

    @Override
    public String type() {
        return "NvwaRole";
    }

    @Override
    public String description() {
        return "<p>\u63a5\u53e3\u53c2\u6570\u793a\u4f8b\u63cf\u8ff0: \u4e00\u672c\u8d26\u670d\u52a1(\u8c03\u7528\u65b9\u670d\u52a1\u540d\uff1adatacenter-servcie)\u62bd\u53d6\u5408\u5e76\u62a5\u8868\u670d\u52a1\uff08\u88ab\u8c03\u7528\u65b9\u670d\u52a1url\uff1ahttp://10.2.33.35:8188\uff09\u7684\u89d2\u8272\u6570\u636e</p><p>\u8c03\u7528\u65b9\u670d\u52a1\u540d\uff1adatacenter-service</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u5730\u5740\uff1ahttp://10.2.33.35:8188</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u8ba4\u8bc1\u7528\u6237\uff1aadmin</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u8ba4\u8bc1\u5bc6\u7801\uff1agcP@ssw0rd</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u53c2\u6570\uff1a<p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u53c2\u6570\u63cf\u8ff0\uff1a\u672c\u793a\u4f8b\u4e3a\u4e00\u672c\u8d26\u670d\u52a1\u62bd\u53d6\u5408\u5e76\u670d\u52a1\u7684\u89d2\u8272\u6570\u636e\u3002</p>";
    }

    @Override
    public void execute(CommonDataSyncExecutorContext context) {
        CommonDataSyncSettingItemDTO itemDTO = context.getDataSyncSettingItemDTO();
        this.dataSync(itemDTO);
    }

    @Transactional(rollbackFor={Exception.class})
    public void dataSync(CommonDataSyncSettingItemDTO itemDTO) {
        this.dataSync(this.fetchDataSyncRoleDatas(itemDTO), this.fetchDataSyncRoleUserRelationDatas(itemDTO));
    }

    @Transactional(rollbackFor={Exception.class})
    public void dataSync(List<DataSyncNvwaRoleDTO> roles, List<DataSyncNvwaRoleUserRelationDTO> daRoleUserRelations) {
        this.saveDataSyncRoleDatas(roles);
        this.saveDataSyncRoleUserRelationDatas(daRoleUserRelations);
    }

    public List<DataSyncNvwaRoleUserRelationDTO> fetchDataSyncRoleUserRelationDatas(CommonDataSyncSettingItemDTO itemDTO) {
        if (ObjectUtils.isEmpty(itemDTO.getUrl())) {
            return null;
        }
        List roleUserRelations = Collections.emptyList();
        try {
            NvwaLoginUserDTO userDTO = this.dataSyncService.initNvwaFeignClientTokenEnv(itemDTO.getUrl(), itemDTO.getUsername(), itemDTO.getPassword());
            BusinessResponseEntity<List<DataSyncNvwaRoleUserRelationDTO>> roleUserRelationsResponseEntity = this.dataSyncService.getNvwaFeignClient().getNvwaRoleUserRelationDTOs(new URI(itemDTO.getUrl()), userDTO);
            if (roleUserRelationsResponseEntity.getData() != null) {
                roleUserRelations = (List)roleUserRelationsResponseEntity.getData();
            }
        }
        catch (Exception e) {
            this.LOGGER.error(e.getMessage(), e);
        }
        this.LOGGER.info("\u62bd\u53d6\u5230" + roleUserRelations.size() + "\u4e2a\u89d2\u8272\u5173\u8054\u6570\u636e");
        return roleUserRelations;
    }

    public List<DataSyncNvwaRoleDTO> fetchDataSyncRoleDatas(CommonDataSyncSettingItemDTO itemDTO) {
        if (ObjectUtils.isEmpty(itemDTO.getUrl())) {
            return null;
        }
        List nvwaRoleDTOS = Collections.emptyList();
        try {
            NvwaLoginUserDTO userDTO = this.dataSyncService.initNvwaFeignClientTokenEnv(itemDTO.getUrl(), itemDTO.getUsername(), itemDTO.getPassword());
            BusinessResponseEntity<List<DataSyncNvwaRoleDTO>> nvwaRoleDTOsResponseEntity = this.dataSyncService.getNvwaFeignClient().getNvwaRoleDTOs(new URI(itemDTO.getUrl()), userDTO);
            if (nvwaRoleDTOsResponseEntity.getData() != null) {
                nvwaRoleDTOS = (List)nvwaRoleDTOsResponseEntity.getData();
            }
        }
        catch (Exception e) {
            this.LOGGER.error(e.getMessage(), e);
        }
        this.LOGGER.info("\u62bd\u53d6\u5230" + nvwaRoleDTOS.size() + "\u4e2a\u89d2\u8272\u6570\u636e");
        return nvwaRoleDTOS;
    }

    public void saveDataSyncRoleDatas(List<DataSyncNvwaRoleDTO> roles) {
        if (CollectionUtils.isEmpty(roles)) {
            return;
        }
        this.LOGGER.info("\u589e\u91cf\u6a21\u5f0f\u88c5\u5165\u89d2\u8272\u6570\u636e");
        AtomicInteger addCount = new AtomicInteger(0);
        AtomicInteger currentIndex = new AtomicInteger(0);
        NvwaRoleDataSyncExecutor syncExecutor = (NvwaRoleDataSyncExecutor)SpringContextUtils.getBean(NvwaRoleDataSyncExecutor.class);
        for (DataSyncNvwaRoleDTO roleDTO : roles) {
            try {
                currentIndex.addAndGet(1);
                syncExecutor.insertOrUpdateRole(roleDTO, currentIndex, addCount);
            }
            catch (Exception e) {
                this.LOGGER.error(e.getMessage(), e);
            }
        }
        this.LOGGER.info("\u65b0\u589e" + addCount.get() + "\u4e2a\u89d2\u8272,\u66f4\u65b0" + (roles.size() - addCount.get()) + "\u4e2a\u89d2\u8272");
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void insertOrUpdateRole(DataSyncNvwaRoleDTO nvwaRoleDTO, AtomicInteger currentIndex, AtomicInteger addCount) {
        this.LOGGER.info("\u62bd\u53d6\u7b2c" + currentIndex + "\u4e2a\u89d2\u8272\u505a\u66f4\u65b0,code:" + nvwaRoleDTO.getName());
        GlueRoleDTO glueRole = NvwaRoleConverter.convertGlueDTO(nvwaRoleDTO);
        Optional oldRole = this.roleService.findByName(glueRole.getName());
        if (oldRole.isPresent()) {
            RoleV roleV = MergeDataUtil.mergeRole((GlueRole)glueRole, (Role)((Role)oldRole.get()));
            roleV.setId(((Role)oldRole.get()).getId());
            this.roleService.modify(roleV);
        } else {
            if ("ffffffff-ffff-ffff-bbbb-ffffffffffff".equals(glueRole.getId()) || "ffffffff-ffff-ffff-aaaa-ffffffffffff".equals(glueRole.getId())) {
                return;
            }
            RoleV roleV = MergeDataUtil.mergeRole((GlueRole)glueRole, null);
            this.roleService.create(roleV);
            addCount.addAndGet(1);
        }
    }

    public void saveDataSyncRoleUserRelationDatas(List<DataSyncNvwaRoleUserRelationDTO> roleUserRelations) {
        if (CollectionUtils.isEmpty(roleUserRelations)) {
            return;
        }
        this.LOGGER.info("\u589e\u91cf\u6a21\u5f0f\u88c5\u5165\u89d2\u8272\u5173\u8054\u7528\u6237\u6570\u636e");
        boolean add = false;
        NvwaRoleDataSyncExecutor dataSyncExecutor = (NvwaRoleDataSyncExecutor)SpringContextUtils.getBean(NvwaRoleDataSyncExecutor.class);
        Map<String, String> userName2IdMap = this.nvwaUserClient.getUsers().stream().collect(Collectors.toMap(User::getName, User::getId));
        Map<String, String> roleName2IdMap = this.roleService.getAllRoles().stream().collect(Collectors.toMap(Namable::getName, Identifiable::getId));
        HashMap<String, Set> userId2RoleIdsMap = new HashMap<String, Set>();
        roleUserRelations.stream().forEach(roleUserRelation -> {
            String roleName = roleUserRelation.getRoleName();
            String roleId = (String)roleName2IdMap.get(roleName);
            if (ObjectUtils.isEmpty(roleId)) {
                this.LOGGER.warn("\u5f53\u524d\u7cfb\u7edf\u672a\u627e\u5230\u89d2\u8272Name\u4e3a\u2019".concat(roleName).concat("\u2018\u7684\u89d2\u8272\uff0c \u8df3\u8fc7\u8be5\u6761\u89d2\u8272\u5173\u8054\u7528\u6237\u4fe1\u606f\u540c\u6b65\u3002"));
                return;
            }
            List<String> userNames = roleUserRelation.getUserNames();
            if (CollectionUtils.isEmpty(userNames)) {
                return;
            }
            userNames.stream().forEach(userName -> {
                String userId = (String)userName2IdMap.get(userName);
                if (ObjectUtils.isEmpty(userId)) {
                    return;
                }
                Set roleIds = (Set)userId2RoleIdsMap.get(userId);
                if (roleIds == null) {
                    userId2RoleIdsMap.put(userId, new HashSet());
                }
                ((Set)userId2RoleIdsMap.get(userId)).add(roleId);
            });
        });
        userId2RoleIdsMap.forEach((userId, recieveRoleIds) -> {
            Set exsitsRoleIds = this.roleService.getIdByIdentity(userId);
            Set addRoleIds = recieveRoleIds.stream().filter(i -> !exsitsRoleIds.contains(i)).collect(Collectors.toSet());
            Set deleteRoleIds = exsitsRoleIds.stream().filter(i -> !recieveRoleIds.contains(i)).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(addRoleIds)) {
                addRoleIds.stream().forEach(addRoleId -> {
                    try {
                        dataSyncExecutor.insertOrUpdateReleation((String)addRoleId, (String)userId);
                    }
                    catch (Exception e) {
                        this.LOGGER.error(e.getMessage(), e);
                    }
                });
                this.LOGGER.info("\u7528\u6237ID\uff1a" + userId + "\uff0c\u65b0\u589e" + addRoleIds.size() + "\u4e2a\u89d2\u8272\u5173\u8054");
            }
            if (!CollectionUtils.isEmpty(deleteRoleIds)) {
                deleteRoleIds.stream().forEach(deleteRoleId -> {
                    try {
                        dataSyncExecutor.delete((String)deleteRoleId, (String)userId);
                    }
                    catch (Exception e) {
                        this.LOGGER.error(e.getMessage(), e);
                    }
                });
                this.LOGGER.info("\u7528\u6237ID\uff1a" + userId + "\uff0c\u5220\u9664" + deleteRoleIds.size() + "\u4e2a\u89d2\u8272\u5173\u8054");
            }
        });
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void insertOrUpdateReleation(String roleId, String userId) {
        this.roleService.grant(roleId, userId);
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void delete(String roleId, String userId) {
        this.roleService.revoke(roleId, userId);
    }
}

