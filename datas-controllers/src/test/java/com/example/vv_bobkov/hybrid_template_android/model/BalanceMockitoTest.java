package com.example.vv_bobkov.hybrid_template_android.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.cglib.proxy.Factory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vv-bobkov on 02.08.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class BalanceMockitoTest {

    // Context of the app under test.
    @Mock
    Context fakeContext;

    private Balance[] excBalances, actBalances;
    private String[] currencys = {"RU", "USA", "EUR", "BEL"};
    private Float[] amounts = {100f, 2003f, 299f, 49833f};

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        excBalances = new Balance[currencys.length];
        int pos = 0;

        for (String currency :
                currencys) {
            excBalances[pos] = new Balance();
            excBalances[pos].setCurrency(currency);
            excBalances[pos].setAmount(amounts[pos]);
            pos++;
        }
    }

    @Test
    public void setCurrencyTest() {

        actBalances = new Balance[excBalances.length];
        int pos = 0;

        for (String currency :
                currencys) {
            actBalances[pos] = new Balance();
            actBalances[pos].setCurrency(currency);
            actBalances[pos].setAmount(amounts[pos]);
            pos++;
        }

        pos = 0;
        for (Balance balance :
                actBalances) {
            Assert.assertEquals(excBalances[pos], balance);
            pos++;
        }
    }


    /**
     * Service method of forming the message about the mismatch.
     *
     * @param cleanMessage
     * @param expected
     * @param actual
     * @throws AssertionError
     */
    private static void comparisonFailure(final String cleanMessage,
                                          final Object expected,
                                          final Object actual)
            throws AssertionError {

        String formatted = "";
        if (cleanMessage != null && !cleanMessage.equals("")) {
            formatted = cleanMessage + " ";
        }
        String expectedString = String.valueOf(expected);
        String actualString = String.valueOf(actual);
        if (expectedString.equals(actualString)) {
            formatted += "expected: "
                    + formatClassAndValue(expected, expectedString)
                    + " but was: " + formatClassAndValue(actual, actualString);
        } else {
            formatted += "expected:<" + expectedString + "> but was:<"
                    + actualString + ">";
        }
        if (formatted == null) {
            throw new AssertionError();
        }
        throw new AssertionError(formatted);
    }

    /**
     * Service method formatting method of the name class.
     *
     * @param value
     * @param valueString
     * @return
     */
    private static String formatClassAndValue(Object value, String valueString) {
        String className = value == null ? "null" : value.getClass().getName();
        return className + "<" + valueString + ">";
    }

    /**
     * Service method of comparison balances
     *
     * @param expBalance
     * @param actBalance
     */
    private static void assertEquals(final Balance expBalance,
                                     final Balance actBalance) {
        boolean isComparisonFailure = false;
        String cleanMessage = "The balance is null - ";
        if (expBalance == null || actBalance == null) {
            isComparisonFailure = true;
        } else {
            if (expBalance.getAmount() != actBalance.getAmount()
                    || !expBalance.getCurrency().equals(actBalance.getCurrency())) {
                cleanMessage = "The balance on exception currency" + expBalance.getCurrency() + " isn't equals - ";
                isComparisonFailure = true;
            }
            if (!isComparisonFailure) return;
        }
        if (isComparisonFailure) {

            comparisonFailure(cleanMessage,
                    expBalance,
                    actBalance);
        }
    }
}
