/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.output.FileInfosAndGroup
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService
 *  com.jiuqi.nr.definition.util.AttachmentObj
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.FileInfosAndGroup;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.nr.definition.util.AttachmentObj;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFieldInfo;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFieldStruct;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckFieldResultItem;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckFileItem;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckParam;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckReturnInfo;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFormStruct;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.ChineseCharToEn;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.FieldAndFileInfo;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.FileKeyAndInfo;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class BlobFileSizeCheck {
    private DataDefinitionRuntimeController2 dataDefinitionRuntimeController;
    private IRunTimeViewController runTimeViewController;
    private IDataAccessProvider dataAccessProvider;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private FileInfoService fileInfoService;
    private IJtableParamService jtableParamService;
    private CommonUtil commonUtil;
    private DimensionUtil dimensionUtil;
    private IEntityDataService entityDataService;
    private IRuntimeDataLinkService dataLinkService;
    private EntityQueryHelper entityQueryHelper;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private FilePoolService filePoolService;
    private static final Logger logger = LoggerFactory.getLogger(BlobFileSizeCheck.class);
    private Map<FieldDefine, BlobFieldInfo> fixFields = null;
    private Map<FieldDefine, BlobFieldInfo> floatFields = null;
    private DimensionValueSet dimensionValueSet = null;
    private AsyncTaskMonitor monitor = null;
    private JtableContext context = null;
    private Map<String, String> entityMap = null;
    private String unitDimName = null;
    private HashMap<String, Integer> untityOrderDic = null;
    private String mainDimName;
    private String taskTitle;
    private String formSchemeTitle;
    private int fieldCount = 0;
    private FormSchemeDefine define = null;
    private Map<String, String> dataLinkRegionCatch = new HashMap<String, String>();
    private static final String NO_ROWID = "NO_ROWID";
    LinkedHashMap<String, BlobFileSizeCheckResultItem> resultMap = new LinkedHashMap();

    private void getParam() {
        this.dataDefinitionRuntimeController = (DataDefinitionRuntimeController2)BeanUtil.getBean(DataDefinitionRuntimeController2.class);
        this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.fileInfoService = (FileInfoService)BeanUtil.getBean(FileInfoService.class);
        this.jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        this.commonUtil = (CommonUtil)BeanUtil.getBean(CommonUtil.class);
        this.dimensionUtil = (DimensionUtil)BeanUtil.getBean(DimensionUtil.class);
        this.entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
        this.dataLinkService = (IRuntimeDataLinkService)BeanUtil.getBean(IRuntimeDataLinkService.class);
        this.entityQueryHelper = (EntityQueryHelper)BeanUtil.getBean(EntityQueryHelper.class);
        this.runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.filePoolService = (FilePoolService)BeanUtil.getBean(FilePoolService.class);
    }

    public BlobFileSizeCheckReturnInfo blobFileSizeCheck(BlobFileSizeCheckParam param, AsyncTaskMonitor monitor) throws Exception {
        List<String> curUnitKeys;
        JtableContext tmpContext;
        BlobFileSizeCheckReturnInfo returnInfo = new BlobFileSizeCheckReturnInfo();
        this.monitor = monitor;
        this.context = param.getContext();
        this.dimensionValueSet = null;
        if (this.resultMap != null) {
            this.resultMap.clear();
        }
        if (this.entityMap != null) {
            this.entityMap.clear();
        }
        if (this.untityOrderDic != null) {
            this.untityOrderDic.clear();
        }
        this.getParam();
        this.fieldCount = 0;
        if (monitor != null) {
            monitor.progressAndMessage(0.1, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
        }
        FormSchemeDefine formDefine = null;
        try {
            formDefine = this.runTimeViewController.getFormScheme(this.context.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        this.mainDimName = this.dimensionUtil.getDwMainDimName(this.context.getFormSchemeKey());
        if (param.getUnitKeys() == null || param.getUnitKeys().size() == 0) {
            tmpContext = new JtableContext(this.context);
            if (tmpContext.getDimensionSet().get(this.mainDimName) != null) {
                ((DimensionValue)tmpContext.getDimensionSet().get(this.mainDimName)).setValue("");
            }
            this.dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)tmpContext);
            Object Value = this.dimensionValueSet.getValue(this.mainDimName);
            if (Value instanceof List) {
                curUnitKeys = (List<String>)Value;
            } else {
                String units = this.dimensionValueSet.getValue(this.mainDimName).toString();
                curUnitKeys = Arrays.asList(units.split(","));
            }
            this.InitEntityMap();
        } else {
            tmpContext = new JtableContext(this.context);
            if (tmpContext.getDimensionSet().get(this.mainDimName) != null) {
                ((DimensionValue)tmpContext.getDimensionSet().get(this.mainDimName)).setValue(String.join((CharSequence)";", param.getUnitKeys()));
            }
            this.dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)tmpContext);
            curUnitKeys = param.getUnitKeys();
            if (this.entityMap == null) {
                int capacity = (int)((double)curUnitKeys.size() * 1.5);
                this.entityMap = capacity <= 16 ? new HashMap<String, String>() : new HashMap<String, String>(capacity);
            }
        }
        if (param.getSelBlobItem().size() == 0) {
            ArrayList errItems = new ArrayList();
            ArrayList<BlobFileSizeCheckResultItem> allItems = new ArrayList<BlobFileSizeCheckResultItem>();
            returnInfo.setErrItems(errItems);
            returnInfo.setAllItems(allItems);
            returnInfo.setSelZBCount(0);
            returnInfo.setUnitCount(curUnitKeys.size());
            if (monitor != null) {
                monitor.progressAndMessage(0.99, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
            }
            return returnInfo;
        }
        for (String unitKey : curUnitKeys) {
            BlobFileSizeCheckResultItem item = new BlobFileSizeCheckResultItem();
            if (this.resultMap.containsKey(unitKey)) continue;
            item = this.getCheckResultItem(null, null, unitKey, 0.0);
            this.resultMap.put(unitKey, item);
        }
        this.parseFields(param.getSelBlobItem());
        this.getFixFieldsCheckResult();
        this.getFloatFieldsCheckResult();
        ArrayList<BlobFileSizeCheckResultItem> allResultItems = new ArrayList<BlobFileSizeCheckResultItem>(this.resultMap.values());
        ArrayList<BlobFileSizeCheckResultItem> errItems = new ArrayList<BlobFileSizeCheckResultItem>();
        ArrayList<BlobFileSizeCheckResultItem> allItems = new ArrayList<BlobFileSizeCheckResultItem>();
        for (BlobFileSizeCheckResultItem resultItem : allResultItems) {
            List<BlobFileSizeCheckFieldResultItem> fieldItems = resultItem.getFieldItems();
            for (BlobFileSizeCheckFieldResultItem blobFileSizeCheckFieldResultItem : resultItem.getFieldItems()) {
                List<BlobFileSizeCheckFileItem> errFileItems = blobFileSizeCheckFieldResultItem.getErrFilesInField();
                if (blobFileSizeCheckFieldResultItem.getErrFilesInField().size() >= 2) {
                    Collections.sort(errFileItems, new Comparator<BlobFileSizeCheckFileItem>(){

                        @Override
                        public int compare(BlobFileSizeCheckFileItem o1, BlobFileSizeCheckFileItem o2) {
                            if (o1 == null || o2 == null) {
                                return -1;
                            }
                            String s1 = ChineseCharToEn.getFirstLetter(o1.getFileName());
                            String s2 = ChineseCharToEn.getFirstLetter(o2.getFileName());
                            return s1.compareTo(s2);
                        }
                    });
                }
                if (blobFileSizeCheckFieldResultItem.getFilesInField().size() < 2) continue;
                List<BlobFileSizeCheckFileItem> fileItems = blobFileSizeCheckFieldResultItem.getFilesInField();
                fileItems.removeAll(errFileItems);
                ArrayList<BlobFileSizeCheckFileItem> newFileItems = new ArrayList<BlobFileSizeCheckFileItem>(fileItems);
                Collections.sort(newFileItems, new Comparator<BlobFileSizeCheckFileItem>(){

                    @Override
                    public int compare(BlobFileSizeCheckFileItem o1, BlobFileSizeCheckFileItem o2) {
                        if (o1 == null || o2 == null) {
                            return -1;
                        }
                        String s1 = ChineseCharToEn.getFirstLetter(o1.getFileName());
                        String s2 = ChineseCharToEn.getFirstLetter(o2.getFileName());
                        return s1.compareTo(s2);
                    }
                });
                blobFileSizeCheckFieldResultItem.getFilesInField().clear();
                blobFileSizeCheckFieldResultItem.getFilesInField().addAll(errFileItems);
                blobFileSizeCheckFieldResultItem.getFilesInField().addAll(newFileItems);
            }
            allItems.add(resultItem);
        }
        for (BlobFileSizeCheckResultItem resultItem : allResultItems) {
            boolean isNeedAdd = false;
            List<BlobFileSizeCheckFieldResultItem> fieldItems = resultItem.getFieldItems();
            for (BlobFileSizeCheckFieldResultItem fieldItem : fieldItems) {
                if (fieldItem.getErrFilesInField().size() > 0) {
                    isNeedAdd = true;
                    break;
                }
                if (fieldItem.getFilesInField().size() != 0 || fieldItem.getNullable()) continue;
                isNeedAdd = true;
                break;
            }
            if (!isNeedAdd) continue;
            errItems.add(resultItem);
        }
        if (errItems != null && errItems.size() > 0) {
            EntityViewDefine entityView = this.entityQueryHelper.getDwEntityView(formDefine.getKey());
            String periodDimName = this.getPeriodDimName(formDefine.getKey());
            String period = ((DimensionValue)this.context.getDimensionSet().get(periodDimName)).getValue();
            IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(entityView, period, formDefine.getKey(), false);
            final HashMap<String, Integer> hashMap = this.entityQueryHelper.entityOrderByKey(entityTable);
            Collections.sort(allItems, new Comparator<BlobFileSizeCheckResultItem>(){

                @Override
                public int compare(BlobFileSizeCheckResultItem o1, BlobFileSizeCheckResultItem o2) {
                    if (o1 == null || o2 == null) {
                        return -1;
                    }
                    int level1 = hashMap.containsKey(o1.getUnitCode()) ? (Integer)hashMap.get(o1.getUnitCode()) : 0;
                    int level2 = hashMap.containsKey(o2.getUnitCode()) ? (Integer)hashMap.get(o2.getUnitCode()) : 0;
                    return level1 - level2;
                }
            });
            Collections.sort(errItems, new Comparator<BlobFileSizeCheckResultItem>(){

                @Override
                public int compare(BlobFileSizeCheckResultItem o1, BlobFileSizeCheckResultItem o2) {
                    if (o1 == null || o2 == null) {
                        return -1;
                    }
                    int level1 = hashMap.containsKey(o1.getUnitCode()) ? (Integer)hashMap.get(o1.getUnitCode()) : 0;
                    int level2 = hashMap.containsKey(o2.getUnitCode()) ? (Integer)hashMap.get(o2.getUnitCode()) : 0;
                    return level1 - level2;
                }
            });
        }
        returnInfo.setErrItems(errItems);
        returnInfo.setAllItems(allItems);
        int iCount = 0;
        for (BlobFormStruct formStruct : param.getSelBlobItem()) {
            if (formStruct.getChildren() == null) continue;
            iCount += formStruct.getChildren().size();
        }
        returnInfo.setSelZBCount(iCount);
        returnInfo.setUnitCount(curUnitKeys.size());
        if (monitor != null) {
            monitor.progressAndMessage(0.99, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
        }
        return returnInfo;
    }

    private void parseFields(List<BlobFormStruct> blobItems) throws Exception {
        if (this.monitor != null) {
            this.monitor.progressAndMessage(0.1, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
        }
        this.fixFields = new LinkedHashMap<FieldDefine, BlobFieldInfo>();
        this.floatFields = new LinkedHashMap<FieldDefine, BlobFieldInfo>();
        if (blobItems.size() == 0) {
            return;
        }
        if (this.entityMap == null) {
            this.entityMap = new HashMap<String, String>((int)((double)this.resultMap.size() * 1.5));
        }
        for (BlobFormStruct blobItem : blobItems) {
            for (BlobFieldStruct childBlobItem : blobItem.getChildren()) {
                try {
                    DataRegionDefine region;
                    BlobFieldInfo info;
                    double maxValue;
                    double minValue;
                    DataLinkDefine linkDefine;
                    FieldDefine field;
                    block34: {
                        FormDefine formDefine;
                        String formCode = childBlobItem.getFormCode();
                        if (!StringUtils.hasText(formCode) && (formDefine = this.runTimeViewController.queryFormById(childBlobItem.getFormKey())) != null) {
                            formCode = formDefine.getFormCode();
                            childBlobItem.setFormCode(formCode);
                        }
                        if ((field = this.dataDefinitionRuntimeController.queryFieldDefine(childBlobItem.getKey())) == null || (linkDefine = this.runTimeViewController.queryDataLinkDefine(childBlobItem.getDataLinkKey())) == null) continue;
                        minValue = -1.0;
                        maxValue = -1.0;
                        info = new BlobFieldInfo();
                        try {
                            String[] extArr;
                            ArrayList alist;
                            String attachment;
                            info.setFormCode(formCode);
                            info.setCanNull(field.getNullable());
                            BigDataDefine bigData = this.dataLinkService.getAttachmentDataFromLink(linkDefine.getKey());
                            if (bigData == null || bigData.getData() == null || "".equals(attachment = DesignFormDefineBigDataUtil.bytesToString((byte[])bigData.getData()))) break block34;
                            AttachmentObj attachmentObj = (AttachmentObj)JSONUtil.parseObject((String)attachment, AttachmentObj.class);
                            String maxValueStr = "".equals(attachmentObj.getMaxSize()) ? String.valueOf(-1) : attachmentObj.getMaxSize();
                            String minValueStr = "".equals(attachmentObj.getMinSize()) ? String.valueOf(-1) : attachmentObj.getMinSize();
                            maxValue = Double.parseDouble(maxValueStr);
                            minValue = Double.parseDouble(minValueStr);
                            if (attachmentObj.getDocument().size() > 0) {
                                alist = attachmentObj.getDocument();
                                for (String ext : alist) {
                                    if (ext.contains("/")) {
                                        for (String s : extArr = ext.split("/")) {
                                            info.getExtList().add("." + s);
                                        }
                                        continue;
                                    }
                                    info.getExtList().add("." + ext);
                                }
                            }
                            if (attachmentObj.getImg().size() > 0) {
                                alist = attachmentObj.getImg();
                                for (String ext : alist) {
                                    if (ext.contains("/")) {
                                        for (String s : extArr = ext.split("/")) {
                                            info.getExtList().add("." + s);
                                        }
                                        continue;
                                    }
                                    info.getExtList().add("." + ext);
                                }
                            }
                            if (attachmentObj.getStadio().size() > 0) {
                                alist = attachmentObj.getStadio();
                                for (String ext : alist) {
                                    if (ext.contains("/")) {
                                        for (String s : extArr = ext.split("/")) {
                                            info.getExtList().add("." + s);
                                        }
                                        continue;
                                    }
                                    info.getExtList().add("." + ext);
                                }
                            }
                            if (attachmentObj.getVedio().size() > 0) {
                                alist = attachmentObj.getVedio();
                                for (String ext : alist) {
                                    if (ext.contains("/")) {
                                        for (String s : extArr = ext.split("/")) {
                                            info.getExtList().add("." + s);
                                        }
                                        continue;
                                    }
                                    info.getExtList().add("." + ext);
                                }
                            }
                            if (attachmentObj.getZip().size() <= 0) break block34;
                            alist = attachmentObj.getZip();
                            for (String ext : alist) {
                                if (ext.contains("/")) {
                                    for (String s : extArr = ext.split("/")) {
                                        info.getExtList().add("." + s);
                                    }
                                    continue;
                                }
                                info.getExtList().add("." + ext);
                            }
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            continue;
                        }
                    }
                    if (minValue == -1.0 && maxValue == -1.0) {
                        info.setIsCheckFileSize(false);
                    } else {
                        info.setIsCheckFileSize(true);
                        if (maxValue == -1.0) {
                            maxValue = 2.147483647E9;
                        }
                    }
                    if (linkDefine == null || (region = this.runTimeViewController.queryDataRegionDefine(linkDefine.getRegionKey())) == null) continue;
                    ++this.fieldCount;
                    info.setBlobFieldStruct(childBlobItem);
                    info.setMinValue(minValue);
                    info.setMaxValue(maxValue);
                    if (region.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
                        this.floatFields.put(field, info);
                        continue;
                    }
                    this.fixFields.put(field, info);
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        if (this.monitor != null) {
            this.monitor.progressAndMessage(0.2, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
        }
    }

    private void getFixFieldsCheckResult() {
        if (this.fixFields.size() == 0) {
            return;
        }
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormulaSchemeKey(this.context.getFormulaSchemeKey());
        queryEnvironment.setFormSchemeKey(this.context.getFormSchemeKey());
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        for (Map.Entry<FieldDefine, BlobFieldInfo> fieldEntry : this.fixFields.entrySet()) {
            dataQuery.addColumn(fieldEntry.getKey());
        }
        dataQuery.setMasterKeys(this.dimensionValueSet);
        try {
            String unitKey;
            String jtableFileArea = "JTABLEAREA";
            ExecutorContext executorContext = new ExecutorContext((IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, (IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController, this.entityViewRunTimeController, this.context.getFormSchemeKey());
            executorContext.setEnv((IFmlExecEnvironment)environment);
            executorContext.setJQReportModel(true);
            IDataTable dataTable = dataQuery.executeQuery((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext);
            double stepValue = 0.0;
            stepValue = 0.3 / (double)this.resultMap.size();
            ArrayList<String> addedUnitKeys = new ArrayList<String>();
            HashMap<String, FieldAndFileInfo> groupAndFileInfoMap = new HashMap<String, FieldAndFileInfo>();
            for (int i = 0; i < dataTable.getCount(); ++i) {
                if (this.monitor != null) {
                    this.monitor.progressAndMessage(0.2 + stepValue * (double)i, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
                }
                IDataRow dataRow = dataTable.getItem(i);
                DimensionValueSet dimSet = dataRow.getRowKeys();
                unitKey = dimSet.getValue(this.mainDimName).toString();
                for (Map.Entry<FieldDefine, BlobFieldInfo> fieldEntry : this.fixFields.entrySet()) {
                    FieldAndFileInfo fieldAndFileInfo = new FieldAndFileInfo();
                    fieldAndFileInfo.setUnitKey(unitKey);
                    fieldAndFileInfo.setFieldDefine(fieldEntry.getKey());
                    fieldAndFileInfo.setBlobFieldInfo(fieldEntry.getValue());
                    String value = dataRow.getAsString(fieldEntry.getKey());
                    groupAndFileInfoMap.put(value, fieldAndFileInfo);
                }
            }
            this.getFileInfo(groupAndFileInfoMap);
            for (String groupKey : groupAndFileInfoMap.keySet()) {
                FieldAndFileInfo fieldAndFileInfo = (FieldAndFileInfo)groupAndFileInfoMap.get(groupKey);
                unitKey = fieldAndFileInfo.getUnitKey();
                BlobFileSizeCheckResultItem item = null;
                if (this.resultMap.containsKey(unitKey)) {
                    item = this.resultMap.get(unitKey);
                } else {
                    item = this.getCheckResultItem(null, null, unitKey, 0.0);
                    this.resultMap.put(unitKey, item);
                }
                Map<String, FileKeyAndInfo> fileKeyAndInfoMap = fieldAndFileInfo.getFileKeyAndInfoMap();
                FieldDefine fieldDefine = fieldAndFileInfo.getFieldDefine();
                BlobFieldInfo blobFieldInfo = fieldAndFileInfo.getBlobFieldInfo();
                if (fileKeyAndInfoMap.keySet().isEmpty()) {
                    if (blobFieldInfo.getCanNull()) continue;
                    BlobFileSizeCheckFieldResultItem fieldItem = new BlobFileSizeCheckFieldResultItem();
                    fieldItem.setFormCode(blobFieldInfo.getFormCode());
                    fieldItem.setFormKey(blobFieldInfo.getFormKey());
                    fieldItem.setFormTitle(blobFieldInfo.getFormTitle());
                    String dataLinkKey = blobFieldInfo.getDataLinkKey();
                    String dataRegionKey = this.dataLinkRegionCatch.get(dataLinkKey);
                    if (null == dataRegionKey) {
                        DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkKey);
                        dataRegionKey = dataLinkDefine.getRegionKey();
                        this.dataLinkRegionCatch.put(dataLinkKey, dataRegionKey);
                    }
                    fieldItem.setDataRegionKey(dataRegionKey);
                    fieldItem.setDataLinkKey(dataLinkKey);
                    fieldItem.setFieldCode(fieldDefine.getCode());
                    fieldItem.setFieldKey(fieldDefine.getKey());
                    fieldItem.setFieldTitle(fieldDefine.getTitle());
                    fieldItem.setRowId(null);
                    fieldItem.setNullable(fieldDefine.getNullable());
                    item.addFieldItem(fieldItem);
                    fieldItem.setNullable(false);
                    addedUnitKeys.add(String.format("%s_%s_%s", unitKey, blobFieldInfo.getFormCode(), fieldDefine.getCode()));
                    continue;
                }
                double minValue = blobFieldInfo.getMinValue();
                double maxValue = blobFieldInfo.getMaxValue();
                List<String> extList = blobFieldInfo.getExtList();
                BlobFileSizeCheckFieldResultItem fieldItem = new BlobFileSizeCheckFieldResultItem();
                fieldItem.setFormCode(blobFieldInfo.getFormCode());
                fieldItem.setFormKey(blobFieldInfo.getFormKey());
                fieldItem.setFormTitle(blobFieldInfo.getFormTitle());
                String dataLinkKey = blobFieldInfo.getDataLinkKey();
                String dataRegionKey = this.dataLinkRegionCatch.get(dataLinkKey);
                if (null == dataRegionKey) {
                    DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkKey);
                    dataRegionKey = dataLinkDefine.getRegionKey();
                    this.dataLinkRegionCatch.put(dataLinkKey, dataRegionKey);
                }
                fieldItem.setDataRegionKey(dataRegionKey);
                fieldItem.setDataLinkKey(dataLinkKey);
                fieldItem.setFieldCode(fieldDefine.getCode());
                fieldItem.setFieldKey(fieldDefine.getKey());
                fieldItem.setFieldTitle(fieldDefine.getTitle());
                fieldItem.setRowId(null);
                fieldItem.setNullable(fieldDefine.getNullable());
                item.addFieldItem(fieldItem);
                addedUnitKeys.add(String.format("%s_%s_%s", unitKey, blobFieldInfo.getFormCode(), fieldDefine.getCode()));
                for (String fileKey : fileKeyAndInfoMap.keySet()) {
                    FileKeyAndInfo fileKeyAndInfo = fileKeyAndInfoMap.get(fileKey);
                    double fileSize = fileKeyAndInfo.getSize();
                    fileSize = fileSize / 1024.0 / 1024.0;
                    BigDecimal b = BigDecimal.valueOf(fileSize);
                    fileSize = b.setScale(2, 4).doubleValue();
                    fieldItem.addFileSize(fileSize);
                    BlobFileSizeCheckFileItem fileItem = new BlobFileSizeCheckFileItem();
                    fileItem.setFileName(fileKeyAndInfo.getName());
                    fileItem.setFileSize(fileSize);
                    boolean hasAdded = false;
                    if (blobFieldInfo.getIsCheckFileSize() && (fileSize < minValue || fileSize > maxValue)) {
                        fieldItem.addErrFile(fileItem);
                        hasAdded = true;
                    }
                    if (extList.size() > 0 && !hasAdded && !extList.contains(fileKeyAndInfo.getExtension())) {
                        fieldItem.addErrFile(fileItem);
                    }
                    fieldItem.addFile(fileItem);
                }
            }
            int i = dataTable.getCount();
            for (Map.Entry<String, BlobFileSizeCheckResultItem> resultEntry : this.resultMap.entrySet()) {
                if (this.monitor != null) {
                    this.monitor.progressAndMessage(0.2 + stepValue * (double)i++, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
                }
                BlobFileSizeCheckResultItem item = resultEntry.getValue();
                for (Map.Entry<FieldDefine, BlobFieldInfo> fieldEntry : this.fixFields.entrySet()) {
                    if (addedUnitKeys.contains(String.format("%s_%s_%s", resultEntry.getKey(), fieldEntry.getValue().getFormCode(), fieldEntry.getKey().getCode()))) continue;
                    BlobFileSizeCheckFieldResultItem fieldItem = new BlobFileSizeCheckFieldResultItem();
                    fieldItem.setFormCode(fieldEntry.getValue().getFormCode());
                    fieldItem.setFormKey(fieldEntry.getValue().getFormKey());
                    fieldItem.setFormTitle(fieldEntry.getValue().getFormTitle());
                    String dataLinkKey = fieldEntry.getValue().getDataLinkKey();
                    String dataRegionKey = this.dataLinkRegionCatch.get(dataLinkKey);
                    if (null == dataRegionKey) {
                        DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkKey);
                        dataRegionKey = dataLinkDefine.getRegionKey();
                        this.dataLinkRegionCatch.put(dataLinkKey, dataRegionKey);
                    }
                    fieldItem.setDataRegionKey(dataRegionKey);
                    fieldItem.setDataLinkKey(dataLinkKey);
                    fieldItem.setFieldCode(fieldEntry.getKey().getCode());
                    fieldItem.setFieldKey(fieldEntry.getKey().getKey());
                    fieldItem.setFieldTitle(fieldEntry.getKey().getTitle());
                    fieldItem.setRowId(null);
                    fieldItem.setNullable(fieldEntry.getKey().getNullable());
                    item.addFieldItem(fieldItem);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return;
        }
    }

    private void getFileInfo(Map<String, FieldAndFileInfo> groupAndFileInfoMap) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(this.context.getTaskKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        ArrayList<String> groupKeys = new ArrayList<String>(groupAndFileInfoMap.keySet());
        List fileInfoByGroup = this.filePoolService.getFileInfoByGroup(groupKeys, dataScheme.getKey());
        for (FileInfosAndGroup fileInfosAndGroup : fileInfoByGroup) {
            FieldAndFileInfo fieldAndFileInfo = groupAndFileInfoMap.get(fileInfosAndGroup.getGroupKey());
            Map<String, FileKeyAndInfo> fileKeyAndInfoMap = fieldAndFileInfo.getFileKeyAndInfoMap();
            List fileInfos = fileInfosAndGroup.getFileInfos();
            for (FileInfo fileInfo : fileInfos) {
                FileKeyAndInfo fileKeyAndInfo = fileKeyAndInfoMap.get(fileInfo.getKey());
                if (null == fileKeyAndInfo) {
                    fileKeyAndInfo = new FileKeyAndInfo();
                    fileKeyAndInfo.setFileKey(fileInfo.getKey());
                    fileKeyAndInfoMap.put(fileInfo.getKey(), fileKeyAndInfo);
                }
                fileKeyAndInfo.setName(fileInfo.getName());
                fileKeyAndInfo.setExtension(fileInfo.getExtension());
                fileKeyAndInfo.setSize(fileInfo.getSize());
            }
        }
    }

    private void getFloatFieldsCheckResult() {
        if (this.floatFields.size() == 0) {
            return;
        }
        int index = 0;
        double stepValue = 0.3 / (double)this.fieldCount;
        for (Map.Entry<FieldDefine, BlobFieldInfo> fieldEntry : this.floatFields.entrySet()) {
            if (this.monitor != null) {
                this.monitor.progressAndMessage(0.5 + stepValue * (double)index, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
                ++index;
            }
            double minValue = fieldEntry.getValue().getMinValue();
            double maxValue = fieldEntry.getValue().getMaxValue();
            List<String> extList = fieldEntry.getValue().getExtList();
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormulaSchemeKey(this.context.getFormulaSchemeKey());
            queryEnvironment.setFormSchemeKey(this.context.getFormSchemeKey());
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
            dataQuery.addColumn(fieldEntry.getKey());
            dataQuery.setMasterKeys(this.dimensionValueSet);
            try {
                ExecutorContext executorContext = new ExecutorContext((IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, (IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController, this.entityViewRunTimeController, this.context.getFormSchemeKey());
                executorContext.setEnv((IFmlExecEnvironment)environment);
                executorContext.setJQReportModel(true);
                IDataTable dataTable = dataQuery.executeQuery((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext);
                ArrayList<String> addedUnitKeys = new ArrayList<String>();
                for (int i = 0; i < dataTable.getCount(); ++i) {
                    IDataRow dataRow = dataTable.getItem(i);
                    DimensionValueSet dimensionValueSet = dataRow.getRowKeys();
                    String unitKey = dimensionValueSet.getValue(this.mainDimName).toString();
                    String value = dataRow.getAsString(fieldEntry.getKey());
                    HashMap<String, FieldAndFileInfo> groupAndFileInfoMap = new HashMap<String, FieldAndFileInfo>();
                    FieldAndFileInfo fieldAndFileInfo = new FieldAndFileInfo();
                    fieldAndFileInfo.setUnitKey(unitKey);
                    fieldAndFileInfo.setFieldDefine(fieldEntry.getKey());
                    fieldAndFileInfo.setBlobFieldInfo(fieldEntry.getValue());
                    groupAndFileInfoMap.put(value, fieldAndFileInfo);
                    String rowId = dimensionValueSet.getValue("RECORDKEY").toString();
                    this.getFileInfo(groupAndFileInfoMap);
                    Map<String, FileKeyAndInfo> fileKeyAndInfoMap = fieldAndFileInfo.getFileKeyAndInfoMap();
                    addedUnitKeys.add(unitKey);
                    BlobFileSizeCheckResultItem item = null;
                    if (this.resultMap.containsKey(unitKey)) {
                        item = this.resultMap.get(unitKey);
                    } else {
                        item = this.getCheckResultItem(fieldEntry, null, unitKey, 0.0);
                        this.resultMap.put(unitKey, item);
                    }
                    BlobFileSizeCheckFieldResultItem fieldItem = new BlobFileSizeCheckFieldResultItem();
                    fieldItem.setFormCode(fieldEntry.getValue().getFormCode());
                    fieldItem.setFormKey(fieldEntry.getValue().getFormKey());
                    fieldItem.setFormTitle(fieldEntry.getValue().getFormTitle());
                    String dataLinkKey = fieldEntry.getValue().getDataLinkKey();
                    String dataRegionKey = this.dataLinkRegionCatch.get(dataLinkKey);
                    if (null == dataRegionKey) {
                        DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkKey);
                        dataRegionKey = dataLinkDefine.getRegionKey();
                        this.dataLinkRegionCatch.put(dataLinkKey, dataRegionKey);
                    }
                    fieldItem.setDataRegionKey(dataRegionKey);
                    fieldItem.setDataLinkKey(dataLinkKey);
                    fieldItem.setFieldCode(fieldEntry.getKey().getCode());
                    fieldItem.setFieldKey(fieldEntry.getKey().getKey());
                    fieldItem.setFieldTitle(fieldEntry.getKey().getTitle());
                    fieldItem.setRowId(rowId);
                    fieldItem.setNullable(fieldEntry.getKey().getNullable());
                    item.addFieldItem(fieldItem);
                    for (String fileKey : fileKeyAndInfoMap.keySet()) {
                        FileKeyAndInfo fileKeyAndInfo = fileKeyAndInfoMap.get(fileKey);
                        double fileSize = fileKeyAndInfo.getSize();
                        fileSize = fileSize / 1024.0 / 1024.0;
                        BigDecimal b = BigDecimal.valueOf(fileSize);
                        fileSize = b.setScale(2, 4).doubleValue();
                        fieldItem.addFileSize(fileSize);
                        BlobFileSizeCheckFileItem fileItem = new BlobFileSizeCheckFileItem();
                        fileItem.setFileName(fileKeyAndInfo.getName());
                        fileItem.setFileSize(fileSize);
                        boolean hasAdded = false;
                        if (fieldEntry.getValue().getIsCheckFileSize() && (fileSize < minValue || fileSize > maxValue)) {
                            fieldItem.addErrFile(fileItem);
                            hasAdded = true;
                        }
                        if (extList.size() > 0 && !hasAdded && !extList.contains(fileKeyAndInfo.getExtension())) {
                            fieldItem.addErrFile(fileItem);
                        }
                        fieldItem.addFile(fileItem);
                    }
                }
                int maxFieldItemNum = 0;
                for (String string : addedUnitKeys) {
                    BlobFileSizeCheckResultItem blobFileSizeCheckResultItem = this.resultMap.get(string);
                    List<BlobFileSizeCheckFieldResultItem> fieldItems = blobFileSizeCheckResultItem.getFieldItems();
                    int fieldItemSize = fieldItems.size();
                    if (fieldItemSize <= maxFieldItemNum) continue;
                    maxFieldItemNum = fieldItemSize;
                }
                for (Map.Entry entry : this.resultMap.entrySet()) {
                    int fieldItemSize = ((BlobFileSizeCheckResultItem)entry.getValue()).getFieldItems().size();
                    if (fieldItemSize == maxFieldItemNum) continue;
                    for (int i = 0; i < maxFieldItemNum - fieldItemSize; ++i) {
                        BlobFileSizeCheckResultItem item = (BlobFileSizeCheckResultItem)entry.getValue();
                        BlobFileSizeCheckFieldResultItem fieldItem = new BlobFileSizeCheckFieldResultItem();
                        fieldItem.setFormCode(fieldEntry.getValue().getFormCode());
                        fieldItem.setFormKey(fieldEntry.getValue().getFormKey());
                        fieldItem.setFormTitle(fieldEntry.getValue().getFormTitle());
                        String dataLinkKey = fieldEntry.getValue().getDataLinkKey();
                        String dataRegionKey = this.dataLinkRegionCatch.get(dataLinkKey);
                        if (null == dataRegionKey) {
                            DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkKey);
                            dataRegionKey = dataLinkDefine.getRegionKey();
                            this.dataLinkRegionCatch.put(dataLinkKey, dataRegionKey);
                        }
                        fieldItem.setDataRegionKey(dataRegionKey);
                        fieldItem.setDataLinkKey(dataLinkKey);
                        fieldItem.setFieldCode(fieldEntry.getKey().getCode());
                        fieldItem.setFieldKey(fieldEntry.getKey().getKey());
                        fieldItem.setFieldTitle(fieldEntry.getKey().getTitle());
                        fieldItem.setRowId(NO_ROWID);
                        fieldItem.setNullable(fieldEntry.getKey().getNullable());
                        item.addFieldItem(fieldItem);
                    }
                }
                for (String string : addedUnitKeys) {
                    int recordIndex = 0;
                    BlobFileSizeCheckResultItem blobFileSizeCheckResultItem = this.resultMap.get(string);
                    List<BlobFileSizeCheckFieldResultItem> fieldItems = blobFileSizeCheckResultItem.getFieldItems();
                    for (BlobFileSizeCheckFieldResultItem fieldItem : fieldItems) {
                        if (!fieldItem.getFieldKey().equals(fieldEntry.getKey().getKey())) continue;
                        fieldItem.setRecordIndex(recordIndex++);
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return;
            }
        }
    }

    private BlobFileSizeCheckResultItem getCheckResultItem(Map.Entry<FieldDefine, BlobFieldInfo> fieldEntry, FileInfo fileInfo, String unitKey, double fileSize) {
        BlobFileSizeCheckResultItem item = new BlobFileSizeCheckResultItem();
        item.setUnitKey(unitKey);
        item.setUnitCode(unitKey);
        if (this.entityMap.containsKey(unitKey)) {
            item.setUnitTitle(this.entityMap.get(unitKey));
            if (null != this.unitDimName) {
                item.setUnitDimName(this.unitDimName);
            }
        } else {
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            EntityViewData masterEntity = this.getMasterEntityview(this.context.getFormSchemeKey());
            if (masterEntity != null) {
                entityQueryByKeyInfo.setEntityViewKey(masterEntity.getKey());
                entityQueryByKeyInfo.setEntityKey(unitKey);
                if (this.context != null) {
                    entityQueryByKeyInfo.setContext(this.context);
                }
                IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
                EntityData entityData = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo).getEntity();
                item.setUnitTitle(entityData.getTitle());
                item.setUnitCode(entityData.getCode());
                item.setUnitDimName(masterEntity.getDimensionName());
                if (null == this.unitDimName) {
                    this.unitDimName = masterEntity.getDimensionName();
                }
                this.entityMap.put(unitKey, entityData.getTitle());
            }
        }
        if (this.context != null) {
            item.setTaskKey(this.context.getTaskKey());
            item.setFormSchemeKey(this.context.getFormSchemeKey());
        }
        item.setTaskTitle(this.taskTitle);
        item.setFormSchemeTitle(this.formSchemeTitle);
        if (fieldEntry != null) {
            item.setFormCode(fieldEntry.getValue().getFormCode());
            item.setFormKey(fieldEntry.getValue().getFormKey());
            item.setFormTitle(fieldEntry.getValue().getFormTitle());
            item.setFieldCode(fieldEntry.getKey().getCode());
            item.setFieldKey(fieldEntry.getKey().getKey());
            item.setFieldTitle(fieldEntry.getKey().getTitle());
        }
        if (fileInfo != null) {
            item.setFileName(fileInfo.getName());
        }
        item.setFileSize(fileSize);
        return item;
    }

    private void InitEntityMap() throws Exception {
        IEntityTable table;
        TaskDefine taskDefine = this.commonUtil.getTaskDefine(this.context.getTaskKey());
        if (taskDefine != null) {
            this.taskTitle = taskDefine.getTitle();
        }
        this.define = this.commonUtil.getFormScheme(this.context.getFormSchemeKey());
        if (this.define != null) {
            this.formSchemeTitle = this.define.getTitle();
        }
        if ((table = this.getEntityTable()) != null) {
            List allRows = table.getAllRows();
            int capacity = (int)((double)allRows.size() * 1.5);
            this.entityMap = capacity <= 16 ? new HashMap<String, String>() : new HashMap<String, String>(capacity);
            for (IEntityRow dataRow : allRows) {
                this.entityMap.put(dataRow.getEntityKeyData(), dataRow.getTitle());
            }
            this.untityOrderDic = this.entityQueryHelper.entityOrderByKey(table);
        } else {
            this.entityMap = new HashMap<String, String>();
            this.untityOrderDic = new HashMap();
        }
    }

    private IEntityTable getEntityTable() throws Exception {
        IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(this.context.getFormSchemeKey());
        if (this.dimensionValueSet != null) {
            entityQuery.setMasterKeys(this.dimensionValueSet);
        }
        return this.entityQueryHelper.buildEntityTable(entityQuery, this.context.getFormSchemeKey(), false);
    }

    private EntityViewData getMasterEntityview(String formSchemeKey) {
        return this.jtableParamService.getDwEntity(formSchemeKey);
    }

    private String getPeriodDimName(String formSchemeKey) {
        EntityViewData periodEntityView = this.jtableParamService.getDataTimeEntity(formSchemeKey);
        return periodEntityView.getDimensionName();
    }
}

