/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.investcarryover.vo.InvestBillCarryOverSettingVO
 *  com.jiuqi.va.datamodel.service.VaDataModelPublishedService
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.gcreport.invest.investbillcarryover.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.invest.investbillcarryover.dao.InvestBillCarryOverSettingDao;
import com.jiuqi.gcreport.invest.investbillcarryover.entity.InvestBillCarryOverSettingEO;
import com.jiuqi.gcreport.invest.investbillcarryover.enums.AccoutCarryOverModeEnum;
import com.jiuqi.gcreport.invest.investbillcarryover.enums.AccoutTypeEnum;
import com.jiuqi.gcreport.invest.investbillcarryover.service.InvestBillCarryOverSettingService;
import com.jiuqi.gcreport.investcarryover.vo.InvestBillCarryOverSettingVO;
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
public class InvestBillCarryOverSettingServiceImpl
implements InvestBillCarryOverSettingService {
    @Autowired
    private InvestBillCarryOverSettingDao investBillCarryOverSettingDao;

    @Override
    public void saveSetting(InvestBillCarryOverSettingVO carryOverSettingVO) {
        InvestBillCarryOverSettingEO carryOverSettingEO = new InvestBillCarryOverSettingEO();
        BeanUtils.copyProperties(carryOverSettingVO, (Object)carryOverSettingEO);
        carryOverSettingEO.setOrdinal(new Double(OrderGenerator.newOrderID()));
        this.investBillCarryOverSettingDao.save(carryOverSettingEO);
    }

    @Override
    public List<InvestBillCarryOverSettingVO> listSettings(String carryOverSchemeId) {
        InvestBillCarryOverSettingEO carryOverSettingEO = new InvestBillCarryOverSettingEO();
        carryOverSettingEO.setCarryOverSchemeId(carryOverSchemeId);
        List<InvestBillCarryOverSettingEO> carryOverSettingEOS = this.investBillCarryOverSettingDao.listInvestBillCarryOverSetting(carryOverSchemeId);
        if (CollectionUtils.isEmpty(carryOverSettingEOS)) {
            return new ArrayList<InvestBillCarryOverSettingVO>();
        }
        if (carryOverSettingEOS.get(0).getOrdinal().equals(0.0)) {
            carryOverSettingEOS.forEach(eo -> eo.setOrdinal(new Double(OrderGenerator.newOrderID())));
            this.investBillCarryOverSettingDao.updateBatch(carryOverSettingEOS);
        }
        Map<String, Object> listCarryOverColums = this.listCarryOverColums();
        List investColums = (List)listCarryOverColums.get("investColums");
        List fvchColums = (List)listCarryOverColums.get("fvchColums");
        investColums.addAll(fvchColums);
        Map<String, String> columnName2TitleMap = investColums.stream().collect(Collectors.toMap(item -> item.getColumnName(), item -> item.getColumnTitle(), (k1, k2) -> k1));
        List<InvestBillCarryOverSettingVO> carryOverSettingVOS = carryOverSettingEOS.stream().map(eo -> this.covertEOToVO(columnName2TitleMap, (InvestBillCarryOverSettingEO)((Object)eo))).collect(Collectors.toList());
        return carryOverSettingVOS;
    }

    @NotNull
    private InvestBillCarryOverSettingVO covertEOToVO(Map<String, String> columnName2TitleMap, InvestBillCarryOverSettingEO eo) {
        AccoutCarryOverModeEnum carryOverModeEnum;
        InvestBillCarryOverSettingVO carryOverSettingVO = new InvestBillCarryOverSettingVO();
        BeanUtils.copyProperties((Object)eo, carryOverSettingVO);
        carryOverSettingVO.setTargetFieldTitle(columnName2TitleMap.get(carryOverSettingVO.getTargetField()));
        carryOverSettingVO.setSourceFieldTitle(columnName2TitleMap.get(carryOverSettingVO.getSourceField()));
        carryOverSettingVO.setSourceBeginFieldTitle(columnName2TitleMap.get(carryOverSettingVO.getSourceBeginField()));
        carryOverSettingVO.setSourceAddFieldTitle(columnName2TitleMap.get(carryOverSettingVO.getSourceAddField()));
        carryOverSettingVO.setSourceReduceFieldTitle(columnName2TitleMap.get(carryOverSettingVO.getSourceReduceField()));
        carryOverSettingVO.setAccountTypeTitle(AccoutTypeEnum.getEnumBycode(carryOverSettingVO.getAccountType()).getTitle());
        if (AccoutCarryOverModeEnum.CHANGE.getCode().equals(carryOverSettingVO.getCarryOverMode())) {
            List<String> list = Arrays.asList(carryOverSettingVO.getSourceFieldTitle(), carryOverSettingVO.getSourceBeginFieldTitle(), carryOverSettingVO.getSourceAddFieldTitle(), carryOverSettingVO.getSourceReduceFieldTitle());
            carryOverSettingVO.setSourceFieldTitle(list.stream().filter(Objects::nonNull).collect(Collectors.joining(",")));
        }
        carryOverSettingVO.setCarryOverModeTitle((carryOverModeEnum = AccoutCarryOverModeEnum.getEnumBycode(carryOverSettingVO.getCarryOverMode())) == null ? "" : carryOverModeEnum.getTitle());
        return carryOverSettingVO;
    }

    @Override
    public void updateSetting(InvestBillCarryOverSettingVO carryOverSettingVO) {
        InvestBillCarryOverSettingEO carryOverSettingEO = new InvestBillCarryOverSettingEO();
        BeanUtils.copyProperties(carryOverSettingVO, (Object)carryOverSettingEO);
        this.investBillCarryOverSettingDao.update((BaseEntity)carryOverSettingEO);
    }

    @Override
    public void deleteSetting(String id) {
        InvestBillCarryOverSettingEO carryOverSettingEO = new InvestBillCarryOverSettingEO();
        carryOverSettingEO.setId(id);
        this.investBillCarryOverSettingDao.delete((BaseEntity)carryOverSettingEO);
    }

    @Override
    public Map<String, Object> listCarryOverColums() {
        VaDataModelPublishedService vaDataModelPublishedService = (VaDataModelPublishedService)SpringContextUtils.getBean(VaDataModelPublishedService.class);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_INVESTBILL");
        DataModelDO dataModelDO = vaDataModelPublishedService.get(dataModelDTO);
        List columns = dataModelDO.getColumns();
        List<String> list = Arrays.asList("IMAGESTATE", "QRCODE", "ATTACHNUM", "UNITCODE", "INVESTEDUNIT");
        List investColums = columns.stream().filter(item -> {
            String columnName = item.getColumnName();
            if (list.contains(columnName)) {
                return false;
            }
            return item == null || !DataModelType.ColumnAttr.SYSTEM.equals((Object)item.getColumnAttr());
        }).collect(Collectors.toList());
        HashMap<String, Object> columsInfoMap = new HashMap<String, Object>();
        columsInfoMap.put("investColums", investColums);
        dataModelDTO.setName("GC_FVCH_FIXEDITEM");
        dataModelDO = vaDataModelPublishedService.get(dataModelDTO);
        List fvchColums = dataModelDO.getColumns().stream().filter(item -> item != null && !DataModelType.ColumnAttr.SYSTEM.equals((Object)item.getColumnAttr())).collect(Collectors.toList());
        dataModelDTO.setName("GC_FVCH_OTHERITEM");
        dataModelDO = vaDataModelPublishedService.get(dataModelDTO);
        fvchColums.addAll(dataModelDO.getColumns().stream().filter(item -> item != null && !DataModelType.ColumnAttr.SYSTEM.equals((Object)item.getColumnAttr())).collect(Collectors.toList()));
        columsInfoMap.put("fvchColums", fvchColums);
        return columsInfoMap;
    }

    @Override
    public void exchangeSort(String currId, String exchangeId) {
        if (currId.equals(exchangeId)) {
            return;
        }
        InvestBillCarryOverSettingEO currConfigEO = (InvestBillCarryOverSettingEO)this.investBillCarryOverSettingDao.get((Serializable)((Object)currId));
        InvestBillCarryOverSettingEO exchangeConfigEO = (InvestBillCarryOverSettingEO)this.investBillCarryOverSettingDao.get((Serializable)((Object)exchangeId));
        if (ObjectUtils.isEmpty((Object)currConfigEO) || ObjectUtils.isEmpty((Object)exchangeConfigEO)) {
            return;
        }
        Double currOrdinal = currConfigEO.getOrdinal();
        Double exchangeOrdinal = exchangeConfigEO.getOrdinal();
        currConfigEO.setOrdinal(exchangeOrdinal);
        exchangeConfigEO.setOrdinal(currOrdinal);
        this.investBillCarryOverSettingDao.update((BaseEntity)currConfigEO);
        this.investBillCarryOverSettingDao.update((BaseEntity)exchangeConfigEO);
    }
}

