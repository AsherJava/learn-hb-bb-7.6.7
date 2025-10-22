/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.util.SerializeUtils
 */
package com.jiuqi.nr.dafafill.owner.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.dafafill.entity.DataFillData;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.owner.common.DFPrivateErrorEnum;
import com.jiuqi.nr.dafafill.owner.dao.DFDefinitionPrivateDao;
import com.jiuqi.nr.dafafill.owner.dao.DFGroupPrivateDao;
import com.jiuqi.nr.dafafill.owner.entity.DataFillDefinitionPrivate;
import com.jiuqi.nr.dafafill.owner.entity.DataFillGroupPrivate;
import com.jiuqi.nr.dafafill.owner.service.IDataFillPrivateService;
import com.jiuqi.nr.dafafill.service.IDataFillDataService;
import com.jiuqi.nr.definition.util.SerializeUtils;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataFillPrivateService
implements IDataFillPrivateService {
    private static final Logger logger = LoggerFactory.getLogger(DataFillPrivateService.class);
    @Autowired
    private DFGroupPrivateDao groupDao;
    @Autowired
    private DFDefinitionPrivateDao definitionDao;
    @Autowired
    private IDataFillDataService dataFillDataService;

    @Override
    public void addGroup(DataFillGroupPrivate data) {
        this.groupDao.add(data);
    }

    @Override
    public void deleteGroupByKey(String key) {
        this.groupDao.deleteByKey(key);
    }

    @Override
    public List<DataFillGroupPrivate> getGroupByParentAndUser(String parentKey, String user) {
        List<DataFillGroupPrivate> res = this.groupDao.getByParentKeyAndUser(parentKey, user);
        if (res == null) {
            return Collections.emptyList();
        }
        return res;
    }

    @Override
    public void modifyGroup(String key, String title) {
        this.groupDao.modify(key, title);
    }

    @Override
    public void addDefinition(DataFillDefinitionPrivate data, String model, String language) throws JQException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        DataFillModel modelObj = (DataFillModel)mapper.readValue(model, DataFillModel.class);
        this.definitionDao.add(data);
        this.dataFillDataService.saveModel(data.getKey(), language, modelObj);
    }

    @Override
    public void deleteDefinitionByKey(String key) throws JQException {
        this.definitionDao.deleteByKey(key);
        this.dataFillDataService.delete(key);
    }

    @Override
    public DataFillDefinitionPrivate getDefinitionByKey(String key) {
        return this.definitionDao.getByKey(key);
    }

    @Override
    public List<DataFillDefinitionPrivate> getAllDefinition() {
        return this.definitionDao.getAll();
    }

    @Override
    public List<DataFillDefinitionPrivate> getDefinitionByParentAndUser(String groupId, String user) {
        List<DataFillDefinitionPrivate> res = this.definitionDao.getByParentAndUser(groupId, user);
        if (res == null) {
            return Collections.emptyList();
        }
        return res;
    }

    @Override
    public void modifyModel(String key, String model, String language) throws JQException {
        if (!StringUtils.hasText(language)) {
            language = "zh-CN";
        }
        DataFillData data = this.dataFillDataService.findByDefinition(key, language);
        data.setData(this.getDataByte(this.modelStr2DataFillModel(model)));
        this.dataFillDataService.modify(data);
    }

    @Override
    public void modifyDefinition(DataFillDefinitionPrivate definition) {
        this.definitionDao.modify(definition);
    }

    private byte[] getDataByte(DataFillModel model) throws JQException {
        byte[] data;
        try {
            data = SerializeUtils.jsonSerializeToByte((Object)model);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DFPrivateErrorEnum.DFP_ERROR_SERIALIZE);
        }
        return data;
    }

    private DataFillModel modelStr2DataFillModel(String model) throws JQException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (DataFillModel)mapper.readValue(model, DataFillModel.class);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DFPrivateErrorEnum.DFP_ERROR_JSON_PROCESS);
        }
    }
}

