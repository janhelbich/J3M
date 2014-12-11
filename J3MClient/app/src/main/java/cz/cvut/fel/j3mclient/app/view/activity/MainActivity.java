package cz.cvut.fel.j3mclient.app.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import cz.cvut.fel.j3mclient.app.R;
import cz.cvut.fel.j3mclient.app.model.BazaarOrder;
import cz.cvut.fel.j3mclient.app.service.BazaarRestService;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

@EActivity
public class MainActivity extends Activity {

    @RestService
    BazaarRestService service;

    static boolean refreshing = false;

    public List<BazaarOrder> showedOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadNewOrdersTask().execute(this);

        Button refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.refreshing) {
                    return;
                }
                new DownloadNewOrdersTask().execute(MainActivity.this);
            }
        });
    }

    String[] createStringArrayFromOrderList(List<BazaarOrder> newOrders) {
        String[] data = new String[newOrders.size()];

        for (int i = 0; i < newOrders.size(); i++) {
            data[i] = newOrders.get(i).getFirstName() + " " + newOrders.get(i).getSurname();
        }

        return data;
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

    void populateOrderNodes(String[] data) {
        ListView orderDataList = (ListView) this.findViewById(R.id.ordersListView);
        ArrayAdapter<String> textViewAdapter = new ArrayAdapter<String>(this, R.layout.order_node, data);
        orderDataList.setAdapter(textViewAdapter);
    }

    void registerOrderNodesHandlers() {
        ListView orderDataList = (ListView) this.findViewById(R.id.ordersListView);

        orderDataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent showOrderIntent = new Intent(MainActivity.this, ShowOrder_.class);
                showOrderIntent.putExtra(OrderParameters.ORDER_ID, MainActivity.this.showedOrders.get(position).getOrderId());

                startActivity(showOrderIntent);
            }
        });
    }
}

class DownloadNewOrdersTask extends AsyncTask<MainActivity, Void, List<BazaarOrder>> {

    private Exception exception;
    private MainActivity mainActivity;

    @Override
    protected List<BazaarOrder> doInBackground(MainActivity... mainActivities) {
        MainActivity.refreshing = true;
        this.mainActivity = mainActivities[0];

        try {
            this.mainActivity.showedOrders = this.mainActivity.service.getNewOrders();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.mainActivity, R.string.server_connection_failed, Toast.LENGTH_LONG).show();
            return null;
        }

        return this.mainActivity.showedOrders;
    }

    protected void onPostExecute(List<BazaarOrder> orders) {
        String[] data = {};

        if (orders != null) {
            data = mainActivity.createStringArrayFromOrderList(orders);
        }

        mainActivity.populateOrderNodes(data);
        mainActivity.registerOrderNodesHandlers();
        MainActivity.refreshing = false;
    }
}
