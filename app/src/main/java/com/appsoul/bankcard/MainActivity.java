package com.appsoul.bankcard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsoul.bankcard.utils.CreditCardNumberTextWatcher;
import com.appsoul.bankcard.utils.CreditCardType;
import com.appsoul.bankcard.utils.ResourceUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout month_layout, year_layout,
            cvv_layout, fn_layout, ln_layout,
            country_layout, zip_layout;
    private EditText editTextCreditCardNumber,
            editText_month, editText_year, editText_cvv,
            fn, ln, zipCode, countryEdt;
    private Button submitBtn;

    ArrayList<String> arrayList;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.month_layout = (TextInputLayout) this.findViewById(R.id.month_layout);
        this.year_layout = (TextInputLayout) this.findViewById(R.id.year_layout);
        this.cvv_layout = (TextInputLayout) this.findViewById(R.id.cvv_layout);
        this.fn_layout = (TextInputLayout) this.findViewById(R.id.fn_layout);
        this.ln_layout = (TextInputLayout) this.findViewById(R.id.ln_layout);
        this.country_layout = (TextInputLayout) this.findViewById(R.id.country_layout);
        this.zip_layout = (TextInputLayout) this.findViewById(R.id.zip_layout);

        this.editTextCreditCardNumber = (EditText) this.findViewById(R.id.editText_creditCardNumber);
        this.editText_month =(EditText) this.findViewById(R.id.editText_month);
        this.editText_year =(EditText) this.findViewById(R.id.editText_year);
        this.editText_cvv =(EditText) this.findViewById(R.id.editText_cvv);
        this.fn =(EditText) this.findViewById(R.id.fn);
        this.ln =(EditText) this.findViewById(R.id.ln);
        this.zipCode =(EditText) this.findViewById(R.id.zipCode);
        this.countryEdt =(EditText) this.findViewById(R.id.countryEdt);
        this.countryEdt.setShowSoftInputOnFocus(false);

        this.submitBtn = (Button) this.findViewById(R.id.submitBtn);

        TextWatcher cardNumberTextWatcher = new CreditCardNumberTextWatcher(this.editTextCreditCardNumber);
        this.editTextCreditCardNumber.addTextChangedListener(cardNumberTextWatcher);

        editText_month.setFilters(new InputFilter[]{ new InputFilterExpiry("1", "12")});
        editText_year.setFilters(new InputFilter[]{ new InputFilterExpiry("1", "99")});

        TextWatcher cvvTextWatcher = new CvvTextWatcher(this.editText_cvv);
        this.editText_cvv.addTextChangedListener(cvvTextWatcher);

        countryFunc();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validMonth()) {
                    return;
                } else if (!validYear()) {
                    return;
                } else if (!validCVV()) {
                    return;
                } else if (!validfn()) {
                    return;
                } else if (!validln()) {
                    return;
                } else if (!validCountry()) {
                    return;
                } else if (!validZip()) {
                    return;
                } else {
                    submitFunc();
                }
            }
        });

