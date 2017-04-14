package com.parentof.mai.views.activity.childProfile;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.ChildUpdateInterfaceCallback;
import com.parentof.mai.api.apicalls.SyncProfileImageApi;
import com.parentof.mai.model.getchildrenmodel.AdditionalInfo_;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.getchildrenmodel.Child_;
import com.parentof.mai.model.getchildrenmodel.HealthDetails;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChildProfileActivity extends AppCompatActivity implements ChildUpdateInterfaceCallback {
    @Bind(R.id.ll_child_general)
    LinearLayout llGeneral;

    @Bind(R.id.ll_child_additional)
    LinearLayout llAdditional;

    @Bind(R.id.ll_child_medical)
    LinearLayout llMedical;


    @Bind(R.id.detailsTV)
    TextView detailsTextView;

    @Bind(R.id.tv_general)
    TextView tvGeneral;
    @Bind(R.id.tv_additional)
    TextView tvAdditional;
    @Bind(R.id.tv_child_medical)
    TextView tvMedical;
    @Bind(R.id.childProf_TopLLayout)
    LinearLayout lyProfile;
    @Bind(R.id.img_general)
    ImageView imgGeneral;
    @Bind(R.id.img_additional)
    ImageView imgAdditional;
    @Bind(R.id.img_child_medical)
    ImageView imgMedical;
    int flag = 0;
    @Bind(R.id.view_general)
    View viewGeneral;
    @Bind(R.id.view_additional)
    View viewAdditional;
    private SharedPreferences prefs;
    private String mobile_num;
    private String TAG = " cildAvti";
    public static final int REQUEST_CODE_GALLERY = 4;
    private static final int REQUEST_CAMERA = 1;
    @Bind(R.id.childProfileImage)
    CircularImageView childImageView;
    @Bind(R.id.imageChildEdit)
    ImageView childEditImage;
    Child childBean;

    Bitmap bitmap;
    String selectedImagePath;
    ImageView tempImageView;
    boolean keyPad = false;
    String tempFileName;
    private final int REQUEST_CODE_IMAGE = 2;
    String childGenderFlag = "Male";

    boolean imageFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
        childBean = (Child) getIntent().getParcelableExtra(Constants.SELECTED_CHILD);
        if (childBean != null && childBean.getChild() != null)
            childGenderFlag = childBean.getChild().getGender();
        toolbarInit();
        //Logger.logD(TAG, " child bean : " +childBean.getChild().getId());

        getFragmentManager().beginTransaction().replace(R.id.fragment_child_container, ChildGeneralFragment.newInstance(null, childBean)).commit();

        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        if (prefs.contains(Constants.MOBILENUM)) {
            mobile_num = prefs.getString(Constants.MOBILENUM, "");
        } else {
            ToastUtils.displayToast("Couldn't get Mobile Number. please try again!", this);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //finish();
        }
       // Logger.logD(Constants.PROJECT, "Image File--" + CommonClass.getFileImageFromFolder(childBean.getChild().getId()));

        final View activityRootView = findViewById(R.id.childMainLayout);

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > ToastUtils.dpToPx(ChildProfileActivity.this, 200)) {
                    keyPad = false;
                    showProfile(View.GONE);
                } else {
                    if (llAdditional.getTag().toString().equals("2") || llGeneral.getTag().toString().equals("2") || llMedical.getTag().toString().equals("2")) {
                        showProfile(View.GONE);
                        keyPad = true;
                    } else
                        showProfile(View.VISIBLE);
                }
            }
        });
        if (childBean == null)
            return;

        setChildImage();

    }

    boolean setChildImage() {

        boolean imgFlag = CommonClass.getImageFromDirectory(childImageView, childBean.getChild().getId());
        if (!imgFlag) {
            childImageView.setImageBitmap(CommonClass.StringToBitMap(prefs.getString("child_image", "")));

        }
        return imgFlag;
    }

    private void showProfile(int visibility) {
        lyProfile.setVisibility(visibility);
    }

    @OnClick(R.id.imageChildEdit)
    void setProfileImage() {
        selectImage(childImageView);
    }

    @OnClick(R.id.childProfileImage)
    void clickOnProfileImage() {
        selectImage(childImageView);
    }

    void toolbarInit() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (toolbar != null) {
                if (childBean != null && childBean.getChild().getFirstName() != null)
                    toolbar.setTitle(childBean.getChild().getFirstName() + " Profile");
                else
                    toolbar.setTitle(getResources().getString(R.string.child_profile));

                toolbar.setTitleTextColor(CommonClass.getColor(this, R.color.white));
                /*ImageView imageView = (ImageView) toolbar.findViewById(R.id.parentIcon);
                imageView.setImageResource(R.drawable.ic_action_overflow);
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonClass.loadSettingsActivity(ChildProfileActivity.this);
                    }
                });*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @OnClick(R.id.ll_child_general)
    void llGeneral() {
        llGeneral.setTag("2");
        llAdditional.setTag("1");
        llMedical.setTag("1");
        ChildGeneralFragment generalFragment = ChildGeneralFragment.newInstance(null, childBean);
        //generalFragment.callInterface(this);
        detailsTextView.setVisibility(View.VISIBLE);
        detailsTextView.setText(getString(R.string.child_fill_string));
        getFragmentManager().beginTransaction().replace(R.id.fragment_child_container, generalFragment).commit();
        buttonOnCLick(Constants.GENERAL);
    }


    @OnClick(R.id.ll_child_medical)
    void llMedical() {
        llGeneral.setTag("1");
        llAdditional.setTag("1");
        llMedical.setTag("2");
        if (childBean != null && childBean.getChild() != null) {
            ChildMedicalFragment childMedicalFragment = ChildMedicalFragment.newInstance(null, childBean);
            //childMedicalFragment.callInterface(this);
            detailsTextView.setVisibility(View.VISIBLE);
            detailsTextView.setText(getString(R.string.childmedstr));
            getFragmentManager().beginTransaction().replace(R.id.fragment_child_container, childMedicalFragment).commit();
            buttonOnCLick(Constants.MEDICAL);
        } else {
            ToastUtils.displayToast("Please add general information", this);
        }
    }

    @OnClick(R.id.ll_child_additional)
    void llAdditional() {
        llGeneral.setTag("1");
        llAdditional.setTag("2");
        llMedical.setTag("1");
        /* if (llAdditional.getTag().equals("2"))
            showProfile(View.GONE);*/
        if (childBean != null && childBean.getChild() != null) {
            ChildAdditionalFragment childAdditionalFragment = ChildAdditionalFragment.newInstance(null, childBean);
            //childAdditionalFragment.callInterface(this);
            detailsTextView.setVisibility(View.GONE);
            getFragmentManager().beginTransaction().replace(R.id.fragment_child_container, childAdditionalFragment).commit();
            buttonOnCLick(Constants.ADDITIONAL);
        } else {
            ToastUtils.displayToast("Please add general information", this);
        }
    }

    @Override
    public void onBackPressed() {
        llGeneral.setTag("1");
        llAdditional.setTag("1");
        llMedical.setTag("1");
        if (lyProfile.getVisibility() == View.GONE && keyPad) //
            lyProfile.setVisibility(View.VISIBLE);
        else {
            Bundle b = new Bundle();
            b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
            Intent intent = new Intent();
            intent.putExtras(b);
            setResult(199, intent);
            finish();//finishing activity
        }
       /* Intent i = new Intent();
        setResult(REQUEST_CODE_IMAGE,i);
        finish();*/
    }

    private void buttonOnCLick(String selected) {
        switch (selected) {
            case Constants.GENERAL:
                showSelectedTab(llGeneral, llMedical, llAdditional, tvGeneral, tvMedical, tvAdditional, imgGeneral, imgMedical, imgAdditional);
                imgGeneral.setImageResource(R.drawable.general_selected);
                imgAdditional.setImageResource(R.drawable.additional_normal);
                imgMedical.setImageResource(R.drawable.medical_normal);
                break;
            case Constants.MEDICAL:

                showSelectedTab(llMedical, llAdditional, llGeneral, tvMedical, tvAdditional, tvGeneral,
                        imgMedical, imgAdditional, imgGeneral);
                imgGeneral.setImageResource(R.drawable.general_normal);
                imgAdditional.setImageResource(R.drawable.additional_normal);
                imgMedical.setImageResource(R.drawable.medical_selected);

                break;
            case Constants.ADDITIONAL:

                showSelectedTab(llAdditional, llGeneral, llMedical, tvAdditional, tvGeneral, tvMedical, imgAdditional, imgGeneral, imgMedical);
                imgGeneral.setImageResource(R.drawable.general_normal);
                imgAdditional.setImageResource(R.drawable.additional_slected);
                imgMedical.setImageResource(R.drawable.medical_normal);

                break;
            default:
                break;
        }

    }

    void showSelectedTab(LinearLayout linearLayout, LinearLayout linearLayout1, LinearLayout linearLayout2,
                         TextView textView, TextView textView1, TextView textView2, ImageView imageView, ImageView imageView1, ImageView imageView2) {
        linearLayout.setBackgroundColor(CommonClass.getColor(this, R.color.colorPrimaryDark));
        textView.setTextColor(CommonClass.getColor(this, R.color.white));

        linearLayout1.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        textView1.setTextColor(CommonClass.getColor(this, R.color.colorPrimaryDark));

        linearLayout2.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        textView2.setTextColor(CommonClass.getColor(this, R.color.colorPrimaryDark));

    }


    void setGravtarImages(ImageView images, int position, boolean flag, String gender) {
        if (!flag && "male".equalsIgnoreCase(gender)) {
            images.setImageResource(Constants.CHILD_BOY_IMAGES[position]);
        } else if (!flag && "female".equalsIgnoreCase(gender)) {
            images.setImageResource(Constants.CHILD_GIRL_IMAGES[position]);
        }
    }

    @Override
    public void getUpdatedHealthData(HealthDetails healthDetails) {
        childBean.getChild().setHealthDetails(healthDetails);
        if (imageFlag)
            syncChildProfileImage();
        else
            Logger.logD(Constants.PROJECT, "No Updates in images!!!");
    }

    @Override
    public void getUpdatedAddiData(AdditionalInfo_ additionalInfo_) {
        childBean.getChild().setAdditionalInfo(additionalInfo_);
        if (imageFlag)
            syncChildProfileImage();
        else
            Logger.logD(Constants.PROJECT, "No Updates in images!!!");
    }

    @Override
    public void getUpdatedGenData(Child_ updatedChild) {
        if (childBean == null)
            childBean = new Child();
        childBean.setChild(updatedChild);
        if (childBean.getChild().getId() != null && tempFileName != null && !tempFileName.isEmpty()) {
            renameImageName(childBean.getChild().getId());
        }
        // if(childBean!=null && childBean.getChild()!=null)
        if (!childGenderFlag.equalsIgnoreCase(childBean.getChild().getGender())) {
            boolean flag = setChildImage();
            setGravtarImages(childImageView, 0, flag, childBean.getChild().getGender());
            Bitmap image = ((BitmapDrawable) childImageView.getDrawable()).getBitmap();
            Logger.logD(Constants.PROJECT, "Sel Image bit map->" + image);
            prefs.edit().putString("child_image", CommonClass.BitMapToString(image)).apply();
        }
        if (imageFlag)
            syncChildProfileImage();
        else
            Logger.logD(Constants.PROJECT, "No Updates in images!!!");

    }

    void syncChildProfileImage() {
        if (bitmap != null)
            createImagePath(bitmap);
        SyncProfileImageApi.syncProfileImg(ChildProfileActivity.this, CommonClass.getFileImageFromFolder(childBean.getChild().getId()), childBean.getChild().getId(), "child", "profile");
    }

    private void selectImage(final ImageView image) {
        final CharSequence[] items = {"Camera", "Gallery"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                tempImageView = image;
                if ("Camera".equals(items[item])) {
                    tempImageView = image;
                    File path = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".png");
                    final String fname = "img_" + System.currentTimeMillis() + ".png";
                    final File sdImageMainDirectory = new File(path, fname);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if ("Gallery".equals(items[item])) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_CODE_GALLERY);
                    }/*else{
                     //   Intent photoPickerIntent = new Intent(this, XYZ.class);
                        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

                    }*/
                }
            }
        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFlag = true;
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {

                bitmap = (Bitmap) data.getExtras().get("data");

                Log.d("Image Path", "From Camera-->" + bitmap);
                Log.d("Image Path", "From Camera tempImageView -->" + tempImageView);

                //    selectedImagePath = createImagePath(bitmap);
                try {
                    if (bitmap == null || tempImageView == null)
                        return;

                    Log.d("Image Path", "From Camera Loop -->" + bitmap);
                    tempImageView.setImageBitmap(bitmap);
                    Log.d("Image Path", "Temp From Camera-->" + bitmap);
                } catch (Exception e) {
                    Logger.logE("getActivity", "Sell your product", e);
                }
            } else if (requestCode == REQUEST_CODE_GALLERY) {
                try {
                    Uri selectedImageUri = data.getData();
                    Log.d("Image Path", "From URI--" + selectedImageUri);
                    selectedImagePath = generatePath(selectedImageUri, this);
                    Log.d("Image Path", "From filePath--" + selectedImagePath);

                    File imgFile = new File(selectedImagePath);
                    bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    childImageView.setImageBitmap(bitmap);

                    Log.d("Image Path", "From Gallery--" + bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public String createImagePath(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes);
        Log.d("Image Path", "createImagePath--" + bitmap);
        String fileName = "";
        if (childBean != null && childBean.getChild() != null) {
            fileName = "/Mai/" + childBean.getChild().getId() + "_" + System.currentTimeMillis() + ".png";
            Log.d("Image Path", "createImagePath fileName--" + fileName);
        } else {
            fileName = "/Mai/" + "_" + System.currentTimeMillis() + ".png";
            tempFileName = "_" + System.currentTimeMillis();
            Log.d("Image Path", "createImagePath ID null fileName--" + fileName);
        }
        File destination = new File(String.valueOf(CommonClass.getFileImageFromFolder(childBean.getChild().getId())));
        if (destination.exists()) {
            boolean deleted = destination.delete();
           /* Log.d("Image Path", "createImagePath destination 2--" + destination);
            Log.d("Image Path", "createImagePath deleteds--" + deleted);
            Log.d("Image Path", "createImagePath fileName--" + fileName);*/
            if (deleted)
                destination = new File(Environment.getExternalStorageDirectory(), fileName);
            else
                Log.d("Image Path", "Not delete--");
        } else {
            destination = new File(Environment.getExternalStorageDirectory(), fileName);

        }
        FileOutputStream fo;
        try {
            if (!destination.exists())
                destination.getParentFile().mkdirs();
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fo);
            fo.flush();
            fo.close();
        } catch (FileNotFoundException e) {
            Log.d("TAG", this.getClass().getSimpleName(), e);
        } catch (IOException e) {
            Log.d("TAG", this.getClass().getSimpleName(), e);
        }
        return destination.toString();
    }


    void renameImageName(String childID) {
        try {
            File destinationFolder = new File(Environment.getExternalStorageDirectory(), "/Mai");
            if (destinationFolder.exists()) {
                String[] images = destinationFolder.list();
                for (int i = 0; i < images.length; i++) {
                    Log.d("renameImageName", "Inside for lop Img --" + images[i]);
                    if (images[i].contains(tempFileName)) {
                        Log.d("renameImageName", "createImagePath contains --" + images[i]);
                        File tempDeleteFile = new File(destinationFolder.getAbsolutePath() + "/" + images[i]);
                        Log.d("renameImageName Path", "before rename contains --" + tempDeleteFile);
                        String renameStr = "/Mai/" + childID + "_" + System.currentTimeMillis() + ".png";
                        Log.d("renameImageName Path", "renameStr --" + renameStr);

                        File destination = new File(Environment.getExternalStorageDirectory(), renameStr);
                        FileOutputStream fo;
                        try {
                            if (!destination.exists())
                                destination.getParentFile().mkdirs();
                            destination.createNewFile();
                            fo = new FileOutputStream(destination);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fo);
                            fo.flush();
                            fo.close();
                        } catch (FileNotFoundException e) {
                            Log.d("TAG", this.getClass().getSimpleName(), e);
                        } catch (IOException e) {
                            Log.d("TAG", this.getClass().getSimpleName(), e);
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generatePath(Uri uri, Context context) {
        String filePath = null;
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            filePath = generateFromKitkat(uri, context);
        }
        if (filePath != null) {
            return filePath;
        }
        Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath == null ? uri.getPath() : filePath;
    }

    @TargetApi(19)
    private String generateFromKitkat(Uri uri, Context context) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String wholeID = DocumentsContract.getDocumentId(uri);
            Logger.logD(Constants.PROJECT, "WholeID--" + wholeID);
            String id = wholeID.split(":")[1];
            String[] column = {MediaStore.Images.Media.DATA};
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = context.getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath;
    }

}
