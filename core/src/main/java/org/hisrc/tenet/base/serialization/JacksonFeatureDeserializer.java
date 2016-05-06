package org.hisrc.tenet.base.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.lang3.Validate;
import org.hisrc.tenet.feature.model.Feature;
import org.hisrc.tenet.feature.model.FeatureCollection;
import org.hisrc.tenet.geometry.model.Geometry;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonFeatureDeserializer<O extends FeatureCollection<I, G, C, P>, I extends Feature<G, C, P>, G extends Geometry<C>, C, P>
		implements Supplier<Stream<? extends I>> {

	private final InputStream inputStream;
	private Class<O> featureCollectionClass;

	public JacksonFeatureDeserializer(Class<O> baseObjectClass, InputStream inputStream) {
		Validate.notNull(baseObjectClass);
		Validate.notNull(inputStream);
		this.featureCollectionClass = baseObjectClass;
		this.inputStream = inputStream;
	}

	@Override
	public Stream<? extends I> get() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			O featureCollection = mapper.readValue(this.inputStream, this.featureCollectionClass);
			return featureCollection.getFeatures().stream();
		} catch (Exception ex) {
			throw new IllegalStateException("Could not read deserialize the object.", ex);
		} finally {
			try {
				inputStream.close();
			} catch (IOException ignored) {
			}
		}
	}
}
