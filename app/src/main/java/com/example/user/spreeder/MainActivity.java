package com.example.user.spreeder;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private static MainActivity instance;
    //ArrayList<String> list = new ArrayList<String>();
    String list;
    private TblPassage tblPassage;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_text_field);
        instance = this;
        tblPassage = new TblPassage(this);
        list="Speed reading is the art of silencing subvocalization. Most readers have an average reading speed of 200 wpm, which is about as fast as they can read a passage out loud. This is no coincidence. It is their inner voice that paces through the text that keeps them from achieving higher reading speeds. They can only read as fast as they can speak because that's the way they were taught to read, through reading systems like Hooked on Phonics.\n" +
                "However, it is entirely possible to read at a much greater speed, with much better reading comprehension, by silencing this inner voice. The solution is simple - absorb reading material faster than that inner voice can keep up. \n" +
                "In the real world, this is achieved through methods like reading passages using a finger to point your way. You read through a page of text by following your finger line by line at a speed faster than you can normally read. This works because the eye is very good at tracking movement. Even if at this point full reading comprehension is lost, it's exactly this method of training that will allow you to read faster.\n" +
                "With the aid of software like Spreeder, it's much easier to achieve this same result with much less effort. Load a passage of text (like this one), and the software will pace through the text at a predefined speed that you can adjust as your reading comprehension increases. \n" +
                "To train to read faster, you must first find your base rate. Your base rate is the speed that you can read a passage of text with full comprehension. We've defaulted to 300 wpm, showing one word at a time, which is about the average that works best for our users. Now, read that passage using spreeder at that base rate. \n" +
                "After you've finished, double that speed by going to the Settings and changing the Words Per Minute value. Reread the passage. You shouldn't expect to understand everything - in fact, more likely than not you'll only catch a couple words here and there. If you have high comprehension, that probably means that you need to set your base rate higher and rerun this test again. You should be straining to keep up with the speed of the words flashing by. This speed should be faster than your inner voice can \"read\".\n" +
                "Now, reread the passage again at your base rate. It should feel a lot slower – if not, try running the speed test again). Now try moving up a little past your base rate – for example, at 400 wpm – , and see how much you can comprehend at that speed. \n" +
                "That's basically it - constantly read passages at a rate faster than you can keep up, and keep pushing the edge of what you're capable of. You'll find that when you drop down to lower speeds, you'll be able to pick up much more than you would have thought possible.\n" +
                "One other setting that's worth mentioning in this introduction is the chunk size – the number of words that are flashed at each interval on the screen. When you read aloud, you can only say one word at a time. However, this limit does not apply to speed reading. Once your inner voice subsides and with constant practice, you can read multiple words at a time. This is the best way to achieve reading speeds of 1000+ wpm. Start small with 2 word chunk sizes and find out that as you increase, 3,4, or even higher chunk sizes are possible.\n" +
                "Good luck!\n";

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
            sampleDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void sampleDialog(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Sample");
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_layout, null);
        builderSingle.setView(v);
        final EditText newText = (EditText) v.findViewById(R.id.new_text);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.dialog_list);
        cursor = tblPassage.fetchPassages();
        int i = 1;
        if (cursor.moveToFirst()) {
            do {
            arrayAdapter.add(i + ". " + cursor.getString(0));
                i++;
            } while (cursor.moveToNext());
        }else {
            tblPassage.insertPassage(list);
            arrayAdapter.add(list);
        }

        /*for (int i = 0; i < list.size(); i++) {
           arrayAdapter.add(list.get(i));
        }*/
    builderSingle.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderSingle.setPositiveButton("add",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = newText.getText().toString();
                        tblPassage.insertPassage(strName);
                        editText.setText(strName);
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        int position = strName.indexOf(".");
                        editText.setText(strName.substring(position+1));
                    }
                });
        builderSingle.show();
        // Another method for adding edit text in java code
       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hello User");
        builder.setMessage("What is your name:");

        // Use an EditText view to get user input.
        final EditText input = new EditText(this);
        input.setId(0);
        builder.setView(input);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();*/
    }
    public SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }
    public static MainActivity getInstance() {
        return instance;
    }

    public void doSpreed(View view){
        String spreedText;
        int j=0,k=0;
        spreedText = String.valueOf(editText.getText());
         /* Bundle b=new Bundle();
        b.putStringArray("spreed_array", arr);
   */   if(spreedText.equals(null))
            Toast.makeText(getApplicationContext(),"Enter Some Text",Toast.LENGTH_SHORT).show();
        else {
            Intent i = new Intent(getApplicationContext(), SpreedPlayer.class);
            i.putExtra("spreed_text", spreedText);
            i.putExtra("activity", "MainActivity");
            startActivity(i);
        }
    }
}
