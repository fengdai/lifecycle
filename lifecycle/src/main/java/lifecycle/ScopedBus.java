package lifecycle;

public interface ScopedBus {

  void post(Object event);

  void register(Object listener);
}
