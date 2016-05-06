package org.hisrc.tenet.railwaygraph.graph;

import java.text.MessageFormat;
import java.util.function.BiPredicate;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;

public class RailwayLinkAngleIsSuitablePredicate implements BiPredicate<RailwayLink, RailwayLink> {

	public static BiPredicate<RailwayLink, RailwayLink> INSTANCE = new RailwayLinkAngleIsSuitablePredicate();

	private static final double MAX_ALLOWED_ANGLE_IN_DEGREES = 90;
	private static final double MAX_ALLOWED_ANGLE = Math.PI * (MAX_ALLOWED_ANGLE_IN_DEGREES / 180);
	private static final double WARNING_TRESHHOLD_ANGLE_IN_DEGREES = 15;
	private static final double WARNING_TRESHHOLD_ANGLE = Math.PI * (WARNING_TRESHHOLD_ANGLE_IN_DEGREES / 180);

	// 180 * Vector2D.angle(lastVector, firstVector) / Math.PI

	@Override
	public boolean test(RailwayLink firstLink, RailwayLink secondLink) {

		double[] preLastPointOfFirstLink = firstLink.getGeometry().getPreLastPoint();
		double[] lastPointOfFirstLink = firstLink.getGeometry().getLastPoint();
		double[] firstPointOfSecondLink = secondLink.getGeometry().getFirstPoint();
		double[] secondPointOfSecondLink = secondLink.getGeometry().getSecondPoint();
		
		double lon0 = lastPointOfFirstLink[0];
		double lat0 = lastPointOfFirstLink[1];
		double lon1 = firstPointOfSecondLink[0];
		double lat1 = firstPointOfSecondLink[1];
		

		final Vector2D lastSegmentOfFirstLink = new Vector2D(lastPointOfFirstLink[0] - preLastPointOfFirstLink[0],
				lastPointOfFirstLink[1] - preLastPointOfFirstLink[1]);

		final Vector2D firstSegmentOfFirstLink = new Vector2D(secondPointOfSecondLink[0] - firstPointOfSecondLink[0],
				secondPointOfSecondLink[1] - firstPointOfSecondLink[1]);

		final double angle = Vector2D.angle(lastSegmentOfFirstLink, firstSegmentOfFirstLink);

		if (angle < MAX_ALLOWED_ANGLE) {
			if (angle > WARNING_TRESHHOLD_ANGLE) {
				System.out.println(MessageFormat.format(
						"Angle between the first link [{0}], [{1,number,#.############},{2,number,#.############}] and the second link [{3}], [{4,number,#.############},{5,number,#.############}] is too big [{6,number,#.##}].",
						firstLink.getProperties().getId(),
						lon0, lat0, 
						secondLink.getProperties().getId(),
						lon1, lat1, (angle / Math.PI) * 180));
			}
			return true;
		} else {
			return false;
		}

	}

}
