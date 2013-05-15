/**
 * Copyright 2013 GOT5
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.got5.tapestry5.cdi.test.components;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Property;
import org.got5.tapestry5.cdi.beans.NamedPojo;
import org.got5.tapestry5.cdi.beans.Pojo;

public class DumbComponent {

	@Inject
	@Property
	private Pojo pojo;
	
	@Inject
	@Property
	private NamedPojo namedPojo;
	
}
