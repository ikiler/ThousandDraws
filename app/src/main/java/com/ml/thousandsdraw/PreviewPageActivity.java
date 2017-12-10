package com.ml.thousandsdraw;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ml.thousandsdraw.dialog.dialogs;
import com.ml.thousandsdraw.model.QQshareListener;
import com.ml.thousandsdraw.model.list_config;
import com.ml.thousandsdraw.sql.ListSqlHelp;
import com.ml.thousandsdraw.util.Constants;
import com.ml.thousandsdraw.util.PicCrop;
import com.ml.thousandsdraw.util.config;
import com.ml.thousandsdraw.util.mfileUtil;
import com.ml.thousandsdraw.util.shareUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;

import java.io.File;

public class PreviewPageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView previewImage;
    private Intent intent;
    private Toolbar toolbar;
    list_config list_config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_page);
        toolbar = findViewById(R.id.preview_toolbar);
        setSupportActionBar(toolbar);
        intent = getIntent();
        list_config = (com.ml.thousandsdraw.model.list_config) intent.getSerializableExtra("list_config");
        initView();


    }

    public  void initView(){
        toolbar.setTitle("haha000");
        previewImage = findViewById(R.id.preview_image);
        findViewById(R.id.preview_crop).setOnClickListener(this);
        findViewById(R.id.preview_delete).setOnClickListener(this);
        findViewById(R.id.preview_editor).setOnClickListener(this);
        findViewById(R.id.preview_save).setOnClickListener(this);
        findViewById(R.id.preview_share).setOnClickListener(this);

        previewImage.setImageBitmap(BitmapFactory.decodeFile(
                list_config.getImage_path()+".png"));
    }
    private void CreateSeleteDialog(){
        LayoutInflater inflater = LayoutInflater.from(PreviewPageActivity.this);
        View v = inflater.inflate(R.layout.croup_child,null);
        v.findViewById(R.id.croup_head).setOnClickListener(this);
        v.findViewById(R.id.croup_free).setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(PreviewPageActivity.this);
        builder.setView(v);
        builder.show();
    }
    private void startCroup(int type){
        Uri uri = Uri.fromFile(new File(list_config.getImage_path()+".png"));
        PicCrop.cropFromUri(PreviewPageActivity.this,
                uri,
                config.SAVE_PATH_TOADCARD,
                type);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.preview_crop:
                CreateSeleteDialog();
                break;
            case R.id.preview_delete:
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(PreviewPageActivity.this);
                builder.setTitle(R.string.sure_delete);
                builder.setMessage(R.string.sure_delete);
                builder.setPositiveButton(R.string.sure,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListSqlHelp help = new ListSqlHelp(PreviewPageActivity.this);
                        help.delete(list_config);
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel,null);
                builder.show();
                break;
            case R.id.preview_editor:
                intent.setClass(PreviewPageActivity.this,Worker.class);
                startActivity(intent);
                break;
            case R.id.preview_save:
                mfileUtil.copyFile(list_config.getImage_path()+".png", config.SAVE_PATH_TOADCARD+System.currentTimeMillis()+".png");
                Toast.makeText(PreviewPageActivity.this,"successful",Toast.LENGTH_SHORT).show();
                break;
            case R.id.preview_share:
                mfileUtil.copyFile(list_config.getImage_path()+".png", config.SAVE_PATH_TO_LOCAL_CACHE);
                shareUtil.createShareWindow(PreviewPageActivity.this,
                        PreviewPageActivity.this.findViewById(R.id.preview_rootview),
                        config.SAVE_PATH_TO_LOCAL_CACHE);
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("image/*");
//                Uri ui = Uri.fromFile(new File(list_config.getImage_path()+".png"));
//                i.putExtra(Intent.EXTRA_STREAM,ui);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.putExtra(Intent.EXTRA_SUBJECT, "千层画分享");
//                i.putExtra(Intent.EXTRA_TEXT, "Hi！我在千层画设计了一幅盖世之作！快来为我点赞吧！");
//                startActivity(Intent.createChooser(i, getTitle()));
//                Tencent tencent = Tencent.createInstance(Constants.APPID, PreviewPageActivity.this);
//                Bundle params = new Bundle();
//                params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,list_config.getImage_path()+".png");
//                params.putString(QQShare.SHARE_TO_QQ_APP_NAME,"返回千层画");
//                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
//                tencent.shareToQQ(PreviewPageActivity.this, params,null);
                break;
            case R.id.croup_head:
                startCroup(PicCrop.TYPE_AVATAR);
                break;
            case R.id.croup_free:
                startCroup(PicCrop.TYPE_NORMAL);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PicCrop.onActivityResult(requestCode,resultCode,data,this,new PicCrop.CropHandler(){
            @Override
            public void handleCropResult(Uri uri, int tag) {

            }

            @Override
            public void handleCropError(Intent data) {

            }
        });
    }
}
