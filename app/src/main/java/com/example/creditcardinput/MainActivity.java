package com.example.creditcardinput;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.creditcardinput.data.CreditCard;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout bCreditCardNumber;
    private TextInputLayout bExpirationDate;
    private TextInputLayout bCvv;
    private TextInputLayout bFirstName;
    private TextInputLayout bLastName;
    private TextInputEditText etCreditCardNumber;
    private TextInputEditText etExpirationDate;
    private TextInputEditText etCvv;
    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;
    private MaterialButton submitbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bCreditCardNumber = findViewById(R.id.box_credit_card_number);
        bExpirationDate = findViewById(R.id.box_expiration_date);
        bCvv = findViewById(R.id.box_security_code);
        bFirstName = findViewById(R.id.box_first_name);
        bLastName = findViewById(R.id.box_last_name);
        etCreditCardNumber = findViewById(R.id.credit_card_number);
        etExpirationDate = findViewById(R.id.expiration_date);
        etCvv =  findViewById(R.id.cvv);
        etFirstName = findViewById(R.id.first_name);
        etLastName = findViewById(R.id.last_name);
        submitbtn = findViewById(R.id.btn_submit_payment);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateCard(v);
            }
        });
    }

    public void ValidateCard(View view) {
        clearPreviousErrorMessages();
        String creditCardNumber = etCreditCardNumber.getText().toString();
        String expirationDate = etExpirationDate.getText().toString();
        String cvv = etCvv.getText().toString();
        String firstName = CreditCard.ValidName(etFirstName.getText().toString());
        etFirstName.setText(firstName);
        String lastName = CreditCard.ValidName(etLastName.getText().toString());
        etLastName.setText(lastName);

        if (!CreditCard.isValidCardNumber(creditCardNumber)) {
            bCreditCardNumber.setError(getString(R.string.invalid_card_number));
            etCreditCardNumber.requestFocus();
        } else if (!CreditCard.isValidExpirationDate(expirationDate)) {
            bExpirationDate.setError(getString(R.string.invalid_expiration_date));
            etExpirationDate.requestFocus();
        } else if (!CreditCard.isValidCVV(creditCardNumber, cvv)) {
            bCvv.setError(getString(R.string.invalid_cvv));
            etCvv.requestFocus();
        } else if (firstName.isEmpty()) {
            bFirstName.setError(getString(R.string.please_enter_first_name));
            etFirstName.requestFocus();
        } else if (lastName.isEmpty()) {
            bLastName.setError(getString(R.string.please_enter_last_name));
            etLastName.requestFocus();
        } else {
            closeKeyboard(view);
            CreditCard creditCard = new CreditCard(creditCardNumber, expirationDate, cvv, firstName, lastName);
            alertDialog(submitCreditCard(creditCard), null, getString(R.string.ok));
        }
    }

    private String submitCreditCard(CreditCard creditCard) {
        return getString(R.string.successful);

    }

    private void closeKeyboard(View view) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void clearPreviousErrorMessages() {
        bCreditCardNumber.setError(null);
        bExpirationDate.setError(null);
        bCvv.setError(null);
        bFirstName.setError(null);
        bLastName.setError(null);

    }

    private void alertDialog(String title, String message, String buttonText) {
        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonText, null)
                .show();
    }
}