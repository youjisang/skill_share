//package com.immymemine.kevin.skillshare.activity;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import com.immymemine.kevin.skillshare.R;
//import com.immymemine.kevin.skillshare.adapter.SearchRecyclerViewAdapter;
//
//import java.util.ArrayList;
//
//public class SearchActivity extends AppCompatActivity {
//
//    EditText edit_text_search;
//    ImageButton toolbar_button_back, toolbar_button_cancel;
//    RecyclerView searchRecyclerView;
//    SearchRecyclerViewAdapter searchRecyclerViewAdapter;
//    List<Search> data, data2;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search);
//        initiateView();
//        data = new ArrayList<>();
//        data2 = new ArrayList<>();
//        data.add(new Search("music", "allen", "http://s3.amazonaws.com/skillshare/uploads/parentClasses/2f4f5efd1d503e7131249c94cf2ed7bc/681a4bd7", "3:40", "150", "200"));
//        data.add(new Search("coding", "james", "https://static.skillshare.com/uploads/project/95045c8c57d1227a6cfb442bd5d3661d/219967c6", "4:40", "200", "450"));
//        data.add(new Search("hobby", "sera", "https://static.skillshare.com/uploads/project/d00cdd4401224eb969fc135174b89135/b6adbd89", "3:35", "180", "320"));
//        initRecyclerView();
//
//        textWatcherControl();
//
//    }
//
//    private void textWatcherControl() {
//
//        edit_text_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                toolbar_button_cancel.setVisibility(View.VISIBLE);
//                toolbar_button_cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        edit_text_search.setText("");
//                        searchRecyclerView.setVisibility(View.INVISIBLE);
//                    }
//                });
//
//                Log.e("CharSequence1", "======CharSequence s =====" + s);
//                if (s.length() == 0) {
//                    toolbar_button_cancel.setVisibility(View.INVISIBLE);
//                }
//                enterEvent(s);
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }
//
//
//    private void enterEvent(CharSequence c) {
//        edit_text_search.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER) && (c.length() > 2)) {
//
//                    data2.clear();
//
//                    for (int i = 0; i < data.size(); i++) {
//                        Log.e("check1", "======check1 =====");
//                        if (c.toString().equals(data.get(i).getTitle().toString())) {
//                            data2.add(new Search(data.get(i).getTitle().toString(), data.get(i).getTutor().toString(), data.get(i).getImageUrl().toString(), data.get(i).getDuration().toString(), data.get(i).getThumbup().toString(), data.get(i).getAttendanceNum().toString()));
//                        }
//                    }
//
//                    searchRecyclerViewAdapter.setData(data2, data2.size());
//                    searchRecyclerView.setVisibility(View.VISIBLE);
//
//                    return true;
//
//
//                } else if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER) && (c.length() <= 2)) {
//                    //?
//                    data2.clear();
//                    //
//                    Toast.makeText(getApplicationContext(), "your search is too short\n(minimum is 3 characters.)", Toast.LENGTH_LONG).show();
//                    edit_text_search.setText("");
//
//                }
//                return true;
//            }
//        });
//    }
//
//    private void initiateView() {
//
//
//        toolbar_button_back = findViewById(R.id.toolbar_button_back);
//        toolbar_button_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        toolbar_button_cancel = findViewById(R.id.toolbar_button_cancel);
//        // 처음에 액티비티 들어갔을 때는 invisible 상태
//        toolbar_button_cancel.setVisibility(View.INVISIBLE);
//
//        edit_text_search = findViewById(R.id.edit_text_search);
//        searchRecyclerView = findViewById(R.id.recycler_view_search);
//    }
//
//    private void initRecyclerView() {
//        searchRecyclerView.setHasFixedSize(true);
//        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(this, data);
//        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        searchRecyclerView.setAdapter(searchRecyclerViewAdapter);
//        searchRecyclerView.setVisibility(View.INVISIBLE);
//
//    }
//
//}
