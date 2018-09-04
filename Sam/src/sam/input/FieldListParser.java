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

import java.util.ArrayList;

import booker.building_data.BookerField;
import booker.building_data.FieldList;
import booker.building_data.NamespaceReferences;
import otis.lexical.AndParser;
import otis.lexical.CannotParseException;
import otis.lexical.InputSequence;
import otis.lexical.OptionalParser;
import otis.lexical.Parser;
import otis.lexical.RequiredParser;
import otis.lexical.StringParser;
import otis.lexical.UpdateListener;

public class FieldListParser implements SamParser {

	private FieldParser fieldParser;
	private Parser optionalDelimiter;
	private Parser requiredDelimiter;
	private Parser fieldListEnd;

	public FieldListParser(ArrayList<UpdateListener> listeners) {
		String err = "Delimiter required between field definitions.";
		fieldParser = new FieldParser(listeners);
		DelimiterParser delimiterParser = new DelimiterParser();
		optionalDelimiter = new OptionalParser(delimiterParser);
		requiredDelimiter = RequiredParser.wrap((delimiterParser), err);
		StringParser terminator = new StringParser(";");
		fieldListEnd = new AndParser(new Parser[] { optionalDelimiter, terminator });
	}

	@Override
	public FieldList parse(InputSequence in,
			NamespaceReferences objects) throws CannotParseException {
		
		FieldList fields = new FieldList();

		boolean continueParsing = true;
		while (continueParsing) {
			try {
				fieldListEnd.parse(in);
				continueParsing = false;
			} catch (CannotParseException e) {
				BookerField field = fieldParser.parse(in, objects);
				fields.add(field);
				requiredDelimiter.parse(in);
			}
		}
		return fields;
	}

}
