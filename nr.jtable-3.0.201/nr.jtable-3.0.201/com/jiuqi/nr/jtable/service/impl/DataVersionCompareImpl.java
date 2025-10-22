/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.engine.bean.CompareDifferenceItem
 *  com.jiuqi.nr.data.engine.bean.FixedRegionCompareDifference
 *  com.jiuqi.nr.data.engine.bean.FloatRegionCompareDifference
 *  com.jiuqi.nr.data.engine.bean.FloatUniqueKeyRegionCompareDifference
 *  com.jiuqi.nr.data.engine.bean.FormCompareDifference
 *  com.jiuqi.nr.data.engine.version.IDataVersionCompare
 *  com.jiuqi.nr.data.engine.version.IRegionCompareDifference
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nr.io.dataset.impl.RegionDataSet
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.ExpViewFields
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 */
package com.jiuqi.nr.jtable.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.engine.bean.CompareDifferenceItem;
import com.jiuqi.nr.data.engine.bean.FixedRegionCompareDifference;
import com.jiuqi.nr.data.engine.bean.FloatRegionCompareDifference;
import com.jiuqi.nr.data.engine.bean.FloatUniqueKeyRegionCompareDifference;
import com.jiuqi.nr.data.engine.bean.FormCompareDifference;
import com.jiuqi.nr.data.engine.version.IDataVersionCompare;
import com.jiuqi.nr.data.engine.version.IRegionCompareDifference;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.jtable.dataversion.compare.AbstractCompareStrategy;
import com.jiuqi.nr.jtable.dataversion.compare.FloatUniqueKeyCompareStrategy;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.CompareDiffenceUtil;
import com.jiuqi.nr.jtable.util.RegionDataCompareFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataVersionCompareImpl
implements IDataVersionCompare {
    private static final Logger logger = LoggerFactory.getLogger(DataVersionCompareImpl.class);
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    private static String FRACTION_ZERO = ".0000000000000000000000000000000";
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<FormCompareDifference> compareVersionData(Map<String, DimensionValue> dimensionSet, String formSchemeKey, List<String> formKeys, UUID initialDataVersionId, UUID compareDataVersionId) {
        ArrayList<FormCompareDifference> formDifferences = new ArrayList<FormCompareDifference>();
        for (String formKey : formKeys) {
            FormCompareDifference two;
            FormDefine queryFormById = this.runtimeView.queryFormById(formKey);
            if (queryFormById.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM) || (two = this.compareFormVersionData1(dimensionSet, formSchemeKey, formKey, initialDataVersionId, compareDataVersionId)).getUpdateRegions() == null || two.getUpdateRegions().size() <= 0) continue;
            formDifferences.add(two);
        }
        return formDifferences;
    }

    private FormCompareDifference compareFormVersionData(Map<String, DimensionValue> dimensionSet, String formSchemeKey, String formKey, UUID initialDataVersionId, UUID compareDataVersionId) {
        FormCompareDifference formCompareDifference = new FormCompareDifference();
        FormData form = this.jtableParamService.getReport(formKey, formSchemeKey);
        formCompareDifference.setFormKey(formKey);
        formCompareDifference.setFormCode(form.getCode());
        formCompareDifference.setFormName(form.getTitle());
        ArrayList<IRegionCompareDifference> regionCompareDifferences = new ArrayList<IRegionCompareDifference>();
        formCompareDifference.setUpdateRegions(regionCompareDifferences);
        List<RegionData> regions = this.jtableParamService.getRegions(formKey);
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(formSchemeKey);
        jtableContext.setFormKey(formKey);
        jtableContext.setDimensionSet(dimensionSet);
        RegionDataCompareFactory regionDataCompareFactory = new RegionDataCompareFactory();
        for (RegionData region : regions) {
            AbstractCompareStrategy regionDataVersionCompareStrategy = regionDataCompareFactory.getRegionDataVersionCompareStrategy(region);
            IRegionCompareDifference regionCompareDifference = regionDataVersionCompareStrategy.compareRegionVersionData(region, jtableContext, initialDataVersionId, compareDataVersionId);
            if (null == regionCompareDifference) continue;
            if (regionCompareDifference instanceof FixedRegionCompareDifference) {
                FixedRegionCompareDifference fixedRegionCompareDifference = (FixedRegionCompareDifference)regionCompareDifference;
                List updateItems = fixedRegionCompareDifference.getUpdateItems();
                if (null == updateItems || updateItems.size() <= 0) continue;
                regionCompareDifferences.add(regionCompareDifference);
                continue;
            }
            if (regionCompareDifference instanceof FloatRegionCompareDifference) {
                regionCompareDifferences.add(regionCompareDifference);
                continue;
            }
            FloatUniqueKeyRegionCompareDifference floatUniqueKeyRegionCompareDifference = (FloatUniqueKeyRegionCompareDifference)regionCompareDifference;
            if (null == floatUniqueKeyRegionCompareDifference.getNatures() || floatUniqueKeyRegionCompareDifference.getNatures().size() <= 0) continue;
            regionCompareDifferences.add(regionCompareDifference);
        }
        return formCompareDifference;
    }

    private FormCompareDifference compareFormVersionData1(Map<String, DimensionValue> dimensionSetMap, String formSchemeKey, String formKey, UUID initialDataVersionId, UUID compareDataVersionId) {
        FormCompareDifference formCompareDifference;
        block98: {
            ArrayList<FileInfo> tableFile;
            List<com.jiuqi.nr.io.params.base.RegionData> regions;
            ArrayList<Object> regionCompareDifferences;
            JtableContext jtableContext;
            block97: {
                jtableContext = new JtableContext();
                jtableContext.setFormSchemeKey(formSchemeKey);
                jtableContext.setFormKey(formKey);
                jtableContext.setDimensionSet(dimensionSetMap);
                formCompareDifference = new FormCompareDifference();
                FormData form = this.jtableParamService.getReport(formKey, formSchemeKey);
                formCompareDifference.setFormKey(formKey);
                formCompareDifference.setFormCode(form.getCode());
                formCompareDifference.setFormName(form.getTitle());
                regionCompareDifferences = new ArrayList<Object>();
                formCompareDifference.setUpdateRegions(regionCompareDifferences);
                regions = this.getRegions(formKey);
                DimensionValueSet dimensionSet = new DimensionValueSet();
                if (null != dimensionSetMap) {
                    for (String key : dimensionSetMap.keySet()) {
                        dimensionSet.setValue(key, (Object)dimensionSetMap.get(key).getValue());
                    }
                }
                List fieldKeys = this.runtimeView.getFieldKeysInForm(formKey);
                HashSet fieldSet = new HashSet();
                fieldSet.addAll(fieldKeys);
                FileInfo fileInfo2 = this.fileInfoService.getFileInfo(compareDataVersionId.toString() + formKey, "DataVer", FileStatus.AVAILABLE);
                FileInfo fileInfo3 = this.fileInfoService.getFileInfo(initialDataVersionId.toString() + formKey, "DataVer", FileStatus.AVAILABLE);
                tableFile = new ArrayList<FileInfo>();
                if (fileInfo2 == null && fileInfo3 == null) {
                    return this.compareFormVersionData(dimensionSetMap, formSchemeKey, formKey, initialDataVersionId, compareDataVersionId);
                }
                if (fileInfo2 != null) {
                    tableFile.add(fileInfo2);
                }
                if (fileInfo3 != null) {
                    tableFile.add(fileInfo3);
                }
                if (tableFile.isEmpty() || tableFile.size() != 1 || initialDataVersionId.toString().equals(compareDataVersionId.toString()) || !initialDataVersionId.toString().equals("00000000-0000-0000-0000-000000000000") && !compareDataVersionId.toString().equals("00000000-0000-0000-0000-000000000000")) break block97;
                for (FileInfo fileInfo : tableFile) {
                    byte[] bs = this.fileService.area("DataVer").download(fileInfo.getKey());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    String result = null;
                    try {
                        out.write(bs);
                        result = new String(out.toByteArray());
                    }
                    catch (IOException e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    if (result == null) continue;
                    List formList = new ArrayList();
                    try {
                        formList = (List)this.objectMapper.readValue(result, Object.class);
                    }
                    catch (JsonProcessingException e1) {
                        logger.error(e1.getMessage());
                    }
                    for (com.jiuqi.nr.io.params.base.RegionData region : regions) {
                        String tableKey;
                        String key;
                        Iterator fieldDataList;
                        Object fieldDefine2;
                        List listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runtimeView.getFieldKeysInRegion(region.getKey()));
                        HashSet<String> tableKeys = new HashSet<String>();
                        for (Object fieldDefine2 : listFieldDefine) {
                            tableKeys.add(fieldDefine2.getOwnerTableKey());
                        }
                        HashMap rows = new HashMap();
                        fieldDefine2 = formList.iterator();
                        while (fieldDefine2.hasNext()) {
                            Object object = fieldDefine2.next();
                            Map o = (Map)object;
                            for (String tableKey2 : tableKeys) {
                                if (null == o.get(tableKey2)) continue;
                                rows.put(tableKey2, o.get(tableKey2));
                            }
                        }
                        TableContext context = new TableContext("", formSchemeKey, formKey, dimensionSet, OptTypes.FORM, ".txt");
                        dimensionSet.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
                        context.setExpEntryFields(ExpViewFields.KEY);
                        context.setExportBizkeyorder(true);
                        context.setOrdered(false);
                        RegionDataSet dataSet = new RegionDataSet(context, region);
                        if (region.getRegionTop() == 1) {
                            FixedRegionCompareDifference fixedRegionCompareDifference = new FixedRegionCompareDifference();
                            fixedRegionCompareDifference.setRegionKey(region.getKey());
                            fixedRegionCompareDifference.setRegionName(region.getTitle());
                            ArrayList<CompareDifferenceItem> differenceItems = new ArrayList<CompareDifferenceItem>();
                            fixedRegionCompareDifference.setUpdateItems(differenceItems);
                            ArrayList row = null;
                            if (dataSet.hasNext()) {
                                row = (ArrayList)dataSet.next();
                            }
                            HashMap<String, Integer> rowIndex = new HashMap<String, Integer>();
                            for (int i = 0; i < dataSet.getFieldDataList().size(); ++i) {
                                rowIndex.put(((ExportFieldDefine)dataSet.getFieldDataList().get(i)).getCode(), i);
                            }
                            for (FieldDefine fieldDefine3 : listFieldDefine) {
                                String format1;
                                String format;
                                SimpleDateFormat sdf;
                                List list;
                                if (fieldDefine3.getType().equals((Object)FieldType.FIELD_TYPE_FILE) || fieldDefine3.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE)) continue;
                                CompareDifferenceItem differenceItem = new CompareDifferenceItem();
                                String initialValue = null;
                                String compareValue = null;
                                List deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine3.getKey()});
                                Integer integer = (Integer)rowIndex.get(((DataFieldDeployInfo)deployInfos.get(0)).getTableName() + "." + fieldDefine3.getCode());
                                if (initialDataVersionId.toString().equals("00000000-0000-0000-0000-000000000000") && null != integer) {
                                    if (null != row && !row.isEmpty() && null != row.get(integer)) {
                                        initialValue = row.get(integer).toString();
                                    }
                                    if (null != rows) {
                                        list = (List)rows.get(fieldDefine3.getOwnerTableKey());
                                        for (Map map : list) {
                                            if (null == map.get(fieldDefine3.getCode())) continue;
                                            compareValue = map.get(fieldDefine3.getCode()).toString();
                                        }
                                    }
                                } else if (null != integer) {
                                    if (null != row && !row.isEmpty() && null != row.get(integer)) {
                                        compareValue = row.get(integer).toString();
                                    }
                                    if (null != rows && null != (list = (List)rows.get(fieldDefine3.getOwnerTableKey()))) {
                                        for (Map map : list) {
                                            if (null == map.get(fieldDefine3.getCode())) continue;
                                            initialValue = map.get(fieldDefine3.getCode()).toString();
                                        }
                                    }
                                }
                                if (null == initialValue) {
                                    initialValue = "";
                                }
                                if (null == compareValue) {
                                    compareValue = "";
                                }
                                if (null == initialValue || null == compareValue || initialValue.equals(compareValue)) continue;
                                try {
                                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
                                    nf.setMinimumFractionDigits(30);
                                    Number number = nf.parse(initialValue);
                                    Number number1 = nf.parse(compareValue);
                                    BigDecimal bigDecimal = new BigDecimal(number.doubleValue());
                                    BigDecimal bigDecimal1 = new BigDecimal(number1.doubleValue());
                                    if (bigDecimal.compareTo(bigDecimal1) == 0) {
                                        continue;
                                    }
                                }
                                catch (ParseException nf) {
                                    // empty catch block
                                }
                                if (fieldDefine3.getType().equals((Object)FieldType.FIELD_TYPE_DATE)) {
                                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        format = sdf.format(new Date(Long.valueOf(String.valueOf(initialValue))));
                                    }
                                    catch (Exception e) {
                                        format = initialValue;
                                    }
                                    try {
                                        format1 = sdf.format(new Date(Long.valueOf(String.valueOf(compareValue))));
                                    }
                                    catch (Exception e) {
                                        format1 = compareValue;
                                    }
                                    if (format.equals(format1)) continue;
                                    compareValue = format1;
                                    initialValue = format;
                                }
                                if (fieldDefine3.getType().equals((Object)FieldType.FIELD_TYPE_DECIMAL)) {
                                    FieldDefine fraction = fieldDefine3;
                                    int fractionDigits = fraction.getFractionDigits() + 1;
                                    if (null != initialValue && initialValue.length() > 0 && !initialValue.contains(".") && fractionDigits > 1) {
                                        initialValue = initialValue + FRACTION_ZERO.substring(0, fractionDigits);
                                    }
                                    if (null != compareValue && compareValue.length() > 0 && !compareValue.contains(".") && fractionDigits > 1) {
                                        compareValue = compareValue + FRACTION_ZERO.substring(0, fractionDigits);
                                    }
                                }
                                if (fieldDefine3.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
                                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    try {
                                        format = sdf.format(new Date(Long.valueOf(String.valueOf(initialValue))));
                                    }
                                    catch (Exception e) {
                                        format = initialValue;
                                    }
                                    try {
                                        format1 = sdf.format(new Date(Long.valueOf(String.valueOf(compareValue))));
                                    }
                                    catch (Exception e) {
                                        format1 = compareValue;
                                    }
                                    if (format.equals(format1)) continue;
                                    compareValue = format1;
                                    initialValue = format;
                                }
                                differenceItem.setInitialValue(initialValue);
                                differenceItem.setCompareValue(compareValue);
                                String difference = CompareDiffenceUtil.compareDifference(fieldDefine3.getType().getValue(), initialValue, compareValue);
                                differenceItem.setDifference(difference);
                                differenceItem.setFieldKey(fieldDefine3.getKey());
                                differenceItem.setFieldCode(fieldDefine3.getCode());
                                differenceItem.setFieldTitle(fieldDefine3.getTitle());
                                List linksInRegionByField = this.runtimeView.getLinksInRegionByField(region.getKey(), fieldDefine3.getKey());
                                if (null != linksInRegionByField && !linksInRegionByField.isEmpty()) {
                                    differenceItem.setDataLinkKey(((DataLinkDefine)linksInRegionByField.get(0)).getKey());
                                    String entityKey = fieldDefine3.getEntityKey();
                                    if (StringUtils.isNotEmpty((String)entityKey)) {
                                        EntityQueryByKeyInfo queryEntityDataByKey = new EntityQueryByKeyInfo();
                                        queryEntityDataByKey.setEntityViewKey(entityKey);
                                        queryEntityDataByKey.setEntityKey(initialValue);
                                        queryEntityDataByKey.setContext(jtableContext);
                                        EntityByKeyReturnInfo entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKey(queryEntityDataByKey);
                                        EntityData entity = entityByKeyReturnInfo.getEntity();
                                        differenceItem.setInitialValue(entity.getTitle());
                                        queryEntityDataByKey.setEntityKey(compareValue);
                                        entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKey(queryEntityDataByKey);
                                        entity = entityByKeyReturnInfo.getEntity();
                                        differenceItem.setCompareValue(entity.getTitle());
                                    }
                                }
                                differenceItems.add(differenceItem);
                            }
                            if (fixedRegionCompareDifference.getUpdateItems().isEmpty()) continue;
                            regionCompareDifferences.add(fixedRegionCompareDifference);
                            continue;
                        }
                        if (!region.getAllowDuplicateKey()) {
                            FloatUniqueKeyRegionCompareDifference difference;
                            RegionData regionData = new RegionData();
                            BeanUtils.copyProperties(region, regionData);
                            FloatUniqueKeyCompareStrategy fuk = new FloatUniqueKeyCompareStrategy();
                            fieldDataList = dataSet.getFieldDataList();
                            ArrayList<Map<String, Object>> initalRows = new ArrayList();
                            ArrayList<Map<String, Object>> compareRows = new ArrayList();
                            while (dataSet.hasNext()) {
                                ArrayList arrayList = (ArrayList)dataSet.next();
                                if (null == arrayList || arrayList.isEmpty()) continue;
                                HashMap row = new HashMap();
                                int i = 0;
                                Iterator iterator = fieldDataList.iterator();
                                while (iterator.hasNext()) {
                                    ExportFieldDefine define = (ExportFieldDefine)iterator.next();
                                    if (i >= arrayList.size()) continue;
                                    row.put(define.getCode().split("\\.")[1], arrayList.get(i));
                                    ++i;
                                }
                                if (initialDataVersionId.equals("00000000-0000-0000-0000-000000000000")) {
                                    initalRows.add(row);
                                    continue;
                                }
                                compareRows.add(row);
                            }
                            if (!initialDataVersionId.equals("00000000-0000-0000-0000-000000000000")) {
                                initalRows = (List)rows.get(tableKeys.iterator().next());
                            } else {
                                compareRows = (List)rows.get(tableKeys.iterator().next());
                            }
                            if (null == (difference = fuk.compareRegionVersionDataFile(regionData, jtableContext, initialDataVersionId, compareDataVersionId, initalRows, compareRows)).getNatures() || difference.getNatures().size() <= 0) continue;
                            regionCompareDifferences.add(difference);
                            continue;
                        }
                        if (dataSet.hasNext()) {
                            dataSet.next();
                        }
                        FloatRegionCompareDifference floatRegionCompareDifference = new FloatRegionCompareDifference();
                        floatRegionCompareDifference.setRegionKey(region.getKey());
                        floatRegionCompareDifference.setRegionName(region.getTitle());
                        if (initialDataVersionId.toString().equals("00000000-0000-0000-0000-000000000000")) {
                            floatRegionCompareDifference.setInitialRows(dataSet.getTotalCount());
                            key = "";
                            fieldDataList = tableKeys.iterator();
                            if (fieldDataList.hasNext()) {
                                key = tableKey = (String)fieldDataList.next();
                            }
                            floatRegionCompareDifference.setCompareRows(rows.get(key) == null ? 0 : ((List)rows.get(key)).size());
                        } else {
                            floatRegionCompareDifference.setCompareRows(dataSet.getTotalCount());
                            key = "";
                            fieldDataList = tableKeys.iterator();
                            if (fieldDataList.hasNext()) {
                                key = tableKey = (String)fieldDataList.next();
                            }
                            floatRegionCompareDifference.setInitialRows(rows.get(key) == null ? 0 : ((List)rows.get(key)).size());
                        }
                        floatRegionCompareDifference.setDifferenceRows(floatRegionCompareDifference.getCompareRows() - floatRegionCompareDifference.getInitialRows());
                        if (floatRegionCompareDifference.getDifferenceRows() == 0) continue;
                        regionCompareDifferences.add(floatRegionCompareDifference);
                    }
                }
                break block98;
            }
            if (tableFile.isEmpty() || tableFile.size() != 2) break block98;
            HashMap versionDatas = new HashMap();
            for (FileInfo fileInfo : tableFile) {
                byte[] bs = this.fileService.area("DataVer").download(fileInfo.getKey());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Object result = null;
                try {
                    out.write(bs);
                    result = new String(out.toByteArray());
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                if (result == null) continue;
                List formList = new ArrayList();
                try {
                    formList = (List)this.objectMapper.readValue((String)result, Object.class);
                }
                catch (JsonProcessingException e) {
                    logger.error(e.getMessage());
                }
                versionDatas.put(fileInfo.getName(), formList);
            }
            for (com.jiuqi.nr.io.params.base.RegionData region : regions) {
                Object o;
                List listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runtimeView.getFieldKeysInRegion(region.getKey()));
                HashSet<String> tableKeys = new HashSet<String>();
                for (FieldDefine fieldDefine : listFieldDefine) {
                    tableKeys.add(fieldDefine.getOwnerTableKey());
                }
                HashMap initialRows = new HashMap();
                HashMap compareRows = new HashMap();
                for (Object object : (List)versionDatas.get(compareDataVersionId.toString())) {
                    o = (Map)object;
                    for (String tableKey : tableKeys) {
                        if (null == o.get(tableKey)) continue;
                        compareRows.put(tableKey, o.get(tableKey));
                    }
                }
                for (Object object : (List)versionDatas.get(initialDataVersionId.toString())) {
                    o = (Map)object;
                    for (String tableKey : tableKeys) {
                        if (null == o.get(tableKey)) continue;
                        initialRows.put(tableKey, o.get(tableKey));
                    }
                }
                if (region.getRegionTop() == 1) {
                    FixedRegionCompareDifference fixedRegionCompareDifference = new FixedRegionCompareDifference();
                    fixedRegionCompareDifference.setRegionKey(region.getKey());
                    fixedRegionCompareDifference.setRegionName(region.getTitle());
                    ArrayList<CompareDifferenceItem> differenceItems = new ArrayList<CompareDifferenceItem>();
                    fixedRegionCompareDifference.setUpdateItems(differenceItems);
                    for (FieldDefine fieldDefine : listFieldDefine) {
                        String format1;
                        String format;
                        SimpleDateFormat sdf;
                        List list;
                        CompareDifferenceItem differenceItem = new CompareDifferenceItem();
                        String initialValue = null;
                        String compareValue = null;
                        if (null != initialRows && null != (list = (List)initialRows.get(fieldDefine.getOwnerTableKey()))) {
                            for (Map map : list) {
                                if (null != map.get(fieldDefine.getCode())) {
                                    initialValue = map.get(fieldDefine.getCode()).toString();
                                    continue;
                                }
                                initialValue = "";
                            }
                        }
                        if (null != compareRows && null != (list = (List)compareRows.get(fieldDefine.getOwnerTableKey()))) {
                            for (Map map : list) {
                                if (null != map.get(fieldDefine.getCode())) {
                                    compareValue = map.get(fieldDefine.getCode()).toString();
                                    continue;
                                }
                                compareValue = "";
                            }
                        }
                        if (null == initialValue || null == compareValue || initialValue.equals(compareValue)) continue;
                        try {
                            Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(initialValue);
                            Number number1 = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(compareValue);
                            BigDecimal bigDecimal = new BigDecimal(number.doubleValue());
                            BigDecimal bigDecimal1 = new BigDecimal(number1.doubleValue());
                            if (bigDecimal.compareTo(bigDecimal1) == 0) {
                                continue;
                            }
                        }
                        catch (ParseException number) {
                            // empty catch block
                        }
                        if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DATE)) {
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                format = sdf.format(new Date(Long.valueOf(String.valueOf(initialValue))));
                            }
                            catch (Exception e) {
                                format = initialValue;
                            }
                            try {
                                format1 = sdf.format(new Date(Long.valueOf(String.valueOf(compareValue))));
                            }
                            catch (Exception e) {
                                format1 = compareValue;
                            }
                            if (format.equals(format1)) continue;
                            compareValue = format1;
                            initialValue = format;
                        }
                        if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
                            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                format = sdf.format(new Date(Long.valueOf(String.valueOf(initialValue))));
                            }
                            catch (Exception e) {
                                format = initialValue;
                            }
                            try {
                                format1 = sdf.format(new Date(Long.valueOf(String.valueOf(compareValue))));
                            }
                            catch (Exception e) {
                                format1 = compareValue;
                            }
                            if (format.equals(format1)) continue;
                            compareValue = format1;
                            initialValue = format;
                        }
                        if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DECIMAL)) {
                            int fractionDigits = fieldDefine.getFractionDigits() + 1;
                            if (null != initialValue && !initialValue.contains(".") && fractionDigits > 1) {
                                if (initialValue.length() == 0) {
                                    initialValue = "0";
                                }
                                initialValue = initialValue + FRACTION_ZERO.substring(0, fractionDigits);
                            }
                            if (null != compareValue && !compareValue.contains(".") && fractionDigits > 1) {
                                if (compareValue.length() == 0) {
                                    compareValue = "0";
                                }
                                compareValue = compareValue + FRACTION_ZERO.substring(0, fractionDigits);
                            }
                        }
                        differenceItem.setInitialValue(initialValue);
                        differenceItem.setCompareValue(compareValue);
                        String difference = CompareDiffenceUtil.compareDifference(fieldDefine.getType().getValue(), initialValue, compareValue);
                        differenceItem.setDifference(difference);
                        differenceItem.setFieldKey(fieldDefine.getKey());
                        differenceItem.setFieldCode(fieldDefine.getCode());
                        differenceItem.setFieldTitle(fieldDefine.getTitle());
                        List linksInRegionByField = this.runtimeView.getLinksInRegionByField(region.getKey(), fieldDefine.getKey());
                        if (null != linksInRegionByField && !linksInRegionByField.isEmpty()) {
                            differenceItem.setDataLinkKey(((DataLinkDefine)linksInRegionByField.get(0)).getKey());
                        }
                        differenceItems.add(differenceItem);
                    }
                    if (fixedRegionCompareDifference.getUpdateItems().isEmpty()) continue;
                    regionCompareDifferences.add(fixedRegionCompareDifference);
                    continue;
                }
                if (!region.getAllowDuplicateKey()) {
                    List compareRows1;
                    RegionData regionData = new RegionData();
                    BeanUtils.copyProperties(region, regionData);
                    FloatUniqueKeyCompareStrategy fuk = new FloatUniqueKeyCompareStrategy();
                    List initalRows1 = (List)initialRows.get(tableKeys.iterator().next());
                    FloatUniqueKeyRegionCompareDifference difference = fuk.compareRegionVersionDataFile(regionData, jtableContext, initialDataVersionId, compareDataVersionId, initalRows1, compareRows1 = (List)compareRows.get(tableKeys.iterator().next()));
                    if (null == difference.getNatures() || difference.getNatures().size() <= 0) continue;
                    regionCompareDifferences.add(difference);
                    continue;
                }
                FloatRegionCompareDifference floatRegionCompareDifference = new FloatRegionCompareDifference();
                floatRegionCompareDifference.setRegionKey(region.getKey());
                floatRegionCompareDifference.setRegionName(region.getTitle());
                floatRegionCompareDifference.setInitialRows(initialRows.get(((FieldDefine)listFieldDefine.get(0)).getOwnerTableKey()) == null ? 0 : ((List)initialRows.get(((FieldDefine)listFieldDefine.get(0)).getOwnerTableKey())).size());
                floatRegionCompareDifference.setCompareRows(compareRows.get(((FieldDefine)listFieldDefine.get(0)).getOwnerTableKey()) == null ? 0 : ((List)compareRows.get(((FieldDefine)listFieldDefine.get(0)).getOwnerTableKey())).size());
                floatRegionCompareDifference.setDifferenceRows(floatRegionCompareDifference.getCompareRows() - floatRegionCompareDifference.getInitialRows());
                if (floatRegionCompareDifference.getDifferenceRows() == 0) continue;
                regionCompareDifferences.add(floatRegionCompareDifference);
            }
        }
        return formCompareDifference;
    }

    private List<com.jiuqi.nr.io.params.base.RegionData> getRegions(String formKey) {
        ArrayList<com.jiuqi.nr.io.params.base.RegionData> regions = new ArrayList<com.jiuqi.nr.io.params.base.RegionData>();
        List allRegionDefines = this.runtimeView.getAllRegionsInForm(formKey);
        for (DataRegionDefine dataRegionDefine : allRegionDefines) {
            com.jiuqi.nr.io.params.base.RegionData regionData = new com.jiuqi.nr.io.params.base.RegionData();
            regionData.initialize(dataRegionDefine);
            regions.add(regionData);
        }
        return regions;
    }

    private IRegionCompareDifference getCompareRegionVersionData(RegionData region, JtableContext jtableContext, UUID initialDataVersionId, UUID compareDataVersionId) {
        if (region.getRegionTop() > 1) {
            FloatRegionCompareDifference floatRegionCompareDifference = new FloatRegionCompareDifference();
            floatRegionCompareDifference.setRegionKey(region.getKey());
            floatRegionCompareDifference.setRegionName(region.getTitle());
            return floatRegionCompareDifference;
        }
        return null;
    }
}

