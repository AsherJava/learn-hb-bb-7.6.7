/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.quantity.config;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.quantity.bean.QuantityCategory;
import com.jiuqi.nr.quantity.bean.QuantityInfo;
import com.jiuqi.nr.quantity.bean.QuantityUnit;
import com.jiuqi.nr.quantity.dao.QuantityCategoryDao;
import com.jiuqi.nr.quantity.dao.QuantityInfoDao;
import com.jiuqi.nr.quantity.dao.QuantityUnitDao;
import java.util.Date;
import javax.sql.DataSource;

public class AmountInitExecutor
implements CustomClassExecutor {
    private static final String QI_AMOUNT_ID = "9dbbf13d-c5c7-4894-977f-e45922862ee3";
    private static final String QC_RMB_ID = "9493b4eb-6516-48a8-a878-25a63a23e63a";

    public void execute(DataSource dataSource) throws Exception {
        QuantityInfoDao quantityInfoDao = (QuantityInfoDao)SpringBeanUtils.getBean(QuantityInfoDao.class);
        QuantityCategoryDao quantityCategoryDao = (QuantityCategoryDao)SpringBeanUtils.getBean(QuantityCategoryDao.class);
        QuantityUnitDao quantityUnitDao = (QuantityUnitDao)SpringBeanUtils.getBean(QuantityUnitDao.class);
        QuantityInfo qiAmount = new QuantityInfo();
        qiAmount.setId(QI_AMOUNT_ID);
        qiAmount.setName("AMOUNT");
        qiAmount.setTitle("\u91d1\u989d");
        qiAmount.setOrder("M2EI9XHR");
        qiAmount.setModifyTime(new Date().getTime());
        quantityInfoDao.addQuantityInfo(qiAmount);
        QuantityCategory qcRMB = new QuantityCategory();
        qcRMB.setId(QC_RMB_ID);
        qcRMB.setQuantityId(QI_AMOUNT_ID);
        qcRMB.setName("RENMINBI");
        qcRMB.setTitle("\u4eba\u6c11\u5e01");
        qcRMB.setOrder("M2LO1WAO");
        qcRMB.setBase(true);
        qcRMB.setRate(1.0);
        qcRMB.setModifyTime(new Date().getTime());
        quantityCategoryDao.addQuantityCategory(qcRMB);
        QuantityUnit quYuan = new QuantityUnit();
        quYuan.setId("2987655e-f4f7-426d-992a-0969d03dcc01");
        quYuan.setCategoryId(QC_RMB_ID);
        quYuan.setQuantityId(QI_AMOUNT_ID);
        quYuan.setName("YUAN");
        quYuan.setTitle("\u5143");
        quYuan.setBase(true);
        quYuan.setRate(1.0);
        quYuan.setOrder("M2LO2FZZ");
        quYuan.setModifyTime(new Date().getTime());
        QuantityUnit quBaiYuan = new QuantityUnit();
        quBaiYuan.setId("3241a466-bbdf-4622-a4b0-e43011038bc8");
        quBaiYuan.setCategoryId(QC_RMB_ID);
        quBaiYuan.setQuantityId(QI_AMOUNT_ID);
        quBaiYuan.setName("BAIYUAN");
        quBaiYuan.setTitle("\u767e\u5143");
        quBaiYuan.setRate(100.0);
        quBaiYuan.setOrder("M2LO2S1B");
        quBaiYuan.setModifyTime(new Date().getTime());
        QuantityUnit quQianYuan = new QuantityUnit();
        quQianYuan.setId("3e738271-c01b-4c30-8c04-10645aadd244");
        quQianYuan.setCategoryId(QC_RMB_ID);
        quQianYuan.setQuantityId(QI_AMOUNT_ID);
        quQianYuan.setName("QIANYUAN");
        quQianYuan.setTitle("\u5343\u5143");
        quQianYuan.setRate(1000.0);
        quQianYuan.setOrder("M2OG2N4B");
        quQianYuan.setModifyTime(new Date().getTime());
        QuantityUnit quWanYuan = new QuantityUnit();
        quWanYuan.setId("0cfa03e0-9fe3-48bd-b135-f749785ed2b6");
        quWanYuan.setCategoryId(QC_RMB_ID);
        quWanYuan.setQuantityId(QI_AMOUNT_ID);
        quWanYuan.setName("WANYUAN");
        quWanYuan.setTitle("\u4e07\u5143");
        quWanYuan.setRate(10000.0);
        quWanYuan.setOrder("M2OGFGFO");
        quWanYuan.setModifyTime(new Date().getTime());
        QuantityUnit quBaiWanYuan = new QuantityUnit();
        quBaiWanYuan.setId("8d347193-82d4-4a22-9d57-427a27548a07");
        quBaiWanYuan.setCategoryId(QC_RMB_ID);
        quBaiWanYuan.setQuantityId(QI_AMOUNT_ID);
        quBaiWanYuan.setName("BAIWAN");
        quBaiWanYuan.setTitle("\u767e\u4e07");
        quBaiWanYuan.setRate(1000000.0);
        quBaiWanYuan.setOrder("M2OGG4ZE");
        quBaiWanYuan.setModifyTime(new Date().getTime());
        QuantityUnit quQianWanYuan = new QuantityUnit();
        quQianWanYuan.setId("b82632a4-9303-47e6-b6fe-daa5a95c1c8a");
        quQianWanYuan.setCategoryId(QC_RMB_ID);
        quQianWanYuan.setQuantityId(QI_AMOUNT_ID);
        quQianWanYuan.setName("QIANWAN");
        quQianWanYuan.setTitle("\u5343\u4e07");
        quQianWanYuan.setRate(1.0E7);
        quQianWanYuan.setOrder("M2OGGMZH");
        quQianWanYuan.setModifyTime(new Date().getTime());
        QuantityUnit quYiYuan = new QuantityUnit();
        quYiYuan.setId("348e502d-4230-45f2-bb1c-ebcc0d6f8a1f");
        quYiYuan.setCategoryId(QC_RMB_ID);
        quYiYuan.setQuantityId(QI_AMOUNT_ID);
        quYiYuan.setName("YIYUAN");
        quYiYuan.setTitle("\u4ebf\u5143");
        quYiYuan.setRate(1.0E8);
        quYiYuan.setOrder("M2OGHPO3");
        quYiYuan.setModifyTime(new Date().getTime());
        quantityUnitDao.addQuantityUnit(quYuan);
        quantityUnitDao.addQuantityUnit(quBaiYuan);
        quantityUnitDao.addQuantityUnit(quQianYuan);
        quantityUnitDao.addQuantityUnit(quWanYuan);
        quantityUnitDao.addQuantityUnit(quBaiWanYuan);
        quantityUnitDao.addQuantityUnit(quQianWanYuan);
        quantityUnitDao.addQuantityUnit(quYiYuan);
    }
}

