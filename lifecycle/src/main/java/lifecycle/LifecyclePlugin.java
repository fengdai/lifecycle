package lifecycle;

import android.content.Intent;
import android.os.Bundle;

public interface LifecyclePlugin {

  void onCreate(LifecycleScope scope, Bundle savedInstanceState);

  void onStart();

  void onResume();

  void onPause();

  void onSaveInstanceState(Bundle outState);

  void onStop();

  void onDestroy();

  boolean onActivityResult(int requestCode, int resultCode, Intent data);
}
