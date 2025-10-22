/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.basedata.auth.domain.BaseDataAuthDO
 *  com.jiuqi.va.basedata.auth.service.VaBaseDataAuthService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.data;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.impl.basedata.BaseDataDataCheck;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.CommonBaseDataQuery;
import com.jiuqi.nr.entity.adapter.impl.basedata.exception.BaseDataSyncException;
import com.jiuqi.nr.entity.adapter.provider.IDataModifyProvider;
import com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityDataRow;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.param.IEntityDeleteParam;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;
import com.jiuqi.va.basedata.auth.domain.BaseDataAuthDO;
import com.jiuqi.va.basedata.auth.service.VaBaseDataAuthService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class BaseDataModifier
extends CommonBaseDataQuery
implements IDataModifyProvider {
    private static final Logger logger = LoggerFactory.getLogger(BaseDataModifier.class);
    private BaseDataClient baseDataClient;
    private VaBaseDataAuthService baseDataAuthService;
    private SystemIdentityService systemIdentityService;
    private BaseDataDataCheck basDataDataCheck;

    public BaseDataModifier(BaseDataClient baseDataClient, BaseDataDefineClient baseDataDefineClient, SystemIdentityService systemIdentityService, BaseDataDataCheck basDataDataCheck, VaBaseDataAuthService baseDataAuthService) {
        super(baseDataDefineClient);
        this.baseDataClient = baseDataClient;
        this.systemIdentityService = systemIdentityService;
        this.basDataDataCheck = basDataDataCheck;
        this.baseDataAuthService = baseDataAuthService;
    }

    @Override
    public EntityUpdateResult insertRows(IEntityUpdateParam updateParam) throws BaseDataSyncException {
        return this.updateBasedata(true, updateParam);
    }

    @Override
    public EntityUpdateResult deleteRows(IEntityDeleteParam deleteParam) throws BaseDataSyncException {
        EntityUpdateResult result = new EntityUpdateResult();
        BaseDataBatchOptDTO batchDTO = new BaseDataBatchOptDTO();
        batchDTO.setQueryParam(this.getBaseDataFilter(deleteParam));
        List<EntityDataRow> deleteRows = deleteParam.getDeleteRows();
        ArrayList<BaseDataDO> datalist = new ArrayList<BaseDataDO>(deleteRows.size());
        for (EntityDataRow deleteRow : deleteRows) {
            BaseDataDO baseDataDO = new BaseDataDO();
            baseDataDO.setId(UUID.fromString(deleteRow.getRecKey()));
            baseDataDO.setObjectcode(deleteRow.getEntityKeyData());
            baseDataDO.setTableName(deleteParam.getEntityId());
            datalist.add(baseDataDO);
        }
        batchDTO.setDataList(datalist);
        R sync = this.baseDataClient.batchRemove(batchDTO);
        if (sync.getCode() != 0) {
            throw new BaseDataSyncException(String.format("\u5220\u9664\u57fa\u7840\u6570\u636e'%s'\u7684\u6570\u636e\u65f6\uff0c\u53d1\u751f\u4e86\u9519\u8bef\uff1a%s", deleteParam.getEntityId(), sync.getMsg()));
        }
        StringBuffer errorMessage = new StringBuffer();
        List<String> successKey = this.executeResult(sync, errorMessage);
        for (String code : successKey) {
            result.addCodeToKey(code, code);
        }
        return result;
    }

    @Override
    public EntityUpdateResult updateRows(IEntityUpdateParam updateParam) throws BaseDataSyncException {
        return this.updateBasedata(false, updateParam);
    }

    @Override
    public EntityCheckResult rowsCheck(IEntityUpdateParam updateParam, boolean insert) {
        EntityCheckResult checkResult = new EntityCheckResult();
        List<EntityDataRow> modifyRows = updateParam.getModifyRows();
        Date versionDate = updateParam.getVersionDate();
        String tableName = updateParam.getEntityId();
        List<CheckFailNodeInfo> checkFailNodeInfos = this.basDataDataCheck.checkData(updateParam, insert);
        checkResult.setEntityId(tableName);
        checkResult.setVersionTime(versionDate);
        checkResult.setFailInfos(checkFailNodeInfos);
        return checkResult;
    }

    private EntityUpdateResult updateBasedata(boolean insert, IEntityUpdateParam updateParam) throws BaseDataSyncException {
        EntityUpdateResult updateResult = new EntityUpdateResult();
        this.checkData(updateParam.getModifyRows(), insert);
        BaseDataBatchOptDTO batchDTO = new BaseDataBatchOptDTO();
        BaseDataDTO baseDataFilter = this.getBaseDataFilter(updateParam);
        List<EntityDataRow> updateRows = updateParam.getModifyRows();
        List existDataRows = new ArrayList();
        if (insert) {
            List codes = updateRows.stream().map(e -> e.getValue("code").toString()).collect(Collectors.toList());
            baseDataFilter.setRecoveryflag(Integer.valueOf(0));
            baseDataFilter.setBaseDataCodes(codes);
            PageVO existData = this.baseDataClient.list(baseDataFilter);
            existDataRows = existData.getRows();
            List queryKeys = existDataRows.stream().map(BaseDataDO::getCode).collect(Collectors.toList());
            updateRows = updateRows.stream().filter(e -> !queryKeys.contains(e.getValue("code"))).collect(Collectors.toList());
            baseDataFilter.setRecoveryflag(Integer.valueOf(1));
            PageVO existRecoveryData = this.baseDataClient.list(baseDataFilter);
            for (EntityDataRow updateRow : updateRows) {
                Optional<BaseDataDO> findRow = existRecoveryData.getRows().stream().filter(e -> e.getCode().equalsIgnoreCase(String.valueOf(updateRow.getValue("code")))).findFirst();
                if (!findRow.isPresent()) continue;
                Map<String, Object> rowData = updateRow.getRowData();
                BaseDataDO baseDataDO = findRow.get();
                rowData.put("objectcode", baseDataDO.getObjectcode());
                rowData.put("recoveryflag", 0);
                rowData.put("id", baseDataDO.getId());
            }
        }
        if (updateRows.size() == 0) {
            return updateResult;
        }
        ArrayList<BaseDataDO> datalist = new ArrayList<BaseDataDO>(updateRows.size());
        if (!insert) {
            List objectCodes = updateRows.stream().map(EntityDataRow::getEntityKeyData).collect(Collectors.toList());
            baseDataFilter.setRecoveryflag(Integer.valueOf(0));
            baseDataFilter.setBaseDataObjectcodes(objectCodes);
            PageVO existData = this.baseDataClient.list(baseDataFilter);
            existDataRows = existData.getRows();
        }
        Map<String, BaseDataDO> existBaseDataMap = existDataRows.stream().collect(Collectors.toMap(BaseDataDO::getObjectcode, b -> b, (b1, b2) -> b2));
        for (EntityDataRow updateRow : updateRows) {
            Object parentValue;
            Object code;
            BaseDataDO baseDataDO = new BaseDataDO();
            String recKey = updateRow.getRecKey();
            if (StringUtils.hasText(recKey)) {
                baseDataDO.setId(UUID.fromString(recKey));
            }
            if ((code = updateRow.getValue("code")) != null && !"".equals(code)) {
                String codeValue = String.valueOf(code);
                baseDataDO.setCode(codeValue);
                if (insert) {
                    updateRow.setEntityKeyData(codeValue);
                }
            } else {
                BaseDataDO existBaseData = existBaseDataMap.get(updateRow.getEntityKeyData());
                if (existBaseData != null) {
                    baseDataDO.setCode(existBaseData.getCode());
                }
            }
            String entityKeyData = updateRow.getEntityKeyData();
            if (StringUtils.hasText(entityKeyData)) {
                baseDataDO.setObjectcode(entityKeyData);
                updateResult.addCodeToKey(baseDataDO.getCode(), entityKeyData);
            } else {
                updateResult.addCodeToKey(baseDataDO.getCode(), baseDataDO.getCode());
            }
            if (StringUtils.hasText(updateRow.getTitle())) {
                baseDataDO.setName(updateRow.getTitle());
            }
            if (StringUtils.hasText(updateRow.getParentId())) {
                baseDataDO.setParentcode(updateRow.getParentId());
            }
            baseDataDO.setTableName(updateParam.getEntityId());
            updateRow.getRowData().forEach((key, value) -> baseDataDO.put(key.toLowerCase(Locale.ROOT), value));
            Set<String> keys = updateRow.getRowData().keySet();
            if (keys.contains("PARENTCODE".toLowerCase()) && ((parentValue = updateRow.getValue("PARENTCODE".toLowerCase())) == null || "".equals(parentValue))) {
                baseDataDO.setParentcode("-");
            }
            datalist.add(baseDataDO);
        }
        baseDataFilter.setBaseDataCodes(null);
        baseDataFilter.setRecoveryflag(Integer.valueOf(0));
        batchDTO.setQueryParam(baseDataFilter);
        batchDTO.setDataList(datalist);
        batchDTO.setHighTrustability(true);
        R sync = this.baseDataClient.sync(batchDTO);
        if (sync.getCode() != 0) {
            throw new BaseDataSyncException(String.format("%s\u57fa\u7840\u6570\u636e'%s'\u7684\u6570\u636e\u65f6\uff0c\u53d1\u751f\u4e86\u9519\u8bef\uff1a%s", insert ? "\u63d2\u5165" : "\u66f4\u65b0", updateParam.getEntityId(), sync.getMsg()));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("\u57fa\u7840\u6570\u636e[{}]{}\u6570\u636e\u6210\u529f", (Object)updateParam.getEntityId(), (Object)(insert ? "\u65b0\u589e" : "\u4fee\u6539"));
        }
        StringBuffer errorMessage = new StringBuffer();
        List<String> successKey = this.executeResult(sync, errorMessage);
        if (insert) {
            if (CollectionUtils.isEmpty(successKey)) {
                successKey = updateRows.stream().map(e -> String.valueOf(e.getValue("code"))).collect(Collectors.toList());
            }
            if (logger.isDebugEnabled()) {
                StringBuilder sbs = new StringBuilder();
                for (String code : successKey) {
                    sbs.append(code).append(";");
                }
                logger.debug("\u65b0\u589e\u57fa\u7840\u6570\u636e\u7684\u6807\u8bc6\u4e3a\uff1a{}", (Object)sbs);
            }
            this.setBaseDataAuth(baseDataFilter, successKey, updateParam);
        }
        return updateResult;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setBaseDataAuth(BaseDataDTO baseDataDTO, List<String> successKey, IEntityUpdateParam updateParam) throws BaseDataSyncException {
        ContextUser user = NpContextHolder.getContext().getUser();
        if (user == null) {
            return;
        }
        String userName = user.getName();
        if (!StringUtils.hasText(userName)) {
            return;
        }
        boolean isAdmin = this.systemIdentityService.isAdmin();
        if (isAdmin) {
            return;
        }
        BaseDataDefineDTO basedataDefineDTO = new BaseDataDefineDTO();
        basedataDefineDTO.setName(baseDataDTO.getTableName());
        BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(basedataDefineDTO);
        Integer authflag = baseDataDefineDO.getAuthflag();
        if (authflag == null || authflag == 0) {
            return;
        }
        ArrayList<BaseDataAuthDO> basedataAuthDetail = new ArrayList<BaseDataAuthDO>();
        HashSet<String> hasAuth = new HashSet<String>();
        for (BaseDataOption.AuthType authType : BaseDataOption.AuthType.values()) {
            if (BaseDataOption.AuthType.NONE.equals(authType)) continue;
            hasAuth.add("at" + authType.toString().toLowerCase());
        }
        List authTypeExtendName = this.baseDataAuthService.getAuthTypeExtendName();
        if (!CollectionUtils.isEmpty(authTypeExtendName)) {
            for (String authName : authTypeExtendName) {
                String auth = ("at" + authName).toLowerCase();
                hasAuth.add(auth);
            }
        }
        StringBuilder sbs = new StringBuilder();
        for (String key : successKey) {
            BaseDataAuthDO authDO = new BaseDataAuthDO();
            authDO.setBizname(userName);
            authDO.setBiztype(Integer.valueOf(1));
            authDO.setObjectcode(key);
            authDO.setDefinename(baseDataDTO.getTableName());
            authDO.setAuthtype(Integer.valueOf(1));
            for (String authKey : hasAuth) {
                authDO.put(authKey, (Object)1);
            }
            sbs.append("\u6807\u8bc6\uff1b").append(key).append(";");
            basedataAuthDetail.add(authDO);
        }
        String token = ShiroUtil.getToken();
        if (!StringUtils.hasText(token)) {
            token = UUID.randomUUID().toString();
            ShiroUtil.bindToken((String)token);
        }
        try {
            R sync = this.baseDataAuthService.updateDetail(basedataAuthDetail);
            if (sync.getCode() == 1) {
                throw new BaseDataSyncException(String.format("\u8bbe\u7f6e\u57fa\u7840\u6570\u636e'%s'\u7684\u6743\u9650\u65f6\uff0c\u53d1\u751f\u4e86\u9519\u8bef\uff1a%s", updateParam.getEntityId(), sync.getMsg()));
            }
        }
        finally {
            ShiroUtil.unbindToken((String)token);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("\u57fa\u7840\u6570\u636e\u6388\u6743\u5b8c\u6210\uff0c\u7528\u6237\uff1a{},\u57fa\u7840\u6570\u636e\uff1a{}", (Object)user.getName(), (Object)sbs);
        }
    }

    private void checkData(List<EntityDataRow> modifyRows, boolean insert) throws BaseDataSyncException {
        for (EntityDataRow modifyRow : modifyRows) {
            Set<String> keySet = modifyRow.getRowData().keySet();
            Object orgcode = modifyRow.getValue("code");
            if (this.isNullObject(orgcode) && this.checkState(insert, keySet.contains("code"))) {
                throw new BaseDataSyncException("\u57fa\u7840\u6570\u636e\u7f16\u7801\u4e0d\u80fd\u4e3a\u7a7a.");
            }
            Object name = modifyRow.getValue("name");
            if (!this.isNullObject(name) || !this.checkState(insert, keySet.contains("name"))) continue;
            throw new BaseDataSyncException("\u57fa\u7840\u6570\u636e\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a.");
        }
    }

    private List<String> executeResult(R sync, StringBuffer errorMessage) {
        ArrayList<String> successKeys = new ArrayList<String>();
        Object resultsObject = sync.get((Object)"results");
        if (resultsObject != null) {
            List results = (List)resultsObject;
            for (R r : results) {
                if (r.getCode() == 1) {
                    errorMessage.append(r.getMsg()).append("\r\n");
                    continue;
                }
                Object basedatacode = r.get((Object)"BASEDATACODE");
                if (basedatacode == null || "".equals(basedatacode)) continue;
                successKeys.add(String.valueOf(basedatacode));
            }
        }
        return successKeys;
    }

    public EntityCheckResult rowsCheck() throws BaseDataSyncException {
        return null;
    }

    private boolean checkState(boolean insert, boolean contains) {
        if (insert) {
            return true;
        }
        return contains;
    }

    private boolean isNullObject(Object orgcode) {
        return orgcode == null || "".equals(orgcode);
    }
}

