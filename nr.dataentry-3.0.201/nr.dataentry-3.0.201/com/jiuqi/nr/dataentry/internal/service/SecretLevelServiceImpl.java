/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.io.source.ByteArrayOutputStream
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.SecurityLevelService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.api.IStateSecretLevelService
 *  com.jiuqi.nr.data.access.param.SecretLevel
 *  com.jiuqi.nr.data.access.param.SecretLevelDim
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.BatchSecretLevelInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.params.output.SecretLevelInfo
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.authority.util.ExcelUtils
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.xlib.utils.StringUtil
 *  javax.annotation.Resource
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.SecurityLevelService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.api.IStateSecretLevelService;
import com.jiuqi.nr.data.access.param.SecretLevel;
import com.jiuqi.nr.data.access.param.SecretLevelDim;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.BatchSecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.authority.util.ExcelUtils;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.xlib.utils.StringUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
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

@Service
public class SecretLevelServiceImpl
implements ISecretLevelService {
    private static final Logger logger = LoggerFactory.getLogger(SecretLevelServiceImpl.class);
    @Resource
    private IRunTimeViewController runtimeView;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Resource
    private SecurityLevelService securityLevelService;
    @Resource
    private IJtableParamService jtableParamService;
    @Resource
    private IJtableEntityService jtableEntityService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private FileUploadOssService fileUploadOssService;
    @Autowired
    private IStateSecretLevelService stateSecretLevelService;
    private static final String[] titleList = new String[]{"\u5e8f\u53f7", "\u5355\u4f4dID", "\u5355\u4f4d\u4ee3\u7801", "\u5355\u4f4d\u540d\u79f0", "\u5bc6\u7ea7"};
    private static final int INDEX_NUM = 0;
    private static final int UNIT_ID_NUM = 1;
    private static final int UNIT_CODE_NUM = 2;
    private static final int UNIT_NAME_NUM = 3;
    private static final int SECRET_LEVEL_NUM = 4;
    public static final String SEPARATOR = File.separator;
    public static final String FILE_NAME = "\u5bc6\u7ea7";
    private Function<List<SecretLevel>, List<SecretLevelItem>> convertToSecretItemList = listLevle -> listLevle.stream().map(e -> new SecretLevelItem(e.getName(), e.getTitle())).collect(Collectors.toList());
    private Function<SecretLevel, SecretLevelItem> convertSecretItem = levle -> new SecretLevelItem(levle.getName(), levle.getTitle());
    private Function<SecretLevelItem, SecretLevel> convertSecretLevel = levle -> new SecretLevel(levle.getName(), levle.getTitle());

    public boolean secretLevelEnable(String taskKey) {
        boolean securityLevelEnabled = this.securityLevelService.isSecurityLevelEnabled();
        String secretLevel = this.taskOptionController.getValue(taskKey, "SECRET_LEVEL");
        return securityLevelEnabled && "1".equals(secretLevel);
    }

    public List<SecretLevelItem> getSecretLevelItems() {
        List secretLevelItems = this.stateSecretLevelService.getSecretLevelItems();
        return this.convertToSecretItemList.apply(secretLevelItems);
    }

    public SecretLevelItem getSecretLevelItem(String servetLevelName) {
        SecretLevel secretLevel = this.stateSecretLevelService.getSecretLevelItem(servetLevelName);
        return this.convertSecretItem.apply(secretLevel);
    }

    public boolean canAccess(SecretLevelItem sercetLevelItem) {
        return this.stateSecretLevelService.canAccess(this.convertSecretLevel.apply(sercetLevelItem));
    }

    public boolean compareSercetLevel(SecretLevelItem sercetLevelItem1, SecretLevelItem sercetLevelItem2) {
        return this.stateSecretLevelService.compareSercetLevel(this.convertSecretLevel.apply(sercetLevelItem1), this.convertSecretLevel.apply(sercetLevelItem2));
    }

    public SecretLevelInfo getSecretLevel(JtableContext jtableContext) {
        Map dimensionSet = jtableContext.getDimensionSet();
        DimensionValueSet masterKey = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        SecretLevel secretLevel = this.stateSecretLevelService.getSecretLevel(masterKey, jtableContext.getFormSchemeKey());
        SecretLevelInfo secretLevelInfo = new SecretLevelInfo();
        secretLevelInfo.setJtableContext(jtableContext);
        secretLevelInfo.setSecretLevelItem(this.convertSecretItem.apply(secretLevel));
        return secretLevelInfo;
    }

    public List<SecretLevelItem> getSecretLevelItems(JtableContext jtableContext) {
        Map dimensionSet = jtableContext.getDimensionSet();
        DimensionValueSet masterKey = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        List secretLevels = this.stateSecretLevelService.getSecretLevelItems(masterKey, jtableContext.getFormSchemeKey());
        return this.convertToSecretItemList.apply(secretLevels);
    }

    public void setSecretLevel(SecretLevelInfo sercetLevelInfo) {
        boolean secretEnable = this.secretLevelEnable(sercetLevelInfo.getJtableContext().getTaskKey());
        if (!secretEnable) {
            return;
        }
        JtableContext jtableContext = sercetLevelInfo.getJtableContext();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)jtableContext.getDimensionSet());
        this.stateSecretLevelService.saveSecretLevel(dimensionValueSet, jtableContext.getFormSchemeKey(), this.convertSecretLevel.apply(sercetLevelInfo.getSecretLevelItem()));
    }

    public List<SecretLevelInfo> querySecretLevels(JtableContext jtableContext) {
        ArrayList<SecretLevelInfo> res = new ArrayList<SecretLevelInfo>();
        Map dimensionSet = jtableContext.getDimensionSet();
        DimensionValueSet masterKey = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        Map secretLevels = this.stateSecretLevelService.batchQuerySecretLevels(masterKey, jtableContext.getFormSchemeKey());
        secretLevels.forEach((key, value) -> {
            SecretLevelInfo secretLevelInfo = new SecretLevelInfo();
            secretLevelInfo.setSecretLevelItem(this.convertSecretItem.apply((SecretLevel)value));
            JtableContext context = new JtableContext(jtableContext);
            context.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)key));
            secretLevelInfo.setJtableContext(context);
            res.add(secretLevelInfo);
        });
        return res;
    }

    public void batchSaveBatchSecretLevel(BatchSecretLevelInfo batchSecretLevelInfo) {
        JtableContext jtableContext = batchSecretLevelInfo.getJtableContext();
        Map dimensionSet = jtableContext.getDimensionSet();
        DimensionValueSet masterKey = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        List secretLevelInofs = batchSecretLevelInfo.getSercetLevels();
        List secretLevelDim = secretLevelInofs.stream().map(item -> {
            JtableContext context = item.getJtableContext();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet());
            SecretLevel secretLevel = this.convertSecretLevel.apply(item.getSecretLevelItem());
            return new SecretLevelDim(dimensionValueSet, secretLevel);
        }).collect(Collectors.toList());
        this.stateSecretLevelService.batchSaveSecretLevel(masterKey, jtableContext.getFormSchemeKey(), secretLevelDim);
    }

    public void extractPrePeriodSecretLevel(JtableContext jtableContext) {
        Map dimensionSet = jtableContext.getDimensionSet();
        DimensionValueSet masterKey = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        this.stateSecretLevelService.extractPrePeriodSecretLevel(masterKey, jtableContext.getTaskKey(), jtableContext.getFormSchemeKey());
    }

    private TableModelDefine getSercetLevelTable(JtableContext jtableContext) {
        JtableContext excutorJtableContext = new JtableContext(jtableContext);
        TableModelDefine sercetLevelTable = this.stateSecretLevelService.getTableModelDefine(jtableContext.getFormSchemeKey());
        return sercetLevelTable;
    }

    private void setQueryModel(JtableContext jtableContext, NvwaQueryModel queryModel) {
        TableModelDefine sercetLevelTable = this.getSercetLevelTable(jtableContext);
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(sercetLevelTable.getID());
        for (ColumnModelDefine column : allColumns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        Map<String, String> dimensionNameColumnMap = this.getDimensionNameColumnMap(jtableContext, sercetLevelTable.getName());
        Map dimensionSet = jtableContext.getDimensionSet();
        for (String code : dimensionNameColumnMap.keySet()) {
            for (ColumnModelDefine column : allColumns) {
                String dimName;
                String dimValue;
                if (!column.getCode().equals(code)) continue;
                ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(sercetLevelTable.getID(), code);
                if (dimensionNameColumnMap.get(code) == null || dimensionNameColumnMap.get(code) == "" || (dimValue = ((DimensionValue)dimensionSet.get(dimName = dimensionNameColumnMap.get(code))).getValue()) == null || dimValue == "") continue;
                queryModel.getColumnFilters().put(columnModelDefine, dimValue);
            }
        }
    }

    private ArrayKey getSercetLevelRowKeysValue(INvwaUpdatableDataSet nvwaUpdatableDataSet, JtableContext jtableContext) {
        ArrayKey arrayKey = new ArrayKey(new Object[0]);
        TableModelDefine sercetLevelTable = this.getSercetLevelTable(jtableContext);
        Map<String, String> dimensionNameColumnMap = this.getDimensionNameColumnMap(jtableContext, sercetLevelTable.getName());
        List rowKeyColumns = nvwaUpdatableDataSet.getRowKeyColumns();
        ArrayList<String> rowKeyValue = new ArrayList<String>();
        Map dimensionSet = jtableContext.getDimensionSet();
        for (ColumnModelDefine rowKeyColumn : rowKeyColumns) {
            String dimValue;
            String code = rowKeyColumn.getCode();
            String dimName = dimensionNameColumnMap.get(code);
            if (dimName == null || dimName == "" || (dimValue = ((DimensionValue)dimensionSet.get(dimName)).getValue()) == null || dimValue == "") continue;
            rowKeyValue.add(dimValue);
        }
        ArrayKey rowKeysValue = arrayKey.append(rowKeyValue);
        return rowKeysValue;
    }

    private void buildSecretLevelDataRow(JtableContext jtableContext, INvwaDataRow iNvwaDataRow, SecretLevelInfo sercetLevelInfo) {
        JtableContext excutorJtableContext = new JtableContext(jtableContext);
        TableModelDefine sercetLevelTable = this.stateSecretLevelService.getTableModelDefine(excutorJtableContext.getFormSchemeKey());
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(sercetLevelTable.getID());
        Map<String, String> dimensionNameColumnMap = this.getDimensionNameColumnMap(excutorJtableContext, sercetLevelTable.getName());
        Map dimensionSet = excutorJtableContext.getDimensionSet();
        block16: for (int i = 0; i < allColumns.size(); ++i) {
            String code;
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)allColumns.get(i);
            switch (code = columnModelDefine.getCode()) {
                case "SL_ID": {
                    iNvwaDataRow.setValue(i, (Object)UUID.randomUUID().toString());
                    continue block16;
                }
                case "SL_FORMSCHEMEKEY": {
                    iNvwaDataRow.setValue(i, (Object)sercetLevelInfo.getJtableContext().getFormSchemeKey());
                    continue block16;
                }
                case "SL_FORMKEY": {
                    iNvwaDataRow.setValue(i, (Object)"");
                    continue block16;
                }
                case "SL_LEVEL": {
                    iNvwaDataRow.setValue(i, (Object)sercetLevelInfo.getSecretLevelItem().getName());
                    continue block16;
                }
                case "SL_USER": {
                    iNvwaDataRow.setValue(i, (Object)sercetLevelInfo.getSecretLevelItem().getName());
                    continue block16;
                }
                case "SL_UPDATETIME": {
                    iNvwaDataRow.setValue(i, (Object)new Time(new Date().getTime()));
                    continue block16;
                }
                default: {
                    String dimName = dimensionNameColumnMap.get(code);
                    if (dimName == null || dimName == "") continue block16;
                    iNvwaDataRow.setValue(i, (Object)((DimensionValue)dimensionSet.get(dimName)).getValue());
                }
            }
        }
    }

    private Map<String, String> getDimensionNameColumnMap(JtableContext jtableContext, String tableName) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        HashMap<String, String> dimensionValueColumnMap = new HashMap<String, String>();
        try {
            TableModelRunInfo tableInfo = executorContext.getCache().getDataModelDefinitionsCache().getTableInfo(tableName);
            Map dimensionSet = jtableContext.getDimensionSet();
            for (String dimensionName : dimensionSet.keySet()) {
                String columnCode = tableInfo.getDimensionFieldCode(dimensionName);
                if (StringUtil.isNotEmpty((String)columnCode)) {
                    dimensionValueColumnMap.put(columnCode, dimensionName);
                    continue;
                }
                logger.error("\u7ef4\u5ea6\u540d\u672a\u627e\u5230\u5973\u5a32column:" + dimensionName);
            }
        }
        catch (ParseException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return dimensionValueColumnMap;
    }

    private void getAllEntity(EntityReturnInfo queryEntityData, List<EntityData> unitList) {
        for (EntityData entity : queryEntityData.getEntitys()) {
            unitList.add(entity);
            this.getAllEntity(entity, unitList);
        }
    }

    private void getAllEntity(EntityData parentEntity, List<EntityData> unitList) {
        for (EntityData entity : parentEntity.getChildren()) {
            unitList.add(entity);
            this.getAllEntity(entity, unitList);
        }
    }

    public void exportSecretLevel(JtableContext jtableContext, HttpServletResponse response) {
        boolean secretEnable = this.secretLevelEnable(jtableContext.getTaskKey());
        if (!secretEnable) {
            return;
        }
        try {
            ExportData secretLevelExportData = this.exportSecretLevelData(jtableContext);
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

    private ExportData exportSecretLevelData(JtableContext jtableContext) {
        JtableContext queryJtableContext = new JtableContext(jtableContext);
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        ((DimensionValue)queryJtableContext.getDimensionSet().get(dwEntity.getDimensionName())).setValue("");
        List<SecretLevelInfo> querySecretLevels = this.querySecretLevels(queryJtableContext);
        HashMap<String, String> unitSecretLevelMap = new HashMap<String, String>();
        for (SecretLevelInfo SecretLevelInfo2 : querySecretLevels) {
            String unitKey = ((DimensionValue)SecretLevelInfo2.getJtableContext().getDimensionSet().get(dwEntity.getDimensionName())).getValue();
            unitSecretLevelMap.put(unitKey, SecretLevelInfo2.getSecretLevelItem().getTitle());
        }
        EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
        entityQueryInfo.setEntityViewKey(dwEntity.getKey());
        entityQueryInfo.setAllChildren(true);
        entityQueryInfo.setContext(queryJtableContext);
        EntityReturnInfo entityData = this.jtableEntityService.queryEntityData(entityQueryInfo);
        ArrayList<EntityData> unitList = new ArrayList<EntityData>();
        this.getAllEntity(entityData, unitList);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(FILE_NAME);
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
        if (null != unitList && unitList.size() > 0) {
            for (int i = 0; i < unitList.size(); ++i) {
                row = sheet.createRow(i + 1);
                EntityData unitData = (EntityData)unitList.get(i);
                block12: for (int n = 0; n < titleList.length; ++n) {
                    XSSFCell cellData = row.createCell(n);
                    switch (n) {
                        case 0: {
                            cellData.setCellValue(i + 1);
                            cellData.setCellStyle(style2);
                            continue block12;
                        }
                        case 1: {
                            cellData.setCellValue(unitData.getId());
                            cellData.setCellStyle(style);
                            continue block12;
                        }
                        case 2: {
                            cellData.setCellValue(unitData.getCode());
                            cellData.setCellStyle(style);
                            continue block12;
                        }
                        case 3: {
                            cellData.setCellValue(unitData.getTitle());
                            cellData.setCellStyle(style);
                            continue block12;
                        }
                        case 4: {
                            if (unitSecretLevelMap.containsKey(unitData.getId())) {
                                cellData.setCellValue((String)unitSecretLevelMap.get(unitData.getId()));
                            }
                            cellData.setCellStyle(style);
                            continue block12;
                        }
                        default: {
                            logger.info("default");
                        }
                    }
                }
            }
        }
        List<SecretLevelItem> secretLevelItems = this.getSecretLevelItems();
        String[] actions = (String[])secretLevelItems.stream().map(SecretLevelItem::getTitle).toArray(String[]::new);
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
                workbook.write((OutputStream)os);
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

    public ReturnInfo importSecretLevel(String fileKey, JtableContext jtableContext) {
        block5: {
            boolean secretEnable = this.secretLevelEnable(jtableContext.getTaskKey());
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
                Sheet secretLevelSheet = excel.getSheet(FILE_NAME);
                boolean isCanImport = this.checkImportSheet(secretLevelSheet);
                List<Object> secretLevelList = new ArrayList();
                if (isCanImport) {
                    secretLevelList = this.checkImportDataInfo(secretLevelSheet, jtableContext);
                    if (secretLevelList == null || secretLevelList.isEmpty()) {
                        ReturnInfo returnInfo = new ReturnInfo();
                        returnInfo.setMessage("\u6ca1\u6709\u53ef\u5bfc\u5165\u7684\u5bc6\u7ea7");
                        return returnInfo;
                    }
                    BatchSecretLevelInfo batchSecretLevelInfo = new BatchSecretLevelInfo();
                    batchSecretLevelInfo.setJtableContext(jtableContext);
                    batchSecretLevelInfo.setSercetLevels(secretLevelList);
                    this.batchSaveBatchSecretLevel(batchSecretLevelInfo);
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

    private List<SecretLevelInfo> checkImportDataInfo(Sheet secretLevelSheet, JtableContext jtableContext) {
        ArrayList<SecretLevelInfo> checkInfoResultList = new ArrayList<SecretLevelInfo>();
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        List allEntityKeysList = this.jtableEntityService.getAllEntityKey(dwEntity.getKey(), jtableContext.getDimensionSet(), jtableContext.getFormSchemeKey());
        if (secretLevelSheet.getSheetName().equals(FILE_NAME)) {
            for (Row row : secretLevelSheet) {
                if (row.getRowNum() == 0) continue;
                try {
                    SecretLevelItem secretLevelItem;
                    SecretLevelInfo secretLevelInfo = new SecretLevelInfo();
                    String unitID = row.getCell(1).toString();
                    if (!allEntityKeysList.contains(unitID)) continue;
                    JtableContext rowJtableContext = new JtableContext(jtableContext);
                    ((DimensionValue)rowJtableContext.getDimensionSet().get(dwEntity.getDimensionName())).setValue(unitID);
                    secretLevelInfo.setJtableContext(rowJtableContext);
                    String secretLevel = row.getCell(4).toString();
                    if (!StringUtils.isNotEmpty((String)secretLevel) || (secretLevelItem = this.getSecretLevelItem(secretLevel)) == null) continue;
                    secretLevelInfo.setSecretLevelItem(secretLevelItem);
                    checkInfoResultList.add(secretLevelInfo);
                }
                catch (Exception e) {
                    return checkInfoResultList;
                }
            }
        }
        return checkInfoResultList;
    }
}

