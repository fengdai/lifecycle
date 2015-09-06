package lifecycle;

import com.squareup.otto.Bus;
import java.util.HashSet;
import java.util.Set;

class ScopedBusPlugin extends DefaultLifecyclePlugin implements ScopedBus {
  private final Set<Object> listeners = new HashSet<>();
  private final Bus bus;

  public ScopedBusPlugin(Bus bus) {
    this.bus = bus;
  }

  @Override public void post(Object event) {
    bus.post(event);
  }

  @Override public void register(Object listener) {
    if (this.listeners.add(listener)) {
      if (getScope().isRunning()) {
        bus.register(listener);
      }
    }
  }

  @Override public void onResume() {
    for (Object listener : listeners) {
      bus.register(listener);
    }
  }

  @Override public void onPause() {
    for (Object listener : listeners) {
      bus.unregister(listener);
    }
  }

  @Override public void onDestroy() {
    this.listeners.clear();
  }
}
