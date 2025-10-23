/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.model.MetaExportModel
 *  com.jiuqi.np.common.exception.JQException
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.quantity.transfer;

import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.model.MetaExportModel;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.quantity.bean.QuantityCategory;
import com.jiuqi.nr.quantity.bean.QuantityInfo;
import com.jiuqi.nr.quantity.bean.QuantityUnit;
import com.jiuqi.nr.quantity.service.IQuantityService;
import com.jiuqi.nr.quantity.service.KeyCondType;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class QuantityModelTransfer
implements IModelTransfer {
    private static final Logger logger = LoggerFactory.getLogger(QuantityModelTransfer.class);
    @Autowired
    private IQuantityService quantityService;

    public void importModel(IImportContext iImportContext, byte[] bytes) throws TransferException {
        JSONObject jsonObject = new JSONObject(new String(bytes, StandardCharsets.UTF_8));
        QuantityInfo quantityInfo = new QuantityInfo();
        quantityInfo.fromJson(jsonObject);
        List<QuantityCategory> quantityCategories = this.buildQuantityCategoriesFromJson(jsonObject);
        List<QuantityUnit> quantityUnits = this.buildQuantityUnitsFromJson(jsonObject);
        this.importQuantityInfo(quantityInfo);
        this.importQuantityCategories(quantityInfo.getId(), quantityCategories);
        this.importQuantityUnits(quantityInfo.getId(), quantityUnits);
    }

    private void importQuantityInfo(QuantityInfo quantityInfo) throws TransferException {
        QuantityInfo findQuantityInfo = null;
        try {
            findQuantityInfo = this.quantityService.getQuantityInfoById(quantityInfo.getId());
            if (findQuantityInfo == null) {
                findQuantityInfo = this.quantityService.getQuantityInfoByName(quantityInfo.getName());
                if (findQuantityInfo == null) {
                    this.quantityService.addQuantityInfo(quantityInfo);
                } else {
                    this.quantityService.modifyQuantityInfo(quantityInfo, KeyCondType.NAME);
                }
            } else {
                this.quantityService.modifyQuantityInfo(quantityInfo, KeyCondType.ID);
            }
        }
        catch (JQException e) {
            throw new TransferException(String.format("\u91cf\u7eb2[%s]\u5bfc\u5165\u5931\u8d25\uff01", quantityInfo.getTitle()), (Throwable)e);
        }
    }

    private void importQuantityCategories(String quantityId, List<QuantityCategory> quantityCategories) throws TransferException {
        if (!CollectionUtils.isEmpty(quantityCategories)) {
            for (QuantityCategory quantityCategory : quantityCategories) {
                try {
                    QuantityCategory findQuantityCategory = this.quantityService.getQuantityCategoryById(quantityCategory.getId());
                    if (findQuantityCategory == null) {
                        findQuantityCategory = this.quantityService.getQuantityCategroyByName(quantityCategory.getName());
                        if (findQuantityCategory == null) {
                            this.quantityService.addQuantityCategory(quantityCategory);
                            continue;
                        }
                        if (findQuantityCategory.getQuantityId().equals(quantityId)) {
                            this.quantityService.modifyQuantityCategory(quantityCategory, KeyCondType.NAME);
                            continue;
                        }
                        throw new TransferException(String.format("\u91cf\u7eb2\u5206\u7c7b[%s]\u6807\u8bc6\u91cd\u590d\uff01\uff01", quantityCategory.getTitle()));
                    }
                    this.quantityService.modifyQuantityCategory(quantityCategory, KeyCondType.ID);
                }
                catch (JQException e) {
                    throw new TransferException(String.format("\u91cf\u7eb2\u5206\u7c7b[%s]\u5bfc\u5165\u5931\u8d25\uff01", quantityCategory.getTitle()), (Throwable)e);
                }
            }
        }
    }

    private void importQuantityUnits(String quantityId, List<QuantityUnit> quantityUnits) throws TransferException {
        if (!CollectionUtils.isEmpty(quantityUnits)) {
            for (QuantityUnit quantityUnit : quantityUnits) {
                try {
                    QuantityUnit findQuantityUnit = this.quantityService.getQuantityUnitById(quantityUnit.getId());
                    if (findQuantityUnit == null) {
                        findQuantityUnit = this.quantityService.getQuantityUnitByName(quantityUnit.getName());
                        if (findQuantityUnit == null) {
                            this.quantityService.addQuantityUnit(quantityUnit);
                            continue;
                        }
                        if (findQuantityUnit.getQuantityId().equals(quantityId)) {
                            this.quantityService.modifyQuantityUnit(quantityUnit, KeyCondType.NAME);
                            continue;
                        }
                        throw new TransferException(String.format("\u91cf\u7eb2\u5355\u4f4d[%s]\u6807\u8bc6\u91cd\u590d\uff01\uff01", quantityUnit.getTitle()));
                    }
                    this.quantityService.modifyQuantityUnit(quantityUnit, KeyCondType.ID);
                }
                catch (JQException e) {
                    throw new TransferException(String.format("\u91cf\u7eb2\u5355\u4f4d[%s]\u5bfc\u5165\u5931\u8d25\uff01", quantityUnit.getTitle()), (Throwable)e);
                }
            }
        }
    }

    public MetaExportModel exportModel(IExportContext iExportContext, String nodeKey) throws TransferException {
        MetaExportModel exportModel = new MetaExportModel();
        String quantityId = nodeKey.substring(3);
        try {
            QuantityInfo quantityInfo = this.quantityService.getQuantityInfoById(quantityId);
            JSONObject jsonObject = quantityInfo.toJson();
            this.buildQuantityCategoryJson(jsonObject, quantityId);
            this.buildQuantityUnitJson(jsonObject, quantityId);
            exportModel.setData(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        }
        catch (JQException e) {
            logger.error("\u91cf\u7eb2\u5bfc\u51fa\u5931\u8d25\uff01", e);
        }
        return exportModel;
    }

    private void buildQuantityCategoryJson(JSONObject jsonObject, String quantityId) throws JQException {
        List<QuantityCategory> quantityCategories = this.quantityService.getQuantityCategoryByQuanId(quantityId);
        if (quantityCategories != null) {
            JSONArray categoryArr = new JSONArray();
            quantityCategories.forEach(category -> categoryArr.put((Object)category.toJson()));
            jsonObject.put("categories", (Object)categoryArr);
        }
    }

    private List<QuantityCategory> buildQuantityCategoriesFromJson(JSONObject jsonObject) {
        ArrayList<QuantityCategory> quantityCategories = new ArrayList<QuantityCategory>();
        JSONArray categoryArr = jsonObject.getJSONArray("categories");
        if (categoryArr != null) {
            categoryArr.forEach(categoryJson -> {
                QuantityCategory quantityCategory = new QuantityCategory();
                quantityCategory.fromJson((JSONObject)categoryJson);
                quantityCategories.add(quantityCategory);
            });
        }
        return quantityCategories;
    }

    private void buildQuantityUnitJson(JSONObject jsonObject, String quantityId) throws JQException {
        List<QuantityUnit> quantityUnits = this.quantityService.getQuantityUnitByQuantityId(quantityId);
        if (quantityUnits != null) {
            JSONArray unitArr = new JSONArray();
            quantityUnits.forEach(unit -> unitArr.put((Object)unit.toJson()));
            jsonObject.put("units", (Object)unitArr);
        }
    }

    private List<QuantityUnit> buildQuantityUnitsFromJson(JSONObject jsonObject) {
        ArrayList<QuantityUnit> quantityUnits = new ArrayList<QuantityUnit>();
        JSONArray unitArr = jsonObject.getJSONArray("units");
        if (unitArr != null) {
            unitArr.forEach(unitJson -> {
                QuantityUnit quantityUnit = new QuantityUnit();
                quantityUnit.fromJson((JSONObject)unitJson);
                quantityUnits.add(quantityUnit);
            });
        }
        return quantityUnits;
    }
}

