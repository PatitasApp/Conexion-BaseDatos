package com.example.basedatospatitas

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.contentValuesOf

class MainActivity : AppCompatActivity() {
    var txtid:EditText?=null
    var txtNombre:EditText?=null
    var txtTipo:EditText?=null
    var txtFechaNacimiento:EditText?=null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtid= findViewById(R.id.txtid)
        txtNombre= findViewById(R.id.txtNombre)
        txtTipo= findViewById(R.id.txtTipo)
        txtFechaNacimiento= findViewById(R.id.txtFechaNacimiento)
    }
    fun insertar(view: View) {
        var connet=SQLite(this,"patitasDB",null,1)
        var baseDatos=connet.writableDatabase


        var id=txtid?.text.toString()
        var Nombre=txtNombre?.text.toString()
        var Tipo=txtTipo?.text.toString()
        var FechaNacimiento= txtFechaNacimiento?.text.toString()

        if(id.isEmpty()==false && Nombre.isEmpty()==false && Tipo.isEmpty()==false && FechaNacimiento.isEmpty()==false){
            var registro= ContentValues()
            registro.put("id",id)
            registro.put("Nombre",Nombre)
            registro.put("Tipo",Tipo)
            registro.put("FechaNacimiento", FechaNacimiento)

            baseDatos.insert("mascotas",null,registro)
            txtid?.setText("")
            txtNombre?.setText("")
            txtTipo?.setText("")
            txtFechaNacimiento?.setText("")

            Toast.makeText(this,"Registro exitoso", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Complete todos los campos",Toast.LENGTH_LONG).show()
        }
        baseDatos.close()
    }
    fun buscar(view: View){
        val connet=SQLite(this,"patitasDB",null,1)
        val baseDatos=connet.writableDatabase
        val id=txtid?.text.toString()
        if(id.isEmpty()==false){
            val fila=baseDatos.rawQuery("select Nombre, Tipo, FechaNacimiento from mascotas where id='$id'", null)
            if(fila.moveToFirst()==true){
                txtNombre?.setText(fila.getString(0))
                txtTipo?.setText(fila.getString(1))
                txtFechaNacimiento?.setText(fila.getString(2))
                baseDatos.close()
            }else{
                txtNombre?.setText("")
                txtTipo?.setText("")
                txtFechaNacimiento?.setText("")
                Toast.makeText(this,"No se encontraron registros", Toast.LENGTH_LONG).show()

            }
        }
        baseDatos.close()
    }
    fun eliminar(view: View) {
        val connet = SQLite(this, "patitasDB", null, 1)
        val baseDatos = connet.writableDatabase
        val id = txtid?.text.toString()
        if (id.isEmpty() == false) {

            val cantidad = baseDatos.delete("mascotas", "id='" + id + "'", null)
            if (cantidad > 0) {
                Toast.makeText(this, "La mascota fue eliminada", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No se encontro mascota", Toast.LENGTH_LONG).show()
            }
            txtid?.setText("")
            txtNombre?.setText("")
            txtTipo?.setText("")
            txtFechaNacimiento?.setText("")
        } else {
                Toast.makeText(this, "El campo debe estar completo", Toast.LENGTH_LONG).show()
        }
        baseDatos.close()
    }
    fun editar(view: View){
        val connet=SQLite(this,"patitasDB",null,1)
        val baseDatos=connet.writableDatabase

        val id=txtid?.text.toString()
        val Nombre= txtNombre?.text.toString()
        val Tipo=txtTipo?.text.toString()
        val Fechanacimiento= txtFechaNacimiento?.text.toString()

        if(id.isEmpty()==false && Nombre.isEmpty()==false && Tipo.isEmpty()==false && Fechanacimiento.isEmpty()==false){
            var registro = ContentValues()
            registro.put("id",id)
            registro.put("Nombre",Nombre)
            registro.put("Tipo",Tipo)
            registro.put("FechaNacimiento",Fechanacimiento)

            val cantidad =baseDatos.update("mascotas",registro,"id='$id'",null)
            if(cantidad>0){
                Toast.makeText(this,"La mascota se edito con exito",Toast.LENGTH_LONG).show()

            }else{
                Toast.makeText(this, "La mascota no fue encontrada",Toast.LENGTH_LONG).show()
            }

        }else{
            Toast.makeText(this,"Debe llenar los campos",Toast.LENGTH_LONG).show()
        }


    }

}
