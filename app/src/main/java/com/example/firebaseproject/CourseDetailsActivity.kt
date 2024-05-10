package com.example.firebaseproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.firebaseproject.ui.theme.Black
import com.example.firebaseproject.ui.theme.FirebaseProjectTheme
import com.google.firebase.firestore.FirebaseFirestore

class CourseDetailsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseProjectTheme {
                // A surface container using the 'background' color from
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "GFG",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                })
                        }) { innerPadding ->
                        Text(
                            modifier = Modifier.padding(innerPadding),
                            text = "Cap nhat du lieu."
                        )

                        // on below line creating variable for list of
                        var pdList = mutableStateListOf<Product?>()
                        var db: FirebaseFirestore =
                            FirebaseFirestore.getInstance()

                        // on below line getting data from our database
                        db.collection("Products").get()
                            .addOnSuccessListener { queryDocumentSnapshots ->
                                if (!queryDocumentSnapshots.isEmpty) {
                                    val list =
                                        queryDocumentSnapshots.documents
                                    for (d in list) {
                                        // to our object class.
                                        val c: Product? = d.toObject(Product::class.java)
                                        c?.pdID = d.id
                                        Log.e("TAG", "Product id is : " + c!!.pdID)
                                        pdList.add(c)
                                    }
                                } else {
                                    // if the snapshot is empty we are
                                    // displaying a toast message.
                                    Toast.makeText(
                                        this@CourseDetailsActivity,
                                        "No data found in Database",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    this@CourseDetailsActivity,
                                    "Fail to get the data.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        // on below line we are calling method to display
                        firebaseUI(LocalContext.current, pdList)
                        // ket thuc
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun firebaseUI(context: Context, pdList: SnapshotStateList<Product?>)
    {
        Column(
            // adding modifier for our column
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxWidth()
                .background(Color.White),
            // on below line adding vertical and
            // horizontal alignment for column.
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Dữ Liệu Sản Phẩm", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            LazyColumn {
                itemsIndexed(pdList) { index, item ->
                    // on below line we are creating
                    // a card for our list view item.
                    Card(
                        onClick = {
                            val i = Intent(
                                context,
                                UpdateCourse::class.java
                            )
                            i.putExtra("pdID", item?.pdID)
                            i.putExtra("pdName", item?.pdName)
                            i.putExtra("pdType", item?.pdType)
                            i.putExtra("pdPrice", item?.pdPrice)
                            i.putExtra("pdImage", item?.pdImage)
                            context.startActivity(i)

                        },
                        // on below line we are adding
                        // padding from our all sides.
                        modifier = Modifier.padding(8.dp),
                    ) {
                        // on below line we are creating
                        // a row for our list view item
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            pdList[index]?.pdImage?.let {
                                Image(
                                    modifier = Modifier
                                        .height(105.dp)
                                        .width(110.dp)
                                        .clip(RoundedCornerShape(size = 15.dp)),
                                    painter = rememberAsyncImagePainter(it),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "hinh anh"
                                )
                            }
                            Column(
                                // for our row we are adding modifier
                                // to set padding from all sides.
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.width(20.dp))
                                // on below line inside row we are adding
                                // on below line we are displaying course name.
                                pdList[index]?.pdName?.let {
                                    Row {
                                        Text(
                                            text = "Tên sp:",
                                            modifier = Modifier.padding(4.dp),
                                            color = Black,
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                fontSize = 18.sp, fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Text(
                                            text = it,
                                            modifier = Modifier.padding(4.dp),
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                fontSize = 18.sp, fontWeight = FontWeight.Light
                                            )
                                        )
                                    }
                                }
                                // adding spacer on below line.
                                Spacer(modifier = Modifier.height(5.dp))

                                // on below line displaying text for course
                                pdList[index]?.pdType?.let {
                                    Row {
                                        Text(
                                            text = "Loại sp:",
                                            modifier = Modifier.padding(4.dp),
                                            color = Black,
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                fontSize = 18.sp, fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Text(
                                            text = it,
                                            modifier = Modifier.padding(4.dp),
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                fontSize = 18.sp, fontWeight = FontWeight.Light
                                            )
                                        )
                                    }
                                }
                                // adding spacer on below line.
                                Spacer(modifier = Modifier.width(5.dp))

                                pdList[index]?.pdPrice?.let {
                                    Row {
                                        Text(
                                            text = "Giá sp:",
                                            modifier = Modifier.padding(4.dp),
                                            color = Black,
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                fontSize = 18.sp, fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Text(
                                            text = it,
                                            modifier = Modifier.padding(4.dp),
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                fontSize = 18.sp, fontWeight = FontWeight.Light
                                            )
                                        )
                                    }
                                }
                                // adding spacer on below line.
                                Spacer(modifier = Modifier.height(5.dp))

                            }
                            Column() {
                                Button(
                                    onClick = {
                                        // on below line opening course details activity.
                                        context.startActivity(Intent(context, UpdateCourse::class.java))
                                    },
                                    // on below line we are
                                    // adding modifier to our button.
                                    modifier = Modifier
                                        .width(70.dp)
                                        .height(50.dp)
                                ) {
                                    // on below line we are adding text for our button
                                    Icon(
                                        painter = painterResource(R.drawable.edit_24),
                                        contentDescription = "Edit Icon"
                                    )
                                }
                                Spacer(modifier = Modifier.height(3.dp))
                                Button(
                                    onClick = {
                                        // on below line opening course details activity.
                                        deleteList(pdList[index]?.pdID, context)
                                    },
                                    // on below line we are
                                    // adding modifier to our button.
                                    modifier = Modifier
                                        .width(70.dp)
                                        .height(50.dp)
                                ) {
                                    // on below line we are adding text for our button
                                    Icon(
                                        painter = painterResource(R.drawable.delete_24),
                                        modifier = Modifier.size(150.dp),
                                        contentDescription = "Delete Icon"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun deleteList(pdID: String?, context: Context) {

        // getting our instance from Firebase Firestore.
        val db = FirebaseFirestore.getInstance();

        // below line is for getting the collection
        // where we are storing our courses.
        db.collection("Products").document(pdID.toString()).delete()
            .addOnSuccessListener {
                // displaying toast message when our course is deleted.
                Toast.makeText(context, "Product Deleted successfully..", Toast.LENGTH_SHORT)
                    .show()
                context.startActivity(Intent(context, CourseDetailsActivity::class.java))
            }.addOnFailureListener {
                // on below line displaying toast message when
                // we are not able to delete the course
                Toast.makeText(context, "Fail to delete course..", Toast.LENGTH_SHORT).show()
            }
    }
}
