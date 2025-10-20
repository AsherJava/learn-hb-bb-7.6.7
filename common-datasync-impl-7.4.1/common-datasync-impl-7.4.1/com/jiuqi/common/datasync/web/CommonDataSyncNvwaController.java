/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.np.authz2.Identifiable
 *  com.jiuqi.np.authz2.Namable
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.attr.UserExtendedAttribute
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.common.datasync.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.datasync.converter.NvwaBaseDataConverter;
import com.jiuqi.common.datasync.converter.NvwaOrganizationConverter;
import com.jiuqi.common.datasync.converter.NvwaRoleConverter;
import com.jiuqi.common.datasync.converter.NvwaUserConverter;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaAttributeDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaBaseDataDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaEntityIdentityDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaOrganizationDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaRoleDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaRoleUserRelationDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaUserDTO;
import com.jiuqi.np.authz2.Identifiable;
import com.jiuqi.np.authz2.Namable;
import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.attr.UserExtendedAttribute;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonDataSyncNvwaController {
    public static final String ROOT_PATH = "api/v1/commondatasync";
    public static final String URL_getNvwaRoleDTOs = "api/v1/commondatasync/role";
    public static final String URL_getNvwaRoleUserRelationDTOs = "api/v1/commondatasync/role-user-relation";
    public static final String URL_getNvwaUserDTOs = "api/v1/commondatasync/user";
    public static final String URL_getNvwaOrgnizationDTOs = "api/v1/commondatasync/orgnization";
    public static final String URL_getNvwaBaseDataDTOs = "api/v1/commondatasync/basedata";
    public static final String URL_getNvwaEntityIdentityDTOs = "api/v1/commondatasync/nvwa-entity-identitys";
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private NvwaUserClient nvwaUserClient;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private OrgIdentityService orgIdentityService;
    public static final String SQL_getNvwaEntityIdentityDTOs = "select user.name as username,\ntabledefine.TD_CODE as entityTableName,\nidentity.ENTITY_DATA_KEY_ as entityDataCode\nfrom NP_AUTHZ_ENTITY_IDENTITY identity\njoin np_user user on user.id = identity.IDENTITY_ID_\njoin sys_tabledefine tabledefine\non tabledefine.TD_KEY = identity.ENTITY_TABLE_KEY_";

    @PostMapping(value={"api/v1/commondatasync/role"})
    public BusinessResponseEntity<List<DataSyncNvwaRoleDTO>> getNvwaRoleDTOs() {
        List roles = this.roleService.getAllRoles();
        LinkedList<DataSyncNvwaRoleDTO> daRoles = new LinkedList<DataSyncNvwaRoleDTO>();
        for (Role role : roles) {
            DataSyncNvwaRoleDTO nvwaGlueRoleDTO = NvwaRoleConverter.convertToDataSyncDTO(role);
            daRoles.add(nvwaGlueRoleDTO);
        }
        return BusinessResponseEntity.ok(daRoles);
    }

    @PostMapping(value={"api/v1/commondatasync/role-user-relation"})
    public BusinessResponseEntity<List<DataSyncNvwaRoleUserRelationDTO>> getNvwaRoleUserRelationDTOs() {
        Map<String, String> userId2NameMap = this.nvwaUserClient.getUsers().stream().collect(Collectors.toMap(User::getId, User::getName));
        Map<String, String> roleId2NameMap = this.roleService.getAllRoles().stream().collect(Collectors.toMap(Identifiable::getId, Namable::getName));
        ArrayList relations = new ArrayList();
        roleId2NameMap.forEach((roleId, roleName) -> {
            List userIds = this.roleService.getIdentityIdByRole(roleId);
            if (CollectionUtils.isEmpty((Collection)userIds)) {
                return;
            }
            List<String> usernames = userIds.stream().map(userId -> (String)userId2NameMap.get(userId)).filter(Objects::nonNull).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(usernames)) {
                return;
            }
            DataSyncNvwaRoleUserRelationDTO nvwaRoleUserRelationDTO = new DataSyncNvwaRoleUserRelationDTO((String)roleName, usernames);
            relations.add(nvwaRoleUserRelationDTO);
        });
        return BusinessResponseEntity.ok(relations);
    }

    @PostMapping(value={"api/v1/commondatasync/user"})
    public BusinessResponseEntity<List<DataSyncNvwaUserDTO>> getNvwaUserDTOs() {
        List npUserList = this.nvwaUserClient.getUsers();
        LinkedList<DataSyncNvwaUserDTO> userList = new LinkedList<DataSyncNvwaUserDTO>();
        for (User npUser : npUserList) {
            List userExtendList = this.nvwaUserClient.getExtendedAttribute(npUser.getId());
            ArrayList<DataSyncNvwaAttributeDTO> attributes = new ArrayList<DataSyncNvwaAttributeDTO>();
            for (UserExtendedAttribute userAttribute : userExtendList) {
                DataSyncNvwaAttributeDTO nvwaDAAttribute = new DataSyncNvwaAttributeDTO(userAttribute.getAttributeName(), userAttribute.getAttributeValue());
                attributes.add(nvwaDAAttribute);
            }
            Collection grantedOrgS = this.orgIdentityService.getGrantedOrg(npUser.getId());
            DataSyncNvwaUserDTO nvwaGlueUserDTO = NvwaUserConverter.convertToDataSyncDTO(npUser, attributes, grantedOrgS);
            userList.add(nvwaGlueUserDTO);
        }
        return BusinessResponseEntity.ok(userList);
    }

    @PostMapping(value={"api/v1/commondatasync/orgnization"})
    public BusinessResponseEntity<List<DataSyncNvwaOrganizationDTO>> getNvwaOrgnizationDTOs(@RequestParam(value="orgType") String orgType) {
        OrgDTO query = new OrgDTO();
        query.setAuthType(OrgDataOption.AuthType.NONE);
        query.setPagination(Boolean.valueOf(false));
        query.setCategoryname(orgType);
        query.setStopflag(Integer.valueOf(-1));
        query.setRecoveryflag(Integer.valueOf(-1));
        query.setVersionDate(new Date());
        query.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        PageVO orgs = this.orgDataClient.list(query);
        List<String> zbNames = this.getOrganizationZBNames(orgType);
        LinkedList<DataSyncNvwaOrganizationDTO> daOrgs = new LinkedList<DataSyncNvwaOrganizationDTO>();
        for (OrgDO org : orgs.getRows()) {
            DataSyncNvwaOrganizationDTO nvwaGlueOrganizationDTO = NvwaOrganizationConverter.convertToDataSyncDTO(org, zbNames);
            daOrgs.add(nvwaGlueOrganizationDTO);
        }
        return BusinessResponseEntity.ok(daOrgs);
    }

    private List<String> getOrganizationZBNames(String type) {
        OrgCategoryDO orgCatDTO = new OrgCategoryDO();
        if (ObjectUtils.isEmpty(type)) {
            type = "MD_ORG";
        }
        orgCatDTO.setName(type);
        PageVO orgCatList = this.orgCategoryClient.list(orgCatDTO);
        if (orgCatList.getTotal() == 0) {
            return Collections.emptyList();
        }
        List zbs = ((OrgCategoryDO)orgCatList.getRows().get(0)).getZbs();
        if (zbs == null || zbs.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(zbs.stream().map(t -> t.getName()).collect(Collectors.toList()));
    }

    @PostMapping(value={"api/v1/commondatasync/basedata"})
    public BusinessResponseEntity<List<DataSyncNvwaBaseDataDTO>> getNvwaBaseDataDTOs(@RequestParam(value="type", required=false) String baseDataType) {
        BaseDataDTO query = new BaseDataDTO();
        query.setPagination(Boolean.valueOf(false));
        query.setTableName(baseDataType);
        query.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        query.setIgnoreShareFields(Boolean.valueOf(true));
        PageVO baseDatas = this.baseDataClient.list(query);
        LinkedList<DataSyncNvwaBaseDataDTO> daBaseDatas = new LinkedList<DataSyncNvwaBaseDataDTO>();
        for (BaseDataDO baseData : baseDatas.getRows()) {
            DataSyncNvwaBaseDataDTO nvwaGlueBaseDataDTO = NvwaBaseDataConverter.convertToDataSyncDTO(baseData, baseDataType);
            daBaseDatas.add(nvwaGlueBaseDataDTO);
        }
        return BusinessResponseEntity.ok(daBaseDatas);
    }

    @PostMapping(value={"api/v1/commondatasync/nvwa-entity-identitys"})
    public BusinessResponseEntity<List<DataSyncNvwaEntityIdentityDTO>> getNvwaEntityIdentityDTOs() {
        List daAuthEntityIdentityDTOS = (List)this.jdbcTemplate.query(SQL_getNvwaEntityIdentityDTOs, rs -> {
            ArrayList<DataSyncNvwaEntityIdentityDTO> authEntityIdentityDTOS = new ArrayList<DataSyncNvwaEntityIdentityDTO>();
            while (rs.next()) {
                String username = rs.getString(1);
                String entityTableName = rs.getString(2);
                String entityDataCode = rs.getString(3);
                DataSyncNvwaEntityIdentityDTO nvwaEntityIdentityDTO = new DataSyncNvwaEntityIdentityDTO(username, entityTableName, entityDataCode);
                authEntityIdentityDTOS.add(nvwaEntityIdentityDTO);
            }
            return authEntityIdentityDTOS;
        });
        return BusinessResponseEntity.ok((Object)daAuthEntityIdentityDTOS);
    }
}

