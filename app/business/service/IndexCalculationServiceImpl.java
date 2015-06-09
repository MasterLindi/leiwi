package business.service;

import commons.utils.JavaToScalaConverter;
import view.model.IndexDetailVM;
import view.model.IndexResultVM;
import view.model.IndexVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cli on 01/06/15.
 */
public class IndexCalculationServiceImpl implements IndexCalculationService {
    @Override
    public IndexResultVM calculateIndex(IndexVM indexVM) {
        if (indexVM.family()) {
            List<IndexDetailVM> detailVMList = new ArrayList<>();
            detailVMList.add(new IndexDetailVM("Luftgüte", 3.0, 16.4576734, 48.769889, 1.32, 3, 3));
            detailVMList.add(new IndexDetailVM("Krankenversorung", 4.0, 16.454334, 48.764389, 3.2, 23, 33));
            return new IndexResultVM(1.3, new JavaToScalaConverter().convertList(detailVMList));
        } else if (indexVM.students()) {
            List<IndexDetailVM> detailVMList = new ArrayList<>();
            detailVMList.add(new IndexDetailVM("Luftgüte", 2.0, 16.45387874, 48.76889879, 2.12, 3, 2));
            detailVMList.add(new IndexDetailVM("Krankenversorung", 7.0, 16.425334, 48.768349, 0.3, 3, 2));
            return new IndexResultVM(2.3, new JavaToScalaConverter().convertList(detailVMList));
        } else {
            List<IndexDetailVM> detailVMList = new ArrayList<>();
            detailVMList.add(new IndexDetailVM("Luftgüte", 8.0, 16.45387874, 48.76887889, 1.11,4, 4));
            detailVMList.add(new IndexDetailVM("Krankenversorung", 2.0, 16.45334, 48.768349, 8.32, 6, 4));
            return new IndexResultVM(4.3, new JavaToScalaConverter().convertList(detailVMList));
        }
    }
}
