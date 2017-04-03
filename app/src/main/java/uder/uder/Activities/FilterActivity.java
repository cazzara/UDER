package uder.uder.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import uder.uder.R;

/**
 * Created by cazza223 on 3/20/2017.
 */

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // https://developer.android.com/guide/topics/ui/controls/spinner.html

        Spinner milk_type_spinner = (Spinner)findViewById(R.id.sp_typeList);
        Spinner milk_price_spinner = (Spinner)findViewById(R.id.sp_priceRange);
        Spinner milk_flavor_spinner = (Spinner)findViewById(R.id.sp_flavor);
        Spinner milk_brand_spinner = (Spinner)findViewById(R.id.sp_brand);

        ArrayAdapter<CharSequence> typeSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.milk_types, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> priceSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.milk_prices, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> flavorSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.milk_flavors, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> brandSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.milk_brands, android.R.layout.simple_spinner_item);

        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flavorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        milk_type_spinner.setAdapter(typeSpinnerAdapter);
        milk_price_spinner.setAdapter(priceSpinnerAdapter);
        milk_flavor_spinner.setAdapter(flavorSpinnerAdapter);
        milk_brand_spinner.setAdapter(brandSpinnerAdapter);

    }

}
