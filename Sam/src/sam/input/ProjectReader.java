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

import booker.building_data.AddToEndStrategy;
import booker.building_data.BookerObject;
import booker.building_data.BookerProject;
import booker.io.ProjectLoader;
import booker.io.ProjectReadCompleteListener;
import otis.lexical.CannotParseException;
import otis.lexical.EndOfSequenceException;
import otis.lexical.ErrorListener;
import otis.lexical.InputSequence;
import otis.lexical.OptionalParser;
import otis.lexical.SyntaxException;
import otis.lexical.UpdateListener;

public class ProjectReader implements ProjectLoader {
	
	private ArrayList<UpdateListener> updateListeners;
	private ArrayList<ErrorListener> errorListeners;
	private ArrayList<ProjectReadCompleteListener> readCompleteListeners;
	private BookerProject project;

	public ProjectReader() {
		updateListeners = new ArrayList<UpdateListener>();
		readCompleteListeners = new ArrayList<ProjectReadCompleteListener>();
		errorListeners = new ArrayList<ErrorListener>();
		project = new BookerProject();
	}

	private void notifyUpdateListeners(String message) {
		for (int i = 0; i < updateListeners.size(); i++) {
			updateListeners.get(i).update(message);
		}
	}
	
	private void notifyError(String message) {
		for (int i = 0; i < errorListeners.size(); i++) {
			errorListeners.get(i).error(message);
		}
	}

	@Override
	public void load(String fileName) {
		notifyUpdateListeners("Reading input file '" + fileName + "'.");

		notifyUpdateListeners("Initial file scan.");
		InputSequence in = new InputSequence(fileName, 999);
		OptionalParser optionalDelimiter = new OptionalParser(new DelimiterParser());
		optionalDelimiter.parse(in);

		ObjectParser objectParser = new ObjectParser(updateListeners);

		boolean continueParsing = true;
		while (continueParsing) {
			try {
				project.add((BookerObject)objectParser.parseWithMessage(in, project),
						new AddToEndStrategy<BookerObject>());
				optionalDelimiter.parse(in);
			} catch (EndOfSequenceException e1) {
				continueParsing = false;
			} catch (CannotParseException e2) {
				continueParsing = false;
			} catch (SyntaxException e3){
				continueParsing = false;
				notifyError(e3.getMessage());
			}

		}
		notifyUpdateListeners("Finished reading input file '" + fileName + "'.");
		
		for(int i=0;i<readCompleteListeners.size();i++){
			readCompleteListeners.get(i).projectReadComplete(project);
		}
		
	}

	@Override
	public void addProjectReadCompleteListener(ProjectReadCompleteListener readCompleteListener) {
		readCompleteListeners.add(readCompleteListener);
		
	}

	@Override
	public void addUpdateListener(UpdateListener updateListener) {
		updateListeners.add(updateListener);
	}
	
	public void addErrorListener(ErrorListener errorListener){
		errorListeners.add(errorListener);
	}

}
