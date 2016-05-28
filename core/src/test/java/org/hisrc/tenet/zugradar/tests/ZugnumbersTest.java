package org.hisrc.tenet.zugradar.tests;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;

import org.junit.Ignore;
import org.junit.Test;

public class ZugnumbersTest {

	@Ignore
	@Test
	public void writesZugnumbers() throws IOException {
		request(LocalDate.of(2016, 1, 1));
	}

	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
	public static final String URL_PATTERN = "http://www.apps-bahn.de/bin/livemap/query-livemap.exe/dny?L=vs_livefahrplan&performLocating=1&performFixedLocating=1&look_requesttime={1}&livemapRequest=yes&ts={0}";

	public void request(LocalDate localDate) throws IOException {
		final Map<String, String> trainIds = new HashMap<>();

		System.out.println("Start.");
		for (int hour = 0; hour < 24; hour++) {
			for (int minute = 0; minute < 60; minute += 3) {
				final String url = MessageFormat.format(URL_PATTERN, DATE_FORMATTER.format(localDate),
						TIME_FORMATTER.format(LocalTime.of(hour, minute, 0)));
				try (InputStream is = new URL(url).openStream()) {
					JsonArray results = Json.createReader(is).readArray();
					JsonArray trainsArray = results.getJsonArray(0);
					for (int index = 0; index < trainsArray.size() - 1; index++) {
						final JsonArray trainArray = trainsArray.getJsonArray(index);
						final String trainNumber = trainArray.getString(0);
						final String trainId = trainArray.getString(3);
						final String departure = trainArray.getString(16, null);
						if (trainIds.putIfAbsent(trainNumber, trainId) == null) {
							System.out.println(MessageFormat.format("{0},{1}", trainNumber, trainId));
						}
					}
				}
			}
		}
		System.out.println(trainIds.size());
	}
}
