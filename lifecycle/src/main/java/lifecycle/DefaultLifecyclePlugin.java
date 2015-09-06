package lifecycle;

import android.content.Intent;
import android.os.Bundle;

public class DefaultLifecyclePlugin implements LifecyclePlugin {
  private LifecycleScope scope;

  public DefaultLifecyclePlugin() {
  }

  protected LifecycleScope getScope() {
    if (scope == null) {
      throw new IllegalStateException(
          "Did you forget to register this plugin with LifecycleScope#registerPlugins?"
              + " Or maybe the plugin didn't call super.onCreate()");
    }
    return scope;
  }

  @Override public void onCreate(LifecycleScope scope, Bundle savedInstanceState) {
    this.scope = scope;
  }

  @Override public void onStart() {
  }

  @Override public void onResume() {
  }

  @Override public void onPause() {
  }

  @Override public void onSaveInstanceState(Bundle outState) {
  }

  @Override public void onStop() {
  }

  @Override public void onDestroy() {
  }

  @Override public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
    return false;
  }
}
