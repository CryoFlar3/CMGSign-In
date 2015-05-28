package org.computermentors.cmgsign_in;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by CryoFlar3 on 5/28/15.
 */
public class CMGSignInApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "5z3vrWqs8PML5OAxltsYqv3nd4lo2Mzu8IDbHeCo", "b9yIaimVWtVqQMlURrriKPTEY4CzEQOyrkcz9MK0");
    }
}
