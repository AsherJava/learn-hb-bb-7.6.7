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
import nr.single.para.compare.definition.CompareDataFormulaDTO;
import nr.single.para.compare.definition.ISingleCompareDataFormulaService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataFormulaDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataDao;
import nr.single.para.compare.internal.defintion.dao2.CompareDataFormulaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareDataFormulaServiceImpl
implements ISingleCompareDataFormulaService {
    @Autowired
    private CompareDataFormulaDao formulaDao;
    @Autowired
    private ICompareDataDao<CompareDataFormulaDO> compareDataDao;
    private final Function<CompareDataFormulaDO, CompareDataFormulaDTO> toDto = Convert::cdffromDo;
    private final Function<List<CompareDataFormulaDO>, List<CompareDataFormulaDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<CompareDataFormulaDTO> list(CompareDataFormulaDTO compareDataDTO) {
        List<CompareDataFormulaDTO> list = new ArrayList<CompareDataFormulaDTO>();
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            CompareDataFormulaDO obj = this.compareDataDao.get(compareDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        }
        if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey()) && StringUtils.isNotEmpty((String)compareDataDTO.getFmlSchemeCompareKey())) {
            list = this.formulaDao.getByScheme(compareDataDTO.getInfoKey(), compareDataDTO.getFmlSchemeCompareKey());
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            List<CompareDataFormulaDO> list2 = this.compareDataDao.getByInfoKey(compareDataDTO.getInfoKey());
            list.addAll((Collection<CompareDataFormulaDTO>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public void add(CompareDataFormulaDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.insert(compareDataDTO);
    }

    @Override
    public void update(CompareDataFormulaDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.update(compareDataDTO);
    }

    @Override
    public void delete(CompareDataFormulaDTO compareDataDTO) throws SingleCompareException {
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            this.compareDataDao.delete(compareDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            this.compareDataDao.deleteByInfoKey(compareDataDTO.getInfoKey());
        }
    }

    @Override
    public void batchAdd(List<CompareDataFormulaDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataFormulaDTO> list2 = new ArrayList<CompareDataFormulaDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchInsert(list2);
    }

    @Override
    public void batchUpdate(List<CompareDataFormulaDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataFormulaDTO> list2 = new ArrayList<CompareDataFormulaDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<CompareDataFormulaDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (CompareDataFormulaDTO dto : compareDataDTOs) {
            list2.add(dto.getKey());
        }
        this.compareDataDao.batchDelete(list2);
    }
}

