package net.npike.android.wrangler.activity;

import net.npike.android.wrangler.MockableApplication;
import net.npike.android.wrangler.provider.HashMapMockContentProvider;
import net.npike.android.wrangler.provider.ResolverRenamingMockContext;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentProvider;
import android.test.mock.MockContentResolver;
import android.util.Log;

public class BootstrapActivity extends Activity {

	private static final String TAG = "BootstrapActivity";
	private MockContentResolver mMockContentResolver;
	private IsolatedContext mProviderContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mMockContentResolver = new MockContentResolver();

		RenamingDelegatingContext targetContextWrapper = new ResolverRenamingMockContext(this, mMockContentResolver);
		mProviderContext = new IsolatedContext(mMockContentResolver, targetContextWrapper);
		
		((MockableApplication) getApplication()).setContentResolver(mMockContentResolver);

		Log.d(TAG, "Ready.");
	}
	
	public void injectMockProvider(final String authority, MockContentProvider provider) {
		Log.d(TAG, "injectMockProvider");
		mMockContentResolver.addProvider(authority, provider);
	}

	public void startActivityUnderTest(final String targetPackage, Class<?> testActivity) {
		Log.d(TAG, "Starting activity under test... "+testActivity.getName());
		Intent intent = new Intent(Intent.ACTION_MAIN);
		launchActivityWithIntent(targetPackage, testActivity, intent);
	}

	private final void launchActivityWithIntent(String pkg, Class<?> activityCls, Intent intent) {
		intent.setClassName(pkg, activityCls.getName());
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}
