/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.service.impl;

import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreReferService;
import com.jiuqi.nr.calibre2.api.ICalibreCheckReference;
import com.jiuqi.nr.calibre2.api.ICallBackCalibreCheck;
import com.jiuqi.nr.calibre2.api.IReferenceCalibre;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.vo.ReferenceCalibreVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalibreReferServiceImpl
implements ICalibreReferService {
    @Autowired(required=false)
    private List<ICallBackCalibreCheck> callBackCalibreCheckList;
    @Autowired
    private ICalibreDataService calibreDataService;

    @Override
    public List<ReferenceCalibreVO> getRefer(String code, List<String> calibre) {
        ArrayList<ReferenceCalibreVO> referenceCalibreList = new ArrayList<ReferenceCalibreVO>();
        if (this.callBackCalibreCheckList == null) {
            return referenceCalibreList;
        }
        for (ICallBackCalibreCheck calibreCheck : this.callBackCalibreCheckList) {
            ICalibreCheckReference check = calibreCheck.check(code, calibre);
            List<IReferenceCalibre> references = check.getReferences();
            List<String> calibreKeys = references.stream().map(IReferenceCalibre::calibreDataCode).collect(Collectors.toList());
            CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
            calibreDataDTO.setCalibreCode(code);
            calibreDataDTO.setCodes(calibreKeys);
            Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreDataDTO);
            if (list.getCode() == 0) {
                return referenceCalibreList;
            }
            List<CalibreDataDTO> data = list.getData();
            Map<String, String> codeToNameMap = data.stream().collect(Collectors.toMap(CalibreDataDO::getCode, CalibreDataDO::getName));
            for (IReferenceCalibre reference : references) {
                boolean enableDelete = check.enableDelete(reference.calibreDataCode());
                ReferenceCalibreVO instance = ReferenceCalibreVO.getInstance(reference, enableDelete);
                instance.setCalibreTitle(codeToNameMap.get(instance.getCalibreDataCode()));
                referenceCalibreList.add(instance);
            }
        }
        return referenceCalibreList;
    }
}

