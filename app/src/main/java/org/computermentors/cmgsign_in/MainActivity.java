package org.computermentors.cmgsign_in;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity{

    ListView mListView;
    protected List<ParseObject> mStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.listView);

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
                    String[] studentId = new String[mStudents.size()];
                    int i = 0;
                    for (ParseObject message : mStudents) {
                        students[i] = message.getString(ParseConstants.KEY_NAME);

                        ParseObject gameScore = new ParseObject(ParseConstants.CLASS_ATTENDANCE);
                        gameScore.put("name", students[i]);
                        gameScore.put("absent", true);
                        gameScore.put("present", false);
                        gameScore.put("excused", false);
                        gameScore.saveEventually();

                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            students);
                    mListView.setAdapter(adapter);
                }
            }
        });


        registerForContextMenu(mListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.signin_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.id_absent:



        }

        return super.onContextItemSelected(item);
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
