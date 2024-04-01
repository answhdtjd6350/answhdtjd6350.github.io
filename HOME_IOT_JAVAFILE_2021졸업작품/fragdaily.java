package com.example.jongseong3;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import java.util.Calendar;


public class fragdaily extends Fragment {
    private View view;
    String moon = "myFile";

    private TextView mText;
    private Button mPickDate;
    private Button mPickTime;
    private Button mdaily;
    private Button mdaily1;
    private TextView mtext1;
    private TextView mtext2;
    private TextView mtext3;
    private Button mPickDate1;
    private Button mPickTime1;
    private Button reset;


    public fragdaily() { }


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dailykit, container, false);


        Calendar myCalendar = Calendar.getInstance();
        mText = (TextView) view.findViewById(R.id.textdate1);
        mdaily = (Button) view.findViewById(R.id.dailybtn1);
        mdaily1 = (Button) view.findViewById(R.id.dailybtn2);
        mtext1 = (TextView) view.findViewById(R.id.textdate2);
        mtext2 = (TextView) view.findViewById(R.id.texttime1);
        mtext3 = (TextView) view.findViewById(R.id.texttime2);
        reset  = (Button)view.findViewById(R.id.resetbtn);

        mPickDate = (Button) view.findViewById(R.id.datebt);
        mPickTime = (Button) view.findViewById(R.id.timebtn);
        mPickDate1 = (Button) view.findViewById(R.id.datebt1);
        mPickTime1 = (Button) view.findViewById(R.id.timebtn1);

        SharedPreferences sf = getActivity().getSharedPreferences(moon, 0);
        String date1 = sf.getString("DATE1", "");
        String date2 = sf.getString("DATE2","");
        String time1 = sf.getString("TIME1","");
        String time2 = sf.getString("TIME2","");
        String daily1 = sf.getString("DAILY1","+");
        String daily2 = sf.getString("DAILY2","+");

        mText.setText(date1);
        mtext1.setText(date2);
        mtext2.setText(time1);
        mtext3.setText(time2);
        mdaily.setText(daily1);
        mdaily1.setText(daily2);

        mdaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle("물품을 입력해주세요");

                final EditText name = new EditText(getContext());
                alert.setView(name);

                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String username = name.getText().toString();
                        mdaily.setText(username);
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }
        });

        mdaily1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle("물품을 입력해주세요");

                final EditText name = new EditText(getContext());
                alert.setView(name);

                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String username = name.getText().toString();
                        mdaily1.setText(username);
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }
        });

        mPickDate.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(getContext(), dlistener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.show();
            }
        });

        mPickDate1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(getContext(), dlistener1, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.show();
            }
        });

        mPickTime1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();

                TimePickerDialog dialog = new TimePickerDialog(getContext(), tlistener1, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });

        mPickTime.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();

                TimePickerDialog dialog = new TimePickerDialog(getContext(), tlistener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle("초기화 하시겠습니까?");

                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        mdaily1.setText("+");
                        mdaily.setText("+");
                        mText.setText("");
                        mtext1.setText("");
                        mtext2.setText("");
                        mtext3.setText("");
                    }
                });

                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }

        });

        return view;
    }

    //데일리1번 상자날짜
    final private DatePickerDialog.OnDateSetListener dlistener = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("DefaultLocale")
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mText.setText(String.format("           %d-%d-%d", year, month + 1, dayOfMonth));
        }

    };
    //데일리2번 상자날짜
    final private DatePickerDialog.OnDateSetListener dlistener1 = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("DefaultLocale")
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mtext1.setText(String.format("           %d-%d-%d", year, month + 1, dayOfMonth));
        }

    };
    //데일리2번 상자시간
    final private TimePickerDialog.OnTimeSetListener tlistener1 = new TimePickerDialog.OnTimeSetListener() {
        @SuppressLint("DefaultLocale")
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mtext3.setText(String.format("           %d시 %d분", hourOfDay, minute));
        }
    };
    //데일리1번 상자시간
    final private TimePickerDialog.OnTimeSetListener tlistener = new TimePickerDialog.OnTimeSetListener() {
        @SuppressLint("DefaultLocale")
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mtext2.setText(String.format("           %d시 %d분", hourOfDay, minute));
        }
    };
    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences sf = getActivity().getSharedPreferences(moon,0);
        SharedPreferences.Editor editor = sf.edit();
        String date1 = mText.getText().toString();
        String date2 = mtext1.getText().toString();
        String time1 = mtext2.getText().toString();
        String time2 = mtext3.getText().toString();
        String daily1 = mdaily.getText().toString();
        String daily2 = mdaily1.getText().toString();
        editor.putString("DATE1",date1);
        editor.putString("DATE2",date2);
        editor.putString("TIME1",time1);
        editor.putString("TIME2",time2);
        editor.putString("DAILY1",daily1);
        editor.putString("DAILY2",daily2);
        editor.commit();
    }
}