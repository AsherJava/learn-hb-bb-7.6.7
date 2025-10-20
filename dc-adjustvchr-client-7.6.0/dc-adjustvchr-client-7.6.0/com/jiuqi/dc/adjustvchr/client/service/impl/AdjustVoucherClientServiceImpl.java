/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.TableParseUtils
 *  com.jiuqi.dc.base.common.env.EnvCenter
 *  com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties
 *  com.jiuqi.dc.base.common.utils.BeanCopyUtil
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.impl.bean.OrgDataDO
 *  com.jiuqi.gcreport.organization.impl.service.GcOrgDataService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.dc.adjustvchr.client.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.TableParseUtils;
import com.jiuqi.dc.adjustvchr.client.dao.AdjustVchrClientDao;
import com.jiuqi.dc.adjustvchr.client.domain.AdjustVchrIdListDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVchrQueryResultDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.service.AdjustVoucherClientService;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrBaseDataVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrItemVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO;
import com.jiuqi.dc.base.common.env.EnvCenter;
import com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties;
import com.jiuqi.dc.base.common.utils.BeanCopyUtil;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.impl.bean.OrgDataDO;
import com.jiuqi.gcreport.organization.impl.service.GcOrgDataService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdjustVoucherClientServiceImpl
implements AdjustVoucherClientService {
    @Autowired
    private GcOrgDataService gcOrgDataService;
    @Autowired
    private INvwaSystemOptionService sysOptionService;
    @Autowired
    private BaseDataClient baseDataApi;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private ServiceConfigProperties prop;
    @Autowired
    private AdjustVchrClientDao adjustVchrClientDao;
    private static final Map<String, String> convertCurrencyMap = new HashMap<String, String>();

    @Override
    public String getMaxVchrNum(String unitCode, Integer acctYear) {
        return this.listVchrNumBySize(unitCode, acctYear, 1).get(0);
    }

    @Override
    public List<String> listVchrNumBySize(String unitCode, Integer acctYear, int size) {
        Assert.isNotEmpty((String)unitCode, (String)"\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)acctYear, (String)"\u5e74\u5ea6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((size > 0 ? 1 : 0) != 0, (String)"\u83b7\u53d6\u51ed\u8bc1\u53f7\u6570\u91cf\u4e0d\u80fd\u5c0f\u4e8e1", (Object[])new Object[0]);
        String vchrNum = this.adjustVchrClientDao.getMaxVchrNum(unitCode, acctYear);
        Integer num = StringUtils.isEmpty((String)vchrNum) ? 0 : Integer.valueOf(vchrNum.substring(6));
        String year = String.valueOf(Calendar.getInstance().get(1));
        String period = String.format("%02d", Calendar.getInstance().get(2) + 1);
        String vchrNumPrefix = year + period;
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 1; i <= size; ++i) {
            result.add(vchrNumPrefix + String.format("%04d", num + i));
        }
        return result;
    }

    @Override
    public AdjustVchrQueryResultDTO list(AdjustVoucherQueryDTO param) {
        OrgDataParam orgDataParam = new OrgDataParam();
        orgDataParam.setOrgType(param.getOrgType());
        orgDataParam.setAuthType("ACCESS");
        orgDataParam.setOrgVerCode(param.getAcctYear() + param.getPeriodType() + String.format("00%02d", param.getAffectPeriodStart()));
        HashSet unitCodes = new HashSet();
        HashMap<String, OrgDataDO> unitMap = new HashMap<String, OrgDataDO>();
        List orgDataDOList = new ArrayList();
        if (CollectionUtils.isEmpty(param.getUnitCodes())) {
            orgDataDOList = this.gcOrgDataService.listAllChildrenWithSelf(orgDataParam);
            unitMap.putAll(orgDataDOList.stream().collect(Collectors.toMap(OrgDataDO::getCode, unit -> unit, (k1, k2) -> k2)));
            unitCodes.addAll(orgDataDOList.stream().map(OrgDataDO::getCode).collect(Collectors.toList()));
        } else {
            for (String unitCode : param.getUnitCodes()) {
                orgDataParam.setOrgParentCode(unitCode);
                orgDataDOList = this.gcOrgDataService.listAllChildrenWithSelf(orgDataParam);
                unitMap.putAll(orgDataDOList.stream().collect(Collectors.toMap(OrgDataDO::getCode, unit -> unit, (k1, k2) -> k2)));
                unitCodes.addAll(orgDataDOList.stream().map(OrgDataDO::getCode).collect(Collectors.toList()));
            }
        }
        if (CollectionUtils.isEmpty(unitCodes)) {
            return new AdjustVchrQueryResultDTO();
        }
        param.setUnitCodes(new ArrayList<String>(unitCodes));
        int count = this.adjustVchrClientDao.countByCondi(param);
        if (count == 0) {
            return new AdjustVchrQueryResultDTO();
        }
        BaseDataDTO baseDataCondi = new BaseDataDTO();
        baseDataCondi.setTableName("MD_ADJUSTTYPE");
        PageVO adjustTypeData = this.baseDataApi.list(baseDataCondi);
        Map<Object, Object> baseDataDOMap = new HashMap();
        if (adjustTypeData != null && adjustTypeData.getRs().getCode() == 0 && adjustTypeData.getTotal() > 0) {
            baseDataDOMap = adjustTypeData.getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO::getName, (k1, k2) -> k2));
        }
        List<AdjustVoucherVO> vouchers = this.adjustVchrClientDao.listByCondi(param);
        HashMap<String, AdjustVoucherVO> groupMap = new HashMap<String, AdjustVoucherVO>();
        HashMap<String, String> vchrIdMap = new HashMap<String, String>();
        Iterator<AdjustVoucherVO> iterator = vouchers.iterator();
        while (iterator.hasNext()) {
            AdjustVoucherVO voucher;
            String adjustTypeName = (String)baseDataDOMap.get((voucher = iterator.next()).getAdjustTypeCode());
            voucher.setAdjustTypeName((String)(adjustTypeName == null ? voucher.getAdjustTypeCode() : adjustTypeName));
            voucher.setItems(new ArrayList<AdjustVchrItemVO>());
            groupMap.put(voucher.getGroupId(), voucher);
            vchrIdMap.put(voucher.getId(), voucher.getGroupId());
        }
        AdjustVchrIdListDTO itemParam = new AdjustVchrIdListDTO();
        ArrayList<String> voucherIds = new ArrayList<String>();
        for (Map.Entry entry : groupMap.entrySet()) {
            voucherIds.add(((AdjustVoucherVO)entry.getValue()).getId());
        }
        itemParam.setVchrIds(voucherIds);
        itemParam.setAssistDims(this.dimensionService.findAllDimFieldsVOByTableName("DC_ADJUSTVCHRITEM"));
        itemParam.setConvertAmountCols(this.getConvertAmountColumns());
        List<AdjustVchrItemEO> itemDatas = this.adjustVchrClientDao.listByVchrId(itemParam);
        this.convert2VO(vchrIdMap, groupMap, itemDatas, unitMap);
        return new AdjustVchrQueryResultDTO(vouchers, count);
    }

    @Override
    public List<DimensionVO> listAdjustDim() {
        return this.dimensionService.findDimFieldsVOByTableName("DC_ADJUSTVCHRITEM");
    }

    @Override
    public Map<String, List<String>> getAccountAndReclassifly() {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        boolean bizDateEnable = "1".equals(this.sysOptionService.findValueById("DC_ADJUSTVOUCHER_BIZDATEENABLE"));
        boolean istableExist = TableParseUtils.tableExist((String)"", Arrays.asList("DC_SCHEME_ACCOUNTAGE", "DC_SCHEME_RECLASSIFY"));
        if (!bizDateEnable || !istableExist) {
            result.put("accountSubj", new ArrayList());
            result.put("reclassifySubj", new ArrayList());
            return result;
        }
        HashSet<String> accountSubSet = new HashSet<String>(this.adjustVchrClientDao.listAccountSubject());
        HashSet<String> reclassifySubSet = new HashSet<String>(this.adjustVchrClientDao.listReclassfySubject());
        result.put("accountSubj", new ArrayList<String>(accountSubSet));
        result.put("reclassifySubj", new ArrayList<String>(reclassifySubSet));
        return result;
    }

    @Override
    public AdjustVchrSysOptionVO getAdjustVchrSysOptions() {
        boolean digestNotNull = "1".equals(this.sysOptionService.findValueById("DC_ADJUSTVOUCHER_DIGESTNOTNULL"));
        boolean bizDateEnable = "1".equals(this.sysOptionService.findValueById("DC_ADJUSTVOUCHER_BIZDATEENABLE"));
        boolean cfItemEnable = "1".equals(this.sysOptionService.findValueById("DC_ADJUSTVOUCHER_CFITEMENABLE"));
        boolean enableOrgnCurr = "1".equals(this.sysOptionService.findValueById("DC_ADJUSTVOUCHER_ENABLE_ORGNCURR"));
        boolean remarkEnable = "1".equals(this.sysOptionService.findValueById("DC_ADJUSTVOUCHER_REMARKENABLE"));
        ArrayList<String> extraFIelds = new ArrayList<String>();
        if (bizDateEnable) {
            extraFIelds.add("BIZDATE");
            extraFIelds.add("EXPIREDATE");
        }
        if (cfItemEnable) {
            extraFIelds.add("CFITEMCODE");
        }
        AdjustVchrSysOptionVO sysOptionVO = new AdjustVchrSysOptionVO();
        sysOptionVO.setDigestNotNull(digestNotNull);
        sysOptionVO.setExtraFields(extraFIelds);
        sysOptionVO.setEnableOrgnCurr(enableOrgnCurr);
        sysOptionVO.setRemarkEnable(remarkEnable);
        return sysOptionVO;
    }

    @Override
    public List<AdjustVchrBaseDataVO> getRepCurrCodeByUnit(String unitCode) {
        return CollectionUtils.newArrayList();
    }

    private void convert2VO(Map<String, String> vchrIdMap, Map<String, AdjustVoucherVO> groupMap, List<AdjustVchrItemEO> itemDatas, Map<String, OrgDataDO> unitMap) {
        List assistDimList = this.dimensionService.findAllDimFieldsVOByTableName("DC_ADJUSTVCHRITEM");
        Map<String, DimensionVO> assistDimMap = assistDimList.stream().collect(Collectors.toMap(DimensionVO::getCode, assist -> assist, (k1, k2) -> k2));
        HashMap<String, Map<String, AdjustVchrBaseDataVO>> assistBaseDataMap = new HashMap<String, Map<String, AdjustVchrBaseDataVO>>();
        HashMap<String, Set<String>> baseDataCodes = new HashMap<String, Set<String>>();
        HashMap<String, String> dimCodeReferFieldMap = new HashMap<String, String>();
        for (AdjustVchrItemEO itemData : itemDatas) {
            AdjustVchrItemVO item = (AdjustVchrItemVO)BeanCopyUtil.copyObj(AdjustVchrItemVO.class, (Object)((Object)itemData));
            item.setUnitName(unitMap.get(item.getUnitCode()).getName());
            AdjustVoucherVO group = groupMap.get(vchrIdMap.get(item.getVchrId()));
            group.getItems().add(item);
            for (String assistFlag : item.getAssistDatas().keySet()) {
                if (StringUtils.isEmpty((String)assistDimMap.get(assistFlag).getReferField())) continue;
                String tableName = assistDimMap.get(assistFlag).getReferField();
                this.addBaseDataCode(baseDataCodes, tableName, String.valueOf(item.getAssistDatas().get(assistFlag)));
                dimCodeReferFieldMap.put(tableName, assistFlag);
            }
        }
        String loginUnitCode = EnvCenter.getContextOrgCode();
        for (String assistTableName : baseDataCodes.keySet()) {
            assistBaseDataMap.put((String)dimCodeReferFieldMap.get(assistTableName), this.getBaseDataInfo(loginUnitCode, loginUnitCode, assistTableName, (Set)baseDataCodes.get(assistTableName)));
        }
        for (AdjustVoucherVO group : groupMap.values()) {
            this.convertVoProperty(group, assistDimMap, assistBaseDataMap);
        }
    }

    private void convertVoProperty(AdjustVoucherVO voucher, Map<String, DimensionVO> assistDimMap, Map<String, Map<String, AdjustVchrBaseDataVO>> assistBaseDataMap) {
        for (AdjustVchrItemVO item : voucher.getItems()) {
            voucher.setDsum(NumberUtils.sum((double)(voucher.getDsum() != null ? voucher.getDsum() : 0.0), (double)(item.getDebit() == null ? 0.0 : item.getDebit().doubleValue())));
            voucher.setCsum(NumberUtils.sum((double)(voucher.getCsum() != null ? voucher.getCsum() : 0.0), (double)(item.getCredit() == null ? 0.0 : item.getCredit().doubleValue())));
            item.setAdjustTypeName(voucher.getAdjustTypeName());
            for (Map.Entry<String, Object> entry : item.getAssistDatas().entrySet()) {
                DimensionVO assistDim = assistDimMap.get(entry.getKey());
                if (!StringUtils.isEmpty((String)assistDim.getReferField())) {
                    item.getAssistDatas().put(entry.getKey(), assistBaseDataMap.get(entry.getKey()).get(entry.getValue()));
                    continue;
                }
                item.getAssistDatas().put(entry.getKey(), entry.getValue());
            }
        }
        voucher.setDifference(NumberUtils.sub((double)voucher.getDsum(), (double)voucher.getCsum()));
    }

    private Map<String, AdjustVchrBaseDataVO> getBaseDataInfo(String unitCode, String bookCode, String tableName, Set<String> codes) {
        BaseDataDTO condi = new BaseDataDTO();
        condi.setPagination(Boolean.valueOf(false));
        condi.setUnitcode(unitCode);
        condi.setTableName(tableName);
        condi.put("baseDataCodes", codes.stream().collect(Collectors.toList()));
        List baseDataList = this.baseDataApi.list(condi).getRows();
        Set baseDataCodeSet = baseDataList.stream().map(BaseDataDO::getCode).collect(Collectors.toSet());
        BaseDataDO baseDataDO = new BaseDataDO();
        for (String code : codes) {
            if (baseDataCodeSet.contains(code)) continue;
            baseDataDO.setCode(code);
            baseDataDO.setName(code);
            baseDataDO.setTableName(tableName);
            baseDataList.add(baseDataDO);
        }
        HashMap<String, AdjustVchrBaseDataVO> baseDataInfo = new HashMap<String, AdjustVchrBaseDataVO>(baseDataList.size());
        baseDataList.forEach(basedata -> baseDataInfo.put(basedata.getCode(), new AdjustVchrBaseDataVO((BaseDataDO)basedata)));
        return baseDataInfo;
    }

    private void addBaseDataCode(Map<String, Set<String>> baseDataCodes, String tableName, String code) {
        if (StringUtils.isEmpty((String)code)) {
            return;
        }
        if (baseDataCodes.containsKey(tableName)) {
            baseDataCodes.get(tableName).add(code);
        } else {
            HashSet<String> values = new HashSet<String>();
            values.add(code);
            baseDataCodes.put(tableName, values);
        }
    }

    private List<String> getConvertAmountColumns() {
        String[] convertAmountColArr = new String[]{"CNYDEBIT", "CNYCREDIT", "HKDDEBIT", "HKDCREDIT", "USDDEBIT", "USDCREDIT"};
        return Arrays.asList(convertAmountColArr);
    }

    static {
        convertCurrencyMap.put("CNY", "\u4eba\u6c11\u5e01");
        convertCurrencyMap.put("HKD", "\u6e2f\u5e01");
        convertCurrencyMap.put("USD", "\u7f8e\u5143");
    }
}

