/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.task.service.impl;

import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.JIOParamParser;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nr.single.core.task.SingleTaskException;
import com.jiuqi.nr.single.core.task.model.SingleFieldInfo;
import com.jiuqi.nr.single.core.task.model.SingleRegionInfo;
import com.jiuqi.nr.single.core.task.model.SingleTableInfo;
import com.jiuqi.nr.single.core.task.service.ISingleTaskData;
import com.jiuqi.nr.single.core.task.service.ISingleTaskParamReader;
import com.jiuqi.nr.single.core.task.service.impl.SingleTaskDataImpl;
import com.jiuqi.nr.single.core.task.util.SingleTaskUtils;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleTaskParamReaderImpl
implements ISingleTaskParamReader {
    private static final Logger logger = LoggerFactory.getLogger(SingleTaskDataImpl.class);
    private ISingleTaskData taskData;
    private JIOParamParser jioParaser;
    private List<InOutDataType> inOutDatas;
    private ParaInfo paraInfo;
    private List<SingleTableInfo> tableList;

    public SingleTaskParamReaderImpl(ISingleTaskData taskData) throws SingleTaskException {
        this.taskData = taskData;
        this.inOutDatas = new ArrayList<InOutDataType>();
        String bbbtFile = SinglePathUtil.getNewFilePath(this.taskData.getTaskParaDir(), "BBBT.DBF");
        try {
            if (SinglePathUtil.getFileExists(bbbtFile)) {
                this.inOutDatas.add(InOutDataType.BBCS);
            }
        }
        catch (SingleFileException e1) {
            logger.error(e1.getMessage(), e1);
            new SingleFileException(e1.getMessage(), e1);
        }
        if (this.inOutDatas.isEmpty()) {
            return;
        }
        this.jioParaser = new JIOParamParser(this.taskData.getTaskDir(), false);
        this.jioParaser.setInOutData(this.inOutDatas);
        try {
            this.jioParaser.parse();
            this.paraInfo = this.jioParaser.getParaInfo();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            new SingleFileException(e.getMessage(), e);
        }
    }

    @Override
    public List<SingleTableInfo> getSingleTableInfos() {
        if (this.tableList == null) {
            this.tableList = new ArrayList<SingleTableInfo>();
            if (this.paraInfo != null) {
                for (RepInfo rep : this.paraInfo.getRepInfos()) {
                    SingleTableInfo tableInfo = new SingleTableInfo();
                    tableInfo.setTableFlag(rep.getCode());
                    tableInfo.setTableTitle(rep.getTitle());
                    tableInfo.setCondition(rep.getFilter());
                    FieldDefs oldDef = rep.getDefs();
                    SingleRegionInfo newRegion = tableInfo.getFixRegion();
                    this.copyFromDef(newRegion, oldDef);
                    newRegion.setFloatingId(-1);
                    for (FieldDefs oldSubDef : oldDef.getSubMbs()) {
                        SingleRegionInfo newSubRegion = new SingleRegionInfo();
                        newSubRegion.setFloatingId(oldSubDef.getRegionInfo().getMapArea().getFloatRangeStartNo());
                        this.copyFromDef(newSubRegion, oldSubDef);
                        tableInfo.getChildRegions().add(newSubRegion);
                    }
                    this.tableList.add(tableInfo);
                }
            }
        }
        return this.tableList;
    }

    private void copyFromDef(SingleRegionInfo newRegion, FieldDefs oldDef) {
        for (ZBInfo zb : oldDef.getZbsNoZDM()) {
            SingleFieldInfo field = new SingleFieldInfo();
            field.setFieldCode(zb.getFieldName());
            field.setFieldTitle(zb.getZbTitle());
            field.setPrecision(zb.getLength());
            field.setDecimal(zb.getDecimal());
            field.setDataType(SingleTaskUtils.getSingleDataType(zb.getDataType()));
            newRegion.getFields().add(field);
        }
    }

    @Override
    public boolean isIncloudParam() {
        boolean result = false;
        if (this.paraInfo != null) {
            result = true;
        }
        return result;
    }
}

