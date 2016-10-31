package digital.mo.apps.filehandling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import digital.mo.apps.filehandling.objects.Kita;

public class SelectableKitaListActivity extends AppCompatActivity {

    private ArrayList<Kita> kitas;
    private ArrayAdapter<Kita> kitaAdapter;
    private ListView kitaListView;
    protected static String SELECTED_KITA_BUNDLE = "selectedKita";
    protected static String SELECTED_KITA_ID = "kitaID";
    private static int REQUEST_CODE = 1;


    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);


        kitaListView = (ListView)findViewById(R.id.kita_list);

        readKitas();

        kitaAdapter = new ArrayAdapter<Kita>(SelectableKitaListActivity.this,
                android.R.layout.simple_list_item_1, kitas);
        kitaListView.setAdapter(kitaAdapter);

        kitaListView.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent kitaDetailIntent = new Intent(SelectableKitaListActivity.this,
                                                     SelectableKitaDetailActivity.class);
                kitaDetailIntent.putExtra(SELECTED_KITA_BUNDLE,kitas.get(position));
                startActivityForResult(kitaDetailIntent,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int selectedKitaID = data.getIntExtra(SELECTED_KITA_ID,-1);

        if (selectedKitaID == -1)
            return;

        for (Kita k : kitas){
            if (k.getId() == selectedKitaID){
                Toast.makeText(this,k.getName()+" has been selected",Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    /*
         * Fills the kitas ArrayList with Kita Objects from file
         * @returns: void
         * @requires: kitas not null
          * Fetches Kita csv file from raw folder,
          * reads the csv line by line
          * splits lines with delimeter '^'
          * passes contents Array to Kita instance generator
          *
         */
    public void readKitas() {
        BufferedReader reader = null;
        String line = "";
        kitas = new ArrayList<Kita>();

        try {
            reader = new BufferedReader(new InputStreamReader(
                    getResources().openRawResource(R.raw.kita_einrichtung)));

            while ((line = reader.readLine()) != null) {

                if (line.substring(line.length()-1).equals("^"))
                    line = line + " ";

                String[] rawKitaValues = line.split("\\^");
                kitas.add(Kita.createKita(rawKitaValues));

            }
            reader.close();

        }catch (Exception e){
            Log.e("Exception",e.getMessage());
            Toast.makeText(SelectableKitaListActivity.this,"Kita list creation failed!",Toast.LENGTH_SHORT).show();
        }finally {

            try {
                if (reader!=null) {
                    reader.close();
                }
            } catch (IOException e) {
                Log.e("Exception",e.getMessage());
            }
        }
    }
}
