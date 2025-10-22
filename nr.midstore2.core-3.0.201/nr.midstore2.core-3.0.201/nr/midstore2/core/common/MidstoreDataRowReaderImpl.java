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
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package nr.midstore2.core.common;

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
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import java.util.ArrayList;
import java.util.List;
import nr.midstore2.core.dataset.IMidstoreDataSet;
import nr.midstore2.core.dataset.IMidstoreRegionDataSetReader;
import nr.midstore2.core.internal.dataset.AbstractMidstoreDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MidstoreDataRowReaderImpl
implements IDataRowReader {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreDataRowReaderImpl.class);
    private IFieldsInfo fieldsInfo;
    private final List<FieldDefine> fieldDefines;
    private final IMidstoreRegionDataSetReader regionDataSetReader;
    private DataTable dataTable = null;
    private final IMidstoreDataSet regionDataSet;

    public MidstoreDataRowReaderImpl(List<FieldDefine> fieldDefines, IMidstoreRegionDataSetReader regionDataSetReader, IMidstoreDataSet regionDataSet) {
        this.fieldDefines = fieldDefines;
        this.regionDataSetReader = regionDataSetReader;
        this.regionDataSet = regionDataSet;
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtils.getBean(IRuntimeDataSchemeService.class);
        if (regionDataSet != null && regionDataSet.getRegionData() != null) {
            this.dataTable = runtimeDataSchemeService.getDataTable(regionDataSet.getRegionData().getDataTableKey());
        }
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
            try {
                int index = this.fieldsInfo.indexOf(fieldDefine);
                Object dataObj = dataRow.getValueObject(index);
                String data = AbstractData.valueOf((Object)dataObj, (int)DataTypesConvert.fieldTypeToDataType((FieldType)this.fieldsInfo.getDataType(index))).getAsString();
                if (this.regionDataSet instanceof AbstractMidstoreDataSet) {
                    AbstractMidstoreDataSet abstractRegionDataSet = (AbstractMidstoreDataSet)this.regionDataSet;
                    if (fieldDefine.getCode().equals("DATATIME") && dataObj == null && this.dataTable != null && this.dataTable.getDataTableType().equals((Object)DataTableType.ACCOUNT)) {
                        Object datatime = qContext.getMasterKeys().getValue("DATATIME");
                        data = AbstractData.valueOf((Object)datatime, (int)DataTypesConvert.fieldTypeToDataType((FieldType)this.fieldsInfo.getDataType(index))).getAsString();
                    }
                    data = abstractRegionDataSet.dataTransfer(fieldDefine, data);
                    if (abstractRegionDataSet.getMultistageUnitReplace() != null && fieldDefine.getCode().equals("MDCODE")) {
                        data = abstractRegionDataSet.getMultistageUnitReplace().getSuperiorCode(data);
                    }
                    if (abstractRegionDataSet.getMzOrgCodeRepairService() != null && "MDCODE".equals(fieldDefine.getCode())) {
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

