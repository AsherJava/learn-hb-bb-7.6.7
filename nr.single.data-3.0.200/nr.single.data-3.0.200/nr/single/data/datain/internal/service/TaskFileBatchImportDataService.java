/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.io.dataset.IRegionDataSet
 *  com.jiuqi.nr.io.dataset.impl.RegionDataSet
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.ExpViewFields
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet
 *  javax.annotation.Resource
 *  nr.single.map.data.TaskDataContext
 */
package nr.single.data.datain.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import nr.single.data.datain.service.ITaskFileBatchImportDataService;
import nr.single.map.data.TaskDataContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskFileBatchImportDataService
implements ITaskFileBatchImportDataService {
    private static final Logger logger = LoggerFactory.getLogger(TaskFileBatchImportDataService.class);
    @Autowired
    private IRunTimeViewController viewController;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    private Map<String, String> uuidCache = null;

    @Override
    public Map<String, DimensionValue> getNewDimensionSet(Map<String, DimensionValue> dimensionValueSet) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        for (String code : dimensionValueSet.keySet()) {
            DimensionValue value = new DimensionValue();
            value.setName(code);
            value.setValue(dimensionValueSet.get(code) != null ? dimensionValueSet.get(code).getValue() : "");
            dimensionSet.put(value.getName(), value);
        }
        return dimensionSet;
    }

    @Override
    public IRegionDataSet getImportBatchRegionDataSet(TableContext tableContext, int regionTop, List<String> fieldsArr) {
        List<RegionData> regionList = this.getFormRegions(tableContext.getFormKey());
        RegionData region = this.getRegionData(regionList, regionTop);
        ArrayList<ExportFieldDefine> fields = new ArrayList<ExportFieldDefine>();
        for (String field : fieldsArr) {
            ExportFieldDefine efd = new ExportFieldDefine();
            efd.setCode(field);
            if (StringUtils.isNotEmpty((String)field) && field.contains(".")) {
                int id = field.indexOf(".");
                String fieldCode = field.substring(id + ".".length(), field.length());
                efd.setCode(fieldCode);
            }
            fields.add(efd);
        }
        FormDefine formDefine = this.viewController.queryFormById(tableContext.getFormKey());
        Object regionDataSet = null;
        regionDataSet = formDefine.getFormType() == FormType.FORM_TYPE_ACCOUNT && region != null && region.getRegionTop() > 1 ? new SBRegionDataSet(tableContext, region, fields) : new RegionDataSet(tableContext, region, fields);
        return regionDataSet;
    }

    @Override
    public IRegionDataSet getBatchExportRegionDataSet(TableContext tableContext, RegionData region) {
        boolean isAccountRegion = this.isTzDataRegion(tableContext, region);
        Object regionDataSet = null;
        regionDataSet = isAccountRegion ? new SBRegionDataSet(tableContext, region) : new RegionDataSet(tableContext, region);
        return regionDataSet;
    }

    @Override
    public void openTempTable(TaskDataContext importContext, List<String> corpKeys) {
        try {
            TempAssistantTable tempTable = new TempAssistantTable(corpKeys, 6);
            tempTable.createTempTable();
            tempTable.insertIntoTempTable();
            importContext.getIntfObjects().put("FMDMtempTable", tempTable);
        }
        catch (Exception e) {
            importContext.error(logger, e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void closeTempTable(TaskDataContext importContext) {
        TempAssistantTable tempTable = (TempAssistantTable)importContext.getIntfObjects().get("FMDMtempTable");
        if (tempTable != null) {
            try {
                tempTable.close();
                importContext.getIntfObjects().remove("FMDMtempTable");
            }
            catch (Exception e) {
                importContext.error(logger, e.getMessage(), (Throwable)e);
            }
        }
    }

    @Override
    public void setTempTable(TaskDataContext importContext, TableContext tableContext) {
        TempAssistantTable tempTable = (TempAssistantTable)importContext.getIntfObjects().get("FMDMtempTable");
        if (tempTable != null) {
            tableContext.setTempAssistantTable(importContext.getEntityCompanyType(), tempTable);
        }
    }

    @Override
    public IRunTimeViewController getViewController() {
        return this.viewController;
    }

    @Override
    public TableContext getTableContex(Map<String, DimensionValue> dimSetMap, String taskKey, String formShecemKey, String formKey, String syncTaskID) {
        DimensionValueSet dimensionSet = new DimensionValueSet();
        if (null != dimSetMap) {
            Map<String, String> uCache = this.getStringKey();
            for (String key : dimSetMap.keySet()) {
                try {
                    String value = null;
                    ArrayList<String> ids = null;
                    DimensionValue dimValue = dimSetMap.get(key);
                    String dimensionValue = dimValue.getValue();
                    if (dimensionValue instanceof List) {
                        ids = (ArrayList<String>)((Object)dimensionValue);
                        for (String code : ids) {
                            uCache.put(code, code);
                        }
                    } else if (dimensionValue instanceof String) {
                        String vCode = dimValue.getValue();
                        if (uCache.containsKey(vCode)) {
                            value = uCache.get(vCode);
                        } else {
                            String[] codes = vCode.split(",");
                            String[] codes2 = vCode.split(";");
                            if (codes.length > 1) {
                                ids = new ArrayList<String>();
                                for (String code : codes) {
                                    ids.add(code);
                                    uCache.put(code, code);
                                }
                            } else if (codes2.length > 1) {
                                ids = new ArrayList();
                                for (String code : codes2) {
                                    ids.add(code);
                                    uCache.put(code, code);
                                }
                            } else {
                                uCache.put(vCode, vCode);
                            }
                        }
                    }
                    if (null != value) {
                        dimensionSet.setValue(key, value);
                        continue;
                    }
                    if (ids != null) {
                        dimensionSet.setValue(key, (Object)ids);
                        continue;
                    }
                    dimensionSet.setValue(key, (Object)dimValue.getValue());
                }
                catch (Exception e) {
                    dimensionSet.setValue(key, (Object)dimSetMap.get(key).getValue());
                    logger.error(e.getMessage(), e);
                }
            }
        }
        TableContext context = new TableContext(taskKey, formShecemKey, formKey, dimensionSet, OptTypes.FORM, "", "", syncTaskID);
        context.setAttachment(false);
        context.setAttachmentArea("JTABLEAREA");
        context.setExpEntryFields(ExpViewFields.valueOf((String)"code".toUpperCase()));
        context.setExpEnumFields(ExpViewFields.valueOf((String)"code".toUpperCase()));
        return context;
    }

    @Override
    public List<RegionData> getFormRegions(String formKey) {
        List allRegions = this.viewController.getAllRegionsInForm(formKey);
        ArrayList<RegionData> regions = new ArrayList<RegionData>();
        for (DataRegionDefine dataRegionDefine : allRegions) {
            RegionData regionData = new RegionData();
            regionData.initialize(dataRegionDefine);
            regions.add(regionData);
        }
        return regions;
    }

    private RegionData getRegionData(List<RegionData> formRegions, int regionTop) {
        RegionData region = null;
        for (RegionData regionData : formRegions) {
            if (regionData.getType() == 2) {
                if (regionData.getRegionLeft() != regionTop) continue;
                region = regionData;
                break;
            }
            if (regionData.getRegionTop() != regionTop) continue;
            region = regionData;
            break;
        }
        return region;
    }

    @Override
    public RegionData getRegionDataByForm(String formKey, int regionTop) {
        List<RegionData> formRegions = this.getFormRegions(formKey);
        return this.getRegionData(formRegions, regionTop);
    }

    private Map<String, String> getStringKey() {
        if (null == this.uuidCache) {
            this.uuidCache = new HashMap<String, String>();
        }
        return this.uuidCache;
    }

    @Override
    public boolean isTzDataRegion(TableContext tableContext, RegionData region) {
        FormDefine formDefine;
        boolean isAccountRegion = false;
        if (region != null && region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue() && (formDefine = this.viewController.queryFormById(region.getFormKey())) != null && formDefine.getFormType() == FormType.FORM_TYPE_ACCOUNT) {
            isAccountRegion = true;
        }
        return isAccountRegion;
    }
}

