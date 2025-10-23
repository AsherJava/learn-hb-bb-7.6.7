/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.SecurityLevel
 *  com.jiuqi.np.authz2.service.SecurityLevelService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.dataentity.entity.IDataEntity
 *  com.jiuqi.nr.dataentity.entity.IDataEntityRow
 *  com.jiuqi.nr.dataentity.service.DataEntityService
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.authority.util.ExcelUtils
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.impl.NvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.data.access.api.impl;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.SecurityLevel;
import com.jiuqi.np.authz2.service.SecurityLevelService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.access.api.IStateSecretLevelService;
import com.jiuqi.nr.data.access.api.param.BatchSecretLevelInfo;
import com.jiuqi.nr.data.access.api.response.ExportData;
import com.jiuqi.nr.data.access.api.response.ReturnInfo;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.SecretLevel;
import com.jiuqi.nr.data.access.param.SecretLevelDim;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.util.NvwaDataEngineQueryUtil;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.entity.IDataEntityRow;
import com.jiuqi.nr.dataentity.service.DataEntityService;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.authority.util.ExcelUtils;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.impl.NvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class StateSecretLevelServiceImpl
implements IStateSecretLevelService {
    private static final Logger logger = LoggerFactory.getLogger(StateSecretLevelServiceImpl.class);
    @Resource
    IRunTimeViewController runtimeView;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Resource
    SecurityLevelService securityLevelService;
    @Autowired
    DataAccesslUtil dataAccesslUtil;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    NvwaDataEngineQueryUtil nvwaDataEngineQueryUtil;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private DataEntityService dataEntityService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private FileUploadOssService fileUploadOssService;
    private static final String[] titleList = new String[]{"\u5e8f\u53f7", "\u5355\u4f4dID", "\u5355\u4f4d\u4ee3\u7801", "\u5355\u4f4d\u540d\u79f0", "\u5bc6\u7ea7"};
    private static final int INDEXNUM = 0;
    private static final int UNITIDNUM = 1;
    private static final int UNITCODENUM = 2;
    private static final int UNITNAMENUM = 3;
    private static final int SECRETLEVELNUM = 4;
    public static final String SEPARATOR = File.separator;
    public static final String FILENAME = "\u5bc6\u7ea7";
    public static final String SECRETLEVEL = "SECRET_LEVEL";
    public static final String TABLENAME_SECRET = "NR_SECRETLEVEL_";
    public static final String SECRET_LEVEL = "SL_LEVEL";

    @Override
    public boolean secretLevelEnable(String taskKey) {
        boolean securityLevelEnabled = this.securityLevelService.isSecurityLevelEnabled();
        String secretLevel = this.taskOptionController.getValue(taskKey, SECRETLEVEL);
        return securityLevelEnabled && "1".equals(secretLevel);
    }

    @Override
    public List<SecretLevel> getSecretLevelItems() {
        List systemSecurityLevels = this.securityLevelService.getSystemSecurityLevels();
        ArrayList<SecretLevel> secretLevels = new ArrayList<SecretLevel>();
        for (SecurityLevel securityLevel : systemSecurityLevels) {
            if (!this.securityLevelService.canAccess(securityLevel.getName())) continue;
            SecretLevel secretLevel = new SecretLevel(securityLevel.getName(), securityLevel.getTitle());
            secretLevels.add(secretLevel);
        }
        if (secretLevels.isEmpty()) {
            SecurityLevel securityLevel = (SecurityLevel)systemSecurityLevels.get(0);
            SecretLevel secretLevel = new SecretLevel(securityLevel.getName(), securityLevel.getTitle());
            secretLevels.add(secretLevel);
        }
        return secretLevels;
    }

    @Override
    public SecretLevel getSecretLevelItem(String servetLevelName) {
        List<SecretLevel> secretLevels = this.getSecretLevelItems();
        if (StringUtils.isNotEmpty((String)servetLevelName)) {
            for (SecretLevel secretLevel : secretLevels) {
                if (!servetLevelName.equals(secretLevel.getName()) && !servetLevelName.equals(secretLevel.getTitle())) continue;
                return secretLevel;
            }
            try {
                SecurityLevel systemSecurityLevel = this.securityLevelService.getSystemSecurityLevel(servetLevelName);
                if (systemSecurityLevel == null) {
                    return secretLevels.get(0);
                }
                return SecretLevel.NOSEE;
            }
            catch (Exception e) {
                logger.error("\u67e5\u8be2\u7cfb\u7edf\u5bc6\u7ea7\u62a5\u9519\uff1a" + servetLevelName, e);
                return secretLevels.get(0);
            }
        }
        return secretLevels.get(0);
    }

    @Override
    public boolean canAccess(SecretLevel sercetLevelItem) {
        if (sercetLevelItem == null) {
            return false;
        }
        if (SecretLevel.NOSEE.equals(sercetLevelItem)) {
            return false;
        }
        return this.securityLevelService.canAccess(sercetLevelItem.getName());
    }

    @Override
    public boolean compareSercetLevel(SecretLevel sercetLevelItem1, SecretLevel sercetLevelItem2) {
        if (SecretLevel.NOSEE.equals(sercetLevelItem1) || SecretLevel.NOSEE.equals(sercetLevelItem2)) {
            return false;
        }
        if (sercetLevelItem2 == null) {
            return true;
        }
        if (sercetLevelItem1 == null) {
            return false;
        }
        String sercetLevel1 = sercetLevelItem1.getName();
        String sercetLevel2 = sercetLevelItem2.getName();
        if (StringUtils.isNotEmpty((String)sercetLevel1) && StringUtils.isNotEmpty((String)sercetLevel2)) {
            return sercetLevel1.compareTo(sercetLevel2) >= 0;
        }
        return false;
    }

    @Override
    public void saveSecretLevel(DimensionValueSet masterKey, String formSchemeKey, SecretLevel secretLevel) {
        INvwaDataSet secretLevelDataTable = this.getSercetLevelDataTable(formSchemeKey, masterKey, false);
        TableModelDefine tableDefine = this.getTableModelDefine(formSchemeKey);
        ArrayKey rowKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(tableDefine.getName(), secretLevelDataTable, masterKey);
        INvwaDataRow secretLevelRow = secretLevelDataTable.findRow(rowKey);
        NvwaUpdatableDataSet nvwaUpdatableDataSet = (NvwaUpdatableDataSet)secretLevelDataTable;
        if (secretLevelRow == null) {
            try {
                secretLevelRow = nvwaUpdatableDataSet.appendRow();
            }
            catch (Exception e) {
                throw new AccessException(e.getMessage());
            }
        }
        this.setSecretLevelInfo(secretLevelRow, formSchemeKey, secretLevel);
        try {
            nvwaUpdatableDataSet.commitChanges(new DataAccessContext(this.dataModelService));
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @Override
    public void batchSaveSecretLevel(DimensionValueSet masterKey, String formSchemeKey, List<SecretLevelDim> secretLevelinfos) {
        INvwaDataSet sercetLevelDataTable = this.getSercetLevelDataTable(formSchemeKey, masterKey, false);
        TableModelDefine tableDefine = this.getTableModelDefine(formSchemeKey);
        NvwaUpdatableDataSet updatableDataSet = (NvwaUpdatableDataSet)sercetLevelDataTable;
        try {
            for (SecretLevelDim level : secretLevelinfos) {
                ArrayKey rowKey = this.buildRowKey(tableDefine.getName(), sercetLevelDataTable, level.getDimensionValueSet(), formSchemeKey);
                INvwaDataRow secretLevelRow = sercetLevelDataTable.findRow(rowKey);
                if (secretLevelRow == null) {
                    secretLevelRow = updatableDataSet.appendRow();
                    this.nvwaDataEngineQueryUtil.setRowKey(tableDefine.getName(), secretLevelRow, level.getDimensionValueSet());
                }
                this.setSecretLevelInfo(secretLevelRow, formSchemeKey, level.getSecretLevel());
            }
            updatableDataSet.commitChanges(new DataAccessContext(this.dataModelService));
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @Override
    public SecretLevel getSecretLevel(DimensionValueSet masterKey, String formSchemeKey) {
        TableModelDefine secretTable;
        String sercetLevelTableName;
        ArrayKey rowKey;
        INvwaDataSet sercetLevelDataTable = this.getSercetLevelDataTable(formSchemeKey, masterKey, true);
        INvwaDataRow findRow = sercetLevelDataTable.findRow(rowKey = this.buildRowKey(sercetLevelTableName = (secretTable = this.getTableModelDefine(formSchemeKey)).getName(), sercetLevelDataTable, masterKey, formSchemeKey));
        if (findRow != null) {
            return this.getSercetLevelInfo(sercetLevelDataTable, findRow);
        }
        List<SecretLevel> secretLevels = this.getSecretLevelItems();
        return secretLevels.get(0);
    }

    @Override
    public List<SecretLevel> getSecretLevelItems(DimensionValueSet masterKey, String formSchemeKey) {
        SecretLevel formSecretLevelInfo = this.getSecretLevel(masterKey, formSchemeKey);
        List<SecretLevel> secretLevels = this.getSecretLevelItems();
        ArrayList<SecretLevel> fileSecretLevels = new ArrayList<SecretLevel>();
        for (SecretLevel secretLevel : secretLevels) {
            if (!this.compareSercetLevel(formSecretLevelInfo, secretLevel)) continue;
            fileSecretLevels.add(secretLevel);
        }
        return fileSecretLevels;
    }

    @Override
    public Map<DimensionValueSet, SecretLevel> batchQuerySecretLevels(DimensionValueSet masterKey, String formSchemeKey) {
        HashMap<DimensionValueSet, SecretLevel> sercetLevelInfos = new HashMap<DimensionValueSet, SecretLevel>();
        TableModelDefine tableDefine = this.getTableModelDefine(formSchemeKey);
        INvwaDataSet sercetLevelDataTable = this.getSercetLevelDataTable(formSchemeKey, masterKey, true);
        for (int rowIndex = 0; rowIndex < sercetLevelDataTable.size(); ++rowIndex) {
            INvwaDataRow dataRow = sercetLevelDataTable.getRow(rowIndex);
            DimensionValueSet rowKey = this.nvwaDataEngineQueryUtil.convertRowKeyToDimensionValueSet(tableDefine.getName(), dataRow.getRowKey(), sercetLevelDataTable.getRowKeyColumns());
            DimensionValueSet filterKey = DimensionValueSetUtil.filterDimensionValueSet(rowKey, "SL_FORMSCHEMEKEY");
            sercetLevelInfos.put(filterKey, this.getSercetLevelInfo(sercetLevelDataTable, dataRow));
        }
        return sercetLevelInfos;
    }

    @Override
    public INvwaDataSet getSercetLevelDataTable(String formSchemeKey, DimensionValueSet masterKey, boolean readOnly) {
        DimensionValueSetUtil.clearEmptyDimensionValue(masterKey);
        TableModelDefine tableDefine = this.getTableModelDefine(formSchemeKey);
        List fields = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        masterKey.setValue("SL_FORMSCHEMEKEY", (Object)formSchemeKey);
        return this.nvwaDataEngineQueryUtil.queryDataSetWithRowKey(tableDefine.getName(), masterKey, fields, new ArrayList<String>(), new HashMap<String, Boolean>(), readOnly);
    }

    @Override
    public TableModelDefine getTableModelDefine(String formSchemeKey) {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        String sercetLevelTableName = this.dataAccesslUtil.getTableName(formScheme, "NR_SECRETLEVEL_%s");
        TableModelDefine sercetLevelTable = this.dataModelService.getTableModelDefineByName(sercetLevelTableName);
        return sercetLevelTable;
    }

    @Override
    public void extractPrePeriodSecretLevel(DimensionValueSet masterKey, String taskKey, String formSchemeKey) {
        boolean secretEnable = this.secretLevelEnable(taskKey);
        if (!secretEnable) {
            return;
        }
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        EntityViewDefine entityView = this.dataAccesslUtil.queryDWEntityViewDefine(formScheme, taskDefine);
        ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
        DimensionValueSet queryEntityKey = new DimensionValueSet();
        queryEntityKey.setValue("DATATIME", masterKey.getValue("DATATIME"));
        IDataEntity entityData = null;
        try {
            entityData = this.dataEntityService.getIEntityTable(entityView, executorContext, queryEntityKey, formSchemeKey);
        }
        catch (Exception e2) {
            logger.error("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u5f02\u5e38\uff01");
        }
        ArrayList allEntitys = new ArrayList();
        if (entityData != null) {
            IDataEntityRow allRows = entityData.getAllRow();
            allEntitys.addAll(allRows.getRowList().stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList()));
        }
        String currPeriod = String.valueOf(masterKey.getValue("DATATIME"));
        DimensionValueSet queryKey = new DimensionValueSet(masterKey);
        PeriodWrapper periodWrapper = new PeriodWrapper(currPeriod);
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
        periodProvider.priorPeriod(periodWrapper);
        queryKey.setValue("DATATIME", (Object)periodWrapper.toString());
        INvwaUpdatableDataSet priorSercetLevelTableDatas = (INvwaUpdatableDataSet)this.getSercetLevelDataTable(formSchemeKey, queryKey, false);
        if (priorSercetLevelTableDatas.size() == 0) {
            return;
        }
        TableModelDefine tableDefine = this.getTableModelDefine(formSchemeKey);
        String dimensionName = this.dataAccesslUtil.getDwDimensionName(entityView.getEntityId());
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        for (INvwaDataRow nextRow : priorSercetLevelTableDatas) {
            String unitKey;
            DimensionValueSet priorRowKeys = this.nvwaDataEngineQueryUtil.convertRowKeyToDimensionValueSet(tableDefine.getName(), nextRow.getRowKey(), priorSercetLevelTableDatas.getRowKeyColumns());
            if (priorRowKeys.hasValue(dimensionName) && !allEntitys.contains(unitKey = priorRowKeys.getValue(dimensionName).toString())) continue;
            DimensionValueSet currRowKeys = new DimensionValueSet(priorRowKeys);
            currRowKeys.setValue("DATATIME", (Object)currPeriod);
            INvwaDataRow nvwaDataRow = null;
            INvwaUpdatableDataSet currSercetLevelTableDatas = (INvwaUpdatableDataSet)this.getSercetLevelDataTable(formSchemeKey, currRowKeys, false);
            if (currSercetLevelTableDatas == null) {
                throw new AccessException("\u83b7\u53d6\u6570\u636e\u96c6\u5931\u8d25\uff01");
            }
            if (currSercetLevelTableDatas.size() == 0) {
                try {
                    nvwaDataRow = currSercetLevelTableDatas.appendRow();
                    this.nvwaDataEngineQueryUtil.setRowKey(tableDefine.getName(), nvwaDataRow, currRowKeys);
                }
                catch (Exception e3) {
                    throw new AccessException(e3.getMessage());
                }
            } else {
                nvwaDataRow = currSercetLevelTableDatas.getRow(0);
            }
            SecretLevel secretLevelInfo = this.getSercetLevelInfo((INvwaDataSet)currSercetLevelTableDatas, nextRow);
            this.setSecretLevelInfo(nvwaDataRow, formSchemeKey, secretLevelInfo);
            try {
                currSercetLevelTableDatas.commitChanges(context);
            }
            catch (Exception e4) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u5973\u5a32\u63d0\u4ea4\u6570\u636e\u66f4\u65b0\u51fa\u9519" + e4.getMessage(), e4);
            }
        }
    }

    @Override
    public void exportSecretLevel(DimensionValueSet masterKey, String taskKey, String formSchemeKey, HttpServletResponse response) {
        boolean secretEnable = this.secretLevelEnable(taskKey);
        if (!secretEnable) {
            return;
        }
        try {
            ExportData secretLevelExportData = this.exportSecretLevelData(masterKey, taskKey, formSchemeKey);
            if (null != secretLevelExportData) {
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(secretLevelExportData.getFileName(), "UTF-8"));
                response.setContentType("application/octet-stream");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(secretLevelExportData.getData());
                response.flushBuffer();
            }
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private ExportData exportSecretLevelData(DimensionValueSet masterKey, String taskKey, String formSchemeKey) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        String dimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
        Map<DimensionValueSet, SecretLevel> querySecretLevels = this.batchQuerySecretLevels(masterKey, formSchemeKey);
        HashMap<String, String> unitSecretLevelMap = new HashMap<String, String>();
        for (Map.Entry<DimensionValueSet, SecretLevel> entry : querySecretLevels.entrySet()) {
            String unitKey = String.valueOf(entry.getKey().getValue(dimensionName));
            unitSecretLevelMap.put(unitKey, entry.getValue().getTitle());
        }
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        EntityViewDefine entityView = this.dataAccesslUtil.queryDWEntityViewDefine(formScheme, taskDefine);
        ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
        DimensionValueSet queryEntityKey = new DimensionValueSet();
        queryEntityKey.setValue("DATATIME", masterKey.getValue("DATATIME"));
        IDataEntity entityData = null;
        try {
            entityData = this.dataEntityService.getIEntityTable(entityView, executorContext, queryEntityKey, formSchemeKey);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u5f02\u5e38\uff01");
        }
        IDataEntityRow entityRow = entityData.getAllRow();
        List rows = entityRow.getRowList();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(FILENAME);
        sheet.setColumnWidth(0, 2048);
        sheet.setColumnWidth(1, 2560);
        sheet.setColumnWidth(2, 6400);
        sheet.setColumnWidth(3, 6400);
        sheet.setColumnWidth(4, 6400);
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle style = workbook.createCellStyle();
        style = this.createCellStyle(style);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("\u5b8b\u4f53");
        font.setBold(true);
        style.setFont(font);
        XSSFCell cell = null;
        for (int i = 0; i < titleList.length; ++i) {
            cell = row.createCell(i);
            cell.setCellValue(titleList[i]);
            cell.setCellStyle(style);
        }
        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        if (!CollectionUtils.isEmpty(rows)) {
            for (int i = 0; i < rows.size(); ++i) {
                row = sheet.createRow(i + 1);
                IEntityRow unitData = (IEntityRow)rows.get(i);
                block14: for (int n = 0; n < titleList.length; ++n) {
                    XSSFCell cellData = row.createCell(n);
                    switch (n) {
                        case 0: {
                            cellData.setCellValue(i + 1);
                            cellData.setCellStyle(style2);
                            continue block14;
                        }
                        case 1: {
                            cellData.setCellValue(unitData.getEntityKeyData());
                            cellData.setCellStyle(style);
                            continue block14;
                        }
                        case 2: {
                            cellData.setCellValue(unitData.getCode());
                            cellData.setCellStyle(style);
                            continue block14;
                        }
                        case 3: {
                            cellData.setCellValue(unitData.getTitle());
                            cellData.setCellStyle(style);
                            continue block14;
                        }
                        case 4: {
                            if (unitSecretLevelMap.containsKey(unitData.getEntityKeyData())) {
                                cellData.setCellValue((String)unitSecretLevelMap.get(unitData.getEntityKeyData()));
                            }
                            cellData.setCellStyle(style);
                            continue block14;
                        }
                        default: {
                            logger.info("default");
                        }
                    }
                }
            }
        }
        List<SecretLevel> secretLevelItems = this.getSecretLevelItems();
        String[] actions = (String[])secretLevelItems.stream().map(SecretLevel::getTitle).toArray(String[]::new);
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)dvHelper.createExplicitListConstraint(actions);
        CellRangeAddressList addressList = new CellRangeAddressList(1, 65535, 4, 4);
        DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
        validation.setSuppressDropDownArrow(true);
        validation.setShowErrorBox(true);
        sheet.addValidationData(validation);
        if (null != workbook) {
            ByteArrayOutputStream os = new ByteArrayOutputStream(0xA00000);
            try {
                workbook.write(os);
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            byte[] byteArray = os.toByteArray();
            ExportData formulaExportData = new ExportData("\u5bc6\u7ea7.xlsx", byteArray);
            return formulaExportData;
        }
        return null;
    }

    private XSSFCellStyle createCellStyle(XSSFCellStyle style) {
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_80_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        return style;
    }

    @Override
    public ReturnInfo importSecretLevel(String fileKey, DimensionValueSet masterKey, String taskKey, String formSchemeKey) {
        block5: {
            boolean secretEnable = this.secretLevelEnable(taskKey);
            if (!secretEnable) {
                ReturnInfo returnInfo = new ReturnInfo();
                returnInfo.setMessage("error");
                return returnInfo;
            }
            try {
                byte[] downloadFileByteFormTemp = this.fileUploadOssService.downloadFileByteFormTemp(fileKey);
                ByteArrayInputStream fileInputStream = new ByteArrayInputStream(downloadFileByteFormTemp);
                Workbook excel = ExcelUtils.create((InputStream)fileInputStream);
                if (excel == null) break block5;
                Sheet secretLevelSheet = excel.getSheet(FILENAME);
                boolean isCanImport = this.checkImportSheet(secretLevelSheet);
                List<Object> secretLevelList = new ArrayList();
                if (isCanImport) {
                    secretLevelList = this.checkImportDataInfo(secretLevelSheet, masterKey, formSchemeKey, formSchemeKey);
                    if (secretLevelList == null || secretLevelList.isEmpty()) {
                        ReturnInfo returnInfo = new ReturnInfo();
                        returnInfo.setMessage("\u6ca1\u6709\u53ef\u5bfc\u5165\u7684\u5bc6\u7ea7");
                        return returnInfo;
                    }
                    BatchSecretLevelInfo batchSecretLevelInfo = new BatchSecretLevelInfo();
                    batchSecretLevelInfo.setDimensionValueSet(masterKey);
                    batchSecretLevelInfo.setSercetLevels(secretLevelList);
                    this.batchSaveSecretLevel(masterKey, formSchemeKey, secretLevelList);
                    break block5;
                }
                ReturnInfo returnInfo = new ReturnInfo();
                returnInfo.setMessage("\u5bfc\u5165\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\uff0c\u8bf7\u5728\u6a21\u677f\u57fa\u7840\u4e0a\u8fdb\u884c\u4fee\u6539\uff01\uff01\uff01");
                return returnInfo;
            }
            catch (IOException e) {
                ReturnInfo returnInfo = new ReturnInfo();
                returnInfo.setMessage("error");
                return returnInfo;
            }
        }
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage("success");
        return returnInfo;
    }

    private boolean checkImportSheet(Sheet secretLevelSheet) {
        boolean isCanImport = true;
        if (secretLevelSheet == null) {
            isCanImport = false;
        } else {
            Row headRow = secretLevelSheet.getRow(0);
            boolean isIndex = headRow.getCell(0).getStringCellValue().equals(titleList[0]);
            boolean isUnitId = headRow.getCell(1).getStringCellValue().equals(titleList[1]);
            boolean isUnitCode = headRow.getCell(2).getStringCellValue().equals(titleList[2]);
            boolean isUnitName = headRow.getCell(3).getStringCellValue().equals(titleList[3]);
            boolean isSecretLevel = headRow.getCell(4).getStringCellValue().equals(titleList[4]);
            if (!(isIndex && isUnitId && isUnitCode && isUnitName && isSecretLevel)) {
                isCanImport = false;
            }
        }
        return isCanImport;
    }

    private List<SecretLevelDim> checkImportDataInfo(Sheet secretLevelSheet, DimensionValueSet masterKey, String taskKey, String formSchemeKey) {
        ArrayList<SecretLevelDim> checkInfoResultList = new ArrayList<SecretLevelDim>();
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        String dimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
        EntityViewDefine entityView = this.dataAccesslUtil.queryDWEntityViewDefine(formScheme, taskDefine);
        ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
        DimensionValueSet queryEntityKey = new DimensionValueSet();
        queryEntityKey.setValue("DATATIME", masterKey.getValue("DATATIME"));
        IDataEntity entityData = null;
        try {
            entityData = this.dataEntityService.getIEntityTable(entityView, executorContext, queryEntityKey, formSchemeKey);
        }
        catch (Exception e) {
            throw new AccessException(e.getMessage());
        }
        IDataEntityRow entityRow = entityData.getAllRow();
        List rows = entityRow.getRowList();
        List allEntityKeysList = rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        if (secretLevelSheet.getSheetName().equals(FILENAME)) {
            for (Row row : secretLevelSheet) {
                if (row.getRowNum() == 0) continue;
                try {
                    SecretLevel secretLevelItem;
                    SecretLevelDim secretLevelInfo = new SecretLevelDim();
                    String unitID = row.getCell(1).toString();
                    if (!allEntityKeysList.contains(unitID)) continue;
                    masterKey.setValue(dimensionName, (Object)unitID);
                    secretLevelInfo.setDimensionValueSet(masterKey);
                    String secretLevel = row.getCell(4).toString();
                    if (!StringUtils.isNotEmpty((String)secretLevel) || (secretLevelItem = this.getSecretLevelItem(secretLevel)) == null) continue;
                    secretLevelInfo.setSecretLevel(secretLevelItem);
                    checkInfoResultList.add(secretLevelInfo);
                }
                catch (Exception e) {
                    return checkInfoResultList;
                }
            }
        }
        return checkInfoResultList;
    }

    private SecretLevel getSercetLevelInfo(INvwaDataSet sercetLevelDataTable, INvwaDataRow dataRow) {
        SecretLevel sercetLevelItem = new SecretLevel();
        Optional<Column> secretLevelField = sercetLevelDataTable.getMetadata().getColumns().stream().filter(e -> ((NvwaQueryColumn)e.getInfo()).getColumnModel().getName().equals(SECRET_LEVEL)).findFirst();
        if (!secretLevelField.isPresent()) {
            return sercetLevelItem;
        }
        ColumnModelDefine secretColumn = ((NvwaQueryColumn)secretLevelField.get().getInfo()).getColumnModel();
        Object value = dataRow.getValue(secretColumn);
        sercetLevelItem = this.getSecretLevelItem(String.valueOf(value));
        return sercetLevelItem;
    }

    private void setSecretLevelInfo(INvwaDataRow secretLevelRow, String formSchemeKey, SecretLevel sercetLevelInfo) {
        TableModelDefine tableDefine = this.getTableModelDefine(formSchemeKey);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        List columNames = columns.stream().map(e -> e.getCode()).collect(Collectors.toList());
        block16: for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine field = (ColumnModelDefine)columns.get(i);
            if (field == null) continue;
            int index = columNames.indexOf(field.getCode());
            switch (field.getCode()) {
                case "SL_ID": {
                    secretLevelRow.setValue(index, (Object)UUID.randomUUID().toString());
                    continue block16;
                }
                case "SL_FORMSCHEMEKEY": {
                    secretLevelRow.setValue(index, (Object)formSchemeKey);
                    continue block16;
                }
                case "SL_FORMKEY": {
                    secretLevelRow.setValue(index, (Object)"");
                    continue block16;
                }
                case "SL_LEVEL": {
                    secretLevelRow.setValue(index, (Object)sercetLevelInfo.getName());
                    continue block16;
                }
                case "SL_USER": {
                    secretLevelRow.setValue(index, (Object)NpContextHolder.getContext().getUserId());
                    continue block16;
                }
                case "SL_UPDATETIME": {
                    secretLevelRow.setValue(index, (Object)new Time(new Date().getTime()));
                    continue block16;
                }
            }
        }
    }

    private ArrayKey buildRowKey(String tableName, INvwaDataSet sercetLevelDataTable, DimensionValueSet masterKey, String formSchemeKey) {
        masterKey.setValue("SL_FORMSCHEMEKEY", (Object)formSchemeKey);
        ArrayKey arrayKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(tableName, sercetLevelDataTable, masterKey);
        return arrayKey;
    }
}

