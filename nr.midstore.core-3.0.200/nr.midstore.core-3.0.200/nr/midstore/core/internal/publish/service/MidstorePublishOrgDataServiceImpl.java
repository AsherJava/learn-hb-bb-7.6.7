/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.TableType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package nr.midstore.core.internal.publish.service;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.enums.TableType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.common.ExchangeModeType;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreOrgDataFieldDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreOrgDataFieldService;
import nr.midstore.core.definition.service.IMidstoreOrgDataService;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.internal.publish.service.MidstoreSDKLib;
import nr.midstore.core.param.service.IMidstoreMappingService;
import nr.midstore.core.publish.service.IMidstorePublishOrgDataService;
import nr.midstore.core.util.IMidstoreDimensionService;
import nr.midstore.core.util.IMidstoreReadWriteService;
import nr.midstore.core.util.IMidstoreResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstorePublishOrgDataServiceImpl
implements IMidstorePublishOrgDataService {
    private static final Logger logger = LoggerFactory.getLogger(MidstorePublishOrgDataServiceImpl.class);
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;
    @Autowired
    private IMidstoreOrgDataFieldService orgDataFieldService;
    @Autowired
    private IMidstoreOrgDataService orgDataService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private IMidstoreMappingService midstoreMappingService;
    @Autowired
    private IMidstoreDimensionService midstoreDimService;
    @Autowired
    private IMidstoreReadWriteService readWriteService;
    @Autowired
    private IMidstoreResultService resultService;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void publishOrgDataFields(MidstoreContext context, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
        if (StringUtils.isEmpty((String)midstoreScheme.getTaskKey())) {
            throw new MidstoreException("\u672a\u5b9a\u4e49\u4efb\u52a1\u4fe1\u606f\uff0c\u53d1\u5e03\u7ec4\u7ec7\u673a\u6784\u5931\u8d25");
        }
        MidstoreSchemeInfoDTO schemeInfo = this.schemeInfoSevice.getBySchemeKey(midstoreScheme.getKey());
        MidstoreOrgDataFieldDTO queryParam = new MidstoreOrgDataFieldDTO();
        queryParam.setSchemeKey(midstoreScheme.getKey());
        List<MidstoreOrgDataFieldDTO> orgFields = this.orgDataFieldService.list(queryParam);
        TaskDefine taskDefine = this.viewController.queryTaskDefine(midstoreScheme.getTaskKey());
        String orgCode = EntityUtils.getId((String)taskDefine.getDw());
        OrgCategoryDO orgDefine = this.queryOrgDatadefine(orgCode);
        if (orgDefine == null) {
            throw new MidstoreException("\u4efb\u52a1\u5173\u8054\u7684\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u4e0d\u5b58\u5728\uff1a" + orgCode);
        }
        List orgZbList = orgDefine.getZbs();
        ArrayList<DEFieldInfo> deFields = new ArrayList<DEFieldInfo>();
        DEFieldInfo deFieldInfo1 = new DEFieldInfo(UUID.randomUUID().toString(), "MDCODE", "\u4ee3\u7801", DEDataType.STRING, 60, 0);
        DEFieldInfo deFieldInfo2 = new DEFieldInfo(UUID.randomUUID().toString(), "ORGCODE", "ORG\u4ee3\u7801", DEDataType.STRING, 60, 0);
        deFields.add(deFieldInfo1);
        deFields.add(deFieldInfo2);
        if (schemeInfo.isAllOrgField()) {
            DEFieldInfo deFieldInfo3 = new DEFieldInfo(UUID.randomUUID().toString(), "SHORTNAME", "\u7b80\u79f0", DEDataType.STRING, 100, 0);
            deFields.add(deFieldInfo3);
            for (ZB orgZb : orgZbList) {
                int precisoin = 0;
                if (orgZb.getPrecision() != null) {
                    precisoin = orgZb.getPrecision();
                }
                if (precisoin <= 0) {
                    precisoin = 22;
                }
                int decima = 0;
                if (orgZb.getDecimal() != null) {
                    decima = orgZb.getDecimal();
                }
                DEFieldInfo deFieldInfo = new DEFieldInfo(UUID.randomUUID().toString(), orgZb.getName(), orgZb.getTitle(), MidstoreSDKLib.getDEDataTypeByOrg(orgZb.getDatatype()), precisoin, decima);
                deFields.add(deFieldInfo);
            }
        } else {
            for (MidstoreOrgDataFieldDTO field : orgFields) {
                ZB orgZb = orgDefine.getZbByName(field.getCode());
                if (orgZb != null) {
                    int precisoin = 0;
                    if (orgZb.getPrecision() != null) {
                        precisoin = orgZb.getPrecision();
                    }
                    if (precisoin <= 0) {
                        precisoin = 22;
                    }
                    int decima = 0;
                    if (orgZb.getDecimal() != null) {
                        decima = orgZb.getDecimal();
                    }
                    DEFieldInfo deFieldInfo = new DEFieldInfo(UUID.randomUUID().toString(), orgZb.getName(), orgZb.getTitle(), MidstoreSDKLib.getDEDataTypeByOrg(orgZb.getDatatype()), precisoin, decima);
                    deFields.add(deFieldInfo);
                    continue;
                }
                if (!"SHORTNAME".equalsIgnoreCase(field.getCode())) continue;
                DEFieldInfo deFieldInfo = new DEFieldInfo(UUID.randomUUID().toString(), field.getCode(), field.getTitle(), DEDataType.STRING, 100, 0);
                deFields.add(deFieldInfo);
            }
        }
        try {
            DETableInfo deTableInfo = dataExchangeTask.getTableInfoByName("ORG_OTHERDATA");
            if (midstoreScheme.getExchangeMode() == ExchangeModeType.EXCHANGE_POST || schemeInfo.isUseUpdateOrg()) {
                if (deTableInfo == null) {
                    deTableInfo = new DETableInfo(UUID.randomUUID().toString(), "ORG_OTHERDATA", "\u7ec4\u7ec7\u673a\u6784\u6269\u5c55\u8868", TableType.MASTER);
                }
                DETableModel deTableModel = dataExchangeTask.createMasterTable(deTableInfo, deFields);
                logger.info("\u4e2d\u95f4\u5e93\u521b\u5efa\u4e3b\u6570\u8868\uff1a" + deTableModel.getTableInfo().getName());
                return;
            }
            if (deTableInfo == null) return;
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
    }

    private OrgCategoryDO queryOrgDatadefine(String orgName) {
        OrgCategoryDO param = new OrgCategoryDO();
        param.setName(orgName);
        if (NpContextHolder.getContext() != null) {
            param.setTenantName(NpContextHolder.getContext().getTenant());
        }
        PageVO orgDefines = this.orgCategoryClient.list(param);
        OrgCategoryDO orgDefine = null;
        if (orgDefines != null && orgDefines.getRows().size() > 0) {
            orgDefine = (OrgCategoryDO)orgDefines.getRows().get(0);
        }
        return orgDefine;
    }
}

