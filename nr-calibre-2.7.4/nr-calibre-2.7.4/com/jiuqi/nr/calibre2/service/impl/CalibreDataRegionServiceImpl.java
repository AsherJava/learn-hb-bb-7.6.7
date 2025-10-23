/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IMainDimFilter
 */
package com.jiuqi.nr.calibre2.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMainDimFilter;
import com.jiuqi.nr.calibre2.ICalibreDataRegionService;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.common.CalibreDataOption;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDataRegion;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDO;
import com.jiuqi.nr.calibre2.internal.dao.ICalibreSubListDao;
import com.jiuqi.nr.calibre2.internal.domain.CalibreSubListDO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalibreDataRegionServiceImpl
implements ICalibreDataRegionService {
    @Autowired
    private ICalibreDataService calibreDataService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private ICalibreSubListDao subListDao;

    @Override
    public List<CalibreDataRegion> getList(ExecutorContext executorContext, CalibreDefineDO calibreDefine, DimensionValueSet masterKeys) throws Exception {
        return this.getList(executorContext, calibreDefine, masterKeys, null);
    }

    @Override
    public List<CalibreDataRegion> getList(ExecutorContext executorContext, CalibreDefineDO calibreDefine, DimensionValueSet masterKeys, TempAssistantTable tempAssistantTable) throws Exception {
        List<CalibreDataDO> allCalibreDatas = this.getAllCalibreDatas(calibreDefine);
        ArrayList<CalibreDataRegion> list = new ArrayList<CalibreDataRegion>(allCalibreDatas.size());
        if (calibreDefine.getType() == 0) {
            for (CalibreDataDO calibreData : allCalibreDatas) {
                CalibreDataRegion region = new CalibreDataRegion(calibreData);
                list.add(region);
                List<String> entityKeys = this.queryEntities(calibreDefine.getCode(), calibreData.getCode());
                if (entityKeys == null) continue;
                region.getEntityKeys().addAll(entityKeys);
            }
        } else {
            HashMap<String, CalibreDataRegion> regions = new HashMap<String, CalibreDataRegion>();
            ArrayList<Formula> calibreFormulas = new ArrayList<Formula>();
            int index = 0;
            for (CalibreDataDO calibreData : allCalibreDatas) {
                CalibreDataRegion region = this.buildDataRegion(calibreDefine, list, regions, calibreFormulas, calibreData);
                region.setIndex(index);
                ++index;
            }
            IMainDimFilter mainDimFilter = this.dataAccessProvider.newMainDimFilter();
            Map result = mainDimFilter.filterByFormulas(executorContext, masterKeys, calibreFormulas, tempAssistantTable);
            for (String id : result.keySet()) {
                CalibreDataRegion region = (CalibreDataRegion)regions.get(id);
                List values = (List)result.get(id);
                region.getEntityKeys().addAll(values);
            }
            for (CalibreDataRegion region : regions.values()) {
                region.mergeEntityKeys();
            }
            Collections.sort(list);
        }
        return list;
    }

    protected CalibreDataRegion buildDataRegion(CalibreDefineDO calibreDefine, List<CalibreDataRegion> list, Map<String, CalibreDataRegion> regions, List<Formula> calibreFormulas, CalibreDataDO calibreData) {
        CalibreDataRegion region = regions.get(calibreData.getKey());
        if (region == null) {
            String expression;
            region = new CalibreDataRegion(calibreData);
            regions.put(calibreData.getKey(), region);
            list.add(region);
            if (calibreData.getValue().getSum().booleanValue()) {
                CalibreDataDTO byParentDTO = CalibreDataDTO.getInstance(calibreData);
                byParentDTO.setDefineKey(calibreDefine.getKey());
                byParentDTO.setDataTreeType(CalibreDataOption.DataTreeType.DIRECT_CHILDREN);
                Result<List<CalibreDataDTO>> childs = this.calibreDataService.list(byParentDTO);
                for (CalibreDataDTO childCalibreData : childs.getData()) {
                    CalibreDataRegion childRegion = this.buildDataRegion(calibreDefine, list, regions, calibreFormulas, childCalibreData);
                    region.getChildRegions().add(childRegion);
                }
            }
            if (StringUtils.isNotEmpty((String)(expression = (String)calibreData.getValue().getExpression()))) {
                Formula formula = new Formula();
                formula.setReportName(calibreDefine.getName());
                formula.setFormKey(calibreDefine.getKey());
                formula.setId(calibreData.getKey());
                formula.setCode(calibreData.getCode());
                formula.setFormula(expression);
                calibreFormulas.add(formula);
            }
        }
        return region;
    }

    private List<String> queryEntities(String calibreCode, String code) {
        CalibreSubListDO subList = new CalibreSubListDO();
        subList.setCode(code);
        subList.setCalibreCode(calibreCode);
        List<CalibreSubListDO> query = this.subListDao.query(subList);
        return query.stream().map(CalibreSubListDO::getValue).collect(Collectors.toList());
    }

    private List<CalibreDataDO> getAllCalibreDatas(CalibreDefineDO calibreDefine) {
        ArrayList<CalibreDataDO> allCalibreDatas = new ArrayList<CalibreDataDO>();
        CalibreDataDTO byParentDTO = new CalibreDataDTO();
        byParentDTO.setCalibreCode(calibreDefine.getCode());
        byParentDTO.setDefineKey(calibreDefine.getKey());
        byParentDTO.setDataTreeType(CalibreDataOption.DataTreeType.ROOT);
        Result<List<CalibreDataDTO>> DOresult = this.calibreDataService.list(byParentDTO);
        List<CalibreDataDTO> roots = DOresult.getData();
        this.getCalibreDatas(allCalibreDatas, roots);
        return allCalibreDatas;
    }

    private void getCalibreDatas(List<CalibreDataDO> allCalibreDatas, List<CalibreDataDTO> list) {
        for (CalibreDataDTO calibreData : list) {
            allCalibreDatas.add(calibreData);
            CalibreDataDTO byParentDTO = CalibreDataDTO.getInstance(calibreData);
            byParentDTO.setDataTreeType(CalibreDataOption.DataTreeType.DIRECT_CHILDREN);
            Result<List<CalibreDataDTO>> DOresult = this.calibreDataService.list(byParentDTO);
            List<CalibreDataDTO> childs = DOresult.getData();
            if (childs == null || childs.size() <= 0) continue;
            this.getCalibreDatas(allCalibreDatas, childs);
        }
    }
}

