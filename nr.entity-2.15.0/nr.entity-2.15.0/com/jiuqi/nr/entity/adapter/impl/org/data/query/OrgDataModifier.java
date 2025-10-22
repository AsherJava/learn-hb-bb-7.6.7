/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.organization.domain.OrgAuthDO
 *  com.jiuqi.va.organization.service.OrgAuthService
 */
package com.jiuqi.nr.entity.adapter.impl.org.data.query;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.impl.org.OrgDataCheck;
import com.jiuqi.nr.entity.adapter.impl.org.client.OrgAdapterClient;
import com.jiuqi.nr.entity.adapter.impl.org.exception.OrgDataSyncException;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgConvertUtil;
import com.jiuqi.nr.entity.adapter.provider.IDataModifyProvider;
import com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityDataRow;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.param.IEntityDeleteParam;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import com.jiuqi.va.organization.service.OrgAuthService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class OrgDataModifier
implements IDataModifyProvider {
    protected final SystemIdentityService systemIdentityService;
    protected final OrgAdapterClient orgAdapterClient;
    protected final OrgDataCheck orgDataCheck;
    protected final OrgDataClient orgDataClient;
    protected final OrgAuthService orgAuthService;

    public OrgDataModifier(SystemIdentityService systemIdentityService, OrgAdapterClient orgAdapterClient, OrgDataCheck orgDataCheck, OrgDataClient orgDataClient, OrgAuthService orgAuthService) {
        this.systemIdentityService = systemIdentityService;
        this.orgAdapterClient = orgAdapterClient;
        this.orgDataCheck = orgDataCheck;
        this.orgDataClient = orgDataClient;
        this.orgAuthService = orgAuthService;
    }

    protected OrgDTO getParam(IEntityQueryParam param) {
        return OrgConvertUtil.paramConvert(param);
    }

    @Override
    public EntityUpdateResult insertRows(IEntityUpdateParam updateParam) throws OrgDataSyncException {
        EntityDataRow dataRow;
        EntityUpdateResult entityUpdateResult = new EntityUpdateResult();
        List<EntityDataRow> insertRows = updateParam.getModifyRows();
        List listOrder = insertRows.stream().map(EntityDataRow::getTempId).collect(Collectors.toList());
        EntityCheckResult checkResult = entityUpdateResult.getCheckResult();
        checkResult.setVersionTime(updateParam.getVersionDate());
        checkResult.setEntityId(updateParam.getEntityId());
        for (int i = 0; i < insertRows.size(); ++i) {
            EntityDataRow insertRow = insertRows.get(i);
            Object codeValue = insertRow.getValue("code");
            if (codeValue != null && !"".equals(codeValue)) {
                insertRow.setEntityKeyData(codeValue.toString());
            } else {
                insertRow.setEntityKeyData(UUID.randomUUID().toString().toUpperCase(Locale.ROOT));
            }
            entityUpdateResult.addCodeToKey(insertRow.getValue("orgcode").toString(), insertRow.getEntityKeyData());
        }
        if (CollectionUtils.isEmpty(insertRows)) {
            return entityUpdateResult;
        }
        OrgDTO orgDTO = this.getParam(updateParam);
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setVersionDate(new Date());
        List<String> insertCodes = insertRows.stream().filter(EntityDataRow::isNeedSync).map(e -> e.getValue("orgcode").toString()).distinct().collect(Collectors.toList());
        List<String> parentList = insertRows.stream().filter(EntityDataRow::isNeedSync).filter(e -> StringUtils.hasText(e.getParentId())).map(EntityDataRow::getParentId).distinct().collect(Collectors.toList());
        List<OrgDO> filterInsertRows = this.queryExistOrgByOrgCodes(orgDTO, insertCodes, false);
        List<OrgDO> findParentList = this.queryExistOrgByOrgCodes(orgDTO, parentList, false);
        Map<String, String> orgCodeToCodeMap = findParentList.stream().collect(Collectors.toMap(OrgDO::getOrgcode, OrgDO::getCode, (e1, e2) -> e2));
        Map<String, List<EntityDataRow>> orgCodeToRow = insertRows.stream().collect(Collectors.groupingBy(e -> e.getValue("orgcode").toString()));
        HashSet<String> existOrgCodes = new HashSet<String>();
        if (filterInsertRows.size() > 0) {
            for (OrgDO filterInsertRow : filterInsertRows) {
                existOrgCodes.add(filterInsertRow.getOrgcode());
                List<EntityDataRow> findDataRow = orgCodeToRow.get(filterInsertRow.getOrgcode());
                if (findDataRow == null) continue;
                Iterator<Object> iterator = findDataRow.iterator();
                while (iterator.hasNext()) {
                    String realParentId;
                    dataRow = (EntityDataRow)iterator.next();
                    if (!dataRow.isNeedSync()) continue;
                    dataRow.setEntityKeyData(filterInsertRow.getCode());
                    entityUpdateResult.addCodeToKey(dataRow.getValue("orgcode").toString(), dataRow.getEntityKeyData());
                    if (!StringUtils.hasText(dataRow.getParentId()) || (realParentId = orgCodeToCodeMap.get(dataRow.getParentId())) == null) continue;
                    dataRow.setParentId(realParentId);
                    dataRow.putRowData("PARENTCODE".toLowerCase(Locale.ROOT), realParentId);
                }
            }
        }
        insertRows.clear();
        ArrayList<EntityDataRow> tempInsert = new ArrayList<EntityDataRow>();
        orgCodeToRow.forEach((key, value) -> {
            if (!existOrgCodes.contains(key)) {
                for (EntityDataRow dataRow : value) {
                    if (!dataRow.isNeedSync()) continue;
                    tempInsert.add(dataRow);
                }
            }
        });
        Map<String, EntityDataRow> insertMap = tempInsert.stream().collect(Collectors.toMap(EntityDataRow::getTempId, e -> e, (e1, e2) -> e2));
        for (String order : listOrder) {
            dataRow = insertMap.get(order);
            if (dataRow == null) continue;
            insertRows.add(dataRow);
        }
        this.batchUpdateOrg(updateParam, true, "MD_ORG");
        insertRows.clear();
        tempInsert.clear();
        Set<String> keySet = orgCodeToRow.keySet();
        for (String key2 : keySet) {
            List<EntityDataRow> entityDataRows = orgCodeToRow.get(key2);
            tempInsert.addAll(entityDataRows);
        }
        insertMap = tempInsert.stream().collect(Collectors.toMap(EntityDataRow::getTempId, e -> e, (e1, e2) -> e2));
        for (String order : listOrder) {
            EntityDataRow dataRow2 = insertMap.get(order);
            if (dataRow2 == null) continue;
            insertRows.add(dataRow2);
        }
        this.batchUpdateOrg(updateParam, true, updateParam.getEntityId());
        return entityUpdateResult;
    }

    @Override
    public EntityUpdateResult deleteRows(IEntityDeleteParam deleteParam) throws OrgDataSyncException {
        OrgBatchOptDTO batchDTO = new OrgBatchOptDTO();
        OrgDTO orgDTO = this.getParam(deleteParam);
        batchDTO.setQueryParam(orgDTO);
        batchDTO.setHighTrustability(true);
        orgDTO.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
        List<EntityDataRow> deleteRows = deleteParam.getDeleteRows();
        ArrayList<OrgDO> datalist = new ArrayList<OrgDO>(deleteRows.size());
        HashMap<String, String> keyToCode = new HashMap<String, String>(deleteRows.size());
        for (EntityDataRow deleteRow : deleteRows) {
            Object codeValue;
            String keyData = deleteRow.getEntityKeyData();
            Map<String, Object> rowData = deleteRow.getRowData();
            if (rowData != null && (codeValue = rowData.get("ORGCODE")) != null && !"".equals(codeValue)) {
                keyToCode.put(keyData, codeValue.toString());
            }
            OrgDO orgDO = new OrgDO();
            orgDO.setId(UUID.fromString(deleteRow.getRecKey()));
            orgDO.setCode(keyData);
            orgDO.setCategoryname(deleteParam.getEntityId());
            datalist.add(orgDO);
        }
        batchDTO.setDataList(datalist);
        R sync = this.orgDataClient.batchRemove(batchDTO);
        if (sync.getCode() != 0) {
            throw new OrgDataSyncException(String.format("\u5220\u9664\u673a\u6784\u7c7b\u578b'%s'\u7684\u6570\u636e\u65f6\uff0c\u53d1\u751f\u4e86\u9519\u8bef\uff1a%s\u3002", deleteParam.getEntityId(), sync.getMsg()));
        }
        EntityUpdateResult result = new EntityUpdateResult();
        List<String> deleteResult = this.getDeleteResult(sync);
        for (String key : deleteResult) {
            String code = (String)keyToCode.get(key);
            if (StringUtils.hasText(code)) {
                result.addCodeToKey(code, key);
                continue;
            }
            result.addCodeToKey(key, key);
        }
        return result;
    }

    @Override
    public EntityUpdateResult updateRows(IEntityUpdateParam updateParam) throws OrgDataSyncException {
        EntityUpdateResult result = new EntityUpdateResult();
        List<EntityDataRow> modifyRows = updateParam.getModifyRows();
        if (CollectionUtils.isEmpty(modifyRows)) {
            return result;
        }
        for (EntityDataRow row : modifyRows) {
            Object orgCode = row.getValue("orgcode");
            if (orgCode != null && !"".equals(orgCode)) {
                result.addCodeToKey(orgCode.toString(), row.getEntityKeyData());
                continue;
            }
            result.addCodeToKey(row.getEntityKeyData(), row.getEntityKeyData());
        }
        EntityCheckResult checkResult = result.getCheckResult();
        checkResult.setVersionTime(updateParam.getVersionDate());
        checkResult.setEntityId(updateParam.getEntityId());
        this.batchUpdateOrg(updateParam, false, updateParam.getEntityId());
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setOrgAuth(OrgDTO orgDTO, List<String> orgUpdateList) throws OrgDataSyncException {
        if (CollectionUtils.isEmpty(orgUpdateList)) {
            return;
        }
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
        ArrayList<OrgAuthDO> orgAuthDetail = new ArrayList<OrgAuthDO>();
        HashSet<String> hasAuth = new HashSet<String>();
        for (OrgDataOption.AuthType authType : OrgDataOption.AuthType.values()) {
            if (OrgDataOption.AuthType.NONE.equals(authType)) continue;
            hasAuth.add("at" + authType.toString().toLowerCase());
        }
        List authTypeExtendName = this.orgAuthService.getAuthTypeExtendName();
        if (!CollectionUtils.isEmpty(authTypeExtendName)) {
            for (String authName : authTypeExtendName) {
                String auth = ("at" + authName).toLowerCase();
                hasAuth.add(auth);
            }
        }
        for (String key : orgUpdateList) {
            OrgAuthDO authDO = new OrgAuthDO();
            authDO.setBizname(userName);
            authDO.setBiztype(Integer.valueOf(1));
            authDO.setOrgname(key);
            authDO.setOrgcategory(orgDTO.getCategoryname());
            authDO.setAuthtype(Integer.valueOf(1));
            for (String authKey : hasAuth) {
                authDO.put(authKey, (Object)1);
            }
            orgAuthDetail.add(authDO);
        }
        String token = ShiroUtil.getToken();
        if (!StringUtils.hasText(token)) {
            token = UUID.randomUUID().toString();
            ShiroUtil.bindToken((String)token);
        }
        try {
            R sync = this.orgAdapterClient.updateDetail(orgAuthDetail);
            if (sync.getCode() != 0) {
                throw new OrgDataSyncException(String.format("\u8d4b\u4e88\u7ec4\u7ec7\u673a\u6784'%s'\u7684\u6743\u9650\u65f6\uff0c\u53d1\u751f\u4e86\u9519\u8bef\uff1a%s\u3002", orgDTO.getCategoryname(), sync.getMsg()));
            }
        }
        finally {
            ShiroUtil.unbindToken((String)token);
        }
    }

    private void batchUpdateOrg(IEntityUpdateParam updateParam, boolean insert, String table) throws OrgDataSyncException {
        this.executeUpdateByVersion(updateParam, table, insert);
    }

    private void executeUpdateByVersion(IEntityUpdateParam updateParam, String table, boolean insert) throws OrgDataSyncException {
        OrgBatchOptDTO batchDTO = new OrgBatchOptDTO();
        OrgDTO orgDTO = this.getParam(updateParam);
        orgDTO.setCodeScope(null);
        orgDTO.setOrgCodes(null);
        orgDTO.setCategoryname(table);
        if ("MD_ORG".equals(table) || orgDTO.getVersionDate() == null) {
            orgDTO.setVersionDate(new Date());
        }
        ArrayList<EntityDataRow> modifyRows = new ArrayList<EntityDataRow>();
        modifyRows.addAll(updateParam.getModifyRows());
        if (CollectionUtils.isEmpty(modifyRows)) {
            return;
        }
        if (insert) {
            List<String> insertCodes = modifyRows.stream().map(e -> e.getValue("orgcode").toString()).collect(Collectors.toList());
            List<OrgDO> existOrg = this.queryExistOrgByOrgCodes(orgDTO, insertCodes, true);
            for (EntityDataRow modifyRow : modifyRows) {
                Optional<OrgDO> findRow = existOrg.stream().filter(e -> e.getCode().equalsIgnoreCase(String.valueOf(modifyRow.getEntityKeyData()))).findFirst();
                if (!findRow.isPresent()) continue;
                Map<String, Object> rowData = modifyRow.getRowData();
                OrgDO recoverOrgDO = findRow.get();
                rowData.put("orgcode", recoverOrgDO.getOrgcode());
                rowData.put("recoveryflag", 0);
            }
        }
        orgDTO.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
        batchDTO.setQueryParam(orgDTO);
        List<OrgDO> orgUpdateList = this.getOrgUpdateList(modifyRows, table, insert, updateParam.isBatchUpdateModel());
        batchDTO.setDataList(orgUpdateList);
        batchDTO.setHighTrustability(updateParam.isBatchUpdateModel());
        R sync = this.orgDataClient.sync(batchDTO);
        if (sync.getCode() != 0) {
            throw new OrgDataSyncException(String.format("%s\u7ec4\u7ec7\u673a\u6784'%s'\u7684\u6570\u636e\u65f6\uff0c\u53d1\u751f\u4e86\u9519\u8bef\uff1a%s\u3002", insert ? "\u65b0\u589e" : "\u4fee\u6539", table, sync.getMsg()));
        }
        List<String> successKey = orgUpdateList.stream().map(OrgDO::getCode).collect(Collectors.toList());
        if (insert && sync.getCode() == 0) {
            this.setOrgAuth(orgDTO, successKey);
        }
    }

    private List<OrgDO> queryExistOrgByOrgCodes(OrgDTO orgDTO, List<String> insertCodes, boolean recover) {
        if (CollectionUtils.isEmpty(insertCodes)) {
            return Collections.emptyList();
        }
        OrgDTO queryDTO = new OrgDTO();
        queryDTO.setCategoryname(orgDTO.getCategoryname());
        queryDTO.setVersionDate(orgDTO.getVersionDate());
        queryDTO.setOrgOrgcodes(insertCodes);
        if (recover) {
            queryDTO.setRecoveryflag(Integer.valueOf(1));
        }
        PageVO list = this.orgDataClient.list(queryDTO);
        return list.getRows();
    }

    @Override
    public EntityCheckResult rowsCheck(IEntityUpdateParam updateParam, boolean insert) {
        EntityCheckResult checkResult = new EntityCheckResult();
        List<EntityDataRow> modifyRows = updateParam.getModifyRows();
        Date versionDate = updateParam.getVersionDate();
        String tableName = updateParam.getEntityId();
        List<CheckFailNodeInfo> checkFailNodeInfos = this.orgDataCheck.checkData(updateParam, insert);
        checkResult.setEntityId(tableName);
        checkResult.setVersionTime(versionDate);
        checkResult.setFailInfos(checkFailNodeInfos);
        return checkResult;
    }

    private List<OrgDO> getOrgUpdateList(List<EntityDataRow> updateRows, String tableName, boolean insert, boolean batchUpdateModel) {
        ArrayList<OrgDO> datalist = new ArrayList<OrgDO>(updateRows.size());
        for (EntityDataRow updateRow : updateRows) {
            Object parentValue;
            Set<String> keys;
            Object orgcode;
            OrgDO orgDO = new OrgDO();
            orgDO.putAll(updateRow.getRowData());
            String recKey = updateRow.getRecKey();
            if (StringUtils.hasText(recKey)) {
                orgDO.setId(UUID.fromString(recKey));
            }
            if (StringUtils.hasText(updateRow.getEntityKeyData())) {
                orgDO.setCode(updateRow.getEntityKeyData());
            }
            if ((orgcode = updateRow.getValue("orgcode")) != null && !"".equals(orgcode)) {
                orgDO.setOrgcode(String.valueOf(orgcode));
            }
            if (StringUtils.hasText(updateRow.getTitle())) {
                orgDO.setName(updateRow.getTitle());
            }
            if (StringUtils.hasText(updateRow.getParentId())) {
                orgDO.setParentcode(updateRow.getParentId());
            }
            orgDO.setCategoryname(tableName);
            if (!StringUtils.hasText(orgDO.getShortname()) && insert) {
                String shortName = updateRow.getTitle();
                if (shortName.length() >= 30) {
                    shortName = shortName.substring(0, 25).concat("...");
                }
                orgDO.setShortname(shortName);
            }
            orgDO.put("_ORG_EXTEND_CHECKED", (Object)true);
            if (batchUpdateModel) {
                orgDO.put("_ORG_EXTEND_BATCH_ADD", (Object)true);
            }
            if ((keys = updateRow.getRowData().keySet()).contains("PARENTCODE".toLowerCase()) && ((parentValue = updateRow.getValue("PARENTCODE".toLowerCase())) == null || "".equals(parentValue))) {
                orgDO.setParentcode("-");
            }
            datalist.add(orgDO);
        }
        return datalist;
    }

    private List<String> getDeleteResult(R deleteResult) {
        ArrayList<String> deleteKeys = new ArrayList<String>();
        Object resultObject = deleteResult.get((Object)"results");
        if (resultObject != null) {
            List results = (List)resultObject;
            for (R result : results) {
                if (result.getCode() != 0) {
                    System.out.println(result.getMsg());
                    continue;
                }
                Object key = result.get((Object)"key");
                if (key == null || "".equals(key)) continue;
                deleteKeys.add(key.toString());
            }
        }
        return deleteKeys;
    }
}

