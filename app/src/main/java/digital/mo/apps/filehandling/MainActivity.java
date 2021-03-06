package digital.mo.apps.filehandling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import digital.mo.apps.filehandling.objects.Kita;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Kita> kitas;


    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        ListView kitaListView = (ListView)findViewById(R.id.kita_list);
        readKitas();
        ArrayAdapter<Kita> kitaAdapter = new ArrayAdapter<Kita>(MainActivity.this,
                android.R.layout.simple_list_item_1, kitas);
        kitaListView.setAdapter(kitaAdapter);
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
            Toast.makeText(MainActivity.this,"Kita list creation failed!",Toast.LENGTH_SHORT).show();
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
