package business.service;

import at.fhtw.leiwi.Util;
import at.fhtw.leiwi.index.IndexCalculator;
import at.fhtw.leiwi.index.model.IndexResult;
import at.fhtw.leiwi.index.model.Katalog;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.vividsolutions.jts.geom.Point;
import commons.utils.JavaToScalaConverter;
import org.opengis.feature.simple.SimpleFeature;
import view.model.IndexDetailVM;
import view.model.IndexResultVM;
import view.model.IndexVM;

/**
 * Created by cli on 01/06/15.
 */
public class IndexCalculationServiceImpl implements IndexCalculationService {

    private final static Double RADIUS = 2000d;
    private final IndexCalculator indexCalculator;

    public IndexCalculationServiceImpl(){
        indexCalculator = new IndexCalculator();
    }

    @Override
    public IndexResultVM calculateIndex(IndexVM indexVM) {
        SimpleFeature address  = Util.createSimpleFeatureWithPoint(indexVM.lon(), indexVM.lat(),"Adresse");
        if (indexVM.family()) {
            return calculateIndex(address, Util.Profiles.FAMILIEN);
        } else if (indexVM.students()) {
            return calculateIndex(address, Util.Profiles.STUDENTEN);
        } else if (indexVM.common()) {
            return calculateIndex(address, Util.Profiles.ALLGEMEIN);
        }else if (indexVM.mobility()) {
            return calculateIndex(address, Util.Profiles.MOBILITAET);
        }else{
            return calculateIndex(address, Util.Profiles.PENSIONISTEN);
        }
    }

    private IndexResultVM calculateIndex(SimpleFeature address, Util.Profiles profile) {
        IndexResult indexResult = indexCalculator.calculateIndex(address, RADIUS, profile);
        ImmutableList<IndexDetailVM> indexDetailVMs = mapIndexResult(indexResult);

        return new IndexResultVM(indexResult.getGesamtIndex(), new JavaToScalaConverter().convertList(indexDetailVMs));
    }

    private ImmutableList<IndexDetailVM> mapIndexResult(IndexResult indexResult) {
        return FluentIterable.from(indexResult.getKatalogList()).transform(new Function<Katalog, IndexDetailVM>() {
                    @Override
                    public IndexDetailVM apply(Katalog katalog) {
                        Point point = (Point) katalog.getDefaultGeometry();

                        return new IndexDetailVM(katalog.getType(), katalog.getIndexBewertung(),
                                point.getX(), point.getY(), katalog.getDistanceFromSource(), katalog.getGewichtung(), 1);
                    }
                }).toList();
    }
}
