package com.example.chi6rag.mykart.models;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderTest {
    private Context context;

    @Before
    public void beforeEach() {
        this.context = mock(Context.class);

        SharedPreferences sharedPreferences = mock(SharedPreferences.class);
        SharedPreferences.Editor sharedPreferencesEditor = mock(SharedPreferences.Editor.class);
        setupSharedPreferencesStubs(sharedPreferences, sharedPreferencesEditor);
    }

    @Test
    public void withAbsentStateDoesNotHaveTheSameStateAsOtherOrderWithPresentState() {
        Order order = new Order(null, null, null);
        Order thatOrder = new Order(null, null, "cart");
        assertTrue(order.doesNotHaveSameStateAs(thatOrder));
    }

    @Test
    public void withAbsentStateHasTheSameStateAsOtherOrderWithAbsentState() {
        Order order = new Order(null, null, null);
        Order thatOrder = new Order(null, null, null);
        assertFalse(order.doesNotHaveSameStateAs(thatOrder));
    }

    @Test
    public void withPresentStateDoesNotHaveTheSameStateAsOtherOrderWithAbsentState() {
        Order order = new Order(null, null, "address");
        Order thatOrder = new Order(null, null, null);
        assertTrue(order.doesNotHaveSameStateAs(thatOrder));
    }

    @Test
    public void hasSameStateAsOtherGivenTheyAreExactlySame() {
        Order order = new Order(null, null, "payment");
        Order thatOrder = new Order(null, null, "payment");
        assertFalse(order.doesNotHaveSameStateAs(thatOrder));
    }

    @Test
    public void retainsItsStateWhenItIsUpdatedByComparingItsStateWithOtherOrderOfSameState() {
        Order order = new Order(null, null, "cart");
        Order thatOrder = new Order(null, null, "cart");
        thatOrder.updateStateByComparingWith(order, this.context);
        assertEquals(thatOrder.state, order.state);
    }

    @Test
    public void retainsItsStateWhenItIsUpdatedByComparingItsStateWithNull() {
        Order order = new Order(null, null, "cart");
        order.updateStateByComparingWith(null, this.context);
        assertEquals(order.state, "cart");
    }

    @Test
    public void hasStateNullWhenItsStateIsUpdatedByComparingItWithOtherOrderWithAbsentState() {
        Order order = new Order(null, null, "cart");
        Order thatOrder = new Order(null, null, null);
        order.updateStateByComparingWith(thatOrder, this.context);
        assertEquals(null, order.state);
    }

    @Test
    public void hasNewStateWhenItsStateIsUpdatedByComparingItWithOrderOfNewState() {
        String oldState = "cart";
        String newState = "payment";
        Order order = new Order(null, null, oldState);
        Order thatOrder = new Order(null, null, newState);
        order.updateStateByComparingWith(thatOrder, this.context);
        assertEquals(newState, order.state);
    }

    private void setupSharedPreferencesStubs(SharedPreferences sharedPreferences, SharedPreferences.Editor sharedPreferencesEditor) {
        when(this.context.getSharedPreferences(Order.TAG, Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences);

        when(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor);
        when(sharedPreferencesEditor.putString(Order.STATUS, "cart")).thenReturn(sharedPreferencesEditor);
        when(sharedPreferencesEditor.putString(Order.STATUS, null)).thenReturn(sharedPreferencesEditor);
        when(sharedPreferencesEditor.putString(Order.STATUS, "payment")).thenReturn(sharedPreferencesEditor);
        when(sharedPreferencesEditor.commit()).thenReturn(true);
    }
}
