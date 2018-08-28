package com.depuracion.util;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.depruacion.entidad.Usuario;

public class TxtUtil {
	static String[] usuarios = null;
	static String[] claves = null;
	
	
	
	static int contador = 0;

	public static ArrayList<Usuario> obtenerUsuariosYClaves() {
		
		ArrayList<Usuario> datos = new ArrayList<Usuario>();
		

		// Fichero del que queremos leer
		File fichero = new File(Util.TXT_FILE_PATH + Util.TXT_FILE_NAME);
		Scanner s = null;

		try {
			// Leemos el contenido del fichero
			System.out.println("... Leemos el contenido del fichero ...");
			s = new Scanner(fichero);
			// Leemos linea a linea el fichero
			while (s.hasNextLine()) {
				
				String linea = s.nextLine(); 
				// Guardamos la linea en un String
				if (linea.contains("@")) {
					Usuario usuarioActual =  new Usuario();
					usuarioActual.setNombreUsuario(linea.replace(",", ""));
					usuarioActual.setClave(s.nextLine().toString().replace(",", ""));
					datos.add(usuarioActual);

				}

			}
			Iterator<Usuario> itrUsuarios = datos.iterator();
			while (itrUsuarios.hasNext()) {
				Usuario usuario = itrUsuarios.next();
				System.out.println("Usuario:" + usuario.getNombreUsuario());
			}

		} catch (Exception ex) {
			System.out.println("Mensaje: " + ex.getMessage());
		} finally {
			// Cerramos el fichero tanto si la lectura ha sido correcta o no
			try {
				if (s != null)
					s.close();
			} catch (Exception ex2) {
				System.out.println("Mensaje 2: " + ex2.getMessage());
			}
		}
		return datos;
	}

}
