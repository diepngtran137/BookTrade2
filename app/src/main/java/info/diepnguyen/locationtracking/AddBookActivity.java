package info.diepnguyen.locationtracking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class AddBookActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST =  71;
    private static final int CAPTURE_IMAGE_REQUEST = 72;
    private Uri filePath;

    //Firebase
    DatabaseReference mDataRef;
    FirebaseStorage mStorage;
    StorageReference mStorageRef;

    EditText bookName, authorName;
    Spinner genreSpinner;
    ArrayAdapter<CharSequence> genreAdapter;
    ImageView photoView;
    ImageButton chooseBtn, captureBtn;

    String photoURL;
    String genreTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Firebase init
        mDataRef = FirebaseDatabase.getInstance().getReference().child("book");
        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReference();

        initComponent();

        //Open Gallery to choose photo
        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
                chooseImg();
            }
        });

        //Open Camera to take picture
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImg();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
            }
        });

        Log.i("Add Book","on Create is running");
    }

    private void addBook() {
        //
        if(filePath != null){
            StorageReference ref = mStorageRef.child("images/").child(filePath.getLastPathSegment());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    photoURL = taskSnapshot.getDownloadUrl().toString();
                    Book book = new Book(FirebaseAuth.getInstance().getCurrentUser().getUid(),bookName.getText().toString()
                            ,authorName.getText().toString()
                            ,genreTxt
                            ,photoURL);
                    mDataRef.push().setValue(book);
                    Intent intent = new Intent(AddBookActivity.this,BookList.class);
                    startActivity(intent);

                }
            });
        }
        else {
            Calendar calendar = Calendar.getInstance();
            StorageReference ref = mStorageRef.child("images/").child("img" + calendar.getTimeInMillis());
            photoView.setDrawingCacheEnabled(true);
            photoView.buildDrawingCache();
            Bitmap bitmap = photoView.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = ref.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    Book book = new Book(FirebaseAuth.getInstance().getCurrentUser().getUid(),bookName.getText().toString()
                            ,authorName.getText().toString()
                            ,genreTxt,downloadUrl);
                    mDataRef.push().setValue(book);
                    Intent intent = new Intent(AddBookActivity.this,BookList.class);
                    startActivity(intent);
                }
            });

        }
    }

    private void captureImg() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAPTURE_IMAGE_REQUEST);

    }

    private void chooseImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose Image"),PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null)
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                photoView.setImageBitmap(bitmap);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if(requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            photoView.setImageBitmap(bitmap);

        }
    }

    private void initComponent() {
        bookName =(EditText) findViewById(R.id.bookName);
        authorName = (EditText) findViewById(R.id.bookAuthor);
        photoView = (ImageView)findViewById(R.id.photoView);
        chooseBtn = (ImageButton) findViewById(R.id.choose_img_btn);
        captureBtn = (ImageButton)findViewById(R.id.capture_img_btn);

        genreSpinner = (Spinner)findViewById(R.id.genre_spinner);
        genreAdapter = ArrayAdapter.createFromResource(this,R.array.genre_array,android.R.layout.simple_spinner_item);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genreTxt = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(),+"selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                returnMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnMenu() {
        Intent intent = new Intent(AddBookActivity.this,MenuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Add Book","on Resume is running");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Add Book","on Pause is running");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Add Book","on Start is running");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Add Book","on Stop is running");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Add Book","on Restart is running");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Add Book","on Destroy is running");
    }

}
