package org.hisrc.tenet.graph;

import java.io.Serializable;

import org.hisrc.tenet.railwaygraph.model.NormalRailwayLinkTransition;
import org.hisrc.tenet.railwaygraph.model.RailwayLinkTransition;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.jgrapht.EdgeFactory;

public class NormalRailwayLinkTransitionEdgeFactory
		implements EdgeFactory<RailwayLink, RailwayLinkTransition>, Serializable {

	private static final long serialVersionUID = 3021799234739673560L;
	public static final EdgeFactory<RailwayLink, RailwayLinkTransition> INSTANCE = new NormalRailwayLinkTransitionEdgeFactory();

	@Override
	public RailwayLinkTransition createEdge(RailwayLink start, RailwayLink end) {
		return new NormalRailwayLinkTransition(start, end);
	}
}