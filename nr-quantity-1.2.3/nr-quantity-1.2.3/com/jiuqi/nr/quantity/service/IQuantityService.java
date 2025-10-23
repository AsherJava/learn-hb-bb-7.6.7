/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.quantity.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.quantity.bean.QuantityCategory;
import com.jiuqi.nr.quantity.bean.QuantityInfo;
import com.jiuqi.nr.quantity.bean.QuantityUnit;
import com.jiuqi.nr.quantity.service.KeyCondType;
import java.util.List;

public interface IQuantityService {
    public void addQuantityInfo(QuantityInfo var1) throws JQException;

    public void modifyQuantityInfo(QuantityInfo var1, KeyCondType var2) throws JQException;

    public void deleteQuantityInfoById(String var1) throws JQException;

    public void deleteQuantityInfoByIds(List<String> var1) throws JQException;

    public QuantityInfo getQuantityInfoById(String var1) throws JQException;

    public QuantityInfo getQuantityInfoByName(String var1) throws JQException;

    public List<QuantityInfo> getAllQuantityInfo() throws JQException;

    public void addQuantityCategory(QuantityCategory var1) throws JQException;

    public void modifyQuantityCategory(QuantityCategory var1, KeyCondType var2) throws JQException;

    public void deleteQuantityCategoryById(String var1) throws JQException;

    public void deleteQuantityCategoryByIds(List<String> var1) throws JQException;

    public void deleteQuantityCategoryByQuanId(String var1) throws JQException;

    public void deleteQuantityCategoryByQuanIds(List<String> var1) throws JQException;

    public QuantityCategory getQuantityCategoryById(String var1) throws JQException;

    public QuantityCategory getQuantityCategroyByName(String var1) throws JQException;

    public List<QuantityCategory> getQuantityCategoryByQuanId(String var1) throws JQException;

    public void addQuantityUnit(QuantityUnit var1) throws JQException;

    public void modifyQuantityUnit(QuantityUnit var1, KeyCondType var2) throws JQException;

    public void deleteQuantityUnitById(String var1) throws JQException;

    public void deleteQuantityUnitByIds(List<String> var1) throws JQException;

    public void deleteQuantityUnitByCategoryId(String var1) throws JQException;

    public void deleteQuantityUnitByCategoryIds(List<String> var1) throws JQException;

    public void deleteQuantityUnitByQuantityId(String var1) throws JQException;

    public void deleteQuantityUnitByQuantityIds(List<String> var1) throws JQException;

    public QuantityUnit getQuantityUnitById(String var1) throws JQException;

    public QuantityUnit getQuantityUnitByName(String var1) throws JQException;

    public List<QuantityUnit> getQuantityUnitByCategoryId(String var1) throws JQException;

    public List<QuantityUnit> getQuantityUnitByQuantityId(String var1) throws JQException;

    public List<QuantityInfo> fuzzyQueryQuantityInfo(String var1) throws JQException;

    public List<QuantityCategory> fuzzyQueryQuantityCategory(String var1) throws JQException;

    public List<QuantityUnit> fuzzyQueryQuantityUnit(String var1) throws JQException;

    public void validateQuantityInfo(QuantityInfo var1, boolean var2) throws JQException;

    public void validateQuantityCategory(QuantityCategory var1, boolean var2) throws JQException;

    public void validateQuantityUnit(QuantityUnit var1, boolean var2) throws JQException;

    public boolean hasBase4QuantityCategory(String var1) throws JQException;

    public boolean hasBase4QuantityUnit(String var1) throws JQException;
}

