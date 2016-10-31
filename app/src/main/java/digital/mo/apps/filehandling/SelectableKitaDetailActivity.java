package digital.mo.apps.filehandling;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import digital.mo.apps.filehandling.objects.Kita;

public class SelectableKitaDetailActivity extends AppCompatActivity {

    private Kita selectedKita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectable_kita_detail_view);

        Intent kitaDetailIntent = getIntent();
        if (kitaDetailIntent != null){
            selectedKita = (Kita)kitaDetailIntent.getSerializableExtra("KITA_CODE");
            populateKitaDetails();
        }
        else {
            Toast.makeText(this,"Missing Intent!",Toast.LENGTH_SHORT).show();
        }
    }

    private void populateKitaDetails() {
        if (selectedKita != null){
            TextView name = (TextView)findViewById(R.id.name);
            name.setText(selectedKita.getName());
            TextView street = (TextView)findViewById(R.id.street);
            street.setText(selectedKita.getStreet());
            TextView houseNo = (TextView)findViewById(R.id.houseNo);
            houseNo.setText(selectedKita.getHouseNo());
            TextView houseNoAlpha = (TextView)findViewById(R.id.houseNoAlpha);
            houseNoAlpha.setText(selectedKita.getHouseNoAlpha());
            TextView postal = (TextView)findViewById(R.id.postal);
            postal.setText(selectedKita.getPostal());
            TextView place = (TextView)findViewById(R.id.place);
            place.setText(selectedKita.getPlace());
            TextView contactName = (TextView)findViewById(R.id.contactName);
            contactName.setText(selectedKita.getContactName());
            TextView phone = (TextView)findViewById(R.id.phone);
            phone.setText(selectedKita.getPhone());
            TextView fax = (TextView)findViewById(R.id.fax);
            fax.setText(selectedKita.getFax());
            TextView agency = (TextView)findViewById(R.id.agency);
            agency.setText(selectedKita.getAgencyName());
            TextView url = (TextView)findViewById(R.id.url);
            url.setText(selectedKita.getUrl());
            TextView email = (TextView)findViewById(R.id.email);
            email.setText(selectedKita.getUrl());
            Button select = (Button)findViewById(R.id.select);
            select.setSelected(selectedKita.isSelected());
        }
        else {
            Toast.makeText(this, "Kita is null",Toast.LENGTH_SHORT).show();
        }
    }

    public void selectClicked(View view) {
        Intent resultIntent = getIntent();
        if(selectedKita.getUrl()!=null && selectedKita.getUrl().isEmpty()){
            Toast.makeText(this, "Keine URL hinterlegt!", Toast.LENGTH_LONG).show();
            return;
        }

        String url = "http://" + selectedKita.getUrl();

        Uri webpage = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }
}
