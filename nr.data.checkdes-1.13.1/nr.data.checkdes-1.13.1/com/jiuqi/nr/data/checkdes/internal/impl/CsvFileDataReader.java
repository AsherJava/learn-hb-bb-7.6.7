/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 */
package com.jiuqi.nr.data.checkdes.internal.impl;

import com.csvreader.CsvReader;
import com.jiuqi.nr.data.checkdes.internal.ctxt.ImpContext;
import com.jiuqi.nr.data.checkdes.internal.io.CKDExpEntity;
import com.jiuqi.nr.data.checkdes.internal.io.CsvHeaders;
import com.jiuqi.nr.data.checkdes.internal.io.FileDataReader;
import java.io.IOException;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvFileDataReader
implements FileDataReader {
    private static final Logger logger = LoggerFactory.getLogger(CsvFileDataReader.class);
    private final ImpContext ctxt;
    private final CsvReader csvReader;

    public CsvFileDataReader(CsvReader csvReader, ImpContext ctxt) {
        this.csvReader = csvReader;
        this.ctxt = ctxt;
    }

    @Override
    public void read(ImpContext context, Consumer<CKDExpEntity> consumer) throws IOException {
        this.csvReader.readHeaders();
        String[] headers = this.csvReader.getHeaders();
        while (this.csvReader.readRecord()) {
            CKDExpEntity ckdExpEntity = this.read(headers, this.csvReader, context);
            consumer.accept(ckdExpEntity);
        }
    }

    private CKDExpEntity read(String[] headers, CsvReader csvReader, ImpContext context) throws IOException {
        CKDExpEntity ckdExpEntity = new CKDExpEntity();
        ckdExpEntity.setUserId(context.getUserId());
        ckdExpEntity.setUserNickName(context.getUserNickName());
        ckdExpEntity.setUpdateTime(context.getImpDate().getTime());
        for (String header : headers) {
            String colValue = csvReader.get(header);
            if (CsvHeaders.MD_CODE.getValue().equals(header)) {
                ckdExpEntity.setMdCode(colValue);
                continue;
            }
            if (CsvHeaders.PERIOD.getValue().equals(header)) {
                ckdExpEntity.setPeriod(colValue);
                continue;
            }
            if (CsvHeaders.FORMULA_SCHEME_TITLE.getValue().equals(header)) {
                ckdExpEntity.setFormulaSchemeTitle(colValue);
                continue;
            }
            if (CsvHeaders.FORM_CODE.getValue().equals(header)) {
                ckdExpEntity.setFormCode(colValue);
                continue;
            }
            if (CsvHeaders.FORMULA_CODE.getValue().equals(header)) {
                ckdExpEntity.setFormulaCode(colValue);
                continue;
            }
            if (CsvHeaders.GLOB_ROW.getValue().equals(header)) {
                ckdExpEntity.setGlobRow(Integer.parseInt(colValue));
                continue;
            }
            if (CsvHeaders.GLOB_COL.getValue().equals(header)) {
                ckdExpEntity.setGlobCol(Integer.parseInt(colValue));
                continue;
            }
            if (CsvHeaders.DIM_STR.getValue().equals(header)) {
                ckdExpEntity.setDimStr(colValue);
                continue;
            }
            if (CsvHeaders.CONTENT.getValue().equals(header)) {
                ckdExpEntity.setDescription(colValue);
                continue;
            }
            if (CsvHeaders.USER_ID.getValue().equals(header)) {
                ckdExpEntity.setUserId(colValue);
                continue;
            }
            if (CsvHeaders.USER_NICK_NAME.getValue().equals(header)) {
                ckdExpEntity.setUserNickName(colValue);
                continue;
            }
            if (CsvHeaders.UPDATE_TIME.getValue().equals(header)) {
                try {
                    ckdExpEntity.setUpdateTime(Long.parseLong(colValue));
                }
                catch (NumberFormatException e) {
                    logger.error(e.getMessage(), e);
                }
                continue;
            }
            ckdExpEntity.addDim(CsvHeaders.getEntityIdByHeader(header), colValue);
        }
        return ckdExpEntity;
    }
}

