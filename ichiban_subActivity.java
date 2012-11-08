package net.miyazakiSeminar.Interface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ichiban_subActivity extends Activity implements OnClickListener {
	
	ImageView iv;
	Intent it;
	Matrix mx;
	byte[] s;
	Bitmap bmp;
	FileOutputStream fos, xfos;
	Button bt;
	EditText et1, et2;
		
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ichiban_sub);
		
		iv = (ImageView)findViewById(R.id.imageView1);
		it = getIntent();
		s = it.getByteArrayExtra("image");
		mx = new Matrix();
		mx.postRotate(90);		
		bmp = BitmapFactory.decodeByteArray(s, 0, s.length);
		bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mx, true);
		
		iv.setImageBitmap(bmp);
		
		et1 = (EditText)findViewById(R.id.editText1);
		et2 = (EditText)findViewById(R.id.editText2);
		bt = (Button)findViewById(R.id.button1);
		bt.setOnClickListener(this);
	}

	public void onClick(View arg0) {
		// TODO 自動生成されたメソッド・スタブ
		String root = Environment.getExternalStorageDirectory().toString() + "/ubisus/";
		File rf = new File(root);
		if(!rf.exists()){
			rf.mkdir();
		}		
		String path = root + "temp/";
		File f = new File(path);
		if(!f.exists()){
			f.mkdir();
		}
			
		String filename = "image.jpg";
		
		String filePath = path + filename;
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		
		try {
			fos = new FileOutputStream(file, true);
			fos.write(s);
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		Toast.makeText(this, path, Toast.LENGTH_LONG).show();
		
		String title = et1.getText().toString();
		String honbun = et2.getText().toString();
		
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://www.miyazaki-seminar.net/projects/ippan_android/ubisus/upload.php");
			MultipartEntity entity = new MultipartEntity();
			entity.addPart("title", new StringBody(title, Charset.forName("UTF-8")));
			entity.addPart("honbun", new StringBody(honbun, Charset.forName("UTF-8")));
			FileBody fileBody = new FileBody(file);
			entity.addPart("file", fileBody);
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			entity.consumeContent();
			client.getConnectionManager().shutdown();
			Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show();
		} catch (ClientProtocolException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
