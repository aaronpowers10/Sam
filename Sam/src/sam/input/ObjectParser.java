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

import booker.building_data.BookerObject;
import booker.building_data.NamespaceReferences;
import booker.io.BuildingParser;
import otis.lexical.CannotParseException;
import otis.lexical.InputSequence;
import otis.lexical.SyntaxException;
import otis.lexical.UpdateListener;

public class ObjectParser extends BuildingParser<BookerObject> {

	private DelimiterParser delimiterParser;
	private WordParser wordParser;
	private BookerObject object;
	private FieldListParser fieldsParser;

	public ObjectParser(ArrayList<UpdateListener> listeners) {
		super(listeners);
		delimiterParser = new DelimiterParser();
		wordParser = new WordParser();
		fieldsParser = new FieldListParser(listeners);
	}

	public BookerObject parse(InputSequence in, NamespaceReferences<BookerObject> objects) {

		try {
			String name = wordParser.parse(in);
			delimiterParser.parse(in);
			String type = wordParser.parse(in);
			delimiterParser.parse(in);
			object = new BookerObject(name, type);
		} catch (CannotParseException e) {
			throw new SyntaxException("Could not parse object on line " + in.lineNumber() + ".");
		}

		try {
			object.setFields(fieldsParser.parse(in, objects));
		} catch (CannotParseException e) {
			throw new SyntaxException("Syntax error on line number " + in.lineNumber() + ".");
		}

		return object;
	}

	@Override
	public String message() {
		return "Reading object '" + object.name() + "' of type '" + object.type() + "'.";
	}

}
