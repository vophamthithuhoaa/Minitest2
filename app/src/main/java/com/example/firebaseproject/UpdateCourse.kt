package com.example.firebaseproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.firebaseproject.ui.theme.FirebaseProjectTheme
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore


class UpdateCourse : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
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
                                        title = { /*TODO*/
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

                                firebaseUI(
                                    LocalContext.current,
                                    intent.getStringExtra("pdID"),
                                    intent.getStringExtra("pdName"),
                                    intent.getStringExtra("pdType"),
                                    intent.getStringExtra("pdPrice"),
                                    intent.getStringExtra("pdImage")
                                )
                            }
                        }
            }
        }
    }


    // cap nhat
    @Composable
    fun firebaseUI(
        context: Context,
        id: String?,
        name: String?,
        type: String?,
        price: String?,
        image: String?
    ) {

        // on below line creating variable for course name,
        // course duration and course description.
        val pdID = remember {
            mutableStateOf(id)
        }
        val pdName = remember {
            mutableStateOf(name)
        }

        val pdType = remember {
            mutableStateOf(type)
        }

        val pdPrice = remember {
            mutableStateOf(price)
        }
        val pdImage = remember {
            mutableStateOf(image)
        }

        // on below line creating a column
        Column(
            // adding modifier for our column
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.White),
            // on below line adding vertical and
            // horizontal alignment for column.
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Sửa Thông Tin Sản Phẩm", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            TextField(
                value = pdName.value.toString(),
                onValueChange = { pdName.value = it },
                placeholder = { Text(text = "Tên sản phẩm") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black, fontSize =
                15.sp),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = pdType.value.toString(),
                onValueChange = { pdType.value = it },
                placeholder = { Text(text = "Loại sản phẩm")
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black, fontSize =
                15.sp),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = pdPrice.value.toString(),
                onValueChange = { pdPrice.value = it },
                placeholder = { Text(text = "Giá sản phẩm") },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                    textStyle = TextStyle(color = Color.Black, fontSize =
                    15.sp),
                    singleLine = true,
                )

                    Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = pdImage.value.toString(),
                onValueChange = { pdImage.value = it },
                placeholder = { Text(text = "Hình ảnh sản phẩm") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black, fontSize =
                15.sp),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            // on below line we are validating user input
                            if (TextUtils.isEmpty(pdName.value.toString())) {
                                Toast.makeText(context, "Hãy nhập tên sản phẩm",
                                    Toast.LENGTH_SHORT)
                                    .show()
                            } else if
                                           (TextUtils.isEmpty(pdType.value.toString())) {
                                Toast.makeText(
                                    context,
                                    "Hãy nhập loại sản phẩm",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else if
                                           (TextUtils.isEmpty(pdPrice.value.toString())) {
                                Toast.makeText(
                                    context,
                                    "Hãy nhập giá sản phẩm",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else if
                                    (TextUtils.isEmpty(pdImage.value.toString())) {
                                Toast.makeText(
                                    context,
                                    "Hãy thêm hình ảnh sản phẩm",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }else {
                                // on below line adding data to
                                // firebase firestore database.
                                updateDataToFirebase(
                                    pdID.value,
                                    pdName.value,
                                    pdType.value,
                                    pdPrice.value,
                                    pdImage.value,
                                    context
                                )
                            }
                        },
                        // on below line we are
                        // adding modifier to our button.
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // on below line we are adding text for our button
                        Text(text = "CẬP NHẬT THÔNG TIN", modifier =
                        Modifier.padding(8.dp))
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
        }

        private fun updateDataToFirebase(
            id: String?,
            name: String?,
            type: String?,
            price: String?,
            image: String?,
            context: Context
        ) {
            // inside this method we are passing our updated values
            // inside our object class and later on we
            // will pass our whole object tofirebase Firestore.
            val updatedProduct = Product(id, name, type, price, image)
            // getting our instance from Firebase Firestore.
            val db = FirebaseFirestore.getInstance();

            db.collection("Products").document(id.toString()).set(updatedProduct)
                .addOnSuccessListener {
                    // on below line displaying toast message and opening
                    // new activity to view courses.
                    Toast.makeText(context, "Products Updated successfully..",
                        Toast.LENGTH_SHORT).show()
                    context.startActivity(Intent(context,
                        CourseDetailsActivity::class.java))
                    //  finish()

                }.addOnFailureListener {
                    Toast.makeText(context, "Fail to update product : " +
                            it.message, Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }
