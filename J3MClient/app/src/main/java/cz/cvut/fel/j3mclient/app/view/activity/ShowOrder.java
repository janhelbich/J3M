package cz.cvut.fel.j3mclient.app.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import cz.cvut.fel.j3mclient.app.R;
import cz.cvut.fel.j3mclient.app.model.BazaarOrder;

public class ShowOrder extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_order);

        int orderId = getIntent().getIntExtra(OrderParameters.ORDER_ID, -1);

        TextView orderIdField = (TextView) findViewById(R.id.orderIdField);
        TextView emailField = (TextView) findViewById(R.id.emailField);
        TextView firstNameField = (TextView) findViewById(R.id.firstNameField);
        TextView lastNameField = (TextView) findViewById(R.id.lastNameField);
        TextView priceField = (TextView) findViewById(R.id.priceField);

        BazaarOrder order = this.getOrder();

        orderIdField.setText(Long.toString(order.getOrderId()));
        emailField.setText(order.getEmail());
        firstNameField.setText(order.getFirstName());
        lastNameField.setText(order.getSurname());
        priceField.setText("102" + ",-" + OrderParameters.CURRENCY);

    }

    private BazaarOrder getOrder() {
        BazaarOrder order = new BazaarOrder();

        order.setOrderId((long) 20);
        order.setEmail("test at test.com");
        order.setFirstName("JAN");
        order.setSurname("VLK");

        return order;
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
