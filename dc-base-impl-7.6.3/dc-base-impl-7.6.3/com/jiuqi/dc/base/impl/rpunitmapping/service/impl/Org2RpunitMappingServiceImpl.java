/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.client.rpunitmapping.dto.Org2RpunitMappingSaveDTO
 *  com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO
 *  com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingReturnVO
 *  com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingVO
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.organization.service.OrgDataService
 *  tk.mybatis.mapper.entity.EntityColumn
 *  tk.mybatis.mapper.mapperhelper.EntityHelper
 */
package com.jiuqi.dc.base.impl.rpunitmapping.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.client.rpunitmapping.dto.Org2RpunitMappingSaveDTO;
import com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO;
import com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingReturnVO;
import com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingVO;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.base.impl.rpunitmapping.dao.Org2RpunitMappingDao;
import com.jiuqi.dc.base.impl.rpunitmapping.entity.Org2RpunitMappingEntity;
import com.jiuqi.dc.base.impl.rpunitmapping.service.Org2RpunitMappingService;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.organization.service.OrgDataService;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

@Service
public class Org2RpunitMappingServiceImpl
implements Org2RpunitMappingService {
    @Autowired
    private Org2RpunitMappingDao org2RpunitMappingDao;
    @Autowired
    private OrgDataService orgDataService;
    @Autowired
    private OrgDataClient orgDataClient;
    private static final String STRING_EMPTY = "";

    @Override
    public Org2RpunitMappingReturnVO query(Org2RpunitMappingQueryVO queryVO) {
        Org2RpunitMappingReturnVO returnVO = new Org2RpunitMappingReturnVO();
        List<Map<String, Object>> qryResultMap = this.org2RpunitMappingDao.listAll(queryVO);
        ArrayList<Org2RpunitMappingVO> resultList = new ArrayList<Org2RpunitMappingVO>();
        Map<String, OrgDO> orgDOMap = this.queryOrgDO();
        for (Map<String, Object> result : qryResultMap) {
            Org2RpunitMappingVO vo = new Org2RpunitMappingVO();
            vo.setId(result.get("ID").toString());
            vo.setOrgCode(this.getStrValue(result.get("ORGCODE")));
            vo.setOrgName(orgDOMap.getOrDefault(this.getStrValue(result.get("ORGCODE")), new OrgDO()).getName());
            vo.setAcctYear(queryVO.getAcctYear().intValue());
            vo.setUnitCode1(this.getStrValue(result.get("UNITCODE_1")));
            vo.setUnitCode2(this.getStrValue(result.get("UNITCODE_2")));
            vo.setUnitCode3(this.getStrValue(result.get("UNITCODE_3")));
            vo.setUnitCode4(this.getStrValue(result.get("UNITCODE_4")));
            vo.setUnitCode5(this.getStrValue(result.get("UNITCODE_5")));
            vo.setUnitCode6(this.getStrValue(result.get("UNITCODE_6")));
            vo.setUnitCode7(this.getStrValue(result.get("UNITCODE_7")));
            vo.setUnitCode8(this.getStrValue(result.get("UNITCODE_8")));
            vo.setUnitCode9(this.getStrValue(result.get("UNITCODE_9")));
            vo.setUnitCode10(this.getStrValue(result.get("UNITCODE_10")));
            vo.setUnitCode11(this.getStrValue(result.get("UNITCODE_11")));
            vo.setUnitCode12(this.getStrValue(result.get("UNITCODE_12")));
            resultList.add(vo);
        }
        queryVO.setPageNum(Integer.valueOf(-1));
        int totalCount = this.org2RpunitMappingDao.getListAllCount(queryVO);
        returnVO.setDataList(resultList);
        returnVO.setTotalCount(Integer.valueOf(totalCount));
        return returnVO;
    }

    @Override
    @OuterTransaction
    public List<Org2RpunitMappingVO> save(List<Org2RpunitMappingVO> voList) {
        ArrayList<Org2RpunitMappingEntity> updateList = new ArrayList<Org2RpunitMappingEntity>();
        ArrayList<Org2RpunitMappingEntity> addList = new ArrayList<Org2RpunitMappingEntity>();
        for (Org2RpunitMappingVO vo : voList) {
            Org2RpunitMappingEntity entity = new Org2RpunitMappingEntity(vo.getAcctYear());
            if (StringUtils.isEmpty((String)vo.getId())) {
                vo.setId(UUIDUtils.newUUIDStr());
                BeanUtils.copyProperties(vo, (Object)entity);
                addList.add(entity);
                continue;
            }
            BeanUtils.copyProperties(vo, (Object)entity);
            updateList.add(entity);
        }
        this.org2RpunitMappingDao.batchInsert(addList);
        this.org2RpunitMappingDao.batchUpdate(updateList);
        if (!CollectionUtils.isEmpty(addList)) {
            LogHelper.info((String)DcFunctionModuleEnum.UNITMAPPING.getFullModuleName(), (String)String.format("\u65b0\u589e-\u4e00\u672c\u8d26\u5355\u4f4d%1$s-%2$d\u5e74", ((Org2RpunitMappingEntity)((Object)addList.get(0))).getOrgCode(), ((Org2RpunitMappingEntity)((Object)addList.get(0))).getAcctYear()), (String)JsonUtils.writeValueAsString(addList));
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            LogHelper.info((String)DcFunctionModuleEnum.UNITMAPPING.getFullModuleName(), (String)String.format("\u4fee\u6539-\u4e00\u672c\u8d26\u5355\u4f4d%1$s-%2$d\u5e74", ((Org2RpunitMappingEntity)((Object)updateList.get(0))).getOrgCode(), ((Org2RpunitMappingEntity)((Object)updateList.get(0))).getAcctYear()), (String)JsonUtils.writeValueAsString(updateList));
        }
        return voList;
    }

    @Override
    @OuterTransaction
    public Org2RpunitMappingVO saveOrUpdate(Org2RpunitMappingSaveDTO saveDto) {
        Assert.isNotNull((Object)saveDto);
        Assert.isTrue((saveDto.getAcctYear() > 2000 && saveDto.getAcctYear() < 2050 ? 1 : 0) != 0, (String)"\u5e74\u5ea6\u53c2\u6570\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((saveDto.getAcctPeriod() >= 1 && saveDto.getAcctPeriod() <= 12 ? 1 : 0) != 0, (String)"\u5e74\u5ea6\u53c2\u6570\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isNotEmpty((String)saveDto.getUnitCode(), (String)"\u4e00\u672c\u8d26\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)saveDto.getReportUnitCode(), (String)"\u62a5\u8868\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        OrgDTO orgDto = new OrgDTO();
        orgDto.setAuthType(OrgDataOption.AuthType.NONE);
        orgDto.setCode(saveDto.getUnitCode());
        OrgDO orgDO = this.orgDataService.get(orgDto);
        Assert.isNotNull((Object)orgDO, (String)String.format("\u4e00\u672c\u8d26\u5355\u4f4d\u4ee3\u7801\u3010%s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", orgDO), (Object[])new Object[0]);
        Org2RpunitMappingEntity condi = new Org2RpunitMappingEntity();
        condi.setAcctYear(saveDto.getAcctYear());
        condi.setOrgCode(saveDto.getUnitCode());
        List<Org2RpunitMappingEntity> savedList = this.org2RpunitMappingDao.select(condi);
        Org2RpunitMappingEntity entity = this.convert2Entity(saveDto);
        if (savedList.isEmpty()) {
            entity.setId(UUIDUtils.newUUIDStr());
            this.org2RpunitMappingDao.insert(entity);
            Org2RpunitMappingVO vo = (Org2RpunitMappingVO)BeanConvertUtil.convert((Object)((Object)entity), Org2RpunitMappingVO.class, (String[])new String[0]);
            vo.setOrgName(orgDO.getName());
            LogHelper.info((String)DcFunctionModuleEnum.UNITMAPPING.getFullModuleName(), (String)String.format("\u65b0\u589e-\u4e00\u672c\u8d26\u5355\u4f4d%1$s", saveDto.getUnitCode()), (String)JsonUtils.writeValueAsString((Object)saveDto));
            return vo;
        }
        if (savedList.size() == 1) {
            entity.setId(savedList.get(0).getId());
            this.org2RpunitMappingDao.updateByPrimaryKey(entity);
            Org2RpunitMappingVO vo = (Org2RpunitMappingVO)BeanConvertUtil.convert((Object)((Object)entity), Org2RpunitMappingVO.class, (String[])new String[0]);
            vo.setOrgName(orgDO.getName());
            LogHelper.info((String)DcFunctionModuleEnum.UNITMAPPING.getFullModuleName(), (String)String.format("\u4fee\u6539-\u4e00\u672c\u8d26\u5355\u4f4d%1$s", saveDto.getUnitCode()), (String)JsonUtils.writeValueAsString((Object)saveDto));
            return vo;
        }
        throw new BusinessRuntimeException(String.format("\u4e00\u672c\u8d26\u5355\u4f4d\u3010%s\u3011\u5df2\u5b58\u5728\u591a\u6761\u62a5\u8868\u5355\u4f4d\u6620\u5c04\uff0c\u4e0d\u5141\u8bb8\u4fee\u6539", saveDto.getUnitCode()));
    }

    @Override
    public int deleteByIds(Org2RpunitMappingQueryVO queryVO) {
        if (queryVO.getDelIds() != null && queryVO.getDelIds().size() > 0) {
            List<Map<String, Object>> deleteDatas = this.org2RpunitMappingDao.listAll(queryVO);
            Map<String, OrgDO> orgDOMap = this.queryOrgDO();
            for (Map<String, Object> deleteData : deleteDatas) {
                deleteData.put("ORGNAME", orgDOMap.getOrDefault(this.getStrValue(deleteData.get("ORGCODE")), new OrgDO()).getName());
            }
            int count = this.org2RpunitMappingDao.deleteByIds(queryVO);
            LogHelper.info((String)DcFunctionModuleEnum.UNITMAPPING.getFullModuleName(), (String)String.format("\u5220\u9664-%1$d\u5e74", queryVO.getAcctYear()), (String)JsonUtils.writeValueAsString(deleteDatas));
            return count;
        }
        throw new RuntimeException("\u672a\u52fe\u9009\u9700\u8981\u5220\u9664\u7684\u6570\u636e\u3002");
    }

    private String getStrValue(Object object) {
        return object == null ? STRING_EMPTY : object.toString();
    }

    @Override
    @OuterTransaction
    public String importExcel(Integer acctYear, List<Org2RpunitMappingVO> rowDatas) {
        if (rowDatas.isEmpty()) {
            return "\u5bfc\u5165\u5931\u8d25\uff0c\u6587\u4ef6\u5185\u5bb9\u4e3a\u7a7a";
        }
        ArrayList<String> chkMessage = new ArrayList<String>();
        ArrayList<String> orgCodeList = new ArrayList<String>(rowDatas.size());
        OrgDTO orgDto = new OrgDTO();
        orgDto.setAuthType(OrgDataOption.AuthType.NONE);
        Map<String, OrgDO> orgMap = this.orgDataService.list(orgDto).getRows().stream().collect(Collectors.toMap(OrgDO::getCode, item -> item, (k1, k2) -> k2));
        int index = 1;
        for (Org2RpunitMappingVO org2RpunitMappingVO : rowDatas) {
            ++index;
            if (StringUtils.isEmpty((String)org2RpunitMappingVO.getOrgCode())) {
                chkMessage.add(String.format("\u7b2c%d\u884c\uff0c\u4e00\u672c\u8d26\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", index));
                org2RpunitMappingVO.setIgnored(true);
                continue;
            }
            if (orgMap.get(org2RpunitMappingVO.getOrgCode()) == null) {
                chkMessage.add(String.format("\u7b2c%1$d\u884c\uff0c\u4e00\u672c\u8d26\u5355\u4f4d\u4ee3\u7801\u3010%2$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", index, org2RpunitMappingVO.getOrgCode()));
                org2RpunitMappingVO.setIgnored(true);
                continue;
            }
            orgCodeList.add(org2RpunitMappingVO.getOrgCode());
        }
        Org2RpunitMappingQueryVO condi = new Org2RpunitMappingQueryVO();
        condi.setAcctYear(acctYear);
        condi.setPageNum(Integer.valueOf(-1));
        condi.setOrgs(orgCodeList);
        ArrayList listByOrgCodes = orgCodeList.isEmpty() ? CollectionUtils.newArrayList() : this.org2RpunitMappingDao.listByOrgCodes(condi);
        HashMap mappingMap = new HashMap(listByOrgCodes.size());
        HashSet repeatCodeSet = new HashSet();
        listByOrgCodes.forEach(item -> {
            if (mappingMap.containsKey(item.getOrgCode())) {
                repeatCodeSet.add(item.getOrgCode());
            } else {
                mappingMap.put(item.getOrgCode(), item);
            }
        });
        ArrayList<Org2RpunitMappingEntity> addList = new ArrayList<Org2RpunitMappingEntity>();
        ArrayList<Org2RpunitMappingEntity> updateList = new ArrayList<Org2RpunitMappingEntity>();
        index = 1;
        for (Org2RpunitMappingVO org2RpunitMappingVO : rowDatas) {
            ++index;
            if (org2RpunitMappingVO.getIgnored()) continue;
            if (repeatCodeSet.contains(org2RpunitMappingVO.getOrgCode())) {
                chkMessage.add(String.format("\u7b2c%1$d\u884c\uff0c\u4e00\u672c\u8d26\u5355\u4f4d\u4ee3\u7801\u3010%2$s\u3011\u5b58\u5728\u591a\u6761\u62a5\u8868\u5355\u4f4d\u6620\u5c04\u3001\u81ea\u52a8\u8df3\u8fc7", index, org2RpunitMappingVO.getOrgCode()));
                continue;
            }
            if (mappingMap.containsKey(org2RpunitMappingVO.getOrgCode())) {
                org2RpunitMappingVO.setId(((Org2RpunitMappingEntity)((Object)mappingMap.get(org2RpunitMappingVO.getOrgCode()))).getId());
                org2RpunitMappingVO.setAcctYear(acctYear.intValue());
                updateList.add((Org2RpunitMappingEntity)((Object)BeanConvertUtil.convert((Object)org2RpunitMappingVO, Org2RpunitMappingEntity.class, (String[])new String[0])));
                continue;
            }
            org2RpunitMappingVO.setId(UUIDUtils.newUUIDStr());
            org2RpunitMappingVO.setAcctYear(acctYear.intValue());
            addList.add((Org2RpunitMappingEntity)((Object)BeanConvertUtil.convert((Object)org2RpunitMappingVO, Org2RpunitMappingEntity.class, (String[])new String[0])));
        }
        this.org2RpunitMappingDao.batchInsert(addList);
        this.org2RpunitMappingDao.batchUpdate(updateList);
        int total = rowDatas.size();
        int success = addList.size() + updateList.size();
        int fail = total - success;
        StringBuilder message = new StringBuilder(String.format("\u5bfc\u5165\u5b8c\u6210\uff0c\u5171\u5bfc\u5165%1$d\u6761\u6570\u636e\uff0c\u5176\u4e2d\u65b0\u589e%2$d\u6761\uff0c\u66f4\u65b0%3$d\u6761\uff0c\u5931\u8d25%4$d\u6761\r\n", total, addList.size(), updateList.size(), fail));
        if (!chkMessage.isEmpty()) {
            message.append("\u5931\u8d25\u6570\u636e\u8be6\u7ec6\u4fe1\u606f\uff1a\r\n").append(StringUtils.join((Object[])chkMessage.toArray(), (String)"\r\n"));
        }
        return message.toString();
    }

    @Override
    public List<String> listUnitCodeByOrgCodeAndPeriod(String orgCode, int year, int period) {
        Org2RpunitMappingQueryVO queryVO = new Org2RpunitMappingQueryVO();
        queryVO.setAcctYear(Integer.valueOf(year));
        queryVO.setAcctPeriod(Integer.valueOf(period));
        queryVO.setOrgs(Arrays.asList(orgCode));
        return this.org2RpunitMappingDao.listUnitCodeByOrgCodeAndPeriod(queryVO);
    }

    @Override
    public List<String> listOrgCode(int year) {
        Org2RpunitMappingEntity orgUnitMappingDO = new Org2RpunitMappingEntity(year);
        return this.org2RpunitMappingDao.listOrgCode(orgUnitMappingDO);
    }

    @Override
    public List<String> getAllOrgCodeByRpUnitCode(Org2RpunitMappingQueryVO queryVO) {
        return this.org2RpunitMappingDao.getAllOrgCodeByRpUnitCode(queryVO);
    }

    private Org2RpunitMappingEntity convert2Entity(Org2RpunitMappingSaveDTO saveDto) {
        Org2RpunitMappingEntity mappingEntity = new Org2RpunitMappingEntity(saveDto.getAcctYear());
        mappingEntity.setOrgCode(saveDto.getUnitCode());
        Map<String, EntityColumn> columnMap = EntityHelper.getColumns(Org2RpunitMappingEntity.class).stream().collect(Collectors.toMap(EntityColumn::getColumn, entity -> entity, (k1, k2) -> k2));
        for (int period = 1; period <= 12; ++period) {
            Method writeMethod;
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(Org2RpunitMappingEntity.class, columnMap.get("UNITCODE_" + period).getProperty());
            if (propertyDescriptor == null || (writeMethod = propertyDescriptor.getWriteMethod()) == null) continue;
            try {
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }
                writeMethod.invoke((Object)mappingEntity, period < saveDto.getAcctPeriod() ? "#" : saveDto.getReportUnitCode());
                continue;
            }
            catch (Throwable ex) {
                throw new FatalBeanException("Could not copy property '" + propertyDescriptor.getName() + "' from source to target", ex);
            }
        }
        return mappingEntity;
    }

    private Map<String, OrgDO> queryOrgDO() {
        OrgDTO condi = new OrgDTO();
        condi.setPagination(Boolean.valueOf(false));
        condi.setAuthType(OrgDataOption.AuthType.NONE);
        List baseDatas = this.orgDataClient.list(condi).getRows();
        if (CollectionUtils.isEmpty((Collection)baseDatas)) {
            return new HashMap<String, OrgDO>();
        }
        return baseDatas.stream().collect(Collectors.toMap(OrgDO::getCode, item -> item, (k1, k2) -> k2));
    }
}

