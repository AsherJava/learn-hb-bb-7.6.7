/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.quantity.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.quantity.bean.QuantityCategory;
import com.jiuqi.nr.quantity.bean.QuantityInfo;
import com.jiuqi.nr.quantity.bean.QuantityUnit;
import com.jiuqi.nr.quantity.dao.QuantityCategoryDao;
import com.jiuqi.nr.quantity.dao.QuantityInfoDao;
import com.jiuqi.nr.quantity.dao.QuantityUnitDao;
import com.jiuqi.nr.quantity.service.IQuantityService;
import com.jiuqi.nr.quantity.service.KeyCondType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuantityServiceImpl
implements IQuantityService {
    @Autowired
    private QuantityInfoDao quantityInfoDao;
    @Autowired
    private QuantityCategoryDao quantityCategoryDao;
    @Autowired
    private QuantityUnitDao quantityUnitDao;

    @Override
    public void addQuantityInfo(QuantityInfo quantityInfo) throws JQException {
        this.quantityInfoDao.addQuantityInfo(quantityInfo);
    }

    @Override
    public void modifyQuantityInfo(QuantityInfo quantityInfo, KeyCondType keyType) throws JQException {
        if (keyType.equals((Object)KeyCondType.ID)) {
            this.quantityInfoDao.updateQuantityInfo(quantityInfo);
        } else if (keyType.equals((Object)KeyCondType.NAME)) {
            this.quantityInfoDao.updateQuantityInfoByName(quantityInfo);
        }
    }

    @Override
    public void deleteQuantityInfoById(String id) throws JQException {
        this.quantityInfoDao.deleteQuantityInfoById(id);
        this.quantityCategoryDao.deleteQuantityCategoryByQuanId(id);
        this.quantityUnitDao.deleteQuantityUnitByQuanId(id);
    }

    @Override
    public void deleteQuantityInfoByIds(List<String> ids) throws JQException {
        for (String id : ids) {
            this.deleteQuantityInfoById(id);
        }
    }

    @Override
    public QuantityInfo getQuantityInfoById(String id) throws JQException {
        return this.quantityInfoDao.getQuantityInfoById(id);
    }

    @Override
    public QuantityInfo getQuantityInfoByName(String name) throws JQException {
        return this.quantityInfoDao.getQuantityInfoByName(name);
    }

    @Override
    public List<QuantityInfo> getAllQuantityInfo() throws JQException {
        return this.quantityInfoDao.getAllQuantityInfo();
    }

    @Override
    public void addQuantityCategory(QuantityCategory quantityCategory) throws JQException {
        this.quantityCategoryDao.addQuantityCategory(quantityCategory);
        this.quantityInfoDao.updateQuantityInfoModifyTime(quantityCategory.getQuantityId(), quantityCategory.getModifyTime());
    }

    @Override
    public void modifyQuantityCategory(QuantityCategory quantityCategory, KeyCondType keyType) throws JQException {
        if (keyType.equals((Object)KeyCondType.ID)) {
            this.quantityCategoryDao.updateQuantityCategory(quantityCategory);
        } else if (keyType.equals((Object)KeyCondType.NAME)) {
            this.quantityCategoryDao.updateQuantityCategoryByName(quantityCategory);
        }
        this.quantityInfoDao.updateQuantityInfoModifyTime(quantityCategory.getQuantityId(), quantityCategory.getModifyTime());
    }

    @Override
    public void deleteQuantityCategoryById(String id) throws JQException {
        QuantityCategory quantityCategory = this.quantityCategoryDao.getQuantityCategoryById(id);
        this.quantityCategoryDao.deleteQuantityCategoryById(id);
        this.quantityUnitDao.deleteQuantityUnitByCategoryId(id);
        long modifyTime = System.currentTimeMillis();
        this.quantityInfoDao.updateQuantityInfoModifyTime(quantityCategory.getQuantityId(), modifyTime);
    }

    @Override
    public void deleteQuantityCategoryByIds(List<String> ids) throws JQException {
        for (String id : ids) {
            this.deleteQuantityCategoryById(id);
        }
    }

    @Override
    public void deleteQuantityCategoryByQuanId(String quantityId) throws JQException {
        this.quantityCategoryDao.deleteQuantityCategoryByQuanId(quantityId);
        long modifyTime = System.currentTimeMillis();
        this.quantityInfoDao.updateQuantityInfoModifyTime(quantityId, modifyTime);
    }

    @Override
    public void deleteQuantityCategoryByQuanIds(List<String> quantityIds) throws JQException {
        for (String qid : quantityIds) {
            this.quantityCategoryDao.deleteQuantityCategoryByQuanId(qid);
        }
    }

    @Override
    public QuantityCategory getQuantityCategoryById(String id) throws JQException {
        return this.quantityCategoryDao.getQuantityCategoryById(id);
    }

    @Override
    public QuantityCategory getQuantityCategroyByName(String name) throws JQException {
        return this.quantityCategoryDao.getQuantityCategoryByName(name);
    }

    @Override
    public List<QuantityCategory> getQuantityCategoryByQuanId(String quantityId) throws JQException {
        return this.quantityCategoryDao.getQuantityCategoryByQuanId(quantityId);
    }

    @Override
    public void addQuantityUnit(QuantityUnit quantityUnit) throws JQException {
        this.quantityUnitDao.addQuantityUnit(quantityUnit);
        this.quantityInfoDao.updateQuantityInfoModifyTime(quantityUnit.getQuantityId(), quantityUnit.getModifyTime());
    }

    @Override
    public void modifyQuantityUnit(QuantityUnit quantityUnit, KeyCondType keyType) throws JQException {
        if (keyType.equals((Object)KeyCondType.ID)) {
            this.quantityUnitDao.updateQuantityUnit(quantityUnit);
        } else if (keyType.equals((Object)KeyCondType.NAME)) {
            this.quantityUnitDao.updateQuantityUnitByName(quantityUnit);
        }
        this.quantityInfoDao.updateQuantityInfoModifyTime(quantityUnit.getQuantityId(), quantityUnit.getModifyTime());
    }

    @Override
    public void deleteQuantityUnitById(String id) throws JQException {
        QuantityUnit quantityUnit = this.quantityUnitDao.getQuantityUnitById(id);
        this.quantityUnitDao.deleteQuantityUnitById(id);
        long modifyTime = System.currentTimeMillis();
        this.quantityInfoDao.updateQuantityInfoModifyTime(quantityUnit.getQuantityId(), modifyTime);
    }

    @Override
    public void deleteQuantityUnitByIds(List<String> ids) throws JQException {
        for (String id : ids) {
            this.deleteQuantityUnitById(id);
        }
    }

    @Override
    public void deleteQuantityUnitByCategoryId(String categoryId) throws JQException {
        QuantityCategory quantityCategory = this.getQuantityCategoryById(categoryId);
        this.quantityUnitDao.deleteQuantityUnitByCategoryId(categoryId);
        long modifyTime = System.currentTimeMillis();
        this.quantityInfoDao.updateQuantityInfoModifyTime(quantityCategory.getQuantityId(), modifyTime);
    }

    @Override
    public void deleteQuantityUnitByCategoryIds(List<String> categoryIds) throws JQException {
        for (String cid : categoryIds) {
            this.quantityUnitDao.deleteQuantityUnitByCategoryId(cid);
        }
    }

    @Override
    public void deleteQuantityUnitByQuantityId(String quantityId) throws JQException {
        this.quantityUnitDao.deleteQuantityUnitByQuanId(quantityId);
        long modifyTime = System.currentTimeMillis();
        this.quantityInfoDao.updateQuantityInfoModifyTime(quantityId, modifyTime);
    }

    @Override
    public void deleteQuantityUnitByQuantityIds(List<String> quantityIds) throws JQException {
        for (String qid : quantityIds) {
            this.quantityUnitDao.deleteQuantityUnitByQuanId(qid);
        }
    }

    @Override
    public QuantityUnit getQuantityUnitById(String id) throws JQException {
        return this.quantityUnitDao.getQuantityUnitById(id);
    }

    @Override
    public QuantityUnit getQuantityUnitByName(String name) throws JQException {
        return this.quantityUnitDao.getQuantityUnitByName(name);
    }

    @Override
    public List<QuantityUnit> getQuantityUnitByCategoryId(String categoryId) throws JQException {
        return this.quantityUnitDao.getQuantityUnitByCategoryId(categoryId);
    }

    @Override
    public List<QuantityUnit> getQuantityUnitByQuantityId(String quantityId) throws JQException {
        return this.quantityUnitDao.getQuantityUnitByQuantityId(quantityId);
    }

    @Override
    public List<QuantityInfo> fuzzyQueryQuantityInfo(String keywords) throws JQException {
        return this.quantityInfoDao.fuzzyQueryQuantityInfo(keywords);
    }

    @Override
    public List<QuantityCategory> fuzzyQueryQuantityCategory(String keywords) throws JQException {
        return this.quantityCategoryDao.fuzzyQueryQuantityCategory(keywords);
    }

    @Override
    public List<QuantityUnit> fuzzyQueryQuantityUnit(String keywords) throws JQException {
        return this.quantityUnitDao.fuzzyQueryQuantityUnit(keywords);
    }

    @Override
    public void validateQuantityInfo(QuantityInfo quantityInfo, boolean isNew) throws JQException {
        if (isNew && this.quantityInfoDao.validateName(null, quantityInfo.getName()) > 0) {
            throw new JQException(new ErrorEnum(){

                public String getCode() {
                    return "100";
                }

                public String getMessage() {
                    return "\u91cf\u7eb2\u6807\u8bc6\u4e0d\u80fd\u91cd\u590d\uff01";
                }
            });
        }
        if (this.quantityInfoDao.validateTitle(isNew ? null : quantityInfo.getId(), quantityInfo.getTitle()) > 0) {
            throw new JQException(new ErrorEnum(){

                public String getCode() {
                    return "101";
                }

                public String getMessage() {
                    return "\u91cf\u7eb2\u6807\u9898\u4e0d\u80fd\u91cd\u590d\uff01";
                }
            });
        }
    }

    @Override
    public void validateQuantityCategory(QuantityCategory quantityCategory, boolean isNew) throws JQException {
        if (isNew && this.quantityCategoryDao.validateName(null, quantityCategory.getName()) > 0) {
            throw new JQException(new ErrorEnum(){

                public String getCode() {
                    return "200";
                }

                public String getMessage() {
                    return "\u91cf\u7eb2\u5206\u7c7b\u6807\u8bc6\u4e0d\u80fd\u91cd\u590d\uff01";
                }
            });
        }
        if (this.quantityCategoryDao.validateTitle(isNew ? null : quantityCategory.getId(), quantityCategory.getQuantityId(), quantityCategory.getTitle()) > 0) {
            throw new JQException(new ErrorEnum(){

                public String getCode() {
                    return "201";
                }

                public String getMessage() {
                    return "\u540c\u4e00\u91cf\u7eb2\u4e0b\u91cf\u7eb2\u5206\u7c7b\u6807\u9898\u4e0d\u80fd\u91cd\u590d\uff01";
                }
            });
        }
        if (this.quantityCategoryDao.validateBase(isNew ? null : quantityCategory.getId(), quantityCategory.getQuantityId()) > 0 && quantityCategory.isBase()) {
            throw new JQException(new ErrorEnum(){

                public String getCode() {
                    return "202";
                }

                public String getMessage() {
                    return "\u540c\u4e00\u91cf\u7eb2\u4e0b\u53ea\u80fd\u6709\u4e00\u4e2a\u57fa\u51c6\u5206\u7c7b\uff01";
                }
            });
        }
    }

    @Override
    public void validateQuantityUnit(QuantityUnit quantityUnit, boolean isNew) throws JQException {
        if (isNew && this.quantityUnitDao.validateName(null, quantityUnit.getName()) > 0) {
            throw new JQException(new ErrorEnum(){

                public String getCode() {
                    return "300";
                }

                public String getMessage() {
                    return "\u91cf\u7eb2\u5355\u4f4d\u6807\u8bc6\u4e0d\u80fd\u91cd\u590d\uff01";
                }
            });
        }
        if (this.quantityUnitDao.validateTitle(isNew ? null : quantityUnit.getId(), quantityUnit.getCategoryId(), quantityUnit.getTitle()) > 0) {
            throw new JQException(new ErrorEnum(){

                public String getCode() {
                    return "301";
                }

                public String getMessage() {
                    return "\u540c\u4e00\u91cf\u7eb2\u5206\u7c7b\u4e0b\u91cf\u7eb2\u5355\u4f4d\u6807\u9898\u4e0d\u80fd\u91cd\u590d\uff01";
                }
            });
        }
        if (this.quantityUnitDao.validateBase(isNew ? null : quantityUnit.getId(), quantityUnit.getCategoryId()) > 0 && quantityUnit.isBase()) {
            throw new JQException(new ErrorEnum(){

                public String getCode() {
                    return "302";
                }

                public String getMessage() {
                    return "\u540c\u4e00\u91cf\u7eb2\u5206\u7c7b\u4e0b\u53ea\u80fd\u6709\u4e00\u4e2a\u57fa\u51c6\u5355\u4f4d\uff01";
                }
            });
        }
    }

    @Override
    public boolean hasBase4QuantityCategory(String quantityId) throws JQException {
        return this.quantityCategoryDao.validateBase(null, quantityId) > 0;
    }

    @Override
    public boolean hasBase4QuantityUnit(String categoryId) throws JQException {
        return this.quantityUnitDao.validateBase(null, categoryId) > 0;
    }
}

