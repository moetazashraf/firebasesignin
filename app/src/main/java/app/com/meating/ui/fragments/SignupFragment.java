package app.com.meating.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.utilities.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import app.com.meating.R;
import app.com.meating.ui.activities.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static app.com.meating.utilities.FirebaseConstants.EMAIL_NODE;
import static app.com.meating.utilities.FirebaseConstants.USERINFO_NODE;
import static app.com.meating.utilities.FirebaseConstants.USERNAME_NODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {


    @BindView(R.id.signup_username)
    EditText username;
    @BindView(R.id.signup_email)
    EditText email;
    @BindView(R.id.signup_password)
    EditText password;
    @BindView(R.id.signup_countrykey)
    EditText countrykey;
    @BindView(R.id.signup_phonenumber)
    EditText signupPhonenumber;
    @BindView(R.id.signup_edu)
    EditText Edu;
    @BindView(R.id.signup_promocode)
    EditText promocode;
    @BindView(R.id.signup_buttonsignup)
    Button signupButton;
    Unbinder unbinder;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (firebaseAuth.getCurrentUser() != null) {
            getActivity().finish();
            startActivity(new Intent(getContext(), MainActivity.class));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.signup_buttonsignup)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signup_buttonsignup:
                try {
                    regiterUser();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void regiterUser() {
        final String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            initializeFirebasedatabaseVariables(emailStr);
                            updateProfileAndLaunch(user);
                        } else {
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();

                            return;
                        }
                    }
                });

    }


    private void updateProfileAndLaunch(FirebaseUser user) {
        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(username.getText().toString())
                .build();

        user.updateProfile(userProfileChangeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (getActivity() != null){
                            getActivity().finish();
                            startActivity(new Intent(getContext(), MainActivity.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initializeFirebasedatabaseVariables(String emailStr) {


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                mDatabase.child(USERINFO_NODE).child(userId)
                        .child(EMAIL_NODE).setValue(emailStr);

                mDatabase.child(USERINFO_NODE).child(userId)
                        .child(USERNAME_NODE).setValue(username.getText().toString());

            mDatabase.child(USERINFO_NODE).child(userId)
                    .child("phonenumber").setValue(countrykey.getText().toString().concat(signupPhonenumber.getText().toString()));

            mDatabase.child(USERINFO_NODE).child(userId)
                    .child("education").setValue(Edu.getText().toString());

            mDatabase.child(USERINFO_NODE).child(userId)
                    .child("promocode").setValue(promocode.getText().toString());

        }

    }

}
