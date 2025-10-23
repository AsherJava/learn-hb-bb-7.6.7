/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.quantity.bean.QuantityCategory
 *  com.jiuqi.nr.quantity.bean.QuantityInfo
 *  com.jiuqi.nr.quantity.bean.QuantityUnit
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.query.ResourceData
 *  com.jiuqi.nvwa.resourceview.query.ResourceGroup
 */
package com.jiuqi.nr.resourceview.quantity.util;

import com.jiuqi.nr.quantity.bean.QuantityCategory;
import com.jiuqi.nr.quantity.bean.QuantityInfo;
import com.jiuqi.nr.quantity.bean.QuantityUnit;
import com.jiuqi.nr.resourceview.quantity.bean.QuantityCategoryDTO;
import com.jiuqi.nr.resourceview.quantity.bean.QuantityInfoDTO;
import com.jiuqi.nr.resourceview.quantity.bean.QuantityUnitDTO;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.query.ResourceData;
import com.jiuqi.nvwa.resourceview.query.ResourceGroup;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.util.StringUtils;

public final class QuantityConvert {
    public static QuantityInfoDTO DO2DTO(QuantityInfo qi) {
        if (qi == null) {
            return null;
        }
        QuantityInfoDTO qiDTO = new QuantityInfoDTO();
        qiDTO.setId(QuantityConvert.getFakeId("QI", qi.getId()));
        qiDTO.setName(qi.getName());
        qiDTO.setTitle(qi.getTitle());
        qiDTO.setOrder(qi.getOrder());
        if (qi.getModifyTime() > 0L) {
            qiDTO.setModifyTime(new Date(qi.getModifyTime()));
        }
        qiDTO.setGroup(null);
        qiDTO.setIcon(null);
        qiDTO.setResourceType(null);
        qiDTO.setSubResourceType(null);
        qiDTO.setResourceTypeTitle(null);
        qiDTO.setSubResourceTypeTitle(null);
        return qiDTO;
    }

    public static QuantityCategoryDTO DO2DTO(QuantityCategory qc) {
        if (qc == null) {
            return null;
        }
        QuantityCategoryDTO qcDTO = new QuantityCategoryDTO();
        qcDTO.setId(QuantityConvert.getFakeId("QC", qc.getId()));
        qcDTO.setName(qc.getName());
        qcDTO.setTitle(qc.getTitle());
        qcDTO.setOrder(qc.getOrder());
        if (qc.getModifyTime() > 0L) {
            qcDTO.setModifyTime(new Date(qc.getModifyTime()));
        }
        String qiId = QuantityConvert.getFakeId("QI", qc.getQuantityId());
        qcDTO.setGroup(qiId);
        qcDTO.setQuantityId(qiId);
        qcDTO.setBase(qc.isBase());
        qcDTO.setRate(qc.getRate());
        qcDTO.setIcon(null);
        qcDTO.setResourceType(null);
        qcDTO.setSubResourceType(null);
        qcDTO.setResourceTypeTitle(null);
        qcDTO.setSubResourceTypeTitle(null);
        return qcDTO;
    }

    public static QuantityUnitDTO DO2DTO(QuantityUnit qu) {
        if (qu == null) {
            return null;
        }
        QuantityUnitDTO quDTO = new QuantityUnitDTO();
        quDTO.setId(QuantityConvert.getFakeId("QU", qu.getId()));
        quDTO.setName(qu.getName());
        quDTO.setTitle(qu.getTitle());
        quDTO.setOrder(qu.getOrder());
        if (qu.getModifyTime() > 0L) {
            quDTO.setModifyTime(new Date(qu.getModifyTime()));
        }
        String qcId = QuantityConvert.getFakeId("QC", qu.getCategoryId());
        quDTO.setGroup(qcId);
        quDTO.setCategoryId(qcId);
        String qiId = QuantityConvert.getFakeId("QI", qu.getQuantityId());
        quDTO.setQuantityId(qiId);
        quDTO.setBase(qu.isBase());
        quDTO.setRate(qu.getRate());
        quDTO.setIcon(null);
        quDTO.setResourceType(null);
        quDTO.setSubResourceType(null);
        quDTO.setResourceTypeTitle(null);
        quDTO.setSubResourceTypeTitle(null);
        return quDTO;
    }

