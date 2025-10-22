/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billextract.client.BillExtractSettingClient
 *  com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO
 *  com.jiuqi.va.bill.impl.BillMetaType
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 *  org.springframework.transaction.support.TransactionTemplate
 */
package com.jiuqi.gcreport.formulaschemeconfig.transfermodule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billextract.client.BillExtractSettingClient;
import com.jiuqi.gcreport.formulaschemeconfig.dao.BillFormulaSchemeConfigDao;
import com.jiuqi.gcreport.formulaschemeconfig.service.BillFormulaSchemeConfigService;
import com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO;
import com.jiuqi.va.bill.impl.BillMetaType;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class BillFormulaSchemeConfigTransferModule
extends VaParamTransferModuleIntf {
    private static final Logger LOGGER = LoggerFactory.getLogger(BillFormulaSchemeConfigTransferModule.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private BillFormulaSchemeConfigService billFormulaSchemeConfigService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BillExtractSettingClient extractSettingClient;
    @Autowired
    private BillFormulaSchemeConfigDao billFormulaSchemeConfigDao;
    private static final String MODULE_NAME_FORMULASCHEMECONFIG = "MODULE_BILL_FORMULASCHEMECONFIG";
    private static final String MODULE_ID_FORMULASCHEMECONFIG = "MODULE_ID_FORMULASCHEMECONFIG";
    private static final String MODULE_TITLE_FORMULASCHEMECONFIG = "\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848";
    private static final String CATEGORY_NAME_BILL_FORMULASCHEMECONFIG = "CATEGORY_BILL_FORMULASCHEMECONFIG";
    private static final String CATEGORY_TITLE_BILL_FORMULASCHEMECONFIG = "\u5355\u636e";
    private static final String CATEGORY_NAME_BILLLIST_FORMULASCHEMECONFIG = "CATEGORY_BILLLIST_FORMULASCHEMECONFIG";
    private static final String CATEGORY_TITLE_BILLLIST_FORMULASCHEMECONFIG = "\u5355\u636e\u5217\u8868";

    public String getModuleId() {
        return MODULE_ID_FORMULASCHEMECONFIG;
    }

    public String getName() {
        return MODULE_NAME_FORMULASCHEMECONFIG;
    }

    public String getTitle() {
        return MODULE_TITLE_FORMULASCHEMECONFIG;
    }

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categories = new ArrayList<VaParamTransferCategory>();
        VaParamTransferCategory billCategory = new VaParamTransferCategory();
        VaParamTransferCategory billListcategory = new VaParamTransferCategory();
        billCategory.setName(CATEGORY_NAME_BILL_FORMULASCHEMECONFIG);
        billCategory.setTitle(CATEGORY_TITLE_BILL_FORMULASCHEMECONFIG);
        billListcategory.setName(CATEGORY_NAME_BILLLIST_FORMULASCHEMECONFIG);
        billListcategory.setTitle(CATEGORY_TITLE_BILLLIST_FORMULASCHEMECONFIG);
        billCategory.setSupportExport(true);
        billCategory.setSupportExportData(false);
        billListcategory.setSupportExport(true);
        billListcategory.setSupportExportData(false);
        categories.add(billCategory);
        categories.add(billListcategory);
        return categories;
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String category, String nodeId) {
        if (CATEGORY_NAME_BILL_FORMULASCHEMECONFIG.equals(category) && StringUtils.isEmpty((String)nodeId)) {
            return this.listBySettingType("BILL");
        }
        if (CATEGORY_NAME_BILLLIST_FORMULASCHEMECONFIG.equals(category) && StringUtils.isEmpty((String)nodeId)) {
            return this.listBySettingType("BILLLIST");
        }
        return Collections.emptyList();
    }

    private List<VaParamTransferBusinessNode> listBySettingType(String billSettingType) {
        ArrayList<VaParamTransferBusinessNode> nodes = new ArrayList<VaParamTransferBusinessNode>();
        Map<String, String> billNameMap = this.queryBillMetaInfo().stream().collect(Collectors.toMap(MetaInfoDim::getUniqueCode, MetaInfoDim::getTitle, (k1, k2) -> k2));
        List<String> billList = this.billFormulaSchemeConfigDao.listByBillSettingType(billSettingType);
        if (CollectionUtils.isEmpty(billList)) {
            return CollectionUtils.newArrayList();
        }
        for (String bill : billList) {
            VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
            node.setId(bill);
            node.setTitle(StringUtils.isEmpty((String)billNameMap.get(bill)) ? bill : billNameMap.get(bill));
            nodes.add(node);
        }
        return nodes;
    }

    private List<MetaInfoDim> queryBillMetaInfo() {
        List billMetaInfoList = (List)FormulaSchemeConfigUtils.parseResponse((BusinessResponseEntity)this.extractSettingClient.listMetaInfo(new BillMetaType().getName()));
        return billMetaInfoList == null ? CollectionUtils.newArrayList() : billMetaInfoList;
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        if (!StringUtils.isEmpty((String)nodeId)) {
            VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
            Map<String, String> billNameMap = this.queryBillMetaInfo().stream().collect(Collectors.toMap(MetaInfoDim::getUniqueCode, MetaInfoDim::getTitle, (k1, k2) -> k2));
            node.setTypeTitle(MODULE_TITLE_FORMULASCHEMECONFIG);
            node.setName(nodeId);
            node.setTitle(StringUtils.isEmpty((String)billNameMap.get(nodeId)) ? nodeId : billNameMap.get(nodeId));
            node.setType(Level.BILL.name());
            node.setId(nodeId);
            return node;
        }
        return null;
    }

    public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        String billName;
        ArrayList<VaParamTransferFolderNode> nodes = new ArrayList<VaParamTransferFolderNode>();
        VaParamTransferFolderNode node = new VaParamTransferFolderNode();
        Map<String, String> billNameMap = this.queryBillMetaInfo().stream().collect(Collectors.toMap(MetaInfoDim::getUniqueCode, MetaInfoDim::getTitle, (k1, k2) -> k2));
        String string = billName = StringUtils.isEmpty((String)billNameMap.get(nodeId)) ? nodeId : billNameMap.get(nodeId);
        if (CATEGORY_NAME_BILL_FORMULASCHEMECONFIG.equals(category)) {
            node.setTitle("\u5355\u636e/" + billName);
        } else if (CATEGORY_NAME_BILLLIST_FORMULASCHEMECONFIG.equals(category)) {
            node.setTitle("\u5355\u636e\u5217\u8868/" + billName);
        }
        nodes.add(node);
        return nodes;
    }

    public List<VaParamTransferBusinessNode> getRelatedBusiness(String category, String nodeId) {
        return super.getRelatedBusiness(category, nodeId);
    }

    public String getExportModelInfo(String category, String nodeId) {
        Map<String, List<BillFormulaSchemeConfigTableVO>> formulaSchemeConfigTableMap = this.billFormulaSchemeConfigService.queryTabSelectOrgIds(nodeId, null, true);
        return JSONUtil.toJSONString(formulaSchemeConfigTableMap);
    }

    public String getExportDataInfo(String category, String nodeId) {
        return super.getExportDataInfo(category, nodeId);
    }

    public void importModelInfo(String category, String info) {
        Map<String, List<BillFormulaSchemeConfigTableVO>> formulaSchemeConfigTableMap = BillFormulaSchemeConfigTransferModule.parseJson(info);
        this.transactionTemplate.execute(status -> {
            try {
                this.billFormulaSchemeConfigService.importFormulaSchemeConfig("strategySetting", (List)formulaSchemeConfigTableMap.get("batchStrategy"));
                this.billFormulaSchemeConfigService.importFormulaSchemeConfig("unitSetting", (List)formulaSchemeConfigTableMap.get("batchUnit"));
            }
            catch (Exception e) {
                LOGGER.error("\u5bfc\u5165\u53d6\u6570\u8bbe\u7f6e\u5931\u8d25\uff01", e);
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
            return null;
        });
    }

    public void importDataInfo(String category, String targetId, String info) {
        super.importDataInfo(category, targetId, info);
    }

    public static Map<String, List<BillFormulaSchemeConfigTableVO>> parseJson(String jsonString) {
        try {
            return (Map)objectMapper.readValue(jsonString, (TypeReference)new TypeReference<Map<String, List<BillFormulaSchemeConfigTableVO>>>(){});
        }
        catch (IOException e) {
            throw new RuntimeException("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u5bfc\u5165\u6570\u636e\u89e3\u6790\u5931\u8d25", e);
        }
    }

    public static enum Level {
        BILL(65, "\u5355\u636e");

        final int code;
        final String name;

        private Level(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}

