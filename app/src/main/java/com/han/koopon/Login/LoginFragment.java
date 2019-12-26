package com.han.koopon.Login;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.han.koopon.Main.MainFragment;
import com.han.koopon.R;
import com.han.koopon.Util.PFUtil;


import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private FirebaseAuth mAuth;
    private ProgressBar progress_bar;
    private EditText id_tf,password_tf;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        bindingViews(view);
        return view;
    }
    private void bindingViews( View view){
        id_tf = view.findViewById(R.id.login_id_tf);
        password_tf= view.findViewById(R.id.login_password_tf);
        progress_bar = view.findViewById(R.id.progress_bar);
        view.findViewById(R.id.login_button).setOnClickListener(loginListner);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    public void updateUI(FirebaseUser user){

    }

    //로그인
    View.OnClickListener loginListner = (v)->{
        String id = id_tf.getText().toString();
        String password = password_tf.getText().toString();

        //글자 체크
        if (id == null || password == null ||
            id.equals("") || password.equals("")){
            Toast.makeText(getContext(), "아이디 또는 비밀번호는 빈칸일 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        //이메일 유효성체크
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (!id.matches(regex)){
            Toast.makeText(getContext(), "이메일 형식에 맞게 써주세용", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i(TAG,id+"/"+password);
        progress_bar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(id, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress_bar.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "## signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                    .replace(R.id.mainFrame, MainFragment.newInstance() )
                                    .addToBackStack(null)
                                    .commit();
                            PFUtil.savePreference(getContext(),PFUtil.AUTO_LOGIN_ID,user.getEmail());
                        } else {
                            // If sign in fails, display a message to the user.
                            String err = task.getException().getMessage();
                            Log.w(TAG, "signInWithEmail:failure "+err);

                            if ("There is no user record corresponding to this identifier. The user may have been deleted.".contains(err)){
                                Toast.makeText(getContext(), "회원가입 내역이 없습니다. 회원가입해주세요.", Toast.LENGTH_SHORT).show();
                            }

                            updateUI(null);
                        }
                    }
                });
    };

    //회원가입
    View.OnClickListener signInListner = (v)->{
        String id = id_tf.getText().toString();
        String password = password_tf.getText().toString();
        Log.i(TAG,id+"/"+password);
        mAuth.createUserWithEmailAndPassword(id, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress_bar.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    };

}
