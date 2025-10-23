/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IConfigTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IDataTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IMetaFinder
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IPublisher
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.bi.transfer.engine.model.GuidMapperBean
 *  com.jiuqi.bi.transfer.engine.model.NameMapperBean
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.Consts
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.service.PeriodService
 *  org.jdom2.Element
 */
package com.jiuqi.nr.param.transfer.datascheme;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IConfigTransfer;
import com.jiuqi.bi.transfer.engine.intf.IDataTransfer;
import com.jiuqi.bi.transfer.engine.intf.IMetaFinder;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.intf.IPublisher;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.bi.transfer.engine.model.GuidMapperBean;
import com.jiuqi.bi.transfer.engine.model.NameMapperBean;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.Consts;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.param.transfer.ChangeObj;
import com.jiuqi.nr.param.transfer.FieldChangeObj;
import com.jiuqi.nr.param.transfer.ParamTransferConfig;
import com.jiuqi.nr.param.transfer.Utils;
import com.jiuqi.nr.param.transfer.datascheme.DataSchemeBusinessManager;
import com.jiuqi.nr.param.transfer.datascheme.DataSchemeFolderManager;
import com.jiuqi.nr.param.transfer.datascheme.DataSchemeModelTransfer;
import com.jiuqi.nr.param.transfer.datascheme.IDesignDataSchemeCacheProxy;
import com.jiuqi.nr.param.transfer.datascheme.TransferId;
import com.jiuqi.nr.param.transfer.datascheme.TransferIdParse;
import com.jiuqi.nr.param.transfer.datascheme.dao.IDimDataFieldDao;
import com.jiuqi.nr.param.transfer.datascheme.dto.DataSchemeFolder;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.service.PeriodService;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DataSchemeTransferFactory
extends TransferFactory {
    private static final Logger logger = LoggerFactory.getLogger(DataSchemeTransferFactory.class);
    @Autowired
    private DataSchemeModelTransfer dataSchemeModelTransfer;
    @Autowired
    private DataSchemeFolderManager dataSchemeFolderManager;
    @Autowired
    private DataSchemeBusinessManager dataSchemeBusinessManager;
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private PeriodService periodService;
    @Autowired
    private IFormTypeApplyService formTypeApplyService;
    @Autowired
    private IDesignDataSchemeCacheProxy iDesignDataSchemeCacheProxy;
    @Autowired
    private IDimDataFieldDao iDimDataFieldDao;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    private static final int ORGTYPE = 1;
    private static final int BASEDATATYPE = 2;
    private static final int ENUMDATATYPE = 3;

    public String getId() {
        return "DATASCHEME_TRANSFER_FACTORY_ID";
    }

    public String getTitle() {
        return "\u6570\u636e\u65b9\u6848";
    }

    public String getModuleId() {
        return "com.jiuqi.nr";
    }

    public boolean supportExport(String s) {
        return true;
    }

    public IModelTransfer createModelTransfer(String s) {
        return this.dataSchemeModelTransfer;
    }

    public IConfigTransfer createConfigTransfer() {
        return null;
    }

    public IPublisher createPublisher() {
        return null;
    }

    public IDataTransfer createDataTransfer(String s) {
        return null;
    }

    public boolean supportExportData(String s) {
        return false;
    }

    public List<NameMapperBean> handleMapper() {
        return null;
    }

    public List<GuidMapperBean> handleMapper(List<NameMapperBean> list) {
        return null;
    }

    public IMetaFinder createMetaFinder(String s) {
        return null;
    }

    public AbstractFolderManager getFolderManager() {
        return this.dataSchemeFolderManager;
    }

    public AbstractBusinessManager getBusinessManager() {
        return this.dataSchemeBusinessManager;
    }

    public String getModifiedTime(String s) throws TransferException {
        Basic basic = this.getBasic(s);
        if (basic == null) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        Instant updateTime = basic.getUpdateTime();
        return updateTime == null ? null : DATE_FORMAT.format(updateTime);
    }

    public Basic getBasic(String s) throws TransferException {
        TransferId transferId = TransferIdParse.parseId(s);
        NodeType nodeType = transferId.getNodeType();
        String key = transferId.getKey();
        switch (nodeType) {
            case GROUP: 
            case SCHEME_GROUP: {
                return this.iDesignDataSchemeCacheProxy.getDataGroup(key);
            }
            case SCHEME: {
                return this.iDesignDataSchemeCacheProxy.getDataScheme(key);
            }
            case TABLE: 
            case MD_INFO: 
            case ACCOUNT_TABLE: 
            case DETAIL_TABLE: 
            case MUL_DIM_TABLE: {
                return this.iDesignDataSchemeCacheProxy.getDataTable(key);
            }
        }
        return null;
    }

    public String getIcon() {
        return null;
    }

    public List<ResItem> getRelatedBusiness(String s) throws TransferException {
        TransferId transferId = TransferIdParse.parseId(s);
        NodeType nodeType = transferId.getNodeType();
        String key = transferId.getKey();
        ArrayList<ResItem> relatedList = new ArrayList<ResItem>();
        switch (nodeType) {
            case SCHEME: {
                this.getSchemeRelatedBusiness(key, relatedList);
                return relatedList;
            }
            case TABLE: 
            case MD_INFO: 
            case ACCOUNT_TABLE: 
            case DETAIL_TABLE: {
                DesignDataTable dataTable = this.iDesignDataSchemeCacheProxy.getDataTable(key);
                if (dataTable == null) {
                    return relatedList;
                }
                this.getSchemeRelatedBusiness(dataTable.getDataSchemeKey(), relatedList);
                List<String> entityIds = this.iDimDataFieldDao.getEntityIdsByDataTableKey(dataTable.getKey());
                for (String entityId : entityIds) {
                    if (!StringUtils.hasLength(entityId)) continue;
                    this.getEntityRelatedBusiness(entityId, relatedList);
                }
                return relatedList;
            }
        }
        return relatedList;
    }

    private void getSchemeRelatedBusiness(String key, List<ResItem> relatedList) throws TransferException {
        boolean addSchemeRel = this.iDesignDataSchemeCacheProxy.isAddSchemeEntityRel(key);
        if (addSchemeRel) {
            return;
        }
        List dataSchemeDimension = this.iDesignDataSchemeService.getDataSchemeDimension(key);
        for (DataDimension dataDimension : dataSchemeDimension) {
            String dimKey;
            if (dataDimension.getDimensionType() == DimensionType.PERIOD) {
                dimKey = dataDimension.getDimKey();
                try {
                    boolean period13;
                    IPeriodEntity period = this.periodService.queryPeriodByKey(dimKey);
                    if (period.getPeriodType() == PeriodType.CUSTOM) {
                        ResItem resItem = new ResItem(dimKey, "", "PERIOD_TRANSFER_FACTORY_ID");
                        relatedList.add(resItem);
                        continue;
                    }
                    if (period.getPeriodType() != PeriodType.MONTH || !(period13 = PeriodUtils.isPeriod13((String)period.getCode(), (PeriodType)period.getPeriodType()))) continue;
                    ResItem resItem = new ResItem(dimKey, "", "PERIOD_TRANSFER_FACTORY_ID");
                    relatedList.add(resItem);
                    continue;
                }
                catch (Exception e) {
                    throw new TransferException("\u67e5\u627e\u5173\u8054\u8d44\u6e90\u5931\u8d25", (Throwable)e);
                }
            }
            dimKey = dataDimension.getDimKey();
            this.getEntityRelatedBusiness(dimKey, relatedList);
        }
    }

    private void getEntityRelatedBusiness(String entityKey, List<ResItem> relatedList) {
        EntityInfo type = this.getType(entityKey);
        switch (type.type) {
            case 1: {
                ResItem resItem = new ResItem(type.key, "OrgCategory", "OrgData");
                relatedList.add(resItem);
                this.relateFormType(entityKey, relatedList);
                break;
            }
            case 2: {
                ResItem resItem = new ResItem("define##" + type.key, "BaseDataDefine", "BaseDataDefine");
                relatedList.add(resItem);
                break;
            }
            case 3: {
                ResItem resItem = new ResItem(type.key, "EnumData", "EnumData");
                relatedList.add(resItem);
                break;
            }
        }
    }

    private void relateFormType(String key, List<ResItem> relatedList) {
        String code = this.formTypeApplyService.getEntityFormTypeCode(key);
        if (code == null) {
            return;
        }
        ResItem resItem = new ResItem(code, "FORM_TYPE_TYPE", "FORM_TYPE_TRANSFER_FACTORY_ID");
        relatedList.add(resItem);
    }

    public List<String> getDependenceFactoryIds() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("PERIOD_TRANSFER_FACTORY_ID");
        list.add("OrgData");
        list.add("BaseDataDefine");
        list.add("FORM_TYPE_TRANSFER_FACTORY_ID");
        return list;
    }

    public int getOrder() {
        return -126;
    }

    public void sortImportBusinesses(List<BusinessNode> importBusinessNodes) {
        importBusinessNodes.sort(Comparator.nullsLast((o1, o2) -> {
            String type = o1.getType();
            String type1 = o2.getType();
            NodeType nodeType = NodeType.valueOf((String)type);
            NodeType nodeType1 = NodeType.valueOf((String)type1);
            return nodeType.getValue() - nodeType1.getValue();
        }));
    }

    public EntityInfo getType(String entityId) {
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.type = 0;
        if (entityId == null) {
            return entityInfo;
        }
        String[] split = entityId.split("@", 2);
        if (split.length < 2) {
            return entityInfo;
        }
        entityInfo.key = split[0];
        if ("ORG".equals(split[1])) {
            entityInfo.type = 1;
        }
        if ("BASE".equals(split[1])) {
            entityInfo.type = 2;
        }
        if ("ENUM".equals(split[1])) {
            entityInfo.type = 3;
        }
        return entityInfo;
    }

    public void toDocumentExtra(Element folderElement, FolderNode folderNode) {
        String order = folderNode.getOrder();
        if (order != null) {
            folderElement.setAttribute("order", order);
        }
        if (folderNode.getName() != null) {
            folderElement.setAttribute("name", folderNode.getName());
        }
        if (folderNode instanceof DataSchemeFolder) {
            DataSchemeFolder dataSchemeFolder = (DataSchemeFolder)folderNode;
            if (dataSchemeFolder.getDataSchemeKey() != null) {
                folderElement.setAttribute("dataSchemeKey", dataSchemeFolder.getDataSchemeKey());
            }
            folderElement.setAttribute("dataGroupKind", dataSchemeFolder.getDataGroupKind().getTitle());
            if (dataSchemeFolder.getUpdateTime() != null) {
                folderElement.setAttribute("updateTime", Consts.FMT.format(dataSchemeFolder.getUpdateTime()));
            }
            if (dataSchemeFolder.getDesc() != null) {
                folderElement.setAttribute("desc", dataSchemeFolder.getDesc());
            }
        }
    }

    public FolderNode createFolderNode() {
        return new DataSchemeFolder();
    }

    public void loadFolderExtra(Element element, FolderNode folderNode) {
        String order = element.getAttributeValue("order");
        String name = element.getAttributeValue("name");
        folderNode.setOrder(order);
        folderNode.setName(name);
        if (folderNode instanceof DataSchemeFolder) {
            String dataSchemeKey = element.getAttributeValue("dataSchemeKey");
            String dataGroupKind = element.getAttributeValue("dataGroupKind");
            String updateTime = element.getAttributeValue("updateTime");
            String desc = element.getAttributeValue("desc");
            if (dataGroupKind != null) {
                ((DataSchemeFolder)folderNode).setDataGroupKind(DataGroupKind.titleOf((String)dataGroupKind));
            }
            ((DataSchemeFolder)folderNode).setDataSchemeKey(dataSchemeKey);
            ((DataSchemeFolder)folderNode).setDesc(desc);
            if (updateTime != null) {
                ((DataSchemeFolder)folderNode).setUpdateTime(Instant.from(Consts.FMT.parse(updateTime)));
            }
        }
    }

    public void beforeImport(ITransferContext context, List<BusinessNode> businessNodes) throws TransferException {
        this.dataSchemeModelTransfer.cleanFieldChangeFormulaObjs();
        this.doLogHelper(businessNodes);
        ParamTransferConfig.buildNpContext(context.getOperator());
    }

    private void doLogHelper(List<BusinessNode> businessNodes) {
        if (businessNodes != null && !businessNodes.isEmpty()) {
            LogHelper.info((String)"\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa", (String)"\u5bfc\u5165\u6570\u636e\u65b9\u6848\u53c2\u6570", (String)"\u8fdb\u884c\u6570\u636e\u65b9\u6848\u8d44\u6e90\u7684\u5bfc\u5165\uff01");
        }
    }

    public String getImportDetailFileKey(ITransferContext context) {
        StringBuilder allMessage = new StringBuilder();
        List<FieldChangeObj> fieldChangeFormulaObjs = this.dataSchemeModelTransfer.getFieldChangeFormulaObjs();
        Map<String, List<FieldChangeObj>> formSchemeToFormulaChange = fieldChangeFormulaObjs.stream().collect(Collectors.groupingBy(FieldChangeObj::getDataSchemeKey));
        for (Map.Entry<String, List<FieldChangeObj>> dataSchemeToFieldChangeEntry : formSchemeToFormulaChange.entrySet()) {
            List<FieldChangeObj> dataSchemeToFieldChangeValue = dataSchemeToFieldChangeEntry.getValue();
            String dataSchemeTitle = dataSchemeToFieldChangeValue.get(0).getDataSchemeTitle();
            StringBuilder fieldMessageForScheme = new StringBuilder();
            fieldMessageForScheme.append("\u6570\u636e\u65b9\u6848 ").append(dataSchemeTitle).append("\r\n");
            for (FieldChangeObj fieldChangeObj : dataSchemeToFieldChangeValue) {
                fieldMessageForScheme.append("\u4e0b\u6570\u636e\u8868 ").append(fieldChangeObj.getDataTableTitle()).append("\r\n");
                if (!CollectionUtils.isEmpty(fieldChangeObj.getAddFields())) {
                    fieldMessageForScheme.append("\u65b0\u589e\u7684\u6307\u6807\u6709\uff1a");
                    for (ChangeObj addFormula : fieldChangeObj.getAddFields()) {
                        fieldMessageForScheme.append(addFormula.getTitle()).append("[").append(addFormula.getCode()).append("]").append("\u3001");
                    }
                    fieldMessageForScheme.append("\r\n");
                }
                if (!CollectionUtils.isEmpty(fieldChangeObj.getUpdateFields())) {
                    fieldMessageForScheme.append("\u66f4\u65b0\u7684\u6307\u6807\u6709\uff1a");
                    for (ChangeObj addFormula : fieldChangeObj.getUpdateFields()) {
                        fieldMessageForScheme.append(addFormula.getTitle()).append("[").append(addFormula.getCode()).append("]").append("\u3001");
                    }
                    fieldMessageForScheme.append("\r\n");
                }
                if (!CollectionUtils.isEmpty(fieldChangeObj.getDeleteFields())) {
                    fieldMessageForScheme.append("\u5220\u9664\u7684\u6307\u6807\u6709\uff1a");
                    for (ChangeObj addFormula : fieldChangeObj.getDeleteFields()) {
                        fieldMessageForScheme.append(addFormula.getTitle()).append("[").append(addFormula.getCode()).append("]").append("\u3001");
                    }
                    fieldMessageForScheme.append("\r\n");
                }
                fieldMessageForScheme.append("\r\n");
            }
            fieldMessageForScheme.append("\r\n");
            allMessage.append((CharSequence)fieldMessageForScheme);
        }
        if (allMessage.length() == 0) {
            allMessage.append("\u6570\u636e\u65b9\u6848\u53c2\u6570\u65e0\u53d8\u5316");
        }
        String fileKey = "";
        try {
            fileKey = Utils.fileUpload("\u6570\u636e\u65b9\u6848\u540c\u6b65\u7ed3\u679c\u4fe1\u606f.txt", allMessage);
        }
        catch (Exception e) {
            logger.error("\u53c2\u6570\u5bfc\u5165\u5199\u5165\u6587\u4ef6\u65f6\u51fa\u73b0\u9519\u8bef: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return fileKey;
    }

    private static class EntityInfo {
        private int type;
        private String key;

        private EntityInfo() {
        }
    }
}

