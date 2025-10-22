/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.NumberFormatParser
 *  com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext
 *  com.jiuqi.nr.attachment.input.FileUploadInfo
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.datacrud.common.DataTypeConvert
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  net.coobird.thumbnailator.Thumbnails
 */
package com.jiuqi.nr.io.datacheck;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext;
import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.datacrud.common.DataTypeConvert;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.io.common.ExtConstants;
import com.jiuqi.nr.io.datacheck.TransferData;
import com.jiuqi.nr.io.datacheck.param.TransferParam;
import com.jiuqi.nr.io.datacheck.param.TransferSource;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.service.IDataTransfer;
import com.jiuqi.nr.io.service.IoEntityService;
import com.jiuqi.nr.io.service.impl.DefaultDataTransferProvider;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskOptionTransfer
implements IDataTransfer {
    protected static final Logger log = LoggerFactory.getLogger(TaskOptionTransfer.class);
    protected FileService fileService;
    protected IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    protected DataModelService dataModelService;
    protected FileAreaService fileAreaService;
    protected AttachmentIOService attachmentIOService;
    protected IRunTimeViewController runTimeViewController;
    protected IoEntityService ioEntityService;
    protected INvwaSystemOptionService nvwaSystemOptionService;
    protected FormDefine formDefine;
    protected final Map<String, String> enumDatakeys = new HashMap<String, String>();
    protected final Map<String, DataLinkDefine> enumDatakeyLink = new HashMap<String, DataLinkDefine>();
    protected final Map<String, IEntityTable> enumDataValues = new HashMap<String, IEntityTable>();
    private static final String[] trueValues = new String[]{"true", "yes", "y", "on", "1", "\u662f", "\u221a"};
    private static final String[] falseValues = new String[]{"false", "no", "n", "off", "0", "\u5426", "\u00d7"};

    public TaskOptionTransfer(DefaultDataTransferProvider dataTransferProvider, TransferParam param) {
        this.fileService = dataTransferProvider.getFileService();
        this.dataDefinitionRuntimeController = dataTransferProvider.getDataDefinitionRuntimeController();
        this.dataModelService = dataTransferProvider.getDataModelService();
        this.fileAreaService = dataTransferProvider.getFileAreaService();
        this.attachmentIOService = dataTransferProvider.getAttachmentIOService();
        this.runTimeViewController = dataTransferProvider.getRunTimeViewController();
        this.ioEntityService = dataTransferProvider.getIoEntityService();
        this.nvwaSystemOptionService = dataTransferProvider.getNvwaSystemOptionService();
        List allLinksInRegion = this.runTimeViewController.getAllLinksInRegion(param.getRegionKey());
        for (DataLinkDefine dataLinkDefine : allLinksInRegion) {
            EntityViewDefine queryEntityView;
            String linkExpression = dataLinkDefine.getLinkExpression();
            String selectViewKey = null;
            if (null != linkExpression) {
                if (dataLinkDefine.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FORMULA)) continue;
                try {
                    FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(linkExpression);
                    this.enumDatakeys.put(linkExpression, fieldDefine.getEntityKey());
                    this.enumDatakeyLink.put(linkExpression, dataLinkDefine);
                    selectViewKey = fieldDefine.getEntityKey();
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (null == (queryEntityView = this.runTimeViewController.getViewByLinkDefineKey(dataLinkDefine.getKey())) || queryEntityView.getEntityId() == null) continue;
            try {
                IEntityTable entityTables = this.ioEntityService.getIEntityTable(queryEntityView, param.getTableContext(), param.getExecutorContext());
                this.enumDataValues.put(selectViewKey + dataLinkDefine.getLinkExpression(), entityTables);
            }
            catch (Exception e) {
                log.debug("\u67e5\u8be2\u6307\u6807\u6570\u636e\u8fde\u63a5\u5b9e\u4f53\u7ed3\u679c\u51fa\u9519{}", (Object)e.getMessage());
            }
        }
        DataRegionDefine regionDefine = this.runTimeViewController.queryDataRegionDefine(param.getRegionKey());
        this.formDefine = this.runTimeViewController.queryFormById(regionDefine.getFormKey());
    }

    public TransferData dataTypelncorrect(TransferSource transferSource) {
        FieldDefine dataField = transferSource.getDataField();
        DataField field = (DataField)dataField;
        DataFieldType dataFieldType = field.getDataFieldType();
        if (dataFieldType.equals((Object)DataFieldType.FILE) || dataFieldType.equals((Object)DataFieldType.PICTURE)) {
            TransferData transferData = new TransferData();
            transferData.setTransferType(0);
            transferData.setValue(transferSource.getValue());
            return transferData;
        }
        if (!dataFieldType.equals((Object)DataFieldType.BIGDECIMAL) && !dataFieldType.equals((Object)DataFieldType.INTEGER) || !transferSource.getValue().toString().contains("\u2030")) {
            try {
                AbstractData.valueOf((Object)transferSource.getValue(), (int)DataTypeConvert.dataFieldType2DataType((int)field.getDataFieldType().getValue()));
            }
            catch (DataTypeException e) {
                TransferData transferData = new TransferData();
                transferData.setTransferType(1);
                transferData.setValue("");
                transferData.setMsg("\u6307\u6807\u3010" + field.getTitle() + "\u3011\uff1a\u6570\u636e\u7c7b\u578b\u4e0d\u5339\u914d\uff0c\u6e05\u7a7a\u6307\u6807\u6570\u636e\u540e\u5bfc\u5165\uff01\u539f\u503c\u4e3a\u3010" + transferSource.getValue() + "\u3011\u3002");
                return transferData;
            }
        }
        TransferData transferData = new TransferData();
        transferData.setTransferType(0);
        transferData.setValue(transferSource.getValue());
        return transferData;
    }

    public TransferData dataLenathIncorrect(TransferSource transferSource) {
        int decimal;
        FieldDefine dataField = transferSource.getDataField();
        DataField field = (DataField)dataField;
        if (field.getRefDataEntityKey() != null) {
            TransferData transferData = new TransferData();
            transferData.setValue(transferSource.getValue());
            transferData.setTransferType(0);
            return transferData;
        }
        if (field.getDataFieldType().equals((Object)DataFieldType.FILE) || field.getDataFieldType().equals((Object)DataFieldType.PICTURE)) {
            TransferData transferData = new TransferData();
            transferData.setTransferType(0);
            transferData.setValue(transferSource.getValue());
            return transferData;
        }
        int precision = field.getPrecision() == null ? 0 : field.getPrecision();
        int n = decimal = field.getDecimal() == null ? 0 : field.getDecimal();
        if (!field.getDataFieldType().equals((Object)DataFieldType.BIGDECIMAL) && !field.getDataFieldType().equals((Object)DataFieldType.INTEGER) || !transferSource.getValue().toString().contains("\u2030")) {
            AbstractData abstractData;
            try {
                abstractData = AbstractData.valueOf((Object)transferSource.getValue(), (int)DataTypeConvert.dataFieldType2DataType((int)field.getDataFieldType().getValue()));
            }
            catch (DataTypeException e) {
                TransferData transferData = new TransferData();
                transferData.setValue(transferSource.getValue());
                transferData.setTransferType(2);
                transferData.setMsg("\u6307\u6807\u3010" + dataField.getTitle() + "\u3011\uff1a\u6570\u636e\u957f\u5ea6\u8d85\u957f\uff01\u539f\u503c\u4e3a\u3010" + transferSource.getValue() + "\u3011\u3002");
                return transferData;
            }
            if ((field.getDataFieldType().equals((Object)DataFieldType.BIGDECIMAL) || field.getDataFieldType().equals((Object)DataFieldType.INTEGER)) && !transferSource.getValue().toString().contains("\u2030")) {
                return this.checkSize(transferSource.getValue().toString(), precision, decimal, field.getTitle(), abstractData.getAsCurrency());
            }
        }
        String enumKey = this.enumDatakeys.get(field.getKey());
        if (field.getDataFieldType().equals((Object)DataFieldType.STRING) && StringUtils.isEmpty((String)enumKey)) {
            String string = transferSource.getValue().toString();
            if (field.getPrecision() < string.length()) {
                String value = string.substring(0, field.getPrecision());
                TransferData transferData = new TransferData();
                transferData.setValue(value);
                transferData.setTransferType(1);
                transferData.setMsg("\u6307\u6807\u3010" + field.getTitle() + "\u3011\uff1a\u6570\u636e\u957f\u5ea6\u8d85\u957f\uff0c\u4fee\u6b63\u540e\u5bfc\u5165\uff01\u539f\u503c\u4e3a\u3010" + string + "\u3011\uff1b\u4fee\u6b63\u540e\u7684\u503c\u4e3a\u3010" + value + "\u3011");
                return transferData;
            }
        }
        TransferData transferData = new TransferData();
        transferData.setValue(transferSource.getValue());
        transferData.setTransferType(0);
        return transferData;
    }

    public TransferData dataValuelsEmptyIncorrect(TransferSource transferSource) {
        FieldDefine dataField = transferSource.getDataField();
        TransferData transferData = new TransferData();
        if (dataField instanceof DataFieldDTO && ((DataFieldDTO)dataField).getDataFieldKind().equals((Object)DataFieldKind.TABLE_FIELD_DIM) && this.formDefine != null && this.formDefine.getFormType() == FormType.FORM_TYPE_ACCOUNT) {
            transferData.setTransferType(0);
            transferData.setValue(transferSource.getValue());
            return transferData;
        }
        if (transferSource.getTableContext().getCheckType() == 1 && (dataField.getType().equals((Object)FieldType.FIELD_TYPE_FILE) || dataField.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE))) {
            transferData.setTransferType(0);
            transferData.setValue(transferSource.getValue());
            return transferData;
        }
        if (!dataField.getNullable().booleanValue()) {
            transferData.setTransferType(1);
            transferData.setMsg("\u6307\u6807\u3010" + dataField.getTitle() + "\u3011\uff1a\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff0c\u8df3\u8fc7\u5f53\u524d\u884c!");
            return transferData;
        }
        transferData.setTransferType(0);
        transferData.setValue(transferSource.getValue());
        return transferData;
    }

    public TransferData enumDataIllegal(TransferSource transferSource) {
        Object data = transferSource.getValue();
        TableContext tableContext = transferSource.getTableContext();
        FieldDefine def = transferSource.getDataField();
        String enumKey = this.enumDatakeys.get(def.getKey());
        IEntityTable iEntityTable = this.enumDataValues.get(enumKey + def.getKey());
        TransferData transferData = new TransferData();
        if (null != iEntityTable) {
            String string;
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
            if (tableContext.getExpEnumFields().equals((Object)ExpViewFields.TITLE)) {
                String[] split;
                List allRows = iEntityTable.getAllRows();
                StringBuilder multiTitle = new StringBuilder();
                String string2 = data.toString();
                IEntityRow findByCode = iEntityTable.findByCode(data.toString());
                if (string2.contains("|") && (findByCode = iEntityTable.findByCode((split = data.toString().split("\\|"))[0])) == null) {
                    findByCode = iEntityTable.findByCode(split[1]);
                }
                if (findByCode != null) {
                    String code1 = findByCode.getCode() != null ? findByCode.getCode() : data.toString();
                    String code = findByCode.getEntityKeyData() != null ? findByCode.getEntityKeyData() : code1;
                    transferData.setTransferType(0);
                    transferData.setValue(code);
                    return transferData;
                }
                String[] multiCodeTitle = string2.split(";");
                ArrayList<String> asList = new ArrayList<String>(Arrays.asList(multiCodeTitle));
                for (IEntityRow row : allRows) {
                    if (row.getCode().equals(string2) || row.getTitle().equals(string2) || string2.contains("|") && (row.getCode().equals(string2.split("\\|")[0]) || row.getCode().equals(string2.split("\\|")[1]))) {
                        if (Consts.EntityField.ENTITY_FIELD_KEY.fieldKey.equals(referCode)) {
                            String code = null != row.getEntityKeyData() ? row.getEntityKeyData() : row.getCode();
                            transferData.setTransferType(0);
                            transferData.setValue(code);
                            return transferData;
                        }
                        transferData.setTransferType(0);
                        transferData.setValue(row.getCode());
                        return transferData;
                    }
                    if (!string2.contains(";")) continue;
                    Iterator iterator = asList.iterator();
                    while (iterator.hasNext()) {
                        String next = (String)iterator.next();
                        if (!row.getCode().equals(next) && !row.getTitle().equals(next) && (!next.contains("|") || !row.getCode().equals(next.split("\\|")[0]) && !row.getCode().equals(next.split("\\|")[1]))) continue;
                        if (multiTitle.length() > 0) {
                            multiTitle.append(";").append(row.getEntityKeyData());
                        } else {
                            multiTitle.append(row.getEntityKeyData());
                        }
                        iterator.remove();
                    }
                }
                if (!multiTitle.toString().isEmpty()) {
                    transferData.setTransferType(0);
                    transferData.setValue(multiTitle.toString());
                    return transferData;
                }
                DataLinkDefine dataLinkDefine = this.enumDatakeyLink.get(def.getKey());
                if (dataLinkDefine != null && !dataLinkDefine.getAllowUndefinedCode().booleanValue()) {
                    transferData.setTransferType(1);
                    transferData.setValue(data);
                    transferData.setMsg("\u6307\u6807\u3010" + def.getTitle() + "\u3011\uff1a\u679a\u4e3e\u4e0d\u5b58\u5728\uff0c\u539f\u503c\u5165\u5e93\uff01\u539f\u503c\u4e3a\u3010" + data + "\u3011\u3002");
                    return transferData;
                }
            } else if (tableContext.getExpEnumFields().equals((Object)ExpViewFields.CODE)) {
                DataLinkDefine dataLinkDefine;
                string = data.toString();
                if (string.contains(";")) {
                    String[] split;
                    StringBuilder multiTitle = new StringBuilder();
                    for (String string2 : split = string.split(";")) {
                        DataLinkDefine dataLinkDefine2;
                        if (string2 == null) continue;
                        IEntityRow findByCode = iEntityTable.findByCode(string2);
                        IEntityRow findByEntityKey = iEntityTable.findByEntityKey(string2);
                        if (findByCode == null && findByEntityKey == null && (dataLinkDefine2 = this.enumDatakeyLink.get(def.getKey())) != null && !dataLinkDefine2.getAllowUndefinedCode().booleanValue()) {
                            transferData.setTransferType(1);
                            transferData.setValue(data);
                            transferData.setMsg("\u6307\u6807\u3010" + def.getTitle() + "\u3011\uff1a\u679a\u4e3e\u4e0d\u5b58\u5728\uff0c\u539f\u503c\u5165\u5e93\uff01\u539f\u503c\u4e3a\u3010" + data + "\u3011\u3002");
                            return transferData;
                        }
                        if (multiTitle.length() == 0) {
                            multiTitle.append(findByCode == null ? (findByEntityKey == null ? string2 : findByEntityKey.getEntityKeyData()) : findByCode.getEntityKeyData());
                            continue;
                        }
                        multiTitle.append(";").append(findByCode == null ? (findByEntityKey == null ? string2 : findByEntityKey.getEntityKeyData()) : findByCode.getEntityKeyData());
                    }
                    transferData.setTransferType(0);
                    transferData.setValue(multiTitle.toString());
                    return transferData;
                }
                IEntityRow findByCode = iEntityTable.findByCode(data.toString());
                if (findByCode != null) {
                    String code1 = findByCode.getCode() != null ? findByCode.getCode() : data.toString();
                    String code = findByCode.getEntityKeyData() != null ? findByCode.getEntityKeyData() : code1;
                    transferData.setTransferType(0);
                    transferData.setValue(code);
                    return transferData;
                }
                IEntityRow findByEntityKey = iEntityTable.findByEntityKey(data.toString());
                if (findByEntityKey == null && (dataLinkDefine = this.enumDatakeyLink.get(def.getKey())) != null && !dataLinkDefine.getAllowUndefinedCode().booleanValue()) {
                    transferData.setTransferType(1);
                    transferData.setValue(data);
                    transferData.setMsg("\u6307\u6807\u3010" + def.getTitle() + "\u3011\uff1a\u679a\u4e3e\u4e0d\u5b58\u5728\uff0c\u539f\u503c\u5165\u5e93\uff01\u539f\u503c\u4e3a\u3010" + data + "\u3011\u3002");
                    return transferData;
                }
            } else if (tableContext.getExpEnumFields().equals((Object)ExpViewFields.KEY)) {
                string = data.toString();
                if (string.contains(";")) {
                    String[] split;
                    StringBuilder multiTitle = new StringBuilder();
                    for (String string2 : split = string.split(";")) {
                        DataLinkDefine dataLinkDefine;
                        if (string2 == null) continue;
                        IEntityRow findByCode = iEntityTable.findByCode(string2);
                        IEntityRow findByEntityKey = iEntityTable.findByEntityKey(string2);
                        if (findByCode == null && findByEntityKey == null && (dataLinkDefine = this.enumDatakeyLink.get(def.getKey())) != null && !dataLinkDefine.getAllowUndefinedCode().booleanValue()) {
                            transferData.setTransferType(1);
                            transferData.setValue(data);
                            transferData.setMsg("\u6307\u6807\u3010" + def.getTitle() + "\u3011\uff1a\u679a\u4e3e\u4e0d\u5b58\u5728\uff0c\u539f\u503c\u5165\u5e93\uff01\u539f\u503c\u4e3a\u3010" + data + "\u3011\u3002");
                            return transferData;
                        }
                        if (multiTitle.length() == 0) {
                            multiTitle.append(findByCode == null ? (findByEntityKey == null ? string2 : findByEntityKey.getEntityKeyData()) : findByCode.getEntityKeyData());
                            continue;
                        }
                        multiTitle.append(";").append(findByCode == null ? (findByEntityKey == null ? string2 : findByEntityKey.getEntityKeyData()) : findByCode.getEntityKeyData());
                    }
                    transferData.setTransferType(0);
                    transferData.setValue(multiTitle.toString());
                    return transferData;
                }
                IEntityRow byEntityKey = iEntityTable.findByEntityKey(data.toString());
                if (null != byEntityKey) {
                    String code = byEntityKey.getEntityKeyData() != null ? byEntityKey.getEntityKeyData() : data.toString();
                    transferData.setTransferType(0);
                    transferData.setValue(code);
                    return transferData;
                }
                DataLinkDefine dataLinkDefine = this.enumDatakeyLink.get(def.getKey());
                if (dataLinkDefine != null && !dataLinkDefine.getAllowUndefinedCode().booleanValue()) {
                    transferData.setTransferType(1);
                    transferData.setValue(data);
                    transferData.setMsg("\u6307\u6807\u3010" + def.getTitle() + "\u3011\uff1a\u679a\u4e3e\u4e0d\u5b58\u5728\uff0c\u539f\u503c\u5165\u5e93\uff01\u539f\u503c\u4e3a\u3010" + data + "\u3011\u3002");
                    return transferData;
                }
            }
        }
        transferData.setTransferType(0);
        transferData.setValue(data);
        return transferData;
    }

    @Override
    public TransferData transfer(TransferSource transferSource) {
        TransferData transferData = new TransferData();
        Object data = transferSource.getValue();
        if (null == data || data.equals("")) {
            return this.dataValuelsEmptyIncorrect(transferSource);
        }
        TransferData dataTypelncorrect = this.dataTypelncorrect(transferSource);
        if (dataTypelncorrect.getTransferType() == 1) {
            log.warn(dataTypelncorrect.getMsg());
            return dataTypelncorrect;
        }
        TransferData dataLenathIncorrect = this.dataLenathIncorrect(transferSource);
        if (dataLenathIncorrect.getTransferType() != 0) {
            return dataLenathIncorrect;
        }
        data = dataLenathIncorrect.getValue();
        TableContext tableContext = transferSource.getTableContext();
        FieldDefine def = transferSource.getDataField();
        RegionData regionData = transferSource.getRegionData();
        String dataSchemeKey = transferSource.getDataSchemeKey();
        IDataRow dataRow = transferSource.getDataRow();
        String enumKey = null;
        enumKey = this.enumDatakeys.get(def.getKey());
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) {
            if (!tableContext.isAttachment() && tableContext.isNewFileGroup()) {
                data = UUID.randomUUID().toString();
            }
            if (!tableContext.isAttachment()) {
                transferData.setTransferType(0);
                transferData.setValue(data);
                return transferData;
            }
            String groupKey = UUID.randomUUID().toString();
            String[] fileNames = data.toString().split(";");
            if (fileNames.length == 1 && fileNames[0].isEmpty()) {
                transferData.setTransferType(0);
                transferData.setValue(data);
                return transferData;
            }
            FileUploadByGroupKeyContext groupKeyContext = new FileUploadByGroupKeyContext();
            groupKeyContext.setGroupKey(groupKey);
            groupKeyContext.setFormKey(regionData.getFormKey());
            groupKeyContext.setFormSchemeKey(tableContext.getFormSchemeKey());
            groupKeyContext.setTaskKey(tableContext.getTaskKey());
            groupKeyContext.setFieldKey(def.getKey());
            if (dataSchemeKey != null) {
                groupKeyContext.setDataSchemeKey(dataSchemeKey);
            }
            if (dataRow != null) {
                DimensionCombination dimensionCombination = new DimensionCombinationBuilder(dataRow.getRowKeys()).getCombination();
                groupKeyContext.setDimensionCombination(dimensionCombination);
            }
            ArrayList<FileUploadInfo> fileUploadInfos = new ArrayList<FileUploadInfo>();
            for (int i = 0; i < fileNames.length; ++i) {
                int one;
                File file = this.getFile(tableContext.getPwd() + fileNames[i]);
                byte[] buffer = this.getBytes(file);
                if (buffer == null && fileNames[i] != null && (one = fileNames[i].lastIndexOf("\\.")) != -1) {
                    String substring = fileNames[i].substring(0, one);
                    file = this.getFile(tableContext.getPwd() + substring);
                    buffer = this.getBytes(file);
                }
                if (null == buffer) continue;
                FileUploadInfo fileUploadInfo = new FileUploadInfo();
                fileUploadInfo.setFile((InputStream)new ByteArrayInputStream(buffer));
                fileUploadInfo.setName(file.getName());
                fileUploadInfo.setSize((long)buffer.length);
                fileUploadInfos.add(fileUploadInfo);
                groupKeyContext.setFileUploadInfos(fileUploadInfos);
            }
            if (groupKeyContext.getFileUploadInfos() != null && !groupKeyContext.getFileUploadInfos().isEmpty()) {
                this.attachmentIOService.uploadByGroup(groupKeyContext);
            }
            transferData.setTransferType(0);
            transferData.setValue(groupKey);
            return transferData;
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE)) {
            if (!tableContext.isAttachment() && tableContext.isNewFileGroup()) {
                data = UUID.randomUUID().toString();
            }
            if (!tableContext.isAttachment()) {
                transferData.setTransferType(0);
                transferData.setValue(data);
                return transferData;
            }
            String groupKey = UUID.randomUUID().toString();
            String[] fileNames = data.toString().split(";");
            if (fileNames.length == 1 && fileNames[0].isEmpty()) {
                return null;
            }
            for (int i = 0; i < fileNames.length; ++i) {
                int one;
                File file = this.getFile(tableContext.getPwd() + fileNames[i]);
                byte[] buffer = this.getBytes(file);
                if (buffer == null && fileNames[i] != null && (one = fileNames[i].lastIndexOf(46)) != -1) {
                    String substring = fileNames[i].substring(0, one);
                    file = this.getFile(tableContext.getPwd() + substring);
                    buffer = this.getBytes(file);
                }
                if (null == buffer) continue;
                FileInfo uploadByGroup = this.fileAreaService.uploadByGroup(file.getName(), groupKey, buffer);
                this.suoluotu(buffer, file.getName(), uploadByGroup.getKey());
            }
            transferData.setTransferType(0);
            transferData.setValue(groupKey);
            return transferData;
        }
        if (StringUtils.isNotEmpty((String)enumKey)) {
            transferData = this.enumDataIllegal(transferSource);
            DataField field = (DataField)def;
            String string = (String)transferData.getValue();
            if (field.getPrecision() < string.length()) {
                transferData.setValue(data);
                transferData.setTransferType(2);
                transferData.setMsg("\u6307\u6807\u3010" + def.getCode() + "\u3011\uff1a\u679a\u4e3e\u8f6c\u6362\u540e\u6570\u636e\u957f\u5ea6\u8d85\u957f\uff01\u539f\u503c\u4e3a\u3010" + data + "\u3011\u3002");
            }
            return transferData;
        }
        if (def.getType().equals((Object)FieldType.FIELD_TYPE_LOGIC)) {
            boolean isMatch = false;
            for (int i = 0; i < trueValues.length; ++i) {
                if (data.toString().toLowerCase().equals(trueValues[i])) {
                    data = true;
                    isMatch = true;
                    break;
                }
                if (!data.toString().toLowerCase().equals(falseValues[i])) continue;
                data = false;
                isMatch = true;
                break;
            }
            if (isMatch) {
                transferData.setTransferType(0);
                transferData.setValue(data);
            } else {
                transferData.setTransferType(2);
                transferData.setValue(data);
                transferData.setMsg("\u6307\u6807\u3010" + def.getTitle() + "\u3011\uff1a\u503c\u65e0\u6cd5\u89e3\u6790\u4e3a\u5e03\u5c14\u7c7b\u578b\uff01\u539f\u503c\u4e3a\u3010" + data + "\u3011\u3002");
            }
            return transferData;
        }
        if (1 == def.getType().getValue() || 3 == def.getType().getValue() || 8 == def.getType().getValue()) {
            if (NumberFormatParser.parse((FormatProperties)def.getFormatProperties()).isThousandPer()) {
                if (data.toString().contains("\u2030")) {
                    try {
                        data = new BigDecimal(data.toString().replace("\u2030", "")).divide(new BigDecimal(1000)).toPlainString();
                        transferData.setTransferType(0);
                        transferData.setValue(data);
                    }
                    catch (Exception e) {
                        transferData.setTransferType(2);
                        transferData.setValue(data);
                        transferData.setMsg("\u6307\u6807\u3010" + def.getTitle() + "\u3011\uff1a\u53ea\u80fd\u8f93\u5165\u5343\u5206\u6bd4\u6570\u636e\uff01\u539f\u503c\u4e3a\u3010" + data + "\u3011\u3002");
                    }
                } else {
                    transferData.setTransferType(2);
                    transferData.setValue(data);
                    transferData.setMsg("\u6307\u6807\u3010" + def.getTitle() + "\u3011\uff1a\u53ea\u80fd\u8f93\u5165\u5343\u5206\u6bd4\u6570\u636e\uff01\u539f\u503c\u4e3a\u3010" + data + "\u3011\u3002");
                }
            } else {
                BigDecimal initialBigDecimal = null;
                try {
                    if (!StringUtils.isEmpty((String)data.toString())) {
                        if (data.toString().contains(",")) {
                            Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(data.toString());
                            initialBigDecimal = BigDecimal.valueOf(number.doubleValue());
                        } else {
                            initialBigDecimal = new BigDecimal(data.toString());
                        }
                    }
                    transferData.setTransferType(0);
                    transferData.setValue(initialBigDecimal);
                }
                catch (Exception e) {
                    transferData.setTransferType(1);
                    transferData.setValue(null);
                    transferData.setMsg("\u6307\u6807\u3010" + def.getTitle() + "\u3011\uff1a\u6570\u636e\u7c7b\u578b\u4e0d\u5339\u914d\uff0c\u6309NULL\u503c\u5bfc\u5165\uff01\u539f\u503c\u4e3a\u3010" + data + "\u3011\u3002");
                }
            }
            return transferData;
        }
        if (5 == def.getType().getValue() || 6 == def.getType().getValue()) {
            if (data instanceof java.util.Date) {
                java.util.Date originalDate = (java.util.Date)data;
                if (5 == def.getType().getValue()) {
                    originalDate = this.formatDate(originalDate);
                }
                transferData.setTransferType(0);
                transferData.setValue(originalDate);
            } else if (data instanceof String) {
                try {
                    String formatString = 5 == def.getType().getValue() ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss";
                    java.util.Date date = new SimpleDateFormat(formatString).parse((String)data);
                    transferData.setTransferType(0);
                    transferData.setValue(date);
                }
                catch (Exception e) {
                    transferData.setTransferType(2);
                    transferData.setValue(data);
                    transferData.setMsg("\u6307\u6807\u3010" + def.getTitle() + "\u3011\uff1a\u6570\u636e\u7c7b\u578b\u4e0d\u5339\u914d\uff01\u539f\u503c\u4e3a\u3010" + data + "\u3011\u3002");
                }
            } else {
                transferData.setTransferType(2);
                transferData.setValue(data);
                transferData.setMsg("\u6307\u6807\u3010" + def.getTitle() + "\u3011\uff1a\u6570\u636e\u7c7b\u578b\u4e0d\u5339\u914d\uff01\u539f\u503c\u4e3a\u3010" + data + "\u3011\u3002");
            }
            return transferData;
        }
        transferData.setTransferType(0);
        transferData.setValue(data);
        return transferData;
    }

    private File getFile(String filePath) {
        String[] files;
        File file = new File(filePath);
        if (file.isDirectory() && (files = file.list()).length > 0) {
            file = new File(FilenameUtils.normalize(filePath + ExtConstants.FILE_SEPARATOR + files[0]));
        }
        return file;
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
            byte[] textByte = path.getBytes(StandardCharsets.UTF_8);
            FileInfoBuilder.newFileInfo((FileInfo)smallFile, (String)smallFile.getFileGroupKey(), (String)Base64.encodeBase64String(textByte));
        }
        catch (IOException e) {
            log.warn("\u5bfc\u5165\u56fe\u7247\u7f29\u7565\u56fe\u5931\u8d25:{}", (Object)e.getMessage());
        }
    }

    private TransferData checkSize(String valueStr, int precision, int decimal, String fieldCode, BigDecimal valueDecimal) {
        valueDecimal = new BigDecimal(valueDecimal.stripTrailingZeros().toPlainString());
        String roundImportStr = this.nvwaSystemOptionService.get("nr-data-entry-group", "MATCH_ROUND_OF_IMPORT");
        TransferData transferData = new TransferData();
        if (valueDecimal.precision() - valueDecimal.scale() > precision - decimal) {
            transferData.setTransferType(2);
            transferData.setValue(valueStr);
            transferData.setMsg("\u6307\u6807\u3010" + fieldCode + "\u3011\uff1a\u6570\u636e\u7cbe\u5ea6\u8d85\u957f\uff01\u539f\u503c\u4e3a\u3010" + valueStr + "\u3011\u3002");
        } else if (valueDecimal.scale() > decimal) {
            if ("1".equals(roundImportStr)) {
                valueDecimal = valueDecimal.setScale(decimal, RoundingMode.HALF_UP);
                transferData.setValue(valueDecimal);
                transferData.setTransferType(1);
                transferData.setMsg("\u6307\u6807\u3010" + fieldCode + "\u3011\u6570\u636e\u5c0f\u6570\u4f4d\u6570\u8d85\u957f\uff0c\u4fee\u6b63\u540e\u5bfc\u5165\uff01\u539f\u503c\u4e3a\u3010" + valueStr + "\u3011\uff1b\u4fee\u6b63\u540e\u7684\u503c\u4e3a\u3010" + valueDecimal + "\u3011");
            } else {
                transferData.setTransferType(2);
                transferData.setValue(valueStr);
                transferData.setMsg("\u6307\u6807\u3010" + fieldCode + "\u3011\uff1a\u6570\u636e\u7cbe\u5ea6\u8d85\u957f\uff01\u539f\u503c\u4e3a\u3010" + valueStr + "\u3011\u3002");
            }
        } else {
            transferData.setTransferType(0);
            transferData.setValue(valueDecimal);
        }
        return transferData;
    }

    private java.util.Date formatDate(java.util.Date originalDate) {
        LocalDateTime localDateTime = originalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(0).withMinute(0).withSecond(0);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}

