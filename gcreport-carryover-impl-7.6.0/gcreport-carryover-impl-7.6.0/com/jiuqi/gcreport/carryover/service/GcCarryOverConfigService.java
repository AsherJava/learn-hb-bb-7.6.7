/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.carryover.vo.CarryOverConfigVO
 *  com.jiuqi.gcreport.carryover.vo.CarryOverTypeVO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.carryover.service;

import com.jiuqi.gcreport.carryover.entity.CarryOverConfigEO;
import com.jiuqi.gcreport.carryover.vo.CarryOverConfigVO;
import com.jiuqi.gcreport.carryover.vo.CarryOverTypeVO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface GcCarryOverConfigService {
    public String saveConfig(CarryOverConfigVO var1);

    public Boolean deleteConfigById(String var1);

    public String updateConfig(CarryOverConfigVO var1);

    public List<CarryOverTypeVO> listCarryOverType();

    public String getConfigOptionById(String var1);

    public CarryOverConfigEO getCarryOverConfigById(String var1);

    public List<CarryOverConfigVO> listCarryOverConfig();

    public List<CarryOverConfigEO> listAll();

    public Boolean exchangeSortConfig(String var1, String var2);

    public void importConfigByJson(boolean var1, MultipartFile var2);
}

