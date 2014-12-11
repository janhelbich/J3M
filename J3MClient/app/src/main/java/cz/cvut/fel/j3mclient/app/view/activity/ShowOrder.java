package cz.cvut.fel.j3mclient.app.view.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.rest.RestService;

import cz.cvut.fel.j3mclient.app.R;
import cz.cvut.fel.j3mclient.app.model.BazaarOrder;
import cz.cvut.fel.j3mclient.app.service.BazaarRestService;

@EActivity
public class ShowOrder extends Activity {

    @RestService
    BazaarRestService service;

    public long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_order);

        orderId = getIntent().getLongExtra(OrderParameters.ORDER_ID, -1);

        new DownloadOrderTask().execute(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_order, menu);
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

class DownloadOrderTask extends AsyncTask<ShowOrder, Void, BazaarOrder> {

    private Exception exception;
    private ShowOrder showOrder;

    @Override
    protected BazaarOrder doInBackground(ShowOrder... showedOrders) {
        BazaarOrder order;
        this.showOrder = showedOrders[0];

        try {
            order = this.showOrder.service.getOrder(this.showOrder.orderId);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.showOrder, R.string.server_connection_failed, Toast.LENGTH_LONG).show();
            return null;
        }

        return order;
    }

    protected void onPostExecute(BazaarOrder order) {

        if (order == null) {
            return;
        }

        TextView orderIdField = (TextView) showOrder.findViewById(R.id.orderIdField);
        TextView emailField = (TextView) showOrder.findViewById(R.id.emailField);
        TextView nameField = (TextView) showOrder.findViewById(R.id.nameField);
        TextView transportField = (TextView) showOrder.findViewById(R.id.transportField);
        TextView priceField = (TextView) showOrder.findViewById(R.id.priceField);
        TextView streetField = (TextView) showOrder.findViewById(R.id.streetField);
        TextView cityField = (TextView) showOrder.findViewById(R.id.cityField);
        TextView zipField = (TextView) showOrder.findViewById(R.id.zipField);
        TextView stateField = (TextView) showOrder.findViewById(R.id.stateField);

        orderIdField.setText(Long.toString(order.getOrderId()));
        emailField.setText(order.getEmail());
        nameField.setText(order.getFirstName() + order.getSurname());
        transportField.setText(order.getTransport().getName());
        priceField.setText(order.getPrice().getAmount() + " " + order.getPrice().getCurrency().getCurrencyCode());
        streetField.setText(order.getStreet() + " " + order.getHouseNumber());
        cityField.setText(order.getCity());
        zipField.setText(order.getZip());
        stateField.setText(order.getState().getName());

        String[] productData = new String[order.getProducts().size()];

        for (int i = 0; i < productData.length; i++) {
            productData[i] = order.getProducts().get(i).getProduct().getName() + "  (" + order.getProducts().get(i).getPrice().getAmount() + order.getProducts().get(i).getPrice().getCurrency().getCurrencyCode() + ")";
        }

        ListView orderDataList = (ListView) this.showOrder.findViewById(R.id.productList);
        ArrayAdapter<String> textViewAdapter = new ArrayAdapter<String>(this.showOrder, R.layout.order_node, productData);
        orderDataList.setAdapter(textViewAdapter);
    }
}
