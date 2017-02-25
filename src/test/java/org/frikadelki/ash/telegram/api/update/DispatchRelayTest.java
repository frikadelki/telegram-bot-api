package org.frikadelki.ash.telegram.api.update;

import lombok.val;
import org.frikadelki.ash.telegram.api.message.TgmMessage;
import org.junit.Test;

import static org.mockito.Mockito.*;


public final class DispatchRelayTest {

	private static TgmUpdateHandler makeUpdateHandlerFilterMock(final TgmUpdateFilter filter) {
		val acceptAllUpdateHandler = mock(TgmUpdateHandler.class);
		when(acceptAllUpdateHandler.getFilter()).thenReturn(filter);
		return acceptAllUpdateHandler;
	}

	@Test
	public void testAcceptAny() {
		val acceptAllMockHandler = makeUpdateHandlerFilterMock(TgmUpdateFilters.ACCEPT_ALL);

		val relay = new TgmUpdateDispatchRelay();
		relay.addHandler(acceptAllMockHandler);
		relay.dispatchUpdate(mock(TgmUpdateDispatchContext.class), TgmUpdate.builder()
				.newMessage(TgmMessage.builder().text("Hello").build())
				.build());
	}
}
