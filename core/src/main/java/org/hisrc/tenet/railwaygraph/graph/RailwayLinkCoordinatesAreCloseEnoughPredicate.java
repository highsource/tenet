package org.hisrc.tenet.railwaygraph.graph;

import java.text.MessageFormat;
import java.util.function.BiPredicate;

import org.hisrc.tenet.railwaynetwork.model.RailwayLink;

public class RailwayLinkCoordinatesAreCloseEnoughPredicate implements BiPredicate<RailwayLink, RailwayLink> {

	public static BiPredicate<RailwayLink, RailwayLink> INSTANCE = new RailwayLinkCoordinatesAreCloseEnoughPredicate();

	// Ca. 3m
	private static final double EPSILON = 0.000025;

	@Override
	public boolean test(RailwayLink firstLink, RailwayLink secondLink) {

		double[] lastPointOfFirstLink = firstLink.getGeometry().getLastPoint();
		double[] firstPointOfSecondLink = secondLink.getGeometry().getFirstPoint();

		double lon0 = lastPointOfFirstLink[0];
		double lat0 = lastPointOfFirstLink[1];
		double lon1 = firstPointOfSecondLink[0];
		double lat1 = firstPointOfSecondLink[1];
		if (lon0 == lon1 && lat0 == lat1) {
			return true;
		} else {
			double dlon = lon1 - lon0;
			double dlat = lat1 - lat0;
			final double d = Math.sqrt(dlon * dlon + dlat * dlat);

			if (d < EPSILON) {
				System.out.println(MessageFormat.format(
						"Last point of the first link [{0}], [{1,number,#.############},{2,number,#.############}] and first point of the second link [{3}], [{4,number,#.############},{5,number,#.############}] are very close [{6,number,#.############}] but do not match exactly.",
						firstLink.getProperties().getId(),
						lon0, lat0, 
						secondLink.getProperties().getId(),
						lon1, lat1, d));
				return true;
			} else {
				return false;
			}
		}
	}

}
