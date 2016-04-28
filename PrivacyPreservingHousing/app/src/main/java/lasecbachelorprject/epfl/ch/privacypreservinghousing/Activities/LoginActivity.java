package lasecbachelorprject.epfl.ch.privacypreservinghousing.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import lasecbachelorprject.epfl.ch.privacypreservinghousing.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private ParseUser user;


    // UI references.
    private Toolbar toolbar;
    private EditText inputName, inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutPassword;
    private Button btnSignUp;

    private String loginErrorMessage = "Invalid user name or password, please try again. Make sure that you've Signed Up";
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    //Facebook

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        user = ParseUser.getCurrentUser();


        if(user != null){
            startMainMenu();
        }
        else {

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
            inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
            inputName = (EditText) findViewById(R.id.input_name);
            inputPassword = (EditText) findViewById(R.id.input_password);
            btnSignUp = (Button) findViewById(R.id.btn_signup);

            inputName.addTextChangedListener(new MyTextWatcher(inputName));
            inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitForm();
                }
            });



        }
    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
    }


    private void startMainMenu() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    /*
    * Validating form
    */
    private void submitForm() {
        if (!validateName()) {
            return;
        }


        if (!validatePassword()) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }




    private boolean validatePassword() {


        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }



    /**
     * Methods that provides login directly to Parse
     */
    private void attemptLogIn() {
        // Reset errors.

        // Store values at the time of the login attempt.
        String userName = inputName.getText().toString().trim();
        String password = inputPassword.getText().toString();


        ParseUser.logInInBackground(userName, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    if((Boolean)user.get("emailVerified")){
                        LoginActivity.this.user =  user;
                        startMainMenu();
                    }
                    else{
                        ParseUser.logOutInBackground();
                        dialogBuilder.setMessage(getString(R.string.emailConfirmationMessage));
                        alertDialog = dialogBuilder.create();
                        alertDialog.show();
                    }
                } else {

                    dialogBuilder.setMessage(loginErrorMessage);
                    alertDialog = dialogBuilder.create();
                    alertDialog.show();
                }

            }
        });

    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}

