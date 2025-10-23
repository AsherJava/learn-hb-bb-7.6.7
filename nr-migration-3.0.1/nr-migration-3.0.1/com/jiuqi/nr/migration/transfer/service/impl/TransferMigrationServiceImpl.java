/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  com.jiuqi.va.organization.dao.VaOrgVersionDao
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.migration.transfer.service.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.migration.transfer.service.ITransferMigrationService;
import com.jiuqi.nr.migration.transfer.util.TransferMigrationUtilts;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.OrgVersionClient;
import com.jiuqi.va.organization.dao.VaOrgVersionDao;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class TransferMigrationServiceImpl
implements ITransferMigrationService {
    private static final Logger logger = LoggerFactory.getLogger(TransferMigrationServiceImpl.class);
    @Autowired
    private OrgVersionClient orgVerService;
    @Autowired
    private VaOrgVersionDao orgVersionDao;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.NOT_SUPPORTED)
    public R importMDORG(List<OrgDO> orgDOs) {
        OrgDTO queryParam = new OrgDTO();
        queryParam.setCategoryname("MD_ORG");
        queryParam.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
        String tenantName = null;
        if (NpContextHolder.getContext() != null) {
            tenantName = NpContextHolder.getContext().getTenant();
        }
        queryParam.setVersionDate(new Date());
        OrgBatchOptDTO orgBatchOptDTO = new OrgBatchOptDTO();
        orgBatchOptDTO.setHighTrustability(true);
        orgBatchOptDTO.setTenantName(tenantName);
        orgBatchOptDTO.setDataList(orgDOs);
        orgBatchOptDTO.setQueryParam(queryParam);
        return this.orgDataClient.sync(orgBatchOptDTO);
    }

    @Override
    public R importOrgVersion(String categoryname, List<OrgVersionDO> orgVersions) {
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(categoryname);
        PageVO list = this.orgVerService.list(param);
        if (list != null && list.getTotal() > 0) {
            list.getRows().forEach(v -> this.orgVersionDao.delete(v));
        }
        orgVersions.forEach(orgVersionDO -> this.orgVersionDao.insertSelective(orgVersionDO));
        OrgVersionDO orgVersionDO2 = new OrgVersionDO();
        orgVersionDO2.setCategoryname(categoryname);
        return this.orgVerService.syncCache(orgVersionDO2);
    }

    @Override
    public R importOrgData(String categoryname, Map<String, List<OrgDO>> versionAndDataList) throws Exception {
        Set<String> versionSet = versionAndDataList.keySet();
        List versionSortedList = versionSet.stream().sorted().collect(Collectors.toList());
        for (String versionValid : versionSortedList) {
            List<OrgDO> orgList = versionAndDataList.get(versionValid);
            Date validTime = TransferMigrationUtilts.StringToDate(versionValid);
            this.saveResult(orgList, categoryname, validTime);
        }
        return R.ok();
    }

    @Override
    public R splitOrgVersion(String categoryname, String versionValid) throws Exception {
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(categoryname);
        PageVO list = this.orgVerService.list(param);
        OrgVersionDO nearOrgVersionDO = null;
        Date validTime = TransferMigrationUtilts.StringToDate(versionValid);
        if (list != null && list.getTotal() > 0) {
            for (int i = 0; i < list.getRows().size(); ++i) {
                OrgVersionDO orgVersionDO = (OrgVersionDO)list.getRows().get(i);
                if (validTime.compareTo(orgVersionDO.getValidtime()) < 0 || validTime.compareTo(orgVersionDO.getInvalidtime()) >= 0) continue;
                nearOrgVersionDO = orgVersionDO;
                break;
            }
        }
        if (nearOrgVersionDO == null) {
            return R.error((String)"\u7248\u672c\u62c6\u5206\u5f02\u5e38\uff1a\u6ca1\u6709\u627e\u5230\u9700\u8981\u62c6\u5206\u7684\u7248\u672c\u3002");
        }
        if (validTime.compareTo(nearOrgVersionDO.getValidtime()) != 0) {
            nearOrgVersionDO.setValidtime(validTime);
            nearOrgVersionDO.setTitle(TransferMigrationUtilts.getOrgVersionTitle(validTime));
            return this.orgVerService.split(nearOrgVersionDO);
        }
        return R.ok();
    }

    @Override
    public R updateOrgData(String categoryname, Map<String, List<OrgDO>> versionAndDataList) throws Exception {
        TransferMigrationUtilts.handleRequired(categoryname, versionAndDataList);
        Set<String> versionSet = versionAndDataList.keySet();
        List versionSortedList = versionSet.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        for (String versionValid : versionSortedList) {
            R r = this.splitOrgVersion(categoryname, versionValid);
            if (r.getCode() != 0) continue;
            List<OrgDO> orgList = versionAndDataList.get(versionValid);
            this.updateOrgData(categoryname, TransferMigrationUtilts.StringToLong(versionValid), orgList);
        }
        return R.ok();
    }

    public R updateOrgData(String categoryname, long versionValid, List<OrgDO> orgList) throws Exception {
        Date validTime = new Date(versionValid);
        this.saveResult(orgList, categoryname, validTime);
        return R.ok();
    }

    public boolean saveResult(List<OrgDO> datas, String categoryname, Date validTime) throws Exception {
        if (CollectionUtils.isEmpty(datas)) {
            return true;
        }
        this.batchSync(categoryname, datas, validTime);
        return true;
    }

    private void batchSync(String categoryname, List<OrgDO> datas, Date validTime) throws Exception {
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }
        String tenantName = null;
        if (NpContextHolder.getContext() != null) {
            tenantName = NpContextHolder.getContext().getTenant();
        }
        OrgDTO queryParam = new OrgDTO();
        queryParam.setCategoryname(categoryname);
        queryParam.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
        queryParam.setVersionDate(validTime);
        OrgBatchOptDTO orgBatchOptDTO = new OrgBatchOptDTO();
        orgBatchOptDTO.setHighTrustability(true);
        orgBatchOptDTO.setTenantName(tenantName);
        orgBatchOptDTO.setDataList(datas);
        orgBatchOptDTO.setQueryParam(queryParam);
        R r = this.orgDataClient.sync(orgBatchOptDTO);
    }
}

