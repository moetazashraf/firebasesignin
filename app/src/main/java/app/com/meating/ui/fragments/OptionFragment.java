package app.com.meating.ui.fragments;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.Spanned;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import app.com.meating.R;
import app.com.meating.utilities.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static java.lang.String.format;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionFragment extends Fragment {


    @BindView(R.id.option_button_option1)
    Button option1;
    @BindView(R.id.option_button_option2)
    Button option2;
    @BindView(R.id.option_textlogin)
    TextView loginOption;
    Unbinder unbinder;

    public OptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    if (getActivity().getSupportFragmentManager().getBackStackEntryCount() <1){
                       enableclicklisteners();
                    }
                }
            });
        }
    }

    private void enableclicklisteners() {
        loginOption.setEnabled(true);
        option1.setEnabled(true);
        option2.setEnabled(true);
    }

    private void disableclicklisteners() {
        loginOption.setEnabled(false);
        option1.setEnabled(false);
        option2.setEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_option, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.option_button_option1, R.id.option_button_option2, R.id.option_textlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.option_button_option1:
                signup();
                break;
            case R.id.option_button_option2:
                break;
            case R.id.option_textlogin:
                login();
                break;
        }
    }

    private void login() {
        disableclicklisteners();
        LoginFragment loginFragment = new LoginFragment();

        slideAnimation(loginFragment);
        getActivity().getSupportFragmentManager()
                .beginTransaction().addToBackStack(null).add(R.id.fregister, loginFragment)
                .commit();
    }

    private void signup() {
        disableclicklisteners();
        SignupFragment signupFragment = new SignupFragment();
        slideAnimation(signupFragment);
        getActivity().getSupportFragmentManager()
                .beginTransaction().addToBackStack(null).add(R.id.fregister, signupFragment)
                .commit();
    }

    private void slideAnimation(Fragment fragment){
        Slide slideTransition = null ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            slideTransition = new Slide(Gravity.END);
            slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

            ChangeBounds changeBoundsTransition = new ChangeBounds();
            changeBoundsTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

            fragment.setEnterTransition(slideTransition);
            fragment.setAllowEnterTransitionOverlap(false);
            fragment.setAllowReturnTransitionOverlap(false);
            fragment.setSharedElementEnterTransition(changeBoundsTransition);
        }
    }


}
