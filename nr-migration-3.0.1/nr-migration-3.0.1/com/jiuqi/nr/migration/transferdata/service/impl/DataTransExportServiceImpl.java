/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckDescription
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.definition.option.DimGroupOptionService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.fielddatacrud.FieldRelationFactory
 *  com.jiuqi.nr.mapping2.bean.FormulaMapping
 *  com.jiuqi.nr.mapping2.bean.PeriodMapping
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 *  com.jiuqi.nr.mapping2.service.FormulaMappingService
 *  com.jiuqi.nr.mapping2.service.PeriodMappingService
 *  com.jiuqi.nr.mapping2.service.ZBMappingService
 *  com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataMapping
 *  com.jiuqi.nvwa.mapping.bean.OrgMapping
 *  com.jiuqi.nvwa.mapping.service.IBaseDataMappingService
 *  com.jiuqi.nvwa.mapping.service.IOrgMappingService
 *  com.jiuqi.xlib.utils.GUID
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.migration.transferdata.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.definition.option.DimGroupOptionService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.service.FormulaMappingService;
import com.jiuqi.nr.mapping2.service.PeriodMappingService;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nr.migration.syncscheme.bean.SyncScheme;
import com.jiuqi.nr.migration.syncscheme.service.ISyncSchemeService;
import com.jiuqi.nr.migration.syncscheme.vo.SchemeData;
import com.jiuqi.nr.migration.transferdata.bean.CheckErrorDesc;
import com.jiuqi.nr.migration.transferdata.bean.DataSynRange;
import com.jiuqi.nr.migration.transferdata.bean.ExportParam;
import com.jiuqi.nr.migration.transferdata.bean.TransCheckError;
import com.jiuqi.nr.migration.transferdata.bean.TransDimension;
import com.jiuqi.nr.migration.transferdata.bean.TransOrgInfo;
import com.jiuqi.nr.migration.transferdata.bean.TransferExportDTO;
import com.jiuqi.nr.migration.transferdata.common.JqrXmlExportProcessor;
import com.jiuqi.nr.migration.transferdata.common.TransferUtils;
import com.jiuqi.nr.migration.transferdata.dbservice.service.IQueryDataService;
import com.jiuqi.nr.migration.transferdata.jqrmapping.JQRResourceMapping2Service;
import com.jiuqi.nr.migration.transferdata.log.XmlDataExportLog;
import com.jiuqi.nr.migration.transferdata.service.IDataTransExportService;
import com.jiuqi.nr.migration.transferdata.service.ITreeTransXmlProcess;
import com.jiuqi.nr.migration.transferdata.service.impl.TreeTransXmlProcessImpl_56;
import com.jiuqi.nr.migration.transferdata.service.impl.TreeTransXmlProcessImpl_57;
import com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataMapping;
import com.jiuqi.nvwa.mapping.bean.OrgMapping;
import com.jiuqi.nvwa.mapping.service.IBaseDataMappingService;
import com.jiuqi.nvwa.mapping.service.IOrgMappingService;
import com.jiuqi.xlib.utils.GUID;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DataTransExportServiceImpl
implements IDataTransExportService {
    private static final Logger logger = LoggerFactory.getLogger(DataTransExportServiceImpl.class);
    @Autowired
    private ISyncSchemeService iSyncSchemeService;
    @Autowired
    private IQueryDataService queryDataExecutorImpl;
    @Autowired
    private IFormulaRunTimeController formulaRunTime;
    @Autowired
    private RuntimeViewController runTimeViewController;
    @Autowired
    private ZBMappingService zbMappingService;
    @Autowired
    protected FieldRelationFactory fieldRelationFactory;
    @Autowired
    protected DimGroupOptionService dimGroupOptionService;
    @Autowired
    private JQRResourceMapping2Service jqrCustomMapping2Service;
    @Autowired
    private PeriodMappingService periodMappingService;
    @Autowired
    private IOrgMappingService orgMappingService;
    @Autowired
    private IBaseDataMappingService baseDataMappingService;
    @Autowired
    private FormulaMappingService formulaMappingService;
    @Autowired
    private ICheckErrorDescriptionService checkErrorDescriptionService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;

    @Override
    public void downloadJQRDataPackage(ExportParam exportVo, HttpServletResponse response) {
        try {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(exportVo.getTaskId());
            if (taskDefine == null) {
                this.setResponse(response, "\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a\u3002");
                return;
            }
            XmlDataExportLog exportLog = new XmlDataExportLog();
            ByteArrayOutputStream baoStream = this.getXmlByteMap(exportVo, exportLog);
            if (baoStream != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String name = taskDefine.getTitle() + sdf.format(new Date()) + ".jqr";
                byte[] bytes = baoStream.toByteArray();
                this.setResponse(response, exportLog.getSummaryLog(), bytes, name);
                baoStream.close();
            } else {
                this.setResponse(response, "\u6ca1\u6709\u6570\u636e\u53ef\u5bfc\u51fa\u3002");
            }
            LogHelper.info((String)"\u5bfc\u51faJQR\u6570\u636e\u5305", (String)"JQR\u6570\u636e\u5305\u5bfc\u51fa\u6210\u529f", (String)exportLog.getSummaryLog());
        }
        catch (Exception e) {
            try {
                this.setResponse(response, e.getMessage());
                throw new RuntimeException(e);
            }
            catch (Exception ex) {
                LogHelper.info((String)"\u5bfc\u51faJQR\u6570\u636e\u5305", (String)"JQR\u6570\u636e\u5305\u5bfc\u51fa\u5931\u8d25", (String)e.getMessage());
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void executeSyncScheme(TransferExportDTO transferExport, HttpServletResponse response) {
        ExportParam transferExportVo = transferExport.toTransferExportVo();
        TransferUtils.extractDimToVo(transferExport.getDimensionValueSet(), transferExportVo);
        this.downloadJQRDataPackage(transferExportVo, response);
    }

    @Override
    public void executeSyncScheme(String schemeKey, HttpServletResponse response) throws Exception {
        if (!StringUtils.hasLength(schemeKey)) {
            return;
        }
        SyncScheme syncScheme = this.iSyncSchemeService.getByKey(schemeKey);
        if (syncScheme == null) {
            return;
        }
        String data = syncScheme.getData();
        if (data == null) {
            return;
        }
        TransferExportDTO transferExport = this.getTransferExport(data, null);
        this.executeSyncScheme(transferExport, response);
    }

    @Override
    public byte[] executeSyncScheme(TransferExportDTO transferExport) throws Exception {
        XmlDataExportLog exportLog = new XmlDataExportLog();
        ExportParam transferExportVo = transferExport.toTransferExportVo();
        TransferUtils.extractDimToVo(transferExport.getDimensionValueSet(), transferExportVo);
        try (ByteArrayOutputStream baoStream = this.getXmlByteMap(transferExportVo, exportLog);){
            if (baoStream != null) {
                byte[] byArray = baoStream.toByteArray();
                return byArray;
            }
        }
        return new byte[0];
    }

    @Override
    public byte[] executeSyncScheme(String schemeKey) throws Exception {
        if (!StringUtils.hasLength(schemeKey)) {
            return new byte[0];
        }
        SyncScheme syncScheme = this.iSyncSchemeService.getByKey(schemeKey);
        if (syncScheme == null) {
            return new byte[0];
        }
        String data = syncScheme.getData();
        if (data == null) {
            return new byte[0];
        }
        TransferExportDTO transferExport = this.getTransferExport(data, null);
        return this.executeSyncScheme(transferExport);
    }

    @Override
    public byte[] executeSyncScheme(SchemeData schemeData) throws Exception {
        if (schemeData == null) {
            return new byte[0];
        }
        String data = schemeData.getData();
        ObjectMapper objectMapper = new ObjectMapper();
        TransferExportDTO transferExport = (TransferExportDTO)objectMapper.readValue(data, TransferExportDTO.class);
        return this.executeSyncScheme(transferExport);
    }

    @Override
    public byte[] executeSyncScheme(String schemeKey, Map<String, DimensionValue> dimValueSet) throws Exception {
        if (!StringUtils.hasLength(schemeKey)) {
            return new byte[0];
        }
        SyncScheme syncScheme = this.iSyncSchemeService.getByKey(schemeKey);
        String data = syncScheme.getData();
        if (data == null) {
            return new byte[0];
        }
        TransferExportDTO transferExport = this.getTransferExport(data, dimValueSet);
        return this.executeSyncScheme(transferExport);
    }

    private TransferExportDTO getTransferExport(String data, Map<String, DimensionValue> dimValueSet) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TransferExportDTO transferExportDTO = (TransferExportDTO)objectMapper.readValue(data, TransferExportDTO.class);
        if (dimValueSet != null) {
            transferExportDTO.setDimensionValueSet(dimValueSet);
        }
        return transferExportDTO;
    }

    private void setResponse(HttpServletResponse response, String logInfo) throws Exception {
        this.setResponse(response, logInfo, null, null);
    }

    private void setResponse(HttpServletResponse response, String logInfo, byte[] bytes, String fileName) throws Exception {
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition, download-logger");
        response.setContentType("application/zip");
        response.addHeader("download-logger", URLEncoder.encode(logInfo, "UTF-8"));
        response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + (StringUtils.hasLength(fileName) ? URLEncoder.encode(fileName, "UTF-8") : ""));
        if (bytes != null) {
            response.addHeader("Content-Length", String.valueOf(bytes.length));
            response.getOutputStream().write(bytes);
        } else {
            response.addHeader("Content-Length", String.valueOf(0));
        }
        response.flushBuffer();
    }

    private ByteArrayOutputStream getXmlByteMap(ExportParam vo, XmlDataExportLog exportLog) throws Exception {
        logger.info("jqr\u683c\u5f0f\u6570\u636e\u5305\u5bfc\u51fa");
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(vo.getTaskId());
        logger.info("\u4efb\u52a1\uff1a" + taskDefine.getTaskCode());
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(vo.getFormSchemeId());
        if (formDefines.size() == 0) {
            logger.error("\u62a5\u8868\u65b9\u6848\u4e0b\u65e0\u62a5\u8868");
            return null;
        }
        exportLog.setTaskInfo(taskDefine.getTaskCode()).setDwEntityId(taskDefine.getDw()).setPeriodValue(vo.getPeriodValue());
        logger.info("\u62a5\u8868\u5217\u8868\uff1a " + formDefines.stream().map(FormDefine::getFormCode).collect(Collectors.joining(", ")));
        DataSynRange range = DataSynRange.valueOf(vo.getSynRange());
        logger.info("\u540c\u6b65\u914d\u7f6e\uff1a " + range.getTitle());
        logger.info("\u662f\u5426\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\uff1a " + vo.isCreateXM());
        logger.info("\u662f\u5426\u5bfc\u51fa\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\uff1a " + vo.isSynCheckErrorDes());
        JqrXmlExportProcessor process = new JqrXmlExportProcessor(this.queryDataExecutorImpl, this.runTimeViewController, this.fieldRelationFactory, taskDefine, formDefines, range, vo.isSynCommitMemo());
        process.setExportLog(exportLog);
        boolean isBase = vo.getDwEntityId().endsWith("@BASE");
        this.getMappingData(process, vo, taskDefine.getTaskCode());
        IEntityTable entityTable = TransferUtils.getEntityTable(vo.getDwEntityId(), vo.getPeriodValue());
        logger.info("\u4e3b\u7ef4\u5ea6\uff1a" + vo.getDwEntityId() + ", \u65f6\u671f\uff1a " + vo.getPeriodValue());
        List<TransOrgInfo> orgInfos = vo.getOrgInfos();
        ByteArrayOutputStream bass = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream((OutputStream)bass, Charset.forName("GBK"));
        if (orgInfos != null && orgInfos.size() > 0) {
            logger.info("\u5355\u4f4d\u5217\u8868\uff1a" + orgInfos.stream().map(TransOrgInfo::getCode).collect(Collectors.joining(" ")));
            HashMap<String, Map<String, String>> code2bdPropertyMap = new HashMap<String, Map<String, String>>();
            String tableName = isBase ? vo.getDwEntityId().split("@")[0].substring(3) : vo.getDwEntityId().split("@")[0];
            this.handleDataXml(zipOut, process, vo, entityTable, code2bdPropertyMap, tableName);
            if (vo.isCreateXM() && StringUtils.hasLength(tableName)) {
                this.handleTreeXml(zipOut, entityTable, process.getMappingCache().getTableNameMapping(), isBase, process.getVersion());
            }
            if (isBase) {
                this.handleManagementXml(zipOut, process, vo, entityTable, code2bdPropertyMap);
            }
            zipOut.close();
        }
        return bass;
    }

    private void handleDataXml(ZipOutputStream zipOut, JqrXmlExportProcessor process, ExportParam vo, IEntityTable entityTable, Map<String, Map<String, String>> code2bdPropertyMap, String tableName) throws IOException {
        boolean isBase = vo.getDwEntityId().endsWith("@BASE");
        TransDimension periodDimension = TransDimension.getPeriodDimension("DATATIME", vo.getPeriodEntityId(), vo.getPeriodValue());
        Map<String, TransCheckError> checkErrors = null;
        if (vo.isSynCheckErrorDes()) {
            process.setSynCheckErrorDes(true);
            List<String> unitCodes = vo.getOrgInfos().stream().map(TransOrgInfo::getCode).collect(Collectors.toList());
            checkErrors = this.getCheckErrors(vo.getFormSchemeId(), vo.getMappingSchemeId(), vo.getDwEntityId(), unitCodes, vo.getPeriodValue());
        }
        for (int i = 0; i < vo.getOrgInfos().size(); ++i) {
            TransOrgInfo org = vo.getOrgInfos().get(i);
            String code = org.getCode();
            String title = org.getTitle();
            logger.info("\u5f00\u59cb\u83b7\u53d6 code: {}, title: {} \u7684\u6570\u636e", (Object)code, (Object)title);
            process.setCurrentExportUnitInfo("\u5355\u4f4d\uff1a" + code);
            TransDimension dwDim = TransDimension.getDWDim(vo.getDwEntityId(), vo.isCreateXM(), code, title, tableName);
            dwDim.setParentCode(entityTable.findByCode(code) != null ? entityTable.findByCode(code).getParentEntityKey() : "");
            process.setDimensions(periodDimension, dwDim);
            if (isBase) {
                Map<String, String> baseDataProperty = TransferUtils.getBaseDataProperty(entityTable, code);
                process.setBdPropertyMap(baseDataProperty);
                code2bdPropertyMap.put(code, baseDataProperty);
            }
            if (!isBase) {
                logger.info("\u4e3b\u7ef4\u5ea6\u662f\u5355\u4f4d-\u65f6\u671f\uff0c\u83b7\u53d6ParentCode\u3002");
                String parent = TransferUtils.getParentCode(entityTable, code);
                logger.info("ParentCode\uff1a{}", (Object)parent);
                process.setParentCode(parent);
            }
            if (vo.isSynCheckErrorDes() && checkErrors != null && checkErrors.get(code) != null) {
                process.setTransCheckErrorDes(checkErrors.get(code));
            }
            String fileName = this.getFileName(i + 1, code, title) + ".xml";
            logger.info("\u5f00\u59cb\u83b7\u53d6{}\u5b57\u8282\u6570\u7ec4\u3002", (Object)fileName);
            process.exportXmlFile();
            byte[] exportData = process.getExportData();
            if (exportData != null) {
                logger.info("{}\u5b57\u8282\u6570\u7ec4\u83b7\u53d6\u7ed3\u675f\u3002", (Object)fileName);
                this.writeByteArrayToZOS(zipOut, fileName, exportData);
                continue;
            }
            logger.warn("{}\u5b57\u8282\u6570\u7ec4\u4e3a\u7a7a\u3002", (Object)fileName);
        }
    }

    private void writeByteArrayToZOS(ZipOutputStream zipOut, String fileName, byte[] bytes) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        zipOut.write(bytes);
        zipOut.closeEntry();
    }

    private Map<String, TransDimension> getManagementXmlParam(ExportParam vo, IEntityTable entityTable, Map<String, Map<String, String>> code2bdPropertyMap) throws Exception {
        if (vo == null || entityTable == null || code2bdPropertyMap == null) {
            logger.error("\u8f93\u5165\u53c2\u6570\u5b58\u5728 null \u503c\uff0c\u65e0\u6cd5\u751f\u6210 managementXmlParam\u3002");
            return null;
        }
        List<TransOrgInfo> orgInfos = vo.getOrgInfos();
        if (orgInfos == null || orgInfos.isEmpty()) {
            logger.warn("\u5355\u4f4d\u4fe1\u606f\u5217\u8868\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u751f\u6210 managementXmlParam\u3002");
            return null;
        }
        String[] dimensionGroup = this.dimGroupOptionService.getGroupOptionValue(vo.getTaskId());
        if (dimensionGroup.length == 0) {
            logger.warn("\u7ef4\u5ea6\u7ec4\u9009\u9879\u503c\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u751f\u6210 managementXmlParam\u3002");
            return null;
        }
        String relatedEntityID = TransferUtils.getEntityFieldRelateID(dimensionGroup);
        IEntityTable relatedEntityTable = TransferUtils.getEntityTable(relatedEntityID, vo.getPeriodValue());
        String glkj = dimensionGroup[1];
        HashMap<String, List<String>> management2Codes = new HashMap<String, List<String>>();
        for (TransOrgInfo orgInfo : orgInfos) {
            Map<String, String> baseDataProperty = this.getBaseDataProperty(orgInfo.getCode(), entityTable, code2bdPropertyMap);
            if (!baseDataProperty.containsKey(glkj)) continue;
            String management = baseDataProperty.get(glkj);
            management2Codes.computeIfAbsent(management, k -> new ArrayList()).add(orgInfo.getCode());
        }
        return this.buildManagement2DWDim(management2Codes, relatedEntityTable, relatedEntityID);
    }

    private Map<String, String> getBaseDataProperty(String code, IEntityTable entityTable, Map<String, Map<String, String>> code2bdPropertyMap) {
        if (code2bdPropertyMap.containsKey(code)) {
            return code2bdPropertyMap.get(code);
        }
        Map<String, String> baseDataProperty = TransferUtils.getBaseDataProperty(entityTable, code);
        code2bdPropertyMap.put(code, baseDataProperty);
        return baseDataProperty;
    }

    private Map<String, TransDimension> buildManagement2DWDim(Map<String, List<String>> management2Codes, IEntityTable relatedEntityTable, String relatedEntityID) {
        HashMap<String, TransDimension> management2DWDim = new HashMap<String, TransDimension>();
        if (management2Codes.size() > 0) {
            for (String management : management2Codes.keySet()) {
                IEntityRow managementEntityRow = relatedEntityTable.findByCode(management);
                String name = relatedEntityID.endsWith("@BASE") ? relatedEntityID.split("@")[0] : "UNITID";
                TransDimension dwDimension = TransDimension.getDWDimension(name, relatedEntityID, managementEntityRow.getCode(), managementEntityRow.getTitle());
                dwDimension.setManagement(relatedEntityID.split("@")[0]);
                management2DWDim.put(management, dwDimension);
            }
        }
        return management2DWDim;
    }

    private String getFileName(int i, String code, String title) {
        if (!StringUtils.hasLength(code) || !StringUtils.hasLength(title)) {
            return "";
        }
        StringBuilder fileName = new StringBuilder();
        if (i != -1) {
            fileName.append(i).append("_");
        }
        fileName.append(code).append("_");
        if (title.contains(" ")) {
            String _t = title;
            if (_t.contains("|")) {
                _t = _t.split("\\|")[1];
            }
            fileName.append(_t);
        } else {
            fileName.append(title);
        }
        return fileName.toString();
    }

    private void handleManagementXml(ZipOutputStream zipOut, JqrXmlExportProcessor process, ExportParam vo, IEntityTable entityTable, Map<String, Map<String, String>> code2bdPropertyMap) throws Exception {
        String periodV = process.getMappingCache().getPeriodMappingMap().get(vo.getPeriodValue());
        if (!StringUtils.hasLength(periodV)) {
            periodV = vo.getPeriodValue();
        }
        TransDimension periodTranDim = TransDimension.getPeriodDimension("DATATIME", vo.getPeriodEntityId(), periodV);
        Map<String, TransDimension> managementXmlParam = this.getManagementXmlParam(vo, entityTable, code2bdPropertyMap);
        if (managementXmlParam == null) {
            return;
        }
        Map<String, byte[]> managexml2Byte = process.batchGetManageXml(managementXmlParam, periodTranDim);
        for (String fileName : managexml2Byte.keySet()) {
            byte[] exportData = managexml2Byte.get(fileName);
            this.writeByteArrayToZOS(zipOut, fileName, exportData);
        }
    }

    private void handleTreeXml(ZipOutputStream zipOut, IEntityTable entityTable, String tableName, boolean isBase, String version) throws IOException {
        if (!isBase) {
            logger.error("\u4e0d\u652f\u6301\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u7684\u5355\u4f4d\u65b0\u589e\u3002");
            return;
        }
        logger.info("\u65b0\u589e\u9879\u76ee\uff1ajqr\u7248\u672c\u662f{}", (Object)version);
        ITreeTransXmlProcess treeTransXmlProcess = "5.6".equals(version) ? new TreeTransXmlProcessImpl_56(entityTable, tableName) : new TreeTransXmlProcessImpl_57(entityTable, tableName);
        String treeName = treeTransXmlProcess.getName();
        logger.info("\u5f00\u59cb\u83b7\u53d6{}\u7684\u5b57\u8282\u6570\u7ec4\u3002", (Object)treeName);
        byte[] treeXmlData = treeTransXmlProcess.getTreeData();
        if (treeXmlData == null) {
            logger.warn("tree.xml\u5b57\u8282\u6570\u7ec4\u4e3a\u7a7a\u3002");
            return;
        }
        logger.info("\u83b7\u53d6tree.xml\u5b57\u8282\u6570\u7ec4\u7ed3\u675f\u3002");
        this.writeByteArrayToZOS(zipOut, treeName, treeXmlData);
    }

    private void getMappingData(JqrXmlExportProcessor process, ExportParam vo, String taskCode) {
        boolean isBase = vo.getDwEntityId().endsWith("@BASE");
        if (process.getMappingCache() == null) {
            String mappingSchemeId = vo.getMappingSchemeId();
            if (!StringUtils.hasLength(mappingSchemeId)) {
                process.setTableNameMapping(isBase ? vo.getDwEntityId().split("@")[0].substring(3) : vo.getDwEntityId().split("@")[0]);
                process.setSolutionMapping(taskCode);
                return;
            }
            String tableName = this.getTableNameMapping(mappingSchemeId, vo, isBase);
            String version = this.getVersionMapping(mappingSchemeId);
            String solutionMapping = this.getSolutionNameMapping(mappingSchemeId, taskCode);
            Map<String, String> periodMapping = this.getPeriodMapping(mappingSchemeId);
            Map<String, String> zbMapping = this.getZbMapping(mappingSchemeId);
            process.setPeriodMapping(periodMapping);
            process.setTableNameMapping(tableName);
            process.setSolutionMapping(solutionMapping);
            process.setZbMapping(zbMapping);
            process.setVersion(version);
            if (!isBase) {
                process.setOrgMapping(this.getOrgMapping(mappingSchemeId));
            } else {
                process.setBDMapping(this.getBaseDataMapping(mappingSchemeId, vo.getDwEntityId()));
            }
        }
    }

    private String getTableNameMapping(String mappingSchemeId, ExportParam vo, boolean isBase) {
        String tableName = this.jqrCustomMapping2Service.getTableNameMapping(mappingSchemeId);
        if (!StringUtils.hasLength(tableName)) {
            tableName = isBase ? vo.getDwEntityId().split("@")[0].substring(3) : vo.getDwEntityId().split("@")[0];
        }
        return tableName;
    }

    private String getSolutionNameMapping(String mappingSchemeId, String taskCode) {
        String solutionMapping = this.jqrCustomMapping2Service.getSolutionNameMapping(mappingSchemeId);
        if (!StringUtils.hasLength(solutionMapping)) {
            solutionMapping = taskCode;
        }
        return solutionMapping;
    }

    private String getVersionMapping(String mappingSchemeId) {
        String versionMapping = this.jqrCustomMapping2Service.getVersionMapping(mappingSchemeId);
        if (!StringUtils.hasLength(versionMapping)) {
            versionMapping = "5.7";
        }
        return versionMapping;
    }

    private Map<String, String> getPeriodMapping(String mappingSchemeId) {
        List byMS = this.periodMappingService.findByMS(mappingSchemeId);
        HashMap<String, String> pM = new HashMap<String, String>();
        if (byMS != null) {
            for (PeriodMapping periodMapping : byMS) {
                pM.put(periodMapping.getPeriod(), periodMapping.getMapping());
            }
        }
        return pM;
    }

    private Map<String, String> getZbMapping(String mappingSchemeId) {
        HashMap<String, String> zbMapping = new HashMap<String, String>();
        List byMSAndForm = this.zbMappingService.findByMS(mappingSchemeId);
        for (ZBMapping zbMap : byMSAndForm) {
            zbMapping.put(zbMap.getZbCode(), zbMap.getMapping());
        }
        return zbMapping;
    }

    private Map<String, Map<String, String>> getBaseDataMapping(String mappingSchemeKey, String dw) {
        HashMap<String, Map<String, String>> bdMapping = new HashMap<String, Map<String, String>>();
        List baseDataMapping = this.baseDataMappingService.getBaseDataMapping(mappingSchemeKey);
        for (BaseDataMapping bs : baseDataMapping) {
            String baseDataCode;
            if (bs == null || !StringUtils.hasLength(baseDataCode = bs.getBaseDataCode()) || !baseDataCode.equals(dw.split("@BASE")[0])) continue;
            HashMap<String, String> bdItemMapping = new HashMap<String, String>();
            List baseDataItemMapping = this.baseDataMappingService.getBaseDataItem(mappingSchemeKey, baseDataCode);
            for (BaseDataItemMapping bedim : baseDataItemMapping) {
                bdItemMapping.put(bedim.getBaseDataItemCode(), bedim.getMappingCode());
            }
            bdMapping.put(dw, bdItemMapping);
        }
        return bdMapping;
    }

    private Map<String, String> getOrgMapping(String mappingSchemeKey) {
        HashMap<String, String> orgMapping = new HashMap<String, String>();
        List orgMappingByMS = this.orgMappingService.getOrgMappingByMS(mappingSchemeKey);
        for (OrgMapping pm : orgMappingByMS) {
            if (!StringUtils.hasLength(pm.getMapping())) continue;
            orgMapping.put(pm.getCode(), pm.getMapping());
        }
        return orgMapping;
    }

    private Map<String, TransCheckError> getCheckErrors(String formSchemeKey, String mappingKey, String dwEntityId, List<String> unitCodes, String periodValue) {
        HashMap<String, TransCheckError> res = new HashMap<String, TransCheckError>();
        List allFormulaMappings = this.formulaMappingService.findByMS(mappingKey);
        if (allFormulaMappings == null || allFormulaMappings.size() == 0) {
            logger.warn("\u516c\u5f0f\u9519\u8bef\u8bf4\u660e\uff1a \u6620\u5c04\u65b9\u6848\u4e2d\u672a\u914d\u7f6e\u516c\u5f0f\u6620\u5c04\u4fe1\u606f\u3002");
        }
        ArrayList<String> formKeys = new ArrayList<String>();
        HashMap<String, String> formKey2FormCode = new HashMap<String, String>();
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        for (FormDefine formDefine : formDefines) {
            formKeys.add(formDefine.getKey());
            formKey2FormCode.put(formDefine.getKey(), formDefine.getFormCode());
        }
        List<CheckDesObj> checkDesObjs = this.getCheckErrorDesObjs(formSchemeKey, formKeys, dwEntityId, unitCodes, periodValue);
        if (checkDesObjs.size() == 0) {
            logger.info("\u516c\u5f0f\u9519\u8bef\u8bf4\u660e\uff1a\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0b\u65e0\u516c\u5f0f\u9519\u8bef\u8bf4\u660e\u3002");
            return null;
        }
        Map<String, List<CheckDesObj>> unit2CheckDesObjMap = checkDesObjs.stream().collect(Collectors.groupingBy(checkDesObj -> {
            Map dimensionSet = checkDesObj.getDimensionSet();
            if (dwEntityId.startsWith("MD_ORG")) {
                return ((DimensionValue)dimensionSet.get("MD_ORG")).getValue();
            }
            return ((DimensionValue)dimensionSet.get(dwEntityId.split("@")[0])).getValue();
        }));
        for (String unitCode : unit2CheckDesObjMap.keySet()) {
            List<CheckDesObj> checkDesObjList = unit2CheckDesObjMap.get(unitCode);
            if (checkDesObjList.size() == 0) {
                logger.info("\u516c\u5f0f\u9519\u8bef\u8bf4\u660e\uff1a\u5355\u4f4d " + unitCode + "\u4e0b\u65e0\u516c\u5f0f\u9519\u8bef\u8bf4\u660e\u3002");
                continue;
            }
            TransCheckError checkError = new TransCheckError();
            checkError.setUnitCode(unitCode);
            checkError.setDataTime(periodValue);
            for (CheckDesObj checkDesObj2 : checkDesObjList) {
                if (allFormulaMappings == null || allFormulaMappings.size() == 0) {
                    checkError.addCheckErrorDescList(this.getTransCheckError(checkDesObj2, "", true));
                    continue;
                }
                boolean flag = false;
                for (FormulaMapping formulaMapping : allFormulaMappings) {
                    String formCode;
                    if (!StringUtils.hasLength(formulaMapping.getmFormulaCode()) || !checkDesObj2.getFormulaSchemeKey().equals(formulaMapping.getFormulaScheme()) || !checkDesObj2.getFormulaCode().equals(formulaMapping.getFormulaCode()) || !StringUtils.hasLength(formCode = (String)formKey2FormCode.get(checkDesObj2.getFormKey())) || !formCode.equals(formulaMapping.getFormCode())) continue;
                    flag = true;
                    checkError.addCheckErrorDescList(this.getTransCheckError(checkDesObj2, formulaMapping.getmFormulaCode(), true));
                }
                if (flag) continue;
                checkError.addCheckErrorDescList(this.getTransCheckError(checkDesObj2, "", true));
            }
            if (checkError.getCheckErrorDescList().size() == 0) {
                logger.info("\u516c\u5f0f\u9519\u8bef\u8bf4\u660e\uff1a\u5355\u4f4d " + unitCode + "\u4e0b\u65e0\u516c\u5f0f\u9519\u8bef\u8bf4\u660e\u3002");
                continue;
            }
            res.put(unitCode, checkError);
        }
        return res;
    }

    private List<CheckDesObj> getCheckErrorDesObjs(String formSchemeKey, List<String> formKeys, String dwEntityId, List<String> unitCodes, String periodValue) {
        List formulaSchemes = this.formulaRunTime.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        List formulaSchemeKeys = formulaSchemes.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        HashMap<String, DimensionValue> dimMap = new HashMap<String, DimensionValue>();
        DimensionValue mainDimValue = new DimensionValue();
        mainDimValue.setName(dwEntityId);
        mainDimValue.setValue(String.join((CharSequence)";", unitCodes));
        DimensionValue periodDimValue = new DimensionValue();
        periodDimValue.setName("DATATIME");
        periodDimValue.setValue(periodValue);
        dimMap.put("MD_ORG", mainDimValue);
        dimMap.put("DATATIME", periodDimValue);
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimMap, formSchemeKey, null);
        CheckDesQueryParam param = new CheckDesQueryParam();
        param.setFormKey(formKeys);
        param.setFormSchemeKey(formSchemeKey);
        param.setDimensionCollection(dimensionCollection);
        param.setFormulaSchemeKey(formulaSchemeKeys);
        return this.checkErrorDescriptionService.queryFormulaCheckDes(param);
    }

    private CheckErrorDesc getTransCheckError(CheckDesObj checkDesObj, String mFormulaCode, boolean isFixedArea) {
        CheckErrorDesc checkErrorDesc = new CheckErrorDesc();
        String formulaCode = mFormulaCode;
        if (!StringUtils.hasLength(mFormulaCode)) {
            formulaCode = checkDesObj.getFormulaCode();
        }
        checkErrorDesc.setFormulaCode(formulaCode);
        FormulaDefine formulaDefine = this.formulaRunTimeController.findFormulaDefine(checkDesObj.getFormulaCode(), checkDesObj.getFormulaSchemeKey());
        if (formulaDefine != null) {
            checkErrorDesc.setFormulaExp(formulaDefine.getExpression());
        }
        if (StringUtils.hasLength(checkDesObj.getFloatId()) && "ID:null;".equals(checkDesObj.getFloatId())) {
            checkErrorDesc.setFloatId("96FEF1501750CF431CBD2872CA673BB2");
        } else {
            String floatId = checkDesObj.getFloatId();
            String[] dimkvList = floatId.split(";");
            StringBuilder dimV = new StringBuilder();
            for (String dimkv : dimkvList) {
                if ("null".equals(dimkv.split(":")[1])) continue;
                dimV.append(dimkv.split(":")[1]);
            }
            checkErrorDesc.setFloatCode(dimV.toString());
        }
        checkErrorDesc.setRecid(GUID.randomID());
        CheckDescription checkDescription = checkDesObj.getCheckDescription();
        if (checkDescription != null) {
            checkErrorDesc.setDescription(checkDescription.getDescription());
            checkErrorDesc.setModifyTime(checkDescription.getUpdateTime());
        }
        return checkErrorDesc;
    }
}

