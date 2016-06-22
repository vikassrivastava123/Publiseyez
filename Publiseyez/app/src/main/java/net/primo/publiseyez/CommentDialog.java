package net.primo.publiseyez;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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
 * Created by Vikas on 6/12/2016.
 */

public class CommentDialog extends DialogFragment implements View.OnClickListener{

    EditText mEditText;

    public static interface OnCompleteListener {
        public abstract void onComplete(String comment);
    }
    private OnCompleteListener mListener;
    // make sure the Activity implemented it
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View v = inflater.inflate(R.layout.layout_comment_dialog, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        // Get field from view
        mEditText = (EditText) v.findViewById(R.id.commentText);
        mEditText.requestFocus();

        ImageView submit = (ImageView) v.findViewById(R.id.submitCommentImageView);
        submit.setOnClickListener(this);
        TextView submitTextView = (TextView)v.findViewById(R.id.submitCommentTextView);
        submitTextView.setOnClickListener(this);

        ImageView cancel = (ImageView) v.findViewById(R.id.cancelCommentImageView);
        cancel.setOnClickListener(this);
        TextView cancelTextView = (TextView)v.findViewById(R.id.cancelCommentTextView);
        cancelTextView.setOnClickListener(this);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void postCommentToActivity (String comment){
        mListener.onComplete(comment);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitCommentImageView:
                Log.d("comment", "new comment submitted by user");
                postCommentToActivity(mEditText.getText().toString());
                this.dismiss();
                break;
            case R.id.submitCommentTextView:
                Log.d("comment", "new comment submitted by user");
                postCommentToActivity(mEditText.getText().toString());
                this.dismiss();
                break;
            case R.id.cancelCommentImageView:
                Log.d ("comment", "Cancelled");
                this.dismiss();
                break;
            case R.id.cancelCommentTextView:
                Log.d ("comment", "Cancelled");
                this.dismiss();
                break;
            default:
                break;
        }
    }
}
