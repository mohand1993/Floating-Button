package fbutton.muf.com.floating_button;

import android.view.View;
import android.widget.Toast;

/**
 * Created by Muhannad F on 4/18/2015.
 */
public class MakeTostClickListener implements View.OnClickListener {
    private final String str;

    public MakeTostClickListener(String s) {
        str = s;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(),str,Toast.LENGTH_SHORT).show();
    }
}
