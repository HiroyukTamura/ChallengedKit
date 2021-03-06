/*
 * Copyright 2017 Hiroyuki Tamura
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cks.hiroyuki2.worksupport3.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.cks.hiroyuki2.worksupprotlib.Entity.Group;
import com.cks.hiroyuki2.worksupport3.Fragments.GroupSettingFragment;
import com.cks.hiroyuki2.worksupport3.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import icepick.Icepick;
import icepick.State;

import static com.cks.hiroyuki2.worksupport3.Util.initAdMob;

/**
 * {@link GroupSettingFragment} の親。今のところこのActivityが関わるFragmentはこれひとつのみ。
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_group_setting)
public class GroupSettingActivity extends AppCompatActivity {
    private static final String TAG = "MANUAL_TAG: " + GroupSettingActivity.class.getSimpleName();
    private boolean isSavedInstance = false;
    public static final String NEW_GROUP_NAME = "NEW_GROUP_NAME";
    @State String newGroupName = null;
    @ViewById(R.id.toolbar) Toolbar toolbar;
    @ViewById(R.id.fragment_container) FrameLayout fl;
    @State @Extra Group group;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        isSavedInstance = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @AfterViews
    void afterViews() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title_setting_group);
        initAdMob(this);

        if (isSavedInstance){
            setFragment();
        }
    }
    
    private void setFragment(){
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        GroupSettingFragment fragment = GroupSettingFragment.newInstance(group);
        t.add(R.id.fragment_container, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @see MainActivity#onResultExitGroup(int, Group)
     * を発火させたいのでこのような実装になってる
     */
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent());
        finish();
        super.onBackPressed();
    }

//    public void setNewGroupName(String newGroupName) {
//        this.newGroupName = newGroupName;
//    }
}
