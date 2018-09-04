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

import booker.building_data.ListValue;
import booker.building_data.NamespaceReferences;
import otis.lexical.AndParser;
import otis.lexical.CannotParseException;
import otis.lexical.CharacterParser;
import otis.lexical.InputSequence;
import otis.lexical.OptionalParser;
import otis.lexical.Parser;

public class ListParser implements SamParser {
	
	private Parser openParenthesis;
	private Parser closedParenthesis;
	private Parser elementDelimiter;

	public ListParser() {
				
		Parser optionalDelimiter = new OptionalParser(new DelimiterParser());
		openParenthesis = new AndParser(new Parser[]{new CharacterParser("("),optionalDelimiter});
		elementDelimiter = new AndParser(new Parser[]{optionalDelimiter, new CharacterParser(","),optionalDelimiter});
		closedParenthesis = new AndParser(new Parser[]{optionalDelimiter, new CharacterParser(")")});
	}

	public ListValue parse(InputSequence in, NamespaceReferences references)
			throws CannotParseException {
		ListValue list = new ListValue();
		openParenthesis.parse(in);
		FieldValueParser fieldValueParser = new FieldValueParser();
		list.add(fieldValueParser.parse(in, references));
		boolean continueParsing = true;
		while (continueParsing) {
			try {
				closedParenthesis.parse(in);
				continueParsing = false;
			} catch (CannotParseException e1) {
				elementDelimiter.parse(in);
				list.add(fieldValueParser.parse(in, references));
			}
		}
		return list;
	}

}