//        if (areDrawablesIdentical(getResources().getDrawable(R.drawable.icon_none),
//                editTextCreditCardNumber.getCompoundDrawables()[2])) {
//            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
//        }

    }

    private void submitFunc() {
        // Initialize dialog
        dialog=new Dialog(MainActivity.this);

        // set custom dialog
        dialog.setContentView(R.layout.payment_success_dialog);

//        // set custom height and width
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        // set transparent background
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // show dialog
        dialog.show();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());

        // Initialize and assign variable
        TextView cn = dialog.findViewById(R.id.cn);
        TextView date = dialog.findViewById(R.id.date);
        TextView name = dialog.findViewById(R.id.name);
        TextView country = dialog.findViewById(R.id.country);
        TextView zip = dialog.findViewById(R.id.zip);
        TextView dismiss = dialog.findViewById(R.id.dismiss);

        cn.setText("" + editTextCreditCardNumber.getText().toString().trim());
        date.setText("" + currentDateandTime);
        name.setText("" + fn.getText().toString().trim() + " " + ln.getText().toString().trim());
        country.setText("" + countryEdt.getText().toString().trim());
        zip.setText("" + zipCode.getText().toString().trim());


        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void countryFunc() {
        // initialize array list
        arrayList=new ArrayList<>();

        // set value in array list
        arrayList.add("Pakistan");
        arrayList.add("Bahrain");
        arrayList.add("Iran ");
        arrayList.add("Iraq ");
        arrayList.add("Israel ");
        arrayList.add("Israeli ");
        arrayList.add("Controlled ");
        arrayList.add("Territory ");
        arrayList.add("Jordan ");
        arrayList.add("Kuwait ");
        arrayList.add("Lebanon ");
        arrayList.add("Oman ");
        arrayList.add("Qatar ");
        arrayList.add("Saudi Arabia");
        arrayList.add("Syria ");
        arrayList.add("Turkey ");
        arrayList.add("Algeria ");
        arrayList.add("Tunisia");
        arrayList.add("Sudan ");
        arrayList.add("Somalia ");

        this.countryEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(MainActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);

                // Initialize array adapter
                ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);

                // set adapter
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        countryEdt.setText(adapter.getItem(position));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    public class CreditCardNumberTextWatcher implements TextWatcher {

        private static final String LOG_TAG = "Umair";
        public static final char SEPARATOR = ' ';

        private EditText editText;

        private int after;
        private String beforeString;

        public CreditCardNumberTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            this.after = after;
            this.beforeString = s.toString();
            Log.e(LOG_TAG, "@@beforeTextChanged s=" + s
                    + " . start="+ start+" . after=" + after+" . count="+ count);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.e(LOG_TAG, "@@onTextChanged s=" + s
                    + " . start="+ start+" . before=" + before+" . count="+ count);
            String newText = s.toString();

            String textPrefix = newText.substring(0, start);
            String textInserted = newText.substring(start, start + this.after);
            String textSuffix = newText.substring(start + this.after);
            String textBeforeCursor = textPrefix + textInserted;

            // User delete the SEPARATOR.
            if(this.after == 0 && count == 0 && beforeString.charAt(start) == SEPARATOR)  {
                if(start > 0)  {
                    textPrefix = textPrefix.substring(0, textPrefix.length() -1);
                }
            }

            // Non-digit
            String regex = "[^\\d]";

            String textPrefixClean = textPrefix.replaceAll(regex, "");
            String textInsertedClean = textInserted.replaceAll(regex, "");
            String textSuffixClean = textSuffix.replaceAll(regex, "");
            String textBeforeCursorClean = textPrefixClean + textInsertedClean;

            // creditCardNumber
            String newTextClean = textPrefixClean + textInsertedClean + textSuffixClean;

            CreditCardType creditCardType = this.showDetectedCreditCardImage(newTextClean);

            int[] blockLengths = CreditCardType.DEFAULT_BLOCK_LENGTHS; // {4,4,4,4,4}
            int minLength = 0;
            int maxLength = CreditCardType.DEFAULT_MAX_LENGTH; // 4*5

            if(creditCardType != null)  {
                blockLengths = creditCardType.getBlockLengths();
                minLength = creditCardType.getMinLength();
                maxLength = creditCardType.getMaxLength();
            }
            Log.i(LOG_TAG, "newTextClean= " + newTextClean);


            int[] separatorIndexs = new int[blockLengths.length];
            for(int i=0; i < separatorIndexs.length; i++) {
                if(i==0)  {
                    separatorIndexs[i] = blockLengths[i];
                } else {
                    separatorIndexs[i] = blockLengths[i] + separatorIndexs[i-1];
                }
            }
            Log.i(LOG_TAG, "blockLengths= " + this.toString(blockLengths));
            Log.i(LOG_TAG, "separatorIndexs= " +  this.toString(separatorIndexs));

            int cursorPosition = start + this.after - textBeforeCursor.length() + textBeforeCursorClean.length();

            StringBuilder sb = new StringBuilder();
            int separatorCount = 0;
            int cursorPositionDelta = 0;
            int LOOP_MAX = Math.min(newTextClean.length(), maxLength);

            for(int i = 0; i < LOOP_MAX; i++) {
                sb.append(newTextClean.charAt(i));

                if(this.contains(separatorIndexs,i + 1) && i < LOOP_MAX - 1) {
                    sb.append(SEPARATOR);
                    separatorCount++;
                    if(i < cursorPosition) {
                        cursorPositionDelta++;
                    }
                }
            }
            cursorPosition= cursorPosition + cursorPositionDelta;

            String textFormatted = sb.toString();
            if(cursorPosition > textFormatted.length()) {
                cursorPosition =  textFormatted.length();
                if (areDrawablesIdentical(getResources().getDrawable(R.drawable.icon_none),
                        editTextCreditCardNumber.getCompoundDrawables()[2])) {
                    submitBtn.setEnabled(false);
                } else {
                    submitBtn.setEnabled(true);
                }
            } else {
                submitBtn.setEnabled(false);
            }

            this.editText.removeTextChangedListener(this);
            this.editText.setText(textFormatted);
            this.editText.addTextChangedListener(this);
            this.editText.setSelection(cursorPosition);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        private String toString(int[] array)  {
            StringBuilder sb= new StringBuilder();
            for(int i=0;i< array.length;i++) {
                if(i == 0) {
                    sb.append("[").append(array[i]);
                } else {
                    sb.append(", ").append(array[i]);
                }
            }
            sb.append("]");
            return sb.toString();
        }

        private boolean contains(int[] values, int value)  {
            for(int i=0;i<values.length;i++) {
                if(values[i] == value) {
                    return true;
                }
            }
            return false;
        }

        private CreditCardType showDetectedCreditCardImage(String creditCardNumber)  {
            CreditCardType type = CreditCardType.detect(creditCardNumber);

            if(type != null)  {
                Drawable icon = ResourceUtils.getDrawableByName(this.editText.getContext(), type.getImageResourceName());
                this.editText.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
            } else {
                Drawable icon = ResourceUtils.getDrawableByName(this.editText.getContext(), "icon_none");
                this.editText.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
            }
            return type;
        }
    }

    public class CvvTextWatcher implements TextWatcher {
        private EditText editText;

        public CvvTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length()>0) {
                if (areDrawablesIdentical(getResources().getDrawable(R.drawable.icon_none),
                        editTextCreditCardNumber.getCompoundDrawables()[2])) {
                    editText_cvv.setText("");
                    Toast.makeText(MainActivity.this, "invalid card number", Toast.LENGTH_SHORT).show();
                } else if (areDrawablesIdentical(getResources().getDrawable(R.drawable.icon_american_express),
                        editTextCreditCardNumber.getCompoundDrawables()[2])) {
                    editText_cvv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                } else {
                    editText_cvv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public static boolean areDrawablesIdentical(Drawable drawableA, Drawable drawableB) {
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }

    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }

    public class InputFilterExpiry implements InputFilter {

        private int min, max;

        public InputFilterExpiry(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterExpiry(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                // Remove the string out of destination that is to be replaced
                String newVal = dest.toString().substring(0, dstart) + dest.toString().substring(dend, dest.toString().length());
                // Add the new string in
                newVal = newVal.substring(0, dstart) + source.toString() + newVal.substring(dstart, newVal.length());
                int input = Integer.parseInt(newVal);
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }
        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

    // foam falidation
    private boolean validMonth() {
        String email = editText_month.getText().toString().trim();

        if (email.isEmpty()) {
//                month_layout.setError("month is missing");
            month_layout.setError(" ");
            requestFocus(editText_month);
            return false;
        } else {
            month_layout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validYear() {
        String email = editText_year.getText().toString().trim();

        if (email.isEmpty()) {
//            year_layout.setError("year is missing");
            year_layout.setError(" ");
            requestFocus(editText_year);
            return false;
        } else if (editText_month.getText().toString().isEmpty()) {
            month_layout.setError(" ");
            requestFocus(editText_month);
            return false;
        } else {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
            String currentDateandTime = sdf.format(new Date());

            String expiryDate = editText_month.getText().toString().trim() + "/" + editText_year.getText().toString().trim();
            Date date1 = sdf.parse(expiryDate);
            Date date2 = sdf.parse(currentDateandTime);

            if (date1.compareTo(date2)<0)
            {
                year_layout.setError("invalid");
                requestFocus(editText_year);
                return false;
            }

        }catch (ParseException e1){
            e1.printStackTrace();
        }

            year_layout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validCVV() {
        String email = editText_cvv.getText().toString().trim();

        if (email.isEmpty()) {
//            cvv_layout.setError("cvv is missing");
            cvv_layout.setError(" ");
            requestFocus(editText_cvv);
            return false;
        } else {

            try {
                if (areDrawablesIdentical(getResources().getDrawable(R.drawable.icon_american_express),
                        editTextCreditCardNumber.getCompoundDrawables()[2]) && editText_cvv.getText().toString().trim().length() < 4) {
                    cvv_layout.setError("must be 4 numbers");
                    requestFocus(editText_cvv);
                    return false;
                } else if (editText_cvv.getText().toString().trim().length() < 3){
                    cvv_layout.setError("must be 3 numbers");
                    requestFocus(editText_cvv);
                    return false;
                }
            } catch (Exception e1){
                e1.printStackTrace();
            }

            cvv_layout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validfn() {
        String email = fn.getText().toString().trim();

        if (email.isEmpty()) {
//            fn_layout.setError("first name is missing");
            fn_layout.setError(" ");
            requestFocus(fn);
            return false;
        } else {
            fn_layout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validln() {
        String email = ln.getText().toString().trim();

        if (email.isEmpty()) {
//            ln_layout.setError("last name is missing");
            ln_layout.setError(" ");
            requestFocus(ln);
            return false;
        } else {
            ln_layout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validCountry() {
        String email = countryEdt.getText().toString().trim();

        if (email.isEmpty()) {
//            country_layout.setError("country is missing");
            country_layout.setError(" ");
            requestFocus(countryEdt);
            return false;
        } else {
            country_layout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validZip() {
        String email = zipCode.getText().toString().trim();

        if (email.isEmpty()) {
//            zip_layout.setError("zip code is missing");
            zip_layout.setError(" ");
            requestFocus(zipCode);
            return false;
        } else if (email.length() < 5) {
            zip_layout.setError("must be 3 numbers");
            requestFocus(zipCode);
            return false;
        } else {
            zip_layout.setErrorEnabled(false);
            return true;
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}