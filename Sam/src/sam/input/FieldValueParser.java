/*
 *
 *  Copyright (C) 2017 Aaron Powers
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package sam.input;

import booker.building_data.FieldValue;
import booker.building_data.NamespaceReferences;
import otis.lexical.CannotParseException;
import otis.lexical.InputSequence;
import otis.lexical.SyntaxException;

public class FieldValueParser implements SamParser {

	private NumericValueParser numericParser;
	private StringValueParser stringParser;
	private ListParser listParser;

	public FieldValueParser() {
		numericParser = new NumericValueParser();
		stringParser = new StringValueParser();
		listParser = new ListParser();
	}

	public FieldValue parse(InputSequence in, NamespaceReferences objects) {
		try {
			return listParser.parse(in, objects);
		} catch (CannotParseException e1) {
			try {
				return numericParser.parse(in, objects);
			} catch (CannotParseException e2) {
				try {
					return stringParser.parse(in, objects);
				} catch (CannotParseException e3) {
					throw new SyntaxException("Could not parse field value on line " + in.lineNumber() + ".");
				}
			}
		}
	}

}
