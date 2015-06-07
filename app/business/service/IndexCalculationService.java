package business.service;

import view.model.IndexResultVM;
import view.model.IndexVM;

/**
 * Created by cli on 01/06/15.
 */
public interface IndexCalculationService {
    IndexResultVM calculateIndex(IndexVM indexVM);
}
