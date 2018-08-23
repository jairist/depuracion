package com.depuracion.util;

public class EnumWordDictionary {

	public static enum enumNombreColumna {
		TestCaseName("Nombre Caso de Prueba"),
		Usuario("Usuario"),
		Clave("Clave"),
		Resultado("Resultado");

		public String valor;

		private enumNombreColumna(String datoRecibido) {
			this.valor = datoRecibido;
		}
	}

}