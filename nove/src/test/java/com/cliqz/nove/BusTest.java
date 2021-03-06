package com.cliqz.nove;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Stefano Pacifici
 */
@SuppressWarnings("UnnecessaryBoxing")
public class BusTest {

    private Bus bus;

    @Before
    public void init() {
        bus = new Bus();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowTryingToRegisterNullObject() {
        bus.register(null);
    }

    @Test(expected = DispatcherNotFoundException.class)
    public void shouldThrowIfDispatcherDoesNotExists() {
        bus.register(this);
    }

    @Test(expected = SubclassRegistrationException.class)
    public void shouldThrowTryingToRegisterFromSuper() {
        new BadSubSubscriber(bus);
    }

    @Test
    public void shouldNotThrowForClassEnforcedSuper() {
        new NiceSubSubscriber(bus);
    }

    @Test
    public void shouldDeliverMessages() {
        Dispatcher dispatcher = mock(Dispatcher.class);
        when(dispatcher.canHandleMessage(any(Integer.class))).thenReturn(true);
        bus.addTestDispatcher(dispatcher);
        bus.post(Integer.valueOf(5));
        verify(dispatcher).post(Integer.valueOf(5));
    }

    @Test
    public void shouldNotDeliverUnexpectedTypes() {
        Dispatcher dispatcher = mock(Dispatcher.class);
        reset(dispatcher);
        bus.post(Long.valueOf(4));
        verifyZeroInteractions(dispatcher);
    }

    @Test
    public void shouldLoadADispatcher() {
        final String msg = "Hello, nove!!!";
        final RegularSubscriber subscriber = new RegularSubscriber(bus);
        bus.post(msg);
        assertThat(subscriber.msg, is(notNullValue()));
        assertThat(subscriber.msg, equalTo(msg));
    }

}
