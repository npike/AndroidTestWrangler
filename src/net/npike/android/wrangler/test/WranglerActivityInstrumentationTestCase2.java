package net.npike.android.wrangler.test;

import net.npike.android.wrangler.activity.BootstrapActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class WranglerActivityInstrumentationTestCase2<T extends Activity> extends ActivityInstrumentationTestCase2<T> {
	private Class<T> mRealActivityClass;

	public WranglerActivityInstrumentationTestCase2(Class<T> activityClass) {
		super((Class<T>) BootstrapActivity.class);

		mRealActivityClass = activityClass;
	}
	
	public BootstrapActivity getBootstrapActivity() {
		return (BootstrapActivity) getActivity();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		final String targetPackage = getInstrumentation().getTargetContext().getPackageName();
		Log.d(getClass().getName(), "Launching activity under test: "+mRealActivityClass.getName());
		launchActivity(targetPackage, mRealActivityClass, null);
		
		Log.d(getClass().getName(), "Done.");
	}

}
