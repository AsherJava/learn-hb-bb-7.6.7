/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.common.DataTypeUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.organization.domain.OrgAuthDO
 *  com.jiuqi.va.organization.service.OrgAuthService
 */
package com.jiuqi.nr.entity.adapter.impl.org;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.adapter.executor.IExtendAuthExecutor;
import com.jiuqi.nr.entity.adapter.executor.IExtendDataExecutor;
import com.jiuqi.nr.entity.adapter.impl.org.OrgVersionManager;
import com.jiuqi.nr.entity.adapter.impl.org.auth.OrgAuthTypeExtendImpl;
import com.jiuqi.nr.entity.adapter.impl.org.base.OrgAdapterException;
import com.jiuqi.nr.entity.adapter.impl.org.base.OrgImportObject;
import com.jiuqi.nr.entity.adapter.impl.org.client.OrgAdapterClient;
import com.jiuqi.nr.entity.adapter.impl.org.data.DefaultOrgDataExecutor;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgConvertUtil;
import com.jiuqi.nr.entity.adapter.provider.DefineDTO;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.IDefineQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.IEntityAuthProvider;
import com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum;
import com.jiuqi.nr.entity.adapter.provider.QueryOptions;
import com.jiuqi.nr.entity.bo.EntitySearchBO;
import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.ext.formtype.IGetOrgFormTypeZbService;
import com.jiuqi.nr.entity.internal.model.impl.EntityAttributeImpl;
import com.jiuqi.nr.entity.internal.model.impl.EntityModelImpl;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityGroup;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.param.IEntityDeleteParam;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.common.DataTypeUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import com.jiuqi.va.organization.service.OrgAuthService;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OrganizationAdapterImpl
implements IEntityAdapter {
    private static final long serialVersionUID = 1L;
    private static final Logger logs = LoggerFactory.getLogger(OrganizationAdapterImpl.class);
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private DataModelClient vaDataModelClient;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private OrgAdapterClient orgAdapterClient;
    @Autowired
    private OrgVersionManager orgVersionManager;
    @Autowired
    private OrgAuthService orgAuthService;
    @Autowired
    @Qualifier(value="defaultOrgDataExecutor")
    private DefaultOrgDataExecutor orgDataExecutor;
    private List<IExtendAuthExecutor<OrganizationAdapterImpl>> orgAuthExecutor;
    private List<IExtendDataExecutor<OrganizationAdapterImpl>> extendDataExecutors;
    @Autowired(required=false)
    private IGetOrgFormTypeZbService formTypeZbService;
    @Autowired
    private INvwaSystemOptionService systemOptionService;

    @Autowired
    public void setExtendDataExecutors(List<IExtendDataExecutor<OrganizationAdapterImpl>> extendDataExecutors) {
        extendDataExecutors.sort(Comparator.comparing(IExtendDataExecutor::getOrder));
        this.extendDataExecutors = extendDataExecutors;
    }

    @Autowired
    public void setOrgAuthProvider(List<IExtendAuthExecutor<OrganizationAdapterImpl>> orgAuthExecutor) {
        orgAuthExecutor.sort(Comparator.comparing(IExtendAuthExecutor::getOrder));
        this.orgAuthExecutor = orgAuthExecutor;
    }

    @Override
    public String getId() {
        return "ORG";
    }

    @Override
    public String getTitle() {
        return "\u7ec4\u7ec7\u673a\u6784";
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean enableSelect() {
        return true;
    }

    @Override
    public List<IEntityGroup> getRootEntityGroups() {
        return null;
    }

    @Override
    public IEntityGroup queryEntityGroup(String groupId) {
        return null;
    }

    @Override
    public List<IEntityGroup> getChildrenEntityGroups(String groupId) {
        return null;
    }

    @Override
    public List<IEntityDefine> getEntitiesInGroup(String groupId) {
        DefineDTO dto = new DefineDTO();
        dto.setTableName("MD_ORG");
        return this.getOrgDefineProvider().query(dto);
    }

    @Override
    public List<IEntityDefine> getEntities(int dimensionFlag) {
        if (dimensionFlag == 1) {
            return this.getOrgDefineProvider().query(null);
        }
        return null;
    }

    @Override
    public IEntityDefine getEntity(String tableName) {
        return this.getOrgDefineProvider().get(tableName);
    }

    @Override
    public IEntityDefine getEntityByCode(String subTreeEntityCode) {
        if (subTreeEntityCode.endsWith("_CODE")) {
            subTreeEntityCode = subTreeEntityCode.substring(0, subTreeEntityCode.indexOf("_CODE"));
        }
        return this.getEntity(subTreeEntityCode);
    }

    @Override
    public List<IEntityRefer> getEntityRefer(String tableName) {
        List columns;
        DataModelDO table = this.getOrgDefineProvider().getModel(tableName);
        if (table != null && (columns = table.getColumns()) != null) {
            return columns.stream().map(v -> OrgConvertUtil.referConvert(tableName, v)).filter(Objects::nonNull).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public TableModelDefine getTableByEntityId(String tableName) {
        return this.getOrgDefineProvider().getNvwaModel(tableName);
    }

    @Override
    public IEntityModel getEntityModel(String tableName) {
        TableModelDefine table = this.getTableByEntityId(tableName);
        if (table == null) {
            return null;
        }
        ColumnModelDefine bblxField = this.formTypeZbService.getFormTypeZbColumn(tableName);
        Map<String, IEntityAttribute> colMap = this.getOrgDefineProvider().getAttributes(tableName);
        ArrayList<IEntityAttribute> attributes = new ArrayList<IEntityAttribute>(colMap.values());
        EntityModelImpl impl = new EntityModelImpl();
        impl.setEntityId(table.getCode());
        impl.setEntityAttributes(attributes);
        impl.setBizKeyField(colMap.get("CODE"));
        IEntityAttribute bblxAttribute = bblxField == null ? colMap.get("BBLX") : EntityAttributeImpl.transferFromColumn(bblxField);
        impl.setBblxField(bblxAttribute);
        impl.setRecKeyField(colMap.get("ID"));
        ArrayList<IEntityAttribute> showColumns = new ArrayList<IEntityAttribute>();
        for (IEntityAttribute value : attributes) {
            if (OrgConvertUtil.disabledField(value.getCode())) continue;
            showColumns.add(value);
        }
        impl.setShowFields(showColumns);
        impl.setNameField(colMap.get("NAME"));
        impl.setCodeField(colMap.get("ORGCODE"));
        impl.setParentField(colMap.get("PARENTCODE"));
        impl.setOrderField(colMap.get("ORDINAL"));
        impl.setIconField(colMap.get("ICON"));
        impl.setBeginDateField(colMap.get("VALIDTIME"));
        impl.setEndDateField(colMap.get("INVALIDTIME"));
        impl.setStoppedField(colMap.get("STOPFLAG"));
        impl.putI18nCode("NAME", "localizedName");
        return impl;
    }

    @Override
    public List<String> getPath(String tableName) {
        return null;
    }

    @Override
    public List<IEntityDefine> fuzzySearchEntityByKeyWords(EntitySearchBO bo) {
        if (bo.getCalibre() != null && bo.getCalibre() == 1) {
            return null;
        }
        List<IEntityDefine> orgTypes = this.getOrgDefineProvider().query(null);
        return orgTypes.stream().filter(v -> {
            if (!StringUtils.hasText(bo.getKeyWords())) {
                return true;
            }
            if (v.getCode().contains(bo.getKeyWords())) {
                return true;
            }
            return v.getTitle().contains(bo.getKeyWords());
        }).filter(e -> {
            if (bo.getCalibre() == null) {
                return true;
            }
            return bo.getCalibre() != 1;
        }).collect(Collectors.toList());
    }

    @Override
    public List<IEntityDefine> getSubTreeEntities(String parentTableName) {
        if (!"MD_ORG".equals(parentTableName)) {
            return null;
        }
        List<IEntityDefine> orgTypes = this.getOrgDefineProvider().query(null);
        return orgTypes.stream().filter(v -> !"MD_ORG".equals(v.getCode())).collect(Collectors.toList());
    }

    @Override
    public IEntityDefine getBaseEntityBySubTreeEntityId(String childrenTableName) {
        if (!"MD_ORG".equals(childrenTableName)) {
            return this.getOrgDefineProvider().get("MD_ORG");
        }
        return null;
    }

    private IDefineQueryProvider getOrgDefineProvider() {
        return this.getExecutor(null, ProviderMethodEnum.DEFINE_QUERY).getDefineProvider();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void importEntityData(String entityId, InputStream inputStream) {
        String token = ShiroUtil.getToken();
        if (!StringUtils.hasText(token)) {
            token = UUID.randomUUID().toString();
            ShiroUtil.bindToken((String)token);
        }
        try {
            List<OrgVersionDO> version = this.orgVersionManager.listVersion(entityId);
            for (OrgVersionDO versionDO : version) {
                OrgDTO param = new OrgDTO();
                param.setCategoryname(versionDO.getCategoryname());
                param.setName(UUID.randomUUID().toString());
                param.setVersionDate(versionDO.getValidtime());
                OrgImportObject importObj = new OrgImportObject(inputStream);
                String data = this.orgAdapterClient.importData(importObj, (Map<String, Object>)param);
                logs.debug(data);
            }
        }
        catch (Exception e) {
            logs.info("\u673a\u6784\u7c7b\u578b\u6570\u636e\u5bfc\u5165\u5931\u8d25:", e);
        }
        finally {
            ShiroUtil.unbindToken((String)token);
        }
    }

    @Override
    public IEntityDefine importEntityDefine(InputStream inputStream) throws JQException {
        OrgCategoryDO param = new OrgCategoryDO();
        try {
            byte[] buffer = IOUtils.toByteArray(inputStream);
            JacksonUtils.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ExportOrgDefine exportOrgDefine = (ExportOrgDefine)JacksonUtils.mapper.readValue(buffer, ExportOrgDefine.class);
            OrgCategoryDO orgCategoryDO = exportOrgDefine.getOrgCategoryDO();
            param.setName(orgCategoryDO.getName());
            PageVO orgcates = this.orgCategoryClient.list(param);
            if (orgcates.getTotal() > 0) {
                List zbs = orgCategoryDO.getZbs();
                for (ZB zb : zbs) {
                    zb.setId(DataTypeUtil.UUID_EMPTY);
                }
                this.orgCategoryClient.update(orgCategoryDO);
            } else {
                this.orgCategoryClient.add(orgCategoryDO);
            }
            DataModelDO dataModelDO = exportOrgDefine.getDataModelDO();
            this.vaDataModelClient.push(dataModelDO);
            List<Object> importVersions = exportOrgDefine.getVersions();
            List<OrgVersionDO> queryVersions = this.orgVersionManager.listVersion(orgCategoryDO.getName());
            Optional<OrgVersionDO> maxVersion = queryVersions.stream().max(Comparator.comparing(OrgVersionDO::getValidtime));
            if (maxVersion.isPresent()) {
                importVersions = importVersions.stream().filter(e -> e.getValidtime().after(((OrgVersionDO)maxVersion.get()).getValidtime())).sorted(Comparator.comparing(OrgVersionDO::getValidtime)).collect(Collectors.toList());
            }
            for (OrgVersionDO importVersion : importVersions) {
                this.orgVersionManager.addVersion(importVersion);
            }
        }
        catch (Exception e2) {
            throw new JQException((ErrorEnum)OrgAdapterException.ADAPTER_ORG_IMPORT_DEFINE, (Throwable)e2);
        }
        return this.getEntity(param.getName());
    }

    @Override
    public void exportEntityData(String entityId, OutputStream outputStream) throws JQException {
        String token = ShiroUtil.getToken();
        if (!StringUtils.hasText(token)) {
            token = UUID.randomUUID().toString();
            ShiroUtil.bindToken((String)token);
        }
        try {
            OrgVersionDO ver = this.orgVersionManager.getVersion(entityId, new Date());
            HashMap<String, Object> param = new HashMap<String, Object>(16);
            param.put("categoryname", ver.getCategoryname());
            param.put("versiontitle", ver.getTitle());
            byte[] request = this.orgAdapterClient.exportData(param);
            IOUtils.write(request, outputStream);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)OrgAdapterException.ADAPTER_ORG_EXPORT_DATA, (Throwable)e);
        }
        finally {
            ShiroUtil.unbindToken((String)token);
        }
    }

    @Override
    public void exportEntityDefine(String entityId, OutputStream outputStream) throws JQException {
        try {
            OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
            orgCategoryDO.setName(entityId);
            PageVO orgcates = this.orgCategoryClient.list(orgCategoryDO);
            if (orgcates.getTotal() == 0) {
                throw new RuntimeException(String.format("\u672a\u627e\u5230\u6807\u8bc6\u4e3a%s\u7684\u7ec4\u7ec7\u673a\u6784\u5b9a\u4e49", entityId));
            }
            DataModelDTO modelParam = new DataModelDTO();
            modelParam.setName(entityId);
            DataModelDO dataModelDO = this.vaDataModelClient.get(modelParam);
            if (dataModelDO == null) {
                throw new RuntimeException(String.format("\u672a\u627e\u5230\u6807\u8bc6\u4e3a%s\u7684\u7ec4\u7ec7\u673a\u6784\u5efa\u6a21", entityId));
            }
            OrgCategoryDO orgcate = (OrgCategoryDO)orgcates.getRows().get(0);
            List<OrgVersionDO> versions = this.orgVersionManager.listVersion(entityId);
            ExportOrgDefine exportOrgDefine = new ExportOrgDefine();
            exportOrgDefine.setOrgCategoryDO(orgcate);
            exportOrgDefine.setDataModelDO(dataModelDO);
            exportOrgDefine.setVersions(versions);
            String data = JacksonUtils.mapper.writeValueAsString((Object)exportOrgDefine);
            IOUtils.write(data.getBytes(StandardCharsets.UTF_8), outputStream);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)OrgAdapterException.ADAPTER_ORG_EXPORT_DEFINE, (Throwable)e);
        }
    }

    @Override
    public boolean isEnableAuthority(String entityId) {
        return true;
    }

    @Override
    public boolean canReadEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.ACCESS.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canEditEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.MANAGE.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canReadEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.ACCESS.toString(), entityId, entityKeyDatas, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canEditEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.MANAGE.toString(), entityId, entityKeyDatas, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canAuditEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.APPROVAL.toString(), entityId, entityKeyDatas, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canSubmitEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.SUBMIT.toString(), entityId, entityKeyDatas, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canUploadEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.REPORT.toString(), entityId, entityKeyDatas, queryVersionDate);
    }

    @Override
    public boolean canWriteEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.WRITE.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canWriteEntity(String entityId, Set<String> entityKeyDatas, Date queryVersionDate) {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.WRITE.toString(), entityId, entityKeyDatas, queryVersionDate);
    }

    @Override
    public boolean canSubmitEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.SUBMIT.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canSubmitEntity(String entityId, String entityKeyData, String identityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.SUBMIT.toString(), entityId, entityKeyData, identityId, queryVersionDate);
    }

    @Override
    public boolean canUploadEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.REPORT.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canUploadEntity(String entityId, String entityKeyData, String identityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.REPORT.toString(), entityId, entityKeyData, identityId, queryVersionDate);
    }

    @Override
    public boolean canAuditEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.APPROVAL.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canAuditEntity(String entityId, String entityKeyData, String identityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgDataOption.AuthType.APPROVAL.toString(), entityId, entityKeyData, identityId, queryVersionDate);
    }

    @Override
    public boolean canPublishEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgAuthTypeExtendImpl.AuthTypeExtend.PUBLISH.name(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public boolean canReadUnPublishEntity(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).canAuthorityEntityKey(OrgAuthTypeExtendImpl.AuthTypeExtend.READ_UN_PUBLISH.name(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public Set<String> getCanReadEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).getCanOperateEntityKeys(OrgDataOption.AuthType.ACCESS.toString(), entityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanEditEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).getCanOperateEntityKeys(OrgDataOption.AuthType.MANAGE.toString(), entityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanWriteEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).getCanOperateEntityKeys(OrgDataOption.AuthType.WRITE.toString(), entityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanAuditEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).getCanOperateEntityKeys(OrgDataOption.AuthType.APPROVAL.toString(), entityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanSubmitEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).getCanOperateEntityKeys(OrgDataOption.AuthType.SUBMIT.toString(), entityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanUploadEntityKeys(String entityId, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).getCanOperateEntityKeys(OrgDataOption.AuthType.REPORT.toString(), entityId, queryVersionDate);
    }

    @Override
    public Set<String> getCanUploadIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).getCanOperateIdentities(OrgDataOption.AuthType.REPORT.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public Set<String> getCanAuditIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).getCanOperateIdentities(OrgDataOption.AuthType.APPROVAL.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public Set<String> getCanSubmitIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).getCanOperateIdentities(OrgDataOption.AuthType.SUBMIT.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public Set<String> getCanReadIdentityKeys(String entityId, String entityKeyData, Date queryVersionDate) throws UnauthorizedEntityException {
        return this.getAuthProvider(entityId, queryVersionDate).getCanOperateIdentities(OrgDataOption.AuthType.ACCESS.toString(), entityId, entityKeyData, queryVersionDate);
    }

    @Override
    public void grantAllPrivilegesToEntityTable(String entityCode) {
        OrgDTO param = new OrgDTO();
        param.setCategoryname(entityCode);
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setVersionDate(new Date());
        PageVO dataPage = this.orgDataClient.list(param);
        if (dataPage.getTotal() == 0) {
            return;
        }
        List<OrgAuthDO> map = dataPage.getRows().stream().map(org -> OrgConvertUtil.createOrgAuthDO(entityCode, org.getCode())).collect(Collectors.toList());
        this.updateAuth(map);
    }

    @Override
    public void grantAllPrivilegesToEntityData(String entityId, String ... entityKeyData) {
        if (entityKeyData != null && entityKeyData.length > 0) {
            ArrayList<OrgAuthDO> map = new ArrayList<OrgAuthDO>();
            for (String code : entityKeyData) {
                map.add(OrgConvertUtil.createOrgAuthDO(entityId, code));
            }
            this.updateAuth(map);
        }
    }

    private void updateAuth(List<OrgAuthDO> map) {
        this.orgAuthService.updateDetail(map);
    }

    private IEntityAuthProvider getAuthProvider(String entityId, Date version) {
        for (IExtendAuthExecutor<OrganizationAdapterImpl> authExecutor : this.orgAuthExecutor) {
            if (!authExecutor.isEnable(entityId)) continue;
            return authExecutor.getProvider(entityId, version);
        }
        return this.orgAuthExecutor.get(0).getProvider(entityId, version);
    }

    @Override
    public EntityResultSet getAllRows(IEntityQueryParam query) {
        return this.getDataProvider(query).getAllData();
    }

    @Override
    public EntityResultSet getRootRows(IEntityQueryParam query) {
        return this.getDataProvider(query).getRootData();
    }

    @Override
    public EntityResultSet getChildRows(IEntityQueryParam query, String ... entityKeyDatas) {
        return this.getDataProvider(query).getChildData(QueryOptions.TreeType.DIRECT_CHILDREN, entityKeyDatas);
    }

    @Override
    public EntityResultSet getAllChildRows(IEntityQueryParam query, String entityKeyData) {
        return this.getDataProvider(query).getChildData(QueryOptions.TreeType.ALL_CHILDREN, entityKeyData);
    }

    @Override
    public EntityResultSet findByEntityKeys(IEntityQueryParam query) {
        return this.getDataProvider(query).findByEntityKeys();
    }

    @Override
    public String getParent(IEntityQueryParam query, String queryCode) {
        return this.getDataProvider(query).getParent(queryCode);
    }

    @Override
    public String getParent(IEntityQueryParam query, Map<String, Object> rowData) {
        if (rowData == null) {
            throw new IllegalArgumentException("'rowData' must not be null.");
        }
        return this.getDataProvider(query).getParent(rowData);
    }

    @Override
    public int getMaxDepth(IEntityQueryParam query) {
        return this.getDataProvider(query).getMaxDepth();
    }

    @Override
    public int getMaxDepthByEntityKey(IEntityQueryParam query, String entityKeyData) {
        return this.getDataProvider(query).getMaxDepthByEntityKey(entityKeyData);
    }

    @Override
    public EntityResultSet findByCode(IEntityQueryParam query) {
        return this.getDataProvider(query).findByCodes();
    }

    @Override
    public int getDirectChildCount(IEntityQueryParam query, String entityKeyData) {
        return this.getDataProvider(query).getChildCount(entityKeyData, QueryOptions.TreeType.DIRECT_CHILDREN);
    }

    @Override
    public int getAllChildCount(IEntityQueryParam query, String entityKeyData) {
        return this.getDataProvider(query).getChildCount(entityKeyData, QueryOptions.TreeType.ALL_CHILDREN);
    }

    @Override
    public Map<String, Integer> getDirectChildCountByParent(IEntityQueryParam query, String parentKey) {
        return this.getDataProvider(query).getChildCountByParent(parentKey, QueryOptions.TreeType.DIRECT_CHILDREN);
    }

    @Override
    public Map<String, Integer> getAllChildCountByParent(IEntityQueryParam query, String parentKey) {
        return this.getDataProvider(query).getChildCountByParent(parentKey, QueryOptions.TreeType.ALL_CHILDREN);
    }

    @Override
    public String[] getParentsEntityKeyDataPath(IEntityQueryParam query, String entityKeyData) {
        return this.getDataProvider(query).getParentsEntityKeyDataPath(entityKeyData);
    }

    @Override
    public String[] getParentsEntityKeyDataPath(IEntityQueryParam query, Map<String, Object> rowData) {
        if (rowData == null) {
            throw new IllegalArgumentException("'rowData' must not be null.");
        }
        return this.getDataProvider(query).getParentsEntityKeyDataPath(rowData);
    }

    @Override
    public int getTotalCount(IEntityQueryParam query) {
        return this.getDataProvider(query).getTotalCount();
    }

    private IExtendDataExecutor getExecutor(IEntityQueryParam query, ProviderMethodEnum providerEnum) {
        for (IExtendDataExecutor<OrganizationAdapterImpl> executor : this.extendDataExecutors) {
            if (!executor.isEnable(query, providerEnum)) continue;
            if (query != null) {
                query.getQueryContext().getLogger().accept(e -> {
                    if (e.isTraceEnabled()) {
                        e.trace("\u6267\u884c\u5668\u4e3a\uff1a{}", executor.getClass().getSimpleName());
                    }
                });
            }
            return executor;
        }
        return this.orgDataExecutor;
    }

    private IDataQueryProvider getDataProvider(IEntityQueryParam query) {
        return this.getExecutor(query, ProviderMethodEnum.DATA_QUERY).getDataQueryProvider(query);
    }

    @Override
    public EntityUpdateResult insertRows(IEntityUpdateParam addParam) throws EntityUpdateException {
        return this.getExecutor(addParam, ProviderMethodEnum.DATA_MODIFY).getDataModifyProvider().insertRows(addParam);
    }

    @Override
    public EntityUpdateResult deleteRows(IEntityDeleteParam deleteParam) throws EntityUpdateException {
        return this.getExecutor(deleteParam, ProviderMethodEnum.DATA_MODIFY).getDataModifyProvider().deleteRows(deleteParam);
    }

    @Override
    public EntityUpdateResult updateRows(IEntityUpdateParam updateParam) throws EntityUpdateException {
        return this.getExecutor(updateParam, ProviderMethodEnum.DATA_MODIFY).getDataModifyProvider().updateRows(updateParam);
    }

    @Override
    public EntityCheckResult rowsCheck(IEntityUpdateParam updateParam, boolean insert) throws EntityUpdateException {
        return this.getExecutor(updateParam, ProviderMethodEnum.DATA_MODIFY).getDataModifyProvider().rowsCheck(updateParam, insert);
    }

    @Override
    public EntityResultSet simpleQuery(IEntityQueryParam query) {
        return this.getDataProvider(query).simpleQueryByKeys();
    }

    @Override
    public String getDimensionName(String entityId) {
        return "MD_ORG";
    }

    @Override
    public String getDimensionNameByCode(String entityCode) {
        return "MD_ORG";
    }

    @Override
    public String getEntityIdByCode(String entityCode) {
        return entityCode;
    }

    @Override
    public boolean isDBMode(String entityId, Date date) {
        OrgVersionDO versionDO = this.orgVersionManager.getVersion(entityId, date);
        return versionDO != null && !versionDO.isActive() && this.systemOptionService.get("ORG_AUTH_EXT", "ORG_DB_MODE").equals("1");
    }

    @Override
    public String getEntityCode(String entityId) {
        return entityId;
    }

    static class ExportOrgDefine
    implements Serializable {
        private OrgCategoryDO orgCategoryDO;
        private DataModelDO dataModelDO;
        private List<OrgVersionDO> versions;

        ExportOrgDefine() {
        }

        public OrgCategoryDO getOrgCategoryDO() {
            return this.orgCategoryDO;
        }

        public void setOrgCategoryDO(OrgCategoryDO orgCategoryDO) {
            this.orgCategoryDO = orgCategoryDO;
        }

        public DataModelDO getDataModelDO() {
            return this.dataModelDO;
        }

        public void setDataModelDO(DataModelDO dataModelDO) {
            this.dataModelDO = dataModelDO;
        }

        public List<OrgVersionDO> getVersions() {
            return this.versions;
        }

        public void setVersions(List<OrgVersionDO> versions) {
            this.versions = versions;
        }
    }
}

