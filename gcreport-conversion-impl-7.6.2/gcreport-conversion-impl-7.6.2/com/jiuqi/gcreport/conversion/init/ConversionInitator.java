/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.rate.client.vo.CommonRateSchemeVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemItemVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum
 *  com.jiuqi.gcreport.rate.impl.init.ConversionInit
 *  com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.conversion.init;

import com.jiuqi.common.rate.client.vo.CommonRateSchemeVO;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemItemDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.service.ConversionSystemService;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemItemVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum;
import com.jiuqi.gcreport.rate.impl.init.ConversionInit;
import com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversionInitator
implements ConversionInit {
    @Autowired
    private CommonRateSchemeService rateSchemeService;
    @Autowired
    private ConversionSystemService systemService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ConversionSystemItemDao itemDao;

    public void excute() {
        this.updateTaskSchemeSetting();
        this.updateIndex();
    }

    public void updateTaskSchemeSetting() {
        List vo = this.rateSchemeService.listAllRateScheme();
        Map<String, String> rateSchemeCodeVId = vo.stream().collect(Collectors.toMap(CommonRateSchemeVO::getId, CommonRateSchemeVO::getCode));
        Map<String, List<Map>> groupMap = EntNativeSqlDefaultDao.getInstance().selectMap(this.getQuerySql("GC_CONV_RATE_G"), new Object[0]).stream().collect(Collectors.groupingBy(v -> v.get("SYSTEMID").toString()));
        List<ConversionSystemTaskSchemeVO> taskSchemes = this.systemService.getSystemTaskSchemes();
        ArrayList<ConversionSystemTaskVO> updateVO = new ArrayList<ConversionSystemTaskVO>();
        block0: for (ConversionSystemTaskSchemeVO taskSchemeVO : taskSchemes) {
            ConversionSystemTaskVO taskVO = new ConversionSystemTaskVO();
            BeanUtils.copyProperties(taskSchemeVO, taskVO);
            taskVO.setId(taskSchemeVO.getSchemeTaskId());
            String systemId = taskSchemeVO.getSystemId();
            List<Map> groupList = groupMap.get(systemId);
            if (groupList == null) continue;
            String taskId = taskSchemeVO.getTaskId();
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskId);
            PeriodType periodType = taskDefine.getPeriodType();
            for (Map groupData : groupList) {
                int period = Integer.parseInt(groupData.get("PERIODID").toString());
                if (periodType.type() != period) continue;
                String rateSchemeCode = rateSchemeCodeVId.get(groupData.get("ID").toString());
                taskVO.setRateSchemeCode(rateSchemeCode);
                updateVO.add(taskVO);
                continue block0;
            }
        }
        if (updateVO.isEmpty()) {
            return;
        }
        this.systemService.saveTaskScheme(updateVO);
    }

    public void updateIndex() {
        List allItemEO = EntNativeSqlDefaultDao.newInstance((String)"GC_CONV_SYSTEM_ITEM", ConversionSystemItemEO.class).selectEntity(this.getQuerySql("GC_CONV_SYSTEM_ITEM"), new Object[0]);
        List<ConversionSystemTaskSchemeVO> taskSchemes = this.systemService.getSystemTaskSchemes();
        List taskSchemesIds = taskSchemes.stream().map(ConversionSystemTaskSchemeVO::getSchemeTaskId).collect(Collectors.toList());
        ArrayList<ConversionSystemItemEO> finalItemEO = new ArrayList<ConversionSystemItemEO>();
        for (ConversionSystemItemEO itemEO : allItemEO) {
            if (taskSchemesIds.contains(itemEO.getSchemeTaskId())) {
                finalItemEO.add(itemEO);
                continue;
            }
            this.itemDao.deleteById(itemEO.getId());
        }
        Map<String, List<ConversionSystemItemEO>> itemEOMap = finalItemEO.stream().collect(Collectors.groupingBy(ConversionSystemItemEO::getFormId));
        ArrayList<ConversionSystemItemVO> addItemVO = new ArrayList<ConversionSystemItemVO>();
        for (String formKey : itemEOMap.keySet()) {
            List<ConversionSystemItemEO> itemEOList = itemEOMap.get(formKey);
            String tasKSchemeId = itemEOList.get(0).getSchemeTaskId();
            Set settingIndexIds = itemEOList.stream().map(ConversionSystemItemEO::getIndexId).collect(Collectors.toSet());
            ArrayList<String> updateIndexIds = new ArrayList<String>();
            List regions = this.runTimeViewController.getAllRegionsInForm(formKey);
            for (DataRegionDefine region : regions) {
                List allIndexIds = this.runTimeViewController.getFieldKeysInRegion(region.getKey());
                for (String indexId : allIndexIds) {
                    if (settingIndexIds.contains(indexId) || updateIndexIds.contains(indexId)) continue;
                    updateIndexIds.add(indexId);
                    ConversionSystemItemVO itemVO = new ConversionSystemItemVO();
                    itemVO.setIndexId(indexId);
                    itemVO.setFormId(formKey);
                    itemVO.setRegionKey(region.getKey());
                    itemVO.setRateTypeCode(RateTypeEnum.COPY.getCode());
                    itemVO.setCreatetime(new Date());
                    addItemVO.add(itemVO);
                }
            }
        }
        this.systemService.batchSaveConversionSystemItemIndexInfo(addItemVO);
    }

    public String getQuerySql(String tableName) {
        return "select * from " + tableName;
    }
}

