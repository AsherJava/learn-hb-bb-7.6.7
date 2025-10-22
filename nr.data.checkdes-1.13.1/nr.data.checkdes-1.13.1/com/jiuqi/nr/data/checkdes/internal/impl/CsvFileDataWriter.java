/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 */
package com.jiuqi.nr.data.checkdes.internal.impl;

import com.csvreader.CsvWriter;
import com.jiuqi.nr.data.checkdes.internal.ctxt.ExpContext;
import com.jiuqi.nr.data.checkdes.internal.io.CKDExpEntity;
import com.jiuqi.nr.data.checkdes.internal.io.CsvHeaders;
import com.jiuqi.nr.data.checkdes.internal.io.FileDataWriter;
import com.jiuqi.nr.data.checkdes.obj.CKDExpPar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class CsvFileDataWriter
implements FileDataWriter {
    private final ExpContext ctxt;
    private final CsvWriter csvWriter;
    private final List<CKDExpEntity> data = new ArrayList<CKDExpEntity>();

    public CsvFileDataWriter(CsvWriter csvWriter, ExpContext ctxt) {
        this.csvWriter = csvWriter;
        this.ctxt = ctxt;
    }

    @Override
    public void accept(CKDExpEntity ckdExpEntity) {
        this.data.add(ckdExpEntity);
    }

    @Override
    public void write() throws IOException {
        CKDExpPar ckdExpPar = this.ctxt.getCkdExpPar();
        List<String> allDimEntityIds = this.ctxt.getCommonUtil().getHelper().getSchemeDimEntityIds(this.ctxt.getFormSchemeDefine());
        boolean expUserTime = ckdExpPar.isExpUserTime();
        this.writeHeaders(this.csvWriter, allDimEntityIds, expUserTime);
        for (CKDExpEntity datum : this.data) {
            ArrayList<String> rowData = new ArrayList<String>();
            rowData.add(datum.getMdCode());
            rowData.add(datum.getPeriod());
            rowData.add(datum.getFormulaSchemeTitle());
            rowData.add(datum.getFormCode());
            rowData.add(datum.getFormulaCode());
            rowData.add(String.valueOf(datum.getGlobRow()));
            rowData.add(String.valueOf(datum.getGlobCol()));
            rowData.add(datum.getDimStr());
            rowData.add(datum.getDescription());
            if (expUserTime) {
                rowData.add(datum.getUserId());
                rowData.add(datum.getUserNickName());
                rowData.add(String.valueOf(datum.getUpdateTime()));
            }
            for (String entityId : allDimEntityIds) {
                String dimValue = datum.getDims().get(entityId);
                rowData.add(dimValue);
            }
            this.csvWriter.writeRecord(rowData.toArray(new String[0]));
        }
    }

    private void writeHeaders(CsvWriter csvWriter, List<String> dimEntityIds, boolean expUserTime) throws IOException {
        ArrayList<String> headers = new ArrayList<String>();
        headers.add(CsvHeaders.MD_CODE.getValue());
        headers.add(CsvHeaders.PERIOD.getValue());
        headers.add(CsvHeaders.FORMULA_SCHEME_TITLE.getValue());
        headers.add(CsvHeaders.FORM_CODE.getValue());
        headers.add(CsvHeaders.FORMULA_CODE.getValue());
        headers.add(CsvHeaders.GLOB_ROW.getValue());
        headers.add(CsvHeaders.GLOB_COL.getValue());
        headers.add(CsvHeaders.DIM_STR.getValue());
        headers.add(CsvHeaders.CONTENT.getValue());
        if (expUserTime) {
            headers.add(CsvHeaders.USER_ID.getValue());
            headers.add(CsvHeaders.USER_NICK_NAME.getValue());
            headers.add(CsvHeaders.UPDATE_TIME.getValue());
        }
        if (!CollectionUtils.isEmpty(dimEntityIds)) {
            dimEntityIds.forEach(o -> headers.add(CsvHeaders.getEntityHeader(o)));
        }
        csvWriter.writeRecord(headers.toArray(new String[0]), false);
    }
}

