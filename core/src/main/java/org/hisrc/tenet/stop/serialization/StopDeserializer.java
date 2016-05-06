package org.hisrc.tenet.stop.serialization;

import java.io.InputStream;

import org.hisrc.tenet.base.serialization.JacksonCsvDeserializer;
import org.hisrc.tenet.stop.model.Stop;

public class StopDeserializer extends JacksonCsvDeserializer<Stop> {

	public StopDeserializer(InputStream inputStream) {
		super(Stop.class, inputStream);
	}
}
