/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.budget.masterdata.intf;

import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.masterdata.intf.MasterDataVersion;
import com.jiuqi.budget.masterdata.intf.OrgDataCenter;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component(value="BudOrgVersionUtil")
public class OrgVersionUtil {
    private final OrgDataCenter orgDataCenter;

    public static OrgVersionUtil getInstance() {
        return Holder.instance;
    }

    public OrgVersionUtil(OrgDataCenter orgDataCenter) {
        this.orgDataCenter = orgDataCenter;
    }

    public MasterDataVersion getOrgVersionByPeriod(String orgType, DataPeriod period) {
        List<MasterDataVersion> orgVersions;
        if ("".equals(orgType) || orgType == null) {
            orgType = "MD_ORG";
        }
        if ((orgVersions = this.orgDataCenter.listVersions(orgType)) == null || orgVersions.isEmpty()) {
            return null;
        }
        return this.getOrgVersionByPeriod(orgVersions, period);
    }

    public MasterDataVersion getOrgVersionByPeriod(List<? extends MasterDataVersion> orgVersions, DataPeriod period) {
        if (orgVersions == null || orgVersions.isEmpty()) {
            return null;
        }
        LocalDateTime localDateTime = period.toLocalDateTime();
        for (MasterDataVersion masterDataVersion : orgVersions) {
            LocalDateTime inValidTime = masterDataVersion.getInValidTime();
            LocalDateTime validTime = masterDataVersion.getValidTime();
            if (!localDateTime.equals(validTime) && !validTime.isBefore(localDateTime) || !inValidTime.isAfter(localDateTime)) continue;
            return masterDataVersion;
        }
        return null;
    }

    private static class Holder {
        static final OrgVersionUtil instance = (OrgVersionUtil)ApplicationContextRegister.getBean(OrgVersionUtil.class);

        private Holder() {
        }
    }
}

