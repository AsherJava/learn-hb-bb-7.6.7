/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.financialcheckapi.checkconfig.vo.FinancialCheckConfigVO
 *  com.jiuqi.gcreport.financialcheckcore.checkconfig.dao.FinancialCheckConfigDao
 *  com.jiuqi.gcreport.financialcheckcore.checkconfig.entity.FinancialCheckConfigEO
 *  com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.OrgUtils
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.financialcheckImpl.checkconfig.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.service.FinancialCheckConfigService;
import com.jiuqi.gcreport.financialcheckapi.checkconfig.vo.FinancialCheckConfigVO;
import com.jiuqi.gcreport.financialcheckcore.checkconfig.dao.FinancialCheckConfigDao;
import com.jiuqi.gcreport.financialcheckcore.checkconfig.entity.FinancialCheckConfigEO;
import com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.OrgUtils;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialCheckConfigServiceImpl
implements FinancialCheckConfigService {
    private static final Logger logger = LoggerFactory.getLogger(FinancialCheckConfigServiceImpl.class);
    private final String CACHE_NAME = "FinancialCheckConfigCache";
    @Autowired
    private FinancialCheckConfigDao financialCheckConfigDao;
    @Autowired
    private NedisCacheManager cacheManger;

    @Override
    public void save(FinancialCheckConfigVO financialCheckConfigVO) {
        Map options;
        FinancialCheckConfigEO financialCheckConfigEO = new FinancialCheckConfigEO();
        financialCheckConfigEO.setCheckWay(financialCheckConfigVO.getCheckWay());
        financialCheckConfigEO.setOrgType(financialCheckConfigVO.getOrgType());
        financialCheckConfigEO.setDataSource(financialCheckConfigVO.getDataSource());
        if (!CollectionUtils.isEmpty((Collection)financialCheckConfigVO.getOrgRange())) {
            List unitCodeOnlyParent = OrgUtils.getBaseUnitCodeOnlyParent((List)financialCheckConfigVO.getOrgRange());
            financialCheckConfigEO.setOrgRange(String.join((CharSequence)",", unitCodeOnlyParent));
        }
        if (!CollectionUtils.isEmpty((Collection)financialCheckConfigVO.getSubjectRange())) {
            List baseDataCodeOnlyParent = BaseDataUtils.getBaseDataCodeOnlyParent((List)financialCheckConfigVO.getSubjectRange(), (String)"MD_ACCTSUBJECT");
            financialCheckConfigEO.setSubjectRange(String.join((CharSequence)",", baseDataCodeOnlyParent));
        }
        if (Objects.isNull(options = financialCheckConfigVO.getOptions())) {
            financialCheckConfigEO.setOptions("{}");
        } else {
            financialCheckConfigEO.setOptions(JsonUtils.writeValueAsString((Object)options));
        }
        List financialCheckConfigEOS = this.financialCheckConfigDao.selectList((BaseEntity)new FinancialCheckConfigEO());
        if (!CollectionUtils.isEmpty((Collection)financialCheckConfigEOS)) {
            financialCheckConfigEO.setId(((FinancialCheckConfigEO)financialCheckConfigEOS.get(0)).getId());
            financialCheckConfigEO.setUpdateTime(new Date());
            financialCheckConfigEO.setCreateTime(((FinancialCheckConfigEO)financialCheckConfigEOS.get(0)).getCreateTime());
            financialCheckConfigEO.setCreator(((FinancialCheckConfigEO)financialCheckConfigEOS.get(0)).getCreator());
            this.financialCheckConfigDao.update((BaseEntity)financialCheckConfigEO);
        } else {
            financialCheckConfigEO.setId(UUIDUtils.newUUIDStr());
            financialCheckConfigEO.setCreator(NpContextHolder.getContext().getUserName());
            financialCheckConfigEO.setCreateTime(new Date());
            financialCheckConfigEO.setUpdateTime(financialCheckConfigEO.getCreateTime());
            this.financialCheckConfigDao.add((BaseEntity)financialCheckConfigEO);
        }
        this.cacheManger.getCache("FinancialCheckConfigCache").evict("FinancialCheckConfigCache");
    }

    @Override
    public FinancialCheckConfigVO query() {
        return (FinancialCheckConfigVO)this.cacheManger.getCache("FinancialCheckConfigCache").get("FinancialCheckConfigCache", () -> {
            List financialCheckConfigEOS = this.financialCheckConfigDao.selectList((BaseEntity)new FinancialCheckConfigEO());
            if (!CollectionUtils.isEmpty((Collection)financialCheckConfigEOS)) {
                return this.convert2VO((FinancialCheckConfigEO)financialCheckConfigEOS.get(0));
            }
            return new FinancialCheckConfigVO();
        });
    }

    private FinancialCheckConfigVO convert2VO(FinancialCheckConfigEO financialCheckConfigEO) {
        String options;
        HashMap<String, String> orgMap;
        FinancialCheckConfigVO financialCheckConfigVO = new FinancialCheckConfigVO();
        BeanUtils.copyProperties(financialCheckConfigEO, financialCheckConfigVO);
        if (!StringUtils.isEmpty((String)financialCheckConfigEO.getOrgRange())) {
            String[] orgCodes = financialCheckConfigEO.getOrgRange().split(",");
            ArrayList<String> orgCodeList = new ArrayList<String>(orgCodes.length);
            ArrayList orgMapList = new ArrayList(orgCodes.length);
            for (String orgCode : orgCodes) {
                orgCodeList.add(orgCode);
                OrgToJsonVO orgByCode = GcOrgBaseTool.getInstance().getOrgByCode(orgCode);
                orgMap = new HashMap<String, String>();
                orgMap.put("code", orgCode);
                orgMap.put("title", Objects.isNull(orgByCode) ? orgCode : orgByCode.getTitle());
                orgMapList.add(orgMap);
            }
            financialCheckConfigVO.setOrgRange(orgCodeList);
            financialCheckConfigVO.setOrgRangeMap(orgMapList);
        }
        if (!StringUtils.isEmpty((String)financialCheckConfigEO.getSubjectRange())) {
            String[] subjectCodes = financialCheckConfigEO.getSubjectRange().split(",");
            ArrayList<String> subjectCodeList = new ArrayList<String>(subjectCodes.length);
            ArrayList subjectMapList = new ArrayList(subjectCodes.length);
            for (String subjectCode : subjectCodes) {
                subjectCodeList.add(subjectCode);
                GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", subjectCode);
                orgMap = new HashMap();
                orgMap.put("code", subjectCode);
                orgMap.put("title", Objects.isNull(gcBaseData) ? subjectCode : gcBaseData.getTitle());
                subjectMapList.add(orgMap);
            }
            financialCheckConfigVO.setSubjectRange(subjectCodeList);
            financialCheckConfigVO.setSubjectRangeMap(subjectMapList);
        }
        if (!StringUtils.isEmpty((String)(options = financialCheckConfigEO.getOptions()))) {
            Map optionsMap = (Map)JsonUtils.readValue((String)options, HashMap.class);
            financialCheckConfigVO.setOptions(optionsMap);
        }
        return financialCheckConfigVO;
    }
}

