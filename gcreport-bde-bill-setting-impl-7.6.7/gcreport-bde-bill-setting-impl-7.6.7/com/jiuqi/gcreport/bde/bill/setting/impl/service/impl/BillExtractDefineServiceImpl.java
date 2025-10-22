/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.gcreport.bde.bill.common.enums.BillSettingType
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillDefineDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractDefineDTO
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.billextract.client.BillExtractSettingClient
 *  com.jiuqi.gcreport.billextract.client.vo.TreeVO
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.va.bill.impl.BillMetaType
 *  com.jiuqi.va.datamodel.service.VaDataModelPublishedService
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service.impl;

import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.gcreport.bde.bill.common.enums.BillSettingType;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillDefineDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractDefineDTO;
import com.jiuqi.gcreport.bde.bill.setting.impl.dao.BillExtractDefineDao;
import com.jiuqi.gcreport.bde.bill.setting.impl.entity.BillExtractDefineEO;
import com.jiuqi.gcreport.bde.bill.setting.impl.intf.IBillDefineRefChecker;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillExtractDefineService;
import com.jiuqi.gcreport.bde.bill.setting.impl.utils.BillExtractSettingBillDefineUtil;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.billextract.client.BillExtractSettingClient;
import com.jiuqi.gcreport.billextract.client.vo.TreeVO;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.va.bill.impl.BillMetaType;
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillExtractDefineServiceImpl
implements BillExtractDefineService {
    @Autowired
    private BillExtractSettingClient extractSettingClient;
    @Autowired
    private BillExtractDefineDao defineDao;
    @Autowired(required=false)
    private List<IBillDefineRefChecker> refCheckers;
    private final NedisCache defineCache;
    private static final Logger LOGGER = LoggerFactory.getLogger(BillExtractDefineServiceImpl.class);
    private static final List<String> BILL_SYSTEM_EXTENDED_LIST = new ArrayList<String>(Arrays.asList("QRCODE", "QUOTECODE", "ATTACHNUM", "IMAGETYPE", "IMAGESTATE", "IMAGENUM", "DISABLESENDMAILFLAG", "GOTOLASTREJECT", "TEMPSTEP", "ABOLISHUSER", "ABOLISHTIME"));
    @Autowired
    private VaDataModelPublishedService dataModelService;

    public BillExtractDefineServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_BILL_EXTRACT_DEFINE_MANAGE");
        this.defineCache = cacheManager.getCache("BDE_BILL_EXTRACT_DEFINE");
    }

    @Override
    public List<TreeVO> getExtractDefineTree() {
        ArrayList extractDefineTreeList = CollectionUtils.newArrayList();
        for (BillSettingType settingType : BillSettingType.values()) {
            TreeVO settingTypeNode = new TreeVO();
            settingTypeNode.setId(settingType.getCode());
            settingTypeNode.setLeaf(Boolean.valueOf(false));
            settingTypeNode.setTitle(settingType.getName());
            settingTypeNode.setParentCode("-");
            settingTypeNode.setExpand(Boolean.valueOf(true));
            extractDefineTreeList.add(settingTypeNode);
            List<BillExtractDefineDTO> defines = this.listBySettingType(settingType.getCode());
            for (BillExtractDefineDTO extractDefine : defines) {
                TreeVO node = new TreeVO();
                node.setId(extractDefine.getId());
                node.setLeaf(Boolean.valueOf(true));
                node.setExpand(Boolean.valueOf(true));
                node.setTitle(extractDefine.getName());
                node.setParentCode(settingType.getCode());
                settingTypeNode.addChild(node);
            }
        }
        return extractDefineTreeList;
    }

    public List<BillExtractDefineDTO> listBySettingType(String billSettingType) {
        Map<String, String> billNameMap = this.queryBillMetaInfo().stream().collect(Collectors.toMap(MetaInfoDim::getUniqueCode, MetaInfoDim::getTitle, (k1, k2) -> k2));
        List<BillExtractDefineEO> fetchSchemeDatas = this.defineDao.listByBillSettingType(billSettingType);
        if (CollectionUtils.isEmpty(fetchSchemeDatas)) {
            return CollectionUtils.newArrayList();
        }
        ArrayList<BillExtractDefineDTO> result = new ArrayList<BillExtractDefineDTO>(fetchSchemeDatas.size());
        for (BillExtractDefineEO extractDefine : fetchSchemeDatas) {
            try {
                result.add(this.convert2Dto(extractDefine, billNameMap));
            }
            catch (Exception e) {
                LOGGER.error("BDE\u5355\u636e\u53d6\u6570\u5b9a\u4e49\u3010{}\u3011\u83b7\u53d6\u5bf9\u5e94\u7684\u6570\u636e\u9879\u65f6\u51fa\u73b0\u9519\u8bef\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)extractDefine.getId(), (Object)e);
            }
        }
        return result;
    }

    private BillExtractDefineDTO convert2Dto(BillExtractDefineEO eo, Map<String, String> billNameMap) {
        BillExtractDefineDTO dto;
        dto.setName(StringUtils.isEmpty((String)billNameMap.get((dto = (BillExtractDefineDTO)BeanConvertUtil.convert((Object)eo, BillExtractDefineDTO.class, (String[])new String[0])).getId())) ? dto.getId() : billNameMap.get(dto.getId()));
        return dto;
    }

    @Override
    public List<SelectOptionVO> getBillDefineList(String billSettingType) {
        List<MetaInfoDim> billMetaInfoList = this.queryBillMetaInfo();
        BillSettingType oppBillType = BillSettingType.getOppBillType((String)billSettingType);
        Set billDefineSet = this.listBySettingType(oppBillType.getCode()).stream().map(BillExtractDefineDTO::getId).collect(Collectors.toSet());
        return billMetaInfoList.stream().filter(item -> !billDefineSet.contains(item.getUniqueCode())).map(item -> new SelectOptionVO(item.getUniqueCode(), item.getTitle())).collect(Collectors.toList());
    }

    @Override
    public List<BillDefineDTO> getBillDefineListWithState(String metaType) {
        BillSettingType billSettingTypeEnum = BillSettingType.getEnumByCode((String)metaType);
        Assert.isNotNull((Object)billSettingTypeEnum);
        String billSettingType = billSettingTypeEnum.getCode();
        List<SelectOptionVO> billDefineList = this.getBillDefineList(billSettingType);
        List<BillExtractDefineDTO> defines = this.listBySettingType(billSettingType);
        ArrayList<BillDefineDTO> billDefineDTOList = new ArrayList<BillDefineDTO>();
        for (SelectOptionVO selectOptionVO : billDefineList) {
            BillDefineDTO billDefineDTO = new BillDefineDTO();
            billDefineDTO.setCode(selectOptionVO.getCode());
            billDefineDTO.setTitle(selectOptionVO.getName());
            for (BillExtractDefineDTO define : defines) {
                if (!define.getId().equals(selectOptionVO.getCode())) continue;
                billDefineDTO.setInExtractList(Boolean.valueOf(true));
            }
            billDefineDTOList.add(billDefineDTO);
        }
        return billDefineDTOList;
    }

    private List<MetaInfoDim> queryBillMetaInfo() {
        List billMetaInfoList = (List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.extractSettingClient.listMetaInfo(new BillMetaType().getName()));
        return billMetaInfoList == null ? CollectionUtils.newArrayList() : billMetaInfoList;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String saveExtractDefine(String metaType, List<BillExtractDefineDTO> extractBillList) {
        BillSettingType billSettingTypeEnum = BillSettingType.getEnumByCode((String)metaType);
        Assert.isNotNull((Object)billSettingTypeEnum);
        String billSettingType = billSettingTypeEnum.getCode();
        Set billDefineSet = this.listBySettingType(billSettingType).stream().map(BillExtractDefineDTO::getId).collect(Collectors.toSet());
        BillExtractDefineEO defineEo = null;
        StringBuilder warnMsg = new StringBuilder();
        ArrayList createDeineList = CollectionUtils.newArrayList();
        for (BillExtractDefineDTO extractDefine : extractBillList) {
            if (StringUtils.isEmpty((String)extractDefine.getId())) {
                LOGGER.error("\u5355\u636e\u53d6\u6570\u5355\u636e\u5b9a\u4e49\u4fdd\u5b58\u53c2\u6570\u9519\u8bef\uff0c\u5df2\u81ea\u52a8\u8df3\u8fc7\uff0c\u8be6\u7ec6\u53c2\u6570:{}", (Object)JsonUtils.writeValueAsString((Object)extractDefine));
                continue;
            }
            if (billDefineSet.contains(extractDefine.getId())) continue;
            defineEo = (BillExtractDefineEO)BeanConvertUtil.convert((Object)extractDefine, BillExtractDefineEO.class, (String[])new String[0]);
            defineEo.setOrdinal(BigDecimal.valueOf(System.currentTimeMillis()));
            createDeineList.add(defineEo);
        }
        ArrayList deleteDeineList = CollectionUtils.newArrayList();
        Set newDefineSet = extractBillList.stream().map(BillExtractDefineDTO::getId).collect(Collectors.toSet());
        for (String extractDefine : billDefineSet) {
            if (newDefineSet.contains(extractDefine)) continue;
            if (!this.hasRef(extractDefine)) {
                deleteDeineList.add(extractDefine);
                continue;
            }
            warnMsg.append(String.format("\u5355\u636e\u3010%1$s\u3011\u56e0\u5b58\u5728\u53d6\u6570\u65b9\u6848\u65e0\u6cd5\u5220\u9664\uff0c\u81ea\u52a8\u8df3\u8fc7", extractDefine));
        }
        this.defineDao.batchInsert(billSettingType, createDeineList);
        this.defineDao.batchDelete(billSettingType, deleteDeineList);
        return warnMsg.toString();
    }

    private boolean hasRef(String extractDefine) {
        boolean chk = true;
        for (IBillDefineRefChecker checker : this.refCheckers) {
            chk = checker.hasRef(extractDefine);
            if (!chk) continue;
            return true;
        }
        return false;
    }

    @Override
    public void cleanCache(String billSettingType) {
        this.defineCache.evict(billSettingType);
    }

    @Override
    public List<DataModelColumn> getBillDataModelList(DataModelDTO param) {
        PageVO dataModelDOPageVO = this.dataModelService.list(param);
        List dataModelDOList = dataModelDOPageVO.getRows();
        DataModelDO dataModelDO = (DataModelDO)dataModelDOList.get(0);
        List columns = dataModelDO.getColumns();
        return columns.stream().filter(item -> !DataModelType.ColumnAttr.SYSTEM.equals((Object)item.getColumnAttr()) && !BILL_SYSTEM_EXTENDED_LIST.contains(item.getColumnName())).collect(Collectors.toList());
    }

    @Override
    public List<DataModelColumn> getBillDataModelListByBillId(String billUniqueCode) {
        String mainTableName = BillExtractSettingBillDefineUtil.getMainTableNameByBillUniqueCode(billUniqueCode);
        DataModelDTO param = new DataModelDTO();
        param.setBiztype(DataModelType.BizType.BILL);
        param.setName(mainTableName);
        return this.getBillDataModelList(param);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean exchangeExtractDefineOrdinal(String srcBillDefine, String targetBillDefine) {
        return false;
    }
}

