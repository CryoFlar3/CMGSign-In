package org.computermentors.cmgsign_in;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends ListActivity {

    protected List<ParseObject> mStudents;
    public TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Day od the week
        Date c = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("EEEE");
        String dayOfWeek = format2.format(c);

        setProgressBarIndeterminateVisibility(true);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_STEMCORPS);
        query.whereEqualTo(ParseConstants.KEY_DAY, dayOfWeek);
        query.addAscendingOrder(ParseConstants.KEY_NAME);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> stemCorps, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    // We found messages
                    mStudents = stemCorps;

                    String[] students = new String[mStudents.size()];
                    int i = 0;
                    for (ParseObject message : mStudents) {
                        students[i] = message.getString(ParseConstants.KEY_NAME);
                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            students);
                    setListAdapter(adapter);
                }
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject message = mStudents.get(position);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
