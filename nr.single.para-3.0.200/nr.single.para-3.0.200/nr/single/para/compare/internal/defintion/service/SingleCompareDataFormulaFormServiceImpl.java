/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.para.compare.internal.defintion.service;

import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import nr.single.para.compare.definition.CompareDataFormulaFormDTO;
import nr.single.para.compare.definition.ISingleCompareDataFormulaFormService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataFormulaFormDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataDao;
import nr.single.para.compare.internal.defintion.dao2.CompareDataFormulaFormDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareDataFormulaFormServiceImpl
implements ISingleCompareDataFormulaFormService {
    @Autowired
    private CompareDataFormulaFormDao formulaFormDao;
    @Autowired
    private ICompareDataDao<CompareDataFormulaFormDO> compareDataDao;
    private final Function<CompareDataFormulaFormDO, CompareDataFormulaFormDTO> toDto = Convert::cdfffromDo;
    private final Function<List<CompareDataFormulaFormDO>, List<CompareDataFormulaFormDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<CompareDataFormulaFormDTO> list(CompareDataFormulaFormDTO compareDataDTO) {
        List<CompareDataFormulaFormDO> list2;
        ArrayList<CompareDataFormulaFormDTO> list = new ArrayList<CompareDataFormulaFormDTO>();
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            CompareDataFormulaFormDO obj = this.compareDataDao.get(compareDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        }
        if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey()) && StringUtils.isNotEmpty((String)compareDataDTO.getFmlSchemeCompareKey())) {
            list2 = this.compareDataDao.getByParentInInfo(compareDataDTO.getInfoKey(), compareDataDTO.getFmlSchemeCompareKey());
            list.addAll((Collection<CompareDataFormulaFormDTO>)this.list2Dto.apply(list2));
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            list2 = this.compareDataDao.getByInfoKey(compareDataDTO.getInfoKey());
            list.addAll((Collection<CompareDataFormulaFormDTO>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public List<CompareDataFormulaFormDTO> listByScheme(String infoKey, String schemeCompareKey) {
        return this.formulaFormDao.getByScheme(infoKey, schemeCompareKey);
    }

    @Override
    public void add(CompareDataFormulaFormDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.insert(compareDataDTO);
    }

    @Override
    public void update(CompareDataFormulaFormDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.update(compareDataDTO);
    }

    @Override
    public void delete(CompareDataFormulaFormDTO compareDataDTO) throws SingleCompareException {
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            this.compareDataDao.delete(compareDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            this.compareDataDao.deleteByInfoKey(compareDataDTO.getInfoKey());
        }
    }

    @Override
    public void batchAdd(List<CompareDataFormulaFormDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataFormulaFormDTO> list2 = new ArrayList<CompareDataFormulaFormDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchInsert(list2);
    }

    @Override
    public void batchUpdate(List<CompareDataFormulaFormDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataFormulaFormDTO> list2 = new ArrayList<CompareDataFormulaFormDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<CompareDataFormulaFormDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (CompareDataFormulaFormDTO dto : compareDataDTOs) {
            list2.add(dto.getKey());
        }
        this.compareDataDao.batchDelete(list2);
    }
}

