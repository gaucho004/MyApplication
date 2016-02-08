package activities.doctorswall.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import application.doctorswall.victor.myapplication.R;
import victor.doctorswall.components.MyFontButton;
import victor.doctorswall.components.MyFontEdittextView;

public class AddSpecialties extends AppCompatActivity {
    MyCustomAdapter dataAdapter = null;
    MyFontEdittextView etSpeciality;
    MyFontButton btnok;

    StringBuffer responseText;
    List<String> degrees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_specialties);

        degrees = new ArrayList<String>();
        etSpeciality = (MyFontEdittextView) findViewById(R.id.etspecialties_entry);



        displayListView();

        checkButtonClick();
    }



    private class MyCustomAdapter extends ArrayAdapter<Specialties> {

        private ArrayList<Specialties> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Specialties> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Specialties>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
           // Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.specialtiesdialog_layout, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Specialties specialty = (Specialties) cb.getTag();
                       /* Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();*/

                        //etSpeciality.append("\n" + specialty.getCode() +specialty.getName());


                        specialty.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Specialties sp = countryList.get(position);
            holder.code.setText(" (" +  sp.getCode() + ")");
            holder.name.setText(sp.getName());
            holder.name.setChecked(sp.isSelected());
            holder.name.setTag(sp);

            return convertView;

        }

    }


    private void checkButtonClick() {


        btnok = (MyFontButton) findViewById(R.id.btnsaveSpecialties);
        btnok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    degrees.clear();
                 responseText = new StringBuffer();
               // responseText.append("The following were selected...\n");

                ArrayList<Specialties> countryList = dataAdapter.countryList;
                for(int i=0;i<countryList.size();i++){
                    Specialties sp = countryList.get(i);
                    if(sp.isSelected()){
                       // responseText.append("\n" + sp.getCode());

                        degrees.add(sp.getCode());

                    }
                }

             /*   Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();*/
                if(degrees.size()>0){
                    int total=degrees.size()-1;

                    responseText.append(degrees.get(total));

                    total--;
                    while(total>=0){

                        responseText.append(", " + degrees.get(total));

                        total--;
                    }

                }else{
                        responseText.append("");
                }


                Intent intent=new Intent();
                intent.putExtra("degrees",responseText.toString());

                setResult(RESULT_OK,intent);
                finish();//finishing activity


            }
        });

    }


    private void displayListView() {

        //Array list of countries
        ArrayList<Specialties> countryList = new ArrayList<Specialties>();
        Specialties sp = new Specialties("AuD","Doctor of Audiology",false);
        countryList.add(sp);
        sp = new Specialties("DC" ,"Doctor of Chiropractic",false);
        countryList.add(sp);
        sp = new Specialties("DDS","Doctor of Dental Surgery, Doctor of Dental Science",false);
        countryList.add(sp);
        sp = new Specialties("DMD","Doctor of Dental Medicine, Doctor of Medical Dentistry",false);
        countryList.add(sp);
        sp = new Specialties("DO or OD","Doctor of Optometry, Doctor of Osteopathic Medicine",false);
        countryList.add(sp);
        sp = new Specialties("DPM","Doctor of Podiatric Medicine",false);
        countryList.add(sp);
        sp = new Specialties("DPT","Doctor of Physical Therapy",false);
        countryList.add(sp);
        sp = new Specialties("DScPT","Doctor of Science in Physical Therapy",false);
        countryList.add(sp);
        sp = new Specialties("DSN","Doctor of Science in Nursing",false);
        countryList.add(sp);
        sp = new Specialties("DVM","Doctor of Veterinary Mediciney",false);
        countryList.add(sp);
        sp = new Specialties("ENT","Ear, nose and throat specialist",false);
        countryList.add(sp);
        sp = new Specialties("GP","General Practitioner",false);
        countryList.add(sp);
        sp = new Specialties("GYN","Gynecologist",false);
        countryList.add(sp);
        sp = new Specialties("MD","Doctor of Medicine",false);
        countryList.add(sp);
        sp = new Specialties("MS","Master of Surgery",false);
        countryList.add(sp);
        sp = new Specialties("OB/GYN","Obstetrician and Gynecologist",false);
        countryList.add(sp);
        sp = new Specialties("PharmD","Doctor of Pharmacy",false);
        countryList.add(sp);




        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.specialtiesdialog_layout, countryList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Specialties sp = (Specialties) parent.getItemAtPosition(position);
             /*   Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + sp.getName(),
                        Toast.LENGTH_LONG).show();*/
            }
        });

    }

}
