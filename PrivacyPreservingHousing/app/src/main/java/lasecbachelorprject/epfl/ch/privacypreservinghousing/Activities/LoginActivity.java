package lasecbachelorprject.epfl.ch.privacypreservinghousing.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import lasecbachelorprject.epfl.ch.privacypreservinghousing.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends FragmentActivity {

    private ParseUser user;


    // UI references.
    private AutoCompleteTextView userNameView;
    private EditText mPasswordView;
    private TextView signUpLink;
    private TextView passwordFogotLink;

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


            dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setNeutralButton("Return", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });


            mPasswordView = (EditText) findViewById(R.id.login_password);
            mPasswordView.setText("");
            //TODO: add Sign up link
           // signUpLink = (TextView ) findViewById(R.id.signUpLink);

            signUpLink.setPaintFlags(signUpLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            signUpLink.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intent);
                }
            });


            setLocationAutomplete();
;


        }
    }

    private void setLocationAutomplete() {
        /*PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("Set autoPlace", "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Error autoComp", "An error occurred: " + status);
            }
        });*/
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

    /**
     * Methods that provides login directly to Parse
     */
    private void attemptLogIn() {
        // Reset errors.
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userName = userNameView.getText().toString().trim();
        String password = mPasswordView.getText().toString();


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
}

