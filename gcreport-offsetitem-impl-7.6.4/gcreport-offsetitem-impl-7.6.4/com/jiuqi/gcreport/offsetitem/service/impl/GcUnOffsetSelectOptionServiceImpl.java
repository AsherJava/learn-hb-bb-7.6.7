/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum
 *  com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum
 *  com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcUnOffsetOptionVo
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.dao.GcUnOffsetSelectOptionDao;
import com.jiuqi.gcreport.offsetitem.entity.GcUnOffsetSelectOptionEO;
import com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum;
import com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum;
import com.jiuqi.gcreport.offsetitem.i18n.UnOffsetSelectOptionI18Const;
import com.jiuqi.gcreport.offsetitem.service.GcUnOffsetSelectOptionService;
import com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO;
import com.jiuqi.gcreport.offsetitem.vo.GcUnOffsetOptionVo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class GcUnOffsetSelectOptionServiceImpl
implements GcUnOffsetSelectOptionService {
    @Autowired
    private GcUnOffsetSelectOptionDao gcUnOffsetSelectOptionDao;

    @Override
    public List<GcInputAdjustVO> queryContentById(String id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> listUnOffsetConfigDatas(String dataSource) {
        return this.gcUnOffsetSelectOptionDao.listUnOffsetConfigDatas(dataSource);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void exchangeSort(String id, String dataSource, String pageCode, int step) {
        GcUnOffsetSelectOptionEO unOffsetConfigData = this.gcUnOffsetSelectOptionDao.getUnOffsetConfigDataById(id);
        if (null == unOffsetConfigData) {
            return;
        }
        GcUnOffsetSelectOptionEO gcUnOffsetSelectOptionEO = step < 0 ? this.gcUnOffsetSelectOptionDao.getNextNodeByIdAndOrder(unOffsetConfigData.getOrdinal(), dataSource, pageCode) : this.gcUnOffsetSelectOptionDao.getPreNodeByIdAndOrder(unOffsetConfigData.getOrdinal(), dataSource, pageCode);
        if (null == gcUnOffsetSelectOptionEO) {
            throw new BusinessRuntimeException(step > 0 ? "\u4e0d\u80fd\u518d\u79fb\u4e86\uff0c\u5df2\u7ecf\u4e3a\u7b2c\u4e00\u6761\u4e86" : "\u4e0d\u80fd\u518d\u79fb\u4e86\uff0c\u5df2\u7ecf\u4e3a\u6700\u540e\u4e00\u6761\u4e86");
        }
        Integer tempSort = unOffsetConfigData.getOrdinal();
        unOffsetConfigData.setOrdinal(gcUnOffsetSelectOptionEO.getOrdinal());
        gcUnOffsetSelectOptionEO.setOrdinal(tempSort);
        this.gcUnOffsetSelectOptionDao.updateData(unOffsetConfigData);
        this.gcUnOffsetSelectOptionDao.updateData(gcUnOffsetSelectOptionEO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delete(GcUnOffsetOptionVo gcUnOffsetOptionVo) {
        GcUnOffsetSelectOptionEO gcUnOffsetSelectOptionEO = new GcUnOffsetSelectOptionEO();
        BeanUtils.copyProperties(gcUnOffsetOptionVo, (Object)gcUnOffsetSelectOptionEO);
        this.gcUnOffsetSelectOptionDao.deleteOfId(gcUnOffsetSelectOptionEO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(List<GcUnOffsetOptionVo> gcUnOffsetOptionVos) {
        ArrayList<GcUnOffsetSelectOptionEO> gcUnOffsetSelectOptionEOS = new ArrayList<GcUnOffsetSelectOptionEO>();
        for (GcUnOffsetOptionVo gcUnOffsetOptionVo : gcUnOffsetOptionVos) {
            GcUnOffsetSelectOptionEO gcUnOffsetSelectOptionEO = new GcUnOffsetSelectOptionEO();
            BeanUtils.copyProperties(gcUnOffsetOptionVo, (Object)gcUnOffsetSelectOptionEO);
            gcUnOffsetSelectOptionEO.setContent(gcUnOffsetOptionVo.getContent());
            if (gcUnOffsetOptionVo.getEffectRange() != null) {
                gcUnOffsetSelectOptionEO.setEffectRange(JsonUtils.writeValueAsString((Object)gcUnOffsetOptionVo.getEffectRange()));
            }
            gcUnOffsetSelectOptionEOS.add(gcUnOffsetSelectOptionEO);
        }
        this.gcUnOffsetSelectOptionDao.updateDataBatch(gcUnOffsetSelectOptionEOS);
    }

    @Override
    public List<Map<String, Object>> listSelectOptionForTab(String dataSource) {
        List<Map<String, Object>> unOffsetConfigDatas = this.gcUnOffsetSelectOptionDao.listUnOffsetConfigDatas(dataSource);
        HashMap<String, Object> unOffsetMap = new HashMap<String, Object>();
        HashMap<String, Object> unOffsetParentMap = new HashMap<String, Object>();
        ArrayList<Map<String, Object>> configDatas = new ArrayList<Map<String, Object>>();
        ArrayList unOffsetDatas = new ArrayList();
        ArrayList unOffsetParentDatas = new ArrayList();
        unOffsetConfigDatas.forEach(unOffsetConfigData -> {
            String effectrange = (String)unOffsetConfigData.get("EFFECTRANGE");
            HashMap<String, String> selectMap = new HashMap<String, String>();
            selectMap.put("id", (String)unOffsetConfigData.get("ID"));
            selectMap.put("value", (String)unOffsetConfigData.get("CODE"));
            String I18n = UnOffsetSelectOptionI18Const.getI18nForCode((String)unOffsetConfigData.get("CODE"));
            if (StringUtils.hasLength(I18n)) {
                selectMap.put("showType", GcI18nUtil.getMessage((String)I18n));
            } else {
                selectMap.put("showType", (String)unOffsetConfigData.get("TITLE"));
            }
            selectMap.put("isUnitTreeSort", String.valueOf(unOffsetConfigData.get("SORTTYPE")));
            selectMap.put("isShow", String.valueOf(1));
            selectMap.put("ordinal", unOffsetConfigData.get("ORDINAL").toString());
            if (effectrange.equals(TabSelectEnum.NOT_OFFSET_PAGE.getCode())) {
                unOffsetDatas.add(selectMap);
            }
            if (effectrange.equals(TabSelectEnum.NOT_OFFSET_PARENT_PAGE.getCode())) {
                unOffsetParentDatas.add(selectMap);
            }
        });
        unOffsetMap.put("title", TabSelectEnum.NOT_OFFSET_PAGE.getTitle());
        unOffsetMap.put("code", TabSelectEnum.NOT_OFFSET_PAGE.getCode());
        unOffsetMap.put("value", unOffsetDatas);
        unOffsetParentMap.put("title", TabSelectEnum.NOT_OFFSET_PARENT_PAGE.getTitle());
        unOffsetParentMap.put("code", TabSelectEnum.NOT_OFFSET_PARENT_PAGE.getCode());
        unOffsetParentMap.put("value", unOffsetParentDatas);
        configDatas.add(unOffsetMap);
        configDatas.add(unOffsetParentMap);
        return configDatas;
    }

    @Override
    public void saveSelectOption(Map<Object, List<Map<String, Object>>> selectOptions, String dataSource) {
        this.gcUnOffsetSelectOptionDao.saveSelectOption(selectOptions, dataSource);
    }

    @Override
    public String getCurDataSource() {
        String optValue = GcSystermOptionTool.getOptionValue((String)"FINANCIAL_CUBES_ENABLE");
        if ("1".equals(optValue)) {
            return this.gcUnOffsetSelectOptionDao.getCurDataSource();
        }
        return DataSourceEnum.INPUT_DATA.getCode();
    }
}

