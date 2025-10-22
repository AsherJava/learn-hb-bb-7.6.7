/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.datawarning.service;

import com.jiuqi.nr.datawarning.dao.IDataWarningDao;
import com.jiuqi.nr.datawarning.defines.DataWarningDefine;
import com.jiuqi.nr.datawarning.defines.DataWarningIdentify;
import com.jiuqi.nr.datawarning.defines.DataWarningType;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/datawarning"})
public class DataWarningProvider {
    private static final Logger log = LoggerFactory.getLogger(DataWarningProvider.class);
    @Autowired
    private IDataWarningDao dataWarningDao;

    @RequestMapping(value={"/datawarning-add"}, method={RequestMethod.POST})
    public String add(@RequestBody DataWarningDefine dataWarningDefine) {
        try {
            dataWarningDefine.setUpdatetime(new Date());
            Boolean flag = this.dataWarningDao.Insert(dataWarningDefine);
            if (flag.booleanValue()) {
                return "true";
            }
            return "false";
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return "false";
        }
    }

    @RequestMapping(value={"/datawarning-update"}, method={RequestMethod.POST})
    public String update(@RequestBody DataWarningDefine warningDefine) {
        try {
            DataWarningDefine define = this.dataWarningDao.QueryById(warningDefine.getId());
            if (define != null) {
                warningDefine.setUpdatetime(new Date());
                Boolean flag = this.dataWarningDao.Update(warningDefine);
                if (flag.booleanValue()) {
                    return "true";
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return "false";
        }
        return "false";
    }

    @RequestMapping(value={"/datawarning-updatebymodel"}, method={RequestMethod.POST})
    public String update(@RequestBody List<DataWarningDefine> warningDefines) {
        try {
            Boolean flag = this.dataWarningDao.Update(warningDefines);
            if (flag.booleanValue()) {
                return "true";
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return "false";
        }
        return "false";
    }

    @RequestMapping(value={"/datawarning-remove"}, method={RequestMethod.GET})
    public String remove(String id) {
        try {
            Boolean flag = this.dataWarningDao.Delete(id);
            if (flag.booleanValue()) {
                return "true";
            }
            return "false";
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return "false";
        }
    }

    @RequestMapping(value={"/datawarning-removebykeyandissave"}, method={RequestMethod.GET})
    public String remove(@RequestParam(value="key") String key, @RequestParam(value="isSave") Boolean isSave) {
        try {
            Boolean flag = this.dataWarningDao.Delete(key, isSave);
            if (flag.booleanValue()) {
                return "true";
            }
            return "false";
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return "false";
        }
    }

    @RequestMapping(value={"/datawarning-querywarningitemsbykey"}, method={RequestMethod.POST})
    public List<DataWarningDefine> queryWarningItemsByKey(@RequestBody Map<String, String> keys) {
        String key = keys.get("p_key");
        try {
            if (key != null) {
                return this.dataWarningDao.QueryDataWarnigs(key);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping(value={"/datawarning-querywarningitemsbyidentify"}, method={RequestMethod.POST})
    public List<DataWarningDefine> queryWarningItemsByIdentify(@RequestBody Map<String, String> keys) {
        String identifyStr = keys.get("p_identify");
        try {
            if (identifyStr != null) {
                return this.dataWarningDao.QueryDataWarnigs(DataWarningIdentify.valueOf(identifyStr));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping(value={"/datawarning-querywarningitemsbykeyandidentify"}, method={RequestMethod.POST})
    public List<DataWarningDefine> queryWarningItemsByKeyAndIdentify(@RequestBody Map<String, String> keys) {
        String key = keys.get("p_key");
        String identifyStr = keys.get("p_identify");
        try {
            if (key != null && identifyStr != null) {
                return this.dataWarningDao.QueryDataWarnigs(key, DataWarningIdentify.valueOf(identifyStr));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping(value={"/datawarning-querywarningitemsbyidentifyandtype"}, method={RequestMethod.POST})
    public List<DataWarningDefine> queryWarningItemsByIdentifyAndType(@RequestBody Map<String, String> keys) {
        String identifyStr = keys.get("p_identify");
        String typeStr = keys.get("p_wtype");
        try {
            if (identifyStr != null && typeStr != null) {
                return this.dataWarningDao.QueryDataWarnigs(DataWarningType.valueOf(typeStr), DataWarningIdentify.valueOf(identifyStr));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping(value={"/datawarning-querywarningitemsbykeyandidentifyandtype"}, method={RequestMethod.POST})
    public List<DataWarningDefine> queryWarningItemsByKeyAndidenAndtype(@RequestBody Map<String, String> keys) {
        String key = keys.get("p_key");
        String identifyStr = keys.get("p_identify");
        String typeStr = keys.get("p_wtype");
        try {
            if (key != null && identifyStr != null && typeStr != null) {
                return this.dataWarningDao.QueryDataWarnigs(key, DataWarningType.valueOf(typeStr), DataWarningIdentify.valueOf(identifyStr));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping(value={"/datawarning-queryitem"}, method={RequestMethod.GET})
    public DataWarningDefine queryWarningItem(String id) {
        try {
            return this.dataWarningDao.QueryById(id);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value={"/datawarning-querywarningitemsbyfieldCode"}, method={RequestMethod.POST})
    public List<DataWarningDefine> queryWarningItemsByFieldCode(@RequestBody Map<String, String> keys) {
        String key = keys.get("p_key");
        String fieldCode = keys.get("p_fieldCode");
        try {
            if (key != null && fieldCode != null) {
                return this.dataWarningDao.QueryDataWarnigsByKeyAndFieldCode(key, fieldCode);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping(value={"/datawarning-querywarningitemsbyfieldSettingCode"}, method={RequestMethod.POST})
    public List<DataWarningDefine> queryWarningItemsByFieldSettingCode(@RequestBody Map<String, String> keys) {
        String key = keys.get("p_key");
        String fieldSettingCode = keys.get("p_fieldSettingCode");
        try {
            if (key != null && fieldSettingCode != null) {
                return this.dataWarningDao.QueryDataWarnigsByKeyAndFieldSettingCode(key, fieldSettingCode);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}

