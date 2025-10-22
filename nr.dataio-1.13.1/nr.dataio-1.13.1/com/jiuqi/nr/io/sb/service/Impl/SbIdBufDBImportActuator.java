/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.bufdb.BufDBException
 *  com.jiuqi.bi.bufdb.db.IIndexedCursor
 *  com.jiuqi.bi.bufdb.db.IRecord
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 */
package com.jiuqi.nr.io.sb.service.Impl;

import com.jiuqi.bi.bufdb.BufDBException;
import com.jiuqi.bi.bufdb.db.IIndexedCursor;
import com.jiuqi.bi.bufdb.db.IRecord;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.io.sb.bean.RowDimValue;
import com.jiuqi.nr.io.sb.service.Impl.BufDBImportActuator;
import com.jiuqi.nr.io.tz.bean.FlagState;
import com.jiuqi.nr.io.tz.exception.TzImportException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import org.springframework.util.CollectionUtils;

public class SbIdBufDBImportActuator
extends BufDBImportActuator {
    @Override
    public void setDataFields(List<DataField> fields) {
        for (DataField field : fields) {
            if (field.getDataFieldKind() != DataFieldKind.BUILT_IN_FIELD || !"BIZKEYORDER".equals(field.getCode()) && !"SBID".equals(field.getCode())) continue;
            super.setDataFields(fields);
            return;
        }
        throw new TzImportException("\u7f3a\u5c11\u53f0\u8d26\u4fe1\u606f\u8868\u4e1a\u52a1\u4e3b\u952e\u6307\u6807");
    }

    @Override
    protected FlagState flagData0() throws BufDBException {
        this.logger.info("SbIdBufDBImportActuator#flagData");
        boolean notice = !CollectionUtils.isEmpty(this.listeners);
        boolean containPeriodField = !this.tmpTable.getPeriodicFields().isEmpty();
        FlagState flagState = new FlagState();
        ArrayList<Integer> noVersionIndex = new ArrayList<Integer>();
        ArrayList<Integer> srcNoVersionIndex = new ArrayList<Integer>();
        LinkedHashMap<Integer, Integer> index2Type = new LinkedHashMap<Integer, Integer>();
        LinkedHashMap<Integer, Integer> srcIndex2Type = new LinkedHashMap<Integer, Integer>();
        List<DataField> timePointFields = this.tmpTable.getTimePointFields();
        for (DataField timePointField : timePointFields) {
            if (!timePointField.isGenerateVersion()) {
                noVersionIndex.add(index2Type.size());
                srcNoVersionIndex.add(srcIndex2Type.size());
            }
            index2Type.put((Integer)this.tableIndex.get(timePointField.getCode()), timePointField.getDataFieldType().getValue());
            srcIndex2Type.put((Integer)this.srcTableIndex.get(timePointField.getCode()), timePointField.getDataFieldType().getValue());
        }
        Integer idIndex = (Integer)this.tableIndex.get("_ID");
        Integer orderIndex = (Integer)this.tableIndex.get("_ORDINAL");
        Integer optIndex = (Integer)this.tableIndex.get("_OPT");
        Integer rptOptIndex = (Integer)this.tableIndex.get("_RPT_OPT");
        Integer sbIdIndex = (Integer)this.tableIndex.get("_SBID");
        DataField sbIdField = this.tmpTable.getSbId();
        Integer inputSbIdIndex = (Integer)this.tableIndex.get(sbIdField.getCode());
        Integer srcOptIndex = (Integer)this.srcTableIndex.get("_OPT");
        Integer srcSbIdIndex = (Integer)this.srcTableIndex.get("SBID");
        Integer validTimeIndex = (Integer)this.srcTableIndex.get("VALIDDATATIME");
        ArrayList<Object> row = new ArrayList<Object>();
        Object[] rowData = new Object[index2Type.size()];
        Object[] srcRowData = new Object[index2Type.size()];
        int addCount = 0;
        int recordUpdateCount = 0;
        int noRecordUpdateCount = 0;
        int rptAddCount = 0;
        int tqUpdate = 0;
        int noVerUpdate = 0;
        int verUpdate = 0;
        int eqUpdate0 = 0;
        int eqUpdate = 0;
        int noneCount = 0;
        IIndexedCursor cursor = this.table.openCursor();
        IIndexedCursor srcCursor = this.srcTable.openCursor(this.srcTable.getName() + "_INDEX_ORDER");
        while (cursor.next()) {
            String sbId;
            IRecord record = cursor.getRecord();
            row.clear();
            int id = record.getInt(idIndex.intValue());
            String inSbId = record.getString(inputSbIdIndex.intValue());
            RowDimValue rowDimValue = (RowDimValue)this.rowDim.get(id);
            row.add(rowDimValue.getMdCode());
            row.addAll(rowDimValue.getDim());
            row.add(record.getInt(orderIndex.intValue()));
            boolean locate = srcCursor.locate(row);
            if (locate) {
                IRecord srcRecord = srcCursor.getRecord();
                sbId = srcRecord.getString(srcSbIdIndex.intValue());
                if (!Objects.equals(inSbId, sbId)) {
                    record.setInt(optIndex.intValue(), 4);
                    sbId = inSbId;
                    ++addCount;
                } else {
                    this.collectColData(srcRowData, srcIndex2Type, srcRecord);
                    this.collectColData(rowData, index2Type, record);
                    if (!this.compareData(srcRowData, rowData, timePointFields)) {
                        int opt;
                        if (this.config.getDestPeriod().equals(srcRecord.getString(validTimeIndex.intValue()))) {
                            opt = 3;
                            ++tqUpdate;
                        } else if (noVersionIndex.size() == index2Type.size()) {
                            opt = 3;
                            ++noVerUpdate;
                        } else if (noVersionIndex.isEmpty()) {
                            opt = 2;
                            ++verUpdate;
                        } else {
                            for (Integer index : noVersionIndex) {
                                rowData[index.intValue()] = null;
                            }
                            for (Integer versionIndex : srcNoVersionIndex) {
                                srcRowData[versionIndex.intValue()] = null;
                            }
                            if (Arrays.equals(srcRowData, rowData)) {
                                opt = 3;
                                ++eqUpdate0;
                            } else {
                                opt = 2;
                                ++eqUpdate;
                            }
                        }
                        if (opt == 2) {
                            ++recordUpdateCount;
                        } else {
                            ++noRecordUpdateCount;
                        }
                        record.setInt(optIndex.intValue(), opt);
                        if (notice) {
                            rowDimValue.setOpt(opt);
                            rowDimValue.setValues(new ArrayList<Object>());
                            rowDimValue.setOldValues(new ArrayList<Object>());
                            rowDimValue.addOldValues(srcRowData);
                            rowDimValue.addValues(rowData);
                        }
                    } else {
                        ++noneCount;
                    }
                    srcRecord.setInt(srcOptIndex.intValue(), 2);
                    srcCursor.update();
                }
            } else {
                sbId = inSbId;
                record.setInt(optIndex.intValue(), 4);
                ++addCount;
            }
            ++rptAddCount;
            record.setInt(rptOptIndex.intValue(), 4);
            record.setString(sbIdIndex.intValue(), sbId);
            cursor.update();
            rowDimValue.setSbId(sbId);
        }
        cursor.close();
        srcCursor.close();
        flagState.setDel(this.delSbId2Tmp());
        this.logger.info("\u6bd4\u8f83\u6570\u636e {} \u6761\u6570\u636e\u53d1\u751f\u53d8\u5316\uff0c\u540c\u671f\u4fee\u6539\u4e0d\u8bb0\u5f55\u53d8\u66f4", (Object)tqUpdate);
        this.logger.info("\u6bd4\u8f83\u6570\u636e {} \u6761\u6570\u636e\u53d1\u751f\u53d8\u5316\uff0c\u6ca1\u6709\u8981\u751f\u6210\u7248\u672c\u7684\u5b57\u6bb5\u4e0d\u8bb0\u5f55\u53d8\u66f4", (Object)noVerUpdate);
        this.logger.info("\u6bd4\u8f83\u6570\u636e {} \u6761\u6570\u636e\u53d1\u751f\u53d8\u5316\uff0c\u5168\u662f\u8981\u751f\u6210\u7248\u672c\u7684\u8bb0\u5f55\u53d8\u66f4", (Object)verUpdate);
        this.logger.info("\u6bd4\u8f83\u6570\u636e {} \u6761\u6570\u636e\u53d1\u751f\u53d8\u5316\uff0c\u91cd\u8981\u5b57\u6bb5\u6570\u636e\u53d1\u751f\u53d8\u5316\u7684\u8bb0\u5f55\u53d8\u66f4", (Object)eqUpdate);
        this.logger.info("\u6bd4\u8f83\u6570\u636e {} \u6761\u6570\u636e\u53d1\u751f\u53d8\u5316\uff0c\u91cd\u8981\u5b57\u6bb5\u6570\u636e\u672a\u53d1\u751f\u53d8\u5316\u7684\u4e0d\u8bb0\u5f55\u53d8\u66f4", (Object)eqUpdate0);
        if (containPeriodField) {
            flagState.setRptAdd(rptAddCount);
            flagState.setRptDel(-1);
            flagState.setRptNone(0);
            flagState.setRptUpdate(0);
            this.logger.info("\u65e0\u9700\u6bd4\u8f83\u53f0\u8d26\u62a5\u8868\u6570\u636e\u5168\u91cf\u5220\u9664\u518d\u65b0\u589e");
        }
        flagState.setAdd(addCount);
        flagState.setRecordUpdate(recordUpdateCount);
        flagState.setNoRecordUpdate(noRecordUpdateCount);
        flagState.setNone(noneCount);
        return flagState;
    }
}

