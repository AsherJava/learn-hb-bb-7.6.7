/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.util.SerializeUtils
 *  com.jiuqi.util.Guid
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.dafafill.common.DataFillErrorEnum;
import com.jiuqi.nr.dafafill.dao.DataFillDataDao;
import com.jiuqi.nr.dafafill.entity.DataFillData;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.FieldFormat;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.RatioType;
import com.jiuqi.nr.dafafill.service.IDataFillDataService;
import com.jiuqi.nr.definition.util.SerializeUtils;
import com.jiuqi.util.Guid;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DataFillDataServiceImpl
implements IDataFillDataService {
    private static final Logger logger = LoggerFactory.getLogger(DataFillDataServiceImpl.class);
    @Autowired
    DataFillDataDao dao;

    @Override
    public void add(DataFillData model) throws JQException {
        this.dao.add(model);
    }

    @Override
    public void modify(DataFillData model) throws JQException {
        this.dao.modify(model);
    }

    @Override
    public void delete(String definitionId) throws JQException {
        this.dao.deleteByDefinitionId(definitionId);
    }

    @Override
    public DataFillData findByDefinition(String definitionId, String language) throws JQException {
        return this.dao.findByDefinition(definitionId, language);
    }

    @Override
    public DataFillModel getModelByDefinition(String definitionId, String language) throws JQException {
        DataFillModel model = null;
        DataFillData data = this.dao.findByDefinition(definitionId, language);
        if (data != null) {
            model = this.getModelByData(data.getData());
            for (QueryField queryField : model.getQueryFields()) {
                FieldFormat fieldFormat = queryField.getShowFormat();
                if (fieldFormat == null || fieldFormat.getRatioType() != null) continue;
                if (fieldFormat.isPercent()) {
                    fieldFormat.setRatioType(RatioType.PERCENT);
                    continue;
                }
                fieldFormat.setRatioType(RatioType.NONE);
            }
        }
        return model;
    }

    @Override
    public void saveModel(String defId, String language, DataFillModel model) throws JQException {
        DataFillData data;
        if (!StringUtils.hasText(defId)) {
            return;
        }
        if (!StringUtils.hasText(language)) {
            language = this.getDefaultLang();
        }
        if ((data = this.findByDefinition(defId, language)) == null) {
            data = new DataFillData();
            data.setId(Guid.newGuid());
            data.setDefinitionId(defId);
            data.setLanguage(language);
            data.setData(this.getDataByte(model));
            this.add(data);
        } else {
            data.setData(this.getDataByte(model));
            this.modify(data);
        }
    }

    private DataFillModel getModelByData(byte[] data) throws JQException {
        DataFillModel model = null;
        try {
            model = (DataFillModel)SerializeUtils.jsonDeserialize((byte[])data, DataFillModel.class);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataFillErrorEnum.DF_ERROR_SERIALIZE);
        }
        return model;
    }

    private byte[] getDataByte(DataFillModel model) throws JQException {
        byte[] data = null;
        try {
            data = SerializeUtils.jsonSerializeToByte((Object)model);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataFillErrorEnum.DF_ERROR_SERIALIZE);
        }
        return data;
    }

    private String getDefaultLang() {
        return "zh-CN";
    }
}

