package cz.cvut.fel.j3mclient.app.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import cz.cvut.fel.j3mclient.app.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] data = {"TEST1", "TEST2", "TEST3", "TEST4", "TEST5", "TEST6"};
        this.populateOrderNodes(data);
        this.registerOrderNodesHandlers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateOrderNodes(String[] data) {
        ListView orderDataList = (ListView) this.findViewById(R.id.ordersListView);
        ArrayAdapter<String> textViewAdapter = new ArrayAdapter<String>(this, R.layout.order_node, data);
        orderDataList.setAdapter(textViewAdapter);
    }

    private void registerOrderNodesHandlers() {
        ListView orderDataList = (ListView) this.findViewById(R.id.ordersListView);

        orderDataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Position " + position + " was clicked", Toast.LENGTH_LONG).show();
            }
        });
    }


}
