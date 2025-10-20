/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.conversion.conversionrate.vo.CommonOptionVO
 *  com.jiuqi.gcreport.conversion.conversionrate.vo.ConversionRateGroupVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.conversion.conversionrate.service.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.conversion.conversionrate.dao.ConversionRateGroupDao;
import com.jiuqi.gcreport.conversion.conversionrate.entity.ConversionRateGroupEO;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateGroupService;
import com.jiuqi.gcreport.conversion.conversionrate.vo.CommonOptionVO;
import com.jiuqi.gcreport.conversion.conversionrate.vo.ConversionRateGroupVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConversionRateGroupServiceImpl
implements ConversionRateGroupService {
    @Autowired
    private ConversionRateGroupDao dao;

    @Override
    public ConversionRateGroupVO save(ConversionRateGroupVO vo) {
        ConversionRateGroupEO eo = this.convertVO2EO(vo);
        return this.convertEO2VO(this.doSave(eo));
    }

    @Override
    public ConversionRateGroupVO update(ConversionRateGroupVO vo) {
        ConversionRateGroupEO eo = this.convertVO2EO(vo);
        return this.convertEO2VO(this.doUpdate(eo));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ConversionRateGroupVO delete(String id) {
        ConversionRateGroupEO eo = (ConversionRateGroupEO)this.dao.get((Serializable)((Object)id));
        this.beforeDelete(eo);
        this.dao.delete((BaseEntity)eo);
        return this.convertEO2VO(eo);
    }

    @Override
    public ConversionRateGroupVO get(String id) {
        ConversionRateGroupEO eo = (ConversionRateGroupEO)this.dao.get((Serializable)((Object)id));
        if (eo != null) {
            return this.convertEO2VO(eo);
        }
        return null;
    }

    @Override
    public List<ConversionRateGroupVO> queryAll() {
        return this.convertEO2VO(this.dao.loadAll());
    }

    @Override
    public void beforeSave(ConversionRateGroupEO eo) {
        if (eo.getId() == null) {
            eo.setId(UUIDUtils.newUUIDStr());
        }
        eo.setCreateTime(new Date());
    }

    @Override
    public void beforeUpdate(ConversionRateGroupEO eo) {
        eo.setUpdateTime(new Date());
    }

    @Override
    public void beforeDelete(ConversionRateGroupEO eo) {
    }

    @Override
    public ConversionRateGroupEO convertVO2EO(ConversionRateGroupVO vo) {
        ConversionRateGroupEO eo = new ConversionRateGroupEO();
        BeanUtils.copyProperties(vo, (Object)eo);
        return eo;
    }

    @Override
    public ConversionRateGroupVO convertEO2VO(ConversionRateGroupEO eo) {
        ConversionRateGroupVO vo = new ConversionRateGroupVO();
        BeanUtils.copyProperties((Object)eo, vo);
        return vo;
    }

    @Override
    private List<ConversionRateGroupVO> convertEO2VO(List<ConversionRateGroupEO> eoList) {
        ArrayList<ConversionRateGroupVO> resultList = null;
        if (eoList != null && eoList.size() > 0) {
            resultList = new ArrayList<ConversionRateGroupVO>();
            for (ConversionRateGroupEO item : eoList) {
                resultList.add(this.convertEO2VO(item));
            }
        }
        return resultList;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ConversionRateGroupVO> deleteByPeriod(String periodId, String systemId) {
        List<ConversionRateGroupVO> dataList = this.queryByPeriod(periodId, systemId);
        this.dao.deleteByPeriod(periodId, systemId);
        return dataList;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ConversionRateGroupVO> deleteBySystem(String systemId) {
        this.dao.deleteBySystem(systemId);
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ConversionRateGroupEO doSave(ConversionRateGroupEO eo) {
        this.beforeSave(eo);
        if (this.dao.get(eo.getPeriodId(), eo.getGroupName(), eo.getSystemId()) == null) {
            this.dao.save(eo);
        } else {
            this.dao.update((BaseEntity)eo);
        }
        return eo;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ConversionRateGroupEO doUpdate(ConversionRateGroupEO eo) {
        this.beforeUpdate(eo);
        this.dao.update((BaseEntity)eo);
        return eo;
    }

    @Override
    public List<CommonOptionVO> getGroups(String periodId, String systemId) {
        ArrayList<CommonOptionVO> resultList = new ArrayList<CommonOptionVO>();
        if (periodId != null && periodId.trim().length() > 0 && !"undefined".equals(periodId)) {
            Map<String, String> groupsMap = this.dao.queryGroups(periodId, systemId);
            if (groupsMap != null) {
                if (!groupsMap.containsValue("\u9ed8\u8ba4\u5206\u7ec4")) {
                    ConversionRateGroupEO defaultEO = this.createDefaultGroup(periodId, systemId);
                    groupsMap.put(defaultEO.getId(), defaultEO.getGroupName());
                }
                Set<String> keySet = groupsMap.keySet();
                for (String tempID : keySet) {
                    CommonOptionVO tmpVo = new CommonOptionVO();
                    tmpVo.setId(tempID);
                    tmpVo.setTitle(groupsMap.get(tempID));
                    resultList.add(tmpVo);
                }
            } else {
                ConversionRateGroupEO defaultEO = this.createDefaultGroup(periodId, systemId);
                CommonOptionVO tmpVo = new CommonOptionVO();
                tmpVo.setId(defaultEO.getId());
                tmpVo.setTitle(defaultEO.getGroupName());
                resultList.add(tmpVo);
            }
        }
        return resultList;
    }

    private ConversionRateGroupEO createDefaultGroup(String periodId, String systemId) {
        ConversionRateGroupEO eo = new ConversionRateGroupEO();
        eo.setPeriodId(String.valueOf(periodId));
        eo.setSystemId(systemId);
        eo.setGroupName("\u9ed8\u8ba4\u5206\u7ec4");
        eo.setDescription("\u81ea\u52a8\u751f\u6210\u7684\u5206\u7ec4");
        return this.doSave(eo);
    }

    @Override
    public Map<String, String> getGroupsMap(String periodId, String systemId) {
        return this.dao.queryGroups(periodId, systemId);
    }

    @Override
    public List<String> getPeriods(String systemId) {
        List<String> resultList = this.dao.queryPeriodList(systemId);
        if (resultList != null && resultList.size() > 1) {
            resultList.sort(new Comparator<String>(){

                @Override
                public int compare(String o1, String o2) {
                    if (o1 != null && o2 != null) {
                        return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
                    }
                    return 0;
                }
            });
        }
        return resultList;
    }

    @Override
    public ConversionRateGroupVO save(String periodId, String groupName, String systemId) {
        ConversionRateGroupEO eo = new ConversionRateGroupEO();
        eo.setPeriodId(periodId);
        eo.setGroupName(groupName);
        eo.setSystemId(systemId);
        return this.convertEO2VO(this.doSave(eo));
    }

    @Override
    public ConversionRateGroupVO get(String periodId, String groupName, String systemId) {
        ConversionRateGroupEO eo = this.dao.get(periodId, groupName, systemId);
        if (eo != null) {
            return this.convertEO2VO(eo);
        }
        return null;
    }

    @Override
    public List<ConversionRateGroupVO> queryByPeriod(String periodId, String systemId) {
        return this.convertEO2VO(this.dao.queryByPeriod(periodId, systemId));
    }

    @Override
    public List<ConversionRateGroupVO> queryBySystem(String systemId) {
        return this.convertEO2VO(this.dao.queryBySystem(systemId));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateGroupName(String id, String groupName) {
        this.dao.updateGroupName(id, groupName);
    }

    @Override
    public List<ConversionRateGroupVO> queryByIds(List<String> idList) {
        return this.convertEO2VO(this.dao.queryByIds(idList));
    }
}

