package lifecycle;

import android.os.Bundle;

public interface LifecycleScope {

  boolean isRunning();

  void registerPlugins(Bundle savedInstanceState, LifecyclePlugin... lifecyclePlugins);

  void executeAction(LifecycleAction lifecycleAction);

  ScopedBus getScopedBus();
}
