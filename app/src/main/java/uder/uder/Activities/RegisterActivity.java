package uder.uder.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import uder.uder.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText et_fname = (EditText) findViewById(R.id.et_fname);
        final EditText et_lname = (EditText) findViewById(R.id.et_lname);
        final EditText et_username = (EditText) findViewById(R.id.et_username);
        final EditText et_password = (EditText) findViewById(R.id.et_password);

        final TextView tv_register_welcome = (TextView) findViewById(R.id.tv_register_welcome);
        final TextView tv_choice = (TextView) findViewById(R.id.tv_choice);

        final RadioGroup rg_group = (RadioGroup) findViewById(R.id.rg_group);
        final RadioButton rb_user = (RadioButton) findViewById(R.id.rb_user);
        final RadioButton rb_getter = (RadioButton) findViewById(R.id.rb_getter);

    }
}
