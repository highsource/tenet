package org.hisrc.tenet.base.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class JacksonCsvDeserializer<I> implements Supplier<Stream<? extends I>> {
	private final InputStream inputStream;
	private Class<I> itemClass;

	public JacksonCsvDeserializer(Class<I> itemClass, InputStream inputStream) {
		Validate.notNull(itemClass);
		Validate.notNull(inputStream);
		this.itemClass = itemClass;
		this.inputStream = inputStream;
	}

	@Override
	public Stream<? extends I> get() {
		final CsvMapper mapper = new CsvMapper();
		final CsvSchema schema = mapper.schemaFor(itemClass).withHeader().withColumnSeparator(';');
		final ObjectReader reader = mapper.readerFor(itemClass).with(schema);
		try {
			Iterator<I> readValues = reader.readValues(inputStream);
			List<I> values = new LinkedList<>();
			while (readValues.hasNext()) {
				I next = readValues.next();
				values.add(next);
			}
			return values.stream();
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
