/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.store;

import org.junit.Test;
import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessagingException;
import org.springframework.integration.message.MessageBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Iwein Fuld
 */
public class SimpleMessageStoreTest {

	@Test
	public void shouldRetainMessage() {
		SimpleMessageStore store = new SimpleMessageStore();
		Message<String> testMessage1 = MessageBuilder.withPayload("foo").build();
		store.put(testMessage1);
		assertThat((Message<String>) store.get(testMessage1.getHeaders().getId()), is(testMessage1));
	}

	@Test(expected = MessagingException.class)
	public void shouldNotHoldMoreThanCapacity() {
		SimpleMessageStore store = new SimpleMessageStore(1);
		Message<String> testMessage1 = MessageBuilder.withPayload("foo").build();
		Message<String> testMessage2 = MessageBuilder.withPayload("bar").build();
		store.put(testMessage1);
		store.put(testMessage2);
	}

	@Test
	public void shouldHoldCapacityExactly() {
		SimpleMessageStore store = new SimpleMessageStore(2);
		Message<String> testMessage1 = MessageBuilder.withPayload("foo").build();
		Message<String> testMessage2 = MessageBuilder.withPayload("bar").build();
		store.put(testMessage1);
		store.put(testMessage2);
	}
}