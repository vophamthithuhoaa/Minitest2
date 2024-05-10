@file:Suppress("UNREACHABLE_CODE")

package com.example.firebaseproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.TopAppBar
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.R
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.firebaseproject.ui.theme.FirebaseProjectTheme
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore



class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseProjectTheme {
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
                                }) {innerPadding ->
                                Text(
                                    modifier = Modifier.padding(innerPadding),
                                    text = "Them du lieu."
                                )
                                FirebaseUI(LocalContext.current)
                            }
                        }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun FirebaseUI(context: Context) {

    // on below line creating variable for course name,
    // course duration and course description.
    val pdID = remember {
        mutableStateOf("")
    }
    val pdName = remember {
        mutableStateOf("")
    }

    val pdType = remember {
        mutableStateOf("")
    }

    val pdPrice = remember {
        mutableStateOf("")
    }
    val pdImage = remember {
        mutableStateOf("")
    }

    Column(
        // adding modifier for our column
        modifier = Modifier
            .padding(top = 60.dp)
            .fillMaxWidth()
            .background(Color.White),
        // on below line adding vertical and
        // horizontal alignment for column.
        verticalArrangement = Arrangement.Center, horizontalAlignment =
        Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(com.example.firebaseproject.R.drawable.nen),
            modifier = Modifier.size(150.dp),
            contentDescription = "Delete Icon")
        Text(text = "Dữ Liệu Sản Phẩm", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(2.dp))
        TextField(
            value = pdName.value,
            onValueChange = { pdName.value = it },
            placeholder = { Text(text = "Tên sản phẩm") },
            modifier = Modifier
                .padding(13.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(-1.dp))

        TextField(
            value = pdType.value,
            onValueChange = { pdType.value = it },
            placeholder = { Text(text = "Loại sản phẩm") },
            modifier = Modifier
                .padding(13.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(-1.dp))

        TextField(
            value = pdPrice.value,
            onValueChange = { pdPrice.value = it },
            placeholder = { Text(text = "Giá sản phẩm") },
            modifier = Modifier
                .padding(13.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(-1.dp))
        TextField(
            value = pdImage.value,
            onValueChange = { pdImage.value = it },
            placeholder = { Text(text = "Hình ảnh sản phẩm") },
            modifier = Modifier
                .padding(13.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(-1.dp))
        val pdList = mutableStateListOf<Product?>()
        Button(
            onClick = {
                    addDataToFirebase(
                        pdID.value,
                        pdName.value,
                        pdType.value,
                        pdPrice.value,
                        pdImage.value,
                        context
                    )
                context.startActivity(Intent(context, CourseDetailsActivity::class.java))
            },
            // on below line we are
            // adding modifier to our button.
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // on below line we are adding text for our button
            Text(text = "THÊM SẢN PHẨM", modifier = Modifier.padding(8.dp))
        }
        Button(
            onClick = {
                context.startActivity(Intent(context, CourseDetailsActivity::class.java))
            },
            // on below line we are
            // adding modifier to our button.
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // on below line we are adding text for our button
            Text(text = "DANH SÁCH SẢN PHẨM", modifier = Modifier.padding(8.dp))
        }
        Spacer(modifier = Modifier.height(-1.dp))
    }
}

fun addDataToFirebase(
    pdID:String, pdName: String, pdType: String,
    pdPrice: String, pdImage: String, context: Context
) {
    // on below line creating an instance of firebase firestore.
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbCourses: CollectionReference = db.collection("Products")
    val products = Product(pdID, pdName, pdType, pdPrice, pdImage)
    dbCourses.add(products)
        .addOnSuccessListener {
        Toast.makeText(
            context, "Your Course has been added to Firebase Firestore",
            Toast.LENGTH_SHORT
        ).show()
    }.addOnFailureListener { e ->
        Toast.makeText(context, "Fail to add course \n$e",
            Toast.LENGTH_SHORT).show()
    }
}







