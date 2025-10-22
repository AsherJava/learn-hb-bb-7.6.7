/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.io.params.input.ExpViewFields
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.io.sb.ISBImportActuator
 *  com.jiuqi.nr.io.sb.ImportMode
 *  com.jiuqi.nr.io.sb.JIOSBImportActuatorConfig
 *  com.jiuqi.nr.io.sb.SBImportActuatorConfig
 *  com.jiuqi.nr.io.sb.SBImportActuatorFactory
 *  com.jiuqi.nr.io.sb.SBImportActuatorType
 *  com.jiuqi.nr.io.sb.bean.ImportInfo
 *  com.jiuqi.nr.io.tz.exception.TzCopyDataException
 *  com.jiuqi.nr.io.util.DateUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  net.coobird.thumbnailator.Thumbnails
 */
package nr.midstore.core.internal.dataset.tz;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.sb.ISBImportActuator;
import com.jiuqi.nr.io.sb.ImportMode;
import com.jiuqi.nr.io.sb.JIOSBImportActuatorConfig;
import com.jiuqi.nr.io.sb.SBImportActuatorConfig;
import com.jiuqi.nr.io.sb.SBImportActuatorFactory;
import com.jiuqi.nr.io.sb.SBImportActuatorType;
import com.jiuqi.nr.io.sb.bean.ImportInfo;
import com.jiuqi.nr.io.tz.exception.TzCopyDataException;
import com.jiuqi.nr.io.util.DateUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnails;
import nr.midstore.core.dataset.IMidstoreEntityService;
import nr.midstore.core.dataset.MidsotreTableContext;
import nr.midstore.core.dataset.MidstoreTableData;
import nr.midstore.core.internal.dataset.AbstractMidstoreDataSet;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MidstoreSBDataSet
extends AbstractMidstoreDataSet {
    private static final Logger log = LoggerFactory.getLogger(MidstoreSBDataSet.class);
    private MidstoreTableData regionData = null;
    private MidsotreTableContext tableContext;
    private List<FieldDefine> listFieldDefine = null;
    private List<FieldDefine> bizKeyFieldDef = new ArrayList<FieldDefine>();
    private Map<String, FieldDefine> bizKeyDimNameMap = new HashMap<String, FieldDefine>();
    private Map<String, String> enumDatakeys = new HashMap<String, String>();
    private Map<String, IEntityTable> enumDataValues = new HashMap<String, IEntityTable>();
    private Map<String, FieldDefine> fielddefineMap = new HashMap<String, FieldDefine>();
    private Map<String, DataField> dataFields = new HashMap<String, DataField>();
    private List<FieldDefine> fieldRowList = new ArrayList<FieldDefine>();
    private IRunTimeViewController runTimeViewController;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private IDataAssist dataAssist;
    private IDataAccessProvider dataAccessProvider;
    private ISBImportActuator sbImportActuator;
    private SBImportActuatorFactory sbImportActuatorFactory;
    private IMidstoreEntityService ioEntityService;
    private FileAreaService fileAreaService;
    private FileService fileService;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private Map<String, String> zbDimMap = new HashMap<String, String>();
    private DataModelService dataModelService;

    public MidstoreSBDataSet(MidsotreTableContext context, MidstoreTableData regionData, List<ExportFieldDefine> importDefine) {
        this.tableContext = context;
        this.regionData = regionData;
        this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
        this.dataAssist = this.dataAccessProvider.newDataAssist(this.getExecutorContext(context));
        this.sbImportActuatorFactory = (SBImportActuatorFactory)BeanUtil.getBean(SBImportActuatorFactory.class);
        this.ioEntityService = (IMidstoreEntityService)BeanUtil.getBean(IMidstoreEntityService.class);
        this.fileService = (FileService)BeanUtil.getBean(FileService.class);
        this.fileAreaService = this.fileService.area(context.getAttachmentArea());
        this.runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        JIOSBImportActuatorConfig cfg = new JIOSBImportActuatorConfig();
        this.listFieldDefine = new ArrayList<FieldDefine>();
        try {
            List listFieldDefine2 = this.dataDefinitionRuntimeController.getAllFieldsInTable(regionData.getKey());
            for (FieldDefine field : listFieldDefine2) {
                DataField dataField = this.runtimeDataSchemeService.getDataField(field.getKey());
                if (dataField != null && dataField.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) continue;
                this.listFieldDefine.add(field);
            }
        }
        catch (Exception e1) {
            log.error(e1.getMessage(), e1);
        }
        String tableKey = null;
        if (null != this.listFieldDefine && this.listFieldDefine.size() > 0) {
            tableKey = this.listFieldDefine.get(0).getOwnerTableKey();
        }
        ArrayList<FieldDefine> queryFieldDefine = new ArrayList<FieldDefine>();
        this.getBizKeysDef(tableKey, queryFieldDefine, true);
        ArrayList<DataField> listField = new ArrayList<DataField>();
        String tableName = null;
        try {
            List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(tableKey);
            if (deployInfoByDataTableKey != null && !deployInfoByDataTableKey.isEmpty()) {
                tableName = ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        for (ExportFieldDefine item : importDefine) {
            DataField dataField;
            String code = item.getCode();
            if (code.contains(".")) {
                code = code.split("\\.")[1];
            }
            if ((dataField = this.dataFields.get(code)) == null) {
                FieldDefine dimensionField = this.dataAssist.getDimensionField(tableName, code);
                code = dimensionField.getCode();
            }
            listField.add(this.dataFields.get(code));
            this.fieldRowList.add(this.fielddefineMap.get(code));
        }
        cfg.configItems().put("TYPE", SBImportActuatorType.BUF_DB);
        cfg.configItems().put("DEST_TABLE", tableKey);
        cfg.configItems().put("DEST_PERIOD", context.getDimensionSet().getValue("DATATIME"));
        if (this.tableContext.getFloatImpOpt() == 0) {
            cfg.configItems().put("IMPORT_MODE", ImportMode.INCREMENT);
        } else {
            cfg.configItems().put("IMPORT_MODE", ImportMode.ALL);
        }
        for (FieldDefine fieldDefine : this.bizKeyFieldDef) {
            String dimensionName = this.dataAssist.getDimensionName(fieldDefine);
            this.zbDimMap.put(fieldDefine.getCode(), dimensionName);
        }
        cfg.configItems().put("ZB_DIM_MAPPING", this.zbDimMap);
        this.sbImportActuator = this.sbImportActuatorFactory.getImportActuator((SBImportActuatorConfig)cfg);
        this.sbImportActuator.setDataFields(listField);
        this.sbImportActuator.prepare();
        this.initLinkViewMap(context, regionData);
    }

    @Override
    public MidstoreTableData getRegionData() {
        return this.regionData;
    }

    @Override
    public boolean isFloatRegion() {
        return true;
    }

    @Override
    public List<ExportFieldDefine> getFieldDataList() {
        TableDefine td = null;
        ArrayList<ExportFieldDefine> fieldDefines = new ArrayList<ExportFieldDefine>();
        if (null != this.listFieldDefine && !this.listFieldDefine.isEmpty()) {
            for (FieldDefine item : this.listFieldDefine) {
                ExportFieldDefine fd = new ExportFieldDefine(item.getTitle(), item.getCode(), item.getSize().intValue(), item.getType().getValue());
                fd.setValueType(item.getValueType().getValue());
                if (td != null) {
                    fd.setTableCode(td.getCode());
                } else {
                    try {
                        td = this.dataDefinitionRuntimeController.queryTableDefine(item.getOwnerTableKey());
                        fd.setTableCode(td.getCode());
                    }
                    catch (Exception e) {
                        log.info("\u67e5\u5b58\u50a8\u8868\u51fa\u9519{}", e);
                        fd.setTableCode(item.getCode().split("\\.")[0]);
                    }
                }
                fieldDefines.add(fd);
            }
        }
        return fieldDefines;
    }

    @Override
    public DimensionValueSet importDatas(List<Object> row) throws Exception {
        for (int i = 0; i < row.size(); ++i) {
            Object data = row.get(i);
            data = this.dataTransferOri(this.fieldRowList.get(i), data);
            row.set(i, data);
        }
        try {
            this.sbImportActuator.put(row);
        }
        catch (TzCopyDataException e) {
            return DimensionValueSet.EMPTY;
        }
        DimensionValueSet valueSet = new DimensionValueSet();
        for (String value : this.zbDimMap.values()) {
            valueSet.setValue(value, null);
        }
        return valueSet;
    }

    @Override
    public ImportInfo commit() throws Exception {
        try {
            ImportInfo importInfo = this.sbImportActuator.commit();
            return importInfo;
        }
        finally {
            this.sbImportActuator.close();
        }
    }

    @Override
    public void close() {
        if (this.sbImportActuator != null) {
            this.sbImportActuator.close();
        }
    }

    @Override
    public List<FieldDefine> getBizFieldDefList() {
        return this.bizKeyFieldDef;
    }

    @Override
    public FieldDefine getUnitFieldDefine() throws Exception {
        FieldDefine unitFieldDefine = null;
        for (int i = 0; i < this.bizKeyFieldDef.size(); ++i) {
            if (!this.bizKeyFieldDef.get(i).getCode().equals("MDCODE")) continue;
            unitFieldDefine = this.bizKeyFieldDef.get(i);
        }
        if (unitFieldDefine != null) {
            return unitFieldDefine;
        }
        return null;
    }

    private void getBizKeysDef(String tableKey, List<FieldDefine> queryFieldDefine, boolean isImp) {
        try {
            TableDefine tableDef = this.dataDefinitionRuntimeController.queryTableDefine(tableKey);
            if (null == tableDef) {
                return;
            }
            String bizKeys = Arrays.toString(tableDef.getBizKeyFieldsID());
            List allFields = this.dataDefinitionRuntimeController.getAllFieldsInTable(tableKey);
            Collections.sort(allFields, new Comparator<FieldDefine>(){

                @Override
                public int compare(FieldDefine o1, FieldDefine o2) {
                    int i = o1.getCode().compareTo(o2.getCode());
                    if (i == 0) {
                        return o1.getCode().compareTo(o2.getCode());
                    }
                    return i;
                }
            });
            for (FieldDefine it : allFields) {
                this.dataFields.put(it.getCode(), (DataField)it);
                this.fielddefineMap.put(it.getCode(), it);
                if (bizKeys.contains(it.getKey()) && (!it.getCode().equals("BIZKEYORDER") || this.tableContext.isExportBizkeyorder())) {
                    queryFieldDefine.add(it);
                    this.bizKeyFieldDef.add(it);
                } else if (it.getCode().equals("BIZKEYORDER") && this.tableContext.isExportBizkeyorder()) {
                    queryFieldDefine.add(it);
                }
                if (!isImp || !it.getCode().equals("BIZKEYORDER") && !it.getCode().equals("FLOATORDER")) continue;
                if (bizKeys.contains(it.getKey()) && it.getCode().equals("BIZKEYORDER")) {
                    this.bizKeyFieldDef.add(it);
                }
                queryFieldDefine.add(it);
            }
            ArrayList<FieldDefine> bizKeyFieldDef1 = new ArrayList<FieldDefine>();
            block3: for (int i = 0; i < tableDef.getBizKeyFieldsID().length; ++i) {
                for (FieldDefine biz : this.bizKeyFieldDef) {
                    if (!tableDef.getBizKeyFieldsID()[i].equals(biz.getKey())) continue;
                    bizKeyFieldDef1.add(biz);
                    continue block3;
                }
            }
            this.bizKeyFieldDef = bizKeyFieldDef1;
            int index = 0;
            for (FieldDefine biz : this.bizKeyFieldDef) {
                queryFieldDefine.remove(biz);
                queryFieldDefine.add(index, biz);
                ++index;
                String dimensionName = this.dataAssist.getDimensionName(biz);
                this.bizKeyDimNameMap.put(dimensionName, biz);
            }
        }
        catch (Exception e) {
            log.debug("\u83b7\u53d6\u4e3b\u7ef4\u5ea6\u4e3b\u952e\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private ExecutorContext getExecutorContext(MidsotreTableContext context) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        return executorContext;
    }

    private Object dataTransferOri(FieldDefine def, Object data) {
        if (null == data) {
            return null;
        }
        String enumKey = null;
        if (!this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODE)) {
            enumKey = this.enumDatakeys.get(def.getKey());
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_DATE)) {
            return DateUtil.getOriDatetime((String)data.toString(), (String)"yyyy-MM-dd");
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_TIME)) {
            return DateUtil.getOriDatetime((String)data.toString(), (String)"HH:mm:ss");
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
            return DateUtil.getOriDatetime((String)data.toString(), (String)"yyyy-MM-dd HH:mm:ss");
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) {
            if (!this.tableContext.isAttachment()) {
                return data;
            }
            String groupKey = UUID.randomUUID().toString();
            String[] fileNames = data.toString().split(";");
            if (fileNames.length == 1 && fileNames[0].length() == 0) {
                return null;
            }
            for (int i = 0; i < fileNames.length; ++i) {
                File file = this.getFile(this.tableContext.getPwd() + fileNames[i]);
                byte[] buffer = this.getBytes(file);
                if (null == buffer) continue;
                this.fileAreaService.uploadByGroup(file.getName(), groupKey, buffer);
            }
            return groupKey;
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE)) {
            if (!this.tableContext.isAttachment()) {
                return null;
            }
            String groupKey = UUID.randomUUID().toString();
            String[] fileNames = data.toString().split(";");
            if (fileNames.length == 1 && fileNames[0].length() == 0) {
                return null;
            }
            for (int i = 0; i < fileNames.length; ++i) {
                File file = this.getFile(this.tableContext.getPwd() + fileNames[i]);
                byte[] buffer = this.getBytes(file);
                if (null == buffer) continue;
                this.fileAreaService.uploadByGroup(file.getName(), groupKey, buffer);
                this.suoluotu(buffer, file.getName(), groupKey);
            }
            return groupKey;
        }
        if (null != enumKey) {
            IEntityTable iEntityTable = this.enumDataValues.get(enumKey);
            if (null != iEntityTable) {
                IEntityRow byEntityKey;
                FieldDefine refer = null;
                String referCode = "";
                DataField df = (DataField)def;
                if (null != df.getRefDataEntityKey()) {
                    try {
                        refer = this.dataDefinitionRuntimeController.queryFieldDefine(df.getRefDataEntityKey());
                        if (refer == null) {
                            ColumnModelDefine colum = this.dataModelService.getColumnModelDefineByID(df.getRefDataFieldKey());
                            referCode = colum.getCode();
                        } else {
                            referCode = refer.getCode();
                        }
                    }
                    catch (Exception e) {
                        log.debug("\u6ca1\u6709\u627e\u5173\u8054\u7684\u6307\u6807{}", (Object)e.getMessage());
                    }
                }
                if (this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.TITLE)) {
                    String string = data.toString();
                    if (string != null && string.contains("|")) {
                        return string.split("\\|")[0];
                    }
                    List allRows = iEntityTable.getAllRows();
                    for (IEntityRow row : allRows) {
                        if (!row.getTitle().equals(data.toString())) continue;
                        if (Consts.EntityField.ENTITY_FIELD_KEY.equals((Object)referCode)) {
                            return null != row.getEntityKeyData() ? row.getEntityKeyData() : row.getCode();
                        }
                        return row.getCode();
                    }
                } else if (this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODE)) {
                    IEntityRow findByCode = iEntityTable.findByCode(data.toString());
                    if (findByCode != null) {
                        return findByCode.getCode() != null ? findByCode.getCode() : data.toString();
                    }
                } else if (this.tableContext.getExpEnumFields().equals((Object)ExpViewFields.KEY) && null != (byEntityKey = iEntityTable.findByEntityKey(data.toString()))) {
                    return byEntityKey.getEntityKeyData() != null ? byEntityKey.getEntityKeyData() : data.toString();
                }
            }
            return data;
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_LOGIC)) {
            data = data.equals("\u662f");
        } else if (1 == def.getType().getValue() || 3 == def.getType().getValue() || 8 == def.getType().getValue()) {
            BigDecimal initialBigDecimal = null;
            try {
                if (!StringUtils.isEmpty((String)data.toString())) {
                    if (data.toString().contains(",")) {
                        Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(data.toString());
                        initialBigDecimal = new BigDecimal(number.doubleValue());
                    } else {
                        initialBigDecimal = new BigDecimal(data.toString());
                    }
                }
                return initialBigDecimal;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return data;
    }

    private void initLinkViewMap(MidsotreTableContext context, MidstoreTableData regionData) {
        boolean isTable = true;
        List allFieldsInTable = null;
        try {
            allFieldsInTable = this.dataDefinitionRuntimeController.getAllFieldsInTable(regionData.getKey());
        }
        catch (Exception e1) {
            log.error(e1.getMessage(), e1);
        }
        if (allFieldsInTable == null) {
            return;
        }
        for (FieldDefine fieldDefine : allFieldsInTable) {
            String selectViewKey = null;
            if (!StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) continue;
            this.enumDatakeys.put(fieldDefine.getKey(), fieldDefine.getEntityKey());
            selectViewKey = fieldDefine.getEntityKey();
            EntityViewDefine queryEntityView = this.entityViewRunTimeController.buildEntityView(selectViewKey);
            if (null == queryEntityView || queryEntityView.getEntityId() == null) continue;
            try {
                ExecutorContext executorContext = this.getExecutorContext(context);
                IEntityTable entityTables = this.ioEntityService.getIEntityTable(queryEntityView, context, executorContext);
                this.enumDataValues.put(selectViewKey, entityTables);
            }
            catch (Exception e) {
                log.debug("\u67e5\u8be2\u6307\u6807\u6570\u636e\u8fde\u63a5\u5b9e\u4f53\u7ed3\u679c\u51fa\u9519{}", (Object)e.getMessage());
            }
        }
    }

    private byte[] getBytes(File file) {
        byte[] buffer = null;
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);){
            int n;
            byte[] b = new byte[1000];
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e) {
            log.info("\u8bfb\u53d6\u7684\u9644\u4ef6\u6ca1\u6709\u6587\u4ef6\u4fe1\u606f,\u8df3\u8fc7\u8be5\u9644\u4ef6\u5bfc\u5165:{}", (Object)e.getMessage());
        }
        catch (IOException e) {
            log.info("\u8bfb\u53d6\u7684\u9644\u4ef6\u6ca1\u6709\u6587\u4ef6\u4fe1\u606f,\u8df3\u8fc7\u8be5\u9644\u4ef6\u5bfc\u5165:{}", (Object)e.getMessage());
        }
        return buffer;
    }

    private File getFile(String filePath) {
        String[] files;
        File file = new File(filePath);
        if (file.isDirectory() && (files = file.list()).length > 0) {
            file = new File(FilenameUtils.normalize(filePath + "/" + files[0]));
        }
        return file;
    }

    private void suoluotu(byte[] buffer, String name, String groupKey) {
        try {
            String fileName = name;
            long size = buffer.length;
            byte[] smallBytes = null;
            if (size > 20480L) {
                byte[] bytes = buffer;
                float proportion = 0.1f;
                if (size < 512000L && (proportion = (float)(1.0 - (double)(((float)size - 20480.0f) / 50000.0f) * 0.1)) < 0.1f) {
                    proportion = 0.1f;
                }
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
                Thumbnails.of((InputStream[])new InputStream[]{inputStream}).scale((double)proportion).toOutputStream((OutputStream)outPutStream);
                smallBytes = outPutStream.toByteArray();
            } else {
                smallBytes = buffer;
            }
            FileInfo smallFile = this.fileService.area("JTABLEAREA").uploadByGroup(fileName, groupKey, smallBytes);
            String path = this.fileService.area("JTABLEAREA").getPath(smallFile.getKey(), NpContextHolder.getContext().getTenant());
            byte[] textByte = path.getBytes("UTF-8");
            smallFile = FileInfoBuilder.newFileInfo((FileInfo)smallFile, (String)smallFile.getFileGroupKey(), (String)Base64.encodeBase64String(textByte));
        }
        catch (IOException e) {
            log.warn("\u5bfc\u5165\u56fe\u7247\u7f29\u7565\u56fe\u5931\u8d25:{}", (Object)e.getMessage());
        }
    }
}

