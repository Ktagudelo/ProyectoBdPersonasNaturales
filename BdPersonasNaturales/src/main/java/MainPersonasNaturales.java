
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainPersonasNaturales {
    public static void main(String[] args) throws IOException {

        //LEER EL ARCHIVO "Base de Datos Julio 2012 Persona Natural"

        HSSFWorkbook libro = new HSSFWorkbook(new FileInputStream("BasedeDatosJulio2012PersonaNatural.xls"));
        HSSFSheet hoja = libro.getSheetAt(0);
        List<String> data = new ArrayList<String>();
        List<String> nombres = new ArrayList<>();
        List<String> numeros = new ArrayList<>();
        List<String> fechas = new ArrayList<>();
        List<String> telefonos = new ArrayList<>();


        Iterator iterator = hoja.iterator();

        DataFormatter formatter = new DataFormatter();

        while (iterator.hasNext()) {
            Row nextRow = (Row) iterator.next();

            Cell c = nextRow.getCell(1);
            Cell n = nextRow.getCell(2);
            Cell t = nextRow.getCell(4);
            Cell f = nextRow.getCell(5);


            String contenidoNombres = formatter.formatCellValue(c);
            nombres.add(contenidoNombres);

            String contenidoNumeros = formatter.formatCellValue(n);
            numeros.add(contenidoNumeros);

            String contenidoFechas = formatter.formatCellValue(f);
            fechas.add(contenidoFechas);

            String contenidoTelefonos = formatter.formatCellValue(t);
            telefonos.add(contenidoTelefonos);

            Iterator cellIterator = nextRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = (Cell) cellIterator.next();
                String contenidoBd = formatter.formatCellValue(cell);
                data.add(contenidoBd);

            }
        }

//        ***************************************************************************************
//        TOTAL DE CEDULAS QUE NO CUMPLAN CON EL FORMATO XX.XXX.XXX

        int countCedulasSi = 0, countCedulasNo = 0;

        for(int i = 1; i<numeros.size(); i++) {
            Pattern patNum = Pattern.compile("[\\d*]{2}\\.[\\d*]{3}\\.[\\d*]{3}");
            Matcher matNum = patNum.matcher(numeros.get(i));
            if (matNum.matches()) {
                  countCedulasSi++;
            } else {
                  countCedulasNo++;
            }
        }
//        System.out.println("Formato correcto XX.XXX.XXX: " + countCedulasSi);
          System.out.println("Total de cedulas que no cumplen con el formato XX.XXX.XXX: " + countCedulasNo);

//        ***************************************************************************************

//      TOTAL DE NUMEROS TELEFONICOS QUE NO CUMPLEN CON EL FORMATO DE CELULAR O TELEFONO FIJO

        int countTeleSi = 0, countTeleNo = 0;

        for(int i = 1; i < telefonos.size(); i++) {

            Pattern patTel = Pattern.compile("[\\d*]{7}|[\\d*]{10}");
            Matcher matTel = patTel.matcher(telefonos.get(i));
            if (matTel.matches()) {
                countTeleSi++;
            } else {
                countTeleNo++;
            }

        }

//        System.out.println("Formato correcto telefono 7 o 10 digitos: " + countTeleSi);
          System.out.println("Total de numeros telefonicos que no cumplen con el formato de celular o telefono fijo: " + countTeleNo);

//        ***************************************************************************************

//      OBTENER EL TOTAL DE FECHAS DE RESOLUCION QUE SE ENCUENTREN MAL ESCRITAS EN EL RANGO DE 2011 - 2012

        //FORMATO DE FECHA DE RESOLUCION
        //MES EN LETRAS, DIA EN NUMEROS, ACOMPAÑADO DEL CONECTOR ¨DE¨
        // Y FINALIZANDO EL AÑO EN NUMEROS EJEMPLO: AGOSTO 9 DE 2011

        int countFechaSi = 0, countFechaNo = 0;

        for(int i = 1; i < fechas.size(); i++) {

            if (fechas.get(i).contains("2011") || fechas.get(i).contains("2012")) {

                Pattern patFecha = Pattern.compile("\\D*\\s\\d*\\s(DE)\\s[\\d*]{4}");
                Matcher matFecha = patFecha.matcher(fechas.get(i));
                if (matFecha.matches()) {
                    countFechaSi++;
                } else {
                    countFechaNo++;
                }
            }

        }

//      System.out.println("Formato correcto fecha: " + countFechaSi);
        System.out.println("Formato de fecha de resolución Incorrecto: " + countFechaNo);


//        ***************************************************************************************

        //OBTENER EL NUMERO DE CUANTOS NOMBRES DIFERENTES HAY EN LA LISTA. PARA ESTO ES NECESARIO DISTINGUIR EL NOMBRE DEL APELLIDO,
        //TENIENDO EN CUENTA QUE CADA USUARIO TIENE DOS APELIDOS. EJEMPLO: ADRIANA PAOLA CUJAR ALARCON, CUENTA CON DOS NOMBRES.



//        ***************************************************************************************

//        OBTENER EL TOTAL DE PERSONAS QUE TIENEN COMO PROFESION "INGENIERA DE ALIMENTOS"

          Iterator<String> dataIterator = data.iterator();
          int totalPersonas = 0;

          while(dataIterator.hasNext()){
             String elemento = dataIterator.next();
             if(elemento.equals("INGENIERA DE ALIMENTOS"))
                totalPersonas++;
          }

          System.out.println("Obtener el total de personas que tienen como profesión INGENIERIA DE ALIMENTOS: " + totalPersonas);

//        ***************************************************************************************

//        OBTENER EL PROMEDIO MENSUAL DE RESOLUCIONES QUE SE REALIZARON EN LOS MESES DE ENERO, FEBRERO Y MARZO DEL AÑO 2011.
//        EJEMPLO: SI EN ENERO DEL 2011 SE OBTUVIERON 5, FEBRERO 10 Y MARZO 20, EL PROMEDIO SERA 10 RESOLUCIONES EN LOS PRIMEROS 3 MESES DEL 2011.


//        ***************************************************************************************

//        OBTENER EL NOMBRE QUE MAS SE REPITE EN TODA LA LISTA

          List<String> cadenaNombres = new ArrayList<>();
          List<String> cadenaNombresC = new ArrayList<>();

          Collections.sort(nombres);

          for(int i = 1; i<nombres.size(); i++){
            String str = nombres.get(i);
            cadenaNombres = Arrays.asList(str.split(" "));
            cadenaNombresC = Arrays.asList(str.split(" "));

          }

        for(int j = 0; j<cadenaNombres.size(); j++){


//                System.out.println("Prueba Separar Cadena1:" + cadenaNombres.get(j));
//                System.out.println("Prueba Separar Cadena1:" + cadenaNombresC.get(j));
        }

        int countRepetidos = 0;

        if (cadenaNombres.equals(cadenaNombresC)){
            countRepetidos++;

        }

        System.out.println("Los nombres repetidos son: " + countRepetidos);

        System.out.println("********************************************************************");

//        ***************************************************************************************

//        GENERAR ARCHIVO DE SALIDA "Tabla_Resultados.Json" QUE CONTENGA LA SIGUIENTE INFORMACION

          JSONObject myObject = new JSONObject();

          myObject.put("Total de cédulas que no cumplen con el formato.", countCedulasNo);
          myObject.put("Total de números telefónicos que no cumplen con el formato", countTeleNo);
          myObject.put("Total de fechas de Resolución resultantes mal escritas", countFechaNo);
          myObject.put("Total de nombres diferentes en la tabla", 0);
          myObject.put("Total de personas cuya profesión sea INGENIERIA DE ALIMENTOS", new Integer(totalPersonas));
          myObject.put("Promedio del total de resoluciones en los 3 primeros meses del año 2011", 0);
          myObject.put("Nombre más usado en los candidatos", countRepetidos);

          FileWriter file = new FileWriter("Tabla_Resultados.json");
          file.write(String.valueOf(myObject));
          file.flush();
          file.close();
          System.out.println(" ");
          System.out.println("RESULTADO ARCHIVO JSON - ESTE SE ENCUENTRA EN LA RAIZ DEL PROYECTO");
          System.out.print(myObject + "\n");

//*************************************************************************************************


//           for (Cell elemento : nombres)
//            System.out.println(elemento);

//            for (String elemento : fechas)
//            System.out.println(elemento);




    }
}
