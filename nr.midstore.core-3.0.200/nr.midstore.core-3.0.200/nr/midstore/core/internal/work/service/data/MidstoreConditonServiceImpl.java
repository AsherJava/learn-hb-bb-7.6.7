/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.internal.work.service.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.mapping.UnitMappingInfo;
import nr.midstore.core.work.service.data.IMidstoreConditonService;
import org.springframework.stereotype.Service;

@Service
public class MidstoreConditonServiceImpl
implements IMidstoreConditonService {
    @Override
    public String getCondtionSQl(String dePeriodCode) {
        return "DATATIME = '" + dePeriodCode + "'";
    }

    @Override
    public String getCondtionSQl(MidstoreContext context, String dePeriodCode) {
        List<List<String>> list;
        String contition = String.format("DATATIME = '%s'", dePeriodCode);
        if (context.getExchangeEnityCodes().size() > 0 && context.getExchangeEnityCodes().size() <= 500 && !(list = this.getDeEntityCodes(context, 500)).isEmpty()) {
            List<String> deEntityCodes = list.get(0);
            return this.getCondtionSQl(deEntityCodes, dePeriodCode);
        }
        return contition;
    }

    @Override
    public List<List<String>> getDeEntityCodes(MidstoreContext context, int splictCount) {
        ArrayList<List<String>> list = new ArrayList<List<String>>();
        if (context.getExchangeEnityCodes().size() > 0) {
            List<String> allDeEntityCodes = this.getDeEntityCodes(context);
            ArrayList<String> deEntityCodes = new ArrayList<String>();
            for (String deOrgCode2 : allDeEntityCodes) {
                deEntityCodes.add(deOrgCode2);
                if (deEntityCodes.size() < splictCount) continue;
                list.add(deEntityCodes);
                deEntityCodes = new ArrayList();
            }
            if (deEntityCodes.size() > 0) {
                list.add(deEntityCodes);
            }
        }
        return list;
    }

    @Override
    public List<String> getDeEntityCodes(MidstoreContext context) {
        ArrayList<String> deEntityCodes = new ArrayList<String>();
        if (context.getExchangeEnityCodes().size() > 0) {
            Iterator<String> iterator = context.getExchangeEnityCodes().iterator();
            while (iterator.hasNext()) {
                String nrOrgCode2;
                String deOrgCode2 = nrOrgCode2 = iterator.next();
                if (context.getMappingCache().getUnitMappingInfos().containsKey(nrOrgCode2)) {
                    UnitMappingInfo unitInfo = context.getMappingCache().getUnitMappingInfos().get(nrOrgCode2);
                    deOrgCode2 = unitInfo.getUnitMapingCode();
                }
                deEntityCodes.add(deOrgCode2);
            }
        }
        return deEntityCodes;
    }

    @Override
    public String getCondtionSQl(List<String> entityDeCodes, String dePeriodCode) {
        String contition = String.format("DATATIME = '%s'", dePeriodCode);
        if (entityDeCodes != null && entityDeCodes.size() > 0 && entityDeCodes.size() < 10000) {
            StringBuilder sp = new StringBuilder();
            sp.append("MDCODE in (");
            for (String deOrgCode : entityDeCodes) {
                sp.append("'" + deOrgCode + "',");
            }
            sp.deleteCharAt(sp.length() - 1);
            sp.append(" )");
            contition = contition + " and " + sp.toString();
        }
        return contition;
    }
}

