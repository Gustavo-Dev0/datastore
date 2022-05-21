package com.example.cursos

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.idnp.datastoresamplegra.NotePrefs
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // A surface container using the 'background' color from the theme
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "main"){
                composable("main"){
                    MainScreen(navController = navController)
                }
                composable("second"){
                    SecondScreen(navController = navController)
                }
            }

        }
    }
}

@Composable
fun MainScreen(navController: NavController){

    val notePrefs: NotePrefs

    var periodo by remember { mutableStateOf("") }
    var escuela by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var semestre by remember { mutableStateOf("") }
    var duracion by remember { mutableStateOf("") }

    val context: Context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dbHelper = DBHelper(context)


    val fontFamilyForm = remember { mutableStateOf( FontFamily.Default as FontFamily ) }
    val fontSizeForm = remember { mutableStateOf( 16.sp) }
    val backgroundForm = remember { mutableStateOf( Color.Green) }

    notePrefs = NotePrefs(context.dataStore)

    val a = notePrefs.backgroundColor.collectAsState(initial = "-256").value
    backgroundForm.value = Color(a.toInt())

    val a2 = notePrefs.textStyle.collectAsState(initial = "Normal").value
    var fG: FontFamily = FontFamily.Default
    when (a2){
        "Normal" -> fG = FontFamily.Default
        "Cursive" -> fG = FontFamily.Cursive
        "Serif" -> fG = FontFamily.Serif
        "SansSerif" -> fG = FontFamily.SansSerif
        "Monospace" -> fG = FontFamily.Monospace
    }
    fontFamilyForm.value = fG

    val a3 = notePrefs.textSize.collectAsState(initial = "16").value.toFloat()
    fontSizeForm.value = a3.sp

        Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundForm.value)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ){
        Text(text = "Formulario",
            fontFamily = fontFamilyForm.value,
            fontSize = fontSizeForm.value,
            modifier = Modifier.padding(15.dp)
        )
        Row(
            modifier = Modifier.padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Text(
                modifier = Modifier.width(100.dp),
                text = "Periodo académico",
                fontFamily = fontFamilyForm.value,
                fontSize = fontSizeForm.value
            )

            TextField(
                value = periodo,
                textStyle = TextStyle(fontFamily = fontFamilyForm.value, fontSize = fontSizeForm.value),

                onValueChange = {
                    periodo = it
                }
            )
        }

        Row(
            modifier = Modifier.padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Text(
                modifier = Modifier.width(100.dp),
                text = "Escuela profesional",
                fontFamily = fontFamilyForm.value,
                fontSize = fontSizeForm.value
            )

            TextField(
                value = escuela,
                textStyle = TextStyle(fontFamily = fontFamilyForm.value, fontSize = fontSizeForm.value),
                onValueChange = {
                    escuela = it
                }
            )
        }

        Row(
            modifier = Modifier.padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Text(
                modifier = Modifier.width(100.dp),
                text = "Código de asignatura",
                fontFamily = fontFamilyForm.value,
                fontSize = fontSizeForm.value
            )

            TextField(
                value = codigo,
                textStyle = TextStyle(fontFamily = fontFamilyForm.value, fontSize = fontSizeForm.value),
                onValueChange = {
                    codigo = it
                }
            )
        }
        Row(
            modifier = Modifier.padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Text(
                modifier = Modifier.width(100.dp),
                text = "Nombre de asignatura",
                fontFamily = fontFamilyForm.value,
                fontSize = fontSizeForm.value
            )

            TextField(
                value = nombre,
                textStyle = TextStyle(fontFamily = fontFamilyForm.value, fontSize = fontSizeForm.value),
                onValueChange = {
                    nombre = it
                }
            )
        }

        Row(
            modifier = Modifier.padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Text(
                modifier = Modifier.width(100.dp),
                text = "Semestre",
                fontFamily = fontFamilyForm.value,
                fontSize = fontSizeForm.value
            )

            TextField(
                value = semestre,
                textStyle = TextStyle(fontFamily = fontFamilyForm.value, fontSize = fontSizeForm.value),
                onValueChange = {
                    semestre = it
                }
            )
        }

        Row(
            modifier = Modifier.padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Text(
                modifier = Modifier.width(100.dp),
                text = "Duracion",
                fontFamily = fontFamilyForm.value,
                fontSize = fontSizeForm.value
            )

            TextField(
                value = duracion,
                textStyle = TextStyle(fontFamily = fontFamilyForm.value, fontSize = fontSizeForm.value),
                onValueChange = {
                    duracion = it
                }
            )
        }

        Button(
            onClick = {
                val db = dbHelper.writableDatabase
                val values = ContentValues().apply {
                    put("periodo", periodo)
                    put("escuela", escuela)
                    put("codigo", codigo)
                    put("nombre", nombre)
                    put("semestre", semestre)
                    put("duracion", duracion)
                }

                val newRowId = db?.insert("cursos", null, values)
                Log.e("idGenerado", newRowId.toString())
            }
        ) {
            Text(
                text = "Guardar",
                fontFamily = fontFamilyForm.value,
                fontSize = fontSizeForm.value
            )
        }

        Button(onClick = {
            navController.navigate("second")
        }) {
            Text(
                text = "Ver regisros",
                fontFamily = fontFamilyForm.value,
                fontSize = fontSizeForm.value)
        }

        Column(
            Modifier.fillMaxSize()
        ){
            var expanded by remember { mutableStateOf(false) }
            val items = listOf("Rojo", "Amarillo", "Verde", "Blanco")
            val itemsV = listOf(Color.Red, Color.Yellow, Color.Green, Color.White)
            var selectedIndex by remember { mutableStateOf(2) }

            Text(
                text = "Color de fondo",
                fontFamily = fontFamilyForm.value,
                fontSize = fontSizeForm.value)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = items[selectedIndex],
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { expanded = true })
                        .background(Color.Gray)
                        .padding(15.dp)
                )

                DropdownMenu(

                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    items.forEachIndexed { index, s ->
                        DropdownMenuItem(
                            onClick = {
                                selectedIndex = index
                                expanded = false

                                backgroundForm.value = itemsV[selectedIndex]

                                scope.launch {
                                    notePrefs.saveNoteBackgroundColor(backgroundForm.value.toArgb().toString())
                                }


                            }
                        ) {
                            Text(
                                text = s,
                                fontFamily = fontFamilyForm.value,
                                fontSize = fontSizeForm.value
                            )
                        }
                    }
                }
            }

            for (i in itemsV.indices){
                if(backgroundForm.value == itemsV[i]) {
                    selectedIndex = i
                    break
                }
            }



            var expanded2 by remember { mutableStateOf(false) }
            val items2 = listOf("Normal", "Monospace", "Serif", "SanSerif", "Cursive")
            //val itemsV2 = listOf(FontFamily.S, "Cursiva", "Normal", "Subrayado")
            var selectedIndex2 by remember { mutableStateOf(0) }

            Text(
                text = "Estilo de fuente",
                fontFamily = fontFamilyForm.value,
                fontSize = fontSizeForm.value)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = items2[selectedIndex2],
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { expanded2 = true })
                        .background(Color.Gray)
                        .padding(15.dp)
                )

                DropdownMenu(
                    expanded = expanded2,
                    onDismissRequest = { expanded2 = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    items2.forEachIndexed { index, s ->
                        DropdownMenuItem(
                            onClick = {
                                selectedIndex2 = index
                                expanded2 = false

                                var fA: FontFamily = FontFamily.Default
                                when (items2[selectedIndex2]){
                                    "Normal" -> fA = FontFamily.Default
                                    "Cursive" -> fA = FontFamily.Cursive
                                    "Serif" -> fA = FontFamily.Serif
                                    "SansSerif" -> fA = FontFamily.SansSerif
                                    "Monospace" -> fA = FontFamily.Monospace
                                }
                                fontFamilyForm.value = fA

                                scope.launch {
                                    notePrefs.saveNoteTextStyle(items2[selectedIndex2])
                                }

                            }
                        ) {
                            Text(
                                text = s,
                                fontFamily = fontFamilyForm.value,
                                fontSize = fontSizeForm.value
                            )
                        }
                    }
                }
            }
            //Log.e("xd", fontFamilyForm.value)

            var ind = 0
            when (fontFamilyForm.value){
                FontFamily.Default -> ind = 0
                FontFamily.Monospace -> ind = 1
                FontFamily.Serif -> ind = 2
                FontFamily.SansSerif  -> ind = 3
                FontFamily.Cursive -> ind = 4

                else -> {}
            }
            selectedIndex2 = ind


            var expanded3 by remember { mutableStateOf(false) }
            val items3 = listOf("12", "13", "14", "15", "16", "20")
            var selectedIndex3 by remember { mutableStateOf(0) }

            Text(
                text = "Tamaño de fuente",
                fontFamily = fontFamilyForm.value,
                fontSize = fontSizeForm.value)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = items3[selectedIndex3],
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { expanded3 = true })
                        .background(Color.Gray)
                        .padding(15.dp)
                )

                DropdownMenu(
                    expanded = expanded3,
                    onDismissRequest = { expanded3 = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    items3.forEachIndexed { index, s ->
                        DropdownMenuItem(
                            onClick = {
                                selectedIndex3 = index
                                expanded3 = false
                                fontSizeForm.value = items3[selectedIndex3].toInt().sp

                                scope.launch {
                                    notePrefs.saveNoteTextSize(fontSizeForm.value.value.toString())
                                }
                            }
                        ) {
                            Text(
                                text = s,
                                fontFamily = fontFamilyForm.value,
                                fontSize = fontSizeForm.value
                            )
                        }
                    }
                }
            }

            for (i in items3.indices){
                if(fontSizeForm.value.value == items3[i].toFloat()) {
                    selectedIndex3 = i
                    break
                }
            }


        }

    }
}

