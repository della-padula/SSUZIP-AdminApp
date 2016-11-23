package com.ssumunity.ssuzip_admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class EventCreateActivity extends AppCompatActivity {

    private String year = "";
    private String month = "";
    private String day = "";
    private Date date = null;
    private Date today = null;

    private TextView tvTitle = null;
    private TextView tvMaxPerson = null;
    private TextView tvDuedate = null;
    private TextView tvDuedateText = null;

    private EditText etTitle = null;
    private EditText etMaxPerson = null;
    private ImageButton ibCalendar = null;
    private ActionBar actionBar = null;
    private Button createButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceUtil.loadTypeface(EventCreateActivity.this);
        setContentView(R.layout.activity_event_create);

        actionBar = getSupportActionBar();

        TextView TextViewNewFont = new TextView(EventCreateActivity.this);
        FrameLayout.LayoutParams layoutparams = new FrameLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        TextViewNewFont.setLayoutParams(layoutparams);
        TextViewNewFont.setText(getString(R.string.event_create_actionbar_title));

        // TextView Color
        TextViewNewFont.setTextColor(Color.WHITE);

        // TextView Gravity : 일단 비활성화 (Center Alignment가 안됨)
        //TextViewNewFont.setGravity(Gravity.CENTER_HORIZONTAL);
        TextViewNewFont.setTextSize(18);

        // Load Typeface font url String ExternalFontPath
        Typeface FontLoaderTypeface = Typeface.createFromAsset(getAssets(), this.getString(R.string.typeface_bold));
        TextViewNewFont.setTypeface(FontLoaderTypeface);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(TextViewNewFont);
        actionBar.setDisplayHomeAsUpEnabled(true);


        setContent();
        getCurrentDate();

        tvDuedateText.setText(year + getString(R.string.event_create_year) + " " +
                month + getString(R.string.event_create_month) + " " + day + getString(R.string.event_create_day) + " ");

        ibCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(EventCreateActivity.this, listener, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
                dialog.show();
            }
        });

        createButton.setTypeface(TypefaceUtil.typeface);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Change to Two");
                Intent intent = new Intent(EventCreateActivity.this, EventCreateTwoActivity.class);
                intent.putExtra("title", tvTitle.getText().toString());
                intent.putExtra("maxperson", tvMaxPerson.getText().toString());
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("day", day);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                finish();
            }
        });
    }

    private void setContent() {
        tvDuedate = (TextView) findViewById(R.id.tv_duedate);
        ibCalendar = (ImageButton) findViewById(R.id.ib_calendar);

        tvMaxPerson = (TextView) findViewById(R.id.tv_maxPerson);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setTypeface(TypefaceUtil.typeface_m);
        tvMaxPerson.setTypeface(TypefaceUtil.typeface_m);
        tvDuedate.setTypeface(TypefaceUtil.typeface_m);

        createButton = (Button) findViewById(R.id.select_major_button);

        etTitle = (EditText) findViewById(R.id.et_title);
        etTitle.setTypeface(TypefaceUtil.typeface);

        etMaxPerson = (EditText) findViewById(R.id.et_maxPerson);
        etMaxPerson.setTypeface(TypefaceUtil.typeface);

        tvDuedateText = (TextView) findViewById(R.id.tv_duedate_text);
        tvDuedateText.setTypeface(TypefaceUtil.typeface_m);
    }

    public void getCurrentDate(){
        DecimalFormat df = new DecimalFormat("00");
        Calendar currentCal = Calendar.getInstance();

        currentCal.add(currentCal.DATE, 0);

        year = Integer.toString(currentCal.get(Calendar.YEAR));
        month = df.format(currentCal.get(Calendar.MONTH)+1);
        day = df.format(currentCal.get(Calendar.DAY_OF_MONTH));
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int dyear, int dmonth, int dday) {
            dmonth += 1;
            date = new Date(dyear, dmonth, dday);
            today = new Date(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

            int compare = date.compareTo(today);
            if(compare < 0) {
                DialogUtil.showDialog(EventCreateActivity.this, getString(R.string.event_create_dialog_date_title),
                        getString(R.string.event_create_dialog_date_content), 1, 3);
            } else {
                tvDuedate.setText(dyear + getString(R.string.event_create_year) + " " +
                        dmonth + getString(R.string.event_create_month) + " "  + dday + getString(R.string.event_create_day) + " ");
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                overridePendingTransition(R.anim.fade, R.anim.hold);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
