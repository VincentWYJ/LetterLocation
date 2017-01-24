package com.letterlocation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private LetterView mLetterView;
    private TextView mLetterTV;
    private LetterAdapter mLetterAdapter;
    private List<LetterEntity> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mLetterView = (LetterView) findViewById(R.id.letter_view);
        mLetterTV = (TextView) findViewById(R.id.letter_dialog);
        mLetterView.setTextView(mLetterTV);

        mLetterView.setOnTouchingLetterChangedListener(new LetterView.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = mLetterAdapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    mListView.setSelection(position);
                }

            }
        });

        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplication(), ((LetterEntity)mLetterAdapter.getItem(position)).getContent(), Toast.LENGTH_SHORT).show();
            }
        });

        mDataList = filledData(getResources().getStringArray(R.array.date));
        PinyinComparator pinyinComparator = new PinyinComparator();
        Collections.sort(mDataList, pinyinComparator);
        mLetterAdapter = new LetterAdapter(this, mDataList);
        mListView.setAdapter(mLetterAdapter);
    }

    private List<LetterEntity> filledData(String [] data) {
        List<LetterEntity> mList = new ArrayList<LetterEntity>();
        for (int i=0; i<data.length; i++) {
            LetterEntity letterEntity = new LetterEntity();
            letterEntity.setContent(data[i]);
            String pinyin = PinyinUtility.getPinyin(data[i]);
            letterEntity.setPinyin(pinyin);
            String sortString = pinyin.substring(0, 1);
            if (sortString.matches("[A-Z]")) {
                letterEntity.setLetter(sortString);
            } else {
                letterEntity.setLetter("#");
            }
            mList.add(letterEntity);
        }
        return mList;
    }
}
