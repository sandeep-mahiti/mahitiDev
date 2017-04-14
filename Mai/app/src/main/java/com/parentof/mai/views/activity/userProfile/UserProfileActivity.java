package com.parentof.mai.views.activity.userProfile;

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
import com.parentof.mai.activityinterfaces.UpdateUserResponseCallBack;
import com.parentof.mai.model.getuserdetail.GetUserDetailRespModel;

import com.parentof.mai.model.updateuser.UpdateUserGeneralBean;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileActivity extends AppCompatActivity implements UpdateUserResponseCallBack {
    private static final String TAG = "usrprfl ";
    @Bind(R.id.ll_parent_general)
    LinearLayout llGeneral;

    @Bind(R.id.ll_parent_additional)
    LinearLayout llAdditional;

    @Bind(R.id.tv_general)
    TextView tvGeneral;

    @Bind(R.id.tv_additional)
    TextView tvAdditional;

    @Bind(R.id.img_general)
    ImageView imgGeneral;
    @Bind(R.id.img_additional)
    ImageView imgAdditional;

    @Bind(R.id.profile_img_user)
    CircularImageView userImageView;
    @Bind(R.id.userEditImage)
    ImageView userEditImage;
    @Bind(R.id.lyProfile)
    LinearLayout lyProfile;
    public static final int REQUEST_CODE_GALLERY = 4;
    private static final int REQUEST_CAMERA = 1;
    Bitmap bitmap;
    String selectedImagePath;
    ImageView tempImageView;
    List<ImageView> listImageViews = new ArrayList<>();
    boolean keyPad = false;
    GetUserDetailRespModel getUserDetailRespModel = new GetUserDetailRespModel();
    String tempFileName;
    String userId;
    SharedPreferences prefs;
    String relationFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(R.drawable.profile_bg_parent);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        prefs = this.getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        toolbarInit();
        try {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserGeneralFragment()).commit();
        } catch (Exception e) {
            Logger.logE(TAG, " oncrt : ", e);
        }
        final View activityRootView = findViewById(R.id.mainLayout);

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(r);
                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > ToastUtils.dpToPx(UserProfileActivity.this, 200)) {
                    keyPad = false;
                    showProfile(View.GONE);
                } else {
                    if (llAdditional.getTag().toString().equals("2") || llGeneral.getTag().toString().equals("2")) {
                        showProfile(View.GONE);
                        keyPad = true;
                    } else
                        showProfile(View.VISIBLE);
                }
            }
        });
        userId = String.valueOf(prefs.getInt(Constants._ID, 0));
        Logger.logD(Constants.PROJECT, "UserId-->" + userId);
        relationFlag = prefs.getString(PreferencesConstants.RELATION, "");
        //For getting image and setting
        boolean userImgFlag = CommonClass.getUserImageFromDirectory(userImageView, userId);
        if (!userImgFlag) {
            CommonClass.setGravatarUserImage(prefs.getString(PreferencesConstants.RELATION, ""), userImageView);
        }

    }

    @Override
    public void onBackPressed() {
        llGeneral.setTag("1");
        llAdditional.setTag("1");
        if (lyProfile.getVisibility() == View.GONE && keyPad) //&
            lyProfile.setVisibility(View.VISIBLE);
        else {
            finish();
        }
    }

    private void showProfile(int visibility) {
        lyProfile.setVisibility(visibility);
    }


    void toolbarInit() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (toolbar != null) {
                toolbar.setTitle(getResources().getString(R.string.parent_my_profile));
                toolbar.setTitleTextColor(CommonClass.getColor(this, R.color.white));
                ImageView imageView = (ImageView) toolbar.findViewById(R.id.parentIcon);
                /*imageView.setImageResource(R.drawable.ic_action_overflow);
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonClass.loadSettingsActivity(UserProfileActivity.this);
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

    @OnClick(R.id.ll_parent_general)
    void llGeneral() {
        llAdditional.setTag("1");
        llGeneral.setTag("2");
        llAdditional.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        tvAdditional.setTextColor(CommonClass.getColor(this, R.color.colorPrimaryDark));
        imgAdditional.setImageResource(R.drawable.additional_normal);

        llGeneral.setBackgroundColor(CommonClass.getColor(this, R.color.colorPrimaryDark));
        tvGeneral.setTextColor(CommonClass.getColor(this, R.color.white));
        imgGeneral.setImageResource(R.drawable.general_selected);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserGeneralFragment()).commit();


    }

    @OnClick(R.id.ll_parent_additional)
    void llAdditional() {
        llAdditional.setTag("2");
        llGeneral.setTag("1");
      /*  if (llAdditional.getTag().equals("2"))
            showProfile(View.GONE);*/
        llGeneral.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        tvGeneral.setTextColor(CommonClass.getColor(this, R.color.colorPrimaryDark));
        imgGeneral.setImageResource(R.drawable.general_normal);
        ;

        llAdditional.setBackgroundColor(CommonClass.getColor(this, R.color.colorPrimaryDark));
        tvAdditional.setTextColor(CommonClass.getColor(this, R.color.white));
        imgAdditional.setImageResource(R.drawable.additional_slected);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserAdditinalFragment()).commit();
    }

    @OnClick(R.id.userEditImage)
    void setUserProfileImage() {
        selectImage(userImageView);
    }

    @OnClick(R.id.profile_img_user)
    void onClickUserProfileImage() {
        selectImage(userImageView);
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
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {

                bitmap = (Bitmap) data.getExtras().get("data");

                Log.d("Image Path", "From Camera-->" + bitmap);
                Log.d("Image Path", "From Camera tempImageView -->" + tempImageView);

                selectedImagePath = createImagePath(bitmap);
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
                    userImageView.setImageBitmap(bitmap);
                    if (bitmap != null)
                        createImagePath(bitmap);
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
        if (userId != null) {
            fileName = "/Mai/" + userId /*+ "_" + System.currentTimeMillis()*/ + ".png";
            Log.d("Image Path", "createImagePath fileName--" + fileName);
        } else {
            fileName = "/Mai/" + "_" + System.currentTimeMillis() + ".png";
            tempFileName = "_" + System.currentTimeMillis();
            Log.d("Image Path", "createImagePath ID null fileName--" + fileName);
        }
        File destination = new File(Environment.getExternalStorageDirectory(), fileName);

        if (destination.exists()) {
            boolean deleted = destination.delete();
        }
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

    @Override
    public void responseBackToActivity(GetUserDetailRespModel userDetailRespModel) {
        this.getUserDetailRespModel = userDetailRespModel;
    }

    @Override
    public void responseUserGeneralTOActivity(UpdateUserGeneralBean updateUserGeneralBean) {
        Log.d("relationFlag Path", "relationFlag --" + relationFlag + "<-->relationFlag 2-->" + prefs.getString(PreferencesConstants.RELATION, ""));
        boolean userImgFlag = CommonClass.getUserImageFromDirectory(userImageView, userId);
        if (!userImgFlag &&!relationFlag.equals(prefs.getString(PreferencesConstants.RELATION, ""))) {
            Logger.logD(Constants.PROJECT,"UserImage-->"+prefs.getString(PreferencesConstants.RELATION, ""));
            CommonClass.setGravatarUserImage(prefs.getString(PreferencesConstants.RELATION, ""), userImageView);
        }
    }
}
