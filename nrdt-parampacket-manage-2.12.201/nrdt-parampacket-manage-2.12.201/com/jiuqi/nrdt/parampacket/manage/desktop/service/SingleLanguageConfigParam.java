/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.nr.datascheme.i18n.dao.DataSchemeI18nDao
 *  com.jiuqi.nr.datascheme.i18n.entity.DataSchemeI18nDO
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.definition.paramlanguage.dao.impl.DesParamLanguageDaoImpl
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 *  com.jiuqi.nr.param.transfer.definition.check.CheckImportService
 *  com.jiuqi.nr.param.transfer.definition.spi.TaskTransfer
 */
package com.jiuqi.nrdt.parampacket.manage.desktop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.nr.datascheme.i18n.dao.DataSchemeI18nDao;
import com.jiuqi.nr.datascheme.i18n.entity.DataSchemeI18nDO;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.dao.impl.DesParamLanguageDaoImpl;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.param.transfer.definition.check.CheckImportService;
import com.jiuqi.nr.param.transfer.definition.spi.TaskTransfer;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service(value="singleLanguageConfigParam")
public class SingleLanguageConfigParam
implements TaskTransfer {
    private static final Logger logger = LoggerFactory.getLogger(SingleLanguageConfigParam.class);
    @Autowired
    private DataSchemeI18nDao dataSchemeI18nDao;
    @Autowired
    private DesParamLanguageDaoImpl desParamLanguageDao;
    @Autowired(required=false)
    private CheckImportService checkImportService;
    public static final String SINGLE_CONFIG_ID = "cfa0ed55-9259-4c0d-a6db-fcb76b2fc220";
    ObjectMapper mapper = new ObjectMapper();

    public String getId() {
        return SINGLE_CONFIG_ID;
    }

    public byte[] exportTaskData(IExportContext iExportContext, String taskKey) throws TransferException {
        List dataSchemeI18nDOs = this.dataSchemeI18nDao.list(this.dataSchemeI18nDao.getClz());
        List desParamLanguages = this.desParamLanguageDao.list(DesParamLanguage.class);
        Object[] obj = new Object[]{dataSchemeI18nDOs, desParamLanguages};
        byte[] bytes = null;
        try {
            bytes = this.mapper.writeValueAsBytes((Object)obj);
            logger.info("\u5bfc\u51fa\u5efa\u6a21\u591a\u8bed\u8a00\u914d\u7f6e\u5b8c\u6210-\u5355\u673a\u7248\u4f7f\u7528");
        }
        catch (JsonProcessingException e) {
            logger.error("\u5bfc\u51fa\u5efa\u6a21\u591a\u8bed\u8a00\u914d\u7f6e\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
        return bytes;
    }

    public void importTaskData(IImportContext context, String taskKey, byte[] data) throws TransferException {
        try {
            int i;
            Object[] arr;
            if (null != this.checkImportService && !this.checkImportService.checkImportLanguage()) {
                logger.info("\u5916\u90e8\u63a5\u53e3\u5224\u65ad\u7981\u6b62\u5bfc\u5165\u591a\u8bed\u8a00\u53c2\u6570:" + this.checkImportService.getClass());
                return;
            }
            Object[] configurations = (Object[])this.mapper.readValue(data, Object[].class);
            if (configurations[0] != null) {
                List dataSchemeI18nDOs = (List)configurations[0];
                arr = new DataSchemeI18nDO[dataSchemeI18nDOs.size()];
                for (i = 0; i < dataSchemeI18nDOs.size(); ++i) {
                    arr[i] = SingleLanguageConfigParam.map2Obj((Map)dataSchemeI18nDOs.get(i), DataSchemeI18nDO.class);
                }
                this.dataSchemeI18nDao.deleteAll();
                this.dataSchemeI18nDao.insert(arr);
            }
            if (configurations[1] != null) {
                List desParamLanguages = (List)configurations[1];
                arr = new DesParamLanguage[desParamLanguages.size()];
                for (i = 0; i < desParamLanguages.size(); ++i) {
                    arr[i] = SingleLanguageConfigParam.map2Obj((Map)desParamLanguages.get(i), DesParamLanguage.class);
                }
                this.desParamLanguageDao.deleteAll();
                this.desParamLanguageDao.insert(arr);
            }
            logger.info("\u5bfc\u5165\u5efa\u6a21\u591a\u8bed\u8a00\u914d\u7f6e\u5b8c\u6210-\u5355\u673a\u7248\u4f7f\u7528");
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165\u5efa\u6a21\u591a\u8bed\u8a00\u914d\u7f6e\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
    }

    private static <T> T map2Obj(Map source, Class<T> target) throws IllegalAccessException, InstantiationException {
        Field[] declaredFields = target.getDeclaredFields();
        T t = target.newInstance();
        for (Field declaredField : declaredFields) {
            Object value = source.get(declaredField.getName());
            if (value == null) continue;
            ReflectionUtils.makeAccessible(declaredField);
            if ("resourceType".equals(declaredField.getName())) {
                value = LanguageResourceType.valueOf((String)value.toString());
            }
            declaredField.set(t, value);
        }
        return t;
    }
}

