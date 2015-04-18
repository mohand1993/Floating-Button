package fbutton.muf.com.floating_button;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import fbutton.muf.com.floating_button_library.FloatingButton;


public class MainActivity extends ActionBarActivity {

    private FloatingButton floatingButton;
    private FloatingButton floatingButton2;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        String[] values = new String[] { "This", "is", "Just",
                "a", "dummy", "list", "designed", "for",
                "use", "with", "Simple Floating button", "Library", "with", "poor design",
                "and some bad english grammers", ":)" };

        list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values));
        floatingButton = (FloatingButton)findViewById(R.id.floatingButton);
        floatingButton2 = (FloatingButton)findViewById(R.id.floatingButton2);

        floatingButton.addButton(R.drawable.red_circle,new MakeTostClickListener("I am Red"));
        floatingButton.addButton(R.drawable.yellow_circle,new MakeTostClickListener("I am Yellow"));
        floatingButton.addButton(R.drawable.green_circle,new MakeTostClickListener("I am Green"));
        floatingButton.addButton(R.drawable.blue_circle,new MakeTostClickListener("I am blue"));
        floatingButton.animateWithScroll(list);


        floatingButton2.addButton(R.drawable.red_circle,new MakeTostClickListener("I am Red"));
        floatingButton2.addButton(R.drawable.yellow_circle,new MakeTostClickListener("I am Yellow"));
        floatingButton2.addButton(R.drawable.green_circle,new MakeTostClickListener("I am Green"));

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