    public static QuantityInfo DTO2DO(QuantityInfoDTO qiDTO) {
        if (qiDTO == null) {
            return null;
        }
        QuantityInfo qi = new QuantityInfo();
        qi.setId(QuantityConvert.getRealId(qiDTO.getId()));
        qi.setName(qiDTO.getName());
        qi.setTitle(qiDTO.getTitle());
        qi.setOrder(qiDTO.getOrder());
        if (qiDTO.getModifyTime() != null) {
            qi.setModifyTime(qiDTO.getModifyTime().getTime());
        }
        return qi;
    }

    public static QuantityCategory DTO2DO(QuantityCategoryDTO qcDTO) {
        String quantityId;
        if (qcDTO == null) {
            return null;
        }
        QuantityCategory qc = new QuantityCategory();
        qc.setId(QuantityConvert.getRealId(qcDTO.getId()));
        qc.setName(qcDTO.getName());
        qc.setTitle(qcDTO.getTitle());
        qc.setOrder(qcDTO.getOrder());
        if (qcDTO.getModifyTime() != null) {
            qc.setModifyTime(qcDTO.getModifyTime().getTime());
        }
        if (!StringUtils.hasLength(quantityId = qcDTO.getQuantityId())) {
            quantityId = qcDTO.getGroup();
        }
        qc.setQuantityId(QuantityConvert.getRealId(quantityId));
        qc.setBase(qcDTO.isBase());
        qc.setRate(qcDTO.getRate());
        return qc;
    }

    public static QuantityUnit DTO2DO(QuantityUnitDTO quDTO) {
        String categoryId;
        if (quDTO == null) {
            return null;
        }
        QuantityUnit qu = new QuantityUnit();
        qu.setId(QuantityConvert.getRealId(quDTO.getId()));
        qu.setName(quDTO.getName());
        qu.setTitle(quDTO.getTitle());
        qu.setOrder(quDTO.getOrder());
        if (quDTO.getModifyTime() != null) {
            qu.setModifyTime(quDTO.getModifyTime().getTime());
        }
        if (!StringUtils.hasLength(categoryId = quDTO.getCategoryId())) {
            categoryId = quDTO.getGroup();
        }
        qu.setCategoryId(QuantityConvert.getRealId(categoryId));
        qu.setQuantityId(QuantityConvert.getRealId(quDTO.getQuantityId()));
        qu.setBase(quDTO.isBase());
        qu.setRate(quDTO.getRate());
        return qu;
    }

    public static ResourceData DTO2RD(QuantityInfoDTO qiDTO) {
        if (qiDTO == null) {
            return null;
        }
        ResourceData rd = new ResourceData();
        rd.setId(qiDTO.getId());
        rd.setName(qiDTO.getName());
        rd.setTitle(qiDTO.getTitle());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (qiDTO.getModifyTime() != null) {
            rd.setModifyTime(dateFormat.format(qiDTO.getModifyTime()));
        }
        rd.setType(NodeType.NODE_DATA);
        rd.setGroup(qiDTO.getGroup());
        rd.setIcon(qiDTO.getIcon());
        rd.setResourceType(qiDTO.getResourceType());
        rd.setResourceTypeTitle(qiDTO.getResourceTypeTitle());
        rd.setSubResourceType(qiDTO.getSubResourceType());
        rd.setSubResourceTypeTitle(qiDTO.getSubResourceTypeTitle());
        rd.getCustomValues().put("qi_order", qiDTO.getOrder());
        return rd;
    }

