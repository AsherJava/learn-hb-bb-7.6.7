/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nvwa.glue.client.basedata.datainto.domain.VaDaPushResult
 *  com.jiuqi.nvwa.glue.client.common.SyncContext
 *  com.jiuqi.nvwa.glue.client.organization.datainto.IOrganizationProductService
 *  com.jiuqi.nvwa.glue.client.organization.datainto.util.OrgTypeUtil
 *  com.jiuqi.nvwa.glue.data.impl.GlueOrganizationDTO
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.common.datasync.executor.impl;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.datasync.converter.NvwaOrganizationConverter;
import com.jiuqi.common.datasync.dto.CommonDataSyncSettingItemDTO;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutor;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutorContext;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaOrganizationDTO;
import com.jiuqi.common.datasync.service.CommonDataSyncService;
import com.jiuqi.nvwa.glue.client.basedata.datainto.domain.VaDaPushResult;
import com.jiuqi.nvwa.glue.client.common.SyncContext;
import com.jiuqi.nvwa.glue.client.organization.datainto.IOrganizationProductService;
import com.jiuqi.nvwa.glue.client.organization.datainto.util.OrgTypeUtil;
import com.jiuqi.nvwa.glue.data.impl.GlueOrganizationDTO;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
public class NvwaOrgDataSyncExecutor
implements CommonDataSyncExecutor {
    private final Logger LOGGER = LoggerFactory.getLogger(NvwaOrgDataSyncExecutor.class);
    @Autowired
    private IOrganizationProductService productService;
    @Autowired
    private CommonDataSyncService dataSyncService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;

    @Override
    public String title() {
        return "\u5973\u5a32\u5e73\u53f0\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u540c\u6b65";
    }

    @Override
    public String type() {
        return "NvwaOrg";
    }

    @Override
    public String description() {
        return "<p>\u63a5\u53e3\u53c2\u6570\u793a\u4f8b\u63cf\u8ff0: \u4e00\u672c\u8d26\u670d\u52a1(\u8c03\u7528\u65b9\u670d\u52a1\u540d\uff1adatacenter-servcie)\u62bd\u53d6\u5408\u5e76\u62a5\u8868\u670d\u52a1\uff08\u88ab\u8c03\u7528\u65b9\u670d\u52a1url\uff1ahttp://10.2.33.35:8188\uff09\u7684MD_ORG\u7ec4\u7ec7\u673a\u6784\u6570\u636e</p><p>\u8c03\u7528\u65b9\u670d\u52a1\u540d\uff1adatacenter-service</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u5730\u5740\uff1ahttp://10.2.33.35:8188</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u8ba4\u8bc1\u7528\u6237\uff1aadmin</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u8ba4\u8bc1\u5bc6\u7801\uff1agcP@ssw0rd</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u53c2\u6570\uff1a<p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u53c2\u6570\u63cf\u8ff0\uff1a\u672c\u793a\u4f8b\u4e3a\u4e00\u672c\u8d26\u670d\u52a1\u62bd\u53d6\u5408\u5e76\u670d\u52a1\u7684MD_ORG\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u3002</p>";
    }

    @Override
    public void execute(CommonDataSyncExecutorContext context) throws Exception {
        CommonDataSyncSettingItemDTO itemDTO = context.getDataSyncSettingItemDTO();
        List<String> orgTypes = itemDTO.getOrgTypes();
        if (CollectionUtils.isEmpty(orgTypes)) {
            this.dataSync(itemDTO, "MD_ORG");
            return;
        }
        Iterator<String> iterator = orgTypes.iterator();
        while (iterator.hasNext()) {
            this.dataSync(itemDTO, iterator.next());
        }
    }

    public void dataSync(CommonDataSyncSettingItemDTO itemDTO, String orgType) throws Exception {
        List<DataSyncNvwaOrganizationDTO> dataSyncDatas = this.fetchDataSyncDatas(itemDTO, orgType);
        this.saveDataSyncDatas(orgType, dataSyncDatas);
    }

    public List<DataSyncNvwaOrganizationDTO> fetchDataSyncDatas(CommonDataSyncSettingItemDTO itemDTO, String orgType) {
        if (ObjectUtils.isEmpty(itemDTO.getUrl())) {
            return null;
        }
        List nvwaOrgnizationDTOs = Collections.emptyList();
        try {
            NvwaLoginUserDTO userDTO = this.dataSyncService.initNvwaFeignClientTokenEnv(itemDTO.getUrl(), itemDTO.getUsername(), itemDTO.getPassword());
            BusinessResponseEntity<List<DataSyncNvwaOrganizationDTO>> nvwaOrgnizationDTOsResponseEntity = this.dataSyncService.getNvwaFeignClient().getNvwaOrgnizationDTOs(new URI(itemDTO.getUrl()), userDTO, orgType);
            if (nvwaOrgnizationDTOsResponseEntity.getData() != null) {
                nvwaOrgnizationDTOs = (List)nvwaOrgnizationDTOsResponseEntity.getData();
            }
        }
        catch (Exception e) {
            this.LOGGER.error(e.getMessage(), e);
        }
        return nvwaOrgnizationDTOs;
    }

    @Transactional(rollbackFor={Exception.class})
    public void saveDataSyncDatas(String categoryname, List<DataSyncNvwaOrganizationDTO> nvwaOrganizationDTOS) throws Exception {
        if (CollectionUtils.isEmpty(nvwaOrganizationDTOS)) {
            return;
        }
        NvwaOrgDataSyncExecutor syncExecutor = (NvwaOrgDataSyncExecutor)SpringContextUtils.getBean(NvwaOrgDataSyncExecutor.class);
        this.LOGGER.info("\u7ec4\u7ec7\u673a\u6784\u5f00\u59cb\u62bd\u53d6\uff0c\u603b\u6570\uff1a" + nvwaOrganizationDTOS.size());
        OrgCategoryDO ocd = OrgTypeUtil.getOrgCategoryDO((String)categoryname);
        if (ocd != null) {
            try {
                VaDaPushResult result = new VaDaPushResult(new SyncContext(), ocd.getName());
                AtomicInteger currentIndex = new AtomicInteger(0);
                nvwaOrganizationDTOS.stream().forEach(org -> {
                    try {
                        currentIndex.addAndGet(1);
                        syncExecutor.insertOrUpdateOrg(ocd, result, currentIndex, (DataSyncNvwaOrganizationDTO)org);
                    }
                    catch (Exception e) {
                        this.LOGGER.error(e.getMessage(), e);
                    }
                });
                this.LOGGER.info("\u7ec4\u7ec7\u673a\u6784\u62bd\u53d6\u5b8c\u6210,\u5177\u4f53\u88c5\u5165\u7ed3\u679c\u4e3a\uff1a" + result.toString());
            }
            catch (Exception e) {
                this.LOGGER.error("\u62bd\u53d6\u7ec4\u7ec7\u673a\u6784\u5f02\u5e38\u7ed3\u675f,\u9519\u8bef\u4fe1\u606f\u4e3a" + e.getMessage() + "\u3002", e);
            }
        }
        this.LOGGER.info("\u7ec4\u7ec7\u673a\u6784\u7ed3\u675f\u62bd\u53d6\u3002");
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void insertOrUpdateOrg(OrgCategoryDO ocd, VaDaPushResult result, AtomicInteger currentIndex, DataSyncNvwaOrganizationDTO nvwaOrganizationDTO) throws Exception {
        DataSyncNvwaOrganizationDTO existNvwaOrganizationDTO = this.get(ocd.getName(), nvwaOrganizationDTO.getCode());
        if (existNvwaOrganizationDTO != null) {
            nvwaOrganizationDTO.setId(existNvwaOrganizationDTO.getId());
        }
        this.LOGGER.info("\u62bd\u53d6\u7b2c" + currentIndex + "\u4e2a\u7ec4\u7ec7\u673a\u6784\u505a\u66f4\u65b0,code:" + nvwaOrganizationDTO.getCode());
        GlueOrganizationDTO glueOrganization = NvwaOrganizationConverter.convertGlueDTO(nvwaOrganizationDTO);
        result.getContext().setOrgVersionDate(glueOrganization.getValidTime());
        this.productService.saveResult(result, Arrays.asList(glueOrganization));
    }

    private DataSyncNvwaOrganizationDTO get(String type, String orgCode) {
        if (ObjectUtils.isEmpty(orgCode)) {
            return null;
        }
        OrgDTO query = new OrgDTO();
        query.setAuthType(OrgDataOption.AuthType.NONE);
        query.setPagination(Boolean.valueOf(false));
        query.setCategoryname(type);
        query.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        ArrayList<String> orgCodes = new ArrayList<String>(1);
        orgCodes.add(orgCode);
        query.setOrgCodes(orgCodes);
        PageVO orgs = this.orgDataClient.list(query);
        if (orgs.getTotal() == 0) {
            return null;
        }
        List<String> zbNames = this.getOrganizationZBNames(type);
        DataSyncNvwaOrganizationDTO glueOrganizationDTO = NvwaOrganizationConverter.convertToDataSyncDTO((OrgDO)orgs.getRows().get(0), zbNames);
        return glueOrganizationDTO;
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
}

