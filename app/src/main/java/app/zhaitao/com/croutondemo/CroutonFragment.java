package app.zhaitao.com.croutondemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class CroutonFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final Style INFINITE = new Style.Builder().setBackgroundColorValue(Style.holoBlueLight).build();
    private static final Configuration CONFIGURATION = new Configuration.Builder().setDuration(Configuration.DURATION_INFINITE).build();

    private CheckBox displayOnTop;
    private Spinner styleSpinner;
    private EditText croutonTextEdit;
    private EditText croutonDurationEdit;
    private Crouton infiniteCrouton;

    public CroutonFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crouton, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_show).setOnClickListener(this);
        croutonTextEdit = (EditText) view.findViewById(R.id.edit);
        croutonDurationEdit = (EditText) view.findViewById(R.id.duration);
        styleSpinner = (Spinner) view.findViewById(R.id.spinner);
        styleSpinner.setOnItemSelectedListener(this);



        displayOnTop = (CheckBox) view.findViewById(R.id.display_on_top);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_show:
                showCrouton();
                break;
            default: {
                if (infiniteCrouton != null) {
                    Crouton.hide(infiniteCrouton);
                    infiniteCrouton = null;
                }
                break;
            }
        }
    }


    private void showCrouton() {
        Style croutonStyle = getSelectedStyleFromSpinner();
        if (croutonStyle != null) {
            showBuiltInCrouton(croutonStyle);
        } else {
            showAdvancedCrouton();
        }
    }

    private void showAdvancedCrouton() {
        switch (styleSpinner.getSelectedItemPosition()) {
            case 4:
                showCustomCrouton();
                break;
            case 5:
                showCustomViewCrouton();
                break;
        }
    }

    // 显示Crouton
    private void showCrouton(String croutonText, Style croutonStyle, Configuration configuration) {
        final boolean infinte = INFINITE == croutonStyle;
        if (infinte) {
            croutonText = "点击或旋转屏幕消失";
        }
        final Crouton crouton;
        if (displayOnTop.isChecked()) {
            crouton = Crouton.makeText(getActivity(), croutonText, croutonStyle);
        } else {
            crouton = Crouton.makeText(getActivity(), croutonText, croutonStyle, R.id.group);
        }
        if (infinte) {
            infiniteCrouton = crouton;
        }
        crouton.setOnClickListener(this).setConfiguration(infinte ? CONFIGURATION : configuration).show();
    }

    // 显示自定义的Crouton
    private void showCustomCrouton() {
        if (TextUtils.isEmpty(croutonDurationEdit.getText().toString().trim())) {
            showCrouton("持续时间为空", Style.ALERT, Configuration.DEFAULT);
            return;
        }
        int croutonDuration = Integer.parseInt(croutonDurationEdit.getText().toString().trim());
        Style croutonStyle = new Style.Builder().build();
        Configuration configuration = new Configuration.Builder().setDuration(croutonDuration).build();
        new Configuration.Builder().setInAnimation()
        showCrouton(croutonTextEdit.getText().toString(), croutonStyle, configuration);
    }

    // 显示自定义View的Crouton
    private void showCustomViewCrouton() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.crouton_custom_view, null);
        final Crouton crouton;
        if (displayOnTop.isChecked()) {
            crouton = Crouton.make(getActivity(), view);
        } else {
            crouton = Crouton.make(getActivity(), view, R.id.group);
        }
        crouton.show();
    }

    private void showBuiltInCrouton(final Style croutonStyle) {
        showCrouton(croutonTextEdit.getText().toString(),croutonStyle,Configuration.DEFAULT);
    }

    // 返回选择的Style
    private Style getSelectedStyleFromSpinner() {
        switch ((int) styleSpinner.getSelectedItemId()) {
            case 0:
                return Style.ALERT;
            case 1:
                return Style.CONFIRM;
            case 2:
                return Style.INFO;
            case 3:
                return INFINITE;
            default:
                return null;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch ((int) id) {
            case 4:
                croutonDurationEdit.setVisibility(View.VISIBLE);
                croutonTextEdit.setVisibility(View.VISIBLE);
                break;
            case 5: {
                croutonTextEdit.setVisibility(View.GONE);
                croutonDurationEdit.setVisibility(View.GONE);
            }
            default: {
                croutonDurationEdit.setVisibility(View.GONE);
                croutonTextEdit.setVisibility(View.VISIBLE);
                break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}