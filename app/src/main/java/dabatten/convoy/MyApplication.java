package dabatten.convoy;

import android.app.Application;
import android.os.Bundle;
import com.parse.Parse;

public class MyApplication extends Application {

    private static final String PARSE_APP_ID = "vdRz96EEFsOEypWmtnXXJzaN3bjCHhVOEwJVw4kG";
    private static final String PARSE_CLIENT_KEY = "16Ju1I4uVp4KMisR0Sh0gc3Qc3RenXsueleNSYyi";

    @Override
    public void onCreate() {
        super.onCreate();
        /* fix later
        Bundle metadata = this.getApplicationInfo().metaData;
        String app_id = metadata.getString("com.parse.APPLICATION_ID");
        String client_key = metadata.getString("com.parse.CLIENT_KEY");
        */
        Parse.initialize(this, PARSE_APP_ID, PARSE_CLIENT_KEY);

    }
}