    public static ResourceData DTO2RD(QuantityCategoryDTO qcDTO) {
        if (qcDTO == null) {
            return null;
        }
        ResourceData rd = new ResourceData();
        rd.setId(qcDTO.getId());
        rd.setName(qcDTO.getName());
        rd.setTitle(qcDTO.getTitle());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (qcDTO.getModifyTime() != null) {
            rd.setModifyTime(dateFormat.format(qcDTO.getModifyTime()));
        }
        rd.setType(NodeType.NODE_DATA);
        String group = qcDTO.getGroup();
        if (!StringUtils.hasLength(group)) {
            group = qcDTO.getQuantityId();
        }
        rd.setGroup(group);
        rd.setIcon(qcDTO.getIcon());
        rd.setResourceType(qcDTO.getResourceType());
        rd.setResourceTypeTitle(qcDTO.getResourceTypeTitle());
        rd.setSubResourceType(qcDTO.getSubResourceType());
        rd.setSubResourceTypeTitle(qcDTO.getSubResourceTypeTitle());
        rd.getCustomValues().put("qi_id", qcDTO.getQuantityId());
        rd.getCustomValues().put("qc_order", qcDTO.getOrder());
        rd.getCustomValues().put("qc_base", qcDTO.isBase() ? "\u662f" : "\u5426");
        rd.getCustomValues().put("qc_rate", qcDTO.getRate() + "");
        return rd;
    }

    public static ResourceData DTO2RD(QuantityUnitDTO quDTO) {
        if (quDTO == null) {
            return null;
        }
        ResourceData rd = new ResourceData();
        rd.setId(quDTO.getId());
        rd.setName(quDTO.getName());
        rd.setTitle(quDTO.getTitle());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (quDTO.getModifyTime() != null) {
            rd.setModifyTime(dateFormat.format(quDTO.getModifyTime()));
        }
        rd.setType(NodeType.NODE_DATA);
        String group = quDTO.getGroup();
        if (!StringUtils.hasLength(group)) {
            group = quDTO.getCategoryId();
        }
        rd.setGroup(group);
        rd.setIcon(quDTO.getIcon());
        rd.setResourceType(quDTO.getResourceType());
        rd.setResourceTypeTitle(quDTO.getResourceTypeTitle());
        rd.setSubResourceType(quDTO.getSubResourceType());
        rd.setSubResourceTypeTitle(quDTO.getSubResourceTypeTitle());
        rd.getCustomValues().put("qi_id", quDTO.getQuantityId());
        rd.getCustomValues().put("qc_id", quDTO.getCategoryId());
        rd.getCustomValues().put("qu_order", quDTO.getOrder());
        rd.getCustomValues().put("qu_base", quDTO.isBase() ? "\u662f" : "\u5426");
        BigDecimal bigDecimal = BigDecimal.valueOf(quDTO.getRate());
        rd.getCustomValues().put("qu_rate", bigDecimal.toPlainString());
        return rd;
    }

    public static String getRealId(String fakeId) {
        return fakeId.substring(3);
    }

    public static String getFakeId(String quantityType, String realId) {
        return quantityType + "_" + realId;
    }

    public static ResourceData QI2RD(QuantityInfo qi) {
        if (qi == null) {
            return null;
        }
        return QuantityConvert.DTO2RD(QuantityConvert.DO2DTO(qi));
    }

    public static ResourceData QC2RD(QuantityCategory qc) {
        if (qc == null) {
            return null;
        }
        return QuantityConvert.DTO2RD(QuantityConvert.DO2DTO(qc));
    }

    public static ResourceData QU2RD(QuantityUnit qu) {
        if (qu == null) {
            return null;
        }
        return QuantityConvert.DTO2RD(QuantityConvert.DO2DTO(qu));
    }

    public static ResourceGroup RD2RG(ResourceData rd) {
        if (rd == null) {
            return null;
        }
        ResourceGroup rg = new ResourceGroup();
        rg.setId(rd.getId());
        rg.setName(rd.getName());
        rg.setTitle(rd.getTitle());
        rg.setModifyTime(rd.getModifyTime());
        rg.setType(NodeType.NODE_GROUP);
        rg.setGroup(rd.getGroup());
        return rg;
    }
}

