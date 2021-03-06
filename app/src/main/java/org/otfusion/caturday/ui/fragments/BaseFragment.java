package org.otfusion.caturday.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.squareup.otto.Bus;

import org.otfusion.caturday.application.VoteCatsApplication;
import org.otfusion.caturday.service.CatService;
import org.otfusion.caturday.ui.activities.CatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    @Inject
    Bus bus;

    @Inject
    CatService catService;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        VoteCatsApplication voteCatsApplication = (VoteCatsApplication)getActivity().getApplication();
        voteCatsApplication.getApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayoutId(), container, false);
        ButterKnife.bind(this, view);
        getBus().register(this);
        loadUIContent();
        ActionBar supportActionBar = getCatActivity().getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(getTitle());
        }
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getBus().unregister(this);
    }

    CatActivity getCatActivity() {
        return (CatActivity) getActivity();
    }

    public String getTitle() {
        return getActivity().getResources().getString(getTitleId());
    }

    protected AdapterView.AdapterContextMenuInfo getAdapterContextMenuInfo(
            ContextMenu.ContextMenuInfo menuInfo) {
        return (AdapterView.AdapterContextMenuInfo) menuInfo;
    }

    Intent obtainShareImageIntent(Uri fileUri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.setType("image/jpeg");
        return shareIntent;
    }

    protected Bus getBus() {
        return bus;
    }

    CatService getCatService() {
        return catService;
    }

    public abstract int getContentLayoutId();

    public abstract void loadUIContent();

    public abstract int getTitleId();

}