@Composable
fun SecondScreen(navController: NavController){
    val context = LocalContext.current
    val dbHelper = DBHelper(context)
    val db = dbHelper.readableDatabase


    val projection = arrayOf("id", "periodo", "escuela",
        "codigo", "nombre", "semestre", "duracion")

    val cursor = db.query(
        "cursos",   // The table to query
        projection,
        null,
        null,
        null,
        null,
        null
    )

    val itemsBD = mutableListOf<ArrayList<String>>()
    with(cursor) {
        while (moveToNext()) {
            val itemId = getLong(getColumnIndexOrThrow("id"))
            val p = getString(getColumnIndexOrThrow("periodo"))
            val e = getString(getColumnIndexOrThrow("escuela"))
            val c = getString(getColumnIndexOrThrow("codigo"))
            val n = getString(getColumnIndexOrThrow("nombre"))
            val s = getString(getColumnIndexOrThrow("semestre"))
            val d = getString(getColumnIndexOrThrow("duracion"))
            val l = ArrayList<String>()
            l.add(itemId.toString())
            l.add(p)
            l.add(e)
            l.add(c)
            l.add(n)
            l.add(s)
            l.add(d)

            itemsBD.add(l)
        }
    }
    cursor.close()


    Column(
        Modifier.fillMaxSize()
            .padding(16.dp)
    ) {

        LazyColumn {
            items(itemsBD) { data ->
                Box(Modifier.padding(8.dp).background(Color.LightGray)){
                    Text(text = data.toString())
                }

            }
        }
        Button(onClick = { navController.navigate("main") }) {
            Text(text = "Regresar")
        }
    }
}
