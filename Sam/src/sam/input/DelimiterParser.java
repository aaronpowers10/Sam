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

import otis.lexical.CannotParseException;
import otis.lexical.CharacterParser;
import otis.lexical.FromToParser;
import otis.lexical.InputSequence;
import otis.lexical.OneOrMoreParser;
import otis.lexical.OrParser;
import otis.lexical.Parser;
import otis.lexical.StringParser;

public class DelimiterParser implements Parser {
	private Parser delimiterParser;

	public DelimiterParser() {
		CharacterParser bangParser = new CharacterParser("!");
		StringParser newLineParser = new StringParser("\r\n");
		Parser commentEndParser = newLineParser;
		FromToParser commentParser = new FromToParser(bangParser, commentEndParser);

		Parser[] delimiters = new Parser[4];
		delimiters[0] = new CharacterParser(" ");
		delimiters[1] = new CharacterParser("\t");
		delimiters[2] = newLineParser;
		delimiters[3] = commentParser;

		delimiterParser = new OneOrMoreParser(new OrParser(delimiters));
	}

	@Override
	public String parse(InputSequence in) throws CannotParseException {

		return delimiterParser.parse(in);

	}

}
