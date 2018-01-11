package com.immymemine.kevin.skillshare.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.main_adapter.SearchRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.discover.SearchClass;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.ClassService;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    EditText editTextSearch;
    ImageButton toolbarButtonCancel;
    RecyclerView searchRecyclerView;
    SearchRecyclerViewAdapter searchRecyclerViewAdapter;

    String searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initiateView();
        setRecyclerView();
        setEditTextWatcher();
    }

    private void setEditTextWatcher() {
        RxTextView.textChanges(editTextSearch).subscribe(
                (CharSequence c) -> {
                    if(c.length() > 0) {
                        toolbarButtonCancel.setVisibility(View.VISIBLE);
                    } else {
                        toolbarButtonCancel.setVisibility(View.INVISIBLE);
                    }
                    searchContent = c.toString();
                }
        );
    }

    private void initiateView() {
        toolbarButtonCancel = findViewById(R.id.toolbar_button_cancel);
        toolbarButtonCancel.setOnClickListener(v -> editTextSearch.setText(""));
        editTextSearch = findViewById(R.id.edit_text_search);
        searchRecyclerView = findViewById(R.id.recycler_view_search);

        findViewById(R.id.toolbar_button_back).setOnClickListener(v -> finish());

        editTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(searchContent.length() >= 3) {
                    // TODO show progress bar
                    RetrofitHelper.createApi(ClassService.class)
                            .search(searchContent)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    (List<SearchClass> searchClasses) -> searchRecyclerViewAdapter.setData(searchClasses),
                                    (Throwable error) -> Log.e("search error", error.getMessage())
                            );

                    editTextSearch.setText("");
                } else {
                    Toast.makeText(SearchActivity.this, "your search is too short\n(minimum is 3 characters.)", Toast.LENGTH_LONG).show();
                }
            }
            return false;
        });

        editTextSearch.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                return true;
            }
            return true;
        });
    }

    private void setRecyclerView() {
        searchRecyclerView.setHasFixedSize(true); // this helps recyclerview size optimization ... need more study
        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(this);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        searchRecyclerView.setAdapter(searchRecyclerViewAdapter);
    }

    private void handleResponse(List<SearchClass> searchClasses) {
        // TODO hide progress bar

    }

    private void handleError(Throwable error) {

    }
}
