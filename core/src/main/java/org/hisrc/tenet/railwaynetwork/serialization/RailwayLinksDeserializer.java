package org.hisrc.tenet.railwaynetwork.serialization;

import java.io.InputStream;

import org.hisrc.tenet.base.serialization.JacksonFeatureDeserializer;
import org.hisrc.tenet.geometry.model.LineString;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink;
import org.hisrc.tenet.railwaynetwork.model.RailwayLink.Properties;
import org.hisrc.tenet.railwaynetwork.model.RailwayLinks;

public class RailwayLinksDeserializer
		extends JacksonFeatureDeserializer<RailwayLinks, RailwayLink, LineString, double[][], Properties> {

	public RailwayLinksDeserializer(InputStream inputStream) {
		super(RailwayLinks.class, inputStream);
	}
}
