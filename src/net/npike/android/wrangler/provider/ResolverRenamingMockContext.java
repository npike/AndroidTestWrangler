package net.npike.android.wrangler.provider;

import java.io.File;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;

public class ResolverRenamingMockContext extends RenamingDelegatingContext {

	/**
	 * The renaming prefix.
	 */
	private static final String PREFIX = "test.";

	/**
	 * The resolver.
	 */
	private MockContentResolver mResolver;

	/**
	 * Constructor.
	 * 
	 * @param context
	 */
	public ResolverRenamingMockContext(Context context, MockContentResolver resolver) {
		super(new DelegatedMockContext(context), context, "mock_");
		mResolver = resolver;
	}

	@Override
	public ContentResolver getContentResolver() {
		return mResolver;
	}
	/**
	 * The default MockContext has some things stubbed out that we need to test
	 * an IntentService. This mock object implements only the necessary methods.
	 * 
	 * @author npike
	 * 
	 */
	 public static class DelegatedMockContext extends MockContext {
		private Context mDelegatedContext;

		public DelegatedMockContext(Context context) {
			mDelegatedContext = context;
		}

		@Override
		public String getPackageName() {
			return "";
		}

		@Override
		public SharedPreferences getSharedPreferences(String name, int mode) {
			return mDelegatedContext.getSharedPreferences(PREFIX + name, mode);
		}
		
		@Override
        public Resources getResources() {
            return mDelegatedContext.getResources();
        }

        @Override
        public File getDir(String name, int mode) {
            // name the directory so the directory will be separated from
            // one created through the regular Context
            return mDelegatedContext.getDir("mockcontext2_" + name, mode);
        }

        @Override
        public Context getApplicationContext() {
            return this;
        }
	}
}
