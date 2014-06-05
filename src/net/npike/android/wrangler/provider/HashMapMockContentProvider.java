package net.npike.android.wrangler.provider;

import java.util.HashMap;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.test.mock.MockContentProvider;
import android.util.Log;

public class HashMapMockContentProvider extends MockContentProvider {
		private HashMap<Uri, Cursor> expectedResults = new HashMap<Uri, Cursor>();

		public void addQueryResult(Uri uriIn, Cursor expectedResult) {
			expectedResults.put(uriIn, expectedResult);
		}

		@Override
		public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
			Log.d(getClass().getName(), "query "+uri);
			Cursor expectedCursor = expectedResults.get(uri);
			return expectedCursor != null ? expectedCursor : new MatrixCursor(new String[]{BaseColumns._ID});
		}
	}