package org.hisrc.tenet.jgrapht;

import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;

public class RailwayLinkTransitionAStarAdmissibleHeuristic implements AStarAdmissibleHeuristic<RailwayLink> {

	public static final AStarAdmissibleHeuristic<RailwayLink> INSTANCE = new RailwayLinkTransitionAStarAdmissibleHeuristic();

	@Override
	public double getCostEstimate(RailwayLink source, RailwayLink target) {
		double[] p0 = source.getGeometry().getLastPoint();
		double[] p1 = target.getGeometry().getLastPoint();
		double dx = p1[0] - p0[0];
		double dy = p1[1] - p0[1];
		return Math.sqrt(dx * dx + dy * dy);
	}
}
