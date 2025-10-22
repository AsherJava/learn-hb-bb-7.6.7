/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO
 *  com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO
 *  com.jiuqi.gcreport.billextract.client.intf.IBillExtractSchemeConfigService
 *  com.jiuqi.va.bizmeta.controller.MetaGroupController
 *  com.jiuqi.va.bizmeta.controller.MetaInfoController
 *  com.jiuqi.va.bizmeta.domain.dimension.MetaGroupDim
 *  com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupVO
 *  com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO
 *  com.jiuqi.va.bizmeta.domain.metamodel.MetaModelDTO
 *  com.jiuqi.va.bizmeta.service.impl.MetaInfoService
 *  com.jiuqi.va.domain.bill.BillDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  com.jiuqi.va.domain.metainfo.MetaInfoVO
 *  com.jiuqi.va.feign.client.BillClient
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.gcreport.billextract.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO;
import com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO;
import com.jiuqi.gcreport.billextract.client.intf.IBillExtractSchemeConfigService;
import com.jiuqi.gcreport.billextract.impl.dao.BillExtractSettingDao;
import com.jiuqi.gcreport.billextract.impl.service.BillExtractSettingService;
import com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil;
import com.jiuqi.va.bizmeta.controller.MetaGroupController;
import com.jiuqi.va.bizmeta.controller.MetaInfoController;
import com.jiuqi.va.bizmeta.domain.dimension.MetaGroupDim;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupVO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.domain.metamodel.MetaModelDTO;
import com.jiuqi.va.bizmeta.service.impl.MetaInfoService;
import com.jiuqi.va.domain.bill.BillDataDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import com.jiuqi.va.domain.metainfo.MetaInfoVO;
import com.jiuqi.va.feign.client.BillClient;
import com.jiuqi.va.feign.client.DataModelClient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillExtractSettingServiceImpl
implements BillExtractSettingService {
    @Autowired
    private MetaGroupController metaGroupController;
    @Autowired
    private MetaInfoController metaInfoController;
    @Autowired
    private MetaInfoService metaInfoService;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private BillClient billClient;
    @Autowired
    private BillExtractSettingDao extractSettingDao;
    @Autowired(required=false)
    private IBillExtractSchemeConfigService schemeConfigService;

    @Override
    public List<MetaInfoDim> listMetaInfo(String billMetaType) {
        Assert.isNotEmpty((String)billMetaType);
        MetaModelDTO metaModelCondi = new MetaModelDTO();
        MetaGroupVO metaGroupVo = this.metaGroupController.getAllGroup(metaModelCondi);
        List groups = metaGroupVo.getGroups();
        HashSet<String> moduleSet = new HashSet<String>();
        for (MetaGroupDim metaGroup : groups) {
            moduleSet.add(metaGroup.getModuleName());
        }
        MetaInfoPageDTO metaInfoCondi = new MetaInfoPageDTO();
        ArrayList billMetaInfoList = CollectionUtils.newArrayList();
        for (String moduleName : moduleSet) {
            metaInfoCondi.setModule(moduleName);
            metaInfoCondi.setMetaType(billMetaType);
            MetaInfoVO meaInfoVo = this.metaInfoController.getAllMetaInfos(metaInfoCondi);
            billMetaInfoList.addAll(meaInfoVo.getMetaInfos());
        }
        return billMetaInfoList;
    }

    @Override
    public MetaInfoDTO getMetaInfoByUniqueCode(String uniqueCode) {
        Assert.isNotEmpty((String)uniqueCode);
        MetaInfoDTO info = this.metaInfoService.getMetaInfoByUniqueCode(uniqueCode);
        if (info == null) {
            throw new BusinessRuntimeException(String.format("\u6839\u636e\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u6570\u636e\u9879\uff0c\u8bf7\u68c0\u67e5\u5143\u6570\u636e\u662f\u5426\u88ab\u4fee\u6539\u6216\u5220\u9664", uniqueCode));
        }
        return info;
    }

    @Override
    public String getMasterTableName(String uniqueCode) {
        Assert.isNotEmpty((String)uniqueCode);
        BillDataDTO params = new BillDataDTO();
        params.setDefineCode(uniqueCode);
        R tableR = this.billClient.getMasterTableName(params);
        String masterTableName = (String)tableR.get((Object)"data");
        if (StringUtils.isEmpty((String)masterTableName)) {
            throw new BusinessRuntimeException(String.format("\u6839\u636e\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5355\u636e\u4e3b\u8868\uff0c\u8bf7\u68c0\u67e5\u5355\u636e\u5b9a\u4e49\u914d\u7f6e\u662f\u5426\u6b63\u786e", uniqueCode));
        }
        Assert.isNotEmpty((String)masterTableName);
        return masterTableName;
    }

    @Override
    public DataModelDO getDataModelByName(String dataModelName) {
        Assert.isNotEmpty((String)dataModelName);
        DataModelDTO condi = new DataModelDTO();
        condi.setName(dataModelName);
        DataModelDO dataModel = this.dataModelClient.get(condi);
        if (dataModel == null) {
            throw new BusinessRuntimeException(String.format("\u6839\u636e\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u6570\u636e\u5efa\u6a21\uff0c\u8bf7\u68c0\u67e5\u6570\u636e\u5efa\u6a21\u662f\u5426\u88ab\u4fee\u6539\u6216\u5220\u9664", dataModelName));
        }
        return dataModel;
    }

    @Override
    public DataModelColumn getDataModelColumn(String dataModelName, String columnName) {
        Assert.isNotEmpty((String)dataModelName);
        Assert.isNotEmpty((String)columnName);
        DataModelDO dataModelDO = this.getDataModelByName(dataModelName);
        return dataModelDO.getColumns().stream().filter(item -> columnName.equals(item.getColumnName())).findFirst().orElseThrow(() -> new BusinessRuntimeException(String.format("\u6839\u636e\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u6570\u636e\u5efa\u6a21\u3010%2$s\u3011\u4e2d\u7684\u5217\uff0c\u8bf7\u68c0\u67e5\u6570\u636e\u5efa\u6a21\u662f\u5426\u88ab\u4fee\u6539\u6216\u5220\u9664", columnName, dataModelDO.getTitle())));
    }

    @Override
    public String getOrgType(String defineName) {
        Assert.isNotEmpty((String)defineName);
        return BillExtractUtil.queryOrgTypeByColumn(this.getDataModelColumn(this.getMasterTableName(defineName), "UNITCODE"));
    }

    @Override
    public List<Map<String, Object>> listBills(String defineName, BillExtractLisDTO qeuryCondi) {
        Assert.isNotEmpty((String)defineName);
        Assert.isNotNull((Object)qeuryCondi);
        Assert.isNotEmpty((String)qeuryCondi.getUnitCode());
        Assert.isNotEmpty((String)qeuryCondi.getStartDate());
        Assert.isNotEmpty((String)qeuryCondi.getEndDate());
        return this.extractSettingDao.selectBills(this.getMasterTableName(defineName), qeuryCondi);
    }

    @Override
    public Map<String, Object> getBill(String defineName, String billCode) {
        Assert.isNotEmpty((String)defineName);
        Assert.isNotEmpty((String)billCode);
        Map<String, Object> billData = this.extractSettingDao.selectBill(this.getMasterTableName(defineName), billCode);
        if (billData == null || billData.isEmpty()) {
            throw new BusinessRuntimeException(String.format("\u5355\u636e\u5b9a\u4e49\u3010%1$s\u3011\u6839\u636e\u5355\u636e\u7f16\u53f7\u3010%2$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u6570\u636e\uff0c\u8bf7\u68c0\u67e5\u5355\u636e\u662f\u5426\u88ab\u4fee\u6539\u6216\u5220\u9664", defineName, billCode));
        }
        return billData;
    }

    @Override
    public BillSchemeConfigDTO getSchemeByOrgId(String defineName, String unitCode) {
        return this.schemeConfigService.getSchemeByOrgId(defineName, unitCode);
    }
}

