/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.mapping2.bean.PeriodMapping
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 *  com.jiuqi.nr.mapping2.service.PeriodMappingService
 *  com.jiuqi.nr.mapping2.service.ZBMappingService
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.PeriodMapingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo
 */
package nr.midstore2.data.param.internal;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.service.PeriodMappingService;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.PeriodMapingInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo;
import java.util.List;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import nr.midstore2.data.param.IReportMidstoreMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstoreMappingServiceImpl
implements IReportMidstoreMappingService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstoreMappingServiceImpl.class);
    @Autowired
    private ZBMappingService zbMappingService;
    @Autowired
    private PeriodMappingService periodMapingService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;

    @Override
    public void initZbMapping(ReportMidstoreContext context) {
        if (StringUtils.isNotEmpty((String)context.getConfigKey())) {
            List zbMappingList = this.zbMappingService.findByMS(context.getConfigKey());
            for (ZBMapping zbMapping : zbMappingList) {
                DataField field;
                if (!StringUtils.isNotEmpty((String)zbMapping.getMapping())) continue;
                String fieldKey = null;
                DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(zbMapping.getTable());
                if (dataTable != null && (field = this.dataSchemeSevice.getDataFieldByTableKeyAndCode(dataTable.getKey(), zbMapping.getZbCode())) != null) {
                    fieldKey = field.getKey();
                }
                if (StringUtils.isEmpty(fieldKey) || StringUtils.isEmpty((String)zbMapping.getMapping())) {
                    logger.info("\u65e0\u6548\u6620\u5c04\u5173\u7cfb\uff1a" + zbMapping.getTable() + "," + zbMapping.getZbCode() + "," + zbMapping.getMapping());
                    continue;
                }
                String mappingZBCode = zbMapping.getMapping();
                int id1 = mappingZBCode.indexOf("[");
                int id2 = mappingZBCode.indexOf("]");
                String mappingTableCode = "";
                if (id1 > 0 && id2 > 0 && id2 > id1) {
                    mappingTableCode = mappingZBCode.substring(0, id1);
                    mappingZBCode = mappingZBCode.substring(id1 + 1, id2);
                }
                ZBMappingInfo info = new ZBMappingInfo((Object)zbMapping);
                info.setFieldKey(fieldKey);
                info.setZbCode(zbMapping.getZbCode());
                info.setTable(zbMapping.getTable());
                info.setZbMapping((Object)zbMapping);
                info.setMapingTable(mappingTableCode);
                info.setMappingzb(mappingZBCode);
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
    public void initPeriodMapping(ReportMidstoreContext context) {
        if (StringUtils.isNotEmpty((String)context.getConfigKey())) {
            List periodMappingList = this.periodMapingService.findByMS(context.getConfigKey());
            for (PeriodMapping periodMapping : periodMappingList) {
                if (!StringUtils.isNotEmpty((String)periodMapping.getMapping()) || !StringUtils.isNotEmpty((String)periodMapping.getPeriod())) continue;
                PeriodMapingInfo info = new PeriodMapingInfo((Object)periodMapping);
                info.setPeriodCode(periodMapping.getPeriod());
                info.setPeriodMapCode(periodMapping.getMapping());
                context.getMappingCache().getPeriodMappingInfos().put(info.getPeriodCode(), info);
                context.getMappingCache().getSrcPeriodMappingInfos().put(info.getPeriodMapCode(), info);
            }
        }
    }
}

