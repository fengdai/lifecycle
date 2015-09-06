package lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.squareup.otto.Bus;

public class LifecycleActivity extends Activity implements LifecycleScope {
  private Bus bus = new Bus();

  private final CompositeLifecyclePlugin compositePlugin = new CompositeLifecyclePlugin();
  private boolean resumed = false;
  private ScopedBus scopedBus;

  @Override public boolean isRunning() {
    return resumed;
  }

  @Override
  public void registerPlugins(Bundle savedInstanceState, LifecyclePlugin... lifecyclePlugins) {
    for (LifecyclePlugin plugin : lifecyclePlugins) {
      plugin.onCreate(this, savedInstanceState);
      compositePlugin.add(plugin);
      getScopedBus().register(plugin);
    }
  }

  @Override public void executeAction(LifecycleAction lifecycleAction) {
    lifecycleAction.execute(this);
  }

  @Override public ScopedBus getScopedBus() {
    return scopedBus;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!isFinishing()) {
      ScopedBusPlugin scopedBus = new ScopedBusPlugin(this.bus);
      this.scopedBus = scopedBus;
      registerPlugins(savedInstanceState, scopedBus);
      getScopedBus().register(this);
    }
  }

  @Override protected void onStart() {
    super.onStart();
    compositePlugin.onStart();
  }

  @Override protected void onResume() {
    super.onResume();
    resumed = true;
    compositePlugin.onResume();
  }

  @Override protected void onPause() {
    if (resumed) {
      resumed = false;
      compositePlugin.onPause();
    }
    super.onPause();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (!this.isFinishing()) {
      compositePlugin.onSaveInstanceState(outState);
    }
  }

  @Override protected void onStop() {
    compositePlugin.onStop();
    super.onStop();
  }

  @Override protected void onDestroy() {
    compositePlugin.onDestroy();
    super.onDestroy();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (!compositePlugin.onActivityResult(requestCode, resultCode, data)) {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }
}
