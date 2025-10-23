/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.Violation
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.DataSchemeValidator
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 *  com.jiuqi.nvwa.subsystem.core.model.SubServerLevel
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validator
 */
package com.jiuqi.nr.datascheme.internal.validator;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.Violation;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.DataSchemeValidator;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import com.jiuqi.nvwa.subsystem.core.model.SubServerLevel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
@Primary
public class DefaultDataSchemeValidator
implements DataSchemeValidator {
    @Autowired
    private Validator validator;
    @Autowired
    private IDataSchemeDao<DesignDataSchemeDO> dataSchemeDao;
    @Autowired
    private IDataSchemeDao<DataSchemeDO> runtimeDataSchemeDao;
    @Autowired
    private IDataDimDao<DesignDataDimDO> dataDimDao;
    @Autowired
    private IParamLevelManager paramLevelManager;
    private final Map<String, BiConsumer<DesignDataScheme, DesignDataScheme>> checkDataSchemeByPropMap = new ConcurrentHashMap<String, BiConsumer<DesignDataScheme, DesignDataScheme>>();
    private final Logger logger = LoggerFactory.getLogger(DefaultDataSchemeValidator.class);

    public void checkDataScheme(DesignDataScheme dataScheme) throws SchemeDataException {
        Assert.notNull((Object)dataScheme, "dataScheme must not be null.");
        Assert.notNull((Object)dataScheme.getCode(), "code must not be null.");
        Assert.notNull((Object)dataScheme.getTitle(), "title must not be null.");
        this.checkDataScheme0(dataScheme);
    }

    private synchronized void initCheckDataSchemeByPropMap() {
        this.checkDataSchemeByPropMap.put("prefix", (dataScheme, old) -> {
            String prefix = dataScheme.getPrefix();
            this.validate((DesignDataScheme)dataScheme, "prefix");
            if (prefix != null) {
                prefix = prefix.toUpperCase();
                dataScheme.setPrefix(prefix);
                DesignDataScheme byPre = this.dataSchemeDao.getByPrefix(prefix);
                if (byPre != null && !byPre.getKey().equals(dataScheme.getKey())) {
                    throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_2.getMessage(dataScheme.getPrefix()));
                }
            }
            if (old != null && (prefix != null ? !prefix.equals(old.getPrefix()) : old.getPrefix() != null)) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_4.getMessage());
            }
        });
        this.checkDataSchemeByPropMap.put("code", (dataScheme, old) -> {
            DesignDataSchemeDO byCode;
            String code = dataScheme.getCode();
            this.validate((DesignDataScheme)dataScheme, "code");
            if (code != null && (byCode = this.dataSchemeDao.getByCode(code = code.toUpperCase())) != null && !byCode.getKey().equals(dataScheme.getKey())) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_1.getMessage(dataScheme.getCode()));
            }
            if (old != null && !old.getCode().equals(code)) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_6.getMessage());
            }
        });
        this.checkDataSchemeByPropMap.put("title", (dataScheme, old) -> {
            String title = dataScheme.getTitle();
            this.validate((DesignDataScheme)dataScheme, "title");
            if (title != null) {
                List<DesignDataSchemeDO> by1;
                dataScheme.setTitle(title);
                if (dataScheme.getDataGroupKey() == null) {
                    dataScheme.setDataGroupKey("00000000-0000-0000-0000-000000000000");
                }
                if (!CollectionUtils.isEmpty(by1 = this.dataSchemeDao.getBy(dataScheme.getTitle(), dataScheme.getDataGroupKey()))) {
                    for (DesignDataSchemeDO dataSchemeDO : by1) {
                        if (dataSchemeDO.getKey().equals(dataScheme.getKey())) continue;
                        throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_3.getMessage(dataScheme.getTitle()));
                    }
                }
            }
        });
    }

    public void checkDataScheme(DesignDataScheme dataScheme, String ... properties) throws SchemeDataException {
        if (properties == null) {
            return;
        }
        if (this.checkDataSchemeByPropMap.isEmpty()) {
            this.initCheckDataSchemeByPropMap();
        }
        DesignDataSchemeDO old = null;
        if (dataScheme.getKey() != null) {
            old = this.dataSchemeDao.get(dataScheme.getKey());
        }
        for (String property : properties) {
            this.checkDataSchemeByPropMap.get(property).accept(dataScheme, old);
        }
    }

    public void levelCheckDataScheme(String dataScheme) throws SchemeDataException {
        if (dataScheme == null) {
            return;
        }
        this.levelCheckDataScheme(this.dataSchemeDao.get(dataScheme));
    }

    public void levelCheckDataScheme(DesignDataScheme dataScheme) throws SchemeDataException {
        int value;
        SubServerLevel level;
        if (dataScheme == null) {
            return;
        }
        if (DataSchemeUtils.isSet(dataScheme.getLevel()) && this.paramLevelManager.isOpenParamLevel() && (level = this.paramLevelManager.getLevel()) != null && (value = level.getValue()) != 0 && !String.valueOf(value).equals(dataScheme.getLevel())) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_7.getMessage());
        }
    }

    public void checkSubLevelModify(DesignDataScheme dataScheme, List<DesignDataDimension> dataDimensions) {
        List<DesignDataDimDO> oldDims;
        DesignDataSchemeDO oldScheme = this.dataSchemeDao.get(dataScheme.getKey());
        ArrayList<String> res = new ArrayList<String>();
        boolean flag = true;
        if (!StringUtils.equals((String)oldScheme.getTitle(), (String)dataScheme.getTitle())) {
            flag = false;
            res.add("\u6807\u9898\u53ea\u8bfb");
        }
        if ((oldDims = this.dataDimDao.getByDataScheme(oldScheme.getKey())).size() == dataDimensions.size()) {
            for (DesignDataDimDO oldDim : oldDims) {
                boolean dimModified = dataDimensions.stream().noneMatch(x -> x.getDimKey().equals(oldDim.getDimKey()));
                if (!dimModified) continue;
                flag = false;
                res.add("\u7ef4\u5ea6\u53ea\u8bfb");
                break;
            }
        } else {
            flag = false;
            res.add("\u7ef4\u5ea6\u53ea\u8bfb");
        }
        if (!StringUtils.equals((String)oldScheme.getDataGroupKey(), (String)dataScheme.getDataGroupKey())) {
            flag = false;
            res.add("\u5206\u7ec4\u53ea\u8bfb");
        }
        if (!flag) {
            throw new SchemeDataException("\u53c2\u6570\u4e3a\u4e0b\u53d1\u53c2\u6570:" + res);
        }
    }

    public boolean isSubLevel(DesignDataScheme dataScheme) {
        int value;
        SubServerLevel level;
        if (DataSchemeUtils.isSet(dataScheme.getLevel()) && this.paramLevelManager.isOpenParamLevel() && (level = this.paramLevelManager.getLevel()) != null && (value = level.getValue()) != 0) {
            return !String.valueOf(value).equals(dataScheme.getLevel());
        }
        return false;
    }

    public <E extends DesignDataScheme> void checkDataScheme(Collection<E> dataSchemes) throws SchemeDataException {
        throw new UnsupportedOperationException("\u6682\u672a\u652f\u6301");
    }

    public List<Violation> validator(DesignDataScheme dataSchemes) {
        throw new UnsupportedOperationException("\u6682\u672a\u652f\u6301");
    }

    public Map<String, List<Violation>> validator(Collection<DesignDataScheme> dataSchemes) {
        throw new UnsupportedOperationException("\u6682\u672a\u652f\u6301");
    }

    private void checkDataScheme0(DesignDataScheme dataScheme) throws SchemeDataException {
        List<DesignDataSchemeDO> by1;
        DesignDataScheme byPre;
        DesignDataSchemeDO byCode;
        if (dataScheme.getAuto() == null) {
            dataScheme.setAuto(Boolean.valueOf(false));
        }
        if (dataScheme.getOrder() == null) {
            dataScheme.setOrder(OrderGenerator.newOrder());
        }
        this.validate((DataScheme)dataScheme);
        String prefix = dataScheme.getPrefix();
        if (prefix != null) {
            prefix = prefix.toUpperCase();
            dataScheme.setPrefix(prefix);
        }
        String code = dataScheme.getCode();
        code = code.toUpperCase();
        dataScheme.setCode(code.toUpperCase());
        DataSchemeDO old = null;
        if (dataScheme.getKey() != null) {
            old = this.dataSchemeDao.get(dataScheme.getKey());
        }
        if (old != null) {
            DataSchemeDO run;
            if (prefix != null ? !prefix.equals(old.getPrefix()) : old.getPrefix() != null) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_4.getMessage());
            }
            if (!old.getCode().equals(code)) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_6.getMessage());
            }
            if (old.getType() != dataScheme.getType()) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_9.getMessage());
            }
            dataScheme.setBizCode(old.getBizCode());
            if (!StringUtils.isEmpty((String)old.getEncryptScene()) && !old.getEncryptScene().equals(dataScheme.getEncryptScene()) && null != (run = this.runtimeDataSchemeDao.get(dataScheme.getKey()))) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_11.getMessage());
            }
        } else if (StringUtils.isEmpty((String)dataScheme.getBizCode())) {
            dataScheme.setBizCode(OrderGenerator.newOrder());
        } else {
            DesignDataSchemeDO byBizCode = this.dataSchemeDao.getByBizCode(dataScheme.getBizCode());
            if (null != byBizCode && !byBizCode.getKey().equals(dataScheme.getKey())) {
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_10.getMessage(dataScheme.getBizCode()));
            }
        }
        if ((byCode = this.dataSchemeDao.getByCode(dataScheme.getCode())) != null && !byCode.getKey().equals(dataScheme.getKey())) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_1.getMessage(dataScheme.getCode()));
        }
        if (prefix != null && (byPre = (DesignDataScheme)this.dataSchemeDao.getByPrefix(prefix)) != null && !byPre.getKey().equals(dataScheme.getKey())) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_2.getMessage(dataScheme.getPrefix()));
        }
        if (dataScheme.getDataGroupKey() == null) {
            dataScheme.setDataGroupKey("00000000-0000-0000-0000-000000000000");
        }
        if (!CollectionUtils.isEmpty(by1 = this.dataSchemeDao.getBy(dataScheme.getTitle(), dataScheme.getDataGroupKey()))) {
            for (DesignDataSchemeDO dataSchemeDO : by1) {
                if (dataSchemeDO.getKey().equals(dataScheme.getKey())) continue;
                throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_3.getMessage(dataScheme.getTitle()));
            }
        }
    }

    private void validate(DataScheme dataScheme) throws SchemeDataException {
        Set validate = this.validator.validate((Object)dataScheme, new Class[0]);
        if (validate != null && !validate.isEmpty()) {
            this.logger.info("\u6570\u636e\u9a8c\u8bc1\u4e0d\u901a\u8fc7: {}", (Object)dataScheme);
            Iterator iterator = validate.iterator();
            if (iterator.hasNext()) {
                ConstraintViolation item = (ConstraintViolation)iterator.next();
                String message = item.getMessage();
                throw new SchemeDataException(message);
            }
        }
    }

    private void validate(DesignDataScheme dataScheme, String property) throws SchemeDataException {
        Set validate = this.validator.validateProperty((Object)dataScheme, property, new Class[0]);
        if (validate != null && !validate.isEmpty()) {
            this.logger.error("\u6570\u636e\u9a8c\u8bc1\u4e0d\u901a\u8fc7: {}", (Object)dataScheme);
            String errorMessage = null;
            for (ConstraintViolation item : validate) {
                String message = item.getMessage();
                if (StringUtils.isEmpty((String)message)) continue;
                errorMessage = message;
                break;
            }
            if (!StringUtils.isEmpty(errorMessage)) {
                throw new SchemeDataException(errorMessage);
            }
        }
    }
}

