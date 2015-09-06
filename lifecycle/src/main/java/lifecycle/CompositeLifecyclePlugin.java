package lifecycle;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

class CompositeLifecyclePlugin implements LifecyclePlugin {
  private final List<LifecyclePlugin> plugins = new ArrayList<>();

  public void add(LifecyclePlugin plugin) {
    if (plugins.contains(plugin)) {
      throw new IllegalArgumentException("Duplicate registration of " + plugin);
    } else {
      plugins.add(plugin);
    }
  }

  public void clear() {
    plugins.clear();
  }

  @Override public void onCreate(LifecycleScope scope, Bundle savedInstanceState) {
  }

  @Override public void onStart() {
    for (LifecyclePlugin plugin : plugins) {
      plugin.onStart();
    }
  }

  @Override public void onResume() {
    for (LifecyclePlugin plugin : plugins) {
      plugin.onResume();
    }
  }

  @Override public void onPause() {
    for (int i = plugins.size() - 1; i >= 0; --i) {
      plugins.get(i).onPause();
    }
  }

  @Override public void onSaveInstanceState(Bundle bundle) {
    for (LifecyclePlugin plugin : plugins) {
      plugin.onSaveInstanceState(bundle);
    }
  }

  @Override public void onStop() {
    for (int i = plugins.size() - 1; i >= 0; --i) {
      plugins.get(i).onStop();
    }
  }

  @Override public void onDestroy() {
    for (int i = plugins.size() - 1; i >= 0; --i) {
      plugins.get(i).onDestroy();
    }
    clear();
  }

  @Override public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
    for (LifecyclePlugin plugin : plugins) {
      if (plugin.onActivityResult(requestCode, resultCode, data)) {
        return true;
      }
    }
    return false;
  }
}
