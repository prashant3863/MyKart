package com.example.chi6rag.mykart.models;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderTest {
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
        thatOrder.updateStateByComparingWith(order);
        assertEquals(thatOrder.state, order.state);
    }

    @Test
    public void retainsItsStateWhenItIsUpdatedByComparingItsStateWithNull() {
        Order order = new Order(null, null, "cart");
        order.updateStateByComparingWith(null);
        assertEquals(order.state, "cart");
    }

    @Test
    public void hasStateNullWhenItsStateIsUpdatedByComparingItWithOtherOrderWithAbsentState() {
        Order order = new Order(null, null, "cart");
        Order thatOrder = new Order(null, null, null);
        order.updateStateByComparingWith(thatOrder);
        assertEquals(null, order.state);
    }

    @Test
    public void hasNewStateWhenItsStateIsUpdatedByComparingItWithOrderOfNewState() {
        String oldState = "cart";
        String newState = "payment";
        Order order = new Order(null, null, oldState);
        Order thatOrder = new Order(null, null, newState);

        order.updateStateByComparingWith(thatOrder);

        assertEquals(newState, order.state);
    }
}
