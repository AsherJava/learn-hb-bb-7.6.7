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
import nr.single.para.compare.definition.CompareDataFormulaSchemeDTO;
import nr.single.para.compare.definition.ISingleCompareDataFormulaScemeService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataFormulaSchemeDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareDataFormulaSchemeServiceImpl
implements ISingleCompareDataFormulaScemeService {
    @Autowired
    private ICompareDataDao<CompareDataFormulaSchemeDO> compareDataDao;
    private final Function<CompareDataFormulaSchemeDO, CompareDataFormulaSchemeDTO> toDto = Convert::cdfsDo;
    private final Function<List<CompareDataFormulaSchemeDO>, List<CompareDataFormulaSchemeDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<CompareDataFormulaSchemeDTO> list(CompareDataFormulaSchemeDTO compareDataDTO) {
        ArrayList<CompareDataFormulaSchemeDTO> list = new ArrayList<CompareDataFormulaSchemeDTO>();
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            CompareDataFormulaSchemeDO obj = this.compareDataDao.get(compareDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            List<CompareDataFormulaSchemeDO> list2 = this.compareDataDao.getByInfoKey(compareDataDTO.getInfoKey());
            list.addAll((Collection<CompareDataFormulaSchemeDTO>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public void add(CompareDataFormulaSchemeDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.insert(compareDataDTO);
    }

    @Override
    public void update(CompareDataFormulaSchemeDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.update(compareDataDTO);
    }

    @Override
    public void delete(CompareDataFormulaSchemeDTO compareDataDTO) throws SingleCompareException {
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            this.compareDataDao.delete(compareDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            this.compareDataDao.deleteByInfoKey(compareDataDTO.getInfoKey());
        }
    }

    @Override
    public void batchAdd(List<CompareDataFormulaSchemeDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataFormulaSchemeDTO> list2 = new ArrayList<CompareDataFormulaSchemeDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchInsert(list2);
    }

    @Override
    public void batchUpdate(List<CompareDataFormulaSchemeDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataFormulaSchemeDTO> list2 = new ArrayList<CompareDataFormulaSchemeDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<CompareDataFormulaSchemeDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (CompareDataFormulaSchemeDTO dto : compareDataDTOs) {
            list2.add(dto.getKey());
        }
        this.compareDataDao.batchDelete(list2);
    }
}

