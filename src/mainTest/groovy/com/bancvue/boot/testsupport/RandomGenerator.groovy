/*
 * Copyright 2014 BancVue, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.boot.testsupport

import org.fluttercode.datafactory.impl.DataFactory

public class RandomGenerator {

	private static Random random = new Random();
	private static DataFactory df = new DataFactory();

	static {
		init(getSeed());
	}

	private static long getSeed() {
		return random.nextLong();
	}

	public static void init(long seed) {
		random = new Random(seed);
		df = new DataFactory();
		df.randomize(random.nextInt());
	}

	public static DataFactory getDataFactory() {
		return df;
	}

	public static long id() {
		long id = random.nextInt();
		if (id < 0) {
			id *= -1;
		} else if (id == 0) {
			id = 1
		}
		return id;
	}

	public static int number() {
		return random.nextInt();
	}

	public static String text(int length) {
		return df.getRandomText(length);
	}

	public static String zipCode() {
		return df.getNumberText(5);
	}

	public static String phoneNumber() {
		return df.getNumberText(3) + "-" + df.getNumberText(3) + "-" + df.getNumberText(4);
	}

	public static String numericText(int digits) {
		return df.getNumberText(digits);
	}

	public static String address() {
		return df.getNumberText(5) + " " + df.getRandomText(8) + " " + item("St", "Rd", "Ave");
	}

	public static <T> T item(List<T> items) {
		return df.getItem(items);
	}

	public static <T> T item(T... items) {
		return df.getItem(Arrays.asList(items));
	}

}