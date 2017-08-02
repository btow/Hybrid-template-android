package com.example.vv_bobkov.hybrid_template_android.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BalanceInstrumentedTest {

    private static final String S_TABLE_QIWI_USERS = "qiwi_users";
    private final String DB_NAME = "qiwisUsers";

    private final String TABLE_QIWI_USERS = "qiwi_users",
            TABLE_QIWI_USERS_ID = "_id",
            TABLE_QIWI_USERS_NAME = "name";
    private String sqlCommand = "create table " + TABLE_QIWI_USERS + " ("
            + TABLE_QIWI_USERS_ID + " integer primary key, "
            + TABLE_QIWI_USERS_NAME + " text)";
    private SQLiteDatabase excSqLiteDatabase;
    private List<QiwiUsers> excDataset;
    private Context appContext;


    @Before
    public void setUp() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();

        excDataset = new ArrayList<>();
        excDataset.add(new QiwiUsers(0, "Marisa"));
        excDataset.add(new QiwiUsers(1, "Madeline"));
        excDataset.add(new QiwiUsers(2, "Galloway"));
        excDataset.add(new QiwiUsers(3, "Sophie"));
        excDataset.add(new QiwiUsers(4, "Lori"));
        excDataset.add(new QiwiUsers(5, "Becker"));
        excDataset.add(new QiwiUsers(6, "Martha"));
        excDataset.add(new QiwiUsers(7, "Cohen"));
        excDataset.add(new QiwiUsers(8, "Duffy"));
        excDataset.add(new QiwiUsers(9, "Russell"));

        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(appContext, DB_NAME, null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(sqlCommand);
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        excSqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        excSqLiteDatabase.execSQL(sqlCommand);

        ContentValues cv = new ContentValues();

        for (QiwiUsers expQiwiUser :
                excDataset) {
            cv.clear();
            cv.put(TABLE_QIWI_USERS_ID, expQiwiUser.getId());
            cv.put(TABLE_QIWI_USERS_NAME, expQiwiUser.getName());
            excSqLiteDatabase.insert(TABLE_QIWI_USERS, null, cv);
        }
    }

    @Test
    public void useAppContext() throws Exception {

        Assert.assertEquals("com.example.vv_bobkov.hybrid_template_android.model", appContext.getPackageName());
    }

    @Test
    public void ControllerDBMocTest() {

        ControllerDB actControllerDB = new ControllerDB();
        Assert.assertEquals(DB_NAME, actControllerDB.getDbName());
    }

    /**
     * Service method of forming the message about the mismatch.
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
     * @param value
     * @param valueString
     * @return
     */
    private static String formatClassAndValue(Object value, String valueString) {
        String className = value == null ? "null" : value.getClass().getName();
        return className + "<" + valueString + ">";
    }

    /**
     * Service method of comparison lists
     * @param expDataset
     * @param actDataset
     */
    private static void assertEquals(final List<QiwiUsers> expDataset,
                                     final List<QiwiUsers> actDataset) {
        boolean isComparisonFailure = false;
        String cleanMessage = "The dataset is null - ";
        if (expDataset == null || actDataset == null) {
            isComparisonFailure = true;
        } else if (expDataset.size() != actDataset.size()) {
            cleanMessage = "The sizes of datasets isn't equals - ";
            isComparisonFailure = true;
        } else if (expDataset.size() == 0) {
            cleanMessage = "The size expeted and actual datasets is 0 - ";
            isComparisonFailure = true;
        } else {
            int index = 0;
            for (QiwiUsers expQiwiUser :
                    expDataset) {
                QiwiUsers actQiwiUser = actDataset.get(index);
                if (expQiwiUser.getId() != actQiwiUser.getId()
                        || !expQiwiUser.getName().equals(actQiwiUser.getName())) {
                    cleanMessage = "The datasets in position [" + index + "] isn't equals - ";
                    isComparisonFailure = true;
                }
                index++;
            }
            if (!isComparisonFailure) return;
        }
        if (isComparisonFailure) {

            comparisonFailure(cleanMessage,
                    expDataset,
                    actDataset);
        }
    }

    /**
     * Service method of comparing databases.
     * @param expDB
     * @param actDB
     */
    private static void assertEquals(final SQLiteDatabase expDB,
                                     final SQLiteDatabase actDB) {
        boolean isComparisonFailure = false;
        String cleanMessage = "The ControllerDB is null - ";
        if (expDB == null || actDB == null) {
            isComparisonFailure = true;
        } else if (expDB.getVersion() != actDB.getVersion()) {
            cleanMessage = "The version of databases isn't equals - ";
            isComparisonFailure = true;
        } else {
            Cursor expCursor = expDB.query(S_TABLE_QIWI_USERS, null, null, null, null, null, null);
            Cursor actCursor = actDB.query(S_TABLE_QIWI_USERS, null, null, null, null, null, null);

            if (expCursor == null || actCursor == null) {
                cleanMessage = "The table " + S_TABLE_QIWI_USERS + " in DataBase does not exist - ";
                isComparisonFailure = true;
            } else if (!expCursor.moveToFirst() || !actCursor.moveToFirst()) {
                cleanMessage = "The records in table " + S_TABLE_QIWI_USERS + " of DataBase does not exist - ";
                isComparisonFailure = true;
            } else {
                do {
                    int index = expCursor.getInt(0);
                    if (index != actCursor.getInt(0)
                            || !expCursor.getString(1).equals(actCursor.getString(1))) {
                        cleanMessage = "The records of DataBases table " + S_TABLE_QIWI_USERS + " in position [" + index + "] isn't equals - ";
                        isComparisonFailure = true;
                    }
                    if (!actCursor.moveToNext() && actCursor.getCount() < index) {
                        cleanMessage = "In the actual database there is no corresponding position [" + ++index + "] recording - ";
                        isComparisonFailure = true;
                    }
                } while (expCursor.moveToNext());
                if (!isComparisonFailure) return;
            }
            if (isComparisonFailure) {

                comparisonFailure(cleanMessage,
                        expDB,
                        actDB);
            }
        }
    }
}
