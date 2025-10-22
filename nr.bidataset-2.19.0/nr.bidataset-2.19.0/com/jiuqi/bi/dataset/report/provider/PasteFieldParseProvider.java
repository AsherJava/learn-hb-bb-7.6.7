/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.bi.dataset.report.provider;

import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.model.ReportExpField;
import com.jiuqi.bi.dataset.report.provider.ExpParseProvider;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ExpFieldVo;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ExpParsedFieldVo;
import com.jiuqi.bi.dataset.report.remote.controller.vo.PasteParsedFieldVo;
import com.jiuqi.np.common.exception.JQException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasteFieldParseProvider {
    @Autowired
    private ExpParseProvider expParseProvider;

    public List<PasteParsedFieldVo> parse(String taskKey, List<ReportExpField> fields, List<ReportDsParameter> params) throws JQException {
        ArrayList<PasteParsedFieldVo> parsedFields = new ArrayList<PasteParsedFieldVo>();
        ArrayList<ExpFieldVo> expFields = new ArrayList<ExpFieldVo>();
        fields.forEach(field -> {
            ExpFieldVo expField = new ExpFieldVo();
            expField.setExpresion(field.getExp());
            expFields.add(expField);
        });
        List<ExpParsedFieldVo> parsedFieldVos = this.expParseProvider.doParse(taskKey, expFields, params);
        for (int i = 0; i < parsedFieldVos.size(); ++i) {
            ExpParsedFieldVo parsedField = parsedFieldVos.get(i);
            PasteParsedFieldVo pasteField = new PasteParsedFieldVo(fields.get(i));
            pasteField.setFieldType(parsedField.getFieldType());
            pasteField.setDataType(parsedField.getDatatype());
            pasteField.setErrorExpMsg(parsedField.getErrorMsg());
            FieldType fieldType = pasteField.getFieldType();
            if (fieldType != null && (fieldType.equals((Object)FieldType.TIME_DIM) || fieldType.equals((Object)FieldType.GENERAL_DIM))) {
                pasteField.setKeyField(pasteField.getCode());
            }
            parsedFields.add(pasteField);
        }
        return parsedFields;
    }
}

