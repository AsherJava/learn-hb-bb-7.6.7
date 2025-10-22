/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataRowReader
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.io.common;

import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.dataset.IRegionDataSetReader;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.tz.dataset.AbstractRegionDataSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataRowReaderImpl
implements IDataRowReader {
    private static final Logger logger = LoggerFactory.getLogger(DataRowReaderImpl.class);
    private IFieldsInfo fieldsInfo;
    private final List<FieldDefine> fieldDefines;
    private final IRegionDataSetReader regionDataSetReader;
    private final IRegionDataSet regionDataSet;
    private final RegionData regionData;
    private final FormDefine formDefine;
    private Map<String, Integer> queryFieldsIndex;

    public DataRowReaderImpl(List<FieldDefine> fieldDefines, IRegionDataSetReader regionDataSetReader, IRegionDataSet regionDataSet) {
        this.fieldDefines = fieldDefines;
        this.regionDataSetReader = regionDataSetReader;
        this.regionDataSet = regionDataSet;
        this.regionData = regionDataSet.getRegionData();
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtils.getBean(IRunTimeViewController.class);
        this.formDefine = runTimeViewController.queryFormById(this.regionData.getFormKey());
    }

    public DataRowReaderImpl(List<FieldDefine> fieldDefines, IRegionDataSetReader regionDataSetReader, IRegionDataSet regionDataSet, Map<String, Integer> queryFieldsIndex) {
        this.fieldDefines = fieldDefines;
        this.regionDataSetReader = regionDataSetReader;
        this.regionDataSet = regionDataSet;
        this.queryFieldsIndex = queryFieldsIndex;
        this.regionData = regionDataSet.getRegionData();
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtils.getBean(IRunTimeViewController.class);
        this.formDefine = runTimeViewController.queryFormById(this.regionData.getFormKey());
    }

    public boolean needRowKey() {
        return this.regionDataSetReader.needRowKey();
    }

    public void start(QueryContext qContext, IFieldsInfo fieldsInfo) throws Exception {
        this.fieldsInfo = fieldsInfo;
        this.regionDataSetReader.start(this.fieldDefines);
    }

    public void readRowData(QueryContext qContext, IDataRow dataRow) throws Exception {
        ArrayList<Object> row = new ArrayList<Object>();
        for (FieldDefine fieldDefine : this.fieldDefines) {
            String fieldCode = fieldDefine.getCode();
            try {
                int index = this.queryFieldsIndex != null ? this.queryFieldsIndex.get(fieldDefine.getKey()).intValue() : this.fieldsInfo.indexOf(fieldDefine);
                Object dataObj = dataRow.getValueObject(index);
                String data = AbstractData.valueOf((Object)dataObj, (int)DataTypesConvert.fieldTypeToDataType((FieldType)this.fieldsInfo.getDataType(index))).getAsString();
                if (this.regionDataSet instanceof AbstractRegionDataSet) {
                    AbstractRegionDataSet abstractRegionDataSet = (AbstractRegionDataSet)this.regionDataSet;
                    if (fieldCode.equals("DATATIME") && dataObj == null && this.formDefine.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT) && this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                        Object datatime = qContext.getMasterKeys().getValue("DATATIME");
                        data = AbstractData.valueOf((Object)datatime, (int)DataTypesConvert.fieldTypeToDataType((FieldType)this.fieldsInfo.getDataType(index))).getAsString();
                    }
                    data = abstractRegionDataSet.dataTransfer(fieldDefine, data);
                    if (abstractRegionDataSet.getMultistageUnitReplace() != null && fieldCode.equals("MDCODE")) {
                        data = abstractRegionDataSet.getMultistageUnitReplace().getSuperiorCode(data);
                    }
                    if (abstractRegionDataSet.getMzOrgCodeRepairService() != null && "MDCODE".equals(fieldCode)) {
                        data = abstractRegionDataSet.dwValueRepair(dataRow, data);
                    }
                }
                row.add(data);
            }
            catch (DataTypeException e) {
                logger.info("\u6570\u636e\u7c7b\u578b\u8f6c\u6362\u5f02\u5e38{}", (Object)e.getMessage(), (Object)e);
            }
        }
        this.regionDataSetReader.readRowData(row);
    }

    public void finish(QueryContext qContext) throws Exception {
        this.regionDataSetReader.finish();
    }
}

