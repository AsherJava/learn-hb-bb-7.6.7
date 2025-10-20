/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodCondiVO
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.base.common.module.IModuleGather
 *  com.jiuqi.dc.base.common.utils.BeanCopyUtil
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.dc.base.impl.onlinePeriod.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodCondiVO;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.base.common.module.IModuleGather;
import com.jiuqi.dc.base.common.utils.BeanCopyUtil;
import com.jiuqi.dc.base.impl.onlinePeriod.domain.OnlinePeriodDefineDO;
import com.jiuqi.dc.base.impl.onlinePeriod.mapper.OnlinePeriodDefineMapper;
import com.jiuqi.dc.base.impl.onlinePeriod.service.OnlinePeriodDefineService;
import com.jiuqi.dc.base.impl.orgcomb.service.OrgCombDefineService;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.np.log.LogHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlinePeriodDefineServiceImpl
implements OnlinePeriodDefineService {
    private static final Logger logger = LoggerFactory.getLogger(OnlinePeriodDefineServiceImpl.class);
    @Autowired
    private OnlinePeriodDefineMapper onlinePeriodDefineMapper;
    @Autowired
    private IModuleGather iModuleGather;
    @Autowired
    private OrgCombDefineService orgCombDefineService;

    @Override
    public List<OnlinePeriodDefineVO> getAllTableData(OnlinePeriodCondiVO onlinePeriodCondiVO) {
        OnlinePeriodDefineDO onlinePeriodDefineDO = new OnlinePeriodDefineDO();
        onlinePeriodDefineDO.setModuleCode(onlinePeriodCondiVO.getModuleCode());
        List<OnlinePeriodDefineVO> defineVOList = this.onlinePeriodDefineMapper.getAllTableData(onlinePeriodDefineDO);
        if (!StringUtils.isEmpty((String)onlinePeriodCondiVO.getUnitCode())) {
            List<String> orgCombCodeList = this.orgCombDefineService.findOrgCombCodes(onlinePeriodCondiVO.getUnitCode());
            ArrayList<OnlinePeriodDefineVO> onlinePeriodDefineVOList = new ArrayList<OnlinePeriodDefineVO>();
            if (CollectionUtils.isEmpty(orgCombCodeList)) {
                return onlinePeriodDefineVOList;
            }
            HashSet<String> orgCombCodeSet = new HashSet<String>(orgCombCodeList);
            block0: for (OnlinePeriodDefineVO defineVO : defineVOList) {
                for (String orgCombCode : defineVO.getOrgCombCodes().split(";")) {
                    if (!orgCombCodeSet.contains(orgCombCode)) continue;
                    defineVO.setGroupName(this.iModuleGather.getModuleByCode(defineVO.getModuleCode()).getName());
                    onlinePeriodDefineVOList.add(defineVO);
                    continue block0;
                }
            }
            return onlinePeriodDefineVOList;
        }
        for (OnlinePeriodDefineVO defineVO : defineVOList) {
            defineVO.setGroupName(this.iModuleGather.getModuleByCode(defineVO.getModuleCode()).getName());
            String[] tempPeriod = defineVO.getOnlinePeriod().split("-");
            defineVO.setOnlinePeriod(tempPeriod[0] + "\u5e74" + tempPeriod[1] + "\u6708");
        }
        return defineVOList;
    }

    @Override
    public void insertPeriod(OnlinePeriodDefineVO onlinePeriodDefineVO) {
        Assert.isNotNull((Object)onlinePeriodDefineVO.getOnlinePeriod(), (String)"\u4e0a\u7ebf\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)onlinePeriodDefineVO.getOrgCombCodes(), (String)"\u5355\u4f4d\u7ec4\u81f3\u5c11\u9009\u62e9\u4e00\u4e2a", (Object[])new Object[0]);
        this.checkExistsOnlinePeriodByCombCode(onlinePeriodDefineVO);
        Integer minYear = this.getMinPeriodYear();
        OnlinePeriodDefineDO onlinePeriodDefineDO = (OnlinePeriodDefineDO)((Object)BeanCopyUtil.copyObj(OnlinePeriodDefineDO.class, (Object)onlinePeriodDefineVO));
        onlinePeriodDefineDO.setId(UUIDUtils.newUUIDStr());
        onlinePeriodDefineDO.setVer(0L);
        this.onlinePeriodDefineMapper.insert((Object)onlinePeriodDefineDO);
        int saveYear = Integer.parseInt(onlinePeriodDefineVO.getOnlinePeriod().substring(0, 4));
        String title = String.format("\u65b0\u589e-%1$d\u5e74%2$s\u6708", saveYear, onlinePeriodDefineVO.getOnlinePeriod().substring(5));
        LogHelper.info((String)DcFunctionModuleEnum.ONLINEPERIOD.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)onlinePeriodDefineVO));
        this.syncTableByYear(saveYear, minYear);
    }

    @Override
    public void nvwaImportPeriod(OnlinePeriodDefineVO onlinePeriodDefineVO) {
        Assert.isNotNull((Object)onlinePeriodDefineVO.getOnlinePeriod(), (String)"\u4e0a\u7ebf\u671f\u95f4\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)onlinePeriodDefineVO.getOrgCombCodes(), (String)"\u5355\u4f4d\u7ec4\u81f3\u5c11\u9009\u62e9\u4e00\u4e2a", (Object[])new Object[0]);
        this.checkExistsOnlinePeriodByCombCode(onlinePeriodDefineVO);
        Integer minYear = this.getMinPeriodYear();
        OnlinePeriodDefineDO onlinePeriodDefineDO = (OnlinePeriodDefineDO)((Object)BeanCopyUtil.copyObj(OnlinePeriodDefineDO.class, (Object)onlinePeriodDefineVO));
        onlinePeriodDefineDO.setVer(0L);
        this.onlinePeriodDefineMapper.insert((Object)onlinePeriodDefineDO);
        int saveYear = Integer.parseInt(onlinePeriodDefineVO.getOnlinePeriod().substring(0, 4));
        String title = String.format("\u5973\u5a32\u53c2\u6570\u5bfc\u5165-%1$d\u5e74%2$s\u6708", saveYear, onlinePeriodDefineVO.getOnlinePeriod().substring(5));
        LogHelper.info((String)DcFunctionModuleEnum.ONLINEPERIOD.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)onlinePeriodDefineVO));
        this.syncTableByYear(saveYear, minYear);
    }

    private void checkExistsOnlinePeriodByCombCode(OnlinePeriodDefineVO onlinePeriodDefineVO) {
        String[] orgCombCodes;
        OnlinePeriodDefineDO onlinePeriodDefineDO = (OnlinePeriodDefineDO)((Object)BeanCopyUtil.copyObj(OnlinePeriodDefineDO.class, (Object)onlinePeriodDefineVO));
        List<OnlinePeriodDefineVO> onlinePeriodDefines = this.onlinePeriodDefineMapper.getAllTableData(onlinePeriodDefineDO);
        HashSet<String> orgCombCodeSet = new HashSet<String>();
        for (OnlinePeriodDefineVO onlinePeriodDefine : onlinePeriodDefines) {
            if (onlinePeriodDefine.getId().equals(onlinePeriodDefineVO.getId())) continue;
            String[] orgCombCodes2 = onlinePeriodDefine.getOrgCombCodes().split(";");
            orgCombCodeSet.addAll(Arrays.asList(orgCombCodes2));
        }
        for (String orgCombCode : orgCombCodes = onlinePeriodDefineVO.getOrgCombCodes().split(";")) {
            if (!orgCombCodeSet.contains(orgCombCode)) continue;
            throw new IllegalArgumentException(String.format("\u5355\u4f4d\u7ec4\u5408\u3010%1$s\u3011\u5df2\u6709\u4e0a\u7ebf\u671f\u95f4", orgCombCode));
        }
    }

    private void syncTableByYear(Integer saveYear, Integer minYear) {
        ArrayList<Integer> asyncYearList = new ArrayList<Integer>();
        if (minYear == null) {
            Calendar calendar = Calendar.getInstance();
            minYear = calendar.get(1);
        }
        if (saveYear < minYear) {
            for (int i = saveYear.intValue(); i < minYear; ++i) {
                asyncYearList.add(i);
            }
        }
        if (!CollectionUtils.isEmpty(asyncYearList)) {
            EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
            ArrayList tableDefineList = CollectionUtils.newArrayList();
            entityTableCollector.getEntitys().forEach(entity -> {
                List shardingList;
                if (ShardingBaseEntity.class.isAssignableFrom(entity.getClass()) && !CollectionUtils.isEmpty((Collection)(shardingList = ((ShardingBaseEntity)entity).getShardingList()))) {
                    for (String sharding : shardingList) {
                        TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance((BaseEntity)entity, (DBTable)entityTableCollector.getDbTableByType(entity.getClass()));
                        DefinitionTableV tableDefine = tableDefineConvertHelper.convert();
                        if (entity instanceof ITableExtend) {
                            tableDefine.getFields().addAll(((ITableExtend)entity).getExtendFieldList(null));
                        }
                        String tableName = (((ShardingBaseEntity)entity).getTableNamePrefix() + "_" + sharding).toUpperCase();
                        tableDefine.setCode(tableName);
                        tableDefine.setTableName(tableName);
                        List indexList = tableDefine.getIndexs();
                        if (!CollectionUtils.isEmpty((Collection)indexList)) {
                            indexList.forEach(e -> e.setTitle(e.getTitle() + sharding));
                        }
                        tableDefineList.add(tableDefine);
                    }
                }
            });
            for (DefinitionTableV tableDefine : tableDefineList) {
                try {
                    DeployTableProcessor.newInstance((DefinitionTableV)tableDefine).deploy();
                }
                catch (Exception e) {
                    logger.error("\u8868\u7ed3\u6784\u521d\u59cb\u5316" + tableDefine.getTableName() + "\u5931\u8d25", e);
                }
            }
        }
    }

    @Override
    public void updatePeriod(OnlinePeriodDefineVO onlinePeriodDefineVO) {
        Assert.isNotNull((Object)onlinePeriodDefineVO);
        Assert.isNotEmpty((String)onlinePeriodDefineVO.getId());
        Integer minYear = this.getMinPeriodYear();
        this.checkExistsOnlinePeriodByCombCode(onlinePeriodDefineVO);
        OnlinePeriodDefineDO onlinePeriodDefineDO = (OnlinePeriodDefineDO)((Object)BeanCopyUtil.copyObj(OnlinePeriodDefineDO.class, (Object)onlinePeriodDefineVO));
        onlinePeriodDefineDO.setVer(System.currentTimeMillis());
        this.onlinePeriodDefineMapper.updateByPrimaryKey((Object)onlinePeriodDefineDO);
        int saveYear = Integer.parseInt(onlinePeriodDefineVO.getOnlinePeriod().substring(0, 4));
        String title = String.format("\u4fee\u6539-%1$d\u5e74%2$s\u6708", saveYear, onlinePeriodDefineVO.getOnlinePeriod().substring(5));
        LogHelper.info((String)DcFunctionModuleEnum.ONLINEPERIOD.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)onlinePeriodDefineVO));
        this.syncTableByYear(saveYear, minYear);
    }

    @Override
    public void deletePeriod(String id) {
        Assert.isNotEmpty((String)id);
        OnlinePeriodDefineDO onlinePeriodDefineDO = new OnlinePeriodDefineDO();
        onlinePeriodDefineDO.setId(id);
        OnlinePeriodDefineDO periodDefineDO = (OnlinePeriodDefineDO)((Object)this.onlinePeriodDefineMapper.selectOne((Object)onlinePeriodDefineDO));
        String title = String.format("\u5220\u9664-%1$s\u5e74%2$s\u6708", periodDefineDO.getOnlinePeriod().substring(0, 4), periodDefineDO.getOnlinePeriod().substring(5));
        LogHelper.info((String)DcFunctionModuleEnum.ONLINEPERIOD.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)((Object)periodDefineDO)));
        this.onlinePeriodDefineMapper.delete((Object)onlinePeriodDefineDO);
    }

    @Override
    public OnlinePeriodDefineVO getPeriodDataById(String id) {
        Assert.isNotEmpty((String)id);
        OnlinePeriodDefineDO onlinePeriodDefineDO = new OnlinePeriodDefineDO();
        onlinePeriodDefineDO.setId(id);
        OnlinePeriodDefineDO periodDefineDO = (OnlinePeriodDefineDO)((Object)this.onlinePeriodDefineMapper.selectOne((Object)onlinePeriodDefineDO));
        if (periodDefineDO == null) {
            throw new IllegalArgumentException(String.format("\u4e0a\u7ebf\u671f\u95f4\u5b9a\u4e49\u67e5\u8be2\u53c2\u6570\u9519\u8bef\uff0c%1$s\u4e0d\u5b58\u5728", id));
        }
        return (OnlinePeriodDefineVO)BeanCopyUtil.copyObj(OnlinePeriodDefineVO.class, (Object)((Object)periodDefineDO));
    }

    @Override
    public Integer getMinPeriodYear() {
        String minPeriod = this.onlinePeriodDefineMapper.getMinPeriod(new OnlinePeriodDefineDO());
        if (!StringUtils.isEmpty((String)minPeriod)) {
            return Integer.valueOf(minPeriod.substring(0, 4));
        }
        return null;
    }
}

