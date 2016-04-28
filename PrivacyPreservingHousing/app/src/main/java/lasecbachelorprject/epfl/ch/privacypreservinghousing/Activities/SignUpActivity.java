package lasecbachelorprject.epfl.ch.privacypreservinghousing.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import lasecbachelorprject.epfl.ch.privacypreservinghousing.R;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends FragmentActivity {
    private ParseUser user;

    // UI references.
    private AutoCompleteTextView userNameView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private String emailTakenError = "The email that you provided is already taken, please try with a new one";
    private String nameTaken = "The user name is already taken, try with an new one";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mPasswordView = (EditText) findViewById(R.id.input_password);
        mPasswordView.setText("");


        Button SignInButton = (Button) findViewById(R.id.email_sign_in_button);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignIn();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSignIn() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString();
        String userName = userNameView.getText().toString().trim();

        View focusView = null;
        boolean cancel = false;

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if(TextUtils.isEmpty(userName)){
            userNameView.setError(getString(R.string.error_field_required));
            focusView = userNameView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;

        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            //  There was an error; don't attempt login and focus the first
            //  form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            user = new ParseUser();
            user.setUsername(userName);
            user.setPassword(password);
            user.setEmail(email);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        startMainPage();

                    } else {
                        AlertDialog.Builder ErrorNotification = new AlertDialog.Builder(SignUpActivity.this);

                        if (e.getCode() == ParseException.EMAIL_TAKEN) {
                            ErrorNotification.setMessage(emailTakenError);
                        }
                        if (e.getCode() == ParseException.USERNAME_TAKEN) {
                            ErrorNotification.setMessage(nameTaken);
                        } else {
                            ErrorNotification.setMessage(e.getMessage());
                        }
                        ErrorNotification.setNeutralButton("Return", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog dialogBox = ErrorNotification.create();
                        dialogBox.show();
                    }
                }
            });
        }
    }

    private void startMainPage() {
        Intent intent = new Intent(SignUpActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    /**
     * Check the validity of an email
     *
     * @param email to check for validity
     * @return true if it's valid, false otherwise
     */
    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    /**
     * Check the validity of a password
     * @param password to check
     * @return true if valid, false otherwise
     */
    private boolean isPasswordValid(String password) {
        return password.length() >= 5 && password.contains("\\d");
    }
}

