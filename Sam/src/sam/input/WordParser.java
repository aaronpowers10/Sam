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

import otis.lexical.BetweenParser;
import otis.lexical.CannotParseException;
import otis.lexical.CharacterParser;
import otis.lexical.InputSequence;
import otis.lexical.Parser;

public class WordParser {
	
	private Parser parser;

	public WordParser() {
		initializeParsers();
	}

	private void initializeParsers() {
		CharacterParser quoteParser = new CharacterParser("'");
		parser = new BetweenParser(quoteParser, quoteParser);

	}

	public String parse(InputSequence in) throws CannotParseException {
		return parser.parse(in);
	}

}
