/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.clbrbill.enums.ClbrChangeTypeEnum
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nvwa.authority.role.vo.RoleVo
 *  com.jiuqi.nvwa.authority.role.web.NvwaAuthorityRoleGroupController
 *  com.jiuqi.nvwa.authority.user.vo.BatchUserOperation
 *  com.jiuqi.nvwa.authority.user.vo.BatchUserReq
 *  com.jiuqi.nvwa.authority.user.vo.UserDTOReq
 *  com.jiuqi.nvwa.authority.user.vo.UserManageOrgReq
 *  com.jiuqi.nvwa.authority.user.web.NvwaAuthorityUserController
 *  com.jiuqi.nvwa.authority.vo.ResultObject
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDataService
 *  com.jiuqi.va.bill.intf.BillDefine
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.biz.intf.autotask.AutoTask
 *  com.jiuqi.va.bizmeta.service.IMetaInfoService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.gcreport.clbrbill.bill.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.clbrbill.enums.ClbrChangeTypeEnum;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nvwa.authority.role.vo.RoleVo;
import com.jiuqi.nvwa.authority.role.web.NvwaAuthorityRoleGroupController;
import com.jiuqi.nvwa.authority.user.vo.BatchUserOperation;
import com.jiuqi.nvwa.authority.user.vo.BatchUserReq;
import com.jiuqi.nvwa.authority.user.vo.UserDTOReq;
import com.jiuqi.nvwa.authority.user.vo.UserManageOrgReq;
import com.jiuqi.nvwa.authority.user.web.NvwaAuthorityUserController;
import com.jiuqi.nvwa.authority.vo.ResultObject;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDataService;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.biz.intf.autotask.AutoTask;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AuthorityManageAutoTask
implements AutoTask {
    private final Logger logger = LoggerFactory.getLogger(AuthorityManageAutoTask.class);
    @Autowired
    private NvwaAuthorityRoleGroupController nvwaAuthorityRoleGroupController;
    @Autowired
    private NvwaAuthorityUserController nvwaAuthorityUserController;
    @Autowired
    BillDataService billDataService;
    @Autowired
    BillDefineService billDefineService;
    @Autowired
    IMetaInfoService metaInfoService;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private SystemUserService sysUserService;

    public String getName() {
        return "AuthorityManageAutoTask";
    }

    public String getTitle() {
        return "\u6743\u9650\u5355\u6388\u6743\u81ea\u52a8\u4efb\u52a1";
    }

    public String getAutoTaskModule() {
        return "bill";
    }

    public Boolean canRetract() {
        return true;
    }

    public R retrack(Object params) {
        return null;
    }

    public R execute(Object params) {
        boolean isManage;
        TenantDO tenantDO = (TenantDO)params;
        String bizCode = tenantDO.getExtInfo("bizCode").toString();
        TenantDO param = new TenantDO();
        HashMap<String, String> extInfo = new HashMap<String, String>();
        param.setExtInfo(extInfo);
        extInfo.put("billCode", bizCode);
        BillDefine billDefine = this.billDataService.getBillDefineByCode(param);
        MetaInfoDTO metaData = this.metaInfoService.getMetaInfoByUniqueCode(billDefine.getName());
        BillContextImpl billContext = new BillContextImpl();
        billContext.setDisableVerify(true);
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)billContext, metaData.getUniqueCode());
        model.loadByCode(bizCode);
        Map tablesData = model.getData().getTablesData();
        boolean bl = isManage = !CollectionUtils.isEmpty((Collection)tablesData.get("GC_AUTHORITYMANAGEMENTBILL"));
        if (isManage) {
            this.authorityManage(model);
        } else {
            this.authorityChange(model);
        }
        return R.ok();
    }

    private void authorityManage(BillModelImpl model) {
        Map tablesData = model.getData().getTablesData();
        Map<String, List<String>> childMTableMap = this.getChildMTableMap(tablesData, "GC_AUTHORITYITEMBILL");
        List childrenTables = (List)tablesData.get("GC_AUTHORITYITEMBILL");
        for (Map childTable : childrenTables) {
            String userId = childTable.get("USERLINKID").toString();
            ArrayList<String> manageCodes = new ArrayList();
            if (childTable.get("MANAGEORGCODES") != null) {
                String manageBindingId = childTable.get("MANAGEORGCODES").toString();
                manageCodes = childMTableMap.get(manageBindingId);
            }
            String roleBindingId = childTable.get("STATIONROLE") == null ? "" : childTable.get("STATIONROLE").toString();
            List<String> roleCodes = childMTableMap.get(roleBindingId);
            this.setRoleCodes(userId, this.getRoleId(roleCodes), manageCodes);
        }
    }

    private void authorityChange(BillModelImpl model) {
        Map tablesData = model.getData().getTablesData();
        List childrenTables = (List)tablesData.get("GC_AUTHORITYITEMBILL");
        String manageCode = null;
        for (Map childTable : childrenTables) {
            String masteCode = childTable.get("MANAGEBILLCODE").toString();
            if (manageCode == null) {
                manageCode = masteCode;
                continue;
            }
            if (manageCode.equals(masteCode)) continue;
            throw new BusinessRuntimeException("\u53d8\u66f4\u5b50\u8868\u6765\u6e90\u4e8e\u591a\u4e2a\u6743\u9650\u7ba1\u7406\u8868");
        }
        Map<String, List<String>> childMTableMap = this.getChildMTableMap(tablesData, "GC_AUTHORITYITEMCHANGEBILL");
        List childrenAfterTables = (List)tablesData.get("GC_AUTHORITYITEMCHANGEBILL");
        this.changeToManage(childrenAfterTables, manageCode);
        for (Map childTable : childrenAfterTables) {
            String userId = childTable.get("USERLINKID").toString();
            String changeType = childTable.get("CHANGETYPE").toString();
            if (changeType.equals(ClbrChangeTypeEnum.STOP.getCode())) {
                this.stopUser(userId);
                continue;
            }
            String roleBindingId = childTable.get("STATIONROLE") == null ? "" : childTable.get("STATIONROLE").toString();
            List<String> roleCodes = childMTableMap.get(roleBindingId);
            ArrayList<String> manageCodes = new ArrayList<String>();
            if (childTable.get("MANAGEORGCODES") != null) {
                String manageBindingId = childTable.get("MANAGEORGCODES").toString();
                manageCodes = childMTableMap.get(manageBindingId);
            }
            manageCodes.add("EVERYONE");
            this.setRoleCodes(userId, this.getRoleId(roleCodes), null);
            this.setManageCodes(userId, manageCodes);
        }
    }

    private void changeToManage(List<Map<String, Object>> childrenAfterTables, String manageCode) {
        String querySql = "select * from GC_AUTHORITYITEMBILL  where BILLCODE = '" + manageCode + "'";
        List dataList = EntNativeSqlDefaultDao.getInstance().selectMap(querySql, new Object[0]);
        if (CollectionUtils.isEmpty(dataList)) {
            this.logger.info("\u672a\u67e5\u5230\u7ba1\u7406\u5355\u5b50\u8868\u4fe1\u606f");
            return;
        }
        String masterId = ((Map)dataList.get(0)).get("MASTERID").toString();
        String deleteSql = "delete from GC_AUTHORITYITEMBILL  where BILLCODE = '" + manageCode + "'";
        EntNativeSqlDefaultDao.getInstance().execute(deleteSql);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setBiztype(DataModelType.BizType.BILL);
        dataModelDTO.setName("GC_AUTHORITYITEMBILL");
        try {
            HashMap<String, Object> changeData = new HashMap<String, Object>();
            for (Map<String, Object> childrenAfterTable : childrenAfterTables) {
                String id = UUIDUtils.newUUIDStr();
                block19: for (Map.Entry<String, Object> entry : childrenAfterTable.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    switch (key) {
                        case "MANAGEORGCODES": 
                        case "STATIONROLE": {
                            if (value == null) {
                                changeData.put(key, null);
                                continue block19;
                            }
                            String binDingId = this.copyMultipleChildData("GC_AUTHORITYITEMCHANGEBILL", value.toString(), masterId, id);
                            changeData.put(key, binDingId);
                            continue block19;
                        }
                        case "ID": {
                            changeData.put(key, id);
                            continue block19;
                        }
                        case "MASTERID": 
                        case "MANAGEBILLID": {
                            changeData.put(key, masterId);
                            continue block19;
                        }
                        case "MANAGEBILLCODE": 
                        case "ITEMCODE": 
                        case "BILLCODE": {
                            changeData.put(key, manageCode);
                            continue block19;
                        }
                    }
                    changeData.put(key, value);
                }
                this.insertSqlExecute("GC_AUTHORITYITEMBILL", changeData);
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
    }

    private String copyMultipleChildData(String tableName, String srcBingId, String targetMasTerId, String groupId) {
        String multipleTableName = tableName + "_M";
        String manageSql = "select * from " + multipleTableName + "  where BINDINGID = '" + srcBingId + "'";
        List childDataList = EntNativeSqlDefaultDao.getInstance().selectMap(manageSql, new Object[0]);
        String binDingId = UUIDUtils.newUUIDStr();
        for (Map childTable : childDataList) {
            childTable.put("ID", UUIDUtils.newUUIDStr());
            childTable.put("GROUPID", groupId);
            childTable.put("BINDINGID", binDingId);
            childTable.put("MASTERID", targetMasTerId);
            this.insertSqlExecute("GC_AUTHORITYITEMBILL_M", childTable);
        }
        return binDingId;
    }

    private void insertSqlExecute(String tableName, Map<String, Object> dataMap) {
        try {
            CommonDao commonDao = (CommonDao)SpringContextUtils.getBean(CommonDao.class);
            SQL sql = new SQL();
            sql.INSERT_INTO(tableName);
            HashMap<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("tableName", tableName);
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                String columnName = entry.getKey();
                sql.INTO_COLUMNS(new String[]{entry.getKey()});
                sql.INTO_VALUES(new String[]{" #{param." + columnName + "}"});
                paramMap.put(columnName, entry.getValue());
            }
            SqlDTO sqlDTO = new SqlDTO(ShiroUtil.getTenantName(), sql.toString());
            sqlDTO.setParam(paramMap);
            commonDao.executeBySql(sqlDTO);
        }
        catch (Exception e) {
            this.logger.error("\u65b0\u589e\u6570\u636e\u5931\u8d25" + e.getMessage(), e);
        }
    }

    private List<String> getRoleId(List<String> roleCodes) {
        if (CollectionUtils.isEmpty(roleCodes)) {
            return null;
        }
        NpContext context = NpContextHolder.getContext();
        this.bindAdminContext();
        ArrayList<String> roleIds = new ArrayList<String>();
        try {
            for (String roleCode : roleCodes) {
                RoleVo vo = this.nvwaAuthorityRoleGroupController.queryRoleByCode(roleCode);
                if (vo == null) continue;
                roleIds.add(vo.getId());
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        finally {
            NpContextHolder.setContext((NpContext)context);
        }
        return roleIds;
    }

    private void setManageCodes(String userId, List<String> manageCodes) {
        NpContext context = NpContextHolder.getContext();
        this.bindAdminContext();
        try {
            UserManageOrgReq userManageOrgReq = new UserManageOrgReq();
            userManageOrgReq.setId(userId);
            userManageOrgReq.setManageOrgCodes(manageCodes);
            this.nvwaAuthorityUserController.manageOrg(userManageOrgReq);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        finally {
            NpContextHolder.setContext((NpContext)context);
        }
    }

    private void stopUser(String userId) {
        NpContext context = NpContextHolder.getContext();
        this.bindAdminContext();
        try {
            BatchUserReq batchUserReq = new BatchUserReq();
            batchUserReq.setUserOperation(BatchUserOperation.BATCH_UNENABLE);
            ArrayList<String> userIds = new ArrayList<String>();
            userIds.add(userId);
            batchUserReq.setIds(userIds);
            this.nvwaAuthorityUserController.batchOperation(batchUserReq);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        finally {
            NpContextHolder.setContext((NpContext)context);
        }
    }

    private void setRoleCodes(String userId, List<String> roleCodes, List<String> manageCodes) {
        NpContext context = NpContextHolder.getContext();
        this.bindAdminContext();
        try {
            UserDTOReq userDTOReq = new UserDTOReq();
            userDTOReq.setId(userId);
            userDTOReq.setManageOrgCodes(manageCodes);
            userDTOReq.setRoleIds(roleCodes);
            userDTOReq.setEnabled(Boolean.TRUE);
            ResultObject r = this.nvwaAuthorityUserController.update(userDTOReq);
            this.logger.info("\u6388\u6743\u5355\u6388\u6743\u7ed3\u679c\u4e3a\uff1a" + r.getMessage());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        finally {
            NpContextHolder.setContext((NpContext)context);
        }
    }

    private Map<String, List<String>> getChildMTableMap(Map<String, List<Map<String, Object>>> tablesData, String tableName) {
        List<Map<String, Object>> childrenMTables = tablesData.get(tableName + "_M");
        if (childrenMTables == null) {
            return new HashMap<String, List<String>>();
        }
        HashMap<String, List<String>> dataMap = new HashMap<String, List<String>>();
        for (Map<String, Object> childM : childrenMTables) {
            String bindingId = childM.get("BINDINGID").toString();
            String bindingValue = (String)childM.get("BINDINGVALUE");
            ArrayList<String> childrenMList = (ArrayList<String>)dataMap.get(bindingId);
            if (childrenMList == null) {
                childrenMList = new ArrayList<String>();
                childrenMList.add(bindingValue);
                dataMap.put(bindingId, childrenMList);
                continue;
            }
            childrenMList.add(bindingValue);
        }
        return dataMap;
    }

    private void bindAdminContext() {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = new NpContextUser();
        Optional systemUser = this.sysUserService.getUsers().stream().findFirst();
        SystemUser user = (SystemUser)systemUser.get();
        contextUser.setId(user.getId());
        contextUser.setName(user.getName());
        contextUser.setNickname(user.getNickname());
        contextUser.setDescription(user.getDescription());
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getFullname());
        npContext.setIdentity((ContextIdentity)identity);
        NpContextHolder.setContext((NpContext)npContext);
    }
}

