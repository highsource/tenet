package org.hisrc.tenet.graph;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.railwaygraph.model.RailwayLinkTransition;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;

public class RailwayTransitionGraph extends DefaultDirectedGraph<RailwayLink, RailwayLinkTransition> {

	private static final long serialVersionUID = -2773128904309716002L;

	public RailwayTransitionGraph(EdgeFactory<RailwayLink, RailwayLinkTransition> ef) {
		super(ef);
	}

	@Override
	public double getEdgeWeight(RailwayLinkTransition e) {
		Validate.notNull(e);
		return e.getWeight();
	}
}