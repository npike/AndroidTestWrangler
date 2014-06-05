package net.npike.android.wrangler;

import android.app.Application;
import android.content.ContentResolver;

public class MockableApplication extends Application {
	private ContentResolver mContentResolver;

	public void setContentResolver(ContentResolver resolver) {
		mContentResolver = resolver;
	}

	@Override
	public ContentResolver getContentResolver() {
		if (mContentResolver != null && BuildConfig.DEBUG) {
			return mContentResolver;
		}
		return super.getContentResolver();
	}
}
