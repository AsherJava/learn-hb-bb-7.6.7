/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.customExcelBatchImport.service.impl;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelCheckResultInfo;
import com.jiuqi.nr.customExcelBatchImport.service.ICustomExcelRegionTitleService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomExcelRegionTitleServiceImpl
implements ICustomExcelRegionTitleService {
    private static final Logger log = LoggerFactory.getLogger(CustomExcelRegionTitleServiceImpl.class);
    @Resource
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Resource
    private IRunTimeViewController runtimeViewController;
    @Resource
    private IRuntimeDataRegionService iRuntimeDataRegionService;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IDimensionProvider dimensionProvider;

    @Override
    public Map<String, DataRegionDefine> getAllRegionTitleMap(String formSchemeKey) {
        HashMap<String, DataRegionDefine> regionDefineMap = new HashMap<String, DataRegionDefine>();
        List formDefineList = this.runtimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        for (FormDefine formDefine : formDefineList) {
            if (FormType.FORM_TYPE_NEWFMDM.name().equals(formDefine.getFormType().name())) continue;
            List dataRegionDefines = this.iRuntimeDataRegionService.getDataRegionsInForm(formDefine.getKey());
            for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
                String title = this.getRegionTitle(formDefine, dataRegionDefine);
                regionDefineMap.put(title, dataRegionDefine);
            }
        }
        return regionDefineMap;
    }

    @Override
    public String getTitleByRegion(DataRegionDefine dataRegionDefine) {
        FormDefine formDefine = this.runtimeViewController.queryFormById(dataRegionDefine.getFormKey());
        return this.getRegionTitle(formDefine, dataRegionDefine);
    }

    private String getRegionTitle(FormDefine formDefine, DataRegionDefine dataRegionDefine) {
        String separator = " ";
        StringBuilder fileName = new StringBuilder();
        fileName.append(formDefine.getFormCode());
        fileName.append(separator);
        fileName.append(formDefine.getTitle().length() > 10 ? formDefine.getTitle().substring(0, 10) : formDefine.getTitle());
        if (dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
            Position position = new Position(dataRegionDefine.getRegionLeft(), dataRegionDefine.getRegionTop());
            fileName.append(separator);
            fileName.append(position.toString());
        }
        return fileName.toString();
    }

    @Override
    public DataRegionDefine getRegionByTitle(String formSchemeKey, String regionTitle) {
        List formDefineList = this.runtimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        for (FormDefine formDefine : formDefineList) {
            List dataRegionDefines = this.iRuntimeDataRegionService.getDataRegionsInForm(formDefine.getKey());
            for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
                String title = this.getRegionTitle(formDefine, dataRegionDefine);
                if (!title.equals(regionTitle)) continue;
                return dataRegionDefine;
            }
        }
        return null;
    }

    @Override
    public List<FieldDefine> getRegionFieldDefineList(DataRegionDefine dataRegionDefine) {
        ArrayList<FieldDefine> filedInfoList = new ArrayList<FieldDefine>();
        List dataLinkDefineList = this.runtimeViewController.getAllLinksInRegion(dataRegionDefine.getKey());
        Collections.sort(dataLinkDefineList, new Comparator<DataLinkDefine>(){

            @Override
            public int compare(DataLinkDefine linkData0, DataLinkDefine linkData1) {
                if (linkData0.getPosY() != linkData1.getPosY()) {
                    return linkData0.getPosY() - linkData1.getPosY();
                }
                return linkData0.getPosX() - linkData1.getPosX();
            }
        });
        for (DataLinkDefine dataLinkDefine : dataLinkDefineList) {
            if (dataLinkDefine.getType() != DataLinkType.DATA_LINK_TYPE_FIELD) continue;
            FieldDefine fieldDefine = null;
            try {
                fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(dataLinkDefine.getLinkExpression());
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            if (fieldDefine == null) continue;
            filedInfoList.add(fieldDefine);
        }
        return filedInfoList;
    }

    @Override
    public Map<String, List<FieldDefine>> getDimFieldDefineMap(DataRegionDefine dataRegionDefine, CustomExcelCheckResultInfo customExcelCheckResultInfo) {
        HashMap<String, List<FieldDefine>> regionFiledInfoMap = new HashMap<String, List<FieldDefine>>();
        ArrayList tables = new ArrayList();
        try {
            List fieldKeys = this.runtimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
            List tableDefines = this.dataDefinitionRuntimeController.queryTableDefinesByFields((Collection)fieldKeys);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            String bizKey = dataRegionDefine.getBizKeyFields();
            for (TableDefine tableDefine : tableDefines) {
                String[] bizKeyFields;
                for (String field : bizKeyFields = tableDefine.getBizKeyFieldsID()) {
                    FieldDefine fieldDefine = null;
                    try {
                        fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(field);
                    }
                    catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    if (bizKey.contains(fieldDefine.getKey())) continue;
                    if (fieldDefine.getCode().equalsIgnoreCase("DATATIME") && customExcelCheckResultInfo != null) {
                        customExcelCheckResultInfo.setPeriodFiledInfo(fieldDefine);
                        continue;
                    }
                    if (fieldDefine.getCode().equalsIgnoreCase("ADJUST") && customExcelCheckResultInfo != null) {
                        customExcelCheckResultInfo.setAdjustFiledInfo(fieldDefine);
                        continue;
                    }
                    if (fieldDefine == null || fieldDefine.getValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER) continue;
                    String fieldDimensionName = this.dimensionProvider.getFieldDimensionName(executorContext, fieldDefine);
                    List<FieldDefine> filedInfoList = null;
                    if (regionFiledInfoMap.containsKey(fieldDimensionName)) {
                        filedInfoList = (List)regionFiledInfoMap.get(fieldDimensionName);
                    } else {
                        filedInfoList = new ArrayList();
                        regionFiledInfoMap.put(fieldDimensionName, filedInfoList);
                    }
                    filedInfoList.add(fieldDefine);
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return regionFiledInfoMap;
    }
}

