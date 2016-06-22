package net.primo.publiseyez;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Vikas on 6/21/2016.
 */

public class ChangePassword extends DialogFragment implements View.OnClickListener{

    EditText mOldPassword;
    EditText mNewPassword;
    EditText mNewPasswordReEnter;

    public static interface OnCompleteListener {
        public abstract void onComplete(String oldPassword, String newPassword, String newPasswordReEnter);
    }
    private ChangePassword.OnCompleteListener mListener;
    // make sure the Activity implemented it
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (ChangePassword.OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View v = inflater.inflate(R.layout.layout_change_password, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        // Get field from view
        mOldPassword = (EditText) v.findViewById(R.id.changePasswordOldPassword);
        mOldPassword.requestFocus();

        mNewPassword = (EditText) v.findViewById(R.id.changePasswordNewPassword);
        mNewPasswordReEnter = (EditText) v.findViewById(R.id.changePasswordNewPasswordReEnter);

        ImageView submit = (ImageView) v.findViewById(R.id.changePasswordSubmitImageView);
        submit.setOnClickListener(this);
        TextView submitTextView = (TextView)v.findViewById(R.id.changePasswordSubmitTextView);
        submitTextView.setOnClickListener(this);

        ImageView cancel = (ImageView) v.findViewById(R.id.changePasswordCancelImageView);
        cancel.setOnClickListener(this);
        TextView cancelTextView = (TextView)v.findViewById(R.id.changePasswordCancelTextView);
        cancelTextView.setOnClickListener(this);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void postCommentToActivity (String oldPassword, String newPassword, String newPasswordReEnter){
        mListener.onComplete(oldPassword, newPassword, newPasswordReEnter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changePasswordSubmitImageView:
                if (mOldPassword.getText().toString().isEmpty() ||
                        mNewPassword.getText().toString().isEmpty() ||
                        mNewPasswordReEnter.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "One or more field is empty", Toast.LENGTH_SHORT).show();
                }
                else if (mOldPassword.getText().toString().equals(mNewPassword.getText().toString()))
                {
                    mNewPassword.setText(null);
                    mNewPassword.requestFocus();
                    mNewPasswordReEnter.setText(null);
                    Toast.makeText(getActivity().getApplicationContext(),
                            "New password is same as old password", Toast.LENGTH_SHORT).show();
                }
                else if (mNewPassword.getText().toString().equals(mNewPasswordReEnter.getText().toString()))
                {
                    postCommentToActivity (mOldPassword.getText().toString(), mNewPassword.getText().toString(),
                            mNewPasswordReEnter.getText().toString());
                    Log.d("Change Password", "Password is changed by user");
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Re-Entered password should be same as New password", Toast.LENGTH_SHORT).show();
                    mNewPasswordReEnter.setText(null);
                    mNewPasswordReEnter.requestFocus();
                }
                this.dismiss();
                break;
            case R.id.changePasswordSubmitTextView:
                if (mOldPassword.getText().toString().isEmpty() ||
                        mNewPassword.getText().toString().isEmpty() ||
                        mNewPasswordReEnter.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "One or more field is empty", Toast.LENGTH_SHORT).show();
                }
                else if (mOldPassword.getText().toString().equals(mNewPassword.getText().toString()))
                {
                    mNewPassword.setText(null);
                    mNewPassword.requestFocus();
                    mNewPasswordReEnter.setText(null);
                    Toast.makeText(getActivity().getApplicationContext(),
                            "New password is same as old password", Toast.LENGTH_SHORT).show();
                }
                else if (mNewPassword.getText().toString().equals(mNewPasswordReEnter.getText().toString()))
                {
                    postCommentToActivity (mOldPassword.getText().toString(), mNewPassword.getText().toString(),
                            mNewPasswordReEnter.getText().toString());
                    Log.d("Change Password", "Password is changed by user");
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Re-Entered password should be same as New password", Toast.LENGTH_SHORT).show();
                    mNewPasswordReEnter.setText(null);
                    mNewPasswordReEnter.requestFocus();
                }
                this.dismiss();
                break;
            case R.id.changePasswordCancelImageView:
                Log.d ("comment", "Cancelled");
                this.dismiss();
                break;
            case R.id.changePasswordCancelTextView:
                Log.d ("comment", "Cancelled");
                this.dismiss();
                break;
            default:
                break;
        }
    }
}
