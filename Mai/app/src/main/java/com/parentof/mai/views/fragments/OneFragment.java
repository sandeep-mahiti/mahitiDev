package com.parentof.mai.views.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.parentof.mai.R;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.Typewriter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Typewriter hello;
    Typewriter personal;
    View oneView;

    public OneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OneFragment newInstance(String param1, String param2) {
        OneFragment fragment = new OneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            oneView = inflater.inflate(R.layout.fragment_one, container, false);
            startAnimations(oneView);
            hello = (Typewriter) oneView.findViewById(R.id.hello);
            personal = (Typewriter) oneView.findViewById(R.id.personal);
            hello.addTextChangedListener(mTextEditorWatcher);
            hello.setVisibility(View.INVISIBLE);
            personal.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return oneView;
    }


    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                //int firstStringLength = hello.getText().toString().length();
                // TODO Auto-generated method stub
                if (getResources().getString(R.string.hello).equals(hello.getText().toString())) {
                    personal.setVisibility(View.VISIBLE);
                    personal.setBackground(getResources().getDrawable(R.drawable.corners_button));
                    secondAnimation();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
    };

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void startAnimations(View view) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.center_top);
        anim.setInterpolator((new AccelerateDecelerateInterpolator()));
        anim.setFillAfter(true);
        ImageView iv = (ImageView) view.findViewById(R.id.logo);
        iv.setAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String boldText = "Mai !";
                String helloTxt = "Hello, I am ";
                String sourceString = helloTxt+ "<b>" + boldText + "</b> " ;
               /* SpannableString str = new SpannableString(helloTxt+ boldText);
                str.setSpan(new StyleSpan(Typeface.BOLD), 10, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
                hello.setVisibility(View.VISIBLE);
                hello.setCharacterDelay(100);
                hello.animateText((Html.fromHtml(sourceString)));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    void secondAnimation() {
        try {
            personal.setCharacterDelay(100);
            personal.animateText(getResources().getString(R.string.your_personal_parent));
        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, "Exception_", e);
        }
    }


}
