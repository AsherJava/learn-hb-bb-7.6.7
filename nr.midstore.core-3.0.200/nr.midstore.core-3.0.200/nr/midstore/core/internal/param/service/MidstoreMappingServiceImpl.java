/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nr.mapping.bean.BaseDataMapping
 *  com.jiuqi.nr.mapping.bean.PeriodMapping
 *  com.jiuqi.nr.mapping.bean.UnitMapping
 *  com.jiuqi.nr.mapping.bean.ZBMapping
 *  com.jiuqi.nr.mapping.service.BaseDataMappingService
 *  com.jiuqi.nr.mapping.service.PeriodMappingService
 *  com.jiuqi.nr.mapping.service.UnitMappingService
 *  com.jiuqi.nr.mapping.service.ZBMappingService
 *  com.jiuqi.nr.mapping.web.vo.BaseDataVO
 */
package nr.midstore.core.internal.param.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nr.mapping.bean.BaseDataMapping;
import com.jiuqi.nr.mapping.bean.PeriodMapping;
import com.jiuqi.nr.mapping.bean.UnitMapping;
import com.jiuqi.nr.mapping.bean.ZBMapping;
import com.jiuqi.nr.mapping.service.BaseDataMappingService;
import com.jiuqi.nr.mapping.service.PeriodMappingService;
import com.jiuqi.nr.mapping.service.UnitMappingService;
import com.jiuqi.nr.mapping.service.ZBMappingService;
import com.jiuqi.nr.mapping.web.vo.BaseDataVO;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.bean.mapping.EnumItemMappingInfo;
import nr.midstore.core.definition.bean.mapping.EnumMappingInfo;
import nr.midstore.core.definition.bean.mapping.PeriodMapingInfo;
import nr.midstore.core.definition.bean.mapping.UnitMappingInfo;
import nr.midstore.core.definition.bean.mapping.ZBMappingInfo;
import nr.midstore.core.param.service.IMidstoreMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreMappingServiceImpl
implements IMidstoreMappingService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreMappingServiceImpl.class);
    @Autowired
    private UnitMappingService unitMappingService;
    @Autowired
    private ZBMappingService zbMappingService;
    @Autowired
    private PeriodMappingService periodMapingService;
    @Autowired
    private BaseDataMappingService baseDataMappingService;

    @Override
    public boolean checkMaping(MidstoreContext context, MidstoreResultObject result) {
        if (StringUtils.isNotEmpty((String)context.getConfigKey())) {
            List unitMappingList = this.unitMappingService.findByMS(context.getConfigKey());
            HashSet<String> repeatUnitCodes = new HashSet<String>();
            HashSet<String> repeatSrcUnitCodes = new HashSet<String>();
            for (UnitMapping unitMapping : unitMappingList) {
                if (!StringUtils.isNotEmpty((String)unitMapping.getMapping())) continue;
                if (repeatSrcUnitCodes.contains(unitMapping.getMapping())) {
                    result.setMessage("\u6620\u5c04\u7684\u76ee\u6807\u5355\u4f4d\u5b58\u5728\u91cd\u7801\uff1a" + unitMapping.getMapping());
                    result.setSuccess(false);
                    return false;
                }
                repeatSrcUnitCodes.add(unitMapping.getMapping());
                if (!repeatUnitCodes.contains(unitMapping.getUnitCode())) {
                    repeatUnitCodes.add(unitMapping.getMapping());
                    continue;
                }
                result.setMessage("\u6620\u5c04\u7684\u76ee\u6807\u5355\u4f4d\u5b58\u5728\u91cd\u7801\uff1a" + unitMapping.getUnitCode());
                result.setSuccess(false);
                return false;
            }
        }
        return true;
    }

    @Override
    public void initOrgMapping(MidstoreContext context) {
        if (StringUtils.isNotEmpty((String)context.getConfigKey())) {
            List unitMappingList = this.unitMappingService.findByMS(context.getConfigKey());
            for (UnitMapping unitMapping : unitMappingList) {
                if (!StringUtils.isNotEmpty((String)unitMapping.getMapping())) continue;
                UnitMappingInfo info = new UnitMappingInfo(unitMapping);
                context.getMappingCache().getUnitMappingInfos().put(info.getUnitMapping().getUnitCode(), info);
                context.getMappingCache().getSrcUnitMappingInfos().put(info.getUnitMapping().getMapping(), info);
            }
        }
    }

    @Override
    public void initZbMapping(MidstoreContext context) {
        if (StringUtils.isNotEmpty((String)context.getConfigKey())) {
            List zbMappingList = this.zbMappingService.findByMS(context.getConfigKey());
            for (ZBMapping zbMapping : zbMappingList) {
                if (!StringUtils.isNotEmpty((String)zbMapping.getMapping())) continue;
                ZBMappingInfo info = new ZBMappingInfo(zbMapping);
                String code = String.format("%s[%s]", zbMapping.getTable(), zbMapping.getZbCode());
                context.getMappingCache().getZbMapingInfos().put(code, info);
                context.getMappingCache().getZbMapingInfosOld().put(zbMapping.getZbCode(), info);
                String mappingZb = info.getMappingzb();
                int id = info.getMappingzb().indexOf("[");
                if (id < 0) {
                    mappingZb = String.format("%s[%s]", zbMapping.getTable(), mappingZb);
                }
                context.getMappingCache().getSrcZbMapingInfos().put(mappingZb, info);
                context.getMappingCache().getSrcZbMapingInfosOld().put(info.getMappingzb(), info);
            }
        }
    }

    @Override
    public void initEnumMapping(MidstoreContext context) {
        List baseDataMapingList;
        if (StringUtils.isNotEmpty((String)context.getConfigKey()) && (baseDataMapingList = this.baseDataMappingService.getBaseDataMapping(context.getConfigKey())) != null) {
            for (BaseDataMapping baseDataMaping : baseDataMapingList) {
                if (context.getMappingCache().getEnumMapingInfos().containsKey(baseDataMaping.getBaseDataCode())) continue;
                EnumMappingInfo baseMapInfo = new EnumMappingInfo(baseDataMaping);
                List list = this.baseDataMappingService.getBaseDataItemMapping(context.getConfigKey(), baseDataMaping.getBaseDataCode());
                for (BaseDataItemMapping item : list) {
                    EnumItemMappingInfo newItem = new EnumItemMappingInfo(item);
                    baseMapInfo.add(newItem);
                }
                context.getMappingCache().getEnumMapingInfos().put(baseDataMaping.getBaseDataCode(), baseMapInfo);
            }
        }
    }

    @Override
    public Map<String, BaseDataVO> getBaseDataItemMapping(MidstoreContext context, String baseName) {
        List BaseDataMappingList;
        Map<String, Object> result = null;
        result = StringUtils.isNotEmpty((String)context.getConfigKey()) ? ((BaseDataMappingList = this.baseDataMappingService.getBaseDataItem(context.getConfigKey(), baseName)) != null ? BaseDataMappingList.stream().collect(Collectors.toMap(BaseDataVO::getCode, BaseDataVO2 -> BaseDataVO2)) : new HashMap()) : new HashMap<String, BaseDataVO>();
        return result;
    }

    @Override
    public String getMapFieldCode(String zbMapCode) {
        String zbCode = zbMapCode;
        if (StringUtils.isNotEmpty((String)zbMapCode)) {
            int id1 = zbMapCode.indexOf("[");
            int id2 = zbMapCode.indexOf("]");
            if (id1 > 0 && id2 > id1) {
                zbCode = zbMapCode.substring(id1 + 1, id2);
            }
        }
        return zbCode;
    }

    @Override
    public String getMapTableCode(String zbMapCode) {
        String tableCode = "";
        int id1 = zbMapCode.indexOf("[");
        int id2 = zbMapCode.indexOf("]");
        if (id1 > 0 && id2 > id1) {
            tableCode = zbMapCode.substring(0, id1);
        }
        return tableCode;
    }

    @Override
    public void initPeriodMapping(MidstoreContext context) {
        if (StringUtils.isNotEmpty((String)context.getConfigKey())) {
            List periodMappingList = this.periodMapingService.findByMS(context.getConfigKey());
            for (PeriodMapping periodMapping : periodMappingList) {
                if (!StringUtils.isNotEmpty((String)periodMapping.getMapping()) || !StringUtils.isNotEmpty((String)periodMapping.getPeriod())) continue;
                PeriodMapingInfo info = new PeriodMapingInfo(periodMapping);
                context.getMappingCache().getPeriodMappingInfos().put(info.getPeriodCode(), info);
                context.getMappingCache().getSrcPeriodMappingInfos().put(info.getPeriodMapCode(), info);
            }
        }
    }
}

