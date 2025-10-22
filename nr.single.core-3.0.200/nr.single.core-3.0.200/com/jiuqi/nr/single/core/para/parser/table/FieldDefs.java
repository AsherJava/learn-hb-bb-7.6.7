/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.table;

import com.jiuqi.nr.single.core.para.parser.eoums.EnumInfo;
import com.jiuqi.nr.single.core.para.parser.table.AreaInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldDefs {
    private Map<Integer, EnumInfo> enumMap;
    private List<ZBInfo> zbs = new ArrayList<ZBInfo>();
    private List<String> zbNames;
    private List<ZBInfo> zbInfoNoZDNList;
    private Map<String, ZBInfo> zbInfosPair;
    private Map<String, ZBInfo> zbInfosPairByCRNum;
    private Map<String, ZBInfo> zbInfosPairByCRPos;
    private Map<String, ZBInfo> zbInfosPairNoSysZDM;
    private List<FieldDefs> subMbs = new ArrayList<FieldDefs>();
    private AreaInfo regionInfo;

    public final AreaInfo getRegionInfo() {
        return this.regionInfo;
    }

    public final void setRegionInfo(AreaInfo value) {
        this.regionInfo = value;
    }

    public final int getZBCount() {
        return this.zbs.size();
    }

    public final int getChildDefsCount() {
        return this.subMbs.size();
    }

    public final List<ZBInfo> getZbs() {
        return this.zbs;
    }

    public final void clearDatas() {
        this.zbInfoNoZDNList = null;
        this.zbNames = null;
        this.zbInfosPair = null;
        this.zbInfosPairNoSysZDM = null;
        this.zbInfosPairByCRNum = null;
        this.zbInfosPairByCRPos = null;
    }

    public final List<ZBInfo> getZbsNoZDM() {
        if (this.zbInfoNoZDNList == null) {
            this.zbInfoNoZDNList = new ArrayList<ZBInfo>();
            for (ZBInfo zbinfo : this.zbs) {
                if (zbinfo == null || "SYS_ZDM".equals(zbinfo.getFieldName())) continue;
                this.zbInfoNoZDNList.add(zbinfo);
            }
        }
        return this.zbInfoNoZDNList;
    }

    public final void setZbs(List<ZBInfo> zbs_0) {
        this.zbs = zbs_0;
    }

    public final List<FieldDefs> getSubMbs() {
        return this.subMbs;
    }

    public final void setSubMbs(List<FieldDefs> subMbs_0) {
        this.subMbs = subMbs_0;
    }

    public final void addZbs(ZBInfo info) {
        this.zbs.add(info);
    }

    public final void addSubMb(FieldDefs info) {
        this.subMbs.add(info);
    }

    public final Map<Integer, EnumInfo> getEnumMap() {
        return this.enumMap;
    }

    public final void setEnumMap(Map<Integer, EnumInfo> enumMap_0) {
        this.enumMap = enumMap_0;
    }

    public final int fieldNameToIndex(String zbName) {
        List<String> zbNameList = this.getZbNameList();
        int result = zbNameList.indexOf(zbName);
        return result;
    }

    public final List<String> getZbNameList() {
        if (this.zbNames == null) {
            this.zbNames = new ArrayList<String>();
            for (ZBInfo zbinfo : this.zbs) {
                this.zbNames.add(zbinfo.getFieldName());
            }
        }
        return this.zbNames;
    }

    public final Map<String, ZBInfo> getZbInfosPair() {
        if (this.zbInfosPairNoSysZDM == null) {
            this.zbInfosPairNoSysZDM = new HashMap<String, ZBInfo>();
            for (ZBInfo zb : this.zbs) {
                if ("SYS_ZDM".equals(zb.getFieldName())) continue;
                this.zbInfosPairNoSysZDM.put(zb.getFieldName(), zb);
            }
        }
        return this.zbInfosPairNoSysZDM;
    }

    public final Map<String, ZBInfo> getAllZbInfosPair() {
        if (this.zbInfosPair == null) {
            this.zbInfosPair = new HashMap<String, ZBInfo>();
            for (ZBInfo zb : this.zbs) {
                this.zbInfosPair.put(zb.getFieldName(), zb);
            }
        }
        return this.zbInfosPair;
    }

    public Map<String, ZBInfo> getZbInfosPairByCRNum() {
        if (this.zbInfosPairByCRNum == null) {
            this.zbInfosPairByCRNum = new HashMap<String, ZBInfo>();
            for (ZBInfo zb : this.zbs) {
                String aCode = "";
                if (zb.getNumPos() != null && zb.getNumPos().length > 1) {
                    aCode = String.valueOf(zb.getNumPos()[0]) + "_";
                    aCode = aCode + String.valueOf(zb.getNumPos()[1]);
                }
                this.zbInfosPairByCRNum.put(aCode, zb);
            }
        }
        return this.zbInfosPairByCRNum;
    }

    public void setZbInfosPairByCRNum(Map<String, ZBInfo> zbInfosPairByCRNum) {
        this.zbInfosPairByCRNum = zbInfosPairByCRNum;
    }

    public Map<String, ZBInfo> getZbInfosPairByCRPos() {
        if (this.zbInfosPairByCRPos == null) {
            this.zbInfosPairByCRPos = new HashMap<String, ZBInfo>();
            for (ZBInfo zb : this.zbs) {
                String aCode = "";
                if (zb.getGridPos() != null && zb.getGridPos().length > 1) {
                    aCode = String.valueOf(zb.getGridPos()[0]) + "_";
                    aCode = aCode + String.valueOf(zb.getGridPos()[1]);
                }
                this.zbInfosPairByCRPos.put(aCode, zb);
            }
        }
        return this.zbInfosPairByCRPos;
    }

    public void setZbInfosPairByCRPos(Map<String, ZBInfo> zbInfosPairByCRPos) {
        this.zbInfosPairByCRPos = zbInfosPairByCRPos;
    }
}

