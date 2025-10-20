/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataRow
 */
package com.jiuqi.va.bill.bd.bill.impl;

import com.jiuqi.va.bill.bd.bill.domain.MapInfoDTO;
import com.jiuqi.va.bill.bd.bill.intf.BillToMasterDataIntf;
import com.jiuqi.va.bill.bd.bill.model.BasedataApplyBillModel;
import com.jiuqi.va.bill.bd.bill.model.BillAlterModel;
import com.jiuqi.va.bill.bd.bill.model.RegistrationBillModel;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.biz.intf.data.DataRow;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillToMasterHandle {
    @Autowired(required=false)
    private List<BillToMasterDataIntf> billToMasterProvider;

    public void beforeCheckByApply(BasedataApplyBillModel model, Map<String, Object> itemData, ApplyRegMapDO mapDefine, MapInfoDTO mapinfo, Map<String, String> fieldMap, String defineName, Map<String, Map<String, List<String>>> treeCodes, List<Map<String, Object>> applyBillDatas) {
        if (this.billToMasterProvider == null || this.billToMasterProvider.size() <= 0) {
            return;
        }
        defineName = defineName.toUpperCase();
        for (BillToMasterDataIntf billToMasterData : this.billToMasterProvider) {
            if (!billToMasterData.isEnable(defineName)) continue;
            billToMasterData.beforeCheckByApply(model, itemData, mapDefine, mapinfo, fieldMap, defineName, treeCodes, applyBillDatas);
        }
    }

    public void beforeCheckByAlter(BillAlterModel model, Map<String, Object> itemData, ApplyRegMapDO mapDefine, MapInfoDTO mapinfo, Map<String, String> fieldMap, String defineName, List<Map<String, Object>> applyBillDatas) {
        if (this.billToMasterProvider == null || this.billToMasterProvider.size() <= 0) {
            return;
        }
        defineName = defineName.toUpperCase();
        for (BillToMasterDataIntf billToMasterData : this.billToMasterProvider) {
            if (!billToMasterData.isEnable(defineName)) continue;
            billToMasterData.beforeCheckByAlter(model, itemData, mapDefine, mapinfo, fieldMap, defineName, applyBillDatas);
        }
    }

    public void beforeCheckByCreate(DataRow dataRow, String defineName) {
        if (this.billToMasterProvider == null || this.billToMasterProvider.size() <= 0) {
            return;
        }
        defineName = defineName.toUpperCase();
        for (BillToMasterDataIntf billToMasterData : this.billToMasterProvider) {
            if (!billToMasterData.isEnable(defineName)) continue;
            billToMasterData.beforeCheckByCreate(dataRow, defineName);
        }
    }

    public Object createMasterData(RegistrationBillModel model, String defineName) {
        if (this.billToMasterProvider == null || this.billToMasterProvider.size() <= 0) {
            return null;
        }
        defineName = defineName.toUpperCase();
        for (BillToMasterDataIntf billToMasterData : this.billToMasterProvider) {
            if (!billToMasterData.isEnable(defineName)) continue;
            return billToMasterData.createMasterData(model, defineName);
        }
        return null;
    }

    public Object getDelMasterData(DataRow dataRow, String defineName) {
        if (this.billToMasterProvider == null || this.billToMasterProvider.size() <= 0) {
            return null;
        }
        defineName = defineName.toUpperCase();
        for (BillToMasterDataIntf billToMasterData : this.billToMasterProvider) {
            if (!billToMasterData.isEnable(defineName)) continue;
            return billToMasterData.getDelMasterData(dataRow, defineName);
        }
        return null;
    }

    public boolean checkDataExist(DataRow dataRow, Map<String, Object> applyitemValue, Boolean bool, Map<String, String> reFieldMap, String defineName) {
        if (this.billToMasterProvider == null || this.billToMasterProvider.size() <= 0) {
            return false;
        }
        defineName = defineName.toUpperCase();
        for (BillToMasterDataIntf billToMasterData : this.billToMasterProvider) {
            if (!billToMasterData.isEnable(defineName)) continue;
            return billToMasterData.checkDataExist(dataRow, applyitemValue, bool, reFieldMap, defineName);
        }
        return false;
    }

    public void syncMasterData(Object syncData, String defineName) {
        if (this.billToMasterProvider == null || this.billToMasterProvider.size() <= 0) {
            return;
        }
        defineName = defineName.toUpperCase();
        for (BillToMasterDataIntf billToMasterData : this.billToMasterProvider) {
            if (!billToMasterData.isEnable(defineName)) continue;
            billToMasterData.syncMasterData(syncData, defineName);
        }
    }

    public void delMasterData(Object delData, String defineName) {
        if (this.billToMasterProvider == null || this.billToMasterProvider.size() <= 0) {
            return;
        }
        defineName = defineName.toUpperCase();
        for (BillToMasterDataIntf billToMasterData : this.billToMasterProvider) {
            if (!billToMasterData.isEnable(defineName)) continue;
            billToMasterData.delMasterData(delData, defineName);
        }
    }

    public boolean checkMasterDataIsChangeByDataRow(Map<String, String> reFieldMap, DataRow currData, DataRow oldData, String defineName) {
        if (this.billToMasterProvider == null || this.billToMasterProvider.size() <= 0) {
            return false;
        }
        defineName = defineName.toUpperCase();
        for (BillToMasterDataIntf billToMasterData : this.billToMasterProvider) {
            if (!billToMasterData.isEnable(defineName)) continue;
            return billToMasterData.checkMasterDataIsChangeByDataRow(reFieldMap, currData, oldData, defineName);
        }
        return false;
    }

    public boolean checkMasterDataIsChangeByDataMap(DataRow master, Map<String, String> reFieldMap, Map<String, Object> currData, Map<String, Object> oldData, String defineName) {
        if (this.billToMasterProvider == null || this.billToMasterProvider.size() <= 0) {
            return false;
        }
        defineName = defineName.toUpperCase();
        for (BillToMasterDataIntf billToMasterData : this.billToMasterProvider) {
            if (!billToMasterData.isEnable(defineName)) continue;
            return billToMasterData.checkMasterDataIsChangeByDataMap(master, reFieldMap, currData, oldData, defineName);
        }
        return false;
    }
}

