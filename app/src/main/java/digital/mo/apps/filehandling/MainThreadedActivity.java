package digital.mo.apps.filehandling;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import digital.mo.apps.filehandling.objects.Kita;

public class MainThreadedActivity extends AppCompatActivity {

    private ProgressBar pg;
    private ArrayList<Kita> kitas;
    Handler backgroundTask;


    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_w_progress);
        pg = (ProgressBar)findViewById(R.id.progress);
        pg.setProgress(0);
        final ListView kitaListView = (ListView)findViewById(R.id.kita_list);
        kitaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Kita k = kitas.get(position);
                Intent kitaIntent = new Intent(MainThreadedActivity.this,SelectableKitaDetailActivity.class);
                kitaIntent.putExtra("KITA_CODE",k);
                startActivity(kitaIntent);
            }
        });

        if (savedInstanceState != null) {
            kitas = (ArrayList<Kita>) savedInstanceState.get("kitas");
        }

        if (kitas == null) {
            backgroundTask = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(final Message msg) {
                        MainThreadedActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (msg.getData().getInt("progress") >= 100) {
                                    pg.setVisibility(View.GONE);
                                    findViewById(R.id.kita_list).setVisibility(View.VISIBLE);
                                    ArrayAdapter<Kita> kitaAdapter = new ArrayAdapter<Kita>(MainThreadedActivity.this,
                                            android.R.layout.simple_list_item_1, kitas);
                                    kitaListView.setAdapter(kitaAdapter);
                                    kitaAdapter.notifyDataSetChanged();
                                } else {
                                    pg.setProgress(msg.getData().getInt("progress"));
                                }
                            }
                        });
                    return true;
                }
            });


            Thread myThread = new Thread(new KitaReader());
            myThread.start();

        }else {
            pg.setVisibility(View.GONE);
            findViewById(R.id.kita_list).setVisibility(View.VISIBLE);
            ArrayAdapter<Kita> kitaAdapter = new ArrayAdapter<Kita>(MainThreadedActivity.this,
                    android.R.layout.simple_list_item_1, kitas);
            kitaListView.setAdapter(kitaAdapter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("kitas",kitas);
    }

    class KitaReader implements Runnable {
        @Override
        public void run() {
            BufferedReader reader = null;
            String line = "";
            int lineNo = 0;
            int before = 0;
            int progress;
            kitas = new ArrayList<Kita>();

            try {
                reader = new BufferedReader(new InputStreamReader(
                        getResources().openRawResource(R.raw.kita_einrichtung)));

                while ((line = reader.readLine()) != null) {
                    Thread.sleep(2);
                    lineNo++;

                    if (line.substring(line.length()-1).equals("^"))
                        line = line + " ";

                    String[] rawKitaValues = line.split("\\^");
                    kitas.add(Kita.createKita(rawKitaValues));

                    progress = (int)((lineNo/1050.f)*100);
                    if (before!=progress) {
                        Message m = backgroundTask.obtainMessage();
                        Bundle b = new Bundle();
                        b.putInt("progress", progress);
                        m.setData(b);
                        backgroundTask.sendMessage(m);
                    }
                    before = progress;
                }
                reader.close();
            }catch (Exception e){
                Log.e("Exception",e.getMessage());
                Toast.makeText(MainThreadedActivity.this,"Couldn't create Kita list",Toast.LENGTH_SHORT).show();
            }finally {

                try {
                    if (reader!=null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    Log.e("Exception",e.getMessage());
                }
            }

            Message m = backgroundTask.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("progress",100);
            m.setData(b);
            backgroundTask.sendMessage(m);

        }
    }
}
