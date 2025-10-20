/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.impl.RegionTabSettingData
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.paramlanguage.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.helper.SaveParamLanguageHelper;
import com.jiuqi.nr.designer.paramlanguage.service.SaveLanguageService;
import com.jiuqi.nr.designer.paramlanguage.util.RegionTabSettingUtils;
import com.jiuqi.nr.designer.paramlanguage.vo.BigDataSaveObject;
import com.jiuqi.nr.designer.paramlanguage.vo.ParamLanguageObject;
import com.jiuqi.nr.designer.paramlanguage.vo.RegionTabSettingObject;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveLanguageServiceImpl
implements SaveLanguageService {
    @Autowired
    private DesParamLanguageDao desParamLanguageDao;
    @Autowired
    private SaveParamLanguageHelper saveParamLanguageHelper;
    @Autowired
    private DesignFormDefineService formService;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;

    @Override
    public void batchSaveParamLanguage(ParamLanguageObject[] paramLanguages) throws DBParaException {
        ArrayList<String[]> needDeleteList = new ArrayList<String[]>();
        for (ParamLanguageObject paramLanguage : paramLanguages) {
            List desParamLanguageList = this.desParamLanguageDao.queryLanguage(paramLanguage.getResourceKey(), LanguageResourceType.valueOf((int)paramLanguage.getResourceType()), String.valueOf(paramLanguage.getLanguageType()));
            if (desParamLanguageList.size() <= 0) continue;
            String[] delete = new String[]{((DesParamLanguage)desParamLanguageList.get(0)).getResourceKey(), ((DesParamLanguage)desParamLanguageList.get(0)).getLanguageType()};
            needDeleteList.add(delete);
        }
        ArrayList<DesParamLanguage> desParamLanguagesList = this.saveParamLanguageHelper.ParamLanguageObjectToParamLanguage(paramLanguages);
        DesParamLanguage[] paramAddLanguageArray = desParamLanguagesList.toArray(new DesParamLanguage[desParamLanguagesList.size()]);
        if (needDeleteList.size() > 0) {
            this.desParamLanguageDao.batchDelete(needDeleteList);
        }
        if (paramAddLanguageArray.length > 0) {
            this.desParamLanguageDao.batchInsert(paramAddLanguageArray);
        }
    }

    @Override
    public void saveBigDataParamLanguage(BigDataSaveObject bigDataSaveObject) throws JQException {
        int languageType = null == bigDataSaveObject.getLanguage() ? LanguageType.CHINESE.getValue() : Integer.parseInt(bigDataSaveObject.getLanguage());
        try {
            if (bigDataSaveObject.getGrid2Data() != null) {
                Map<String, Grid2Data> grid2DataMap = bigDataSaveObject.getGrid2Data();
                for (String formKey : grid2DataMap.keySet()) {
                    Grid2Data grid2Data = grid2DataMap.get(formKey);
                    byte[] bytes = Grid2Data.gridToBytes((Grid2Data)grid2Data);
                    this.formService.updateBigDataDefine(formKey, "FORM_DATA", languageType, bytes);
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_175, (Throwable)e);
        }
        try {
            if (bigDataSaveObject.getRegionTabSettingData() != null) {
                Map<String, List<RegionTabSettingObject>> regionTabSettingDataMap = bigDataSaveObject.getRegionTabSettingData();
                for (String regionKey : regionTabSettingDataMap.keySet()) {
                    String regionSettingKey = this.iDesignTimeViewController.queryDataRegionDefine(regionKey).getRegionSettingKey();
                    List<RegionTabSettingObject> regionTabSettingObjects = regionTabSettingDataMap.get(regionKey);
                    List<RegionTabSettingDefine> regionTabSettingDefines = RegionTabSettingUtils.tabSettingObjListConversion(regionTabSettingObjects);
                    byte[] data = RegionTabSettingData.regionTabSettingDataToBytes(regionTabSettingDefines);
                    this.formService.updateBigDataDefine(regionSettingKey, "REGION_TAB", languageType, data);
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_176, (Throwable)e);
        }
        try {
            if (bigDataSaveObject.getFillingGuideData() != null) {
                Map<String, String> fillingGuideData = bigDataSaveObject.getFillingGuideData();
                for (String formKey : fillingGuideData.keySet()) {
                    String fill = fillingGuideData.get(formKey);
                    if (!StringUtils.isNotEmpty((String)fill)) continue;
                    this.formService.updateBigDataDefine(formKey, "FILLING_GUIDE", languageType, DesignFormDefineBigDataUtil.StringToBytes((String)fill));
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_176, (Throwable)e);
        }
    }
}

